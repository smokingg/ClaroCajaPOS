package cl.hyh.redpagos.caja.base.parser;

/**
 * Clase que define la estructura de lo que se debe imprimir de los Vouchers
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefVoucherPrint {

    private String value = "";
    private String field = "";
    private String format = "";
    private String rFill = "";
    private String lFill = "";
    private int length = 0;
    
    /**
     * Retorna la variable value de tipo String
     * @return
     */
    public String getValue() {
        return value;
    }
    /**
     * Setea la variable value 
     * @param evalue Valor a ser asignado a la variable value
     */
    public void setValue(String evalue) {
        this.value = evalue;
    }
    /**
     * Retorna la variable field de tipo String
     * @return
     */
    public String getField() {
        return field;
    }
    /**
     * Setea la variable field 
     * @param efield Valor a ser asignado a la variable field
     */
    public void setField(String efield) {
        this.field = efield;
    }
    /**
     * Retorna la variable format de tipo String
     * @return
     */
    public String getFormat() {
        return format;
    }
    /**
     * Setea la variable format 
     * @param eformat Valor a ser asignado a la variable format
     */
    public void setFormat(String eformat) {
        this.format = eformat;
    }
    /**
     * Retorna la variable rFill de tipo String
     * @return
     */
    public String getRFill() {
        return rFill;
    }
    /**
     * Setea la variable rFill 
     * @param fill Valor a ser asignado a la variable rFill
     */
    public void setRFill(String fill) {
        rFill = fill;
    }
    /**
     * Retorna la variable lFill de tipo String
     * @return
     */
    public String getLFill() {
        return lFill;
    }
    /**
     * Setea la variable lFill 
     * @param fill Valor a ser asignado a la variable lFill
     */
    public void setLFill(String fill) {
        lFill = fill;
    }
    /**
     * Retorna el valor de la variable 'lenght'
     * @return
     */
    public int getLength() {
        return length;
    }
    /**
     * Setea la variable length 
     * @param elength Valor a ser asignado a la variable length
     */
    public void setLength(int elength) {
        this.length = elength;
    }
}
