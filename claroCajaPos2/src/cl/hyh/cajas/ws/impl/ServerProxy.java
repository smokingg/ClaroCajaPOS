package cl.hyh.cajas.ws.impl;

public class ServerProxy implements cl.hyh.cajas.ws.impl.Server {
  private String _endpoint = null;
  private cl.hyh.cajas.ws.impl.Server server = null;
  
  public ServerProxy() {
    _initServerProxy();
  }
  
  public ServerProxy(String endpoint) {
    _endpoint = endpoint;
    _initServerProxy();
  }
  
  private void _initServerProxy() {
    try {
      server = (new cl.hyh.cajas.ws.impl.ServerServiceLocator()).getServerPort();
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
  
  public cl.hyh.cajas.ws.impl.Server getServer() {
    if (server == null)
      _initServerProxy();
    return server;
  }
  
  public cl.hyh.cajas.ws.impl.Response disconnect(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.disconnect(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.NumeroOperacionOut numeroOperacion(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.numeroOperacion(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.Response autorizaDevolucion(cl.hyh.cajas.ws.impl.AutorizaDevolucionIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.autorizaDevolucion(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.AperturaCajaOut aperturaCaja(cl.hyh.cajas.ws.impl.AperturaCajaIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.aperturaCaja(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.AutentificaOut autentifica(cl.hyh.cajas.ws.impl.AutentificaIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.autentifica(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.Response envioOperacion(cl.hyh.cajas.ws.impl.OperacionIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.envioOperacion(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.Response remesa(cl.hyh.cajas.ws.impl.RemesaIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.remesa(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.ConsultaSaldoFavorVtrOut consultaSaldoFavorVtr(cl.hyh.cajas.ws.impl.ConsultaSaldoFavorVtrIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.consultaSaldoFavorVtr(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.ConsultaCuentasVtrOut consultaCuentasVtr(cl.hyh.cajas.ws.impl.ConsultaCuentasVtrIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.consultaCuentasVtr(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.ConsultaDeudaVtrOut consultaDeudaVtr(cl.hyh.cajas.ws.impl.ConsultaDeudaRutVTRIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.consultaDeudaVtr(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.Response envioPago(cl.hyh.cajas.ws.impl.EnvioPagoIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.envioPago(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.Response envioReversa(cl.hyh.cajas.ws.impl.EnvioReversaIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.envioReversa(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.ListaRecaudadoresOut listaRecaudadores(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.listaRecaudadores(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.Response cambioPassword(cl.hyh.cajas.ws.impl.CambioPasswordIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.cambioPassword(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.PingOut ping(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.ping(arg0);
  }
  
  public void testConnection() throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    server.testConnection();
  }
  
  public cl.hyh.cajas.ws.impl.RecargaOut recarga(cl.hyh.cajas.ws.impl.RecargaIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.recarga(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.AutorizaChequeOut autorizaCheque(cl.hyh.cajas.ws.impl.AutorizaChequeIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.autorizaCheque(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.InicializaCajaOut inicializaCaja(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.inicializaCaja(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.LoginOut login(cl.hyh.cajas.ws.impl.LoginIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.login(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.CierreDiarioOut cierre(cl.hyh.cajas.ws.impl.Request arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.cierre(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.CierreDiarioOut consultaCierreDiario(cl.hyh.cajas.ws.impl.ConsultaCierreDiarioIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.consultaCierreDiario(arg0);
  }
  
  public cl.hyh.cajas.ws.impl.ConsultaOperacionOut consultaOperacion(cl.hyh.cajas.ws.impl.ConsultaOperacionIn arg0) throws java.rmi.RemoteException{
    if (server == null)
      _initServerProxy();
    return server.consultaOperacion(arg0);
  }
  
  
}