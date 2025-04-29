package cl.hyh.trx;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Datos;

/**
 * @author abertens
 *
 */
public class SampleTrx implements ITrxBase {

	public void init(Datos htParm ) {}

    public int execute( ICajaView vista, int key, Datos htParam ) {
        if( key == 0 ) {
            vista.setEntryTitle("Título de la canción", true );
            vista.setEntryMessage("Mensaje para SampleTrx", true );
            String[] l = { "item1", "item2", "item5", "item3" };
            vista.setEntryList(l, true, false);
            return ICajaView._WAITFORACTION;
        } else {
            return 10;
        }
    }
}
