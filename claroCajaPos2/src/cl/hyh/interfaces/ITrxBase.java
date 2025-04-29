package cl.hyh.interfaces;

import cl.hyh.redpagos.caja.base.Datos;


/**
 * @author abertens
 *
 */
public interface ITrxBase {

    /*
     * key=0 PRIMERA LLAMADA
     * key=1 TIMEOUT
     * key=2 VOLVIENDO DE TRX ANIDADA
     * key=KeyEvent.VK_*
     * 
     * init() SE LLAMA AL INMEDIATAMENTE DESPUES DE INSTANCIAR LA CLASE
     * 
     */
    public int execute(ICajaView vista, int key, Datos htParam );
    public void init( Datos htParam );
}
