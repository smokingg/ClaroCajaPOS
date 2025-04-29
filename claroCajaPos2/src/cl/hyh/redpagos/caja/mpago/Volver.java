package cl.hyh.redpagos.caja.mpago;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.MedioPago;

/**
 * Medio de pago: Efecivo
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Volver extends MedioPago implements ITrxBase {
    int estado = 0;
    public void init(Datos datosVista){
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
    	
    	// remuevo el medio de pago ajuste sencillo antes de volver
    	if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size()>0){
    		for (int i = 0; i < vista.getOperTRV().getCarroMediosPago().getMediosPago().size() ; i++) {
				if(vista.getOperTRV().getCarroMediosPago().getMediosPago().get(i).getNombre().equals("AjusteSencillo")){
					vista.getOperTRV().getCarroMediosPago().getMediosPago().remove(i);
				}
			}
    	}
    	
    	return 14;
        /*if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
            JOptionPane.showMessageDialog(null, "Para volver debe vaciar\ncarro de medios de pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            return 13;
        }
        else return 14;*/
    	
    }
}
