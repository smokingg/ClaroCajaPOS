package cl.hyh.redpagos.caja.base;

/**
 * Clase de excepcion para los medios de pago
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class MedioPagoException extends Exception {
    
    private String msg;
    
    /**
     * Constructor que asigna un mensaje al mensaje de la excepcion
     * @param msg  Mensaje que define a la excepcion
     */
    public MedioPagoException( String msg ) {
        this.msg = msg;
    }
    
    public String toString() {
        return msg;
    }
}
