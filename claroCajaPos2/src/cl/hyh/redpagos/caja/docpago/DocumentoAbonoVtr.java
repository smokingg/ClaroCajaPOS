package cl.hyh.redpagos.caja.docpago;

import java.util.GregorianCalendar;

import cl.hyh.cajas.ws.impl.CuentaAbonoVTR;
import cl.hyh.cajas.ws.impl.CuentaVTR;
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
public class DocumentoAbonoVtr extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Documento: " + this.getDatos().getStringValue( "NumeroDocumento" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "NumeroCuenta" );
    }    
    
    public boolean isIngresable(CarroCompra carro){
    	for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("NumeroCuenta"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){                
            	return false;
            }
        }
        return true;
    }
    
    public String glosaCarro() {
        String n = "Abono VTR";
        return String.format( "%-20s %10s $%15s", n,this.getDatos().getStringValue("NumeroCuenta"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }
    
    public void llenarDatos(CuentaAbonoVTR cuenta){
    	this.getDatos().setValue("NumeroCuenta", cuenta.getNumeroCuenta());
    	this.getDatos().setValue("SistemaOrigen", cuenta.getSistemaOrigen());
    	this.getDatos().setValue("DireccionPostal", cuenta.getDireccionPostal());
    	this.getDatos().setValue("Tipo", "DocumentoAbonoVtr");
    }
    
    public void vaciarDatos(TransaccionCaja trx){
    	this.getDatos().setValue("Monto", trx.getMonto());
    	this.getDatos().setValue("DireccionPostal", "NA");
    	this.getDatos().setValue("NumeroCuenta", Long.toString(trx.getCuentaCliente()));
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoAbonoVtr");
    }
    
    public void llenarTrx(TransaccionCaja trx){
    	Tools.initTrx(trx);    	
    	
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(Long.parseLong(this.getDatos().getStringValue("NumeroCuenta")));
    	if(this.getDatos().getStringValue("SistemaOrigen").equals("TANGO")){
    		trx.setOrigen("1");
    	}
    	else{
    		trx.setOrigen("2");
    	}    	
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
