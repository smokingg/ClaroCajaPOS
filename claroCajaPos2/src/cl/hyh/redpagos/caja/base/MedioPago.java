package cl.hyh.redpagos.caja.base;

import java.io.Serializable;
import java.util.ArrayList;

import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefVoucher;

/**
 * Representacion interna de los medios de pago existentes en la aplicacion, consiste en una estructura Datos
 * que posee las variables que lo caracterizan y un monto que lo define
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class MedioPago implements Serializable {

    protected String nombre;
    protected Datos datos;
    String[] opt;
    
    /**
     * inicializacion unica de todo lo concerniente a los medios de pago
     * 
     * @param paymentMethodName
     */
    public static void init(String paymentMethodName) {
    }

    /**
     * obtencion de lineas de voucher de cliente
     * 
     * @return arreglo de lineas LineaVoucher de voucher del cliente
     */
    public LineaVoucher[] getVoucherCliente() {
    	Base.logger.info("Nombre del Medio de Pago getVoucherCliente : "+this.nombre);
        DefMedioPago def = Base.getDefMedioPago(this.nombre);
        DefVoucher defV = Base.getDefVoucher(def.getCustomerVoucher());
        ArrayList<LineaVoucher> lineas =  Voucher.armarVoucher(this.datos,defV);
        LineaVoucher []voucher = new LineaVoucher[lineas.size()];
        
        for(int i = 0; i < lineas.size(); i++){
            voucher[i] = lineas.get(i);
        }
        return voucher;
    }
    
    public LineaVoucher[] getVoucherClienteDuplicado() {
        DefMedioPago def = Base.getDefMedioPago(this.nombre);
        DefVoucher defV = Base.getDefVoucher(def.getCustomerVoucher());
        ArrayList<LineaVoucher> lineas =  Voucher.armarVoucher(this.datos,defV);
        LineaVoucher []voucher = new LineaVoucher[lineas.size()];
        
        for(int i = 0; i < lineas.size(); i++){
            voucher[i] = lineas.get(i);
        }
        return voucher;
    }
    
    public LineaVoucher[] getPremioCliente(){
        return null;
    }
    public LineaVoucher[] getPremioLocal(){
        return null;
    }
    public LineaVoucher[] getCommerceVoucherDuplicado() {
        return null;
    }
    
    // TODO se agrega metodo para obtener Voucher de reimpresion TBK !!
    public LineaVoucher[] getCommerceVoucherReimpresion() {
        return null;
    }
    
    /**
     * obtencion de lineas de voucher de comercio (para el cajero)
     * 
     * @return arreglo de lineas LineaVoucher de voucher del comercio
     */
    public LineaVoucher[] getCommerceVoucher() {
        try{
            DefMedioPago def = Base.getDefMedioPago(this.nombre);
            DefVoucher defV = Base.getDefVoucher(def.getComerceVoucher());
            if(defV == null){
                Base.logger.info("Definicion no encontrada");
                return null;
            }
            ArrayList<LineaVoucher> lineas =  Voucher.armarVoucher(this.datos,defV);
            LineaVoucher []voucher = new LineaVoucher[lineas.size()];
            
            for(int i = 0; i < lineas.size(); i++){
                voucher[i] = lineas.get(i);
            }
            return voucher;
        }
        catch(Exception e){
            Base.logger.info("Definicion no encontrada");
            return null;
        }
    }
    
    public int setMontoMPago(ICajaView vista){
        vista.hideAllEntries();
        vista.setEntryTitle( "Ingrese Monto", true );                
        vista.setEntryMessage( "Ingrese monto", true );
        vista.setEntryTextLabel("Ingrese monto:", true);
        long monto = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
        vista.setEntryText(Long.toString(monto), true, false, false, "[0-9]{1,12}", "Monto no v\u00E1lido");
        return ICajaView._WAITFORACTION;
    }

   
    /**
     * reversar un medio de pago ya autorizado
     * 
     */
    public void reversar(boolean llamadaExt) throws MedioPagoException {
    }

    /**
     * anular un medio de pago ya finiquitado
     * 
     */
    public void anular() throws MedioPagoException {
    }
    public int anular(ICajaView vista, int key, Datos data) throws MedioPagoException {
        return 0;
    }
    /**
     * obtencion de planes de cuotas para el medio de pago
     * 
     * @return arreglo de planes de cuotas PlanCuota para el medio de pago
     */
    public PlanCuotas[] getPlanesCuotas() {
        return null;
    }

    /**
     * recuperar el monto asociado al medio de pago
     * 
     * @return monto del medio de pago
     */
    public long getMonto() {
        return datos.getLongValue( "Monto" );
    }
    
    /**
     * obtener Datos del medio de pago
     * 
     * @return
     */
    public Datos getDatos() {
        return datos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }
    
    public String glosaCarro() {
        return String.format( "%-20s %-20s", nombre, Format.formatMonto(datos.getLongValue( "Monto" )) );
    }
    @Override
    public String toString() {
        return String.format( "%-20s %-20s", nombre, Format.formatMonto(datos.getLongValue( "Monto" )) );      
    }
    
    public int execute(ICajaView vista, int key, Datos data){
        return 0;
    }
    public void llenarMP(MedioPagoCaja mp){
    	
    }
}
