package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import ws.claro.cl.proxy.AppControlConsultarProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionOut;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Respuesta;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.OperacionOutHolder;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.holders.RespuestaHolder;
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

public class TrxClaroAbonoFijo implements ITrxBase {
	int estado = 0;
	String tiposDocs = "";
	String rut = "";
	OperacionOut opOut = null;
	private String msg = "";
	private String origen = "";
	String []clientesCuentas;
	int index = 0;
	DocumentoPago doc;
	DetalleDocumento documento = null;
    long monto;
    String codigo;
    ParamSet pList;
    String tipoAbono;
	String[] tipos;
	long totalCuenta = 0;

	
	  public void init(Datos datosVista){
	        tiposDocs = "Siscli";
	        msg = datosVista.getStringValue("btnParam");
	        String []aux = msg.split(",");
	        origen = aux[0];
	    }
	
	public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 ) {
            estado = 0;
            vista.acceptEscape(true);
            //vista.setInputTimeout(15);
            
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }
        else if( key == KeyEvent.VK_ENTER ) {
        } 
        else if( key == KeyEvent.VK_ESCAPE ) {
            return 20;
        }
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        pList = Base.getParamSet("posDat");
    	tipos = pList.getStringValue("PagoAbono").split(",");
     
        switch( estado ) {
            case 0:
            	estado = 10;     	
            	String[] lista = new String[tipos.length];
        
            	
            	for (int i = 0; i < lista.length; i++ ){
            		lista[i] = "Pago Abono " + tipos[i];
            	}
            	
            	vista.setEntryTitle("Seleccione tipo de Abono:", true);
            	vista.setEntryList(lista, true, false);
                return ICajaView._WAITFORACTION;
            	
            case 1:
            	estado = 2;
                vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
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
                             
                
                if(!Tools.validarRut(codigo)){
                    estado = 2;
                    vista.setEntryMessage( "RUT  Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                if(tipoAbono.equalsIgnoreCase("PagoAbono")){
                	estado = 3;
                }
                else
                {
                	estado = 11;
                }
                
                // Se elimina el DV Ingresado por el Usuario !!
                rut = codigo.substring(0, codigo.length()-2);
				return ICajaView._NOWAITFORACTION;

            case 3:
                
                //cbriones: se debe Implementar invocacion a servicio de Consulta OSB !!!
                //ServerProxy pr = Proxy.getProxyInstance();
                PlataformaPagoConsultarServerProxy pr = AppControlConsultarProxy.getProxyInstance();
                
                // Generar los DTOs de entrada !!
                //ConsultaDeudaRutVTRIn in = new ConsultaDeudaRutVTRIn();
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
                opIn.setTipoOperacion("rut"); // se setea el tipo de busqueda que debe realizar en este caso es RUT !!
                Base.logger.info("Valor Tipo Operacion: "+  opIn.getTipoOperacion());
                opIn.setValorOperacion(rut);
                Base.logger.info("Valor Operacion: "+  opIn.getValorOperacion());
                
                opIn.setOrigen(origen);
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
				
				
				// Instanciar DTO Respuesta para rescatar codigo de error !!
				Respuesta r = resp.value;
				// Instanciar DTO Respuesta Operacion para obtener la info !!
				opOut = opOutHold.value;
				
				
				//if(out.getHeaderOut().getRc() != 0){
				if(r.getRetCode() != 0){	
					Base.logger.error("No existen datos [RetCode]:" + r.getRetCode() + " [msg]:" + r.getRetDescError());
//                    JOptionPane.showMessageDialog(null, r.getRetDescError(), "Continuar", JOptionPane.INFORMATION_MESSAGE);

					int seleccion = JOptionPane.showOptionDialog(null, r.getRetDescError()
							+ " - ¿Desea abonar?", "Continuar",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    new Object[] { "Si", "No"},    // null para YES, NO y CANCEL
						    "Si");
					
					if (seleccion == JOptionPane.OK_OPTION) {
	                    //si no encuentra datos deja que igual continue un abono al rut            
	                    //Datos para que la caja no muestre null
	                    opOut = abonoSinDoc(codigo);
					} else {
						return 20;
					}

				}
				
				estado = 4;
				return ICajaView._NOWAITFORACTION;
              
        case 4:
            estado = 5;
            int contAux=0; 
            String clientesCuentasAux[];
            //clientesCuentas = new String[out.getCuentas().length + 1];
            try{
            	for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){
            		if (!opOut.getDetalleDocumentos(i).getTipoRegistro().equalsIgnoreCase("ABONOI")){
            			contAux = contAux +1;
            		}
            	}
            	clientesCuentas = new String[opOut.getDetalleDocumentos().length + 1];
            	clientesCuentasAux = new String[contAux + 1];
            	if (contAux == 0){
            		estado = 2;
                	JOptionPane.showMessageDialog(null, "No existen datos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
    	        	 vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
    	             vista.setEntryTextLabel("Ingrese Rut:", true);
    	             vista.setEntryText("", true, false, false,null,null);
    	             return ICajaView._WAITFORACTION;
            	}
            	
            }catch (Exception e) {
            	estado = 2;
            	JOptionPane.showMessageDialog(null, "No existen datos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	        	 vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
	             vista.setEntryTextLabel("Ingrese Rut:", true);
	             vista.setEntryText("", true, false, false,null,null);
	             return ICajaView._WAITFORACTION;
			}
            
            vista.hideAllEntries();
            vista.setEntryTitle( "Abono a Rut", true );
            clientesCuentas[0] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-10s"
					, "Origen", "T. Doc", "Num Doc", "T. Reg.", "Servicio", "Fec Venc", "Saldo");
            clientesCuentasAux[0] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-10s"
					, "Origen", "T. Doc", "Num Doc", "T. Reg.", "Servicio", "Fec Venc", "Saldo");
            //for(int i = 0; i < out.getCuentas().length; i++){   
            int k = 0;
            for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){
            	//CuentaAbonoVTR aux = out.getCuentas(i);
            	DetalleDocumento aux = opOut.getDetalleDocumentos(i);
            	if(!aux.getTipoRegistro().equalsIgnoreCase("ABONOI")){
            		clientesCuentasAux[k + 1] = String.format("%7s %6s %10s %12s %10s %8s %10s"
							, aux.getSistemaOrigen()
							, aux.getTipoDocumento()
							, aux.getFolioDocumento()
							, Tools.getTipoRegistroVisual(aux.getTipoRegistro())
							, aux.getIdServicio()
							//, opOut.getFechaEmisionDocumento().replace("-", "")
							, aux.getFechaVencimientoDocumento().replace("-", "")
							, Format.formatMontoPantalla((long)aux.getSaldoAdeudado()));
            		k = k+1;
				}
            }

            try{
            	clientesCuentas = new String[clientesCuentasAux.length];
            	clientesCuentas = clientesCuentasAux;
            }catch (Exception e) {
            	estado = 2;
            	JOptionPane.showMessageDialog(null, "No existen datos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	        	 vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
	             vista.setEntryTextLabel("Ingrese Rut:", true);
	             vista.setEntryText("", true, false, false,null,null);
	             return ICajaView._WAITFORACTION;
			}
            
            vista.setEntryList(clientesCuentas, true, false,index + 1);
            return ICajaView._WAITFORACTION;
             
        case 5:                
            doc = null;
            index = vista.getEntryListIndex();
            if(index == 0){
                estado = 4;
                return ICajaView._NOWAITFORACTION;
            }
            else{
                index = index -1;
            }
            estado = 6;
            documento = opOut.getDetalleDocumentos(index);        
            
            rut = opOut.getRutCliente() + "-" + opOut.getDvCliente();
            
            return ICajaView._NOWAITFORACTION;
        
        case 6:
        	estado = 7;
        	vista.setEntryMessage( "Ingrese monto a abonar", true );
            vista.setEntryTextLabel("Ingrese monto a abonar:", true);
            vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
            return ICajaView._WAITFORACTION;
            
        case 7:
        	estado = 8;
        	int num = 0;
        	
        	if (tipoAbono.equalsIgnoreCase("PagoAbono")){
				num = index;
			}
        	
        	monto = Long.parseLong(vista.getEntryText());
        	
        	if(monto == 0){
        		estado=6;
        		JOptionPane.showMessageDialog(null, "No se permiten abonos con monto Cero", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        		return ICajaView._NOWAITFORACTION;
        		
        	}
        	        	
        	try {
                doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
               // doc = FactoryDocumentoPago.makeInstance("DocumentoAbonoClaro");
            } catch (BaseException e) {
            	Tools.logStackTrace(Base.logger, e);
                Base.logger.error("No se pudo instanciar el documento");
                return 20;
            }                
            ((DocumentoCuentaClaro)doc).llenarDatos(documento);
            
            totalCuenta = new Long(doc.getDatos().getStringValue("SaldoAdeudadoClaro"));
            
            //((DocumentoAbonoClaro)doc).llenarDatos(documento);
            doc.getDatos().setValue("Monto", monto);//            
			doc.getDatos().setValue("Rut",opOut.getRutCliente()+"-"+opOut.getDvCliente());
			doc.getDatos().setValue("DvCliente",opOut.getDvCliente());
			doc.getDatos().setValue("RutCliente", opOut.getRutCliente());			
			doc.getDatos().setValue("ClienteClaro", "");
			//doc.getDatos().setValue("Cliente", opOut.getDetalleDocumentos(num).getNombreCliente());
			doc.getDatos().setValue("TipoAbono", tipoAbono);
			doc.getDatos().setValue("SaldoAdeudadoClaro", monto);
			doc.getDatos().setValue("Tipo", "DocumentoAbonoFijoClaro");
            
            return ICajaView._NOWAITFORACTION;
        
        case 8:
        	String  aFavor="Saldo Total: ";
        	long saldo = 0;
        	Base.logger.info(" ------ SaldoAdeudadoClaro -------- : " + totalCuenta );
        	Base.logger.info(" ------ monto -------- : " + monto);
        	
        	
        	if (totalCuenta == 0){
        		
        		saldo = monto;
        		aFavor = "Saldo a Favor Total: ";
        		
        		
        	}else{
        		
        		if (totalCuenta > monto){
        			saldo = totalCuenta;
        			totalCuenta = new Long(doc.getDatos().getStringValue("SaldoAdeudadoClaro")) - monto;
        	            			
        		}else{
        			
        			saldo =  monto - new Long(doc.getDatos().getStringValue("SaldoAdeudadoClaro")) ;
        			aFavor = "Saldo a Favor Total: ";
        			totalCuenta =0;
        			
        		}
        			
        	}
        	
        	vista.hideAllEntries();
            vista.setEntryTitle( "Abono por RUT", true );
            vista.setEntryTextArea("Monto: " + doc.getDatos().getStringValue("Monto")
            		+"\nNumero Documento: " + doc.getDatos().getStringValue("FolioDocumentoClaro")
            		+"\nSistema Origen: " + doc.getDatos().getStringValue("SistemaOrigenClaro")
            		+"\n"+ aFavor +   saldo
            		+"\n"+"Saldo Actual: "+ totalCuenta
                    , true);
            estado = 9;
            return ICajaView._WAITFORACTION;
            
        case 9:
        	if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
                Base.logger.info("Documento ya existe en carro de compras");
                JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                estado = 4;
                return ICajaView._NOWAITFORACTION;
            }
        	
            // Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
            if(Base.getEdicion()){
                if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("Monto"))){
                	Base.logger.info("El Abono se puede agregar al carro, el monto es menor al de los Medios de pago");
                }else{
                	Base.logger.info("El Abono No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
                    JOptionPane.showMessageDialog(null, "El Abono No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    estado = 4;
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
            doc.getDatos().show("Abono Claro");
            return 20;  
            
        	// Diferenciación de Abono
        case 10:        	
        	int j = vista.getEntryListIndex();
        	estado = 1;
        	if(tipos[j].equalsIgnoreCase("Deuda")){
        		tipoAbono = "PagoAbono";
        	}
        	else{
            	tipoAbono = "PagoAbono" + tipos[j];
        	}
        	
        	return ICajaView._NOWAITFORACTION;
            
        case 11:
            estado = 6;
            opOut = abonoSinDoc(codigo);
        	documento = opOut.getDetalleDocumentos(0);
            rut = opOut.getRutCliente() + "-" + opOut.getDvCliente();           
            return ICajaView._NOWAITFORACTION;   

        }
        return 0;

}
	
	//Si no encuentra documentos se hace el abono al rut
	private OperacionOut abonoSinDoc(String codigo) {
		OperacionOut operacion = new OperacionOut();
		operacion.setRutCliente(rut);
		operacion.setDvCliente(codigo.charAt((codigo.length())-1) + "");
        DetalleDocumento documentoVacio = new DetalleDocumento();
        documentoVacio.setSistemaOrigen(origen);
        documentoVacio.setTipoDocumento("B/V");
        documentoVacio.setFolioDocumento(0);
        documentoVacio.setTipoRegistro("DEUDA");
        documentoVacio.setIdServicio("0");
        documentoVacio.setNumeroCuenta("0");
        documentoVacio.setFechaEmisionDocumento("");
        documentoVacio.setFechaVencimientoDocumento("");
        documentoVacio.setSaldo(0);
        documentoVacio.setSaldoAdeudado(0);
        documentoVacio.setCodigoEmpresa(0);
        documentoVacio.setCodigoPortador(0);
        documentoVacio.setCodigoZIP("");
        documentoVacio.setDireccionCliente("");
        documentoVacio.setGiro("");
        DetalleDocumento docList[] = new DetalleDocumento[1];
        docList[0] = documentoVacio;
        operacion.setDetalleDocumentos(docList);
        
        return operacion;
	}
}
