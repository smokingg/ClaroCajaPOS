package cl.hyh.redpagos.caja.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import ws.claro.cl.TransaccionDTO;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefVoucher;

/**
 * Representación interna de los documentos de pago existentes en la aplicacion
 * @author Rafael Hernandez
 *
 */
public class DocumentoPago implements Serializable {

    private String nombre;
    private Datos datos = new Datos();
    
    /**
     * Retorna la variable datos
     * @return la estructura datos
     */
    public Datos getDatos() {
        return datos;
    }
    
    public void setDatos( Datos losDatos ) {
        datos = losDatos;
    }

    /**
     * Retorna la variable visible
     * @return
     */
    public boolean getVisible() {
        return datos.getBooleanValue( "Visible" );
    }
    
    /**
     * Retorna la variable montoVisible
     * @return
     */
    public long getMontoVisible() {
        return datos.getLongValue( "MontoVisible");
    }
    
    /**
     * Retorna la variable monto
     * @return
     */
    public long getMonto() {
        if(datos.getLongValue( "Monto" ) != 0){
            return datos.getLongValue( "Monto" );
        }
        else if(datos.getLongValue("MontoSaldo") != 0){
            return datos.getLongValue("MontoSaldo");
        }
        // cbriones: se agrega retorno para campo Monto Claro agregado para Pruebas !!!
        else if(datos.getLongValue("MontoClaro") != 0){
            return datos.getLongValue("MontoClaro");
        }
        // cbriones: se agrega retorno para campo Monto Claro  !!!
        else if(datos.getLongValue("SaldoAdeudadoClaro") != 0){
            return datos.getLongValue("SaldoAdeudadoClaro");
        }
        else{
            return datos.getLongValue("MontoDocumento");
        }
    }

    /**
     * Setea la variable monto
     * @param unMonto
     */
    public void setMonto(long unMonto) {
        datos.setValue( "Monto", unMonto );
    }
    
    /**
     * Retorna el arreglo de LineaVoucher que posee el documento de pago para el cliente
     * @return
     */
    public LineaVoucher[] getCustomerVoucher() {
        
        if(this.nombre.equals("AtisTelefonica") || this.nombre.equals("AtisTotalizado")){
            if(this.datos.getStringValue("Area").equals("") && this.datos.getStringValue("Telefono").equals("")){
                this.datos.setValue("isTelefono", 0);
            }
            this.getDatos().setValue("AreaTelefono", "(" + this.datos.getStringValue("Area") + ") " + 
                    this.datos.getStringValue("Telefono"));
            this.getDatos().setValue("ClienteCuenta", this.datos.getStringValue("Cliente") + "/" + this.datos.getStringValue("Cuenta"));
            String []docs = Base.getDocs("");
            int type = Integer.parseInt(this.getDatos().getStringValue("DocTipo"));
            this.getDatos().setValue("TipoDoc", Base.getTipoDoc(docs,Integer.toString(type)));            
        }
        
        if(this.getDatos().getStringValue("Rut") == null || this.getDatos().getStringValue("Rut").equals("")
                || this.getDatos().getStringValue("Rut").equals(" ")){
            this.getDatos().setValue("isRut", 0);
            this.getDatos().setValue("Rut", " ");
        }
        
        DefDocumentoPago def = Base.getDefDocumentoPago(this.nombre);
        DefVoucher defV = Base.getDefVoucher(def.getCustomerVoucher());
        ArrayList<LineaVoucher> lineas =  Voucher.armarVoucher(this.datos,defV);
        LineaVoucher []voucher = new LineaVoucher[lineas.size()];
        
        for(int i = 0; i < lineas.size(); i++){
            voucher[i] = lineas.get(i);
           // Base.logger.info("getCustomerVoucher - Doc. Pago: "+voucher[i].getLinea());
        }
        
        return voucher;
    }
    
    public void setCustomerVoucher(String voucher){
        this.nombre = voucher;
    }
    
