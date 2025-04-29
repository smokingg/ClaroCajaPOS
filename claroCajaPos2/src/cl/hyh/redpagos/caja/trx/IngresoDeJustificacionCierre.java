package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.JustificacionCierreDTO;
import ws.claro.cl.JustificacionOperacionDTO;
import ws.claro.cl.MedioPagoResponseDTO;
import ws.claro.cl.MotivoJustificacionDTO;
import ws.claro.cl.MotivoJustificacionResponseDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.RegistrarJustificacionRequestDTO;
import ws.claro.cl.RegistrarJustificacionResponseDTO;
import ws.claro.cl.TipoTransaccionDTO;
import ws.claro.cl.ValidarJustificacionRequestDTO;
import ws.claro.cl.ValidarJustificacionResponseDTO;
import ws.claro.cl.proxy.AppControlNotificarProxy;
import ws.claro.cl.proxy.AppControlProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * 
 * @author rfuentes
 *
 */
public class IngresoDeJustificacionCierre implements ITrxBase {
	private int estado = 0;
	private int index = 0;
	private int indexEstado = 0;
	private int indexMedioPago = 0;
	private MotivoJustificacionDTO[] motivosJustificaciones;
	private TipoTransaccionDTO[] tiposTransacciones;
	private String[] justificaciones;
	private String[] estadoJustificacion;
	private String[] mediosDePago;
	private String fechaStr;
	private String fechaStrF2;
	private Boolean isMotivoSinJustificacion;

	// variables solictidadas
	private String rut = "";
	private String cuentaCliente = "";
	private String numeroDocumento = "";
	private String montoJustificado = "";
	private String observacion = "";
	public static int RC_REINTENTAR = -1;
	public static int RC_CANCELAR = -3;
	public static int RC_NUEVA = -2;
	public static int RC_REVERSAR = -4;
	public static int RC_OK = 0;
	public static int RC_JOURNAL = -5;
	public static int RC_REENVIAR = -6;
	public static int RC_ERRORCONSULTA = -7;

