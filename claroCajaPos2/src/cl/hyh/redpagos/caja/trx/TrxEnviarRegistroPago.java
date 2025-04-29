package cl.hyh.redpagos.caja.trx;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.NotificadorOffline;

public class TrxEnviarRegistroPago implements ITrxBase{

    protected Datos headerIn;
    
    public static class MyFilter implements FilenameFilter {
        public boolean accept( File dir, String name ) {
            if( name.endsWith( ".ctl" ) )
                return( true );
            else
                return( false );
        }
    }
    
    public void init(Datos datosVista){        
        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){
    	
    	if( vista.showMyConfirmDialog("Confirme por favor","Esta operación no es reversible, Se enviaran los Pagos Registrados hasta este Momento\n Presione SI para continuar o NO para volver al menu principal")
                == JOptionPane.NO_OPTION ) {
            return 12;
        }
    	
    	
    	if (NotificadorOffline.notificar()) {
    		JOptionPane.showMessageDialog(null, "Se Ha Enviado el Archivo de Registro de Pagos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			Base.logger.info("Se ha Enviado Archivo CVS Contingencia");
		} else {
			JOptionPane.showMessageDialog(null, "No es posible Enviar el Archivo de Registro de Pagos\nEste envio se realizara en cuanto vuelva la caja Online", "Continuar", JOptionPane.INFORMATION_MESSAGE);
			Base.logger.info("No se ha Enviado Archivo CVS Contingencia");
		}
		
    	return 11;
    }
    

}
