package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

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
public class AperturaAgencia implements ITrxBase {
    private int estado = 0;
    private String usuario;
    private String clave;
    private String claveN1;
    private String claveN2;
    private String recaudador;
    private String perfil = "c";

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
                    rc = getProfile();
                    if( perfil == "c"){
                        JOptionPane.showMessageDialog(null,"Usuario no tiene permisos de apertura","Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                    if( rc == 0 ) {
                     // OK, TENGO TODOS LOS DATOS
                        ParamSet pList = Base.getParamSet( "posDat" );
                        pList.setValue( "Usuario", usuario );
                        pList.setValue( "Perfil", perfil );
                        pList.setValue( "Cajero", recaudador );
                        pList.setValue( "FechaPago", Tools.getFecha() );
                        pList.save();
                        Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
                        int rcS = enviaAperturaAgencia(vista);
                        if(rcS == 0){
                            JOptionPane.showMessageDialog(null, "Apertura Agencia Realizada","Info", JOptionPane.INFORMATION_MESSAGE);
                            return 11;
                        }
                        else if(rcS == 2){
                            JOptionPane.showMessageDialog(null, "Agencia ya fue cerrada y no puede volver a abrirse durante el día","Info", JOptionPane.INFORMATION_MESSAGE); 
                            return 13;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No hay línea --- No es posible abrir Agencia","Info", JOptionPane.INFORMATION_MESSAGE); 
                            return 13;
                        }
                    } else if( rc == -1 ) {
                        // NO ENCONTRO USUARIO EN ARCHIVO DE USUARIOS
                        msg = "Usuario y/o clave no válidos";
                    } else if( rc == -2 ) {
                        msg = "Error al leer usuarios";
                    }
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
                } else {
                    msg = "Fallo servicio de Autenticacion, rc=" + rc;
                }
                Base.logger.error( "Error Login: " + msg );
                vista.setEntryMessage(msg, true);
                estado = 0;
                // CASO ESPECIAL: OFFLINE
                if( rc == -3 || rc == -4 ) {
                    JOptionPane.showMessageDialog(null, "No hay línea. No se puede realizar la apertura de agencia.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
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
	                    rc = getProfile();
	                    if( rc == 0 ) {
	                    	// OK, TENGO TODOS LOS DATOS
	    	                ParamSet pList = Base.getParamSet( "posDat" );
	    	                pList.setValue( "Usuario", usuario );
	    	                pList.setValue( "Perfil", perfil );
	    	                pList.setValue( "Cajero", recaudador );
	    	                pList.setValue( "FechaPago", Tools.getFecha() );
	    	                pList.save();
	    	                Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
 
	    	                int rcS = enviaAperturaAgencia(vista);
	    	                if(rcS == 0)
	    	                    return 11;
	    	                else{
	    	                    return 13;
	    	                }
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
            } 
        }
    }

    private int enviaAperturaAgencia(ICajaView vista) {
        Servicio aperturaAgencia = null;
        int rc = -1;
        int rcS = -1;
        try {
            aperturaAgencia = FactoryServicio.makeInstance( "AperturaAgencia" );
            vista.showBusyWindow("Consultando", "Espere por favor...");
            rcS = aperturaAgencia.execute();            
            vista.hideBusyWindow();
        } catch (Exception e) {
            vista.hideBusyWindow();
            Tools.logStackTrace( Base.logger, e );
            return rc;
        }
        if( rcS != 0 ){
            return rc;
        }
        rc = Integer.parseInt(aperturaAgencia.getHeaderOut().getStringValue("RetCode"));
        if( rc != 0){
            if(rc == 2 || rc == 1){
                return rc;
            }
            rc = -1;
            //JOptionPane.showMessageDialog(null, "Problemas en la apertura de agencia" , "Continuar", JOptionPane.INFORMATION_MESSAGE);
        }
        return rc;
    }
    
    private int validateUser() {
        boolean valid = false;
        
        if( dummyLogin ) {
	    	try {
				BufferedReader fd = new BufferedReader( new FileReader("/RedDePagos/data/config/usuarios.dat") );
				String line = null;
				while( true ) {
					line = fd.readLine();
					if( line == null )
						break;
					String fields[] = line.split(",");
					if( fields != null && fields.length >= 3 ) {
						if( usuario.equalsIgnoreCase(fields[0]) ) {
							// VALIDACION CUCHUFLETA DE CLAVE
							if( clave.length() == 3 && 
									fields[0].length() >= 3 && 
									clave.substring(0, 3).equalsIgnoreCase(fields[0].substring(0,3)) ) {
								valid = true;									
							}
							break;
						}
					}
				}
				fd.close();
				if( !valid ) {
					return -1;
				} else {
					// OK
	                return 0;						
				}
			} catch (FileNotFoundException e) {
	            Base.logger.error( "Error al leer archivo de usuarios: " + e.toString()  );
				e.printStackTrace();
			} catch (IOException e) {
	            Base.logger.error( "Error al leer archivo de usuarios: " + e.toString()  );
				e.printStackTrace();
			}
			return -2;
        } else {
        	// VALIDACION REAL CONTRA AUTENTICA VIA IF
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
            }
        }
    }
    
    private int getProfile() {
        boolean found = false;
    	try {
			BufferedReader fd = new BufferedReader( new FileReader("/RedDePagos/data/config/usuarios.dat") );
			String line = null;
			while( true ) {
				line = fd.readLine();
				if( line == null )
					break;
				String fields[] = line.split(",");
				if( fields != null && fields.length >= 3 ) {
					if( usuario.equalsIgnoreCase(fields[0]) ) {
						perfil = "c";
						if( fields[1].equalsIgnoreCase("S")) {
							perfil = "s";
						} else if( fields[1].equalsIgnoreCase("B")) {
							perfil = "b";
						} else if( fields[1].equalsIgnoreCase("C")) {
							perfil = "c";
						} else if( fields[1].equalsIgnoreCase("E")) {
                            perfil = "e";
                        }else {
							perfil = "c";
							Base.logger.error( "Perfil desconocido (" + fields[1] + ") para usuario " + usuario );
						}
						recaudador = fields[2];
						found = true;
						break;
					}
				}
			}
			fd.close();
			if( !found ) {
                // USUARIO NO ENCONTRADO?
				return -1;
			} else {
				// OK
                return 0;						
			}
		} catch (FileNotFoundException e) {
            Base.logger.error( "Error al leer archivo de usuarios: " + e.toString()  );
			e.printStackTrace();
		} catch (IOException e) {
            Base.logger.error( "Error al leer archivo de usuarios: " + e.toString()  );
			e.printStackTrace();
		}
    	return -2;
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
