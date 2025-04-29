package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ws.claro.cl.AperturaCajaOutDTO;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.AutentificarInDTO;
import ws.claro.cl.AutentificarOutDTO;
import ws.claro.cl.ListaRecaudadoresOutDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * @author abertens
 *
 * password inválida
 * password vencida
 * usuario bloqueado
 * error autenticación
 */
public class LoginOffLine implements ITrxBase {
    private int estado = 0;
    private String usuario;
    private String clave;
    private String recaudador;
    private int codRecaudador;
    private String fechaRecaudacion;
    private String perfil = "2"; // por default es un Cajero !!
    private int codCajero;
    //private String cajero;
    private int sessionId;
    //private AutentificaOut respAut;
    private AutentificarOutDTO respAut;
    //private AperturaCajaOut respAper;
    private AperturaCajaOutDTO respAper;
    
    ListaRecaudadoresOutDTO listaReca = null;
    
    private int canal;
    private int entidad;
    
    int index = 0;
   
    
    public static Logger logger;
    
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
        	switch( estado ){
        		case 0:
					vista.setEntryTitle( "Caja en Modo Contingencia", true );   
        			vista.setEntryTextLabel("Ingrese usuario:", true);
                    vista.setEntryText("", true, false, false, null, null);
                    estado = 1;
                    return ICajaView._WAITFORACTION;
        		case 1:
        			
                    clave = vista.getEntryText();
                    if(clave.equals("")){
    	                estado = 0;
						vista.setEntryTitle( "Caja en Modo Contingencia", true );   
    	                vista.setEntryMessage( "Usuario de Caja Inválido - Ingrese nuevamente", true );
    	                vista.setEntryTextLabel("Ingrese usuario::", true);
                        vista.setEntryText("", true, false, true, null, null);
    	                return ICajaView._WAITFORACTION;
    	            }
                    int rc = 0;
                    
                    vista.showBusyWindow("Validando Usuario", "Espere por favor...");
                    rc = validateUser();
                    vista.hideBusyWindow();
                    
                    if( rc == 0 ){

                    		estado = 2;
                    		return ICajaView._NOWAITFORACTION;                    		
                    	}
                    
                    else {

                        msg = "Usuario No Autorizado para operar Contingencia en esta Caja";
                    }
                    Base.logger.error( "Error Login: " + msg );
                    vista.setEntryMessage(msg, true);
                    estado = 0;
                    return ICajaView._NOWAITFORACTION;
                    
        		
                     
        		case 2:
        			 rc = 0;
                     vista.showBusyWindow("Ejecutando Apertura Modo Contingencia", "Espere por favor...");
					 Tools.espera(10);
                     vista.hideBusyWindow();
                     
                     if( rc == 0 ){ 
                    	// OK, TENGO TODOS LOS DATOS
                 		
     	                ParamSet pList = Base.getParamSet( "posDat" );
     	                Base.logger.info( "Login: " + pList.getStringValue("Usuario") );
     	                pList.setValue( "AbiertaSinConexion", "si" );	
     	               	pList.save();
     	                return 12;
     	                
                     }
                     else {
                    	 // TODO cbriones: validar flag de retorno ??
                         //msg = respAper.getHeaderOut().getRcMessage();
                    	 msg = "No se Pudo Aperturar la Caja en Modo Contingencia";
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
        Base.logger.info("Agencia: "+ pSet.getStringValue("Agencia"));
		Base.logger.info("Caja: "+ pSet.getStringValue("Caja"));
		Base.logger.info("Cajero: "+ pSet.getStringValue("Cajero"));
		Base.logger.info("Entidad: "+ pSet.getStringValue("Entidad"));
        Base.logger.info("Recaudador: "+ pSet.getStringValue("CodigoRecaudador"));
		Base.logger.info("SessionId: "+ pSet.getStringValue("SessionId"));
		Base.logger.info("UsuarioOffline: "+ pSet.getStringValue("UsuarioOffline"));
		Base.logger.info("Usuario: "+ clave);
				
		if(pSet.getStringValue("UsuarioOffline").equals(clave)){
			
			Base.logger.info("Resp: Usuario Autorizado para Operar en esta Caja en Modo Contingencia ");
			Base.logger.info("Resp Caja: " + pSet.getStringValue("Caja"));
			return 0;
			
		} else {
			JOptionPane.showMessageDialog(null, "Error al Autentificar Usuario", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			Base.logger.error("Se produjo un error al Autentificar al Usuario en Modo Contingencia...");
			return -1;
		}
		

    }
  
}
