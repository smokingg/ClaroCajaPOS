package cl.hyh.redpagos.caja.base;

/**
 * Representacion interna de los distintos planes de cuotas existenes, poseen un nombre, montos minimos y maximos
 * un arreglo con las cuotas existentes y las marcas a las cuales son aplicables las distintas cuotas
 * @author Rafael Hernandez
 *
 */
public class PlanCuotas {

    private String name;
    private boolean tieneCuotas = false;
    private long montoMinimo = 0;
    private long montoMaximo = 10000000;
    private int[] cuotas = new int[0];
    private String[] marcas;
    private int tipoCredito = 0;
    
    /**
     * Retorna la variable name
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * Setea el nombre del plan de cuotas
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Retorna la variable tieneCuotas
     * @return
     */
    public boolean isTieneCuotas() {
        return tieneCuotas;
    }
    /**
     * Setea si el plan de cuotas tiene cuotas
     * @param tieneCuotas
     */
    public void setTieneCuotas(boolean tieneCuotas) {
        this.tieneCuotas = tieneCuotas;
    }
    /**
     * Retorna el montoMinimo
     * @return
     */
    public long getMontoMinimo() {
        return montoMinimo;
    }
    /**
     * Setea el monto minimo para el plan
     * @param montoMinimo
     */
    public void setMontoMinimo(long montoMinimo) {
        this.montoMinimo = montoMinimo;
    }
    /**
     * Retorna el montoMaximo
     * @return
     */
    public long getMontoMaximo() {
        return montoMaximo;
    }
    /**
     * Setea el monto maximo para el plan
     * @param montoMaximo
     */
    public void setMontoMaximo(long montoMaximo) {
        this.montoMaximo = montoMaximo;
    }
    /**
     * Retorna el arreglo de cuotas
     * @return
     */
    public int[] getCuotas() {
        return cuotas;
    }
    /**
     * Setea el arreglo de cuotas posibles
     * @param cuotas
     */
    public void setCuotas(int[] cuotas) {
        this.cuotas = cuotas;
    }
    /**
     * Retorna el arreglo de marcas
     * @return
     */
    public String[] getMarcas() {
        return marcas;
    }
    /**
     * Setea el arreglo de marcas
     * @param marcas
     */
    public void setMarcas(String[] marcas) {
        this.marcas = marcas;
    }
    public int getTipoCredito() {
        return tipoCredito;
    }
    public void setTipoCredito(int tipoCredito) {
        this.tipoCredito = tipoCredito;
    }
}
