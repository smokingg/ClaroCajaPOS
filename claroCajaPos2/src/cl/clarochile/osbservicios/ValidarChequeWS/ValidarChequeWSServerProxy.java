package cl.clarochile.osbservicios.ValidarChequeWS;

import java.rmi.RemoteException;

/**
 * Server Proxy para implementacion de Consulta Validacion de Cheque hacia App de Control Caja Claro
 * @author cbriones
 *
 */
public class ValidarChequeWSServerProxy implements ValidarChequeWS_PortType{

	  private String _endpoint = null;
	  private ValidarChequeWS_PortType server = null;
	  
	  public ValidarChequeWSServerProxy() {
	    _initServerProxy();
	  }
	  
	  public ValidarChequeWSServerProxy(String endpoint) {
	    _endpoint = endpoint;
	    _initServerProxy();
	  }
	  
	  private void _initServerProxy() {
	    try {
	      server = (new ValidarChequeWS_ServiceLocator()).getValidarChequeWSSOAP();
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
	  
	  public ValidarChequeWS_PortType getServer() {
	    if (server == null)
	      _initServerProxy();
	    return server;
	  }
	  
	  @Override
	  public Respuesta validarCheque(OperacionIn operacionIn, Caja caja) throws RemoteException {
		 // TODO Auto-generated method stub
		  if (server == null)
		      _initServerProxy();
		 return server.validarCheque(operacionIn, caja);
	  }

}
