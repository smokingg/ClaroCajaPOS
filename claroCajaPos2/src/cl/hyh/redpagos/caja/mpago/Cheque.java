package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarAprobacionChInDTO;
import ws.claro.cl.ConsultarAprobacionChOutDTO;
import ws.claro.cl.MedioPagoDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.ValidacionChequesProtestadosRequestDTO;
import ws.claro.cl.ValidacionChequesProtestadosResponseDTO;
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
import cl.hyh.redpagos.caja.pos.BasePos;
import cl.hyh.redpagos.caja.pos.PosDeviceException;

/**
 * Medio de Pago: Cheque al día
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Cheque extends ChequeBase implements ITrxBase{
    
	String []pagueseA;
    long montoP= 0;
    long montoTotal= 0;
	
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("Cheque");
        this.setNombre( "Cheque" );
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
            	String mensajeProtesto = this.clienteProtestado(vista);
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
                JOptionPane.showMessageDialog(null, "No puede Cancelar con Cheque un Doc. Cheque Protestado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("No puede Cancelar con Cheque un Doc. Cheque Protestado");
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
    
            	//TODO validar que si existen varios docs en el carro..
                // Se debe cancelar el total de carro con solo este medio de pago  !!
                if(vista.getOperTRV().getCarroCompras().getDocumentos().size()>1){
                	// Si el monto ingresado no coincide
                	if(vista.getOperTRV().getCarroCompras().getMontoTotal() > Long.valueOf(vista.getEntryText())){
                		estado = 0;
                		Base.logger.error("El monto no coincide con el total del carro para N documentos...");
                		JOptionPane.showMessageDialog(null, "Debe cancelar el Total del Carro","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                		 return this.setMontoMPago(vista);  
                	}
                }
                              
                montoP = Long.parseLong(vista.getEntryText());
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
            	if((montoP)  == 0){
                    estado = 0;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
            	
            	DefMedioPago def = Base.getDefMedioPago("Cheque");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                    return 13;
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", montoP);
                
                /*if(this.isTotal(vista)){
                    if(vista.getOperTRV().getCarroCompras().getMontoTotal() != this.datos.getLongValue("Monto")){
                        estado = 0;
                        vista.hideAllEntries();
                        vista.setEntryTitle( "Cheque al dia", true );                
                        vista.setEntryMessage( "Ingrese monto por el total", true );
                        vista.setEntryTextLabel("Ingrese monto por el total:", true);
                        vista.setEntryText(Long.toString(vista.getOperTRV().getCarroCompras().getMontoTotal()), true, false, false, "[0-9]+", "Monto no valido");
                        return ICajaView._WAITFORACTION;
                    }
                }*/
                return this.ingreseCheque(vista);
                
            case 1:
                //Se ingresa el cheque, y si esta disponible se extraen los datos
                return this.etapaIngresoCheque(vista);
                
            case 3:
                // pasamos lectura del banco, se lee cuenta
                return this.etapaIngresoCuenta(vista);    
                
            case 4:
                // pasamos lectura de cuenta, pasamos a captura de serial del cheque
                return this.etapaIngresoSerial(vista);    
                
            case 5:
                // pasamos lectura del serial, leemos la fecha o rut
                return this.etapaIngresoFecha(vista, this._CHEQUE_DIA); 
                
            case 2:
                // se debio haber ingresado rut             
                estado = 6;
                vista.setEntryTitle( "CHEQUE CARGA MANUAL", true );
                
                this.datos.setValue("Fecha", Tools.getFecha().substring(0, 10));           
                if(Tools.validarRut(vista.getEntryText())){
                    this.datos.setValue("Rut", vista.getEntryText()); 
                }   
                else{                 
                    estado = 2;                    
                    vista.setEntryMessage( "Rut no valido", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                //Listos para la logica del proceso
                //return this.etapaCreacionServicio(vista, this._CHEQUE_DIA)
                
                int codigo = this.busquedaCodigoAutorizacion(vista, this._CHEQUE_DIA);
                Base.logger.info("Codigo etapa Creacion Servicio: " + codigo);
                return codigo;
                
            case 6:
            	Base.logger.info("Posicionado en Case = 6...");
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
        		
                //Obtenemos el codigo de autorizacion y se pregunta sobre impresiones
                return this.etapaIngresoAutorizacion(vista,this._CHEQUE_DIA,this.datos.getStringValue("codigo"));
                
            case 7:
                estado = 8;
                vista.clearAllEntries();
                vista.setEntryMessage( "Ingrese Paguese a ", true );
               // vista.setEntryTextLabel("Ingrese paguese a:", true);
               // vista.setEntryText("VTR Banda Ancha (Chile) S.A.", true, false, false,null,null);
               // vista.setEntryText("Claro Chile S.A.", true, false, false,null,null);
                pagueseA = new String[1];
                pagueseA[0] = "Claro Chile S.A.";
                //pagueseA[1] = "Cheque";
                //pagueseA[2] = "Vale Vista";
                //pagueseA[3] = "Transferencia";
                vista.setEntryList(Base.getList(pagueseA,",",0), true, false);
                return ICajaView._WAITFORACTION;
                
            case 8:
                estado = 9;
                /**
                if(vista.getEntryText().equals("")){
                    estado = 8;                    
                    vista.setEntryMessage( "Ingrese Paguese a ", true );
                    vista.setEntryTextLabel("Ingrese paguese a:", true);
                    //vista.setEntryText("VTR Banda Ancha (Chile) S.A.", true, false, false,null,null);
                    vista.setEntryText("Claro Chile S.A.", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                this.datos.setValue("Nombre", vista.getEntryText());
                */
                String opt = pagueseA[vista.getEntryListIndex()];
                this.datos.setValue("Nombre", opt);
                vista.showBusyWindow("Imprimiendo Cheque", "Espere por favor...");
                this.printCheque(vista); 
                Tools.espera(5);
                vista.hideBusyWindow();
                return ICajaView._NOWAITFORACTION;
                
            case 9:
                estado = 11;
                vista.setEntryMessage( "Ingrese Teléfono", true );
                vista.setEntryTextLabel("Ingrese Teléfono:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 11:
                estado = 10;
                if(vista.getEntryText().equals("")){
                    estado = 9;
                    vista.setEntryMessage( "Ingrese Teléfono", true );
                    vista.setEntryTextLabel("Ingrese Teléfono:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                this.datos.setValue("Telefono", vista.getEntryText());
                this.franqueoCheque(vista);
                return ICajaView._WAITFORACTION;
                
            case 10:
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
                
            case 100:
                int respSuper = claveSup.execute(vista, key, this.datos);
                if(respSuper == 15){
                    JOptionPane.showMessageDialog(null, "Validacion incorrecta de supervisor.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                if(respSuper != 11)
                    return respSuper;
                estado = 6;
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
            + "\nRut: " + this.getDatos().getStringValue( "Rut" ) + "\nMonto: " + 
            Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean isIngresable(ICajaView vista){
        boolean total = isTotal(vista);
        if(Base.getEdicion()){
            return false;
        }
        /*if(total){
            if( vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0 )
                return false;
        }*/
        
        return true;
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
    
    /**
     * Valida protestos de los clientes en el carro. retorna vacio en caso correcto o el mensaje a mostrar en caso de erorr
     * @param vista
     * @return String con el mensaje
     */
    public static String clienteProtestado(ICajaView vista) throws RemoteException{
    	String					mensajeSalida 	= "";
        Map<String, String> 	validarRut 		= new HashMap<String, String>();
        AppControlCajaWSServerProxy pr 			= AppControlProxy.getProxyInstance(); 
        // se valida si alguno de los rut de cliente en el carro de compras tiene protesto para cheque
        for(int i=0; i < vista.getOperTRV().getCarroCompras().getDocumentos().size(); i++){
        	String rutCliente = vista.getOperTRV().getCarroCompras().getDocument(i).getDatos().getStringValue("Rut");
        	if(!"".equals(rutCliente) && !validarRut.containsKey(rutCliente)){
        		if(rutCliente.split("-") != null && rutCliente.split("-").length == 2){
        			validarRut.put(rutCliente.split("-")[0], rutCliente.split("-")[1]);
        		}
        		
        	}
        }
        // valido los rut en el map contra el servicio
        for (String rut : validarRut.keySet()) {
        	if(rut != null && !"".equals(rut)){
        		ValidacionChequesProtestadosRequestDTO request = new ValidacionChequesProtestadosRequestDTO();
            	request.setRut(Integer.parseInt(rut));
            	request.setDv(validarRut.get(rut));
            	ValidacionChequesProtestadosResponseDTO response;
    			response = pr.obtenerValidacionChequesProtestados(request );

            	// si el servicio se ejecuto correctamente
            	if(response.getRetCode().equals("0")) {
            		// si el rut consultado tiene protestos
            		if(response.isHasProtesto()) {
            			// valido si tiene aprobación. Si es que tiene le permito el paso. Si no tiene muestro el mensaje
            			if(!response.isHasAprobacion()) {
            				
            				mensajeSalida += "El Rut " + request.getRut() + "-" + request.getDv() + " se encuentra bloqueado por protesto \n";
            				
            			}
            			
            		}
            		
            	}
        	}
		}
        return mensajeSalida;
    }
    
    public boolean isTotal(ICajaView vista){
/*        CarroCompra carro = vista.getOperTRV().getCarroCompras();
        DefDocumentoPago def = Base.getDefDocumentoPago(carro.getDocument(0).getNombre());
        for( int i = 0 ; i < carro.getDocumentos().size(); i++){
            DefDocumentoPago aux = Base.getDefDocumentoPago(carro.getDocument(i).getNombre());
            if(!def.getEmpresa().equals(aux.getEmpresa())){
                return true;
            }
        }*/
        return false;
    }
    
    public void llenarMP(MedioPagoCaja mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion("mpCheque");
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodigoAutorizacion"));
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	mp.setDepositante(this.getDatos().getStringValue("Titular"));
    	
    	int year = Integer.parseInt(this.getDatos().getStringValue("Fecha").substring(0,4));
    	int month = Integer.parseInt(this.getDatos().getStringValue("Fecha").substring(4,6));
    	int day = Integer.parseInt(this.getDatos().getStringValue("Fecha").substring(6,8));
    	
    	mp.setFechaVencimiento(new GregorianCalendar(year,month - 1,day));
    	mp.setNumeroCheque(this.getDatos().getStringValue("Serial"));
    	mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	mp.setRutPagador(this.getDatos().getStringValue("Rut"));
    	mp.setTipoTransaccion("mpCheque");
    	mp.setTipoTotal("1002");
    }
    
    // TODO validar valores de seteo en el DTO ???
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	//mp.setTipoTransaccion("mpCheque");
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodigoAutorizacion"));
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	mp.setDepositante(this.getDatos().getStringValue("Titular"));
    	// Reporte cheques ORSAN FASE 2-B
    	mp.setNumeroTarjeta(this.getDatos().getStringValue("CodigoAutInterno"));
    	
    	String year = "";
    	String month = "";
    	String day = "";
    	
    	System.out.println("Fecha Cheque: "+this.getDatos().getStringValue("Fecha"));
    	if(this.getDatos().getStringValue("Fecha").indexOf("-") > 0){
    		year = this.getDatos().getStringValue("Fecha").trim().substring(0,4);
        	System.out.println("Anho Cheque largo: "+year);
        	month = this.getDatos().getStringValue("Fecha").trim().substring(5,7);
        	System.out.println("Mes Cheque largo: "+month);
        	day = this.getDatos().getStringValue("Fecha").trim().substring(8,10);
        	System.out.println("Dia Cheque largo: "+day);
    	}else{
    		year = this.getDatos().getStringValue("Fecha").trim().substring(0,4);
        	System.out.println("Anho Cheque corto: "+year);
        	month = this.getDatos().getStringValue("Fecha").trim().substring(4,6);
        	System.out.println("Mes Cheque corto: "+month);
        	day = this.getDatos().getStringValue("Fecha").trim().substring(6,8);
        	System.out.println("Dia Cheque corto: "+day);
    	}
    	
    	
    	//mp.setFechaVencimiento((new GregorianCalendar(year,month - 1,day)).toString());
    	//mp.setFechaVencimiento(this.getDatos().getStringValue("Fecha"));
    	mp.setFechaVencimiento(year+"-"+month+"-"+day);
    	
    	
    	mp.setNumeroCheque(this.getDatos().getStringValue("Serial"));
    	mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	mp.setPagadorRut(this.getDatos().getStringValue("Rut"));
    	mp.setPagadorDigitoVerificador(this.getDatos().getStringValue("Dv"));
    	mp.setTipoTransaccion("mpCheque");
    	// Validar cual debe ser el tipo total ??
    	// mp.setTipoTotal("1002");
    	mp.setTipoTotal("6");
    }
    
    public void vaciarMP(MedioPagoCaja mp){
    	this.getDatos().setValue("Monto",mp.getMonto());
    	this.getDatos().setValue("Banco",Integer.toString(mp.getCodigoBanco()));
    	this.getDatos().setValue("Sucursal","");
    	this.getDatos().setValue("Cuenta",mp.getNumeroCtaCte());
    	this.getDatos().setValue("Serial",mp.getNumeroCheque());
    	this.getDatos().setValue("Rut",mp.getRutPagador());
    	this.getDatos().setValue("CodigoAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("Titular",mp.getNombrePagador());
    	this.getDatos().setValue("Fecha",Tools.getFecha(mp.getFechaVencimiento().getTime()));
    	this.getDatos().setValue("TipoTotal","1002");
    }
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("Banco",mp.getCodigoBanco());
    	this.getDatos().setValue("Sucursal","");
    	this.getDatos().setValue("Cuenta",mp.getNumeroCtaCte());
    	this.getDatos().setValue("Serial",mp.getNumeroCheque());
    	this.getDatos().setValue("Rut",mp.getRutPagador());
    	this.getDatos().setValue("CodigoAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("Titular",mp.getNombrePagador());
    	this.getDatos().setValue("Fecha",mp.getFechaVencimiento());
    	System.out.println("Fecha vencimiento Medio pago Cheque: "+this.getDatos().getStringValue("Fecha"));
    	//this.getDatos().setValue("TipoTotal","1002");
    	this.getDatos().setValue("TipoTotal","6");
    }
}
