package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

/**
 * Implementacion de un pago rapido en efectivo por codigo de barras Atis
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TrxRecarga extends OperAdmin implements ITrxBase, Serializable{
    int estado = 0;
    Datos data = null;
    long montoDoc = 0;
    DocumentoPago docP = null;
    MedioPago mPago = null;
    String []opt = new String[2];
    //String []opt = new String[1];
    
    public TrxRecarga(String nombreServicio, boolean online) {
    }
    private static final long serialVersionUID = 1L;
    
    public TrxRecarga(){
    }
    public void init(){
        estado = 0;
    }
    public int execute(ICajaView vista, int key, Datos htParam) {
        if( key == 0 ) {            
            estado = 0;
            // TODO se comenta , aun no se habilita esta operacion..
            /**
            if(vista.getOperTRV().getCarroCompras().getDocumentos().size() > 0){
                JOptionPane.showMessageDialog(null, "Carro de Compras no vacio\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            */
            // Se habilita msj momentaneo !!!
            JOptionPane.showMessageDialog(null, "No se encuentra habilitado este Módulo....", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            return 13;
            
            
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
        case 0:
            
        	/**
        	return 15;
            /*opt[0] = "Telefonía Móvil";
            opt[1] = "Otro Prepago";
            estado = 1;            
            vista.setEntryMessage( "Ingrese tipo recarga", true );
            vista.setEntryTextLabel("Ingrese tipo recarga:", true);
            vista.setEntryList(opt, true, true);
            return ICajaView._WAITFORACTION;*/
        	
        	// TODO cbriones: se agrega momentaneamente...
        	estado = 1;
            
            vista.setEntryMessage( "No se encuentra habilitado este Módulo....", true );
            //vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
            // vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]{1,12}","Monto no valido");
            return ICajaView._WAITFORACTION;  
            
            
        case 1:
            estado = 2;
            int index = vista.getEntryListIndex();
            switch( index ){
                case 0:
                    return 15;
                case 1:
                    return 15;
                case 2:
                    return 15;
                case 3:
                    return 15;
                
            }
           
        }
        
        return 0;
    }
}
