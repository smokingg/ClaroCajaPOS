package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.proxy.AppControlProxy;

public class TrxEfectivoDirecto implements ITrxBase{
    int estado = 0;
    Datos data;
    long montoTotal = 0;
    String email;
    MedioPago mPago = null;
    MedioPago mAjusteSencillo = null;
    
    public void init(Datos datosVista){        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
    	ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
        if( key == 0 ) {
            estado = 0;
            if(vista.getOperTRV().getCarroCompras().getDocumentos().size() == 0){
            	JOptionPane.showMessageDialog(null, "No tiene documentos en el carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                JOptionPane.showMessageDialog(null, "Carro de Medios de Pago ya existe\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9 ) {
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                vista.getOperTRV().getCarroMediosPago().borrarCarro();
            }
            return 13;
        }else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                DefMedioPago defM = Base.getDefMedioPago("Efectivo");
                data = new Datos(defM.getRecordDef());  
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal();
                data.setValue("Monto", AjusteSencillo.ajustarMontoSencillo(montoTotal)); 
                
                vista.setEntryMessage( "Ingrese monto a cancelar", true );
                vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                vista.setEntryText(Long.toString(AjusteSencillo.ajustarMontoSencillo(montoTotal)), true, false, false,"[0-9]{1,12}","Monto no valido");
                return ICajaView._WAITFORACTION;
            case 1:
                estado = 2;
                mPago = null;
                String montoM = vista.getEntryText();
                if(Long.parseLong(montoM) < this.data.getLongValue("Monto")){
                    estado = 1;
                    vista.setEntryMessage( "Monto insuficiente:" + Format.formatMonto(Long.parseLong(montoM)), true );
                    vista.setEntryTextLabel("Monto insuficiente:", true);
                    vista.setEntryText(Long.toString(this.data.getLongValue("Monto")), true, false, false,"[0-9]+","Monto no valido");
                    return ICajaView._WAITFORACTION;
                }
                // MSPULVEDA: valido que el monto ingresado sea modulo de 10 
                // Proyecto: Ajuste sencillo
                if(Long.parseLong(montoM) % AjusteSencillo.DIEZ != 0){
                	estado = 1;
                    vista.setEntryMessage( "Monto incorrecto, ingrese valor redondeado:" + Format.formatMonto(Long.parseLong(montoM)), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(this.data.getLongValue("Monto")), true, false, false,"[0-9]+","Monto no valido");
                    return ICajaView._WAITFORACTION;
                }
                try{
                    mPago = FactoryMedioPago.makeInstance("Efectivo");
                } 
                catch(BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
                try{
                	mAjusteSencillo = FactoryMedioPago.makeInstance("AjusteSencillo");
                } 
                catch(BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
                this.data.setValue("Monto", Long.parseLong(montoM));
                mPago.getDatos().asignaPorNombre(this.data);
                mAjusteSencillo.getDatos().setValue("Monto", AjusteSencillo.montoAjustado(montoTotal));
                mAjusteSencillo.getDatos().setValue("MontoAjustado", AjusteSencillo.montoAjustado(montoTotal));
                
                
            case 2:  ////// NUEVO PARA IMPRIMIR PDF
                estado = 3;
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
            	/*	
            		for(int k = 0; k < ruts.size(); k++){
            			listaRuts[k]=ruts[0].toString();
            		}
            		*/
            		//String[] listaRuts = ruts.toArray(new String[ruts.size()]);
            		
            		
            		
            		
            		
                	//Base.logger.info("DOCUMETO: "+listaRuts.);
                	
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
                estado = 4;
                email = vista.getEntryText();
                Base.logger.info("EMAILS: "+email);
                
                this.data.setValue("emails",email);
                
                mPago.getDatos().asignaPorNombre(this.data);
                
                vista.getOperTRV().getCarroMediosPago().addMedioPago(mPago);
                vista.getOperTRV().getCarroMediosPago().addMedioPago(mAjusteSencillo);
                
                
                vista.setEntryMessage("", false);
                vista.hideAllEntries();
                vista.setEntryTitle( "Pago Efectivo", true );
                vista.setEntryTextLabel("Medio Pago:", true);
                vista.setEntryTextArea(vista.getOperTRV().imprimirBoletaPantalla(), true);
                long vuelto = vista.getOperTRV().getCarroMediosPago().getVuelto(vista.getOperTRV().getCarroCompras().getMontoTotal());
                vista.setEntryMessage("Vuelto: $" + Format.formatMontoPantalla(vuelto), true);
                return ICajaView._WAITFORACTION;
                
            case 4:            
            	 // Se agrega confirmacion para generar Notificacion de Pago...
            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		
            		 vista.showBusyWindow("Enviando", "Espere por favor...");
                     vista.getOperTRV().confirmar(vista);
                     vista.hideBusyWindow();
                      
            	}
            	vista.removeTRV();	
                return 14;
        }
        return 0;
    }

}
