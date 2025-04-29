package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.cajas.ws.impl.ConsultaDeudaRutVTRIn;
import cl.hyh.cajas.ws.impl.ConsultaDeudaVtrOut;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.cajas.ws.impl.DocumentoVTR;
import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.ServicioVTR;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
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
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoServicioVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoVtr;

/**
 * Implementacion de la transaccion de consulta de documentos Atis 
 * por medio de codigo de barra
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TrxVtrRutCut implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    DocumentoPago doc;
    String []docs = null;
    ArrayList<Datos> clientes;
    ArrayList<Datos> detalle;
    ArrayList<Datos> detalleCut;
    ArrayList<Datos> clientesCut;
    String []clientesCuentas;
    protected Datos dataPagina;
    protected int nextPage = 0;
    protected ArrayList <Integer> paginas = new ArrayList<Integer>();
    Servicio consultaTelefonica = null;
    Servicio consultaTelefonicaDetalle = null;
    String tiposDocs = "";
    boolean offline = false;
    boolean unico = false;
    int index = 0;
    boolean isCut = false;
    Object [][]matrix = null;
    String rut = "";
    String prod = "";
    ConsultaDeudaVtrOut out = null;
    CuentaVTR cuenta = null;
    ArrayList <String> prods = null;
    
    
    public void init(Datos datosVista){
        tiposDocs = "Siscli";
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
     
        switch( estado ) {
            case 0:
                estado = 1;
                vista.setEntryMessage( "Ingrese Rut Formato: 14566941-3", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 1:
                String codigo = vista.getEntryText();
                if(!Tools.validarRut(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "RUT  Invalido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                
                
                
                
                
                rut = codigo;
                
                ServerProxy pr = Proxy.getProxyInstance();
            	ConsultaDeudaRutVTRIn in = new ConsultaDeudaRutVTRIn();
            	HeaderIn hIn = new HeaderIn();
            	ParamSet pSet = Base.getParamSet("posDat");
            	
            	hIn.setAgencia((int)(pSet.getLongValue("Agencia")));
        		hIn.setCajaFisica((int)(pSet.getLongValue("Caja")));
        		hIn.setEntidad((int)(pSet.getLongValue("Entidad")));
        		hIn.setCajero(Integer.parseInt(pSet.getStringValue("Cajero")));
        		hIn.setSession(Integer.parseInt(pSet.getStringValue("SessionId")));
        		hIn.setUsuario(pSet.getStringValue("Usuario"));
        		hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
            	
        		in.setHeaderIn(hIn);
            	in.setRut(rut);
            	in.setTipoConsulta("ConsultaDetalleCut");
            	
            	vista.showBusyWindow("Consultando", "Espere por favor...");
				try {
					out = pr.consultaDeudaVtr(in);
				} catch (RemoteException e2) {
					vista.hideBusyWindow();
                    JOptionPane.showMessageDialog(null, "Error de conexión", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					Tools.logStackTrace(Base.logger, e2);
					return 20;
				}
				vista.hideBusyWindow();
				if(out.getHeaderOut().getRc() != 0){
					Base.logger.error("No existen datos [rc]:" + out.getHeaderOut().getRc() + " [msg]:" + out.getHeaderOut().getRcMessage());
					JOptionPane.showMessageDialog(null, out.getHeaderOut().getRcMessage(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 20;
				}
				estado = 2;
				return ICajaView._NOWAITFORACTION;
            case 2:
            	vista.paintButtons(Tools.getBotones(6));
                estado = 3;
                clientesCuentas = new String[out.getCuentas().length + 1];
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Rut", true );
                clientesCuentas[0] = String.format("%-13s %-13s %-12s %-13s", "Cuenta Unica", "Cuenta","Saldo","Dir.Cobranza");
                for(int i = 0; i < out.getCuentas().length; i++){     
                	String fecha = Tools.getFecha(out.getCuentas()[i].getFechaVencimiento().getTime());
                    clientesCuentas[i + 1] = String.format("%13s %13s $%12s %13s",out.getCuentas()[i].getCuentaUnica() ,out.getCuentas()[i].getNumeroCuenta(),Format.formatMontoPantalla((long)out.getCuentas()[i].getSaldoCuenta()),out.getCuentas()[i].getDireccionCobranza());
                }
                vista.setEntryMessage( "Nombre de Cliente: " + out.getCliente().getNombreCliente() + " | Rut: " + Tools.limpiarRut(out.getCliente().getRutCliente()), true );
                vista.setEntryList(clientesCuentas, true, false,index + 1);
                return ICajaView._WAITFORACTION;
            case 3:                
                doc = null;
                index = vista.getEntryListIndex();
                if(index == 0){
                    estado = 2;
                    return ICajaView._NOWAITFORACTION;
                }
                else{
                    index = index -1;
                }
                estado = 2;
                cuenta = out.getCuentas()[index];
                for(int i = 0 ; i < out.getServicios().length ; i++){
                	String cUnicaOut = Long.toString(out.getServicios()[i].getNumeroCuenta());
                	String cUnicaProd = Long.toString(cuenta.getNumeroCuenta());
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
                        doc.getDatos().setValue("Cut", "si");
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
                        doc.getDatos().show("Servicio VTR");
                	}                		
                	
                }                               
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
                cuenta = out.getCuentas()[index];
                estado = 201;
                return ICajaView._NOWAITFORACTION;
            case 201:            	
                vista.paintButtons(Tools.getBotones(3));
            	estado = 21;
            	index = 0;
            	int cant = 0;
            	prods = new ArrayList<String>();
            	
            	for(int i = 0 ; i < out.getServicios().length; i++){
            		ServicioVTR producto = out.getServicios()[i];
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
            	}
            	
                clientesCuentas = new String[cant + 1];
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta por Rut - Detalle Productos", true );
                clientesCuentas[0] = String.format("%-13s %-6s %-13s %-13s %-12s %-13s", "Tipo Producto","Estado","Cuenta Unica", "Cuenta","Saldo","Dir.Cobranza");
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
                        doc.getDatos().setValue("Cut", "si");
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
                vista.setEntryTitle( "Consulta por Rut - Detalle Servicio", true );
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
                vista.setEntryTitle( "Consulta por Rut - Detalle Servicios", true );
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
                doc.getDatos().setValue("Cut", "si");
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
                        doc.getDatos().setValue("Cut", "si");
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
                        doc.getDatos().setValue("Cut", "si");
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
