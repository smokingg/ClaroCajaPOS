package cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne;

public class PlataformaPagoConsultarItemsOneProxy implements cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_PortType {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_PortType plataformaPagoConsultarItemsOne_PortType = null;
  
  public PlataformaPagoConsultarItemsOneProxy() {
    _initPlataformaPagoConsultarItemsOneProxy();
  }
  
  public PlataformaPagoConsultarItemsOneProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoConsultarItemsOneProxy();
  }
  
  private void _initPlataformaPagoConsultarItemsOneProxy() {
    try {
      plataformaPagoConsultarItemsOne_PortType = (new cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_ServiceLocator()).getPlataformaPagoConsultarSOAP();
      if (plataformaPagoConsultarItemsOne_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoConsultarItemsOne_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoConsultarItemsOne_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoConsultarItemsOne_PortType != null)
      ((javax.xml.rpc.Stub)plataformaPagoConsultarItemsOne_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOne_PortType getPlataformaPagoConsultarItemsOne_PortType() {
    if (plataformaPagoConsultarItemsOne_PortType == null)
      _initPlataformaPagoConsultarItemsOneProxy();
    return plataformaPagoConsultarItemsOne_PortType;
  }
  
  public void consultar(cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.OperacionIn operacionIn, cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.holders.OperacionOutHolder operacionOut, cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.holders.RespuestaHolder respuesta) throws java.rmi.RemoteException{
    if (plataformaPagoConsultarItemsOne_PortType == null)
      _initPlataformaPagoConsultarItemsOneProxy();
    plataformaPagoConsultarItemsOne_PortType.consultar(operacionIn, operacionOut, respuesta);
  }
  
  
}