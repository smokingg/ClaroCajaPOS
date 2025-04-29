package ws.claro.cl;

public class AppControlCajaWSProxy implements ws.claro.cl.AppControlCajaWS {
  private String _endpoint = null;
  private ws.claro.cl.AppControlCajaWS appControlCajaWS = null;
  
  public AppControlCajaWSProxy() {
    _initAppControlCajaWSProxy();
  }
  
  public AppControlCajaWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initAppControlCajaWSProxy();
  }
  
  private void _initAppControlCajaWSProxy() {
    try {
      appControlCajaWS = (new ws.claro.cl.AppControlCajaWSServiceLocator()).getAppControlCajaWSPort();
      if (appControlCajaWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)appControlCajaWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)appControlCajaWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (appControlCajaWS != null)
      ((javax.xml.rpc.Stub)appControlCajaWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ws.claro.cl.AppControlCajaWS getAppControlCajaWS() {
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS;
  }
  
  public ws.claro.cl.RespuestaLoginTO login(java.lang.String user, java.lang.String pass) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.login(user, pass);
  }
  
  public ws.claro.cl.InicializarCajaDTO inicializarCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.inicializarCaja(request);
  }
  
  public ws.claro.cl.AutentificarOutDTO autentificar(ws.claro.cl.AutentificarInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.autentificar(request);
  }
  
  public ws.claro.cl.AperturaCajaOutDTO aperturaCaja(ws.claro.cl.AperturaCajaInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.aperturaCaja(request);
  }
  
  public ws.claro.cl.PingOutDTO pingCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.pingCaja(request);
  }
  
  public ws.claro.cl.CierreCajaOutDTO cierreCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.cierreCaja(request);
  }
  
  public ws.claro.cl.NumeroOperacionOutDTO numeroOperacion(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.numeroOperacion(request);
  }
  
  public ws.claro.cl.ListaRecaudadoresOutDTO listaRecaudadores(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.listaRecaudadores(request);
  }
  
  public ws.claro.cl.DesconectarCajaOutDTO desconectarCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.desconectarCaja(request);
  }
  
  public ws.claro.cl.RespuestaLoginTO updateUserPassword(java.lang.String user, java.lang.String pass, java.lang.String newPass) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.updateUserPassword(user, pass, newPass);
  }
  
  public ws.claro.cl.ConsultarOperacionOutDTO consultarOperacion(ws.claro.cl.ConsultarOperacionInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarOperacion(request);
  }
  
  public ws.claro.cl.ConsultarDevolucionOutDTO consultarDevolucion(ws.claro.cl.ConsultarDevolucionInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarDevolucion(request);
  }
  
  public ws.claro.cl.ConsultarDenominacionOutDTO consultarDenominacion(ws.claro.cl.ConsultarDenominacionInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarDenominacion(request);
  }
  
  public ws.claro.cl.ConsultarBancosOutDTO consultarBancos(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarBancos(request);
  }
  
  public ws.claro.cl.ListaBancosCtasOutDTO listaBancosCtas(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.listaBancosCtas(request);
  }
  
  public ws.claro.cl.ConsultarAprobacionChOutDTO consultarAprobacionCheque(ws.claro.cl.ConsultarAprobacionChInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarAprobacionCheque(request);
  }
  
  public ws.claro.cl.ConsultarTarjetasMTOutDTO listaTarjetasMultitienda(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.listaTarjetasMultitienda(request);
  }
  
  public ws.claro.cl.OperacionTbkResponseDTO consultarOperTbkRut(ws.claro.cl.ConsultarOperTbkInDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarOperTbkRut(request);
  }
  
  public ws.claro.cl.RegistrarSencilloCajeroResponseDTO registrarSencilloCajero(ws.claro.cl.RegistrarSencilloCajeroRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.registrarSencilloCajero(request);
  }
  
  public ws.claro.cl.ValidarSencilloCajeroResponseDTO validarSencilloCajero(ws.claro.cl.ValidarSencilloCajeroRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.validarSencilloCajero(request);
  }
  
  public ws.claro.cl.RegistrarSencilloCierreResponseDTO registrarSencilloCierre(ws.claro.cl.RegistrarSencilloCierreRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.registrarSencilloCierre(request);
  }
  
  public ws.claro.cl.MotivoJustificacionResponseDTO getMotivoJustificacion() throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.getMotivoJustificacion();
  }
  
  public ws.claro.cl.RegistrarJustificacionResponseDTO registrarJustificacionCierre(ws.claro.cl.RegistrarJustificacionRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.registrarJustificacionCierre(request);
  }
  
  public ws.claro.cl.ValidarJustificacionResponseDTO validarJustificacionCierre(ws.claro.cl.ValidarJustificacionRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.validarJustificacionCierre(request);
  }
  
  public ws.claro.cl.UltimaSesionResponseDTO ultimaSesion(ws.claro.cl.UltimaSesionRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.ultimaSesion(request);
  }
  
  public ws.claro.cl.MedioPagoResponseDTO getMediosPago() throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.getMediosPago();
  }
  
  public ws.claro.cl.CierreCajaOutDTO cierreCajaFecha(ws.claro.cl.HeaderDTO request, java.lang.String fechaCierre) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.cierreCajaFecha(request, fechaCierre);
  }
  
  public ws.claro.cl.ObtenerDetalleCierreResponseDTO obtenerDetalleCierre(ws.claro.cl.ObtenerDetalleCierreRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerDetalleCierre(request);
  }
  
  public ws.claro.cl.AnuncioMarketingResponseDTO obtenerAnunciosMarketing(java.lang.String[] request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerAnunciosMarketing(request);
  }
  
  public ws.claro.cl.AprobacionDescuentoResponseDTO obtenerAprobacionDescuento(ws.claro.cl.AprobacionDescuentoRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerAprobacionDescuento(request);
  }
  
  public ws.claro.cl.ValidacionChequesProtestadosResponseDTO obtenerValidacionChequesProtestados(ws.claro.cl.ValidacionChequesProtestadosRequestDTO request) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerValidacionChequesProtestados(request);
  }
  
  public ws.claro.cl.GuardarVoucherTbkResponseDTO guardarVoucherTbk(ws.claro.cl.GuardarVoucherTbkRequestDTO voucherTBK) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.guardarVoucherTbk(voucherTBK);
  }
  
  public ws.claro.cl.GuardarVoucherTbkResponseDTO actualizarVoucherTbk(ws.claro.cl.ActualizarVoucherTbkRequestDTO voucherTBK) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.actualizarVoucherTbk(voucherTBK);
  }
  
  public ws.claro.cl.VoucherOperacionResponseDTO[] obtenerVoucherOperacion(java.lang.String numeroOperacion) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerVoucherOperacion(numeroOperacion);
  }
  
  public java.lang.String obtenerEmailsPorRut(java.lang.String[] rut) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerEmailsPorRut(rut);
  }
  
  public ws.claro.cl.ConsultaAdjuntoResponseDTO consultarAdjunto(java.lang.String idAdjunto) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.consultarAdjunto(idAdjunto);
  }
  
  public ws.claro.cl.GenericResponseDTO actualizarEstadoEnvio(java.lang.String numeroOperacion, java.lang.String estadoEnvio) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.actualizarEstadoEnvio(numeroOperacion, estadoEnvio);
  }
  
  public ws.claro.cl.ObtenerAuthMailResponseDTO obtenerAuthMail(java.lang.String paramSet) throws java.rmi.RemoteException{
    if (appControlCajaWS == null)
      _initAppControlCajaWSProxy();
    return appControlCajaWS.obtenerAuthMail(paramSet);
  }
  
  
}