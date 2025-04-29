package cl.hyh.redpagos.caja.trx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Writer;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ArqueoConceptoDTO;
import ws.claro.cl.CierreCajaOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.ItemArqueoDTO;
import ws.claro.cl.OperacionDTO;
import ws.claro.cl.JustificacionDTO;
import ws.claro.cl.ObtenerDetalleCierreRequestDTO;
import ws.claro.cl.ObtenerDetalleCierreResponseDTO;
import ws.claro.cl.RemesaDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.hyh.cajas.ws.impl.ArqueoConcepto;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.cajas.ws.impl.CierreDiarioOut;
import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.ItemArqueo;
import cl.hyh.cajas.ws.impl.Request;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.Oper;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.ServicioOper;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefElement;
import cl.hyh.redpagos.caja.base.parser.DefField;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefRecord;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;

public class TrxCerrarCaja implements ITrxBase{
    String origenDir;
    String destinoDir;
    String fecha;
    String hora;
    String agencia;
    String caja;
    String fName;
    String user;
    protected Datos headerIn;
    private static int RC_CANCELAR = -3;
    private static int RC_OK = 0;
    private ObtenerDetalleCierreResponseDTO detalleCierre = null;
    private String fechaStr;
    
    public static class MyFilter implements FilenameFilter {
        public boolean accept( File dir, String name ) {
            if( name.endsWith( ".ctl" ) )
                return( true );
            else
                return( false );
        }
    }
    
	/**
	 * Al inicio verificamos si existe o no una fecha de sesión almacenada en
	 * posDat, de existir quiere decir que iniciamos el proceso de cierre de una
	 * fecha anterior.
	 */
    public void init(Datos datosVista){        
    	ParamSet pSet = Base.getParamSet("posDat");
		SimpleDateFormat fechaSession = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha = null;
		
		try {
			String fechaSessionStr = pSet.getStringValue("FechaSession");

			if (!"".equals(fechaSessionStr.trim())) {
				fecha = fechaSession.parse(fechaSessionStr);
				fechaStr = formatter.format(fecha);
			} else {
				fechaStr = Tools.getFechaDDMMYYYY();
			}
		} catch (ParseException e) {
			Base.logger
					.error("Se produjo un error en la base de datos post Invocar al servicio de Registro Sencillo..."
							+ e);
		}
    }
    
    /**
     * Elimino la fecha de session anterior para evitar cualquier problema 
     * con una nueva apertura
     */
    public void post() {
    	ParamSet pSet = Base.getParamSet("posDat");
    	pSet.setValue("FechaSession", "");
    }
    
