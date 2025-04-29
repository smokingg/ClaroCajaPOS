package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import ws.claro.cl.ArqueoConceptoDTO;
import ws.claro.cl.ItemArqueoDTO;
import ws.claro.cl.proxy.AppControlConsultarProxy;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.DetalleDocumento;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionIn;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.OperacionOut;
import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.ArchivoCuadratura;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.NotificadorOffline;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;

/**
 * Implementacion de la transaccion de consulta por Rut Claro
 * 
 * @author cbriones
 * 
 */
public class TrxClaroCierreContingencia implements ITrxBase {
	int estado = 0;
	String efectivo = "";
	String chequeDia = "";
	String montoDebito="";
	String montoCredito="";
	String montoMultitiendas="";
	Datos data;
	DocumentoPago doc;
	String[] docs = null;
	String tiposDocs = "";
	int index = 0;
	int indexCta = 0;
	String rut = "";
	String dv = "";
	String numeroCuenta="";
	

	// se setean como atributo DTO de salida del servicio App Control
	OperacionOut opOut = null;
	OperacionOut opOutIt = null;

	CuentaVTR cuenta = null;
	DetalleDocumento cuentaDocClaro = null;

	String trv = null;
	String fecha = "";
	String numeroDocumento = "";
	long montoDocumento = 0;

	public void init(Datos datosVista) {
		tiposDocs = "Siscli";
		trv = datosVista.getStringValue("btnParam");

	}

