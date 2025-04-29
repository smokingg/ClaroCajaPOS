package cl.hyh.redpagos.caja.trx;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.RegistrarSencilloCajeroRequestDTO;
import ws.claro.cl.RegistrarSencilloCajeroResponseDTO;
import ws.claro.cl.ValidarSencilloCajeroRequestDTO;
import ws.claro.cl.ValidarSencilloCajeroResponseDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Clase orientada a manejar el ingreso del sencillo al momento de ingresar a la
 * aplicación o salir de ella
 * 
 * @author rfuentes
 *
 */
public final class IngresoDeSencillo implements ITrxBase {
	private int estado = 0;
	private String monto = null;
	private String menuDestino = null;
	private String trxOrigen = null;

	@Override
	public int execute(ICajaView vista, int key, Datos htParam) {
		try {

			/**
			 * valido si la trx de origen es al momento de apertura o cierre de
			 * la aplicacion
			 */
			if (trxOrigen != null ? trxOrigen.equals("login") : false) {
				return switchDeValidacion(vista);
			} else if (trxOrigen != null ? trxOrigen.equals("cierreCajaDia")
					: false) {
				return switchDeValidacion(vista);
			}
		} catch (Exception e) {
			Base.logger.error("Resp: " + e);
		}
		return 0;
	}

