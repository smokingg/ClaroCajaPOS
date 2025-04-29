package cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS;

import org.apache.axis.client.Stub;

import cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento;

public class PlataformaPagoEnviarCorreoSilverpopWSProxy implements cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWS {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWS plataformaPagoEnviarCorreoSilverpopWS = null;
  
  public PlataformaPagoEnviarCorreoSilverpopWSProxy() {
    _initPlataformaPagoEnviarCorreoSilverpopWSProxy();
  }
  
  public PlataformaPagoEnviarCorreoSilverpopWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoEnviarCorreoSilverpopWSProxy();
  }
  
  private void _initPlataformaPagoEnviarCorreoSilverpopWSProxy() {
    try {
      plataformaPagoEnviarCorreoSilverpopWS = (new cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSSOAPQSServiceLocator()).getPlataformaPagoEnviarCorreoSilverpopWSSOAPQSPort();
      if (plataformaPagoEnviarCorreoSilverpopWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoEnviarCorreoSilverpopWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoEnviarCorreoSilverpopWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoEnviarCorreoSilverpopWS != null)
      ((javax.xml.rpc.Stub)plataformaPagoEnviarCorreoSilverpopWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWS getPlataformaPagoEnviarCorreoSilverpopWS() {
    if (plataformaPagoEnviarCorreoSilverpopWS == null)
      _initPlataformaPagoEnviarCorreoSilverpopWSProxy();
    return plataformaPagoEnviarCorreoSilverpopWS;
  }
  
  public void enviarCorreo(java.lang.String idOperacion, java.lang.String mails, javax.xml.rpc.holders.StringHolder status, javax.xml.rpc.holders.StringHolder errorCode) throws java.rmi.RemoteException{
    if (plataformaPagoEnviarCorreoSilverpopWS == null)
      _initPlataformaPagoEnviarCorreoSilverpopWSProxy();
    plataformaPagoEnviarCorreoSilverpopWS.enviarCorreo(idOperacion, mails, status, errorCode);
  }


  
public PlataformaPagoEnviarCorreoSilverpopWS getServer() {
	 if (plataformaPagoEnviarCorreoSilverpopWS == null)
		 _initPlataformaPagoEnviarCorreoSilverpopWSProxy();
	    return  plataformaPagoEnviarCorreoSilverpopWS;
}
  
}