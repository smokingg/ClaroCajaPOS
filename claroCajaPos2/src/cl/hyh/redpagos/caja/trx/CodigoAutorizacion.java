package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.security.InvalidKeyException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DesEncrypter;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;

public class CodigoAutorizacion implements ITrxBase{
    private int estado = 0;
    String tarjeta = "";
    public void init( Datos htParm ) {}
    
    public int execute( ICajaView vista, int key, Datos htParam ) {
    	String msg = "";

    	if( key == 0 ) {
            estado = 0;
        } else if( key == 1 ) {
            // TIMEOUT
            return 11;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // OTRA TECLA. PROCESE CON LO QUE ESTE EN EL XML...
            return ICajaView._PASSTHROUGH;
        }
        
        while( true ) {
            if( estado == 0 ) {
                vista.setEntryMessage("", false);
                vista.setEntryTextLabel("Ingrese codigo autorización:", true);
                vista.setEntryText("", true, false, false, null, null);
                //vista.setEntryText("", true, false, true, null, null);
                estado = 1;
                return ICajaView._WAITFORACTION;
            } else if( estado == 1 ) {
                estado = 2;
                tarjeta = vista.getEntryText();
                return ICajaView._NOWAITFORACTION;
            }
            else if( estado == 2  ){                
                // MARCO COMO VALIDO=1 (O NO VALIDO=0)
            	htParam.setValue("codigo", tarjeta);
            	// VOLVERE A QUIEN ME LLAMO... (POR PARTITURA.XML)
            	return 11;
            }
        }
    }
}
