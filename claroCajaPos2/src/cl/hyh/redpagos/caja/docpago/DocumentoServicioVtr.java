package cl.hyh.redpagos.caja.docpago;

import java.util.GregorianCalendar;

import cl.hyh.cajas.ws.impl.ServicioVTR;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Representacion de un documento de Telefonica
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DocumentoServicioVtr extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Servicio: " + this.getDatos().getStringValue( "IdentificadorServicio" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "IdentificadorServicio" );
    }    
    public boolean isIngresable(CarroCompra carro){
    	for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("IdentificadorServicio"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){                
            	return false;
            }
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoCuentaVtr")){
            	if(carro.getDocument(i).getDatos().getStringValue("NumeroCuenta").equals(this.getDatos().getStringValue("NumeroCuenta")))
            		return false;
            }
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoVtr")){
            	if(carro.getDocument(i).getDatos().getStringValue("NumeroCuenta").equals(this.getDatos().getStringValue("NumeroCuenta")))
            		return false;
            }
        }
        return true;
    }
    public String glosaCarro() {
        String n = "Servicio VTR";
        return String.format( "%-20s $%15s %10s %5s %9s", n,Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )),this.getDatos().getStringValue("NumeroCuenta"),this.getDatos().getStringValue("Producto").trim(),this.getDatos().getStringValue("NumeroServicio").trim() );

    }
    public void llenarDatos(ServicioVTR aux){
    	this.getDatos().setValue("Monto", (long)aux.getSaldoServicio());
    	this.getDatos().setValue("CuentaUnica", aux.getCuentaUnica());
    	this.getDatos().setValue("NumeroCuenta", aux.getNumeroCuenta());
    	this.getDatos().setValue("NumeroDocumento", aux.getNumeroDocumento());
    	this.getDatos().setValue("SistemaOrigen", aux.getSistemaOrigen());
    	this.getDatos().setValue("NumeroServicio", aux.getNumeroServicio());
    	this.getDatos().setValue("EstadoServicio", aux.getEstadoServicio());
    	this.getDatos().setValue("Producto", aux.getProducto());
    	this.getDatos().setValue("IdentificadorServicio", aux.getIdentificadorServicio());
    	this.getDatos().setValue("Tipo", "DocumentoServicioVtr");
    }
    public void vaciarDatos(TransaccionCaja trx){
    	this.getDatos().setValue("Monto", trx.getMonto());
    	this.getDatos().setValue("CuentaUnica", "0");
    	this.getDatos().setValue("NumeroCuenta", Long.toString(trx.getCuentaCliente()));
    	this.getDatos().setValue("NumeroDocumento", trx.getNumeroDocumento());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("NumeroServicio", trx.getServicio());
    	this.getDatos().setValue("EstadoServicio", "NA");
    	this.getDatos().setValue("Producto", "NA");
    	this.getDatos().setValue("IdentificadorServicio", "NA");
    	this.getDatos().setValue("Tipo", "DocumentoServicioVtr");
    }
    public void llenarTrx(TransaccionCaja trx){
    	Tools.initTrx(trx);
    	
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(Long.parseLong(this.getDatos().getStringValue("NumeroCuenta")));
    	trx.setNumeroDocumento(this.getDatos().getStringValue("NumeroDocumento"));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
    	trx.setServicio(this.getDatos().getStringValue("NumeroServicio"));
    	trx.setTipoRegistro(this.getDatos().getStringValue("IdentificadorServicio"));
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	if(aux.length == 2){
	    	trx.setRut(aux[0]);
	    	trx.setDv(aux[1]);
    	}
    	else{
    		trx.setRut("");
	    	trx.setDv("");
    	}
    }
}
