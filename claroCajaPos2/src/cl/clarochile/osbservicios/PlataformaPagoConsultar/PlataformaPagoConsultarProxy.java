package cl.clarochile.osbservicios.PlataformaPagoConsultar;

public class PlataformaPagoConsultarProxy implements cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultar_PortType {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultar_PortType plataformaPagoConsultar_PortType = null;
  
  public PlataformaPagoConsultarProxy() {
    _initPlataformaPagoConsultarProxy();
  }
  
  public PlataformaPagoConsultarProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoConsultarProxy();
  }
  
  private void _initPlataformaPagoConsultarProxy() {
    try {
      plataformaPagoConsultar_PortType = (new cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultar_ServiceLocator()).getPlataformaPagoConsultarSOAP();
      if (plataformaPagoConsultar_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoConsultar_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoConsultar_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoConsultar_PortType != null)
      ((javax.xml.rpc.Stub)plataformaPagoConsultar_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultar_PortType getPlataformaPagoConsultar_PortType() {
    if (plataformaPagoConsultar_PortType == null)
      _initPlataformaPagoConsultarProxy();
    return plataformaPagoConsultar_PortType;
  }
  
  public void consultar(cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn operacionIn, cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.OperacionOutHolder operacionOut, cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.RespuestaHolder respuesta) throws java.rmi.RemoteException{
    if (plataformaPagoConsultar_PortType == null)
      _initPlataformaPagoConsultarProxy();
    plataformaPagoConsultar_PortType.consultar(operacionIn, operacionOut, respuesta);
  }
  
  
}