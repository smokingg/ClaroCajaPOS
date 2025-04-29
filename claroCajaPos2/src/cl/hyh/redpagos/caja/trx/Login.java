package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ws.claro.cl.AperturaCajaInDTO;
import ws.claro.cl.AperturaCajaOutDTO;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.AutentificarInDTO;
import ws.claro.cl.AutentificarOutDTO;
import ws.claro.cl.ListaRecaudadoresOutDTO;
import ws.claro.cl.UltimaSesionRequestDTO;
import ws.claro.cl.UltimaSesionResponseDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.ThreadEnviosContingencia;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.trx.Browser;

/**
 * @author abertens
 *
 * password inválida
 * password vencida
 * usuario bloqueado
 * error autenticación
 */
public class Login implements ITrxBase {
	private int estado = 0;
	private String usuario;
	private String clave;
	private String recaudador;
	private int codRecaudador;
	private String fechaRecaudacion;
	private String perfil = "2"; // por default es un Cajero !!
	private int codCajero;
	// private String cajero;
	private int sessionId;
	// private AutentificaOut respAut;
	private AutentificarOutDTO respAut;
	// private AperturaCajaOut respAper;
	private AperturaCajaOutDTO respAper;

	ListaRecaudadoresOutDTO listaReca = null;

	private int canal;
	private int entidad;

	int index = 0;
	String[] recaudadores;

	public static Logger logger;

	public void init(Datos htParm) {}

