package cl.clarochile.osbservicios.PlataformaPagoConsultar;

import java.rmi.RemoteException;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.OperacionOutHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.RespuestaHolder;

/**
 * Server Proxy para implementacion de Consulta Pago hacia App de Control Caja Claro
 * @author cbriones
 *
 */
public class PlataformaPagoConsultarServerProxy implements PlataformaPagoConsultar_PortType{

	  private String _endpoint = null;
	  private PlataformaPagoConsultar_PortType server = null;
	  
	  public PlataformaPagoConsultarServerProxy() {
	    _initServerProxy();
	  }
	  
	  public PlataformaPagoConsultarServerProxy(String endpoint) {
	    _endpoint = endpoint;
	    _initServerProxy();
	  }
	  
	  private void _initServerProxy() {
	    try {
	      server = (new PlataformaPagoConsultar_ServiceLocator()).getPlataformaPagoConsultarSOAP();
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
	  
	  public PlataformaPagoConsultar_PortType getServer() {
	    if (server == null)
	      _initServerProxy();
	    return server;
	  }
	  
	@Override
	public void consultar(OperacionIn operacionIn,
				          OperacionOutHolder operacionOut, 
				          RespuestaHolder respuesta) throws RemoteException {

		if (server == null)
		      _initServerProxy();
		server.consultar(operacionIn, operacionOut, respuesta);
		
	}

}
