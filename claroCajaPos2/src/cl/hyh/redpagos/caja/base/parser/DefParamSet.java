package cl.hyh.redpagos.caja.base.parser;

/**
 * Clase que define la estructura de los ParamSet
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefParamSet {
   
    private String name;
    private DefRecord defRecord;
    private String type; /* "resource" o "file" */
    private String fName;
    
    /**
     * Constructor de la definicion de los parametros
     * 
     */
    public DefParamSet(){
        defRecord =  new DefRecord();
        name = "";
        type = "";
        fName = "";
    }
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
    /**
     * Retorna la definicion de record
     * @return
     */
    public DefRecord getDefRecord() {
        return defRecord;
    }
    /**
     * Setea la definicion de record
     * @param edefRecord
     */
    public void setDefRecord(DefRecord edefRecord) {
        this.defRecord = edefRecord;
    }
    /**
     * Retorna la variable type
     * @return
     */
    public String getType() {
        return type;
    }
    /**
     * Setea la variable type
     * @param etype
     */
    public void setType(String etype) {
        this.type = etype;
    }
    /**
     * Retorna la variable fName
     * @return
     */
    public String getFName() {
        return fName;
    }
    /**
     * Setea la variable fName
     * @param ename
     */
    public void setFName(String ename) {
        fName = ename;
    }
    
}
