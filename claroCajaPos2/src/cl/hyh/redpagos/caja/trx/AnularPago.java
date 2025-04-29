package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.URLString;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarOperacionInDTO;
import ws.claro.cl.ConsultarOperacionOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.MedioPagoDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.proxy.AppControlNotificarProxy;
import ws.claro.cl.proxy.AppControlProxy;


/**
 * Se agrega logica para incluir el nombre del archivo o copia de los comprobantes
 * para la impresión en PDF
 * 
 * @author aligare
 *
 */
public class AnularPago implements ITrxBase, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static StringBuilder sb;
    int estado = 0;
    Datos data = null;
    DefServicio def = null;
    OperTRV operTRV = null;
    String respLeer;
    String tipoTarjeta;
    int montoPago=0;
    int numBoleta = 0;
    String ultdigTrj;
    String numUnicoTbk;
    String respuesta;
    boolean esTbk;
    String codTbkReturn;
    String msgTbkReturn;
    ArrayList<LineaVoucher> comprobante;
    LineaVoucher[] voucherComercioDuplicado = null;
   
    //NumeroOperacionOut operOut = null;
    Map<String,String> motivoMap=new HashMap<String, String>();
    String[] motivos;
    
    NumeroOperacionOutDTO operOut = null;
    ConsultarOperacionOutDTO opOut = null;

    public void init(Datos data){
        
    }
    public int execute(ICajaView vista, int key, Datos htParam){
        if( key == 0 ) {            
            estado = 0;
            //vista.setInputTimeout(15);
            
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }else if( key == KeyEvent.VK_F7){
            estado = 7;
        }else if( key == KeyEvent.VK_F8){
            estado = 6;
        } 
        else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9){
            ParamSet pList = Base.getParamSet( "posDat" );
            pList.setValue( "FechaPago", Tools.getFecha() );
            return ICajaView._PASSTHROUGH;
        } 
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        switch( estado ) {
            case 0:
                estado = 1;
                def = Base.getDefServicio("ConsultaJournalOperacion");
//                Servicio consultaEdicion = null; 
                data = new Datos(def.getInputRecordDef());
                //int resp = 0;
                estado = 2;
                return ICajaView._NOWAITFORACTION;
            case 2:
                estado = 3;
                vista.hideAllEntries();
                vista.setEntryMessage( "Ingrese N°Operación", true );
                vista.setEntryTextLabel("Ingrese N°Operación:", true);
                vista.setEntryText("", true, false, false,null,null);
                
                return ICajaView._WAITFORACTION; // Esta es la que corresponde !!!
                //return ICajaView._NOWAITFORACTION;
                
            case 3:
            	
                // se comenta para solo mostrar ventana de advertencia !!!
                if(vista.getEntryText().equals("")){
                    estado = 3;
                    vista.hideAllEntries();
                    vista.setEntryMessage( "Ingrese N°Operación", true );
                    vista.setEntryTextLabel("Ingrese N°Operación:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                this.data.setValue("NumeroOperacion", vista.getEntryText());
                estado = 4;
                return ICajaView._NOWAITFORACTION;
                
            case 4:
            	// TODO Implementacion de busqueda para Claro !!
            	
            	//ServerProxy pr = Proxy.getProxyInstance();
            	AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
            	
            	ConsultarOperacionInDTO opIn = new ConsultarOperacionInDTO();
            	opOut = null;
            	
            	ParamSet pSet = Base.getParamSet("posDat");
//            	ParamSet posCfg = Base.getParamSet("posCfg");
//            	ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
        		
            	Base.logger.info( "Realizando Busqueda de Operacion anterior...");
            	opIn.setAgencia((pSet.getStringValue("Agencia")));
            	Base.logger.info( "Agencia: " +  opIn.getAgencia());
            	opIn.setCajaFisica((pSet.getStringValue("Caja")));
        		Base.logger.info( "Caja Fisica: " +  opIn.getCajaFisica());
        		opIn.setEntidad((pSet.getStringValue("Entidad")));
        		Base.logger.info( "Entidad: " +  opIn.getEntidad());
        		opIn.setCajero((pSet.getStringValue("Cajero")));
        		Base.logger.info( "Cajero: " +  opIn.getCajero());
        		opIn.setSession((pSet.getStringValue("SessionId")));
        		Base.logger.info( "SessionId: " +  opIn.getSession());
        		opIn.setUsuario(pSet.getStringValue("Usuario"));
        		//opIn.setUsuario(pSet.getStringValue("CodigoRecaudador"));
        		Base.logger.info( "Usuario: " +  opIn.getUsuario());
        		opIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
        		Base.logger.info( "Recaudador: " +  opIn.getRecaudador());
        		
        		opIn.setNumeroOperacion(this.data.getLongValue("NumeroOperacion"));
        		Base.logger.info( "Nro Operacion: " +  opIn.getNumeroOperacion());
        		
        		vista.showBusyWindow("Consultando", "Espere por favor...");
        		//ConsultarOperacionOutDTO resp = null;
        		try {
        			opOut = pr.consultarOperacion(opIn);
        			Base.logger.info( "Cod. Resp. Anular Pago: " + opOut.getRetCode());
        			Base.logger.info( "Desc. Resp. Anular Pago: " + opOut.getRetDesc());
        		} catch (RemoteException e) {
        			vista.hideBusyWindow();
        			Base.logger.info( "Codigo de Error Anular Pago: " + opOut.getRetCode());
        			Base.logger.info( "Mensaje de Error Anular Pago: " + opOut.getRetDesc());
                    //JOptionPane.showMessageDialog(null, resp.getHeaderOut().getRcMessage(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, opOut.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			Tools.logStackTrace(Base.logger, e);
        			return 13;
        		}
        		
        		vista.hideBusyWindow();
        		
        		if(!"0".equalsIgnoreCase(opOut.getRetCode())){
        			//JOptionPane.showMessageDialog(null, "[rc]:" + opOut.getRetCode() + " [msg]:" + opOut.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			JOptionPane.showMessageDialog(null, opOut.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			return 13;
        		}
        		
        		// Validar si el documento a armar es anulable !!!
        		if("N".equalsIgnoreCase(opOut.getOperacion().getAnulable())){
        			JOptionPane.showMessageDialog(null, "Operación no Autorizada para Anular, solicite autorización a soporte de caja", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			return 13;
        		}
        			
        		MedioPagoDTO[] mediosPagoArry = opOut.getOperacion().getMediosdepago();
        		boolean isTbkn = false;
                for (int i = 0; i < mediosPagoArry.length; i++) {
					MedioPagoDTO medioPagoDTO = mediosPagoArry[i];
					
					if (medioPagoDTO.getTipoTransaccion().equalsIgnoreCase("mpCredito") 
							|| medioPagoDTO.getTipoTransaccion().equalsIgnoreCase("mpMultitienda")) {
						isTbkn = true;
					}
                }

        		if(isTbkn && ("N".equalsIgnoreCase(opOut.getOperacion().getAnulableTbk()))){
        			JOptionPane.showMessageDialog(null, "Operación Con medio de pago Tarjeta TBK no Autorizada, solicite autorización a soporte de caja", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			return 13;
        		}
        		
        		
        		//validar que el pago a anular, sea un pago realizado hoy
        		System.out.println(" fecha en que el sistema registró el pago : "
        				+ opOut.getOperacion().getFechaPago());
        		System.out.println(" fecha en que el cajero envió el pago : "
        				+ opOut.getOperacion().getFechaOperacion());  		
        		if(!(new SimpleDateFormat("yyyyMMdd").format(new Date()).toString())
        				.equalsIgnoreCase(opOut.getOperacion().getFechaPago())){
        			JOptionPane.showMessageDialog(null, "Operación no corresponde al día de hoy", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			return 13;
        		} 
        		
                try{
                	operTRV = Tools.armarClaro(opOut);
                	//operTRV = Tools.armarVTR(opOut);
                    //operTRV = (OperTRV)((ServicioJournalDetalle)consultaEdicion).getOper(); 
                }catch( Exception e ){
                    Base.logger.info(e.getMessage());
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }
                
                String fecha = opOut.getOperacion().getFechaOperacion();
                String hora = opOut.getOperacion().getHoraOperacion(); 
                String operacion = "";
                if(operTRV.isRemesa()){
                	operacion = "EnvioRemesa";
                }
                else{
                	operacion = "EnvioTrv";
                }
                operTRV.setNumeroOperacion(this.data.getLongValue("NumeroOperacion"));
                operTRV.setCaja(opOut.getOperacion().getCaja());
                operTRV.setCajero(opOut.getOperacion().getUsuario());
                operTRV.setFecha(fecha);
                operTRV.setHora(hora);
                operTRV.setRegular(true);
                operTRV.setOperacion(operacion);                
               
                estado = 5;
                esTbk = false;
                MedioPagoDTO[]pagosClaroTbk = opOut.getOperacion().getMediosdepago();
                          
                for (int i = 0; i < pagosClaroTbk.length; i++) {
					MedioPagoDTO medioPagoDTO = pagosClaroTbk[i];
					
					if (medioPagoDTO.getTipoTransaccion().equalsIgnoreCase("mpCredito") || medioPagoDTO.getTipoTransaccion().equalsIgnoreCase("mpMultitienda")) {
						if (medioPagoDTO.getTipoTransaccion().equalsIgnoreCase("mpCredito")){
							tipoTarjeta = "CR";
						}
						if (medioPagoDTO.getTipoTransaccion().equalsIgnoreCase("mpMultitienda")){
							tipoTarjeta = "NB";
						}
						montoPago = Integer.parseInt(medioPagoDTO.getMontoPagado());
						numBoleta = (int) this.data.getLongValue("NumeroOperacion");
						numUnicoTbk = medioPagoDTO.getIdTrxTbk();// "00700012201410300108390000"; // Obtener el numUnico desde el servicio
						estado = 5;
						esTbk=true;
					} 
					
				}
                
                
                
                return ICajaView._NOWAITFORACTION;
                
            case 5:
            	vista.hideBusyWindow();
                vista.hideAllEntries();
                vista.setEntryTextLabel("Detalle:", true);
                if(operTRV.isRemesa()){
                	vista.setEntryTextArea(operTRV.imprimirJournalPantallaRemesa(), true);

                }
                else{
	                vista.setEntryTextArea(operTRV.imprimirJournalPantalla(), true);
	                
                }
                
              //Si es ONE se debe seleccionar motivo de anulacion
                if(opOut.getOperacion().getTransacciones(0).getOrigen().equals("ONE")){
                	estado = 6;
                }else{
                	estado = 7;
                }                
                return ICajaView._WAITFORACTION;
            case 6:
            	estado = 7;
            	vista.hideAllEntries();
                vista.setEntryTextLabel("Motivo de Anulación:", true);
                
            	ParamSet motivoList = Base.getParamSet( "posDat" );
            	String motivosStr=motivoList.getStringValue("motivoanulaciones");
                motivos=motivosStr.split("\\|");
                String[] fmotivos=new String[(motivos.length/2)];
                int k=1;
                for(int i=0;i<fmotivos.length;i++){
                	fmotivos[i]=motivos[k+1];
                	motivoMap.put(String.valueOf(i), String.valueOf(k+1));
                	k+=2;
                }
                vista.setEntryList(fmotivos, true, false);
   
                return ICajaView._WAITFORACTION;
            case 7:
 
                if( vista.showMyConfirmDialog("Confirme Anulación", "Desea anular?") == JOptionPane.YES_OPTION ) {
                	
                try {
                    if(operTRV != null){
                    	ParamSet pList = Base.getParamSet( "posDat" );
                        
                    	
                    	 pr = AppControlProxy.getProxyInstance(); 
                         
                         HeaderDTO hIn = new HeaderDTO();
                         
                     	hIn.setAgencia(pList.getStringValue("Agencia"));
                 		hIn.setCajaFisica(pList.getStringValue("Caja"));
                 		hIn.setEntidad(pList.getStringValue("Entidad"));
                 		hIn.setCajero(pList.getStringValue("Cajero"));
                 		hIn.setSession(pList.getStringValue("SessionId"));
                 		hIn.setUsuario(pList.getStringValue("Usuario"));
                 		hIn.setRecaudador(pList.getStringValue("CodigoRecaudador"));
                 		
                         try {
                         	operOut= pr.numeroOperacion(hIn);
                 		} catch (RemoteException e2) {
                 			Tools.logStackTrace(Base.logger, e2);
               			    return 13;
                 		}  
                 		   
                         if(!("0").equalsIgnoreCase(operOut.getRetCode()) || operOut.getNumeroOperacion() < 0){  
                        	 JOptionPane.showMessageDialog(null, "No se pudo realizar la Anulación", "Info", JOptionPane.INFORMATION_MESSAGE);
                             return 13;
                         }
                         
                         Caja caja = new Caja();
                         Operacion opInAn = new Operacion();

                     	caja.setAgencia(pList.getStringValue("Agencia"));
                     	Base.logger.info( "Agencia: " + caja.getAgencia());    
                 		caja.setIdCaja(Integer.parseInt(pList.getStringValue("Caja")));
                 		Base.logger.info( "Caja: " + caja.getIdCaja());    
                 		caja.setEntidad(pList.getStringValue("Entidad"));
                 		Base.logger.info( "Entidad: " + caja.getEntidad());   
                 		caja.setCodigoSesion(Long.parseLong(pList.getStringValue("SessionId")));
                 		Base.logger.info( "Session: " + caja.getCodigoSesion());
                 		caja.setUsuario(pList.getStringValue("Cajero"));
                 		Base.logger.info( "Usuario: " + caja.getUsuario()); 
                 		caja.setRecaudador(pList.getStringValue("CodigoRecaudador"));
                 		Base.logger.info( "Recaudador: " + caja.getRecaudador()); 
                 		caja.setCanal(new Long(pList.getStringValue("Canal")).intValue());
                 		Base.logger.info( "Canal: " + caja.getCanal()); 
                 		
                 		opInAn.setCaja(caja);
                 		opInAn.setFechaPago(Tools.getFecha());
                 		opInAn.setMonto(operTRV.getCarroCompras().getMontoTotal());
                 		opInAn.setNumeroOperacion(operOut.getNumeroOperacion());
                 		opInAn.setTipoOperacion(2);
                		
                		Transaccion []trxsClaro = new Transaccion[1];
                		
                		trxsClaro[0] = new Transaccion();
                		Tools.initTrxClaro(trxsClaro[0]);
                		
                		if(operTRV.isRemesa()){
                			trxsClaro[0].setTipoTransaccion("AnulacionRemesa");
                		}
                		else{
                			trxsClaro[0].setTipoTransaccion("Anulacion");
                		}  
                		//Enviando motivo en caso de ser ONE
                		if(opOut.getOperacion().getTransacciones(0).getOrigen().equals("ONE")){
                			int index = vista.getEntryListIndex();
                			String posicion=motivoMap.get(String.valueOf(index));
                			trxsClaro[0].setMonto(Long.valueOf(motivos[Integer.parseInt(posicion)-1]));
                		}
                		
                		trxsClaro[0].setNroOperacionAReversar(Long.parseLong(this.data.getStringValue("NumeroOperacion")));
                		
                		opInAn.setTransaccion(trxsClaro);
                		
                		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago []pagosClaro = new cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[0];
                		opInAn.setMedioPago(pagosClaro);
                        
                		// TODO validar la impelemntacion para Claro de esta funcionalidad !!!
                		// Se debe generar la Notificacion de Anulacion Inmediatamente, sin pasar por  SAF !!
                		NotificacionEnvio notif = new NotificacionEnvio();
                		notif.setOperacion(opInAn);
                		
                		NotificacionRespuesta resp = null;
                		PlataformaPagoNotificarServerProxy prNot = AppControlNotificarProxy.getProxyInstance();
                		
        				ParamSet posDat = Base.getParamSet("posDat");
    					
                		
                		try {
                			vista.showBusyWindow("Anulando", "Espere por favor...");
        					
//                			[REspinoza]
                			if(esTbk){
                				Base.logger.info("P I N P A D - inicializa");
	                				Base.tbk.inicializa(posDat.getStringValue("TransbankConfig"));
	            					// Instanciamos la clase TbkMetodos
	            					TbkMetodos metodos = new TbkMetodos();
            					
	                				Base.logger.info("Anulando TBK");
		                			String ingMoneda="CL";
									String ingTipoTrx="TBKDEV";
			                		
	                				vista.hideBusyWindow();
	                				vista.showBusyWindow("Operando Pinpad", "Espere por favor...");
	                				
			                		respuesta = devolucion(numUnicoTbk.substring(8, 16), numUnicoTbk.substring(16, 22), 
			                				String.valueOf(montoPago),  ingMoneda, "0", "0", ultdigTrj, numUnicoTbk.substring(0, 4), 
			                				numUnicoTbk.substring(4, 8), ingTipoTrx, tipoTarjeta, metodos);
			                		
//			                		vista.hideBusyWindow();
//		                			vista.showBusyWindow("Anulando", "Espere por favor...");
			                		
			                		if(respuesta!=null){
			                			
			                			URLString urlVC = new URLString(respuesta);
			                			
			                			//voucher temporal super anti-cortes de luz 
			                			generaDatosAnulacionTbk(vista, numUnicoTbk, tipoTarjeta, urlVC, "boletaTbkTemp.dat");
			                			
					                    //Obtener código/mensaje retornado por Transbank
				                		codTbkReturn=urlVC.getValor("AUTRET")==null?codTbkReturn=urlVC.getValor("TXCRET"):urlVC.getValor("AUTRET");
//				  		              	msgTbkReturn= urlVC.getValor("TXCGLO")==null?"":urlVC.getValor("TXCGLO");
				                		msgTbkReturn=Format.getMesageTbk(urlVC);
				  		              	msgTbkReturn="RetTbk [" + codTbkReturn + "]." + Format.formatMsjTbk(msgTbkReturn);
			                		
			                			if (respuesta.contains("TXCRET")&& respuesta.contains("AUTRET")){
					                		if(urlVC.getValor("TXCRET").equals("00") && Integer.parseInt(urlVC.getValor("AUTRET")) <= 9){
					                			
//					                			//voucher temporal super anti-cortes de luz 
//					                			generaDatosAnulacionTbk(vista, numUnicoTbk, tipoTarjeta, urlVC, "boletaTbkTemp.dat");
					                			
					                			Base.logger.info("Anulacion TBK Exitosa, Anulando PDP");
						                		vista.hideBusyWindow();
					                			vista.showBusyWindow("Anulando", "Espere por favor...");
					                			resp = prNot.notificar(notif);
					                			
					                			//para evitar inconsistencias se confirma a tbk inmediatamente
					                			Base.logger.info("P I N P A D - confirma");
					                			metodos.confirmarOperacion(Base.tbk);
					                			
					                			generaDatosAnulacionTbk(vista, numUnicoTbk, tipoTarjeta, urlVC, "boletaTbk.dat");
					                			Voucher.borraBoleta("boletaTbkTemp.dat");
					                			
					                			Base.logger.info("Codigo Notificacion Anulacion: "+resp.getRespuesta().getRetCode());
					                			Base.logger.info("Msje Notificacion Anulacion: "+resp.getRespuesta().getRetDesc());
					                		 }else{
					                			 Base.logger.info("RetTbk [" + codTbkReturn + "].  " + msgTbkReturn);					                             
					                			 JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
					 		                    		"Error", JOptionPane.INFORMATION_MESSAGE);
					                			 
					                			 return 13;
					                		 }
				                		}else{
				                			 vista.hideBusyWindow();
				                			 
				                			 Voucher.borraBoleta("boletaTbkTemp.dat");
				                			 
						                     if(msgTbkReturn.trim().equalsIgnoreCase("")){
						                    	 msgTbkReturn = "Error Anulacion Tbk, Reintentar...";
						                     }
						                     
				                			 JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
					 		                    		"Error", JOptionPane.INFORMATION_MESSAGE);
						                     
						                     return 13;
				                		}
	                			}
			                		
			                	// TODO Por que borrar boletas y anular solo PDP aqui ¿? !! 
//			                		else{
//	                				Voucher.borraBoleta("boletaTbk.dat");
//	                				Voucher.borraBoleta("boletaTbkTemp.dat");
//	                				
//	                				Base.logger.info("Anulando solo PDP");
//			                		vista.hideBusyWindow();
//		                			vista.showBusyWindow("Anulando", "Espere por favor...");
//	                				resp = prNot.notificar(notif);
//		                			Base.logger.info("Codigo Notificacion Anulacion: "+resp.getRespuesta().getRetCode());
//		                			Base.logger.info("Msje Notificacion Anulacion: "+resp.getRespuesta().getRetDesc());
//	                				
//	                			}       			
                			}else{                				
                				Base.logger.info("Anulando solo PDP");
                				resp = prNot.notificar(notif);
	                			Base.logger.info("Codigo Notificacion Anulacion: "+resp.getRespuesta().getRetCode());
	                			Base.logger.info("Msje Notificacion Anulacion: "+resp.getRespuesta().getRetDesc());
                			}
                		} catch (RemoteException e) {
                			//borrar duplicados guardados
            				Voucher.borraBoleta("boletaTbkTemp.dat");
            				
                			Tools.logStackTrace(Base.logger, e);
                			JOptionPane.showMessageDialog(null, "Error en la ejecucion de Notificación para Anulación", "Info", JOptionPane.INFORMATION_MESSAGE);
                            return 13;
                		} finally {
                			//si el pago tbk queda ok, se borra el temporal
                			Voucher.borraBoleta("boletaTbkTemp.dat");
                			vista.hideBusyWindow();
                		}
                		
                		if (resp!= null){
                		
                		if(resp.getRespuesta().getRetCode() != 0){
//                			[Se quita según Multicard]
//                			if(esTbk){
//	                			String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
//	                			metodos.cierre(Base.tbk,requerimiento);
//	                			metodos.confirmarOperacion(Base.tbk);
//                			}
                			Base.logger.error("Codigo Notificacion Anulacion Erroneo: "+resp.getRespuesta().getRetCode());
                			Base.logger.info("Msje Notificacion Anulacion Erroneo: "+resp.getRespuesta().getRetDesc());
                			if(resp.getRespuesta().getRetCode() == -44) {
                				JOptionPane.showMessageDialog(null, "No se pudo realizar la Notificación para Anulación:\n" + resp.getRespuesta().getRetDesc(), "Info", JOptionPane.INFORMATION_MESSAGE);
                			} else {
                				JOptionPane.showMessageDialog(null, "No se pudo realizar la Notificación para Anulación", "Info", JOptionPane.INFORMATION_MESSAGE);
                			}
                			//borrar duplicados guardados
            				Voucher.borraBoleta("boletaTbk.dat");
                			
                            return 13;
                		}
                		
                		// Se comenta esta operacion para evitar error  !!
                        //Serializa.serializa(opInAn, "SAF");  
                        
                		if(!esTbk){
                			imprimirBoletaAnulacion(vista);
                			JOptionPane.showMessageDialog(null, "Operación Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                		}else{
                			imprimirBoletaAnulacion(vista);
//                			metodos.confirmarOperacion(Base.tbk);
	                		URLString urlVC = new URLString(respuesta);
	                			                		
	                		 String[]voucherDev = null;
	                         LineaVoucher vL = null;
	                		 if (urlVC.getValor("TXCRET") != null) {
	                			 
	                			 //Obtener código retornado por Transbank
			                	 codTbkReturn=urlVC.getValor("AUTRET")==null?codTbkReturn=urlVC.getValor("TXCRET"):urlVC.getValor("AUTRET");
//					             msgTbkReturn= urlVC.getValor("TXCGLO")==null?"":urlVC.getValor("TXCGLO");
			                	 msgTbkReturn= Format.getMesageTbk(urlVC);
					             msgTbkReturn="RetTbk [" + codTbkReturn + "]. " + Format.formatMsjTbk(msgTbkReturn);
		                		
	                			 if(urlVC.getValor("TXCRET").equals("00") 
	                					 || Integer.parseInt(urlVC.getValor("TXCRET")) <= 9){
	                				 
	                				 String vcli = urlVC.getValor("VOUCHER");
	                				 if (vcli.contains("%20%20"))
	                				 {
	                				   vcli = URLDecoder.decode(vcli);
	                				 }
	                				 voucherDev = vcli.split("\\n");
	                	        		
	                        		 voucherComercioDuplicado = new LineaVoucher[voucherDev.length];
	                                 for(int i = 0 ; i < voucherDev.length ; i++){
	                                     vL = new LineaVoucher();
	                                     vL.setLinea(voucherDev[i].replaceAll("\\r", ""));
	                                     vL.setSmall(true);
	                                     voucherComercioDuplicado[i] = vL;
	                                     Base.logger.info("Linea Voucher Copia Comercio: ["+vL.getLinea()+"]");
	                                 }
	                                 
	                                 /**
	                                  * [REspinoza] guardar datos anualcion para reimpresion directa tbk
	                                  */
//	                                 generaDatosAnulacionTbk(vista, numUnicoTbk, tipoTarjeta, urlVC);
	                                 
	                                 generaComprobante(voucherComercioDuplicado);
	                                 
	                                 ParamSet pSet2 = Base.getParamSet("posDat");

	                         		DatosFileNet datosFileNet = new DatosFileNet();
	                         		datosFileNet.setCodigo_sesion(pSet2.getStringValue("SessionId"));
	                         		datosFileNet.setCodusuario_envia(pSet2.getStringValue("CodigoRecaudador"));
	                         		datosFileNet.setEmail_para(vista.getEntryText());
	                         		datosFileNet.setNumOperacion(String.valueOf(operOut.getNumeroOperacion()));
	                         		datosFileNet.setPropietario(pSet2.getStringValue("Usuario"));
	                         		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_ANULACION);
	                                 
	                                 //Primera Copia
	                                Voucher.printVoucher(comprobante, false, Voucher.COPIA_CLIENTE, datosFileNet);
	                                 //Segunda Copia
	                                Voucher.printVoucher(comprobante, false, Voucher.COPIA_LOCAL, datosFileNet);
	                		
	                			 }else{
	                     			vista.hideBusyWindow();
	              		            Base.logger.error("RetTbk [" + codTbkReturn + "]. " + msgTbkReturn);	              		            
	              		            JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
	  		                    		"Error", JOptionPane.INFORMATION_MESSAGE);
	              		            
	              		            
	              		            return 13;
	                     		 }
	                	 JOptionPane.showMessageDialog(null, "Operación de Anulación Transbank  Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	             		 }else{
	             			vista.hideBusyWindow();
	      		            Base.logger.error("No se pudo Imprimir el Voucher");
	      		            JOptionPane.showMessageDialog(null, "Error al tratar de imprimir el Voucher", "Error", JOptionPane.INFORMATION_MESSAGE);
	      		            return 13;
	             		 }
	                		
                		}
                        // TODO Se limpian los carros para Documentos y Medios de Pago 
                        operTRV.getCarroMediosPago().getMediosPago().clear();
                        operTRV.getCarroCompras().getDocumentos().clear();
                        return 13;
                    }
                    }else{

           			 JOptionPane.showMessageDialog(null, "Error en la ejecucion de Anulación", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                
                //try catch general para que no se caiga la aplicacion
                } catch (Exception e) {
                	JOptionPane.showMessageDialog(null, "Error en la ejecucion de Anulación", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                   
                }
                return 13;
                
            case 8:
                if( vista.showMyConfirmDialog("Confirme Edición", "Desea continuar editando los documentos cargados?") == JOptionPane.YES_OPTION ) {
                    if(operTRV != null){
                        vista.createTRV();
                        //Base.setOperTRV( operTRV );
                        vista.getOperTRV().setNumeroOperacion(operTRV.getNumeroOperacion());
                        vista.getOperTRV().setCaja(operTRV.getCaja());
                        vista.getOperTRV().setCajero(operTRV.getCajero());
                        vista.getOperTRV().setFechaPago(operTRV.getFechaPago());
                        ParamSet pList = Base.getParamSet( "posDat" );
                        pList.setValue( "FechaPago", operTRV.getFechaPago() );
                        for(int j = 0; j < operTRV.getCarroCompras().getDocumentos().size(); j++){
                            try {
                                vista.getOperTRV().addDocumentoPago(operTRV.getCarroCompras().getDocument(j));
                            } catch (BaseException e) {
                                Tools.logStackTrace(Base.logger, e);
                            }
                        }
                        for(int j = 0; j < operTRV.getCarroMediosPago().getMediosPago().size(); j++){
                            try {
                                vista.getOperTRV().addMedioPago(operTRV.getCarroMediosPago().getPago(j));
                            } catch (BaseException e) {
                                Tools.logStackTrace(Base.logger, e);
                            }
                        }
                        Base.setEdicion(true);
                        return 15;
                    }
                }
                return 13;     
                
        }
        return 0;
    }
    
    public LineaVoucher getLinea(String mensaje, boolean bold,boolean underline,boolean right,boolean tri){
        LineaVoucher oper = new LineaVoucher();
        oper.setBold(bold);
        oper.setUnderline(underline);
        oper.setRight(right);
        oper.setCenter(tri);
        oper.setLinea(mensaje);
        
        return oper;
    }
    
    public void imprimirBoletaAnulacion(ICajaView vista){
        LineaVoucher []voucher = null;
        
        data.setValue("Monto", this.data.getLongValue("Monto"));      
        data.setValue("TipoTotal", this.data.getStringValue("TipoTotal"));
        
        ArrayList<LineaVoucher> header = Voucher.armarVoucher(this.data, Base.getDefVoucher("VoucherBoletaHeader"));   
        
        ArrayList<LineaVoucher> body = new ArrayList<LineaVoucher>();
        String s = String.format("%-20s: %-8s", "Operación Anulada", this.data.getStringValue("NumeroOperacion"));
        body.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-20s: %-8s", "Operación Anulación", operOut.getNumeroOperacion());
        body.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-20s: %-8s", "Monto", Format.formatMonto(new Long(opOut.getOperacion().getMonto())));
        body.add(this.getLinea(s, false,false,false,false));
        
        //msepulveda: bloque en caso de ajuste de sencillo
        if(opOut.getOperacion().getMediosdepago() != null && opOut.getOperacion().getMediosdepago().length > 0){
        	for (MedioPagoDTO mp : opOut.getOperacion().getMediosdepago()) {
				if(mp.getTipoTransaccion().equals("mpAjusteSencillo")){
					s = String.format("%-20s: %-8s", "Monto Ajustado", Format.formatMontoAjustado(new Long(mp.getMontoPagado())));
			        body.add(this.getLinea(s, false,false,false,false));
				}
			}
        }
        
        
        
        body.add(this.getLinea("",false,false,false,false));
        
        ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));

        
        voucher = new LineaVoucher[body.size() + header.size() + footerDatos.size() + 1 + 1];
        int pos = 0;
        //Agregamos el header
        int l = 0 ;
        for(pos = 0; pos < header.size(); pos++){
            voucher[pos] = header.get(l);
            l++;
        }
        l = 0;
        for(l = 0; l < body.size(); l++){
            voucher[pos] = body.get(l);
            pos++;
        }
               
        LineaVoucher oper = new LineaVoucher();
        ParamSet pList = Base.getParamSet( "posDat" ); 
        oper.setLinea("Operador:" + pList.getStringValue( "Cajero" ));
        voucher[pos] = oper;
        pos++;
        l = 0;
        for(l = 0; l < footerDatos.size(); l++){
            voucher[pos] = footerDatos.get(l);
            pos++;
        }
        //Agregamos la hora y la fecha
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());
        oper = new LineaVoucher();
        oper.setLinea("Fecha: " + df.format(Calendar.getInstance().getTime())+"    Hora:" + t.toString());
        voucher[pos] = oper;
        pos++;        
        
        ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
        for(int i = 0; i < voucher.length ; i++){
            boleta.add(voucher[i]);
        }
        
        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
        
        ParamSet pSet = Base.getParamSet("posDat");

		DatosFileNet datosFileNet = new DatosFileNet();
		datosFileNet.setCodigo_sesion(pSet.getStringValue("SessionId"));
		datosFileNet.setCodusuario_envia(pSet.getStringValue("CodigoRecaudador"));
		datosFileNet.setEmail_para(vista.getEntryText()); 
		datosFileNet.setNumOperacion(String.valueOf(operOut.getNumeroOperacion()));
		datosFileNet.setPropietario(pSet.getStringValue("Usuario"));
		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_ANULACION);
		
        Voucher.printVoucher(boleta,true, Voucher.COPIA_LOCAL,datosFileNet); 
        vista.hideBusyWindow();
    }
	
	/**
	 * Metodo DEVOLUCION.
	 *  Todos los parametros hacen referencia a lo que aparece en el voucher de la venta realizada. 
	 * @param ingFecha		 Fecha de la transacción.
	 * @param ingNroOpe		 Numero de operación.
	 * @param ingMonto		 Monto de la venta.
	 * @param ingMoneda		 Tipo de moneda.
	 * @param ingVuelto		 Vuelto.
	 * @param ingPropina	 Propina.
	 * @param ing4UD		 Ultimos 4 digitos de la tarjeta.
	 * @param ingLocal		 Local.
	 * @param ingCaja		 Caja.
	 * @param ingTipoTrx	 Tipo de transaccion.
	 * @param ingTipoTarjeta Tipo de tarjeta.
	 * @param metodos 
	 * @return respuesta 	 Formato URL de la reimpresion.
	 */
	public static String devolucion(String ingFecha,String ingNroOpe,String ingMonto,String ingMoneda,
									   String ingVuelto,String ingPropina,String ing4UD,String ingLocal,
									   String ingCaja,String ingTipoTrx,String ingTipoTarjeta, TbkMetodos metodos){
		sb = new StringBuilder();
		sb.append("FECHA=" 	 + 	ingFecha 	+ "&");
		sb.append("NROOPE="  + 	ingNroOpe 	+ "&");
		sb.append("MONTO=" 	 + 	ingMonto 	+ "&");
		sb.append("MONEDA="  + 	ingMoneda 	+ "&");
		sb.append("VUELTO="  + 	ingVuelto 	+ "&");
		sb.append("PROPINA=" + 	ingPropina 	+ "&");
		sb.append("ULT4DIG=" + 	ing4UD 		+ "&");
		sb.append("LOCAL=" 	 + 	ingLocal 	+ "&");
		sb.append("CAJA=" 	 + 	ingCaja 	+ "&");
		sb.append("TIPTRX="  + 	ingTipoTrx 	+ "&");
		sb.append("TIPTAR="  + 	ingTipoTarjeta);
		
		String requerimiento = sb.toString();
		Base.logger.info("P I N P A D - devolucion");
		String respuesta =metodos.devolucion(Base.tbk, requerimiento);
//		metodos.confirmarOperacion(Base.tbk);
		
		return respuesta;
		
		
	}
	
    public void generaComprobante (LineaVoucher []aux){
    	
    	comprobante = new ArrayList<LineaVoucher>();
		for(int j = 0; j < aux.length; j++){
			comprobante.add(aux[j]);
		}
    	
	
    }
    
    // [REspinoza] voucher para la reimpresión
    private void generaDatosAnulacionTbk(ICajaView vista, String numUnicoTbk,
    		String tipoTarjeta, URLString urlVC, String nombreArchivo) {
       	ArrayList<LineaVoucher> respaldo = new ArrayList<LineaVoucher>();
       	LineaVoucher lineaTrx = new LineaVoucher();

		 String termId = urlVC.getValor("TERMID");
		 String numSec = urlVC.getValor("NUMSEC");
//		 String numSecDev = urlVC.getValor("NUMSEC");

       	try {	       	
	       	lineaTrx.setLinea("Anulacion" + "|"
	       			+ numUnicoTbk + "|"
	       			+ tipoTarjeta + "|"
	       			+ termId + "|"
	       			+ numSec + "|"
	       			+ termId);
	       	
	       	respaldo.add(lineaTrx);
	       	Voucher.escribeBoleta(respaldo,nombreArchivo);
       	} catch (Exception e) {
       		Base.logger.error("generaDatosPagoTbk() Ocurrio un error al registrar datos tarjeta " + e.getMessage());
		}
		
	}
 }
