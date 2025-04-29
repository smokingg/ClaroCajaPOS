package cl.hyh.redpagos.caja.docpago;

import ws.claro.cl.TransaccionDTO;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleProducto;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.base.install.Base;
import cl.hyh.cajas.ws.impl.ServicioVTR;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.CarroMPagos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

public class DocumentoItemClaro extends DocumentoPago{

	public String toString() {
        // retorna detalle de documento
        String s = "";
        
       // s = this.getNombre() + "\nNumero Item: " + this.getDatos().getStringValue( "NumeroItem" ) + "\nMonto: " +
        s = this.getDatos().getStringValue("NumeroDocumento") + "\n" + this.getDatos().getStringValue( "NumeroItem" ) 
        + "\n" + this.getDatos().getStringValue("DescripcionItem") + "\n" + Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "NumeroItem" );
    }    
    
    public boolean isIngresable(CarroCompra carro){
    	for(int i = 0; i < carro.getDocumentos().size(); i++){
    		
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("NumeroItem"))
               && carro.getDocument(i).getDatos().getStringValue("NumeroDocumento").equals(this.getDatos().getStringValue("NumeroDocumento"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){                
            	return false;
            }
            
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoCuentaClaro")){
            	System.out.println("Comparando con un Doc Cuenta Claro");
            	System.out.println("Nro Documento Item: "+this.getDatos().getStringValue("NumeroDocumento"));
            	System.out.println("Nro Documento Cuenta Claro: "+carro.getDocument(i).getDatos().getStringValue("FolioDocumentoClaro"));
            	if(carro.getDocument(i).getDatos().getStringValue("FolioDocumentoClaro").equals(this.getDatos().getStringValue("NumeroDocumento")))
            		return false;
            }
        }
        return true;
    }
    
    public String glosaCarro() {
       // String n = "Item Claro";
    	String n = "";
        return String.format( "%-8s %8s %8s %10s %10s $%10s",
        		this.getDatos().getStringValue("SistemaOrigen").trim(),
        		"Item",
        		this.getDatos().getStringValue("NumeroDocumento"),
        		this.getDatos().getStringValue("NumeroItem").trim(),
        		this.getDatos().getStringValue("DescripcionItem").trim(),
                Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )));

    }
    
    public void llenarDatos(DetalleProducto aux){
    	this.getDatos().setValue("Monto", (long)aux.getMontoItem());
    	// TODO No poseo ningun nro de Cuenta en este DTO !!
    	//this.getDatos().setValue("CuentaUnica", aux.getCuentaUnica());
    	//this.getDatos().setValue("NumeroCuenta", aux.getNumeroCuenta());
    	//this.getDatos().setValue("NumeroDocumento", "No esta en DTO");
    		
    	//un item ahora puede ser de VTV o de ONE
    	//this.getDatos().setValue("SistemaOrigen", "VTV");
    	
    	this.getDatos().setValue("NumeroItem", aux.getCodigoItem());    	
    	this.getDatos().setValue("DescripcionItem", aux.getDescripcionItem());
    	//this.getDatos().setValue("Producto", aux.getProducto());
    	//this.getDatos().setValue("IdentificadorServicio", aux.getCodigoItem());
    	this.getDatos().setValue("TipoItem", aux.getTipoItem());
    	//this.getDatos().setValue("TipoDocumento", "DOCDETALLE");
    	this.getDatos().setValue("Tipo", "DocumentoItemClaro");
    }
    
    public void vaciarDatos(Transaccion trx){
    	this.getDatos().setValue("Monto", trx.getMonto());
    	//this.getDatos().setValue("CuentaUnica", "0");
    	this.getDatos().setValue("NumeroCuenta", trx.getCuentaCliente());
    	this.getDatos().setValue("NumeroDocumento", trx.getNumeroDocumento());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("NumeroServicio", trx.getServicio());
    	this.getDatos().setValue("EstadoServicio", "NA");
    	this.getDatos().setValue("Producto", "NA");
    	this.getDatos().setValue("IdentificadorServicio", "NA");
    	this.getDatos().setValue("Tipo", "DocumentoItemClaro");
    }
    
    public void llenarTrxClaro(Transaccion trx){
    	Tools.initTrxClaro(trx);
    	
    	trx.setNroOperacionAReversar(new Long(this.getDatos().getStringValue("NumeroItem")));
    	
    	trx.setFechaVencimiento(this.getDatos().getStringValue("FechaVencimiento"));
    	trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setCuentaCliente(this.getDatos().getStringValue("NumeroCuenta"));
    	trx.setNumeroDocumento(new Long(this.getDatos().getStringValue("NumeroDocumento")));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigen"));
    	trx.setServicio(this.getDatos().getStringValue("NumeroServicio"));
    	trx.setTipoRegistro(this.getDatos().getStringValue("TipoRegistro"));
    	trx.setTipoDocumento(this.getDatos().getStringValue("TipoDocumento"));
    	trx.setEmpresa(this.getDatos().getIntValue("CodigoEmpresa"));
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
    
    public void vaciarDatosClaro(TransaccionDTO trx){
    	this.getDatos().setValue("Monto", trx.getMontoDocumento());
    	//this.getDatos().setValue("CuentaUnica", "0");
    	this.getDatos().setValue("NumeroCuenta", trx.getCuentaCliente());
    	this.getDatos().setValue("NumeroDocumento", trx.getNumeroDocumento());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("NumeroServicio", trx.getServicio());
    	this.getDatos().setValue("EstadoServicio", "NA");
    	this.getDatos().setValue("Producto", "NA");
    	this.getDatos().setValue("IdentificadorServicio", "NA");
    	this.getDatos().setValue("Tipo", "DocumentoItemClaro");
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
    	
    	Base.logger.info("Editable: Total Carro Medio Pago: "+totalCarroP);
    	
    	// Se contabiliza el total del Carro de Documentos
    	for(int i = 0; i < carroC.getDocumentos().size(); i++){	
    		totalCarroC += carroC.getDocument(i).getMonto();
        }
    	
    	Base.logger.info("Editable: Total Carro Documentos: "+totalCarroC);
    	Base.logger.info("Editable: Total Documento a subir : "+montoDocumento);
    	
    	// validamos que la suma de los documentos de carro + el documento a subir
    	// Sea menor o igual al Monto Total del Carro de Medios de Pago
    	if(totalCarroP >= montoDocumento + totalCarroC){          
    		Base.logger.info("Editable: Los Medios de Pago son Mayor a los Documentos..OK");
        	return true;
        }
    	Base.logger.info("Editable: Los Medios de Pago son Mayor a los Documentos..OK");
        return false;
    }
    
}
