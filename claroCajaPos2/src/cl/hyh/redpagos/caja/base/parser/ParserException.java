package cl.hyh.redpagos.caja.base.parser;

/**
 * Clase que define las excepciones para los distintos parser de archivos xml implementados.
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserException extends Exception{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String msg;
    /**
     * Constructor de la excepcion que define el mensaje que la caracteriza
     * @param emsg Mensaje a ser incluido en la excepcion
     */
    public ParserException(String emsg){
        this.msg = emsg;
    }
    public String toString(){
        return msg;
    }
}
