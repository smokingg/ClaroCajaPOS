package cl.hyh.redpagos.caja.docpago;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Representacion de un documento para recargas de producto Claro
 * @author cbriones
 *
 */
public class DocumentoRecargaClaro extends DocumentoPago{

	public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nTelefono: " + this.getDatos().getLongValue( "Telefono" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "NumeroDocumento" );
    }
    
    public boolean isEliminable(CarroCompra carro){
       // return false;
    	return true;
    }
    
    public void llenarTrxClaro(Transaccion trx){
    	Tools.initTrxClaro(trx);
    	
    	//trx.setNroOperacionAReversar(new Long(this.getDatos().getStringValue("NumeroItem")));
    	
    	trx.setFechaVencimiento(Tools.getFecha());
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	//trx.setCuentaCliente(this.getDatos().getStringValue("NumeroCuenta"));
    	//trx.setNumeroDocumento(new Long(this.getDatos().getStringValue("NumeroDocumento")));
    	trx.setOrigen(this.getDatos().getStringValue("VTV"));
    	//trx.setServicio(this.getDatos().getStringValue("NumeroServicio"));
    	//trx.setTipoRegistro(this.getDatos().getStringValue("TipoRegistro"));
    	//trx.setTipoDocumento(this.getDatos().getStringValue("TipoDocumento"));
    	//trx.setEmpresa(new Integer(this.getDatos().getLongValue("CodigoEmpresa")+""));
    	/**
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	if(aux.length == 2){
	    	trx.setRut(new Long(aux[0]));
	    	trx.setDv(aux[1]);
    	}
    	else{
    		trx.setRut(new Long(0));
	    	trx.setDv("");
    	}
    	*/
    }
    
}
