package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaSaldoFavorPeticionType;
import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaSaldoFavorRespuestaType;
import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ConsultaSaldoFavorRespuestaTypeHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.DatosHeaderResponseHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.ListaCuentaType;
import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAFProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DevuelveSaldoFavorPeticionType;
import cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DevuelveSaldoFavorRespuestaType;
import cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DevuelveSaldoFavorRespuestaTypeHolder;
import cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.HeaderRequest;
import cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.PlataformaPagonotificarDevolucionSAFProxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.proxy.AppControlDevolucionSAFProxy;
import ws.claro.cl.proxy.AppControlNotificarDevolucionSAFProxy;
import ws.claro.cl.proxy.AppControlProxy;

/**
 * 
 * @author REspinoza
 *
 */
public class TrxClaroDevolucionSAF implements ITrxBase {

	int estado = 0;
	String rut = "";
	String[] clientesCuentas;
	int index = 0;
	ListaCuentaType cuenta = null;

	ConsultaSaldoFavorRespuestaType opOut = null;
	DevuelveSaldoFavorRespuestaType opOutNotificar = null;

	public void init(Datos data) {

	}

	public int execute(ICajaView vista, int key, Datos htParam) {

		System.out.println("key: " + key);
		System.out.println("htParam: " + htParam);

		if (key == 0) {
			estado = 0;
			vista.acceptEscape(true);
		} else if (key == KeyEvent.VK_F1) {
			estado = 10;
		} else if (key == KeyEvent.VK_ENTER) {
		} else if (key == 120) {
			return 20;
		} else if (key == KeyEvent.VK_F9) {
			return 20;
		} else if (key == KeyEvent.VK_ESCAPE) {
			return 20;
		} else {
			// otra tecla. Lo que sea que esté en el XML...
			return ICajaView._PASSTHROUGH;
		}

		switch (estado) {
		case 0:
			estado = 1;
			vista.setEntryMessage("Ingrese Rut (Formato: 99999999-X)", true);
			vista.setEntryTextLabel("Ingrese Rut:", true);
			vista.setEntryText("", true, false, false, null, null);
			return ICajaView._WAITFORACTION;
		case 1:
			String codigo = vista.getEntryText();

			if ("".trim().equalsIgnoreCase(codigo)) {
				estado = 1;
				vista.setEntryMessage(
						"No ha ingresado ningun Rut - Ingrese nuevamente", true);
				vista.setEntryTextLabel("Ingrese Rut:", true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			}

			if (!Tools.validarRut(codigo)) {
				estado = 1;
				vista.setEntryMessage("RUT  Inválido - Ingrese nuevamente",
						true);
				vista.setEntryTextLabel("Ingrese Rut:", true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			}

			// Se elimina el DV Ingresado por el Usuario
			
//			rut = codigo.substring(0, codigo.length() - 2);
			rut = codigo;
			
			try {

				PlataformaPagoconsultaDevolucionSAFProxy pr = AppControlDevolucionSAFProxy
						.getProxyInstance();

				ConsultaSaldoFavorPeticionType opIn = new ConsultaSaldoFavorPeticionType();
				ConsultaSaldoFavorRespuestaTypeHolder respTypeHolder = new ConsultaSaldoFavorRespuestaTypeHolder();
				DatosHeaderResponseHolder datosHdrResp = new DatosHeaderResponseHolder();

				opIn.setRutCliente(rut);
				Base.logger.info("Valor Operacion: " + opIn.getRutCliente());

				vista.showBusyWindow("Consultando", "Espere por favor...");

				try {
					pr.consultarSaldoFavor(opIn, null, respTypeHolder,
							datosHdrResp);

					// Instanciar DTO Respuesta para rescatar codigo de error
					opOut = respTypeHolder.value;

				} catch (RemoteException e2) {
					e2.printStackTrace();
					Base.logger.error("Error en la invocacion al Servicio: "
							+ e2.getMessage());
					vista.hideBusyWindow();
					JOptionPane.showMessageDialog(null, "Error de conexión",
							"Continuar", JOptionPane.INFORMATION_MESSAGE);
					Tools.logStackTrace(Base.logger, e2);
					return 20;
				}

				vista.hideBusyWindow();

				// Validar respuesta
				if (opOut.getResultadoEjecucion() == null
						|| opOut.getResultadoEjecucion().getCodigoError() == null
						|| !opOut.getResultadoEjecucion().getCodigoError()
								.equalsIgnoreCase("0")
						|| opOut.getListaCuenta() == null) {

					Base.logger.error("No existen datos [RetCode]:"
							+ opOut.getResultadoEjecucion().getCodigoError()
							+ " [msg]:"
							+ opOut.getResultadoEjecucion().getMensaje());
					JOptionPane.showMessageDialog(null, opOut
							.getResultadoEjecucion().getMensaje() != null
							&& !opOut.getResultadoEjecucion().getMensaje()
									.equals("")
							&& !opOut.getResultadoEjecucion().getMensaje()
									.equals("0") ? opOut
							.getResultadoEjecucion().getMensaje()
							: "No se encuentra la cuenta", "Continuar",
							JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}

				estado = 2;
				return ICajaView._NOWAITFORACTION;

			} catch (Exception n) {
				JOptionPane.showMessageDialog(null,
						"Problemas al consultar Rut", "Continuar",
						JOptionPane.INFORMATION_MESSAGE);
				return 20;
			}

		case 2:
			estado = 3;
			try {
				clientesCuentas = new String[opOut.getListaCuenta().length + 1];

				vista.hideAllEntries();
				vista.setEntryTitle("Devolución Saldo Favor", true);
				clientesCuentas[0] = String.format(
						"%-13s %-20s %-13s %-13s %-13s", "Rut", "Nombre",
						"N° Cuenta", "Tipo Cuenta", "Saldo Cuenta");

				for (int i = 0; i < opOut.getListaCuenta().length; i++) {
					ListaCuentaType aux = opOut.getListaCuenta(i);

					clientesCuentas[i + 1] = String.format(
							"%-13s %-20s %-13s %-13s %-13s",
							opOut.getRutCliente(), opOut.getNombreCliente(),
							aux.getNumeroCuenta(), aux.getTipoCuenta().trim(),
							aux.getSaldoCuenta());
				}

				vista.setEntryList(clientesCuentas, true, false, index + 1);
				return ICajaView._WAITFORACTION;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Problemas al consultar Cuenta:" + e.getStackTrace(),
						"Continuar", JOptionPane.INFORMATION_MESSAGE);
				return 20;
			}

		case 3:
			index = vista.getEntryListIndex();
			if (index == 0) {
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			} else {
				index = index - 1;
			}
			estado = 4;

			// Obtenemos cuenta seleccionada
			cuenta = opOut.getListaCuenta(index);

			return ICajaView._NOWAITFORACTION;

		case 4:

			vista.hideAllEntries();
			vista.setEntryTitle("Devolución Saldo Favor", true);
			vista.setEntryTextArea(
					"\n\nNombre: " + opOut.getNombreCliente() + "\n\nRut: "
							+ opOut.getRutCliente() + "\n\nNro Cuenta: "
							+ cuenta.getNumeroCuenta() + "\n\nTipo Cuenta: "
							+ cuenta.getTipoCuenta() + "\n\nSaldo a Favor: "
							+ cuenta.getSaldoCuenta(), true);
			estado = 5;
			return ICajaView._WAITFORACTION;

		case 5:

			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"¿Está seguro que desea continuar?", "Confirme por favor",
					dialogButton);

			if (dialogResult == JOptionPane.YES_OPTION) {
				vista.showBusyWindow("Consultando", "Espere por favor...");
				notificarDevolucionSAF(cuenta, vista);
				estado = 0;
				return ICajaView._NOWAITFORACTION;

			} else {
				estado = 0;
				return ICajaView._NOWAITFORACTION;
			}

		}
		return 0;
	}

	/**
	 * Servicio Notificar Devolucion SAF |
	 * AppControlCaja.NotificarDevolucionSAFService
	 * 
	 * @param cta
	 * @param vista 
	 */
	private void notificarDevolucionSAF(ListaCuentaType cta, ICajaView vista) {

		PlataformaPagonotificarDevolucionSAFProxy pr = AppControlNotificarDevolucionSAFProxy
				.getProxyInstance();

		DevuelveSaldoFavorPeticionType opIn = new DevuelveSaldoFavorPeticionType();
		DevuelveSaldoFavorRespuestaTypeHolder respTypeHolder = new DevuelveSaldoFavorRespuestaTypeHolder();
		cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DatosHeaderResponseHolder datosHdrResp = new cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.DatosHeaderResponseHolder();

		opIn.setMontoDevolucion(new BigDecimal(cta.getSaldoCuenta()));
		opIn.setNumeroCuenta(cta.getNumeroCuenta());
		opIn.setNumeroEnvioPago("1"); // TODO Valor constante, fecha

		try {

			pr.devolverSaldoFavor(opIn, null, respTypeHolder, datosHdrResp);

			// Instanciar DTO Respuesta para rescatar codigo de error
			opOutNotificar = respTypeHolder.value;

//			Base.logger.error("AKIIIIIIIIIII: "
//					+ opOutNotificar.getResultadoEjecucion().getCodigoError());

			if (opOutNotificar != null
					&& opOutNotificar.getResultadoEjecucion() != null
					&& opOutNotificar.getResultadoEjecucion().getCodigoError()
							.equalsIgnoreCase("0")) {
				JOptionPane.showMessageDialog(null,
						"Devolución SAF Exitosa",
						"Continuar", JOptionPane.INFORMATION_MESSAGE);
			} else if (opOutNotificar != null
					&& opOutNotificar.getResultadoEjecucion() != null
					&& opOutNotificar.getResultadoEjecucion().getCodigoError()
							.equalsIgnoreCase("1")) {
				
				JOptionPane.showMessageDialog(null,
						"Error Devolución SAF. Rut no válido",
						"Continuar", JOptionPane.INFORMATION_MESSAGE);
				
			} else if (opOutNotificar != null
					&& opOutNotificar.getResultadoEjecucion() != null
					&& opOutNotificar.getResultadoEjecucion().getCodigoError()
							.equalsIgnoreCase("2")) {
				
				JOptionPane.showMessageDialog(null,
						"Error Devolución SAF. Marca errónea",
						"Continuar", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,
						"Problemas al notificar la devolución, código:" + opOutNotificar.getResultadoEjecucion().getCodigoError(),
						"Continuar", JOptionPane.INFORMATION_MESSAGE);
			}


		} catch (RemoteException e2) {
			e2.printStackTrace();
			Base.logger.error("Error en la invocacion al Servicio: "
					+ e2.getMessage());
			vista.hideBusyWindow();
			JOptionPane.showMessageDialog(null, "Error de conexión",
					"Continuar", JOptionPane.INFORMATION_MESSAGE);
			Tools.logStackTrace(Base.logger, e2);
//			return 20;
		}

		vista.hideBusyWindow();

	}
}
