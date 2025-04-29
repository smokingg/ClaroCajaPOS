package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.proxy.AppControlProxy;

import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;

/**
 * Implementacion de la transaccion final de cierre de pago, en ella se revisan si se cumplen las condiciones 
 * de cierre, se confirman los medios de pago y se vacian los carros
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TrxCerrarPago implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    long newSaldo = 0;
    long saldo = 0;
    DocumentoPago docAbono = null;
    String email;
    
    public void init(Datos datosVista){
        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 || key == 2 ) {
            estado = 0;
            if(vista.getOperTRV().getCarroCompras().getDocumentos().size() == 0){
                JOptionPane.showMessageDialog(null, "Carro de Compras vacio\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 14;
            }
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() == 0){
                JOptionPane.showMessageDialog(null, "Carro de Medios de Pago vacio\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            if(Base.getEdicion()){
                saldo = vista.getOperTRV().getCarroMediosPago().getMontoTotal() - vista.getOperTRV().getCarroCompras().getMontoTotal();
                if(vista.getOperTRV().getCarroMediosPago().getMontoTotal() != vista.getOperTRV().getCarroCompras().getMontoTotal()){
                    JOptionPane.showMessageDialog(null, "Crear documento de abono\nMonto restante: " + Format.formatMonto(saldo), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    return 17;
                }
            }
            //vista.setInputTimeout(15);
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } 
        else if(  key == KeyEvent.VK_F9) {
        	if(Base.getEdicion()){
        		return 20;
        	}
        	else{
        		return ICajaView._PASSTHROUGH;
        	}
        } 
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        
    	ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
     
        switch( estado ) {
        	case 0:
        		
        		estado=3;
        		
        		  if(isPdf) {
                     	LineaVoucher []aux = null;
                 		Set<String> ruts = new HashSet<String>();
                 		ArrayList rut = new ArrayList();
                 		for(int i = 0; i < vista.getOperTRV().getCarroCompras().getDocumentos().size(); i++){
                 			aux = vista.getOperTRV().getCarroCompras().getDocument(i).getCustomerVoucher();
                 			
                 			for(int j = 0; j < aux.length; j++){
                 				if (aux[j].getLinea().contains("RUT:")) {
                 					ruts.add(aux[j].getLinea().replace("RUT:", "").trim());
                 				}
                 			}
                 		}
                 		
                 		String[] listaRuts = new String[0];
                 		Base.logger.info("DOCUMETO1: "+ruts.toString());
                 		
                     	
                 		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
                 		try {
                 			email = pr.obtenerEmailsPorRut(ruts.toArray(new String[ruts.size()]));
      					} catch (RemoteException e) {
      						// TODO Auto-generated catch block
      						e.printStackTrace();
      					}
                 		
                 		

                     	Base.logger.info("EMAILS: "+email);
                     	

                     vista.setEntryMessage( "Email(s) para el envio de Boleta", true );
         			 vista.setEntryTextLabel("Email(s):", true);
                     vista.setEntryText(email, true, false, false, null, null);
                     return ICajaView._WAITFORACTION;
                     }
                     
        	     
            case 3:
            	
            	// Bloque de ajuste de sencillo al monto pagado en efectivo

            	boolean 	montoInsuficiente 	= Boolean.FALSE;
            	long 		montoAjustado 		= 0;
            	
            	// si existe el medio de pago efectivo debo ajustar el sencillo
            	if(vista.getOperTRV().getCarroMediosPago().existeMPEfectivo()){
            		
            		// se calcula el monto pagado en efectivo y aplico el ajusto solo al monto que se paga en efectivo
            		long montoPagadoEfectovo = vista.getOperTRV().getCarroCompras().getMontoTotal() - (vista.getOperTRV().getCarroMediosPago().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoEfectivo());
            		montoAjustado = AjusteSencillo.montoAjustado(montoPagadoEfectovo);
            		
            		// se revisa el salgo que queda al completar los medios de pago y aplicar el ajuste
            		saldo = vista.getOperTRV().getCarroCompras().getMontoTotal() - (vista.getOperTRV().getCarroMediosPago().getMontoTotal() - montoAjustado);
            		
            		// si el saldo es negativo o 0 es porque se completo el pago.
            		if(saldo <= 0){

        				//Agrego el ajuste de sencillo como medio de pago, si y solo si el monstoAjustado es disitnto de 0
            			if(montoAjustado != 0){
            				
            				MedioPago mAjusteSencillo = null;
                			
                            try{
                            	
                            	mAjusteSencillo = FactoryMedioPago.makeInstance("AjusteSencillo");
                            	mAjusteSencillo.getDatos().setValue("Monto", montoAjustado);
                                mAjusteSencillo.getDatos().setValue("MontoAjustado", montoAjustado);
                                vista.getOperTRV().getCarroMediosPago().addMedioPago(mAjusteSencillo);
                    			
                            } 
                            catch(BaseException e) {
                            	
                                Tools.logStackTrace(Base.logger, e);
                                Base.logger.error("Error en agregar ajuste al carro");
                            
                            }
            			
            			}

            			
            		}else{
     
        				// si la resta es positiva es porque falta monto por cubrir
            			montoInsuficiente = Boolean.TRUE;
            			
            		}
            		
            		
            	}else{
            		
            		// si no tiene efectivo, valido la regla general para el monto insuficiente
            		montoInsuficiente = vista.getOperTRV().getCarroCompras().getMontoTotal() > vista.getOperTRV().getCarroMediosPago().getMontoTotal();
            		saldo = vista.getOperTRV().getCarroMediosPago().getMontoTotal()-vista.getOperTRV().getCarroCompras().getMontoTotal();
            	}
            	
            	// FIN BLOQUE AJUSTE SENCILLO

                if(montoInsuficiente){
                	
                    JOptionPane.showMessageDialog(null, "Carro de Medios de Pago insuficiente\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
               
                }
                
                long 		vuelto 				= vista.getOperTRV().getCarroMediosPago().getVuelto(vista.getOperTRV().getCarroCompras().getMontoTotal());
                
                
               	
                if(saldo == 0){
                    estado = 1;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Cierre Pago", true );
                    vista.setEntryTextLabel("Detalle:", true);
                    vista.setEntryTextArea(vista.getOperTRV().imprimirBoletaPantalla(), true);
                    vista.setEntryMessage("Vuelto: $" + Format.formatMontoPantalla(vuelto), true);
                    return ICajaView._WAITFORACTION;                    
                }
                else{
                    //Alcanza el efectivo pagado para dar el vuelto
                    if(saldo <= vista.getOperTRV().getCarroMediosPago().getMontoEfectivo()){
                        estado = 2;  
                        vista.hideAllEntries();
                        vista.setEntryTitle( "Cierre Pago", true );
                        vista.setEntryTextLabel("Detalle:", true);
                        vista.setEntryTextArea(vista.getOperTRV().imprimirBoletaPantalla(), true);
                        vista.setEntryMessage("Vuelto: $" + Format.formatMontoPantalla(vuelto), true);
                        return ICajaView._WAITFORACTION;                        
                    }
                    else if(saldo > vista.getOperTRV().getCarroMediosPago().getMontoEfectivo() && vista.getOperTRV().getCarroMediosPago().getMontoEfectivo() != 0){
                        //Devolvemos lo que se pueda en efectivo, el resto se abona
                        newSaldo = saldo-vista.getOperTRV().getCarroMediosPago().getMontoEfectivo();
                        JOptionPane.showMessageDialog(null, "Devolver en efectivo\nSaldo: " + Format.formatMonto(saldo-newSaldo) + "\nCrear documento de abono\nSaldo: " + Format.formatMonto(newSaldo), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        return 17;
                    }
                    //Es necesario instanciar un documento de abono futuro para igualar el saldo
                    else{
                        JOptionPane.showMessageDialog(null, "Crear documento de abono\nMonto restante: " + Format.formatMonto(saldo), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        return 17;
                    }
                }
                
                           
            case 1:
            	boolean recarga = false;
            	boolean transBank = false;
            	int rc1 = -1;
            	// Se agrega confirmacion para generar Notificacion de Pago...
            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		
            		 vista.showBusyWindow("Enviando", "Espere por favor...");
            		 String respVC =  vista.getOperTRV().getDatos().getStringValue("voucherComercio");
            		 if (respVC != null){
            			 
            			 if (respVC.trim().length() > 0){
            				 rc1 = vista.getOperTRV().confirmarTBK(vista, respVC);
            				 transBank = true;
            						 
            			 }else{
            				 rc1 = vista.getOperTRV().confirmar( vista );
            			 }
            			 
            		 }else{
            			 rc1 = vista.getOperTRV().confirmar( vista );
            		 }
            		 
            		 if (rc1 != 0 && transBank){
            			 Base.logger.info("P I N P A D - cierre y confirmacion");
                     	TbkMetodos metodos = new TbkMetodos();
             			String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
             			metodos.cierre(Base.tbk,requerimiento);
             			metodos.confirmarOperacion(Base.tbk);
                     }
            		 
                                     
                     if(vista.getOperTRV().isRecarga())
                     	recarga = true;
                     vista.hideBusyWindow();
                     vista.removeTRV();
                     // Eliminamos lo que podria ser una edicion de Pago
                     Base.setEdicion(false);
                     if(recarga)
                     	return 17;
                     
            	}
            	 // Eliminamos lo que podria ser una edicion de Pago
                Base.setEdicion(false);
            	vista.removeTRV();
            	return 15;
            	
            case 2:
            	// Se agrega confirmacion para generar Notificacion de Pago...
            	boolean transBank2 = false;
            	int rc2 = -1;
            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		
            		 vista.showBusyWindow("Enviando", "Espere por favor...");
                     //int rc2 = vista.getOperTRV().confirmar( vista );
                     //vista.hideBusyWindow();
            		 String respVC =  vista.getOperTRV().getDatos().getStringValue("voucherComercio");
            		 if (respVC != null){
            			 
            			 if (respVC.trim().length() > 0){
            				 rc2 = vista.getOperTRV().confirmarTBK(vista, respVC);
            				 transBank2 = true;
            						 
            			 }else{
            				 rc2 = vista.getOperTRV().confirmar( vista );
            			 }
            			 
            		 }else{
            			 rc2 = vista.getOperTRV().confirmar( vista );
            		 }
            		 
            		 if (rc2 != 0 && transBank2){
            			 Base.logger.info("P I N P A D - cierre y confirmacion");
                     	TbkMetodos metodos = new TbkMetodos();
             			String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
             			metodos.cierre(Base.tbk,requerimiento);
             			metodos.confirmarOperacion(Base.tbk);
                     }
            		 
                     
                     recarga = false;
                     if(vista.getOperTRV().isRecarga())
                     	recarga = true;
                     vista.removeTRV();
                     // Eliminamos lo que podria ser una edicion de Pago
                     Base.setEdicion(false);
                     if(recarga)
                     	return 17;
                    
            	}
            	vista.removeTRV();
            	// Eliminamos lo que podria ser una edicion de Pago
                Base.setEdicion(false);
            	return 16;
               
        }
        return 0;
    }
    
}
