package cl.hyh.redpagos.caja.base;

/**
 * Clase de excepcion para la base de la aplicacion
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class BaseException extends Exception {
    
    private String msg = "";
    private Exception e;
    
    /**
     * Constructor que recibe una excepcion y un mensaje
     * @param e Excepcion 
     * @param msg Mensaje que define la excepcion
     */
    public BaseException( Exception e, String msg ) {
        this.msg = msg;
        this.e = e;
    }
    /**
     * Constructor que recibe solamente un mensaje
     * @param msg Mensaje que define la excepcion
     */
    public BaseException(String msg){
        this.msg = msg;
    }
    
    public String toString() {
        if( e != null )
            return e.toString()  + msg;
        else
            return msg;
    }

    /**
     * Retorna la variable msg
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setea la variable msg
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Retorna la variable de excepcion e
     * @return
     */
    public Exception getException() {
        return e;
    }

    /**
     * Setea la variable de excepcion e
     * @param e
     */
    public void setE(Exception e) {
        this.e = e;
    }

}
