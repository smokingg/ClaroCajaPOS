package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import ws.claro.cl.proxy.AppControlConsultarProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionOut;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.docpago.DocumentoClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaVtr;

/**
 * Implementacion de la transaccion de consulta por Rut Claro
 * 
 * @author cbriones
 * 
 */
public class TrxClaroPagoServiciosContingencia implements ITrxBase {
	int estado = 0;
	String codigo = "";
	Datos data;
	DocumentoPago doc;
	String opt[];
	String optOrigen[];
	String[] docs = null;
	String tiposDocs = "";
	int index = 0;
	int indexCta = 0;
	String rut = "";
	String dv = "";
	String numeroCuenta="";
	String numeroFono="";
	String valorLista="";
	String valorListaVista="";
	String valorListaTipoDoc="";
	String controlInterno="0";

	// se setean como atributo DTO de salida del servicio App Control
	OperacionOut opOut = null;
	OperacionOut opOutIt = null;

	CuentaVTR cuenta = null;
	DetalleDocumento cuentaDocClaro = null;

	String trv = null;
	String fecha = "";
	String numeroDocumento = "";
	long montoDocumento = 0;
	int codEmpresa=0;

	public void init(Datos datosVista) {
		tiposDocs = "Siscli";
		trv = datosVista.getStringValue("btnParam");

	}