	public int execute( ICajaView vista, int key, Datos htParam ) {
    	String msg = "";

    	if( key == 0 ) {
            vista.setInputTimeout(30);
        } else if( key == 1 ) {
            // TIMEOUT
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else if( key == 2 ) {
        	// VUELVO DE TRX ANIDADA, EN ESTE CASO, VALIDACION DE SUPERVISOR 
        } else {
            // OTRA TECLA. PROCESE CON LO QUE ESTE EN EL XML...
            return ICajaView._PASSTHROUGH;
        }
        
        while( true ) {
        	switch( estado ){
        		case 0:
        			vista.setEntryTextLabel("Ingrese usuario:", true);
                    vista.setEntryText("", true, false, false, null, null);
                    estado = 1;
                    return ICajaView._WAITFORACTION;
        		case 1:
        			usuario = vista.getEntryText();
                    vista.setEntryMessage("", false);
                    vista.setEntryTextLabel("Ingrese clave:", true);
                    vista.setEntryText("", true, false, true, null, null);
                    estado = 2;
                    return ICajaView._WAITFORACTION;
        		case 2:
                    clave = vista.getEntryText();
                    if(clave.equals("")){
    	                estado = 2;
    	                vista.setEntryMessage( "Clave Inválida - Ingrese nuevamente", true );
    	                vista.setEntryTextLabel("Ingrese clave:", true);
                        vista.setEntryText("", true, false, true, null, null);
    	                return ICajaView._WAITFORACTION;
    	            }
                    int rc = 0;
                    
                    vista.showBusyWindow("Validando", "Espere por favor...");
                    rc = validateUser();
                    vista.hideBusyWindow();
                    
                    if( rc == 0 ){
                    	if(perfil.equals("1")){ // Preguntamos si es Administrador ...
	                    	estado = 3;
	                    	//vista.setEntryTextLabel("Ingrese recaudador:", true);
	                    	Base.logger.info( "El usuario: "+usuario+" es Administrador");     
	                    	// TODO se debe agregar llamada a servicio para lista de recaudadores..
	                    	if(this.listaRecaudadores() == 0){
	                    		recaudadores = new String[listaReca.getListaRecaudadores().length];
	                    		for(int i=0; i< listaReca.getListaRecaudadores().length; i++){
	                    			recaudadores[i] = listaReca.getListaRecaudadores(i).getCodRecaudador()+
	                    			"-"+
	                    			listaReca.getListaRecaudadores(i).getNombreRecaudador();
	                    		}
	                    	}
	                    	
	                    	vista.setEntryList(recaudadores, true, false,index);
	                    	
	                        //vista.setEntryText(usuario, true, false, false, null, null);
	                        
	                        vista.setEntryMessage( "Usuario Administrador - Debe Seleccionar un Recaudador", true );
	                        fechaRecaudacion = Tools.getFecha();
	                        ///////////////////////////
	                        return ICajaView._WAITFORACTION;
                    	}
                    	else{
                    		estado = 6;
                    		recaudador = String.valueOf(codCajero);
                    		fechaRecaudacion = Tools.getFecha();
                    		return ICajaView._NOWAITFORACTION;                    		
                    	}
                    }
                    else {
                    	// TODO cbriones: validar retorno de validacion ??
                        //msg = respAut.getHeaderOut().getRcMessage();
                        msg = respAut.getRetDesc();
                    }
                    Base.logger.error( "Error Login: " + msg );
                    if (msg.equalsIgnoreCase("Error de Conexión... Fuera de Línea")){
                    	JOptionPane.showMessageDialog(null, "Se Cerrará la aplicación", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    	System.exit(0);
                    }
                    vista.setEntryMessage(msg, true);
                    estado = 0;
                    return ICajaView._NOWAITFORACTION;
                    
        		case 3:
        			//recaudador = vista.getEntryText();
        			index = vista.getEntryListIndex();
        			recaudador = recaudadores[index].split("-")[0];
        			
        			Base.logger.info( "El recaudador sera: "+recaudador);
        			//estado = 4;
        			estado = 6;
        			return ICajaView._NOWAITFORACTION;
        			
        		case 4:
        			estado = 5;
        			vista.setEntryMessage( "Ingrese fecha AAAAMMDD", true );
        			vista.setEntryTextLabel("Ingrese fecha recaudación:", true);
        			vista.setEntryText(Tools.getFecha(), true, false, false,"[0-9]+","Datos erroneos");
        			return ICajaView._WAITFORACTION;
        			
        		case 5:
                     estado = 6;
                     fechaRecaudacion = vista.getEntryText();
                     SimpleDateFormat inFormat = new SimpleDateFormat( "yyyyMMdd" );
                     try {
                         Date date = inFormat.parse( fechaRecaudacion );
                         if(!inFormat.format(date).equals(fechaRecaudacion)){
                             estado = 4;
                             return ICajaView._NOWAITFORACTION;
                         }
                         if(date.after( new Date() )){
                        	 vista.setEntryMessage("Fecha no puede ser posterior", true);
                             estado = 4;
                             return ICajaView._NOWAITFORACTION;
                         }
                     } catch (ParseException e) {
                         estado = 4;
                         return ICajaView._NOWAITFORACTION;
                     }
                     if( fechaRecaudacion.length() != 8 ){
                         estado = 4;
                         return ICajaView._NOWAITFORACTION;
                     }
                     return ICajaView._NOWAITFORACTION;
                     
        		case 6:
        			 rc = 0;
                     vista.showBusyWindow("Ejecutando Apertura", "Espere por favor...");
                     rc = aperturaCaja();
                     vista.hideBusyWindow();
                     
                     if( rc == 0 ){ 
                    	// OK, TENGO TODOS LOS DATOS
                 		
     	                ParamSet pList = Base.getParamSet( "posDat" );
     	                pList.setValue( "Usuario", usuario );
     	                pList.setValue( "Perfil", perfil );
     	                //pList.setValue( "Cajero", cajero );
     	                pList.setValue( "Cajero", codCajero );
     	                pList.setValue("SessionId", sessionId);
     	                pList.setValue("Recaudador", recaudador);
     	                pList.setValue("CodigoRecaudador", codRecaudador);
     	                pList.setValue( "FechaPago", fechaRecaudacion );
     	                pList.setValue( "UsuarioOffline", usuario );
     	                
     	                // se agregan como parametros al archivo de configuracion
     	                pList.setValue( "Canal", canal );
     	                pList.setValue( "Entidad", entidad );
     	                
     	                pList.save();
     	                Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
     	                
     	                //seteo usuario transbank
     	                try {
	     	               ParamSet posDat = Base.getParamSet("posDat");
	     	              Base.logger.info("P I N P A D - inicializa");
	     	               Base.tbk.inicializa(posDat.getStringValue("TransbankConfig")); //Aqui se solia poner el codCajero
	     	               Base.tbk.setNroEmpleado(codCajero+""); //Aqui se pone ahora el codCajero
	     	               Base.logger.info( "configurar empleado ok" );
     	                } catch (Exception e ) {
     	                	Base.logger.info( "Error al configurar empleado" );
     	                }
     	                 	                
                        //Una vez logueado, se abre la pantalla de chat
                        Base.logger.error( "Iniciando Chat : " + usuario );
                        String url = (pList.getStringValue("URL_ChatWeb") == null?"":pList.getStringValue("URL_ChatWeb"));
                        Browser chatJava = new Browser(usuario, url);
                        chatJava.abrirChat();
     	                
     	                // Se agrega validacion para Autorizacion de Anulacion/Edicion
     	                if("1".equalsIgnoreCase(perfil)){	// Admin
     	                	ThreadEnviosContingencia envios = new ThreadEnviosContingencia();
     	                	envios.start();
     	                	return 12;
     	                } else if ("5".equalsIgnoreCase(perfil)) { // Supervisor
     	                	ThreadEnviosContingencia envios = new ThreadEnviosContingencia();
     	                	envios.start();
     	                	return 14;
     	                }else{
     	                	ThreadEnviosContingencia envios = new ThreadEnviosContingencia();
     	                	envios.start();
     	                	return 11;  
     	                }
     	                	
     	                //return 11;  
                     } else if (rc == -4) {   
                    	 
                    	 ParamSet pList = Base.getParamSet( "posDat" );
      	                 pList.setValue( "Usuario", usuario );
                    	 // Codigo -20 'CAJA NO FUE CERRADA EN DIA ANTERIOR'
                    	 int retCode = Integer.valueOf(respAper.getRetCode().trim());
                    	 if (retCode == -20) {
                    		int dialogButton = JOptionPane.YES_NO_OPTION;
                 			int dialogResult = JOptionPane.showConfirmDialog(null,
                 					"¿Caja no fue cerrada en día anterior desea proceder con el cierre?", "Cierre de Caja",
                 					dialogButton);

                 			if (dialogResult == JOptionPane.YES_OPTION) {
                 				try {
									obtenerUltimaSesion();
									return 10;
								} catch (RemoteException e) {
									JOptionPane.showMessageDialog(null,
											"Error al obtener última sesión", "Continuar",
											JOptionPane.INFORMATION_MESSAGE);
									Tools.logStackTrace(Base.logger, e);
									Base.logger
											.error("Se produjo un error al al obtener última sesión...");
									return -1;
								}
                 			}
                    	 }
                    	 msg = respAper.getRetDesc();
                     }
                     else {
                    	 // TODO cbriones: validar flag de retorno ??
                         //msg = respAper.getHeaderOut().getRcMessage();
                    	 msg = respAper.getRetDesc();
                     }
                     Base.logger.error( "Error Apertura: " + msg );
                     vista.setEntryMessage(msg, true);
                     
                     estado = 0;
                     
                     return ICajaView._NOWAITFORACTION;
                     
        	}
        }
    }
    
    private int validateUser() {
    	
    	//ServerProxy pr = Proxy.getProxyInstance();
    	AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
    	
    	//AutentificaIn aIn= new AutentificaIn();
    	AutentificarInDTO aIn= new AutentificarInDTO();
    	
    	//HeaderIn hIn = new HeaderIn();
    	//HeaderDTO hIn = new HeaderDTO();
    	
    	ParamSet pSet = Base.getParamSet("posDat");
		
    	Base.logger.info("Autentificando Usuario....");
    	aIn.setAgencia(pSet.getStringValue("Agencia"));
        Base.logger.info("Agencia: "+aIn.getAgencia());
        aIn.setCajaFisica(pSet.getStringValue("Caja"));
		Base.logger.info("Caja: "+aIn.getCajaFisica());
		aIn.setCajero(pSet.getStringValue("Cajero"));
		Base.logger.info("Cajero: "+aIn.getCajero());
		aIn.setEntidad(pSet.getStringValue("Entidad"));
		Base.logger.info("Entidad: "+aIn.getEntidad());
		aIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
        Base.logger.info("Recaudador: "+aIn.getRecaudador());
        aIn.setSession(pSet.getStringValue("SessionId"));
		Base.logger.info("SessionId: "+aIn.getSession());
		//aIn.setUsuario(pSet.getStringValue("Usuario"));
		//Base.logger.info("Usuario: "+aIn.getUsuario());
		
		//aIn.setHeaderIn(aIn);
		
		aIn.setUsuario(usuario);
		Base.logger.info("Usuario: "+aIn.getUsuario());
		aIn.setPassword(clave);
//		MTORO se oculta la clave en el log
//		Base.logger.info("Passwd: "+aIn.getPassword());
		
		respAut = null;
		
		try {
			
			//TODO AUTENTICAR PRUEBA
			respAut = pr.autentificar(aIn);
			
//			respAut = new AutentificarOutDTO();
//			respAut.setCajero(102);
//			respAut.setPerfil("1");
//			respAut.setRetCode("0");
//			respAut.setRetDesc("OK");
//			
//			Base.logger.info("Resp: "+respAut.getRetCode());
//			Base.logger.info("Resp msj: "+respAut.getRetDesc());
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "Error al Autentificar Usuario", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			respAut = new AutentificarOutDTO();
			respAut.setRetDesc("Error de Conexión... Fuera de Línea");
			Tools.logStackTrace(Base.logger, e);
			Base.logger.error("Se produjo un error al Invocar al servicio de Autentificacion...");
			return -1;
		}
		
		// TODO cbriones: validar datos de retorno (flag de error??)!!
		//HeaderOut hOut = respAut.getHeaderOut();
		//HeaderOut hOut = new HeaderOut();
		//if(hOut.getRc() == 0){
		if("0".equalsIgnoreCase(respAut.getRetCode())){
			perfil = respAut.getPerfil();
			Base.logger.info("Perfil: "+respAut.getPerfil());
	    	codCajero = respAut.getCajero();
	    	Base.logger.info("Cod. Cajero: "+respAut.getCajero());
			return 0;
		}
		else{
			return -1;
		}
	}
	
	/**
	 * En caso de que la caja no haya sido cerrada un día anterior 
	 * se obtiene el código y fecha de la última sesión 
	 * 
	 * @throws RemoteException
	 */
	private void obtenerUltimaSesion() throws RemoteException{
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		UltimaSesionRequestDTO usr = new UltimaSesionRequestDTO();
		UltimaSesionResponseDTO usrd = null;
		ParamSet pSet = Base.getParamSet("posDat");
		
		usr.setAgencia(pSet.getStringValue("Agencia"));
		usr.setRecaudador(recaudador);
		usr.setUsuario(String.valueOf(codCajero));
		usrd = pr.ultimaSesion(usr);
		
		pSet.setValue("Cajero", usrd.getSesion().getCodUsuario());
		pSet.setValue("SessionId", usrd.getSesion().getCodSesion());
		pSet.setValue("FechaSession", usrd.getSesion().getFechaSesion());
		pSet.setValue("CodigoRecaudador", usrd.getSesion().getCodRecaudador());
	}

	private int listaRecaudadores() {

		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		AutentificarInDTO aIn = new AutentificarInDTO();

		ParamSet pSet = Base.getParamSet("posDat");

		Base.logger.info("Obteniendo Lista de Recaudadores...");
		aIn.setAgencia(pSet.getStringValue("Agencia"));
		Base.logger.info("Agencia: " + aIn.getAgencia());
		aIn.setCajaFisica(pSet.getStringValue("Caja"));
		Base.logger.info("Caja: " + aIn.getCajaFisica());
		aIn.setCajero(codCajero + "");
		Base.logger.info("Cajero: " + codCajero);
		aIn.setEntidad(pSet.getStringValue("Entidad"));
		Base.logger.info("Entidad: " + aIn.getEntidad());
		aIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
		Base.logger.info("Recaudador: " + aIn.getRecaudador());
		aIn.setSession(pSet.getStringValue("SessionId"));
		Base.logger.info("SessionId: " + aIn.getSession());

		aIn.setUsuario(codCajero + "");
		Base.logger.info("Usuario: " + codCajero + "");
		aIn.setPassword(clave);
//		MTORO se oculta la clave en el log
//		Base.logger.info("Passwd: " + aIn.getPassword());

		listaReca = null;

		try {

			listaReca = pr.listaRecaudadores(aIn);

			Base.logger.info("Resp: " + listaReca.getRetCode());
			Base.logger.info("Resp msj: " + listaReca.getRetDesc());
		} catch (RemoteException e) {
			Base.logger
					.error("Error en la invocacion al rescatar Recaudadores: "
							+ e.getMessage());
			// vista.hideBusyWindow();
			JOptionPane.showMessageDialog(null, "Error de conexión",
					"Continuar", JOptionPane.INFORMATION_MESSAGE);
			Tools.logStackTrace(Base.logger, e);
			// return 20;
		}

		// TODO cbriones: validar datos de retorno (flag de error??)!!
		if ("0".equalsIgnoreCase(listaReca.getRetCode())) {
			return 0;
		}

		if (listaReca == null) {
			JOptionPane.showMessageDialog(null,
					"Error al obtener Recaudadores", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			return -1;
		} else {
			return 0;
		}

	}

	private int aperturaCaja() {

		// ServerProxy pr = Proxy.getProxyInstance();
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();

		// AperturaCajaIn aIn= new AperturaCajaIn();
		AperturaCajaInDTO aIn = new AperturaCajaInDTO();

		// HeaderIn hIn = new HeaderIn();
		// HeaderDTO hIn = new HeaderDTO();

		ParamSet pSet = Base.getParamSet("posDat");
		
    	Base.logger.info( "Aperturando Caja....");  
    	aIn.setAgencia((pSet.getStringValue("Agencia")));
		Base.logger.info( "Agencia: " + aIn.getAgencia());      
		aIn.setCajaFisica((pSet.getStringValue("Caja")));
		Base.logger.info( "Caja: " + aIn.getCajaFisica()); 
		aIn.setEntidad((pSet.getStringValue("Entidad")));
		Base.logger.info( "Entidad: " + aIn.getEntidad()); 
		//aIn.setUsuario(usuario);
		aIn.setUsuario(codCajero+"");
		Base.logger.info( "Usuario: " + aIn.getUsuario()); 
		aIn.setCajero(codCajero+"");
		//aIn.setCajero(pSet.getStringValue("Cajero"));
		Base.logger.info( "Cajero: " + aIn.getCajero()); 
		//aIn.setRecaudador(recaudador);
		//aIn.setRecaudador(pSet.getStringValue("Recaudador"));
		aIn.setRecaudador(recaudador);
		//aIn.setRecaudador(codCajero+"");
		Base.logger.info( "Recaudador: " + aIn.getRecaudador()); 
		//aIn.setSession(pSet.getStringValue("SessionId"));
		//Base.logger.info( "Session: " + aIn.getSession()); 
		
		//aIn.setHeaderIn(hIn);
		aIn.setFechaPago(fechaRecaudacion);
		Base.logger.info( "Fecha pago: " + aIn.getFechaPago()); 
		
		//aIn.setRecaudador(recaudador);
		
		respAper = null;
		
		try {
			// TODO cbriones: el serv no esta retornando codigo, se setea 0 !!
			respAper = pr.aperturaCaja(aIn);
			
			//respAper = new AperturaCajaOutDTO();
			//respAper.setRetCode("0");
			
			Base.logger.info("Resp: "+respAper.getRetCode());
			Base.logger.info("Resp msj: "+respAper.getRetDesc());
			Base.logger.info("Estado: "+respAper.getEstado());
			Base.logger.info("Canal: "+respAper.getCanal());
			Base.logger.info("Entidad: "+respAper.getEntidad());
			
		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
		}
		
		// TODO cbriones: validar flag de retorno ??
		//HeaderOut hOut = respAper.getHeaderOut();
		//HeaderOut hOut = new HeaderOut();
	
		if("0".equalsIgnoreCase(respAper.getRetCode())){
			if(respAper.getEstado().equals("ABIERTA_COMPLETA")){
			//if(respAper.getEstado().equalsIgnoreCase("OPERACION EXITOSA")){
					
				sessionId = respAper.getSessionId();
				Base.logger.info("Resp sessionId: "+respAper.getSessionId());
				codRecaudador = respAper.getCodRecaudador();
				Base.logger.info("Resp Cod Recaudador: "+respAper.getCodRecaudador());
		    	recaudador = respAper.getNombreRecaudador();
		    	Base.logger.info("Resp Nom Recaudador: "+respAper.getNombreRecaudador());
		    	canal = respAper.getCanal();
		    	Base.logger.info("Canal: "+respAper.getCanal());
		    	entidad = respAper.getEntidad();
		    	Base.logger.info("Entidad: "+respAper.getEntidad());
		    	
				return 0;
			}
			else if(respAper.getEstado().equals("ABIERTA_INCOMPLETA")){
				return -1;
			}
			else if(respAper.getEstado().equals("CERRADA_COMPLETA")){
				return -2;
			}
			else if(respAper.getEstado().equals("CERRADA_INCOMPLETA")){
				return -3;
			}
			else{
				return -4;
			}
		}
		else{
			return -4;
		}
    }
}
