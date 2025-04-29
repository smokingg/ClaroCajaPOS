package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import ws.claro.cl.proxy.AppControlNvOneProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.OperacionIn;
import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.OperacionOut;
import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaNVOneServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.Respuesta;
import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.holders.OperacionOutArrayHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.holders.RespuestaHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.hyh.cajas.ws.impl.ConsultaDeudaVtrOut;
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

public class TrxClaroNotaVentaOne implements ITrxBase{

	int estado = 0;
	int index = 0;
	String consultaDocumento = "";
	String []clientesCuentas;
	String []clientesCuentasPaso;
	DocumentoPago doc;
	CuentaVTR cuenta = null;
	
	DetalleDocumento cuentaDocClaro = null;
	
	ConsultaDeudaVtrOut out = null;
	ArrayList <String> prods = null;
	String prod = "";
	String codigoNotaventa="";
	String codigo = "";
	String rut = "";
	SimpleDateFormat inputDf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat inputHrs = new SimpleDateFormat("HH:mm:ss");
	Date hoy = new Date();
	List<OperacionOut> conDatos = null; 
	// se setean como atributo DTO de salida del servicio OSB 
	OperacionOut[] opOut = null;
	OperacionOut[] opOutPaso = null;
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
	            vista.setEntryMessage( "Ingrese Cuenta de la Nota Venta", true );
	            vista.setEntryTextLabel("Ingrese Cuenta :", true);
	            vista.setEntryText("", true, false, false,"[0-9]+","Cuenta no válida");
	            return ICajaView._WAITFORACTION;	           
	            
	        case 1:	            
	        	estado = 2;
	        	 codigo = vista.getEntryText();
	            if(codigo.equals("")){
	                estado = 1;
	                vista.setEntryMessage( "N° inválido - Ingrese nuevamente", true );
	                vista.setEntryTextLabel("Ingrese Cuenta :", true);
	                vista.setEntryText("", true, false, false,"[0-9]+","Cuenta no válida");
	                return ICajaView._WAITFORACTION;
	            }
	            
	            consultaDocumento = codigo;
				return ICajaView._NOWAITFORACTION;
			
			case 2:
				estado = 3;
				vista.setEntryMessage( "Ingrese Nota Venta", true );
	            vista.setEntryTextLabel("Ingrese Nota Venta:", true);
	            vista.setEntryText("", true, false, false,"[0-9]+","Nota Venta no válida");
	            return ICajaView._WAITFORACTION;	
			
			case 3:
			 // estado = 4;
			   codigoNotaventa = vista.getEntryText();
	            if(codigoNotaventa.equals("")){
	                estado = 2;
	                vista.setEntryMessage( "N° inválido - Ingrese nuevamente", true );
	                vista.setEntryTextLabel("Ingrese Nota Venta:", true);
	                vista.setEntryText("", true, false, false,"[0-9]+","Nota Venta no válida");
	                return ICajaView._WAITFORACTION;
	            }
			  		 
	            //cbriones: se debe Implementar invocacion a servicio de Consulta OSB !!!
	            PlataformaPagoConsultaNVOneServerProxy pr = AppControlNvOneProxy.getProxyInstance();
                
                // Generar los DTOs de entrada !!
                ParamSet pSet = Base.getParamSet("posDat");
                
                OperacionIn opIn = new OperacionIn();
                // TODO validar Tipo de operacion para Nota de Venta ??
                opIn.setCuenta(consultaDocumento);
                Base.logger.info("Valor Cuenta: "+  opIn.getCuenta());
                opIn.setNumeroOrden(codigoNotaventa);
                Base.logger.info("Valor Numero Orden: "+  opIn.getNumeroOrden());
                
                OperacionOutArrayHolder opOutHold = new OperacionOutArrayHolder();
                OperacionOutArrayHolder opOutHoldPaso = new OperacionOutArrayHolder();
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
				
