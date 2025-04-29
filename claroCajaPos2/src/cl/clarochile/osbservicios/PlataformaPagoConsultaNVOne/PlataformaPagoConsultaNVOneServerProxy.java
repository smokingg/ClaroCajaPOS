package cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne;


/**
 * Server Proxy para implementacion de Consulta Nota de Venta ONE hacia App de Control Caja Claro
 * @author Cbriones
 * 
 * Clase clonada para Consulta Nota de Venta ONE REspinoza
 *
 */
public class PlataformaPagoConsultaNVOneServerProxy implements PlataformaPagoConsultaCuentaNVOne_PortType{
	  private String _endpoint = null;
	  private cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_PortType server = null;
	  
	  public PlataformaPagoConsultaNVOneServerProxy() {
	    _initPlataformaPagoConsultaCuentaNVOneProxy();
	  }
	  
	  public PlataformaPagoConsultaNVOneServerProxy(String endpoint) {
	    _endpoint = endpoint;
	    _initPlataformaPagoConsultaCuentaNVOneProxy();
	  }
	  
	  private void _initPlataformaPagoConsultaCuentaNVOneProxy() {
	    try {
	      server = (new cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_ServiceLocator()).getPlataformaPagoConsultaCuentaNVOneSOAP();
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
	  
	  public cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaCuentaNVOne_PortType getServer() {
	    if (server == null)
	      _initPlataformaPagoConsultaCuentaNVOneProxy();
	    return server;
	  }
	  
	  @Override
	  public void consultar(cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.OperacionIn operacionIn, cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.holders.OperacionOutArrayHolder operacionOut, cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.holders.RespuestaHolder respuesta) throws java.rmi.RemoteException{
	    if (server == null)
	      _initPlataformaPagoConsultaCuentaNVOneProxy();
	    server.consultar(operacionIn, operacionOut, respuesta);
	  }
	  
}
