package cl.hyh.redpagos.caja.base.parser;

import java.util.ArrayList;

import cl.hyh.redpagos.caja.base.PlanCuotas;

/**
 * Clase que define la estructura de los MedioPago
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefMedioPago {
    private DefRecord recordDef;
    private String name;
    private String customerVoucher;
    private String comerceVoucher;
    private String className;
    
    private ArrayList<PlanCuotas> planesCuotas = new ArrayList<PlanCuotas>();
    
    /**
     * Constructor de la definicion de los medios de pago
     * 
     */
    public DefMedioPago(){
        recordDef =  new DefRecord();
        name = "";
        customerVoucher = "";
        className = "";
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
     * Retorna el arreglo de planes de cuota
     * @return
     */
    public ArrayList<PlanCuotas> getPlanesCuotas() {
        return planesCuotas;
    }
    /**
     * Setea el arreglo de planes de cuota
     * @param eplanesCuotas
     */
    public void setPlanesCuotas(ArrayList<PlanCuotas> eplanesCuotas) {
        this.planesCuotas = eplanesCuotas;
    }
    public String getComerceVoucher() {
        return comerceVoucher;
    }
    public void setComerceVoucher(String comerceVoucher) {
        this.comerceVoucher = comerceVoucher;
    }
    
    
}
