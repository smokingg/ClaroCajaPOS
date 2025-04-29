package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import ws.claro.cl.proxy.AppControlConsultarProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
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
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.docpago.DocumentoAbonoClaro;

public class TrxAccesorioClaro implements ITrxBase{

	int estado = 0;
	String rut = "";
	String nroDoc = "";
	String glosa = "";
	String vendedor = "";
	long monto;
	DocumentoPago doc;
	
	@Override
	public int execute(ICajaView vista, int key, Datos htParam) {
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
                vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 1:
                String codigo = vista.getEntryText();
                
                if("".trim().equalsIgnoreCase(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "No ha ingresado ningun Rut - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                if(!Tools.validarRut(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "RUT  Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                

                
                rut = codigo;
                
				estado = 2;
				return ICajaView._NOWAITFORACTION;
				
            case 2:
                estado = 3;
                vista.setEntryMessage( "Ingrese Nro. Documento", true );
                vista.setEntryTextLabel("Ingrese Nro. Documento:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 3:                
            	codigo = vista.getEntryText();
            	
                if("".equalsIgnoreCase(codigo)){
                    estado = 3;
                    vista.setEntryMessage( "Documento Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Nro. Documento:", true);
                    vista.setEntryText("", true, false, false, null, null);
                    return ICajaView._WAITFORACTION;
                }
                
                nroDoc = codigo;
				
				estado = 4;
				return ICajaView._NOWAITFORACTION;
                
            case 4:
            	estado = 41;
            	vista.setEntryMessage( "Ingrese monto", true );
                vista.setEntryTextLabel("Ingrese monto:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
                
            case 41:
            	estado = 42;
            	monto = Long.parseLong(vista.getEntryText());
            	
            	vista.setEntryMessage( "Ingrese Vendedor", true );
                vista.setEntryTextLabel("Ingrese Vendedor:", true);
                vista.setEntryText("", true, false, false, null, null);
                return ICajaView._WAITFORACTION;
            
            case 42:
            	vendedor = vista.getEntryText();
            	
            	if("".equalsIgnoreCase(vendedor.trim())){
                    vista.setEntryMessage( "Vendedor Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Vendedor:", true);
                    vista.setEntryText("", true, false, false, null, null);
                    return ICajaView._WAITFORACTION;
                }
            	
            	estado = 5;
            	vista.setEntryMessage( "Ingrese glosa", true );
                vista.setEntryTextLabel("Ingrese glosa:", true);
                vista.setEntryText("", true, false, false, null, null);
                return ICajaView._WAITFORACTION;
                
            case 5:
            	estado = 6;
            	glosa = vista.getEntryText();
            	
            	if("".equalsIgnoreCase(glosa.trim())){
                    estado = 5;
                    vista.setEntryMessage( "Glosa Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese glosa:", true);
                    vista.setEntryText("", true, false, false, null, null);
                    return ICajaView._WAITFORACTION;
                }
            	
            	try {
                    doc = FactoryDocumentoPago.makeInstance("DocumentoAccesorioClaro");
                } catch (BaseException e) {
                	Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("No se pudo instanciar el documento");
                    return 20;
                }
                
                // No invoco a llenar datos.. se realiza directamente !!
                // ((DocumentoAccesorioClaro)doc).llenarDatos(cuenta);
                
                doc.getDatos().setValue("Rut", rut);
                doc.getDatos().setValue("RutAccesorio", rut);  
                doc.getDatos().setValue("Monto", monto);
                doc.getDatos().setValue("NumeroDocumento", nroDoc);
                doc.getDatos().setValue("Glosa", glosa);
                doc.getDatos().setValue("Tipo", "DocumentoAccesorioClaro");
                doc.getDatos().setValue("CodigoRecaudador", vendedor);
                
                // Se agrega el Codigo del Recaudador y la Sucursal
                ParamSet pList = Base.getParamSet( "posDat" );
              //  doc.getDatos().setValue("CodigoRecaudador", pList.getStringValue("CodigoRecaudador"));
                doc.getDatos().setValue("CodigoSucursal", pList.getStringValue("Agencia"));
                
                return ICajaView._NOWAITFORACTION;
                
            case 6:
            	long saldo = monto;
            	
            	vista.hideAllEntries();
                vista.setEntryTitle( "Pago Accesorio", true );
                vista.setEntryTextArea("Monto: " + doc.getDatos().getStringValue("Monto")
                		+"\nRut Cliente: " + doc.getDatos().getStringValue("Rut")
                		+"\nNumero Documento: " + doc.getDatos().getStringValue("NumeroDocumento")
                		+"\nVendedor: " + doc.getDatos().getStringValue("CodigoRecaudador")
                		+"\nSucursal: " + doc.getDatos().getStringValue("CodigoSucursal")
                		+"\nGlosa: " + doc.getDatos().getStringValue("Glosa")
                		+"\nSaldo Total: " + saldo
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
                
                vista.hideAllEntries();
                try {
                    vista.getOperTRV().addDocumentoPago(doc);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar documento de pago al carro");
                }
                
                doc.getDatos().show("Accesorio Claro");
                /**
                vista.getOperTRV().getCarroCompras();
                vista.paintButtons(Tools.getBotones(2));
                estado = 0;
                return ICajaView._NOWAITFORACTION;
                */
                
                return 20;     
                   
        }
        return 0;
    }

	@Override
	public void init(Datos htParam) {
		// TODO Auto-generated method stub
	}

}
