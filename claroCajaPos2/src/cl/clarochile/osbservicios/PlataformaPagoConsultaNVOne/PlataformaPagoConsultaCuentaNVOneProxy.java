package cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne;

public class PlataformaPagoConsultaCuentaNVOneProxy implements cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_PortType {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_PortType plataformaPagoConsultaCuentaNVOne_PortType = null;
  
  public PlataformaPagoConsultaCuentaNVOneProxy() {
    _initPlataformaPagoConsultaCuentaNVOneProxy();
  }
  
  public PlataformaPagoConsultaCuentaNVOneProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoConsultaCuentaNVOneProxy();
  }
  
  private void _initPlataformaPagoConsultaCuentaNVOneProxy() {
    try {
      plataformaPagoConsultaCuentaNVOne_PortType = (new cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_ServiceLocator()).getPlataformaPagoConsultaCuentaNVOneSOAP();
      if (plataformaPagoConsultaCuentaNVOne_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoConsultaCuentaNVOne_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoConsultaCuentaNVOne_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoConsultaCuentaNVOne_PortType != null)
      ((javax.xml.rpc.Stub)plataformaPagoConsultaCuentaNVOne_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_PortType getPlataformaPagoConsultaCuentaNVOne_PortType() {
    if (plataformaPagoConsultaCuentaNVOne_PortType == null)
      _initPlataformaPagoConsultaCuentaNVOneProxy();
    return plataformaPagoConsultaCuentaNVOne_PortType;
  }
  
  public void consultar(cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.OperacionIn operacionIn, cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.holders.OperacionOutArrayHolder operacionOut, cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.holders.RespuestaHolder respuesta) throws java.rmi.RemoteException{
    if (plataformaPagoConsultaCuentaNVOne_PortType == null)
      _initPlataformaPagoConsultaCuentaNVOneProxy();
    plataformaPagoConsultaCuentaNVOne_PortType.consultar(operacionIn, operacionOut, respuesta);
  }
  
  
}