package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.apache.xerces.impl.dv.util.Base64;


import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.GuardarVoucherTbkRequestDTO;
import ws.claro.cl.JustificacionCierreDTO;
import ws.claro.cl.RegistrarJustificacionRequestDTO;
import ws.claro.cl.proxy.AppControlProxy;

/**
 * Clase que se encarga de imprimir el voucher en un PDF en local para ser impreso por el cajero
 * en una impresora normal.
 * 
 * @author msepulveda
 *
 */
public class VoucherPDF extends Voucher {
	
	
	public static final String TIPO_COPIA_CLIENTE 						= "Cliente";
	public static final String TIPO_COPIA_LOCAL	 						= "Local";

	
	
	/**
	 * Metodo que se encarga de imprimir el voucher en un pdf, contiene la misma logica de 
	 * {@link Voucher}
	 * 
	 * @param lineas lineas a imprimir
	 * @param isLogo indica si es logo
	 */
	public static void printVoucher(ArrayList <LineaVoucher>lineas, boolean isLogo, String fileName, DatosFileNet datos){
		Base.logger.info("Entro en Print Voucher con filename : " + fileName);
		boolean ok = true;
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());
        if (datos != null) {
        	datos.setFilesName(fileName);
        }
		GuardarVoucher guardarArchivo = new GuardarVoucher();
		
		
        if(lineas.size() == 0){
            return;
        }
        
        Base.logger.info("Tamanho de Lineas: "+lineas.size());
        
        for(int j = 0; j < lineas.size(); j++){
        	Base.logger.info("Imprimiendo Voucher: "+lineas.get(j).getLinea());
        }
        
        for(int i = 0; i < lineas.size(); i++){
        	if(lineas.get(i) != null){
        		Base.logger.info("Print Voucher: "+lineas.get(i).getLinea());
        		lineas.get(i).setLinea(lineas.get(i).getLinea().replace(" ", "&#160;"));
        		lineas.get(i).setLinea(lineas.get(i).getLinea().replace("<p&#160;", "<p "));
        		lineas.get(i).setLinea(lineas.get(i).getLinea().replace("<span&#160;", "<span "));

        		int formato = 0;
        		if
        		(i == 0 && isLogo) {
        			formato = formato + Base.pos.printer._MODE_LOGO;
        		}
        		if(ok == true){
        			try {
        				if(lineas.get(i).isBold()){
        					formato = formato + Base.pos.printer._MODE_BOLD;
        				}
        				if(lineas.get(i).isCenter()){
        					formato = formato + Base.pos.printer._MODE_CENTER;
        				}
        				if(lineas.get(i).isUnderline()){
        					formato = formato + Base.pos.printer._MODE_UNDERLINE;
        				}
        				if(lineas.get(i).isSmall()){
        					formato = formato + Base.pos.printer._MODE_SMALL;
        				}
        				if(lineas.get(i).isRight()){
        					try{
        						String []aux = lineas.get(i).getLinea().split(",");
        						Base.logger.error("Longitud de aux: "+aux.length);
        						if(!lineas.get(i).isCaps()){
            						lineas.get(i).setLinea(Base.pos.pdfPrinter.makeRightPrintString(Base.pos.printer._SIZE_NORMAL,aux[0],aux[1]));
            					}
            					else{
            						lineas.get(i).setLinea(Base.pos.pdfPrinter.makeRightPrintString(Base.pos.printer._SIZE_2X,aux[0],aux[1]));
            					}
        					}catch(Exception e){
        						Base.logger.error("Error en parseo de alineacion derecha.. "+e.getMessage());
        					}
        					
        				}
        				if(lineas.get(i).isCaps()){
        					formato = formato + Base.pos.printer._MODE_BIG_2;
        				}
        				
        				
        				if(lineas.get(i).getLinea().trim().contains("RUT: 0-0")){
        					lineas.get(i).setLinea("");
        				}
        				//Base.pos.printer.printLine(formato, lineas.get(i).getLinea());

        				
        				Base.pos.pdfPrinter.printLine(formato, lineas.get(i).getLinea());
        			} catch (Exception e) {
        				Tools.logStackTrace(Base.logger, e);
        			}
        		}
        		
        		
        	}else{
        		Base.logger.info("Linea " + i + "es nulo");
        	}
        }
        Base.logger.info("IMPRIMIENDO EN PDF");
        Base.pos.pdfPrinter.printBoleta(fileName,isLogo);
        
        // Se arma el objeto con los datos del PDF para guardar en BD
        if(datos!=null) {
        	Base.logger.info("Comienza a guardar PDF en DB");
        	armarGuardarVoucher(fileName, datos);
        	guardarVoucher(datos);
        }
       
        
    }
	
	
	public static void armarGuardarVoucher(String fileName, DatosFileNet datos){
		Base.logger.info("Arma datos FAltantes para Guardar PDF");
		  //////////// codigo para guardar comprobante PDF en base de datos 
				ParamSet posCfg = Base.getParamSet("posCfg");
		        String basePathVoucher = posCfg.getStringValue("VoucherDir");
		        File file = new File( basePathVoucher + "\\" + fileName + ".pdf" );
		        Base64 base64 = new Base64();
				byte[] fileArray = new byte[(int) file.length()];
				
				 System.out.println("archivo: " + file.exists());
				InputStream inputStream;

				String encodedFile = "";
				try {
					inputStream = new FileInputStream(file);
					inputStream.read(fileArray);
					encodedFile = base64.encode(fileArray);
				} catch (Exception e) {
					Base.logger.error("Error al convertir base64 el PDF.. "+e.getMessage());
				}
				
				datos.setStr_comprobante(encodedFile);
				if (fileName.equals(Voucher.COPIA_CLIENTE) || fileName.equals(Voucher.COPIA_TBK_CLIENTE)) {
					datos.setTipo_copia(TIPO_COPIA_CLIENTE.toUpperCase());
					//String name= ;//+"_OP"+datos.getNumOperacion();
					datos.setTipo_comprobante(fileName.toUpperCase().replace("COPIA","COMPROBANTE"));
					
				}else {
					Date d = new Date();
					datos.setTipo_copia(TIPO_COPIA_LOCAL.toUpperCase());
					datos.setTipo_comprobante(fileName.toUpperCase().replace("COPIA",""));//+"_"+ new Date());
				}
				
				
				

				
	}
	
	public static void guardarVoucher(DatosFileNet datos){
		
		
		Base.logger.info("Guarda PDF en DB");
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
		GuardarVoucherTbkRequestDTO gvtbk = new GuardarVoucherTbkRequestDTO();

		gvtbk.setCodigoSesion(datos.getCodigo_sesion());
		gvtbk.setCodigoUsuarioEnvia(datos.getCodusuario_envia());
		gvtbk.setComprobante(datos.getStr_comprobante());
		gvtbk.setEmailPara(datos.getEmail_para());
		gvtbk.setNumerOperacion(datos.getNumOperacion());
		gvtbk.setPropietario(datos.getPropietario());
		gvtbk.setTipoComprobante(datos.getTipo_comprobante());
		gvtbk.setTipoCopia(datos.getTipo_copia());
		gvtbk.setTipOperacion(datos.getTipo_operacion());
		
		try {		
			
			pr.guardarVoucherTbk(gvtbk);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			Base.logger.error("Error al guardar en BD datos y comprobante PDF.. "+e.getMessage());
		}
		Base.logger.info("Termina guardar PDF en DB");		
		
	}

}
