package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.JOptionPane;

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
public class TbkManual extends MedioPago implements ITrxBase {
    int estado = 0;
    String[] opt;
    int index = 0;
    long montoTotal = 0;
    long montoP = 0;
    String tipoTarjeta = "";

    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("TarjetaTbkManual");
        this.setNombre( "TarjetaTbkManual" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
           // if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
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
                estado = 8;
                DefMedioPago def = Base.getDefMedioPago("TarjetaTbkManual");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());                
                return this.setMontoMPago(vista);
                
                /*
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryTextLabel("Tipo Tarjeta: ", true);
                opt = new String[2];
                opt[0] = "Tarjeta Crédito";
                opt[1] = "Tarjeta Débito";
                vista.setEntryList(opt, true, false);
                */       
            case 1:                
                index = vista.getEntryListIndex();
            	this.getDatos().setValue("idTrxTbk", "0");
            	this.getDatos().setValue("fechaContable","");
            	this.getDatos().setValue("productoTarjeta","0");
            	this.getDatos().setValue("tbkContingencia","c");
                if(index == 0){
                    estado = 10;
                    //this.datos.setValue("TipoTotal", "TC" );
                    this.datos.setValue("TipoTotal", "mpCreditoManual" );
                    this.datos.setValue("isCredito", "1" );
                    return ICajaView._NOWAITFORACTION;
                }
                else if(index == 1){
                    estado = 4;
                    //this.datos.setValue("TipoTotal", "TD" );                    
                    this.datos.setValue("TipoTotal", "mpDebitoManual" );
                    this.datos.setValue("NumeroCuotas", "0" );
                    return ICajaView._NOWAITFORACTION;
                }
            case 10:
                estado = 2;
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryTextLabel("Tipo Tarjeta: ", true);
                opt = Base.getEmisoresCredito();
                vista.setEntryList(Base.getList(opt,",",1), true, false);
                
                return ICajaView._WAITFORACTION;
            case 2:
                estado = 3;
                
//                tipotarjeta obtenido en case 11
                index = vista.getEntryListIndex();
                tipoTarjeta = opt[index].split(",")[0];
                Base.logger.info("TIPO TARJETA : " + tipoTarjeta);
                
                this.datos.setValue("isCredito", "1" );
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryTextLabel("Plan Cuotas: ", true);
                
                String[] cuotas = new String[3];
                cuotas[0] = "Sin Cuotas";
                cuotas[1] = "3 Cuotas Precio Contado";
                cuotas[2] = "Cuotas Normales";
                vista.setEntryList(cuotas, true, false);
                return ICajaView._WAITFORACTION;
            case 3:
                index = vista.getEntryListIndex();
                // Seleccion de Sin Cuotas
                if(index == 0){
                	estado = 4;
                    this.datos.setValue("NumeroCuotas", "0" );
                    return ICajaView._NOWAITFORACTION;
                }
                // Seleccion de 3 Cuotas precio contado
				if (index == 1) {
					estado = 4;
					this.datos.setValue("NumeroCuotas", "3");
					return ICajaView._NOWAITFORACTION;
				}
				// Seleccion de Cuotas normales
                if (index == 2) {
                	estado = 11;
                	String planCuotas = Base.getParamSet("posDat").getStringValue("planCuotas");
                	cuotas = planCuotas.split(",");
                	vista.setEntryList(cuotas, true, false);
                	return ICajaView._WAITFORACTION;
                }
                
            case 4:
                estado = 5;
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
                vista.setEntryMessage( "Ingrese Código Autorización", true );
                vista.setEntryTextLabel("Ingrese Código Autorización:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;                
            case 5:
                estado = 6;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 20){
                    estado = 5;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TBK CARGA MANUAL", true );
                    vista.setEntryMessage( "Código inválido", true );
                    vista.setEntryTextLabel("Ingrese Código Autorización:", true);
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
            case 6:
                estado = 7;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 26 ){
                    estado = 6;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "TBK CARGA MANUAL", true );
                    vista.setEntryMessage( "N° inválido", true );
                    vista.setEntryTextLabel("Ingrese N° Operación:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                //this.datos.setValue("NumeroUnico", vista.getEntryText() );
                this.datos.setValue("Emisor", "1000" );
                
                vista.hideAllEntries();
                vista.setEntryTitle( "TBK CARGA MANUAL", true );
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
 
                try {
                    vista.getOperTRV().addMedioPago(this);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                }
                
                return 13;
            case 8:
            	estado = 9;
            	montoP = Long.parseLong(vista.getEntryText());
                ParamSet pSet = Base.getParamSet( "posCfg" );
            	if ((montoP)  < Long.parseLong(pSet.getStringValue("montoTransbank"))){
            		estado = 8;
            		JOptionPane.showMessageDialog(null, "El monto minimo a cancelar con Tarjeta debe ser de $"+pSet.getStringValue("montoTransbank"), "Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13; 
            	}
                this.datos.setValue("Monto", montoP);
                return ICajaView._NOWAITFORACTION;

            case 9:
            	estado=1;
            	 vista.hideAllEntries();
                 vista.setEntryTitle( "TBK CARGA MANUAL", true );
                 vista.setEntryTextLabel("Tipo Tarjeta: ", true);
                 opt = new String[2];
                 opt[0] = "Tarjeta Crédito";
                 opt[1] = "Tarjeta Débito";
                 vista.setEntryList(opt, true, false);
                 
                 return ICajaView._WAITFORACTION;
            
            case 11:
            	estado = 4;
            	index = vista.getEntryListIndex(); 
            	String planCuotas = Base.getParamSet("posDat").getStringValue("planCuotas");
            	cuotas = planCuotas.split(",");
            	this.datos.setValue("NumeroCuotas", cuotas[index]);
				return ICajaView._NOWAITFORACTION;
        }
        
        
        return 0;
    }
    
    public boolean isIngresable(ICajaView vista){
    	if(Base.getEdicion()){
            return false;
        }
        return true;
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
        	//mp.setTipoTotal("7");
    		
    		mp.setTipoTotal("3");
        	mp.setTipoTransaccion("mpDebito");
    	}
    	else if(this.getDatos().getStringValue("TipoTotal").equals("mpCreditoManual")){
    		// Validar cual debe ser el tipo total ??
        	// mp.setTipoTotal("1008");
        	//mp.setTipoTotal("8");
        	
//        	mp.setTipoTotal("2");
    		mp.setTipoTotal(tipoTarjeta);
        	mp.setTipoTransaccion("mpCredito");
    	}else{ 
    		
    		// Esta opcion debiese ser para Multitiendas ??
    		mp.setTipoTotal("4");
    		mp.setTipoTransaccion("mpMultitienda");
    	}

    	mp.setIdTrxTbk(this.getDatos().getStringValue("idTrxTbk"));
    	mp.setFechaContable(this.getDatos().getStringValue("fechaContable"));
    	mp.setProductoTarjeta(this.getDatos().getStringValue("productoTarjeta"));
    	mp.setTbkContingencia(this.getDatos().getStringValue("tbkContingencia"));
    }
}