	public int execute(ICajaView vista, int key, Datos datos) {
		if (key == 0) {
			estado = 0;
    		if(vista.getOperTRV().getCarroCompras().getDocumentos().size() > 0){
    			JOptionPane.showMessageDialog(null, "Modo Contingencia solo permite un Pago por Transacción", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                return 13;
    		}
			// vista.setInputTimeout(15);
		} else if (key == 1) {
			// Timeoutma
			return 13;
		} else if (key == KeyEvent.VK_ENTER) {
			if (estado == 13){
				return 13;
			}
		} else {
			// otra tecla. Lo que sea que esté en el XML...
			return ICajaView._PASSTHROUGH;
		}

		Base.logger.info("La invocacion se realizo desde: " + trv);

		switch (estado) {
		case 0:
			estado = 1;
			vista.hideAllEntries();
            vista.setEntryTitle( "Seleccione Sistema de Origen", true );                
            vista.setEntryMessage( "Sistema de Origen", true );
            vista.setEntryTextLabel("Seleccione:", true);
            opt = new String[6];      
            opt[0] = "Claro TV Satelital (DTH)";
            opt[1] = "Claro 3Play (HFC)";
            opt[2] = "Claro Movil (VTV)";
            opt[3] = "Claro Carrier-Empresas (SGA)";
            opt[4] = "Claro SAP";
            opt[5] = "Claro Movil (ONE)";
            optOrigen = new String[6];      
            optOrigen[0] = "ESAC";
            optOrigen[1] = "SUR";
            optOrigen[2] = "VTV";
            optOrigen[3] = "SGA";
            optOrigen[4] = "SAP";
            optOrigen[5] = "ONE";
           
            vista.setEntryList(opt, true, false);
            return ICajaView._WAITFORACTION;
		
		case 1:
			
			estado = 2;
			index = vista.getEntryListIndex();
            valorLista = optOrigen[index];
            valorListaVista=opt[index];
            
            if(valorLista.equalsIgnoreCase("VTV") ||
            		valorLista.equalsIgnoreCase("SAP") ||
            		valorLista.equalsIgnoreCase("ONE") ){
            	codEmpresa=8;
            }
            
            if(valorLista.equalsIgnoreCase("ESAC")||valorLista.equalsIgnoreCase("SUR")){
            	codEmpresa=1;
            }
            
            if(valorLista.equalsIgnoreCase("SGA")){
            	codEmpresa=3;
            }
            
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese Rut (Formato: 99999999-X)", true);
			vista.setEntryTextLabel("Ingrese Rut:", true);
			vista.setEntryText("", true, false, false, null, null);
			return ICajaView._WAITFORACTION;

		case 2:
			
			codigo = vista.getEntryText();

			
			 if("".trim().equalsIgnoreCase(codigo)){
                 estado = 2;
                 vista.setEntryMessage( "No ha ingresado ningun Rut - Ingrese nuevamente", true );
                 vista.setEntryTextLabel("Ingrese Rut:", true);
                 vista.setEntryText("", true, false, false,null,null);
                 return ICajaView._WAITFORACTION;
             }
			
			if (!Tools.validarRut(codigo)) {
				estado = 2;
				vista.setEntryMessage("RUT  Inválido - Ingrese nuevamente",	true);
				vista.setEntryTextLabel("Ingrese Rut:", true);
				vista.setEntryText("", true, false, false, null, null);
				return ICajaView._WAITFORACTION;
			}

			// Se elimina el DV Ingresado por el Usuario !!
			rut = codigo.substring(0, codigo.length() - 2);
			dv = codigo.substring(rut.length() + 1);

			// cbriones: se debe Implementar invocacion a servicio de Consulta
			// OSB !!!
			// ServerProxy pr = Proxy.getProxyInstance();
			PlataformaPagoConsultarServerProxy pr = AppControlConsultarProxy
					.getProxyInstance();

			// Generar los DTOs de entrada !!
			// ConsultaDeudaRutVTRIn in = new ConsultaDeudaRutVTRIn();
			ParamSet pSet = Base.getParamSet("posDat");

			OperacionIn opIn = new OperacionIn();
			// Instanciar un Objeto Caja para OperacionIn
			Caja caja = new Caja();
			caja.setAgencia(pSet.getStringValue("Agencia"));
			Base.logger.info("Valor Agencia: " + caja.getAgencia());
			caja.setIdCaja((int) (pSet.getLongValue("Caja")));
			Base.logger.info("Valor Id Caja: " + caja.getIdCaja());
			caja.setEntidad(pSet.getStringValue("Entidad"));
			Base.logger.info("Valor Entidad: " + caja.getEntidad());
			caja.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
			Base.logger.info("Valor Recaudador: " + caja.getRecaudador());
			caja.setUsuario(pSet.getStringValue("Usuario"));
			Base.logger.info("Valor Usuario: " + caja.getUsuario());
			caja.setCodigoSesion(new Long(pSet.getStringValue("SessionId")));
			Base.logger.info("Valor Session: " + caja.getCodigoSesion());
			// caja.setCanal(canal);

			// caja.setCanal(1);
			caja.setCanal(new Integer(pSet.getStringValue("Canal")).intValue());
			Base.logger.info("Valor Canal: " + caja.getCanal());

			estado = 3;
			return ICajaView._NOWAITFORACTION;
			
			
		case 3:
				estado=4;
//				vista.hideAllEntries();
//	            vista.setEntryTitle( "Tipo de Documento", true );                
//	            vista.setEntryMessage( "Seleccione Tipo de Documento", true );
//	            vista.setEntryTextLabel("Seleccione:", true);
//	            opt = new String[2];      
//	            opt[0] = "B/V";
//	            opt[1] = "FAC";
//	            vista.setEntryList(opt, true, false);
	            return ICajaView._NOWAITFORACTION;
				
		case 4:
			
			estado = 5;
			//index = vista.getEntryListIndex();
			//valorListaTipoDoc = opt[index];
			valorListaTipoDoc = "B/V";
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese Número Documento", true);
			vista.setEntryTextLabel("Ingrese Número:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");

			return ICajaView._WAITFORACTION;
			
		case 5:
			
			numeroDocumento = vista.getEntryText();
			
            if(valorLista.equalsIgnoreCase("SUR")||valorLista.equalsIgnoreCase("SGA")){
            	numeroCuenta="0";
            	estado=7;
            	return ICajaView._NOWAITFORACTION;
            	
            }else{
            	
				vista.hideAllEntries();
				vista.setEntryTitle("Pago Servicios", true);
				vista.setEntryMessage("Ingrese Número de Cuenta", true);
				vista.setEntryTextLabel("Ingrese Cuenta:", true);
				//vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
				vista.setEntryText("", true, false, false, null, null);
				
				if(valorLista.equalsIgnoreCase("VTV")||valorLista.equalsIgnoreCase("ONE")){
					estado = 21;
				}else if(valorLista.equalsIgnoreCase("ESAC")){
					estado = 7;
				}else{ // SAP 
					estado = 6;
				}

				return ICajaView._WAITFORACTION;
            }
			
		case 6:
			numeroCuenta = vista.getEntryText();
			estado = 7;
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese Número de Teléfono a Pagar", true);
			vista.setEntryTextLabel("Ingrese Número:", true);
			vista.setEntryText("", true, false, false, null,null);
			return ICajaView._WAITFORACTION;
		
		case 7:
			
			estado = 8;
			
			if(valorLista.equalsIgnoreCase("SUR")||valorLista.equalsIgnoreCase("SGA")){
				numeroFono="0";
			}else if(valorLista.equalsIgnoreCase("ESAC")){
				numeroFono="0";
				numeroCuenta = vista.getEntryText();
			}else if(valorLista.equalsIgnoreCase("ONE")){
				numeroFono="0";
				controlInterno = vista.getEntryText();
			}
			else{
				numeroFono = vista.getEntryText();
			}
			SimpleDateFormat inFormat = new SimpleDateFormat("yyyyMMdd");
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese fecha AAAAMMDD", true);
			vista.setEntryTextLabel("Ingrese fecha:", true);
			vista.setEntryText(inFormat.format(new Date()), true, true, false,	"[0-9]+", "Datos erroneos");
			return ICajaView._WAITFORACTION;
		
		case 8:
			estado = 9;
			fecha = vista.getEntryText();
			return ICajaView._NOWAITFORACTION;
		case 9:
			estado = 10;
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese monto", true);
			vista.setEntryTextLabel("Ingrese monto:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Monto ingresado no válido");
			return ICajaView._WAITFORACTION;

		case 10:
			doc = null;
			montoDocumento = Long.parseLong(vista.getEntryText());
			estado = 11;

			try {
				// doc =
				// FactoryDocumentoPago.makeInstance("DocumentoCuentaVtr");
				doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
				
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("No se pudo instanciar el documento");
				return 20;
			}

			
			cuentaDocClaro = new DetalleDocumento();
			cuentaDocClaro.setFechaEmisionDocumento(Tools.getFecha());
			cuentaDocClaro.setFechaVencimientoDocumento(Tools.getFecha());
			cuentaDocClaro.setSaldo(montoDocumento);
			cuentaDocClaro.setSaldoAdeudado(montoDocumento);
			cuentaDocClaro.setNumeroCuenta(numeroCuenta);
			cuentaDocClaro.setDireccionCliente("N/A");
			cuentaDocClaro.setSistemaOrigen(valorLista);
			cuentaDocClaro.setFolioDocumento(Long.parseLong(numeroDocumento));
			cuentaDocClaro.setTipoDocumento(valorListaTipoDoc);
			
			if (valorLista.equalsIgnoreCase("SAP")){
				cuentaDocClaro.setTipoRegistro("DEUDAMAYOR");
			}else{
				cuentaDocClaro.setTipoRegistro("Deuda");
			}
			
			cuentaDocClaro.setCodigoEmpresa(codEmpresa);
			cuentaDocClaro.setMontoTotalDocumento(montoDocumento);
			cuentaDocClaro.setIdServicio(controlInterno);
			cuentaDocClaro.setNumeroCuenta(numeroCuenta);
			cuentaDocClaro.setTelefonoContacto(numeroFono);
			cuentaDocClaro.setCodigoPortador(Integer.parseInt(numeroFono));
			//cuentaDocClaro.setNombreCliente(numeroFono);
		
			
			// cuenta = out.getCuentas()[index];
//			cuenta = new CuentaVTR();
//			cuenta.setCuentaUnica("0");
//			cuenta.setDireccionCobranza("NA");
//			cuenta.setFechaVencimiento(new GregorianCalendar());
//			cuenta.setNumeroCuenta(Long.parseLong(numeroCuenta));
//			cuenta.setSistemaOrigen(valorLista);

			((DocumentoCuentaClaro) doc).llenarDatos(cuentaDocClaro);

			doc.getDatos().setValue("Rut", codigo);
			doc.getDatos().setValue("DvCliente", dv);
			doc.getDatos().setValue("RutCliente", rut);
			doc.getDatos().setValue("Cliente", "");
			doc.getDatos().setValue("CuentaClaro", numeroCuenta);
			doc.getDatos().setValue("SistemaOrigenClaro", valorLista);
			doc.getDatos().setValue("FechaVencimientoClaro", Tools.getFecha());
			doc.getDatos().setValue("SaldoAdeudadoClaro", montoDocumento);
			doc.getDatos().setValue("Monto", montoDocumento);
			doc.getDatos().setValue("NumeroCuenta ", numeroCuenta);
			doc.getDatos().setValue("IdServicioClaro ", controlInterno);
			doc.getDatos().setValue("TelefonoContacto ", numeroFono);
			//doc.getDatos().setValue("CodigoPortador ", numeroFono);
			
			

			return ICajaView._NOWAITFORACTION;

		case 11:
			vista.paintButtons(Tools.getBotones(-1));
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Servicios en Contingencia", true);
			vista.setEntryTextArea("Origen: "	+ valorListaVista
								+ "\nRut Cliente: " 	+ doc.getDatos().getStringValue("Rut")
								+ "\nNumero Documento: " 	+ numeroDocumento
								+ "\nNúmero Cuenta: "	+     doc.getDatos().getStringValue("CuentaClaro")
								+ "\nNúmero Control Interno: "	+ doc.getDatos().getStringValue("IdServicioClaro")
								+ "\nNúmero de Teléfono: "	+ doc.getDatos().getStringValue("TelefonoContacto")
								+ "\nFecha Emisión: "	+ fecha
								+ "\nFecha Vencimiento: " + fecha
								+ "\nMonto a Pagar: " + Format.formatMonto(doc.getDatos().getLongValue("SaldoAdeudadoClaro")), true);
			estado = 12;
			return ICajaView._WAITFORACTION;

		case 12:
			estado=13;
			vista.hideAllEntries();
			try {
				vista.getOperTRV().addDocumentoPago(doc);

			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger
						.error("Error en agregar documento de pago al carro");
			}
			doc.getDatos().show("Documento VTR");
			
			ParamSet posDat = Base.getParamSet("posDat");
			posDat.setValue("TipoPagoContingencia","Servicio");
			posDat.save();
			
			vista.paintButtons(Tools.getBotones(2));
			return ICajaView._NOWAITFORACTION;

		
		case 21:
			numeroCuenta = vista.getEntryText();
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese Número de Control Interno", true);
			vista.setEntryTextLabel("Ingrese Número:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			//vista.setEntryText("", true, false, false, null, null);
			if(valorLista.equalsIgnoreCase("ONE")){
				estado = 7;
			}else if(valorLista.equalsIgnoreCase("VTV")){
				estado = 22;
			}
			return ICajaView._WAITFORACTION;
		
	   case 22:
		   controlInterno = vista.getEntryText();
		   estado=7;
		    vista.hideAllEntries();
			vista.setEntryTitle("Pago Servicios", true);
			vista.setEntryMessage("Ingrese Número de Teléfono a Pagar", true);
			vista.setEntryTextLabel("Ingrese Número:", true);
			vista.setEntryText("", true, false, false, null,null);
			return ICajaView._WAITFORACTION;
	
		   
			
		}
		return 0;
	}
}
