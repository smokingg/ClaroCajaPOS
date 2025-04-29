package cl.hyh.redpagos.caja.base; 

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.cajas.ws.impl.CierreDiarioOut;
import cl.hyh.cajas.ws.impl.EnvioPagoIn;
import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.cajas.ws.impl.NumeroOperacionOut;
import cl.hyh.cajas.ws.impl.OperacionCaja;
import cl.hyh.cajas.ws.impl.OperacionIn;
import cl.hyh.cajas.ws.impl.Request;
import cl.hyh.cajas.ws.impl.Response;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.redpagos.caja.docpago.DocumentoAbonoClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoAbonoVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoAccesorioClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoDevolucionClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoDevolucionVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoItemClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoRecargaClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoServicioVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoVtr;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;
import cl.hyh.redpagos.caja.mpago.Cheque;
import cl.hyh.redpagos.caja.mpago.ChequeFecha;
import cl.hyh.redpagos.caja.mpago.ChequeOffline;
import cl.hyh.redpagos.caja.mpago.Deposito;
import cl.hyh.redpagos.caja.mpago.Efectivo;
import cl.hyh.redpagos.caja.mpago.OtroMedioPago;
import cl.hyh.redpagos.caja.mpago.Tarjeta;
import cl.hyh.redpagos.caja.mpago.TarjetaManual;
import cl.hyh.redpagos.caja.mpago.TbkManual;
import cl.hyh.redpagos.caja.mpago.ValeVista;
import cl.hyh.redpagos.caja.trx.TrxTarjetaTbkManualDirecto;

import javax.swing.JOptionPane;

