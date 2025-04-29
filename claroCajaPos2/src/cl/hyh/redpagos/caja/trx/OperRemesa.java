package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.proxy.AppControlNotificarProxy;
import ws.claro.cl.proxy.AppControlProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
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
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.ServicioOper;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;

public class OperRemesa extends OperAdmin implements ITrxBase, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String fechaStr;
    
    int estado = 0;
    
    public OperRemesa(){
        this.setServicio("EnvioRemesa");
    }
    
    /**
	 * Al inicio verificamos si existe o no una fecha de sesión almacenada 
	 * en posDat, de existir quiere decir que iniciamos el proceso de cierre 
	 * de una fecha anterior.
	 */
	@Override
	public void init(Datos htParam) {
		ParamSet pSet = Base.getParamSet("posDat");
		SimpleDateFormat fechaSession = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date fecha = null;
		try {
			String fechaSessionStr = pSet.getStringValue("FechaSession");

			if (!"".equals(fechaSessionStr.trim())) {
				fecha = fechaSession.parse(fechaSessionStr);
				fechaStr = formatter.format(fecha);
			} else {
				fechaStr = Tools.getFecha();
			}
		} catch (ParseException e) {
			Base.logger
					.error("Se produjo un error en la base de datos post Invocar al servicio de Registro Sencillo..."
							+ e);
		}
	}
    
    public int execute(ICajaView vista, int key, Datos htParam){
        if( key == 0 ) {            
            estado = 0;
            //vista.setInputTimeout(15);
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            case 0:
                estado = 1;
                vista.hideAllEntries();
                vista.setEntryMessage( "Seleccione tipo remesa", true );
                vista.setEntryTextLabel("Seleccione tipo remesa:", true);
                String []opt = new String[2];
                opt[0] = "Efectivo";
                opt[1] = "Cheque";
               // opt[2] = "Vale Vista";
                vista.setEntryList(opt, true, true);
                return ICajaView._WAITFORACTION;
            case 1:
                int index = vista.getEntryListIndex();
                if(index == 0){
                    estado = 2;
                    return ICajaView._NOWAITFORACTION;
                }
                else if(index == 1){
                    estado = 4;
                    return ICajaView._NOWAITFORACTION;
                }
                else if(index == 2){
                	//estado = 8;
                	estado = 6;
                	return ICajaView._NOWAITFORACTION;
                }
                else{
                    estado = 6;
                    return ICajaView._NOWAITFORACTION;
                }
            case 2:
                estado = 3;
                vista.setEntryMessage( "Ingrese monto efectivo", true );
                vista.setEntryTextLabel("Ingrese monto efectivo:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,12}", "Monto no válido");
                return ICajaView._WAITFORACTION;                
            case 3:
                long monto = Long.parseLong(vista.getEntryText());
                this.getDatos().setValue("Monto", monto);
                this.getDatos().setValue("TipoTotal", "mpEfectivo");
                confirmar(vista);
                return 13;
            case 4:
                estado = 5;
                vista.setEntryMessage( "Ingrese monto cheques", true );
                vista.setEntryTextLabel("Ingrese monto cheques:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,12}", "Monto no válido");
                return ICajaView._WAITFORACTION;
            case 5:
                monto = Long.parseLong(vista.getEntryText());
                this.getDatos().setValue("Monto", monto);
                this.getDatos().setValue("TipoTotal", "mpCheque");
                confirmar(vista);
                return 13;
            case 6:
                estado = 7;
                vista.setEntryMessage( "Ingrese monto vales vista", true );
                vista.setEntryTextLabel("Ingrese monto vales vista:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,12}", "Monto no válido");
                return ICajaView._WAITFORACTION;
            case 7:
                monto = Long.parseLong(vista.getEntryText());
                this.getDatos().setValue("Monto", monto);
                this.getDatos().setValue("TipoTotal", "mpValeVista");
                confirmar(vista);
                return 13;
            case 8:
                estado = 9;
                vista.setEntryMessage( "Ingrese monto cheques", true );
                vista.setEntryTextLabel("Ingrese monto cheques:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,12}", "Monto no válido");
                return ICajaView._WAITFORACTION;
            case 9:
                monto = Long.parseLong(vista.getEntryText());
                this.getDatos().setValue("Monto", monto);
                this.getDatos().setValue("TipoTotal", "mpChequeFecha");
                confirmar(vista);
                return 13;
                
        }
        return 0;
    }
    
    public void confirmar(ICajaView vista){
    	String fecha = fechaStr;
        ParamSet pSet = Base.getParamSet("posDat");
        
        //Pedimos N°Operacion al servidor
        
        //ServerProxy pr = Proxy.getProxyInstance();
        AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance(); 
        
        //NumeroOperacionOut operOut = null;
        NumeroOperacionOutDTO operOut = null;
        
        //HeaderIn hIn = new HeaderIn();
        HeaderDTO hIn = new HeaderDTO();
        
    	hIn.setAgencia(pSet.getStringValue("Agencia"));
		hIn.setCajaFisica(pSet.getStringValue("Caja"));
		hIn.setEntidad(pSet.getStringValue("Entidad"));
		hIn.setCajero(pSet.getStringValue("Cajero"));
		hIn.setSession(pSet.getStringValue("SessionId"));
		hIn.setUsuario(pSet.getStringValue("Cajero"));
		//hIn.setUsuario(pSet.getStringValue("CodigoRecaudador"));
		hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
		
        try {
        	//operOut= pr.numeroOperacion(new Request(hIn));
        	operOut= pr.numeroOperacion(hIn);
		} catch (RemoteException e2) {
			 Tools.logStackTrace(Base.logger, e2);                  
             this.reversar();
             JOptionPane.showMessageDialog(null, "No se pudo realizar la remesa\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
             return;
		}   
		
        //if(operOut.getHeaderOut().getRc() != 0 || operOut.getNumeroOperacion() < 0){
        if(!("0").equalsIgnoreCase(operOut.getRetCode()) || operOut.getNumeroOperacion() < 0){  	
            this.reversar();
            JOptionPane.showMessageDialog(null, "No se pudo realizar la remesa\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //Guardamos el N° de operación
        this.numeroOperacion = operOut.getNumeroOperacion();
        this.numeroOperacionReversa = operOut.getNumeroOperacionReversa();
        
        this.setNumeroOperacion(numeroOperacion);
        vista.showBusyWindow("Enviando", "Espere por favor...");
        
        int rc = enviarRemesa( vista );
        
        while( true ){
            if(rc == RC_OK){
                //Se envio ok, pasamos a la Impresión del voucher
                break;
            }
            else if( rc == RC_REINTENTAR){
                //reintentamos mandar la misma remesa
                rc = enviarRemesa( vista );
            }
            else if ( rc == RC_CANCELAR ){
            	//Reversar Remesa
            	vista.hideBusyWindow();
                JOptionPane.showMessageDialog(null, "Falló el envio de remesa\nVuelva a realizarla", "Error Envio", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        
        vista.hideBusyWindow();
        //Imprimimos un voucher para la remesa
        this.imprimirBoleta(vista);
        JOptionPane.showMessageDialog(null, "Operación Realizada", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        
        return;
    }
    
    public int enviarRemesa( ICajaView vista ){
        ParamSet pList = Base.getParamSet( "posDat" );
        ParamSet pSet = Base.getParamSet( "posCfg" );    
        
        this.agencia = pList.getStringValue("Agencia");
        this.entidad = pList.getStringValue("Entidad");
        this.usuario = pList.getStringValue("Usuario");
        this.caja = pList.getStringValue("Caja");        
        this.cajero = pList.getStringValue("Cajero");
        this.sesion = pList.getStringValue("SessionId");
        this.recaudador = pList.getStringValue("CodigoRecaudador");
        this.fecha = fechaStr;
        
        this.setNumeroOperacion(numeroOperacion);       
        
        this.canal = new Long(pList.getStringValue("Canal"));
        
        int rc = this.processOperAdmin(this);
        
        if (rc < 0){
       	 	Base.logger.info("Error arrojado por App de Control (Rem)!!!");
            if( vista.showMyConfirmDialog("Confirme por favor","Error en envío de pago\nDesea reintentar el envió?")
                    == JOptionPane.NO_OPTION  ) {
                return RC_CANCELAR;
            }
            else{
                return RC_REINTENTAR;
            }
       }else if (rc == 0){
       	 //Todo correcto, pasamos a la Impresión de los comprobantes
           Base.logger.info("Pago de Remesa ejecutado correctamente (Rem)");
           return RC_OK; 
       }
        
        /**
        switch( rc ){
            case -100:
                //Si fallo la conexión ofrecemos reintentar el envió
                Base.logger.info("Error de conexion");
                if( vista.showMyConfirmDialog("Confirme por favor","Error en envío de remesa\nDesea reintentar el envió?")
                        == JOptionPane.NO_OPTION  ) {
                    return RC_CANCELAR;
                }
                else{
                    return RC_REINTENTAR;
                }
            case Servicio.RC_OK:
                //Todo correcto, pasamos a la Impresión de los comprobantes
                Base.logger.info("Pago ejecutado correctamente");
                return RC_OK;                 
        }
        */
        
        return -1;
    }
    
    public int processOperAdmin(OperAdmin operAdmin){
    	
    	PlataformaPagoNotificarServerProxy pr = AppControlNotificarProxy.getProxyInstance();
         
    	Operacion opIn = new Operacion();
    
    	Caja caja = new Caja();
    
    	caja.setAgencia(this.getAgencia());
    	Base.logger.info( "Agencia (Rem): " + caja.getAgencia()); 
		caja.setIdCaja(Integer.parseInt(this.getCaja()));
		Base.logger.info( "Caja (Rem): " + caja.getIdCaja());  
		caja.setEntidad(this.getEntidad());
		Base.logger.info( "Entidad (Rem): " + caja.getEntidad()); 
		caja.setCodigoSesion(Long.parseLong(this.getSesion()));
		Base.logger.info( "Session (Rem): " + caja.getCodigoSesion()); 
		//caja.setUsuario(this.getRecaudador());
		caja.setUsuario(this.getCajero());
		Base.logger.info( "Usuario (Rem): " + caja.getUsuario()); 
		caja.setRecaudador(this.getRecaudador());
		Base.logger.info( "Recaudador (Rem): " + caja.getRecaudador()); 
		caja.setCanal(new  Long(this.getCanal()).intValue());
		Base.logger.info( "Canal (Rem): " + caja.getCanal()); 
		
		opIn.setCaja(caja);
		
		opIn.setFechaPago(fechaStr);
		Base.logger.info( "Fecha (Rem): " + opIn.getFechaPago()); 
		opIn.setMonto(this.getDatos().getLongValue("Monto"));
		Base.logger.info( "Monto (Rem): " + opIn.getMonto());
		
		opIn.setNumeroOperacion(numeroOperacion);
		Base.logger.info( "Nro operacion (Rem): " + opIn.getNumeroOperacion()); 
		
		// Se setea el tipo de Operacion correspondiente a las Remesas.
		String op = "";
		if(this.getDatos().getStringValue("TipoTotal").substring(2).equals("Efectivo")){
			op = "6";
		}
		else if(this.getDatos().getStringValue("TipoTotal").substring(2).equals("Cheque")){
			op = "9";
		}
		/**
		else if(this.getDatos().getStringValue("TipoTotal").substring(2).equals("ChequeFecha")){
			op = "10";
		}
		else if(this.getDatos().getStringValue("TipoTotal").substring(2).equals("ValeVista")){
			op = "11";
		}
		*/
		
		opIn.setTipoOperacion(new Integer(op).intValue()); // validar que tipo de operacion debe ser ??
		Base.logger.info( "Tipo Operacion (Rem): " + opIn.getTipoOperacion()); 
		
		Transaccion []trxsClaro = new Transaccion[1];
		trxsClaro[0] = new Transaccion();
		Tools.initTrxClaro(trxsClaro[0]);
		
		trxsClaro[0].setTipoTransaccion("Remesa" + this.getDatos().getStringValue("TipoTotal").substring(2));
		Base.logger.info( "Tipo Trx (Rem): " + trxsClaro[0].getTipoTransaccion());
		trxsClaro[0].setMonto(this.getDatos().getLongValue("Monto"));
		Base.logger.info( "Monto Trx (Rem): " + trxsClaro[0].getMonto());
		
		opIn.setTransaccion(trxsClaro);
		
		NotificacionEnvio notif = new NotificacionEnvio();
		notif.setOperacion(opIn);
		
		NotificacionRespuesta resp = null;
		
		try {
			resp = pr.notificar(notif);
			Base.logger.info("Codigo Notificacion (Rem): "+resp.getRespuesta().getRetCode());
			Base.logger.info("Msje Notificacion (Rem): "+resp.getRespuesta().getRetDesc());
		} catch (RemoteException e2) {
			Tools.logStackTrace(Base.logger, e2);
			return -100;
		}
		
		if(resp.getRespuesta().getRetCode() != 0){
			Base.logger.error(resp.getRespuesta().getRetDesc());
			//return -100;
		}
		
		return resp.getRespuesta().getRetCode();
    }
    
    public void imprimirBoleta(ICajaView vista){
        LineaVoucher []voucher = null;
        
        Datos data = new Datos();
        data.setValue("Monto", this.getDatos().getLongValue("Monto"));      
        data.setValue("TipoTotal", this.getDatos().getStringValue("TipoTotal"));
        
        ArrayList<LineaVoucher> header = Voucher.armarVoucher(this.datos, Base.getDefVoucher("VoucherBoletaHeader"));
        ArrayList<LineaVoucher> body = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherRemesa"));
        ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));

        
        voucher = new LineaVoucher[body.size() + header.size() + footerDatos.size() + 1 + 1];
        int pos = 0;
        //Agregamos el header
        int l = 0 ;
        for(pos = 0; pos < header.size(); pos++){
            voucher[pos] = header.get(l);
            l++;
        }
        l = 0;
        for(l = 0; l < body.size(); l++){
            voucher[pos] = body.get(l);
            pos++;
        }
               
        LineaVoucher oper = new LineaVoucher();
        ParamSet pList = Base.getParamSet( "posDat" ); 
        /**
        // TODO cbriones. este nro de operacion no se utiliza !!!
        long numOper = Long.parseLong(pList.getStringValue( "NumeroOperacion" ));
        numOper = numOper - 1 ;
        */
        oper.setLinea("N°Operación:" + numeroOperacion + "   Operador:" + pList.getStringValue( "Cajero" ));
        voucher[pos] = oper;
        pos++;
        l = 0;
        for(l = 0; l < footerDatos.size(); l++){
            voucher[pos] = footerDatos.get(l);
            pos++;
        }
        //Agregamos la hora y la fecha
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());
        oper = new LineaVoucher();
        oper.setLinea("Fecha: " + df.format(Calendar.getInstance().getTime())+"    Hora:" + t.toString());
        voucher[pos] = oper;
        pos++;        
        
        ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
        for(int i = 0; i < voucher.length ; i++){
            boleta.add(voucher[i]);
        }
        
        vista.showBusyWindow("Enviando Remesa", "Espere por favor...");
        
        ParamSet pSet = Base.getParamSet("posDat");

		DatosFileNet datosFileNet = new DatosFileNet();
		datosFileNet.setCodigo_sesion(pSet.getStringValue("SessionId"));
		datosFileNet.setCodusuario_envia(pSet.getStringValue("CodigoRecaudador"));
		datosFileNet.setEmail_para(null);
		datosFileNet.setNumOperacion(null);
		datosFileNet.setPropietario(pSet.getStringValue("Usuario"));
		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_REMESA);
		
        Voucher.printVoucher(boleta,true, Voucher.COPIA_REMESA,datosFileNet); 
        
        // TODO cbriones: se le agregan 30 espacios al final de la Boleta ???
        // Se dejaran 10 para pruebas mientras se valida esto !!!
        //for(int i = 0; i < 30 ; i++){
        for(int i = 0; i < 10 ; i++){	
            oper = new LineaVoucher();
            oper.setLinea("");
            boleta.add(oper);
        }
        
        Voucher.printVoucherDos(boleta, true);
        vista.hideBusyWindow();
    }
}
