package cl.hyh.redpagos.caja.base.parser;


/**
 * Clase que define la estructura de los Servicios
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
/**
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefServicio {
    private DefRecord inputRecordDef;
    private DefRecord outputRecordDef;
    
    private String name;
    private int timeout;
    private String type;
    private String className;
    private String host;
    private String appl;
    private String saf="no";
    private String cola = "colaDefault";
    
    /**
     * Retorna la definicion del record input
     * @return
     */
    public DefRecord getInputRecordDef() {
        return inputRecordDef;
    }
    /**
     * Setea la variable de la definicion del record input
     * @param einputRecordDef
     */
    public void setInputRecordDef(DefRecord einputRecordDef) {
        this.inputRecordDef = einputRecordDef;
    }
    /**
     * Retorna la definicion del record output
     * @return
     */
    public DefRecord getOutputRecordDef() {
        return outputRecordDef;
    }
    /**
     * Setea la variable de la definicion del record output
     * @param eoutputRecordDef
     */
    public void setOutputRecordDef(DefRecord eoutputRecordDef) {
        this.outputRecordDef = eoutputRecordDef;
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
     * Retorna la variable timeout
     * @return
     */
    public int getTimeout() {
        return timeout;
    }
    /**
     * Setea la variable timeout
     * @param etimeout
     */
    public void setTimeout(int etimeout) {
        this.timeout = etimeout;
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
     * Retorna la variable className
     * @return
     */
    public String getClassName() {
        return className;
    }
    /**
     * Setea la variable clasName
     * @param eclassName
     */
    public void setClassName(String eclassName) {
        this.className = eclassName;
    }
    /**
     * @return
     */
    public String getHost() {
        return host;
    }
    /**
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }
    /**
     * @return
     */
    public String getAppl() {
        return appl;
    }
    public void setAppl(String appl) {
        this.appl = appl;
    }
    public String getSaf() {
        return saf;
    }
    public void setSaf(String saf) {
        this.saf = saf;
    }
    public String getCola() {
        return cola;
    }
    public void setCola(String cola) {
        this.cola = cola;
    }
    
}
