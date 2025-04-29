package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class ConfiguraImpresora implements ITrxBase {
    int estado = 0;
    String user = "";
    String pass = "";
    String [][]matrix = null;
    Properties config = new Properties();;
    Hashtable <String,Properties>contextos = new Hashtable<String,Properties>();
    
    public void init(Datos datosVista){  
        try {
            config.load( getClass().getClassLoader().getResourceAsStream("config/config.cfg") );            
        } catch (IOException e) {
            Tools.logStackTrace(Base.logger, e);
        }
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 ) {
            estado = 0;
            vista.acceptEscape(true);
        } else if( key == 1 ) {
            return 13;
        }
        else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_ESCAPE ) {
            return 13;
        } 
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
         
        switch( estado ) {
            case 0:
                estado = 1;
                vista.setEntryTitle( "Configuracion ", true );
                vista.setEntryMessage( "Ingrese impresora", true );
                String []filtros = new String[2];
                filtros[0] = "Epson TM-H5200II";
                filtros[1] = "Epson TM-H6000III";
                vista.setEntryList(filtros, true, true);
                return ICajaView._WAITFORACTION;
            case 1:
                int index = vista.getEntryListIndex();
                File origen = null;
                if(index == 0){
                    origen = new File("/RedDePagos/data/config/jpos5.xml");
                }
                else{
                    origen = new File("/RedDePagos/data/config/jpos6.xml");
                }
                        
                File destino = new File("/RedDePagos/data/config/jpos.xml");
                try {
                    ConfiguraImpresora.copy(origen, destino);
                } catch (IOException e) {
                    Base.logger.error(e.toString());
                }
                ParamSet posDat = Base.getParamSet("posDat");
                if(index == 0){                    
                    posDat.setValue( "printer", 5 );
                    posDat.save();
                }
                else{
                    posDat.setValue( "printer", 6 );
                    posDat.save();
                }
                JOptionPane.showMessageDialog(null, "Impresora configurada.\nDebe reinicializar", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
                return ICajaView._NOWAITFORACTION;
                
        }
        return 0;
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
