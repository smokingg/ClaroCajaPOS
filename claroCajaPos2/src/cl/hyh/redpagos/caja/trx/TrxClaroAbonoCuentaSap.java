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

import cl.hyh.cajas.ws.impl.ConsultaCuentasVtrOut;
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
import cl.hyh.redpagos.caja.docpago.DocumentoAbonoClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;

/**
 * Implementacion de la transaccion para Abono a una cuenta Claro
 * @author cbriones
 *
 */
public class TrxClaroAbonoCuentaSap extends TrxClaroAbonoSap{
    
    String nroCuenta = "";
    
    
    DetalleCuenta cuenta = null;
    DetalleDocumento cuentaDocClaro = null;
    
    OperacionOut opOut = null;
    
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
     
        switch( estado ) {
            case 0:
                estado = 1;
                vista.setEntryMessage( "Ingrese Documento", true );
    			vista.setEntryTextLabel("Ingrese Documento:", true);
    			vista.setEntryText("", true, false, false,"[0-9]+","Documento no válido");
                return ICajaView._WAITFORACTION;
                
            case 1:
                String codigo = vista.getEntryText();
                
                if("".trim().equalsIgnoreCase(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "No ha ingresado ningún Número de Documento - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Documento:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                if(!Tools.validaNumerico(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "Docuemnto no válido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Documento:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                
                nroCuenta = codigo;
                
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
                opIn.setTipoOperacion("doc"); // se setea el tipo de busqueda que debe realizar en este caso es CUENTA !!
                Base.logger.info("Valor Tipo Operacion: "+  opIn.getTipoOperacion());
                opIn.setValorOperacion(nroCuenta);
                Base.logger.info("Valor Operacion: "+  opIn.getValorOperacion());
                
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
				
				
				// Instanciar DTO Respuesta para rescatar codigo de error !!
				Respuesta r = resp.value;
				// Instanciar DTO Respuesta Operacion para obtener la info !!
				opOut = opOutHold.value;

				if(r.getRetCode() != 0){	
					Base.logger.error("No existen datos [RetCode]:" + r.getRetCode() + " [msg]:" + r.getRetDescError());
					JOptionPane.showMessageDialog(null, r.getRetDescError(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				
				estado = 2;
				return ICajaView._NOWAITFORACTION;
				
            case 2:
                estado = 3;
                try{
                	clientesCuentas = new String[opOut.getDetalleDocumentos().length + 1];
        			Base.logger.info("Cantidad de Documentos: "+  opOut.getDetalleDocumentos().length);

        			vista.hideAllEntries();
        			vista.setEntryTitle( "Consulta por Documento SAP", true );

        			//clientesCuentas[0] = String.format("%-13s %-13s %-12s %-13s", "Cuenta Unica", "Cuenta","Saldo","Dir.Cobranza");
        			clientesCuentas[0] = String.format("%-7s %-6s %-10s %-12s %-10s %-8s %-8s %-10s"
        					, "Origen", "T. Doc", "Num Doc", "T. Reg.", "Servicio", "Cuenta", "Fec Venc", "Saldo");
        			//, "Origen", "T. Doc", "Num Doc", "Servicio", "Cuenta", "Fec Emis", "Fec Venc", "Saldo");

        			//for(int i = 0; i < out.getCuentas().length; i++){ 
        			for(int i = 0; i < opOut.getDetalleDocumentos().length; i++){ 
        				//String fecha = Tools.getFecha(out.getCuentas()[i].getFechaVencimiento().getTime());
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

        			rut = opOut.getRutCliente() + "-" + opOut.getDvCliente();

        			// vista.setEntryMessage( "Nombre de Cliente: " + out.getCliente().getNombreCliente() + " | Rut: " + Tools.limpiarRut(out.getCliente().getRutCliente()), true );
        			vista.setEntryMessage( "Nombre de Cliente: " + opOut.getDetalleDocumentos()[0].getNombreCliente() + " | Rut: " + opOut.getRutCliente() + "-" + opOut.getDvCliente(), true );
        			vista.setEntryList(clientesCuentas, true, false,index + 1);
        			return ICajaView._WAITFORACTION;
        			} catch (Exception e){
        				JOptionPane.showMessageDialog(null, "NO EXISTEN DOCUMENTOS", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        				return 20;
        			}
                
            case 3:    
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

    			//cuenta = out.getCuentas()[index];
    			cuentaDocClaro = opOut.getDetalleDocumentos(index);
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
    			doc.getDatos().setValue("TotalSaldo", opOut.getDetalleDocumentos(index).getMontoTotalDocumento());

    			return ICajaView._NOWAITFORACTION;
                
            case 4:
            	estado = 8;
            	vista.setEntryMessage( "Ingrese monto a abonar", true );
                vista.setEntryTextLabel("Ingrese monto a abonar:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
                
            case 5:
            	estado = 6;
            	monto = Long.parseLong(vista.getEntryText()); 
            	
                doc.getDatos().setValue("TipoRegistroClaro", "DEUDAMAYOR");
                doc.getDatos().setValue("TotalSC", Long.valueOf("0"));
                doc.getDatos().setValue("Rut", rut);  
                doc.getDatos().setValue("Monto", monto);
                doc.getDatos().setValue("SaldoAdeudadoClaro", monto);
                doc.getDatos().setValue("ClienteClaro", "");
                doc.getDatos().setValue("Tipo", "DocumentoAbonoClaro");
                return ICajaView._NOWAITFORACTION;
                
            case 6:
            	String  aFavor="Saldo Total: ";
            	long saldoSC = 0;
            	long saldo = 0;
            	long totalCuenta = new Long(doc.getDatos().getStringValue("TotalSaldo"));
            	//long totalSC = new Long(doc.getDatos().getStringValue("TotalSC"));
            	

            		if (totalCuenta == 0){
                		
                		saldo = monto;
                		aFavor = "Saldo a Favor Total: ";
                		
                		
                	}else{
                		
                		if (totalCuenta > monto){
                			saldo = totalCuenta;
                			totalCuenta = new Long(doc.getDatos().getStringValue("TotalSaldo")) - monto;
                	            			
                		}else{
                			saldo =  monto - new Long(doc.getDatos().getStringValue("TotalSaldo")) ;
                			aFavor = "Saldo a Favor Total: ";
                			totalCuenta =0;
                		}	
                	}	
            	
            	vista.hideAllEntries();
                vista.setEntryTitle( "Abono por Documento", true );
                vista.setEntryTextArea("Monto: " + doc.getDatos().getStringValue("Monto")
                		+"\nNumero Cuenta: " + doc.getDatos().getStringValue("CuentaClaro")
                		+"\nSistema Origen: " + doc.getDatos().getStringValue("SistemaOrigenClaro")
                		+"\n"+ aFavor +   saldo
                		+"\n"+"Saldo Actual: "+ totalCuenta
                        , true);
                estado = 7;
                return ICajaView._WAITFORACTION;
                
            case 7:
            	if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
                    Base.logger.info("Documento ya existe en carro de compras");
                    JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    estado = 2;
                    return ICajaView._NOWAITFORACTION;
                }
            	
                // Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
                if(Base.getEdicion()){
	                if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("Monto"))){
	                	Base.logger.info("El Abono se puede agregar al carro, el monto es menor al de los Medios de pago");
	                }else{
	                	Base.logger.info("El Abono No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
	                    JOptionPane.showMessageDialog(null, "El Abono No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	                    estado = 2;
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
            
            case 8:
            	estado = 5;
            	monto = Long.parseLong(vista.getEntryText());
            	if(monto == Long.parseLong("0"))
            	{
            		estado = 8;
            		vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(monto), true );
                    vista.setEntryTextLabel("Ingrese monto a abonar:", true);
                    vista.setEntryText("", true, false, false,"[0-9]+","Monto ingresado no válido");
                    return ICajaView._WAITFORACTION;
            	}
                return ICajaView._NOWAITFORACTION;
        }
        return 0;
    }
    
    private DetalleDocumento llenarDocumento(DetalleCuenta cuenta) {
        DetalleDocumento d = new DetalleDocumento();
        d.setFechaEmisionDocumento("");
        d.setFechaVencimientoDocumento("");
        d.setSaldo(0);
        d.setSaldoAdeudado(0);
        d.setNumeroCuenta(cuenta.getNumeroCuenta());
        d.setDireccionCliente("");
        d.setSistemaOrigen(cuenta.getSistemaOrigen());
        d.setNombreCliente("");
        d.setFolioDocumento(0);
        d.setTipoDocumento("");
        d.setIdServicio("");
        d.setTipoRegistro("PagoAbono");
        d.setCodigoEmpresa(8);
        d.setCodigoPortador(0);
       
        return d;
	}
}