    public int execute(ICajaView vista, int key, Datos datos){

    	int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane
				.showConfirmDialog(
						null,
						"Esta operación no es reversible, ¿ha remesado todo el efectivo y cheques en su caja?\n "
								+ "Presione SI para continuar o NO para volver al menu principal",
						"Confirme por favor", dialogButton);
		
		
		
		if (dialogResult == JOptionPane.NO_OPTION) {
			 return 10;
		} else {
			//Validamos que la caja se encuentra cuadada
			if (obtenerDetalleCierre() == RC_OK) {
				if (detalleCierre.getDiferenciaCuadratura() != 0) {
					String msg = "";
					long dif = detalleCierre.getDiferenciaCuadratura();
					
					String msgws = "";					
					if (!detalleCierre.getRetDesc().equals("OK")){
						msgws = ": "+detalleCierre.getRetDesc();
					}

					if (dif < 0) {
						msg = "Presenta diferencia de " + dif + ", le falta Justificación de pagos por realizar" + msgws;
					} else if (dif > 0) {
						msg = "Presenta diferencia de " + dif + ", le falta Justificación de remesas por realizar"+ msgws;
					}
					
					JOptionPane.showMessageDialog(null, msg,
							"Existe Diferencia", JOptionPane.INFORMATION_MESSAGE);
					return 10;
				}
			}
		}
    	
    	
    	//ServerProxy pr = Proxy.getProxyInstance();
    	AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
    	
    	//HeaderIn hIn = new HeaderIn();
    	HeaderDTO hIn = new HeaderDTO();
    	
    	ParamSet pSet = Base.getParamSet("posDat");
    	ParamSet posCfg = Base.getParamSet("posCfg");
    	ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
		
    	Base.logger.info( "Realizando Cierre de Caja Diario....");
    	hIn.setAgencia((pSet.getStringValue("Agencia")));
    	Base.logger.info( "Agencia: " +  hIn.getAgencia());
		hIn.setCajaFisica((pSet.getStringValue("Caja")));
		Base.logger.info( "Caja Fisica: " +  hIn.getCajaFisica());
		//hIn.setEntidad((pSet.getStringValue("Entidad")));
		//hIn.setCajero((pSet.getStringValue("Cajero")));
		hIn.setSession((pSet.getStringValue("SessionId")));
		Base.logger.info( "SessionId: " +  hIn.getSession());
		hIn.setUsuario(pSet.getStringValue("Cajero"));
		//hIn.setUsuario(pSet.getStringValue("CodigoRecaudador"));
		Base.logger.info( "Usuario: " +  hIn.getUsuario());
		//hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
		
		Base.logger.info( "Logout: " + pSet.getStringValue("Usuario") );
		user = pSet.getStringValue("Usuario");
		
	//	pSet.setValue( "Usuario", "" );
		pSet.setValue( "Perfil", "" );
	//	pSet.setValue( "Cajero", 0 );
		//pSet.setValue( "SessionId", "" );
		pSet.setValue( "SessionId", 0 );
		pSet.save();		
		
		//CierreDiarioOut resp = null;
		CierreCajaOutDTO resp = null;
		
		vista.showBusyWindow("Consultando", "Espere por favor...");
		try {
			//resp = pr.cierre(new Request(hIn));
			resp = pr.cierreCaja(hIn);
			Base.logger.info( "Cod. Resp Cierre Caja: " + resp.getRetCode());
			Base.logger.info( "Desc. Resp Cierre Caja: " + resp.getRetDesc());
		} catch (RemoteException e) {
			vista.hideBusyWindow();
            JOptionPane.showMessageDialog(null, "Excepción de comunicación.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			Tools.logStackTrace(Base.logger, e);
			return 11;
		} catch(Exception ex) {
			vista.hideBusyWindow();
			JOptionPane.showMessageDialog(null, resp.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
			Tools.logStackTrace(Base.logger, ex);
			return 11;
		}
		
		vista.hideBusyWindow();
		//if(resp.getHeaderOut().getRc() != 0){
		if(!"0".equalsIgnoreCase(resp.getRetCode())){
			//JOptionPane.showMessageDialog(null, "[rc]:" + resp.getHeaderOut().getRc() + " [msg]:" + resp.getHeaderOut().getRcMessage(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, "[rc]:" + resp.getRetCode() + " [msg]:" + resp.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
			return 11;
		}
		//Base.logger.info( "BANCO: " +  resp.getItemsArqueo(0).getBanco());
        String s = "";
        
        //fecha = Tools.getFecha();
        fecha = resp.getFecha();
        
        //ArqueoConcepto arq = null;
        ArqueoConceptoDTO arq = null;
        
        OperacionDTO[] conAnulacion;
        long montoTotalTbk = 0, montoTotal = 0;
        long cantidadTbk= 0, cantidad = 0;
        long montoAux = 0; 
        
        /********************************************/
        /******* Aca va el Arqueo de Caja  **********/
        /********************************************/
        Base.logger.info( "Generando Arqueo de Caja...");
        voucher.add(this.getLinea("CIERRE DE CAJA", true,true,false,true));
        s = String.format("%-18s: %-15s", "Agencia", hIn.getAgencia());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Fecha", fecha);
        voucher.add(this.getLinea(s, false,false,false,false));
        // No viene Nombre de usuario en DTO de Claro
        //s = String.format("%-18s: %-15s", "Usuario", resp.getNombreUsuario());
        s = String.format("%-18s: %-15s", "Usuario", user);
        voucher.add(this.getLinea(s, false,false,false,false));
        
        /** Se cambio DTO de salida en Claro, validar lo que se debe rescatar ???
        s = String.format("%-18s: %-15s", "Usuario ESAC", resp.getFolioESAC());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Folio ESAC", resp.getFolioESAC());
        voucher.add(this.getLinea(s, false,false,false,false));
        */

        s = String.format("%-18s: %-15s", "Cod. sesión", Integer.toString(resp.getSesion()));
        voucher.add(this.getLinea(s, false,false,false,false));
        
        //Imprimimos informacion de sencillo
        voucher.add(this.getLinea(" ", false, false,false,false));
        voucher.add(this.getLinea("Sencillo", true,true,false,false));
        s = String.format("%-18s: %-15s", "S. Apertura Caja", Format.formatMonto(detalleCierre.getSencilloApertura()));
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "S. Cierre Caja", Format.formatMonto(detalleCierre.getSencilloCierre()));
        voucher.add(this.getLinea(s, false,true,false,false));
        s = String.format("%-18s: %-15s", "Diferencia", Format.formatMonto(detalleCierre.getSencilloApertura() - detalleCierre.getSencilloCierre()));
        voucher.add(this.getLinea(s, false,false,false,false));
        voucher.add(this.getLinea(" ", false, false,false,false));
        
       // s = String.format("%-18s: %-8s %-8s %-8s","----","CAJERO","SISTEMA" ,"DIFERENCIA");
        s = String.format("%-13s  %-9s %-9s %-9s %-8s","","PAGOS","REMESA", "" ,"");
        // TODO setear en false primer parametro para achicar la letra !!!
        voucher.add(this.getLinea(s, true,false,false,false));
        s = String.format("%-13s: %-9s %-9s %-9s %-8s","OPERACIONES","INGRESADO", "REALIZADA", "AJUSTE", "DIFERENCIA");
        voucher.add(this.getLinea(s, true,false,false,false));
        
        long totalCajero = 0;
        long totalSistema = 0;
        long totalAjuste = 0;
        long depositoEfectivo = 0;
        long depositoChequeMismoBanco = 0;
        long depositoChequeOtroBanco = 0;
        
        //if(resp.getConceptos() != null){
        if(resp.getConceptosArqueo() != null){
	        /*for(int i = 0 ; i < resp.getConceptos().length ; i++){
	        	arq = resp.getConceptos()[i];
	        	if(arq.getConcepto().contains("Remesa")){
		        	boolean bold = false;
		        	boolean under = false;
		        	boolean right = false;
		        	boolean center = false;     	
		        	s = String.format("%-18s: %-8s %-8s %-8s", arq.getConcepto(), Format.formatMonto(arq.getTotalCajero()),Format.formatMonto( arq.getTotalSistema()),Format.formatMonto( arq.getTotalCajero() - arq.getTotalSistema()));
		        	voucher.add(this.getLinea(s, bold, under,right,center));
		        	totalCajero += arq.getTotalCajero();
		        	totalSistema += arq.getTotalSistema();
	        	}
	        	else
	        		continue;
	        }
	        voucher.add(this.getLinea(" , ", false, true,true,false));
	        s = String.format("%-18s: %-8s %-8s %-8s", "TOTAL REMESAS", Format.formatMonto(totalCajero),Format.formatMonto(totalSistema),Format.formatMonto( totalCajero - totalSistema));
	    	voucher.add(this.getLinea(s, true, false,false,false));*/
	    	totalCajero = 0;
	        totalSistema = 0;
	        //for(int i = 0 ; i < resp.getConceptos().length ; i++){
	        for(int i = 0 ; i < resp.getConceptosArqueo().length ; i++){
	        	arq = resp.getConceptosArqueo()[i];
	        	if(!arq.getConcepto().contains("Remesa") && !arq.getConcepto().contains("mpAjusteSencillo")){
		        	boolean bold = false;
		        	boolean under = false;
		        	boolean right = false;
		        	boolean center = false;     	
		        	//s = String.format("%-18s: %-8s %-8s %-8s", Tools.getClaveDiccionario(arq.getConcepto()), Format.formatMonto(arq.getTotalCajero()),Format.formatMonto( arq.getTotalSistema()),Format.formatMonto( arq.getTotalCajero() - arq.getTotalSistema()));
		        	// TODO: formatear la linea para achicar los textos....
		        //	s = String.format("%-18s: %-8s %-8s %-8s", arq.getConcepto(), Format.formatMonto(arq.getTotalCajero()),Format.formatMonto( arq.getTotalSistema()),Format.formatMonto( arq.getTotalCajero() - arq.getTotalSistema()));
		        	long ajuste = 0; 
		        	if("mpEfectivo".equals(arq.getConcepto())){
		        		ajuste = AjusteSencillo.getTotalAjuste(resp.getConceptosArqueo());
		        	}
		        	
		        	s = String.format("%-13s: %-9s %-9s %-9s %-8s", Tools.getClaveDiccionario(arq.getConcepto()), Format.formatMonto( arq.getTotalSistema()), Format.formatMonto(arq.getTotalCajero()), Format.formatMontoAjustado(ajuste) , Format.formatMonto( arq.getTotalCajero() - arq.getTotalSistema() - ajuste));
		        	voucher.add(this.getLinea(s, bold, under,right,center));
		        	if(arq.getConcepto().equals("mpDevolucion")){
		        		totalCajero -= arq.getTotalCajero();
			        	totalSistema -= arq.getTotalSistema();
		        	}
		        	else{
		        		totalCajero += arq.getTotalCajero();
			        	totalSistema += arq.getTotalSistema();
		        	}
		        	if(arq.getConcepto().equals("mpEfectivo")){
		        		depositoEfectivo = arq.getTotalCajero();
		        		totalAjuste+=ajuste;
		        	}
	        	}
	        	else
	        		continue;
	        }
	        voucher.add(this.getLinea(" , ", false, true,true,false));
	        
	        //s = String.format("%-18s: %-8s %-8s %-8s", "TOTAL REMESAS", Format.formatMonto(totalCajero),Format.formatMonto(totalSistema),Format.formatMonto( totalCajero - totalSistema));
	        s = String.format("%-13s: %-9s %-9s %-9s %-8s", "TOTAL REMESAS", Format.formatMonto(totalSistema), Format.formatMonto(totalCajero),Format.formatMonto(totalAjuste),Format.formatMonto( totalCajero - totalSistema - totalAjuste));
	    	voucher.add(this.getLinea(s, true, false,false,false));
        }
//        int totalAnulaciones = 0;
//        for(int j=0; j < resp.getAnulacionCierre().length; j++){
//        	totalAnulaciones += Integer.valueOf(resp.getAnulacionCierre(j).getMonto());
//        	
//        	
//        }
//        
//        System.out.println("Total Anulaciones = " + totalAnulaciones);
        
		        // Detalle de anulaciones [REspinoza]           
        Base.logger.info( "Obtener detalle anulaciones....");
        if(resp.getAnulacionCierre() != null) {
        	conAnulacion = resp.getAnulacionCierre();
        	for(int a = 0 ; a < conAnulacion.length ; a++){
        		OperacionDTO op = conAnulacion[a];
        		
        		montoAux = Long.parseLong(op.getMonto());
        		
        		if(op.getAnulableTbk().equalsIgnoreCase("S")) {
        			montoTotalTbk=montoTotalTbk+montoAux;
        			cantidadTbk++;
        		} else {
        			montoTotal=montoTotal+montoAux;
        			cantidad++;
        		}
        	}
        } else {
        	Base.logger.info( "No existe detalle anulaciones....");
        }
  
        voucher.add(this.getLinea("", false, false, false, false));
        
        s = String.format("%-16s: %-10s %-10s"," ","Cantidad","Monto Total");
        voucher.add(this.getLinea(s, true,false,false,false));
        
        s = String.format("%-16s:    %-10s %-10s", "Anulaciones \n de Operación", cantidad,Format.formatMonto(montoTotal));
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-16s:    %-10s %-10s", "Anulaciones \n de Transbank", cantidadTbk,Format.formatMonto(montoTotalTbk));
        voucher.add(this.getLinea(s, false,false,false,false));
        ///////////
		voucher.add(this.getLinea(" ", false, false, false, false));
		
		//Imprimimos la informacion de las justificaciones
		voucher = agruparImprimirJustificaciones(voucher);
		
		voucher.add(this.getLinea(" ", false, false, false, false));
		
		//Imprimimos info de remesas
		voucher = agruparImprimirRemesas(voucher);
		
		voucher.add(this.getLinea(" , ", false, true,true,false));
		
		s = String.format("%-23s: %-9s %-8s", "Total Final",
				"", Format.formatMonto(detalleCierre.getDiferenciaCuadratura()));
		voucher.add(this.getLinea(s, true, false, false, false));
		
		//Imprimimos informacion de Reversas
		s = String.format("%-23s: %-9s %-8s", "Reversas de operación",
				detalleCierre.getCantidadReversas(), Format.formatMonto(detalleCierre.getTotalMontoReversado()));
		voucher.add(this.getLinea(s, false, false, false, false));
		voucher.add(this.getLinea(" ", false, false, false, false));
        
        LineaVoucher oper = new LineaVoucher();
//        ParamSet pList = Base.getParamSet( "posDat" ); 
        oper.setLinea("Operador:" + hIn.getCajero());        
        voucher.add(oper);

        //Agregamos la hora y la fecha
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());
        oper = new LineaVoucher();
        oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
        voucher.add( oper );
        
        voucher.add(this.getLinea("", false, false, false, false));
    	voucher.add(this.getLinea("******** CAJA CERRADA ********", false, false,false,true));
        
        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
        


		DatosFileNet datosFileNet = new DatosFileNet();
		datosFileNet.setCodigo_sesion(pSet.getStringValue("SessionId"));
		datosFileNet.setCodusuario_envia(pSet.getStringValue("CodigoRecaudador"));
		datosFileNet.setEmail_para(null);
		datosFileNet.setNumOperacion(null);
		datosFileNet.setPropietario(pSet.getStringValue("Usuario"));
		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_CIERRE);
		
