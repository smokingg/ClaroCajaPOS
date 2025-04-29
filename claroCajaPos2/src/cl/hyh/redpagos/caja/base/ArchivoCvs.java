package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ArchivoCvs {

	
	
	public static void crearArchivoCvs(String linea){
		
		String fecha = Tools.getFechaYYYYMMDD();
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String dir = "";
        String s = "";
        FileWriter ficheroCvs = null;
        PrintWriter pw = null;
        
    	dir = "OfflineDirCvs";
    	s = String.format( "EnvioCvs_%s", fecha );
    	try{ 
    		
    	String nombreArchivoCvs = pSet.getStringValue( dir ) + s + ".cvs";
    		
        File fichero = new File(pSet.getStringValue( dir ) + s + ".cvs");
              
        if (!fichero.exists()){
        	
        	 ficheroCvs = new FileWriter(nombreArchivoCvs,true);
        	 pw = new PrintWriter(ficheroCvs);
        	 pw.println(pSet.getStringValue("cabeceraCvs"));
        	 pw.println(linea);
        	
        }else{
       	 ficheroCvs = new FileWriter(nombreArchivoCvs,true);
       	 pw = new PrintWriter(ficheroCvs);
       	 pw.println(linea);
        	
        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			if(ficheroCvs!=null){
    				ficheroCvs.close();
    			}
    		
    		}catch(Exception e2){
    			e2.printStackTrace();
    			
    		}
    	}
		
	}

}
