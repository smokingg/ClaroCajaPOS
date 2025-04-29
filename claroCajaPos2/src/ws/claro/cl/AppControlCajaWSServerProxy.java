package ws.claro.cl;

import java.rmi.RemoteException;

/**
 * Server Proxy para implementacion de Operaciones de Control con Servidor de Caja.
 * @author cbriones
 *
 */
public class AppControlCajaWSServerProxy implements ws.claro.cl.AppControlCajaWS {
	
  private String _endpoint = null;
  private ws.claro.cl.AppControlCajaWS server = null;
  
  public AppControlCajaWSServerProxy() {
    _initServerProxy();
  }
  
  public AppControlCajaWSServerProxy(String endpoint) {
    _endpoint = endpoint;
    _initServerProxy();
  }
  
  private void _initServerProxy() {
    try {
      server = (new ws.claro.cl.AppControlCajaWSServiceLocator()).getAppControlCajaWSPort();
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
  
  public ws.claro.cl.AppControlCajaWS getServer() {
    if (server == null)
      _initServerProxy();
    return server;
  }
  
  
	@Override
	public InicializarCajaDTO inicializarCaja(ws.claro.cl.HeaderDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.inicializarCaja(request);
	}
	
	@Override
	public AutentificarOutDTO autentificar(AutentificarInDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.autentificar(request);
	}
	
	@Override
	public AperturaCajaOutDTO aperturaCaja(AperturaCajaInDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.aperturaCaja(request);
	}
	
	@Override
	public PingOutDTO pingCaja(HeaderDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.pingCaja(request);
	}

	@Override
	public NumeroOperacionOutDTO numeroOperacion(HeaderDTO request)	throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.numeroOperacion(request);
	}

	@Override
	public CierreCajaOutDTO cierreCaja(HeaderDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.cierreCaja(request);
	}

	@Override
	public ListaRecaudadoresOutDTO listaRecaudadores(HeaderDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.listaRecaudadores(request);
	}

	@Override
	public DesconectarCajaOutDTO desconectarCaja(HeaderDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.desconectarCaja(request);
	}

	@Override
	public RespuestaLoginTO login(String user, String pass) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.login(user, pass);
	}

	@Override
	public ConsultarOperacionOutDTO consultarOperacion(ConsultarOperacionInDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.consultarOperacion(request);
	}

	@Override
	public ConsultarDevolucionOutDTO consultarDevolucion(ConsultarDevolucionInDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.consultarDevolucion(request);
	}

	@Override
	public ConsultarDenominacionOutDTO consultarDenominacion(ConsultarDenominacionInDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.consultarDenominacion(request);
	}

	@Override
	public ListaBancosCtasOutDTO listaBancosCtas(HeaderDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.listaBancosCtas(request);
	}

	@Override
	public ConsultarAprobacionChOutDTO consultarAprobacionCheque(ConsultarAprobacionChInDTO request) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.consultarAprobacionCheque(request);
	}

	@Override
	public ConsultarBancosOutDTO consultarBancos(HeaderDTO request) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.consultarBancos(request);
	}

	@Override
	public ConsultarTarjetasMTOutDTO listaTarjetasMultitienda(HeaderDTO request) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.listaTarjetasMultitienda(request);
	}

	@Override
	public RespuestaLoginTO updateUserPassword(String user, String pass,
			String newPass) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.updateUserPassword(user, pass, newPass);
	}

	@Override
	public RegistrarSencilloCajeroResponseDTO registrarSencilloCajero(
			RegistrarSencilloCajeroRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.registrarSencilloCajero(request);
	}

	@Override
	public ValidarSencilloCajeroResponseDTO validarSencilloCajero(
			ValidarSencilloCajeroRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.validarSencilloCajero(request);
	}

	@Override
	public RegistrarSencilloCierreResponseDTO registrarSencilloCierre(
			RegistrarSencilloCierreRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.registrarSencilloCierre(request);
	}

	@Override
	public MotivoJustificacionResponseDTO getMotivoJustificacion()
			throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.getMotivoJustificacion();
	}

	@Override
	public RegistrarJustificacionResponseDTO registrarJustificacionCierre(
			RegistrarJustificacionRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.registrarJustificacionCierre(request);
	}

	@Override
	public ValidarJustificacionResponseDTO validarJustificacionCierre(
			ValidarJustificacionRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.validarJustificacionCierre(request);
	}

	@Override
	public UltimaSesionResponseDTO ultimaSesion(UltimaSesionRequestDTO request)
			throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.ultimaSesion(request);
	}

	@Override
	public MedioPagoResponseDTO getMediosPago() throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.getMediosPago();
	}

	@Override
	public CierreCajaOutDTO cierreCajaFecha(HeaderDTO request,
			String fechaCierre) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.cierreCajaFecha(request, fechaCierre);
	}

	@Override
	public ObtenerDetalleCierreResponseDTO obtenerDetalleCierre(
			ObtenerDetalleCierreRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.obtenerDetalleCierre(request);
	}
  
  	public OperacionTbkResponseDTO consultarOperTbkRut(
			ConsultarOperTbkInDTO request) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.consultarOperTbkRut(request);
	}

	@Override
	public AnuncioMarketingResponseDTO obtenerAnunciosMarketing(String[] request)
			throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.obtenerAnunciosMarketing(request);
	}

	@Override
	public AprobacionDescuentoResponseDTO obtenerAprobacionDescuento(
			AprobacionDescuentoRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.obtenerAprobacionDescuento(request);
	}

	@Override
	public ValidacionChequesProtestadosResponseDTO obtenerValidacionChequesProtestados(
			ValidacionChequesProtestadosRequestDTO request) throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.obtenerValidacionChequesProtestados(request);
	}

	@Override
	public GuardarVoucherTbkResponseDTO guardarVoucherTbk(GuardarVoucherTbkRequestDTO request) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.guardarVoucherTbk(request);
	}

	
	
	
	@Override
	public java.lang.String obtenerEmailsPorRut(java.lang.String[] rut) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.obtenerEmailsPorRut(rut);
	}

	@Override
	public ConsultaAdjuntoResponseDTO consultarAdjunto(java.lang.String idAdjunto)
			throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.consultarAdjunto(idAdjunto);
	}

	@Override
	public GuardarVoucherTbkResponseDTO actualizarVoucherTbk(
			ActualizarVoucherTbkRequestDTO voucherTBK) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.actualizarVoucherTbk(voucherTBK);
	}

	@Override
	public VoucherOperacionResponseDTO[] obtenerVoucherOperacion(
			String numeroOperacion) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.obtenerVoucherOperacion(numeroOperacion);
	}

	@Override
	public GenericResponseDTO actualizarEstadoEnvio(String numeroOperacion,
			String estadoEnvio) throws RemoteException {
		// TODO Auto-generated method stub
		if (server == null)
		      _initServerProxy();
		return server.actualizarEstadoEnvio(numeroOperacion,estadoEnvio);
	}

	@Override
	public ObtenerAuthMailResponseDTO obtenerAuthMail(String paramSet)
			throws RemoteException {
		if (server == null)
		      _initServerProxy();
		return server.obtenerAuthMail(paramSet);
	}

	

}