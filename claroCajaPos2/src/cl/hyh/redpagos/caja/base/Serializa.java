package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.hyh.cajas.ws.impl.EnvioReversaIn;
import cl.hyh.cajas.ws.impl.Request;

public class Serializa {
	
	//public static void serializa(Request r , String tipo) {
	public static void serializa(Operacion r , String tipo) {
        
        // se graba imagen serializada del OperTRV
        
        // NO generamos numero de operacion, ya se obtuvo anteriormente
        
        String fecha = Tools.getFechaHora();
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String dir = "";
        String s = "";
        
        if(tipo.equals("SAF")){
        	dir = "OperSafDir";
        	s = String.format( "Oper_%s", fecha );
        }
        else if(tipo.equals("REVERSA")){
        	dir = "VoucherDir";
        	s = "reversaPago";
        }
        
        try {
            
            FileOutputStream fos = new FileOutputStream( pSet.getStringValue( dir ) + s + ".dat" );
            ObjectOutputStream outStream = new ObjectOutputStream( fos );

            FileDescriptor fd = fos.getFD();
            
            outStream.writeObject( r );
            
            fos.flush();
            fd.sync();
            outStream.close();
            
            //Intentamos leer el archivo dat, si no se puede arrojamos una excepcion y terminamos la ejecución
            File outFile = null;
            ObjectInputStream in = null;
            Object o = null;
            try{
                in = new ObjectInputStream(new FileInputStream(pSet.getStringValue( dir ) + s + ".dat"));
                o = in.readObject();
                in.close();
                in = null;
            }catch(Exception e){
                Tools.logStackTrace(Base.logger, e);
                System.exit(1);         
            }
            
            //Todo OK continuamos para grabar el .ctl            
            FileOutputStream fctl = new FileOutputStream( pSet.getStringValue( dir ) + s + ".ctl" );
            outStream = new ObjectOutputStream( fctl );
            
            fd = fctl.getFD();
            fos.flush();
            fd.sync();
            
            outStream.close();   
            
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
            System.exit(1);
        }
        
    }
	
public static void serializaOffline(Operacion r) {
        
        // se graba imagen serializada del OperTRV
        
        // NO generamos numero de operacion, ya se obtuvo anteriormente
        
        String fecha = Tools.getFechaHora();
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String dir = "";
        String s = "";
        
    	dir = "OperSafOfflineDir";
    	s = String.format( "OperOffline_%s", fecha );

        File fichero = new File(pSet.getStringValue( dir ) + s + ".dat");
        
        if (!fichero.exists()){
    	
    	
        try {
            
            FileOutputStream fos = new FileOutputStream( pSet.getStringValue( dir ) + s + ".dat",true );
            ObjectOutputStream outStream = new ObjectOutputStream( fos );

            FileDescriptor fd = fos.getFD();
            
            outStream.writeObject( r );
         //   outStream.reset();
            
            fos.flush();
            fd.sync();
            outStream.close();
            
            //Intentamos leer el archivo dat, si no se puede arrojamos una excepcion y terminamos la ejecución
            File outFile = null;
            ObjectInputStream in = null;
            Object o = null;
            try{
                in = new ObjectInputStream(new FileInputStream(pSet.getStringValue( dir ) + s + ".dat"));
                o = in.readObject();
                in.close();
                in = null;
            }catch(Exception e){
                Tools.logStackTrace(Base.logger, e);
                System.exit(1);         
            }
            
            //Todo OK continuamos para grabar el .ctl            
            FileOutputStream fctl = new FileOutputStream( pSet.getStringValue( dir ) + s + ".ctl",true );
             outStream = new ObjectOutputStream( fctl );
            
            fd = fctl.getFD();
            fos.flush();
            fd.sync();
            
            outStream.close();   
            
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
            System.exit(1);
        }
        }
        else{
        	
        	 try {
                 
                 FileOutputStream fos = new FileOutputStream( pSet.getStringValue( dir ) + s + ".dat",true );
                 ObjectOutputStreamAligare outStream = new ObjectOutputStreamAligare( fos );

                 FileDescriptor fd = fos.getFD();
                 
                 outStream.writeUnshared( r );
              //   outStream.reset();
                 
                 fos.flush();
                 fd.sync();
                 outStream.close();
                 
                 //Intentamos leer el archivo dat, si no se puede arrojamos una excepcion y terminamos la ejecución
                 File outFile = null;
                 ObjectInputStream in = null;
                 Object o = null;
                 try{
                     in = new ObjectInputStream(new FileInputStream(pSet.getStringValue( dir ) + s + ".dat"));
                     o = in.readObject();
                     in.close();
                     in = null;
                 }catch(Exception e){
                     Tools.logStackTrace(Base.logger, e);
                     System.exit(1);         
                 }
                 
                 //Todo OK continuamos para grabar el .ctl            
                 FileOutputStream fctl = new FileOutputStream( pSet.getStringValue( dir ) + s + ".ctl",true );
                  outStream = new ObjectOutputStreamAligare( fctl );
                 
                 fd = fctl.getFD();
                 fos.flush();
                 fd.sync();
                 
                 outStream.close();   
                 
             } catch (Exception e) {
                 Tools.logStackTrace(Base.logger, e);
                 System.exit(1);
             }
        	
        }
        
    }

