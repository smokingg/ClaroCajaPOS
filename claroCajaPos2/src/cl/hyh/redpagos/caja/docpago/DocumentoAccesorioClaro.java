package cl.hyh.redpagos.caja.docpago;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleCuenta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.base.install.Base;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.CarroMPagos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Documento para Pago de Accesorios Claro
 * @author cbriones
 *
 */
public class DocumentoAccesorioClaro extends DocumentoPago{

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
        }
        return true;
    }
    
    public String glosaCarro() {
        String n = "Venta Accesorio";
        return String.format( "%-20s %10s $%15s", n,this.getDatos().getStringValue("NumeroDocumento"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }
    
    public void vaciarDatos(ws.claro.cl.TransaccionDTO trx){
    	this.getDatos().setValue("Monto", trx.getMontoDocumento());
    	//this.getDatos().setValue("DireccionPostal", "NA");
    	this.getDatos().setValue("NumeroCuenta", trx.getCuentaCliente());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoAccesorioClaro");
    }
    
    public void llenarTrxClaro(Transaccion trx){
    	Tools.initTrxClaro(trx);    	
    	
    	trx.setFechaVencimiento(Tools.getFecha());
    	trx.setMonto((this.getDatos().getLongValue("Monto")));
    	//trx.setCuentaCliente(this.getDatos().getStringValue("NumeroCuenta")); // Consultar que se debe enviar aca ???
    	trx.setOrigen("");	// Se envia en blanco...
    	trx.setTipoRegistro("PagoAccesorio"); // Consultar que se debe enviaar aca ???
    	trx.setNumeroDocumento(Long.parseLong(this.getDatos().getStringValue("NumeroDocumento")));
    	trx.setTipoDocumento("B/V");
    	
    	// TODO se deben completar los 100 primeros caracteres para Vendedor
    	// los restantes seran para la glosa
    	String vendedor = String.format("%-100s",this.getDatos().getStringValue("CodigoRecaudador"));
    	System.out.println("Vendedor rellenado: ["+vendedor+"]");
    	
    	trx.setTipoRegistro(vendedor+"="+this.getDatos().getStringValue("Glosa"));
    	System.out.println("Tipo Registro unificado: ["+trx.getTipoRegistro()+"]");
    	
    	if(!"".equalsIgnoreCase(this.getDatos().getStringValue("Rut")) && 
    	   this.getDatos().getStringValue("Rut") != null){
    		String []aux = this.getDatos().getStringValue("Rut").split("-");
        	if(aux.length == 2){
    	    	trx.setRut(new Long(aux[0]));
    	    	trx.setDv(aux[1]);
        	}
        	else{
        		trx.setRut(new Long(0));
    	    	trx.setDv("");
        	}
    	}else{
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
