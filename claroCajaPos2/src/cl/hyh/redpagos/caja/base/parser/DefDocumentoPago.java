package cl.hyh.redpagos.caja.base.parser;

/**
 * Clase que define la estructura de los Documentos de Pago
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefDocumentoPago {

    private DefRecord recordDef;
    private String name;
    private String customerVoucher;
    private String comerceVoucher;
    private String className;
    private String mediosPago;
    private String empresa;
    private boolean reversable = true;;

    /**
     * Constructor de Documentos de Pago, definen los valores default para sus variables y se instancia
     * la definicion de record que posee dentro
     * 
     */
    public DefDocumentoPago(){
        recordDef = new DefRecord();
        name = "";
        customerVoucher = "";
        className = "";
        mediosPago = "";
    }
    /**
     * Retorna la definicion de Record
     * @return
     */
    public DefRecord getRecordDef() {
        return recordDef;
    }

    /**
     * Setea la definicion de Record
     * @param erecordDef
     */
    public void setRecordDef(DefRecord erecordDef) {
        this.recordDef = erecordDef;
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
     * Retorna la variable customerVoucher
     * @return
     */
    public String getCustomerVoucher() {
        return customerVoucher;
    }
    /**
     * Setea la variable customerVoucher
     * @param ecustomerVoucher
     */
    public void setCustomerVoucher(String ecustomerVoucher) {
        this.customerVoucher = ecustomerVoucher;
    }
    /**
     * Retorna la variable className
     * @return
     */
    public String getClassName() {
        return className;
    }
    /**
     * Setea la variable className
     * @param eclassName
     */
    public void setClassName(String eclassName) {
        this.className = eclassName;
    }
    /**
     * Retorna la variable mediosPago
     * @return
     */
    public String getMediosPago() {
        return mediosPago;
    }
    /**
     * Setea la variable mediosPago
     * @param emediosPago
     */
    public void setMediosPago(String emediosPago) {
        this.mediosPago = emediosPago;
    }
    public String getComerceVoucher() {
        return comerceVoucher;
    }
    public void setComerceVoucher(String comerceVoucher) {
        this.comerceVoucher = comerceVoucher;
    }
    public String getEmpresa() {
        return empresa;
    }
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    public boolean isReversable() {
        return reversable;
    }
    public void setReversable(boolean reversable) {
        this.reversable = reversable;
    }
    
    
    
}
