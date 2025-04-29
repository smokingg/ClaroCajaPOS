package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.proxy.AppControlNotificarProxy;
import ws.claro.cl.proxy.AppControlProxy;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.hyh.cajas.ws.impl.EnvioReversaIn;
import cl.hyh.cajas.ws.impl.OperacionIn;
import cl.hyh.cajas.ws.impl.Response;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.redpagos.caja.base.ThreadOperFtp.MyFilter;

/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ThreadOperSaf extends Thread {
    
	private ParamSet paramSet;
	
    public static class MyFilter implements FilenameFilter {
        public boolean accept( File dir, String name ) {
            if( name.endsWith( ".ctl" ) )
                return( true );
            else
                return( false );
        }
    }
    
    
    public ThreadOperSaf(ParamSet paramSet) {
    	this.paramSet=paramSet;
    }
    @Override
    public void run() {
        
    	
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String odir = pSet.getStringValue( "OperSafDir" );      
        
        // TODO cbriones: se rescatan los codigos que se deben eliminar del SAF
        String codSaf = null;
        if (paramSet.getStringValue( "codigosSaf" )!=null) {
        	 codSaf = paramSet.getStringValue( "codigosSaf" );   	       
        	
        }else {
	         codSaf = pSet.getStringValue( "codigosSaf" ); 
        }
        String codigos[] = codSaf.split(",");
        
        while(true){
            try{
            	
            	// TODO cbriones: se rescatan los codigos que se deben eliminar del SAF
            //	Base.logger.info("Codigos de respuesta a eliminar del SAF: "+codSaf);
            	
                File fDir = new File( odir );
                String[] archivosCtl = fDir.list( new MyFilter() );
                
                if( archivosCtl.length == 0 ){
                    Tools.espera(10);
                    continue;
                }
                ArrayList<String> auxL = new ArrayList<String>();
                
                // ordenamos lista de archivos por nombre ascendentemente
                for( int i = 0; i < archivosCtl.length; i++ ) {
                    auxL.add( archivosCtl[i] );
                }
                
                Collections.sort( auxL );
                for( int i = 0; i < archivosCtl.length; i++ ) {
                    archivosCtl[i] = auxL.get( i );
                }
                
                String []archivosDat = new String[archivosCtl.length];
                
                for( int i = 0; i < archivosCtl.length; i++ ) {
                    String okFName = archivosCtl[i];
                    int l = okFName.length();
                    String datName = okFName.substring( 0, l - 4 ) + ".dat";
                    archivosDat[i] = datName;
                }
                
                for(int i = 0; i < archivosDat.length; i++){
                    //procesaArchivo(archivosCtl[i],archivosDat[i], odir);
                	procesaArchivo(archivosCtl[i],archivosDat[i], odir, codigos);
                }
                
                Tools.espera(10);
            }
            catch(Exception e){
                Tools.logStackTrace(Base.logger, e);
                continue;
            }
        }
    }
    
    //public void procesaArchivo(String fileCtl, String fileDat, String oDir){
    public void procesaArchivo(String fileCtl, String fileDat, String oDir, String codigos[]){
        File outFile = null;
        ObjectInputStream in = null;
        Object o = null;
        
        try{
        	
            try{
                in = new ObjectInputStream(new FileInputStream(oDir + fileDat));
                o = in.readObject();
                in.close();
                in = null;
                Base.logger.info("Leido correctamente archivo de Notificacion Saf....");
            }catch(Exception e){
                Base.logger.error("Error en la carga del OperTrv desde " + fileDat);
                return;
            }
            
            int resp = -1;
            //Response response = null;
            NotificacionRespuesta response = null;
            
            //if(o.getClass() == OperacionIn.class){
            if(o.getClass() == Operacion.class){	
            	response = null;
            	//ServerProxy pr = Proxy.getProxyInstance();
            	PlataformaPagoNotificarServerProxy pr =  AppControlNotificarProxy.getProxyInstance();
            	Base.logger.info("Enviando Notificacion Saf....");
            	Operacion op = null;
                try {
                	// TODO aca se debe enviar el DTO correspondiente a Claro !!
					//response = pr.envioOperacion((OperacionIn)o);
                	NotificacionEnvio notif = new NotificacionEnvio();
                	notif.setOperacion((Operacion)o);
                	op = (Operacion)o;
                	Base.logger.info("op NumeroOperacion: "+op.getNumeroOperacion());
                	Base.logger.info("op TipoOperacion: "+op.getTipoOperacion());
                	Base.logger.info("op Monto: "+op.getMonto());
             
					response = pr.notificar(notif);
				} catch (RemoteException e) {
					Tools.logStackTrace(Base.logger, e);
					return;
				}
            }
            
            //if(response.getHeaderOut().getRc() == 0 || response.getHeaderOut().getRc() == 1){
            // TODO validar que codigos de retorno se deben validar para una Operacion exitosa ??
            if(response.getRespuesta().getRetCode() == 0 || response.getRespuesta().getRetCode() == 1){
                Base.logger.info("Enviado Saf " + fileCtl + " " + new Date());
                Base.logger.info("Enviado Saf " + fileDat + " " + new Date());
                outFile = new File( oDir + fileCtl );
                outFile.delete();
                outFile = new File( oDir + fileDat );
                outFile.delete(); 
            }else{
            	Base.logger.error("Codigo Retorno Saf: " + response.getRespuesta().getRetCode());
                Base.logger.error("Msj Retorno Saf: " + response.getRespuesta().getRetDesc());
                
            	// TODO cbriones: Validar para el caso de Codigo -14 (No existe Nro. de Operacion), 
            	// se debe eliminar el SAF ..
            	// Validar si la cadena de codigos contiene el retornado desde App de Control..
                for(int i=0; i<codigos.length; i++){
                	Base.logger.info("Codigo lista: "+codigos[i]);
                	if(codigos[i].equalsIgnoreCase(String.valueOf(response.getRespuesta().getRetCode()))){
                		// Corresponde a un Codigo de la lista..  Eliminar !!!!!
                		Base.logger.info("Enviado Saf (lista) " + fileCtl + " " + new Date());
                        Base.logger.info("Enviado Saf (lista) " + fileDat + " " + new Date());
                        outFile = new File( oDir + fileCtl );
                        outFile.delete();
                        outFile = new File( oDir + fileDat );
                        outFile.delete(); 
                	}
                }
            }
        
        }
        catch(Exception e){
            Tools.logStackTrace(Base.logger, e);
        }
        finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
            }             
        }
    }
}
