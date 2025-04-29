package cl.hyh.trx;

import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cl.hyh.base.Motor;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;

public class CambiaFechaPago implements ITrxBase {

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
        
        vista.setEntryMessage("Fecha en formato: AAAAMMDD", true);
        while( true ) {
            if( estado == 0 ) {
                vista.setEntryTextLabel("Ingrese Fecha de Pago:", true);
                vista.setEntryText("", true, false, false, "[0-9]{8}", "Fecha no válida (AAAAMMDD)");
                estado = 1;
                return ICajaView._WAITFORACTION;
            } else if( estado == 1 ) {
            	SimpleDateFormat df = new SimpleDateFormat();
            	String pattern = "yyyyMMdd";
        		df.applyPattern(pattern);
        		df.setLenient(false);
            	try {
            		@SuppressWarnings("unused")
					java.util.Date aDate = df.parse(vista.getEntryText());
            		if(aDate.after( new Date() )){
            		    vista.setEntryMessage("Fecha no puede ser posterior", true);
                        estado = 0;
            		}
            		else{
        		        ParamSet pList = Base.getParamSet( "posDat" );
        		        pList.setValue( "FechaPago", vista.getEntryText() );
        	            Motor.logger.info( "Fecha de pago: " + pList.getStringValue("FechaPago") );
                    	return 13;
            		}
				} catch (ParseException e) {
                    vista.setEntryMessage("Fecha no válida (formato: AAAAMMDD)", true);
                    estado = 0;
				}
            }
        }
    }
}
