package cl.hyh.redpagos.caja.pos;

/**
 * Clase de excepcion para los dispositivos POS.
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class PosDeviceException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String msg;
    
    /**
     * Instancia una excepcion definida por un mensaje
     * @param emsg aa
     */
    public PosDeviceException(String emsg){
        this.msg = emsg;
    }
    public String toString(){
        return msg;
    }
}
