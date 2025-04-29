package cl.hyh.base.install;

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
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ProcesaZip {

	private String fileSeparator = System.getProperty("file.separator");
	private static Enumeration<?> entries;
	private static String nuevosDirectorios = "dir.dat";
	private String directoriosLibrerias = "files.dat";
	private String tmpDir = System.getProperty("java.io.tmpdir")+fileSeparator;
	private static String jrePath;
	
	public static void main(String[] args) throws IOException {	    
	    
	    new Base().init();
		for(Enumeration<Object> e = System.getProperties().keys();e.hasMoreElements(); ){
			String key = (String)e.nextElement();
			System.out.println(key+": "+System.getProperty(key));
		}
		ProcesaZip p = new ProcesaZip();
		ZipFile file = new ZipFile("jpos.zip");
		p.procesaZipFile(file);
		
		
	}	
	
	public void armaZipFile(){
	    
	    File fDir = new File( "zip" );
        String[] filenames = fDir.list( );
        
        if( filenames.length == 0 ){
            return;
        }
	    
	    // Create a buffer for reading the files
	    byte[] buf = new byte[1024];
	    
	    try {
	        // Create the ZIP file
	        String outFilename = "jpos.zip";
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
	    
	        // Compress the files
	        for (int i=0; i<filenames.length; i++) {
	            FileInputStream in = new FileInputStream("zip/" + filenames[i]);
	    
	            // Add ZIP entry to output stream.
	            out.putNextEntry(new ZipEntry(filenames[i]));
	    
	            // Transfer bytes from the file to the ZIP file
	            int len;
	            while ((len = in.read(buf)) > 0) {
	                out.write(buf, 0, len);
	            }
	    
	            // Complete the entry
	            out.closeEntry();
	            in.close();
	        }
	    
	        // Complete the ZIP file
	        out.close();
	    } catch (IOException e) {
	    }
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
				Base.logger.debug("Archivo:"+archivo+", dir:"+dirDestino);
				File origen = new File(tmpDir+archivo);
				File destino = new File(dirDestino+archivo);
				if(archivo.compareTo("pos.dat") == 0){
					if(!destino.exists())
						move(origen, destino);
					else{
						/*manejo para updatear archivo y no sobre-escribir*/
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
		    Base.logger.error(e.toString());
		}
		
		
	}
	
	private void procesaPosDat(File nuevo, File actual){
		Properties nuevasProps = new Properties();
		Properties actualProps = new Properties();
		
		try {
			nuevasProps.load(new FileInputStream(nuevo));
			actualProps.load(new FileInputStream(actual));
		} catch (FileNotFoundException e1) {
		    Base.logger.error(e1.toString());
		} catch (IOException e1) {
		    Base.logger.error(e1.toString());
		}
		Enumeration<?> enumNuevas = nuevasProps.propertyNames();
		
		int flag = 0;
		for(;enumNuevas.hasMoreElements();){
			String propiedad = (String)enumNuevas.nextElement();
			if(actualProps.containsKey(propiedad)){
			    Base.logger.debug("Llave ya existe:"+propiedad+", no updateo");
			}
			else{
			    Base.logger.debug("La propiedad es nueva para el archivo:"+ propiedad);
				String valor = nuevasProps.getProperty(propiedad);
				actualProps.setProperty(propiedad, valor);
				flag = 1;
			}
		}
		//Actualizamos la version en caja, cambiando la version del ambiente
		actualProps.setProperty("configurado", "no");
		//if(flag == 1){
		Enumeration<?> enumActuales = actualProps.propertyNames();
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
            Base.logger.error(e4.toString());
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
			    Base.logger.error(e.toString());
			} catch (IOException e) {
			    Base.logger.error(e.toString());
			}
		}
	}
	
	private void creaDirectorios(){
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
	        	}
	        	else{
	        	    Base.logger.debug("Directorio no existe:"+line+", CREANDO");
	        		
	        		if(dir.mkdirs()){
	        		    Base.logger.debug("Directorio creado:"+line+", CREANDO");
	        		}
	        		else{
	        		    Base.logger.debug("Directorio creado:"+line+", CREANDO");
	        		}
	        	}
	        }
	      }
	      finally {
	    	  input.close();
	      }
	    }
	    catch (IOException ex){
	        Base.logger.error(ex.toString());
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
