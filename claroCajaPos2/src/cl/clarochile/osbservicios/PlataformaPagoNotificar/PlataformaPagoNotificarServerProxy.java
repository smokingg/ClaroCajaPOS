package cl.clarochile.osbservicios.PlataformaPagoNotificar;

import java.rmi.RemoteException;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultar_PortType;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultar_ServiceLocator;

/**
 * Server Proxy para implementacion de Notificacion de Pago hacia App de Control Caja Claro
 * @author cbriones
 *
 */
public class PlataformaPagoNotificarServerProxy implements PlataformaPagoNotificar_PortType{

	
	private String _endpoint = null;
	  private PlataformaPagoNotificar_PortType server = null;
	  
	  public PlataformaPagoNotificarServerProxy() {
	    _initServerProxy();
	  }
	  
	  public PlataformaPagoNotificarServerProxy(String endpoint) {
	    _endpoint = endpoint;
	    _initServerProxy();
	  }
	  
	  private void _initServerProxy() {
	    try {
	      server = (new PlataformaPagoNotificar_ServiceLocator()).getPlataformaPagoNotificarSOAP();
	      if (server != null) {
	        if (_endpoint != null)
	          ((javax.xml.rpc.Stub)server)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
	        else
	          _endpoint = (String)((javax.xml.rpc.Stub)server)._getProperty("javax.xml.rpc.service.endpoint.address");
	      }
	      
	    }
	    catch (javax.xml.rpc.ServiceException serviceException) {}
	  }
	  
	  public String getEndpoint() {
	    return _endpoint;
	  }
	  
	  public void setEndpoint(String endpoint) {
	    _endpoint = endpoint;
	    if (server != null)
	      ((javax.xml.rpc.Stub)server)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
	    
	  }
	  
	  public PlataformaPagoNotificar_PortType getServer() {
	    if (server == null)
	      _initServerProxy();
	    return server;
	  }
	  
	@Override
	public NotificacionRespuesta notificar(NotificacionEnvio notificarRequest) throws RemoteException {

		if (server == null)
		      _initServerProxy();
		return server.notificar(notificarRequest);
	}

}
