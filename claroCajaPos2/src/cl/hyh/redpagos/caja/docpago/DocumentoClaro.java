package cl.hyh.redpagos.caja.docpago;

import java.util.GregorianCalendar;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Documento de Cuentas para VTV !!
 * @author cbriones
 *
 */
public class DocumentoClaro extends DocumentoPago{

	public String glosaCarro() {
		/*String n = "Documento de Cuenta Claro";
        String direccion = this.getDatos().getStringValue("DireccionCobranza");
        if(direccion.length() > 15){
        	direccion = direccion.substring(0,15).trim();
        }
        */
        // TODO cbriones. aca se debe definir las columnas desplegadas en la lista ??
        return String.format( "%-20s %15s $%10s", 
        		              this.getDatos().getStringValue("SistemaOrigenClaro"),
        		              this.getDatos().getStringValue("CicloFacturacionClaro"),
        		              Format.formatMontoPantalla(this.getDatos().getLongValue( "TotalSaldoClaro" )) );
	}
	 
	 public void llenarDatos(DetalleCuenta cuenta){
	    	this.getDatos().setValue("SaldoCastigadoClaro", (long)cuenta.getSaldoCastigado());
	    	this.getDatos().setValue("CicloFacturacionClaro", cuenta.getCicloFacturacion());
	    	this.getDatos().setValue("SaldoLimiteCreditoClaro", (long)cuenta.getSaldoLimiteCredito());
	    	this.getDatos().setValue("NumeroCuentaClaro", cuenta.getNumeroCuenta());
	    	this.getDatos().setValue("SistemaOrigenClaro", cuenta.getSistemaOrigen());
	    	this.getDatos().setValue("TotalSaldoClaro", (long)cuenta.getTotalSaldo());
	    	this.getDatos().setValue("TotalSaldoLDIClaro", (long)cuenta.getTotalSaldoLDI());
	    	this.getDatos().setValue("Tipo", "DocumentoClaro");
	}
	 
	public void vaciarDatos(TransaccionCaja trx){
		this.getDatos().setValue("FechaVencimiento", Tools.getFecha(trx.getFechaVencimiento().getTime()));
		this.getDatos().setValue("Monto", trx.getMonto());
		this.getDatos().setValue("CuentaUnica", "0");
		this.getDatos().setValue("DireccionCobranza", "NA");
		this.getDatos().setValue("NumeroCuenta", Long.toString(trx.getCuentaCliente()));
		this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
		this.getDatos().setValue("Tipo", "DocumentoCuentaClaro");
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
