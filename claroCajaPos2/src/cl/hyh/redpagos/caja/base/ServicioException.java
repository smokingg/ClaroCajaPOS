package cl.hyh.redpagos.caja.base;

/**
 * Clase de excepcion para los servicios de la aplicacion
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ServicioException extends Exception {
    
    private String msg;
    
    /**
     * Instancia una excepcion que esta representada por el mensaje msg
     * @param msg
     */
    public ServicioException( String msg ) {
        this.msg = msg;
    }
    
    public String toString() {
        return msg;
    }

}
