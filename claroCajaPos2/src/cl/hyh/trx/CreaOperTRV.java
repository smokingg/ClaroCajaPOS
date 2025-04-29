package cl.hyh.trx;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.NotificadorOffline;
import cl.hyh.redpagos.caja.base.Serializa;

/**
 * @author abertens
 *
 */
public class CreaOperTRV implements ITrxBase {

	public void init(Datos htParm ) {} 

	public int execute( ICajaView vista, int key, Datos htParam ) {
		// CREO EL OPERTRV QUE DARA SOPORTE A ESTA VENTA/RECAUDACION
        vista.createTRV();
        
        if(Serializa.procesarArchivosOffline()){
        	Base.logger.info("Se han Procesado los Archivos de Transacciones Contingencia");
        	Base.logger.info("Se Procedera a enviar el mail....");
        	if(NotificadorOffline.notificar()){
        		Base.logger.info("Se ha enviado el mail....");
        	}else{
        		Base.logger.info("Ha ocurrido un error al enviar el mail");
        	}
        	
        }else{
        	Base.logger.info("No se han Procesado Archivos de Transacciones Contingencia");
        }
        
        
        // DEBE DESTRUIRSE AL VOLVER AL MENU DE CAJERO
        return ICajaView._PREPOSTRETURN;
    }
    
}
