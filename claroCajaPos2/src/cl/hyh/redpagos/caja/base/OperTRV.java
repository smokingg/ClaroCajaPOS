package cl.hyh.redpagos.caja.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.xml.rpc.holders.StringHolder;

import org.example.www.ValidarRecargaWS.ValidarRecargaWSServerProxy;

//import com.ibm.xtq.bcel.generic.INSTANCEOF;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.TarjetaMultitiendaDTO;
import ws.claro.cl.proxy.AppControlProxy;
import ws.claro.cl.proxy.AppControlRecargaProxy;
import cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoProxy;
import cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.proxy.EnviarAlmacenarDocumentoProxy;
import cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSProxy;
import cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.proxy.EnviarCorreoSilverpopProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;
import cl.hyh.redpagos.caja.mpago.ChequeBase;
import cl.hyh.redpagos.caja.mpago.ChequeBaseOffline;
import cl.hyh.redpagos.caja.mpago.Efectivo;
import cl.hyh.redpagos.caja.mpago.Tarjeta;
import cl.hyh.redpagos.caja.mpago.TarjetaManual;
import cl.hyh.redpagos.caja.mpago.TarjetaPresto;
import cl.hyh.redpagos.caja.mpago.TbkManual;
import cl.hyh.redpagos.caja.trx.ReImpresion;

