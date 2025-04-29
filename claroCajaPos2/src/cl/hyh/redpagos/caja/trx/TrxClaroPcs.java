package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.AprobacionDescuentoRequestDTO;
import ws.claro.cl.AprobacionDescuentoResponseDTO;
import ws.claro.cl.proxy.AppControlConsultarProxy;
import ws.claro.cl.proxy.AppControlItemsOneProxy;
import ws.claro.cl.proxy.AppControlProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionOut;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Respuesta;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.OperacionOutHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.RespuestaHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOneServerProxy;
import cl.hyh.cajas.ws.impl.ConsultaDeudaVtrOut;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.cajas.ws.impl.DocumentoVTR;
import cl.hyh.cajas.ws.impl.ServicioVTR;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoItemClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoServicioVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoVtr;

public class TrxClaroPcs implements ITrxBase{

	int estado = 0;
	int index = 0;
	int indexCta=0;
	String codigo = "";
	Datos data;
	DocumentoPago doc;
	String []docs = null;
	ArrayList<Datos> clientes;
	ArrayList<Datos> detalle;
	ArrayList<Datos> detalleCut;
	ArrayList<Datos> clientesCut;
	String []clientesCuentas;
	String []clientesItems;
	protected Datos dataPagina;
	protected int nextPage = 0;
	protected ArrayList <Integer> paginas = new ArrayList<Integer>();
	Servicio consultaTelefonica = null;
	Servicio consultaTelefonicaDetalle = null;
	String tiposDocs = "";
	boolean offline = false;
	boolean unico = false;
	boolean isCut = false;
	Object [][]matrix = null;
	String pcs = "";
	String prod = "";
	ConsultaDeudaVtrOut out = null;

	// se setean como atributo DTO de salida del servicio App Control 
	OperacionOut opOut = null;
	OperacionOut opOutIt = null;
	cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.
	OperacionOut opOutItOne = null;

	CuentaVTR cuenta = null;
	DetalleDocumento cuentaDocClaro = null;

	ArrayList <String> prods = null;

	String trv = null;
	
