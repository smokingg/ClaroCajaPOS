package cl.clarochile.osbservicios.PlataformaPagoConsultaSAF;

public class PlataformaPagoconsultaDevolucionSAFProxy implements cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF {
  private String _endpoint = null;
  private cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF plataformaPagoconsultaDevolucionSAF = null;
  
  public PlataformaPagoconsultaDevolucionSAFProxy() {
    _initPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImplProxy();
  }
  
  public PlataformaPagoconsultaDevolucionSAFProxy(String endpoint) {
    _endpoint = endpoint;
    _initPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImplProxy();
  }
  
  private void _initPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImplProxy() {
    try {
      plataformaPagoconsultaDevolucionSAF = (new cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaDevolucionSAFServiceLocator()).getConsultaDevolucionSAFPort();
      if (plataformaPagoconsultaDevolucionSAF != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)plataformaPagoconsultaDevolucionSAF)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)plataformaPagoconsultaDevolucionSAF)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (plataformaPagoconsultaDevolucionSAF != null)
      ((javax.xml.rpc.Stub)plataformaPagoconsultaDevolucionSAF)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF getServer() {
	    if (plataformaPagoconsultaDevolucionSAF == null)
	    	_initPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImplProxy();
	    return plataformaPagoconsultaDevolucionSAF;
	  }
  
  public cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAF getPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImpl() {
    if (plataformaPagoconsultaDevolucionSAF == null)
      _initPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImplProxy();
    return plataformaPagoconsultaDevolucionSAF;
  }
  
  public void consultarSaldoFavor(cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaSaldoFavorPeticionType arg0, cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.HeaderRequest arg1, cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaSaldoFavorRespuestaTypeHolder arg2, cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.DatosHeaderResponseHolder arg3) throws java.rmi.RemoteException{
    if (plataformaPagoconsultaDevolucionSAF == null)
      _initPlataformaPagoconsultaDevolucionSAF_PlataformaPagoconsultaDevolucionSAFSOAPImplProxy();
    plataformaPagoconsultaDevolucionSAF.consultarSaldoFavor(arg0, arg1, arg2, arg3);
  }
  
  
}