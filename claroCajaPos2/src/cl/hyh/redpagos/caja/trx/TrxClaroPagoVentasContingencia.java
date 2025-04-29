package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;

/**
 * Implementacion de la transaccion de consulta por Rut Claro
 * 
 * @author cbriones
 * 
 */
public class TrxClaroPagoVentasContingencia implements ITrxBase {
	int estado = 0;
	int index = 0;
	String codigo = "";
	DocumentoPago doc;
	String tiposDocs = "";
	String rut = "";
	String dv = "";
	String numeroCuenta="";
	String[] opt;
	String[] optOrigen;
	String origen;
	String optVista;
	String codigoNotaVenta="";
	int empresa;
	
	DetalleDocumento cuentaDocClaro = null;

	String trv = null;
	String fecha = "";
	String numeroDocumento = "";
	long montoDocumento = 0;
	String tipoRegistro;

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
			if (estado == 10){
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
            opt = new String[2];      
            opt[0] = "Nota Venta SAP";
            opt[1] = "Nota Venta ONE";
            optOrigen = new String[2];      
            optOrigen[0] = "SAP";
            optOrigen[1] = "ONE";
           
            vista.setEntryList(opt, true, false);
			
			return ICajaView._WAITFORACTION;
		case 1:
			index = vista.getEntryListIndex();
            origen = optOrigen[index];
            optVista=opt[index];
            
            if(origen.equalsIgnoreCase("SAP")){
            	estado = 2;
            	vista.setEntryTitle("Pago Ventas", true);
    			vista.setEntryMessage("Ingrese Rut (Formato: 99999999-X)", true);
    			vista.setEntryTextLabel("Ingrese Rut:", true);
    			vista.setEntryText("", true, false, false, null, null);
    			return ICajaView._WAITFORACTION;
            }else{
            	estado = 11;
            	return ICajaView._NOWAITFORACTION;
            }
						
		case 2:	
			estado = 3;
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
			
			return ICajaView._NOWAITFORACTION;
				
		case 3:	
			estado = 4;
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Ventas", true);
			vista.setEntryMessage("Ingrese Número Documento o Nota Venta", true);
			vista.setEntryTextLabel("Ingrese Número:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			return ICajaView._WAITFORACTION;

		case 4:
			numeroDocumento = vista.getEntryText();
			estado = 5;
			SimpleDateFormat inFormat = new SimpleDateFormat("yyyyMMdd");
			vista.setEntryTitle("Pago Ventas", true);
			vista.setEntryMessage("Ingrese fecha AAAAMMDD", true);
			vista.setEntryTextLabel("Ingrese fecha:", true);
			vista.setEntryText(inFormat.format(new Date()), true, true, false,
					"[0-9]+", "Datos erroneos");
			return ICajaView._WAITFORACTION;

		case 5:
			estado = 6;
			fecha = vista.getEntryText();
			return ICajaView._NOWAITFORACTION;
		case 6:
			estado = 7;
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Ventas", true);
			vista.setEntryMessage("Ingrese monto", true);
			vista.setEntryTextLabel("Ingrese monto:", true);
			vista.setEntryText("", true, false, false, "[0-9]+",
					"Código ingresado no válido");
			return ICajaView._WAITFORACTION;

		case 7:
			// cbriones: se comenta para pruebas flujo de Claro
			doc = null;
			montoDocumento = Long.parseLong(vista.getEntryText());
			estado = 8;

			try {
				// doc =
				// FactoryDocumentoPago.makeInstance("DocumentoCuentaVtr");
				doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
				
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("No se pudo instanciar el documento");
				return 20;
			}
			
			if(origen.equalsIgnoreCase("ONE")){
				rut = "0";
				dv = "0";
				codigo = "0"+"-"+"0";
				empresa = 1;
				tipoRegistro = "PagoDeudaNV";
				codigoNotaVenta = numeroDocumento;
			}else if(origen.equalsIgnoreCase("SAP")){
				numeroCuenta = "0";
				codigoNotaVenta = "0";
				empresa = 8;
				tipoRegistro = "DEUDAMAYOR";
			}
			
			cuentaDocClaro = new DetalleDocumento();
			cuentaDocClaro.setFechaEmisionDocumento(Tools.getFecha());
			cuentaDocClaro.setFechaVencimientoDocumento(Tools.getFecha());
			cuentaDocClaro.setSaldo(montoDocumento); 
			cuentaDocClaro.setSaldoAdeudado(montoDocumento); 
			cuentaDocClaro.setNumeroCuenta(numeroCuenta);
			cuentaDocClaro.setDireccionCliente("N/A"); 
			cuentaDocClaro.setSistemaOrigen(origen); 
			cuentaDocClaro.setFolioDocumento(Long.parseLong(numeroDocumento)); 
			cuentaDocClaro.setTipoDocumento("B/V"); 
			cuentaDocClaro.setTipoRegistro(tipoRegistro);
			cuentaDocClaro.setCodigoEmpresa(empresa); 
			cuentaDocClaro.setMontoTotalDocumento(montoDocumento); 
			cuentaDocClaro.setIdServicio(codigoNotaVenta); 
			cuentaDocClaro.setCodigoPortador(Integer.parseInt(codigoNotaVenta));



			((DocumentoCuentaClaro) doc).llenarDatos(cuentaDocClaro);

			doc.getDatos().setValue("Rut", codigo);
			doc.getDatos().setValue("DvCliente", dv);
			doc.getDatos().setValue("RutCliente", rut);
			doc.getDatos().setValue("Cliente", "");
			doc.getDatos().setValue("CuentaClaro", numeroCuenta);
			doc.getDatos().setValue("SistemaOrigenClaro", origen);
			doc.getDatos().setValue("FechaVencimientoClaro", Tools.getFecha());
			doc.getDatos().setValue("SaldoAdeudadoClaro", montoDocumento);
			doc.getDatos().setValue("Monto", montoDocumento);
			doc.getDatos().setValue("NumeroCuenta ", numeroCuenta);
			doc.getDatos().setValue("TipoTrx", "PagoDeudaNV");
			

			return ICajaView._NOWAITFORACTION;

		case 8:
			vista.paintButtons(Tools.getBotones(-1));
			vista.hideAllEntries();
			vista.setEntryTitle("Pago Ventas en Contingencia", true);
			vista.setEntryTextArea("Origen: "	+ doc.getDatos().getStringValue("SistemaOrigenClaro")
								+ "\nRut Cliente: " 	+ doc.getDatos().getStringValue("Rut")
								+ "\nN° Documento / N° Nota Venta: " 	+ numeroDocumento
								//+ "\nNúmero Cuenta: "	+ doc.getDatos().getStringValue("CuentaClaro")
								+ "\nFecha Emisión: "	+ doc.getDatos().getStringValue("FechaVencimientoClaro")
								+ "\nFecha Vencimiento: " + doc.getDatos().getStringValue("FechaVencimientoClaro")
								+ "\nMonto a Pagar: " + Format.formatMonto(doc.getDatos().getLongValue("SaldoAdeudadoClaro")), true);
			estado = 9;
			return ICajaView._WAITFORACTION;

		case 9:
			estado = 10;
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
			posDat.setValue("TipoPagoContingencia","Venta");
			posDat.save();
			
			vista.paintButtons(Tools.getBotones(2));
			return ICajaView._NOWAITFORACTION;
			
		case 11:
			estado = 12;	  
			vista.setEntryTitle("Pago Ventas", true);
	        vista.setEntryMessage( "Ingrese Cuenta de la Nota Venta", true );
	        vista.setEntryTextLabel("Ingrese Cuenta :", true);
	        vista.setEntryText("", true, false, false,"[0-9]+","Cuenta no válida");
	        return ICajaView._WAITFORACTION;	           
        
		case 12:	            
			estado = 13;
			String cuenta = vista.getEntryText();
	        if(cuenta.equals("")){
	            estado = 12;
	            vista.setEntryMessage( "N° inválido - Ingrese nuevamente", true );
	            vista.setEntryTextLabel("Ingrese Cuenta :", true);
	            vista.setEntryText("", true, false, false,"[0-9]+","Cuenta no válida");
	            return ICajaView._WAITFORACTION;
	        }
	        
	        numeroCuenta = cuenta;
			return ICajaView._NOWAITFORACTION;
		
		case 13:
			estado = 4;
			vista.setEntryMessage( "Ingrese Nota Venta", true );
	        vista.setEntryTextLabel("Ingrese Nota Venta:", true);
	        vista.setEntryText("", true, false, false,"[0-9]+","Nota Venta no válida");
	        return ICajaView._WAITFORACTION;			
		}
		return 0;
	}
}
