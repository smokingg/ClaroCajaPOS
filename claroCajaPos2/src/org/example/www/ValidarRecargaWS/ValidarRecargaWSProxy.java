package org.example.www.ValidarRecargaWS;

public class ValidarRecargaWSProxy implements org.example.www.ValidarRecargaWS.ValidarRecargaWS_PortType {
  private String _endpoint = null;
  private org.example.www.ValidarRecargaWS.ValidarRecargaWS_PortType validarRecargaWS_PortType = null;
  
  public ValidarRecargaWSProxy() {
    _initValidarRecargaWSProxy();
  }
  
  public ValidarRecargaWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initValidarRecargaWSProxy();
  }
  
  private void _initValidarRecargaWSProxy() {
    try {
      validarRecargaWS_PortType = (new org.example.www.ValidarRecargaWS.ValidarRecargaWS_ServiceLocator()).getValidarRecargaWSSOAP();
      if (validarRecargaWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)validarRecargaWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)validarRecargaWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (validarRecargaWS_PortType != null)
      ((javax.xml.rpc.Stub)validarRecargaWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.example.www.ValidarRecargaWS.ValidarRecargaWS_PortType getValidarRecargaWS_PortType() {
    if (validarRecargaWS_PortType == null)
      _initValidarRecargaWSProxy();
    return validarRecargaWS_PortType;
  }
  
  public org.example.www.ValidarRecargaWS.Respuesta validarRecarga(org.example.www.ValidarRecargaWS.OperacionIn operacionIn, org.example.www.ValidarRecargaWS.Caja caja) throws java.rmi.RemoteException{
    if (validarRecargaWS_PortType == null)
      _initValidarRecargaWSProxy();
    return validarRecargaWS_PortType.validarRecarga(operacionIn, caja);
  }
  
  
}