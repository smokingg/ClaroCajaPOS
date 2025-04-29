package cl.hyh.redpagos.caja.base.parser;

import java.util.ArrayList;

/**
 * Clase que define la estructura de los Vouchers
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefVoucher {

    private String name;
    private ArrayList<DefVoucherLine> defLines = new ArrayList<DefVoucherLine>();
    
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
     * Retorna el arreglo de las definiciones de lineas
     * @return
     */
    public ArrayList<DefVoucherLine> getDefLines() {
        return defLines;
    }
    /**
     * Setea el arreglo de las definiciones de lineas
     * @param edefLines
     */
    public void setDefLines(ArrayList<DefVoucherLine> edefLines) {
        this.defLines = edefLines;
    }
    
    
}
