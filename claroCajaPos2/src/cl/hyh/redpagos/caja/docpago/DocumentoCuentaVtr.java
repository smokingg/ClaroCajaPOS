package cl.hyh.redpagos.caja.docpago;

import java.util.GregorianCalendar;

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
public class DocumentoCuentaVtr extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Cuenta: " + this.getDatos().getStringValue( "NumeroCuenta" ) + "\nMonto: " +
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
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoVtr")){
            	if(carro.getDocument(i).getDatos().getStringValue("NumeroCuenta").equals(this.getDatos().getStringValue("NumeroCuenta")))
            		return false;
            }
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoServicioVtr")){
            	if(carro.getDocument(i).getDatos().getStringValue("NumeroCuenta").equals(this.getDatos().getStringValue("NumeroCuenta")))
            		return false;
            }
        }
        return true;
    }
    public String glosaCarro() {
        String n = "Cuenta Claro";
        String direccion = this.getDatos().getStringValue("DireccionCobranza");
        if(direccion.length() > 15){
        	direccion = direccion.substring(0,15).trim();
        }
        String origen = this.getDatos().getStringValue("SistemaOrigenClaro");
        return String.format( "%-20s $%15s %10s %15s", n,Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )),this.getDatos().getStringValue("NumeroCuenta"), origen );
    }
    public void llenarDatos(CuentaVTR cuenta){
    	this.getDatos().setValue("FechaVencimiento", Tools.getFecha(cuenta.getFechaVencimiento().getTime()));
    	this.getDatos().setValue("Monto", (long)cuenta.getSaldoCuenta());
    	this.getDatos().setValue("CuentaUnica", cuenta.getCuentaUnica());
    	this.getDatos().setValue("DireccionCobranza", cuenta.getDireccionCobranza());
    	this.getDatos().setValue("NumeroCuenta", cuenta.getNumeroCuenta());
    	this.getDatos().setValue("SistemaOrigen", cuenta.getSistemaOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoCuentaVtr");
    }
    public void vaciarDatos(TransaccionCaja trx){
    	this.getDatos().setValue("FechaVencimiento", Tools.getFecha(trx.getFechaVencimiento().getTime()));
    	this.getDatos().setValue("Monto", trx.getMonto());
    	this.getDatos().setValue("CuentaUnica", "0");
    	this.getDatos().setValue("DireccionCobranza", "NA");
    	this.getDatos().setValue("NumeroCuenta", Long.toString(trx.getCuentaCliente()));
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoCuentaVtr");
    }
    public void llenarTrx(TransaccionCaja trx){
    	Tools.initTrx(trx);
    	
    	int year = Integer.parseInt(this.getDatos().getStringValue("FechaVencimiento").substring(0,4));
    	int month = Integer.parseInt(this.getDatos().getStringValue("FechaVencimiento").substring(4,6));
    	int day = Integer.parseInt(this.getDatos().getStringValue("FechaVencimiento").substring(6,8));
    	
    	
    	trx.setFechaVencimiento(new GregorianCalendar(year,month - 1 ,day));
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(Long.parseLong(this.getDatos().getStringValue("NumeroCuenta")));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
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