        Voucher.printVoucher(voucher, true, Voucher.COPIA_ARQUEO_CAJA,datosFileNet);
        vista.hideBusyWindow();
        
        
        /******************************************************/
        /****Aca van detalles de Remesa segun Medio Pago*******/
        /*****************************************************/
		ArrayList<String> conceptos = new ArrayList<String>();
		
		// TODO si no existen Items de Arqueo , no sigue generando Vouchers !!
		if(resp.getItemsArqueo() == null || posCfg.getStringValue("mismoBanco") == null){
			Base.logger.info( "No existen Items de Arqueo, no genera mas Vouchers...");
			return 11;
			//return 12;
		}
		
		Base.logger.info( "Generando Detalles de Remesa segun Medio de Pago...");
        for(int i = 0 ; i < resp.getItemsArqueo().length ; i++){
        	if(conceptos.contains(resp.getItemsArqueo()[i].getConcepto())){
        		continue;
        	}
        	else{
        		conceptos.add(resp.getItemsArqueo()[i].getConcepto());
        	}
        }
        
        for(int i = 0 ; i < conceptos.size() ; i++){
        	voucher = new ArrayList<LineaVoucher>();
        	s = String.format("%-18s: %-15s", "Agencia", hIn.getAgencia());
            voucher.add(this.getLinea(s, false,false,false,false));
            s = String.format("%-18s: %-15s", "Fecha", fecha);
            voucher.add(this.getLinea(s, false,false,false,false));
            // No viene usuario en dto de claro ??
            s = String.format("%-18s: %-15s", "Usuario", user);
            voucher.add(this.getLinea(s, false,false,false,false));
            
            /** validar que info se debe mostrar en cierre para claro ??
            s = String.format("%-18s: %-15s", "Usuario ESAC", resp.getUsuarioESAC());
            voucher.add(this.getLinea(s, false,false,false,false));
            s = String.format("%-18s: %-15s", "Folio ESAC", resp.getFolioESAC());
            voucher.add(this.getLinea(s, false,false,false,false));
            */
            
            s = String.format("%-18s: %-15s", "Cod. Sesión", Integer.toString(resp.getSesion()));
            voucher.add(this.getLinea(s, false,false,false,false));
            Base.logger.info( "Concepto Medio de Pago: "+conceptos.get(i));
        	voucher.add(this.getLinea("-------- " + Tools.getClaveDiccionario(conceptos.get(i)) + " --------", true,true,false,true));
            s = String.format("%-9s %-9s %-9s %-9s","Banco","Numero","Vcto","Valor");
            voucher.add(this.getLinea(s, true,false,false,false));
            voucher.add(this.getLinea(" , ", false, true,true,false));
            
            long subTotal = 0;
            Base.logger.info( "Validando Items de Arqueo para: "+Tools.getClaveDiccionario(conceptos.get(i)));
            for(int j = 0 ; j < resp.getItemsArqueo().length ; j++){
            	
            	//ItemArqueo aux = resp.getItemsArqueo()[j];
            	ItemArqueoDTO aux = resp.getItemsArqueo()[j];
            	Base.logger.info( "Concepto Item de Arqueo: "+aux.getConcepto());
            	if(!conceptos.get(i).equals(aux.getConcepto())){
            		continue;
            	}
            	Base.logger.info( "Valor de Item Arqueo: "+aux.getValor());
            	Base.logger.info( "Codigo Banco Item Arqueo: "+aux.getBanco());
            	Base.logger.info( "fecha vencimiento Item Arqueo: "+aux.getVencimiento());
            	Base.logger.info( "Codigo Mismo Banco Cfg: "+posCfg.getStringValue("mismoBanco"));
            	
            	// TODO se valida que si vienen en null algun parametro, se setea en blanco para efecto de impresion..
            	if(aux.getBanco() == null) aux.setBanco(" ");
            	if(aux.getNumero() == null) aux.setNumero(" ");
            	
            	if(conceptos.get(i).equals("mpCheque")){
            		Base.logger.info( "El Concepto de Arqueo es Medio de Pago Cheque....");
            		if(!"".equalsIgnoreCase(aux.getBanco()) && aux.getBanco()!=null 
            		&& posCfg.getStringValue("mismoBanco") != null && !"".equalsIgnoreCase(posCfg.getStringValue("mismoBanco"))){
	            		if(Integer.parseInt(aux.getBanco()) == Integer.parseInt(posCfg.getStringValue("mismoBanco"))){
	            			depositoChequeMismoBanco += aux.getValor();
	            		}
	            		else{
	            			depositoChequeOtroBanco += aux.getValor();
	            		}
            		}else{
            			depositoChequeOtroBanco += aux.getValor();
            		}
            		
            	}
            	subTotal += aux.getValor();
            	Base.logger.info( "Validando que existe Fecha de Vencimiento en Item Arqueo....");
            	if(aux.getVencimiento() != null) aux.setVencimiento(aux.getVencimiento().substring(0,10));
            	Base.logger.info( "Formateando Monto para Valor de Item Arqueo....");
            	s = String.format("%-9s %-9s %-9s %-9s",aux.getBanco(),aux.getNumero(),aux.getVencimiento() ,Format.formatMonto(aux.getValor()));
                voucher.add(this.getLinea(s, true,false,false,false));
            }
            
            voucher.add(this.getLinea(" , ", false, true,true,false));
            voucher.add(this.getLinea("SubTotal (VTF) : ," + subTotal, true, false,true,false));
            voucher.add(this.getLinea(" , ", false, true,true,false));
            
            voucher.add(this.getLinea(" , ", false, true,true,false));
           // voucher.add(this.getLinea("Total " + conceptos.get(i) + " : ," + subTotal, true, false,true,false));
            voucher.add(this.getLinea("Total " + Tools.getClaveDiccionario(conceptos.get(i)) + " : ," + subTotal, true, false,true,false));
            voucher.add(this.getLinea(" , ", false, true,true,false));
            
            oper = new LineaVoucher();
            oper.setLinea("Operador:" + hIn.getCajero());        
            voucher.add(oper);

            //Agregamos la hora y la fecha
            oper = new LineaVoucher();
            oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
            voucher.add( oper );
            
            voucher.add(this.getLinea("", false, false, false, false));
        	voucher.add(this.getLinea("******** CAJA CERRADA ********", false, false,false,true));
            
            vista.showBusyWindow("Imprimiendo", "Espere por favor...");
            


    		
            Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_CAJA + i,datosFileNet);
            vista.hideBusyWindow();            
        }
        

        /******************************************************/
        /****** 	Informacion de Anulaciones 		**********/
        /*****************************************************/
        Base.logger.info( "Generando Informacion de Anulaciones....");
        voucher = new ArrayList<LineaVoucher>();
        voucher.add(this.getLinea("INFORMACION ANULACIONES CAJA", true,true,false,true));
        s = String.format("%-18s: %-15s", "Agencia", hIn.getAgencia());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Fecha", fecha);
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Usuario", user);
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Cod. Sesión", Integer.toString(resp.getSesion()));
        voucher.add(this.getLinea(s, false,false,false,false));
        
        voucher.add(this.getLinea("", false, false, false, false));
        s = String.format("%-18s: %-15s", "Cantidad Reversas de Operación", cantidad);
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Monto Reversas de Operación", Format.formatMonto(montoTotal));
        voucher.add(this.getLinea(s, false,false,false,false));
        voucher.add(this.getLinea("", false, false, false, false));        

        s = String.format("%-24s: %-15s", "Cantidad Reversas Transbank", cantidadTbk);
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-24s: %-15s", "Monto Reversas Transbank ", Format.formatMonto(montoTotalTbk));
        voucher.add(this.getLinea(s, false,false,false,false));
        voucher.add(this.getLinea("", false, false, false, false));
        
        oper = new LineaVoucher();
        oper.setLinea("Operador:" + hIn.getCajero());        
        voucher.add(oper);

        //Agregamos la hora y la fecha
        oper = new LineaVoucher();
        oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
        voucher.add( oper );
        
        voucher.add(this.getLinea("", false, false, false, false));
    	voucher.add(this.getLinea("******** CAJA CERRADA ********", false, false,false,true));
        
        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
		
        Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_CAJA_ANULACION,datosFileNet);
        vista.hideBusyWindow();
        
        /*********************************************************/
        /***********Realizando Cierre de Pinpad*******************/
        /*********************************************************/
        
		Base.logger.info( "Realizando Cierre de Pinpad");
		
		try {
			Base.logger.info("P I N P A D - inicializa");
			Base.tbk.inicializa(pSet.getStringValue("TransbankConfig"));
		} catch (Exception e) {
			Base.logger.info( "Error inicializacion pinpad");
		}
			
		TbkMetodos metodos = new TbkMetodos();
		String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
		metodos.cierre(Base.tbk,requerimiento);
		metodos.confirmarOperacion(Base.tbk);
        
        /******************************************************/
        /******Aca se genera la Info de Depositos ???*********/
        /*****************************************************/
        Base.logger.info( "Generando Informacion de depositos....");
        voucher = new ArrayList<LineaVoucher>();
        voucher.add(this.getLinea("INFORMACION DEPOSITOS CAJA", true,true,false,true));
        s = String.format("%-18s: %-15s", "Agencia", hIn.getAgencia());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Fecha", fecha);
        voucher.add(this.getLinea(s, false,false,false,false));
        // No viene usuario en Dto de claro ??
        s = String.format("%-18s: %-15s", "Usuario", user);
        voucher.add(this.getLinea(s, false,false,false,false));
        
        /** valida lo que se debe desplegar en Caja de Claro ???
        s = String.format("%-18s: %-15s", "Usuario ESAC", resp.getUsuarioESAC());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Folio ESAC", resp.getFolioESAC());
        voucher.add(this.getLinea(s, false,false,false,false));
        */
        
        s = String.format("%-18s: %-15s", "Cod. Sesión", Integer.toString(resp.getSesion()));
        voucher.add(this.getLinea(s, false,false,false,false));
        
        voucher.add(this.getLinea("", false, false, false, false));
        s = String.format("%-18s: %-15s", "Cuenta Depósito", pSet.getStringValue("cuentaDeposito"));
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Banco Depósito", pSet.getStringValue("bancoDeposito"));
        voucher.add(this.getLinea(s, false,false,false,false));
        voucher.add(this.getLinea("", false, false, false, false));        

        s = String.format("%-24s: %-15s", "Dep. Efectivo", Format.formatMonto(depositoEfectivo));
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-24s: %-15s", "Dep. Cheque Mismo Banco", Format.formatMonto(depositoChequeMismoBanco));
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-24s: %-15s", "Dep. Cheque Otro Banco", Format.formatMonto(depositoChequeOtroBanco));
        voucher.add(this.getLinea(s, false,false,false,false));
        voucher.add(this.getLinea("", false, false, false, false));
        
        oper = new LineaVoucher();
        oper.setLinea("Operador:" + hIn.getCajero());        
        voucher.add(oper);

        //Agregamos la hora y la fecha
        oper = new LineaVoucher();
        oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
        voucher.add( oper );
        
        voucher.add(this.getLinea("", false, false, false, false));
    	voucher.add(this.getLinea("******** CAJA CERRADA ********", false, false,false,true));
        
        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
        
        
        Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_PINPAD,datosFileNet);
        vista.hideBusyWindow();
        
        // TODO, se debiese vaciar el carro de documentos y medios de pago..
		// Para eliminar proceso de Edicion de Pago !!!
		Base.logger.info( "OperTRV: " + vista.getOperTRV() );
		if(vista.getOperTRV() != null){
			
			vista.getOperTRV().getCarroCompras().getDocumentos().clear();
			Base.logger.info( "Carro de Documentos: " + vista.getOperTRV().getCarroCompras().getDocumentos() );
			
			vista.getOperTRV().getCarroMediosPago().borrarCarro();
			Base.logger.info( "Carro de Medio Pago: " + vista.getOperTRV().getCarroMediosPago() );
			
			Base.setEdicion(false);
			Base.logger.info( "Es Edicion: " + Base.getEdicion() );
		}
		
		
		post();
    	return 11;
    }

    /**
     * Recorre agrupa e imprime las justificaciones en el voucher de cierre
     * @param voucher
     */
	@SuppressWarnings("rawtypes")
	private ArrayList<LineaVoucher> agruparImprimirJustificaciones(
			ArrayList<LineaVoucher> voucher) {
		HashMap<String, Par<Integer, Long>> justificaciones = new HashMap<String, Par<Integer, Long>>();
		String s = "";
		long totalJustificacion = 0;
		int cantidad = 0;
		
		// Imprimimos infromacion de justificaciones
		voucher.add(this.getLinea(" ", false, false, false, false));
		s = String.format("%-23s  %-9s %-8s", "JUSTIFICACIONES DE PAGO", "CANTIDAD", "MONTO TOTAL");
		voucher.add(this.getLinea(s, true, false, false, false));
		
		// Recorremos el listado de justificaciones y los agrupamos por el tipo
		if (detalleCierre.getJustificaciones() != null)
			for (int i = 0; i < detalleCierre.getJustificaciones().length; i++) {
				JustificacionDTO justificacion = detalleCierre
						.getJustificaciones(i);
				String llave = justificacion.getMotivo()
						.getDescMotivoJustificacion();
				if (justificaciones.containsKey(llave)) {
					Par<Integer, Long> p = justificaciones.get(llave);
					p.setCantidad(p.getCantidad() + 1);
					p.setTotal(p.getTotal() + justificacion.getMontoJustificado());
	
					justificaciones.remove(llave);
					justificaciones.put(llave, p);
				} else {
					Par<Integer, Long> p = new Par<Integer, Long>(1,
							justificacion.getMontoJustificado());
					justificaciones.put(llave, p);
				}
				totalJustificacion += justificacion.getMontoJustificado();
				cantidad++;
			}

		Iterator it = justificaciones.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String nombre = e.getKey().toString();
			
			if (nombre.length() > 23) {
				nombre = nombre.substring(0, 22);
			}
			s = String.format("%-23s: %-9s %-8s", nombre,
					((Par) e.getValue()).getCantidad(),
					Format.formatMonto((Long) ((Par) e.getValue()).getTotal()));

			if (it.hasNext()) {
				voucher.add(this.getLinea(s, false, false, false, false));
			} else {
				voucher.add(this.getLinea(s, false, true, false, false));
			}
		}
		s = String.format("%-23s: %-9s %-8s", "TOTAL JUSTIFICACIONES", cantidad,
				Format.formatMonto(totalJustificacion));
		voucher.add(this.getLinea(s, true, false, false, false));
		return voucher;
	}
	
	/**
	 * Recorre agrupa e imprime las justificaciones en el voucher de cierre
	 * 
	 * @param voucher
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<LineaVoucher> agruparImprimirRemesas(
			ArrayList<LineaVoucher> voucher) {
		HashMap<String, Par<Integer, Long>> remesas = new HashMap<String, Par<Integer, Long>>();
		String s = "";
		long totalJustificacion = 0;
		int cantidad = 0;

		// Imprimimos infromacion de justificaciones
		voucher.add(this.getLinea(" ", false, false, false, false));
		s = String.format("%-23s  %-9s %-8s", "REMESAS", "CANTIDAD", "MONTO TOTAL");
		voucher.add(this.getLinea(s, true, false, false, false));

		// Recorremos el listado de justificaciones y los agrupamos por el tipo
		if (detalleCierre.getRemesas() != null)
			for (int i = 0; i < detalleCierre.getRemesas().length; i++) {
				RemesaDTO remesa = detalleCierre.getRemesas(i);
				String llave = remesa.getDescripcionOperacion();
				if (remesas.containsKey(llave)) {
					Par<Integer, Long> p = remesas.get(llave);
					p.setCantidad(p.getCantidad() + 1);
					p.setTotal(p.getTotal() + remesa.getMontoOperacion());
	
					remesas.remove(llave);
					remesas.put(llave, p);
				} else {
					Par<Integer, Long> p = new Par<Integer, Long>(1,
							remesa.getMontoOperacion());
					remesas.put(llave, p);
				}
				totalJustificacion += remesa.getMontoOperacion();
				cantidad++;
			}

		Iterator it = remesas.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			s = String.format("%-23s: %-9s %-8s", e.getKey(),
					((Par) e.getValue()).getCantidad(),
					Format.formatMonto((Long) ((Par) e.getValue()).getTotal()));

			if (it.hasNext()) {
				voucher.add(this.getLinea(s, false, false, false, false));
			} else {
				voucher.add(this.getLinea(s, false, true, false, false));
			}
		}
		s = String.format("%-23s: %-9s %-8s", "TOTAL REMESAS", cantidad,
				Format.formatMonto(totalJustificacion));
		voucher.add(this.getLinea(s, true, false, false, false));
		return voucher;
	}
    
    /**
     * 
     */
	public int obtenerDetalleCierre() {
		ParamSet pSet = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		ObtenerDetalleCierreRequestDTO odc = new ObtenerDetalleCierreRequestDTO();

		try {
			Base.logger.info("Obtener obtenerDetalleCierre....");
			odc.setCodSesion(Long.parseLong(pSet.getStringValue("SessionId")));
			Base.logger.info("CodSesion: " + odc.getCodSesion());
			odc.setFecha(fechaStr);
			Base.logger.info("Fecha: " + odc.getFecha());
			odc.setIdAgencia(Long.parseLong(pSet.getStringValue("Agencia")));
			Base.logger.info("Agencia: " + odc.getIdAgencia());
			odc.setIdCaja(Long.parseLong(pSet.getStringValue("Caja")));
			Base.logger.info("Caja: " + odc.getIdCaja());
			//odc.setIdCajero(Long.parseLong(pSet.getStringValue("Cajero")));
			//Base.logger.info("Cajero: " + odc.getIdCajero());
			odc.setIdCajero(Long.parseLong(pSet.getStringValue("CodigoRecaudador")));
			Base.logger.info("Recaudador: " + odc.getIdCajero());

			detalleCierre = pr.obtenerDetalleCierre(odc);
			return RC_OK;
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Error al obtener notificar diferencias", "Continuar",
					JOptionPane.INFORMATION_MESSAGE);
			Base.logger
					.error("Se produjo un error al Invocar al servicio de diferencias envio..."
							+ e);
			return RC_CANCELAR;
		}
	}
	
	/**
	 * 
	 * Clase destinada a agrupar tanto la cantidad como totales de remesas y
	 * justificaciones al momento de imprimir el voucher
	 * 
	 * @param <Integer>
	 *            : cantidad de justificaciones o remesas de esta agrupacion
	 * @param <Long>
	 *            : monto total de remesas o justificacioness
	 */
	class Par<Integer, Long> {

		private Integer cantidad;
		private Long total;

		public Par(Integer cantidad, Long total) {
			this.cantidad = cantidad;
			this.total = total;
		}

		public Integer getCantidad() {
			return cantidad;
		}

		public void setCantidad(Integer l) {
			this.cantidad = l;
		}

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long r) {
			this.total = r;
		}

		@Override
		public int hashCode() {
			return cantidad.hashCode() ^ total.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (!(o instanceof Par))
				return false;
			Par pairo = (Par) o;
			return this.cantidad.equals(pairo.getCantidad())
					&& this.total.equals(pairo.getTotal());
		}

	}
    
    public LineaVoucher getLinea(String mensaje, boolean bold,boolean underline,boolean right,boolean tri){
        LineaVoucher oper = new LineaVoucher();
        oper.setBold(bold);
        oper.setUnderline(underline);
        oper.setRight(right);
        oper.setCenter(tri);
        oper.setLinea(mensaje);
        
        return oper;
    }
}