/**
 * Transaccion de Recaudacion y Venta. Almacena un carro de compras (con documentos de pago asociados) y un carro de medios
 * de pago con los medios de pago que se hayan usado
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class OperTRV extends Oper implements Serializable {

	private CarroCompra carroCompras = new CarroCompra();
	private CarroMPagos carroMediosPago = new CarroMPagos();
    LineaVoucher[] voucherCliente = null;
    LineaVoucher[] voucherComercio = null;
    LineaVoucher[] voucherClienteDuplicado = null;
    LineaVoucher[] voucherComercioDuplicado = null;
    LineaVoucher[] voucherPremioCliente = null;
    LineaVoucher[] voucherPremioComercio = null;
    
    LineaVoucher[] voucherClienteReimpresion = null;
    
    String[][] dataMulticard;
    ArrayList<LineaVoucher> comprobante;
    // Inicio: Pruebas para impresion Transbank
    public LineaVoucher[] getVoucherCliente() {
        LineaVoucher lV = new LineaVoucher();
        LineaVoucher[] lVo = new LineaVoucher[1];
        lV.setLinea("Pago con Tarjeta");
        lVo[0] = lV;
        return lVo;
    }
    public LineaVoucher[] getVoucherClienteDuplicado(){
    	 LineaVoucher lV = new LineaVoucher();
         LineaVoucher[] lVo = new LineaVoucher[1];
         lV.setLinea("Pago con Tarjeta");
         lVo[0] = lV;
         return lVo;
    }
    
    public LineaVoucher[] getVoucherClte() {
        return voucherCliente;
    }
    public LineaVoucher[] getVoucherClteDuplicado(){
        return voucherClienteDuplicado;
    }
    // Fin: Pruebas para impresion Transbank
    
    public LineaVoucher[] getCommerceVoucher(){
        return voucherComercio;
    	// TODO: se reemplaza por Voucher de Reimpresion con Duplicado...
        //return voucherClienteReimpresion;
    }
    
    public LineaVoucher[] getCommerceVoucherDuplicado(){
        return voucherComercioDuplicado;
    }
    public LineaVoucher[] getPremioCliente(){
        return voucherPremioCliente;
    }
    public LineaVoucher[] getPremioLocal(){
        return voucherPremioComercio;
    }
    
    public LineaVoucher[] getCommerceVoucherReimpresion() {
        return voucherClienteReimpresion;
    }
    public void generaComprobante (LineaVoucher []aux){
    	
    	comprobante = new ArrayList<LineaVoucher>();
		for(int j = 0; j < aux.length; j++){
			if(aux[j] != null) {
				comprobante.add(aux[j]);
			} else {
//				linea eliminada del voucher
			}
		}    	
    }
	/**
	 * 
	 */
	public OperTRV() {
		this.setServicio("EnvioTrv");

	}

	/**
	 * @return
	 */
	public CarroCompra getCarroCompras() {
		return carroCompras;
	}

	/**
	 * @return
	 */
	public CarroMPagos getCarroMediosPago() {
		return carroMediosPago;
	}

	public void isVueltoBoleta(){
		if(carroMediosPago.getMontoEfectivo() != 0){
			this.setIsVuelto("true");
		}
		else{
			this.setIsVuelto("false");
		}
	}

	public boolean isValid( MedioPago mPago ) {
		boolean boolMp = false;
		
		// valida que total a pagar, sea mayor o igual al monto adeudado en el carro de docs !!
		if(Math.abs(carroMediosPago.getMontoTotal()) >= Math.abs(carroCompras.getMontoTotal()) &&
				Math.abs(carroMediosPago.getMontoTotal()) > 0 && !mPago.getNombre().equals("AjusteSencillo")){
			Base.logger.info("Total Carro M. Pagos: "+carroMediosPago.getMontoTotal());
			Base.logger.info("Total Carro Documentos: "+carroCompras.getMontoTotal());
			JOptionPane.showMessageDialog(null, "Ya completo el Monto a cancelar", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// validar tarjetas transbank
		if(vailidaNTarjeta(mPago) && vailidaNDocsaNMps(mPago)){
			boolMp=true;
		}
		
		return boolMp;

		// cbriones: validar que no se suba mas de 1 Medios de Pago cuando son 
		// mas de uno los documentos que se desea cancelar.
//		if(carroCompras.getDocumentos().size() > 1 && carroMediosPago.getMediosPago().size() > 0){
//			JOptionPane.showMessageDialog(null, "No se permite ingresar mas de 1 Medio de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
//			return false;
//		}
//		return true;
	}

	private boolean permitido(MedioPago mPago) {
		if (mPago instanceof Efectivo
				|| mPago instanceof Tarjeta
				|| mPago instanceof TarjetaManual
				|| mPago instanceof TarjetaPresto
				|| mPago instanceof TbkManual
				|| mPago instanceof AjusteSencillo
				) {
			return true;
		} else {
			return false;
		} 
	}
	
	/*
	 * [REspinoza] Validar que no se use mas de una tarjeta Transbank
	 */
	private boolean vailidaNTarjeta(MedioPago mPago) {
		
		Map<String,String> mapMps = new HashMap<String,String>();
		mapMps.put("TarjetaCreditoTbk", "TarjetaCreditoTbk");
		mapMps.put("TarjetaDebitoTbk", "TarjetaDebitoTbk");
		mapMps.put("Tarjeta", "Tarjeta");
//		mapMps.put("TarjetaTbkManual", "TarjetaTbkManual");
//		mapMps.put("TarjetaManual", "TarjetaManual");
		
		int contador = 0;
		
		for (MedioPago mp : carroMediosPago.getMediosPago()) {
			if(mapMps.containsKey(mp.getNombre()) == true ) {
				contador++;
			}
		}
		
		if(contador > 0 
				&& (mPago instanceof Tarjeta
						|| mPago instanceof cl.hyh.redpagos.caja.trx.TrxTarjetaTbkDirecto )
				) {
			JOptionPane.showMessageDialog(null, "No se permite utilizar 2 medios de pago Transbank",
					"Continuar", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/*
	 * [REspinoza] Ndocs a Nmps. efectivo/debito || efectivo/credito 
	 */
	private boolean vailidaNDocsaNMps(MedioPago mPago) {
		
		Map<String,String> mapMps = new HashMap<String,String>();
		mapMps.put("Efectivo", "Efectivo");
		mapMps.put("TarjetaCreditoTbk", "TarjetaCreditoTbk");
		mapMps.put("TarjetaDebitoTbk", "TarjetaDebitoTbk");
		mapMps.put("TarjetaTbkManual", "TarjetaTbkManual");
		mapMps.put("TarjetaManual", "TarjetaManual");
		
		boolean hayEfectivo = false;
		boolean hayCheque = false;

		//validar medio de pago ingresado es tarjeta o efectivo
		if (permitido(mPago)) {
			//Tarjeta directo viene null
			if (mPago.getNombre() == null) {
				mPago.setNombre(mPago.getClass().getSimpleName());
			}
		}
		
		
		if (permitido(mPago) && carroCompras.getDocumentos().size() > 1) {
			
			if (carroMediosPago.getMediosPago().size() > 1) {
				JOptionPane.showMessageDialog(null, "Múltiples Documentos permite máximo 2 medios de pago.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			
			for (MedioPago mp : carroMediosPago.getMediosPago()) {
				
				//de los 2 medios de pago uno debe ser efectivo siempre
				if (mp.getNombre().equalsIgnoreCase("Efectivo")) {
					hayEfectivo = true;
				}
				
				//no puede repetirse el mismo medio de pago 2 veces
				if (mp.getNombre().equalsIgnoreCase(mPago.getNombre())) {
					JOptionPane.showMessageDialog(null, "Medio de pago ya utilizado.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
				
				//el medio de pago debe ser efectivo, credito o debito... ningun otro.
				if (!permitido(mp)) {
					JOptionPane.showMessageDialog(null, "N Documentos con N medios de pagos solo permitidos para Efectivo/débito " +
							" y Efectivo/crédito.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			}
			
			for (MedioPago mp : carroMediosPago.getMediosPago()) {
				if (mp.getNombre().equalsIgnoreCase("ChequeFecha")) {
					hayCheque=true;
				}
			}
			
			if (carroMediosPago.getMediosPago().size() > 0
					&& !mPago.getNombre().equalsIgnoreCase("ChequeFecha") && hayCheque) {
				JOptionPane.showMessageDialog(null, "N Documentos con N medios de pagos. Los medios de " +
								"pago deben ser Cheque Fecha", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} 
			
			//de los 2 medios de pago uno debe ser efectivo siempre
			if (carroMediosPago.getMediosPago().size() > 0 
					&& !mPago.getNombre().equalsIgnoreCase("Efectivo") &&  !hayEfectivo) {
				JOptionPane.showMessageDialog(null, "N Documentos con N medios de pagos. Uno de los medios de pago " +
						" debe ser Efectivo.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		} else if ("ChequeFecha".equalsIgnoreCase(mPago.getNombre()) == true
				&& carroCompras.getDocumentos().size() > 1) {
				for (MedioPago mp : carroMediosPago.getMediosPago()) {
					if (!mp.getNombre().equalsIgnoreCase("ChequeFecha")) {
							JOptionPane.showMessageDialog(null, "N Documentos con N medios de pagos. Los medios de " +
									"pago deben ser Cheque Fecha' ", "Continuar", JOptionPane.INFORMATION_MESSAGE);
							return false;
					}
				}
		} else {
			if (carroCompras.getDocumentos().size() > 1 && carroMediosPago.getMediosPago().size() > 0) {
				/*JOptionPane.showMessageDialog(null, "No se permite ingresar mas de "+
						carroMediosPago.getMediosPago().size()+" Medio de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);*/
				JOptionPane.showMessageDialog(null, "N Documentos con N medios de pagos solo permitidos para Efectivo/débito " +
						" y Efectivo/crédito.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		
		
		return true;
	}

	public boolean isValid( DocumentoPago docPago ) {

		// regla 1: si es recarga movistar no se permiten mas documentos en el carro de compra

		String nombre = docPago.getNombre();
		if( nombre.equals( "RecargaMovistar" ) ) {
			if( carroCompras.getDocumentos().size() > 0 )
				return false;
		}
		return true;
	}

	public void addMedioPago( MedioPago mPago ) throws BaseException { 
		if( !isValid( mPago ) )
			throw new BaseException( "medio de pago " + mPago.getNombre() + " incompatible" );

		carroMediosPago.addMedioPago( mPago );
	}

	public void addDocumentoPago( DocumentoPago docPago ) throws BaseException {


		ParamSet posCfg = Base.getParamSet("posCfg");
		ParamSet pSet = Base.getParamSet("posDat");
		
		//[REspinoza] Maxima cantidad de doc (agencia/cajaFisica) tabla tif_parametro
		int maxDocuments = 0;
		try {
			String maxDoc = pSet.getStringValue("MAXDOC");
			maxDocuments = Integer.parseInt(maxDoc);
		} catch(Exception e) {
			maxDocuments = 0;
		} finally {
			if(maxDocuments == 0) {
				maxDocuments = 30; // por defecto se deja en 30 documentos
			}
		}
		
		if( this.carroCompras.getDocumentos().size() >= maxDocuments){
			JOptionPane.showMessageDialog(null, "El pago sobrepasa la cantidad máxima de "
					+maxDocuments+" documentos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			throw new BaseException( "medio de pago " + docPago.getNombre() + " incompatible" );
		}

		if( !isValid( docPago ) )
			throw new BaseException( "medio de pago " + docPago.getNombre() + " incompatible" );

		// solo se agrega si no esta en el carro

		for( int i = 0; i < carroCompras.getDocumentos().size(); i++ ) {
			DocumentoPago dp1 = carroCompras.getDocumentos().get( i );
			if( docPago.equals( dp1 ) )
				return;
		}

		carroCompras.addDocment( docPago );
	}

	public int confirmar(ICajaView vista) {

		String fecha = Tools.getFecha();
		ParamSet pSet = Base.getParamSet("posDat");
		String caja = this.getCaja();
		this.setAgencia(pSet.getStringValue("Agencia"));
		this.setEntidad(pSet.getStringValue("Entidad"));
		this.setCaja(pSet.getStringValue("Caja"));
		this.setDireccion(pSet.getStringValue("Direccion"));
		this.setCajero(pSet.getStringValue("Cajero"));
		this.setUsuario(pSet.getStringValue("CodigoRecaudador"));
		this.setSesion(pSet.getStringValue("SessionId"));
		this.setRecaudador(pSet.getStringValue("CodigoRecaudador"));

		//Se pregunta si el medio de pago es cheque. Si es cheque no se debe rescatar el numero de operación ya que este se recupera antes del franqueo.
		int contCheque = 0;
		for(MedioPago medPag : carroMediosPago.getMediosPago()) {
			if(medPag instanceof ChequeBase && !Base.isEdicion) {
				contCheque++;
			}
		}

		if(contCheque == 0) {

			//Pedimos N°Operacion al servidor

			//ServerProxy pr = Proxy.getProxyInstance();
			AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance(); 

			//NumeroOperacionOut operOut = null;
			NumeroOperacionOutDTO operOut = null;

			//HeaderIn hIn = new HeaderIn();
			HeaderDTO hIn = new HeaderDTO();

			hIn.setAgencia((this.getAgencia()));
			hIn.setCajaFisica((this.getCaja()));
			hIn.setEntidad((this.getEntidad()));
			hIn.setCajero((this.getCajero()));
			hIn.setSession((this.getSesion()));
			hIn.setUsuario(this.getUsuario());
			//hIn.setUsuario(this.getRecaudador());
			hIn.setRecaudador(this.getRecaudador());

			try {
				//operOut= pr.numeroOperacion(new Request(hIn));

				operOut= pr.numeroOperacion(hIn);

			} catch (RemoteException e2) {
				Tools.logStackTrace(Base.logger, e2);        
				Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}  

			//if(operOut.getHeaderOut().getRc() != 0 || operOut.getNumeroOperacion() < 0){   
			if(!("0").equalsIgnoreCase(operOut.getRetCode()) || operOut.getNumeroOperacion() < 0){
				Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}


			// Para edicion, se debe rescatar un Segundo Nro de Operacion...
			if(Base.isEdicion){

				// Aca se setea la Operacion original para generar anulacion de esta !!!
				this.numeroOperacionOriginal = this.numeroOperacion;

				NumeroOperacionOutDTO operOutEdic = null; 
				try {
					operOutEdic = pr.numeroOperacion(hIn);
				} catch (RemoteException e2) {
					Tools.logStackTrace(Base.logger, e2);        
					Base.logger.info("No se pudo obtener en Numero de Operacion para Edicion  !!!");
					this.reversar();
					JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
					return -1;
				}  

				if(!("0").equalsIgnoreCase(operOutEdic.getRetCode()) || operOutEdic.getNumeroOperacion() < 0){
					Base.logger.info("No se pudo obtener en Numero de Operacion para Edicion !!!");
					this.reversar();
					JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
					return -1;
				}

				// TODO se debe setear el nro recuperado en el campo Nuevo para Edicion !!!
				//Guardamos el N° de operación para Edicion Pago
				this.numeroOperacionEdicion = operOutEdic.getNumeroOperacion();
				Base.logger.info("Numero de Operacion Edicion Pago Obtenido: "+this.getNumeroOperacionEdicion());

			}


			//Guardamos el N° de operación
			this.numeroOperacion = operOut.getNumeroOperacion();
			Base.logger.info("Numero de Operacion Obtenido: "+this.getNumeroOperacion());
			//La misma consulta trae el nro de operacion para reversar
			this.numeroOperacionReversa = operOut.getNumeroOperacionReversa();
			Base.logger.info("Numero de Operacion para la Reversa Obtenido: "+this.getNumeroOperacionReversa());
		} else {
			this.numeroOperacion = vista.getDatos().getLongValue("numOper__");
			Base.logger.info("Numero de Operacion Obtenido: "+this.getNumeroOperacion());
			//La misma consulta trae el nro de operacion para reversar
			this.numeroOperacionReversa = vista.getDatos().getLongValue("numOperRes__");
			Base.logger.info("Numero de Operacion para la Reversa Obtenido: "+this.getNumeroOperacionReversa());
		}

		if( this.isRecarga() ){
			// TODO aca NO se autoriza la recarga !!
			// Se debe hacer una vez finalizada OK la Notificacion de Recarga ...
			/**
            //Autorizamos la recarga
        	ParamSet recargaCfg = Base.getParamSet( "recargaCfg" );
            ParamSet recargaDat = Base.getParamSet( "recargaDat" );

        	int numeroSecuencia = recargaDat.getIntValue( "NumeroSecuencia" );
            recargaDat.setValue( "NumeroSecuencia", numeroSecuencia + 1 );
            recargaDat.save();

            //pr = Proxy.getProxyInstance();
            pr = AppControlProxy.getProxyInstance();

            RecargaIn rIn = new RecargaIn();

        	//hIn = new HeaderIn();
        	hIn = new HeaderDTO();

        	hIn.setAgencia((this.getAgencia()));
    		hIn.setCajaFisica((this.getCaja()));
    		hIn.setEntidad((this.getEntidad()));
    		hIn.setCajero((this.getCajero()));
    		hIn.setSession((this.getSesion()));
    		hIn.setUsuario(this.getUsuario());
    		hIn.setRecaudador(this.getRecaudador());

    		// TODO cbriones: aun no se implementa el proceso de Recarga !!!
    		/**
    		rIn.setHeaderIn(hIn);

    		rIn.setAbonado(Long.parseLong(this.getCarroCompras().getDocument(0).getDatos().getStringValue("Telefono")));
    		rIn.setCanal(Integer.parseInt(recargaCfg.getStringValue( "canal" )));
    		rIn.setCodigoProducto(Integer.parseInt(recargaCfg.getStringValue( "codigoProducto" )));
    		rIn.setFechaVenta(Tools.getFecha());
    		rIn.setHoraVenta(Tools.getTime());
    		rIn.setIdDistribuidor(Integer.parseInt(recargaCfg.getStringValue( "idDistribuidor" )));
    		rIn.setIdSubDistribuidor(Integer.parseInt(recargaCfg.getStringValue( "idSubDistribuidor" )));
    		rIn.setIdTerminal(Integer.parseInt(recargaCfg.getStringValue( "idTerminal" )));
    		rIn.setMonto((int)this.getCarroCompras().getDocument(0).getDatos().getLongValue("Monto"));
    		rIn.setSecuenciaTransaccion(numeroSecuencia);

    		RecargaOut resp = null;

    		try {
    			resp = pr.recarga(rIn);
    		} catch (RemoteException e) {
    			Tools.logStackTrace(Base.logger, e);                  
                this.reversar();
                JOptionPane.showMessageDialog(null, "No se pudo realizar la recarga\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
                return -1;
    		}

    		if(resp.getHeaderOut().getRc() != 0){                 
                this.reversar();
                JOptionPane.showMessageDialog(null, "No se pudo realizar la recarga\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
                return -1;
    		}


    		this.getCarroCompras().getDocument(0).getDatos().setValue("NumeroSecuencia", rIn.getSecuenciaTransaccion());
    		this.getCarroCompras().getDocument(0).getDatos().setValue("Fecha", rIn.getFechaVenta());
    		this.getCarroCompras().getDocument(0).getDatos().setValue("Hora", rIn.getHoraVenta());
    		this.getCarroCompras().getDocument(0).getDatos().setValue("CodAutorizacion", resp.getCodigoAutorizacion());
    		this.getCarroCompras().getDocument(0).getDatos().setValue("IdTerminal", rIn.getIdTerminal());
    		this.getCarroCompras().getDocument(0).getDatos().setValue("IdEmpresa", rIn.getIdDistribuidor());
			 */

		}else if(this.isDevolucion()){
			//Autorizamos la devolucion

			//TODO cbriones: el proceso de devolucion no corresponde a este flujo!!
			/**
            pr = Proxy.getProxyInstance();


            AutorizaDevolucionIn dIn = new AutorizaDevolucionIn();
        	hIn = new HeaderIn();

        	hIn.setAgencia(Integer.parseInt(this.getAgencia()));
    		hIn.setCajaFisica(Integer.parseInt(this.getCaja()));
    		hIn.setEntidad(Integer.parseInt(this.getEntidad()));
    		hIn.setCajero(Integer.parseInt(this.getCajero()));
    		hIn.setSession(Integer.parseInt(this.getSesion()));
    		hIn.setUsuario(this.getUsuario());
    		hIn.setRecaudador(this.getRecaudador());

    		DocumentoPago doc = this.getCarroCompras().getDocumentos().get(0);

    		dIn.setHeaderIn(hIn);

    		dIn.setCuentaUnica(doc.getDatos().getStringValue("CuentaUnica"));
    		dIn.setDireccionCobranza(doc.getDatos().getStringValue("DireccionCobranza"));
    		dIn.setNumeroCuenta(doc.getDatos().getStringValue("NumeroCuenta"));
    		dIn.setSistemaOrigen(doc.getDatos().getStringValue("SistemaOrigen"));
    		dIn.setSaldoCuenta(doc.getDatos().getLongValue("Monto"));

    		Response resp = null;

    		try {
    			resp = pr.autorizaDevolucion(dIn);
    		} catch (RemoteException e) {
    			Tools.logStackTrace(Base.logger, e);                  
                this.reversar();
                JOptionPane.showMessageDialog(null, "No se pudo realizar la devolución\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
                return -1;
    		}

    		if(resp.getHeaderOut().getRc() != 0){                 
                this.reversar();
                JOptionPane.showMessageDialog(null, "No se pudo realizar la devolución\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
                return -1;
    		}
			 */
		}

		// TODO se comenta por pruebas de impresion
		/** No se utiliza por cambios en la definicion del metodo de impresion ..
        ArrayList<LineaVoucher> boleta = armarBoletaDuplicado();
		 */
		ArrayList<LineaVoucher> boletaLocal = armarBoletaLocalDuplicado();
		HashMap mapa = null;
		if (this.getCarroCompras().getDocumentos().get(0).getDatos().getStringValue("Tipo").contains("Abono")){
			mapa = armarBoletaDuplicadoAbono();
		}else{
			mapa = armarBoletaDuplicado();
		}
		
		ArrayList<LineaVoucher> boleta = (ArrayList<LineaVoucher>) mapa.get("boleta");
		ArrayList<LineaVoucher> boletaCopia = (ArrayList<LineaVoucher>) mapa.get("boletaDuplicado");


		cuadrarEfectivo();
		for(int i = 0 ; i < this.getCarroMediosPago().getMediosPago().size() ; i++){
			if(this.getCarroMediosPago().getPago(i) instanceof ChequeBase ){
				((ChequeBase)this.getCarroMediosPago().getPago(i)).claveSup = null;
			}
		}        

		int rc;

		//Grabamos la posible reversa
		// TODO validar la implementacion para Claro !!
		this.grabarReversa(true);

		rc = enviarOnline( vista );
		Base.logger.info("RC de respuesta de Notificacion: "+rc); 

		while( true ){
			if(rc == RC_OK){

				// TODO aca debiese generar recarga a Charging System ??
				// Validar si es una Operacion de recarga ..
				if (this.isRecarga()){

					ValidarRecargaWSServerProxy rec = AppControlRecargaProxy.getProxyInstance();
					org.example.www.ValidarRecargaWS.Respuesta respRec = new org.example.www.ValidarRecargaWS.Respuesta();

					//HeaderIn hIn = new HeaderIn();
					org.example.www.ValidarRecargaWS.Caja cajaRec = new org.example.www.ValidarRecargaWS.Caja();

					//OperacionIn oIn = new OperacionIn();
					org.example.www.ValidarRecargaWS.OperacionIn opIn = new org.example.www.ValidarRecargaWS.OperacionIn();

					//hIn.setAgencia(Integer.parseInt(this.getAgencia()));
					cajaRec.setAgencia(this.getAgencia());
					Base.logger.info( "Agencia: (Rec) " + cajaRec.getAgencia());    
					//hIn.setCajaFisica(Integer.parseInt(this.getCaja()));
					cajaRec.setIdCaja(Integer.parseInt(this.getCaja()));
					Base.logger.info( "Caja: (Rec) " + cajaRec.getIdCaja());    
					//hIn.setEntidad(Integer.parseInt(this.getEntidad()));
					cajaRec.setEntidad(this.getEntidad());
					Base.logger.info( "Entidad: (Rec) " + cajaRec.getEntidad());   

					cajaRec.setCodigoSesion(Long.parseLong(this.getSesion()));
					Base.logger.info( "Session: (Rec) " + cajaRec.getCodigoSesion()); 

					cajaRec.setUsuario(this.getCajero());
					//cajaRec.setUsuario(this.getRecaudador());
					//cajaRec.setUsuario(this.getUsuario());
					Base.logger.info( "Usuario: (Rec) " + cajaRec.getUsuario()); 

					//hIn.setRecaudador(this.getRecaudador());	
					// TODO se debe rescatar desde inicicalizacion de cja !!!
					cajaRec.setRecaudador(this.getRecaudador());
					Base.logger.info( "Recaudador: (Rec) " + cajaRec.getRecaudador()); 

					// TODO se debe rescatar desde inicicalizacion de cja !!!
					cajaRec.setCanal(new Long(this.getCanal()).intValue());
					//caja.setCanal(1);
					Base.logger.info( "Canal: (Rec) " + cajaRec.getCanal()); 

					if(vista.getOperTRV().getCarroCompras().getMontoTotal() < 0){
						//operCaja.setMonto(-1*operTRV.getCarroCompras().getMontoTotal());
						opIn.setMonto(-1*vista.getOperTRV().getCarroCompras().getMontoTotal());
					}
					else{
						//operCaja.setMonto(operTRV.getCarroCompras().getMontoTotal());
						opIn.setMonto(vista.getOperTRV().getCarroCompras().getMontoTotal());
					}
					Base.logger.info( "Monto: (Rec) " + opIn.getMonto()); 

					//operCaja.setNumeroOperacion(numeroOperacion);
					// TODO como se setea el tipo de operacion para recargas ????
					if(this.isRecargaFija())
						opIn.setTipoOperacion("2");
					else if(this.isRecargaMovil())
						opIn.setTipoOperacion("1");
					else {
						Base.logger.info( "No es un tipo de Recarga valida!!!");
						opIn.setTipoOperacion("0");
					}

					Base.logger.info( "Tipo Operacion: (Rec): " + opIn.getTipoOperacion()); 

					//opIn.setValorOperacion(""+numeroOperacion);
					opIn.setValorOperacion(vista.getOperTRV().getCarroCompras().getDocument(0).getDatos().getStringValue("FolioDocumentoClaro"));
					Base.logger.info( "Nro. Operacion: (Rec): " + opIn.getValorOperacion()); 

					// TODO: se debe agregar al nuevo DTO regenerado el nro de operacion registrado...
					opIn.setNumOperacionPago(this.numeroOperacion);
					Base.logger.info( "Nro. Operacion Pago: (Rec): " + opIn.getNumOperacionPago()); 

					try {
						// TODO aca debe invocar a Recarga  !!!
						//resp = pr.envioOperacion(oIn);
						respRec = rec.validarRecarga(opIn, cajaRec);
						Base.logger.info("Codigo Resp. Recarga: "+respRec.getRetCode());
						Base.logger.info("Msje Resp. Recarga: "+respRec.getRetDesc());

						if(respRec.getRetCode() == 0){
							//Se envio ok, pasamos a la impresión del voucher
							break;
						}else{

							//reversamos el medios de pago Efectivo y eliminamos el carro
							Base.logger.error("Codigo Resp. Recarga: "+respRec.getRetCode());
							Base.logger.error("Ocurrio un error al realizar la Recarga... reversar la Transaccion !!!");
							Voucher.borraBoleta("boletaCliente.dat");
							Voucher.borraBoleta("boletaLocal.dat");
							Voucher.borraBoleta("boletaTbk.dat");

							this.reversar();
							JOptionPane.showMessageDialog(null, "No se pudo realizar la recarga\nSe cancelará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
							//mover reversa, Se genera archivo de SAF 
							Base.revisarReversasPago();
							return -1;
						}


					} catch (RemoteException e) {
						Tools.logStackTrace(Base.logger, e);
						// Si se genero un error en la recarga.. se debe reversar la Notificacion asociada..
						//rc = RC_CANCELAR;
						Base.logger.error("Ocurrio un error al invocar la Recarga... reversar la Transaccion !!!");
						//reversamos el medios de pago Efectivo y eliminamos el carro
						Voucher.borraBoleta("boletaCliente.dat");
						Voucher.borraBoleta("boletaLocal.dat");
						Voucher.borraBoleta("boletaTbk.dat");
						this.reversar();
						JOptionPane.showMessageDialog(null, "No se pudo realizar la recarga\nSe cancelará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
						//mover reversa, Se genera archivo de SAF 
						Base.revisarReversasPago();
						return -1;

					}

				}else{
					// No corresponde a una recarga..
					//Se envio ok, pasamos a la impresión del voucher
					break;
				}

			}
			else if( rc == RC_REINTENTAR){
				//reintentamos mandar el mismo pago
				rc = enviarOnline( vista );
			}
			else if( rc == RC_REVERSAR){
				//Solo reversamos los medios de pago y eliminamos el carro, no debe generar SAF !!
				Voucher.borraBoleta("boletaCliente.dat");
				Voucher.borraBoleta("boletaLocal.dat");
				Voucher.borraBoleta("boletaTbk.dat"); 
				// El reversar solo elimina el carro de Medios de Pago
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}
			else if ( rc == RC_CANCELAR ){
				//reversamos los medios de pago y eliminamos el carro
				Voucher.borraBoleta("boletaCliente.dat");
				Voucher.borraBoleta("boletaLocal.dat");
				Voucher.borraBoleta("boletaTbk.dat");
				// El reversar solo elimina el carro de Medios de Pago
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe cancelará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				//mover reversa, Se genera archivo de SAF 
				Base.revisarReversasPago();
				return -1;
			}
		}

		//La aplicacion de Control confirmo el pago, eliminamos la reversa que habiamos armado
		this.grabarReversa(false);     

		for(int i = 0 ; i < this.getCarroMediosPago().getMediosPago().size() ; i++){
			if(this.getCarroMediosPago().getPago(i) instanceof Tarjeta ){
				//Confirmar TBK
				break;
			}
		}


		ArrayList<String> archivosGuardar = new ArrayList<String>();

		// Primer paso es imprimir la Boleta Original de Pago.

		DatosFileNet datosFileNet = new DatosFileNet();
		datosFileNet.setCodigo_sesion(pSet.getStringValue("SessionId"));
		datosFileNet.setCodusuario_envia(pSet.getStringValue("CodigoRecaudador"));
		datosFileNet.setEmail_para(vista.getEntryText() ); 
		datosFileNet.setNumOperacion(String.valueOf(this.numeroOperacion));
		datosFileNet.setPropietario(pSet.getStringValue("Usuario"));
		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_PAGO);
		
		imprimirBoleta(boleta, Voucher.COPIA_CLIENTE,datosFileNet);

		
		//archivosGuardar.add(Voucher.COPIA_CLIENTE);
		
		// Segundo paso es imprimir la Copia de la Boleta de Pago.
		imprimirBoleta(boletaCopia, Voucher.COPIA_LOCAL, datosFileNet);
		//archivosGuardar.add(Voucher.COPIA_LOCAL);
		


		// Tercer paso es imprimir Premios, si es que arrojo la Trx TBK
		imprimirPremiosCliente();

		// TODO se comenta ya que siempre se imprime una copia !!!
		
        try{
        	// Tercer paso es imprimir el duplicado de la Boleta de Pago si esta configurado para hacerlo.
	        if(pSet.getStringValue("isDuplicado").equals("si")){
	        	Base.logger.info("Imprimir Duplicado de Boleta !!!!!");
	        	ReImpresion rImpresion = new ReImpresion();
	        	rImpresion.execute(vista, 0, new Datos());
	        	Base.logger.info("Finalizo Imprimir Duplicado de Boleta !!!!!");
	        }
        }catch(Exception e){
        	Base.logger.error("Ocurrio un error al imprimir la copia de la Boleta "+e.getMessage());
        }
		


		// Cuarto paso es imprimir la boleta de Pagos arrojada por TBK
		imprimirBoletaPagos(boletaLocal,Voucher.COPIA_TBK_CLIENTE,datosFileNet);
		archivosGuardar.add(Voucher.COPIA_TBK_CLIENTE);
		//archivosGuardar.add(Voucher.COPIA_TBK_CLIENTE,Voucher.COPIA_TBK_CLIENTE);

		// TODO: como se debe imprimir 2 veces el voucher original de TBK,
		// se invoca nuevamente a la impresion de Boleta de Pagos..
		imprimirBoletaPagos(boletaLocal,Voucher.COPIA_TBK_LOCAL,datosFileNet);
		//archivosGuardar.add(Voucher.COPIA_TBK_LOCAL);
		//long numeroOper = this.getNumeroOperacion();
		
				
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
    	
    	if(isPdf) {		
			enviarBoletaFilenet(this.numeroOperacion);			
			enviarCorreoSilverpop(this.numeroOperacion,vista.getEntryText());			
    	}
		
		//Aqui nunca es Tbk, borrar voucher antiguo
		if (boletaLocal.size()<1) {
			Voucher.borraBoleta("boletaTbk.dat");
		}

		if(this.isDevolucion()){
			JOptionPane.showMessageDialog(null, "Devolución Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(this.isRecarga()){
			JOptionPane.showMessageDialog(null, "Recarga Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "Pago Realizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		}

		borrarPayments();  


		
		return 0;
	}

	private void enviarBoletaFilenet(long numeroOperacion) {
		StringHolder status =  new StringHolder();
		StringHolder errorCode =  new StringHolder();
		
		Base.logger.info("Envío almacenar FILENET");
		
		PlataformaPagoEnviarAlmacenarDocumentoProxy prFN = EnviarAlmacenarDocumentoProxy.getProxyInstance();
		
		
		try {
		prFN.almacenarDocumento(String.valueOf(numeroOperacion), status, errorCode); 
		
		} catch (RemoteException e) {
		Base.logger.error("Error al invocar al enviar el comprobante.. "+e.getMessage());
		}
		Base.logger.info("Termina envío de comprobante");

		
	}
	private void enviarCorreoSilverpop(long numeroOperacion, String correos) {
		StringHolder status =  new StringHolder();
		StringHolder errorCode =  new StringHolder();
		
		Base.logger.info("Envío de correo Silverpop");
		
		PlataformaPagoEnviarCorreoSilverpopWSProxy prSP = EnviarCorreoSilverpopProxy.getProxyInstance();
		
		try {
			prSP.enviarCorreo( String.valueOf(numeroOperacion), correos, status, errorCode); 
		} catch (RemoteException e) {
		Base.logger.error("Error al invocar envio de correo.. "+e.getMessage());
		}
		Base.logger.info("Termina envío de correo");
		
	}

	public class MyFilter implements FilenameFilter {
		public boolean accept( File dir, String name ) {
			if( name.endsWith( ".ctl" ) )
				return( true );
			else
				return( false );
		}
	}

	private void borrarPayments(){
		// se revisa el directorio en cuestion
		ParamSet posCfg = Base.getParamSet("posCfg");
		File fDir = new File( posCfg.getStringValue("PaymentDir") );
		String[] lista = fDir.list( new MyFilter() );

		if( lista.length <= 0 ) {
			return;
		}
		String sName = lista[0].substring( 0, lista[0].length() - 4 );
		new File( posCfg.getStringValue("PaymentDir") + lista[0] ).delete();
		new File( posCfg.getStringValue("PaymentDir") + sName + ".dat" ).delete();
	}

	public void cuadrarEfectivo(){
		long vuelto =  this.getCarroMediosPago().getVuelto(this.getCarroCompras().getMontoTotal());
		if(vuelto > 0){
			for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
				MedioPago mPago = this.getCarroMediosPago().getPago(i);
				if(mPago.getNombre().equals("Efectivo")){
					long monto = mPago.getMonto()-vuelto;
					if(monto < 0){
						this.getCarroMediosPago().getPago(i).getDatos().setValue("Monto", 0L);
						vuelto = vuelto - mPago.getMonto();
					}
					else{
						this.getCarroMediosPago().getPago(i).getDatos().setValue("Monto", monto);
						vuelto = 0;
					}                    
				}             
			}
		}
	}

	public String imprimirBoletaPantalla(){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		int lineas = 0;

		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());  
		data.setValue("ventaDirecta", this.getVentaDirecta());

		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){

			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			lineas = lineas + aux.length;
		}

		ArrayList<LineaVoucher> footerDocs = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterDocumentos"));
		voucher = new LineaVoucher[lineas + footerDocs.size()];
		int pos = 0;

		//Agregamos el body+
		
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			// TODO: aca setea las lineas de Vocuher Desplegadas en Text Area !!
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				if(aux[j].getLinea().trim().contains("RUT: 0-0")){
					aux[j].setLinea("");
				}
				voucher[pos] = aux[j];
				pos++;
			}
		}

		//Agregamos el footer
		for(int i = 0; i < footerDocs.size(); i++){
			voucher[pos] = footerDocs.get(i);
			pos++;
		}

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();

		for(int i = 0; i < voucher.length ; i++){
							
			boleta.add(voucher[i]);
		}

		//Cargamos los vouchers de los medios de pago
		lineas = 0;
		data.setValue("Total", this.getCarroMediosPago().getMontoTotal());
		data.setValue("Vuelto", this.getCarroMediosPago().getVuelto(this.getCarroCompras().getMontoTotal()));

		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			// TODO se valida que el Voucher Cliente no sea null
			if(aux != null){
				lineas = lineas + aux.length;
			}else{
				Base.logger.info("imprimirBoletaPantalla - Voucher Cliente es nulo ");
			}

		}

		ArrayList<LineaVoucher> footerPagos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterPagosPantalla"));
		voucher = new LineaVoucher[lineas + footerPagos.size()];
		pos = 0;
		//Agregamos el body
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				pos++;
			}
		}
		//Agregamos el footer
		for(int i = 0; i < footerPagos.size(); i++){
			voucher[pos] = footerPagos.get(i);
			pos++;
		}
		//Cargamos ambos body en la boleta final
		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
		}
		String boletaPantalla = "";
		String []lineasBoleta = Voucher.printVoucherPantalla(boleta, false);

		for(int i = 0; i < lineasBoleta.length; i++){
			boletaPantalla = boletaPantalla + lineasBoleta[i] + "\n";
		}
		return boletaPantalla;
	}

	public void imprimirBoleta(ArrayList<LineaVoucher> boleta, String nombreComprobante,DatosFileNet datosFileNet){
		Base.logger.info("**************************");
		Base.logger.info("...Imprimiendo Boleta...");
		Base.logger.info("**************************");
		Voucher.printVoucher(boleta,true, nombreComprobante,datosFileNet);
		Base.logger.info("**************************");
		Base.logger.info("...Imprimio Boleta...");
		Base.logger.info("**************************");
	}

	public ArrayList<LineaVoucher> armarBoletaLocalDuplicado(){
		LineaVoucher []voucher = null;
		LineaVoucher []voucherDuplicado = null;
		LineaVoucher []aux = null;
		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
		ArrayList<LineaVoucher> boletaDuplicado = new ArrayList<LineaVoucher>();
		int lineas = 0;

		//Imprimimos cada voucher de los documentos
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getCommerceVoucher();
			if(aux == null){
				Base.logger.info("1- Generando boletaLocal en blanco desde OperTRV.....");
				Voucher.escribeBoleta(boletaDuplicado, "boletaLocal.dat");
				continue;
			}
			boleta = new ArrayList<LineaVoucher>();
			for(int j = 0; j < aux.length; j++){
				boleta.add(aux[j]);
			}
			//aux = this.getCarroMediosPago().getPago(i).getCommerceVoucherDuplicado();
			aux = this.getCarroMediosPago().getPago(i).getCommerceVoucherReimpresion();
			if(aux == null){
				break;
			}
			boletaDuplicado = new ArrayList<LineaVoucher>();
			for(int j = 0; j < aux.length; j++){
				boletaDuplicado.add(aux[j]);
			}
			Base.logger.info("2- Generando boletaLocal desde OperTRV.....");
			Voucher.escribeBoleta(boletaDuplicado, "boletaLocal.dat");
		}
		return boleta;
	}

	// TODO se comenta para pruebas de impresion !!!
	//public ArrayList<LineaVoucher> armarBoletaDuplicado(){
	public HashMap<String, ArrayList<LineaVoucher>> armarBoletaDuplicado(){
		// Se agrega mapa para retornar las dos boletas (original y duplicado)
		Map  map = new HashMap<String, ArrayList<LineaVoucher>>();

		LineaVoucher []voucher = null;
		LineaVoucher []voucherDuplicado = null;
		LineaVoucher []aux = null;
		int lineas = 0;
		String prepago = "";

		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());
		data.setValue("ventaDirecta", this.getVentaDirecta());
		
		
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			if(this.getCarroCompras().getDocument(i).getDatos().getStringValue("TipoTrx").equalsIgnoreCase("Prepago")){
				prepago = "1";
			}
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			// TODO recorro todo el arreglo para desplegar Voucher...
			for(int j=0; j<aux.length; j++){
				Base.logger.info("armarBoletaDuplicado: "+aux[j].getLinea());
			}
			lineas = lineas + aux.length;
		}
		ArrayList<LineaVoucher> header = Voucher.armarVoucher(this.datos, Base.getDefVoucher("VoucherBoletaHeader"));
		ArrayList<LineaVoucher> footerDocs = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterDocumentos"));
		
		Set<String> ruts = new HashSet<String>();
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				if (aux[j].getLinea().contains("RUT:")) {
					ruts.add(aux[j].getLinea().replace("RUT:", "").trim());
				}
			}
		}
		
		List<LineaVoucher> anuncios = Voucher.armaAnuncios(new ArrayList<String>(ruts));
		
		if(this.isRecarga()){
			voucher = new LineaVoucher[lineas + header.size() + footerDocs.size() + 6];} 
		else{
			voucher = new LineaVoucher[lineas + header.size() + footerDocs.size() + 4];
		}

		int pos = 0;
		//Agregamos el header
		int l = 0 ;

		for(pos = 0; pos < header.size() ; pos++){
			voucher[pos] = header.get(l);
			l++;
		}

		LineaVoucher line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setLinea("Timbre Electrónico: " + this.getHora() + this.getFecha() + this.getNumeroOperacion() + "-" + this.getAgencia() + "-" + this.getCaja());
		voucher[pos] = line;
		pos++;

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setLinea("COMPROBANTE DE PAGO");
		voucher[pos] = line;
		pos++;

		if(this.isRecargaFija()){
			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setLinea("RECARGA HOGAR");
			voucher[pos] = line;
			pos++;

			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setRight(true);
			line.setLinea(" , ");
			voucher[pos] = line;
			pos++;

		}

		if(this.isRecargaMovil()){
			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setLinea("RECARGA MOVIL");
			voucher[pos] = line;
			pos++;

			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setRight(true);
			line.setLinea(" , ");
			voucher[pos] = line;
			pos++;

		}

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setRight(true);
		line.setLinea(" , ");
		voucher[pos] = line;
		pos++;

		//Agregamos el body
		
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				if(aux[j].getLinea().trim().equalsIgnoreCase("RUT: 0-0")){
					aux[j].setLinea("");
				}
				if(prepago.equalsIgnoreCase("1")&& aux[j].getLinea().length() > 12){
					if(((aux[j].getLinea().trim()).substring(0, 13).equalsIgnoreCase("Control Int.:")) && prepago.equalsIgnoreCase("1")){
						aux[j].setLinea((aux[j].getLinea().trim()).replace("Control Int.:", "Nota venta. :"));
					}
				}
				
				
				voucher[pos] = aux[j];
				Base.logger.info("Body - voucher["+pos+"]: "+voucher[pos].getLinea());
				pos++;
			}
		}
		
		//Agregamos el footer
		for(int i = 0; i < footerDocs.size(); i++){
			voucher[pos] = footerDocs.get(i);
			Base.logger.info("Footer - voucher["+pos+"]: "+voucher[pos].getLinea());
			pos++;
		}

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setUnderline(true);
		line.setLinea("MEDIOS DE PAGO");
		voucher[pos] = line;
		pos++;

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
		ArrayList<LineaVoucher> boletaDuplicado = new ArrayList<LineaVoucher>();

		// TODO se agrega encabezado para boleta original !!
		line = new LineaVoucher();
		line.setCaps(true);
		line.setBold(true);
		line.setCenter(true);
		line.setLinea("Original - Cliente");
		boleta.add(line);
		//////////////////////////////

		line = new LineaVoucher();
		line.setCaps(true);
		line.setBold(true);
		line.setCenter(true);
		//line.setLinea("Duplicado");
		line.setLinea("Copia");
		boletaDuplicado.add(line);

		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
			boletaDuplicado.add(voucher[i]);
		}

		Base.logger.info("longitud de boleta: "+boleta.size());
		Base.logger.info("longitud de boleta Duplicado: "+boletaDuplicado.size());

		//Cargamos los vouchers de los medios de pago
		lineas = 0;
		int lineasDuplicado = 0;
		data.setValue("Total", this.getCarroMediosPago().getMontoTotal());
		this.isVueltoBoleta();
		data.setValue("isVuelto", this.getIsVuelto());
		data.setValue("Vuelto", this.getCarroMediosPago().getVuelto(this.getCarroCompras().getMontoTotal()));

		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			
			// TODO recorro el voucher para desplegar linea por linea
			for(int j=0; j<aux.length; j++){
				Base.logger.info("Voucher MP Cliente: "+aux[j].getLinea());
			}
			lineas = lineas + aux.length;
			aux = this.getCarroMediosPago().getPago(i).getVoucherClienteDuplicado();
			// TODO recorro el voucher para desplegar linea por linea
			for(int j=0; j<aux.length; j++){
				Base.logger.info("Voucher MP Cliente Duplicado: "+aux[j].getLinea());
			}
			lineasDuplicado = lineasDuplicado + aux.length; 
		}

		ArrayList<LineaVoucher> footerPagos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterPagos"));
		ArrayList<LineaVoucher> footerMensaje = null;
		ArrayList<LineaVoucher> footerMensajeDuplicado = null;

		//if(!this.getRecargaMovistar().equals("true")){
		if(!this.isRecarga()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterMensaje"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterMensajeDuplicado"));
		}
		else if(this.isRecargaFija()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaFija"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaFija"));
		}else if(this.isRecargaMovil()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaMovil"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaMovil"));
		}

		ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));
		voucher = new LineaVoucher[lineas + footerPagos.size() + footerMensaje.size() + 2  + footerDatos.size() + anuncios.size()];
		voucherDuplicado = new LineaVoucher[lineasDuplicado + footerPagos.size() + footerMensajeDuplicado.size() + 2 + footerDatos.size() + anuncios.size()];
		pos = 0;
		int posDuplicado = 0;
		//Agregamos el body
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			String tipoTarjeta = this.getCarroMediosPago().getMediosPago().get(i).getDatos().getStringValue("TipoTotal");
			
			for(int j = 0; j < aux.length; j++){
				
				if(aux[j].getLinea().trim().equalsIgnoreCase("Tarjeta")){
					
					if(tipoTarjeta.contains("Credito")){
						aux[j].setLinea("Tarjeta Crédito ");
					}else{
						if(tipoTarjeta.contains("Debito")){
							aux[j].setLinea("Tarjeta Débito ");
						}else{
							if(tipoTarjeta.contains("Multitienda")){
								aux[j].setLinea("Tarjeta Comercial ");
							}
						}
					}
				}
				
				voucher[pos] = aux[j];
				pos++;
			}
			aux = this.getCarroMediosPago().getPago(i).getVoucherClienteDuplicado();
			for(int j = 0; j < aux.length; j++){
				
				if(aux[j].getLinea().trim().equalsIgnoreCase("Tarjeta")){
					
					if(tipoTarjeta.contains("Credito")){
						aux[j].setLinea("Tarjeta Crédito ");
					}else{
						if(tipoTarjeta.contains("Debito")){
							aux[j].setLinea("Tarjeta Débito ");
						}else{
							if(tipoTarjeta.contains("Multitienda")){
								aux[j].setLinea("Tarjeta Comercial ");
							}
						}
					}
				}
				
				
				voucherDuplicado[posDuplicado] = aux[j];
				posDuplicado++;
			}
		}
		//Agregamos el footer
		for(int i = 0; i < footerPagos.size(); i++){
			voucher[pos] = footerPagos.get(i);
			pos++;
			voucherDuplicado[posDuplicado] = footerPagos.get(i);
			posDuplicado++;
		}
		LineaVoucher oper = new LineaVoucher();
		LineaVoucher operDuplicado = new LineaVoucher();

		ParamSet pList = Base.getParamSet( "posDat" ); 

		long numeroOper = this.getNumeroOperacion();

		operDuplicado.setLinea("Nro. Operación:" + Long.toString( numeroOper ) + "   Operador:" + pList.getStringValue( "Usuario" ));
		oper.setLinea("Nro. Operación:" + Long.toString( numeroOper ) + "   Operador:" + pList.getStringValue( "Recaudador" ));

		voucher[pos] = oper;
		voucherDuplicado[posDuplicado] = operDuplicado;
		posDuplicado++;
		pos++;
		//Agregamos el footer de datos de la caja
		for(int i = 0; i < footerDatos.size(); i++){
			voucher[pos] = footerDatos.get(i);
			pos++;
			voucherDuplicado[posDuplicado] = footerDatos.get(i);
			posDuplicado++;
		}
		//Agregamos la hora y la fecha
		DateFormat df = DateFormat.getDateInstance();
		Time t = new Time(System.currentTimeMillis());
		oper = new LineaVoucher();
		oper.setLinea("Fecha: " + df.format(Calendar.getInstance().getTime())+"    Hora:" + t.toString());
		voucher[pos] = oper;
		pos++;
		voucherDuplicado[posDuplicado] = oper;
		posDuplicado++;
		
		// agregar anuncios
		for (int i = 0; i < anuncios.size(); i++) {
			voucher[pos] = anuncios.get(i);
			voucherDuplicado[posDuplicado] = anuncios.get(i);
			Base.logger.info("Anuncio - voucher["+pos+"]: "+voucher[pos].getLinea());
			pos++;
			posDuplicado++;
		}

		for(int i = 0; i < footerMensaje.size(); i++){
			voucher[pos] = footerMensaje.get(i);
			pos++;
		}
		for(int i = 0; i < footerMensajeDuplicado.size(); i++){
			voucherDuplicado[posDuplicado] = footerMensajeDuplicado.get(i);
			posDuplicado++;
		}
		//Cargamos ambos body en la boleta final
		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);            
		}
		for(int i = 0; i < voucherDuplicado.length; i++){
			boletaDuplicado.add(voucherDuplicado[i]);
		}

		//TODO validar la forma de reimprimir el original..
		// Se respalda el Voucher Original-Cliente
		Voucher.escribeBoleta(boleta,"boletaCliente.dat");
		Voucher.escribeBoleta(boletaDuplicado,"boletaLocal.dat");

		//TODO Se comenta por pruebas de impresion
		//return boleta;

		map.put("boleta", boleta);
		map.put("boletaDuplicado", boletaDuplicado);
		return (HashMap<String, ArrayList<LineaVoucher>>) map;
	}
	
	
	public HashMap<String, ArrayList<LineaVoucher>> armarBoletaDuplicadoAbono(){
		// Se agrega mapa para retornar las dos boletas (original y duplicado)
		Map  map = new HashMap<String, ArrayList<LineaVoucher>>();

		LineaVoucher []voucher = null;
		LineaVoucher []voucherDuplicado = null;
		LineaVoucher []aux = null;
		int lineas = 0;

		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());
		data.setValue("ventaDirecta", this.getVentaDirecta());

		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			// TODO recorro todo el arreglo para desplegar Voucher...
			for(int j=0; j<aux.length; j++){
				Base.logger.info("armarBoletaDuplicado: "+aux[j].getLinea());
			}
			lineas = lineas + aux.length;
		}
		ArrayList<LineaVoucher> header = Voucher.armarVoucher(this.datos, Base.getDefVoucher("VoucherBoletaHeader"));
		ArrayList<LineaVoucher> footerDocs = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterDocumentos"));
		if(this.isRecarga()){
			voucher = new LineaVoucher[lineas + header.size() + footerDocs.size() + 6];} 
		else{
			voucher = new LineaVoucher[lineas + header.size() + footerDocs.size() + 4];
		}

		int pos = 0;
		//Agregamos el header
		int l = 0 ;

		for(pos = 0; pos < header.size() ; pos++){
			voucher[pos] = header.get(l);
			l++;
		}

		LineaVoucher line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setLinea("Timbre Electrónico: " + this.getHora() + this.getFecha() + this.getNumeroOperacion() + "-" + this.getAgencia() + "-" + this.getCaja());
		voucher[pos] = line;
		pos++;

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setLinea("COMPROBANTE DE PAGO ABONO");
		voucher[pos] = line;
		pos++;

		if(this.isRecargaFija()){
			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setLinea("RECARGA HOGAR");
			voucher[pos] = line;
			pos++;

			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setRight(true);
			line.setLinea(" , ");
			voucher[pos] = line;
			pos++;

		}

		if(this.isRecargaMovil()){
			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setLinea("RECARGA MOVIL");
			voucher[pos] = line;
			pos++;

			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setRight(true);
			line.setLinea(" , ");
			voucher[pos] = line;
			pos++;

		}

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setRight(true);
		line.setLinea(" , ");
		voucher[pos] = line;
		pos++;

		//Agregamos el body
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				Base.logger.info("Body - voucher["+pos+"]: "+voucher[pos].getLinea());
				pos++;
			}
		}

		//Agregamos el footer
		for(int i = 0; i < footerDocs.size(); i++){
			voucher[pos] = footerDocs.get(i);
			Base.logger.info("Footer - voucher["+pos+"]: "+voucher[pos].getLinea());
			pos++;
		}

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setUnderline(true);
		line.setLinea("MEDIOS DE PAGO");
		voucher[pos] = line;
		pos++;

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
		ArrayList<LineaVoucher> boletaDuplicado = new ArrayList<LineaVoucher>();

		// TODO se agrega encabezado para boleta original !!
		line = new LineaVoucher();
		line.setCaps(true);
		line.setBold(true);
		line.setCenter(true);
		line.setLinea("Original - Cliente");
		boleta.add(line);
		//////////////////////////////

		line = new LineaVoucher();
		line.setCaps(true);
		line.setBold(true);
		line.setCenter(true);
		//line.setLinea("Duplicado");
		line.setLinea("Copia");
		boletaDuplicado.add(line);

		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
			boletaDuplicado.add(voucher[i]);
		}

		Base.logger.info("longitud de boleta: "+boleta.size());
		Base.logger.info("longitud de boleta Duplicado: "+boletaDuplicado.size());

		//Cargamos los vouchers de los medios de pago
		lineas = 0;
		int lineasDuplicado = 0;
		data.setValue("Total", this.getCarroMediosPago().getMontoTotal());
		this.isVueltoBoleta();
		data.setValue("isVuelto", this.getIsVuelto());
		data.setValue("Vuelto", this.getCarroMediosPago().getVuelto(this.getCarroCompras().getMontoTotal()));

		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			// TODO recorro el voucher para desplegar linea por linea
			for(int j=0; j<aux.length; j++){
				Base.logger.info("Voucher MP Cliente: "+aux[j].getLinea());
			}
			lineas = lineas + aux.length;
			aux = this.getCarroMediosPago().getPago(i).getVoucherClienteDuplicado();
			// TODO recorro el voucher para desplegar linea por linea
			for(int j=0; j<aux.length; j++){
				Base.logger.info("Voucher MP Cliente Duplicado: "+aux[j].getLinea());
			}
			lineasDuplicado = lineasDuplicado + aux.length; 
		}

		ArrayList<LineaVoucher> footerPagos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterPagos"));
		ArrayList<LineaVoucher> footerMensaje = null;
		ArrayList<LineaVoucher> footerMensajeDuplicado = null;

		//if(!this.getRecargaMovistar().equals("true")){
		if(!this.isRecarga()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterMensaje"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterMensajeDuplicado"));
		}
		else if(this.isRecargaFija()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaFija"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaFija"));
		}else if(this.isRecargaMovil()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaMovil"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaMovil"));
		}

		ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));
		voucher = new LineaVoucher[lineas + footerPagos.size() + footerMensaje.size() + 2  + footerDatos.size()];
		voucherDuplicado = new LineaVoucher[lineasDuplicado + + footerPagos.size() + footerMensajeDuplicado.size() + 2  + footerDatos.size()];
		pos = 0;
		int posDuplicado = 0;
		//Agregamos el body
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				pos++;
			}
			aux = this.getCarroMediosPago().getPago(i).getVoucherClienteDuplicado();
			for(int j = 0; j < aux.length; j++){
				voucherDuplicado[posDuplicado] = aux[j];
				posDuplicado++;
			}
		}
		//Agregamos el footer
		for(int i = 0; i < footerPagos.size(); i++){
			voucher[pos] = footerPagos.get(i);
			pos++;
			voucherDuplicado[posDuplicado] = footerPagos.get(i);
			posDuplicado++;
		}
		LineaVoucher oper = new LineaVoucher();
		LineaVoucher operDuplicado = new LineaVoucher();


		ParamSet pList = Base.getParamSet( "posDat" ); 

		long numeroOper = this.getNumeroOperacion();

		operDuplicado.setLinea("Nro. Operación:" + Long.toString( numeroOper ) + "   Operador:" + pList.getStringValue( "Usuario" ));
		oper.setLinea("Nro. Operación:" + Long.toString( numeroOper ) + "   Operador:" + pList.getStringValue( "Recaudador" ));
		voucher[pos] = oper;
		voucherDuplicado[posDuplicado] = operDuplicado;
		posDuplicado++;
		pos++;
		//Agregamos el footer de datos de la caja
		for(int i = 0; i < footerDatos.size(); i++){
			voucher[pos] = footerDatos.get(i);
			pos++;
			voucherDuplicado[posDuplicado] = footerDatos.get(i);
			posDuplicado++;
		}
		//Agregamos la hora y la fecha
		DateFormat df = DateFormat.getDateInstance();
		Time t = new Time(System.currentTimeMillis());
		oper = new LineaVoucher();
		oper.setLinea("Fecha: " + df.format(Calendar.getInstance().getTime())+"    Hora:" + t.toString());
		voucher[pos] = oper;
		pos++;
		voucherDuplicado[posDuplicado] = oper;
		posDuplicado++;

		for(int i = 0; i < footerMensaje.size(); i++){
			voucher[pos] = footerMensaje.get(i);
			pos++;
		}
		for(int i = 0; i < footerMensajeDuplicado.size(); i++){
			voucherDuplicado[posDuplicado] = footerMensajeDuplicado.get(i);
			posDuplicado++;
		}
		//Cargamos ambos body en la boleta final
		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);            
		}
		for(int i = 0; i < voucherDuplicado.length; i++){
			boletaDuplicado.add(voucherDuplicado[i]);
		}

		//TODO validar la forma de reimprimir el original..
		// Se respalda el Voucher Original-Cliente
		Voucher.escribeBoleta(boleta,"boletaCliente.dat");
		Voucher.escribeBoleta(boletaDuplicado,"boletaLocal.dat");

		//TODO Se comenta por pruebas de impresion
		//return boleta;

		map.put("boleta", boleta);
		map.put("boletaDuplicado", boletaDuplicado);
		return (HashMap<String, ArrayList<LineaVoucher>>) map;
	}

	public void imprimirBoletaDocumentos(DatosFileNet datosFileNet){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		ArrayList<LineaVoucher> boleta;
		int lineas = 0;

		//Imprimimos cada voucher de los documentos
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCommerceVoucher();
			if(aux == null){
				break;
			}
			boleta = new ArrayList<LineaVoucher>();
			for(int j = 0; j < aux.length; j++){
				boleta.add(aux[j]);
			}
			Voucher.printVoucher(boleta, false, Voucher.COPIA_LOCAL,datosFileNet);
		}
	}

	private void imprimirBoletaPagos(ArrayList<LineaVoucher> boleta, String nombreComprobante, DatosFileNet datosFileNet){    
		Base.logger.info("... longitud de boleta: "+boleta.size());
		Base.logger.info("**************************");
		Base.logger.info("...Imprimiendo Boleta Pagos...");
		Base.logger.info("**************************");
		Voucher.printVoucher(boleta, false, nombreComprobante,datosFileNet);
		Base.logger.info("**************************");
		Base.logger.info("...Imprimio Boleta Pagos...");
		Base.logger.info("**************************");
		// TODO: aca se envia la confirmacion para TBK
		if(boleta.size() != 0){
			Base.logger.info("..... - Se invoca a Confirmacion de TBK - .....");
			Base.logger.info("P I N P A D - confirma");
			Base.tbk.confirmarOperacion(); //TODO areeglar no ilvidar
		}else{
			Base.logger.info("----- El Medio de pago no es Tarjeta -----");
		}

	}

	public void imprimirPremiosCliente(){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		ArrayList<LineaVoucher> boleta;
		int lineas = 0;

		//Imprimimos cada voucher de los documentos
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getPremioCliente();
			if(aux == null){
				break;
			}
			boleta = new ArrayList<LineaVoucher>();
			for(int j = 0; j < aux.length; j++){
				boleta.add(aux[j]);
			}
			Voucher.printVoucher(boleta, false, Voucher.COPIA_PREMIO_CLIENTE,null);
		}
	}

	public void imprimirPremiosLocal(){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		ArrayList<LineaVoucher> boleta;
		int lineas = 0;

		//Imprimimos cada voucher de los documentos
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getPremioLocal();
			if(aux == null){
				break;
			}
			boleta = new ArrayList<LineaVoucher>();
			for(int j = 0; j < aux.length; j++){
				boleta.add(aux[j]);
			}
			Voucher.printVoucher(boleta, false, Voucher.COPIA_PREMIO_LOCAL, null);
		}
	}

	public void cancelar() {

	}

	public String imprimirResumen(){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		int lineas = 0;
		int docs = 0;
		long totalDocs = 0;
		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());        

		voucher = new LineaVoucher[6];
		int pos = 0;
		LineaVoucher linea1 = new LineaVoucher();
		linea1.setLinea("Fecha: " + this.getFecha());
		voucher[0] = linea1;
		LineaVoucher linea2 = new LineaVoucher();
		linea2.setLinea("Hora: " + this.getHora());
		voucher[1] = linea2;
		LineaVoucher linea3 = new LineaVoucher();
		linea3.setLinea("N°Operacion: " + this.getNumeroOperacion());
		voucher[2] = linea3;
		LineaVoucher linea4 = new LineaVoucher();
		linea4.setLinea("Operacion: " + this.getOperacion());
		voucher[3] = linea4;
		LineaVoucher linea5 = new LineaVoucher();
		linea5.setLinea("---------------------------------");
		voucher[4] = linea5;
		LineaVoucher linea6 = new LineaVoucher();
		linea6.setLinea("N°Documentos: " + this.getCarroCompras().getDocumentos().size() + " Monto: " + this.getCarroCompras().getMontoTotal());
		voucher[5] = linea6;
		pos = 6;

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();

		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
		}

		//Cargamos los vouchers de los medios de pago

		voucher = new LineaVoucher[1];
		pos = 0;

		LineaVoucher linea7 = new LineaVoucher();
		linea7.setLinea("N°Medios Pago: " + this.getCarroMediosPago().getMediosPago().size() + " Monto: " + this.getCarroMediosPago().getMontoTotal());
		voucher[0] = linea7;

		//Cargamos ambos body en la boleta final
		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
		}
		String boletaPantalla = "";
		String []lineasBoleta = Voucher.printVoucherPantalla(boleta, false);

		for(int i = 0; i < lineasBoleta.length; i++){
			boletaPantalla = boletaPantalla + lineasBoleta[i] + "\n";
		}
		return boletaPantalla;
	}

	public String imprimirJournalPantalla(){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		int lineas = 0;

		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());        

		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			lineas = lineas + aux.length;
		}
		ArrayList<LineaVoucher> footerDocs = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterDocumentos"));
		voucher = new LineaVoucher[5 + lineas + footerDocs.size()];
		int pos = 0;
		LineaVoucher linea1 = new LineaVoucher();
		linea1.setLinea("Fecha: " + this.getFecha());
		voucher[0] = linea1;
		LineaVoucher linea2 = new LineaVoucher();
		linea2.setLinea("Hora: " + this.getHora());
		voucher[1] = linea2;
		LineaVoucher linea3 = new LineaVoucher();
		linea3.setLinea("N°Operacion: " + this.getNumeroOperacion());
		voucher[2] = linea3;
		LineaVoucher linea4 = new LineaVoucher();
		linea4.setLinea("Operacion: " + this.getOperacion());
		voucher[3] = linea4;
		LineaVoucher linea5 = new LineaVoucher();
		linea5.setLinea("---------------------------------");
		voucher[4] = linea5;
		pos = 5;
		//Agregamos el body
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				pos++;
			}
		}
		//Agregamos el footer
		for(int i = 0; i < footerDocs.size(); i++){
			voucher[pos] = footerDocs.get(i);
			pos++;
		}
		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();

		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
		}

		//Cargamos los vouchers de los medios de pago
		lineas = 0;
		data.setValue("Total", this.getCarroMediosPago().getMontoTotalAnulacion());
		data.setValue("Vuelto", this.getCarroMediosPago().getVueltoAnulacion(this.getCarroCompras().getMontoTotal()));

		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			if( this.getCarroMediosPago().getPago(i) instanceof Tarjeta ){
				continue;
			}
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			lineas = lineas + aux.length;
		}

		ArrayList<LineaVoucher> footerPagos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterPagosPantalla"));
		voucher = new LineaVoucher[lineas + footerPagos.size()];
		pos = 0;
		//Agregamos el body
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			if( this.getCarroMediosPago().getPago(i) instanceof Tarjeta ){
				continue;
			}
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				pos++;
			}
		}
		//Agregamos el footer
		for(int i = 0; i < footerPagos.size(); i++){
			voucher[pos] = footerPagos.get(i);
			pos++;
		}
		//Cargamos ambos body en la boleta final
		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
		}
		String boletaPantalla = "";
		String []lineasBoleta = Voucher.printVoucherPantalla(boleta, false);

		for(int i = 0; i < lineasBoleta.length; i++){
			boletaPantalla = boletaPantalla + lineasBoleta[i] + "\n";
		}
		return boletaPantalla;
	}

	public boolean isReversable(){
		for(int i = 0; i < this.carroCompras.getDocumentos().size(); i++){
			if(this.carroCompras.getDocument(i).getNombre().equals("RecargaMovistar")){
				return false;
			}
		}
		for(int i = 0; i < this.carroMediosPago.getMediosPago().size(); i++){
			if(this.carroMediosPago.getPago(i).getNombre().equals("TarjetaDebito")){
				return false;
			}
			else if(this.carroMediosPago.getPago(i).getNombre().equals("TarjetaTbkManual")){
				if(this.carroMediosPago.getPago(i).getDatos().getStringValue("TipoTotal").equals("TD"))
					return false;
			}
		}
		return true;
	}

	public String imprimirJournalPantallaRemesa(){
		LineaVoucher []voucher = null;
		LineaVoucher []aux = null;
		int lineas = 0;

		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());        

		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			lineas = lineas + aux.length;
		}

		voucher = new LineaVoucher[5 + lineas];
		int pos = 0;
		LineaVoucher linea1 = new LineaVoucher();
		linea1.setLinea("Fecha: " + this.getFecha());
		voucher[0] = linea1;
		LineaVoucher linea2 = new LineaVoucher();
		linea2.setLinea("Hora: " + this.getHora());
		voucher[1] = linea2;
		LineaVoucher linea3 = new LineaVoucher();
		linea3.setLinea("N°Operacion: " + this.getNumeroOperacion());
		voucher[2] = linea3;
		LineaVoucher linea4 = new LineaVoucher();
		linea4.setLinea("Operacion: " + this.getOperacion());
		voucher[3] = linea4;
		LineaVoucher linea5 = new LineaVoucher();
		linea5.setLinea("---------------------------------");
		voucher[4] = linea5;
		pos = 5;

		//Agregamos el body
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				pos++;
			}
		}

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();

		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
		}

		String boletaPantalla = "";
		String []lineasBoleta = Voucher.printVoucherPantalla(boleta, false);

		for(int i = 0; i < lineasBoleta.length; i++){
			boletaPantalla = boletaPantalla + lineasBoleta[i] + "\n";
		}
		return boletaPantalla;
	}

	public int anular(){
		for(int i = 0; i < this.carroMediosPago.getMediosPago().size(); i++){
			try {
				this.carroMediosPago.removeMedioPagoAnular(i);
			} catch (MedioPagoException e) {
				Tools.logStackTrace(Base.logger, e);
				return -1;
			}
		}
		return 0;
	}

	public void reversar(){
		for(int i = 0; i < this.carroMediosPago.getMediosPago().size(); i++){
			try {
				this.carroMediosPago.removeMedioPago(i);
			} catch (MedioPagoException e) {
				Tools.logStackTrace(Base.logger, e);
			}
		}
	}

	public boolean isAnulable(){
		for(int i = 0 ; i < this.carroMediosPago.getMediosPago().size() ; i++){
			if(this.carroMediosPago.getMediosPago().get(i) instanceof Tarjeta)
				return false;
		}
		return true;
	}

	public boolean comparaSoap(String soapLocal, String soapIF){
		int iPos = soapLocal.indexOf("<HeaderIn>");
		int fPos = soapLocal.indexOf("</ifop");
		String auxLocal = soapLocal.substring(iPos,fPos);
		auxLocal = auxLocal.replaceAll("\\s", "");

		iPos = soapIF.indexOf("<HeaderIn>");
		fPos = soapIF.indexOf("</ActualizacionRequest>");
		if(fPos < 0){
			fPos = soapIF.length();
		}

		String auxIF = soapIF.substring(iPos,fPos);
		auxIF = auxIF.replaceAll("\\s", "");

		Base.logger.info("Soap local: " + auxLocal);
		Base.logger.info("Soap IF: " + auxIF);

		if(auxLocal.equals(auxIF)){
			return true;
		}
		return false;
	}

	private boolean comparaHeaderIn(Datos headerIF){

		if(this.getNumeroOperacion() != Long.parseLong(headerIF.getStringValue("NumeroOperacion"))){
			return false;
		}
		if(!this.getFecha().equals(headerIF.getStringValue("FechaOperacion").trim())){
			return false;
		}
		if(!this.getFechaPago().equals(headerIF.getStringValue("FechaPago").trim())){
			return false;
		}
		if(!this.getHora().equals(headerIF.getStringValue("HoraOperacion").trim())){
			return false;
		}
		return true;
	}

	public void enviarInyectorRecarga(){
		ParamSet pSet = Base.getParamSet("posCfg");
		String destinoDir = pSet.getStringValue("FtpSafDir");
		String fName = String.format("%03d",Integer.parseInt(agencia)) + "_" + String.format("%03d",Integer.parseInt(caja)) + "_" + fecha + "_" + hora;

		File out = new File(destinoDir + fName +".dat");

		Writer output = null;
		try {
			output = new BufferedWriter(new FileWriter(out));
		} catch (IOException e1) {
			Tools.logStackTrace(Base.logger, e1);
		}
		try {
			output.write(this.soap + "\n");
		} catch (Exception e) {
			Tools.logStackTrace(Base.logger, e);
		}
		try {
			output.close();
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
		}

		//Creamos el ctl 
		out = new File(destinoDir + fName +".ctl");        
		output = null;
		try {
			output = new BufferedWriter(new FileWriter(out));
		} catch (IOException e1) {
			Tools.logStackTrace(Base.logger, e1);
		}
		try {
			output.close();
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
		}
	}

	public void enviarInyectorError(){
		ParamSet pSet = Base.getParamSet("posCfg");
		String destinoDir = pSet.getStringValue("FtpSafDir");
		String fName = String.format("%03d",Integer.parseInt(agencia)) + "_" + String.format("%03d",Integer.parseInt(caja)) + "_" + fecha + "_" + hora;

		File out = new File(destinoDir + fName +".err");

		Writer output = null;
		try {
			output = new BufferedWriter(new FileWriter(out));
		} catch (IOException e1) {
			Tools.logStackTrace(Base.logger, e1);
		}
		try {
			output.write(this.soap + "\n");
		} catch (Exception e) {
			Tools.logStackTrace(Base.logger, e);
		}
		try {
			output.close();
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
		}

		//Creamos el ctl 
		out = new File(destinoDir + fName +".ectl");        
		output = null;
		try {
			output = new BufferedWriter(new FileWriter(out));
		} catch (IOException e1) {
			Tools.logStackTrace(Base.logger, e1);
		}
		try {
			output.close();
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
		}
	}

	private void grabarReversa(boolean modo){
		// TODO cbriones: se debe validar la informacion que se
		// grabara para poder reversar notificaciones !!
		ParamSet posCfg = Base.getParamSet("posCfg");

		if(modo){
			ParamSet pList = Base.getParamSet( "posDat" );

			// Necesito generar: NotifcacionEnvio
			// Necesito generar: Operacion
			// Necesito generar: Caja
			// Necesito generar: Transaccion[]
			// Necesito generar: MedioPago[]


			//HeaderIn hIn = new HeaderIn();
			Caja caja = new Caja();

			//OperacionIn oIn = new OperacionIn();
			Operacion opIn = new Operacion();

			//hIn.setAgencia(Integer.parseInt(pList.getStringValue("Agencia")));
			caja.setAgencia(pList.getStringValue("Agencia"));
			Base.logger.info("Agencia (Rev): "+caja.getAgencia()); 

			//hIn.setCajaFisica(Integer.parseInt(pList.getStringValue("Caja")));
			caja.setIdCaja(Integer.parseInt(pList.getStringValue("Caja")));
			Base.logger.info("Caja (Rev): "+caja.getIdCaja()); 

			//hIn.setEntidad(Integer.parseInt(pList.getStringValue("Entidad")));
			caja.setEntidad(pList.getStringValue("Entidad"));
			Base.logger.info("Entidad (Rev): "+caja.getEntidad()); 

			//hIn.setCajero(Integer.parseInt(pList.getStringValue("Cajero")));
			// TODO en Claro No existe el parametro Cajero !!

			//hIn.setSession(Integer.parseInt(pList.getStringValue("SessionId")));
			caja.setCodigoSesion(new Long(pList.getStringValue("SessionId")));
			Base.logger.info("session (Rev): "+caja.getCodigoSesion());

			//hIn.setUsuario(pList.getStringValue("Usuario"));

			// TODO se setea el codigo de recaudador como Usuario !!
			//caja.setUsuario(pList.getStringValue("CodigoRecaudador"));
			caja.setUsuario(pList.getStringValue("Cajero"));
			Base.logger.info("Usuario (Rev): "+caja.getUsuario()); 

			//hIn.setRecaudador(pList.getStringValue("CodigoRecaudador"));
			caja.setRecaudador(pList.getStringValue("CodigoRecaudador"));
			Base.logger.info("CodigoRecaudador (Rev): "+caja.getRecaudador());

			// TODO se debe rescatar desde inicicalizacion de cja !!!
			//caja.setCanal(1);
			caja.setCanal(new Integer(pList.getStringValue("Canal")).intValue());
			Base.logger.info("Canal (Rev): "+caja.getCanal());

			//oIn.setHeaderIn(hIn);
			opIn.setCaja(caja);


			//OperacionCaja operCaja = new OperacionCaja();
			//operCaja.setCanal(0);

			//operCaja.setFechaOperacion(Tools.getFecha());
			opIn.setFechaPago(Tools.getFecha());
			Base.logger.info( "Fecha (Rev): " + opIn.getFechaPago()); 

			//operCaja.setFechaPago(Tools.getFecha());
			//operCaja.setHoraOperacion(Tools.getTime());

			//operCaja.setMonto(this.getCarroCompras().getMontoTotal());
			opIn.setMonto(this.getCarroCompras().getMontoTotal());
			Base.logger.info( "Monto (Rev): " + opIn.getMonto());

			// TODO aca se setea el Nro de operacion de reversa generado !!!!
			//operCaja.setNumeroOperacion(this.getNumeroOperacionReversa());
			opIn.setNumeroOperacion(this.getNumeroOperacionReversa());
			Base.logger.info( "Nro operacion (Rev): " + opIn.getNumeroOperacion()); 

			//operCaja.setTipoOperacion("2");
			opIn.setTipoOperacion(2); // validar que tipo de operacion debe ser ??
			Base.logger.info( "Tipo Operacion (Rev): " + opIn.getTipoOperacion()); 

			//oIn.setOperacion(operCaja);

			//TransaccionCaja []trxs = new TransaccionCaja[1];
			Transaccion []trxsClaro = new Transaccion[1];

			trxsClaro[0] = new Transaccion();
			//Tools.initTrx(trxsClaro[0]);
			Tools.initTrxClaro(trxsClaro[0]);

			trxsClaro[0].setTipoTransaccion("Anulacion");
			Base.logger.info( "Tipo Transaccion (Rev): " + trxsClaro[0].getTipoTransaccion()); 
			trxsClaro[0].setNroOperacionAReversar(this.getNumeroOperacion());
			Base.logger.info( "Nro Operacion a Reversar (Rev): " + trxsClaro[0].getNroOperacionAReversar());
			//Ingreso Id tipo de anulacion en el campo monto (NO CAMBIAR!!!)
			trxsClaro[0].setMonto(Long.valueOf(1));
			//oIn.setTransacciones(trxs);
			opIn.setTransaccion(trxsClaro);

			Base.logger.info( "Trx (Cta Clte) (Rev): " + opIn.getTransaccion(0).getCuentaCliente()); 
			Base.logger.info( "Trx (Fcha venc) (Rev): " + opIn.getTransaccion(0).getFechaVencimiento()); 
			Base.logger.info( "Trx (Monto) (Rev): " + opIn.getTransaccion(0).getMonto()); 
			Base.logger.info( "Trx (Origen) (Rev): " + opIn.getTransaccion(0).getOrigen()); 
			Base.logger.info( "Trx (Servicio) (Rev): " + opIn.getTransaccion(0).getServicio()); 
			Base.logger.info( "Trx (Tipo Doc) (Rev): " + opIn.getTransaccion(0).getTipoDocumento()); 
			Base.logger.info( "Trx (Tipo Reg) (Rev): " + opIn.getTransaccion(0).getTipoRegistro()); 
			Base.logger.info( "Trx (Tipo Trx) (Rev): " + opIn.getTransaccion(0).getTipoTransaccion()); 
			Base.logger.info( "Trx (Nro. Doc) (Rev): " + opIn.getTransaccion(0).getNumeroDocumento()); 
			Base.logger.info( "Trx (Rut) (Rev): " + opIn.getTransaccion(0).getRut()); 
			Base.logger.info( "Trx (Dv) (Rev): " + opIn.getTransaccion(0).getDv()); 
			Base.logger.info( "Trx (Cod. Emp)(Rev) : " + opIn.getTransaccion(0).getEmpresa()); 

			//MedioPagoCaja []mps = new MedioPagoCaja[0];
			cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago []pagosClaro = new cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago[1];

			//oIn.setMediosPago(mps);
			opIn.setMedioPago(pagosClaro);

			/**
    		Base.logger.info( "MP (Cod. Autorz.) (Rev): " + opIn.getMedioPago(0).getCodigoAutorizacion()); 
    		Base.logger.info( "MP (Fecha venc.): (Rev) " + opIn.getMedioPago(0).getFechaVencimiento()); 
    		Base.logger.info( "MP (Monto): (Rev)" + opIn.getMedioPago(0).getMonto()); 
    		Base.logger.info( "MP (Tipo Trx) (Rev): " + opIn.getMedioPago(0).getTipoTransaccion()); 
			 */

			//Serializa.serializa(oIn, "REVERSA");
			Serializa.serializa(opIn, "REVERSA");
		}
		else{
			File outFile = new File( posCfg.getStringValue("VoucherDir") + "reversaPago.dat" );
			outFile.delete();
			outFile = new File( posCfg.getStringValue("VoucherDir") + "reversaPago.ctl" );
			outFile.delete();
		}
	}
	
	/***************************************************************************************************/
	
	
	
	public int confirmarOffline(ICajaView vista) {

		String fecha = Tools.getFecha();
		ParamSet pSet = Base.getParamSet("posDat");
		String caja = this.getCaja();
		this.setAgencia(pSet.getStringValue("Agencia"));
		this.setEntidad(pSet.getStringValue("Entidad"));
		this.setCaja(pSet.getStringValue("Caja"));
		this.setDireccion(pSet.getStringValue("Direccion"));
		this.setCajero(pSet.getStringValue("Cajero"));
		this.setUsuario(pSet.getStringValue("Usuario"));
		this.setSesion(pSet.getStringValue("SessionId"));
		this.setRecaudador(pSet.getStringValue("CodigoRecaudador"));

		//Se pregunta si el medio de pago es cheque. Si es cheque no se debe rescatar el numero de operación ya que este se recupera antes del franqueo.
		int contCheque = 0;
		for(MedioPago medPag : carroMediosPago.getMediosPago()) {
			if(medPag instanceof ChequeBaseOffline && !Base.isEdicion) {
				contCheque++;
			}
		}

		if(contCheque == 0) {

			//HeaderIn hIn = new HeaderIn();
			HeaderDTO hIn = new HeaderDTO();

			hIn.setAgencia((this.getAgencia()));
			hIn.setCajaFisica((this.getCaja()));
			hIn.setEntidad((this.getEntidad()));
			hIn.setCajero((this.getCajero()));
			hIn.setSession((this.getSesion()));
			hIn.setUsuario(this.getUsuario());
			//hIn.setUsuario(this.getRecaudador());
			hIn.setRecaudador(this.getRecaudador());
			
			
			this.numeroOperacionOriginal = this.numeroOperacion;
     		// Aca se setea la Operacion original para generar anulacion de esta !!!
			this.numeroOperacion = 0;
			this.numeroOperacionOriginal = this.numeroOperacion;
			this.numeroOperacionEdicion = 0;

			Base.logger.info("Numero de Operacion Obtenido: "+this.getNumeroOperacion());
			//La misma consulta trae el nro de operacion para reversar
			this.numeroOperacionReversa = 0;
			Base.logger.info("Numero de Operacion para la Reversa Obtenido: "+this.getNumeroOperacionReversa());
		} else {
			this.numeroOperacion = vista.getDatos().getLongValue("numOper__");
			Base.logger.info("Numero de Operacion Obtenido: "+this.getNumeroOperacion());
			//La misma consulta trae el nro de operacion para reversar
			this.numeroOperacionReversa = vista.getDatos().getLongValue("numOperRes__");
			Base.logger.info("Numero de Operacion para la Reversa Obtenido: "+this.getNumeroOperacionReversa());
		}

		

		// TODO se comenta por pruebas de impresion
		/** No se utiliza por cambios en la definicion del metodo de impresion ..
        ArrayList<LineaVoucher> boleta = armarBoletaDuplicado();
		 */
		ArrayList<LineaVoucher> boletaLocal = armarBoletaLocalDuplicado();

		               
		HashMap mapa = armarBoletaDuplicadoOffline();
		ArrayList<LineaVoucher> boleta = (ArrayList<LineaVoucher>) mapa.get("boleta");
		ArrayList<LineaVoucher> boletaCopia = (ArrayList<LineaVoucher>) mapa.get("boletaDuplicado");


		cuadrarEfectivo();
		
		for(int i = 0 ; i < this.getCarroMediosPago().getMediosPago().size() ; i++){
			if(this.getCarroMediosPago().getPago(i) instanceof ChequeBaseOffline ){
				((ChequeBaseOffline)this.getCarroMediosPago().getPago(i)).claveSup = null;
			}
		}        

		int rc;
		// TODO validar la implementacion para Claro !!
	//	this.grabarReversa(true);

		rc = enviarOffline(vista);
		Base.logger.info("RC de respuesta de Notificacion: "+rc); 

		
		// Primer paso es imprimir la Boleta Original de Pago.
		imprimirBoleta(boleta, Voucher.COPIA_CLIENTE,null);

		// Segundo paso es imprimir la Copia de la Boleta de Pago.
		imprimirBoleta(boletaCopia, Voucher.COPIA_LOCAL,null);

		// Tercer paso es imprimir Premios, si es que arrojo la Trx TBK
		imprimirPremiosCliente();

		// TODO se comenta ya que siempre se imprime una copia !!!
		
        try{
        	// Tercer paso es imprimir el duplicado de la Boleta de Pago si esta configurado para hacerlo.
	        if(pSet.getStringValue("isDuplicado").equals("si")){
	        	Base.logger.info("Imprimir Duplicado de Boleta !!!!!");
	        	ReImpresion rImpresion = new ReImpresion();
	        	rImpresion.execute(vista, 0, new Datos());
	        	Base.logger.info("Finalizo Imprimir Duplicado de Boleta !!!!!");
	        }
        }catch(Exception e){
        	Base.logger.error("Ocurrio un error al imprimir la copia de la Boleta "+e.getMessage());
        }
		


		// Cuarto paso es imprimir la boleta de Pagos arrojada por TBK
		imprimirBoletaPagos(boletaLocal, Voucher.COPIA_TBK_CLIENTE,null);

		// TODO: como se debe imprimir 2 veces el voucher original de TBK,
		// se invoca nuevamente a la impresion de Boleta de Pagos..
		imprimirBoletaPagos(boletaLocal, Voucher.COPIA_TBK_LOCAL, null);


		

		JOptionPane.showMessageDialog(null, "Pago Realizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		
		
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
    	
    	if(isPdf) {		
			enviarBoletaFilenet(this.numeroOperacion);		
			enviarCorreoSilverpop(this.numeroOperacion,vista.getEntryText());		
    	}

		borrarPayments();  

		return 0;
	}
	public HashMap<String, ArrayList<LineaVoucher>> armarBoletaDuplicadoOffline(){
		// Se agrega mapa para retornar las dos boletas (original y duplicado)
		Map  map = new HashMap<String, ArrayList<LineaVoucher>>();

		LineaVoucher []voucher = null;
		LineaVoucher []voucherDuplicado = null;
		LineaVoucher []aux = null;
		int lineas = 0;

		Datos data = new Datos();
		data.setValue("Total", this.getCarroCompras().getMontoTotal());
		data.setValue("ventaDirecta", this.getVentaDirecta());

		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			// TODO recorro todo el arreglo para desplegar Voucher...
			for(int j=0; j<aux.length; j++){
				Base.logger.info("armarBoletaDuplicado: "+aux[j].getLinea());
			}
			lineas = lineas + aux.length;
		}
		ArrayList<LineaVoucher> header = Voucher.armarVoucher(this.datos, Base.getDefVoucher("VoucherBoletaHeader"));
		ArrayList<LineaVoucher> footerDocs = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterDocumentos"));
		if(this.isRecarga()){
			voucher = new LineaVoucher[lineas + header.size() + footerDocs.size() + 6];} 
		else{
			voucher = new LineaVoucher[lineas + header.size() + footerDocs.size() + 4];
		}

		int pos = 0;
		//Agregamos el header
		int l = 0 ;

		for(pos = 0; pos < header.size() ; pos++){
			voucher[pos] = header.get(l);
			l++;
		}

		LineaVoucher line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setLinea("Timbre Electrónico: " + this.getHora() + this.getFecha() + this.getNumeroOperacion() + "-" + this.getAgencia() + "-" + this.getCaja());
		voucher[pos] = line;
		pos++;

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setLinea("COMPROBANTE DE PAGO CONTINGENCIA");
		voucher[pos] = line;
		pos++;

		if(this.isRecargaFija()){
			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setLinea("RECARGA HOGAR");
			voucher[pos] = line;
			pos++;

			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setRight(true);
			line.setLinea(" , ");
			voucher[pos] = line;
			pos++;

		}

		if(this.isRecargaMovil()){
			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setLinea("RECARGA MOVIL");
			voucher[pos] = line;
			pos++;

			line = new LineaVoucher();
			line.setBold(false);
			line.setCenter(true);
			line.setSmall(true);
			line.setRight(true);
			line.setLinea(" , ");
			voucher[pos] = line;
			pos++;

		}

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setRight(true);
		line.setLinea(" , ");
		voucher[pos] = line;
		pos++;

		//Agregamos el body
		for(int i = 0; i < this.getCarroCompras().getDocumentos().size(); i++){
			aux = this.getCarroCompras().getDocument(i).getCustomerVoucher();
			for(int j = 0; j < aux.length; j++){
				if(aux[j].getLinea().trim().contains("RUT: 0-0")){
					aux[j].setLinea("");
				}
				voucher[pos] = aux[j];
				Base.logger.info("Body - voucher["+pos+"]: "+voucher[pos].getLinea());
				pos++;
			}
		}

		//Agregamos el footer
		for(int i = 0; i < footerDocs.size(); i++){
			voucher[pos] = footerDocs.get(i);
			Base.logger.info("Footer - voucher["+pos+"]: "+voucher[pos].getLinea());
			pos++;
		}

		line = new LineaVoucher();
		line.setBold(false);
		line.setCenter(true);
		line.setSmall(true);
		line.setUnderline(true);
		line.setLinea("MEDIOS DE PAGO");
		voucher[pos] = line;
		pos++;

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
		ArrayList<LineaVoucher> boletaDuplicado = new ArrayList<LineaVoucher>();

		// TODO se agrega encabezado para boleta original !!
		line = new LineaVoucher();
		line.setCaps(true);
		line.setBold(true);
		line.setCenter(true);
		line.setLinea("Original - Cliente");
		boleta.add(line);
		//////////////////////////////

		line = new LineaVoucher();
		line.setCaps(true);
		line.setBold(true);
		line.setCenter(true);
		//line.setLinea("Duplicado");
		line.setLinea("Copia");
		boletaDuplicado.add(line);

		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);
			boletaDuplicado.add(voucher[i]);
		}

		Base.logger.info("longitud de boleta: "+boleta.size());
		Base.logger.info("longitud de boleta Duplicado: "+boletaDuplicado.size());

		//Cargamos los vouchers de los medios de pago
		lineas = 0;
		int lineasDuplicado = 0;
		data.setValue("Total", this.getCarroMediosPago().getMontoTotal());
		this.isVueltoBoleta();
		data.setValue("isVuelto", this.getIsVuelto());
		data.setValue("Vuelto", this.getCarroMediosPago().getVuelto(this.getCarroCompras().getMontoTotal()));

		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			// TODO recorro el voucher para desplegar linea por linea
			for(int j=0; j<aux.length; j++){
				Base.logger.info("Voucher MP Cliente: "+aux[j].getLinea());
			}
			lineas = lineas + aux.length;
			aux = this.getCarroMediosPago().getPago(i).getVoucherClienteDuplicado();
			// TODO recorro el voucher para desplegar linea por linea
			for(int j=0; j<aux.length; j++){
				Base.logger.info("Voucher MP Cliente Duplicado: "+aux[j].getLinea());
			}
			lineasDuplicado = lineasDuplicado + aux.length; 
		}

		ArrayList<LineaVoucher> footerPagos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterPagos"));
		ArrayList<LineaVoucher> footerMensaje = null;
		ArrayList<LineaVoucher> footerMensajeDuplicado = null;

		//if(!this.getRecargaMovistar().equals("true")){
		if(!this.isRecarga()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterMensaje"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterMensajeDuplicado"));
		}
		else if(this.isRecargaFija()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaFija"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaFija"));
		}else if(this.isRecargaMovil()){
			footerMensaje = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaMovil"));
			footerMensajeDuplicado = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaFooterRecargaMovil"));
		}

		ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));
		voucher = new LineaVoucher[lineas + footerPagos.size() + footerMensaje.size() + 2  + footerDatos.size()];
		voucherDuplicado = new LineaVoucher[lineasDuplicado + + footerPagos.size() + footerMensajeDuplicado.size() + 2  + footerDatos.size()];
		pos = 0;
		int posDuplicado = 0;
		//Agregamos el body
		for(int i = 0; i < this.getCarroMediosPago().getMediosPago().size(); i++){
			aux = this.getCarroMediosPago().getPago(i).getVoucherCliente();
			for(int j = 0; j < aux.length; j++){
				voucher[pos] = aux[j];
				pos++;
			}
			aux = this.getCarroMediosPago().getPago(i).getVoucherClienteDuplicado();
			for(int j = 0; j < aux.length; j++){
				voucherDuplicado[posDuplicado] = aux[j];
				posDuplicado++;
			}
		}
		//Agregamos el footer
		for(int i = 0; i < footerPagos.size(); i++){
			voucher[pos] = footerPagos.get(i);
			pos++;
			voucherDuplicado[posDuplicado] = footerPagos.get(i);
			posDuplicado++;
		}
		LineaVoucher oper = new LineaVoucher();

		ParamSet pList = Base.getParamSet( "posDat" ); 

		long numeroOper = this.getNumeroOperacion();

		oper.setLinea("Nro. Operación:" + Long.toString( numeroOper ) + "   Operador:" + pList.getStringValue( "Recaudador" ));
		voucher[pos] = oper;
		voucherDuplicado[posDuplicado] = oper;
		posDuplicado++;
		pos++;
		//Agregamos el footer de datos de la caja
		for(int i = 0; i < footerDatos.size(); i++){
			voucher[pos] = footerDatos.get(i);
			pos++;
			voucherDuplicado[posDuplicado] = footerDatos.get(i);
			posDuplicado++;
		}
		//Agregamos la hora y la fecha
		DateFormat df = DateFormat.getDateInstance();
		Time t = new Time(System.currentTimeMillis());
		oper = new LineaVoucher();
		oper.setLinea("Fecha: " + df.format(Calendar.getInstance().getTime())+"    Hora:" + t.toString());
		voucher[pos] = oper;
		pos++;
		voucherDuplicado[posDuplicado] = oper;
		posDuplicado++;

		for(int i = 0; i < footerMensaje.size(); i++){
			voucher[pos] = footerMensaje.get(i);
			pos++;
		}
		for(int i = 0; i < footerMensajeDuplicado.size(); i++){
			voucherDuplicado[posDuplicado] = footerMensajeDuplicado.get(i);
			posDuplicado++;
		}
		//Cargamos ambos body en la boleta final
		for(int i = 0; i < voucher.length ; i++){
			boleta.add(voucher[i]);            
		}
		for(int i = 0; i < voucherDuplicado.length; i++){
			boletaDuplicado.add(voucherDuplicado[i]);
		}

		//TODO validar la forma de reimprimir el original..
		// Se respalda el Voucher Original-Cliente
		Voucher.escribeBoleta(boleta,"boletaCliente.dat");
		Voucher.escribeBoleta(boletaDuplicado,"boletaLocal.dat");

		//TODO Se comenta por pruebas de impresion
		//return boleta;

		map.put("boleta", boleta);
		map.put("boletaDuplicado", boletaDuplicado);
		return (HashMap<String, ArrayList<LineaVoucher>>) map;
	}

	public int confirmarTBK(ICajaView vista, String respVC) {

		String fecha = Tools.getFecha();
		ParamSet pSet = Base.getParamSet("posDat");
		String caja = this.getCaja();
		this.setAgencia(pSet.getStringValue("Agencia"));
		this.setEntidad(pSet.getStringValue("Entidad"));
		this.setCaja(pSet.getStringValue("Caja"));
		this.setDireccion(pSet.getStringValue("Direccion"));
		this.setCajero(pSet.getStringValue("Cajero"));
		this.setUsuario(pSet.getStringValue("Usuario"));
		this.setSesion(pSet.getStringValue("SessionId"));
		this.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
		 String[]voucher = null;
         LineaVoucher vL = null;

		//Se pregunta si el medio de pago es cheque. Si es cheque no se debe rescatar el numero de operación ya que este se recupera antes del franqueo.
		int contCheque = 0;
		for(MedioPago medPag : carroMediosPago.getMediosPago()) {
			if(medPag instanceof ChequeBase && !Base.isEdicion) {
				contCheque++;
			}
		}

		if(contCheque == 0) {

			//Pedimos N°Operacion al servidor

			//ServerProxy pr = Proxy.getProxyInstance();
			AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance(); 

			//NumeroOperacionOut operOut = null;
			NumeroOperacionOutDTO operOut = null;

			//HeaderIn hIn = new HeaderIn();
			HeaderDTO hIn = new HeaderDTO();

			hIn.setAgencia((this.getAgencia()));
			hIn.setCajaFisica((this.getCaja()));
			hIn.setEntidad((this.getEntidad()));
			hIn.setCajero((this.getCajero()));
			hIn.setSession((this.getSesion()));
			hIn.setUsuario(this.getUsuario());
			//hIn.setUsuario(this.getRecaudador());
			hIn.setRecaudador(this.getRecaudador());

			try {
				//operOut= pr.numeroOperacion(new Request(hIn));

				operOut= pr.numeroOperacion(hIn);

			} catch (RemoteException e2) {
				Tools.logStackTrace(Base.logger, e2);        
				Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}  

			//if(operOut.getHeaderOut().getRc() != 0 || operOut.getNumeroOperacion() < 0){   
			if(!("0").equalsIgnoreCase(operOut.getRetCode()) || operOut.getNumeroOperacion() < 0){
				Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}


			// Para edicion, se debe rescatar un Segundo Nro de Operacion...
			if(Base.isEdicion){

				// Aca se setea la Operacion original para generar anulacion de esta !!!
				this.numeroOperacionOriginal = this.numeroOperacion;

				NumeroOperacionOutDTO operOutEdic = null; 
				try {
					operOutEdic = pr.numeroOperacion(hIn);
				} catch (RemoteException e2) {
					Tools.logStackTrace(Base.logger, e2);        
					Base.logger.info("No se pudo obtener en Numero de Operacion para Edicion  !!!");
					this.reversar();
					JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
					return -1;
				}  

				if(!("0").equalsIgnoreCase(operOutEdic.getRetCode()) || operOutEdic.getNumeroOperacion() < 0){
					Base.logger.info("No se pudo obtener en Numero de Operacion para Edicion !!!");
					this.reversar();
					JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
					return -1;
				}

				// TODO se debe setear el nro recuperado en el campo Nuevo para Edicion !!!
				//Guardamos el N° de operación para Edicion Pago
				this.numeroOperacionEdicion = operOutEdic.getNumeroOperacion();
				Base.logger.info("Numero de Operacion Edicion Pago Obtenido: "+this.getNumeroOperacionEdicion());

			}


			//Guardamos el N° de operación
			this.numeroOperacion = operOut.getNumeroOperacion();
			Base.logger.info("Numero de Operacion Obtenido: "+this.getNumeroOperacion());
			//La misma consulta trae el nro de operacion para reversar
			this.numeroOperacionReversa = operOut.getNumeroOperacionReversa();
			Base.logger.info("Numero de Operacion para la Reversa Obtenido: "+this.getNumeroOperacionReversa());
		} else {
			this.numeroOperacion = vista.getDatos().getLongValue("numOper__");
			Base.logger.info("Numero de Operacion Obtenido: "+this.getNumeroOperacion());
			//La misma consulta trae el nro de operacion para reversar
			this.numeroOperacionReversa = vista.getDatos().getLongValue("numOperRes__");
			Base.logger.info("Numero de Operacion para la Reversa Obtenido: "+this.getNumeroOperacionReversa());
		}

		if( this.isRecarga() ){
			// TODO aca NO se autoriza la recarga !!
			// Se debe hacer una vez finalizada OK la Notificacion de Recarga ...
			

		}else if(this.isDevolucion()){

		}

		// TODO se comenta por pruebas de impresion
		/** No se utiliza por cambios en la definicion del metodo de impresion ..
        ArrayList<LineaVoucher> boleta = armarBoletaDuplicado();
		 */
		ArrayList<LineaVoucher> boletaLocal = armarBoletaLocalDuplicado();
		HashMap mapa = null;
		if (this.getCarroCompras().getDocumentos().get(0).getDatos().getStringValue("Tipo").contains("Abono")){
			mapa = armarBoletaDuplicadoAbono();
		}else{
			mapa = armarBoletaDuplicado();
		}
		
		ArrayList<LineaVoucher> boleta = (ArrayList<LineaVoucher>) mapa.get("boleta");
		ArrayList<LineaVoucher> boletaCopia = (ArrayList<LineaVoucher>) mapa.get("boletaDuplicado");


		cuadrarEfectivo();
		for(int i = 0 ; i < this.getCarroMediosPago().getMediosPago().size() ; i++){
			if(this.getCarroMediosPago().getPago(i) instanceof ChequeBase ){
				((ChequeBase)this.getCarroMediosPago().getPago(i)).claveSup = null;
			}
		}        

		int rc;

		//Grabamos la posible reversa
		// TODO validar la implementacion para Claro !!
		this.grabarReversa(true);

		rc = enviarOnline( vista );
		Base.logger.info("RC de respuesta de Notificacion: "+rc); 

		while( true ){
			if(rc == RC_OK){

				// Validar si es una Operacion de recarga ..
				if (this.isRecarga()){

					ValidarRecargaWSServerProxy rec = AppControlRecargaProxy.getProxyInstance();
					org.example.www.ValidarRecargaWS.Respuesta respRec = new org.example.www.ValidarRecargaWS.Respuesta();

					//HeaderIn hIn = new HeaderIn();
					org.example.www.ValidarRecargaWS.Caja cajaRec = new org.example.www.ValidarRecargaWS.Caja();

					//OperacionIn oIn = new OperacionIn();
					org.example.www.ValidarRecargaWS.OperacionIn opIn = new org.example.www.ValidarRecargaWS.OperacionIn();

					//hIn.setAgencia(Integer.parseInt(this.getAgencia()));
					cajaRec.setAgencia(this.getAgencia());
					Base.logger.info( "Agencia: (Rec) " + cajaRec.getAgencia());    
					//hIn.setCajaFisica(Integer.parseInt(this.getCaja()));
					cajaRec.setIdCaja(Integer.parseInt(this.getCaja()));
					Base.logger.info( "Caja: (Rec) " + cajaRec.getIdCaja());    
					//hIn.setEntidad(Integer.parseInt(this.getEntidad()));
					cajaRec.setEntidad(this.getEntidad());
					Base.logger.info( "Entidad: (Rec) " + cajaRec.getEntidad());   

					cajaRec.setCodigoSesion(Long.parseLong(this.getSesion()));
					Base.logger.info( "Session: (Rec) " + cajaRec.getCodigoSesion()); 

					cajaRec.setUsuario(this.getCajero());
					//cajaRec.setUsuario(this.getRecaudador());
					//cajaRec.setUsuario(this.getUsuario());
					Base.logger.info( "Usuario: (Rec) " + cajaRec.getUsuario()); 

					//hIn.setRecaudador(this.getRecaudador());	
					// TODO se debe rescatar desde inicicalizacion de cja !!!
					cajaRec.setRecaudador(this.getRecaudador());
					Base.logger.info( "Recaudador: (Rec) " + cajaRec.getRecaudador()); 

					// TODO se debe rescatar desde inicicalizacion de cja !!!
					cajaRec.setCanal(new Long(this.getCanal()).intValue());
					//caja.setCanal(1);
					Base.logger.info( "Canal: (Rec) " + cajaRec.getCanal()); 

					if(vista.getOperTRV().getCarroCompras().getMontoTotal() < 0){
						//operCaja.setMonto(-1*operTRV.getCarroCompras().getMontoTotal());
						opIn.setMonto(-1*vista.getOperTRV().getCarroCompras().getMontoTotal());
					} else {
						//operCaja.setMonto(operTRV.getCarroCompras().getMontoTotal());
						opIn.setMonto(vista.getOperTRV().getCarroCompras().getMontoTotal());
					}
					Base.logger.info( "Monto: (Rec) " + opIn.getMonto()); 

					//operCaja.setNumeroOperacion(numeroOperacion);
					// TODO como se setea el tipo de operacion para recargas ????
					if(this.isRecargaFija())
						opIn.setTipoOperacion("2");
					else if(this.isRecargaMovil())
						opIn.setTipoOperacion("1");
					else {
						Base.logger.info( "No es un tipo de Recarga valida!!!");
						opIn.setTipoOperacion("0");
					}

					Base.logger.info( "Tipo Operacion: (Rec): " + opIn.getTipoOperacion()); 

					//opIn.setValorOperacion(""+numeroOperacion);
					opIn.setValorOperacion(vista.getOperTRV().getCarroCompras().getDocument(0).getDatos().getStringValue("FolioDocumentoClaro"));
					Base.logger.info( "Nro. Operacion: (Rec): " + opIn.getValorOperacion()); 

					// TODO: se debe agregar al nuevo DTO regenerado el nro de operacion registrado...
					opIn.setNumOperacionPago(this.numeroOperacion);
					Base.logger.info( "Nro. Operacion Pago: (Rec): " + opIn.getNumOperacionPago()); 

					try {
						// TODO aca debe invocar a Recarga  !!!
						//resp = pr.envioOperacion(oIn);
						respRec = rec.validarRecarga(opIn, cajaRec);
						Base.logger.info("Codigo Resp. Recarga: "+respRec.getRetCode());
						Base.logger.info("Msje Resp. Recarga: "+respRec.getRetDesc());

						if(respRec.getRetCode() == 0){
							//Se envio ok, pasamos a la impresión del voucher
							break;
						}else{

							//reversamos el medios de pago Efectivo y eliminamos el carro
							Base.logger.error("Codigo Resp. Recarga: "+respRec.getRetCode());
							Base.logger.error("Ocurrio un error al realizar la Recarga... reversar la Transaccion !!!");
							Voucher.borraBoleta("boletaCliente.dat");
							Voucher.borraBoleta("boletaLocal.dat");
							Voucher.borraBoleta("boletaTbk.dat");

							this.reversar();
							JOptionPane.showMessageDialog(null, "No se pudo realizar la recarga\nSe cancelará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
							//mover reversa, Se genera archivo de SAF 
							Base.revisarReversasPago();
							return -1;
						}


					} catch (RemoteException e) {
						Tools.logStackTrace(Base.logger, e);
						// Si se genero un error en la recarga.. se debe reversar la Notificacion asociada..
						//rc = RC_CANCELAR;
						Base.logger.error("Ocurrio un error al invocar la Recarga... reversar la Transaccion !!!");
						//reversamos el medios de pago Efectivo y eliminamos el carro
						Voucher.borraBoleta("boletaCliente.dat");
						Voucher.borraBoleta("boletaLocal.dat");
						Voucher.borraBoleta("boletaTbk.dat");
						this.reversar();
						JOptionPane.showMessageDialog(null, "No se pudo realizar la recarga\nSe cancelará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
						//mover reversa, Se genera archivo de SAF 
						Base.revisarReversasPago();
						return -1;

					}

				}else{
					// No corresponde a una recarga..
					//Se envio ok, pasamos a la impresión del voucher
					break;
				}

			}
			else if( rc == RC_REINTENTAR){
				//reintentamos mandar el mismo pago
				rc = enviarOnline( vista );
			}
			else if( rc == RC_REVERSAR){
				//Solo reversamos los medios de pago y eliminamos el carro, no debe generar SAF !!
				Voucher.borraBoleta("boletaCliente.dat");
				Voucher.borraBoleta("boletaLocal.dat");
				Voucher.borraBoleta("boletaTbk.dat");
				// El reversar solo elimina el carro de Medios de Pago
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				return -1;
			}
			else if ( rc == RC_CANCELAR ){
				//reversamos los medios de pago y eliminamos el carro
				Voucher.borraBoleta("boletaCliente.dat");
				Voucher.borraBoleta("boletaLocal.dat");
				Voucher.borraBoleta("boletaTbk.dat");
				// El reversar solo elimina el carro de Medios de Pago
				this.reversar();
				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe cancelará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
				//mover reversa, Se genera archivo de SAF 
				Base.revisarReversasPago();
				return -1;
			}
		}

		//La aplicacion de Control confirmo el pago, eliminamos la reversa que habiamos armado
		this.grabarReversa(false);     

		for(int i = 0 ; i < this.getCarroMediosPago().getMediosPago().size() ; i++){
			if(this.getCarroMediosPago().getPago(i) instanceof Tarjeta ){
				//Confirmar TBK
				break;
			}
		}

		// Primer paso es imprimir la Boleta Original de Pago.
		DatosFileNet datosFileNet = new DatosFileNet();
		datosFileNet.setCodigo_sesion(pSet.getStringValue("SessionId"));
		datosFileNet.setCodusuario_envia(pSet.getStringValue("CodigoRecaudador"));
		datosFileNet.setEmail_para(vista.getEntryText() );
		datosFileNet.setNumOperacion(String.valueOf(this.numeroOperacion));
		datosFileNet.setPropietario(pSet.getStringValue("Usuario"));
		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_PAGO);
		
		
		imprimirBoleta(boleta, Voucher.COPIA_CLIENTE,datosFileNet);

		// Segundo paso es imprimir la Copia de la Boleta de Pago.
		imprimirBoleta(boletaCopia, Voucher.COPIA_LOCAL,datosFileNet);

		// Tercer paso es imprimir Premios, si es que arrojo la Trx TBK
		imprimirPremiosCliente();

		// TODO se comenta ya que siempre se imprime una copia !!!
		
        try{
        	// Tercer paso es imprimir el duplicado de la Boleta de Pago si esta configurado para hacerlo.
	        if(pSet.getStringValue("isDuplicado").equals("si")){
	        	Base.logger.info("Imprimir Duplicado de Boleta !!!!!");
	        	ReImpresion rImpresion = new ReImpresion();
	        	rImpresion.execute(vista, 0, new Datos());
	        	Base.logger.info("Finalizo Imprimir Duplicado de Boleta !!!!!");
	        }
        }catch(Exception e){
        	Base.logger.error("Ocurrio un error al imprimir la copia de la Boleta "+e.getMessage());
        }

		// Cuarto paso es imprimir la boleta de Pagos arrojada por TBK
		imprimirBoletaPagos(boletaLocal, Voucher.COPIA_TBK_CLIENTE,datosFileNet);

		// TODO: como se debe imprimir 2 veces el voucher original de TBK,
		// se invoca nuevamente a la impresion de Boleta de Pagos..
		imprimirBoletaPagos(boletaLocal, Voucher.COPIA_TBK_LOCAL,datosFileNet);
		
		

		if(this.isDevolucion()){
			JOptionPane.showMessageDialog(null, "Devolución Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(this.isRecarga()){
			JOptionPane.showMessageDialog(null, "Recarga Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		}else{
			Voucher.borraBoleta("boletaTbk.dat");
			 URLString urlVC = new URLString(respVC);
        	 if(urlVC.getValor("TXCRET").equals("00") && Integer.parseInt(urlVC.getValor("AUTRET")) <= 9){ 
                 // Si TRX premiada
        		 if (!urlVC.getValor("VOUCHERPREMIO").equalsIgnoreCase("")) {
        			 
        			// vista.showBusyWindow("Transaccion Premiada", "Espere por favor...");
        			 
        			 String str = urlVC.getValor("VOUCHERPREMIO");
        			 if (str.contains("%20%20"))
        			 {
        			 str = URLDecoder.decode(str);
        			 }
        			 voucher = str.split("\\n");
                     voucherPremioCliente = new LineaVoucher[voucher.length];
                     voucherPremioComercio = new LineaVoucher[voucher.length];
                     for(int i = 0 ; i < voucher.length ; i++){
                         vL = new LineaVoucher();
                         vL.setLinea(voucher[i].replaceAll("\\r", ""));
                         vL.setSmall(true);
                         voucherPremioComercio[i] = vL;
                         voucherPremioCliente[i] = vL;
                         Base.logger.info("Linea Voucher Premio: ["+vL.getLinea()+"]");
                     } 
                     generaComprobante(voucherPremioCliente);
                     
                     Voucher.printVoucher(comprobante, false, Voucher.COPIA_PREMIO_CLIENTE,null);
                     
                     Base.logger.info("--------------  ---------------- datos del emmail: "+vista.getDatos().getStringValue("emails"));
                     try {
 						Thread.sleep(3000);
 					} catch (InterruptedException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
                 //    vista.hideBusyWindow();
        		 }
 
        		 Base.logger.info("P I N P A D - new y obtiene vouchers");
                //[REspinoza] Multicard formatea voucher cliente y comercio
                TbkMetodos metodos = new TbkMetodos();
        		String strVoucherCliente = metodos.ObtenerVouchersClienteComercio(urlVC.getValor("VOUCHER"));
        		URLString urlVoucher = new URLString(strVoucherCliente);
        		 
        		String vcom = urlVoucher.getValor("VOUCHER_COMERCIO");
        		if (vcom.contains("%20%20"))
        		{
        		  vcom = URLDecoder.decode(vcom);
        		}
        		voucher = vcom.split("\\n");

                voucherComercio = new LineaVoucher[voucher.length];
                 for(int i = 0 ; i < voucher.length ; i++){
                     vL = new LineaVoucher();
                     vL.setLinea(voucher[i].replaceAll("\\r", ""));
                     vL.setSmall(true);
                     voucherComercio[i] = vL;
                     Base.logger.info("Linea Voucher Copia Comercio: ["+vL.getLinea()+"]");
                 }
               generaComprobante(voucherComercio);

               /*
                * [REspinoza] No se guarda voucher transbank local, 
                * se guarda el numero único para consulta transbank
                */
            	generaDatosPagoTbk(vista, urlVC);	
	           	
                 ArrayList<LineaVoucher> originalTbk = comprobante; //primero se imprimer el copia cliente, guardo el original

                 // Voucher pago copia cliente
//                 voucher = strVoucherCliente.split("\\n");
                 
                 String vcli = urlVoucher.getValor("VOUCHER_CLIENTE");
                 if (vcli.contains("%20%20"))
                 {
                   vcli = URLDecoder.decode(vcli);
                 }
                 voucher = vcli.split("\\n");
                 voucherCliente = new LineaVoucher[voucher.length];
                 
                 for(int i = 0 ; i < voucher.length ; i++){
                     vL = new LineaVoucher();
                     vL.setLinea(voucher[i].replaceAll("\\r", ""));
                     vL.setSmall(true);
                     voucherCliente[i] = vL;
                     Base.logger.info("Linea Voucher Copia Cliente: ["+vL.getLinea()+"]");
                 }
                 
                 generaComprobante(voucherCliente);
                 
         		//primero voucher cliente y despues original
                 Voucher.printVoucher(comprobante, false, Voucher.COPIA_TBK_CLIENTE,datosFileNet);
                 Voucher.printVoucher(originalTbk, false, Voucher.COPIA_TBK_LOCAL,datosFileNet);
                 
                 Base.logger.info("P I N P A D - confirma");
                 metodos.confirmarOperacion(Base.tbk);
                 
             } else {
            	 Voucher.borraBoleta("boletaTbk.dat"); 
            	// vista.hideBusyWindow();
//            	 TbkMetodos metodos = new TbkMetodos();
//            	 metodos.cierre(Base.tbk, requerimiento)
                 Base.logger.error("No se pudo realizar el pago");
                 String mensaje = Format.getMesageTbk(urlVC);

                 mensaje = mensaje.replaceAll("%20", " ");
                 
                 if(mensaje.trim().equalsIgnoreCase("")){
                	 mensaje = "Error al realizar pago Transbank, reintentar...";
                 }                            
                 
                 JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.INFORMATION_MESSAGE);
                
             }
        	//vista.hideBusyWindow();
			JOptionPane.showMessageDialog(null, "Pago Realizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		ParamSet posCfg = Base.getParamSet("posCfg"); 
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
    	
    	if(isPdf) {		
    		enviarBoletaFilenet(this.numeroOperacion);		
    		enviarCorreoSilverpop(this.numeroOperacion,vista.getEntryText());
    	}

		borrarPayments();  

		return 0;
	}
	
	/*
	 * [REspinoza] Guardar datos pago en archivo local para reimpresion 
	 * consulta transbank directa
	 */
	private void generaDatosPagoTbk(ICajaView vista, URLString urlVC) {
       	ArrayList<LineaVoucher> respaldo = new ArrayList<LineaVoucher>();
       	LineaVoucher lineaTrx = new LineaVoucher();
       	String origen = null;
       	String tarjeta = null;
       	String tipoTarjeta  = null;
       	
       	try {
       		tipoTarjeta = vista.getOperTRV().getDatos().getStringValue("tipoTarjeta");
            tarjeta = urlVC.getValor("FAMTRX");
            if(tarjeta.equalsIgnoreCase("DEBITO")){
            	origen="DB";
            }else if(tarjeta.equalsIgnoreCase("CREDIT")){
//           	 if (tipoTarjeta.equalsIgnoreCase("TR") || tipoTarjeta.equalsIgnoreCase("NB")) {
//           		origen="NB";
//           	 	} else{
           	 	origen="CR";
//           	 	}
            }
	       	
	       	lineaTrx.setLinea("PagoDeuda" + "|"
	       			+ vista.getOperTRV().getDatos().getStringValue("idTrxTbk") + "|"
//	       			+ vista.getOperTRV().getDatos().getStringValue("tipoTarjeta") + "|"
	       			+ origen);
	       	
	       	respaldo.add(lineaTrx);
	       	Voucher.escribeBoleta(respaldo,"boletaTbk.dat");
       	} catch (Exception e) {
       		Base.logger.error("generaDatosPagoTbk() Ocurrio un error al registrar datos tarjeta " + e.getMessage());
		}
		
	}

	
	
}
