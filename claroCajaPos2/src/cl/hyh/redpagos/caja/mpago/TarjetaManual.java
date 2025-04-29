package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import ws.claro.cl.MedioPagoDTO;

import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;

/**
 * Medio de pago: Efecivo
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TarjetaManual extends MedioPago implements ITrxBase {
	
    int estado = 0;
    String[] opt;
    int index = 0;
    String tipoTarjeta = "";
    long montoTotal = 0;
    long montoP = 0;
    
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("TarjetaManual");
        this.setNombre( "TarjetaManual" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
        	//if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
            if(!this.isIngresable(vista)){
                JOptionPane.showMessageDialog(null, "Medio de Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            
            if(!vista.getOperTRV().isValid(this)){
            	Base.logger.info("No se pudo agregar un Segundo Medio de Pago ...");
                return 13;
            }
            
            estado = 0;
            //return this.setMontoMPago(vista);  
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            case 0:
                estado = 10;
                DefMedioPago def = Base.getDefMedioPago("TarjetaManual");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
                
                return this.setMontoMPago(vista);
                
            case 1:                
                index = vista.getEntryListIndex();
                tipoTarjeta = opt[index];
                tipoTarjeta = tipoTarjeta.split(",")[1];
                
                estado = 2;
                //this.datos.setValue("TipoTotal", "mpCreditoManual" );
                this.datos.setValue("TipoTotal", "mpMultitienda" );
                this.datos.setValue("isCredito", "1" );
               // this.datos.setValue("TipoTarjeta", "Crédito" );
//                this.datos.setValue("TipoTarjeta", "Multitienda" );
                this.datos.setValue("TipoTarjeta", tipoTarjeta );
                this.datos.setValue("tbkContingencia", "c");
                this.datos.setValue("idTrxTbk","0");
            	this.datos.setValue("fechaContable","");
            	this.datos.setValue("productoTarjeta","0");
                
               // this.tipoTarjeta = "Crédito";
//                this.tipoTarjeta = "Credito";
                return ICajaView._NOWAITFORACTION;
                
                
            case 2:
                estado = 3;
                this.datos.setValue("isCredito", "1" );
                
                vista.setEntryMessage( "Ingrese cantidad de cuotas", true );
                vista.setEntryTextLabel("N° de cuotas:", true);
                vista.setEntryText("1", true, false, false,"[0-9]+","Número de cuotas inválido");
                return ICajaView._WAITFORACTION;
                
            case 3:
                estado = 4;
                vista.setEntryMessage( "Ingrese cantidad de cuotas", true );                
                
                String cuotas = vista.getEntryText();
                if(Integer.parseInt(cuotas) > 36){
                    estado = 3;
                    vista.setEntryMessage( "Número de cuotas inválido", true );
                    vista.setEntryText("0", true, false, false,"[0-9]+","Número de cuotas inválido");
                    vista.setEntryTextLabel("N° de cuotas:", true);
                    return ICajaView._WAITFORACTION;
                }
                else{
                	this.datos.setValue("NumeroCuotas", cuotas);
                }
                
                return ICajaView._NOWAITFORACTION;
                
            case 4:
                estado = 5;
                vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese Código Autorización", true );
                vista.setEntryTextLabel("Código Autorización:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;      
                
            case 5:
                estado = 6;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 20 ){
                    estado = 5;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                    vista.setEntryMessage( "Código inválido", true );
                    vista.setEntryTextLabel("Código Autorización:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                this.datos.setValue("CodAutorizacion", vista.getEntryText() );
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese N° Operación", true );
                vista.setEntryTextLabel("Ingrese N° Operación:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION; 
                
            case 6:
                estado = 7;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 26 ){
                    estado = 6;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                    vista.setEntryMessage( "N° inválido", true );
                    vista.setEntryTextLabel("Ingrese N° Operación:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                //this.datos.setValue("NumeroUnico", vista.getEntryText() );
                String []emisores = Base.getList( Base.getEmisores(), ",", 1);
                int index = 0 ;
                for(int i = 0 ; i < emisores.length ; i++){
                    if(tipoTarjeta.contains(emisores[i])){
                        index = i;
                        break;
                    }
                }
                this.datos.setValue("Emisor", Base.getList( Base.getEmisores(), ",", 0)[index] );
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese N° Tarjeta", true );
                vista.setEntryTextLabel("N° Tarjeta:", true);
                vista.setEntryText("0", true, false, false,"[0-9]+","Número de tarjeta inválido");
                return ICajaView._WAITFORACTION;                      
                
            case 7:
                if(vista.getEntryText().length() > 16 || vista.getEntryText().length() < 4){
                    estado = 7;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                    vista.setEntryMessage( "Ingrese N° Tarjeta", true );
                    vista.setEntryTextLabel("N° Tarjeta:", true);
                    vista.setEntryText("0", true, false, false,"[0-9]+","Número de tarjeta inválido");
                    return ICajaView._WAITFORACTION;
                }
                
                this.datos.setValue("NumeroUnico", vista.getEntryText());
                this.datos.show("Tarjeta Manual");
                
                try {
                    vista.getOperTRV().addMedioPago(this);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                }
                return 13;
                                
            case 10:
            	estado = 11;
            	montoP = Long.parseLong(vista.getEntryText());
            	
                if(montoP  == 0){
                    estado = 10;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
                
                this.datos.setValue("Monto", montoP);
                return ICajaView._NOWAITFORACTION;

            case 11:
            	estado=1;
            	vista.hideAllEntries();
                vista.setEntryTitle( "TARJETA CARGA MANUAL", true );
                vista.setEntryTextLabel("Tipo Tarjeta: ", true);
                // TODO implementar llamada al servicio para traer tarjetas.. OK !!
                opt = Base.traeTarjetas();
                vista.setEntryList(Base.getList(opt,",",1), true, false);
                
                
                this.datos.setValue("tbkContingencia", "c");
                
                return ICajaView._WAITFORACTION;     
                
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
    
    public void llenarMP(MedioPagoCaja mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion(this.getDatos().getStringValue("TipoTotal"));
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodAutorizacion"));
    	mp.setNumeroTarjeta(this.getDatos().getStringValue("NumeroUnico"));
    	mp.setCantidadCuotas(Integer.parseInt(this.getDatos().getStringValue("NumeroCuotas")));
    	if(this.getDatos().getStringValue("TipoTotal").equals("mpDebitoManual")){
    		// Validar cual debe ser el tipo total ??
        	// mp.setTipoTotal("1007");
        	mp.setTipoTotal("7");
    	}
    	else{
    		// Validar cual debe ser el tipo total ??
        	// mp.setTipoTotal("1008");
        	mp.setTipoTotal("8");
    	}
    //	mp.setTbkContingencia(this.getDatos().getStringValue("tbkContingencia"));
    }
    
    public String[] obtenerTipoTotalTarjeta (String nombreTarjeta) {
    	
    	Boolean obtuvoTipoTotal = false;
    	String[] tipoTotal = new String[2];
    	
    	Base.logger.info("obtenerTipoTotalTarjeta : " + nombreTarjeta.toUpperCase());
    	
    	//Tipo total es Credito
    	String tarjetasCreditoCod[] = Base.getList( Base.getEmisoresCredito(), ",", 0);
    	String tarjetasCreditoDes[] = Base.getList( Base.getEmisoresCredito(), ",", 1);
    	
    	for(int i=0; (i<tarjetasCreditoDes.length) && (!obtuvoTipoTotal);i++) {
//    		Base.logger.info("TARJETA !! : " + this.getDatos().
//    				getStringValue("TipoTarjeta").toUpperCase());
//    		Base.logger.info("comparando con : " + tarjetasCreditoDes[i].toUpperCase());
    		if(tarjetasCreditoDes[i].toUpperCase().equalsIgnoreCase(this.getDatos().
    				getStringValue("TipoTarjeta").toUpperCase())) {
    			tipoTotal[0] = tarjetasCreditoCod[i];
    			tipoTotal[1] = "mpCredito";
    			obtuvoTipoTotal = true;
    		}
    	}
    	//Tipo total es Casa comercial
    	String tarjetasCCCod[] = Base.getList( Base.getEmisoresCasaComercial(), ",", 0);
    	String tarjetasCCDes[] = Base.getList( Base.getEmisoresCasaComercial(), ",", 1);
    	
    	for(int i = 0; (i<tarjetasCCDes.length) && (!obtuvoTipoTotal);i++) {
//    		Base.logger.info("TARJETA !! : " + this.getDatos().
//    				getStringValue("TipoTarjeta").toUpperCase());
//    		Base.logger.info("comparando con : " + tarjetasCCDes[i].toUpperCase());
    		if(tarjetasCCDes[i].toUpperCase().equalsIgnoreCase(this.getDatos().
    				getStringValue("TipoTarjeta").toUpperCase())) {
    			tipoTotal[0] = tarjetasCCCod[i];
    			tipoTotal[1] = "mpMultitienda";
    			obtuvoTipoTotal = true;
    		}
    	}
    	
    	//Tipo total es Debito
    	String tarjetasDebitoCod[] = Base.getList( Base.getEmisoresDebito(), ",", 0);
    	String tarjetasDebitoDes[] = Base.getList( Base.getEmisoresDebito(), ",", 1);
    	
    	for(int i=0; (i<tarjetasDebitoDes.length) && (!obtuvoTipoTotal);i++) {
    		if(tarjetasDebitoDes[i].toUpperCase().equalsIgnoreCase(this.getDatos().
    				getStringValue("TipoTarjeta").toUpperCase())) {
    			tipoTotal[0] = tarjetasDebitoCod[i];
    			tipoTotal[1] = "mpDebito";
    			obtuvoTipoTotal = true;
    		}
    	}
    	
    	Base.logger.info("Tipo Total Encontrado : " + tipoTotal[0] + " - " + tipoTotal[1]);
    	
    	return tipoTotal;
    	
    }
    
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion(this.getDatos().getStringValue("TipoTotal"));
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodAutorizacion"));
    	mp.setNumeroTarjeta(this.getDatos().getStringValue("NumeroUnico"));
    	mp.setCantidadCuotas(Integer.parseInt(this.getDatos().getStringValue("NumeroCuotas")));
    	if(this.getDatos().getStringValue("TipoTotal").equals("mpDebitoManual")){
    		// Validar cual debe ser el tipo total ??
        	// mp.setTipoTotal("1007");
        	mp.setTipoTotal("7");
    	}
    	else if(this.getDatos().getStringValue("TipoTotal").equals("mpCreditoManual")){
    		// Validar cual debe ser el tipo total ??
        	// mp.setTipoTotal("1008");
        	mp.setTipoTotal("8");
    	}else{ // Esta opcion debiese ser para Multitiendas ??
//    		mp.setTipoTotal("4");
    		String[] tipoTotalTarjeta = obtenerTipoTotalTarjeta(this.getDatos()
					.getStringValue("TipoTarjeta"));
			if(tipoTotalTarjeta != null && tipoTotalTarjeta[0] != null) {
			mp.setTipoTransaccion(tipoTotalTarjeta[1]);
			mp.setTipoTotal(tipoTotalTarjeta[0]);
			} else {
			mp.setTipoTransaccion("mpMultitienda");
			mp.setTipoTotal("4");
			}
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
    }

    // TODO validar informacion que se debe cargar para Tarjetas manuales.
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
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
    	this.getDatos().setValue("tbkContingencia",mp.getTbkContingencia());
    	this.getDatos().setValue("idTrxTbk",mp.getIdTrxTbk());
    	this.getDatos().setValue("fechaContable",mp.getFechaContable());
    	this.getDatos().setValue("productoTarjeta",mp.getProductoTarjeta());
    	this.getDatos().setValue("tbkContingencia",mp.getTbkContingencia());
    }
    
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}
    
}
