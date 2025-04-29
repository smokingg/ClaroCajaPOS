package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarAprobacionChInDTO;
import ws.claro.cl.ConsultarAprobacionChOutDTO;
import ws.claro.cl.MedioPagoDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.proxy.AppControlProxy;

import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.MedioPagoException;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.pos.PosDeviceException;

/**
 * Medio de Pago: Cheque a fecha
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ChequeFecha extends ChequeBase  implements ITrxBase {
    long montoP= 0;
    long montoTotal= 0;
    int codigo=0;
    
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("ChequeFecha");
        this.setNombre( "ChequeFecha" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
        	//if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
            if(!this.isIngresable(vista)){
                JOptionPane.showMessageDialog(null, "Medio de Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            try {
            	String mensajeProtesto = Cheque.clienteProtestado(vista);
				if(!mensajeProtesto.equals("")) {
					JOptionPane.showMessageDialog(null, mensajeProtesto, "No se puede realizar el pago con cheque", JOptionPane.INFORMATION_MESSAGE);
	                Base.logger.info("Se encontraron protestos " + mensajeProtesto);
	                return 13;
				}
			} catch (Exception e) {
				Tools.logStackTrace(Base.logger, e);        
				Base.logger.info("No se pudo validar los protestos lanzo error");
				JOptionPane.showMessageDialog(null, "No se pudo realizar la validación de protestos", "Info", JOptionPane.INFORMATION_MESSAGE);
				return 13;
			}
            
            if(!this.isValid(vista)){
                JOptionPane.showMessageDialog(null, "No se Permite cancelar con Cheque un Doc. Cheque Protestado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("No se Permite cancelar con Cheque un Doc. Cheque Protestado");
                return 13;
            }
            
            if(!vista.getOperTRV().isValid(this)){
            	Base.logger.info("No se pudo agregar un Segundo Medio de Pago ...");
                return 13;
            }
            estado = 0;
            if( data.getLongValue("monto")==0 )
                return this.setMontoMPago(vista);
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
            	
//            	[REspinoza] Ya no se usa, Varios cheques a fecha permitido.
//            	//TODO validar que si existen varios docs en el carro..
//                // Se debe cancelar el total de carro con solo este medio de pago  !!
//                if(vista.getOperTRV().getCarroCompras().getDocumentos().size()>1){
//                	// Si el monto ingresado no coincide
//                	if(vista.getOperTRV().getCarroCompras().getMontoTotal() > Long.valueOf(vista.getEntryText())){
//                		estado = 0;
//                		Base.logger.error("El monto no coincide con el total del carro para N documentos...");
//                		JOptionPane.showMessageDialog(null, "Debe cancelar el Total del Carro","Advertencia", JOptionPane.INFORMATION_MESSAGE);
//                		 return this.setMontoMPago(vista);  
//                	}
//                }
                montoP = Long.parseLong(vista.getEntryText());
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
            	if((montoP)  == 0){
                    estado = 0;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
                DefMedioPago def = Base.getDefMedioPago("ChequeFecha");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                    return 13;
                }
                
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", montoP);
                
                return this.ingreseCheque(vista);                
                
            case 1:
                return this.etapaIngresoCheque(vista);
                
            case 3:
                // pasamos lectura del banco, se lee cuenta
                return this.etapaIngresoCuenta(vista);    
                
            case 4:
                // pasamos lectura de cuenta, pasamos a captura de serial del cheque
                return this.etapaIngresoSerial(vista);    
                
            case 5:
                // pasamos lectura del serial, leemos la fecha
                return this.etapaIngresoFecha(vista, this._CHEQUE_FECHA);
                
            case 55:    
                vista.setEntryTitle( "CHEQUE", true );
                estado = 16;
                return ICajaView._NOWAITFORACTION;            
                
            case 2:
                // se debio haber ingresado la fecha del cheque   
                if(this.datos.getStringValue("Fecha").equals("")){
                    estado = 55;
                    return ICajaView._NOWAITFORACTION;
                }
                estado = 6;   
                vista.setEntryMessage( "Ingrese rut del cliente (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                isRut = false;
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 6:
                estado = 7;
                vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
                // se debio haber ingresado el rut  
                if(Tools.validarRut(vista.getEntryText())){
                    this.datos.setValue("Rut", vista.getEntryText()); 
                }   
                else{                 
                    estado = 6;                    
                    vista.setEntryMessage( "Rut no valido", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                vista.setEntryMessage( "Ingrese nombre del cliente", true );
                vista.setEntryTextLabel("Ingrese Nombre:", true);
                // TODO aca se definia por defecto el nombre de PagueseA !!!
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 7:
                //Ingreso rut falta telefono
                estado = 8;
                vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
                String nombre = vista.getEntryText();
                try{
                    if(nombre.equals("")){
                        throw new Exception();
                    }
                    this.datos.setValue("Titular",nombre);
                }
                catch(Exception e){
                    Base.logger.error("Error de tipo de datos");
                    Tools.logStackTrace(Base.logger, e);
                    estado = 7;
                    
                    vista.setEntryMessage( "Nombre invalido", true );
                    vista.setEntryTextLabel("Ingrese Nombre:", true);
                    // TODO aca se definia por defecto el nombre de PagueseA !!!
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                vista.setEntryMessage( "Ingrese telefono del cliente", true );
                vista.setEntryTextLabel("Ingrese Telefono:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 8:
                //Listo con telefono
                vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
                String telefono = vista.getEntryText();
                try{
                    if(telefono.equals("")){
                        throw new Exception();
                    }
                    this.datos.setValue("Telefono",Integer.parseInt(telefono));
                }
                catch(Exception e){
                    Base.logger.error("Error de tipo de datos");
                    Tools.logStackTrace(Base.logger, e);
                    estado = 8;
                    
                    vista.setEntryMessage( "Telefono invalido", true );
                    vista.setEntryTextLabel("Ingrese Telefono:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                //Listos para la logica del proceso
                //return this.etapaCreacionServicio(vista, this._CHEQUE_FECHA);
                
                codigo = this.busquedaCodigoAutorizacion(vista, this._CHEQUE_FECHA);
                Base.logger.info("Codigo etapa Creacion Servicio: " + codigo);
                return codigo;
                
            case 9:
            	Base.logger.info("Posicionado en Case = 9...");
            	// TODO aca se debe implementar invocacion al servicio dependiendo del codigo de error retornado..
                AppControlCajaWSServerProxy prAut = AppControlProxy.getProxyInstance();
                ConsultarAprobacionChInDTO aprIn = new ConsultarAprobacionChInDTO();
                ConsultarAprobacionChOutDTO respAut = null;
            	
                try {
                	aprIn.setCodigoAprobacion(this.getDatos().getStringValue("codigo"));
                    Base.logger.info("Codigo autorizacion para Cheque: " + aprIn.getCodigoAprobacion());
        			Base.logger.info("Realizando la invocacion al servicio de autorizacion de Cheque");
        			respAut = prAut.consultarAprobacionCheque(aprIn);
        			Base.logger.info("Autorizacion de Cheque Codigo: "+respAut.getRetCode());
        			Base.logger.info("Autorizacion de Cheque Desc: "+respAut.getRetDesc());
        			
        			if(!"0".equalsIgnoreCase(respAut.getRetCode())){
        				 Base.logger.error("El servicio de autorizacion de Cheque retorno un error...");
        				 JOptionPane.showMessageDialog(null, "Error en el servicio de Autorizacion de Cheque "+respAut.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
        	                // Aca deberia rechazar el pago ??
        	                //JOptionPane.showMessageDialog(null, "Validacion incorrecta de supervisor.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	                return 13;
        			}
        			//return cod;
        		} catch (RemoteException e) {
        			vista.hideBusyWindow();
        			Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("El execute del servicio retorno error o timeout");
                    JOptionPane.showMessageDialog(null, "Error en el servicio de Autorizacion de Cheque "+respAut.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    // Aca deberia rechazar el pago ??
                    //JOptionPane.showMessageDialog(null, "Validacion incorrecta de supervisor.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
        		}   
                    
                return this.etapaIngresoAutorizacion(vista, this._CHEQUE_FECHA,this.datos.getStringValue("codigo"));
                
            case 10:
                estado = 12;
                vista.showBusyWindow("Imprimiendo Cheque", "Espere por favor...");
                this.printCheque(vista);
                Tools.espera(5);
                vista.hideBusyWindow();
                return ICajaView._NOWAITFORACTION;
                
            case 12: 
                estado = 13;
                this.franqueoCheque(vista);
                return ICajaView._WAITFORACTION;
                
            case 13:
            	//Para el franqueo de cheques se debe obtener el numero de operacion.
            	NumeroOperacionOutDTO operOut = null;
            	operOut = Tools.getNumOper();
            	if(operOut == null || (!operOut.getRetCode().equals("0") || operOut.getNumeroOperacion() < 0)) {
            		Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
    				JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
    				return -1;
            	} else {
            		vista.getDatos().setValue("numOper__", operOut.getNumeroOperacion());
            		this.datos.setValue("numOper__", operOut.getNumeroOperacion());
            		Base.logger.info("Numero de Operacion obtenida para franqueo: " + vista.getDatos().getLongValue("numOper__"));
            		vista.getDatos().setValue("numOperRes__", operOut.getNumeroOperacionReversa());
            		this.datos.setValue("numOperRes__", operOut.getNumeroOperacionReversa());
                	Base.logger.info("Numero de Operacion Reversa obtenida para franqueo: " + vista.getDatos().getLongValue("numOperRes__"));
            	}
            	vista.showBusyWindow("Imprimiendo Franqueo", "Espere por favor...");
                this.printFranqueo();
                Tools.espera(4);
                vista.hideBusyWindow();
               // this.datos.setValue("BancoV",Base.getCampoList2( Base.getBancos(), ",", this.datos.getStringValue("Banco")).substring(6));
                this.datos.setValue("BancoV",Base.getCampoList2( Base.traeBancos(), ",", this.datos.getStringValue("Banco")).substring(6));
                return 15;
                
            case 14:
                estado = 15;
                vista.setEntryMessage( "Ingrese dias cheque", true );
                vista.setEntryTextLabel("Ingrese dias cheque:", true);
                vista.setEntryText("", true, false, false,"[0-9]+","Datos erroneos");
                return ICajaView._WAITFORACTION;
                
            case 15:
                estado = 6;
                ParamSet posCfg = Base.getParamSet("posCfg");
                String dia = vista.getEntryText();
                if(Integer.parseInt( dia ) > posCfg.getLongValue("diasCheque")){
                    estado = 14;
                    return ICajaView._NOWAITFORACTION;
                }
                this.datos.setValue("Fecha", Tools.getNewFecha(Integer.parseInt(dia)));
                this.datos.setValue("Dias", Integer.parseInt(dia));
                vista.setEntryMessage( "Ingrese rut del cliente (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 16:
                estado = 17;
                vista.setEntryMessage( "Ingrese fecha cheque AAAAMMDD", true );
                vista.setEntryTextLabel("Ingrese fecha cheque:", true);
                vista.setEntryText("", true, false, false,"[0-9]+","Datos erroneos");
                return ICajaView._WAITFORACTION;
                
            case 17:
                estado = 6;
                String fecha = vista.getEntryText();
                SimpleDateFormat inFormat = new SimpleDateFormat( "yyyyMMdd" );
                try {
                    Date date = inFormat.parse( fecha );
                    if(!inFormat.format(date).equals(fecha)){
                        estado = 16;
                        return ICajaView._NOWAITFORACTION;
                    }
                } catch (ParseException e) {
                    estado = 16;
                    return ICajaView._NOWAITFORACTION;
                }
                
                posCfg = Base.getParamSet("posCfg");
                if( fecha.length() != 8 ){
                    estado = 16;
                    return ICajaView._NOWAITFORACTION;
                }
                
                System.out.println("Fecha Cheque a Fecha: "+fecha);
                System.out.println("Fecha Actual a Fecha: "+Tools.getFecha(new Date()));
                System.out.println("Dias a Fecha: "+Tools.getDias(fecha));
                //if(Long.parseLong(fecha) < Long.parseLong(Tools.getFecha())){
                if(Long.parseLong(fecha) < Long.parseLong(Tools.getFecha(new Date()))){
                    estado = 16;
                    return ICajaView._NOWAITFORACTION;
                }
                else if(Tools.getDias(fecha) > posCfg.getLongValue("diasCheque")){
                    estado = 16;
                    return ICajaView._NOWAITFORACTION;
                }
                
                this.datos.setValue("Fecha", fecha );
                this.datos.setValue("Dias", Tools.getDias(fecha));
                vista.setEntryMessage( "Ingrese rut del cliente (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 100:
                int respSuper = claveSup.execute(vista, key, this.datos);
                if(respSuper == 15){
                    JOptionPane.showMessageDialog(null, "Validacion invalida de supervisor.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                if(respSuper != 11)
                    return respSuper;  
                estado = 9;
                return ICajaView._NOWAITFORACTION;
        }
        
        return 0;
    }
    
    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nBanco: " + this.getDatos().getIntValue( "Banco" )
            + "\nCuenta: " + this.getDatos().getIntValue( "Cuenta" )
            + "\nSerial: " + this.getDatos().getIntValue( "Serial" )
            + "\nRut: " + this.getDatos().getStringValue( "Rut" ) + 
            "\nFecha: " + this.getDatos().getStringValue( "Fecha" ) + "\nMonto: " + 
            Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean isIngresable(ICajaView vista){
    	
        CarroCompra carro = vista.getOperTRV().getCarroCompras();
        ParamSet pSet = Base.getParamSet("posCfg");
        String []empresas = pSet.getDatos().getStringValue("empresasChequeFecha").split(",");
        DefDocumentoPago def = Base.getDefDocumentoPago(carro.getDocument(0).getNombre());
        String empresaCarro = def.getEmpresa();
        System.out.println("(Valida Cheque Fecha) Empresa Carro: "+empresaCarro);
        
        boolean ok = true;
        if(Base.getEdicion()){
            return false;
        }
        for(int i = 0 ; i < carro.getDocumentos().size() ; i++){
            def = Base.getDefDocumentoPago(carro.getDocument(i).getNombre());
            if(!isDoc(empresas,def.getEmpresa())){
                ok = false;
                break;
            }
        }
        if( ok == false){
            return false;
        }
        ok = true;
        for(int i = 0 ; i < carro.getDocumentos().size() ; i++){
            def = Base.getDefDocumentoPago(carro.getDocument(i).getNombre());
            if(!empresaCarro.equals(def.getEmpresa())){
                ok = false;
                break;
            }
        }
        if(ok == true){
            return true;
        }
        empresas = pSet.getDatos().getStringValue("empresasChequeCombi").split(",");
        ok = true;
        for(int i = 0 ; i < carro.getDocumentos().size() ; i++){
            def = Base.getDefDocumentoPago(carro.getDocument(i).getNombre());
            if(!isDoc(empresas,def.getEmpresa())){
                ok = false;
                break;
            }
        }
        if( ok == false){
            return false;
        }        
        return true;
    }
    
    private boolean isDoc(String[]empresas,String empresaCarro){
        boolean ok = false;
        for(int i = 0 ; i < empresas.length ; i++){
            if(empresas[i].equals(empresaCarro)){
                ok = true;
                break;
            }
        }
        return ok;
    }
    
    public void llenarMP(MedioPagoCaja mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion("mpChequeFecha");
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodigoAutorizacion"));
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	mp.setDepositante(this.getDatos().getStringValue("Titular"));
    	
    	int year = Integer.parseInt(this.getDatos().getStringValue("Fecha").trim().substring(0,4));
    	int month = Integer.parseInt(this.getDatos().getStringValue("Fecha").trim().substring(5,7));
    	int day = Integer.parseInt(this.getDatos().getStringValue("Fecha").trim().substring(8,10));
    	
    	mp.setFechaVencimiento(new GregorianCalendar(year,month - 1,day));
    	mp.setNumeroCheque(this.getDatos().getStringValue("Serial"));
    	mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	mp.setRutPagador(this.getDatos().getStringValue("Rut"));
    	mp.setTipoTransaccion("mpChequeFecha");
    	mp.setTipoTotal("1003");
    }
    
    // TODO validar valores de seteo en el DTO ???
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion("mpChequeFecha");
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodigoAutorizacion"));
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	mp.setDepositante(this.getDatos().getStringValue("Titular"));
    	// Reporte cheques ORSAN FASE 2-B
    	mp.setNumeroTarjeta(this.getDatos().getStringValue("CodigoAutInterno"));
    	
    	System.out.println("Fecha Cheque Fecha: "+this.getDatos().getStringValue("Fecha"));
    	
    	String year = this.getDatos().getStringValue("Fecha").substring(0,4);
    	System.out.println("Anho Cheque Fecha: "+year);
    	String month = this.getDatos().getStringValue("Fecha").substring(4,6);
    	System.out.println("Mes Cheque Fecha: "+month);
    	String day = this.getDatos().getStringValue("Fecha").substring(6,8);
    	System.out.println("Dia Cheque Fecha: "+day);
    	
    	//mp.setFechaVencimiento((new GregorianCalendar(year,month - 1,day)).toString());
    	mp.setFechaVencimiento(year+"-"+month+"-"+day);
    			
    	mp.setNumeroCheque(this.getDatos().getStringValue("Serial"));
    	mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	mp.setPagadorRut(this.getDatos().getStringValue("Rut"));
    	mp.setPagadorDigitoVerificador(this.getDatos().getStringValue("Dv"));
    	// Validar cual debe ser el tipo total ??
    	// mp.setTipoTotal("1003");
    	mp.setTipoTotal("7");
    }
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("Banco",mp.getCodigoBanco());
    	//this.getDatos().setValue("BancoV",Base.getCampoList2( Base.getBancos(), ",", this.datos.getStringValue("Banco")).substring(6));
    	this.getDatos().setValue("BancoV",Base.getCampoList2( Base.traeBancos(), ",", this.datos.getStringValue("Banco")).substring(6));
    	this.getDatos().setValue("Sucursal","");
    	this.getDatos().setValue("Cuenta",mp.getNumeroCtaCte());
    	this.getDatos().setValue("Serial",mp.getNumeroCheque());
    	this.getDatos().setValue("Rut",mp.getRutPagador());
    	this.getDatos().setValue("CodigoAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("Titular",mp.getNombrePagador());
    	this.getDatos().setValue("Fecha",mp.getFechaVencimiento());
    	// Validar cual debe ser el tipo total ??
    	//this.getDatos().setValue("TipoTotal","1003");
    	this.getDatos().setValue("TipoTotal","7");
    }
    
    public boolean isValid(ICajaView vista){
        
        // validar que si existe en carro un Doc CH, no se puede pagar con Cheque..
        for(int i=0; i < vista.getOperTRV().getCarroCompras().getDocumentos().size(); i++){
        	if("CH".equalsIgnoreCase(vista.getOperTRV().getCarroCompras().getDocument(i).getTipoRegistro())){
        		// existe un Cheque Protestado en el carro, no puede pagar con Cheque.
        		return false;
        	}
        }
        
        return true;
    }
}