	@Override
	public int execute(ICajaView vista, int key, Datos htParam) {
		if (key == 0) {
			estado = 0;
			// vista.setInputTimeout(15);
		} else if (key == 1) {
			// Timeout
			return 13;
		} else if (key == KeyEvent.VK_ENTER) {
		} else {
			// otra tecla. Lo que sea que esté en el XML...
			return ICajaView._PASSTHROUGH;
		}

		switch (estado) {
		case 0:
			vista.setEntryTextLabel("Seleccione Justificacion:", true);
			vista.setEntryList(justificaciones, true, false, index);
			estado = 1;
			return ICajaView._WAITFORACTION;
		case 1:
			index = vista.getEntryListIndex();
			// Validar que el motivo sea sin justificacion

			if (motivosJustificaciones[index].getDescMotivoJustificacion()
					.toUpperCase().trim().equals("SIN JUSTIFICACION")) {
				isMotivoSinJustificacion = true;
				estado = 6;
			} else {
				isMotivoSinJustificacion = false;
				estado = 2;
			}
			break;
		case 2:
			vista.setEntryTextLabel("Ingrese Rut:", true);
			vista.setEntryMessage("Ingrese Rut (Formato: 99999999-X)", true);
			vista.setEntryText("", true, false, false, null, null);
			estado = 3;
			return ICajaView._WAITFORACTION;
		case 3:
			rut = vista.getEntryText();

			if ("".equals(rut.trim())) {
				vista.setEntryMessage("RUT Obligatorio - Ingrese nuevamente",
						true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			} else if (!Tools.validarRut(rut)) {
				vista.setEntryMessage("RUT  Inválido - Ingrese nuevamente",
						true);
				vista.setEntryTextLabel("Ingrese Rut:", true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			}

			vista.setEntryTextLabel("Ingrese cuenta cliente:", true);
			vista.setEntryMessage("", true);
			vista.setEntryText("", true, false, false, null, null);
			estado = 4;
			return ICajaView._WAITFORACTION;
		case 4:
			cuentaCliente = vista.getEntryText();
			if ("".equals(cuentaCliente.trim())) {
				vista.setEntryMessage("La cuenta es obligatoria", true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			} else {
				try {
					Long.valueOf(cuentaCliente);
				} catch (NumberFormatException nfe) {
					vista.setEntryMessage("La cuenta tiene que ser numérico",
							true);
					vista.setEntryText("", true, false, false, null, null);
					return ICajaView._WAITFORACTION;
				}
			}
			vista.setEntryTextLabel("Ingrese número de doc:", true);
			vista.setEntryText("", true, false, false, null, null);
			vista.setEntryMessage("", true);
			estado = 5;
			return ICajaView._WAITFORACTION;
		case 5:
			numeroDocumento = vista.getEntryText();
			if ("".equals(numeroDocumento.trim())) {
				vista.setEntryMessage("El nro de documento obligatoria", true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			} else {
				try {
					long nro = Long.valueOf(numeroDocumento);
					if (nro <= 0) {
						vista.setEntryMessage(
								"La nro de documento tiene que ser mayor a 0",
								true);
						vista.setEntryText("", true, false, false, null, null);
						return ICajaView._WAITFORACTION;
					}
				} catch (NumberFormatException nfe) {
					vista.setEntryMessage(
							"La nro de documento tiene que ser numérico", true);
					vista.setEntryText("", true, false, false, null, null);
					return ICajaView._WAITFORACTION;
				}
			}
			estado = 6;
		case 6:
			vista.setEntryTextLabel("Ingrese monto justificar:", true);
			vista.setEntryText("", true, false, false, null, null);
			vista.setEntryMessage("", true);
			estado = 7;
			return ICajaView._WAITFORACTION;
		case 7:
			montoJustificado = vista.getEntryText();
			if ("".equals(montoJustificado.trim())) {
				vista.setEntryMessage("El monto a justificiar es obligatorio",
						true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			} else {
				try {
					long nro = Long.valueOf(montoJustificado);
					if (nro <= 0) {
						vista.setEntryMessage(
								"El monto a justificiar tiene que ser mayor a 0",
								true);
						vista.setEntryText("", true, false, false, null, null);
						return ICajaView._WAITFORACTION;
					}
				} catch (NumberFormatException nfe) {
					vista.setEntryMessage(
							"El monto a justificiar tiene que ser numérico",
							true);
					vista.setEntryText("", true, false, false, null, null);
					return ICajaView._WAITFORACTION;
				}
			}
			vista.setEntryTextLabel("Seleccione Medio de Pago:", true);
			vista.setEntryList(mediosDePago, true, false, indexMedioPago);
			vista.setEntryMessage("", true);
			estado = 8;
			return ICajaView._WAITFORACTION;
		case 8:
			indexMedioPago = vista.getEntryListIndex();

			if (isMotivoSinJustificacion) {
				estado = 11;
			} else {
				estado = 9;
			}
			break;
		case 9:
			vista.setEntryTextLabel("Seleccione Estado:", true);
			vista.setEntryList(estadoJustificacion, true, false, indexEstado);
			estado = 10;
			return ICajaView._WAITFORACTION;
		case 10:
			indexEstado = vista.getEntryListIndex();
			estado = 11;
			break;
		case 11:
			vista.setEntryTextLabel("Ingrese observaciones:", true);
			vista.setEntryText("", true, false, false, null, null);
			estado = 12;
			return ICajaView._WAITFORACTION;
		case 12:
			observacion = vista.getEntryText();
			if ("".equals(observacion.trim())) {
				vista.setEntryMessage("Las observaciones son obligatorias",
						true);
				return ICajaView._WAITFORACTION;
			}
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane
					.showConfirmDialog(
							null,
							"Esta operación no puede ser reversada ¿Esta seguro de efectuar la operación?",
							"Confirme por favor", dialogButton);
			if (dialogResult == JOptionPane.YES_OPTION) {

				if (!isMotivoSinJustificacion) {
					if (!validarJustificacionCierre()) {
						NumeroOperacionOutDTO noo = getNroOperacion();
						int rc = notificarEnvio(noo.getNumeroOperacion());

						if (rc == 0) {
							Base.logger
									.info("Pago de Remesa ejecutado correctamente");
							int retCode = registrarJustificacionCierre(noo
									.getNumeroOperacion());
							if (retCode == 0) {
								JOptionPane
										.showMessageDialog(
												null,
												"Justificación ingresada correctamente",
												"Continuar",
												JOptionPane.INFORMATION_MESSAGE);
							}
						}else if(rc == -260){
							Base.logger.info("Pago de Remesa duplicado");
							JOptionPane
							.showMessageDialog(
									null,
									"La Justificación ya se encontraba ingresada, no es posible repetir una justificación",
									"Continuar",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane
								.showMessageDialog(
									null,
									"Se ha producido un error al notificar envío, de justificación",
									"Continuar",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"La Justificación ya se encontraba ingresada, no es posible repetir una justificación",
										"Continuar",
										JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					int retCode = registrarJustificacionCierre(null);
					if (retCode == 0) {
						JOptionPane.showMessageDialog(null,
								"Justificación ingresada correctamente",
								"Continuar", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				// Todo metodo de retorno
				return 13;
			} else {
				return ICajaView._WAITFORACTION;
			}
		}
		return ICajaView._PREPOSTRETURN;
	}

	private Boolean validarJustificacionCierre() {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		ValidarJustificacionRequestDTO vjr = new ValidarJustificacionRequestDTO();
		ValidarJustificacionResponseDTO vajr = null;
		JustificacionOperacionDTO jod = new JustificacionOperacionDTO();
		JustificacionCierreDTO jcd = new JustificacionCierreDTO();
		String[] rutAr = rut.split("-");

		try {
			jod.setCuentaCliente(cuentaCliente);
			jod.setNumeroDocumento(Long.valueOf(numeroDocumento));
			jod.setRut(Long.valueOf(rutAr[0]));

			jcd.setIdMotivoJustificacion(motivosJustificaciones[index]
					.getIdMotivoJustificacion());
			jcd.setFechaRegistro(fechaStr);
			jcd.setIdUsuario(Long.valueOf(pSet.getStringValue("Cajero").trim()));
			jcd.setIdAgencia(Long
					.valueOf(pSet.getStringValue("Agencia").trim()));
			jcd.setMontoJustificado(Long.valueOf(montoJustificado));
			jcd.setObservacion(observacion);
			jcd.setIdMedioPago(tiposTransacciones[indexMedioPago]
					.getTipoTransaccion());

			vjr.setDatosOperacion(jod);
			vjr.setJustificacion(jcd);
			vajr = pr.validarJustificacionCierre(vjr);
			Base.logger.info("Codigo validacion justificacion: "
					+ vajr.getRetCode());
			Base.logger.info("Msje validacion justificacion: "
					+ vajr.getRetDesc());

			if (Integer.valueOf(vajr.getRetCode()) < 0) {
				JOptionPane.showMessageDialog(null, vajr.getRetDesc(),
						"Continuar", JOptionPane.INFORMATION_MESSAGE);
			}

			return vajr.isExisteJustificacion();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al validar justificacion", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de validacion de justificacion...");
			return null;
		}
	}

	private int registrarJustificacionCierre(Long numeroOperacion) {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		RegistrarJustificacionRequestDTO rjr = new RegistrarJustificacionRequestDTO();
		JustificacionCierreDTO jc = new JustificacionCierreDTO();

		try {
			Base.logger.info("Registrar Justificacion");
			jc.setIdMotivoJustificacion(motivosJustificaciones[index]
					.getIdMotivoJustificacion());
			Base.logger.info("MotivoJustificacion: "
					+ jc.getIdMotivoJustificacion());
			jc.setFechaRegistro(fechaStr);
			Base.logger.info("FechaRegistro: " + jc.getFechaRegistro());
			jc.setIdAgencia(Long.valueOf(pSet.getStringValue("Agencia").trim()));
			Base.logger.info("IdAgencia: " + jc.getIdAgencia());
			jc.setIdMedioPago(tiposTransacciones[indexMedioPago]
					.getTipoTransaccion());
			Base.logger.info("IdMedioPago: " + jc.getIdMedioPago());
			jc.setMontoJustificado(Long.valueOf(montoJustificado));
			Base.logger.info("MontoJustificado: " + jc.getMontoJustificado());
			//jc.setIdUsuario(Long.valueOf(pSet.getStringValue("Cajero").trim()));
			jc.setIdUsuario(Long.valueOf(pSet.getStringValue("CodigoRecaudador").trim()));
			Base.logger.info("IdUsuario: " + jc.getIdUsuario());
			jc.setObservacion(observacion);
			Base.logger.info("Observacion: " + jc.getObservacion());

			if (!isMotivoSinJustificacion) {
				jc.setEstado(estadoJustificacion[indexEstado]);
				Base.logger.info("Estado: " + jc.getEstado());
				jc.setIdOperacion(numeroOperacion);
				Base.logger.info("IdOperacion: " + jc.getIdOperacion());
			}

			rjr.setJustificacion(jc);
			RegistrarJustificacionResponseDTO rjrd = pr
					.registrarJustificacionCierre(rjr);
			Base.logger.info("Codigo registro justificacion: "
					+ rjrd.getRetCode());
			Base.logger.info("Msje registro justificacion: "
					+ rjrd.getRetDesc());
			return Integer.valueOf(rjrd.getRetCode());
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al validar justificacion", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de validacion de justificacion...");
			return -1;
		}
	}

	/**
	 * Obtiene los motivos de justificacion
	 */
	private void obtenerMotivoJustificaciones() {
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();

		try {
			MotivoJustificacionResponseDTO mjr = pr.getMotivoJustificacion();
			motivosJustificaciones = mjr.getMotivosJustificacion();

			justificaciones = new String[motivosJustificaciones.length];
			for (int i = 0; i < motivosJustificaciones.length; i++) {
				justificaciones[i] = motivosJustificaciones[i]
						.getDescMotivoJustificacion();
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al obtener motivos de justificacion", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de motivos de justificacion...");
		}
	}

	private NumeroOperacionOutDTO getNroOperacion() {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		HeaderDTO hd = new HeaderDTO();

		try {
			hd.setAgencia(pSet.getStringValue("Agencia"));
			Base.logger.info("Agencia: " + hd.getAgencia());
			hd.setCajaFisica(pSet.getStringValue("Caja"));
			Base.logger.info("Caja: " + hd.getCajaFisica());
			hd.setCajero(pSet.getStringValue("Cajero"));
			Base.logger.info("Cajero: " + hd.getCajero());
			hd.setEntidad(pSet.getStringValue("Entidad"));
			Base.logger.info("Entidad: " + hd.getEntidad());
			hd.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
			Base.logger.info("CodigoRecaudador: " + hd.getRecaudador());
			hd.setSession(pSet.getStringValue("SessionId"));
			Base.logger.info("SessionId: " + hd.getSession());
			hd.setUsuario(pSet.getStringValue("Cajero"));
			Base.logger.info("Cajero: " + hd.getUsuario());
			return pr.numeroOperacion(hd);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al obtener nro de operacion", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de nro operacion...");
		}
		return null;
	}

	/**
	 * 
	 * @param numeroOperacion
	 * @return
	 */
	private int notificarEnvio(long numeroOperacion) {
		ParamSet pSet = Base.getParamSet("posDat");
		PlataformaPagoNotificarServerProxy pr = AppControlNotificarProxy
				.getProxyInstance();
		NotificacionEnvio ne = new NotificacionEnvio();
		Operacion op = new Operacion();
		Caja caja = new Caja();
		Transaccion trx = new Transaccion();
		MedioPago mp = new MedioPago();
		NotificacionRespuesta nr = null;
		String[] rutAr = rut.split("-");

		try {
			Base.logger.info("Notificar Envio");
			caja.setAgencia(pSet.getStringValue("Agencia"));
			Base.logger.info("Agencia: " + caja.getAgencia());
			caja.setIdCaja(Integer.parseInt(pSet.getStringValue("Caja")));
			Base.logger.info("Caja: " + caja.getIdCaja());
			caja.setEntidad(pSet.getStringValue("Entidad"));
			Base.logger.info("Entidad: " + caja.getEntidad());
			caja.setCodigoSesion(Long.parseLong(pSet
					.getStringValue("SessionId")));
			Base.logger.info("Session: " + caja.getCodigoSesion());
			caja.setUsuario(pSet.getStringValue("Cajero"));
			Base.logger.info("Usuario: " + caja.getUsuario());
			caja.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
			Base.logger.info("Recaudador: " + caja.getRecaudador());
			caja.setCanal(Integer.valueOf(pSet.getStringValue("Canal")));
			Base.logger.info("Canal: " + caja.getCanal());

			trx.setTipoTransaccion("Justificacion");
			Base.logger.info("[TRX] Justificacion: Justificacion");
			trx.setNumeroDocumento(Long.valueOf(numeroDocumento));
			Base.logger.info("[TRX] NumeroDocumento: "
					+ trx.getNumeroDocumento());
			trx.setMonto(Long.valueOf(montoJustificado));
			Base.logger.info("[TRX] Monto: " + trx.getMonto());
			trx.setOrigen("");
			Base.logger.info("[TRX] Origen: " + trx.getOrigen());
			trx.setRut(Long.valueOf(rutAr[0]));
			Base.logger.info("[TRX] Rut: " + trx.getRut());
			trx.setDv(rutAr[1]);
			Base.logger.info("[TRX] DV: " + trx.getDv());
			trx.setFechaVencimiento(fechaStrF2);
			Base.logger.info("[TRX] FechaVencimiento: "
					+ trx.getFechaVencimiento());
			trx.setCodigoPortador(0);
			Base.logger
					.info("[TRX] CodigoPortador: " + trx.getCodigoPortador());
			trx.setCuentaCliente(cuentaCliente);
			Base.logger.info("[TRX] CuentaCliente: " + trx.getCuentaCliente());
			trx.setNroOperacionAReversar(0L);

			mp.setTipoTransaccion(tiposTransacciones[indexMedioPago]
					.getTipoTransaccion());
			Base.logger
					.info("[MP] TipoTransaccion: " + mp.getTipoTransaccion());
			mp.setMonto(Long.valueOf(montoJustificado));
			Base.logger.info("[MP] Monto: " + mp.getMonto());
			mp.setFechaVencimiento(fechaStrF2);
			Base.logger.info("[MP] FechaVencimiento: "
					+ mp.getFechaVencimiento());
			mp.setPagadorRut(rutAr[0]);
			Base.logger.info("[MP] PagadorRut: " + mp.getPagadorRut());
			mp.setPagadorDigitoVerificador(rutAr[1]);
			Base.logger.info("[MP] PagadorDigitoVerificador: "
					+ mp.getPagadorDigitoVerificador());
			mp.setTipoTotal("1");
			Base.logger.info("[MP] TipoTotal: 1");

			op.setTransaccion(new Transaccion[1]);
			op.setTransaccion(0, trx);
			op.setMedioPago(new MedioPago[1]);
			op.setMedioPago(0, mp);
			op.setCaja(caja);

			// El valor 12 corresponde a tipo justificacion
			op.setTipoOperacion(12);
			op.setNumeroOperacion(numeroOperacion);
			Base.logger.info("Numero Operacion: " + op.getNumeroOperacion());
			op.setFechaPago(fechaStrF2);
			Base.logger.info("Fecha: " + op.getFechaPago());
			op.setMonto(Long.valueOf(montoJustificado));
			Base.logger.info("Monto Justificado: " + op.getMonto());

			ne.setOperacion(op);
			nr = pr.notificar(ne);
			Base.logger.info("Codigo Notificacion: "
					+ nr.getRespuesta().getRetCode());
			Base.logger.info("Msje Notificacion: "
					+ nr.getRespuesta().getRetDesc());

		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al obtener notificar envio", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio notificar envio..."
							+ e);
		}
		return nr.getRespuesta().getRetCode();
	}

	/**
	 * Obtiene los medios de pago disponibles, una vez recuperado filtra solo
	 * los solicitados
	 */
	private void obtenerMediosPago() {
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();

		try {
			MedioPagoResponseDTO mpr = pr.getMediosPago();
			TipoTransaccionDTO[] ttd = mpr.getMediosPagos();

			mediosDePago = new String[6];
			tiposTransacciones = new TipoTransaccionDTO[6];
			int medioIdx = 0;
			for (int i = 0; i < ttd.length; i++) {
				String des = ttd[i].getTipoTransaccion();

				if (des.trim().toLowerCase().equals("mpefectivo")
						|| des.trim().toLowerCase().equals("mpdebito")
						|| des.trim().toLowerCase().equals("mpcredito")
						|| des.trim().toLowerCase().equals("mpvalevista")
						|| des.trim().toLowerCase().equals("mpcheque")
						|| des.trim().toLowerCase().equals("mpchequefecha")) {
					tiposTransacciones[medioIdx] = ttd[i];
					mediosDePago[medioIdx] = ttd[i].getDesTransaccion();
					medioIdx++;
				}
			}

		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al obtener medios de pago", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de motivos de justificacion...");
		}
	}

	/**
	 * Al inicio verificamos si existe o no una fecha de sesión almacenada en
	 * posDat, de existir quiere decir que iniciamos el proceso de cierre de una
	 * fecha anterior.
	 */
	@Override
	public void init(Datos htParam) {
		ParamSet pSet = Base.getParamSet("posDat");
		SimpleDateFormat fechaSession = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatter2 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Date fecha = null;
		try {
			String fechaSessionStr = pSet.getStringValue("FechaSession");

			if (!"".equals(fechaSessionStr.trim())) {
				fecha = fechaSession.parse(fechaSessionStr);
				fechaStr = formatter.format(fecha);
				fechaStrF2 = formatter2.format(fecha);
			} else {
				fechaStr = Tools.getFechaDDMMYYYY();
				fechaStrF2 = Tools.getFecha();
			}
		} catch (ParseException e) {
			Base.logger
					.error("Se produjo un error en la base de datos post Invocar al servicio de Registro Sencillo..."
							+ e);
		}

		obtenerMotivoJustificaciones();
		obtenerMediosPago();

		estadoJustificacion = new String[2];
		estadoJustificacion[0] = "Regularizado";
		estadoJustificacion[1] = "Pendiente Ingreso";
	}

}