				if(r.getRetCode() != 0 ){
					Base.logger.error("No existen Documentos para la Nota de Venta: " + opIn.getCuenta());
                    JOptionPane.showMessageDialog(null, "No existen Documentos para la Nota de Venta: " + opIn.getCuenta(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				
				if (opOut.length == 0){
					Base.logger.error("No existen Documentos para la Nota de Venta: " + opIn.getCuenta());
                    JOptionPane.showMessageDialog(null, "Nota de Venta : " + opIn.getNumeroOrden() +" no Tiene Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				
				}
				catch (Exception e){
					JOptionPane.showMessageDialog(null, "Nota de Venta : " + opIn.getNumeroOrden() +" no Tiene Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					//JOptionPane.showMessageDialog(null, "Problemas al recuperar Nota de Venta: " + opIn.getCuenta(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				
				//if(out.getHeaderOut().getRc() != 0){
				if(r.getRetCode() != 0){	
					Base.logger.error("No existen datos [RetCode]:" + r.getRetCode() + " [msg]:" + r.getRetDescError());
                    JOptionPane.showMessageDialog(null, r.getRetDescError(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
	            
				estado = 4;
				return ICajaView._NOWAITFORACTION;
				
	        case 4:
	        	
	        	// Botonera original !! con detalle de Boleta/Documento
            	//vista.paintButtons(Tools.getBotones(1));
            	// Botonera de pruebas !!
            	vista.paintButtons(Tools.getBotones(2));
            	int cantidad=0;       	
                estado = 5;
               
                boolean enCero= false;
                // cbriones: se comenta para pruebas de flujo !!
                //clientesCuentas = new String[out.getCuentas().length + 1];
                conDatos = new ArrayList<OperacionOut>();
                //clientesCuentasPaso = new String[opOut.length + 1];
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Nota Venta", true );
                int indice = 0;
                
                for(int i = 0; i < opOut.length; i++){
                	
                	if(opOut[i].getMonto() <= 0 ){
                		enCero = true;
                		indice++;
                		continue;
                	}else{
                		
                		cantidad++ ;
                	}
                
                }
                
                if((indice == opOut.length ) && enCero){
                	Base.logger.error("Nota de Venta no Tiene Deuda ");
                    JOptionPane.showMessageDialog(null, "Nota de Venta : " + codigoNotaventa + " No Tiene Deuda", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
                }
                
                clientesCuentas = new String[cantidad + 1];
                
                clientesCuentas[0] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-8s %-10s %-6s"
                		, "Id Factura", "Num Legal Factura", "Cod. Pais", "Cod. Empresa", "Fec Creac", "Fec Venc", "monto", "Saldo", "Origen");

                int j=0;
                String fecha=inputDf.format(hoy);
                for(int i = 0; i < opOut.length; i++){
                	
                	if(opOut[i].getMonto() <= 0 ){
                		continue;
                	}
                	
                    if(opOut[i].getFechaVencimiento().equals("") || opOut[i].getFechaVencimiento().contains("T")){
                    	
                    	opOut[i].setFechaVencimiento(fecha);
                    }
                    if(opOut[i].getFechaCreacion().equals("") || opOut[i].getFechaCreacion().contains("T")){
                    	opOut[i].setFechaCreacion(fecha);
                    }
                    clientesCuentas[j+1] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-8s %-10s %-6s"
                     		, opOut[i].getIdFactura()
                     		, opOut[i].getNumLegalfactura()
                     		, opOut[i].getCodigoPais()
                     		, opOut[i].getEmpresa()
                     		, opOut[i].getFechaCreacion().replace("-", "")
                     		, opOut[i].getFechaVencimiento().replace("-", "")
                     		, opOut[i].getMonto()
                     		, opOut[i].getSaldo()
                     		, trv
                    );
                    conDatos.add(opOut[i]);
                    j++;
                }
                	               
                vista.setEntryList(clientesCuentas, true, false,index);
                return ICajaView._WAITFORACTION;
                
            case 5:                
            	// cbriones: se comenta para pruebas flujo de Claro
                doc = null;
                index = vista.getEntryListIndex();
                if(index == 0){
                    estado = 4;
                    return ICajaView._NOWAITFORACTION;
                
                }else{
                	index = index -1;
                }
                conDatos.remove(index);
                estado = 6;
                
                try {
                    doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
                } catch (BaseException e) {
                	Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("No se pudo instanciar el documento");
                    return 20;
                }
                cuentaDocClaro = new DetalleDocumento();
                
                
                opOut[index].setFechaVencimiento(inputDf.format(hoy)+"T"+inputHrs.format(hoy)+"-00:00");
                opOut[index].setFechaCreacion(inputDf.format(hoy)+"T"+inputHrs.format(hoy)+"-00:00");
                //2013-03-06T00:00:00-03:00
                
                
                int codigoEmpresa = 8;
                cuentaDocClaro.setCodigoEmpresa(codigoEmpresa);
                cuentaDocClaro.setFechaEmisionDocumento(opOut[index].getFechaCreacion());
                cuentaDocClaro.setFechaVencimientoDocumento(opOut[index].getFechaVencimiento());
                cuentaDocClaro.setMontoTotalDocumento(opOut[index].getMonto());
                cuentaDocClaro.setSaldo(opOut[index].getSaldo());
                cuentaDocClaro.setSaldoAdeudado(opOut[index].getMonto());
                cuentaDocClaro.setFolioDocumento(opOut[index].getIdFactura());
                cuentaDocClaro.setNumeroCuenta(Long.toString(opOut[index].getNumLegalfactura()));
                cuentaDocClaro.setSistemaOrigen(trv);
                cuentaDocClaro.setTipoDocumento("B/V");
                cuentaDocClaro.setTipoRegistro("PagoDeudaNV");
                cuentaDocClaro.setIdServicio(codigoNotaventa);
                cuentaDocClaro.setNumeroCuenta(codigo);
                cuentaDocClaro.setCodigoPortador(Integer.parseInt(codigoNotaventa));
              //  cuentaDocClaro.setNombreCliente(codigoNotaventa);
                
//                cuentaDocClaro = opOut.getDetalleDocumentos(index);
                ((DocumentoCuentaClaro)doc).llenarDatos(cuentaDocClaro);
//                
                doc.getDatos().setValue("Rut","0"+"-"+"0");
                doc.getDatos().setValue("DvCliente","0");
                doc.getDatos().setValue("RutCliente", "0");
//                doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(index).getNombreCliente());
               
                doc.getDatos().setValue("SistemaOrigenClaro", trv);
                doc.getDatos().setValue("TipoTrx", "PagoDeudaNV");
                
                return ICajaView._NOWAITFORACTION;
                
            	
            case 6:
            	vista.paintButtons(Tools.getBotones(-1));
            	String fechaVista=inputDf.format(hoy);
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Nota Venta", true );
                vista.setEntryTextArea("Origen: " + doc.getDatos().getStringValue("SistemaOrigenClaro")
  					  +"\nTipo Documento: " + doc.getDatos().getStringValue("TipoDocumentoClaro")
              		  +"\nFolio: " + doc.getDatos().getStringValue("FolioDocumentoClaro")
              		  +"\nTipo Registro: " + Tools.getTipoRegistroVisual(doc.getDatos().getStringValue("TipoRegistroClaro"))
              		  +"\nId Servicio: " + doc.getDatos().getStringValue("IdServicioClaro")
              		  +"\nNúmero Cuenta: " + doc.getDatos().getStringValue("CuentaClaro")
              		  +"\nFecha Emisión: " + fechaVista //doc.getDatos().getStringValue("FechaEmisionClaro")
              		  +"\nFecha Vencimiento: " + fechaVista // doc.getDatos().getStringValue("FechaVencimientoClaro")
          			  +"\nSaldo: " + Format.formatMonto(doc.getDatos().getLongValue("SaldoAdeudadoClaro"))
          			  , true);
                
                estado = 7;
                return ICajaView._WAITFORACTION;
                
            case 7:
            	            	
	                if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
	                    Base.logger.info("Documento ya existe en carro de compras");
	                    JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	                    estado = 4;
	                    return ICajaView._NOWAITFORACTION;
	                }
	                
	                // Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
	                if(Base.getEdicion()){
		                if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
		                	Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
		                }else{
		                	Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
		                    JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		                    estado = 0;
		                    return ICajaView._NOWAITFORACTION;
		                }
	                }
	                
	                // Validar que el saldo adeudado no sea Cero !!
	                if(doc.getDatos().getLongValue("SaldoAdeudadoClaro") == 0){
	                	Base.logger.info("El saldo del Doc. es Cero, no se agrega al carro....");
	                    JOptionPane.showMessageDialog(null, "El Saldo del Documento es Cero, No se agregará en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	                    estado = 0;
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
                
                if (!conDatos.isEmpty()){
            		
            		JOptionPane.showMessageDialog(null, "Se deben subir todas las notas de venta al carro", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            		
            		for (int x = 0; x < conDatos.size(); x++){
            		
		            		try {
		                        doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
		                    } catch (BaseException e) {
		                    	Tools.logStackTrace(Base.logger, e);
		                        Base.logger.error("No se pudo instanciar el documento");
		                        return 20;
		                    }
		                    cuentaDocClaro = new DetalleDocumento();
		                    
		                    
		                    conDatos.get(x).setFechaVencimiento(inputDf.format(hoy)+"T"+inputHrs.format(hoy)+"-00:00");
		                    conDatos.get(x).setFechaCreacion(inputDf.format(hoy)+"T"+inputHrs.format(hoy)+"-00:00");
		                    //2013-03-06T00:00:00-03:00
		                    
		                    
		                    codigoEmpresa = 8;
		                    cuentaDocClaro.setCodigoEmpresa(codigoEmpresa);
		                    cuentaDocClaro.setFechaEmisionDocumento(conDatos.get(x).getFechaCreacion());
		                    cuentaDocClaro.setFechaVencimientoDocumento(conDatos.get(x).getFechaVencimiento());
		                    cuentaDocClaro.setMontoTotalDocumento(conDatos.get(x).getMonto());
		                    cuentaDocClaro.setSaldo(conDatos.get(x).getSaldo());
		                    cuentaDocClaro.setSaldoAdeudado(conDatos.get(x).getMonto());
		                    cuentaDocClaro.setFolioDocumento(conDatos.get(x).getIdFactura());
		                    cuentaDocClaro.setNumeroCuenta(Long.toString(conDatos.get(x).getNumLegalfactura()));
		                    cuentaDocClaro.setSistemaOrigen(trv);
		                    cuentaDocClaro.setTipoDocumento("B/V");
		                    cuentaDocClaro.setTipoRegistro("PagoDeudaNV");
		                    cuentaDocClaro.setIdServicio(codigoNotaventa);
		                    cuentaDocClaro.setNumeroCuenta(codigo);
		                    cuentaDocClaro.setCodigoPortador(Integer.parseInt(codigoNotaventa));
		                  //  cuentaDocClaro.setNombreCliente(codigoNotaventa);
		                    
//		                    cuentaDocClaro = opOut.getDetalleDocumentos(index);
		                    ((DocumentoCuentaClaro)doc).llenarDatos(cuentaDocClaro);
//		                    
		                    doc.getDatos().setValue("Rut","0"+"-"+"0");
		                    doc.getDatos().setValue("DvCliente","0");
		                    doc.getDatos().setValue("RutCliente", "0");
//		                    doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(index).getNombreCliente());
		                   
		                    doc.getDatos().setValue("SistemaOrigenClaro", trv);
		                    
			                try {
			                    vista.getOperTRV().addDocumentoPago(doc);
			                } catch (BaseException e) {
			                    Tools.logStackTrace(Base.logger, e);
			                    Base.logger.error("Error en agregar documento de pago al carro");
			                }
		            	
		                doc.getDatos().show("Cuenta Claro");
                    
                    
            		}
            		
            		
            	}
                // cbriones: se setea botonera opcion 2 !!
                //vista.paintButtons(Tools.getBotones(1));
                vista.paintButtons(Tools.getBotones(2));
                estado = 4;
                return ICajaView._NOWAITFORACTION;

        }
        
        return 0;
	}


}