	/**
	 * Valida ingreso obligatorio de sencillo, campo numérico y mayor a 0
	 * 
	 * @param vista
	 * @return
	 */
	private int switchDeValidacion(ICajaView vista) {
		long montoLng = 0;
		switch (estado) {
		case 0:
			Boolean ingresado = validarIngresoDeSencillo();
			if (ingresado != null ? ingresado : false) {
				return Integer.valueOf(menuDestino);
			}

			pintar(vista, null);
			estado = 1;
			return ICajaView._WAITFORACTION;
		case 1:
			monto = vista.getEntryText();

			try {

				if ("".equals(monto.trim())) {
					pintar(vista,
							"El campo monto sencillo es de tipo obligatorio");
					estado = 1;
					return ICajaView._WAITFORACTION;
				}

				montoLng = Long.valueOf(monto);

				if (montoLng <= 0) {
					throw new Exception();
				}
			} catch (NumberFormatException e) {
				pintar(vista, "El monto tiene que ser numero");
				estado = 1;
				return ICajaView._WAITFORACTION;
			} catch (Exception e) {
				pintar(vista, "El monto tiene que ser mayor a 0");
				estado = 1;
				return ICajaView._WAITFORACTION;
			}

			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"¿Está seguro que desea continuar?", "Confirme por favor",
					dialogButton);

			if (dialogResult == JOptionPane.YES_OPTION) {
				if (registrarSencillo(montoLng)){
					return Integer.valueOf(menuDestino);
				} else {
					return 13; 
				}
			} else {
				estado = 1;
				return ICajaView._WAITFORACTION;
			}
		}
		return estado;
	}

	/**
	 * Valida el ingreso de sencillo
	 * <ul>
	 * <li>0 -> No exise sencillo registrado</li>
	 * <li>-1 -> Error SQL</li>
	 * <li>-2 -> Existe sencillo para elementos filtrados</li>
	 * </ul>
	 * 
	 * @return Integer
	 */
	private Boolean validarIngresoDeSencillo() {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		ValidarSencilloCajeroRequestDTO vscr = new ValidarSencilloCajeroRequestDTO();
		ValidarSencilloCajeroResponseDTO rscr = null;

		Base.logger.info("Validando Sencillo Usuario....");
		vscr.setFechaRecibo(Tools.getFechaDDMMYYYY());
		Base.logger.info("Fecha: " + vscr.getFechaRecibo());
		vscr.setIdAgencia(Long
				.valueOf(pSet.getStringValue("Agencia").trim()));
		Base.logger.info("Agencia: " + vscr.getIdAgencia());
		vscr.setIdCaja(Long.valueOf(pSet.getStringValue("Caja").trim()));
		Base.logger.info("Caja: " + vscr.getIdCaja());
		//vscr.setIdCajero(Long.valueOf(pSet.getStringValue("Cajero").trim()));
		//Base.logger.info("Cajero: " + vscr.getIdCajero());
		vscr.setIdCajero(Long.valueOf(pSet.getStringValue("CodigoRecaudador").trim()));
		Base.logger.info("Recaudador: " + vscr.getIdCajero());

		try {
			rscr = pr.validarSencilloCajero(vscr);
			Base.logger.info("Resp: " + rscr.getRetCode());
			Base.logger.info("Resp msj: " + rscr.getRetDesc());

			int retCode = Integer.valueOf(rscr.getRetCode().trim());
			if (retCode == -1) {
				throw new Exception(rscr.getRetDesc());
			}

			return rscr.isSencilloIngresado();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al Validar Ingreso de sencillo", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de Sencillo...");
			return null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Error al Validar Ingreso de sencillo", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error en la base de datos post Invocar al servicio de Sencillo..."
							+ e);
			return null;
		}
	}

	/**
	 * Registra sencillo recibido por el cajero al momento de la apertura de caja
	 * 
	 * @param monto: monto a registrar para el cajero
	 * @return true si el registro fue exitoso, false en caso contrario
	 */
	private Boolean registrarSencillo(Long monto) {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		RegistrarSencilloCajeroRequestDTO scr = new RegistrarSencilloCajeroRequestDTO();
		RegistrarSencilloCajeroResponseDTO rscr = null;

		Base.logger.info("Registrando Sencillo Usuario....");
		scr.setFechaRecibo(Tools.getFechaDDMMYYYY());
		Base.logger.info("Fecha: " + scr.getFechaRecibo());
		scr.setIdAgencia(Long.valueOf(pSet.getStringValue("Agencia").trim()));
		Base.logger.info("Agencia: " + scr.getIdAgencia());
		scr.setIdCaja(Long.valueOf(pSet.getStringValue("Caja").trim()));
		Base.logger.info("Caja: " + scr.getIdCaja());
		//scr.setIdCajero(Long.valueOf(pSet.getStringValue("Cajero").trim()));
		//Base.logger.info("Cajero: " + scr.getIdCajero());
		scr.setIdCajero(Long.valueOf(pSet.getStringValue("CodigoRecaudador").trim()));
		Base.logger.info("Recaudador: " + scr.getIdCajero());
		scr.setMontoRecibido(monto);

		try {
			rscr = pr.registrarSencilloCajero(scr);

			Base.logger.info("Resp: " + rscr.getRetCode());
			Base.logger.info("Resp msj: " + rscr.getRetDesc());

			int retCode = Integer.valueOf(rscr.getRetCode());

			if (retCode == -1) {
				throw new Exception(rscr.getRetDesc());
			}

			return true;
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al Registrar Ingreso de sencillo", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de Sencillo...");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Error al Validar Ingreso de sencillo", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error en la base de datos post Invocar al servicio de Registro Sencillo..."
							+ e);
			return false;
		}
	}

	/**
	 * Imprime en vista destinada campo para ingreso de sencillo y/o despligue
	 * de mensaje en caso de no ser nulo
	 * 
	 * @param vista
	 *            : vista en la que se va a imprimir input de sencillo
	 * @param msg
	 *            : mensaje a desplegar
	 */
	public void pintar(ICajaView vista, String msg) {
		vista.setEntryTextLabel("Ingrese monto sencillo:", true);
		if (msg != null) {
			JOptionPane.showMessageDialog(null, msg);
		}
		vista.setEntryText("", true, false, false, null, null);
	}

	@Override
	public void init(Datos htParam) {
		// Tratamiento de parametros recibidos desde la partitura
		String[] params = htParam.getStringValue("raParam").split(",");

		if (params.length > 1) {
			menuDestino = params[0].trim();
			trxOrigen = params[1].trim();
		}
	}

}