	public static boolean procesarArchivosOffline(){
		
		 ParamSet pSet = Base.getParamSet("posDat");
		 ParamSet pCfg = Base.getParamSet( "posCfg" );
		 ArrayList<String> archivos = new ArrayList<String>();
		 ArrayList<String> archivosErroneos = new ArrayList<String>();
		 ArrayList<String> archivosProcesados = new ArrayList<String>();
		 Base.logger.info("Comienza el Procesamiento de Pagos Offline");
		 boolean continuar= true;
	        if(pSet.getStringValue("sinConexion").equals("no")){
	        	
	        	File directorioOfflineCvs = new File(pCfg.getStringValue("ValidaOfflineDir"));
	         	String[] ficheros = directorioOfflineCvs.list();
	         	
	         	if(ficheros != null && ficheros.length>0){
	         		
	         		for (int x=0; x < ficheros.length;x++){
	         			if(ficheros[x].endsWith(".dat")){
	         				archivos.add(ficheros[x]);
	         			}
	         		}
	         		
	         		if(archivos.isEmpty()){
	         			return false;
	         		}
	         		int cantidadArchivos =  archivos.size();
	         		int errores=0;
	         		int procesador = 0;
	         		
	         		Base.logger.info("Se han registrado " + cantidadArchivos + " Pagos en Contingencia");
	         		
	         		for (Iterator<String> iter = archivos.iterator(); iter.hasNext();) {
	         			String filename = (String) iter.next();
	         			         			
				         Operacion opOut =  Base.procesarArchivoOffline(filename);
				        
			         	 int retorno =  Base.enviarTrxOffilne(opOut);
				         	 if ( retorno != 0){
				         		 
				         		 if(retorno == -1){
					         		Base.logger.info("Se ha Vuelto a perder la conexion, el proceso se detendra");
					         		continuar=false;
					         		break;
				         		 }
				         		 if(retorno == -2){
				         			Base.logger.info("El archivo " + filename + " no se ha podido procesar");
				         			archivosErroneos.add(filename);
				         			errores++;
				         		 }
				         		 
				         		 
				         	 }else{
				         		 procesador++;
				         		 Base.logger.info("Se han procesados " + procesador + " Trx registradas Contingencia");
				         		 archivosProcesados.add(filename);
				         	 }
			         		
//				         if(!continuar){
//				        	 return false;
//				         }
				         	
				         
	         		}
	         		  if(!continuar){
				        	 return false;
				         }
	         		  
	         		for (Iterator<String> iter = archivosErroneos.iterator(); iter.hasNext();) {
	         			String filename = (String) iter.next();
	         			String nombreArchivoCvs = pCfg.getStringValue("ValidaOfflineDir") + filename;
				 	 	String nombreArchivoCvsBackUp = pCfg.getStringValue("ValidaOfflineBackUpErrores") + filename;
				 		
				 		Tools.fileMove(nombreArchivoCvs, nombreArchivoCvsBackUp);
	         		}
	         		
	         		
	         		for (Iterator<String> iter = archivosProcesados.iterator(); iter.hasNext();) {
	         			String filename = (String) iter.next();
	         			String nombreArchivoCvs = pCfg.getStringValue("ValidaOfflineDir") + filename;
				 	 	String nombreArchivoCvsBackUp = pCfg.getStringValue("ValidaOfflineBackUp") + filename;
				 		
				 		Tools.fileMove(nombreArchivoCvs, nombreArchivoCvsBackUp);
	         		}
	         		
	         		Base.logger.info("Total de Archivos Procesados " + cantidadArchivos + " ");
	         		Base.logger.info("Procesados Correctamente " + procesador + " ");
	         		Base.logger.info("Procesados con Errores " + errores + "");
	         	 
	          }else{
	        	  return false;
	          }
	        }else{
	        	 return false;
	        }
		
		return true;
	}

}
