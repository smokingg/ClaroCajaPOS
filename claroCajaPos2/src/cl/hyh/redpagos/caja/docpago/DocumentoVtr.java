package cl.hyh.redpagos.caja.docpago;

import java.util.GregorianCalendar;

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
public class DocumentoVtr extends DocumentoPago {

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
        return this.getDatos().getStringValue( "NumeroDocumento" );
    }    
    
    public boolean isIngresable(CarroCompra carro){
    	for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("NumeroDocumento"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){                
            	return false;
            }
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoCuentaVtr")){
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
        String n = "Documento VTR";
        return String.format( "%-20s $%15s %10s %3s %11s", n,Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )),this.getDatos().getStringValue("NumeroCuenta"),this.getDatos().getStringValue("TipoDocumento").trim(),this.getDatos().getStringValue("NumeroDocumento").trim() );
    }
    
    public void llenarDatos(DocumentoVTR doc){
    	this.getDatos().setValue("FechaVencimiento", Tools.getFecha(doc.getFecVctoDocumento().getTime()));
    	this.getDatos().setValue("FechaEmision", Tools.getFecha(doc.getFecEmisionDocumento().getTime()));
    	this.getDatos().setValue("Monto", (long)doc.getSaldoDocumento());
    	this.getDatos().setValue("CuentaUnica", doc.getCuentaUnica());
    	this.getDatos().setValue("DireccionCobranza", doc.getDireccionCobranza());
    	this.getDatos().setValue("NumeroCuenta", doc.getNumeroCuenta());
    	this.getDatos().setValue("NumeroDocumento", doc.getNumeroDocumento());
    	this.getDatos().setValue("NumeroCorrelativo", doc.getNumeroCorrelativo());
    	this.getDatos().setValue("SistemaOrigen", doc.getSistemaOrigen());
    	this.getDatos().setValue("TipoDocumento", doc.getCodTipoDocumento());
    	this.getDatos().setValue("TipoCorrelativo", doc.getTipoCorrelativo());
    	this.getDatos().setValue("Tipo", "DocumentoVtr");
    }
    
    public void vaciarDatos(TransaccionCaja trx){
    	this.getDatos().setValue("FechaVencimiento", Tools.getFecha(trx.getFechaVencimiento().getTime()));
    	this.getDatos().setValue("FechaEmision", Tools.getFecha(trx.getFechaVencimiento().getTime()));
    	this.getDatos().setValue("Monto", trx.getMonto());
    	this.getDatos().setValue("CuentaUnica", "0");
    	this.getDatos().setValue("DireccionCobranza", "NA");
    	this.getDatos().setValue("NumeroCuenta", Long.toString(trx.getCuentaCliente()));
    	this.getDatos().setValue("NumeroDocumento", trx.getNumeroDocumento());
    	this.getDatos().setValue("NumeroCorrelativo", trx.getNumeroCorrelativo());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("TipoDocumento", trx.getTipoDocumento());
    	this.getDatos().setValue("TipoCorrelativo", trx.getTipoCorrelativo());
    	this.getDatos().setValue("Tipo", "DocumentoVtr");
    }
    
    public void llenarTrx(TransaccionCaja trx){
    	Tools.initTrx(trx);
    	
    	int year = Integer.parseInt(this.getDatos().getStringValue("FechaVencimiento").substring(0,4));
    	int month = Integer.parseInt(this.getDatos().getStringValue("FechaVencimiento").substring(4,6));
    	int day = Integer.parseInt(this.getDatos().getStringValue("FechaVencimiento").substring(6,8));
    	
    	
    	trx.setFechaVencimiento(new GregorianCalendar(year,month - 1,day));
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(Long.parseLong(this.getDatos().getStringValue("NumeroCuenta")));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
    	trx.setNumeroDocumento(this.getDatos().getStringValue("NumeroDocumento"));
    	trx.setTipoDocumento(this.getDatos().getStringValue("TipoDocumento"));
    	trx.setTipoCorrelativo(this.getDatos().getStringValue("TipoCorrelativo"));
    	trx.setNumeroCorrelativo(this.getDatos().getStringValue("NumeroCorrelativo"));
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
