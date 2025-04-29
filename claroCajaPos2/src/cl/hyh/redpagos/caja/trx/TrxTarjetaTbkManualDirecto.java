package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.mpago.TarjetaManual;
import cl.hyh.redpagos.caja.mpago.TbkManual;
import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.proxy.AppControlProxy;

/**
 * Medio de pago: Efecivo
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TrxTarjetaTbkManualDirecto extends MedioPago implements ITrxBase {
    int estado = 0;
    String[] opt;
    int index = 0;
    String tipoTarjeta = "";
    String email;
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("TarjetaTbkManualDirecto");
        this.setNombre( "TarjetaTbkManualDirecto" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){
 
    		ParamSet posDat = Base.getParamSet( "posDat" );
        	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
        if( key == 0 ) {
        	/**
            if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
            	JOptionPane.showMessageDialog(null, "No tiene documentos en el carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            */
        	estado = 0;
        	if(vista.getOperTRV().getCarroCompras().getDocumentos().size() == 0){
            	JOptionPane.showMessageDialog(null, "No tiene documentos en el carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                JOptionPane.showMessageDialog(null, "Carro de Medios de Pago ya existe\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            //return this.setMontoMPago(vista);  
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else if( key == KeyEvent.VK_F9 ) {
        	
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                vista.getOperTRV().getCarroMediosPago().borrarCarro();
            }
            return 13;
        	
        } else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            case 0:
                estado = 1;
                DefMedioPago def = Base.getDefMedioPago("TarjetaTbkManual");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal());
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryTextLabel("Tipo Tarjeta: ", true);
                opt = new String[2];
                opt[0] = "Tarjeta Crédito";
                opt[1] = "Tarjeta Débito";
                vista.setEntryList(opt, true, false);
                return ICajaView._WAITFORACTION;            
            case 1:                
                index = vista.getEntryListIndex();
                tipoTarjeta = opt[index];
                if(index == 0){
                    estado = 2;
                    this.datos.setValue("TipoTotal", "mpCreditoManual" );
                    this.datos.setValue("isCredito", "1" );
                    this.datos.setValue("tbkContingencia", "c");
                    this.datos.setValue("idTrxTbk","0");
                	this.datos.setValue("fechaContable","");
                	this.datos.setValue("productoTarjeta","0");
                    //this.datos.setValue("TipoTarjeta", "Crédito" );
                    //this.tipoTarjeta = "Crédito";
                    return ICajaView._NOWAITFORACTION;
                }
                else if(index == 1){
                    estado = 4;
                    this.datos.setValue("TipoTotal", "mpDebitoManual" );
                    this.datos.setValue("TipoTarjeta", "Débito" );
                    this.datos.setValue("NumeroCuotas", "0" );
                    this.tipoTarjeta = "Débito";
                    this.datos.setValue("tbkContingencia", "c");
                    this.datos.setValue("idTrxTbk","0");
                	this.datos.setValue("fechaContable","");
                	this.datos.setValue("productoTarjeta","0");
                    return ICajaView._NOWAITFORACTION;
                }
                
            case 2:
            	estado = 3;
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryTextLabel("Tipo Tarjeta: ", true);
                opt = Base.getEmisoresCredito();
                vista.setEntryList(Base.getList(opt,",",1), true, false);
                
                return ICajaView._WAITFORACTION;
            case 3:
                estado = 4;
                
                index = vista.getEntryListIndex();
                tipoTarjeta = opt[index].split(",")[0];
                Base.logger.info("TIPO TARJETA : " + tipoTarjeta);
                
                this.datos.setValue("isCredito", "1" );
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryTextLabel("Plan Cuotas: ", true);
                String []cuotas = new String[2];
                cuotas[0] = "Contado";
                cuotas[1] = "Contado 3 Cuotas";
                vista.setEntryList(cuotas, true, false);
                return ICajaView._WAITFORACTION;
            case 4:
                estado = 5;
                index = vista.getEntryListIndex();
                if( index == 0){
                    this.datos.setValue("NumeroCuotas", "0" );
                }
                else if( index == 1 ){
                    this.datos.setValue("NumeroCuotas", "3" );
                }
                return ICajaView._NOWAITFORACTION;
            case 5:
                estado = 6;
                vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese Código Autorización", true );
                vista.setEntryTextLabel("Código Autorización:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;                
            case 6:
                estado = 7;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 20 ){
                    estado = 6;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TBK CARGA MANUAL", true );
                    vista.setEntryMessage( "Código inválido", true );
                    vista.setEntryTextLabel("Código Autorización:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                this.datos.setValue("CodAutorizacion", vista.getEntryText() );
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese N° Operación", true );
                vista.setEntryTextLabel("Ingrese N° Operación:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION; 
            case 7:
                estado = 8;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 26 ){
                    estado = 7;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                    vista.setEntryMessage( "N° inválido", true );
                    vista.setEntryTextLabel("Ingrese N° Operación:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                //this.datos.setValue("NumeroUnico", vista.getEntryText() );
                String []emisores = Base.getList( Base.getEmisores(), ",", 1);
                for(int i = 0 ; i < emisores.length ; i++){
                    if(tipoTarjeta.contains(emisores[i])){
                        break;
                    }
                }
                //this.datos.setValue("Emisor", Base.getList( Base.getEmisores(), ",", 0)[index] );
                this.datos.setValue("Emisor", "1000" );
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese N° Tarjeta", true );
                vista.setEntryTextLabel("N° Tarjeta:", true);
                vista.setEntryText("0", true, false, false,"[0-9]+","Número de tarjeta inválido");
                return ICajaView._WAITFORACTION;
                
            case 8:
            	estado = 9;
            	
            	if(vista.getEntryText().length() > 16 || vista.getEntryText().length() < 4){
                    estado = 8;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                    vista.setEntryMessage( "Ingrese N° Tarjeta", true );
                    vista.setEntryTextLabel("N° Tarjeta:", true);
                    vista.setEntryText("0", true, false, false,"[0-9]+","Número de tarjeta inválido");
                    return ICajaView._WAITFORACTION;
                }
            	this.datos.setValue("tbkContingencia", "c");  
                this.datos.setValue("NumeroUnico", vista.getEntryText());
                this.datos.show("Tarjeta Manual");
                
                try {
                	/*
                	//TarjetaManual tar = new TarjetaManual();
                	TbkManual tar = new TbkManual();
                	//tar.setTipoTarjeta(this.tipoTarjeta);
	            	DefMedioPago dMP = Base.getDefMedioPago("TarjetaTbkManual");
	                tar.setNombre( "TarjetaTbkManual" );
	                tar.setDatos( new Datos( dMP.getRecordDef() ) );
                	tar.getDatos().asignaPorNombre(this.getDatos());
                    vista.getOperTRV().addMedioPago(tar);
                    */
                	vista.getOperTRV().addMedioPago(this);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                }
                return ICajaView._NOWAITFORACTION;
            case 9:  ////// NUEVO PARA IMPRIMIR PDF
                estado = 11;
                if(isPdf) {
                	LineaVoucher []aux = null;
            		Set<String> ruts = new HashSet<String>();
            		ArrayList rut = new ArrayList();
            		for(int i = 0; i < vista.getOperTRV().getCarroCompras().getDocumentos().size(); i++){
            			aux = vista.getOperTRV().getCarroCompras().getDocument(i).getCustomerVoucher();
            			
            			for(int j = 0; j < aux.length; j++){
            				if (aux[j].getLinea().contains("RUT:")) {
            					ruts.add(aux[j].getLinea().replace("RUT:", "").trim());
            				}
            			}
            		}
            		
            		String[] listaRuts = new String[0];
            		Base.logger.info("DOCUMETO1: "+ruts.toString());
            		AppControlCajaWSServerProxy pr2 = AppControlProxy.getProxyInstance();
            		try {
            			email = pr2.obtenerEmailsPorRut(ruts.toArray(new String[ruts.size()]));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
            		

                	Base.logger.info("EMAILS: "+email);

                vista.setEntryMessage( "Email(s) para el envio de Boleta", true );
    			vista.setEntryTextLabel("Email(s):", true);
                vista.setEntryText(email, true, false, false, null, null);
                return ICajaView._WAITFORACTION;
                }                                   
                
            case 11:
                estado = 12;
				email = vista.getEntryText();
                Base.logger.info("EMAILS: "+email);
                this.datos.setValue("emails",email);
                vista.hideAllEntries();
                vista.setEntryTitle( "Pago Tarjeta Manual", true );
                vista.setEntryTextLabel("Medio Pago:", true);
                vista.setEntryTextArea(vista.getOperTRV().imprimirBoletaPantalla(), true);
                return ICajaView._WAITFORACTION;
                
            case 12:            
            	 // Se agrega confirmacion para generar Notificacion de Pago...
            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		vista.showBusyWindow("Enviando", "Espere por favor...");
                    vista.getOperTRV().confirmar(vista);
                    vista.hideBusyWindow();
                    
            	}
            	vista.removeTRV(); 
                return 14;
                
                
        }
        
        return 0;
    }
    
    public boolean isIngresable(ICajaView vista){
        if(Base.getEdicion()){
            return false;
        }
        return true;
    }
    
    public String glosaCarro() {
        return String.format( "%-20s %-20s", "Tarjeta " + this.tipoTarjeta, Format.formatMonto(datos.getLongValue( "Monto" )) );
    }
    
    public void llenarMP(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion(this.getDatos().getStringValue("TipoTotal"));
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodAutorizacion"));
    	mp.setNumeroTarjeta(this.getDatos().getStringValue("NumeroUnico"));
    	mp.setCantidadCuotas(Integer.parseInt(this.getDatos().getStringValue("NumeroCuotas")));
    	if(this.getDatos().getStringValue("TipoTotal").equals("mpDebitoManual")){		
    		mp.setTipoTotal("3");
        	mp.setTipoTransaccion("mpDebito");
    	}
    	else if(this.getDatos().getStringValue("TipoTotal").equals("mpCreditoManual")){
    		mp.setTipoTotal(tipoTarjeta);
        	mp.setTipoTransaccion("mpCredito");
    	}
    	mp.setIdTrxTbk(this.getDatos().getStringValue("idTrxTbk"));
    	mp.setFechaContable(this.getDatos().getStringValue("fechaContable"));
    	mp.setProductoTarjeta(this.getDatos().getStringValue("productoTarjeta"));
    	mp.setTbkContingencia(this.getDatos().getStringValue("tbkContingencia"));
    }
    
    public void vaciarMP(MedioPagoCaja mp){
    	this.getDatos().setValue("Monto",mp.getMonto());
    	this.getDatos().setValue("CodAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("NumeroCuotas", mp.getCantidadCuotas());
    	this.getDatos().setValue("NumeroUnico", mp.getNumeroTarjeta());
    	this.getDatos().setValue("Emisor","1000");
    	if(mp.getTipoTransaccion().equals("mpCreditoManual")){
    		this.datos.setValue("isCredito", "1" );
    	}
    	else{
    		this.datos.setValue("isCredito", "0" );
    	}
    	this.getDatos().setValue("TipoTotal",mp.getTipoTransaccion());
    	this.getDatos().setValue("idTrxTbk", "0");
    	this.getDatos().setValue("fechaContable","");
    	this.getDatos().setValue("productoTarjeta","0");
    	this.getDatos().setValue("tbkContingencia","c");
    }
}
