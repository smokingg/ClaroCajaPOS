package cl.hyh.redpagos.caja.base;

/**
 * Clase que define un tipo de variables monto
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Amount {
    private long valor;
    
    /**
     * Constructor del monto
     * @param valor
     */
    public Amount( long valor ) {
        this.valor = valor * 10000;
    }
    
    public long getValue() {
        return valor / 1000;
    }
    
    public void setValue( long v ) {
        valor = v * 10000;
    }
    public String toString() {
        return "" + valor / 10000;
    }

}
