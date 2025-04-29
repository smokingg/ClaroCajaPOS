package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Pagare extends MedioPago implements ITrxBase {
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
                DefMedioPago def = Base.getDefMedioPago("Pagare");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", Long.parseLong(vista.getEntryText()));
                estado = 1;
                vista.setEntryMessage( "Ingrese N° Pagaré", true );
                vista.setEntryTextLabel("Ingrese N° Pagaré:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,30}","N° Pagaré no válido");
                return ICajaView._WAITFORACTION;
            case 1:
                estado = 2;
                this.datos.setValue("NumeroPagare", vista.getEntryText());
                
                vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                vista.setEntryTextLabel("Ingrese Fecha Pagaré:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
            case 2:
                estado = 3;
                if(vista.getEntryText().equals("")){
                    this.datos.setValue("FechaPagare",Tools.getFecha());
                }
                else{
                    String fecha = vista.getEntryText();
                    Pattern p = Pattern.compile("[0-9]{8}$");
                    Matcher mat = p.matcher(fecha);
                    if ( !mat.find() ){
                        Base.logger.info("Fecha con formato invalido");
                        estado = 2;
                        vista.hideAllEntries();
                        vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                        vista.setEntryTextLabel("Ingrese Fecha Pagaré:", true);
                        vista.setEntryText("", true, false, false,null,null);
                        return ICajaView._WAITFORACTION;
                    }
                    else{
                        this.datos.setValue("FechaPagare",fecha);
                    }
                }
                this.datos.show("Pagare");
                this.setNombre("Pagare");
            
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
