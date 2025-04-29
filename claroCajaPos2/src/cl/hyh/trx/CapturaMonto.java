package cl.hyh.trx;

import java.awt.event.KeyEvent;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Datos;

/**
 * @author abertens
 *
 */
public class CapturaMonto implements ITrxBase {
    private int estado = 0;

    public void init(Datos htParm ) {}
    
    public int execute( ICajaView vista, int key, Datos htParam ) {
        if( key == 0 ) {
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // OTRA TECLA, PROCESE CON LO QUE TENGA EL XML
            return ICajaView._PASSTHROUGH;
        }
        while( true ) {
            if( estado == 0 ) {
                vista.setEntryTextLabel("Ingrese monto:", true);
                vista.setEntryText("", true, false, false, "[0-9]+", "Valor ingresado no válido" );
                estado = 1;
                return ICajaView._WAITFORACTION;
            } else if( estado == 1 ) {
                // ESTAMOS LISTOS, VALIDAMOS Y NOS VAMOS...
                String monto = vista.getEntryText().trim();
                if( new Long(monto).longValue() > 0 ) {
                    htParam.setValue("monto", monto);
                    return ICajaView._PREPOSTRETURN;
                } else {
                    // MONTO NO VALIDO, VOLVEMOS A ESTADO 0 CON MENSAJE
                    vista.setEntryMessage("Monto no válido", true);
                    estado = 0;
                }                
            }
        }
    }
}
