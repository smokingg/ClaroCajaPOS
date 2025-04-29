package cl.hyh.redpagos.caja.base;

public class JournalEntryView {
    private String caja;
    private String fecha;
    private String hora;
    private long numeroOperacion;
    private String operacion;
    private String estado;
    private String item;
    private long numeroItem;
    private long monto;
    
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public long getNumeroOperacion() {
        return numeroOperacion;
    }
    public void setNumeroOperacion(long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }
    public String getOperacion() {
        return operacion;
    }
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public long getNumeroItem() {
        return numeroItem;
    }
    public void setNumeroItem(long numeroItem) {
        this.numeroItem = numeroItem;
    }
    public long getMonto() {
        return monto;
    }
    public void setMonto(long monto) {
        this.monto = monto;
    }
    public String getCaja() {
        return caja;
    }
    public void setCaja(String caja) {
        this.caja = caja;
    }
    
    
}
