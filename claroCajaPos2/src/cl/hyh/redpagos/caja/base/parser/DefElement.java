package cl.hyh.redpagos.caja.base.parser;

/**
 * Clase que define la estructura de los elements
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefElement {
    String name;
    
    /**
     * Retorna la variable name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setea la variable name
     * @param ename
     */
    public void setName(String ename) {
        this.name = ename;
    }
}
