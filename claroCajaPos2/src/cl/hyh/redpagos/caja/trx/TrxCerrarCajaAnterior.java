package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
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

import javax.swing.JOptionPane;

import cl.hyh.cajas.ws.impl.ArqueoConcepto;
import cl.hyh.cajas.ws.impl.CierreDiarioOut;
import cl.hyh.cajas.ws.impl.ConsultaCierreDiarioIn;
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

public class TrxCerrarCajaAnterior implements ITrxBase{
    String origenDir;
    String destinoDir;
    String fecha;
    String hora;
    String agencia;
    String caja;
    String fName;
    int estado = 0;
    String usuario;
    protected Datos headerIn;
    ParamSet pSet = Base.getParamSet("posDat");
    
    public void init(Datos datosVista){        
        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){
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
	            vista.setEntryMessage( "Ingrese fecha AAAAMMDD", true );
                vista.setEntryTextLabel("Ingrese fecha:", true);
                vista.setEntryText("", true, false, false,"[0-9]+","Datos erroneos");
                return ICajaView._WAITFORACTION;
            case 1:
                estado = 2;
                fecha = vista.getEntryText();
                SimpleDateFormat inFormat = new SimpleDateFormat( "yyyyMMdd" );
                try {
                    Date date = inFormat.parse( fecha );
                    if(!inFormat.format(date).equals(fecha)){
                        estado = 1;
                        return ICajaView._NOWAITFORACTION;
                    }
                } catch (ParseException e) {
                    estado = 1;
                    return ICajaView._NOWAITFORACTION;
                }
                if( fecha.length() != 8 ){
                    estado = 1;
                    return ICajaView._NOWAITFORACTION;
                }
                return ICajaView._NOWAITFORACTION;
            case 2:
            	estado = 3;            	
	            vista.setEntryMessage( "Ingrese agencia", true );
                vista.setEntryTextLabel("Ingrese agencia:", true);
                vista.setEntryText(Long.toString(pSet.getLongValue("Agencia")), true, false, false,"[0-9]+","Datos erroneos");
                return ICajaView._WAITFORACTION;
            case 3:
            	agencia = vista.getEntryText();
            	estado = 4;
	            vista.setEntryMessage( "Ingrese usuario", true );
                vista.setEntryTextLabel("Ingrese usuario:", true);
                vista.setEntryText(pSet.getStringValue("Usuario"), true, false, false, null, null);
                return ICajaView._WAITFORACTION;
            case 4:
            	usuario = vista.getEntryText();
            	imprimeInforme(vista);
            	return 11;
    	}
    	return 0;
    }
    
    private void imprimeInforme(ICajaView vista){
    	ServerProxy pr = Proxy.getProxyInstance();
    	
    	HeaderIn hIn = new HeaderIn();
    	ConsultaCierreDiarioIn cIn = new ConsultaCierreDiarioIn();
    	
    	ParamSet pSet = Base.getParamSet("posDat");
    	ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
		
    	hIn.setAgencia((int)(pSet.getLongValue("Agencia")));
		hIn.setCajaFisica((int)(pSet.getLongValue("Caja")));
		hIn.setEntidad((int)(pSet.getLongValue("Entidad")));
		hIn.setCajero(Integer.parseInt(pSet.getStringValue("Cajero")));
		hIn.setSession(Integer.parseInt(pSet.getStringValue("SessionId")));
		hIn.setUsuario(pSet.getStringValue("Usuario"));
		hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));	
		
		cIn.setHeaderIn(hIn);
		cIn.setFechaArqueo(fecha);
		cIn.setAgencia(Integer.parseInt(agencia));
		cIn.setUsuario(usuario);
		
		CierreDiarioOut resp = null;
		vista.showBusyWindow("Consultando", "Espere por favor...");
		try {
			resp = pr.consultaCierreDiario(cIn);
		} catch (RemoteException e1) {
			vista.hideBusyWindow();
            JOptionPane.showMessageDialog(null, resp.getHeaderOut().getRcMessage(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
			Tools.logStackTrace(Base.logger, e1);
			return;
		}
		vista.hideBusyWindow();
		if(resp.getHeaderOut().getRc() != 0){
			JOptionPane.showMessageDialog(null, "[rc]:" + resp.getHeaderOut().getRc() + " [msg]:" + resp.getHeaderOut().getRcMessage(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
        String s = "";
        ArqueoConcepto arq = null;
        voucher.add(this.getLinea("ARQUEO DE CAJA", true,true,false,true));
        s = String.format("%-18s: %-15s", "Agencia", hIn.getAgencia());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Fecha", fecha);
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Usuario", resp.getNombreUsuario());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Usuario Tango", resp.getUsuarioTango());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Folio Tango", resp.getFolioTango());
        voucher.add(this.getLinea(s, false,false,false,false));
        /*s = String.format("%-18s: %-15s", "Usuario SMF", resp.getUsuarioSMF());
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-15s", "Folio SMF", resp.getFolioSMF());
        voucher.add(this.getLinea(s, false,false,false,false));*/
        s = String.format("%-18s: %-15s", "Cod. Sesión", Integer.toString(resp.getSesion()));
        voucher.add(this.getLinea(s, false,false,false,false));
        s = String.format("%-18s: %-8s %-8s %-8s","----","CAJERO","SISTEMA" ,"DIFERENCIA");
        voucher.add(this.getLinea(s, true,false,false,false));
        long totalCajero = 0;
        long totalSistema = 0;
        if(resp.getConceptos() != null){
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
	        for(int i = 0 ; i < resp.getConceptos().length ; i++){
	        	arq = resp.getConceptos()[i];
	        	if(!arq.getConcepto().contains("Remesa")){
		        	boolean bold = false;
		        	boolean under = false;
		        	boolean right = false;
		        	boolean center = false;     	
		        	s = String.format("%-18s: %-8s %-8s %-8s", Tools.getClaveDiccionario(arq.getConcepto()), Format.formatMonto(arq.getTotalCajero()),Format.formatMonto( arq.getTotalSistema()),Format.formatMonto( arq.getTotalCajero() - arq.getTotalSistema()));
		        	voucher.add(this.getLinea(s, bold, under,right,center));
		        	if(arq.getConcepto().equals("mpDevolucion")){
		        		totalCajero -= arq.getTotalCajero();
			        	totalSistema -= arq.getTotalSistema();
		        	}
		        	else{
		        		totalCajero += arq.getTotalCajero();
			        	totalSistema += arq.getTotalSistema();
		        	}	
	        	}
	        	else
	        		continue;
	        }
	        voucher.add(this.getLinea(" , ", false, true,true,false));
	        s = String.format("%-18s: %-8s %-8s %-8s", "TOTAL", Format.formatMonto(totalCajero),Format.formatMonto(totalSistema),Format.formatMonto( totalCajero - totalSistema));
	    	voucher.add(this.getLinea(s, true, false,false,false));
        }
        LineaVoucher oper = new LineaVoucher();
        ParamSet pList = Base.getParamSet( "posDat" ); 
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
		
        Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_CAJA,datosFileNet);
        vista.hideBusyWindow();
        
        /************************************************/
        /************************************************/
        /************************************************/
		ArrayList<String> conceptos = new ArrayList<String>();
		
		if(resp.getItemsArqueo() == null){
			return;
		}
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
            s = String.format("%-18s: %-15s", "Usuario", resp.getNombreUsuario());
            voucher.add(this.getLinea(s, false,false,false,false));
            s = String.format("%-18s: %-15s", "Usuario Tango", resp.getUsuarioTango());
            voucher.add(this.getLinea(s, false,false,false,false));
            s = String.format("%-18s: %-15s", "Folio Tango", resp.getFolioTango());
            voucher.add(this.getLinea(s, false,false,false,false));
            /*s = String.format("%-18s: %-15s", "Usuario SMF", resp.getUsuarioSMF());
            voucher.add(this.getLinea(s, false,false,false,false));
            s = String.format("%-18s: %-15s", "Folio SMF", resp.getFolioSMF());
            voucher.add(this.getLinea(s, false,false,false,false));*/
            s = String.format("%-18s: %-15s", "Cod. Sesión", Integer.toString(resp.getSesion()));
            voucher.add(this.getLinea(s, false,false,false,false));
        	voucher.add(this.getLinea("-------- " + Tools.getClaveDiccionario(conceptos.get(i)) + " --------", true,true,false,true));
            s = String.format("%-9s %-9s %-9s %-9s","Banco","Numero","Vcto" ,"Valor");
            voucher.add(this.getLinea(s, true,false,false,false));
            voucher.add(this.getLinea(" , ", false, true,true,false));
            
            long subTotal = 0;
            for(int j = 0 ; j < resp.getItemsArqueo().length ; j++){
            	ItemArqueo aux = resp.getItemsArqueo()[j];
            	if(!conceptos.get(i).equals(aux.getConcepto())){
            		continue;
            	}
            	subTotal += aux.getValor();
            	s = String.format("%-9s %-9s %-9s %-9s",aux.getBanco(),aux.getNumero(),aux.getVencimiento() ,Format.formatMonto(aux.getValor()));
                voucher.add(this.getLinea(s, true,false,false,false));
            }
            voucher.add(this.getLinea(" , ", false, true,true,false));
            voucher.add(this.getLinea("SubTotal (VTF) : ," + subTotal, true, false,true,false));
            voucher.add(this.getLinea(" , ", false, true,true,false));
            
            voucher.add(this.getLinea(" , ", false, true,true,false));
            voucher.add(this.getLinea("Total " + conceptos.get(i) + " : ," + subTotal, true, false,true,false));
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

    		
            Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_CAJA,datosFileNet);
            vista.hideBusyWindow();
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
