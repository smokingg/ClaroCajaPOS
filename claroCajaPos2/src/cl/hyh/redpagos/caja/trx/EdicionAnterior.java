package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.CierreCajaOutDTO;
import ws.claro.cl.ConsultarOperacionInDTO;
import ws.claro.cl.ConsultarOperacionOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.proxy.AppControlNotificarProxy;
import ws.claro.cl.proxy.AppControlProxy;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.cajas.ws.impl.ConsultaDeudaRutVTRIn;
import cl.hyh.cajas.ws.impl.ConsultaOperacionIn;
import cl.hyh.cajas.ws.impl.ConsultaOperacionOut;
import cl.hyh.cajas.ws.impl.EnvioReversaIn;
import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.cajas.ws.impl.NumeroOperacionOut;
import cl.hyh.cajas.ws.impl.OperacionCaja;
import cl.hyh.cajas.ws.impl.OperacionIn;
import cl.hyh.cajas.ws.impl.Request;
import cl.hyh.cajas.ws.impl.Response;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Serializa;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.ServicioJournalDetalle;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class EdicionAnterior implements ITrxBase, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    int estado = 0;
    Datos data = null;
    DefServicio def = null;
    OperTRV operTRV = null;
    //NumeroOperacionOut operOut = null;
    
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
                Servicio consultaEdicion = null; 
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
            	ParamSet posCfg = Base.getParamSet("posCfg");
            	ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
        		
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
        		// TODO: se setea para pruebas session 289 !!
        		//opIn.setSession("289");
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
        			Base.logger.info( "Cod. Resp. Edicion Pago: " + opOut.getRetCode());
        			Base.logger.info( "Desc. Resp. Edicion Pago: " + opOut.getRetDesc());
        		} catch (RemoteException e) {
        			vista.hideBusyWindow();
        			Base.logger.info( "Codigo de Error Edicion Pago: " + opOut.getRetCode());
        			Base.logger.info( "Mensaje de Error Edicion Pago: " + opOut.getRetDesc());
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
            	
        		/**
        		 * Ahora Solo es edicion, no validar aca ...
        		// Validar si el documento a armar es anulable !!!
        		if("N".equalsIgnoreCase(opOut.getOperacion().getAnulable())){
        			JOptionPane.showMessageDialog(null, "El Documento no es Anulable, debe verificar con un Supervisor", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        			return 13;
        		}
        		*/
        		
          		//validar que el pago a editar, sea un pago realizado hoy
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
                	Base.logger.info("Retorno Operacion para Edicion Anterior...");
                	//operTRV = Tools.armarVTR(opOut);
                    //operTRV = (OperTRV)((ServicioJournalDetalle)consultaEdicion).getOper(); 
                }catch( Exception e ){
                	Base.logger.error("Fallo al armar Operacion para Edicion Anterior...");
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
                return ICajaView._NOWAITFORACTION;
                
            case 5:
            	
            	ParamSet pSet2 = Base.getParamSet("posDat");
            	
                vista.hideAllEntries();
                vista.setEntryTextLabel("Detalle:", true);
                if(operTRV.isRemesa()){
                	vista.setEntryTextArea(operTRV.imprimirJournalPantallaRemesa(), true);
	                
                	/**
	                String []botones = new String[12];
	                botones[0] = "";
	                botones[1] = "";
	                botones[2] = "";
	                botones[3] = "";
	                botones[4] = "";
	                botones[5] = "";
	                botones[6] = "";
	                if("1".equalsIgnoreCase(pSet2.getStringValue("Perfil"))){
	                	botones[7] = "Anular";
	                }else{
	                	botones[7] = "";
	                }
	                botones[8] = "Volver";
	                botones[9] = "";
	                botones[10] = "";
	                botones[11] = "";
	                vista.paintButtons( botones );
	                */
                }
                else{
                	
                	Base.logger.info("Armando Voucher para Impresion Pantalla de Edicion..");
	                vista.setEntryTextArea(operTRV.imprimirJournalPantalla(), true);
	                
	                /**
	                String []botones = new String[12];
	                botones[0] = "";
	                botones[1] = "";
	                botones[2] = "";
	                botones[3] = "";
	                botones[4] = "";
	                botones[5] = "";
	               
	                if("1".equalsIgnoreCase(pSet2.getStringValue("Perfil"))){
	                	botones[6] = "Editar";
	                	botones[7] = "Anular";
	                	
	                }else{
	                	botones[6] = "";
	                	botones[7] = "";
	                }
	                botones[8] = "Volver";
	                botones[9] = "";
	                botones[10] = "";
	                botones[11] = "";
	                vista.paintButtons( botones );
	                */
                }
                
                //vista.hideEnter();
                estado = 7;
                return ICajaView._WAITFORACTION;
                
            case 6:
            	//TODO cbriones: esto no corre en este flujo, existe anulacion por si solo !!!!
                if( vista.showMyConfirmDialog("Confirme Anulación", "Desea anular?") == JOptionPane.YES_OPTION ) {
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
                 		//hIn.setUsuario(pList.getStringValue("CodigoRecaudador"));
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
                 		//caja.setUsuario(pList.getStringValue("CodigoRecaudador"));
                 		caja.setUsuario(pList.getStringValue("Cajero"));
                 		Base.logger.info( "Usuario: " + caja.getUsuario()); 
                 		caja.setRecaudador(pList.getStringValue("CodigoRecaudador"));
                 		Base.logger.info( "Recaudador: " + caja.getRecaudador()); 
                 		caja.setCanal(new Long(pList.getStringValue("Canal")).intValue());
                 		Base.logger.info( "Canal: " + caja.getCanal()); 
                 		
                 		opInAn.setCaja(caja);

                		//OperacionCaja operCaja = new OperacionCaja();
                 		opInAn.setFechaPago(Tools.getFecha());
                 		//opInAn.setHoraOperacion(Tools.getTime());
                 		opInAn.setMonto(operTRV.getCarroCompras().getMontoTotal());
                 		opInAn.setNumeroOperacion(operOut.getNumeroOperacion());
                 		opInAn.setTipoOperacion(2);
                 		//opInAn.setCodigoOperacion("2");
                		
                		//oIn.setOperacion(operCaja);
                		
                		//TransaccionCaja []trxs = new TransaccionCaja[1];
                		Transaccion []trxsClaro = new Transaccion[1];
                		
                		trxsClaro[0] = new Transaccion();
                		Tools.initTrxClaro(trxsClaro[0]);
                		
                		if(operTRV.isRemesa()){
                			trxsClaro[0].setTipoTransaccion("AnulacionRemesa");
                		}
                		else{
                			trxsClaro[0].setTipoTransaccion("Anulacion");
                		}  
                		if(opOut.getOperacion().getTransacciones(0).getOrigen().equalsIgnoreCase("ONE")){
                			trxsClaro[0].setMonto(Long.valueOf(1));
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
                		
                		try {
                			resp = prNot.notificar(notif);
                			Base.logger.info("Codigo Notificacion Anulacion: "+resp.getRespuesta().getRetCode());
                			Base.logger.info("Msje Notificacion Anulacion: "+resp.getRespuesta().getRetDesc());
                			
                		} catch (RemoteException e) {
                			Tools.logStackTrace(Base.logger, e);
                			 JOptionPane.showMessageDialog(null, "Error en la ejecucion de Notificación para Anulación", "Info", JOptionPane.INFORMATION_MESSAGE);
                             return 13;
                		}
                		
                		if(resp.getRespuesta().getRetCode() != 0){	
                			Base.logger.error("Codigo Notificacion Anulacion Erroneo: "+resp.getRespuesta().getRetCode());
                			Base.logger.info("Msje Notificacion Anulacion Erroneo: "+resp.getRespuesta().getRetDesc());
                			JOptionPane.showMessageDialog(null, "No se pudo realizar la Notificación para Anulación", "Info", JOptionPane.INFORMATION_MESSAGE);
                            return 13;
                		}
                		
                		// Se comenta esta operacion para evitar error  !!
                        //Serializa.serializa(opInAn, "SAF");  
                        
                        imprimirBoletaAnulacion(vista);
                        JOptionPane.showMessageDialog(null, "Operación Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        
                        // TODO Se limpian los carros para Documentos y Medios de Pago 
                        operTRV.getCarroMediosPago().getMediosPago().clear();
                        operTRV.getCarroCompras().getDocumentos().clear();
                        return 13;
                    }
                }
                return 13;
                
            case 7:
                if( vista.showMyConfirmDialog("Confirme Regularización", "Desea continuar regularizando los documentos cargados?") == JOptionPane.YES_OPTION ) {
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
		
        Voucher.printVoucher(boleta,true, Voucher.COPIA_CLIENTE,datosFileNet); 
        vista.hideBusyWindow();
    }
}
