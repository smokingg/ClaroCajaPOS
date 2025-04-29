package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import cl.hyh.redpagos.caja.base.parser.DefVoucher;
import cl.hyh.redpagos.caja.base.parser.DefVoucherLine;
import cl.hyh.redpagos.caja.base.parser.DefVoucherPrint;
import cl.hyh.redpagos.caja.pos.PosDeviceException;
import jpos.JposException;
import ws.claro.cl.AnuncioMarketingResponseDTO;
import ws.claro.cl.proxy.AppControlProxy;

/**
 * Esta clase permite generar las lineas de un voucher dado su nombre
 * 
 * MSepulveda - Se agrega logica para impresión en PDF
 * 
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Voucher {
    
	public static final String COPIA_CLIENTE 						= "CopiaCliente";
	public static final String COPIA_LOCAL	 						= "CopiaLocal";
	public static final String COPIA_TBK_LOCAL	 					= "CopiaTbkLocal";
	public static final String COPIA_TBK_CLIENTE	 				= "CopiaTbkCliente";
	public static final String COPIA_PREMIO_CLIENTE	 				= "CopiaPremioCliente";
	public static final String COPIA_PREMIO_LOCAL	 				= "CopiaPremioLocal";
	public static final String COPIA_INICIALIZACION	 				= "CopiaInicializacion";
	public static final String COPIA_CIERRE_CAJA	 				= "CopiaCierreCaja";
	public static final String COPIA_CIERRE_PINPAD	 				= "CopiaCierreCajaPinpad";
	public static final String COPIA_ARQUEO_CAJA	 				= "CopiaArqueoCaja";
	public static final String COPIA_CIERRE_CAJA_ANULACION	 		= "CopiaCierreCajaAnulaciones";
	public static final String COPIA_CIERRE_CAJA_DEPOSITOS	 		= "CopiaCierreCajaDepositos";
	public static final String COPIA_CONFIGURACION_CAJA 			= "CopiaConfiguracionCaja";
	public static final String COPIA_REMESA 						= "CopiaRemesa";
	public static final String COPIA_CIERRE_CAJA_CONTINGENCIA_1	 	= "CopiaCierreCajaContengencia1";
	public static final String COPIA_CIERRE_CAJA_CONTINGENCIA_2	 	= "CopiaCierreCajaContengencia2";
	
	/** PARA COMPROVANTES EN PDF **/
	public static final String TIPO_OPERACION_PAGO	 					= "Pago";
	public static final String TIPO_OPERACION_ANULACION	 				= "Anulacion";
	public static final String TIPO_OPERACION_CIERRE 					= "Cierre";
	public static final String TIPO_OPERACION_REMESA 					= "Remesa";
	
	
    /**
     * Retorna arreglo de LineaVoucher con las lineas del voucher indicado
     * @param datos Objeto Datos que tiene los datos a procesar
     * @param def Definicion de Voucher que contiene la estructura del voucher a procesar
     * @return
     */
    public static ArrayList <LineaVoucher> armarVoucher(Datos datos, DefVoucher def){
        ArrayList<LineaVoucher> voucher = new ArrayList<LineaVoucher>();
        ArrayList<LineaVoucher> aux;
        
        for(int i = 0; i < def.getDefLines().size(); i++){
            DefVoucherLine lin = def.getDefLines().get(i);
            if( lin.getCondField().length() == 0){
                if(!lin.getInclude().equals("")){
                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                    aux = armarVoucher(datos , inc);
                    for(int j = 0; j < aux.size(); j++){
                        voucher.add(aux.get(j));
                    }               
                }
                else{
                    voucher.add(getLine(lin,datos));
                }
             }
            else{
                String []auxData = lin.getCondField().split("\\.");
                if(auxData[0].equals("datos")){
                    if(lin.isCond()){
                        if(lin.getType().equals("char")){
                            if( (datos.getStringValue(auxData[1].trim()).equals(lin.getValue().trim())) ){
                                if(!lin.getInclude().equals("")){
                                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                                    aux = armarVoucher(datos , inc);
                                    for(int j = 0; j < aux.size(); j++){
                                        voucher.add(aux.get(j));
                                    }               
                                }
                                else{
                                    voucher.add(getLine(lin,datos));
                                }
                            }
                        }
                        else if(lin.getType().equals("num")){
                            if( datos.getIntValue(auxData[1]) == Integer.parseInt(lin.getValue()) ){
                                if(!lin.getInclude().equals("")){
                                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                                    aux = armarVoucher(datos , inc);
                                    for(int j = 0; j < aux.size(); j++){
                                        voucher.add(aux.get(j));
                                    }               
                                }
                                else{
                                    voucher.add(getLine(lin,datos));
                                }
                            }
                        }
                        else if(lin.getType().equals("long")){
                            if( datos.getLongValue(auxData[1]) == Long.parseLong(lin.getValue()) ){
                                if(!lin.getInclude().equals("")){
                                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                                    aux = armarVoucher(datos , inc);
                                    for(int j = 0; j < aux.size(); j++){
                                        voucher.add(aux.get(j));
                                    }               
                                }
                                else{
                                    voucher.add(getLine(lin,datos));
                                }
                            }
                        }
                    }
                    else{
                        if(lin.getType().equals("char")){
                            if( !datos.getStringValue(auxData[1].trim()).equals(lin.getValue().trim()) ){
                                if(!lin.getInclude().equals("")){
                                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                                    aux = armarVoucher(datos , inc);
                                    for(int j = 0; j < aux.size(); j++){
                                        voucher.add(aux.get(j));
                                    }               
                                }
                                else{
                                    voucher.add(getLine(lin,datos));
                                }
                            }
                        }
                        else if(lin.getType().equals("num")){
                            if( datos.getIntValue(auxData[1]) != Integer.parseInt(lin.getValue()) ){
                                if(!lin.getInclude().equals("")){
                                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                                    aux = armarVoucher(datos , inc);
                                    for(int j = 0; j < aux.size(); j++){
                                        voucher.add(aux.get(j));
                                    }               
                                }
                                else{
                                    voucher.add(getLine(lin,datos));
                                }
                            }
                        }
                        else if(lin.getType().equals("long")){
                            if( datos.getLongValue(auxData[1]) != Long.parseLong(lin.getValue()) ){
                                if(!lin.getInclude().equals("")){
                                    DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                                    aux = armarVoucher(datos , inc);
                                    for(int j = 0; j < aux.size(); j++){
                                        voucher.add(aux.get(j));
                                    }               
                                }
                                else{
                                    voucher.add(getLine(lin,datos));
                                }
                            }
                        }
                    }
                    
                }
                else{
                    ParamSet paramData = Base.getParamSet(auxData[0]);
                    if(!lin.isCond()){
                        voucher.add(getLine(lin,datos));
                    }
                    if( (paramData.getStringValue(auxData[1]).equals(lin.getValue())) == lin.isCond() ){
                        if(!lin.getInclude().equals("")){
                            DefVoucher inc = Base.getDefVoucher(lin.getInclude());
                            aux = armarVoucher(datos , inc);
                            for(int j = 0; j < aux.size(); j++){
                                voucher.add(aux.get(j));
                            }               
                        }
                        else{
                            voucher.add(getLine(lin,datos));
                        }
                    }
                }
            }
        }
        return voucher;
    }
    private static LineaVoucher getLine(DefVoucherLine lin, Datos datos){
        LineaVoucher linea;
        ParamSet param;
        linea = new LineaVoucher();
        linea.setBold(lin.isBold());
        linea.setCenter(lin.isCenter());
        linea.setUnderline(lin.isUnderline());
        linea.setRight(lin.isRight()); 
        linea.setCaps(lin.isCaps());
        linea.setSmall(lin.isSmall());
        String s = "";
        
        for(int j = 0; j < lin.getDefPrints().size(); j++){
            DefVoucherPrint print = lin.getDefPrints().get(j);
            if(!print.getField().equals("")){
                String msg = "";                                        
                String []aux = print.getField().split("\\.");
                if(aux[0].equals("datos")){
                    if(aux[1].equals("Total") || aux[1].equals("Vuelto") || aux[1].equals("cuota1") || aux[1].equals("cuota2")
                            || aux[1].equals("cuota3") || aux[1].contains("Costo") || aux[1].contains("MontoCuota")){
                        msg = Format.formatMonto(datos.getLongValue(aux[1]));
                    }
                    else if(aux[1].equals("MontoDocumento")|| aux[1].equals("Monto")
                            || aux[1].equals("DocSaldo")){
                        msg = Format.formatMonto(datos.getLongValue(aux[1]));
                    }
                    else if(aux[1].equals("InteresMensual") || aux[1].equals("InteresAnual") ){
                        msg = Format.formatPercent(datos.getStringValue(aux[1]));
                    }
                    // Se agrega para formatear saldo adeudado por documento Claro
                    else if(aux[1].equals("SaldoAdeudadoClaro")){
                    	msg = Format.formatMonto(datos.getLongValue(aux[1]));
                    }
                    // Se agrega para formatear Monto ajustado por redondeo
                    else if(aux[1].equals("MontoAjustado")){
                    	msg = Format.formatMontoAjustado(datos.getLongValue(aux[1]));
                    }
                    else{
                        msg = datos.getStringValue(aux[1]);
                    }
                }
                else {
                    param = Base.getParamSet(aux[0]);
                    msg = param.getStringValue(aux[1]);
                }
                //Se ponen los formatos, largos, rellenos.
                if(!print.getFormat().equals("")){
                    //msg = (String)(Class.forName("cl.telefonica.redpagos.caja.format.FormatClassHora").newInstance()).format(msg);
                }
                if(print.getLength() != 0){
                    
                }
                if(!print.getLFill().equals("")){
                    msg = getFill(Integer.parseInt(print.getLFill())) + msg;
                }
                if(!print.getRFill().equals("")){
                    msg = msg + getFill(Integer.parseInt(print.getRFill()));
                }
                s = s + msg;
            }
            else if(!print.getValue().equals("")){
                if(!print.getLFill().equals("")){
                    s = s + getFill(Integer.parseInt(print.getLFill())) + print.getValue();
                }
                else{
                    s = s + print.getValue();
                }
            }
        }
        linea.setLinea(s);
        return linea;
    }

    /**
     * Imprime en el INFO del logger las lineas
     * 
     * MSepulveda -  Se agrega logica para obtener desde parametro si se debe imprimir en PDF o si se imprime en la impresora termica
     * 
     * @param lineas Arreglo de lineas a ser imprimidas
     */
    public static void printVoucher(ArrayList <LineaVoucher>lineas, boolean isLogo, String fileName, DatosFileNet datos){
    	
    	// msepulveda: Se agrega validación por parametro para la seleccion del periferico de impresion
    	ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
    	if(isPdf) { 
    		VoucherPDF.printVoucher(lineas, isLogo, fileName, datos);
    		return;
    	}
    	
    	
        boolean ok = false;
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());

        if(lineas.size() == 0){
            return;
        }
        
        try {
            Base.pos.printer.openDevice();
            ok = true;            
        } catch (Exception e) {
            ok = false;
            Tools.logStackTrace(Base.logger, e);
        }
        
        /*try {
            if(isLogo){
                Base.pos.printer.printLine(Base.pos.printer._MODE_LOGO, "");
                //Base.pos.printer.printLine(0, "");
            }            
        } catch (PosDeviceException e1) {
            Tools.logStackTrace(Base.logger, e1);
        }*/
        
        Base.logger.info("Tamanho de Lineas: "+lineas.size());
        
        for(int j = 0; j < lineas.size(); j++){
        	Base.logger.info("Imprimiendo Voucher: "+lineas.get(j).getLinea());
        }
        
        for(int i = 0; i < lineas.size(); i++){
        	if(lineas.get(i) != null){
        		Base.logger.info("Print Voucher: "+lineas.get(i).getLinea());
        		int formato = 0;
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
            						lineas.get(i).setLinea(Base.pos.printer.makePrintString(Base.pos.printer._SIZE_NORMAL,aux[0],aux[1]));
            					}
            					else{
            						lineas.get(i).setLinea(Base.pos.printer.makePrintString(Base.pos.printer._SIZE_2X,aux[0],aux[1]));
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
        				Base.pos.printer.printLine(formato, lineas.get(i).getLinea());
        			} catch (PosDeviceException e) {
        				Tools.logStackTrace(Base.logger, e);
        			}
        		}
        	}else{
        		Base.logger.info("Linea " + i + "es nulo");
        	}
        }
        try {
            Base.pos.printer.printLine(Base.pos.printer._MODE_CUT, "");
            if( Base.pos.printer.isOn() ){
                Base.pos.printer.close();
                Base.pos.printer.setOn(false);         
            }
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
        }
        
    }
    
    public static void printVoucherBarra(ArrayList <LineaVoucher>lineas, boolean isLogo,String codBarra){
        boolean ok = false;
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());

        if(lineas.size() == 0){
            return;
        }
        
        try {
            Base.pos.printer.openDevice();
            ok = true;            
        } catch (JposException e) {
            ok = false;
            Tools.logStackTrace(Base.logger, e);
        }
        for(int i = 0; i < lineas.size(); i++){
            Base.logger.info(lineas.get(i).getLinea());
            int formato = 0;
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
                        String []aux = lineas.get(i).getLinea().split(",");
                        if(!lineas.get(i).isCaps()){
                            lineas.get(i).setLinea(Base.pos.printer.makePrintString(Base.pos.printer._SIZE_NORMAL,aux[0],aux[1]));
                        }
                        else{
                            lineas.get(i).setLinea(Base.pos.printer.makePrintString(Base.pos.printer._SIZE_2X,aux[0],aux[1]));
                        }
                    }
                    if(lineas.get(i).isCaps()){
                        formato = formato + Base.pos.printer._MODE_BIG_2;
                    }
                    Base.pos.printer.printLine(formato, lineas.get(i).getLinea());
                } catch (PosDeviceException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
            }
        }
        try {
            Base.pos.printer.printLine(Base.pos.printer._MODE_CUT, "");
        } catch (PosDeviceException e) {
            Tools.logStackTrace(Base.logger, e);
        }
        if( Base.pos.printer.isOn() ){
            try {
                Base.pos.printer.close();
                Base.pos.printer.setOn(false);
            } 
            catch (PosDeviceException e1) {
                Tools.logStackTrace(Base.logger, e1);
            }
        }
    }
    
    public static void printVoucherDos(ArrayList <LineaVoucher>lineas, boolean isLogo){
        boolean ok = false;
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());

        if(lineas.size() == 0){
            return;
        }
        
        try {
            Base.pos.printer.openDevice();
            ok = true;            
        } catch (JposException e) {
            ok = false;
            Tools.logStackTrace(Base.logger, e);
        } 
        /*try {
            if(isLogo){
                Base.pos.printer.printLine(Base.pos.printer._MODE_LOGO, "");
                //Base.pos.printer.printLine(0, "");
            }            
        } catch (PosDeviceException e1) {
            Tools.logStackTrace(Base.logger, e1);
        }*/
        for(int i = 0; i < lineas.size(); i++){
            Base.logger.info(lineas.get(i).getLinea());
            int formato = 0;
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
                    if(lineas.get(i).isCaps()){
                        formato = formato + Base.pos.printer._MODE_BIG_2;
                    }
                    Base.pos.printer.printLine(formato, lineas.get(i).getLinea());
                } catch (PosDeviceException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
            }
        }
        try {
            Base.pos.printer.printLine(Base.pos.printer._MODE_CUT, "");
        } catch (PosDeviceException e) {
            Tools.logStackTrace(Base.logger, e);
        }
    }
    
    public static void escribeBoleta(ArrayList <LineaVoucher> lineas,String nombre){
        ParamSet posCfg = Base.getParamSet("posCfg");
        //File local = new File();
        //BufferedWriter bw = null;
        FileOutputStream os = null;        
        
        try {
            os = new FileOutputStream( posCfg.getStringValue("VoucherDir") + nombre);
            FileDescriptor fd = os.getFD();            
            for( int i = 0; i < lineas.size(); i++ ) {
                LineaVoucher linea = lineas.get(i);
                os.write( (linea.getLinea() + "|" + linea.isBold() + "|" + linea.isCaps() + "|" + linea.isCenter()
                         + "|" + linea.isRight() + "|" + linea.isSmall() + "|" + linea.isUnderline() + "\n").getBytes());
                
                Base.logger.info("Linea Voucher para reimprimir: ["+linea.getLinea()+"]");
            }
            
            os.flush();
            fd.sync();
            os.close(); os = null;
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
            JOptionPane.showMessageDialog(null, "Error al grabar en disco,se cerrará la aplicación\n[" + e.getMessage()+"]\nClase:[Voucher.java]", "Error", JOptionPane.ERROR_MESSAGE);
            Tools.logStackTrace(Base.logger, e);
            System.exit(1);
        }
        finally {
            if( os != null )
                try {
                    os.close();
                } catch (IOException e) {
                }
        }
    }
    
    public static void borraBoleta(String nombre){
        ParamSet posCfg = Base.getParamSet("posCfg");
        File local = new File(posCfg.getStringValue("VoucherDir") + nombre);
        try {
            local.delete();
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
            JOptionPane.showMessageDialog(null, "Error al grabar en disco,se cerrará la aplicación\n[" + e.getMessage()+"]\nClase:[Voucher.java]", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void escribeNewBoleta(String nombre,String valor){
        ParamSet posCfg = Base.getParamSet("posCfg");
        ParamSet pList = Base.getParamSet( "posDat" ); 
        File local = new File(posCfg.getStringValue("VoucherDir") + nombre);
        BufferedWriter bw = null;
        BufferedReader br;
        ArrayList<LineaVoucher> voucher = new ArrayList<LineaVoucher>();
        
        try {
            br = new BufferedReader( new FileReader( posCfg.getStringValue("VoucherDir") + nombre ) );
        
            String linea = "";
            while( (linea = br.readLine()) != null){
                LineaVoucher line = new LineaVoucher();
                String []aux = linea.split("\\|");
                line.setLinea(aux[0]);
                line.setBold(Boolean.parseBoolean(aux[1]));
                line.setCaps(Boolean.parseBoolean(aux[2]));
                line.setCenter(Boolean.parseBoolean(aux[3]));
                line.setRight(Boolean.parseBoolean(aux[4]));
                line.setSmall(Boolean.parseBoolean(aux[5]));
                line.setUnderline(Boolean.parseBoolean(aux[6]));
                voucher.add(line);            
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
        LineaVoucher oper = new LineaVoucher();
        oper.setLinea("Nro. Operación:" + valor + "   Operador:" + pList.getStringValue( "Cajero" ));
        for(int i = 0 ; i < voucher.size() ; i++){
            if(voucher.get(i).getLinea().contains("N°Operación")){
                voucher.set(i, oper);
                break;
            }
        }
        try {
            bw =  new BufferedWriter(new FileWriter(local));
            for( int i = 0; i < voucher.size(); i++ ) {
                LineaVoucher linea = voucher.get(i);
                bw.write( linea.getLinea() + "|" + linea.isBold() + "|" + linea.isCaps() + "|" + linea.isCenter()
                         + "|" + linea.isRight() + "|" + linea.isSmall() + "|" + linea.isUnderline() + "\n");
            }
            bw.close(); bw = null;
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
            return;
        }
        finally {
            if( bw != null )
                try {
                    bw.close();
                } catch (IOException e) {
                }
        }
    }
    
    public static String[] printVoucherPantalla(ArrayList <LineaVoucher>lineas, boolean isLogo){
        String []boleta = new String[lineas.size()];

        for(int i = 0; i < lineas.size(); i++){
            try{
                if(lineas.get(i).isRight()){
                    String []aux = lineas.get(i).getLinea().split(",");
                    if(aux.length < 2){
                        boleta[i] = lineas.get(i).getLinea();
                    }
                    else{
                        boleta[i] = Voucher.makePrintString(Base.pos.printer._SIZE_NORMAL,aux[0],aux[1]);
                    }
                    continue;
                }
                else{
                    boleta[i] = lineas.get(i).getLinea();
                }
            }
            catch(Exception e){
                Tools.logStackTrace(Base.logger, e);
            }
        }
        return boleta;
    }
    
    private static String getFill(int size){
        String msg = "";
        for(int i = 0; i < size; i++){
            msg = msg + " ";
        }
        return msg;
    }
    
    private static String makePrintString(int tipo,String text1,String text2){
        int spaces = 0;
        int lineChars = 0;
        if(tipo == 0){
            lineChars = 35;
        }
        else{
            lineChars = 88;
        }
        String tab = "";
        try{
            spaces = lineChars - (text1.length() + text2.length());
            for (int j = 0 ; j < spaces ; j++){
                tab += " ";
            }
        }
        catch(Exception ex){
        }
        return text1 + tab + text2;
    }
    
    /**
     * 
     * @param ruts
     * @return
     */
    public static final List<LineaVoucher> armaAnuncios(List<String> ruts) {
    	List<LineaVoucher> lineas = new ArrayList<LineaVoucher>();
    	AnuncioMarketingResponseDTO response;
		try {
			response = AppControlProxy.getProxyInstance().obtenerAnunciosMarketing(ruts.toArray(new String[0]));
			if (response != null && response.getAnuncios() != null) {
				LineaVoucher linea = new LineaVoucher();
				linea.linea = response.getAnuncios();
				lineas.add(linea);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    	return lineas;
    }
}
