package cl.hyh.redpagos.caja.trx;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

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
public class ReImpresionTbk implements ITrxBase {

	String trv = null;
	LineaVoucher[] voucherComercioDuplicado = null;
	LineaVoucher[] voucherComercioDuplicadoCliente = null;
	ArrayList<LineaVoucher> comprobante;
	ArrayList<LineaVoucher> comprobanteCliente;
	String codTbkReturn;
	String msgTbkReturn;
	
	public void init(Datos htParm ) {
		trv = htParm.getStringValue("btnParam");
	}
	
	public int execute( ICajaView vista, int key, Datos htParam ) {
		Base.logger.info("Reimpresion Voucher Transbank "+  trv);
		
		ParamSet posCfg = Base.getParamSet("posCfg");
		String basePathVoucher = posCfg.getStringValue("VoucherDir");
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
	    
		if(!isPdf) {
		     	vista.hideAllEntries();
		     	vista.showBusyWindow("Consultando Voucher Transbank", "Espere por favor...");
		     	
		     	try {
		
		        String[] dataTbk = null;
		        
		        Base.logger.info("Reimpresion Validar archivo temporal Transbank "+  trv);
		//        dataTbk = obtenerDatosTrxTbk("boletaTbkTemp.dat");     	
		//        if(dataTbk != null) {
		        	Tools.revisarVoucherTBK();
		//        }
		        
		        Base.logger.info("Reimpresion Voucher Transbank "+  trv);
		        dataTbk = obtenerDatosTrxTbk("boletaTbk.dat");
		        Base.logger.info("P I N P A D - inicializa");
		     	Base.tbk.inicializa(posDat.getStringValue("TransbankConfig"));
				TbkMetodos metodos = new TbkMetodos();
				String idTbkOriginal = dataTbk[1];
				String origenTbk = dataTbk[0].equalsIgnoreCase("anulacion")==true?"DEV":dataTbk[2];//TBKDEV
				
				String termId = dataTbk[0].equalsIgnoreCase("anulacion")==false?null:dataTbk[3];
				String numSec = dataTbk[0].equalsIgnoreCase("anulacion")==false?null:dataTbk[4];
				String termIdDev = dataTbk[0].equalsIgnoreCase("anulacion")==false?null:dataTbk[5];
		
		     	String requerimiento = "IDTRXORIGINAL=" + idTbkOriginal +"&";
		     	
		       if (origenTbk.equalsIgnoreCase("DEV")) { //Solo si es devolucion
			    	requerimiento = requerimiento +
			    	"TERMINALID=" + termId + "&" + 
			        "NUM_SEC_TBK_DEV=" + numSec + "&" + 
			        "TERMINALID_DEV=" + termIdDev + "&";
		       }
		
				requerimiento = requerimiento + "TIPTRX=TBKREM&" + 
					"TIPTRXORIG=" + origenTbk +"&" + 
					"FECHA=" + idTbkOriginal.substring(8, 16) +"&" +
					"NROOPE=" + idTbkOriginal.substring(16, 22) +"&" + 
					"LOCAL="+ idTbkOriginal.substring(0, 4) +"&" + 
					"CAJA=" + idTbkOriginal.substring(4, 8)+"";
		
		     	System.out.println("requerimiento : " + requerimiento);
		 		
		     	Base.logger.info("P I N P A D - reimpresion");
		 		String respuestaVoucher = metodos.reimpresion(Base.tbk, requerimiento);
		 		Base.logger.info(respuestaVoucher); 
		 		 
		 		if (respuestaVoucher != null) {
		 		
			 		URLString urlVC = new URLString(respuestaVoucher);
			 		String[]voucher = null;
			 		String[]voucherDuplicado = null;
			 		LineaVoucher vL = null;
			 		 
			     	//Obtener código retornado por Transbank
			 		codTbkReturn=urlVC.getValor("AUTRET")==null?codTbkReturn=urlVC.getValor("TXCRET"):urlVC.getValor("AUTRET");
			 		msgTbkReturn= urlVC.getValor("TXCGLO")==null?"":urlVC.getValor("TXCGLO");
		//	 		msgTbkReturn= Format.getMesageTbk(urlVC);
		            msgTbkReturn="RetTbk [" + codTbkReturn + "]. " + Format.formatMsjTbk(msgTbkReturn);
			 		
		 			 if(urlVC.getValor("TXCRET").equals("00") 
		 					 || Integer.parseInt(urlVC.getValor("TXCRET")) <= 9){
		 				 
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
		//                  voucherDuplicado = urlVC.getValor("VOUCHER").replace("ORIGINAL COMERCIO - COPIA CLIENTE", "         COPIA CLIENTE").split("\\n");
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
		                  Voucher.printVoucher(comprobante, false, Voucher.COPIA_TBK_LOCAL, null);
		                  
		 			 	}else{
		         			vista.hideBusyWindow();
		
		        			if(msgTbkReturn.trim().equalsIgnoreCase("")){
		        				msgTbkReturn = " Error al realizar reimpresión Transbank, reintentar...";
		                    }
		
		  		            Base.logger.error( msgTbkReturn);
		  		            JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
		  		            		"Error", JOptionPane.INFORMATION_MESSAGE);
		  		            return 11;
		         		 }
		 		 
		 		} else {
			       	 vista.hideBusyWindow();
			         Base.logger.error("No se pudo realizar la validacion del pinpad");
			         JOptionPane.showMessageDialog(null, "Error al tratar de reimprimir el Voucher", "Error", JOptionPane.INFORMATION_MESSAGE);
			         return 11;
		 		}
		 		 
		     	} catch (Exception e){
		     		Base.logger.error("No se pudo Imprimir el Voucher", e);
		     		JOptionPane.showMessageDialog(null, "Error al tratar de reimprimir el Voucher", "Error", JOptionPane.INFORMATION_MESSAGE);
		     	}
		 		
		 		 vista.hideBusyWindow();	
 		 
		}
		else{
			if (Desktop.isDesktopSupported()) {
			    try {
			    	File myFile = new File( basePathVoucher + "\\" + Voucher.COPIA_TBK_CLIENTE  + ".pdf" );
			    	File copyFile = new File( basePathVoucher + "\\" + UUID.randomUUID().toString() + ".pdf" );
			        FileUtils.copyFile(myFile, copyFile);
			        Desktop.getDesktop().open(copyFile);
			    } catch (IOException ex) {
			        // no application registered for PDFs
			    	ex.printStackTrace();
			    }
			}
		}
		
		
		
    	return 11;
    }
	
	public String[] obtenerDatosTrxTbk(String nombreArchivo) {
		    ParamSet posCfg = Base.getParamSet("posCfg");       
	        BufferedReader br;
	        String[] dataTbk = new String[5];
	        
	        try {
	            br = new BufferedReader( new FileReader( posCfg.getStringValue("VoucherDir") + nombreArchivo) );
	            String linea = "";
	            while( (linea = br.readLine()) != null){
	                String []aux = linea.split("\\|");
	                
	                dataTbk = aux;	                          
	            }
	            br.close();
	        }
	        catch (FileNotFoundException e) {
	            Tools.logStackTrace(Base.logger, e);
	            return null;
	        } catch (IOException e) {
	            Tools.logStackTrace(Base.logger, e);
	            return null;
	        }
	        if(dataTbk != null && dataTbk.length != 0){
	            return dataTbk;
	        } else {
	        	return null;
	        }
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
	
}
