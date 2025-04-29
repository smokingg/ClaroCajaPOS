package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarOperTbkInDTO;
import ws.claro.cl.OperacionTbkResponseDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.URLString;
import cl.hyh.redpagos.caja.base.Voucher;

/**
 * 
 * @author REspinoza
 *
 */
public class ReImpresionTbkPorRut implements ITrxBase, Serializable {

    private static final long serialVersionUID = 1L;
    
    int estado = 0;
    String idTransBank="";
    String local="";
    String caja="";
    String fecha="";
    String nroOpe="";
    String nroSec="";
    String[] opt;
	String[] optOrigen;
	String codigo;
	String codigoTbk;
	String msgTbkReturn;
	LineaVoucher[] voucherComercioDuplicado = null;
	LineaVoucher[] voucherComercioDuplicadoCliente = null;
	ArrayList<LineaVoucher> comprobante;
	ArrayList<LineaVoucher> comprobanteCliente;
	OperacionTbkResponseDTO respuesta = null;
	String codTbkReturn;
	
    public void init(Datos data){
        
    }
    public int execute(ICajaView vista, int key, Datos htParam){
        if( key == 0 ) {            
            estado = 0;
            //vista.setInputTimeout(15);
            
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }else if( key == KeyEvent.VK_F7){
            estado = 7;
        }else if( key == KeyEvent.VK_F8){
            estado = 6;
        } 
        else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9){
            ParamSet pList = Base.getParamSet( "posDat" );
            pList.setValue( "FechaPago", Tools.getFecha() );
            return ICajaView._PASSTHROUGH;
        } 
        else {
            // otra tecla. Lo que sea que esta en el XML...
            return ICajaView._PASSTHROUGH;
        }
        switch( estado ) {

            case 0:
                estado = 1;
                vista.setEntryTitle("Reimpresion Voucher por Rut",  true);
                vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
    			vista.setEntryTextLabel("Ingrese Rut:", true);
    			vista.setEntryText("", true, false, false,null,null);
    			return ICajaView._WAITFORACTION;
                                
            case 1:
            	
    			codigo = vista.getEntryText();
    			
	   			 if("".trim().equalsIgnoreCase(codigo)){
	                    estado = 1;
	                    vista.setEntryMessage( "No ha ingresado ningun Rut - Ingrese nuevamente", true );
	                    vista.setEntryTextLabel("Ingrese Rut:", true);
	                    vista.setEntryText("", true, false, false,null,null);
	                    return ICajaView._WAITFORACTION;
	                }
	   			
	   			
	   			if(!Tools.validarRut(codigo)){
	   				estado = 1;
	   				vista.setEntryMessage( "RUT  Inválido - Ingrese nuevamente", true );
	   				vista.setEntryTextLabel("Ingrese Rut:", true);
	   				vista.setEntryText("", true, false, false,null,null);
	   				return ICajaView._WAITFORACTION;
	   			}
               
                estado = 2;
                return ICajaView._NOWAITFORACTION;
                
            case 2:
            	
            	estado = 3;
            	String rut = codigo.substring(0, codigo.length()-2);
            	String dv = codigo.substring( codigo.length()-1);
            	
            	AppControlCajaWSServerProxy consultaReimprecionRut = AppControlProxy.getProxyInstance();
            	
            	ConsultarOperTbkInDTO in = new ConsultarOperTbkInDTO();
            	in.setRut(rut);
            	in.setDv(dv);
            	
			try {
				
				respuesta  = consultaReimprecionRut.consultarOperTbkRut(in);
				
			} catch (RemoteException e) {
				 e.printStackTrace();
				 JOptionPane.showMessageDialog(null, "Error al consultar por Rut", "Error", JOptionPane.INFORMATION_MESSAGE);
		         return 11;
			}
				long respuestaRegistros = respuesta.getCantRegistros();
    			int registros = (int) respuestaRegistros;
    			
    			if (registros > 0) {
    			
            	// se va a buscar la data por el rut y se llena la lista
            	vista.hideAllEntries();
                vista.setEntryTitle( "Seleccione Transacción", true );                
                vista.setEntryMessage( "Transacción", true );
                vista.setEntryTextLabel("Seleccione:", true);
                opt = new String[registros+1];
                optOrigen = new String[registros];
                
                opt[0] = String.format("%-28s %-14s %-10s %-14s %-10s","Id Transaccion Transbank","Rut Cliente","Monto","Suc.","Fecha Pago");
                for (int i=0; i<registros; i++){
                	
                	 opt[i+1] = String.format("%-28s %-14s %-10s %-14s %-10s",respuesta.getOperacionTbkList(i).getIdtrxtbk(),respuesta.getOperacionTbkList(i).getRut()+"-" + respuesta.getOperacionTbkList(i).getDv() ,respuesta.getOperacionTbkList(i).getMonto(),respuesta.getOperacionTbkList(i).getCodagencia(), respuesta.getOperacionTbkList(i).getFechapago());
                	 optOrigen[i] = respuesta.getOperacionTbkList(i).getIdtrxtbk();
                	 
                }

                vista.setEntryList(opt, true, false);
    			}else{
    				 JOptionPane.showMessageDialog(null, "No existen Vouchers para el rut consultado", "Info", JOptionPane.INFORMATION_MESSAGE);
                     return 11;
    			}
                return ICajaView._WAITFORACTION;
            
            case 3:
            	vista.hideAllEntries();
            	int index = vista.getEntryListIndex();
            	
            	if (index == 0){
            		estado = 2;
            		return ICajaView._NOWAITFORACTION;
            	}else{
            		index=index-1;
            	}
            	
            	idTransBank =optOrigen[index];
            	String origen = "";
            	
            	String carType = obtenerTipoTotalTarjeta(respuesta.getOperacionTbkList(index).getTipoTrx());
            	
            	if(carType.equalsIgnoreCase("credito")){
            		origen = "CR";
            	}
            	
            	if(carType.equalsIgnoreCase("debito")){
            		origen = "DB";
            	}
            	
            	if(carType.equalsIgnoreCase("multitienda")){
            		origen = "NB";
            	}
               
                String[]voucher = null;
                String[]voucherDuplicado = null;
                LineaVoucher vL = null;

            	vista.showBusyWindow("Consultando", "Espere por favor...");
            	
            	String respuestaVoucher = "";
            	
            try {
            	ParamSet posDat = Base.getParamSet("posDat");
            	Base.logger.info("P I N P A D - inicializa");
            	Base.tbk.inicializa(posDat.getStringValue("TransbankConfig"));
       		    TbkMetodos metodos = new TbkMetodos();

            	String requerimiento = "IDTRXORIGINAL=" + idTransBank +"&" + 
            							"TIPTRX=TBKREM&" + 
            							"TIPTRXORIG=" + origen +"&" + 
            							"FECHA=" + idTransBank.substring(8, 16) +"&" +
            							"NROOPE=" + idTransBank.substring(16, 22) +"&" + 
            							"LOCAL="+ idTransBank.substring(0, 4) +"&" + 
            							"CAJA=" + idTransBank.substring(4, 8)+"";
            	
            	System.out.println("requerimiento : " + requerimiento);
        		        		 
            	Base.logger.info("P I N P A D - reimpresion");
        		 respuestaVoucher = metodos.reimpresion(Base.tbk, requerimiento);
        		 Base.logger.info(respuestaVoucher);

        		 if(respuestaVoucher!=null){
        			 
        			 URLString urlVC = new URLString(respuestaVoucher);
        			 
                     //Obtener código retornado por Transbank
        			 codTbkReturn=urlVC.getValor("AUTRET")==null?codTbkReturn=urlVC.getValor("TXCRET"):urlVC.getValor("AUTRET");              		
		             msgTbkReturn= urlVC.getValor("TXCGLO")==null?"":urlVC.getValor("TXCGLO");
//        			 msgTbkReturn= Format.getMesageTbk(urlVC);
		             msgTbkReturn="RetTbk [" + codTbkReturn + "]. " + Format.formatMsjTbk(msgTbkReturn);
        		 
        			 if (respuestaVoucher.contains("TXCRET") && respuestaVoucher.contains("VOUCHER")){
				        		 
				        		 if (urlVC.getValor("TXCRET") != null) {
				        			 
				        			 if(urlVC.getValor("TXCRET").equals("00")){
				        				 
				        				 
				                         //[REspinoza] Multicard formatea voucher cliente y comercio
				        	         	String strVoucherCliente = metodos.ObtenerVouchersClienteComercio(urlVC.getValor("VOUCHER"));
				        	         	URLString urlVoucher = new URLString(strVoucherCliente);
				        	         	String vcom = urlVoucher.getValor("VOUCHER_COMERCIO");
				        	         	if (vcom.contains("%20%20"))
				        	         	{
				        	         	  vcom = URLDecoder.decode(vcom);
				        	         	}
				        	         	voucher = vcom.split("\\n");

				                		 voucherComercioDuplicado = new LineaVoucher[voucher.length];
				                		
				                         for(int i = 0 ; i < voucher.length ; i++){
				                             vL = new LineaVoucher();
				                             vL.setLinea(voucher[i].replaceAll("\\r", ""));
				                             vL.setSmall(true);
				                             voucherComercioDuplicado[i] = vL;
				                             Base.logger.info("Linea Voucher Copia Comercio: ["+vL.getLinea()+"]");
				                         }
				                         generaComprobante(voucherComercioDuplicado);                         
				                         
				                         String vcli = urlVoucher.getValor("VOUCHER_CLIENTE");
				                         if (vcli.contains("%20%20"))
				                         {
				                           vcli = URLDecoder.decode(vcli);
				                         }
				                         voucherDuplicado = vcli.split("\\n");
//				                       voucherDuplicado = urlVC.getValor("VOUCHER").replace("ORIGINAL COMERCIO - COPIA CLIENTE", "         COPIA CLIENTE").split("\\n");
				                       voucherComercioDuplicadoCliente = new LineaVoucher[voucherDuplicado.length];

				                         for(int i = 0 ; i < voucherDuplicado.length ; i++){
				                             vL = new LineaVoucher();
				                             vL.setLinea(voucherDuplicado[i].replaceAll("\\r", ""));
				                             vL.setSmall(true);
				                             voucherComercioDuplicadoCliente[i] = vL;
				                             Base.logger.info("Linea Voucher Copia Comercio: ["+vL.getLinea()+"]");
				                         }    
				                         
				                         generaComprobanteDuplicado(voucherComercioDuplicadoCliente);
				                         Voucher.printVoucher(comprobanteCliente, false, Voucher.COPIA_TBK_CLIENTE, null);
				                         Voucher.printVoucher(comprobante, false, Voucher.COPIA_TBK_LOCAL,null);
				        			 
				        			 	} else {
				                			vista.hideBusyWindow();
						                    
				                			if(msgTbkReturn.trim().equalsIgnoreCase("")){
						                    	 msgTbkReturn = " Error al realizar pago Transbank, reintentar...";
						                    }
				                			
				         		            Base.logger.error(msgTbkReturn);
				         		            
				         		           JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
						                    		"Error", JOptionPane.INFORMATION_MESSAGE);
				         		            return 11;
				                		 }
				        		 }else{
					        			vista.hideBusyWindow();
					        			Base.logger.error("RetTbk [" + codTbkReturn + "]. " + msgTbkReturn);
					        			String mensaje = "";
					        			
					                     if(msgTbkReturn.trim().equalsIgnoreCase("")){
					                    	 mensaje="RetTbk [" + codTbkReturn + "]. " + "Error al realizar reimpresion, reintentar...";
					                     } else {
					                    	 mensaje="RetTbk [" + codTbkReturn + "]. " + msgTbkReturn;
					                     }
					        			
					 		            JOptionPane.showMessageDialog(null, mensaje.length()>100?Format.formatText(mensaje):mensaje,
					 		            		"Error", JOptionPane.INFORMATION_MESSAGE);
					 		            return 11;
				        		 }

        		 		} else if (respuestaVoucher.contains("Transaccion anulada")){
	        			 	vista.hideBusyWindow();
		 		            Base.logger.error("No se pudo Imprimir el Voucher. Transacción ya fué anulada.");
		 		            JOptionPane.showMessageDialog(null, "Error al tratar de reimprimir el Voucher. Transacción ya fué anulada.", "Error", JOptionPane.INFORMATION_MESSAGE);
		 		            return 11;
        		 		} else {
	        			 	vista.hideBusyWindow();
		 		            Base.logger.error(codTbkReturn + "No se pudo Imprimir el Voucher : " + msgTbkReturn);
		 		            String mensaje = "";
		 		            
		 		            if (msgTbkReturn.equalsIgnoreCase("")) {
		 		            	mensaje = "Error al reimprimir el Voucher : " + respuestaVoucher;
		 		            } else {
		 		            	mensaje = "RetTbk [" + codTbkReturn + "]. " + msgTbkReturn;
		 		            }
		 		            
		 		            JOptionPane.showMessageDialog(null, mensaje.length()>100?Format.formatText(mensaje):mensaje,
		 		            		"Error", JOptionPane.INFORMATION_MESSAGE);
		 		            return 11;
		        		 }
        		 }else{
        			 vista.hideBusyWindow();
	 		            Base.logger.error("No se pudo Imprimir el Voucher");
	 		            JOptionPane.showMessageDialog(null, "Error al tratar de reimprimir el Voucher.", "Error", JOptionPane.INFORMATION_MESSAGE);
	 		            return 11;
        		 }
        		 
             	} catch (Exception e){
             		Base.logger.error("No se pudo Imprimir el Voucher", e);
             		JOptionPane.showMessageDialog(null, "Error al tratar de reimprimir el Voucher", "Error", JOptionPane.INFORMATION_MESSAGE);
             	}
                 
