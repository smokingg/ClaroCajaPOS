package cl.clarochile.osbservicios.PlataformaPagoNotificar;

public class PlataformaPagoNotificarProxy implements cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_PortType {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_PortType plataformaPagoNotificar_PortType = null;
  
  public PlataformaPagoNotificarProxy() {
    _initPlataformaPagoNotificarProxy();
  }
  
  public PlataformaPagoNotificarProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoNotificarProxy();
  }
  
  private void _initPlataformaPagoNotificarProxy() {
    try {
      plataformaPagoNotificar_PortType = (new cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_ServiceLocator()).getPlataformaPagoNotificarSOAP();
      if (plataformaPagoNotificar_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoNotificar_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoNotificar_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoNotificar_PortType != null)
      ((javax.xml.rpc.Stub)plataformaPagoNotificar_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificar_PortType getPlataformaPagoNotificar_PortType() {
    if (plataformaPagoNotificar_PortType == null)
      _initPlataformaPagoNotificarProxy();
    return plataformaPagoNotificar_PortType;
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta notificar(cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio notificarRequest) throws java.rmi.RemoteException{
    if (plataformaPagoNotificar_PortType == null)
      _initPlataformaPagoNotificarProxy();
    return plataformaPagoNotificar_PortType.notificar(notificarRequest);
  }
  
  
}