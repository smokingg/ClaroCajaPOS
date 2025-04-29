package cl.hyh.redpagos.caja.base.parser;
/**
 * Clase que define la estructura de los Fields, extiende a la clase DefElement
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefField extends DefElement {

    private String type;
    private boolean transmit;
    private String defaultValue;
    private int len = -1;
    
    /**
     * Constructor de las definiciones fields, se definen valores por default para el type(char), transmit(true)
     * defaultValue().
     * 
     */
    public DefField(){
        type = "char";
        transmit = true;
        defaultValue = "";
    }
    /**
     * Retorna la variable type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Setea la variable type, si es de tipo num, se considera como tipo int
     * @param etype
     */
    public void setType(String etype) {
        if(etype.equals("num")){
            this.type = "int";
        }
        else if(etype.equals("monto")){
            this.type = "long";
        }
        else{
            this.type = etype;
        }
    }

    /**
     * Retorna la variable transmit
     * @return
     */
    public boolean getTransmit() {
        return transmit;
    }

    /**
     * Setea la variable transmit
     * @param etransmit
     */
    public void setTransmit(boolean etransmit) {
        this.transmit = etransmit;
    }
    /**
     * Retorna la variable defaultValue
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }
    /**
     * Setea la variable defaultValue
     * @param edefaultValue
     */
    public void setDefaultValue(String edefaultValue) {
        this.defaultValue = edefaultValue;
    }
    public int getLen() {
        return len;
    }
    public void setLen(int len) {
        this.len = len;
    }
    
    
}