    private static boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }

	@Override
	public int execute(ICajaView vista, int key, Datos htParam) {
		// TODO Auto-generated method stub

		if( key == 0 ) {
			estado = 0;
			vista.acceptEscape(true);
			/**
			 JOptionPane.showMessageDialog(null, "No se encuentra habilitada esta Operacion....", "Continuar", JOptionPane.INFORMATION_MESSAGE);
             return 13;
			 */
		} else if( key == 1 ) {
			// Timeout
			return 13;
		} else if( key == KeyEvent.VK_F1 ) {
			estado = 10;
		}
		else if( key == KeyEvent.VK_F2 && estado != 21) {
			estado = 20;
		}
		else if( key == KeyEvent.VK_F2 && estado == 21) {
			estado = 24;
		}
		else if( key == KeyEvent.VK_F3 && estado < 10 ) {
			estado = 30;
		}
		else if (key == KeyEvent.VK_F3 && estado >= 10  && estado < 20){
			estado = 130;
		}
		else if (key == KeyEvent.VK_F3 && estado == 25){
			estado = 233;
		}
		else if (key == KeyEvent.VK_F3 && estado >= 20 ){
			estado = 230;
		}
		else if( key == KeyEvent.VK_ENTER ) {
		} 
		else if( key == KeyEvent.VK_ESCAPE ) {
			return 20;
		}
		else if( key == KeyEvent.VK_F9 ) {
			if(estado < 10){
				return 20;
			}
			else if (estado == 11){
				estado = 2;
				index = 0;
			}
			else if (estado > 10 && estado < 20){
				estado = 10;
				index = 0;
			}
			else if (estado == 21){
				estado = 2;
				index = 0;
			}
			else if(estado > 20 && estado < 30){
				estado = 201;
				index = 0;
			}
		}
		else {
			// otra tecla. Lo que sea que esté en el XML...
			return ICajaView._PASSTHROUGH;
		}

		switch( estado ) {
		case 0:
			estado = 1;
			vista.setEntryMessage( "Ingrese Nro. PCS ", true );
			vista.setEntryTextLabel("Ingrese Nro. PCS :              56", true);
			vista.setEntryText("", true, false, false,null,null);
			return ICajaView._WAITFORACTION;

		case 1:
			String codigo = vista.getEntryText();
			// verificar la forma de validar un PCS ???
			if(codigo.equals("") || codigo.length() != 9 || !isNumeric(codigo)){
				estado = 1;
				vista.setEntryMessage( "El largo del PCS debe ser de 9 dígitos", true );
				vista.setEntryTextLabel("Ingrese Nro. PCS :              56", true);
				vista.setEntryText("", true, false, false,null,null);
				return ICajaView._WAITFORACTION;
			}
			
			pcs = "56" + codigo;
			//cbriones: se debe Implementar invocacion a servicio de Consulta OSB !!!
			PlataformaPagoConsultarServerProxy pr = AppControlConsultarProxy.getProxyInstance();

			// Generar los DTOs de entrada !!
			ParamSet pSet = Base.getParamSet("posDat");

			OperacionIn opIn = new OperacionIn();
			// Instanciar un Objeto Caja para OperacionIn
			Caja caja = new Caja();
			caja.setAgencia(pSet.getStringValue("Agencia"));
			Base.logger.info("Valor Agencia: "+  caja.getAgencia());
			caja.setIdCaja((int)(pSet.getLongValue("Caja")));
			Base.logger.info("Valor Id Caja: "+  caja.getIdCaja());
			caja.setEntidad(pSet.getStringValue("Entidad"));
			Base.logger.info("Valor Entidad: "+  caja.getEntidad());
			caja.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
			Base.logger.info("Valor Recaudador: "+  caja.getRecaudador());
			caja.setUsuario(pSet.getStringValue("Usuario"));
			Base.logger.info("Valor Usuario: "+  caja.getUsuario());
			caja.setCodigoSesion(new Long(pSet.getStringValue("SessionId")));
			Base.logger.info("Valor Session: "+  caja.getCodigoSesion());
			caja.setCanal(new Integer(pSet.getStringValue("Canal")).intValue());
			Base.logger.info("Valor Canal: "+  caja.getCanal());

			opIn.setCaja(caja);
			opIn.setTipoOperacion("lna"); // se debe realizar busuqeda por PCS !!
			Base.logger.info("Valor Tipo Operacion: "+  opIn.getTipoOperacion());
			opIn.setValorOperacion(pcs);
			Base.logger.info("Valor Operacion: "+  opIn.getValorOperacion());

			opIn.setOrigen(trv);
			Base.logger.info("Valor Origen Trx: "+  opIn.getOrigen());

			OperacionOutHolder opOutHold = new OperacionOutHolder();
			RespuestaHolder resp = new RespuestaHolder();

			vista.showBusyWindow("Consultando", "Espere por favor...");

			try {
				//out = pr.consultaDeudaVtr(in);
				pr.consultar(opIn, opOutHold, resp);
			} catch (RemoteException e2) {
				e2.printStackTrace();
				Base.logger.error("Error en la invocacion al Servicio: "+  e2.getMessage());
				vista.hideBusyWindow();
				JOptionPane.showMessageDialog(null, "Error de conexión", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				Tools.logStackTrace(Base.logger, e2);
				return 20;
			}

			vista.hideBusyWindow();

			// Instanciar DTO Respuesta para rescatar codigo de error !!
			Respuesta r = resp.value;
			// Instanciar DTO Respuesta Operacion para obtener la info !!
			opOut = opOutHold.value;


			//if(out.getHeaderOut().getRc() != 0){
			if(r.getRetCode() != 0){	
				Base.logger.error("No existen datos [RetCode]:" + r.getRetCode() + " [msg]:" + r.getRetDescError());
				JOptionPane.showMessageDialog(null, r.getRetDescError(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
				return 20;
			}

			estado = 2;
			return ICajaView._NOWAITFORACTION;

		case 2:
			// Botonera original !!
			//vista.paintButtons(Tools.getBotones(1));
			// Botonera de pruebas !!
			//TODO validar que si es VTV, se debe poder cargar Items, cualquier otro Sistema, NO..
			if("VTV".equalsIgnoreCase(trv)){
				vista.paintButtons(Tools.getBotones(1));
			}else{
				vista.paintButtons(Tools.getBotones(2));
			}

			estado = 3;

			// Se define el tamaño del vector de acuerdo a la cantidad de Documentos que retornen !!
			// TODO validar si debe rescatar Documentos o Cuentas ????
			//clientesCuentas = new String[out.getCuentas().length + 1];
			try{
			clientesCuentas = new String[opOut.getDetalleDocumentos().length + 1];
			vista.hideAllEntries();
			vista.setEntryTitle( "Consulta por PCS", true );

			// Se deben definir los campos a desplegar en la tabla ???
			//clientesCuentas[0] = String.format("%-13s %-13s %-12s %-13s", "Cuenta Unica", "Cuenta","Saldo","Dir.Cobranza");
			clientesCuentas[0] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-8s %-10s"
					, "Origen", "T. Doc", "Num Doc", "T. Reg.", "Servicio", "Cuenta", "Fec Venc", "Saldo");
			//, "Origen", "T. Doc", "Num Doc", "Servicio", "Cuenta", "Fec Emis", "Fec Venc", "Saldo");

			//for(int i = 0; i < out.getCuentas().length; i++){   
			for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){   	
				//String fecha = Tools.getFecha(out.getCuentas()[i].getFechaVencimiento().getTime());
				String fecha = opOut.getDetalleDocumentos()[i].getFechaEmisionDocumento();	
				//clientesCuentas[i + 1] = String.format("%13s %13s $%12s %13s",out.getCuentas()[i].getCuentaUnica() ,out.getCuentas()[i].getNumeroCuenta(),Format.formatMontoPantalla((long)out.getCuentas()[i].getSaldoCuenta()),out.getCuentas()[i].getDireccionCobranza());
				clientesCuentas[i + 1] = String.format("%7s %6s %10s %12s %10s %8s %8s %10s"
						, opOut.getDetalleDocumentos()[i].getSistemaOrigen()
						, opOut.getDetalleDocumentos()[i].getTipoDocumento()
						, opOut.getDetalleDocumentos()[i].getFolioDocumento()
						, Tools.getTipoRegistroVisual(opOut.getDetalleDocumentos()[i].getTipoRegistro())
						, opOut.getDetalleDocumentos()[i].getIdServicio()
						, opOut.getDetalleDocumentos()[i].getNumeroCuenta()
						//, opOut.getDetalleDocumentos()[i].getFechaEmisionDocumento().replace("-", "")
						, opOut.getDetalleDocumentos()[i].getFechaVencimientoDocumento().replace("-", "")
						, Format.formatMontoPantalla((long)opOut.getDetalleDocumentos()[i].getSaldoAdeudado()));
			}

			// se deben reemplazar los siguientes datos por los rescatados desde opOut !!
			//vista.setEntryMessage( "Nombre de Cliente: " + out.getCliente().getNombreCliente() + " | Rut: " + Tools.limpiarRut(out.getCliente().getRutCliente()), true );
			vista.setEntryMessage( "Nombre de Cliente: " + opOut.getDetalleDocumentos()[0].getNombreCliente() + " | Rut: " + opOut.getRutCliente() + "-" + opOut.getDvCliente(), true );

			//rut = opOut.getRutCliente() + "-" + opOut.getDvCliente();

			vista.setEntryList(clientesCuentas, true, false,index + 1);
			return ICajaView._WAITFORACTION;
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, "NO EXISTEN DOCUMENTOS", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				return 20;
			}

		case 3:       
			//cbriones: se comenta para pruebas flujo de Claro
			doc = null;
			index = vista.getEntryListIndex();

			if(index == 0){
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}else{
				index = index -1;
			}

			estado = 4;

			try {
				//doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaVtr");
				doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("No se pudo instanciar el documento");
				return 20;
			}

			//cuenta = out.getCuentas()[index];
			cuentaDocClaro = opOut.getDetalleDocumentos()[index];
			//cuentaDocClaro = opOut.getDetalleDocumentos(index);

			//((DocumentoCuentaVtr)doc).llenarDatos(cuenta);
			((DocumentoCuentaClaro)doc).llenarDatos(cuentaDocClaro);

			/**
              doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
              doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
              doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());
			 */

			doc.getDatos().setValue("Rut",opOut.getRutCliente()+"-"+opOut.getDvCliente());
			doc.getDatos().setValue("DvCliente",opOut.getDvCliente());
			doc.getDatos().setValue("RutCliente", opOut.getRutCliente());
			doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(index).getNombreCliente());


			return ICajaView._NOWAITFORACTION;


		case 4:
			// bloque de consulta si tiene descuentos asociados
			AppControlCajaWSServerProxy 	prx 		= AppControlProxy.getProxyInstance();
			AprobacionDescuentoRequestDTO 	request = new AprobacionDescuentoRequestDTO();
			String mensajeDescuento 		= "";
			try {
				
				request.setRut(Long.valueOf(doc.getDatos().getStringValue("RutCliente")));
				request.setCuenta(doc.getDatos().getStringValue("CuentaClaro"));
				request.setOrigen(doc.getDatos().getStringValue("SistemaOrigenClaro"));
				AprobacionDescuentoResponseDTO response = prx.obtenerAprobacionDescuento(request);
				if(response.getDescuento()!= null){
					String descuento = String.valueOf(response.getDescuento().intValue());
					mensajeDescuento = "\nDescuento: " + descuento + "%";
				}
					
			} catch (Exception e1) {
				
				e1.printStackTrace();
			
			}
			// fin bloque de descuento
			vista.paintButtons(Tools.getBotones(-1));
			vista.hideAllEntries();
			vista.setEntryTitle( "Consulta por PCS", true );
			vista.setEntryTextArea("Origen: " + doc.getDatos().getStringValue("SistemaOrigenClaro")
					+"\nTipo Documento: " + doc.getDatos().getStringValue("TipoDocumentoClaro")
					+"\nFolio: " + doc.getDatos().getStringValue("FolioDocumentoClaro")
					+"\nTipo Registro: " + Tools.getTipoRegistroVisual(doc.getDatos().getStringValue("TipoRegistroClaro"))
					+"\nId Servicio: " + doc.getDatos().getStringValue("IdServicioClaro")
					+"\nNúmero Cuenta: " + doc.getDatos().getStringValue("CuentaClaro")
					+"\nFecha Emisión: " + doc.getDatos().getStringValue("FechaEmisionClaro")
					+"\nFecha Vencimiento: " + doc.getDatos().getStringValue("FechaVencimientoClaro")
					+"\nSaldo: " + Format.formatMonto(doc.getDatos().getLongValue("SaldoAdeudadoClaro"))
					+mensajeDescuento, true);
			estado = 5;
			return ICajaView._WAITFORACTION;

		case 5:
			if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
				Base.logger.info("Documento ya existe en carro de compras");
				JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}

			// Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
			if(Base.getEdicion()){
				if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
					Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
				}else{
					Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
					JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					estado = 2;
					return ICajaView._NOWAITFORACTION;
				}
			}

			// Validar que el saldo adeudado no sea Cero !!
			if(doc.getDatos().getLongValue("SaldoAdeudadoClaro") == 0){
				Base.logger.info("El saldo del Doc. es Cero, no se agrega al carro....");
				JOptionPane.showMessageDialog(null, "El Saldo del Documento es Cero, No se agregará en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}


			// TODO aca se debe implementar la validacion para SC y ST de VTV !!!
			// Si el Doc es Deuda y existen Doc ST (Linea Crdto) y/o Doc SC (Saldo Castigado)
			if("".equalsIgnoreCase(trv) || "VTV".equalsIgnoreCase(trv)){
				// Si es DEUDA el doc que se quiere subir, se valida !!
				if("DEUDA".equalsIgnoreCase(doc.getDatos().getStringValue("TipoRegistroClaro")) && "VTV".equalsIgnoreCase(doc.getDatos().getStringValue("SistemaOrigenClaro"))){
					String cuentaDoc = doc.getDatos().getStringValue("CuentaClaro");
					// Se recorre la lista de docs para averiguar si hay SC o ST para esa cuenta
					for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){ 
						// Para el mismo numero de cuenta, 
						// Se debe validar que en el carro deben estar El doc ST y/o SC si existen !!!
						if(cuentaDoc.equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getNumeroCuenta())){
							for(int x = 0; x < opOut.getDetalleDocumentos().length; x++){ 
							// Si el Doc es Saldo Castigado, verificar que exista en el carro anteriormente
							if("SC".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getSistemaOrigen()) && opOut.getDetalleDocumentos()[x].getSaldoAdeudado() != 0 && cuentaDoc.equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getNumeroCuenta())){
								// validar que el Doc este en el carro previamente
								boolean flgSc = true;
								for(int j = 0; j < vista.getOperTRV().getCarroCompras().getDocumentos().size(); j++){

									if(vista.getOperTRV().getCarroCompras().getDocument(j).getCuentaClaro().equals(doc.getDatos().getStringValue("CuentaClaro")) &&
											vista.getOperTRV().getCarroCompras().getDocument(j).getTipoRegistro().equals("SC")){                
										// Existe un Doc Limite Credito en el Carro, para la Cuenta
										// Debe continuar el flujo Normal..
										flgSc = false;
									}
								}

								if(flgSc){
									// Si recorrio todo el carro, No Existe un Doc Limite Credito en el Carro, para la Cuenta
									// Debe retornar NO OK.. Debe cargar previamente el ST y/o el SC
									Base.logger.info("Existe una Deuda SC para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda");
									JOptionPane.showMessageDialog(null, "Existe una Deuda SC para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
									estado = 2;
									return ICajaView._NOWAITFORACTION;
								}
							}
							}
							
							
							// Si el Doc es Linea de Credito, verificar que exista en el carro anteriormente
							if("ST".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getSistemaOrigen()) && opOut.getDetalleDocumentos()[i].getSaldoAdeudado() != 0 && cuentaDoc.equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getNumeroCuenta())){
								// validar que el Doc este en el carro previamente
								boolean flgSt = true;
								for(int j = 0; j < vista.getOperTRV().getCarroCompras().getDocumentos().size(); j++){

									if(vista.getOperTRV().getCarroCompras().getDocument(j).getCuentaClaro().equals(doc.getDatos().getStringValue("CuentaClaro")) &&
											vista.getOperTRV().getCarroCompras().getDocument(j).getTipoRegistro().equals("ST")){                
										// Existe un Doc Limite Credito en el Carro, para la Cuenta
										// Debe continuar el flujo Normal..
										flgSt = false;
									}
								}
								/*
								if(flgSt){
									// Si recorrio todo el carro, No Existe un Doc Limite Credito en el Carro, para la Cuenta
									// Debe retornar NO OK.. Debe cargar previamente el ST y/o el SC
									Base.logger.info("Existe una Deuda ST para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda");
									JOptionPane.showMessageDialog(null, "Existe una Deuda ST para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
									estado = 2;
									return ICajaView._NOWAITFORACTION;
								}
								*/
							}


						}
					}
				}else{
					
					
					if("ST".equalsIgnoreCase(doc.getDatos().getStringValue("TipoRegistroClaro")) && "VTV".equalsIgnoreCase(doc.getDatos().getStringValue("SistemaOrigenClaro"))){
						String cuentaDoc = doc.getDatos().getStringValue("CuentaClaro");
						// Se recorre la lista de docs para averiguar si hay SC o ST para esa cuenta
						for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){ 
							// Para el mismo numero de cuenta, 
							// Se debe validar que en el carro deben estar El doc ST y/o SC si existen !!!
							if(cuentaDoc.equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getNumeroCuenta())){
								for(int x = 0; x < opOut.getDetalleDocumentos().length; x++){ 
								// Si el Doc es Saldo Castigado, verificar que exista en el carro anteriormente
								if("SC".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getSistemaOrigen()) && opOut.getDetalleDocumentos()[x].getSaldoAdeudado() != 0 ){//&& cuentaDoc.equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getNumeroCuenta())){
									// validar que el Doc este en el carro previamente
									boolean flgSc = true;
									for(int j = 0; j < vista.getOperTRV().getCarroCompras().getDocumentos().size(); j++){

										if(vista.getOperTRV().getCarroCompras().getDocument(j).getCuentaClaro().equals(doc.getDatos().getStringValue("CuentaClaro")) &&
												vista.getOperTRV().getCarroCompras().getDocument(j).getTipoRegistro().equals("SC")){                
											// Existe un Doc Limite Credito en el Carro, para la Cuenta
											// Debe continuar el flujo Normal..
											flgSc = false;
										}
									}

									if(flgSc){
										// Si recorrio todo el carro, No Existe un Doc Limite Credito en el Carro, para la Cuenta
										// Debe retornar NO OK.. Debe cargar previamente el ST y/o el SC
										Base.logger.info("Existe una Deuda SC para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda");
										JOptionPane.showMessageDialog(null, "Existe una Deuda SC para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
										estado = 2;
										return ICajaView._NOWAITFORACTION;
									}
								}
								}
								
								
								// Si el Doc es Linea de Credito, verificar que exista en el carro anteriormente
								if("SC".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getSistemaOrigen()) && opOut.getDetalleDocumentos()[i].getSaldoAdeudado() != 0 && cuentaDoc.equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getNumeroCuenta())){
									// validar que el Doc este en el carro previamente
									boolean flgSt = true;
									for(int j = 0; j < vista.getOperTRV().getCarroCompras().getDocumentos().size(); j++){

										if(vista.getOperTRV().getCarroCompras().getDocument(j).getCuentaClaro().equals(doc.getDatos().getStringValue("CuentaClaro")) &&
												vista.getOperTRV().getCarroCompras().getDocument(j).getTipoRegistro().equals("SC")){                
											// Existe un Doc Limite Credito en el Carro, para la Cuenta
											// Debe continuar el flujo Normal..
											flgSt = false;
										}
									}

									if(flgSt){
										// Si recorrio todo el carro, No Existe un Doc Limite Credito en el Carro, para la Cuenta
										// Debe retornar NO OK.. Debe cargar previamente el ST y/o el SC
										Base.logger.info("Existe una Deuda ST para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda");
										JOptionPane.showMessageDialog(null, "Existe una Deuda ST para la cuenta: "+cuentaDoc+" Debe cancelar esta, junto con la Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
										estado = 2;
										return ICajaView._NOWAITFORACTION;
									}
								}


							}
						}
					}
					
				}
				
			}

			////////////////////////////////////

			vista.hideAllEntries();

			try {
				vista.getOperTRV().addDocumentoPago(doc);
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("Error en agregar documento de pago al carro");
			}

			doc.getDatos().show("Cuenta Claro");

			vista.getOperTRV().getCarroCompras();

			// TODO cbriones: se setean los botones para opcion 2 !!
			//vista.paintButtons(Tools.getBotones(1));
			vista.paintButtons(Tools.getBotones(2));
			estado = 2;
			return ICajaView._NOWAITFORACTION;

		case 30:
			doc = null;
			index = vista.getEntryListIndex();
			if(index == 0){
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}
			else{
				index = index -1;
			}
			cuenta = out.getCuentas()[index];
			for(int i = 0 ; i < out.getCuentas().length ; i++){
				if(cuenta.getCuentaUnica().equals(out.getCuentas()[i].getCuentaUnica()) && !out.getCuentas()[i].getCuentaUnica().equals("0")){
					CuentaVTR aux = out.getCuentas()[i];
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("No se pudo instanciar el documento");
						return 20;
					}
					((DocumentoCuentaVtr)doc).llenarDatos(aux);
					doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
					doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
					doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());
					if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
						Base.logger.info("Documento ya existe en carro de compras");
						JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
						estado = 2;
						return ICajaView._NOWAITFORACTION;
					}
					vista.hideAllEntries();
					try {
						vista.getOperTRV().addDocumentoPago(doc);
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("Error en agregar documento de pago al carro");
					}
					doc.getDatos().show("Cuenta VTR");
				}
			}               
			vista.paintButtons(Tools.getBotones(1));
			estado = 2;
			return ICajaView._NOWAITFORACTION;

		case 10:
			//vista.paintButtons(Tools.getBotones(1)); 
			vista.paintButtons(Tools.getBotones(2));
			estado = 11;
			//clientesCuentas = new String[out.getDocumentos().length + 1];
			clientesCuentas = new String[opOut.getDetalleDocumentos().length + 1];
			vista.hideAllEntries();
			vista.setEntryTitle( "Consulta por Pcs - Detalle Documentos", true );
			clientesCuentas[0] = String.format("%-13s %-5s %-13s %-13s %-12s", "Cuenta", "Tipo", "N° Documento", "F.Vencimiento", "Saldo Neto");

			//for(int i = 0; i < out.getDocumentos().length; i++){
			for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){
				//DocumentoVTR aux = out.getDocumentos()[i];
				DetalleDocumento aux = opOut.getDetalleDocumentos(index);
				String fecha = aux.getFechaEmisionDocumento();
				clientesCuentas[i + 1] = String.format(" %13s %5s %13s %13s $%12s", aux.getNumeroCuenta() , aux.getTipoDocumento(), "No trae !!", aux.getFechaVencimientoDocumento(), Format.formatMontoPantalla((long)aux.getSaldoNeto()));
			}

			vista.setEntryList(clientesCuentas, true, false,index + 1);

			return ICajaView._WAITFORACTION;

		case 11:                
			doc = null;
			index = vista.getEntryListIndex();

			if(index == 0){
				estado = 10;
				return ICajaView._NOWAITFORACTION;
			}else{
				index = index -1;
			}

			estado = 12;

			try {
				//doc = FactoryDocumentoPago.makeInstance("DocumentoVtr");
				doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("No se pudo instanciar el documento");
				return 20;
			}

			//              DocumentoVTR documento = out.getDocumentos()[index];
			//              ((DocumentoVtr)doc).llenarDatos(documento);

			DetalleDocumento documentoClaro = opOut.getDetalleDocumentos(index);
			((DocumentoCuentaClaro)doc).llenarDatos(documentoClaro);

			//              doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
			//              doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
			//              doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());

			doc.getDatos().setValue("Rut",opOut.getRutCliente()+"-"+opOut.getDvCliente());
			doc.getDatos().setValue("DvCliente",opOut.getDvCliente());
			doc.getDatos().setValue("RutCliente", opOut.getRutCliente());
			doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(index).getNombreCliente());

			return ICajaView._NOWAITFORACTION;

		case 12:
			vista.paintButtons(Tools.getBotones(-1));
			vista.hideAllEntries();
			vista.setEntryTitle( "Consulta por Pcs - Detalle Documentos", true );
			vista.setEntryTextArea("Numero Documento: " + doc.getDatos().getStringValue("NumeroDocumento")
					+"\nNumero Cuenta: " + doc.getDatos().getStringValue("NumeroCuenta")
					+"\nSaldo: " + Format.formatMonto(doc.getDatos().getLongValue("Monto"))
					+"\nFecha Vencimiento: " + doc.getDatos().getStringValue("FechaVencimiento")
					, true);
			estado = 13;
			return ICajaView._WAITFORACTION;

		case 13:
			if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
				Base.logger.info("Documento ya existe en carro de compras");
				JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				estado = 10;
				return ICajaView._NOWAITFORACTION;
			}
			// Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
			if(Base.getEdicion()){
				if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
					Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
				}else{
					Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
					JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					estado = 10;
					return ICajaView._NOWAITFORACTION;
				}
			}

			vista.hideAllEntries();
			try {
				vista.getOperTRV().addDocumentoPago(doc);
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("Error en agregar documento de pago al carro");
			}
			doc.getDatos().show("Documento VTR");
			vista.paintButtons(Tools.getBotones(2));
			estado = 10;
			return ICajaView._NOWAITFORACTION;

		case 130:
			doc = null;
			index = vista.getEntryListIndex();
			if(index == 0){
				estado = 10;
				return ICajaView._NOWAITFORACTION;
			}
			else{
				index = index -1;
			}

			DocumentoVTR documento = out.getDocumentos()[index];
			for(int i = 0 ; i < out.getDocumentos().length ; i++){
				if(documento.getCuentaUnica().equals(out.getDocumentos()[i].getCuentaUnica()) && !out.getDocumentos()[i].getCuentaUnica().equals("0")){
					DocumentoVTR aux = out.getDocumentos()[i];
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("No se pudo instanciar el documento");
						return 20;
					}
					((DocumentoVtr)doc).llenarDatos(aux);
					doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
					doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
					doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());
					if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
						Base.logger.info("Documento ya existe en carro de compras");
						JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
						estado = 10;
						return ICajaView._NOWAITFORACTION;
					}
					vista.hideAllEntries();
					try {
						vista.getOperTRV().addDocumentoPago(doc);
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("Error en agregar documento de pago al carro");
					}
					doc.getDatos().show("Documento VTR");
				}
			}               
			vista.paintButtons(Tools.getBotones(2));
			estado = 10;
			return ICajaView._NOWAITFORACTION;

		case 20:
			index = vista.getEntryListIndex();

			if(index == 0){
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}else{
				index = index -1;
				indexCta = index;
			}

			//cuenta = out.getCuentas()[index];
			cuentaDocClaro = opOut.getDetalleDocumentos()[index];
			estado = 201;
			return ICajaView._NOWAITFORACTION;

		case 201:            	
			// Esta es la botonera original !!!!
			// vista.paintButtons(Tools.getBotones(3));
			vista.paintButtons(Tools.getBotones(2));
			estado = 21;
			//index = 0;
			int cant = 0;
			prods = new ArrayList<String>();
			//Para saber si la consulta se realizo bien
			int codReturn = 0;
			String descReturn = "";

			//for(int i = 0 ; i < out.getServicios().length; i++){

			// TODO Validar que cumpla con la validacion del periodo antes de poder rescatar sus Items ..
			ParamSet pSetVal = Base.getParamSet( "posCfg" );
			String dias = pSetVal.getStringValue("diasVencimiento");
			Base.logger.info("Dias a sumar para validacion de Items: "+ dias);
			String []fechaVenc = cuentaDocClaro.getFechaVencimientoDocumento().split("-");
			Base.logger.info("Fecha de Vencimiento del Documento: "+ fechaVenc[2] +"-"+ fechaVenc[1] +"-"+ fechaVenc[0]);

			Calendar fechaV = Calendar.getInstance();
			fechaV.set(new Integer(fechaVenc[0]).intValue(),new Integer(fechaVenc[1]).intValue()-1,new Integer(fechaVenc[2]).intValue()); //Año, Mes -1, dia
			fechaV.add(Calendar.DATE, new Integer(dias).intValue());

			String DATE_FORMAT = "dd-MM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Date c1 = fechaV.getTime();
			Date c2 = new Date();

			Base.logger.info("Fecha Vencimiento mas dias del Parametro: "+sdf.format(c1.getTime()));
			Base.logger.info("Fecha Actual para comparar: "+sdf.format(c2.getTime()));

			if (c2.before(c1) || c2.equals(c1)) {
				Base.logger.error("Fecha Actual es menor o igual a la Fecha Vencimiento....");
				JOptionPane.showMessageDialog(null, "No se permite cargar Items para este Documento", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				estado = 2;
				return ICajaView._NOWAITFORACTION;

			}else{
				Base.logger.error("Fecha Actual es Mayor a la Fecha Vencimiento....");
				// TODO Aca se debe invocar a la Consulta para traer los Items asociados al Documento seleccionado.
				//VTV Consulta por Items
				PlataformaPagoConsultarServerProxy prIt = AppControlConsultarProxy.getProxyInstance();

				//ONE Consulta por Items
				PlataformaPagoConsultarItemsOneServerProxy prItOne = AppControlItemsOneProxy.getProxyInstance();

				// Generar los DTOs de entrada !!
				//ConsultaDeudaRutVTRIn in = new ConsultaDeudaRutVTRIn();
				ParamSet pSetIt = Base.getParamSet("posDat");

				//VTV
				OperacionIn opInIt = new OperacionIn();
				OperacionOutHolder opOutHoldIt = new OperacionOutHolder();
				RespuestaHolder respIt = new RespuestaHolder();

				//ONE
				cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.
				OperacionIn opInItOne = new cl.clarochile.osbservicios.
				PlataformaPagoConsultarItemsOne.OperacionIn();

				cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.
				holders.OperacionOutHolder opOutHoldItOne = new cl.clarochile.
				osbservicios.PlataformaPagoConsultarItemsOne.holders.OperacionOutHolder();

				cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.
				holders.RespuestaHolder respItOne = new cl.clarochile.osbservicios
				.PlataformaPagoConsultarItemsOne.holders.RespuestaHolder();

				trv = cuentaDocClaro.getSistemaOrigen();

				Base.logger.info("trv Origen Consulta Item : " + trv);

				if("VTV".equalsIgnoreCase(trv)){
					// Instanciar un Objeto Caja para OperacionIn
					Caja cajaIt = new Caja();
					cajaIt.setAgencia(pSetIt.getStringValue("Agencia"));
					Base.logger.info("Valor Agencia (Item): "+  cajaIt.getAgencia());
					cajaIt.setIdCaja((int)(pSetIt.getLongValue("Caja")));
					Base.logger.info("Valor Id Caja (Item): "+  cajaIt.getIdCaja());
					cajaIt.setEntidad(pSetIt.getStringValue("Entidad"));
					Base.logger.info("Valor Entidad (Item): "+  cajaIt.getEntidad());
					cajaIt.setRecaudador(pSetIt.getStringValue("CodigoRecaudador"));
					Base.logger.info("Valor Recaudador (Item): "+  cajaIt.getRecaudador());
					cajaIt.setUsuario(pSetIt.getStringValue("Usuario"));
					Base.logger.info("Valor Usuario (Item): "+  cajaIt.getUsuario());
					cajaIt.setCodigoSesion(new Long(pSetIt.getStringValue("SessionId")));
					Base.logger.info("Valor Session (Item): "+  cajaIt.getCodigoSesion());
					cajaIt.setCanal(new Integer(pSetIt.getStringValue("Canal")).intValue());
					Base.logger.info("Valor Canal (Item): "+  cajaIt.getCanal());
	
					opInIt.setCaja(cajaIt);
					opInIt.setTipoOperacion("DOCDETALLE"); // se setea el tipo de busqueda que debe realizar por Documento..para traer sus Items..
					Base.logger.info("Valor Tipo Operacion (Item): "+  opInIt.getTipoOperacion());
					opInIt.setTipoDocumento(opOut.getDetalleDocumentos()[index].getTipoDocumento());
					Base.logger.info("Valor Tipo Documento (Item): "+  opInIt.getTipoDocumento());
					//opInIt.setValorOperacion(""+opOut.getDetalleDocumentos()[index].getFolioDocumento());
					opInIt.setValorOperacion(""+opOut.getDetalleDocumentos()[index].getIdServicio());
					Base.logger.info("Valor Operacion (Item): "+  opInIt.getValorOperacion());				
					//opInIt.setOrigen(trv);
					Base.logger.info("Valor Origen Trx (Item): "+  opInIt.getOrigen());
					//TODO se setea en Tipo de registro el tipo de Doc para consulta !!
					opInIt.setTipoRegistro("DOCDETALLE");
					Base.logger.info("Tipo Registro Trx (Item): "+  opInIt.getTipoRegistro());
					opInIt.setOrigen("VTV");
					Base.logger.info("Valor origen (Item): " + opInIt.getOrigen());
				} else {
					opInItOne.setOrigen("ONE");
					Base.logger.info("Valor origen (Item): "+ opInItOne.getOrigen());
					opInItOne.setTipoOperacion("DOCDETALLE");
					Base.logger.info("Valor tipo operacion (Item): "+ opInItOne.getTipoOperacion());
					opInItOne.setValorOperacion(opOut.getDetalleDocumentos()[index].getIdServicio());
					Base.logger.info("Valor valor operacion (Item): "+ opInItOne.getValorOperacion());
				}

				vista.showBusyWindow("Consultando", "Espere por favor...");

				try {
					 //Consulta Item VTV
					if("VTV".equalsIgnoreCase(trv)){
						Base.logger.info("Llamando al Servicio de Vantive...");
						prIt.consultar(opInIt, opOutHoldIt, respIt);
						
						// Instanciar DTO Respuesta para rescatar codigo de error !!
						Respuesta rIt = respIt.value;
						Base.logger.info("Codigo respuesta item: "+  rIt.getRetCode());
						Base.logger.info("Descripcion respuesta item: "+  rIt.getRetDescError());
						// Instanciar DTO Respuesta Operacion para obtener la info !!
						opOutIt = opOutHoldIt.value;
						//codigos de retorno
						codReturn = rIt.getRetCode();
						descReturn = rIt.getRetDescError();

					}else{ //Consulta Item ONE
						Base.logger.info("Llamando al Servicio de ONE...");
						prItOne.consultar(opInItOne, opOutHoldItOne, respItOne);
						
						// Instanciar DTO Respuesta para rescatar codigo de error !!
						cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.
						Respuesta rItOne = respItOne.value;
						Base.logger.info("Codigo respuesta item: "+  rItOne.getRetCode());
						Base.logger.info("Descripcion respuesta item: "+  rItOne.getRetDescError());
						// Instanciar DTO Respuesta Operacion para obtener la info !!
						opOutItOne = opOutHoldItOne.value;
						//codigos de retorno
						codReturn = rItOne.getRetCode();
						descReturn = rItOne.getRetDescError();
						
						//Mapeo Productos ONE a estructura VTV						
						opOutIt = new OperacionOut();
						
						if(codReturn == 0 && opOutItOne.getDetalleProducto() != null
								&& opOutItOne.getDetalleProducto().length != 0) {
							
							Base.logger.info("Se encontraron : " + opOutItOne.getDetalleProducto().length + "item(s)");
							
						int cont = 0;
						DetalleProducto[] detalleProdList = new DetalleProducto[opOutItOne.getDetalleProducto().length];
						for (cl.clarochile.osbservicios.
								PlataformaPagoConsultarItemsOne.
								DetalleProducto dpOne : opOutItOne.getDetalleProducto()) {
							
							DetalleProducto dpAux = new DetalleProducto();
							
							dpAux.setCodigoItem(dpOne.getIdServicio());
							dpAux.setCodigoSubItem(dpOne.getIdServicio());
							dpAux.setDescripcionItem(dpOne.getTipoDocumento());
							dpAux.setDescripcionSubItem(dpOne.getTipoDocumento());
							dpAux.setIndicadorImpugnacion(null);
							dpAux.setMontoItem(dpOne.getSaldoAdeudado());
							dpAux.setMontoSubItem(dpOne.getSaldoAdeudado());
							dpAux.setTipoItem(dpOne.getTipoDocumento());
							
							detalleProdList[cont] = dpAux;
							cont++;
						}
						opOutIt.setDetalleProductos(detalleProdList);
						}
					}
					//prIt.consultar(opInIt, opOutHoldIt, respIt);
				} catch (RemoteException e2) {
					e2.printStackTrace();
					Base.logger.error("Error en la invocacion al Servicio: "+  e2.getMessage());
					vista.hideBusyWindow();
					JOptionPane.showMessageDialog(null, "Error de conexión", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					Tools.logStackTrace(Base.logger, e2);
					return 20;
				}

				vista.hideBusyWindow();

				if(codReturn != 0){	
					Base.logger.error("No existen datos [RetCode]:" + codReturn + " [msg]:" + descReturn);
					JOptionPane.showMessageDialog(null, descReturn, "Continuar", JOptionPane.INFORMATION_MESSAGE);
					//return 20;
					estado = 2;
					return ICajaView._NOWAITFORACTION;
				}
				
				estado = 202;
				return ICajaView._NOWAITFORACTION;

			}

		case 202:

			Base.logger.info("Comenzando a obtener Listado de Productos (Item)...");
			estado = 21;

			if(opOutIt.getDetalleProductos() == null){	
				Base.logger.error("No existen Items para la cuenta");
				JOptionPane.showMessageDialog(null, "No existen Items para la cuenta", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				//return 20;
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}

			if(opOut.getDetalleDocumentos(indexCta).getSaldoAdeudado() <= 0) {
				Base.logger.error("El documento ya fue pagado. No quedan Items por pagar.");
				JOptionPane.showMessageDialog(null, "El documento ya fue pagado. No quedan Items por pagar.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				//return 20;
				estado = 2;
				return ICajaView._NOWAITFORACTION;
			}

			for(int i = 0 ; i < opOutIt.getDetalleProductos().length; i++){

				DetalleProducto producto = opOutIt.getDetalleProductos()[i];
				Base.logger.info("Producto (Codigo Item): "+  producto.getCodigoItem());
				Base.logger.info("Producto (Desc. Item): "+  producto.getDescripcionItem());
				Base.logger.info("Producto (Monto Item): "+  producto.getMontoItem());
				Base.logger.info("Producto (Tipo Item): "+  producto.getTipoItem());

				//DetalleCuenta producto = opOut.getDetalleCuentas(i);
				prods.add(producto.getCodigoItem());

			}

			//clientesCuentas = new String[cant + 1];
			clientesItems = new String[opOutIt.getDetalleProductos().length + 1];
			vista.hideAllEntries();
			vista.setEntryTitle( "Consulta por Rut - Detalle Items", true );
			//clientesCuentas[0] = String.format("%-13s %-6s %-13s %-13s %-12s %-13s", "Tipo Producto","Estado","Cuenta Unica", "Cuenta","Saldo","Dir.Cobranza");
			clientesItems[0] = String.format("%-10s %-10s %-35s %-6s %-12s ", "Nro. Doc", "Cod. Item", "Desc. Item", "Tipo", "Saldo");

			// TODO validar implementacion para Claro !!
			for(int j = 0 ; j < prods.size(); j++){
				long montoProd = 0;

				/**
              	ServicioVTR aux = null;
              	ServicioVTR last = null;
				 */
				DetalleProducto auxIt = null;
				DetalleProducto last = null;

				for(int i = 0; i < opOutIt.getDetalleProductos().length; i++){
					auxIt = opOutIt.getDetalleProductos()[i];

					clientesItems[i + 1] = String.format("%10s %10s %35s %6s $%12s ",cuentaDocClaro.getFolioDocumento(),auxIt.getCodigoItem(),auxIt.getDescripcionItem(),auxIt.getTipoItem(),Format.formatMontoPantalla(auxIt.getMontoItem()));
				}

				//clientesCuentas[j + 1] = String.format("%13s %6s %13s %13s $%12s %13s",last.getProducto(),last.getEstadoServicio(),last.getCuentaUnica(),last.getNumeroCuenta(),Format.formatMontoPantalla(montoProd),cuenta.getDireccionCobranza());

				// clientesCuentas[j + 1] = String.format("%13s %6s %13s %13s $%12s %13s",aux.getCodigoItem(),aux.getDescripcionItem(),aux.getIndicadorImpugnacion(),cuentaDocClaro.getFolioDocumento(),Format.formatMontoPantalla(montoProd),"Sin Direcc.");
			}

			//vista.setEntryList(clientesCuentas, true, false,index + 1);
			vista.setEntryList(clientesItems, true, false, 0);

			return ICajaView._WAITFORACTION;

		case 21:
			doc = null;
			index = vista.getEntryListIndex();

			if(index == 0){
				estado = 201;
				return ICajaView._NOWAITFORACTION;
			}else{
				index = index -1;
			}

			//estado = 201;
			estado = 22;

			// TODO cbriones: aca se debe generar un Doc para Items de Claro..

			// for(int i = 0 ; i < out.getServicios().length ; i++){
			// for(int i = 0 ; i < opOutIt.getDetalleProductos().length ; i++){

			//if(!prods.get(index).equals(out.getServicios()[i].getProducto()))

			/**
              	if(!prods.get(index).equals(opOutIt.getDetalleProductos()[i].getCodigoItem()))
              		continue;

              	String cUnicaOut = out.getServicios()[i].getCuentaUnica();
              	String cUnicaProd = cuenta.getCuentaUnica();

              	if(cUnicaOut.equals("0") && cUnicaProd.equals("0")){
              		cUnicaOut = Long.toString(out.getServicios()[i].getNumeroCuenta());
              		cUnicaProd = Long.toString(cuenta.getNumeroCuenta());
              	}
			 */

			//if( cUnicaProd.equals(cUnicaOut) ){

			//ServicioVTR aux = out.getServicios()[i];
			DetalleProducto aux = opOutIt.getDetalleProductos(index);

			try {
				doc = FactoryDocumentoPago.makeInstance("DocumentoItemClaro");
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("No se pudo instanciar el documento");
				return 20;
			}
			
			((DocumentoItemClaro)doc).llenarDatos(aux);
			
			// item puede ser VTV o ONE
			doc.getDatos().setValue("SistemaOrigen",cuentaDocClaro.getSistemaOrigen());
			
			doc.getDatos().setValue("Rut",opOut.getRutCliente()+"-"+opOut.getDvCliente());
			doc.getDatos().setValue("RutCliente", Tools.limpiarRut(opOut.getRutCliente()));
			doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(0).getNombreCliente());
			doc.getDatos().setValue("CodigoEmpresa", opOut.getDetalleDocumentos(0).getCodigoEmpresa());

			doc.getDatos().setValue("NumeroDocumento", cuentaDocClaro.getFolioDocumento());
			doc.getDatos().setValue("NumeroCuenta", cuentaDocClaro.getNumeroCuenta());
			doc.getDatos().setValue("NumeroServicio", cuentaDocClaro.getIdServicio());
			doc.getDatos().setValue("IdentificadorServicio", cuentaDocClaro.getIdServicio());

			doc.getDatos().setValue("FechaVencimiento", cuentaDocClaro.getFechaVencimientoDocumento());
			doc.getDatos().setValue("CodigoEmpresa", cuentaDocClaro.getCodigoEmpresa());
			doc.getDatos().setValue("TipoRegistro", cuentaDocClaro.getTipoRegistro());
			doc.getDatos().setValue("TipoDocumento", cuentaDocClaro.getTipoDocumento());

			/*********************************************************
                      if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
                          Base.logger.info("Documento ya existe en carro de compras");
                          JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                          estado = 201;
                          return ICajaView._NOWAITFORACTION;
                      }

                      vista.hideAllEntries();
                      try {
                          vista.getOperTRV().addDocumentoPago(doc);
                      } catch (BaseException e) {
                          Tools.logStackTrace(Base.logger, e);
                          Base.logger.error("Error en agregar documento de pago al carro");
                      }

                      doc.getDatos().show("Servicio Claro");
			 ********************************************************************/
			//}                		

			// }

			return ICajaView._NOWAITFORACTION;

		case 22:
			vista.paintButtons(Tools.getBotones(-1));
			vista.hideAllEntries();
			vista.setEntryTitle( "Consulta por Pcs - Detalle Servicio", true );
			vista.setEntryTextArea("Desc. item: " + doc.getDatos().getStringValue("DescripcionItem")
					+"\nNumero Item: " + doc.getDatos().getStringValue("NumeroItem")
					+"\nTipo Item: " + doc.getDatos().getStringValue("TipoItem")
					+"\nNumero Cuenta: " + doc.getDatos().getStringValue("NumeroCuenta")
					//+"\nEstado Servicio: " + doc.getDatos().getStringValue("EstadoServicio")
					+"\nSaldo: " + Format.formatMonto(doc.getDatos().getLongValue("Monto"))
					, true);
			estado = 23;
			return ICajaView._WAITFORACTION;

		case 23:
			if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
				Base.logger.info("Documento ya existe en carro de compras");
				JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				 estado = 241;
				//estado = 202;
				return ICajaView._NOWAITFORACTION;
			}

			// Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
			if(Base.getEdicion()){
				if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
					Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
				}else{
					Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
					JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					estado = 202;
					return ICajaView._NOWAITFORACTION;
				}
			}
			vista.hideAllEntries();
			try {
				vista.getOperTRV().addDocumentoPago(doc);
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("Error en agregar documento de pago al carro");
			}

			doc.getDatos().show("Servico Claro");
			vista.paintButtons(Tools.getBotones(2));
			index = 0;
			// estado = 241;
			estado = 202;
			return ICajaView._NOWAITFORACTION;

		case 24:
			estado = 241;
			index = vista.getEntryListIndex();
			if(index == 0){
				estado = 201;
				return ICajaView._NOWAITFORACTION;
			}
			else{
				index = index -1;
			}
			prod = prods.get(index);

		case 241:
			estado = 25;
			cant = 0;
			for(int i = 0 ; i < out.getServicios().length ; i++){
				if(!prod.equals(out.getServicios()[i].getProducto()))
					continue;
				if(!cuenta.getCuentaUnica().equals("0")){
					if(cuenta.getCuentaUnica().equals(out.getServicios()[i].getCuentaUnica())){
						cant++;
					}    
				}
				else{
					if(cuenta.getNumeroCuenta() == out.getServicios()[i].getNumeroCuenta()){
						cant++;
					}
				}
			}
			if(cant == 0 ){
				estado = 201;
				return ICajaView._NOWAITFORACTION;
			}
			clientesCuentas = new String[cant + 1];
			vista.hideAllEntries();
			int posArray = 0;
			vista.setEntryTitle( "Consulta por Pcs - Detalle Servicios", true );
			clientesCuentas[0] = String.format("%-13s %-6s %-13s %-13s %-12s", "Tipo Servicio","Estado","Servicio", "Cuenta","Saldo");
			for(int i = 0; i < out.getServicios().length; i++){
				ServicioVTR auxvtr = out.getServicios()[i];
				if(!auxvtr.getCuentaUnica().equals("0")){
					if(!auxvtr.getCuentaUnica().equals(cuenta.getCuentaUnica()) || !auxvtr.getProducto().equals(prod)){
						continue;
					} 
				}
				else{
					if(auxvtr.getNumeroCuenta() != cuenta.getNumeroCuenta() || !auxvtr.getProducto().equals(prod)){
						continue;
					} 
				}
				clientesCuentas[posArray + 1] = String.format("%13s %6s %13s %13s $%12s",auxvtr.getProducto(),auxvtr.getEstadoServicio(),auxvtr.getNumeroServicio(),auxvtr.getNumeroCuenta(),Format.formatMontoPantalla((long)auxvtr.getSaldoServicio()));
				posArray++;
			}
			vista.paintButtons(Tools.getBotones(4));
			vista.setEntryList(clientesCuentas, true, false,1);
			return ICajaView._WAITFORACTION;

		case 25:
			doc = null;
			index = vista.getEntryListIndex();
			if(index == 0){
				estado = 241;
				return ICajaView._NOWAITFORACTION;
			}
			else{
				index = index -1;
			}
			estado = 22;
			try {
				doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("No se pudo instanciar el documento");
				return 20;
			}
			int pos = 0;
			ServicioVTR sel = null;
			for(int i = 0 ; i < out.getServicios().length ; i++){
				ServicioVTR auxvtr = out.getServicios()[i];
				if(!auxvtr.getCuentaUnica().equals("0")){
					if(!auxvtr.getCuentaUnica().equals(cuenta.getCuentaUnica()) || !auxvtr.getProducto().equals(prod)){
						continue;
					}
				}
				else{
					if(auxvtr.getNumeroCuenta() != cuenta.getNumeroCuenta() || !auxvtr.getProducto().equals(prod)){
						continue;
					}
				}
				if(pos == index){
					sel = auxvtr;
					break;
				}
				else{
					pos++;
				}
			}
			ServicioVTR producto = sel;
			((DocumentoServicioVtr)doc).llenarDatos(producto);
			doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
			doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
			doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());

			return ICajaView._NOWAITFORACTION;     

		case 233:
			doc = null;
			index = vista.getEntryListIndex();
			if(index == 0){
				estado = 241;
				return ICajaView._NOWAITFORACTION;
			}
			else{
				index = index -1;
			}
			pos = 0;
			sel = null;
			for(int i = 0 ; i < out.getServicios().length ; i++){
				ServicioVTR auxvtr = out.getServicios()[i];
				if(!auxvtr.getCuentaUnica().equals("0")){
					if(!auxvtr.getCuentaUnica().equals(cuenta.getCuentaUnica()) || !auxvtr.getProducto().equals(prod)){
						continue;
					}
				}
				else{
					if(auxvtr.getNumeroCuenta() != cuenta.getNumeroCuenta() || !auxvtr.getProducto().equals(prod)){
						continue;
					}
				}
				if(pos == index){
					sel = auxvtr;
					break;
				}
				else{
					pos++;
				}
			}
			producto = sel;
			for(int i = 0 ; i < out.getServicios().length ; i++){
				String cUnicaOut = out.getServicios()[i].getCuentaUnica();
				String cUnicaProd = producto.getCuentaUnica();
				if(cUnicaOut.equals("0") && cUnicaProd.equals("0")){
					cUnicaOut = Long.toString(out.getServicios()[i].getNumeroCuenta());
					cUnicaProd = Long.toString(producto.getNumeroCuenta());
				}
				if(cUnicaOut.equals(cUnicaProd) && producto.getProducto().equals(out.getServicios()[i].getProducto())){
					ServicioVTR auxvtr = out.getServicios()[i];
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("No se pudo instanciar el documento");
						return 20;
					}
					((DocumentoServicioVtr)doc).llenarDatos(auxvtr);
					doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
					doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
					doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());
					if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
						Base.logger.info("Documento ya existe en carro de compras");
						JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
						estado = 241;
						return ICajaView._NOWAITFORACTION;
					}
					vista.hideAllEntries();
					try {
						vista.getOperTRV().addDocumentoPago(doc);
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("Error en agregar documento de pago al carro");
					}
					doc.getDatos().show("Servicio VTR");
				}
			}               
			vista.paintButtons(Tools.getBotones(2));
			estado = 241;
			return ICajaView._NOWAITFORACTION;

		case 230:
			doc = null;
			index = vista.getEntryListIndex();
			if(index == 0){
				estado = 201;
				return ICajaView._NOWAITFORACTION;
			}
			else{
				index = index -1;
			}
			producto = out.getServicios()[index];
			for(int i = 0 ; i < out.getServicios().length ; i++){
				if(producto.getCuentaUnica().equals(out.getServicios()[i].getCuentaUnica()) && !out.getServicios()[i].getCuentaUnica().equals("0")){
					ServicioVTR auxvtr = out.getServicios()[i];
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("No se pudo instanciar el documento");
						return 20;
					}
					((DocumentoServicioVtr)doc).llenarDatos(auxvtr);
					doc.getDatos().setValue("Rut", out.getCliente().getRutCliente());
					doc.getDatos().setValue("RutCliente", Tools.limpiarRut(out.getCliente().getRutCliente()));
					doc.getDatos().setValue("Cliente", out.getCliente().getNombreCliente());
					if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
						Base.logger.info("Documento ya existe en carro de compras");
						JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
						estado = 201;
						return ICajaView._NOWAITFORACTION;
					}
					vista.hideAllEntries();
					try {
						vista.getOperTRV().addDocumentoPago(doc);
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
						Base.logger.error("Error en agregar documento de pago al carro");
					}
					doc.getDatos().show("Servicio VTR");
				}
			}               
			vista.paintButtons(Tools.getBotones(2));
			estado = 201;
			return ICajaView._NOWAITFORACTION;
		}

		return 0;
	}

	@Override
	public void init(Datos datosVista) {
		// TODO Auto-generated method stub
		trv = datosVista.getStringValue("btnParam");
	}

}
