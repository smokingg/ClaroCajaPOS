package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ws.claro.cl.proxy.AppControlConsultarProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionOut;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Respuesta;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.OperacionOutHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.RespuestaHolder;
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
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoServicioVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoVtr;

public class TrxClaroNotaVenta implements ITrxBase{

	int estado = 0;
	int index = 0;
	String consultaDocumento = "";
	String []clientesCuentas;
	DocumentoPago doc;
	CuentaVTR cuenta = null;
	
	DetalleDocumento cuentaDocClaro = null;
	
	ConsultaDeudaVtrOut out = null;
	ArrayList <String> prods = null;
	String prod = "";
	
	String rut = "";
	
	// se setean como atributo DTO de salida del servicio OSB 
    OperacionOut opOut = null;
    // este atributo nos indica a que plataofrma se esta realizando la Trx !!
	String trv = null;
	
	@Override
	public void init(Datos htParam) {
		// TODO Auto-generated method stub
		trv = htParam.getStringValue("btnParam");
	}
	
	@Override
	public int execute(ICajaView vista, int key, Datos htParam) {
		// TODO Auto-generated method stub
		if( key == 0 ) {
            estado = 0;
            vista.acceptEscape(true);
            //vista.setInputTimeout(15);
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }
        else if( key == KeyEvent.VK_F1 ) {
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
     
		Base.logger.info("La invocacion se realizo desde: "+  trv);
		
        switch( estado ) {
	        case 0:
	            estado = 1;	           
	            vista.setEntryMessage( "Ingrese Nota Venta", true );
	            vista.setEntryTextLabel("Ingrese Nota Venta:", true);
	            vista.setEntryText("", true, false, false,"[0-9]+","Nota Venta no válida");
	            return ICajaView._WAITFORACTION;	           
	            
	        case 1:	            
	            String codigo = vista.getEntryText();
	            if(codigo.equals("")){
	                estado = 1;
	                vista.setEntryMessage( "N° inválido - Ingrese nuevamente", true );
	                vista.setEntryTextLabel("Ingrese Nota Venta:", true);
	                vista.setEntryText("", true, false, false,"[0-9]+","Nota Venta no válido");
	                return ICajaView._WAITFORACTION;
	            }
	            
	            consultaDocumento = codigo;
                
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
               // caja.setCanal(canal);
                
                opIn.setCaja(caja);
                // TODO validar Tipo de operacion para Nota de Venta ??
                opIn.setTipoOperacion("SAPNOTAVENTA"); //se setea el tipo de busqueda que debe realizar
                Base.logger.info("Valor Tipo Operacion: "+  opIn.getTipoOperacion());
                opIn.setValorOperacion(consultaDocumento);
                Base.logger.info("Valor Operacion: "+  opIn.getValorOperacion());
                opIn.setTipoRegistro("SAPNOTAVENTA");
                Base.logger.info("Valor Tipo Registro: "+  opIn.getTipoRegistro());
                opIn.setOrigen(trv);
                Base.logger.info("Valor Origen Trx: "+  opIn.getOrigen());
                
                OperacionOutHolder opOutHold = new OperacionOutHolder();
                RespuestaHolder resp = new RespuestaHolder();
                
            	vista.showBusyWindow("Consultando", "Espere por favor...");
            	
				try {
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
				
				// Instanciar DTO Respuesta
				Respuesta r = resp.value;
				Base.logger.info("Codgo retornado Nota Venta: "+  r.getRetCode());
				Base.logger.info("Desc. Retornado Nota Venta: "+  r.getRetDescError());
				
				// Instanciar DTO Respuesta Operacion para obtener la info !!
				try{
				opOut = opOutHold.value;
				Base.logger.info("Cantidad de cuentas retornadas: "+  opOut.getDetalleCuentas().length);
				
				Base.logger.info("Nro cuenta Cta: "+  opOut.getDetalleCuentas(0).getNumeroCuenta());
				Base.logger.info("saldo Castigado Cta: "+  opOut.getDetalleCuentas(0).getSaldoCastigado());
				Base.logger.info("Sist. Origen Cta: "+  opOut.getDetalleCuentas(0).getSistemaOrigen());
				
				Base.logger.info("Trae documentos ????: "+  opOut.getDetalleDocumentos());
				
				if(opOut.getDetalleDocumentos() == null){
					Base.logger.error("No existen Documentos para la Nota de Venta: " + opIn.getValorOperacion());
                    JOptionPane.showMessageDialog(null, "No existen Documentos para la Nota de Venta: " + opIn.getValorOperacion(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				
				Base.logger.info("Cantidad de documentos retornados: "+  opOut.getDetalleDocumentos().length);
				
				Base.logger.info("saldo Doc: "+  opOut.getDetalleDocumentos(0).getSaldo());
				Base.logger.info("saldo Adeudado Doc: "+  opOut.getDetalleDocumentos(0).getSaldoAdeudado());
				Base.logger.info("saldo Neto Doc: "+  opOut.getDetalleDocumentos(0).getSaldoNeto());
				Base.logger.info("Sist. Origen Doc: "+  opOut.getDetalleDocumentos(0).getSistemaOrigen());
				Base.logger.info("Folio Documento Doc: "+  opOut.getDetalleDocumentos(0).getFolioDocumento());
				Base.logger.info("Tipo Documento Doc: "+  opOut.getDetalleDocumentos(0).getTipoDocumento());
				Base.logger.info("Fecha Vencimiento Doc: "+  opOut.getDetalleDocumentos(0).getFechaVencimientoDocumento());
				}
				catch (Exception e){
					JOptionPane.showMessageDialog(null, "No existen Documentos para la Nota de Venta: " + opIn.getValorOperacion(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				
				
				//if(out.getHeaderOut().getRc() != 0){
				if(r.getRetCode() != 0){	
					Base.logger.error("No existen datos [RetCode]:" + r.getRetCode() + " [msg]:" + r.getRetDescError());
                    JOptionPane.showMessageDialog(null, r.getRetDescError(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
	            
				estado = 2;
				return ICajaView._NOWAITFORACTION;
				
	        case 2:
	        	
	        	// Botonera original !! con detalle de Boleta/Documento
            	//vista.paintButtons(Tools.getBotones(1));
            	// Botonera de pruebas !!
            	vista.paintButtons(Tools.getBotones(2));
            	
                estado = 3;
                
                // cbriones: se comenta para pruebas de flujo !!
                //clientesCuentas = new String[out.getCuentas().length + 1];
                clientesCuentas = new String[opOut.getDetalleDocumentos().length + 1];
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Nota Venta", true );
                
                //clientesCuentas[0] = String.format("%-13s %-13s %-12s %-13s", "Cuenta Unica", "Cuenta","Saldo","Dir.Cobranza");
                clientesCuentas[0] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-8s %-10s"
                		, "Origen", "T. Doc", "Num Doc", "T. Reg.", "Servicio", "Cuenta", "Fec Venc", "Saldo");
                        //, "Origen", "T. Doc", "Num Doc", "Servicio", "Cuenta", "Fec Emis", "Fec Venc", "Saldo");
                
                //for(int i = 0; i < out.getCuentas().length; i++){ 
                for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){ 
                	//String fecha = Tools.getFecha(out.getCuentas()[i].getFechaVencimiento().getTime());
                	if(opOut.getDetalleDocumentos()[i].getTipoDocumento() == null || 
                     	   "".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getTipoDocumento())){
                     		opOut.getDetalleDocumentos()[i].setTipoDocumento("FAC");
                    }
                     	 
                    if(opOut.getDetalleDocumentos()[i].getFechaVencimientoDocumento() == null || 
                          	   "".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getFechaVencimientoDocumento())){
                          		opOut.getDetalleDocumentos()[i].setFechaVencimientoDocumento(Tools.getFecha().substring(0, 10));
                    }
                    
                    if(opOut.getDetalleDocumentos()[i].getSistemaOrigen() == null || 
                       	   "".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getSistemaOrigen())){
                       		opOut.getDetalleDocumentos()[i].setSistemaOrigen(trv);
                    }
                    
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
                	
                	 
                	 Base.logger.info("NV - Sist. origen "+opOut.getDetalleDocumentos()[i].getSistemaOrigen());
                	 Base.logger.info("NV - Tipo Doc. "+opOut.getDetalleDocumentos()[i].getTipoDocumento());
                	 Base.logger.info("NV - Folio "+opOut.getDetalleDocumentos()[i].getFolioDocumento());
                	 Base.logger.info("NV - Tipo Reg. "+opOut.getDetalleDocumentos()[i].getTipoRegistro());
                	 Base.logger.info("NV - Id. serv. "+opOut.getDetalleDocumentos()[i].getIdServicio());
                	 Base.logger.info("NV - Nro. Cta. "+opOut.getDetalleDocumentos()[i].getNumeroCuenta());
                	 Base.logger.info("NV - Fecha Venc. "+opOut.getDetalleDocumentos()[i].getFechaVencimientoDocumento());
                	 Base.logger.info("NV - Saldo Adeu. "+opOut.getDetalleDocumentos()[i].getSaldoAdeudado());
                	 
                	 
                }
                
                rut = opOut.getRutCliente() + "-" + opOut.getDvCliente();
                
                // vista.setEntryMessage( "Nombre de Cliente: " + out.getCliente().getNombreCliente() + " | Rut: " + Tools.limpiarRut(out.getCliente().getRutCliente()), true );
                vista.setEntryMessage( "Nombre de Cliente: " + opOut.getDetalleDocumentos()[0].getNombreCliente() + " | Rut: " + opOut.getRutCliente() + "-" + opOut.getDvCliente(), true );
                vista.setEntryList(clientesCuentas, true, false,index + 1);
                return ICajaView._WAITFORACTION;
                
            case 3:                
            	// cbriones: se comenta para pruebas flujo de Claro
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
                    doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
                } catch (BaseException e) {
                	Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("No se pudo instanciar el documento");
                    return 20;
                }
                
                cuentaDocClaro = opOut.getDetalleDocumentos(index);
                ((DocumentoCuentaClaro)doc).llenarDatos(cuentaDocClaro);
                
                doc.getDatos().setValue("Rut",opOut.getRutCliente()+"-"+opOut.getDvCliente());
                doc.getDatos().setValue("DvCliente",opOut.getDvCliente());
                doc.getDatos().setValue("RutCliente", opOut.getRutCliente());
                doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(index).getNombreCliente());
                doc.getDatos().setValue("SistemaOrigenClaro", trv);
                doc.getDatos().setValue("TipoTrx", "PagoDeudaNV");
                
                return ICajaView._NOWAITFORACTION;
                
            	
            case 4:
            	vista.paintButtons(Tools.getBotones(-1));
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Nota Venta", true );
                vista.setEntryTextArea("Origen: " + doc.getDatos().getStringValue("SistemaOrigenClaro")
  					  +"\nTipo Documento: " + doc.getDatos().getStringValue("TipoDocumentoClaro")
              		  +"\nFolio: " + doc.getDatos().getStringValue("FolioDocumentoClaro")
              		  +"\nTipo Registro: " + Tools.getTipoRegistroVisual(doc.getDatos().getStringValue("TipoRegistroClaro"))
              		  +"\nId Servicio: " + doc.getDatos().getStringValue("IdServicioClaro")
              		  +"\nNúmero Cuenta: " + doc.getDatos().getStringValue("CuentaClaro")
              		  +"\nFecha Emisión: " + doc.getDatos().getStringValue("FechaEmisionClaro")
              		  +"\nFecha Vencimiento: " + doc.getDatos().getStringValue("FechaVencimientoClaro")
          			  +"\nSaldo: " + Format.formatMonto(doc.getDatos().getLongValue("SaldoAdeudadoClaro"))
          			  , true);
                
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
    							if("SC".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getSistemaOrigen()) && opOut.getDetalleDocumentos()[x].getSaldoAdeudado() != 0){
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
    							if("ST".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getSistemaOrigen()) && opOut.getDetalleDocumentos()[i].getSaldoAdeudado() != 0){
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
    								if("SC".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[x].getSistemaOrigen()) && opOut.getDetalleDocumentos()[x].getSaldoAdeudado() != 0){
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
    								if("SC".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getTipoRegistro()) && "VTV".equalsIgnoreCase(opOut.getDetalleDocumentos()[i].getSistemaOrigen()) && opOut.getDetalleDocumentos()[i].getSaldoAdeudado() != 0){
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
                // cbriones: se setea botonera opcion 2 !!
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
                }else{
                	index = index -1;
                }
                cuenta = out.getCuentas()[index];
                for(int i = 0 ; i < out.getCuentas().length ; i++){
                	if(cuenta.getCuentaUnica().equals(out.getCuentas()[i].getCuentaUnica())  && !out.getCuentas()[i].getCuentaUnica().equals("0") ){
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
                        doc.getDatos().show("Cuenta Claro");
                	}
                }               
                vista.paintButtons(Tools.getBotones(1));
                estado = 2;
                return ICajaView._NOWAITFORACTION;
                
            case 10:
            	vista.paintButtons(Tools.getBotones(2));
            	estado = 11;
            	 clientesCuentas = new String[opOut.getDetalleDocumentos().length + 1];
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Nota Venta - Detalle Documentos", true );
                
                clientesCuentas[0] = String.format("%-13s %-5s %-13s %-13s %-12s", "Cuenta", "Tipo", "N° Documento", "F.Vencimiento", "Saldo");
                
               for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){ 	
            	   DetalleDocumento aux = opOut.getDetalleDocumentos(index);
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
                    doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
                } catch (BaseException e) {
                	Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("No se pudo instanciar el documento");
                    return 20;
                }
                
                DetalleDocumento documentoClaro = opOut.getDetalleDocumentos(index);
                ((DocumentoCuentaClaro)doc).llenarDatos(documentoClaro);
                
                doc.getDatos().setValue("Rut",opOut.getRutCliente()+"-"+opOut.getDvCliente());
                doc.getDatos().setValue("DvCliente",opOut.getDvCliente());
                doc.getDatos().setValue("RutCliente", opOut.getRutCliente());
                doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(index).getNombreCliente());
                
                return ICajaView._NOWAITFORACTION;
                
            case 12:
            	vista.paintButtons(Tools.getBotones(-1));
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Cuenta - Detalle Documentos", true );
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
                }
                else{
                    index = index -1;
                }
                
                cuentaDocClaro = opOut.getDetalleDocumentos()[index];
                estado = 201;
                return ICajaView._NOWAITFORACTION;
                
            case 201:            	
                vista.paintButtons(Tools.getBotones(3));
            	estado = 21;
            	index = 0;
            	int cant = 0;
            	prods = new ArrayList<String>();
            	
            	// TODO no trae detalle productos, se setea detalle cuenta ??
            	for(int i = 0 ; i < opOut.getDetalleCuentas().length; i++){
            	//for(int i = 0 ; i < out.getServicios().length; i++){
            		
            		//ServicioVTR producto = out.getServicios()[i];
            		DetalleCuenta producto = opOut.getDetalleCuentas(i);
            		prods.add(producto.getNumeroCuenta());
            		
            		/**
            		if(!producto.getCuentaUnica().equals("0")){
	            		if(producto.getCuentaUnica().equals(cuenta.getCuentaUnica())){
	            			if(prods.size() == 0 || !prods.contains(producto.getProducto())){
	            				cant++;
	            				prods.add(producto.getProducto());
	            			}
	            		}
            		}
            		else{
            			if(producto.getNumeroCuenta() == cuenta.getNumeroCuenta()){
	            			if(prods.size() == 0 || !prods.contains(producto.getProducto())){
	            				cant++;
	            				prods.add(producto.getProducto());
	            			}
	            		}
            		}
            		*/
            	}
            	
                //clientesCuentas = new String[cant + 1];
                clientesCuentas = new String[opOut.getDetalleCuentas().length + 1];
                
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Cuenta - Detalle Productos", true );
                
                clientesCuentas[0] = String.format("%-13s %-6s %-13s %-12s %-13s", "Tipo Producto", "Estado", "Cuenta", "Saldo"," Dir.Cobranza");
                
                /** TODO validar implementacion para Claro !!
                for(int j = 0 ; j < prods.size(); j++){
                	long montoProd = 0;
                	ServicioVTR aux = null;
                	ServicioVTR last = null;
	                for(int i = 0; i < out.getServicios().length; i++){
	                	aux = out.getServicios()[i];
	                	if(!aux.getCuentaUnica().equals("0")){
		                	if(!aux.getCuentaUnica().equals(cuenta.getCuentaUnica())){
		            			continue;
		            		}
	                	}
	                	else{
	                		if(aux.getNumeroCuenta() != cuenta.getNumeroCuenta()){
		            			continue;
		            		}
	                	}
	                	if(aux.getProducto().equals(prods.get(j))){
	                		montoProd += aux.getSaldoServicio();
	                		last = aux;
	                	}
	                }
	                clientesCuentas[j + 1] = String.format("%13s %6s %13s %13s $%12s %13s",last.getProducto(),last.getEstadoServicio(),last.getCuentaUnica(),last.getNumeroCuenta(),Format.formatMontoPantalla(montoProd),cuenta.getDireccionCobranza());
                }
                */
                
                for(int j = 0 ; j < opOut.getDetalleCuentas().length; j++){
                	clientesCuentas[j + 1] = String.format("%13s %6s %13s $%12s %13s", opOut.getDetalleCuentas(j).getCicloFacturacion(), "no trae !!", opOut.getDetalleCuentas(j).getNumeroCuenta(), Format.formatMontoPantalla(opOut.getDetalleCuentas(j).getTotalSaldo()), "no hay !!");
                }
                
                vista.setEntryList(clientesCuentas, true, false,index + 1);
                return ICajaView._WAITFORACTION;
                
            case 21:
            	doc = null;
                index = vista.getEntryListIndex();
                if(index == 0){
                    estado = 201;
                    return ICajaView._NOWAITFORACTION;
                }
                else{
                    index = index -1;
                }
                estado = 201;
                for(int i = 0 ; i < out.getServicios().length ; i++){
                	if(!prods.get(index).equals(out.getServicios()[i].getProducto()))
                		continue;
                	String cUnicaOut = out.getServicios()[i].getCuentaUnica();
                	String cUnicaProd = cuenta.getCuentaUnica();
                	if(cUnicaOut.equals("0") && cUnicaProd.equals("0")){
                		cUnicaOut = Long.toString(out.getServicios()[i].getNumeroCuenta());
                		cUnicaProd = Long.toString(cuenta.getNumeroCuenta());
                	}
                	if( cUnicaProd.equals(cUnicaOut) ){
                		ServicioVTR aux = out.getServicios()[i];
                		try {
                            doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
                        } catch (BaseException e) {
                        	Tools.logStackTrace(Base.logger, e);
                            Base.logger.error("No se pudo instanciar el documento");
                            return 20;
                        }
                        ((DocumentoServicioVtr)doc).llenarDatos(aux);
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
                return ICajaView._NOWAITFORACTION;
                
            case 22:
            	vista.paintButtons(Tools.getBotones(-1));
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Documento - Detalle Servicio", true );
                vista.setEntryTextArea("Servicio: " + doc.getDatos().getStringValue("CodigoTipoServicio")
                		+"\nNumero Servicio: " + doc.getDatos().getStringValue("NumeroServicio")
                		+"\nIdentificador Servicio: " + doc.getDatos().getStringValue("IdentificadorServicio")
                		+"\nNumero Cuenta: " + doc.getDatos().getStringValue("NumeroCuenta")
                		+"\nEstado Servicio: " + doc.getDatos().getStringValue("EstadoServicio")
                        +"\nSaldo: " + Format.formatMonto(doc.getDatos().getLongValue("Monto"))
                        , true);
                estado = 23;
                return ICajaView._WAITFORACTION;
                
            case 23:
            	if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
                    Base.logger.info("Documento ya existe en carro de compras");
                    JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    estado = 241;
                    return ICajaView._NOWAITFORACTION;
                }
            	// Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
                if(Base.getEdicion()){
	                if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
	                	Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
	                }else{
	                	Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
	                    JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	                    estado = 241;
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
                doc.getDatos().show("Servico VTR");
                vista.paintButtons(Tools.getBotones(2));
                index = 
                estado = 241;
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
                vista.setEntryTitle( "Consulta por Documento - Detalle Servicios", true );
                clientesCuentas[0] = String.format("%-13s %-6s %-13s %-13s %-12s", "Tipo Servicio","Estado","Servicio", "Cuenta","Saldo");
                for(int i = 0; i < out.getServicios().length; i++){
                	ServicioVTR aux = out.getServicios()[i];
                	if(!aux.getCuentaUnica().equals("0")){
	                 	if(!aux.getCuentaUnica().equals(cuenta.getCuentaUnica()) || !aux.getProducto().equals(prod)){
	             			continue;
	             		} 
                	}
                	else{
                		if(aux.getNumeroCuenta() != cuenta.getNumeroCuenta() || !aux.getProducto().equals(prod)){
	             			continue;
	             		} 
                	}                	
                     clientesCuentas[posArray + 1] = String.format("%13s %6s %13s %13s $%12s",aux.getProducto(),aux.getEstadoServicio(),aux.getNumeroServicio(),aux.getNumeroCuenta(),Format.formatMontoPantalla((long)aux.getSaldoServicio()));
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
                	ServicioVTR aux = out.getServicios()[i];
                	if(!aux.getCuentaUnica().equals("0")){
	                	if(!aux.getCuentaUnica().equals(cuenta.getCuentaUnica()) || !aux.getProducto().equals(prod)){
	             			continue;
	             		}
                	}
                	else{
                		if(aux.getNumeroCuenta() != cuenta.getNumeroCuenta() || !aux.getProducto().equals(prod)){
	             			continue;
	             		}
                	}
                	if(pos == index){
                		sel = aux;
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
                	ServicioVTR aux = out.getServicios()[i];
                	if(!aux.getCuentaUnica().equals("0")){
	                	if(!aux.getCuentaUnica().equals(cuenta.getCuentaUnica()) || !aux.getProducto().equals(prod)){
	             			continue;
	             		}
                	}
                	else{
                		if(aux.getNumeroCuenta() != cuenta.getNumeroCuenta() || !aux.getProducto().equals(prod)){
	             			continue;
	             		}
                	}
                	if(pos == index){
                		sel = aux;
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
                		ServicioVTR aux = out.getServicios()[i];
                		try {
                            doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
                        } catch (BaseException e) {
                        	Tools.logStackTrace(Base.logger, e);
                            Base.logger.error("No se pudo instanciar el documento");
                            return 20;
                        }
                        ((DocumentoServicioVtr)doc).llenarDatos(aux);
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
                		ServicioVTR aux = out.getServicios()[i];
                		try {
                            doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
                        } catch (BaseException e) {
                        	Tools.logStackTrace(Base.logger, e);
                            Base.logger.error("No se pudo instanciar el documento");
                            return 20;
                        }
                        ((DocumentoServicioVtr)doc).llenarDatos(aux);
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


}
