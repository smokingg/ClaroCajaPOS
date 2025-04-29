package cl.hyh.redpagos.caja.base.parser;

import java.util.ArrayList;

/**
 * Clase que define la estructura de las lineas de los  Vouchers
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefVoucherLine {

    private boolean center = false;
    private boolean bold = false;
    private boolean underline = false;
    private boolean caps = false;
    private boolean right = false;
    private boolean small = false;
    private String condField = "";
    private String value = "";
    private boolean cond = true;
    private String type = "char";
    private String include = "";
    private ArrayList<DefVoucherPrint> defPrints = new ArrayList<DefVoucherPrint>();
   
    /**
     * Retorna la variable center
     * @return
     */
    public boolean isCenter() {
        return center;
    }
    /**
     * Setea la variable center
     * @param ecenter
     */
    public void setCenter(boolean ecenter) {
        this.center = ecenter;
    }
    /**
     * Retorna la variable bold
     * @return
     */
    public boolean isBold() {
        return bold;
    }
    /**
     * Setea la variable bold
     * @param ebold
     */
    public void setBold(boolean ebold) {
        this.bold = ebold;
    }
    /**
     * Retorna la variable condField
     * @return
     */
    public String getCondField() {
        return condField;
    }
    /**
     * Setea la variable condField
     * @param econdField
     */
    public void setCondField(String econdField) {
        this.condField = econdField;
    }
    /**
     * Retorna la variable value
     * @return
     */
    public String getValue() {
        return value;
    }
    /**
     * Setea la variable value
     * @param evalue
     */
    public void setValue(String evalue) {
        this.value = evalue;
    }
    /**
     * Retorna la variable cond
     * @return
     */
    public boolean isCond() {
        return cond;
    }
    /**
     * Setea la variable cond
     * @param econd
     */
    public void setCond(boolean econd) {
        this.cond = econd;
    }
    /**
     * Retorna el arreglo de definiciones de impresion
     * @return
     */
    public ArrayList<DefVoucherPrint> getDefPrints() {
        return defPrints;
    }
    /**
     * Setea el arreglo de definiciones de impresion
     * @param edefPrints
     */
    public void setDefPrints(ArrayList<DefVoucherPrint> edefPrints) {
        this.defPrints = edefPrints;
    }
    /**
     * Retorna la variable include
     * @return
     */
    public String getInclude() {
        return include;
    }
    /**
     * Setea la variable include
     * @param einclude
     */
    public void setInclude(String einclude) {
        this.include = einclude;
    }
    public boolean isUnderline() {
        return underline;
    }
    public void setUnderline(boolean underline) {
        this.underline = underline;
    }
    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public boolean isCaps() {
        return caps;
    }
    public void setCaps(boolean caps) {
        this.caps = caps;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public boolean isSmall() {
        return small;
    }
    public void setSmall(boolean small) {
        this.small = small;
    }
    
}
