package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JOptionPane;

import cl.hyh.redpagos.caja.base.ThreadOperFtp.MyFilter;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ThreadRevisaRemesa extends Thread {
    
    public static class MyFilter implements FilenameFilter {
        public boolean accept( File dir, String name ) {
            if( name.endsWith( ".ctl" ) )
                return( true );
            else
                return( false );
        }
    }
    
    public void run() {
        
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String odir = pSet.getStringValue( "OperSafDir" );      
        Datos data = null;
        
        while(true){     
            Tools.espera(10*Integer.parseInt(pSet.getStringValue("revisaRemesa")));
            DefServicio def = Base.getDefServicio("ConsultaTotalesCaja");
            data = new Datos(def.getInputRecordDef());
            Servicio consultaTotales = null; 
            int resp = 0;
            try {
                consultaTotales = FactoryServicio.makeInstance("ConsultaTotalesCaja");
                consultaTotales.setRequest(data);
                resp = consultaTotales.execute();
            } catch (BaseException e) {
                Tools.logStackTrace(Base.logger, e);
                JOptionPane.showMessageDialog(null, e.getMsg(),"Error", JOptionPane.INFORMATION_MESSAGE);
                continue;
            }
            ArrayList <Datos> totales = consultaTotales.getResponse().getArrayList("Total");

            if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
                Base.logger.info("El execute del servicio retorno error o timeout"); 
                continue;
            }
            if(totales.size() == 0){
                Base.logger.info("No existen totales"); 
                continue;
            }
            long efectivo = 0;
            long cheque = 0;
            long vale = 0;
            long remesa = 0;
            for(int i = 0;  i < totales.size() ; i++){
                if(totales.get(i).getStringValue("Concepto").equals("MPEfectivo")){
                    efectivo += Long.parseLong(totales.get(i).getStringValue("Monto"));
                }
                else if(totales.get(i).getStringValue("Concepto").equals("MPCheque")){
                    //cheque += Long.parseLong(totales.get(i).getStringValue("Monto"));
                }
                else if(totales.get(i).getStringValue("Concepto").equals("MPChequeFecha")){
                    //cheque += Long.parseLong(totales.get(i).getStringValue("Monto"));
                }
                else if(totales.get(i).getStringValue("Concepto").equals("MPValeVista")){
                    //vale += Long.parseLong(totales.get(i).getStringValue("Monto"));
                }
                else if(totales.get(i).getStringValue("Concepto").equals("MPValeVistaBci")){
                    //vale += Long.parseLong(totales.get(i).getStringValue("Monto"));
                }
                if(totales.get(i).getStringValue("Concepto").equals("EnvioRemesaEF")){
                    remesa += Long.parseLong(totales.get(i).getStringValue("Monto"));
                }
            }
            
			if(Long.parseLong(pSet.getStringValue("montoRemesa")) <= (cheque - remesa) ){
                JOptionPane.showMessageDialog(null, "Debe realizar una remesa de Cheques", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
            if(Long.parseLong(pSet.getStringValue("montoRemesa")) <= ( efectivo + cheque + vale - remesa ) ){
                JOptionPane.showMessageDialog(null, "Debe realizar una remesa", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            continue;
        }
    }
}
