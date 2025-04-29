package cl.hyh.redpagos.caja.trx; 

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.RespuestaLoginTO;
import ws.claro.cl.proxy.AppControlProxy;

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

public class CambiaClave implements ITrxBase {
	
	private static final Integer MIN_PASSWORD_SIZE = 8;
	
    private int estado = 0;
    private String claveOrg = "";
    private String claveN1 = "";
    private String claveN2 = "";

    public void init( Datos htParm ) {}
    
    public int execute( ICajaView vista, int key, Datos htParam ) {
        if( key == 0 ) {
            vista.setInputTimeout(60);
        } else if( key == 1 ) {
            // TIMEOUT
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // OTRA TECLA. PROCESE CON LO QUE ESTE EN EL XML...
            return ICajaView._PASSTHROUGH;
        }
        
        while( true ) {
            if( estado == 0 ) {
                vista.setEntryTextLabel("Ingrese clave actual:", true);
                vista.setEntryText("", true, false, true, null, null);
                estado = 1;
                return ICajaView._WAITFORACTION;
            } else if( estado == 1 ) {
            	if( claveOrg == "" ) {
            		claveOrg = vista.getEntryText();
                    vista.setEntryMessage("", false);
            	}
                vista.setEntryTextLabel("Ingrese clave nueva:", true);
                vista.setEntryText("", true, false, true, "^(?:[0-9]+[a-z]|[a-z]+[0-9])[a-z0-9]*$", "Error en Cambio de Clave debe poseer números y letras");
                estado = 2;
                return ICajaView._WAITFORACTION;
            } else if( estado == 2 ) {
                claveN1 = vista.getEntryText();
                if( claveN1.trim().length() < MIN_PASSWORD_SIZE ) {
                    vista.setEntryMessage("Error en Cambio de Clave debe tener al menos 8 campos", true);
                    estado = 1;
                    return ICajaView._NOWAITFORACTION;                	
                }
                if(claveN1.trim().equals(claveOrg)) {
                	vista.setEntryMessage("Error en Cambio de Clave su nueva clave no debe ser igual a la anterior", true);
                    estado = 1;
                    return ICajaView._NOWAITFORACTION;
                }
                vista.setEntryMessage("", false);
                vista.setEntryTextLabel("Reingrese clave nueva:", true);
                vista.setEntryText("", true, false, true, null, null);
                estado = 3;
                return ICajaView._WAITFORACTION;
            } else if( estado == 3 ) {
                // ESTAMOS LISTOS: VALIDAMOS Y NOS VAMOS
                claveN2 = vista.getEntryText();
                if( !claveN1.equals(claveN2) ) {
                    vista.setEntryMessage("Claves nuevas no son iguales", true);
                    estado = 1;
                    return ICajaView._NOWAITFORACTION;
                }
                // TODO : FALTA VALICADION rc
            	// CAMBIO DE PWD CONTRA AUTENTICA VIA IF
                Datos data = null;
//                Servicio consultaAtis = null;
                AppControlCajaWSServerProxy updateClaveProxy = AppControlProxy.getProxyInstance();
                ParamSet pList = Base.getParamSet( "posDat" );

//                DefServicio def = Base.getDefServicio("CambioClave");
//                data = new Datos(def.getInputRecordDef());
//                data.setValue("Usuario", pList.getStringValue("Usuario"));
//                data.setValue("PasswordActual", claveOrg);
//                data.setValue("PasswordNueva", claveN1);

                int resp = 0;
                RespuestaLoginTO respTo = new RespuestaLoginTO();
                try {
        			Base.logger.info("Realizando la invocacion al servicio de cambio de clave");
        			vista.showBusyWindow("Consultando", "Espere por favor");
        			respTo = updateClaveProxy.updateUserPassword(pList.getStringValue("Usuario"), claveOrg, claveN1);
        			
//                    consultaAtis = FactoryServicio.makeInstance("CambioClave");
//                    consultaAtis.setRequest(data);
//                    vista.showBusyWindow("Consultando", "Espere por favor");
//                    resp = consultaAtis.execute();
                    vista.hideBusyWindow();
	            } catch (RemoteException e) {
//                } catch (BaseException e) {
                    vista.hideBusyWindow();
                	Base.logger.error( "Error al ejecutar servicio, "+e.toString() );
                    JOptionPane.showMessageDialog(null, "Error en la ejecucion del Servicio de Consulta\n" + e.toString(),
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    estado = 0;
                    return ICajaView._NOWAITFORACTION;
                }
                
	            Base.logger.info("Codigo Respuesta updatePassword" + respTo.getRetCod());
	            Base.logger.info("Descripcion Respuesta updatePassword" + respTo.getRetDesc());
                resp = Integer.parseInt(respTo.getRetCod());
                
                if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
                	Base.logger.error( "El execute del servicio retorno error o timeout, rc="+resp);
                    JOptionPane.showMessageDialog(null, "Error en la ejecucion del Servicio de Consulta, rc=" + resp,
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    estado = 0;
                } else {
//                    Datos hOut = consultaAtis.getHeaderOut();
//                    int rc = new Integer( hOut.getStringValue("RetCode") ).intValue();
                	int rc = resp;
                	Base.logger.error("Autentica, cambio de clave rc="+rc);
                	String msg = "";
                    if( rc == 0 ) {
                    	msg = "Actualización exitosa";
                    } else if( rc == 9900 ) {
                    	msg = "Error de parámetros";
                    } else if( rc == 9901 ) {
                    	msg = "Password no válida";
                    } else if( rc == 9902 ) {
                    	msg = "Password vencida";
                    } else if( rc == 9903 ) {
                    	msg = "Usuario bloqueado";
                    } else if( rc == 9904  ) {
                    	msg = "Error de autenticación";
                    } else if( rc == 9905  ) {
                    	msg = "Error de conexión con servicio de autenticación";
                    } else if( rc == 9906  ) {
                    	msg = "Nueva clave es inaceptable";
                    } else {
                    	msg = "Error desconocido";                    	
                    }
      
                    if( rc == 0 ) {
                        // ME VOY...
                        // SI OK, CONFIRMO QUE LE FUE BIEN!
                        JOptionPane.showMessageDialog(null, msg,
                                "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                    	return 13;
                    } else {
                        // SI OK, CONFIRMO QUE LE FUE BIEN!
                        JOptionPane.showMessageDialog(null, msg,
                                "Error en cambio de clave", JOptionPane.ERROR_MESSAGE);
                    	estado = 0;
                    }
                }
            } 
        }
    }

}
