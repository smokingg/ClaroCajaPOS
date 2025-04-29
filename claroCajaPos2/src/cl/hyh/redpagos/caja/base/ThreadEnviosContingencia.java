package cl.hyh.redpagos.caja.base;

public class ThreadEnviosContingencia extends Thread {
	
	
	 public void run() {
		 
		 
		 
		 
		 while (true){
		 
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
             	break;
             }
		 }
		 
          Base.logger.info("Se detiene la ejecucion del Hilo ThreadEnviosContingencia ");
		 
	 }

}
