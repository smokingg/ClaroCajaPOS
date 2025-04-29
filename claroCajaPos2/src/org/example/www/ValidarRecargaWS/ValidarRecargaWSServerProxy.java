package org.example.www.ValidarRecargaWS;

import java.rmi.RemoteException;

/**
 * Server Proxy para implementacion de Consulta Recarga Charging System hacia App de Control Caja Claro
 * @author cbriones
 *
 */
public class ValidarRecargaWSServerProxy implements ValidarRecargaWS_PortType{

	  private String _endpoint = null;
	  private ValidarRecargaWS_PortType server = null;
	  
	  public ValidarRecargaWSServerProxy() {
	    _initServerProxy();
	  }
	  
	  public ValidarRecargaWSServerProxy(String endpoint) {
	    _endpoint = endpoint;
	    _initServerProxy();
	  }
	  
	  private void _initServerProxy() {
	    try {
	      server = (new ValidarRecargaWS_ServiceLocator()).getValidarRecargaWSSOAP();
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
	  
	  public ValidarRecargaWS_PortType getServer() {
	    if (server == null)
	      _initServerProxy();
	    return server;
	  }

	@Override
	public Respuesta validarRecarga(OperacionIn operacionIn, Caja caja)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		 return server.validarRecarga(operacionIn, caja);
	}

}
