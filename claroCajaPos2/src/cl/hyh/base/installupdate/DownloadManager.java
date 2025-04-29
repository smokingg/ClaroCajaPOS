package cl.hyh.base.installupdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.comm.SoapConnectException;
import cl.hyh.redpagos.caja.comm.SoapTimeoutException;

import com.jscape.inet.ftp.Ftp;
import com.jscape.inet.ftp.FtpException;

public class DownloadManager {

	private int RETRIES_CONN = 5;
	private int RETRIES_DOWNLOAD = 5;
	/**
	 * @param args
	 * @throws SoapTimeoutException 
	 * @throws SoapConnectException 
	 * @throws BaseException 
	 */
	public static void main(String[] args) throws BaseException, SoapConnectException, SoapTimeoutException {
		// TODO Auto-generated method stub

		DownloadManager ftp = new DownloadManager();
		//File file = ftp.descargaArchivoFromFTP("192.168.1.119", "ftp", "ftp123", "/home/ftp/", "jpos.zip", "5");
		File file = ftp.descargaArchivoHttpGet("http://192.168.1.119/cajas/"+"jpos.zip", 15, "jpos.zip");
		System.out.println(file.getAbsolutePath());
		
	}
	
	
	public ZipFile descargaZip(){
		ZipFile file = null;
		ParamSet pSet = Base.getParamSet( "posCfg" );
		
		String url = pSet.getStringValue("urlZipFile");
		String nombreArchivo = pSet.getStringValue("zipFile");
		int timeout = Integer.parseInt(pSet.getStringValue("timeoutHttpGet"));
		
		File normalFile = null;
	    		
	    try {
	    	normalFile = descargaArchivoHttpGet(url+nombreArchivo,timeout,nombreArchivo);
	    	
			file = new ZipFile(normalFile);
		} catch (ZipException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			return null;
			
		} catch (IOException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			return null;
		} catch (BaseException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			e.printStackTrace();
			return null;
		} catch (SoapConnectException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			return null;
		} catch (SoapTimeoutException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			return null;
		}
	    
	    return file;
	}
	
	private File descargaArchivoHttpGet(String url, int timeout, String nombreArchivo) throws BaseException, SoapConnectException, SoapTimeoutException{
		HttpURLConnection aC = null;
        URL aURL = null;
        OutputStreamWriter oS = null;
        File archivo = null;
        
        
        try {
            aURL = new URL( url );
            aC = (HttpURLConnection) aURL.openConnection();
        } catch(Exception e) {
            Base.logger.error( "error URL " + e );
        	System.err.println("error URL: "+e.toString());
            throw new BaseException( e, "falla openConnection " + e );
        }
        
        aC.setConnectTimeout( 5000 );
        aC.setReadTimeout( timeout * 1000 );
                                    
        aC.setDoOutput( true );
        aC.setDoInput( true );
        try {
            aC.setRequestMethod( "GET" );
        } catch (ProtocolException e1) {;
        }
        aC.setUseCaches( false );
        aC.setAllowUserInteraction( false );                   

        try {                               
            oS = new OutputStreamWriter( aC.getOutputStream() );
            oS.write("");
            oS.flush();
            oS.close();
            oS = null;

            archivo = new File(System.getProperty("java.io.tmpdir")+System.getProperty("file.separator")+nombreArchivo);
            
            InputStream in = aC.getInputStream();
    		OutputStream out = new FileOutputStream(archivo);

    		byte[] buf = new byte[1024];
    		int len;
    		while ((len = in.read(buf)) > 0) {
    			out.write(buf, 0, len);
    		}
    		in.close();
    		out.close();
            
            
        } catch( java.net.SocketTimeoutException e ) {
            Base.logger.error( e );
            System.err.println("error timeout: "+e.toString());
            if( e.getMessage().startsWith( "connect" ) )
                throw new SoapConnectException();
            else
                throw new SoapTimeoutException();
        } catch (IOException e) {
            Base.logger.error( e );
            System.err.println("error IO: "+e.toString());
            throw new BaseException( e , " falla ejecucion de GET ZIP" );
        }
        finally {
            if( oS != null )
                try {
                    oS.close();
                } catch (IOException e) {
                }
        }
        
        if(archivo != null){
        	System.out.println(archivo.getAbsolutePath()+", "+archivo.getName());
        	Base.logger.info(archivo.getAbsolutePath()+", "+archivo.getName());
        }
		
		return archivo;
	}
	
	public ZipFile descargaZipFTP(){
		ZipFile file = null;
		ParamSet pSet = Base.getParamSet( "posCfg" );
		
		String host = pSet.getStringValue("hostFtp");
	    String user = pSet.getStringValue("userFtp");
	    String passw = pSet.getStringValue("passwFtp");
	    String timeout = pSet.getStringValue("timeoutFtp");
	    String ftpDir = pSet.getStringValue("dirFtp");
	    String archivo = pSet.getStringValue("zipFile");
	    
	    File normalFile = descargaArchivoFromFTP(host, user, passw, ftpDir, archivo, timeout);
	    
	    try {
			file = new ZipFile(normalFile);
		} catch (ZipException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			return null;
			
		} catch (IOException e) {
			Base.logger.error("Fallo al crear zip File desde clase File");
			Tools.logStackTrace(Base.logger, e);
			return null;
		}
	    
	    return file;
	}
	
	
	public File descargaArchivoFromFTP(String host, String user, String passw, String ftpDir, String archivo, String timeout){
		Ftp ftp = new Ftp(host, user, passw);        
        File file = null;
        
        try {
            ftp.setTimeout(Integer.parseInt(timeout)*1000);
        } catch ( Exception ex ) {
            Tools.logStackTrace(Base.logger, ex);
            ftp.setTimeout(30*1000);
        }
		
        for(int i = 0; i < RETRIES_CONN && !ftp.isConnected(); i++){
        	try {
        		System.out.println("Tratando conectar:"+i);
        		ftp.connect();
        	} catch (FtpException e) {
        		Base.logger.error("Fallo en la conexion con el FTP para descarga ZIP");
        		Tools.espera(10);
        	}
        }
        
        if(ftp.isConnected() == false){
        	Base.logger.error("Fallo en la conexion con el FTP para descarga ZIP");
        	return null;
        }
        
        try {
            ftp.setDir(ftpDir);
        } catch (FtpException e) {
            Base.logger.error("Fallo en el change dir del FTP ZIP");
            desconectar(ftp);
        }
        
        
        for(int i = 0; i < RETRIES_DOWNLOAD; i++){
        	try {
        		file = ftp.download(archivo);	
        	} catch (FtpException e1) {
        		Base.logger.error("Fallo en download del ZIP");
        		continue;
        	}
        	break;
        }
        
        desconectar(ftp);
        
		return file;
	}
	
	private void desconectar(Ftp ftp){
		for(int i = 0; i < RETRIES_CONN && ftp.isConnected(); i++){
			try {
            	ftp.disconnect();
        	} catch (FtpException e) {
            	Base.logger.error("Fallo en la desconexion");
            	Tools.espera(10);
        	}
		}
		
	}

}

