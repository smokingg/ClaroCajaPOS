package cl.hyh.redpagos.caja.mpago;

import java.rmi.RemoteException;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.BancoDTO;
import ws.claro.cl.ConsultarAprobacionChInDTO;
import ws.claro.cl.ConsultarAprobacionChOutDTO;
import ws.claro.cl.ConsultarBancosOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.proxy.AppControlConsultarProxy;
import ws.claro.cl.proxy.AppControlProxy;
import ws.claro.cl.proxy.AppControlValidarChequeProxy;

import jpos.JposException;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.clarochile.osbservicios.ValidarChequeWS.Caja;
import cl.clarochile.osbservicios.ValidarChequeWS.OperacionIn;
import cl.clarochile.osbservicios.ValidarChequeWS.Respuesta;
import cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSServerProxy;
import cl.hyh.cajas.ws.impl.AutorizaChequeIn;
import cl.hyh.cajas.ws.impl.AutorizaChequeOut;
import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.pos.BasePos;
import cl.hyh.redpagos.caja.pos.PosDeviceException;
import cl.hyh.redpagos.caja.trx.CodigoAutorizacion;

/**
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ChequeBase extends MedioPago{
    protected int estado = 0;
    public CodigoAutorizacion claveSup = null;
    boolean isRut = true;
    public static final int _CHEQUE_DIA = 0;
    public static final int _CHEQUE_FECHA = 1;
    public static final int _PRINT_OK = 2;
    public static final int _PRINT_FAIL = 3;    
    
    protected void impresionCheque(ICajaView vista){
        vista.hideAllEntries();
        vista.setEntryTitle( "IMPRESION CHEQUE", true );
        vista.setEntryTextLabel( "Ingrese cheque para impresion", true );
        /*try {
            Base.pos.printer.openDevice();
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
        }*/
    }
    
    protected int printCheque(ICajaView vista){
        boolean ok = false;
        try {
            Base.pos.printer.openDevice();
            Tools.espera(1);
            String fecha = this.datos.getStringValue("Fecha");
            Base.logger.info("Fecha del Cheque (1): "+fecha);
            fecha =  fecha.replace("-", "");
            Base.logger.info("Fecha del Cheque (2): "+fecha);
            /**
            int dia = Integer.parseInt(this.datos.getStringValue("Fecha").substring(6,8));            
            int mes = Integer.parseInt(this.datos.getStringValue("Fecha").substring(4,6));
            int agno = Integer.parseInt(this.datos.getStringValue("Fecha").substring(0,4));
            */
            
            int dia = Integer.parseInt(fecha.substring(6,8));            
            int mes = Integer.parseInt(fecha.substring(4,6));
            int agno = Integer.parseInt(fecha.substring(0,4));
            
            Base.pos.printer.printCheck(this.getMonto(), this.datos.getStringValue("Nombre"), dia, mes, agno);            
        } catch (PosDeviceException e) {            
            Base.logger.error(e.toString());
        } catch (JposException e) {
            Base.logger.error(e.toString());
        }
        finally{
            if( Base.pos.printer.isOn() ){
                try {
                    Base.pos.printer.close();
                    Base.pos.printer.setOn(false);
                } catch (PosDeviceException e1) {
                    Tools.logStackTrace(Base.logger, e1);
                }
            }
        }
        if( ok == true){
            return this._PRINT_OK;          
        }
        else{
            return this._PRINT_FAIL;
        }
    }
    
    protected void franqueoCheque(ICajaView vista){
        vista.hideAllEntries();
        vista.setEntryTitle( "FRANQUEO CHEQUE", true );
        vista.setEntryTextLabel( "Ingrese cheque para franqueo", true );
        /*try {
            Base.pos.printer.openDevice();
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
        }*/
    }
    
    protected int printFranqueo(){
        boolean ok = false;
        try {
            ParamSet posDat = Base.getParamSet("posDat");
            Base.pos.printer.openDevice();
            Tools.espera(1);
            
            
            if(posDat.getStringValue("printer").equals("5")){
                Base.pos.printer.printFranqueo(this.datos.getStringValue("Rut"), this.datos.getStringValue("Telefono"),this.datos.getStringValue("CodigoAutorizacion")
                            ,/*posDat.getLongValue("NumeroOperacion")*/this.datos.getLongValue("numOper__"),Format.formatMonto(this.datos.getLongValue("Monto")),posDat.getStringValue("Usuario")
                            //,Format.formatFechaBoleta(Tools.getFecha()),Format.formatHoraPresto(Tools.getTime()),posDat.getLongValue("Entidad")
                            ,Tools.getFecha().substring(0,10),Format.formatHoraPresto(Tools.getTime()),posDat.getLongValue("Entidad")
                            ,posDat.getLongValue("Agencia"),posDat.getLongValue("Caja"),posDat.getStringValue("cuentaDeposito")
                            ,posDat.getStringValue("bancoDeposito"), this.datos.getLongValue("codigoRechazo"));
            }
            else{
                Base.pos.printer.printFranqueo(this.datos.getStringValue("Rut"), this.datos.getStringValue("Telefono"),this.datos.getStringValue("CodigoAutorizacion")
                        ,/*posDat.getLongValue("NumeroOperacion")*/this.datos.getLongValue("numOper__"),Format.formatMonto(this.datos.getLongValue("Monto")),posDat.getStringValue("Usuario")
                        //,Format.formatFechaBoleta(Tools.getFecha()),Format.formatHoraPresto(Tools.getTime()),posDat.getLongValue("Entidad")
                        ,Tools.getFecha().substring(0,10),Format.formatHoraPresto(Tools.getTime()),posDat.getLongValue("Entidad")
                        ,posDat.getLongValue("Agencia"),posDat.getLongValue("Caja"),posDat.getStringValue("cuentaDeposito")
                        ,posDat.getStringValue("bancoDeposito"), this.datos.getLongValue("codigoRechazo"));
            }
            
            ok = true;
        } catch (PosDeviceException e) {               
            Tools.logStackTrace(Base.logger, e);
        } catch (JposException e) {
            Tools.logStackTrace(Base.logger, e);
        } 
        finally{
            if( Base.pos.printer.isOn() ){
                try {
                    Base.pos.printer.close();
                    Base.pos.printer.setOn(false);
                } catch (PosDeviceException e1) {
                    Tools.logStackTrace(Base.logger, e1);
                }
            }
        }
        if( ok == true){
            return this._PRINT_OK;          
        }
        else{
            return this._PRINT_FAIL;
        }
    }
    
    protected int ingreseCheque(ICajaView vista){
        estado = 1;
        vista.hideAllEntries();
        vista.setEntryTitle( "CHEQUE", true );
        vista.setEntryTextLabel( "Ingrese cheque en lector de cheques", true );
        /*try {
            Base.pos.micr.openDevice();
        } catch (PosDeviceException e) {
            Tools.logStackTrace(Base.logger, e);
        }*/
        return ICajaView._WAITFORACTION;
    }
    
    protected int etapaIngresoCheque(ICajaView vista){
        
        try {
            Base.pos.micr.openDevice();
            //Tools.espera(1);
            Tools.espera(2);
            Base.pos.micr.insert();  
            Base.pos.micr.remove();
        } catch (PosDeviceException e) {
            Base.logger.info("Timeout de la insercion del cheque");
            Tools.logStackTrace(Base.logger, e);            
        }
        
        try {
            Base.pos.micr.close();
        } catch (PosDeviceException e) {
            Base.logger.info("Excepcion en el cierre del Micr");
            Tools.logStackTrace(Base.logger, e); 
        }
        
        if(Base.pos.micr.isLeido()){
            estado = 2;
            ParamSet posDat = Base.getParamSet("posDat");
            this.datos.setValue("Banco",Base.pos.micr.getBanco());
            String []bancos = posDat.getStringValue("banco").split("\\,");
            for(int i = 0 ; i < bancos.length ; i++){
                if(this.datos.getStringValue("Banco").equals(bancos[i])){
                    this.datos.setValue("TipoTotal", "MismoBanco");
                    break;
                }
            }
            this.datos.setValue("Cuenta", Long.parseLong(Base.pos.micr.getCuenta()));
            this.datos.setValue("Serial", Long.parseLong(Base.pos.micr.getSerial()));
            this.datos.setValue("Monto", this.getMonto());
            if( this instanceof Cheque ){
                vista.setEntryMessage( "Ingrese rut del cliente (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null );
                return ICajaView._WAITFORACTION;
            }
            else{
                return ICajaView._NOWAITFORACTION;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Error en lectura de cheque", "Error", JOptionPane.INFORMATION_MESSAGE);
            vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
            estado = 3;  
            vista.setEntryTextLabel("Bancos: ", true);
            // TODO se implementa invocacion a servicio para traer bancos..
           // String[] bancos = Base.getBancos();
            String[] bancos = Base.traeBancos();
            vista.setEntryList(Base.getList(bancos,",",1), true, false);
            return ICajaView._WAITFORACTION;                    
        }
    }
    
    protected int etapaIngresoCuenta(ICajaView vista){
        estado = 4;                
        int index = vista.getEntryListIndex();
        
        vista.hideAllEntries();
        vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
        //this.datos.setValue("Banco",Integer.parseInt(Base.getList( Base.getBancos(), ",", 0)[index]));
        this.datos.setValue("Banco",Integer.parseInt(Base.getList( Base.traeBancos(), ",", 0)[index]));
        Base.logger.info("Banco para carga manual: "+this.datos.getStringValue("Banco"));
        
        ParamSet posDat = Base.getParamSet("posDat");
        String []bancos = posDat.getStringValue("banco").split("\\,");
        for(int i = 0 ; i < bancos.length ; i++){
            if(this.datos.getStringValue("Banco").equals(bancos[i])){
                this.datos.setValue("TipoTotal", "MismoBanco");
                break;
            }
        }
        
        vista.setEntryMessage( "Ingrese cuenta", true );
        vista.setEntryTextLabel("Ingrese Cuenta:", true);
        vista.setEntryText("", true, false, false,null,null);
        return ICajaView._WAITFORACTION;
    }
    
    protected int etapaIngresoSerial(ICajaView vista){
        estado = 5;
        vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
        String cuenta = vista.getEntryText();
        if(cuenta.equals("") || cuenta.length() > 18){
            Base.logger.error("Error de tipo de datos");            
            estado = 4;
            vista.setEntryMessage( "Numero de cuenta invalido", true );
            vista.setEntryTextLabel("Ingrese Cuenta:", true);
            vista.setEntryText("", true, false, false,null,null);
            return ICajaView._WAITFORACTION;  
        }
        this.datos.setValue("Cuenta",cuenta);
        Base.logger.info("Nro cuenta para carga manual: "+this.datos.getStringValue("Cuenta"));    
        
        vista.setEntryMessage( "Ingrese serial del cheque", true );
        vista.setEntryTextLabel("Ingrese Serial:", true);
        vista.setEntryText("", true, false, false,"[0-9]+","Serie debe ser numérico");
        return ICajaView._WAITFORACTION;
    }
    
    protected int etapaIngresoAutorizacion(ICajaView vista,int tipo,String cod){
        int estadoActual = 0;
        
        if(tipo == _CHEQUE_DIA ){
            estadoActual = 6;
        }
        else{
            estadoActual = 9;
        }
        
        this.datos.setValue("CodigoAutorizacion",cod);
        
        if(tipo == _CHEQUE_DIA ){
            this.datos.show("Cheque");
            this.setNombre("Cheque");
        }
        else{
            this.datos.show("ChequeFecha");
            this.setNombre("ChequeFecha");
        }
        
        try {
            vista.getOperTRV().addMedioPago(this);
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
            Base.logger.error("Error en agregar medio de pago al carro");
            return 13;
        }
        
        ParamSet posDat = Base.getParamSet("posDat");
        if( posDat.getStringValue("printer").equals("6") || posDat.getStringValue("printer").equals("5")){
            if( vista.showMyConfirmDialog("Confirme por favor","Desea la Impresión de cheque")
                    == JOptionPane.OK_OPTION ) {
                Base.logger.info("Imprimiendo cheque");
                estado = estadoActual + 1;
                this.impresionCheque(vista);               
                return ICajaView._WAITFORACTION;   
            }
            else{
                estado = estadoActual + 3;
                return ICajaView._NOWAITFORACTION;
            }
        }
        else{
            estado = estadoActual + 3;
            return ICajaView._NOWAITFORACTION;
        }
    }
    
    protected int etapaCreacionServicio(ICajaView vista,int tipo){
        int estadoActual = 0;
        
        if(tipo == _CHEQUE_DIA ){
            estadoActual = 6;
        }
        else{
            estadoActual = 9;
        }
        
        //ServerProxy pr = Proxy.getProxyInstance();
        ValidarChequeWSServerProxy pr = AppControlValidarChequeProxy.getProxyInstance();
        
    	//HeaderIn hIn = new HeaderIn();
    	OperacionIn opIn = new OperacionIn();
    	Caja caja = new Caja();
    	
    	ParamSet pSet = Base.getParamSet("posDat");
		
		//hIn.setAgencia((int)(pSet.getLongValue("Agencia")));
		caja.setAgencia(pSet.getStringValue("Agencia"));
		Base.logger.info("Agencia: "+caja.getAgencia());
		//hIn.setCajaFisica((int)(pSet.getLongValue("Caja")));
		caja.setIdCaja((int)(pSet.getLongValue("Caja")));
		Base.logger.info("Caja: "+caja.getIdCaja());
		//hIn.setEntidad((int)(pSet.getLongValue("Entidad")));
		caja.setEntidad(pSet.getStringValue("Entidad"));
		Base.logger.info("Entidad: "+caja.getEntidad());
		//hIn.setCajero(Integer.parseInt(pSet.getStringValue("Cajero")));
		// En DTO Caja no existe parametro Cajero !!
		
		caja.setCanal(Integer.parseInt(pSet.getStringValue("Canal")));
		Base.logger.info("Canal: "+caja.getCanal());
		
		//hIn.setSession(Integer.parseInt(pSet.getStringValue("SessionId")));
		caja.setCodigoSesion(new Long(pSet.getStringValue("SessionId")));
		Base.logger.info("SessionId: "+caja.getCodigoSesion());
		//hIn.setUsuario(pSet.getStringValue("Usuario"));
		caja.setUsuario(pSet.getStringValue("Cajero"));
		//caja.setUsuario(pSet.getStringValue("CodigoRecaudador"));
		Base.logger.info("Usuario: "+caja.getUsuario());
		//hIn.setRecaudador(pSet.getStringValue("Usuario"));
		caja.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
		Base.logger.info("Recaudador: "+caja.getRecaudador());
		//AutorizaChequeIn autCheque = new AutorizaChequeIn();
		//autCheque.setHeaderIn(hIn);

		//autCheque.setBanco(this.getDatos().getStringValue("Banco"));
		opIn.setBanco(this.getDatos().getStringValue("Banco"));
		Base.logger.info("Banco: "+opIn.getBanco());
		//autCheque.setCuenta(this.getDatos().getStringValue("Cuenta"));
		opIn.setCuenta(this.getDatos().getStringValue("Cuenta"));
		Base.logger.info("Cuenta: "+opIn.getCuenta());
		//autCheque.setNumeroSerie(this.getDatos().getStringValue("Serial"));
		opIn.setSerie(this.getDatos().getStringValue("Serial"));
		Base.logger.info("Serial: "+opIn.getSerie());
		//autCheque.setRut(this.getDatos().getStringValue("Rut"));
		// TODO validar si viene con Dig. Verif. ??
		String rut = this.getDatos().getStringValue("Rut");
		Base.logger.info("Rut Cheque: "+rut);
		opIn.setRut(new Long(rut.substring(0, rut.length()-2)));
		Base.logger.info("Rut Consulta Cheque: "+opIn.getRut());
		opIn.setDv(rut.substring(rut.length()-1, rut.length()));
		Base.logger.info("Dv Consulta Cheque: "+opIn.getDv());
		
		//autCheque.setFecha(this.getDatos().getStringValue("Fecha"));
		opIn.setFechaVencimiento(this.getDatos().getStringValue("Fecha"));
		Base.logger.info("Fecha Vencimiento: "+this.getDatos().getStringValue("Fecha"));
		
		//autCheque.setMonto(Long.toString(this.getDatos().getLongValue("Monto")));
		opIn.setMonto(this.getDatos().getLongValue("Monto"));
		Base.logger.info("Monto del Cheque: "+this.getDatos().getLongValue("Monto"));
		
		// Se comenta para pruebas de generacion de Cheque
		// Se debe implementar llamado a servicio definitivo para validacion de cheques...
		
		//AutorizaChequeOut respCheque = null;
		Respuesta respCheque = null;
		Base.logger.info("inicializo DTO para respuesta...");
		
		vista.showBusyWindow("Consultando", "Espere por favor...");
        
		try {
			Base.logger.info("Realizando la invocacion al servicio de Consulta Cheque ORSAN...");
			//respCheque = pr.autorizaCheque(autCheque);
			respCheque = pr.validarCheque(opIn, caja);
			Base.logger.info("Autoriza Cheque ORSAN Codigo: "+respCheque.getRetCode());
			Base.logger.info("Autoriza Cheque ORSAN Desc: "+respCheque.getRetDesc());
		} catch (RemoteException e1) {
			vista.hideBusyWindow();
			Tools.logStackTrace(Base.logger, e1);
            Base.logger.error("El execute del servicio retorno error o timeout");
            JOptionPane.showMessageDialog(null, "Error en la llamada al servicio ORSAN.\nNo se ha podido obtener un código de autorizacion.\nVuelva a intentarlo.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            estado = 100;
            // Aca se invoca directamente al metodo que solicita Cod de autorizacion
          //  claveSup = new CodigoAutorizacion();
          //  return claveSup.execute(vista, 0, this.datos);
            return 13;
           
		}
		
		//Solo para las pruebas...
		/*respCheque.setRetCode(0);
		respCheque.setRetDesc("OPERACION EXITOSA;710377");*/
		
		
		Base.logger.info("Ya realizo la invocacion a Validar Cheque ORSAN....");
		//respCheque.setRetCode(0);
		
		String codAut = "";
        String mensajeRet = "";
        String codAutInterno = "";
        try {
        	String[] parsedRet = respCheque.getRetDesc().split(";");
        	if(parsedRet.length > 1) {
        		codAut = parsedRet[1];
        		mensajeRet = parsedRet[0];
        		if(parsedRet.length > 2){
        			codAutInterno = parsedRet[2];
        		}
        	} else {
        		mensajeRet = parsedRet[0];
        	}
        	
        	Base.logger.info("CodigoAutorizacion ORSAN: " + codAut);
            Base.logger.info("Mensaje retorno: " + mensajeRet);
        } catch(Exception e) {
        	Base.logger.debug("No se pudo splitear el String: " + respCheque.getRetDesc());
        	mensajeRet = respCheque.getRetDesc();
        }
        
        try {
        	this.datos.setValue("codigoRechazo", Long.parseLong(mensajeRet.substring(mensajeRet.length() - 3)));
        } catch (Exception e) {
        	Base.logger.error("No se pudo transformar a long. Esto quiere decir que no hay codigo rechazo. Seteando codigo rechazo a 0.");
        	this.datos.setValue("codigoRechazo", new Long(0).longValue());
        }
		
		vista.hideBusyWindow();
        
		//if(respCheque.getHeaderOut().getRc() != 0){
		if(respCheque.getRetCode() < 0){
            Base.logger.error("El execute del servicio retorno un error: "+respCheque.getRetDesc());
            //JOptionPane.showMessageDialog(null, "Error en el servicio.\nSe requiere autorizacion para continuar.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "La validacion arrojo el siguiente Codigo: "+respCheque.getRetCode()+"\nReintente mas tarde.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            //Retorna sin solicitar codigo de autorizacion
            return 13;
		}
		
        //if(respCheque.getCodigoAutorizacion().equals("")){
        if(respCheque.getRetCode() > 0){        	
            Base.logger.error(("Cheque Rechazado: " + respCheque.getRetDesc()));
            if( vista.showMyConfirmDialog("Confirme por favor","Cheque rechazado: " + respCheque.getRetDesc() + "\nSelecione SI para Rechazar medio de pago o NO para reintertar")
                    == JOptionPane.YES_OPTION ) {
                return 13;
            }
            else{
                estado = 2;
                //Retorna sin solicitar codigo de autorizacion
                return ICajaView._NOWAITFORACTION;
                  }
        }
		
        JOptionPane.showMessageDialog(null, "Medio Pago Cheque autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        
        //this.datos.setValue("CodigoAutorizacion",respCheque.getCodigoAutorizacion());
        
        // EL codigo de autorizacion vendra en el campo Descrip. de retorno..
        //Base.logger.info(("Codigo Autorizacion Cheque: " + respCheque.getRetDesc()));
        //this.datos.setValue("CodigoAutorizacion",respCheque.getRetDesc());
        //this.datos.setValue("CodigoAutorizacion",0);
        //Acá se debe cortar el retDesc para poder obtener el código de autorización de Orsan
        
        this.datos.setValue("CodigoAutorizacion", codAut);
        this.datos.setValue("CodigoAutInterno", codAutInterno);
        
        this.datos.show("Cheque");
        
        if(tipo == _CHEQUE_DIA ){
            this.setNombre("Cheque");
        }
        else{
            this.setNombre("ChequeFecha");
        }
        
        //Metemos al carro de medios de pago el servicio
        try {
            vista.getOperTRV().addMedioPago(this);
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
            Base.logger.error("Error en agregar medio de pago al carro");
            return 13;
        }
        
        ParamSet posDat = Base.getParamSet("posDat");
        if( posDat.getStringValue("printer").equals("6") || posDat.getStringValue("printer").equals("5")){
        	/**
            if( vista.showMyConfirmDialog("Confirme por favor","Desea continuar SIN Impresión de cheque")
                    == JOptionPane.NO_OPTION ) {
                Base.logger.info("Imprimiendo cheque");
                estado = estadoActual + 1;
                this.impresionCheque(vista);               
                return ICajaView._WAITFORACTION;   
            }
            else{
                estado = estadoActual + 3;
                return ICajaView._NOWAITFORACTION;
            } 
            */
        	
        	if( vista.showMyConfirmDialog("Confirme por favor","Desea la Impresión de cheque")
                    == JOptionPane.OK_OPTION ) {
                Base.logger.info("Imprimiendo cheque");
                estado = estadoActual + 1;
                this.impresionCheque(vista);               
                return ICajaView._WAITFORACTION;   
            }
            else{
                estado = estadoActual + 3;
                return ICajaView._NOWAITFORACTION;
            } 
        	
        }
        else{
            estado = estadoActual + 3;
            return ICajaView._NOWAITFORACTION;
        } 
    }
    
    protected int etapaIngresoFecha(ICajaView vista,int tipo){
        
        estado = 55;
        vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
        String serial = vista.getEntryText();
        if(serial.equals("") || serial.length() > 10){
            Base.logger.error("Error de tipo de datos");
            estado = 5;            
            vista.setEntryMessage( "Numero de serial invalido", true );
            vista.setEntryTextLabel("Ingrese serial:", true);
            vista.setEntryText("", true, false, false,"[0-9]+","Serie debe ser numérico");
            return ICajaView._WAITFORACTION;
        }
        
        this.datos.setValue("Serial",serial);              
        if(tipo == this._CHEQUE_DIA){
            estado = 2;
            vista.setEntryMessage( "Ingrese rut del cliente (Formato: 99999999-X)", true );
            vista.setEntryTextLabel("Ingrese Rut:", true);
            vista.setEntryText("", true, false, false,null,null );
        }
        else{
            return ICajaView._NOWAITFORACTION;
        }
        return ICajaView._WAITFORACTION; 
    }
    
    private String[] traeBancos(){
    	Base.logger.info("Obteniendo Bancos desde Servicio...");
    	// TODO aca se debe implementar invocacion al servicio dependiendo del codigo de error retornado..
    	ParamSet posDat = Base.getParamSet("posDat");
        AppControlCajaWSServerProxy prAut = AppControlProxy.getProxyInstance();
        ConsultarBancosOutDTO bancos = new ConsultarBancosOutDTO();
        
        HeaderDTO head = new HeaderDTO();
        head.setAgencia(posDat.getStringValue("Agencia"));
        head.setCajaFisica(posDat.getStringValue("Caja"));
        head.setCajero(posDat.getStringValue("Cajero"));
        head.setEntidad(posDat.getStringValue("Entidad"));
        head.setRecaudador(posDat.getStringValue("CodigoRecaudador"));
        head.setSession(posDat.getStringValue("SessionId"));
        head.setUsuario(posDat.getStringValue("Usuario"));
        
        try {
        	bancos = prAut.consultarBancos(head);
        	
            Base.logger.info("Codigo respuesta bancos: " + bancos.getRetCode());
			Base.logger.info("Descripcion respuesta bancos. "+bancos.getRetDesc());
			
			if(!"0".equalsIgnoreCase(bancos.getRetCode())){
				 Base.logger.error("El servicio de bancos fallo, se debe obtener desde properties...");
				 return Base.getBancos();
			}
			
			// Decodificar el arreglo de dtos hacia el arreglo de String..
			String[] bancoDto = new String[bancos.getBancos().length];
			for(int i=0; i<bancos.getBancos().length; i++){
				bancoDto[i] = bancos.getBancos()[i].getCodBanco()+","+bancos.getBancos()[i].getNombre();
			}
			
			return bancoDto;
			
		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
            Base.logger.error("El execute del servicio para bancos retorno error o timeout");
            return Base.getBancos();
		}   
    	
    }
    
    protected int busquedaCodigoAutorizacion(ICajaView vista,int tipo){
        int estadoActual = 0;
        
        if(tipo == _CHEQUE_DIA ){
            estadoActual = 6;
        }
        else{
            estadoActual = 9;
        }
        
	   	 AppControlCajaWSServerProxy prAut = AppControlProxy.getProxyInstance();
	     ConsultarAprobacionChInDTO aprIn = new ConsultarAprobacionChInDTO();
	     ConsultarAprobacionChOutDTO respAut = null;
        
    	//HeaderIn hIn = new HeaderIn();
    	OperacionIn opIn = new OperacionIn();
    	Caja caja = new Caja();
    	
    	ParamSet pSet = Base.getParamSet("posDat");
		
		//hIn.setAgencia((int)(pSet.getLongValue("Agencia")));
		
    	String codigoAutorizacionBuscado = Tools.getCodAprobacion(this.getDatos().getStringValue("Cuenta"), this.getDatos().getStringValue("Banco"), this.getDatos().getStringValue("Serial"));


		Base.logger.info("inicializo DTO para respuesta...");
		
		vista.showBusyWindow("Consultando", "Espere por favor...");
        
		try {
			Base.logger.info("Realizando la invocacion al servicio de Consulta Cheque a PDP...");
			Base.logger.info("Por el codigo de autorizacion..." + codigoAutorizacionBuscado);
             
            aprIn.setCodigoAprobacion(codigoAutorizacionBuscado);
            Base.logger.info("Codigo autorizacion para Cheque: " + aprIn.getCodigoAprobacion());
 			Base.logger.info("Realizando la invocacion al servicio de autorizacion de Cheque");
 			respAut = prAut.consultarAprobacionCheque(aprIn);
 			Base.logger.info("Autorizacion de Cheque Codigo: "+respAut.getRetCode());
 			Base.logger.info("Autorizacion de Cheque Desc: "+respAut.getRetDesc());
 			
			if(!"0".equalsIgnoreCase(respAut.getRetCode())){
				vista.hideBusyWindow();
				 Base.logger.error("El servicio de busqueda de codigo de autorizacion de Cheque retorno un error...");
				 JOptionPane.showMessageDialog(null, "No se ha encontrado un código de Autorización.\nSe requiere hacer la consulta a ORSAN.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				 int codigo = this.etapaCreacionServicio(vista, tipo);
				 // Aca deberia rechazar el pago ??
	                //JOptionPane.showMessageDialog(null, "Validacion incorrecta de supervisor.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
	                return codigo;
			}
 			
			

		} catch (RemoteException e1) {
			vista.hideBusyWindow();
			Tools.logStackTrace(Base.logger, e1);
            Base.logger.error("El execute del servicio retorno error o timeout");
            JOptionPane.showMessageDialog(null, "No se ha encontrado un código de Autorización.\nSe requiere hacer la consulta a ORSAN.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            int codigo = this.etapaCreacionServicio(vista, tipo);
            Base.logger.info("Codigo etapa Creacion Servicio: " + codigo);
           
		}
		Base.logger.info("Ya realizo la invocacion Buscar codigo autorizacion....");
        
		vista.hideBusyWindow();
        
		JOptionPane.showMessageDialog(null, "Se ha encontrado el código de autorización : " + codigoAutorizacionBuscado, "Continuar", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(null, "Medio Pago Cheque autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        
        this.datos.setValue("CodigoAutorizacion", codigoAutorizacionBuscado);
        this.datos.show("Cheque");
        
        if(tipo == _CHEQUE_DIA ){
            this.setNombre("Cheque");
        }
        else{
            this.setNombre("ChequeFecha");
        }
        
        //Metemos al carro de medios de pago el servicio
        try {
            vista.getOperTRV().addMedioPago(this);
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
            Base.logger.error("Error en agregar medio de pago al carro");
            return 13;
        }
        
        ParamSet posDat = Base.getParamSet("posDat");
        if( posDat.getStringValue("printer").equals("6") || posDat.getStringValue("printer").equals("5")){
        	
        	if( vista.showMyConfirmDialog("Confirme por favor","Desea la Impresión de cheque")
                    == JOptionPane.OK_OPTION ) {
                Base.logger.info("Imprimiendo cheque");
                estado = estadoActual + 1;
                this.impresionCheque(vista);               
                return ICajaView._WAITFORACTION;   
            }
            else{
                estado = estadoActual + 3;
                return ICajaView._NOWAITFORACTION;
            } 
        	
        }
        else{
            estado = estadoActual + 3;
            return ICajaView._NOWAITFORACTION;
        } 
    }
    
    
    
}
