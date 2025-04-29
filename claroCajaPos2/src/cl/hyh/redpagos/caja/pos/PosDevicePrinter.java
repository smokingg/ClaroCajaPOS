package cl.hyh.redpagos.caja.pos;


import java.util.ArrayList;
import java.util.StringTokenizer;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterControl113;
import jpos.POSPrinterConst;
import cl.hyh.redpagos.caja.base.Base;

/** 
 * Representacion interna de la impresora
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class PosDevicePrinter implements PosDevicePrinterInterface{

    boolean isOn = false;
    private ArrayList <String> boleta; 
    private  POSPrinterControl113 ptr = null; 
    private int[] RecLineChars = new int[_MAX_LINE_WIDTHS];
    private int[] SlpLineChars= new int[_MAX_LINE_WIDTHS];
    
    public void initPrinter(){
        ptr = (POSPrinterControl113) new POSPrinter();
        boleta = new ArrayList<String>();
    }
    
    private void init() throws PosDeviceException{
        try{
            long lRecLineCharsCount;
            lRecLineCharsCount = getRecLineChars(RecLineChars);
            String charLists = ptr.getRecLineCharsList();
            System.out.println("Lista Cantidad de Caracteres por fila: "+charLists);
            String aCharList[] = new String[_MAX_LINE_WIDTHS];
            int iCounter=0;
            StringTokenizer st = new StringTokenizer(charLists,",");
            while (st.hasMoreTokens()) 
            {
                aCharList[iCounter]=st.nextToken();
                iCounter++;
            }
            for (int i=0; i < iCounter; i++)
            {
                if(aCharList[i]!=null){
                    RecLineChars[i]= Integer.parseInt(aCharList[i]);
                }
            }
            long lSlpLineCharsCount;
            lSlpLineCharsCount = getSlpLineChars(SlpLineChars);
            String charListsSlp = ptr.getSlpLineCharsList();
            String aCharListSlp[] = new String[_MAX_LINE_WIDTHS];
            int iCounterSlp=0;
            StringTokenizer stSlp = new StringTokenizer(charListsSlp,",");
            while (stSlp.hasMoreTokens()) 
            {
                aCharListSlp[iCounterSlp]=stSlp.nextToken();
                iCounterSlp++;
            }
            for (int i=0;i<iCounterSlp;i++)
            {
                if(aCharListSlp[i]!=null){
                    SlpLineChars[i]= Integer.parseInt(aCharListSlp[i]);
                }
            }
            
            ptr.setRecLetterQuality(true);
            //Register a bitmap
            /*if (ptr.getCapRecBitmap() == true)
            {
                boolean bSetBitmapSuccess = false;
                for (int iRetryCount = 0; iRetryCount < 10; iRetryCount++)
                {
                    try{
                        //Register a bitmap
                        ptr.setBitmap(1, POSPrinterConst.PTR_S_RECEIPT, "/RedDePagos/telefonica2.bmp",
                                (ptr.getRecLineWidth()/2), POSPrinterConst.PTR_BM_CENTER);
                        bSetBitmapSuccess = true;
                        break;
                    } catch (JposException ex)
                    {
                        if (ex.getErrorCode() == UPOSConst.UPOS_E_FAILURE && ex.getErrorCodeExtended() == 0 && ex.getMessage().equals("No esta inicializado."))
                        {
                            try{
                                Thread.sleep(1000);
                            } catch (InterruptedException ex2)
                            {
                            }
                        }
                    }
                }
                if (!bSetBitmapSuccess)
                {
                    BasePos.logger.info("Fallo en la carga del bitmap");                   
                }
            }*/
        }
        catch(JposException e){
            Tools.logStackTrace(BasePos.logger, e);
            throw new PosDeviceException("Excepcion en la inicializacion de la impresora: " + e.toString());            
        }
    }
    
    public void close() throws PosDeviceException {
        if(isOn == true){
            try{
                //Cancel the device.
                ptr.setDeviceEnabled(false);
                //Release the device exclusive control right.
                ptr.release();
                //Finish using the device.
                ptr.close();
            }
            catch(JposException ex){
                Tools.logStackTrace(BasePos.logger, ex);
                throw new PosDeviceException("Excepcion en el cierre de la impresora: " + ex.toString());
            }
            isOn = false;
            Base.logger.info("Dispositivo cerrado correctamente");
        }
        else{
            Base.logger.info("Dispositivo cerrado, falla en el cierre");
        }
    }
    
    public void openDevice() throws JposException{
        boleta = new ArrayList<String>();
        if(isOn == false){
            ptr.open("POSPrinter");
            ptr.claim(1000);
            ptr.setDeviceEnabled(true);        	
            try {
                init();
            } catch (PosDeviceException e) {
                Tools.logStackTrace(BasePos.logger, e);
                throw new JposException(0);
            }
            isOn = true;
            Base.logger.info("Dispositivo abierto correctamente");
        }
        else{
            Base.logger.info("Dispositivo abierto, falla en la apertura");
        }
    }

    public void printLine(int formato, String mensaje) throws PosDeviceException {
        String mensajeFormato = "";
        int num = 12;
        int []n = new int[num];
        try{
            n[0]= formato & PosDevicePrinter._MODE_LEFT;
            n[1]= formato & PosDevicePrinter._MODE_CENTER;
            n[2]= formato & PosDevicePrinter._MODE_RIGHT;
            n[3]= formato & PosDevicePrinter._MODE_BOLD;
            n[4]= formato & PosDevicePrinter._MODE_UNDERLINE;
            n[5]= formato & PosDevicePrinter._MODE_SMALL;
            n[6]= formato & PosDevicePrinter._MODE_BIG_1;
            n[7]= formato & PosDevicePrinter._MODE_BIG_2;
            n[8]= formato & PosDevicePrinter._MODE_BIG_3;
            n[9]= formato & PosDevicePrinter._MODE_BIG_4;
            n[10]= formato & PosDevicePrinter._MODE_CUT;
            n[11] = formato & PosDevicePrinter._MODE_LOGO;
            
            if( n[0] != 0){
                mensajeFormato = mensajeFormato + "\u001b|lA";
            }
            if( n[1] != 0){
                mensajeFormato = mensajeFormato + "\u001b|cA";
            }
            if( n[2] != 0){
                mensajeFormato = mensajeFormato + "\u001b|rA";
            }
            if( n[3] != 0){
                mensajeFormato = mensajeFormato + "\u001b|bC";
            }
            if( n[4] != 0){
                mensajeFormato = mensajeFormato + "\u001b|uC";
            }
            if( n[5] != 0){
                //ptr.setRecLineChars(RecLineChars[4]);
                ptr.setRecLineChars(RecLineChars[6]);
            }
            if( n[6] != 0){
                mensajeFormato = mensajeFormato + "\u001b|1C";
            }
            if( n[7] != 0){
                mensajeFormato = mensajeFormato + "\u001b|2C";
            }
            if( n[8] != 0){
                mensajeFormato = mensajeFormato + "\u001b|3C";
            }
            if( n[9] != 0){
                mensajeFormato = mensajeFormato + "\u001b|4C";
            }
            if( n[10]!= 0){
                mensajeFormato = mensajeFormato + "\u001b|fP";
            }
            if( n[11]!= 0){
                mensajeFormato = mensajeFormato + "\u001b|1B";
            }
            mensajeFormato = mensajeFormato + mensaje + "\u001b|N" + "\n";
            boleta.add(mensajeFormato);
            if( n[10] != 0 ){
                String boletaF = "";
                // aca se setea el ancho de la linea del voucher !!
               // ptr.setRecLineChars(RecLineChars[4]);
                ptr.setRecLineChars(RecLineChars[6]);
                for(int i = 0 ; i < boleta.size() ; i++){
                    boletaF += boleta.get(i);
                }
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT,boletaF);
                boleta = new ArrayList<String>();
            }
            ptr.setRecLineChars(RecLineChars[0]);
        }
        catch(JposException e){
            Tools.logStackTrace(BasePos.logger, e);
            throw new PosDeviceException("Excepcion en la impresion: " + e.toString());
        }
    }

    private long getRecLineChars(int[] recLineChars) throws JposException{
        long lRecLineChars = 0;
        long lCount;
        int i;

        // Calculate the element count.
        String[] temp = ptr.getRecLineCharsList().split(",");
        lCount = temp[0].length();

        if(lCount == 0) {
            lRecLineChars = 0;
        } 
        else {
            if (lCount > _MAX_LINE_WIDTHS)
            {
                lCount = _MAX_LINE_WIDTHS;
            }

            for( i = 0; i < lCount; i++) {
                recLineChars[i] = Integer.parseInt(temp[i]);
            }

            lRecLineChars = lCount;
        }
        return lRecLineChars;
    }
    
    private long getSlpLineChars(int[] slpLineChars) throws JposException{
        long lSlpLineChars = 0;
        long lCount;
        int i;

        // Calculate the element count.
        String[] temp = ptr.getSlpLineCharsList().split(",");
        lCount = temp[0].length();

        if(lCount == 0) {
            lSlpLineChars = 0;
        } 
        else {
            if (lCount > _MAX_LINE_WIDTHS)
            {
                lCount = _MAX_LINE_WIDTHS;
            }

            for( i = 0; i < lCount; i++) {
                slpLineChars[i] = Integer.parseInt(temp[i]);
            }

            lSlpLineChars = lCount;
        }
        return lSlpLineChars;
    }
    
    public void printBarCode(String mensaje) throws PosDeviceException {
        try{
            if (ptr.getCapRecBarCode() == true){
                ptr.printBarCode(POSPrinterConst.PTR_S_RECEIPT, mensaje, POSPrinterConst.PTR_BCS_Code39,
                        50, ptr.getRecLineWidth(), POSPrinterConst.PTR_BC_CENTER,
                        POSPrinterConst.PTR_BC_TEXT_BELOW);
            }    
        }
        catch(JposException e){
            Tools.logStackTrace(BasePos.logger, e);
            throw new PosDeviceException("Excepcion en la impresion: " + e.toString());
        }
    }
    
    public void printCheck(long monto,String nombre, int dia, int mes, int agno) throws PosDeviceException{
        try{
            try{
                ptr.beginInsertion(5000);
                ptr.endInsertion();
            }
            catch(JposException jex){
                Tools.logStackTrace(BasePos.logger, jex);
                throw new PosDeviceException("Timeout de ingreso cheque: " + jex.toString());
            }
            
            String montoString = TransNumeros.Transformar(monto);
            String montoString1;
            String montoString2;
            if( montoString.length() > 44){
                montoString1 = montoString.substring(0,44); 
                montoString2 = montoString.substring(44,montoString.length());
            }
            else{
                montoString1 = montoString; 
                montoString2 = "";
            }
            String nombreCheck = nombre;
            String cheque = "";
            
            /*cheque = cheque +  monto + ".-\u001b|4lF";
            cheque = cheque+ dia + "       "+ TransNumeros.procesarMes(mes)+ "  " + agno + "\u001b|1lF";
            cheque = cheque + "\u001b|1uF" + nombreCheck.toUpperCase() + "\u001b|1lF";
            cheque = cheque + "\u001b|1uF" + montoString1.toUpperCase() + "\u001b|1lF";
            cheque = cheque + "\u001b|1uF" +  montoString2.toUpperCase() +"-\u001b|1lF";*/
            
            ptr.rotatePrint(POSPrinterConst.PTR_S_SLIP, POSPrinterConst.PTR_RP_LEFT90);
            
            //cheque = "\u001b|11lF";
            //cheque = "\u001b|9lF" + "\u001b|3lF";
            /*ptr.printNormal(POSPrinterConst.PTR_S_SLIP,cheque);
           // cheque = "\u001b|rA" + monto + ".-\u001b|2lF";
            cheque = "\u001b|rA" + monto + ".-   \u001b|1lF";
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP,cheque);
            cheque = "\u001b|rA" + dia + "                  "+ TransNumeros.procesarMes(mes)+ "            " + agno + "     \u001b|1lF";
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP,cheque);
            cheque = nombreCheck.toUpperCase() + " \u001b|1lF";
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP,cheque);
            cheque = montoString1.toUpperCase() + " \u001b|1lF";
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP,cheque);
            cheque = montoString2.toUpperCase() +"- \u001b|1lF";*/
            cheque = "\u001b|9lF" + "\u001b|3lF";
            cheque += "\u001b|rA" + monto + ".-      \u001b|1lF";
            cheque += "\u001b|rA" + dia + "                "+ TransNumeros.procesarMes(mes)+ "            " + agno + "      "/*"\u001b|1lF"*/;
            cheque += "\u001b|N" + nombreCheck.toUpperCase() + " \u001b|1lF";
            cheque += montoString1.toUpperCase() + " \u001b|2lF";
            cheque += montoString2.toUpperCase() +"- \u001b|1lF";
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP,cheque);
            
            System.out.println("Impresion de Cheque: "+cheque);
            
            ptr.rotatePrint(POSPrinterConst.PTR_S_SLIP, POSPrinterConst.PTR_RP_NORMAL);
        }
        catch(JposException ex){
            Tools.logStackTrace(BasePos.logger, ex);
            throw new PosDeviceException("Excepcion en la impresion del cheque: " + ex.getMessage());
        }
        //Remove the slip at the slip station.
        try {
            ptr.beginRemoval(5000);
            ptr.endRemoval();
        } catch (JposException e) {
        }

    }
    
    public void printFranqueo(String rut, String telefono,String codAuto,long numOper,String monto,String operador,
    		                  String fecha,String hora, long entidad,long agencia,long caja,String cuentaDeposito,
    		                  String bancoDeposito, long codigoRechazo)throws PosDeviceException{
        try{
            ptr.beginInsertion(5000);
            ptr.endInsertion();
        }
        catch(JposException jex){
            Tools.logStackTrace(BasePos.logger, jex);
            throw new PosDeviceException("Timeout de ingreso cheque: " + jex.toString());
        }
        String leftSpace = "";
        
        try {
            int numLines = ptr.getSlpLineChars();
            for (int i = 33; i < ptr.getSlpLineChars(); i++){
                leftSpace += " ";
            }
        } catch (JposException e) {
            throw new PosDeviceException(e.toString());
        }
        String sendData = "";
        sendData = sendData + "\u001b|1lF ";
     // sendData = sendData + String.format("             %-13s: %15s","RUT TITULAR", rut);
        sendData = sendData + String.format("            %-10s: %15s","RUT TIT.", rut);
        sendData = sendData + "\u001b|1lF ";
       // sendData = sendData + String.format("             %-13s: %15s","FONO CONTACTO", telefono);
        sendData = sendData + String.format("            %-10s: %15s","FONO CONT.", telefono);
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + String.format("            %-10s: %15d","COD. OPER.", numOper);
       // sendData = sendData + String.format("             %-13s: %15s","COD.AUT", codAuto);
        /*if(codigoRechazo != 0) {
        	sendData = sendData + String.format("            %-10s: %22s","COD. AUT. MAN.", codAuto);
        } else {
        	sendData = sendData + String.format("            %-10s: %22s","COD. AUT.", codAuto);
        }*/
        
        sendData = sendData + "\u001b|1lF ";
       // sendData = sendData + String.format("             %-13s: %15s","MONTO", monto);
        sendData = sendData + String.format("            %-10s: %15s","MONTO", monto);
        sendData = sendData + "\u001b|1lF ";
       // sendData = sendData + String.format("             %-13s: %15s","CAJERO",operador);
        sendData = sendData + String.format("            %-10s: %15s","CAJERO",operador);
        sendData = sendData + "\u001b|1lF ";
       // sendData = sendData + String.format("             %-13s: %15s","FECHA", fecha);
        sendData = sendData + String.format("            %-10s: %15s","FECHA", fecha);
        sendData = sendData + "\u001b|1lF ";
       // sendData = sendData + String.format("             %-13s: %15s","HORA", hora);
        sendData = sendData + String.format("            %-10s: %15s","HORA", hora);
        sendData = sendData + "\u001b|1lF ";
       // sendData = sendData + String.format("             %-13s: %15d","AGENCIA", agencia);
        sendData = sendData + String.format("            %-10s: %15d","AGENCIA", agencia);
        sendData = sendData + "\u001b|1lF ";
      //  sendData = sendData + String.format("             %-13s: %15d","CAJA", caja);
        sendData = sendData + String.format("            %-10s: %15d","CAJA", caja);
        /**
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + "\u001b|1lF ";
        */
        // Inicio: se agrega Nro de Operacion (I_Servicio)
        sendData = sendData + "\u001b|1lF ";
        if(codigoRechazo != 0) {
        	sendData = sendData + String.format("            %-10s:\u001b|1lF               %22s","COD.INT.CLARO", codAuto);
        } else {
        	sendData = sendData + String.format("            %-10s: %22s","COD. AUT.", codAuto);
        }
     // sendData = sendData + String.format("             %-13s: %15d","COD.OPER", numOper);
        /*sendData = sendData + String.format("            %-10s: %15d","COD. OPER.", numOper);*/
        if(codigoRechazo != 0) {
        	sendData = sendData + "\u001b|1lF ";
        	sendData = sendData + String.format("            %-10s: %13d","COD. RECHAZO", codigoRechazo);
        }
        
        sendData = sendData + "\u001b|1lF ";
        // Fin:.. 
        sendData = sendData + "             VALOR EN COBRO PARA";
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + "             SER DEPOSITADO EN LA";
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + "             CTA. CTE  Nro. " + cuentaDeposito;
        sendData = sendData + "\u001b|1lF ";
        sendData = sendData + "             DEL BANCO " + bancoDeposito;
        sendData = sendData + "\u001b|1lF ";
        //sendData = sendData + "           P.P. VTR Banda Ancha(Chile)S.A.";
        sendData = sendData + "             P.P. Claro Chile S.A.";
        sendData = sendData + "\u001b|1lF ";
        
        //Printing process
        try {
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP, sendData);
        } catch (JposException e1) {
            throw new PosDeviceException(e1.toString());
        }
        
        //Remove the slip at the slip station.
        try {
            ptr.beginRemoval(5000);
            ptr.endRemoval();
        } catch (JposException e) {
            throw new PosDeviceException(e.toString());
        }

    }
    
    public void printFranqueoNew(String rut, String telefono,String codAuto,long numOper,String monto,String operador,String fecha,String hora,long entidad,long agencia,long caja,String cuentaDeposito,String bancoDeposito)throws PosDeviceException{
        try{
            ptr.beginInsertion(5000);
            ptr.endInsertion();
        }
        catch(JposException jex){
            Tools.logStackTrace(BasePos.logger, jex);
            throw new PosDeviceException("Timeout de ingreso cheque: " + jex.toString());
        }
        String leftSpace = "";
        
        try {
            int numLines = ptr.getSlpLineChars();
            for (int i = 25; i < ptr.getSlpLineChars(); i++){
                leftSpace += " ";
            }
        } catch (JposException e) {
            throw new PosDeviceException(e.toString());
        }
        String sendData = "";
        //sendData = sendData + "\u001b|1lF ";
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Rut", rut);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Teléfono", telefono);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Cod.Aut", codAuto);
        sendData = sendData + leftSpace + String.format("             %-12s: %15d\n","N°Operación", numOper);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Monto", monto);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Operador",operador);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Fecha", fecha);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Hora", hora);
        sendData = sendData + leftSpace + String.format("             %-12s: %15d\n","Entidad", entidad);
        sendData = sendData + leftSpace + String.format("             %-12s: %15d\n","Agencia", agencia);
        sendData = sendData + leftSpace + String.format("             %-12s: %15d\n","Caja", caja);
        sendData = sendData + leftSpace + String.format("\n");
        sendData = sendData + leftSpace + String.format("\n");
        sendData = sendData + leftSpace + String.format("             %-19s: %15s\n","Depositar en Cuenta", cuentaDeposito);
        sendData = sendData + leftSpace + String.format("             %-12s: %15s\n","Banco", bancoDeposito);
        //Printing process
        try {
            ptr.setSlpLineChars(SlpLineChars[1]);
            ptr.printNormal(POSPrinterConst.PTR_S_SLIP, sendData);
            ptr.setSlpLineChars(SlpLineChars[0]);
        } catch (JposException e1) {
            throw new PosDeviceException(e1.toString());
        }
        //Remove the slip at the slip station.
        try {
            ptr.beginRemoval(5000);
            ptr.endRemoval();
        } catch (JposException e) {
            throw new PosDeviceException(e.toString());
        }

    }
    
    public String makePrintString(int tipo,String text1,String text2){
        int spaces = 0;
        int lineChars = 0;
        if(tipo == 0){
            try {
                lineChars = ptr.getRecLineChars();
            } catch (JposException e) {
            }
        }
        else{
            try {
                lineChars = ptr.getRecLineChars()/tipo;
            } catch (JposException e) {
            }
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

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }
    
}
