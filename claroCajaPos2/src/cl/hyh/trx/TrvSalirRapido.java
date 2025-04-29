package cl.hyh.trx;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.visual.MyConfirmDialog;

public class TrvSalirRapido implements ITrxBase {

    public void init(Datos htParm ) {}
    
    public int execute( ICajaView vista, int key, Datos htParam ) {
    	
    	if(vista.getOperTRV() != null){
    		
	        if( vista.getOperTRV().getCarroCompras().getDocumentos().size() > 0 ) {
	        
	        	if( new MyConfirmDialog( "Confirme por favor",
			    		"Tiene documentos en el carro de compras.\nQuiere salir de todas maneras?").showDialog()
			    			== JOptionPane.NO_OPTION ) {
			 	   // ME QUEDO DONDE ESTOY...
			      return 11;
			   } else {
		           // DESTRUIR LO QUE ESTA EN EL CARRO DE COMPRAS
				   if(Base.getEdicion()){
	        		   Base.logger.info("Esta en una Edicion, no se permite eliminar Medio de Pago");
	        		   // TODO debe eliminar solo el carro de Documentos
	         		   vista.getOperTRV().getCarroCompras().getDocumentos().clear();
	        	   }else{
	        		   vista.getOperTRV().reversar();
	        		   vista.removeTRV();
	        		   Base.setEdicion(false);
	        	   }
		           ParamSet posDat = Base.getParamSet("posDat");
		           posDat.setValue("FechaPago", Tools.getFecha());
		           posDat.save();
		           
		           return 12;
			   }    
	        }
	        
	        // DESTRUIR LO QUE ESTA EN EL CARRO DE COMPRAS
	        if(Base.getEdicion()){
	 		   Base.logger.info("Esta en una Edicion, no se permite eliminar Medio de Pago");
	 		   // TODO debe eliminar solo el carro de Documentos
	 		   vista.getOperTRV().getCarroCompras().getDocumentos().clear(); 
	 	    }else{
	 	       Base.logger.info("No es una Edicion, va a reversar la Operacion completa !!!");
	 		   vista.getOperTRV().reversar();
	 		   vista.removeTRV();
	 		   Base.setEdicion(false);
	 	    }
	        
	        ParamSet posDat = Base.getParamSet("posDat");
	        posDat.setValue("FechaPago", Tools.getFecha());
	        posDat.save();
	       
	        return 12;
        
    	}else{
			Base.logger.info("No existen Documentos en el Carro.. solo retornar");
			return 12;
		}
    	
        // SI HAY EN CARRO DE MEDIOS DE PAGO, DEBE HABER EN CARRO DE COMPRAS ?
    }
}
