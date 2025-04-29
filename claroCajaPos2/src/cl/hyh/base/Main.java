package cl.hyh.base;

import javax.swing.JOptionPane;

import cl.hyh.base.installupdate.ProcesaZip;
import cl.hyh.redpagos.caja.base.Base;

/**
 * @author abertens
 *
 */
public class Main {
	

    public static void main(String[] args) {
    	 
    
    	new Base().preInit();
       
      
       /**
        * 
    
       //Bajar el Zip si corresponde76568660-1
       ProcesaZip p = new ProcesaZip();
     
       int rc = p.install();
       if( rc > 0 ){
           JOptionPane.showMessageDialog(null, "Caja instalada.\nDebe reinicializar", "Continuar", JOptionPane.INFORMATION_MESSAGE);
      
           System.exit(0);
        }
       else if( rc < 0 ){
           JOptionPane.showMessageDialog(null, "Falla en la instalacion.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
           System.exit(0);
       }
       */ 
       
       Motor motor = new Motor();
       motor.Start();
       // NUNCA LLEGA ACA...
    } 

}
