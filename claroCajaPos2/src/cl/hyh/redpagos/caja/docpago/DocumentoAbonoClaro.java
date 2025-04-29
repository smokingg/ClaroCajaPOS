package cl.hyh.redpagos.caja.docpago;


import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.base.install.Base;
import cl.hyh.cajas.ws.impl.CuentaAbonoVTR;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.CarroMPagos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Documento para Abonos a Cuentas Claro
 * @author cbriones
 *
 */
public class DocumentoAbonoClaro extends DocumentoPago{

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
        String n = "Abono Claro";
        return String.format( "%-20s %10s $%15s", n,this.getDatos().getStringValue("NumeroCuenta"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }
    
    public void llenarDatos(DetalleCuenta cuenta){
    	this.getDatos().setValue("NumeroCuenta", cuenta.getNumeroCuenta());
    	this.getDatos().setValue("SistemaOrigen", cuenta.getSistemaOrigen());
    	this.getDatos().setValue("TotalSaldo", cuenta.getTotalSaldo());
    	//this.getDatos().setValue("DireccionPostal", cuenta.getDireccionPostal());
    	this.getDatos().setValue("Tipo", "DocumentoAbonoClaro");
    }
    
    //Abono fijo
    public void llenarDatos(DetalleDocumento detalleDoc){
	 	this.getDatos().setValue("FechaEmisionClaro", detalleDoc.getFechaEmisionDocumento());
    	this.getDatos().setValue("FechaVencimientoClaro", detalleDoc.getFechaVencimientoDocumento());
    	this.getDatos().setValue("SaldoClaro", (long)detalleDoc.getSaldo());
    	this.getDatos().setValue("SaldoAdeudadoClaro", (long)detalleDoc.getSaldoAdeudado());
    	this.getDatos().setValue("CuentaClaro", detalleDoc.getNumeroCuenta());
    	this.getDatos().setValue("DireccionCobranzaClaro", detalleDoc.getDireccionCliente());
    	this.getDatos().setValue("SistemaOrigenClaro", detalleDoc.getSistemaOrigen());
    	this.getDatos().setValue("ClienteClaro", detalleDoc.getNombreCliente());
    	this.getDatos().setValue("FolioDocumentoClaro", detalleDoc.getFolioDocumento());
    	this.getDatos().setValue("TipoDocumentoClaro", detalleDoc.getTipoDocumento());
    	this.getDatos().setValue("IdServicioClaro", detalleDoc.getIdServicio());
    	this.getDatos().setValue("TipoRegistroClaro",detalleDoc.getTipoRegistro());
    	this.getDatos().setValue("CodigoEmpresaClaro",detalleDoc.getCodigoEmpresa());
    	
    	this.getDatos().setValue("Tipo", "DocumentoAbonoFijoClaro");
    }
    
    public void vaciarDatos(ws.claro.cl.TransaccionDTO trx){
    	this.getDatos().setValue("Monto", trx.getMontoDocumento());
    	//this.getDatos().setValue("DireccionPostal", "NA");
    	this.getDatos().setValue("NumeroCuenta", trx.getCuentaCliente());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoAbonoClaro");
    }
    
    public void llenarTrx(Transaccion trx){
    	Tools.initTrxClaro(trx);    	
    	
    	trx.setFechaVencimiento(Tools.getFecha());
    	trx.setMonto((this.getDatos().getLongValue("Monto")));
    	trx.setCuentaCliente(this.getDatos().getStringValue("NumeroCuenta"));
    	
    	//REspinoza Ahora los abonos pueden ser a ONE
    	//trx.setOrigen("VTV");
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
    	
       	trx.setTipoRegistro(this.getDatos().getStringValue("TipoRegistro"));
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	if(aux.length == 2){
	    	trx.setRut(new Long(aux[0]));
	    	trx.setDv(aux[1]);
    	}
    	else{
    		trx.setRut(new Long(0));
	    	trx.setDv("");
    	}
    }
    
    //llenarTrx pero para Fijos REspinoza
	public void llenarFijoTrx(Transaccion trx) {
		Tools.initTrxClaro(trx);
    	
    	//trx.setFechaVencimiento(new GregorianCalendar(year,month - 1 ,day));
    	trx.setFechaVencimiento(this.getDatos().getStringValue("FechaVencimientoClaro"));
    	// deberia corresponder al saldo Adeudado ?? 
    	//trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setMonto((this.getDatos().getLongValue("Monto")));
    	trx.setCuentaCliente(this.getDatos().getStringValue("CuentaClaro"));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigenClaro"));
    	trx.setTipoDocumento(this.getDatos().getStringValue("TipoDocumentoClaro"));
    	trx.setTipoRegistro("DEUDA");
    	trx.setNumeroDocumento(this.getDatos().getLongValue("FolioDocumentoClaro"));
    	trx.setServicio(this.getDatos().getStringValue("IdServicioClaro"));    	
    	trx.setEmpresa(new Integer(this.getDatos().getStringValue("CodigoEmpresaClaro")));

    	System.out.println("Rut en Documento Cuenta Claro: "+this.getDatos().getStringValue("Rut"));
    	
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	if(aux.length == 2){
	    	trx.setRut(new Long(aux[0]));
	    	trx.setDv(aux[1]);
    	} else {
    		trx.setRut(new Long(0));
	    	trx.setDv("");
    	}
	}
     
    /**
     * Metodo para validar que un documento no se suba al carro de edicion
     * cuando el monto de este sobrepase lo que existe en el carro de Medios de pago
     */
    public boolean isEditable(CarroMPagos carroP, CarroCompra carroC, long montoDocumento){
    	
    	if(carroP.getMediosPago().size() == 0) return true;
    	long totalCarroP = 0;
    	long totalCarroC = 0;
    	
    	// Se contabiliza el total Del Carro de Medios Pago
    	for(int i = 0; i < carroP.getMediosPago().size(); i++){	
    		totalCarroP += carroP.getMediosPago().get(i).getMonto();
        }
    	
    	System.out.println("Editable: Total Carro Medio Pago: "+totalCarroP);
    	
    	// Se contabiliza el total del Carro de Documentos
    	for(int i = 0; i < carroC.getDocumentos().size(); i++){	
    		totalCarroC += carroC.getDocument(i).getMonto();
        }
    	
    	System.out.println("Editable: Total Carro Documentos: "+totalCarroC);
    	System.out.println("Editable: Total Documento a subir : "+montoDocumento);
    	
    	// validamos que la suma de los documentos de carro + el documento a subir
    	// Sea menor o igual al Monto Total del Carro de Medios de Pago
    	if(totalCarroP >= montoDocumento + totalCarroC){          
    		System.out.println("Editable: Los Medios de Pago son Mayor a los Documentos..OK");
        	return true;
        }
    	System.out.println("Editable: Los Documentos Suman mas que el Total del Carro de Pagos... NOK");
        return false;
    }
}
