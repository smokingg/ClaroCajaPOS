package cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento;

import org.apache.axis.client.Stub;

public class PlataformaPagoEnviarAlmacenarDocumentoProxy implements cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento plataformaPagoEnviarAlmacenarDocumento = null;
  
  public PlataformaPagoEnviarAlmacenarDocumentoProxy() {
    _initPlataformaPagoEnviarAlmacenarDocumentoProxy();
  }
  
  public PlataformaPagoEnviarAlmacenarDocumentoProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoEnviarAlmacenarDocumentoProxy();
  }
  
  private void _initPlataformaPagoEnviarAlmacenarDocumentoProxy() {
    try {
      plataformaPagoEnviarAlmacenarDocumento = (new cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoSOAPQSServiceLocator()).getPlataformaPagoEnviarAlmacenarDocumentoSOAPQSPort();
      if (plataformaPagoEnviarAlmacenarDocumento != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoEnviarAlmacenarDocumento)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoEnviarAlmacenarDocumento)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoEnviarAlmacenarDocumento != null)
      ((javax.xml.rpc.Stub)plataformaPagoEnviarAlmacenarDocumento)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumento getPlataformaPagoEnviarAlmacenarDocumento() {
    if (plataformaPagoEnviarAlmacenarDocumento == null)
      _initPlataformaPagoEnviarAlmacenarDocumentoProxy();
    return plataformaPagoEnviarAlmacenarDocumento;
  }
  
  public void almacenarDocumento(java.lang.String numeroOperacion, javax.xml.rpc.holders.StringHolder status, javax.xml.rpc.holders.StringHolder errorCode) throws java.rmi.RemoteException{
    if (plataformaPagoEnviarAlmacenarDocumento == null)
      _initPlataformaPagoEnviarAlmacenarDocumentoProxy();
    plataformaPagoEnviarAlmacenarDocumento.almacenarDocumento(numeroOperacion, status, errorCode);
  }

public PlataformaPagoEnviarAlmacenarDocumento getServer() {
	 if (plataformaPagoEnviarAlmacenarDocumento == null)
		 _initPlataformaPagoEnviarAlmacenarDocumentoProxy();
	    return  plataformaPagoEnviarAlmacenarDocumento;
}
  
  
}