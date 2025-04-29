package cl.hyh.base.install;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ActualizaZip {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        new Base().init();
        Base.logger.info("Se comienza el armado del zip");
        ActualizaZip a = new ActualizaZip();
        a.armaZipFile();
        Base.logger.info("Zip creado");
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
            Base.logger.error(e.getMessage());
        }
    }
}
