package cl.hyh.base.installupdate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;

public class ProcesaZip {

	private String fileSeparator = System.getProperty("file.separator");
	private static Enumeration<?> entries;
	private static String nuevosDirectorios = "dir.dat";
	private String directoriosLibrerias = "files.dat";
	private String tmpDir = System.getProperty("java.io.tmpdir")+fileSeparator;
	private static String jrePath;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		for(Enumeration<Object> e = System.getProperties().keys();e.hasMoreElements(); ){
			String key = (String)e.nextElement();
			System.out.println(key+": "+System.getProperty(key));
		}
		String tmpDir = System.getProperty("java.io.tmpdir")+System.getProperty("file.separator");
		ZipFile file = new ZipFile(tmpDir+"jpos.zip");
		ProcesaZip p = new ProcesaZip();
		p.procesaZipFile(file);
		
		
	}

	
	public int install(){
		if(new File(fileSeparator+"RedDePagos"+fileSeparator+"data"+fileSeparator+"config"+fileSeparator+"pos.dat").exists()){
			ParamSet pSet = Base.getParamSet("posDat");
			ParamSet pCfg = Base.getParamSet("posCfg");
			
			String actual = pSet.getStringValue("versionAmbiente");
			String nueva = pCfg.getStringValue("versionAmbiente");
			if( actual.compareTo(nueva) != 0){
				System.out.println("Versiones distintas: actual:"+actual+", Nueva version:"+nueva);
				Base.logger.info("Versiones distintas: Actual:"+actual+", Nueva version:"+nueva);
			}
			else{
				System.out.println("Versiones iguales: actual:"+actual+", Nueva version:"+nueva);
				Base.logger.info("Versiones iguales: Actual:"+actual+", Nueva version:"+nueva);
				return 0;
			}
			
		}
		
		DownloadManager downMngr = new DownloadManager();
		/*Descarga desde el sitio FTP seteado en properties*/
		ZipFile file = downMngr.descargaZipFTP();
		/*Descarga con un request GET a URL del archivo*/
		//ZipFile file = downMngr.descargaZip(); 
		try {
			procesaZipFile(file);
		} catch (FileNotFoundException e) {
			Tools.logStackTrace(Base.logger, e);
			return -1;
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
			return -1;
		} catch (Exception e){
			Tools.logStackTrace(Base.logger, e);
			return -1;
		}
		return 1;
	}
	
	
	
	public int procesaZipFile(ZipFile zipFile) throws FileNotFoundException, IOException{
		jrePath = System.getProperty( "java.home" ); 
		
		explotaZip(zipFile); 
		
		creaDirectorios();
		
		mueveArchivos();
		
		return 0;
	}
	
	private void mueveArchivos(){
		System.out.println("Moviendo archivos");
		Base.logger.debug("Moviendo archivos");
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(tmpDir+directoriosLibrerias));
			Enumeration<?> e = props.propertyNames();
			for(;e.hasMoreElements();){
				String archivo = (String)e.nextElement();
				String dirDestino = props.getProperty(archivo);
				if(dirDestino.compareTo("jre") == 0) {
					dirDestino = jrePath+fileSeparator;  
				}
				else if(dirDestino.compareTo("bin") == 0){
					dirDestino = jrePath+fileSeparator+"bin"+fileSeparator;
				}
				else if(dirDestino.compareTo("lib") == 0){
					dirDestino = jrePath+fileSeparator+"lib"+fileSeparator+"ext"+fileSeparator;
				}
				else{
					dirDestino += fileSeparator;
				}
				System.out.println("Archivo:"+archivo+", dir:"+dirDestino);
				Base.logger.debug("Archivo:"+archivo+", dir:"+dirDestino);
				File origen = new File(tmpDir+archivo);
				File destino = new File(dirDestino+archivo);
				if(archivo.compareTo("pos.dat") == 0){
					if(!destino.exists())
						move(origen, destino);
					else{
						/*manejo para updatear archivo y no sobre-escribir*/
						System.out.println("El archivo ya existe, busco nuevas propiedades solamente");
						Base.logger.debug("El archivo ya existe, busco nuevas propiedades solamente");
						procesaPosDat(origen,destino);
					}
				}
				else{
					move(origen, destino);
				}
			}
		}
		catch(IOException e)
		{
			Tools.logStackTrace(Base.logger, e);
		}
		
		
	}
	
	private void procesaPosDat(File nuevo, File actual){
		Properties nuevasProps = new Properties();
		Properties actualProps = new Properties();
		
		try {
			nuevasProps.load(new FileInputStream(nuevo));
			actualProps.load(new FileInputStream(actual));
		} catch (FileNotFoundException e1) {
			Tools.logStackTrace(Base.logger, e1);
		} catch (IOException e1) {
			Tools.logStackTrace(Base.logger, e1);
		}
		Enumeration<?> enumNuevas = nuevasProps.propertyNames();
		
		int flag = 0;
		for(;enumNuevas.hasMoreElements();){
			String propiedad = (String)enumNuevas.nextElement();
			if(actualProps.containsKey(propiedad)){
				System.out.println("Llave ya existe:"+propiedad+", no updateo");
				Base.logger.debug("Llave ya existe:"+propiedad+", no updateo");
			}
			else{
				System.out.println("La propiedad es nueva para el archivo:"+ propiedad);
				Base.logger.debug("La propiedad es nueva para el archivo:"+ propiedad);
				String valor = nuevasProps.getProperty(propiedad);
				actualProps.setProperty(propiedad, valor);
				flag = 1;
			}
		}
		//Actualizamos la version en caja, cambiando la version del ambiente
		ParamSet posCfg = Base.getParamSet("posCfg");
		actualProps.setProperty("versionAmbiente", posCfg.getStringValue("versionAmbiente"));
		actualProps.setProperty("configurado", "no");
		//if(flag == 1){
		Enumeration<?> enumActuales = actualProps.propertyNames();
		System.out.println("Escribiendo nuevas propiedades a archivo");
		Base.logger.debug("Escribiendo nuevas propiedades a archivo");
		BufferedWriter bw = null;
        try {
            bw =  new BufferedWriter(new FileWriter(actual));
            // se graban los campos y valores de la estructura asociada datos                        
            for( ; enumActuales.hasMoreElements();) {
                String key1 = (String)enumActuales.nextElement();
                bw.write( key1 + "=" + actualProps.getProperty(key1) + "\n" );
            }
            bw.close(); bw = null;
            } 
        catch (Exception e4) {
            Tools.logStackTrace(Base.logger, e4);
        }
        finally {
            if( bw != null ){
                try {
                    bw.close();
                } 
                catch (IOException e4) {
                    }
                }
        }
		//}
	}
	
	private void explotaZip(ZipFile zipFile){
		entries = zipFile.entries();
		while(entries.hasMoreElements()){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			if(entry.isDirectory()) {
				System.err.println("Este es un directorio:" + entry.getName());
				Base.logger.error("Este es un directorio:" + entry.getName());
		        continue;
		    }
			System.out.println("Entry:"+entry.getName());
			Base.logger.debug("Entry:"+entry.getName());
			try {
				copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(tmpDir+entry.getName())) );
				
			} catch (FileNotFoundException e) {
				Tools.logStackTrace(Base.logger, e);
			} catch (IOException e) {
				Tools.logStackTrace(Base.logger, e);
			}
		}
	}
	
	private void creaDirectorios(){
		System.out.println("Creando nuevos directorios");
		Base.logger.debug("Creando nuevos directorios");
		File dirDat = null;
		dirDat = new File(tmpDir+nuevosDirectorios);
	
		/*leemos linea por linea los directorios a verificar/crear*/
	    
	    try {
	      BufferedReader input =  new BufferedReader(new FileReader(dirDat));
	      try {
	        String line = null;
	        File dir = null;
	        while (( line = input.readLine()) != null){
	        	dir = new File(line);
	        	if(dir.exists()){
	        		Base.logger.debug("Directorio ya existe:"+line);
	        		System.out.println("Directorio ya existe:"+line);
	        	}
	        	else{
	        		Base.logger.debug("Directorio no existe:"+line+", CREANDO");
	        		System.out.println("Directorio no existe:"+line);
	        		
	        		if(dir.mkdirs()){
	        			Base.logger.debug("Directorio creado:"+line+", CREANDO");
		        		System.out.println("Directorio creado:"+line);
	        		}
	        		else{
	        			Base.logger.debug("Directorio creado:"+line+", CREANDO");
		        		System.out.println("Directorio no creado:"+line);
	        		}
	        	}
	        }
	      }
	      finally {
	    	  input.close();
	      }
	    }
	    catch (IOException ex){
	    	Tools.logStackTrace(Base.logger, ex);
	    }

	}
	
	
	
	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException{
	    byte[] buffer = new byte[1024];
	    int len;

	    while((len = in.read(buffer)) >= 0)
	      out.write(buffer, 0, len);
	    
	    in.close();
	    out.close();
	}

	public static synchronized void move(File src, File dest) throws FileNotFoundException, IOException {
		copy(src, dest);
		src.delete();
	}
	
	public static synchronized void copy(File src, File dest) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
	
}
