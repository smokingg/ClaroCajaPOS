package cl.hyh.redpagos.caja.pos;


import java.io.IOException;

import cl.hyh.redpagos.caja.base.Base;

import jpos.JposException;
import jpos.MICR;
import jpos.events.DataEvent;
import jpos.events.DataListener;
import jpos.MICRControl113;
/**
 * Representacion interna del lector de cheques
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class PosDeviceMicr implements PosDeviceMicrInterface,DataListener{

    private MICR micr = null; 
    
    boolean isOn = false;
    private String banco = "";
    private String cuenta = "";
    private String serial = "";
    
    
    public void initMicr(){
        micr = (MICR)new MICR();
    }

    public void close() throws PosDeviceException {
        if(isOn == true ){
            try{
                micr.removeDataListener(this);
                micr.setDeviceEnabled(false);
                micr.release();
                micr.close();
            }
            catch(JposException ex){
                Tools.logStackTrace(BasePos.logger, ex);
                throw new PosDeviceException("Excepcion en el cierre del micr: " + ex.toString());
            }
            isOn = false;
            Base.logger.info("Dispositivo cerrado correctamente");
        }
        else{
            Base.logger.info("Dispositivo cerrado, falla en el cierre");
        }
    }
    
    public void openDevice() throws PosDeviceException {
        if(isOn == false){
            micr.addDataListener(this);
            try{
                micr.open("MICR");
                micr.setDataEventEnabled(true);
                micr.claim(1000);
                micr.setDeviceEnabled(true);
            }
            catch(JposException e){
                Tools.logStackTrace(BasePos.logger, e);
                throw new PosDeviceException("Excepcion en la apertura del mirc: " + e.toString()); 
            }
            isOn = true;
            Base.logger.info("Dispositivo abierto correctamente");
        }
        else{
            Base.logger.info("Dispositivo abierto, falla en la apertura");
        }
    }

    public void remove() throws PosDeviceException{
        try{
            micr.beginRemoval(5000);
            micr.endRemoval();
        }
        catch(JposException e){
            Tools.logStackTrace(BasePos.logger, e);
            throw new PosDeviceException("Excepcion en el retiro del cheque: " + e.toString());
        }
    }
    
    public void insert() throws PosDeviceException{
        
        try{
            //Insertion operation of a check is started.
            this.banco = "";
            micr.beginInsertion(5000);
            micr.endInsertion();
        }
        catch(JposException ex){
            Tools.logStackTrace(BasePos.logger, ex);
            throw new PosDeviceException("Timeout del ingreso del cheque: " + ex.toString());
        }
    }
    
    public void dataOccurred(DataEvent de){
        MICR control = (MICR)de.getSource();
        try{
            System.out.println("Raw Data: "+control.getRawData()+"\n");
            
            int n1 = control.getRawData().indexOf("r");
            serial = control.getRawData().substring(1, n1);
            
            int n2 = control.getRawData().indexOf("r", n1+1);
           // banco = control.getRawData().substring(n1+2,n1+5);
            banco = control.getRawData().substring(n1+2,n1+5);
            
            int n3 = control.getRawData().indexOf("i");
            cuenta = control.getRawData().substring(n2+1,n3);
            
            //String a4 = control.getRawData().substring(n3+2);
            String a4 = control.getRawData().substring(n3);
            
            System.out.println("Serial:" + serial);
            System.out.println("Banco:" + banco);
            System.out.println("Cuenta:" + cuenta);
            System.out.println("Codigo 4:" + a4);
            control.setDataEventEnabled(true);
        }
        catch(JposException ex){
            Base.logger.info("Error en el listener del lector de cheques");
            Tools.logStackTrace(Base.logger, ex);
        }
    }
    
    /**
     * Retorna la variable banco
     * @return
     */
    public String getBanco() {
        return banco;
    }

    /**
     * Retorna la variable cuenta
     * @return
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * Retorna la variable serial
     * @return
     */
    public String getSerial() {
        return serial;
    }
    public boolean isLeido(){
        if(!banco.equals("")){
            return true;
        }
        else{
            return false;
        }
    }
}
