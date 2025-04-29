package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import ws.claro.cl.MedioPagoDTO;
import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;

/**
 * Medio de pago: Efecivo
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Efectivo extends MedioPago implements ITrxBase {
    int estado = 0;
    long montoP= 0;
    long montoTotal= 0;
    
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("Efectivo");
        this.setNombre( "Efectivo" );
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
            return this.setMontoMPago(vista);  
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
//            	[REspinoza] Ya no se usa. N docs a Efectivo/Debito Efectivo/Credito
            	//TODO validar que si existen varios docs en el carro..
                // Se debe cancelar el total de carro con solo este medio de pago  !!
//                if(vista.getOperTRV().getCarroCompras().getDocumentos().size()>1){
//                	// Si el monto ingresado no coincide
//                	if(vista.getOperTRV().getCarroCompras().getMontoTotal() > Long.valueOf(vista.getEntryText())){
//                		estado = 0;
//                		Base.logger.error("El monto no coincide con el total del carro para N documentos...");
//                		JOptionPane.showMessageDialog(null, "Debe cancelar el Total del Carro","Advertencia", JOptionPane.INFORMATION_MESSAGE);
//                		 return this.setMontoMPago(vista);  
//                	}
//                
//                }
                montoP = Long.parseLong(vista.getEntryText());
                montoTotal =  AjusteSencillo.ajustarMontoSencillo(vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal());
            	if((montoP)  == 0){
                    estado = 0;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
            	// MSPULVEDA: valido que el monto ingresado sea modulo de 10 
                // Proyecto: Ajuste sencillo
                if(montoP % AjusteSencillo.DIEZ != 0){
                	estado = 0;
                    vista.setEntryMessage( "Monto incorrecto, ingrese valor redondeado:" + Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no valido");
                    return ICajaView._WAITFORACTION;
                }
                
            DefMedioPago def = Base.getDefMedioPago("Efectivo");
            if(def == null){
                Base.logger.error("Medio de pago no encontrado");
            }
            this.datos = new Datos(def.getRecordDef());
            this.datos.setValue("Monto", Long.parseLong(vista.getEntryText()));
            
            this.datos.show("Efectivo");
            this.setNombre("Efectivo");
            
            //Metemos al carro de medios de pago el servicio
            
            try {
                vista.getOperTRV().addMedioPago(this);
            } catch (BaseException e) {
                Tools.logStackTrace(Base.logger, e);
                Base.logger.error("Error en agregar medio de pago al carro");
            }
            
            
            return 13;
        }
        
        return 0;
    }
    
    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nMonto: " + Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean isIngresable(ICajaView vista){
        if(Base.getEdicion()){
            return false;
        }
        return true;
    }
    
    public void llenarMP(MedioPagoCaja mp){
    	if(this.getDatos().getLongValue("Monto") < 0){
    		mp.setMonto(-1*this.getDatos().getLongValue("Monto"));
    	}
    	else{
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    	}
    	mp.setTipoTransaccion("mpEfectivo");
    	mp.setTipoTotal("1001");
    }
    
    // TODO validar valores de seteo en el DTO ???
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	if(this.getDatos().getLongValue("Monto") < 0){
    		mp.setMonto(-1*this.getDatos().getLongValue("Monto"));
    	}
    	else{
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    	}
    	mp.setTipoTransaccion("mpEfectivo");
    	// Validar cual debe ser el tipo total ??
    	// mp.setTipoTotal("1001");
    	mp.setTipoTotal("1");
    }
    
    public void llenarMPDevolucion(MedioPagoCaja mp){
    	if(this.getDatos().getLongValue("Monto") < 0){
    		mp.setMonto(-1*this.getDatos().getLongValue("Monto"));
    	}
    	else{
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    	}
    	mp.setTipoTransaccion("mpDevolucion");
    	mp.setTipoTotal("1001");
    }
    
    public void llenarMPDevolucionClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	/**
    	if(this.getDatos().getLongValue("Monto") < 0){
    		mp.setMonto(-1*this.getDatos().getLongValue("Monto"));
    	}
    	else{
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    	}
    	*/
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion("mpDevolucion");
    	// Validar cual debe ser el tipo total ??
    	// mp.setTipoTotal("1001");
    	mp.setTipoTotal("1");
    }
    
    public void vaciarMP(MedioPagoCaja mp){
    	this.getDatos().setValue("Monto",mp.getMonto());
    	this.getDatos().setValue("TipoTotal","1001");
    }
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	//this.getDatos().setValue("TipoTotal","1001");
    	this.getDatos().setValue("TipoTotal","1");
    }
    
    
    public int setMontoMPago(ICajaView vista){
        vista.hideAllEntries();
        vista.setEntryTitle( "Ingrese Monto", true );                
        vista.setEntryMessage( "Ingrese monto", true );
        vista.setEntryTextLabel("Ingrese monto:", true);
        long monto =  AjusteSencillo.ajustarMontoSencillo(vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal());
        vista.setEntryText(Long.toString(monto), true, false, false, "[0-9]{1,12}", "Monto no v\u00E1lido");
        return ICajaView._WAITFORACTION;
    }
}