	public int execute(ICajaView vista, int key, Datos datos) {
		if (key == 0) {
			estado = 0;
			// vista.setInputTimeout(15);
		} else if (key == 1) {
			// Timeoutma
			return 13;
		} else if (key == KeyEvent.VK_ENTER) {
			if (estado == 10){
				return 13;
			}
		} else {
			// otra tecla. Lo que sea que esté en el XML...
			return ICajaView._PASSTHROUGH;
		}

		Base.logger.info("La invocacion se realizo desde: " + trv);

		switch (estado) {
		case 0:
			estado = 1;
			vista.setEntryTitle("Remesas", true);
			vista.setEntryMessage("Ingrese remesa en Efectivo", true);
			vista.setEntryTextLabel("Ingrese Efectivo", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			return ICajaView._WAITFORACTION;

		case 1:
			efectivo = vista.getEntryText();
			
			// MSPULVEDA: valido que el monto ingresado sea modulo de 10 
            // Proyecto: Ajuste sencillo
            if(Long.parseLong(efectivo) % AjusteSencillo.DIEZ != 0){
            	estado = 1;
                vista.setEntryMessage("Monto incorrecto, ingrese un valor redondeado", true );
    			vista.setEntryTextLabel("Ingrese Efectivo", true);
    			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
                return ICajaView._WAITFORACTION;
            }
			
			vista.hideAllEntries();
			vista.setEntryTitle("Remesas", true);
			vista.setEntryMessage("Ingrese Remesa en Cheque al Día", true);
			vista.setEntryTextLabel("Ingrese Monto Cheques:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			estado = 2;
			return ICajaView._WAITFORACTION;
		case 2:
			
			estado = 3;
			chequeDia=vista.getEntryText();
			vista.hideAllEntries();
			vista.setEntryTitle("Remesas", true);
			vista.setEntryMessage("Ingrese Remesa con Tarjeta Débito", true);
			vista.setEntryTextLabel("Ingrese Monto Débito:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			
			return ICajaView._WAITFORACTION;
			
		case 3:
			
			estado = 4;
			montoDebito = vista.getEntryText();
			vista.hideAllEntries();
			vista.setEntryTitle("Remesas", true);
			vista.setEntryMessage("Ingrese Remesa con Tarjeta Credito", true);
			vista.setEntryTextLabel("Ingrese Monto Credito:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			
			return ICajaView._WAITFORACTION;

		case 4:
			montoCredito = vista.getEntryText();
			estado = 5;
			vista.hideAllEntries();
			vista.setEntryTitle("Remesas", true);
			vista.setEntryMessage("Ingrese Remesa con Tarjeta de Multitiendas", true);
			vista.setEntryTextLabel("Ingrese Monto Multitiendas:", true);
			vista.setEntryText("", true, false, false, "[0-9]+","Número ingresado no válido");
			return ICajaView._WAITFORACTION;

		case 5:
			estado = 6;
			montoMultitiendas=vista.getEntryText();
			SimpleDateFormat inFormat = new SimpleDateFormat("yyyyMMdd");
			vista.setEntryMessage("Ingrese fecha AAAAMMDD de Remesa", true);
			vista.setEntryTextLabel("Ingrese fecha:", true);
			vista.setEntryText(inFormat.format(new Date()), true, true, false,"[0-9]+", "Datos erroneos");			
			return ICajaView._WAITFORACTION;
		case 6:
			estado = 7;
			fecha = vista.getEntryText();
			return ICajaView._NOWAITFORACTION;

		case 7:
			
			vista.paintButtons(Tools.getBotones(-1));
			vista.hideAllEntries();
			vista.setEntryTitle("Resumen de Remesas del Día en Contingencia", true);
			vista.setEntryTextArea("Remesa en Efectivo: "	+ Format.formatMonto(Long.parseLong(efectivo))
								+ "\nRemesa en Cheque al Día: " 	+ Format.formatMonto(Long.parseLong(chequeDia))
								+ "\nRemesa en T. Débito:: " 	+ Format.formatMonto(Long.parseLong(montoDebito))
								+ "\nRemesa en T. Credito: "	+ Format.formatMonto(Long.parseLong(montoCredito))
								+ "\nRemesa en T. Multitiendas: "	+ Format.formatMonto(Long.parseLong(montoMultitiendas))
								+ "\n" 
								+ "\nTotal Día: " + Format.formatMonto((Long.parseLong(efectivo) + Long.parseLong(chequeDia) + Long.parseLong(montoDebito) + Long.parseLong(montoCredito) + Long.parseLong(montoMultitiendas))), true);
			estado = 9;
			return ICajaView._WAITFORACTION;

		case 9:
			
	    	if( vista.showMyConfirmDialog("Confirme por favor","Esta operación no es reversible, ¿ha remesado todo el efectivo y cheques en su caja?\n Presione SI para continuar o NO para volver al menu principal")
	                == JOptionPane.NO_OPTION ) {
	            return 12;
	        }
	    	ParamSet pSet = Base.getParamSet("posDat");
	    	ParamSet posCfg = Base.getParamSet("posCfg");
	    	ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
			
			vista.hideAllEntries();
			 String s = "";
		        	        
		        //ArqueoConcepto arq = null;
		       
		        
		        /********************************************/
		        /******* Aca va el Arqueo de Caja  **********/
		        /********************************************/
		        Base.logger.info( "Generando Arqueo de Caja Contingencia...");
		        voucher.add(this.getLinea("ARQUEO DE CAJA CONTINGENCIA", true,true,false,true));
		        s = String.format("%-18s: %-15s", "Agencia", pSet.getStringValue("Agencia"));
		        voucher.add(this.getLinea(s, false,false,false,false));
		        s = String.format("%-18s: %-15s", "Fecha", fecha);
		        voucher.add(this.getLinea(s, false,false,false,false));
		        s = String.format("%-18s: %-15s", "Usuario", pSet.getStringValue("Usuario"));
		        voucher.add(this.getLinea(s, false,false,false,false));
		        
		        
		        s = String.format("%-18s: %-15s", "Cod. Sesión", Integer.toString(1));
		        voucher.add(this.getLinea(s, false,false,false,false));
		        s = String.format("%-13s: %-8s %-8s %-8s %-8s","----","CAJERO","SISTEMA", "AJUSTE" ,"DIFERENCIA");
		        // TODO setear en false primer parametro para achicar la letra !!!
		        voucher.add(this.getLinea(s, true,false,false,false));
		        
		        
		        
		        
		        
		        long totalCajero = (Long.parseLong(efectivo) + Long.parseLong(chequeDia) + Long.parseLong(montoDebito) + Long.parseLong(montoCredito) + Long.parseLong(montoMultitiendas));
		        long totalSistema = 0;
		        long Efectivo = 0;
		        long Cheque = 0;
		        long credito = 0;
		        long debito = 0;
		        long multitienda = 0;
		        long ajusteSencillo = 0;
		        
		        String totales [] = ArchivoCuadratura.procesaArchivoCuadratura();
		        
		        Efectivo = Long.parseLong(totales[0]);
		        Cheque= Long.parseLong(totales[1]);
		        debito= Long.parseLong(totales[2]);
		        credito= Long.parseLong(totales[3]);
		        multitienda= Long.parseLong(totales[4]);
		        ajusteSencillo= Long.parseLong(totales[5]);
		        
		    	//sacar este valor del archivo de cuadratura
		        totalSistema = Efectivo + Cheque + debito + credito + multitienda;
		        
	        	boolean bold = false;
	        	boolean under = false;
	        	boolean right = false;
	        	boolean center = false;     	
	        	
	        	s = String.format("%-13s: %-8s %-8s %-8s %-8s", "Efectivo", Format.formatMonto(Long.parseLong(efectivo)),Format.formatMonto(Efectivo),Format.formatMontoAjustado(ajusteSencillo),Format.formatMonto( Long.parseLong(efectivo) - Efectivo - ajusteSencillo));
	        	voucher.add(this.getLinea(s, bold, under,right,center));
	        	
	        	s = String.format("%-13s: %-8s %-8s %-8s %-8s", "Cheque Dia", Format.formatMonto(Long.parseLong(chequeDia)),Format.formatMonto( Cheque),Format.formatMonto( 0) , Format.formatMonto(Long.parseLong(chequeDia) - Cheque));
	        	voucher.add(this.getLinea(s, bold, under,right,center));
	        		        	
	        	s = String.format("%-13s: %-8s %-8s %-8s %-8s", "Crédito", Format.formatMonto( Long.parseLong(montoCredito)),Format.formatMonto(credito),Format.formatMonto( 0) ,Format.formatMonto(  Long.parseLong(montoCredito) - credito));
	        	voucher.add(this.getLinea(s, bold, under,right,center));
	        	
	        	s = String.format("%-13s: %-8s %-8s %-8s %-8s", "Débito", Format.formatMonto(Long.parseLong(montoDebito)),Format.formatMonto( debito),Format.formatMonto( 0) ,Format.formatMonto( Long.parseLong(montoDebito) - debito));
	        	voucher.add(this.getLinea(s, bold, under,right,center));
	        	
	        	s = String.format("%-13s: %-8s %-8s %-8s %-8s", "Multitienda", Format.formatMonto(Long.parseLong(montoMultitiendas)),Format.formatMonto( multitienda),Format.formatMonto( 0) ,Format.formatMonto( Long.parseLong(montoMultitiendas) - multitienda));
	        	voucher.add(this.getLinea(s, bold, under,right,center));
			        
				voucher.add(this.getLinea(" , ", false, true,true,false));
				s = String.format("%-13s: %-8s %-8s %-8s %-8s", "TOTAL REMESAS", Format.formatMonto(totalCajero),Format.formatMonto(totalSistema),Format.formatMontoAjustado(ajusteSencillo),Format.formatMonto( totalCajero - totalSistema - ajusteSencillo));
				voucher.add(this.getLinea(s, true, false,false,false));
//		        
		        
		        LineaVoucher oper = new LineaVoucher();
		       
		        oper.setLinea("Operador:" + pSet.getStringValue("Cajero"));        
		        voucher.add(oper);

		        //Agregamos la hora y la fecha
		        DateFormat df = DateFormat.getDateInstance();
		        Time t = new Time(System.currentTimeMillis());
		        oper = new LineaVoucher();
		        oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
		        voucher.add( oper );
		        
		        voucher.add(this.getLinea("", false, false, false, false));
		    	voucher.add(this.getLinea("******** CAJA CONTINGENCIA CERRADA ********", false, false,false,true));
		        
		        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
		        Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_CAJA_CONTINGENCIA_1,null);
		        Voucher.printVoucher(voucher, true,  Voucher.COPIA_CIERRE_CAJA_CONTINGENCIA_2,null);
		        vista.hideBusyWindow();
		        
		        if (Tools.fileCierreMove()){
		        	Base.logger.info("Se ha respaldado el cierre en contingencia ");
		        }else{
		        	Base.logger.info("No se ha logrado respaldar el cierre en contingencia ");
		        }
		        
		        vista.showBusyWindow("Enviando Mail...", "Espere por favor...");

		        if (NotificadorOffline.notificar()){
		        	JOptionPane.showMessageDialog(null, "Se ha Enviado Correctamente el Mail de la Transacciones Realizadas", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		        }else{
		        	
		        	JOptionPane.showMessageDialog(null, "No es posible Enviar el Archivo de Registro de Pagos\nEste envio se realizara en cuanto vuelva la caja Online", "Continuar", JOptionPane.INFORMATION_MESSAGE);
					Base.logger.info("No se ha Enviado Archivo CVS Contingencia");
		        }
		        vista.hideBusyWindow();
		     
		        pSet.setValue("CierreOffline","si");
		        pSet.save();
		       				
		    	return 11;

		}

		return 0;
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
