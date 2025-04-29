package cl.clarochile.osbservicios.ValidarChequeWS;

public class ValidarChequeWSProxy implements cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_PortType {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_PortType validarChequeWS_PortType = null;
  
  public ValidarChequeWSProxy() {
    _initValidarChequeWSProxy();
  }
  
  public ValidarChequeWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initValidarChequeWSProxy();
  }
  
  private void _initValidarChequeWSProxy() {
    try {
      validarChequeWS_PortType = (new cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_ServiceLocator()).getValidarChequeWSSOAP();
      if (validarChequeWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)validarChequeWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)validarChequeWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (validarChequeWS_PortType != null)
      ((javax.xml.rpc.Stub)validarChequeWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWS_PortType getValidarChequeWS_PortType() {
    if (validarChequeWS_PortType == null)
      _initValidarChequeWSProxy();
    return validarChequeWS_PortType;
  }
  
  public cl.clarochile.osbservicios.ValidarChequeWS.Respuesta validarCheque(cl.clarochile.osbservicios.ValidarChequeWS.OperacionIn operacionIn, cl.clarochile.osbservicios.ValidarChequeWS.Caja caja) throws java.rmi.RemoteException{
    if (validarChequeWS_PortType == null)
      _initValidarChequeWSProxy();
    return validarChequeWS_PortType.validarCheque(operacionIn, caja);
  }
  
  
}