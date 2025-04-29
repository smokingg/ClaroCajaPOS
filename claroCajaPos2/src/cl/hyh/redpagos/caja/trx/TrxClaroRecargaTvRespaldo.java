package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.proxy.AppControlProxy;

/**
 * Esta clase implementa la recarga para TV de Claro 
 * @author cbriones
 *
 */
public class TrxClaroRecargaTvRespaldo implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    long celular= 0;
    String celularL = "";
    boolean ok = true;
    DocumentoPago doc = null;
    DocumentoPago docP = null;
    long montoMax = 0;
    long montoMin = 0;
    ParamSet recargaCfg = Base.getParamSet( "recargaCfg" );
    ParamSet recargaDat = Base.getParamSet( "recargaDat" );
    ParamSet posDat = Base.getParamSet( "posDat" );
  //  RecargaOut resp = null;
    long monto = 0;
    String email;
    
    public void init(Datos datosVista){        
    	/** Se comentan para pantalla Dummy !!
        montoMax = Long.parseLong(recargaCfg.getStringValue( "montoMax" ));
        montoMin = Long.parseLong(recargaCfg.getStringValue( "montoMin" ));
        */
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
        if( key == 0 ) {
            estado = 0;
            if(vista.getOperTRV().getCarroCompras().getDocumentos().size() > 0){
                JOptionPane.showMessageDialog(null, "Carro de Compras no vacío\nOperación no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 14;
            }
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9 && estado > 5 ) {
            return ICajaView._WAITFORACTION;
        }else {
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga Claro", true );
                if(ok)
                    vista.setEntryMessage( "Ingrese Rut", true );
                else
                    vista.setEntryMessage( "Números no coinciden", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Número ingresado no válido");
                //return ICajaView._WAITFORACTION; // esta es la original !!
                return ICajaView._NOWAITFORACTION;
            case 1:
            	
            	// Se habilita msj momentaneo !!!
                JOptionPane.showMessageDialog(null, "No se encuentra habilitado este Módulo....", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                return 13;
                /** se comenta para solo mostrar ventana de advertencia !!!
                estado = 2;
                celularL = vista.getEntryText();
                if( celularL.length() <= 8 && celularL.length() >= 12){
                    estado = 1;
                    vista.hideAllEntries();
                    vista.setEntryMessage( "Largos incorrectos", true );
                    vista.setEntryTextLabel("Ingrese Número:", true);
                    vista.setEntryText("", true, false, false,"[0-9]+", "Número ingresado no válido");
                    return ICajaView._WAITFORACTION;
                }                
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga Vtr", true );
                vista.setEntryMessage( "Reingrese Número", true );
                vista.setEntryTextLabel("Reingrese Número:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Número ingresado no válido");
                return ICajaView._WAITFORACTION;             
                */   
            case 2:
                estado = 3;
                
                if(!celularL.equals(vista.getEntryText())){
                    estado = 0;
                    ok = false;
                    return ICajaView._NOWAITFORACTION;
                }
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga Vtr", true );
                vista.setEntryMessage( "Ingrese monto recarga", true );
                vista.setEntryTextLabel("Ingrese monto recarga:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
            case 3:
                estado = 4;
                monto = Long.parseLong(vista.getEntryText());
                
                if(monto > montoMax || monto < montoMin){
                    estado = 3;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Recarga Vtr", true );
                    vista.setEntryMessage( "Monto no válido", true );
                    vista.setEntryTextLabel("Ingrese monto recarga:", true);
                    vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                    return ICajaView._WAITFORACTION;
                }    
                
                if(vista.showMyConfirmDialog("Se realizará la recarga","Está operación no se puede reversar una vez realizada\nDesea continuar?")
                        == JOptionPane.NO_OPTION){
                    return 13;
                }
                
                //Seteamos los parametros del servicio
                                
                int numeroSecuencia = recargaDat.getIntValue( "NumeroSecuencia" );
                recargaDat.setValue( "NumeroSecuencia", numeroSecuencia + 1 );
                recargaDat.save();
                
                ServerProxy pr = Proxy.getProxyInstance();
                /**
                 * cbriones: se comenta para eliminar errores..
                 * se debe realizar flujo para Claro !!!
                 
                RecargaIn rIn = new RecargaIn();
            	HeaderIn hIn = new HeaderIn();
            	
            	hIn.setAgencia((int)(posDat.getLongValue("Agencia")));
        		hIn.setCajaFisica((int)(posDat.getLongValue("Caja")));
        		hIn.setEntidad((int)(posDat.getLongValue("Entidad")));
        		
        		rIn.setHeaderIn(hIn);
        		rIn.setAbonado(Long.parseLong(celularL));
        		rIn.setCanal(Integer.parseInt(recargaCfg.getStringValue( "canal" )));
        		rIn.setCodigoProducto(Integer.parseInt(recargaCfg.getStringValue( "codigoProducto" )));
        		rIn.setFechaVenta(Tools.getFecha());
        		rIn.setHoraVenta(Tools.getTime());
        		rIn.setIdDistribuidor(Integer.parseInt(recargaCfg.getStringValue( "idDistribuidor" )));
        		rIn.setIdSubDistribuidor(Integer.parseInt(recargaCfg.getStringValue( "idSubDistribuidor" )));
        		rIn.setIdTerminal(Integer.parseInt(recargaCfg.getStringValue( "idTerminal" )));
        		rIn.setMonto((int)monto);
        		rIn.setSecuenciaTransaccion(numeroSecuencia);
        		
        		resp = null;
                
        		vista.showBusyWindow("Consultando", "Espere por favor...");
        		
        		try {
        			resp = pr.recarga(rIn);
        		} catch (RemoteException e) {
        			vista.hideBusyWindow();
                    Tools.logStackTrace(Base.logger, e);
                    JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
        		}
        		vista.hideBusyWindow();
        		
        		if(resp.getHeaderOut().getRc() != 0){
        			JOptionPane.showMessageDialog(null, resp.getHeaderOut().getRcMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
        			return 13;
        		}
				
				*/
                
                vista.setEntryMessage( "Ingrese monto a cancelar", true );
                vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                vista.setEntryText(Long.toString(monto), true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
            case 4:
                estado = 5;
                MedioPago mPago = null;
                String montoM = vista.getEntryText();
                if(Long.parseLong(montoM) < monto){
                    estado = 4;
                    vista.setEntryMessage( "Monto insuficiente:" + Format.formatMonto(Long.parseLong(montoM)), true );
                    vista.setEntryTextLabel("Monto insuficiente:", true);
                    vista.setEntryText(Long.toString(monto), true, false, false,"[0-9]+", "Monto ingresado no válido");
                    return ICajaView._WAITFORACTION;
                }
                try{
                    mPago = FactoryMedioPago.makeInstance("Efectivo");
                } 
                catch(BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                }          
                
                mPago.getDatos().setValue("Monto", montoM);

                //Crear documento de recarga
                DocumentoPago docR = null;
                try {
                    docR = FactoryDocumentoPago.makeInstance("RecargaMovistar");
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }
                docR.getDatos().setValue("Telefono", celularL );
               // docR.getDatos().setValue("CodAutorizacion", resp.getCodigoAutorizacion());
                docR.getDatos().setValue("Monto", mPago.getMonto());
                mPago.getDatos().setValue("Monto", Long.parseLong(montoM));
                vista.getOperTRV().getCarroCompras().addDocment(docR);
                vista.getOperTRV().getCarroMediosPago().addMedioPago(mPago);
                
                String []botones = new String[12];
                for(int i = 0; i < botones.length; i++){
                    botones[i] = "";
                }
                vista.paintButtons(botones);
                return ICajaView._NOWAITFORACTION;  
            case 5:
                estado = 7;
                vista.hideAllEntries();
                vista.getOperTRV().setVentaDirecta("true");
                vista.getOperTRV().setRecargaMovistar("true");
                return ICajaView._NOWAITFORACTION;        
            case 7:  ////// NUEVO PARA IMPRIMIR PDF
                estado = 6;
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
            		AppControlCajaWSServerProxy pr2 = AppControlProxy.getProxyInstance();
            		try {
            			email = pr2.obtenerEmailsPorRut(ruts.toArray(new String[ruts.size()]));
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
                
            case 6:
				email = vista.getEntryText();
                Base.logger.info("EMAILS: "+email);
                this.data.setValue("emails",email);
                vista.showBusyWindow("Enviando", "Espere por favor..."); 
                vista.getOperTRV().setRecarga( true );
                vista.getOperTRV().confirmar(vista);
                vista.hideBusyWindow();
                vista.removeTRV();
                return 13;      

        }
        return 0;
    }
    
    private String getTelefono(String fono){
        String telefono = "";
        
        if( fono.length() == 11){
            long area = Long.parseLong(fono.substring(0,3));
            long tele = Long.parseLong(fono.substring(4,fono.length()));
            String res = "0" + Long.toString(area) + Long.toString(tele);
            return res;
        }
        return fono;
    }
}
