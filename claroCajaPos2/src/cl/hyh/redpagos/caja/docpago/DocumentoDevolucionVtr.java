package cl.hyh.redpagos.caja.docpago;

import java.util.GregorianCalendar;

import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.cajas.ws.impl.DocumentoVTR;
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
public class DocumentoDevolucionVtr extends DocumentoPago {

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
        String n = "Devolución VTR";
        return String.format( "%-20s %10s $%15s", n,this.getDatos().getStringValue("NumeroCuenta"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }
    
    public void llenarDatos(CuentaVTR cuenta){
    	this.getDatos().setValue("Monto", -1*cuenta.getSaldoCuenta());
    	this.getDatos().setValue("NumeroCuenta", cuenta.getNumeroCuenta()); 
    	this.getDatos().setValue("CuentaUnica", cuenta.getCuentaUnica());
    	this.getDatos().setValue("DireccionCobranza", cuenta.getDireccionCobranza());
    	this.getDatos().setValue("SistemaOrigen", cuenta.getSistemaOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoDevolucionVtr");
    }
    
    public void llenarTrx(TransaccionCaja trx){
    	Tools.initTrx(trx);    	
    	
    	trx.setMonto(-1*this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(Long.parseLong(this.getDatos().getStringValue("NumeroCuenta")));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	trx.setRut(aux[0]);
    	trx.setDv(aux[1]);
    }
}
