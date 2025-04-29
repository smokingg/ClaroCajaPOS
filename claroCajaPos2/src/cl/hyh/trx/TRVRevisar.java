package cl.hyh.trx;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.visual.MyConfirmDialog;

public class TRVRevisar implements ITrxBase {

	public void init(Datos htParm ) {}
	
	public int execute( ICajaView vista, int key, Datos htParam ) {
		/**
		JOptionPane.showMessageDialog(null, "No se encuentra habilitado este Módulo....", "Continuar", JOptionPane.INFORMATION_MESSAGE);
        return 13;
		*/
		ParamSet pList = Base.getParamSet( "posDat" );
		
    	if( vista.getOperTRV().getCarroCompras().getDocumentos().size() == 0 ) {
            JOptionPane.showMessageDialog(null, "No tiene documentos en el carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            return 13;
    	}
    	
    	if (pList.getStringValue("Perfil").equalsIgnoreCase("2")){
    		return 15;
    	}else{
    		return 14;
    	}
  	    
    }
}