    /**
     * Retorna el arreglo de LineaVoucher que posee el documento de pago para el comercio
     * @return
     */
    public LineaVoucher[] getCommerceVoucher() {
        try{
            DefDocumentoPago def = Base.getDefDocumentoPago(this.nombre);
            DefVoucher defV = Base.getDefVoucher(def.getComerceVoucher());
            if(defV == null){
                Base.logger.info("Definicion no encontrada");
                return null;
            }
            ArrayList<LineaVoucher> lineas =  Voucher.armarVoucher(this.datos,defV);
            LineaVoucher []voucher = new LineaVoucher[lineas.size()];
            for(int i = 0; i < lineas.size(); i++){
                voucher[i] = lineas.get(i);
                //Base.logger.info("getCommerceVoucher - voucher[i]: "+voucher[i].getLinea());
            }
            return voucher;
        }
        catch(Exception e){
            Base.logger.info("Definicion no encontrada");
            return null;
        }
    }

    /**
     * Setea la visibilidad del documento de pago
     * @param visible
     */
    public void setVisible(boolean visible) {
        datos.setValue( "Visible", visible );
    }

    /**
     * Setea la variable montoVisible
     * @param montoVisible
     */
    public void setMontoVisible(long montoVisible) {
        datos.setValue( "MontoVisible", montoVisible );
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String glosaCarro() {
        if(nombre.contains("Atis")){
            if(nombre.equals("AtisTelefonica")){
                return String.format( "%-20s %10s $%15s", "Cta Claro",this.getDatos().getStringValue("DocNumeroLATAM"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
            }
            else if(nombre.equals("AtisAbonoCtaCte")){
                return String.format( "%-20s %10s $%15s", "P. Adelantado Telefónica",this.getDatos().getStringValue("DocNumeroLATAM"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );                
            }
            else{
                return String.format( "%-20s %10s $%15s", nombre,this.getDatos().getStringValue("DocNumeroLATAM"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
            }
        }
        
        String nombreDoc = "";        
        if(this.getNombre().contains("Documento")){
            nombreDoc = this.getNombre().substring("Documento".length(), this.getNombre().length());
        }
        else{
            nombreDoc = this.getNombre();
        }
        if(nombre.equals("RecargaMovistar")){
        	nombreDoc = "Recarga Vtr";
        }
        if( nombreDoc.trim().equals("Mundo") ){
            nombreDoc = "188 Telefonica LD";
        }
        else if( nombreDoc.trim().equals("Prosegur") ){
            nombreDoc = "Prosegur Activ";
        }
        else if( nombreDoc.trim().equals("Globus")){
            nombreDoc = "120 Globus LD";
        }   
        else if( nombreDoc.trim().equals("TelefonicaEmpresas")){
            nombreDoc = "Documento Vtr";
        } 
            return String.format( "%-30s  $%15s", nombreDoc, Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }
    
    @Override
    public String toString() {
        return String.format( "%-30s %-15s", nombre, Format.formatMonto(datos.getLongValue( "Monto" )) );      
    }
    
    public boolean equals( DocumentoPago dp1 ) {
        return false;
    }
    public String getClaveDoc(){
        return "";
    }
    public boolean isEliminable(CarroCompra carro){
        return true;
    }
    public boolean isIngresable(CarroCompra carro){
        return true;
    }
    public boolean isEditable(CarroMPagos carroMp, CarroCompra carroCm, long montoDocumento){
        return true;
    }
    public boolean isIngresableParcial(CarroCompra carro){
        return true;
    }
    public int isIngresableCuotas(CarroCompra carro){
        return 0;
    }
    public void llenarDatos(Object o){
    	
    }
    public void vaciarDatos(TransaccionCaja trx){
    	
    }
    public void vaciarDatosClaro(TransaccionDTO trx){
    	
    }
    
    public void llenarTrx(TransaccionCaja trx){
    	
    }
    // Se agrega Seteo para Trx Claro
    public void llenarTrxClaro(Transaccion trx){
    	
    }
    // Se agrega retorno para Numero de Cuenta
    public String getCuentaClaro(){
        return "";
    }
    // Se agrega retorno para Tipo de Registro
    public String getTipoRegistro(){
        return "";
    }
    
}
