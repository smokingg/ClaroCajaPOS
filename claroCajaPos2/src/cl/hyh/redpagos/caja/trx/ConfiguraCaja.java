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
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class ConfiguraCaja implements ITrxBase {
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
        else if( key == KeyEvent.VK_F3 ) {
            for(int i = 0 ; i < matrix.length ; i++){                
                contextos.get(matrix[i][0]).setProperty(matrix[i][1], matrix[i][2]);
            }
            int printerVersion = 0;
            for(Enumeration e = contextos.keys(); e.hasMoreElements();  ){
                String clave = (String)e.nextElement();
                if(clave.equals("datCaja")){
                    contextos.get(clave).setProperty("configurado", "si");
                }
                BufferedWriter bw = null;
                try {
                    File local = new File(config.getProperty(clave));                    
                    
                    bw =  new BufferedWriter(new FileWriter(local));
                    String line = null; 
                    
                    // se graban los campos y valores de la estructura asociada datos                        
                    for( Enumeration e1 = contextos.get(clave).keys(); e1.hasMoreElements();) {
                        String key1 = (String)e1.nextElement();
                        bw.write( key1 + "=" + contextos.get(clave).getProperty(key1) + "\n" );
                        if(key1.equals("printer")){
                            printerVersion = Integer.parseInt(contextos.get(clave).getProperty(key1));
                        }
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
            }
            File origen = null;
            if(printerVersion == 5){
                origen = new File("/RedDePagos/data/config/jpos5.xml");
            }
            else{
                origen = new File("/RedDePagos/data/config/jpos6.xml");
            }
                    
            File destino = new File("/RedDePagos/data/config/jpos.xml");
            try {
                ConfiguraCaja.copy(origen, destino);
            } catch (IOException e) {
                Base.logger.error(e.toString());
            }
            JOptionPane.showMessageDialog(null, "Caja configurada.\nDebe reinicializar", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
            LineaVoucher oper = new LineaVoucher();
            ParamSet pList = Base.getParamSet( "posDat" ); 
            oper.setLinea("Caja Configurada");
            oper.setBold(true);
            oper.setCenter(true);
            boleta.add(oper);
            for(int i = 0; i < 10 ; i++){
                oper = new LineaVoucher();
                oper.setLinea("");
                boleta.add(oper);
            }
            Voucher.printVoucher(boleta,true, Voucher.COPIA_CONFIGURACION_CAJA,null); 
            System.exit(0);
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
                vista.setEntryMessage( "Ingrese password", true );
                vista.setEntryTextLabel("Ingrese password:", true);
                vista.setEntryText("", true, false, true,null,null);
                return ICajaView._WAITFORACTION;
            case 1:
                pass = vista.getEntryText();
                ParamSet posCfg = Base.getParamSet("posCfg");
                if(pass.equals(posCfg.getStringValue("configPass"))){
                    estado = 2;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Usuario/clave incorrectos", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    Base.logger.info("Usuario o Clave invalido");
                    return 13;
                }
                String []botones = new String[12];
                botones[0] = "";
                botones[1] = "";
                botones[2] = "Guardar";
                botones[3] = "";
                botones[4] = "";
                botones[5] = "";
                botones[6] = "";
                botones[7] = "";
                botones[8] = "Volver";
                botones[9] = "";
                botones[10] = "";
                botones[11] = ""; 
                vista.paintButtons( botones );
                return ICajaView._NOWAITFORACTION;
            case 2:
                matrix = new String[config.size()-1][3];
                int i = 0;
                for(Enumeration e = config.keys(); e.hasMoreElements();  ){
                    String clave = (String)e.nextElement();
                    String []aux = ((String)config.getProperty(clave)).split("\\|");
                    if(aux.length != 2){
                        continue;
                    }
                    if(!contextos.containsKey(aux[0])){
                        Properties tmp = new Properties();
                        try {
                            tmp.load( new FileReader(config.getProperty(aux[0])));
                            contextos.put(aux[0], tmp);                           
                        } catch (IOException e1) {
                            Tools.logStackTrace(Base.logger, e1);
                        }  
                    }      
                    matrix[i][0] = aux[0];
                    matrix[i][1] = aux[1];
                    matrix[i][2] = contextos.get(aux[0]).getProperty(aux[1]);
                    i++; 
                                    
                }                
                String []titulos = new String[3];
                titulos[0] = "Contexto";
                titulos[1] = "Campo";
                titulos[2] = "Valor";
                boolean []editable = new boolean[titulos.length];
                for( int k = 0; k < editable.length; ++k ) {
                    editable[k] = false;
                }
                editable[2] = true;
                vista.showTableView(titulos, matrix,editable);
                estado = 4;
                return ICajaView._WAITFORACTION;
            case 4:
                return ICajaView._WAITFORACTION;
                
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
