package cl.hyh.redpagos.caja.trx;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.RegistrarSencilloCierreRequestDTO;
import ws.claro.cl.RegistrarSencilloCierreResponseDTO;
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
public final class IngresoDeSencilloCierre implements ITrxBase {
	private int estado = 0;
	private String monto = null;
	private String fechaStr;

	@Override
	public int execute(ICajaView vista, int key, Datos htParam) {
		try {
			return switchDeValidacion(vista);
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
			if (vista
					.showMyConfirmDialog(
							"Confirme su Cierre de caja",
							"Esta operación no es reversible, ¿ha remesado todo el efectivo y cheques en su caja?\n Presione SI para continuar o NO para volver al menu principal") == JOptionPane.NO_OPTION) {
				return 11;
			}
			estado = 1;
			pintar(vista, null);
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
				if (registrarSencilloCierre(montoLng)) {
					return 10;
				}
			} else {
				estado = 1;
				return ICajaView._WAITFORACTION;
			}
		}
		return estado;
	}

	/**
	 * Registra sencillo recibido por el cajero al momento del cierre de caja
	 * 
	 * @param monto
	 *            : monto a registrar para el cajero
	 * @return true si el registro fue exitoso, false en caso contrario
	 */
	private Boolean registrarSencilloCierre(Long monto) {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		RegistrarSencilloCierreRequestDTO scr = new RegistrarSencilloCierreRequestDTO();
		RegistrarSencilloCierreResponseDTO rscr = null;

		Base.logger.info("Registrando Sencillo Usuario Cierre....");
		scr.setFechaRecibo(fechaStr);
		Base.logger.info("Fecha: " + scr.getFechaRecibo());
		scr.setIdAgencia(Integer.valueOf(pSet.getStringValue("Agencia").trim()));
		Base.logger.info("Agencia: " + scr.getIdAgencia());
		scr.setIdCaja(Integer.valueOf(pSet.getStringValue("Caja").trim()));
		Base.logger.info("Caja: " + scr.getIdCaja());
		//scr.setIdCajero(Integer.valueOf(pSet.getStringValue("Cajero").trim()));
		//Base.logger.info("Cajero: " + scr.getIdCajero());
		scr.setIdCajero(Integer.valueOf(pSet.getStringValue("CodigoRecaudador").trim()));
		Base.logger.info("Recaudador: " + scr.getIdCajero());
		scr.setMontoCierre(monto);
		Base.logger.info("Monto: " + scr.getMontoCierre());

		try {
			rscr = pr.registrarSencilloCierre(scr);

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
		// vista.setEntryTitle("Ingreso de Sencillo", true);
		vista.setEntryTextLabel("Ingrese monto sencillo:", true);
		if (msg != null) {
			JOptionPane.showMessageDialog(null, msg);
		}
		vista.setEntryText("", true, false, false, null, null);
	}

	/**
	 * Al inicio verificamos si existe o no una fecha de sesión almacenada 
	 * en posDat, de existir quiere decir que iniciamos el proceso de cierre 
	 * de una fecha anterior.
	 */
	@Override
	public void init(Datos htParam) {
		ParamSet pSet = Base.getParamSet("posDat");
		SimpleDateFormat fechaSession = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha = null;
		try {
			String fechaSessionStr = pSet.getStringValue("FechaSession");

			if (!"".equals(fechaSessionStr.trim())) {
				fecha = fechaSession.parse(fechaSessionStr);
				fechaStr = formatter.format(fecha);
			} else {
				fechaStr = Tools.getFechaDDMMYYYY();
			}
		} catch (ParseException e) {
			Base.logger
					.error("Se produjo un error en la base de datos post Invocar al servicio de Registro Sencillo..."
							+ e);
		}
	}
}
