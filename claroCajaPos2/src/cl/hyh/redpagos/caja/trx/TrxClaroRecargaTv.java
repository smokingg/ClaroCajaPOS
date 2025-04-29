package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarDenominacionInDTO;
import ws.claro.cl.ConsultarDenominacionOutDTO;
import ws.claro.cl.DenominacionDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Esta clase implementa la recarga para TV de Claro 
 * @author cbriones
 *
 */
public class TrxClaroRecargaTv implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    long celular= 0;
    String celularL = "";
    boolean ok = true;
    boolean okDenominacion = true;
    DocumentoPago doc = null;
    DocumentoPago docP = null;
    long montoMax = 0;
    long montoMin = 0;
    ParamSet recargaCfg = Base.getParamSet( "recargaCfg" );
    ParamSet recargaDat = Base.getParamSet( "recargaDat" );
    ParamSet posDat = Base.getParamSet( "posDat" );
  //  RecargaOut resp = null;
    long monto = 0;
    
    
    String []opt = null;
    DenominacionDTO[] denominaciones = null;
    DetalleDocumento cuentaDocClaro = null;
    String trv = null;
    
    
    /**
     * 
    public void init(Datos datosVista){        
        montoMax = Long.parseLong(recargaCfg.getStringValue( "montoMax" ));
        montoMin = Long.parseLong(recargaCfg.getStringValue( "montoMin" ));
    }
	*/

    @Override
	public void init(Datos datosVista) {
		// TODO Auto-generated method stub
		trv = datosVista.getStringValue("btnParam");
	}
    
    public int execute(ICajaView vista, int key, Datos datos){ 
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
                //vista.hideAllEntries();
                vista.setEntryTitle( "Recarga TV Claro", true );
                vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION; // esta es la original !!
                //return ICajaView._NOWAITFORACTION;
                
            case 1:
            	
                estado = 2;
                
                codigo = vista.getEntryText();
                
                if("".trim().equalsIgnoreCase(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "No ha ingresado ningun Rut - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                if(!Tools.validarRut(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "RUT  Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }        
                return ICajaView._NOWAITFORACTION;             
                
            case 2:
            	// Aca debe solicitar Monto Cancelar !!
                estado = 3;
                /**
                if(!celularL.equals(vista.getEntryText())){
                    estado = 0;
                    ok = false;
                    return ICajaView._NOWAITFORACTION;
                }
                */
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga TV Claro", true );
                vista.setEntryTextLabel("Ingrese monto recarga:", true);
                
                // valido el OK denominacion para solicitar el monto de recarga
                if(okDenominacion){
                	vista.setEntryMessage( "Ingrese monto recarga", true );
                }else{
                    vista.setEntryMessage( "Monto de recarga no válido", true );
                }
                // TODO se debe implementar invocacion al servicio para traer listado de montos...
                
                //////////////////////////////////////////////
                AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
                
                ConsultarDenominacionInDTO hIn = new ConsultarDenominacionInDTO();
            	hIn.setTipoDenominacion("2");
        		Base.logger.info( "Denominacion recarga Fijo: " + hIn.getTipoDenominacion());
        		
        		/////////////////////////////////////
        		
        		vista.showBusyWindow("Consultando", "Espere por favor...");
            	
            	////////////////////////////////////////////7
        		ConsultarDenominacionOutDTO resp = null;
        		
        		try { 
        			resp = pr.consultarDenominacion(hIn);
        			Base.logger.info( "Codigo resp Denominacion: " + resp.getRetCode());
        			Base.logger.info( "Desc resp Denominacion: " + resp.getRetDesc());
        		} catch (RemoteException e) {
        			JOptionPane.showMessageDialog(null, resp.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					Tools.logStackTrace(Base.logger, e);
					vista.hideBusyWindow();
					return 13;
        		}
        		
        		if(!"0".equalsIgnoreCase(resp.getRetCode())){	
					Base.logger.error("No existen datos [RetCode]:" + resp.getRetCode() + " [msg]:" + resp.getRetDesc());
                    JOptionPane.showMessageDialog(null, resp.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    vista.hideBusyWindow();
					return 13;
				}
        		
        		
        		 vista.hideBusyWindow();
        		opt = new String[resp.getDenominacionesList().length];
        		denominaciones = resp.getDenominacionesList();
                
        		Base.logger.info( "Cantidad de Denominaciones: " + resp.getDenominacionesList().length);
        		
        		// TODO: llenar arreglo con valores retornados...
        		for(int i=0; i<resp.getDenominacionesList().length; i++){
        			Base.logger.info( "Denominacion["+i+"]: " + resp.getDenominacionesList(i).getDenominacion());
        			Base.logger.info( "Codigo Denominacion["+i+"]: " + resp.getDenominacionesList(i).getCodDenominacion());
        			Base.logger.info( "Monto Minimo Denominacion["+i+"]: " + resp.getDenominacionesList(i).getMontoMinimo());
        			Base.logger.info( "Monto Maximo Denominacion["+i+"]: " + resp.getDenominacionesList(i).getMontoMaximo());
        			opt[i] = String.valueOf(resp.getDenominacionesList(i).getMontoMinimo());
        			
        		}
//                vista.setEntryList(opt, true, true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
                
            case 3:
            	estado = 4;
                //monto = Long.parseLong(vista.getEntryText());
//                int index = vista.getEntryListIndex();
//                monto = Long.parseLong(opt[index]);
                
            	monto = Long.parseLong(vista.getEntryText());
                boolean validateDenominacion = Boolean.FALSE;
                //Se realiza la validacion de denominacion y que cuadre con un rango
                for (DenominacionDTO  denominacion : denominaciones) {
					if(monto >= denominacion.getMontoMinimo() && monto <= denominacion.getMontoMaximo()){
						validateDenominacion=Boolean.TRUE;
					}
				}
                // si no es valida la denominacion, envio nuevamente al estado 2 con el ok denominacion en falso para mostrar el mensaje
                if(!validateDenominacion){
                	
                    estado = 2;
                    okDenominacion = false;
                    ok=true;
                    return ICajaView._NOWAITFORACTION;
                    
                }
            	
            	
                //Seteamos los parametros del servicio
                // Se debe generar una Notificacion para Poder realizar la Reversa !!
                
                int numeroSecuencia = recargaDat.getIntValue( "NumeroSecuencia" );
                recargaDat.setValue( "NumeroSecuencia", numeroSecuencia + 1 );
                recargaDat.save();
                
                /**
                 * cbriones: Se debe generar un Doc Cuenta Claro de la nada !!
                 * Luego este se debe subir al carro de compras, para proceder a seleccionar el Medio de pago..
                 */ 
                 
                return ICajaView._NOWAITFORACTION;
                
            case 4:
            	estado = 5;
                
                // Aca  debe generarse el Medio de pago Efectivo,  puesto q puede ser uno entre varias opciones...
                MedioPago mPago = null;
                /**
                 * Aca no debe validarse el Monto, ya que se selecciona..
                
                String montoM = vista.getEntryText();
                if(Long.parseLong(montoM) < monto){
                    estado = 4;
                    vista.setEntryMessage( "Monto insuficiente:" + Format.formatMonto(Long.parseLong(montoM)), true );
                    vista.setEntryTextLabel("Monto insuficiente:", true);
                    vista.setEntryText(Long.toString(monto), true, false, false,"[0-9]+", "Monto ingresado no válido");
                    return ICajaView._WAITFORACTION;
                }
                 */
//                try{
//                    mPago = FactoryMedioPago.makeInstance("Efectivo");
//                } 
//                catch(BaseException e) {
//                    Tools.logStackTrace(Base.logger, e);
//                }          
                
                // Se agrega el Monto a cancelar en el Medio de Pago..
//                mPago.getDatos().setValue("Monto", monto);
                
                //Crear documento de Cuenta Claro de mentira, debe generarse con la informacion minima 
                DocumentoPago docR = null;
                
                try {
                    //docR = FactoryDocumentoPago.makeInstance("RecargaMovistar");
                    docR = FactoryDocumentoPago.makeInstance("DocumentoRecargaClaro");
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }
                
                docR.getDatos().setValue("RutRec", codigo );
                docR.getDatos().setValue("Tipo", "DocumentoRecargaClaro" );
                docR.getDatos().setValue("SistemaOrigenClaro", trv);
                
                
               // docR.getDatos().setValue("TipoDocumentoClaro", "REC");
                docR.getDatos().setValue("FolioDocumentoClaro", codigo.substring(0, codigo.length()-2));
               // docR.getDatos().setValue("FechaVencimientoClaro", Tools.getFecha().substring(0,10));
               // docR.getDatos().setValue("SaldoAdeudadoClaro", monto);
                
               // docR.getDatos().setValue("CodAutorizacion", resp.getCodigoAutorizacion());
               // docR.getDatos().setValue("Monto", mPago.getMonto());
                
               // Se debe agregar el Monto a cancelar al Documento generado !!
                docR.getDatos().setValue("Monto", monto);
                
                // Se agrega al carro de compras, el Documento de Recarga !!
                vista.hideAllEntries();
                
                try {
                    vista.getOperTRV().addDocumentoPago(docR);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar documento de pago al carro");
                }
                
//                docR.getDatos().show("Cuenta Claro");
//                
//                vista.getOperTRV().getCarroCompras();
                
                // TODO cbriones: se setean los botones para opcion 2 !!
                //vista.paintButtons(Tools.getBotones(1));
                vista.paintButtons(Tools.getBotones(2));
                
                // Se agrega al carro de Medios de Pago, el Medio de Pago efectivo
//                vista.getOperTRV().getCarroMediosPago().addMedioPago(mPago);
                
                /**
                 * 
                String []botones = new String[12];
                for(int i = 0; i < botones.length; i++){
                    botones[i] = "";
                }
                vista.paintButtons(botones);
                */
                
                return ICajaView._NOWAITFORACTION;  
                
            case 5:
                estado = 6;
                vista.hideAllEntries();
                /**
                 * Esto no creo que sea necesario !!
                vista.getOperTRV().setVentaDirecta("true");
                vista.getOperTRV().setRecargaMovistar("true");
                */
                return ICajaView._NOWAITFORACTION;        
                
            case 6:
            	// Se agrega confirmacion para generar Notificacion de Pago...
//            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
//            		
//            		vista.showBusyWindow("Enviando", "Espere por favor..."); 
//                    vista.getOperTRV().setVentaDirecta("true");
//                    vista.getOperTRV().setRecarga( true );
//                    vista.getOperTRV().setRecargaFija(true);
//                    vista.getOperTRV().setRecargaMovil(false);
//                    
//                    // se debe invocar a Chargin System una vez realizada la Notificacio OK..
//                    // Esto se realiza dentro de la Operacion...
//                    vista.getOperTRV().confirmar(vista);
//                    vista.hideBusyWindow();
//                    
//            	}
//            	vista.removeTRV();
//                return 13;       
            	vista.getOperTRV().setRecarga( true );
           	 	vista.getOperTRV().setRecargaMovil(false);
                vista.getOperTRV().setRecargaFija(true);
                return 20;

        }
        return 0;
    }

}
