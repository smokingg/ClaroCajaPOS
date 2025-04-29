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
 * Medio de pago: Efectivo
 * TODO: cbriones: NO esta activo este medio de Pago ...
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DevolucionEfectivo extends MedioPago implements ITrxBase {
    int estado = 0;
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("Efectivo");
        this.setNombre( "Efectivo" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
            if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
                JOptionPane.showMessageDialog(null, "Medio Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            estado = 0;
            vista.hideAllEntries();
            vista.setEntryTitle( "Ingreso Monto", true );                
            vista.setEntryMessage( "Ingrese monto", true );
            vista.setEntryTextLabel("Ingrese monto:", true);
            vista.setEntryText("", true, false, false, "[0-9]+", "Monto no v\u00E1lido");
            return ICajaView._WAITFORACTION;
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
            DefMedioPago def = Base.getDefMedioPago("Efectivo");
            if(def == null){
                Base.logger.error("Medio de pago no encontrado");
            }
            this.datos = new Datos(def.getRecordDef());
            ParamSet pSet = Base.getParamSet("posCfg");
            if(Long.parseLong(vista.getEntryText()) > Long.parseLong(pSet.getStringValue("maxDevolucionEfectivo"))){
            	estado = 0;
            	vista.hideAllEntries();
                vista.setEntryTitle( "Ingreso Monto", true );                
                vista.setEntryMessage( "Monto no puede superar " + pSet.getStringValue("maxDevolucionEfectivo"), true );
                vista.setEntryTextLabel("Ingrese monto:", true);
                vista.setEntryText("", true, false, false, "[0-9]+", "Monto no v\u00E1lido");
                return ICajaView._WAITFORACTION;
            }
            this.datos.setValue("Monto", -1*Long.parseLong(vista.getEntryText()));
            
            this.datos.show("Efectivo");
            this.setNombre("DevolucionEfectivo");
            
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
}
