package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ArchivoCuadratura {
	 
	public static final String EFECTIVO = "mpEfectivo";
	public static final String CHEQUE = "mpCheque";
	public static final String DEBITO = "mpDebito";
	public static final String CREDITO = "mpCredito";
	public static final String MULTITIENDA = "mpMultitienda";
	public static final String AJUSTE = "mpAjusteSencillo";
	public static final String VALEVISTA = "";
	 
	 public static void crearArchivoCuadratura(){
		
		String fecha = Tools.getFechaYYYYMMDD();
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String dir = "";
        String dirVal = "ValidaOfflineDirCierres";
        String s = "";
        FileWriter ficheroCuadratura = null;
        PrintWriter pw = null;
        
    	dir = "OfflineDirCierres";
    	s = String.format( "Txrs_%s", fecha );
    	try{ 
    		
    	String nombreArchivoCvs = pSet.getStringValue( dir ) + s + ".dat";
        File fichero = new File(pSet.getStringValue( dirVal ) + s + ".dat");
              
        if (!fichero.exists()){
        	
         ficheroCuadratura = new FileWriter(nombreArchivoCvs,true);
       	 pw = new PrintWriter(ficheroCuadratura);
       	 pw.println("mpEfectivo=0");
       	 pw.println("mpCheque=0");
       	 pw.println("mpDebito=0");
       	 pw.println("mpCredito=0");
       	 pw.println("mpMultitienda=0");
       	         	
        }
    	}
        catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			 if (ficheroCuadratura!=null){
    				 ficheroCuadratura.close();
    			 }
    		
    		}catch(Exception e2){
    			e2.printStackTrace();
    			
    		}
    	}
		
		
	}
	
public static void actualizarArchivoCuadratura(String linea){
		
		String fecha = Tools.getFechaYYYYMMDD();
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String dir = "";
        String dirDel = "ValidaOfflineDirCierres";
        String s = "";
        FileWriter ficheroCuadratura = null;
        PrintWriter pw = null;
     
        
    	dir = "OfflineDirCierres";
    	
    	s = String.format( "Txrs_%s", fecha );
    	try{ 
    		
    	String nombreArchivoCvs = pSet.getStringValue( dir ) + s + ".dat";
        File fichero = new File(pSet.getStringValue( dirDel ) + s + ".dat");
        
        if (fichero.exists()){
                
         ficheroCuadratura = new FileWriter(nombreArchivoCvs,true);
         pw = new PrintWriter(ficheroCuadratura);
          
         pw.println(linea);
     
        }
    	}
        catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			ficheroCuadratura.close();
    			
    		
    		}catch(Exception e2){
    			e2.printStackTrace();
    			
    		}
    	}
		
		
	}


public static String[] procesaArchivoCuadratura(){
	
	String fecha = Tools.getFechaYYYYMMDD();
	String fechaBackup = Tools.getFechaHora();
    ParamSet pSet = Base.getParamSet( "posCfg" );
    String dir = "";
    String dirBck = "";
   
    String s = "";
    String sbck = "";
    FileWriter ficheroCuadratura = null;
    PrintWriter pw = null;
    String lineaProcesada = "";
    long efectivo=0;
    long cheque=0;
    long debito=0;
    long credito=0;
    long multi=0;
    long ajusteSencillo=0;
    //long valeVista=0;
    String totales [] = new String[6]; 
    
	dir = "OfflineDirCierres";
	dirBck="ValidaOfflineBackUp";
	String dirDel = "ValidaOfflineDirCierres";
	
	s = String.format( "Txrs_%s", fecha );
	sbck = String.format( "Txrs_%s", fechaBackup );
	try{ 
	
	String nombreArchivoCvs = pSet.getStringValue( dir ) + s + ".dat";
	String nombreArchivoCvsBackUp = pSet.getStringValue( dirBck ) + sbck + ".dat";
	String nombreArchivoCvsAmover = pSet.getStringValue( dirDel ) + s + ".dat";
    File fichero = new File(nombreArchivoCvs);
    
	    if (fichero.exists()){
	    	FileReader lector=new FileReader(nombreArchivoCvs);
	    	BufferedReader contenido=new BufferedReader(lector);
	    	while((lineaProcesada=contenido.readLine())!=null){
	    		String [] campos = lineaProcesada.split("=");
	    		if(campos[0].equalsIgnoreCase(EFECTIVO)){
	    			efectivo = efectivo + Long.parseLong(campos[1]);
	    		}
	    		
	    		if(campos[0].equalsIgnoreCase(CHEQUE)){
	    			cheque = cheque + Long.parseLong(campos[1]);
	    		}
	    		
	    		if(campos[0].equalsIgnoreCase(DEBITO)){
	    			debito = debito + Long.parseLong(campos[1]);
	    		}
	    		
	    		if(campos[0].equalsIgnoreCase(CREDITO)){
	    			credito = credito + Long.parseLong(campos[1]);
	    		}
	    		
	    		if(campos[0].equalsIgnoreCase(MULTITIENDA)){
	    			multi = multi + Long.parseLong(campos[1]);
	    		}
	    		
	    		if(campos[0].equalsIgnoreCase(AJUSTE)){
	    			ajusteSencillo = ajusteSencillo + Long.parseLong(campos[1]);
	    		}
	    		    	
	    	}
	    }
	    
	    totales [0] = String.valueOf(efectivo);
	    totales [1] = String.valueOf(cheque);
	    totales [2] = String.valueOf(debito);
	    totales [3] = String.valueOf(credito);
	    totales [4] = String.valueOf(multi);
	    totales [5] = String.valueOf(ajusteSencillo);
	    
	    Tools.fileMove(nombreArchivoCvsAmover, nombreArchivoCvsBackUp);
	    		
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
	try{
		if(ficheroCuadratura!=null){
			ficheroCuadratura.close();
		}
		
	}catch(Exception e2){
		e2.printStackTrace();
		
	}
}

    return totales;
	
	
	
}

	
	
	
}
