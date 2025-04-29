package cl.hyh.redpagos.caja.docpago;

import ws.claro.cl.DevolucionDTO;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

public class DocumentoDevolucionClaro extends DocumentoPago {

	public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nCodigo Devolucion: " + this.getDatos().getStringValue( "CodigoDevolucion" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "CodigoDevolucion" );
    }    
    
    public boolean isIngresable(CarroCompra carro){
    	for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("CodigoDevolucion"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){                
            	return false;
            }
        }
        return true;
    }
    
    public String glosaCarro() {
        String n = "Devolución Claro";
        return String.format( "%-20s %10s %15s $%15s", n,this.getDatos().getStringValue("NumeroCuenta"), this.getDatos().getStringValue("Concepto"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }
    
    // TODO validar que DTO se dispondra para cargar info en el documento  
    public void llenarDatos(DevolucionDTO cuenta){
    	this.getDatos().setValue("Monto", cuenta.getMonto());
    	this.getDatos().setValue("NumeroCuenta", cuenta.getNumCuenta()); 
    	this.getDatos().setValue("CodigoDevolucion", cuenta.getCodDevolucion());
    	this.getDatos().setValue("Concepto", cuenta.getConcepto());
    	//this.getDatos().setValue("SistemaOrigen", cuenta.g);
    	this.getDatos().setValue("Tipo", "DocumentoDevolucionClaro");
    }
    
    public void llenarTrx(Transaccion trx){
    	Tools.initTrxClaro(trx);    	
    	
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(this.getDatos().getStringValue("NumeroCuenta"));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
    	trx.setNumeroDocumento(new Long(this.getDatos().getStringValue("CodigoDevolucion")));
    	trx.setFechaVencimiento(Tools.getFecha());
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	trx.setRut(new Long(aux[0]));
    	trx.setDv(aux[1]);
    }
}
