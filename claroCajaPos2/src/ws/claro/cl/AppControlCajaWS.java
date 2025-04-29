/**
 * AppControlCajaWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.claro.cl;

public interface AppControlCajaWS extends java.rmi.Remote {
    public ws.claro.cl.RespuestaLoginTO login(java.lang.String user, java.lang.String pass) throws java.rmi.RemoteException;
    public ws.claro.cl.InicializarCajaDTO inicializarCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.AutentificarOutDTO autentificar(ws.claro.cl.AutentificarInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.AperturaCajaOutDTO aperturaCaja(ws.claro.cl.AperturaCajaInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.PingOutDTO pingCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.CierreCajaOutDTO cierreCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.NumeroOperacionOutDTO numeroOperacion(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ListaRecaudadoresOutDTO listaRecaudadores(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.DesconectarCajaOutDTO desconectarCaja(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.RespuestaLoginTO updateUserPassword(java.lang.String user, java.lang.String pass, java.lang.String newPass) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultarOperacionOutDTO consultarOperacion(ws.claro.cl.ConsultarOperacionInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultarDevolucionOutDTO consultarDevolucion(ws.claro.cl.ConsultarDevolucionInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultarDenominacionOutDTO consultarDenominacion(ws.claro.cl.ConsultarDenominacionInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultarBancosOutDTO consultarBancos(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ListaBancosCtasOutDTO listaBancosCtas(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultarAprobacionChOutDTO consultarAprobacionCheque(ws.claro.cl.ConsultarAprobacionChInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultarTarjetasMTOutDTO listaTarjetasMultitienda(ws.claro.cl.HeaderDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.OperacionTbkResponseDTO consultarOperTbkRut(ws.claro.cl.ConsultarOperTbkInDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.RegistrarSencilloCajeroResponseDTO registrarSencilloCajero(ws.claro.cl.RegistrarSencilloCajeroRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ValidarSencilloCajeroResponseDTO validarSencilloCajero(ws.claro.cl.ValidarSencilloCajeroRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.RegistrarSencilloCierreResponseDTO registrarSencilloCierre(ws.claro.cl.RegistrarSencilloCierreRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.MotivoJustificacionResponseDTO getMotivoJustificacion() throws java.rmi.RemoteException;
    public ws.claro.cl.RegistrarJustificacionResponseDTO registrarJustificacionCierre(ws.claro.cl.RegistrarJustificacionRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ValidarJustificacionResponseDTO validarJustificacionCierre(ws.claro.cl.ValidarJustificacionRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.UltimaSesionResponseDTO ultimaSesion(ws.claro.cl.UltimaSesionRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.MedioPagoResponseDTO getMediosPago() throws java.rmi.RemoteException;
    public ws.claro.cl.CierreCajaOutDTO cierreCajaFecha(ws.claro.cl.HeaderDTO request, java.lang.String fechaCierre) throws java.rmi.RemoteException;
    public ws.claro.cl.ObtenerDetalleCierreResponseDTO obtenerDetalleCierre(ws.claro.cl.ObtenerDetalleCierreRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.AnuncioMarketingResponseDTO obtenerAnunciosMarketing(java.lang.String[] request) throws java.rmi.RemoteException;
    public ws.claro.cl.AprobacionDescuentoResponseDTO obtenerAprobacionDescuento(ws.claro.cl.AprobacionDescuentoRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.ValidacionChequesProtestadosResponseDTO obtenerValidacionChequesProtestados(ws.claro.cl.ValidacionChequesProtestadosRequestDTO request) throws java.rmi.RemoteException;
    public ws.claro.cl.GuardarVoucherTbkResponseDTO guardarVoucherTbk(ws.claro.cl.GuardarVoucherTbkRequestDTO voucherTBK) throws java.rmi.RemoteException;
    public ws.claro.cl.GuardarVoucherTbkResponseDTO actualizarVoucherTbk(ws.claro.cl.ActualizarVoucherTbkRequestDTO voucherTBK) throws java.rmi.RemoteException;
    public ws.claro.cl.VoucherOperacionResponseDTO[] obtenerVoucherOperacion(java.lang.String numeroOperacion) throws java.rmi.RemoteException;
    public java.lang.String obtenerEmailsPorRut(java.lang.String[] rut) throws java.rmi.RemoteException;
    public ws.claro.cl.ConsultaAdjuntoResponseDTO consultarAdjunto(java.lang.String idAdjunto) throws java.rmi.RemoteException;
    public ws.claro.cl.GenericResponseDTO actualizarEstadoEnvio(java.lang.String numeroOperacion, java.lang.String estadoEnvio) throws java.rmi.RemoteException;
    public ws.claro.cl.ObtenerAuthMailResponseDTO obtenerAuthMail(java.lang.String paramSet) throws java.rmi.RemoteException;
}
