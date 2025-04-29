package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import cl.hyh.redpagos.caja.trx.TrxCerrarCaja.MyFilter;

import com.jscape.inet.ftp.Ftp;
import com.jscape.inet.ftp.FtpException;

public class ThreadOperFtp extends Thread {
    
    public static class MyFilter implements FilenameFilter {
        public boolean accept( File dir, String name ) {
            if( name.endsWith( ".ctl" ) )
                return( true );
            else
                return( false );
        }
    }
    
    public void run() {
        
        ParamSet pSet = Base.getParamSet( "posCfg" );
        
        String host = pSet.getStringValue("host");
        String user = pSet.getStringValue("user");
        String pswd = pSet.getStringValue("pswd");
        String pdir = pSet.getStringValue("pdir");
        String odir = pSet.getStringValue( "FtpSafDir" );
        
        Ftp ftp = new Ftp(host, user, pswd);        
        
        try {
            ftp.setTimeout(Integer.parseInt(pSet.getStringValue("timeoutFTP"))*1000);
        } catch ( Exception ex ) {
            Tools.logStackTrace(Base.logger, ex);
            ftp.setTimeout(30*1000);
        }
        while(true){
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

            try {
                ftp.connect();
            } catch (FtpException e) {
                Base.logger.error("Fallo en la conexion con el FTP");
                Tools.espera(10);
                continue;
            }
            try {
                ftp.setDir(pdir);
            } catch (FtpException e) {
                Base.logger.error("Fallo en el change dir del FTP");
                try {
                    ftp.disconnect();
                } catch (FtpException e1) {
                    Base.logger.error("Fallo en la desconexion");
                }
                Tools.espera(10);
                continue;
            }
            
            for(int i = 0; i < archivosDat.length; i++){
                procesaArchivo(archivosCtl[i],archivosDat[i],ftp, odir);
            }
            
            try {
                ftp.disconnect();
            } catch (FtpException e) {
                Base.logger.error("Fallo en la desconexion");
                Tools.espera(10);
                continue;
            }
        }
    }
    public void procesaArchivo(String fileCtl, String fileDat, Ftp ftp, String oDir){
        File inFile = new File( oDir + fileDat );
        InputStream in = null;
        InputStream out = null;
        
        try{
            in = new FileInputStream( inFile );
            String inMD5 = Tools.getMD5Checksum( in );
            
            ftp.upload( inFile );
            
            //Traemos el archivo de vuelta y le calculamos su md5 y lo comparamos con el original
            
            File outFile = ftp.download( fileDat );
            out = new FileInputStream( outFile );
            
            String outMD5 = Tools.getMD5Checksum( out );
            
            if(inMD5.equals( outMD5 )){
                ftp.upload( "".getBytes() , fileCtl );
                outFile.delete();
                Base.logger.info("Enviado " + fileDat + " " + new Date());
                outFile = new File( oDir + fileCtl );
                outFile.delete();
                outFile = new File( oDir + fileDat );
                outFile.delete();                        
            }
            else{
                outFile.delete();
                Base.logger.info("Archivo " + fileDat + " no se pudo enviar con exito.");
                Tools.espera(5);
            }
            
        }
        catch(Exception e){
            Tools.logStackTrace(Base.logger, e);         
            Tools.espera(5);
        }
        finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
            }
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
            }                
        }
    }

}
