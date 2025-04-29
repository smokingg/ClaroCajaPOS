package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.HeaderOut;
import cl.hyh.cajas.ws.impl.InicializaCajaOut;
import cl.hyh.cajas.ws.impl.LoginIn;
import cl.hyh.cajas.ws.impl.LoginOut;
import cl.hyh.cajas.ws.impl.Request;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

/**
 * @author abertens
 *
 * password inválida
 * password vencida
 * usuario bloqueado
 * error autenticación
 */
public class AperturaCaja implements ITrxBase {
    private int estado = 0;
    private String usuario;
    private String clave;
    private String claveN1;
    private String claveN2;
    private String recaudador;
    private String perfil = "c";
    private int cajero;
    private int sessionId;
    private LoginOut resp;

    private boolean dummyLogin = false;
    
    public void init( Datos htParm ) {}
    
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
            if( estado == 0 ) {
                vista.setEntryTextLabel("Ingrese usuario:", true);
                vista.setEntryText("", true, false, false, null, null);
                estado = 1;
                return ICajaView._WAITFORACTION;
            } else if( estado == 1 ) {
                usuario = vista.getEntryText();
                vista.setEntryMessage("", false);
                vista.setEntryTextLabel("Ingrese clave:", true);
                vista.setEntryText("", true, false, true, null, null);
                estado = 2;
                return ICajaView._WAITFORACTION;
            } else if( estado == 2 ) {
                // ESTAMOS LISTOS: VALIDAMOS Y NOS VAMOS
                clave = vista.getEntryText();
                
                int rc = 0;
                vista.showBusyWindow("Validando", "Espere por favor...");
                rc = validateUser();
                vista.hideBusyWindow();
                if( rc == 0 ) {
                	// OK, TENGO TODOS LOS DATOS
	                ParamSet pList = Base.getParamSet( "posDat" );
	                pList.setValue( "Usuario", usuario );
	                pList.setValue( "Perfil", perfil );
	                pList.setValue( "Cajero", cajero );
	                pList.setValue("SessionId", sessionId);
	                pList.setValue( "FechaPago", Tools.getFecha() );
	                pList.setValue( "UsuarioOffline", usuario );
	                pList.save();
	                Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
  
    	            return 11;                    
                } else if( rc == 1 ) {
                	// ME ESTAN FORZANDO UN CAMBIO DE CLAVE
                    vista.setEntryMessage("Debe cambiar su clave de acceso", true);
                    estado = 3;
                    return ICajaView._NOWAITFORACTION;
                } else if( rc == -1 ) {
                    // USUARIO O CLAVE NO VALIDOS: VUELVE A ESTADO 0 CON MENSAJE
                    msg = "Usuario y/o clave no válidos";
                } else if( rc == -2 ) {
                    msg = "Error al leer usuarios";
                } else if( rc == -3 ) {
                    // msg = "Error al ejecutar servicio Login";
                	// NO HAY COMUNICACION, ANALISIS LUEGO (SUPER)
                } else if( rc == -4 ) {
                	// NO HAY COMUNICACION, ANALISIS LUEGO (SUPER)
                } else if( rc == -5 ) {
                    msg = "Fallo servicio de Autenticacion";
                } else if( rc == -6 ) {
                    msg = "Usuario bloqueado";
                }else if ( rc == 9907 ){
                    ParamSet posDat = Base.getParamSet("posDat");
                    if( posDat.getStringValue("FechaApertura").equals(Tools.getFecha()) )
                        estado = 6;
                    else{
                        JOptionPane.showMessageDialog(null, "Agencia Cerrada - No es posible abrir la Caja",
                                "Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                }
                else {
                    msg = "Fallo servicio de Autenticacion, rc=" + rc;
                }
                Base.logger.error( "Error Login: " + msg );
                vista.setEntryMessage(msg, true);
                estado = 0;
            } else if( estado == 3 ) {
                vista.setEntryTextLabel("Ingrese clave nueva:", true);
                vista.setEntryText("", true, false, true, null, null);
                estado = 4;
                return ICajaView._WAITFORACTION;
            } else if( estado == 4 ) {
                claveN1 = vista.getEntryText();
                if( claveN1.trim().length() == 0 ) {
                    vista.setEntryMessage("Debe ingresar una clave nueva", false);
                    estado = 3;
                    return ICajaView._NOWAITFORACTION;
                }
            	vista.hideAllEntries();
                vista.setEntryMessage("", false);
                vista.setEntryTextLabel("Reingrese clave nueva:", true);
                vista.setEntryText("", true, false, true, null, null);
                estado = 5;
                return ICajaView._WAITFORACTION;
            } else if( estado == 5 ) {
                claveN2 = vista.getEntryText();
                if( !claveN1.equals(claveN2) ) {
                    vista.setEntryMessage("Claves nuevas no son iguales", true);
                    estado = 3;
                } else {
	                int rc = changePassword();
	                if( rc == 0 ) {
	                    rc =0;
	                    if( rc == 0 ) {
	                    	// OK, TENGO TODOS LOS DATOS
	    	                ParamSet pList = Base.getParamSet( "posDat" );
	    	                pList.setValue( "Usuario", usuario );
	    	                pList.setValue( "Perfil", perfil );
	    	                pList.setValue( "Cajero", recaudador );
	    	                pList.setValue( "FechaPago", Tools.getFecha() );
	    	                pList.setValue( "UsuarioOffline", usuario );
	    	                pList.save();
	    	                Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
 
	    	                int rcS = enviaAperturaCaja(vista);
	                        if( rcS == 0 )
	                            return 11;
	                        else
	                            return 13;
	                    } else if( rc == -1 ) {
	                        // NO ENCONTRO USUARIO EN ARCHIVO DE USUARIOS
	                        msg = "Usuario no encontrado en archivo de usuarios";
	                    } else if( rc == -2 ) {
	                        msg = "Error al leer usuarios";
	                    }                	
	                } else if( rc == -1 ) {
	                    msg = "Clave no válida";
	                } else if( rc == -3 ) {
	                    msg = "Error al ejecutar servicio de cambio de password";
	                } else if( rc == -4 ) {
	                    msg = "Error al ejecutar servicio de cambio de password";
	                } else if( rc == -5 ) {
	                    msg = "Fallo servicio de Autenticacion";
	                } else if( rc == -6 ) {
	                    msg = "Usuario bloqueado";
	                } else if( rc == -8 ) {
	                    msg = "Nueva clave es inaceptable";
	                } else {
	                	// ERRORES VARIOS
	                    msg = "Error al ejecutar servicio de cambio de password, rc=" + rc;
	                }
                    Base.logger.error( "Error Login: " + msg );
                    vista.setEntryMessage(msg, true);
                    estado = 3;                	
                }
            } else if( estado == 6 ) {
                Base.logger.info( "Login en modo Contingencia" );
                // PRECARGO FALLA EN VALIDACION DE SUPERVISOR
                htParam.setValue("supervisor", 0);
                // MARCO ESTADO PARA LA VUELTA
                estado = 7;
                JOptionPane.showMessageDialog(null, "No hay línea. Se habilitará el usuario mediante Supervisor.",
                        "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                return 20;
            } else if( estado == 7 ) {
            	// ENTRO AQUI DESPUES DE VALIDAR SUPERVISOR
            	// VIENE CARGADO htParam("supervisor") con 0=falla, 1=ok.
            	if( htParam.getIntValue("supervisor") == 0 ) {
            		// FALLO LA VALIDACION DE SUPERVISOR
                    JOptionPane.showMessageDialog(null, "Falló validación de supervisor",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return 13;
            	}
            	htParam.setValue("supervisor", 0);
            	vista.hideAllEntries();
            	int rc = 0; // APROVECHO DE VERIFICAR QUE USUARIO EXISTE EN LISTA
            	if( rc == 0 ) {
	                ParamSet pList = Base.getParamSet( "posDat" );
	                pList.setValue( "Usuario", usuario );
	                pList.setValue( "Perfil", perfil );
	                pList.setValue( "Cajero", recaudador );
	                pList.setValue( "FechaPago", Tools.getFecha() );
	                pList.setValue( "UsuarioOffline", usuario );
	                pList.save();
	                Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
    
	                enviaAperturaCaja(vista);
	                
	                return 11;
            	} else if( rc == -1 ) {
                    // NO ENCONTRO USUARIO EN ARCHIVO DE USUARIOS
                    msg = "Usuario no válido";
                    Base.logger.info( "Usuario no encontrado: " + usuario );
            	} else if( rc == -2 ) {
                    msg = "Error al leer archivo de usuarios";
            	} 
                vista.setEntryMessage(msg, true);
                estado = 0;
            }
        }
    }
    
    private int enviaAperturaCaja(ICajaView vista) {
        Servicio aperturaCaja = null;
        int rc = -1;
        int rcS = -1;
        ParamSet posDat = Base.getParamSet("posDat");
        try {
            aperturaCaja = FactoryServicio.makeInstance( "AperturaCaja" );
            vista.showBusyWindow("Consultando", "Espere por favor...");
            rcS = aperturaCaja.execute();
            vista.hideBusyWindow();
        } catch (Exception e) {
            vista.hideBusyWindow();
            Tools.logStackTrace( Base.logger, e );
            if(posDat.getStringValue("FechaApertura").equals(Tools.getFecha())){
                return 0;
            } 
            return rc;
        }
        if( rcS != 0 ){
            if(posDat.getStringValue("FechaApertura").equals(Tools.getFecha())){
                return 0;
            } 
            return rc;
        }
        rc = Integer.parseInt(aperturaCaja.getHeaderOut().getStringValue("RetCode"));
        if( rc != 0 ){  
            return rc;
/*            if(posDat.getStringValue("FechaApertura").equals(Tools.getFecha())){
                return 0;
            }   */         
        }
        else{
            posDat.setValue("FechaApertura", Tools.getFecha());
            posDat.save();
        }
        return rc;
    }
    
    private int validateUser() {
    	ServerProxy pr = Proxy.getProxyInstance();
    	
    	LoginIn lIn = new LoginIn();
    	HeaderIn hIn = new HeaderIn();
    	
    	ParamSet pSet = Base.getParamSet("posDat");
		
		hIn.setAgencia((int)(pSet.getLongValue("Agencia")));
		hIn.setCajaFisica((int)(pSet.getLongValue("Caja")));
		hIn.setEntidad((int)(pSet.getLongValue("Entidad")));
		
		lIn.setHeaderIn(hIn);
		lIn.setUsuario(usuario);
		lIn.setPassword(clave);
		
		resp = null;
		
		try {
			resp = pr.login(lIn);
		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
		}
		
		HeaderOut hOut = resp.getHeaderOut();
		if(hOut.getRc() == 0){
			if(resp.getEstado().equals("ABIERTA_COMPLETA")){
				perfil = resp.getPerfil();
		    	cajero = resp.getCajero();
		    	sessionId = resp.getSessionId();
				return 0;
			}
			else if(resp.getEstado().equals("ABIERTA_INCOMPLETA")){
				return -1;
			}
			else if(resp.getEstado().equals("CERRADA_COMPLETA")){
				return -1;
			}
			else if(resp.getEstado().equals("CERRADA_INCOMPLETA")){
				return -1;
			}
			else{
				return -1;
			}
		}
		else{
			return -1;
		}
    	/****************************************************/
    	/****************************************************/
        /*boolean valid = false;
    	// VALIDACION REAL CONTRA SERVIDOR DE CAJAS
        Datos data = null;
        Servicio consultaAtis = null; 

        DefServicio def = Base.getDefServicio("Login");
        data = new Datos(def.getInputRecordDef());
        data.setValue("Usuario", usuario);
        data.setValue("Password", clave);

        int resp = 0;
        try {
            consultaAtis = FactoryServicio.makeInstance("Login");
            consultaAtis.setRequest(data);
            resp = consultaAtis.execute();
        } catch (BaseException e) {
            Base.logger.error( "Error al ejecutar servicio: " + e.toString()  );
            Tools.logStackTrace( Base.logger, e );
            return -3;
        }
        if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
        	Base.logger.error("El execute del servicio retorno error o timeout, rc="+resp);
            JOptionPane.showMessageDialog(null, "Error en la ejecucion del Servicio de Consulta, rc =" + resp,
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            return -4;
        } else {
            Datos hOut = consultaAtis.getHeaderOut();
            int rc = new Integer( hOut.getStringValue("RetCode") ).intValue();
            //TODO : validación de rc
        	Base.logger.warn("Autentica, rc="+rc);
            if( rc == 0 ) {
             	return 0;
            } else if( rc == 9900 ) {
             	// password parámetros
             	return -5;
            } else if( rc == 9901 ) {
             	// password inválida
             	return -1;
            } else if( rc == 9902 ) {
                // password vencida
             	return 1;
            } else if( rc == 9903 ) {
                // usuario bloqueado
             	return -6;
            } else if( rc == 9904  ) {
                // error autenticación
             	return -5;
            } else if( rc == 9905  ) {
                // error conexión
             	return -5;
            } else if( rc == 9906  ) {
                // nueva clave inaceptable: no puede ocurrir
             	return -5;
            } else if( rc == 9907  ) {
                // time out
                return -3;
            }
            else if( rc == 9999  ) {
                // error de sistema
                return -5;
            }
            return -7; // OTRO ??
        }*/
    }
    
    private int changePassword() {
    	if( dummyLogin ) {
    		return 0;
    	} else {
        	// CAMBIO DE PWD CONTRA AUTENTICA VIA IF
            Datos data = null;
            Servicio consultaAtis = null; 

            DefServicio def = Base.getDefServicio("CambioClave");
            data = new Datos(def.getInputRecordDef());
            data.setValue("Usuario", usuario);
            data.setValue("PasswordActual", clave);
            data.setValue("PasswordNueva", claveN1);

            int resp = 0;
            try {
                consultaAtis = FactoryServicio.makeInstance("CambioClave");
                consultaAtis.setRequest(data);
                resp = consultaAtis.execute();
            } catch (BaseException e) {
                Tools.logStackTrace(Base.logger, e);
                return -3;
            }
            if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
            	Base.logger.error( "El execute del servicio retorno error o timeout, rc="+resp);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion del Servicio de Consulta, rc=" + resp,
                        "Error", JOptionPane.INFORMATION_MESSAGE);
                return -4;
            } else {
                Datos hOut = consultaAtis.getHeaderOut();
                int rc = new Integer( hOut.getStringValue("RetCode") ).intValue();
            	Base.logger.error("Autentica, cambio de clave rc="+rc);
                if( rc == 0 ) {
                 	return 0;
                } else if( rc == 9900 ) {
                 	// password parametros
                 	return -5;
                } else if( rc == 9901 ) {
                 	// password invalida
                 	return -1;
                } else if( rc == 9902 ) {
                    // password vencida
                 	return 1;
                } else if( rc == 9903 ) {
                    // usuario bloqueado
                 	return -6;
                } else if( rc == 9904  ) {
                    // error autenticacion
                 	return -5;
                } else if( rc == 9905  ) {
                    // error conexion
                 	return -5;
                } else if( rc == 9906  ) {
                    // clave nueva no aceptable
                 	return -8;
                }
                else if( rc == 9999  ) {
                    // error sistema
                    return -5;
                }
            	return -7; // OTRO ??
            }
    	}
    }
}
