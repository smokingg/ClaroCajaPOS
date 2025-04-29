package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import ws.claro.cl.ArqueoConceptoDTO;
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
 * Medio de pago: Ajuste sencillo
 * Esta clase se encarga de registrar los ajustes de sencillos realizados al operar con efectivo con el nuevo cambio a la ley que elimina las monedas 
 * de 1 y de 5 pesos
 * 
 * @author Miguel sepúlveda - Aligare
 *
 */
public class AjusteSencillo extends MedioPago implements ITrxBase {
    int estado = 0;
    long montoP= 0;
    long montoTotal= 0;
    private static final int MONTO_AJUSTAR_SENCILLO = 5;
    public static final int DIEZ = 10;
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("AjusteSencillo");
        this.setNombre( "AjusteSencillo" );
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
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
            	if((montoP)  == 0){
                    estado = 0;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
                
            DefMedioPago def = Base.getDefMedioPago("AjusteSencillo");
            if(def == null){
                Base.logger.error("Medio de pago no encontrado");
            }
            this.datos = new Datos(def.getRecordDef());
            this.datos.setValue("Monto", Long.parseLong(vista.getEntryText()));
            
            this.datos.show("AjusteSencillo");
            this.setNombre("AjusteSencillo");
            
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
    	mp.setTipoTransaccion("AjusteSencillo");
    	mp.setTipoTotal("1001");
    }
    
    // TODO validar valores de seteo en el DTO ???
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	if(this.getDatos().getLongValue("Monto") < 0){
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    	}
    	else{
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    	}
    	mp.setTipoTransaccion("mpAjusteSencillo");
    	mp.setTipoTotal("1");
    }
    
    public void vaciarMP(MedioPagoCaja mp){
    	this.getDatos().setValue("Monto",mp.getMonto());
    	this.getDatos().setValue("TipoTotal","1001");
    }
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("MontoAjustado",mp.getMontoPagado());
    	//this.getDatos().setValue("TipoTotal","1001");
    	this.getDatos().setValue("TipoTotal","1");
    }
    
    /**
     * Metodo que se encarga de obtener el monto ajustado para cobrar al cliente
     * con el medio de pago efectivo
     * 
     * @param monto monto real del documento
     * @return monto ajustado aplicando formula
     */
    public static long ajustarMontoSencillo(long monto){
    	long mod = monto % DIEZ;
    	if(mod <= MONTO_AJUSTAR_SENCILLO){
    		return monto - mod;
    	}else{
    		return monto + (DIEZ-mod);
    	}
    }
    
    /**
     * Metodo para mostrar el monto que fue ajustado 
     * 
     * @param monto monto total a pagar en efectivo
     * @return retorna el monto ajustado aplicando formula
     */
    public static long montoAjustado(long monto){
    	long mod = monto % DIEZ;
    	if(mod <= MONTO_AJUSTAR_SENCILLO){
    		return mod * -1;
    	}else{
    		return (DIEZ-mod);
    	}
    }
    
    /**
     * metodo que obtiene el total de los ajustes de sencillo realizado
     * @param conceptos conceptos de arqueo, contiene el item de ajuste
     * @return  suma de los ajustes realizados.
     */
    public static long getTotalAjuste(ArqueoConceptoDTO[] conceptos){
    	long suma = 0;
    	for (ArqueoConceptoDTO arqueoConceptoDTO : conceptos) {
			if("mpAjusteSencillo".equals(arqueoConceptoDTO.getConcepto())){
				suma += arqueoConceptoDTO.getTotalSistema();
			}
		}
    	return suma;
    	
    }

}
