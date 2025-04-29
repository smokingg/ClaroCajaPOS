package cl.hyh.trx;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.visual.MyConfirmDialog;

public class TRVVaciar implements ITrxBase {

	public void init(Datos htParm ) {}
	
	public int execute( ICajaView vista, int key, Datos htParam ) {
		
    	if( vista.getOperTRV().getCarroCompras().getDocumentos().size() > 0 ) {
            if( new MyConfirmDialog( "Confirme por favor",
            		"Se eliminarán todos los documentos del carro de compras.Desea continuar?").showDialog()
            			== JOptionPane.NO_OPTION ) {
         	   // ME QUEDO DONDE ESTOY...
              return 11;
           } else {
         	   OperTRV oper = vista.getOperTRV();
     		   oper.getCarroCompras().getDocumentos().clear();
     		   // debiese vaciar tambien el carro de Medios de pago existente ??
     		   if(Base.getEdicion()){
     			   Base.logger.info("Esta en una Edicion, no se elimina el Medio de Pago");
     		   }else{
     			  oper.getCarroMediosPago().getMediosPago().clear();
     		   }
     		   
        	   return 11;
           }
    	}
    	return 11;
    }
}
