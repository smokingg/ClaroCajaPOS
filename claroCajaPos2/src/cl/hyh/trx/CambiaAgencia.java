package cl.hyh.trx;

import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import cl.hyh.base.Motor;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;

public class CambiaAgencia implements ITrxBase {

    private int estado = 0;

    public void init( Datos htParm ) {}
    
    public int execute( ICajaView vista, int key, Datos htParam ) {
        if( key == 0 ) {
            vista.setInputTimeout(60);
        } else if( key == 1 ) {
            // TIMEOUT
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // OTRA TECLA. PROCESE CON LO QUE ESTE EN EL XML...
            return ICajaView._PASSTHROUGH;
        }
        
        vista.setEntryMessage("En formato: #", true);
        while( true ) {
            if( estado == 0 ) {
                vista.setEntryTextLabel("Ingrese Entidad:", true);
                vista.setEntryText("", true, false, false, "[0-9]+", "Entidad no válida");
                estado = 1;
                return ICajaView._WAITFORACTION;
            } else if( estado == 1 ) {
		        ParamSet pList = Base.getParamSet( "posDat" );
		        pList.setValue( "Entidad", vista.getEntryText() );
	            Motor.logger.info( "Entidad: " + pList.getStringValue("Entidad") );
	            estado = 2;
	            vista.setEntryTextLabel("Ingrese Agencia:", true);
                vista.setEntryText("", true, false, false, "[0-9]+", "Agencia no válida");
            	return ICajaView._WAITFORACTION;
            }
            else if( estado == 2 ) {
                ParamSet pList = Base.getParamSet( "posDat" );
                pList.setValue( "Agencia", vista.getEntryText() );
                Motor.logger.info( "Agencia: " + pList.getStringValue("Agencia") );
                return 13;
            }
        }
    }
}