import ws.claro.cl.proxy.AppControlNotificarProxy;
import cl.hyh.redpagos.caja.trx.TrxTarjetaTbkDirecto;
/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Oper implements Serializable {

    protected Datos datos = new Datos();
    protected String servicio = "";
    protected long numeroOperacion;
    protected long numeroOperacionReversa;
    protected long numeroOperacionOriginal;
    protected long numeroOperacionEdicion;
    protected String fecha = "";
    protected String fechaPago = "";
    protected String fechaVenc = "";
    protected String hora = "";
    protected String operacion ="";
    protected String entidad="";
    protected String agencia="";
    protected String usuario="";
    protected String recaudador="";
    protected String caja="";
    protected String sesion="";
    protected String cajero="";
    protected String direccion="";
    protected String ventaDirecta="";
    protected String isVuelto="";
    protected boolean reversado=false;
    protected String recargaMovistar="";
    protected boolean regular = false;
    protected boolean recarga = false;
    protected boolean devolucion = false;
    protected boolean cut = false;
    protected boolean remesa = false;
    public static int RC_REINTENTAR = -1;
    public static int RC_CANCELAR = -3;
    public static int RC_NUEVA = -2;
    public static int RC_REVERSAR = -4;
    public static int RC_OK = 0;
    public static int RC_JOURNAL = -5;
    public static int RC_REENVIAR = -6;
    public static int RC_ERRORCONSULTA = -7;
    public String soap = "";
    
    protected long canal;
    
    protected boolean recargaMovil = false;
    
    public boolean isRecargaMovil() {
		return recargaMovil;
	}

	public void setRecargaMovil(boolean recargaMovil) {
		this.recargaMovil = recargaMovil;
	}

	protected boolean recargaFija = false;
    
    public boolean isRecargaFija() {
		return recargaFija;
	}

	public void setRecargaFija(boolean recargaFija) {
		this.recargaFija = recargaFija;
	}
	
	/**
     * @return
     */
    public Datos getDatos() {
        return datos;
    }
    
    /**
     * @param numeroOperacion
     * @return
     */
    public Oper recover( String numeroOperacion ) {
        /**
         * recupera desde el servidor o la cola SAF una operación generando un OpTRV o un OpAdmin
         * 
         */
        return null;
    }
    
    /**
     * 
     */
    public void reversar() {        
    }
    
    /**
     * 
     */
    public int anular() {
        return 0;
    }
    
    public int enviarOnline( ICajaView vista ){
        ParamSet pList = Base.getParamSet( "posDat" );
        ParamSet pSet = Base.getParamSet( "posCfg" );    
        
        this.agencia = pList.getStringValue("Agencia");
        this.entidad = pList.getStringValue("Entidad");
        // TODO se setea el Usuario como tal
        this.usuario = pList.getStringValue("Usuario");
        // Se setea Codigo de Recaudador como recaudador..
        this.recaudador = pList.getStringValue("CodigoRecaudador");
        this.caja = pList.getStringValue("Caja");        
        this.cajero = pList.getStringValue("Cajero");
        this.fecha = Tools.getFecha();
        
        this.setNumeroOperacion(numeroOperacion);       
        
        this.canal = new Long(pList.getStringValue("Canal"));
        
        //int rc = this.processOperTRV((OperTRV)this);
        String ret = this.processOperTRV((OperTRV)this);
        String[] r = ret.split("&");
        
        Base.logger.info("r[0]: "+r[0]);
        Base.logger.info("r[1]: "+r[1]);
        
        // TODO cbriones: se setea en -100 para pruebas de SAF !!!!!
       // r[0] = "-100";
        
        if (Integer.parseInt(r[0]) == -100){ // Se debe solicitar el reenvio de la operacion..
       	 	Base.logger.info("Error de Comunicacion... Debe generar SAF");
	       	 if( vista.showMyConfirmDialog("Confirme por favor","Error en envío de pago\nDesea reintentar el envío?")
	                 == JOptionPane.NO_OPTION  ) {
	             return RC_CANCELAR;
	         }
	         else{
	             return RC_REINTENTAR;
	        	 
	         }
        }else if (Integer.parseInt(r[0]) == 0){	//Todo correcto, pasamos a la impresión de los comprobantes
           Base.logger.info("Pago ejecutado correctamente");
           return RC_OK;     	 
        }else if (Integer.parseInt(r[0]) < 0){ // Error de Negocio, No se grabo el Pago, por lo que no debe generar SAF !!
        	 Base.logger.info("Error arrojado por App de Control: "+r[0]+" No debe generar SAF..");
        	 JOptionPane.showMessageDialog(null, "Error en el envío del pago: "+r[1], "Continuar", JOptionPane.INFORMATION_MESSAGE);
        	 //return RC_CANCELAR;
        	 return RC_REVERSAR;
        }
        
        /** TODO cbriones: Implementacion anterior !!
        switch( rc ){
            case -100:
                //Si fallo la conexión ofrecemos reintentar el envió
                Base.logger.info("Error de conexion");
                if( vista.showMyConfirmDialog("Confirme por favor","Error en envío de pago\nDesea reintentar el envió?")
                        == JOptionPane.NO_OPTION  ) {
                    return RC_CANCELAR;
                }
                else{
                    return RC_REINTENTAR;
                }
                
            case Servicio.RC_OK:
                //Todo correcto, pasamos a la impresión de los comprobantes
                Base.logger.info("Pago ejecutado correctamente");
                return RC_OK;                 
        }
        */
        
        return -1;
    }
    
    
    
    public int enviarOffline( ICajaView vista ){
        ParamSet pList = Base.getParamSet( "posDat" );
        ParamSet pSet = Base.getParamSet( "posCfg" );    
        
        this.agencia = pList.getStringValue("Agencia");
        this.entidad = pList.getStringValue("Entidad");
        // TODO se setea el Usuario como tal
        this.usuario = pList.getStringValue("Usuario");
        // Se setea Codigo de Recaudador como recaudador..
        this.recaudador = pList.getStringValue("CodigoRecaudador");
        this.caja = pList.getStringValue("Caja");        
        this.cajero = pList.getStringValue("Cajero");
        this.fecha = Tools.getFecha();
        
        this.setNumeroOperacion(numeroOperacion);       
        
        this.canal = new Long(pList.getStringValue("Canal"));
        
        //int rc = this.processOperTRV((OperTRV)this);
        String ret = this.processOperTRVOffline((OperTRV)this);
        String[] r = ret.split("&");
        
        Base.logger.info("r[0]: "+r[0]);
        Base.logger.info("r[1]: "+r[1]);
        
        // TODO cbriones: se setea en -100 para pruebas de SAF !!!!!
       // r[0] = "-100";
        
        if (Integer.parseInt(r[0]) == -100){ // Se debe solicitar el reenvio de la operacion..
       	 	Base.logger.info("Error de Comunicacion... Debe generar SAF");
	       	 if( vista.showMyConfirmDialog("Confirme por favor","Error en envío de pago\nDesea reintentar el envío?")
	                 == JOptionPane.NO_OPTION  ) {
	             return RC_CANCELAR;
	         }
	         else{
	             return RC_REINTENTAR;
	        	 
	         }
        }else if (Integer.parseInt(r[0]) == 0){	//Todo correcto, pasamos a la impresión de los comprobantes
           Base.logger.info("Pago ejecutado correctamente");
           return RC_OK;     	 
        }else if (Integer.parseInt(r[0]) < 0){ // Error de Negocio, No se grabo el Pago, por lo que no debe generar SAF !!
        	 Base.logger.info("Error arrojado por App de Control: "+r[0]+" No debe generar SAF..");
        	 JOptionPane.showMessageDialog(null, "Error en el envío del pago: "+r[1], "Continuar", JOptionPane.INFORMATION_MESSAGE);
        	 //return RC_CANCELAR;
        	 return RC_REVERSAR;
        }
        
        
        return -1;
    }
    
private String processOperTRVOffline(OperTRV operTRV){ 	
    	
        Caja caja = new Caja();
        String data [] = new String[10]; 
        Operacion opIn = new Operacion();
        String numeroPcs="";
       
        
    	//hIn.setAgencia(Integer.parseInt(this.getAgencia()));
    	caja.setAgencia(this.getAgencia());
    	
    	Base.logger.info( "Agencia: " + caja.getAgencia());    
		caja.setIdCaja(Integer.parseInt(this.getCaja()));
		
		Base.logger.info( "Caja: " + caja.getIdCaja());    
		caja.setEntidad(this.getEntidad());
		Base.logger.info( "Entidad: " + caja.getEntidad());   
		
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setCodigoSesion(Long.parseLong(this.getSesion()));
		Base.logger.info( "Session: " + caja.getCodigoSesion()); 
		
		caja.setUsuario(this.getCajero());
		Base.logger.info( "Usuario: " + caja.getUsuario()); 
		
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setRecaudador(this.getRecaudador());
		Base.logger.info( "Recaudador: " + caja.getRecaudador()); 
		
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setCanal(new Long(this.getCanal()).intValue());
		Base.logger.info( "Canal: " + caja.getCanal()); 
		
		opIn.setCaja(caja);
		
		opIn.setFechaPago(Tools.getFecha());
		Base.logger.info( "Fecha: " + opIn.getFechaPago()); 
		
		
		if(operTRV.getCarroCompras().getMontoTotal() < 0){

			opIn.setMonto(-1*operTRV.getCarroCompras().getMontoTotal());
		}
		else{

			opIn.setMonto(operTRV.getCarroCompras().getMontoTotal());
		}
		
		Base.logger.info( "Monto: " + opIn.getMonto()); 
		
		//operCaja.setNumeroOperacion(numeroOperacion);
		opIn.setNumeroOperacion(numeroOperacion);
		Base.logger.info( "Nro operacion: " + opIn.getNumeroOperacion()); 
		
		//operCaja.setTipoOperacion("1");
		opIn.setTipoOperacion(11); // validar que tipo de operacion debe ser ??
		
		//operCaja.setCodigoOperacion("1");
		// DTO Operacion no posee Codigo de Operacion !! 
		
	
		//Si es edición incluimos el numero de operación original.
		
		if(Base.getEdicion()){
            //operCaja.setTipoOperacion("3");
            opIn.setTipoOperacion(8); // TODO para claro la Edicion de Pago corresponde a Operacion = 8
            
            // Se setea el nro de Operacion para la Edicion del Pago.
            opIn.setNumEdicionPago(this.numeroOperacionEdicion+"");
    		Base.logger.info( "Nro Operacion Edicion Pago: " + opIn.getNumEdicionPago()); 
    		
            //operCaja.setCodigoOperacion("3");
            // DTO Operacion no posee Codigo de Operacion !! 
        }
		else if (this.isDevolucion()){
			//operCaja.setTipoOperacion("4");
			opIn.setTipoOperacion(4);
			//operCaja.setCodigoOperacion("4");
			// DTO Operacion no posee Codigo de Operacion !! 
		}
		else if (this.isRecarga()){
			// TODO validar cual es el Tipo Operacion para Recarga !!
			opIn.setTipoOperacion(5);
		}
		
		//oIn.setOperacion(operCaja);
	
		//TransaccionCaja []trxs = null;
		Transaccion []trxsClaro = null;
		
		int init = 0;
		
		if(Base.getEdicion()){
			//trxs = new TransaccionCaja[operTRV.getCarroCompras().getDocumentos().size() + 1];
			trxsClaro = new Transaccion[operTRV.getCarroCompras().getDocumentos().size() + 1];
			
			//trxs[0] = new TransaccionCaja();
			trxsClaro[0] = new Transaccion();
			
    		//trxs[0].setTipoTransaccion("Anulacion");
    		trxsClaro[0].setTipoTransaccion("Anulacion");
    		
    		//trxs[0].setNroOperacionAReversar(this.numeroOperacionOriginal);
    		trxsClaro[0].setNroOperacionAReversar(this.numeroOperacionOriginal);
    		//Ingreso id tipo de anulacion en el campo monto(NO CAMBIAR!!!)
    		trxsClaro[0].setMonto(Long.valueOf(1));
    		    		
    		trxsClaro[0].setOrigen("");
    		
    		Base.logger.info( "Tipo Operacion: " + opIn.getTipoOperacion()); 
			
			Base.logger.info( "Trx Edic (Cta Clte): " + trxsClaro[0].getCuentaCliente()); 
			Base.logger.info( "Trx Edic (Fcha venc): " + trxsClaro[0].getFechaVencimiento()); 
			Base.logger.info( "Trx Edic (Monto): " + trxsClaro[0].getMonto()); 
			Base.logger.info( "Trx Edic (Origen): " + trxsClaro[0].getOrigen()); 
			Base.logger.info( "Trx Edic (Servicio): " + trxsClaro[0].getServicio()); 
			Base.logger.info( "Trx Edic (Tipo Doc): " + trxsClaro[0].getTipoDocumento()); 
			Base.logger.info( "Trx Edic (Tipo Reg): " + trxsClaro[0].getTipoRegistro()); 
			Base.logger.info( "Trx Edic (Tipo Trx): " + trxsClaro[0].getTipoTransaccion()); 
			Base.logger.info( "Trx Edic (Nro. Doc): " + trxsClaro[0].getNumeroDocumento()); 
			Base.logger.info( "Trx Edic (Rut)    : " + trxsClaro[0].getRut()); 
			Base.logger.info( "Trx Edic (Dv)    : " + trxsClaro[0].getDv()); 
			Base.logger.info( "Trx Edic (Cod. Emp) : " + trxsClaro[0].getEmpresa()); 
			Base.logger.info( "Trx Edic (Nro. Op. Reversar) : " + trxsClaro[0].getNroOperacionAReversar());
			 
    		init = 1;
		}
		else{
			//trxs = new TransaccionCaja[operTRV.getCarroCompras().getDocumentos().size()];
			trxsClaro = new Transaccion[operTRV.getCarroCompras().getDocumentos().size()];
		}
		
		int pos = 0;
		
		//for(int i = init ; i < trxs.length ; i++){ 
		for(int i = init ; i < trxsClaro.length ; i++){ 
			
			DocumentoPago aux = operTRV.getCarroCompras().getDocument(pos);
			numeroPcs = aux.getDatos().getStringValue("TelefonoContacto");
			
			//trxs[i] = new TransaccionCaja();			
			trxsClaro[i] = new Transaccion();
			
			if(aux.getDatos().getStringValue("Tipo").equals("DocumentoCuentaVtr")){
				//((DocumentoCuentaVtr)aux).llenarTrx(trxs[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoServicioVtr")){
				//((DocumentoServicioVtr)aux).llenarTrx(trxs[i]);				
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoVtr")){
				//((DocumentoVtr)aux).llenarTrx(trxs[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoDevolucionClaro")){
				((DocumentoDevolucionClaro)aux).llenarTrx(trxsClaro[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAbonoClaro")){
				((DocumentoAbonoClaro)aux).llenarTrx(trxsClaro[i]);
			}
			// se agrega documento de Cuenta Claro
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoCuentaClaro")){
				((DocumentoCuentaClaro)aux).llenarTrx(trxsClaro[i]);
				// cbriones: se valida que si es recarga, el Monto debe ser el del carro, no existe Doc con deuda !!!
				if(this.isRecarga()){
					aux.setMonto(opIn.getMonto());
				}
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoItemClaro")){
				((DocumentoItemClaro)aux).llenarTrxClaro(trxsClaro[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoRecargaClaro")){
				((DocumentoRecargaClaro)aux).llenarTrxClaro(trxsClaro[i]);
			}
			// se agrega documento para Pago de Accesorios
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAccesorioClaro")){
				Base.logger.info("Entro a setear la trx para Accesorio Claro");
				((DocumentoAccesorioClaro)aux).llenarTrxClaro(trxsClaro[i]);
			}
			else{ // validar si por default debe llenar Trx Claro !!
				//aux.llenarTrx(trxs[i]);
				aux.llenarTrxClaro(trxsClaro[i]);
				if(this.isRecarga()){
					aux.setMonto(opIn.getMonto());
				}
			}
			
			if(this.isDevolucion()){
				//trxs[i].setTipoTransaccion("PagoDevolucion");
				trxsClaro[i].setTipoTransaccion("Devolucion");
			}
			else if(this.isRecarga()){
				// TODO validar Tipo de Trx para recarga !!
				trxsClaro[i].setTipoTransaccion("Recarga");
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoItemClaro")){
				trxsClaro[i].setTipoTransaccion("PagoItem");
			// TODO validar que debe enviarse en Tipo Transaccion y Tipo operacion para pago Accesorio ??? 
			}else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAccesorioClaro")){
				    Base.logger.info("Entro a setear tipo Transaccion PagoAccesorio");
					trxsClaro[i].setTipoTransaccion("PagoAccesorio");
					//opIn.setTipoOperacion(7); // Que tipo de operacion sera ???
		    }else{
				// Se setea por defecto Pago de deuda !!
				trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}
			
			if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAbonoClaro")){
				trxsClaro[i].setTipoTransaccion("PagoAbono");
				// Se define con Codigo 7 el Pago de un Abono a Cuenta
				// El Pago Abono debe ser Operacion de Pago solamente, no utiliza codigo 7 !!!
				//opIn.setTipoOperacion(1);				
			} else if("SC".equalsIgnoreCase(aux.getDatos().getStringValue("TipoRegistroClaro"))){
				// TODO Segun nueva definicion (REspinoza), la operacion corresponde a un Pago Deuda
				//MOrtuzar: La operacion vuelve a ser PagoSaldoCastigado.
				trxsClaro[i].setTipoTransaccion("PagoSaldoCastigado");
				//trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}else if("ST".equalsIgnoreCase(aux.getDatos().getStringValue("TipoRegistroClaro"))){
				// TODO Segun nueva definicion (REspinoza), la operacion corresponde a un Pago Deuda
				//MOrtuzar: Se vuelve a definir como PagoLimiteCredito
				trxsClaro[i].setTipoTransaccion("PagoLimiteCredito");
				//trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}else if("CH".equalsIgnoreCase(aux.getDatos().getStringValue("TipoRegistroClaro"))){
				// TODO Segun nueva definicion (REspinoza), la operacion corresponde a un Pago Deuda
				//trxsClaro[i].setTipoTransaccion("PagoChequeProtestado");
				trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}else if("PagoDeudaNV".equalsIgnoreCase(aux.getDatos().getStringValue("TipoTrx"))){
				// TODO Segun nueva definicion de nota venta (Nsoto)
				trxsClaro[i].setTipoTransaccion("PagoDeudaNV");
			}
			/**
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoServicioVtr")){
				if(aux.getDatos().getStringValue("Cut") != null && aux.getDatos().getStringValue("Cut").equals("si")){
					trxsClaro[i].setTipoTransaccion("PagoDeudaCut");
				}
			}
			*/
			
			Base.logger.info( "Tipo Operacion: " + opIn.getTipoOperacion()); 
			
			Base.logger.info( "Trx "+i+" (Cta Clte): " + trxsClaro[i].getCuentaCliente()); 
			Base.logger.info( "Trx "+i+" (Fcha venc): " + trxsClaro[i].getFechaVencimiento()); 
			Base.logger.info( "Trx "+i+" (Monto): " + trxsClaro[i].getMonto()); 
			Base.logger.info( "Trx "+i+" (Origen): " + trxsClaro[i].getOrigen()); 
			Base.logger.info( "Trx "+i+" (Servicio): " + trxsClaro[i].getServicio()); 
			Base.logger.info( "Trx "+i+" (Tipo Doc): " + trxsClaro[i].getTipoDocumento()); 
			Base.logger.info( "Trx "+i+" (Tipo Reg): " + trxsClaro[i].getTipoRegistro()); 
			Base.logger.info( "Trx "+i+" (Tipo Trx): " + trxsClaro[i].getTipoTransaccion()); 
			Base.logger.info( "Trx "+i+" (Nro. Doc): " + trxsClaro[i].getNumeroDocumento()); 
			Base.logger.info( "Trx "+i+" (Rut)    : " + trxsClaro[i].getRut()); 
			Base.logger.info( "Trx "+i+" (Dv)    : " + trxsClaro[i].getDv()); 
			Base.logger.info( "Trx "+i+" (Cod. Emp) : " + trxsClaro[i].getEmpresa()); 
			Base.logger.info( "Trx "+i+" ( Nro. Op. Reversar --> Item) : " + trxsClaro[i].getNroOperacionAReversar());
			 
			pos++;
		}
		
		//oIn.setTransacciones(trxs);
		opIn.setTransaccion(trxsClaro);
		
		
		
		//MedioPagoCaja []pagos = new MedioPagoCaja[operTRV.getCarroMediosPago().getMediosPago().size()];
		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago []pagosClaro = new cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[operTRV.getCarroMediosPago().getMediosPago().size()];
		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mpEfectivo = null;
		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mpAjuste = null;
		
		//for(int i = 0 ; i < pagos.length ; i++){
		for(int i = 0 ; i < pagosClaro.length ; i++){
			Base.logger.info( "Recorriendo carro para medios de Pago"); 
			// Este medio de pago es del Core !!
			MedioPago aux = operTRV.getCarroMediosPago().getPago(i);
			Base.logger.info( "MP aux (Monto): " + aux.getMonto());
			Base.logger.info( "MP aux (Nombre): " + aux.getNombre());
			
			//pagos[i] = new MedioPagoCaja();
			pagosClaro[i] = new cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago();
			
			//Tools.initMP(pagos[i]);
			Tools.initMPClaro(pagosClaro[i]);
			
			if(aux.getNombre().equals("Efectivo")){
				if(this.isDevolucion()){
					//((Efectivo)aux).llenarMPDevolucion(pagos[i]);
					((Efectivo)aux).llenarMPDevolucionClaro(pagosClaro[i]);
				}
				else{
					//((Efectivo)aux).llenarMP(pagos[i]);
					((Efectivo)aux).llenarMPClaro(pagosClaro[i]);
					
				}
				mpEfectivo = pagosClaro[i];
			}
			if(aux.getNombre().equals("AjusteSencillo")){
				((AjusteSencillo)aux).llenarMPClaro(pagosClaro[i]);
				mpAjuste = pagosClaro[i];
			}
			else if(aux.getNombre().equals("OtroMedioPago")){
				//((Cheque)aux).llenarMP(pagos[i]);
				((OtroMedioPago)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("Cheque")){
				//((Cheque)aux).llenarMP(pagos[i]);
				((ChequeOffline)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("ChequeFecha")){
				//((ChequeFecha)aux).llenarMP(pagos[i]);
				((ChequeFecha)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TarjetaTbkManual")){				
				//((TarjetaManual)aux).llenarMP(pagos[i]);
				//((TarjetaManual)aux).llenarMPClaro(pagosClaro[i]);
				((TbkManual)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TarjetaManual")){				
				//((TarjetaManual)aux).llenarMP(pagos[i]);
				((TarjetaManual)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("Tarjeta")){				
				((Tarjeta)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TarjetaTbkDirecto")){				
				((TrxTarjetaTbkDirecto)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("ValeVista")){				
				((ValeVista)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("DepositoEfectivo")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("DepositoCheque")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("DepositoValeVista")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TransferenciaElectronica")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}else if(aux.getNombre().equals("PapeletaDeposito")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			
			
			Base.logger.info( "MP["+i+"] (Cod. Autoriz.): " + pagosClaro[i].getCodigoAutorizacion()); 
			Base.logger.info( "MP["+i+"] (Fcha venc): " + pagosClaro[i].getFechaVencimiento()); 
			Base.logger.info( "MP["+i+"] (Monto): " + pagosClaro[i].getMonto()); 
			Base.logger.info( "MP["+i+"] (Tipo Trx): " + pagosClaro[i].getTipoTransaccion()); 
			Base.logger.info( "MP["+i+"] (Tipo Total): " + pagosClaro[i].getTipoTotal()); 
			Base.logger.info( "MP["+i+"] (Depositante): " + pagosClaro[i].getDepositante()); 
			Base.logger.info( "MP["+i+"] (Serie Vale Vista): " + pagosClaro[i].getSerieValeVista());
			Base.logger.info( "MP["+i+"] (Cod. Banco): " + pagosClaro[i].getCodigoBanco());
			Base.logger.info( "MP["+i+"] (Nro. Deposito): " + pagosClaro[i].getNumeroDeposito());
			
		}
		
		//se modifican medios pago claro para agregar ajuste de sencillo. se pone (-) dado que el ajuste es redondeado
		if(mpEfectivo != null && mpAjuste != null){
			mpEfectivo.setMonto(mpEfectivo.getMonto() - mpAjuste.getMonto());
		}
		
		//oIn.setMediosPago(pagos);			
		opIn.setMedioPago(pagosClaro);
		Base.logger.info("Se genera el SAF-Contingencia");
		Serializa.serializaOffline(opIn);
	
		
		
		ParamSet posDat = Base.getParamSet("posDat");

		if(posDat.getStringValue("TipoPagoContingencia").equals("Servicio")){
			
			data[0] =opIn.getTransaccion()[0].getOrigen();//Origen
			data[1] = String.valueOf(opIn.getTransaccion()[0].getRut());//rut
			data[2] = String.valueOf(opIn.getTransaccion()[0].getNumeroDocumento());//numdocto
			data[3] = opIn.getTransaccion()[0].getCuentaCliente();//cuenta
			data[4] = numeroPcs;//opIn.getTransaccion()[0].getServicio();//operTRV.getDatos().getStringValue("IdServicioClaro");//pcs
			data[5] = String.valueOf(opIn.getMonto()); 
			data[6] = opIn.getMedioPago()[0].getTipoTransaccion(); //Medio de Pago
			data[7] =Tools.getFecha();
			data[8] = caja.getAgencia();
			data[9] = caja.getUsuario();
			
			String linea = Tools.generaLineaCvs(data);
			ArchivoCvs.crearArchivoCvs(linea);
		}
		
		for ( cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago medioPago : opIn.getMedioPago()) {
			String lineaTotal = Tools.generaCuadratura(medioPago.getTipoTransaccion(), String.valueOf(medioPago.getMonto()));
			ArchivoCuadratura.actualizarArchivoCuadratura(lineaTotal);
		}
		
		
		posDat.setValue("TipoPagoContingencia","");
		posDat.save();
		
		return "0&[ok]";
    }
    
    /**
     * Este metodo realizan la Notificacion hacia App de Control !!
     * @param operTRV
     * @return
     */
    //private int processOperTRV(OperTRV operTRV){
    private String processOperTRV(OperTRV operTRV){
    	
    	try {
    	
        //ServerProxy pr = Proxy.getProxyInstance();
        PlataformaPagoNotificarServerProxy pr = AppControlNotificarProxy.getProxyInstance();
        
        // Necesito generar: NotifcacionEnvio
        		// Necesito generar: Operacion
        				// Necesito generar: Caja
        				// Necesito generar: Transaccion[]
        				// Necesito generar: MedioPago[]
        
        
        //HeaderIn hIn = new HeaderIn();
        Caja caja = new Caja();
        
        //OperacionIn oIn = new OperacionIn();
        Operacion opIn = new Operacion();
        
        
    	//hIn.setAgencia(Integer.parseInt(this.getAgencia()));
    	caja.setAgencia(this.getAgencia());
    	Base.logger.info( "Agencia: " + caja.getAgencia());    
		//hIn.setCajaFisica(Integer.parseInt(this.getCaja()));
		caja.setIdCaja(Integer.parseInt(this.getCaja()));
		Base.logger.info( "Caja: " + caja.getIdCaja());    
		//hIn.setEntidad(Integer.parseInt(this.getEntidad()));
		caja.setEntidad(this.getEntidad());
		Base.logger.info( "Entidad: " + caja.getEntidad());   
		
		//hIn.setCajero(Integer.parseInt(this.getCajero()));
		// No existe Cajero en DTO Caja !!
		
		//hIn.setSession(Integer.parseInt(this.getSesion()));
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setCodigoSesion(Long.parseLong(this.getSesion()));
		Base.logger.info( "Session: " + caja.getCodigoSesion()); 
		
		//hIn.setUsuario(this.getUsuario());
		caja.setUsuario(this.getCajero());
		//caja.setUsuario(this.getRecaudador());
		Base.logger.info( "Usuario: " + caja.getUsuario()); 
		
		//hIn.setRecaudador(this.getRecaudador());	
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setRecaudador(this.getRecaudador());
		Base.logger.info( "Recaudador: " + caja.getRecaudador()); 
		
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setCanal(new Long(this.getCanal()).intValue());
		//caja.setCanal(1);
		Base.logger.info( "Canal: " + caja.getCanal()); 
		
		//oIn.setHeaderIn(hIn);
		opIn.setCaja(caja);
		
		
		//OperacionCaja operCaja = new OperacionCaja();
		//operCaja.setCanal(0);
		// DTO Operacion (opIn) no posee Canal !!
		
		//operCaja.setFechaOperacion(Tools.getFecha());
		// DTO Operacion no posee fecha operacion !!
		
		//operCaja.setFechaPago(Tools.getFecha());
		opIn.setFechaPago(Tools.getFecha());
		Base.logger.info( "Fecha: " + opIn.getFechaPago()); 
		
		//operCaja.setHoraOperacion(Tools.getTime());
		// DTO Operacion no posee hora operacion !!
		
		if(operTRV.getCarroCompras().getMontoTotal() < 0){
			//operCaja.setMonto(-1*operTRV.getCarroCompras().getMontoTotal());
			opIn.setMonto(-1*operTRV.getCarroCompras().getMontoTotal());
		}
		else{
			//operCaja.setMonto(operTRV.getCarroCompras().getMontoTotal());
			opIn.setMonto(operTRV.getCarroCompras().getMontoTotal());
		}
		Base.logger.info( "Monto: " + opIn.getMonto()); 
		
		//operCaja.setNumeroOperacion(numeroOperacion);
		opIn.setNumeroOperacion(numeroOperacion);
		Base.logger.info( "Nro operacion: " + opIn.getNumeroOperacion()); 
		
		//operCaja.setTipoOperacion("1");
		opIn.setTipoOperacion(1); // validar que tipo de operacion debe ser ??
		
		//operCaja.setCodigoOperacion("1");
		// DTO Operacion no posee Codigo de Operacion !! 
		
	
		//Si es edición incluimos el numero de operación original.
		
		if(Base.getEdicion()){
            //operCaja.setTipoOperacion("3");
            opIn.setTipoOperacion(8); // TODO para claro la Edicion de Pago corresponde a Operacion = 8
            
            // Se setea el nro de Operacion para la Edicion del Pago.
            opIn.setNumEdicionPago(this.numeroOperacionEdicion+"");
    		Base.logger.info( "Nro Operacion Edicion Pago: " + opIn.getNumEdicionPago()); 
    		
            //operCaja.setCodigoOperacion("3");
            // DTO Operacion no posee Codigo de Operacion !! 
        }
		else if (this.isDevolucion()){
			//operCaja.setTipoOperacion("4");
			opIn.setTipoOperacion(4);
			//operCaja.setCodigoOperacion("4");
			// DTO Operacion no posee Codigo de Operacion !! 
		}
		else if (this.isRecarga()){
			// TODO validar cual es el Tipo Operacion para Recarga !!
			opIn.setTipoOperacion(5);
		}
		
		
		//oIn.setOperacion(operCaja);
	
		//TransaccionCaja []trxs = null;
		Transaccion []trxsClaro = null;
		
		int init = 0;
		
		if(Base.getEdicion()){
			//trxs = new TransaccionCaja[operTRV.getCarroCompras().getDocumentos().size() + 1];
			trxsClaro = new Transaccion[operTRV.getCarroCompras().getDocumentos().size() + 1];
			
			//trxs[0] = new TransaccionCaja();
			trxsClaro[0] = new Transaccion();
			
    		//trxs[0].setTipoTransaccion("Anulacion");
    		trxsClaro[0].setTipoTransaccion("Anulacion");
    		
    		//trxs[0].setNroOperacionAReversar(this.numeroOperacionOriginal);
    		trxsClaro[0].setNroOperacionAReversar(this.numeroOperacionOriginal);
    		
    		//Ingreso id tipo de anulacion en el campo monto(NO CAMBIAR!!!)
    		trxsClaro[0].setMonto(Long.valueOf(1));
    		
    		trxsClaro[0].setOrigen("");
    		
    		Base.logger.info( "Tipo Operacion: " + opIn.getTipoOperacion()); 
			
			Base.logger.info( "Trx Edic (Cta Clte): " + trxsClaro[0].getCuentaCliente()); 
			Base.logger.info( "Trx Edic (Fcha venc): " + trxsClaro[0].getFechaVencimiento()); 
			Base.logger.info( "Trx Edic (Monto): " + trxsClaro[0].getMonto()); 
			Base.logger.info( "Trx Edic (Origen): " + trxsClaro[0].getOrigen()); 
			Base.logger.info( "Trx Edic (Servicio): " + trxsClaro[0].getServicio()); 
			Base.logger.info( "Trx Edic (Tipo Doc): " + trxsClaro[0].getTipoDocumento()); 
			Base.logger.info( "Trx Edic (Tipo Reg): " + trxsClaro[0].getTipoRegistro()); 
			Base.logger.info( "Trx Edic (Tipo Trx): " + trxsClaro[0].getTipoTransaccion()); 
			Base.logger.info( "Trx Edic (Nro. Doc): " + trxsClaro[0].getNumeroDocumento()); 
			Base.logger.info( "Trx Edic (Rut)    : " + trxsClaro[0].getRut()); 
			Base.logger.info( "Trx Edic (Dv)    : " + trxsClaro[0].getDv()); 
			Base.logger.info( "Trx Edic (Cod. Emp) : " + trxsClaro[0].getEmpresa()); 
			Base.logger.info( "Trx Edic (Nro. Op. Reversar) : " + trxsClaro[0].getNroOperacionAReversar());
			 
    		init = 1;
		}
		else{
			//trxs = new TransaccionCaja[operTRV.getCarroCompras().getDocumentos().size()];
			trxsClaro = new Transaccion[operTRV.getCarroCompras().getDocumentos().size()];
		}
		
		int pos = 0;
		
		//for(int i = init ; i < trxs.length ; i++){ 
		for(int i = init ; i < trxsClaro.length ; i++){ 
			
			DocumentoPago aux = operTRV.getCarroCompras().getDocument(pos);
			//trxs[i] = new TransaccionCaja();			
			trxsClaro[i] = new Transaccion();
			
			if(aux.getDatos().getStringValue("Tipo").equals("DocumentoCuentaVtr")){
				//((DocumentoCuentaVtr)aux).llenarTrx(trxs[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoServicioVtr")){
				//((DocumentoServicioVtr)aux).llenarTrx(trxs[i]);				
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoVtr")){
				//((DocumentoVtr)aux).llenarTrx(trxs[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoDevolucionClaro")){
				((DocumentoDevolucionClaro)aux).llenarTrx(trxsClaro[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAbonoClaro")){
				((DocumentoCuentaClaro)aux).llenarTrx(trxsClaro[i]);
			}
			//REspinoza
			else if (aux.getDatos().getStringValue("Tipo").equals("DocumentoAbonoFijoClaro")) {
				((DocumentoCuentaClaro)aux).llenarFijoTrx(trxsClaro[i]);
			}
			// se agrega documento de Cuenta Claro
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoCuentaClaro")){
				((DocumentoCuentaClaro)aux).llenarTrx(trxsClaro[i]);
				// cbriones: se valida que si es recarga, el Monto debe ser el del carro, no existe Doc con deuda !!!
				if(this.isRecarga()){
					aux.setMonto(opIn.getMonto());
				}
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoItemClaro")){
				((DocumentoItemClaro)aux).llenarTrxClaro(trxsClaro[i]);
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoRecargaClaro")){
				((DocumentoRecargaClaro)aux).llenarTrxClaro(trxsClaro[i]);
			}
			// se agrega documento para Pago de Accesorios
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAccesorioClaro")){
				Base.logger.info("Entro a setear la trx para Accesorio Claro");
				((DocumentoAccesorioClaro)aux).llenarTrxClaro(trxsClaro[i]);
			}
			else{ // validar si por default debe llenar Trx Claro !!
				//aux.llenarTrx(trxs[i]);
				aux.llenarTrxClaro(trxsClaro[i]);
				if(this.isRecarga()){
					aux.setMonto(opIn.getMonto());
				}
			}
			
			if(this.isDevolucion()){
				//trxs[i].setTipoTransaccion("PagoDevolucion");
				trxsClaro[i].setTipoTransaccion("Devolucion");
			}
			else if(this.isRecarga()){
				// TODO validar Tipo de Trx para recarga !!
				trxsClaro[i].setTipoTransaccion("Recarga");
			}
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoItemClaro")){
				trxsClaro[i].setTipoTransaccion("PagoItem");
			// TODO validar que debe enviarse en Tipo Transaccion y Tipo operacion para pago Accesorio ??? 
			}else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAccesorioClaro")){
				    Base.logger.info("Entro a setear tipo Transaccion PagoAccesorio");
					trxsClaro[i].setTipoTransaccion("PagoAccesorio");
					//opIn.setTipoOperacion(7); // Que tipo de operacion sera ???
		    }else{
				// Se setea por defecto Pago de deuda !!
				trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}
			
			if(aux.getDatos().getStringValue("Tipo").equals("DocumentoAbonoClaro")){
				trxsClaro[i].setTipoTransaccion("PagoAbono");
				// Se define con Codigo 7 el Pago de un Abono a Cuenta
				// El Pago Abono debe ser Operacion de Pago solamente, no utiliza codigo 7 !!!
				//opIn.setTipoOperacion(1);
			//REspinoza
			} else if (aux.getDatos().getStringValue("Tipo").equals("DocumentoAbonoFijoClaro")){
						//(Nsoto) Diferenciación de Abono
						trxsClaro[i].setTipoTransaccion(aux.getDatos().getStringValue("TipoAbono"));
				
			} else if("SC".equalsIgnoreCase(aux.getDatos().getStringValue("TipoRegistroClaro"))){
				// TODO Segun nueva definicion (REspinoza), la operacion corresponde a un Pago Deuda
				//MOrtuzar: La operacion vuelve a ser PagoSaldoCastigado.
				trxsClaro[i].setTipoTransaccion("PagoSaldoCastigado");
				//trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}else if("ST".equalsIgnoreCase(aux.getDatos().getStringValue("TipoRegistroClaro"))){
				// TODO Segun nueva definicion (REspinoza), la operacion corresponde a un Pago Deuda
				//MOrtuzar: Se vuelve a definir como PagoLimiteCredito
				trxsClaro[i].setTipoTransaccion("PagoLimiteCredito");
				//trxsClaro[i].setTipoTransaccion("PagoDeuda");
			}else if("CH".equalsIgnoreCase(aux.getDatos().getStringValue("TipoRegistroClaro"))){
				// TODO Segun nueva definicion (REspinoza), la operacion corresponde a un Pago Deuda
				//trxsClaro[i].setTipoTransaccion("PagoChequeProtestado");
				trxsClaro[i].setTipoTransaccion("PagoDeuda");
				
			}else if("PagoDeudaNV".equalsIgnoreCase(aux.getDatos().getStringValue("TipoTrx"))){
				// TODO Segun nueva definicion de nota venta (Nsoto)
				trxsClaro[i].setTipoTransaccion("PagoDeudaNV");
			}
			
			/**
			else if(aux.getDatos().getStringValue("Tipo").equals("DocumentoServicioVtr")){
				if(aux.getDatos().getStringValue("Cut") != null && aux.getDatos().getStringValue("Cut").equals("si")){
					trxsClaro[i].setTipoTransaccion("PagoDeudaCut");
				}
			}
			*/
			
			Base.logger.info( "Tipo Operacion: " + opIn.getTipoOperacion()); 
			
			Base.logger.info( "Trx "+i+" (Cta Clte): " + trxsClaro[i].getCuentaCliente()); 
			Base.logger.info( "Trx "+i+" (Fcha venc): " + trxsClaro[i].getFechaVencimiento()); 
			Base.logger.info( "Trx "+i+" (Monto): " + trxsClaro[i].getMonto()); 
			Base.logger.info( "Trx "+i+" (Origen): " + trxsClaro[i].getOrigen()); 
			Base.logger.info( "Trx "+i+" (Servicio): " + trxsClaro[i].getServicio()); 
			Base.logger.info( "Trx "+i+" (Tipo Doc): " + trxsClaro[i].getTipoDocumento()); 
			Base.logger.info( "Trx "+i+" (Tipo Reg): " + trxsClaro[i].getTipoRegistro()); 
			Base.logger.info( "Trx "+i+" (Tipo Trx): " + trxsClaro[i].getTipoTransaccion()); 
			Base.logger.info( "Trx "+i+" (Nro. Doc): " + trxsClaro[i].getNumeroDocumento()); 
			Base.logger.info( "Trx "+i+" (Rut)    : " + trxsClaro[i].getRut()); 
			Base.logger.info( "Trx "+i+" (Dv)    : " + trxsClaro[i].getDv()); 
			Base.logger.info( "Trx "+i+" (Cod. Emp) : " + trxsClaro[i].getEmpresa()); 
			Base.logger.info( "Trx "+i+" ( Nro. Op. Reversar --> Item) : " + trxsClaro[i].getNroOperacionAReversar());
			 
			pos++;
		}
		
		//oIn.setTransacciones(trxs);
		opIn.setTransaccion(trxsClaro);
		
		//MedioPagoCaja []pagos = new MedioPagoCaja[operTRV.getCarroMediosPago().getMediosPago().size()];
		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago []pagosClaro = new cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[operTRV.getCarroMediosPago().getMediosPago().size()];
		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mpEfectivo = null;
		cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mpAjuste = null;
		
		//for(int i = 0 ; i < pagos.length ; i++){
		for(int i = 0 ; i < pagosClaro.length ; i++){
			Base.logger.info( "Recorriendo carro para medios de Pago"); 
			// Este medio de pago es del Core !!
			MedioPago aux = operTRV.getCarroMediosPago().getPago(i);
			Base.logger.info( "MP aux (Monto): " + aux.getMonto());
			Base.logger.info( "MP aux (Nombre): " + aux.getNombre());
			
			//pagos[i] = new MedioPagoCaja();
			pagosClaro[i] = new cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago();
			
			//Tools.initMP(pagos[i]);
			Tools.initMPClaro(pagosClaro[i]);
			
			if(aux.getNombre().equals("Efectivo")){
				if(this.isDevolucion()){
					//((Efectivo)aux).llenarMPDevolucion(pagos[i]);
					((Efectivo)aux).llenarMPDevolucionClaro(pagosClaro[i]);
				}
				else{
					//((Efectivo)aux).llenarMP(pagos[i]);
					((Efectivo)aux).llenarMPClaro(pagosClaro[i]);
					
				}
				mpEfectivo = pagosClaro[i];
			}

			if(aux.getNombre().equals("AjusteSencillo")){
				((AjusteSencillo)aux).llenarMPClaro(pagosClaro[i]);
				mpAjuste = pagosClaro[i];
			}
			else if(aux.getNombre().equals("OtroMedioPago")){
				//((Cheque)aux).llenarMP(pagos[i]);
				((OtroMedioPago)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("Cheque")){
				//((Cheque)aux).llenarMP(pagos[i]);
				((Cheque)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("ChequeFecha")){
				//((ChequeFecha)aux).llenarMP(pagos[i]);
				((ChequeFecha)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TarjetaTbkManual")){				
				//((TarjetaManual)aux).llenarMP(pagos[i]);
				//((TarjetaManual)aux).llenarMPClaro(pagosClaro[i]);
				((TbkManual)aux).llenarMPClaro(pagosClaro[i]);
			}else if(aux.getNombre().equals("TarjetaTbkManualDirecto")){				
				//((TarjetaManual)aux).llenarMP(pagos[i]);
				//((TarjetaManual)aux).llenarMPClaro(pagosClaro[i]);
				((TrxTarjetaTbkManualDirecto)aux).llenarMP(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TarjetaManual")){				
				//((TarjetaManual)aux).llenarMP(pagos[i]);
				((TarjetaManual)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("Tarjeta")){				
				((Tarjeta)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TarjetaTbkDirecto")){				
				((TrxTarjetaTbkDirecto)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("ValeVista")){				
				((ValeVista)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("DepositoEfectivo")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("DepositoCheque")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("DepositoValeVista")){				
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("TransferenciaElectronica")){
				pagosClaro[i].setTipoTotal("41");
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			else if(aux.getNombre().equals("PapeletaDeposito")){				
				pagosClaro[i].setTipoTotal("9");
				((Deposito)aux).llenarMPClaro(pagosClaro[i]);
			}
			
			
			Base.logger.info( "MP["+i+"] (Cod. Autoriz.): " + pagosClaro[i].getCodigoAutorizacion()); 
			Base.logger.info( "MP["+i+"] (Fcha venc): " + pagosClaro[i].getFechaVencimiento()); 
			Base.logger.info( "MP["+i+"] (Monto): " + pagosClaro[i].getMonto()); 
			Base.logger.info( "MP["+i+"] (Tipo Trx): " + pagosClaro[i].getTipoTransaccion()); 
			Base.logger.info( "MP["+i+"] (Tipo Total): " + pagosClaro[i].getTipoTotal()); 
			Base.logger.info( "MP["+i+"] (Depositante): " + pagosClaro[i].getDepositante()); 
			Base.logger.info( "MP["+i+"] (Serie Vale Vista): " + pagosClaro[i].getSerieValeVista());
			Base.logger.info( "MP["+i+"] (Cod. Banco): " + pagosClaro[i].getCodigoBanco());
			Base.logger.info( "MP["+i+"] (Nro. Deposito): " + pagosClaro[i].getNumeroDeposito());
			
		}
		
		//se modifican medios pago claro para agregar ajuste de sencillo. se pone (-) dado que el ajuste es redondeado
		if(mpEfectivo != null && mpAjuste != null){
			mpEfectivo.setMonto(mpEfectivo.getMonto() - mpAjuste.getMonto());
		}
		
		//oIn.setMediosPago(pagos);			
		opIn.setMedioPago(pagosClaro);
		
		NotificacionEnvio notif = new NotificacionEnvio();
		notif.setOperacion(opIn);
		
		//Response resp = null;
		NotificacionRespuesta resp = null;
		
		try {
			// TODO aca debe invocar a Notificar !!!
			//resp = pr.envioOperacion(oIn);
			resp = pr.notificar(notif);
			Base.logger.info("Codigo Notificacion: "+resp.getRespuesta().getRetCode());
			Base.logger.info("Msje Notificacion: "+resp.getRespuesta().getRetDesc());
			
		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
			//return -100;
			return "-100&Error de Conexion";
		}
		
		
		////////////////////////////////
		//resp.getRespuesta().setRetCode(-3);
		//////////////////////////////////
		
		
		//if(resp.getHeaderOut().getRc() != 0){
		if(resp.getRespuesta().getRetCode() != 0){	
			//Base.logger.error(resp.getHeaderOut().getRcMessage());
			Base.logger.error("Codigo Notificacion Erroneo: "+resp.getRespuesta().getRetCode());
			Base.logger.info("Msje Notificacion Erroneo: "+resp.getRespuesta().getRetDesc());
		}
		
		// TODO actualmente retorna siempre OK.
		// se reemplaza por Codigo retornado desde Consulta Notificacion !!!
		//return Servicio.RC_OK;
		return resp.getRespuesta().getRetCode()+"&"+resp.getRespuesta().getRetDesc();
		
	} catch (Exception e) {
		Tools.logStackTrace(Base.logger, e);
		return "-100&Error Notificacion";
	}
		
    }
    
    public void grabarSaf() {
        
        // se graba imagen serializada del OperTRV
        // generamos numero de operacion
    
        ParamSet pList = Base.getParamSet( "posDat" );
        ParamSet pSet = Base.getParamSet( "posCfg" );
        
        if(numeroOperacion == 0){
            numeroOperacion = pList.getLongValue( "NumeroOperacion" );        
            pList.setValue( "NumeroOperacion", numeroOperacion + 1 );
            pList.setValue("FechaPago", Tools.getFecha());
            pList.save();
        }        
        
        this.agencia = pList.getStringValue("Agencia");
        this.entidad = pList.getStringValue("Entidad");
        this.usuario = pList.getStringValue("Usuario");
        this.caja = pList.getStringValue("Caja");
        
        if(this.isRegular()){
            this.cajero = this.getCajero();
            this.fecha = this.getFecha();
        }
        else{
            this.cajero = pList.getStringValue("Cajero");
            this.fecha = Tools.getFecha();
        }
        
        this.setNumeroOperacion(numeroOperacion);
        
        try {            
            
            String s = String.format( "Oper_%s-%d", fecha, new Long( numeroOperacion ) );
            
            FileOutputStream fos = new FileOutputStream( pSet.getStringValue("OperSafDir") + s + ".dat" );
            ObjectOutputStream outStream = new ObjectOutputStream( fos );

            FileDescriptor fd = fos.getFD();
            
            outStream.writeObject( this );
            
            fos.flush();
            fd.sync();
            outStream.close();
            
            //Intentamos leer el archivo dat, si no se puede arrojamos una excepcion y terminamos la ejecución
            File outFile = null;
            ObjectInputStream in = null;
            Object o = null;
            try{
                in = new ObjectInputStream(new FileInputStream(pSet.getStringValue("OperSafDir") + s + ".dat"));
                o = in.readObject();
                in.close();
                in = null;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al grabar en disco,se cerrará la aplicación\n[" + e.getMessage()+"]\nClase: [Oper.java]", "Error", JOptionPane.ERROR_MESSAGE);
                Tools.logStackTrace(Base.logger, e);
                System.exit(1);         
            }
            
            //Todo OK continuamos para grabar el .ctl            
            FileOutputStream fctl = new FileOutputStream( pSet.getStringValue("OperSafDir") + s + ".ctl" );
            outStream = new ObjectOutputStream( fctl );
            
            fd = fctl.getFD();
            fos.flush();
            fd.sync();
            
            outStream.close();   
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar en disco\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Tools.logStackTrace(Base.logger, e);
            System.exit(1);
        }
        
    }
    
    public int executeOnline() throws BaseException {
        
        // se ejecuta la operacion en modalidad online
     
        Servicio srv = FactoryServicio.makeInstance( servicio );
        srv.getRequest().asignaPorNombre( datos );
        
        int rc = srv.execute();
        this.numeroOperacion = Long.parseLong(srv.getHeaderIn().getStringValue("NumeroOperacion"));
        
        if( rc == Servicio.RC_OK ) {
            datos = new Datos(Base.getDefServicio( this.getServicio() ).getOutputRecordDef());
            if(srv.getNombreServicio().equals("DevolucionAtis") || srv.getNombreServicio().equals("ReversaDevolucionAtis")){
                datos = new Datos();
                datos.setValue("CodOperLatam", srv.getResponse().getDatos("DocumentoDevolucionAtis").getStringValue("CodOperLatam") );
            }else
                datos.asignaPorNombre(srv.getResponse());
        }
        
        return rc;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public long getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(long numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isReversado() {
        return reversado;
    }

    public void setReversado(boolean reversado) {
        this.reversado = reversado;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getVentaDirecta() {
        return ventaDirecta;
    }

    public void setVentaDirecta(String ventaDirecta) {
        this.ventaDirecta = ventaDirecta;
    }

    public String getRecargaMovistar() {
        return recargaMovistar;
    }

    public void setRecargaMovistar(String recargaMovistar) {
        this.recargaMovistar = recargaMovistar;
    }

    public String getIsVuelto() {
        return isVuelto;
    }

    public void setIsVuelto(String isVuelto) {
        this.isVuelto = isVuelto;
    }

    public boolean isRegular() {
        return regular;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public boolean isRecarga() {
        return recarga;
    }

    public void setRecarga(boolean recarga) {
        this.recarga = recarga;
    }

	public String getSesion() {
		return sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}

	public long getNumeroOperacionReversa() {
		return numeroOperacionReversa;
	}

	public void setNumeroOperacionReversa(long numeroOperacionReversa) {
		this.numeroOperacionReversa = numeroOperacionReversa;
	}

	public long getNumeroOperacionOriginal() {
		return numeroOperacionOriginal;
	}

	public void setNumeroOperacionOriginal(long numeroOperacionOriginal) {
		this.numeroOperacionOriginal = numeroOperacionOriginal;
	}

	public long getNumeroOperacionEdicion() {
		return numeroOperacionEdicion;
	}

	public void setNumeroOperacionEdicion(long numeroOperacionEdicion) {
		this.numeroOperacionEdicion = numeroOperacionEdicion;
	}
	
	public boolean isDevolucion() {
		return devolucion;
	}

	public void setDevolucion(boolean devolucion) {
		this.devolucion = devolucion;
	}

	public String getRecaudador() {
		return recaudador;
	}

	public void setRecaudador(String recaudador) {
		this.recaudador = recaudador;
	}

	public boolean isRemesa() {
		return remesa;
	}

	public void setRemesa(boolean remesa) {
		this.remesa = remesa;
	}

	public boolean isCut() {
		return cut;
	}

	public void setCut(boolean cut) {
		this.cut = cut;
	}

	public long getCanal() {
		return canal;
	}

	public void setCanal(long canal) {
		this.canal = canal;
	}
    
    
}