                 vista.hideBusyWindow();
                 
        		// Validar si el documento a armar es anulable !!!
 
                 return 11;
          
        }
        return 0;
    }

	public void generaComprobante (LineaVoucher []aux){
		
		comprobante = new ArrayList<LineaVoucher>();
		for(int j = 0; j < aux.length; j++){
			comprobante.add(aux[j]);
		}
		
	}
			
	public void generaComprobanteDuplicado (LineaVoucher []aux){		
		comprobanteCliente = new ArrayList<LineaVoucher>();
		for(int j = 0; j < aux.length; j++){
			comprobanteCliente.add(aux[j]);
		}
	}

	public String obtenerTipoTotalTarjeta (String nombreTarjeta) {
		
		Boolean obtuvoTipoTotal = false;
		String valorTipoTarjeta=null;
		
		
		Base.logger.info("obtenerTipoTotalTarjeta : " + nombreTarjeta.toUpperCase());
		
		//Tipo total es Credito
		String tarjetasCreditoCod[] = Base.getList( Base.getEmisoresCredito(), ",", 0);
		String tarjetasCreditoDes[] = Base.getList( Base.getEmisoresCredito(), ",", 1);
		
		for(int i=0; (i<tarjetasCreditoDes.length) && (!obtuvoTipoTotal);i++) {
	
			if(tarjetasCreditoCod[i].toUpperCase().equalsIgnoreCase(nombreTarjeta)
					|| nombreTarjeta.toUpperCase().equalsIgnoreCase("CREDITO")) {
				valorTipoTarjeta = "credito";
				obtuvoTipoTotal = true;
			}
		}
		//Tipo total es Casa comercial
		String tarjetasCCCod[] = Base.getList( Base.getEmisoresCasaComercial(), ",", 0);
		String tarjetasCCDes[] = Base.getList( Base.getEmisoresCasaComercial(), ",", 1);
		
		for(int i = 0; (i<tarjetasCCDes.length) && (!obtuvoTipoTotal);i++) {
	//		Base.logger.info("TARJETA !! : " + this.getDatos().
	//				getStringValue("TipoTarjeta").toUpperCase());
	//		Base.logger.info("comparando con : " + tarjetasCCDes[i].toUpperCase());
			if(tarjetasCCCod[i].toUpperCase().equalsIgnoreCase(nombreTarjeta)
					|| nombreTarjeta.toUpperCase().equalsIgnoreCase("MULTITIENDA")) {
				valorTipoTarjeta = "multitienda";
				obtuvoTipoTotal = true;
			}
		}
		
		//Tipo total es Debito
		String tarjetasDebitoCod[] = Base.getList( Base.getEmisoresDebito(), ",", 0);
		String tarjetasDebitoDes[] = Base.getList( Base.getEmisoresDebito(), ",", 1);
		
		for(int i=0; (i<tarjetasDebitoDes.length) && (!obtuvoTipoTotal);i++) {
			if(tarjetasDebitoCod[i].toUpperCase().equalsIgnoreCase(nombreTarjeta) 
					|| nombreTarjeta.toUpperCase().equalsIgnoreCase("DEBITO")) {
				valorTipoTarjeta = "debito";
				obtuvoTipoTotal = true;
			}
		}
		
		Base.logger.info("Tipo Total Encontrado : " + valorTipoTarjeta);
		
		return valorTipoTarjeta;
		
	}
		
}

