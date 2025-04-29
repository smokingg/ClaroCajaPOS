package cl.hyh.redpagos.caja.docpago;

import javax.swing.JOptionPane;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.CarroMPagos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Tools;
/**
 * Documento de Cuentas para aplicacion Claro...
 * @author cbriones
 *
 */
public class DocumentoCuentaClaro extends DocumentoPago{

	 public String glosaCarro() {
	        String n = "Doc. Claro";
	        /**
	        String direccion = this.getDatos().getStringValue("DireccionCobranzaClaro");
	        if(direccion.length() > 15){
	        	direccion = direccion.substring(0,15).trim();
	        }
	        */
	        return String.format("%-10s %10s %10s %15s $%10s", 
	        		this.getDatos().getStringValue("SistemaOrigenClaro"), 
	        		this.getDatos().getStringValue("TipoDocumentoClaro"),
	        		this.getDatos().getStringValue("FolioDocumentoClaro"),
	        		this.getDatos().getStringValue("FechaVencimientoClaro"),
	        		Format.formatMontoPantalla(this.getDatos().getLongValue("SaldoAdeudadoClaro")));
	 }
	 
	 public void llenarDatos(DetalleDocumento detalleDoc){
		 	// No existe el Rut en este DTO !!
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
	    	this.getDatos().setValue("TelefonoContacto",detalleDoc.getTelefonoContacto());
	    	this.getDatos().setValue("CodigoPortador",detalleDoc.getCodigoPortador());
	    	
	    	this.getDatos().setValue("Tipo", "DocumentoCuentaClaro");
	    	
	    	//this.getDatos().setValue("TelefonoRecarga", "DocumentoCuentaClaro");
	    	
	    	this.getDatos().setValue("ClaveDocClaro", detalleDoc.getFolioDocumento()+
	    			//this.getDatos().getStringValue("Rut")+
	    			detalleDoc.getCodigoEmpresa()+
	    			detalleDoc.getTipoDocumento()+
	    			detalleDoc.getTipoRegistro()+
	    			detalleDoc.getSistemaOrigen()+
	    			detalleDoc.getIdServicio());
	}
	 
	 // TODO: validar el DTO a utilizar para la Trx de Caja en Claro ???
    public void vaciarDatosClaro(ws.claro.cl.TransaccionDTO trx){
    	this.getDatos().setValue("FechaVencimientoClaro", trx.getFechaVencimiento());
    	this.getDatos().setValue("Monto", trx.getMontoDocumento());
    	this.getDatos().setValue("TipoTransaccionClaro",trx.getTipoTransaccion());
    	this.getDatos().setValue("TipoDocumentoClaro", trx.getTipoDocumento());
    	this.getDatos().setValue("IdServicioClaro", trx.getServicio());
    	this.getDatos().setValue("SistemaOrigenClaro", trx.getOrigen());
    	this.getDatos().setValue("SistemaOrigenClaro", trx.getOrigen());
    	this.getDatos().setValue("CodigoEmpresaClaro",trx.getEmpresa());
    	
    	this.getDatos().setValue("Rut",trx.getRut()+"-"+trx.getDv());
    	this.getDatos().setValue("SaldoAdeudadoClaro",trx.getMontoDocumento());
    	//this.getDatos().setValue("CuentaUnica", "0");
    	//this.getDatos().setValue("DireccionCobranza", "NA");
    	this.getDatos().setValue("NumeroCuenta", trx.getCuentaCliente());
    	this.getDatos().setValue("FolioDocumentoClaro", trx.getNumeroDocumento());
    	this.getDatos().setValue("SistemaOrigen", trx.getOrigen());
    	this.getDatos().setValue("Tipo", "DocumentoCuentaClaro");
    	
    }
    
    
    /**
	  * El DTO a utilizar para la Trx de Caja en Claro es Transaccion 
	  */
    public void llenarTrx(Transaccion trx){
    	Tools.initTrxClaro(trx);
    	
    	/* validar si debe cambiar el formato de la fecha de vencimiento ??
    	int year = Integer.parseInt(this.getDatos().getStringValue("FechaVencimientoClaro").substring(0,4));
    	int month = Integer.parseInt(this.getDatos().getStringValue("FechaVencimientoClaro").substring(4,6));
    	int day = Integer.parseInt(this.getDatos().getStringValue("FechaVencimientoClaro").substring(6,8));
    	*/
    	
    	//trx.setFechaVencimiento(new GregorianCalendar(year,month - 1 ,day));
    	trx.setFechaVencimiento(this.getDatos().getStringValue("FechaVencimientoClaro"));
    	// deberia corresponder al saldo Adeudado ?? 
    	//trx.setMonto(this.getDatos().getLongValue("Monto"));
    	trx.setMonto(this.getDatos().getLongValue("SaldoAdeudadoClaro"));
    	trx.setCuentaCliente(this.getDatos().getStringValue("CuentaClaro"));
    	trx.setOrigen(this.getDatos().getStringValue("SistemaOrigenClaro"));
    	trx.setTipoDocumento(this.getDatos().getStringValue("TipoDocumentoClaro"));
    	trx.setTipoRegistro(this.getDatos().getStringValue("TipoRegistroClaro"));
    	trx.setNumeroDocumento(this.getDatos().getLongValue("FolioDocumentoClaro"));
    	trx.setServicio(this.getDatos().getStringValue("IdServicioClaro"));
    	trx.setEmpresa(new Integer(this.getDatos().getLongValue("CodigoEmpresaClaro")+""));
    	trx.setCodigoPortador(this.getDatos().getIntValue("CodigoPortador"));
    	
    	System.out.println("Rut en Documento Cuenta Claro: "+this.getDatos().getStringValue("Rut"));
    	
    	String []aux = this.getDatos().getStringValue("Rut").split("-");
    	if(aux.length == 2){
	    	trx.setRut(new Long(aux[0]));
	    	trx.setDv(aux[1]);
    	}/**
    	else{
    		trx.setRut(new Long(0));
	    	trx.setDv("");
    	}
    	*/
    }
    
