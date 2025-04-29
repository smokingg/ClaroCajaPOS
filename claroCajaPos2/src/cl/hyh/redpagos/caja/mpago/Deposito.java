package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.BancoCtaCteDTO;
import ws.claro.cl.ConsultarDenominacionInDTO;
import ws.claro.cl.ConsultarDenominacionOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.ListaBancosCtasOutDTO;
import ws.claro.cl.MedioPagoDTO;
import ws.claro.cl.proxy.AppControlProxy;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

/**
 * Medio de pago: Deposito
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Deposito extends MedioPago implements ITrxBase {
    int estado = 0;
    String []medios;
    String []cuentas;
	String sociedades[];
	String codSociedades[];
	String valorLista="";
	String valorListaVista="";
	int codEmpresa=0;
    //ArrayList <Datos> bancosS;
    BancoCtaCteDTO[] bancosS;
    //ArrayList <Datos> cuentasS;
    BancoCtaCteDTO[] cuentasS;
        ListaBancosCtasOutDTO bancos = null;
    ArrayList<String> arr = new ArrayList<String>();
    Datos dataS;
    long montoP= 0;
    long montoTotal= 0;
    
    public void init(Datos datosVista){
    	/**
        DefMedioPago dMP = Base.getDefMedioPago("Deposito");
        this.setNombre( "Deposito" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
        */
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
        	//if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
            if(!this.isIngresable(vista)){
                JOptionPane.showMessageDialog(null, "Medio de Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            
            if(!vista.getOperTRV().isValid(this)){
            	Base.logger.info("No se pudo agregar un Segundo Medio de Pago ...");
                return 13;
            }
            
            estado = 0;
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
            	/**
                DefServicio defS = Base.getDefServicio("ConsultaBancosDepositos");
                dataS = new Datos(defS.getInputRecordDef());
                */
                
                Servicio consultaBancos = null;
                
                DefMedioPago def = Base.getDefMedioPago("DepositoEfectivo");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", montoP);
                estado = 1;
                medios = new String[2];
                /*medios[0] = "Efectivo";
                medios[1] = "Cheque";
                medios[2] = "Vale Vista";
                medios[3] = "Transferencia";*/
                medios[0] = "Papeleta Depósito";
                medios[1] = "Transferencia";
                
                vista.setEntryTextLabel("Tipo Depósito: ", true);
                vista.setEntryMessage("Tipo Depósito", true);
                vista.setEntryTitle( "Tipo Depósito", true );  
                vista.setEntryList(medios, true, false);
                return ICajaView._WAITFORACTION;
                
            case 1:
                String opt = medios[vista.getEntryListIndex()];
                this.datos.setValue("Tipo", opt);
                /*if( opt.equals("Efectivo") ){
                    estado = 2;
                    this.setNombre("DepositoEfectivo");
                    return ICajaView._NOWAITFORACTION;
                }
                else if( opt.equals( "Cheque" )){
                    estado = 12;
                    this.setNombre("DepositoCheque");
                    return ICajaView._NOWAITFORACTION;
                }
                else if( opt.equals( "Vale Vista" )){
                    estado = 2;
                    this.setNombre("DepositoValeVista");
                    return ICajaView._NOWAITFORACTION;
                }*/
                if(opt.equals("Papeleta Depósito")){
                	estado = 15;
                	this.setNombre("PapeletaDeposito");
                    return ICajaView._NOWAITFORACTION;
                }
                else if( opt.equals( "Transferencia" )){
                    estado = 15;
                    this.setNombre("TransferenciaElectronica");
                    return ICajaView._NOWAITFORACTION;
                }
                
            case 12:
                estado = 13;
                vista.setEntryMessage( "Ingrese N° de Cheques", true );
                vista.setEntryTextLabel("Ingrese N° de Cheques:", true);
                vista.setEntryTitle( "Ingrese N° de Cheques", true ); 
                vista.setEntryText("", true, false, false,"[0-9]{1,9}","Cantidad de Cheques invalida");
                return ICajaView._WAITFORACTION;
                
            case 13:
                this.datos.setValue("Cantidad", vista.getEntryText());
                estado = 2;
                return ICajaView._NOWAITFORACTION;
                
            case 2:
                estado = 3;
                
                int resp = 0;
                
                //////////////////////////////////////////////
                AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
                
                ConsultarDenominacionInDTO hIn = new ConsultarDenominacionInDTO();
            	hIn.setTipoDenominacion("2");
        		Base.logger.info( "Denominacion recarga Fijo: " + hIn.getTipoDenominacion());
        		
        		/////////////////////////////////////
        		
        		vista.showBusyWindow("Consultando", "Espere por favor...");
            	
            	////////////////////////////////////////////
        		
        		// Generar los DTOs de entrada !!
                ParamSet pSet = Base.getParamSet("posDat");
                
                HeaderDTO opIn = new HeaderDTO();
                
                opIn.setAgencia(pSet.getStringValue("Agencia"));
                Base.logger.info("Valor Agencia: "+  opIn.getAgencia());
                opIn.setCajaFisica(pSet.getStringValue("Caja"));
                Base.logger.info("Valor Id Caja: "+  opIn.getCajaFisica());
                opIn.setEntidad(pSet.getStringValue("Entidad"));
                Base.logger.info("Valor Entidad: "+  opIn.getEntidad());
                opIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
                Base.logger.info("Valor Recaudador: "+  opIn.getRecaudador());
                opIn.setUsuario(pSet.getStringValue("Usuario"));
                Base.logger.info("Valor Usuario: "+  opIn.getUsuario());
                opIn.setSession(pSet.getStringValue("SessionId"));
                Base.logger.info("Valor Session: "+  opIn.getSession());
                //opIn.setCanal(new Integer(pSet.getStringValue("Canal")).intValue());
                //Base.logger.info("Valor Canal: "+  opIn.getCanal());
                
        		try { 
        			bancos = pr.listaBancosCtas(opIn);
        			Base.logger.info( "Codigo resp Lista Bancos: " + bancos.getRetCode());
        			Base.logger.info( "Desc resp Lista Bancos: " + bancos.getRetDesc());
        		} catch (RemoteException e) {
        			JOptionPane.showMessageDialog(null, bancos.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					Tools.logStackTrace(Base.logger, e);
					vista.hideBusyWindow();
					return 13;
        		}
        		
        		vista.hideBusyWindow();
        		
        		if(!"0".equalsIgnoreCase(bancos.getRetCode())){	
					Base.logger.error("No existen datos [RetCode]:" + bancos.getRetCode() + " [msg]:" + bancos.getRetDesc());
                    JOptionPane.showMessageDialog(null, bancos.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 13;
				}
                
                //int largo = bancos.getListaBancosCtas().length;
        		Base.logger.info( "Instanciando Arreglo de Lista bancos..");
                //bancosS = new BancoCtaCteDTO[bancos.getListaBancosCtas().length];
                
                if(bancos.getListaBancosCtas().length == 0){
                    Base.logger.info("No existen bancos"); 
                    JOptionPane.showMessageDialog(null, "No se rescato el Listado de Bancos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                
                Base.logger.info( "Generando Arreglo auxiliar para listado en pantalla.. ["+bancos.getListaBancosCtas().length+"]");
                
                arr.add(0,"Banco" + Base.getBancoCodigo(bancos.getListaBancosCtas(0).getCodBanco())) ;
                int  co = 1;
                
                for(int i = 1 ; i < bancos.getListaBancosCtas().length -1 ; i++){
                	Base.logger.info( "Codigo Banco rescatado ["+i+"]: "+bancos.getListaBancosCtas(i).getCodBanco());
                	if(bancos.getListaBancosCtas(i-1).getCodBanco() != bancos.getListaBancosCtas(i).getCodBanco()){
                		arr.add(co,"Banco" + Base.getBancoCodigo(bancos.getListaBancosCtas(i).getCodBanco())) ;
                		Base.logger.info( "Banco almanecado : "+arr.get(co));
                		co++;
                	}
                }
                
                String []aux = new String[arr.size()];
                for(int i = 0 ; i < arr.size() ; i++){
                	aux[i] = arr.get(i);
                	Base.logger.info( "Banco agregado al Arreglo: "+aux[i]);
                }
              
                vista.setEntryMessage("Bancos", true);
                vista.setEntryTitle( "Bancos", true );  
                
                vista.setEntryTextLabel("Bancos: ", true);
                vista.setEntryList(aux, true, false);
                return ICajaView._WAITFORACTION; 
                
            case 3:
                estado = 4;
                int index = vista.getEntryListIndex();
                
                int  co2 = 1;
                
                for(int i = 1 ; i < bancos.getListaBancosCtas().length -1 ; i++){
                	Base.logger.info( "Codigo Banco rescatado ["+i+"]: "+bancos.getListaBancosCtas(i).getCodBanco());
                	if(bancos.getListaBancosCtas(i-1).getCodBanco() != bancos.getListaBancosCtas(i).getCodBanco()){
                		arr.add(co2,"Banco" + Base.getBancoCodigo(bancos.getListaBancosCtas(i).getCodBanco())) ;
                		Base.logger.info( "Banco almanecado : "+arr.get(co2));
                		co2++;
                	}
                }
                
                String []aux2 = new String[arr.size()];
                for(int i = 0 ; i < arr.size() ; i++){
                	aux2[i] = arr.get(i);
                	Base.logger.info( "Banco agregado al Arreglo: "+aux2[i]);
                }
                
                String banco = aux2[index];
                int codigoBanco=0;
                for(int j = 0; j < bancos.getListaBancosCtas().length; j++){
                	if (banco.toUpperCase().equalsIgnoreCase(("Banco" + Base.getBancoCodigo(bancos.getListaBancosCtas(j).getCodBanco())).toUpperCase())){
                		codigoBanco = bancos.getListaBancosCtas(j).getCodBanco();
                		j = bancos.getListaBancosCtas().length;
                	}
                }
                
                int bco = codigoBanco;
                this.datos.setValue("Banco", bco);
                Base.logger.info( "Cod. Banco Seleccionado... "+bco);
                ParamSet posDat = Base.getParamSet("posDat");
                vista.hideAllEntries();
                
                Base.logger.info( "Instanciando Arreglo de Lista cuentas...");
                if(bancos.getListaBancosCtas().length == 0){
                    Base.logger.info("No existen bancos"); 
                    JOptionPane.showMessageDialog(null, "No se rescato el Listado de Bancos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                
                Base.logger.info( "Generando Arreglo auxiliar para listado de cuentas en pantalla.. ["+bancos.getListaBancosCtas().length+"]");
                //cuentas = new String[bancos.getListaBancosCtas().length];
                arr.clear();
               // arr.add(0,"Banco" + Base.getBancoCodigo(bancos.getListaBancosCtas(0).getCodBanco())) ;
                
                int cont = 0;
                for(int i = 0 ; i < bancos.getListaBancosCtas().length ; i++){
                	Base.logger.info( "Cod. Bco. Cta. ["+i+"]: "+bancos.getListaBancosCtas(i).getCodBanco()+" Cod. Bco. Selecc. "+bco);
                	if(bco == bancos.getListaBancosCtas(i).getCodBanco()){
                		//cuentas[cont] = bancos.getListaBancosCtas(i).getNumCtaCte();
                		arr.add(cont, bancos.getListaBancosCtas(i).getNumCtaCte());
                		Base.logger.info( "Nro de  Cuenta del banco seleccionado: "+arr.get(cont));
                		cont ++;
                	}
                }
                
                cuentas = new String[arr.size()];
                for(int i = 0 ; i < arr.size() ; i++){
                	cuentas[i] = arr.get(i);
                	Base.logger.info( "Cuenta agregada al Arreglo: "+cuentas[i]);
                }
                
                vista.setEntryMessage("Número Cuenta", true );
                vista.setEntryTitle("Número Cuenta", true );
                vista.setEntryTextLabel("Número Cuenta: ", true);
                vista.setEntryList(cuentas, true, false);
                //Base.logger.info(bancosS.get(index).getIntValue("Codigo"));
                //this.datos.setValue("Banco",bancosS.get(index).getIntValue("Codigo"));
                this.datos.setValue("Banco","Codigo banco");
                return ICajaView._WAITFORACTION;
                    
            case 4:
                estado = 5;
                index = vista.getEntryListIndex();
                this.datos.setValue("Cuenta", cuentas[index]);
                vista.setEntryTitle("Ingrese Fecha Depósito", true );
                vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                vista.setEntryTextLabel("Ingrese Fecha Depósito:", true);
                vista.setEntryText("", true, false, false,"[0-9]+","Fecha inválida");
                return ICajaView._WAITFORACTION;
                
            case 5:
                estado = 6;
                String fecha = vista.getEntryText();
                if(vista.getEntryText().equals("")){
                    this.datos.setValue("FechaDeposito",Tools.getFecha());
                }
                else{
                    SimpleDateFormat inFormat = new SimpleDateFormat( "yyyyMMdd" );
                    try {
                        Date date = inFormat.parse( fecha );
                        if(!inFormat.format(date).equals(fecha)){
                            estado = 5;
                            vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                            vista.setEntryTextLabel("Ingrese Fecha Depósito:", true);
                            vista.setEntryText("", true, false, false,"[0-9]+","Fecha inválida");
                            return ICajaView._WAITFORACTION;
                        }
                    } catch (ParseException e) {
                        estado = 5;
                        vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                        vista.setEntryTextLabel("Ingrese Fecha Depósito:", true);
                        vista.setEntryText("", true, false, false,"[0-9]+","Fecha inválida");
                        return ICajaView._WAITFORACTION;
                    }
                }
                this.datos.setValue("FechaDeposito",fecha);
                vista.setEntryTitle("Ingrese N°Documento", true );
                vista.setEntryMessage( "Ingrese N°Documento", true );
                vista.setEntryTextLabel("Ingrese N°Documento:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,30}","N°Documento no válido");
                return ICajaView._WAITFORACTION;
                
            case 6:
                estado = 7;
                this.datos.setValue("NumeroDocumento", vista.getEntryText());
                /**
                vista.setEntryMessage( "Ingrese Detalle Solicitante", true );
                vista.setEntryTextLabel("Ingrese Detalle Solicitante:", true);
                */
                vista.setEntryTitle("Ingrese Depositante", true );
                vista.setEntryMessage( "Ingrese Depositante", true );
                vista.setEntryTextLabel("Ingrese Depositante:", true);
                vista.setEntryText("", true, false, false,"[a-zA-Z[\\s]]{1,50}","Nombre depositante no válido");
                return ICajaView._WAITFORACTION;
                
            case 7:
            	
            	String nombreDepositante = vista.getEntryText();
            	
            	if (nombreDepositante.equalsIgnoreCase("")){
            		
            		 Base.logger.error("Nomre depositante Vacio");            
                     estado = 7; // O ....?
                     vista.setEntryMessage( "Debe Ingresar el Nombre del Depositante", true );
                     vista.setEntryTextLabel("Ingrese Depositante:", true);
                     vista.setEntryText("", true, false, false,null,null);
                     return ICajaView._WAITFORACTION;              		
            	}
            	
            	
            	
                if(vista.getEntryText().length() > 64){
                    this.datos.setValue("Solicitante", vista.getEntryText().substring(0, 64));
                }
                else{
                    this.datos.setValue("Solicitante", vista.getEntryText());
                }
                this.datos.show("Deposito");
                
            
                //Metemos al carro de medios de pago el servicio
                
                try {
                    vista.getOperTRV().addMedioPago(this);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                }
                return 13;
        /***********************************************NUEVOS DESARROLLOS*****************************************************************/        
            case 8:
            	vista.setEntryTitle( "Banco de Origen de Depósito", true );
                estado = 9;  
                vista.setEntryTextLabel("Bancos: ", true);
                vista.setEntryMessage( "Seleccione Banco", true );
                // TODO se implementa invocacion a servicio para traer bancos..
               // String[] bancos = Base.getBancos();
                String[] bancos = Base.traeBancos();
                vista.setEntryList(Base.getList(bancos,",",1), true, false);
                return ICajaView._WAITFORACTION;
                
            case 9: 
             	estado = 10;
                int indexCodBanco = vista.getEntryListIndex();
                vista.hideAllEntries();
                this.datos.setValue("CodBancoOrigen",Integer.parseInt(Base.getList( Base.traeBancos(), ",", 0)[indexCodBanco]));
                this.datos.setValue("BancoOrigen",Base.getList( Base.traeBancos(), ",", 1)[indexCodBanco]);
                Base.logger.info("Banco Origen Deposito: "+this.datos.getStringValue("BancoOrigen"));
                Base.logger.info("Código Banco Origen Deposito: "+this.datos.getStringValue("CodBancoOrigen"));
                
//                ParamSet posDatDep = Base.getParamSet("posDat");
//                String []bancoSeldccionado = posDatDep.getStringValue("banco").split("\\,");
//                for(int i = 0 ; i < bancoSeldccionado.length ; i++){
//                    if(this.datos.getStringValue("Banco").equals(bancoSeldccionado[i])){
//                        this.datos.setValue("TipoTotal", "MismoBanco");
//                        break;
//                    }
//                }
                vista.setEntryTitle( "Cuenta Origen de Depósito", true );
                vista.setEntryMessage( "Ingrese cuenta", true );
                vista.setEntryTextLabel("Ingrese Cuenta:", true);
                vista.setEntryText("", true, false, false,"[a-zA-Z0-9-]{1,20}","Numero de cuenta inv\u00E1lido");
                return ICajaView._WAITFORACTION;
            	
            	
            case 10:
            	
                estado = 11;
                String cuenta = vista.getEntryText();
//                if(cuenta.equals("") || cuenta.length() > 18){
//                    Base.logger.error("Error de tipo de datos");            
//                    estado = 10;
//                    vista.setEntryMessage( "Numero de cuenta invalido", true );
//                    vista.setEntryTextLabel("Ingrese Cuenta:", true);
//                    vista.setEntryText("", true, false, false,null,null);
//                    return ICajaView._WAITFORACTION;  
//                }
//                
//                
//                Pattern p = Pattern.compile("[^a-zA-Z0-9-$]");
//    			Matcher d = p.matcher(cuenta); 
//    			
//    			if(d.find()){			
//    				 estado = 10;
//                     vista.setEntryMessage( "Numero de cuenta invalido", true );
//                     vista.setEntryTextLabel("Ingrese Cuenta:", true);
//                     vista.setEntryText("", true, false, false,null,null);
//                     return ICajaView._WAITFORACTION;  
//    			}
                
                
                this.datos.setValue("CuentaOrigen",cuenta);
                Base.logger.info("Nro cuenta de origen de deposito: "+this.datos.getStringValue("CuentaOrigen"));    
                vista.setEntryTitle( "Rut Depositante", true );
                vista.setEntryMessage( "Ingrese rut del cliente (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null );
                return ICajaView._WAITFORACTION;
            
            case 11 : 
                        	
            	 if(Tools.validarRut(vista.getEntryText())){
                     this.datos.setValue("Rut", vista.getEntryText());
                     estado = 15;
                     return ICajaView._NOWAITFORACTION;
                 }   
                 else{                 
                     estado = 11;                    
                     vista.setEntryMessage( "Rut no v\u00E1lido - Reingrese Rut", true );
                     vista.setEntryTextLabel("Ingrese Rut:", true);
                     vista.setEntryText("", true, false, false,null,null);
                     return ICajaView._WAITFORACTION;
                 }
                       
            case 15:
            	estado = 16;
    			vista.hideAllEntries();
                vista.setEntryTitle( "Seleccione Sociedad", true );                
                vista.setEntryMessage( "Sociedad", true );
                vista.setEntryTextLabel("Seleccione:", true);
                
                String[] sociedades = Base.getSociedades();
                vista.setEntryList(Base.getList(sociedades,",",1), true, false);

                return ICajaView._WAITFORACTION;
                
                
            case 16:
    			estado = 2;
    			index = vista.getEntryListIndex();
    			codEmpresa= Integer.parseInt(Base.getList( Base.getSociedades(), ",", 0)[index]);
                valorListaVista=(Base.getList( Base.getSociedades(), ",", 1)[index]);
                
                this.datos.setValue("CodSociedadClaro",codEmpresa);
                this.datos.setValue("SociedadClaro",valorListaVista);
                
                Base.logger.info("Sociedad Seleccionada .... " + this.datos.getStringValue("SociedadClaro"));
                Base.logger.info("Codigo Sociedad Seleccionada .... " + this.datos.getStringValue("CodSociedadClaro"));
                 
                return ICajaView._NOWAITFORACTION;
      /************************************************************************************************************************/
                
        }
        
        return 0;
    }
    
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion("mpDepositoFisico");
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	
    	System.out.println("Fecha Deposito: "+this.getDatos().getStringValue("FechaDeposito"));
    	String year = this.getDatos().getStringValue("FechaDeposito").substring(0,4);
    	System.out.println("Anho Deposito: "+year);
    	String month = this.getDatos().getStringValue("FechaDeposito").substring(4,6);
    	System.out.println("Mes Deposito: "+month);
    	String day = this.getDatos().getStringValue("FechaDeposito").substring(6,8);
    	System.out.println("Dia Deposito: "+day);
    	
    	//mp.setFechaVencimiento((new GregorianCalendar(year,month - 1,day)).toString());
    	mp.setFechaVencimiento(year+"-"+month+"-"+day);
    	
    	mp.setNumeroDeposito(this.getDatos().getStringValue("NumeroDocumento"));
    	mp.setDepositante(this.getDatos().getStringValue("Solicitante"));
    	if(mp.getTipoTotal().equalsIgnoreCase("9")){
    		mp.setTipoTotal("9");
    	}else{
    		mp.setTipoTotal("41");
    	}
        
    }
    
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	Base.logger.info("Vaciando Medio de Pago Deposito...");
    	
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("Banco",mp.getCodigoBanco());
    	this.getDatos().setValue("Sucursal","");
    	this.getDatos().setValue("Cuenta",mp.getNumeroCtaCte());
    	this.getDatos().setValue("Serial",mp.getNumeroCheque());
    	this.getDatos().setValue("Rut",mp.getRutPagador());
    	this.getDatos().setValue("CodigoAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("Titular",mp.getNombrePagador());
    	this.getDatos().setValue("Fecha",mp.getFechaVencimiento());
    	this.getDatos().setValue("FechaDeposito",mp.getFechaVencimiento());
    	this.getDatos().setValue("NumeroDocumento",mp.getNumeroDeposito());
    	this.getDatos().setValue("Solicitante",mp.getDepositante());
    	//this.getDatos().setValue("TipoTotal","1002");
    	this.getDatos().setValue("TipoTotal","9");
    }
    
    
    public String toString() {
        // retorna detalle de documento
        String s = "";
        s = this.getNombre() + "\nMonto: " + Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        return s;
    }
    
    public boolean isIngresable(ICajaView vista){
        if(Base.getEdicion()){
            return false;
        }
        return true;
    }
}
