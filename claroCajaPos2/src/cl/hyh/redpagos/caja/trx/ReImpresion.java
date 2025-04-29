package cl.hyh.redpagos.caja.trx;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.xerces.impl.dv.util.Base64;

import cl.hyh.base.Motor;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.comm.PrintPDF;

public class ReImpresion implements ITrxBase {

	String trv = null;
	
	public void init(Datos htParm ) {
		trv = htParm.getStringValue("btnParam");
	}
	
	public int execute( ICajaView vista, int key, Datos htParam ) {
		Base.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++");
		Base.logger.info("La invocacion a reimprimir se realizo desde: "+  trv);
		Base.logger.info("++++++++++++++++++++++++++++++++++++++++++++++++");
	    
		ParamSet posCfg = Base.getParamSet("posCfg");
		String basePathVoucher = posCfg.getStringValue("VoucherDir");
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
		
		if (isPdf){
			if (Desktop.isDesktopSupported()) {
			    try {
			    	File myFile = new File( basePathVoucher + "\\" + Voucher.COPIA_CLIENTE + ".pdf" );
			    	File copyFile = new File( basePathVoucher + "\\" + UUID.randomUUID().toString() + ".pdf" );
			        FileUtils.copyFile(myFile, copyFile);
			        Desktop.getDesktop().open(copyFile);
			    } catch (IOException ex) {
			        // no application registered for PDFs
			    	ex.printStackTrace();
			    }
			}
		}else{
		
			this.printBoletaCliente("boletaCliente.dat");
		    this.printBoletaLocal("boletaLocal.dat");
		    
		}
		
		return 11;
		
//    	[REspinoza] Esto se consulta a Tbk
//    	this.printBoletaCliente("boletaTbk.dat");

	    //vista.hideBusyWindow();
//    	
    }
	
	private void printBoletaCliente(String nombreArchivo){
	    ParamSet posCfg = Base.getParamSet("posCfg");       
        BufferedReader br;
        ArrayList<LineaVoucher> voucher = new ArrayList<LineaVoucher>();
        try {
            br = new BufferedReader( new FileReader( posCfg.getStringValue("VoucherDir") + nombreArchivo ) );
        
            // TODO se agrega encabezado para indicar que es un duplicado de la boleta original !!
            LineaVoucher lv = new LineaVoucher();
            lv.setCaps(true);
            lv.setBold(true);
            lv.setCenter(true);
            lv.setLinea("Duplicado");
            voucher.add(lv);
            //////////////////////////////
            
            String linea = "";
            while( (linea = br.readLine()) != null){
                LineaVoucher line = new LineaVoucher();
                String []aux = linea.split("\\|");
                if(aux.length == 7) {
                	line.setLinea(aux[0]);
                    line.setBold(Boolean.parseBoolean(aux[1]));
                    line.setCaps(Boolean.parseBoolean(aux[2]));
                    line.setCenter(Boolean.parseBoolean(aux[3]));
                    line.setRight(Boolean.parseBoolean(aux[4]));
                    line.setSmall(Boolean.parseBoolean(aux[5]));
                    line.setUnderline(Boolean.parseBoolean(aux[6]));
                    voucher.add(line);    
                }else {
                	line.setLinea(aux[0]);
                    line.setBold(Boolean.FALSE);
                    line.setCaps(Boolean.FALSE);
                    line.setCenter(Boolean.FALSE);
                    line.setRight(Boolean.FALSE);
                    line.setSmall(Boolean.FALSE);
                    line.setUnderline(Boolean.FALSE);
                    voucher.add(line);
                }
                        
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            Tools.logStackTrace(Base.logger, e);
            return;
        } catch (IOException e) {
            Tools.logStackTrace(Base.logger, e);
            return;
        }
        Voucher.printVoucher(voucher, true, Voucher.COPIA_CLIENTE,null);
	}
	
	private void printBoletaLocal(String nombreArchivo){
	    ParamSet posCfg = Base.getParamSet("posCfg");       
        BufferedReader br;
        ArrayList<LineaVoucher> voucher = new ArrayList<LineaVoucher>();
        try {
            br = new BufferedReader( new FileReader( posCfg.getStringValue("VoucherDir") + nombreArchivo) );
            // TODO se agrega encabezado para indicar que es un duplicado de la boleta original !!
            LineaVoucher lv = new LineaVoucher();
            lv.setCaps(true);
            lv.setBold(true);
            lv.setCenter(true);
            lv.setLinea("Duplicado");
            voucher.add(lv);
            //////////////////////////////
            String linea = "";
            while( (linea = br.readLine()) != null){
                LineaVoucher line = new LineaVoucher();
                String []aux = linea.split("\\|");
                if(aux.length == 7) {
                	line.setLinea(aux[0]);
                    line.setBold(Boolean.parseBoolean(aux[1]));
                    line.setCaps(Boolean.parseBoolean(aux[2]));
                    line.setCenter(Boolean.parseBoolean(aux[3]));
                    line.setRight(Boolean.parseBoolean(aux[4]));
                    line.setSmall(Boolean.parseBoolean(aux[5]));
                    line.setUnderline(Boolean.parseBoolean(aux[6]));
                    voucher.add(line);    
                }else {
                	line.setLinea(aux[0]);
                    line.setBold(Boolean.FALSE);
                    line.setCaps(Boolean.FALSE);
                    line.setCenter(Boolean.FALSE);
                    line.setRight(Boolean.FALSE);
                    line.setSmall(Boolean.FALSE);
                    line.setUnderline(Boolean.FALSE);
                    voucher.add(line);
                }
            
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            Tools.logStackTrace(Base.logger, e);
            return;
        } catch (IOException e) {
            Tools.logStackTrace(Base.logger, e);
            return;
        }
        if(voucher.size() != 0){
            Voucher.printVoucher(voucher, true, Voucher.COPIA_LOCAL, null);
        }
	}
}