    public void llenarMovilTrx(Transaccion trx){
    	Tools.initTrxClaro(trx);
    	
    	trx.setFechaVencimiento(this.getDatos().getStringValue("FechaVencimientoClaro"));
    	trx.setMonto(this.getDatos().getLongValue("SaldoAdeudadoClaro"));
    	trx.setCuentaCliente(this.getDatos().getStringValue("CuentaClaro"));
    	//trx.setOrigen(this.getDatos().getStringValue("SistemaOrigenClaro"));
    	trx.setTipoDocumento(this.getDatos().getStringValue("TipoDocumentoClaro"));
    	//trx.setTipoRegistro(this.getDatos().getStringValue("TipoRegistroClaro"));
    	trx.setNumeroDocumento(this.getDatos().getLongValue("FolioDocumentoClaro"));
    	trx.setServicio(this.getDatos().getStringValue("IdServicioClaro"));
    	trx.setEmpresa(new Integer(this.getDatos().getLongValue("CodigoEmpresaClaro")+""));
    	trx.setOrigen("VTV");	
    	trx.setTipoRegistro("PagoAbono");
    	System.out.println("Rut en Documento Cuenta Claro: "+this.getDatos().getStringValue("Rut"));
    	
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
//    	trx.setTipoRegistro("DEUDA");
    	trx.setTipoRegistro(this.getDatos().getStringValue("TipoRegistroClaro"));
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
     * Metodos que retornan la clave unica del Doc.
     * Para Claro sera:  Folio + Rut + Empresa + Tipo Doc + Tipo Reg + Origen + id Serv ???
     */
    
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "ClaveDocClaro" );
    }
    
    /**
     * Metodos que retornan el Numero de Cuenta al que pertenece el Doc
     */
    public String getCuentaClaro(){
        return this.getDatos().getStringValue( "CuentaClaro" );
    }
    
    /**
     * Metodos que retornan el Tipo de Resgistro del Doc
     */
    public String getTipoRegistro(){
        return this.getDatos().getStringValue( "TipoRegistroClaro" );
    }
    
    /**
     * Metodo para validar que un documento no se suba mas de una vez al carro !!
     */
    public boolean isIngresable(CarroCompra carro){
    	for(int i = 0; i < carro.getDocumentos().size(); i++){
    		
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("ClaveDocClaro"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){                
            	return false;
            }
            
            if(carro.getDocument(i).getDatos().getStringValue("Tipo").equals("DocumentoItemClaro")){
            	if(carro.getDocument(i).getDatos().getStringValue("NumeroDocumento").equals(this.getDatos().getStringValue("FolioDocumentoClaro")))
            		return false;
            }
            
        }
        return true;
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
    	System.out.println("Editable: Total Documento a subir: "+montoDocumento);
    	
    	// validamos que la suma de los documentos de carro + el documento a subir
    	// Sea menor o igual al Monto Total del Carro de Medios de Pago
    	if(totalCarroP >= montoDocumento + totalCarroC){          
    		System.out.println("Editable: Los Medios de Pago son Mayor a los Documentos... OK");
        	return true;
        }
    	System.out.println("Editable: Los Documentos Suman mas que el Total del Carro de Pagos... NOK");
        return false;
    }
    
    /**
     * Metodo para validar que un documento SC o ST NO se puede sacar del carro,
     * Se debe cancelar estos solos o en conjunto con la Deuda.
     */
    public boolean isEliminable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getNombre().equals("DocumentoCuentaClaro")){
                DocumentoPago doc = carro.getDocument(i);
                
                if("VTV".equalsIgnoreCase(this.getDatos().getStringValue("SistemaOrigenClaro")) &&
                   "SC".equalsIgnoreCase(this.getDatos().getStringValue("TipoRegistroClaro")) &&
                   doc.getDatos().getStringValue("CuentaClaro").equalsIgnoreCase(this.getDatos().getStringValue("CuentaClaro")) &&
                   "DEUDA".equalsIgnoreCase(doc.getDatos().getStringValue("TipoRegistroClaro"))){
                	
                   JOptionPane.showMessageDialog(null, "No se puede Eliminar Saldo Castigado, Debe cancelarlo junto a la deuda pendiente", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                   return false;
                }
                /*
                if("VTV".equalsIgnoreCase(this.getDatos().getStringValue("SistemaOrigenClaro")) &&
                   "ST".equalsIgnoreCase(this.getDatos().getStringValue("TipoRegistroClaro")) &&
                   doc.getDatos().getStringValue("CuentaClaro").equalsIgnoreCase(this.getDatos().getStringValue("CuentaClaro")) &&
                   "DEUDA".equalsIgnoreCase(doc.getDatos().getStringValue("TipoRegistroClaro"))){
                     	
                   JOptionPane.showMessageDialog(null, "No se puede Eliminar Limite Credito, Debe cancelarlo junto a la deuda pendiente", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                   return false;
            	}
                */                       
                continue;
            }
        }
        
        return true;
    }
    
}
