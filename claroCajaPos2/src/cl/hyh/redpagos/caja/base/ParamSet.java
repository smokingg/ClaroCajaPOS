package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Set;

import cl.hyh.redpagos.caja.base.parser.DefParamSet;
import cl.hyh.redpagos.caja.base.parser.DefRecord;
import cl.hyh.redpagos.caja.base.parser.ParserException;

/**
 * Representacion de los parametros de la aplicion
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParamSet {

    private DefParamSet defParamSet;
    private Datos datos;
    
    /**
     * @param defParamSet
     */
    public ParamSet( DefParamSet defParamSet ) {
        this.defParamSet = defParamSet;
        datos = new Datos( defParamSet.getDefRecord() );
        
        load();
    }
    
    /**
     * Se cargan los parametros a partir de la definicion existente
     * 
     */
    public void load() {        
        ClassLoader cl = this.getClass().getClassLoader();
        
        if(defParamSet.getType().equals("resource")){
            String fname = defParamSet.getFName();
            InputStream in = null;
            try {
                in = cl.getResource(fname).openStream();
                BufferedReader input = null;
                input =  new BufferedReader(new InputStreamReader(in));
                String line = null; 
                while (( line = input.readLine()) != null){
                    String []aux = line.split("=");
                    this.datos.setValue(aux[0], aux[1]);
                }
                input.close();
            }
            catch (Exception e) {
                Tools.logStackTrace(Base.logger, e);
                return;
            }
        }
        else {
            File local = new File(defParamSet.getFName());
            try {
                BufferedReader inputLocal =  new BufferedReader(new FileReader(local));
                String line = null; 
                while (( line = inputLocal.readLine()) != null){
                    String []aux = line.split("=");
                    if( aux.length >= 2 )
                        this.datos.setValue(aux[0], aux[1]);
                    else if( aux.length == 1 )
                        this.datos.setValue( aux[0], "" );
                }
                inputLocal.close();
            } catch (Exception e) {
                Tools.logStackTrace(Base.logger, e);
                return;
            }
        }
    }
    
    /**
     * Se guardan los cambios realizados a los parametros en los archivos que los definen
     * 
     */
    public void save() {   
        
        FileOutputStream os = null;
        
        try {
            
            os = new FileOutputStream( defParamSet.getFName() + ".tmp" );
            FileDescriptor fd = os.getFD();
            
            String line = null; 
            
            // se graban los campos y valores de la estructura asociada datos
           
            Set<String> llaves = datos.getDatos().keySet();
            String[] ss = new String[datos.getDatos().size()];
            llaves.toArray( ss );
            
            for( int i = 0; i < llaves.size(); i++ ) {
                String name = ss[i];
                String valor = datos.getStringValue( name );
                os.write( (name + "=" + valor + "\n").getBytes() );
            }
            
            os.flush();
            fd.sync();
            os.close(); os = null;
            
            File f1 = new File( defParamSet.getFName() );
            f1.delete();
            File f = new File( defParamSet.getFName() + ".tmp" );
            f.renameTo( f1 );
            
        } catch (Exception e) {
            Tools.logStackTrace(Base.logger, e);
            return;
        }
        finally {
            if( os != null )
                try {
                    os.close();
                } catch (IOException e) {
                }
        }
    }
    
    public String getFixedValue( String campo ) {
        
        String s = datos.getFixedValue( defParamSet.getDefRecord(), campo );
        return s;
        
    }
    
    /**
     * Retorna un parametro de tipo int a partir de su nombre
     * @param campo
     * @return
     */
    public int getIntValue( String campo ) {
        return datos.getIntValue( campo );
    }
    
    /**
     * Retorna un parametro de tipo String a partir de su nombre
     * @param campo
     * @return
     */
    public String getStringValue( String campo ) {
        return datos.getStringValue( campo );
    }
    
    /**
     * Retorna un parametro de tipo date a partir de su nombre
     * @param campo
     * @return
     */
    public Date getDateValue( String campo ) {
        return datos.getDateValue( campo );
    }
    
    /**
     * Setea un parametro mediante la tupla campo-valor de tipo int
     * @param campo
     * @param valor
     */
    public void setValue( String campo, int valor ) {        
        datos.setValue( campo, valor );
    }
    
    /**
     * Setea un parametro mediante la tupla campo-valor de tipo String
     * @param campo
     * @param valor
     */
    public void setValue( String campo, String valor ) {  
        datos.setValue( campo, valor );
    }
    
    /**
     * Setea un parametro mediante la tupla campo-valor de tipo date
     * @param campo
     * @param valor
     */
    public void setValue( String campo, Date valor ) {
        datos.setValue( campo, valor );
    }

    /**
     * Retorna la definicion de ParamSet que corresponde al ParamSet actual
     * @return
     */
    public DefParamSet getDefParamSet() {
        return defParamSet;
    }

    /**
     * Setea la definicion de ParamSet que define la clase
     * @param defParamSet
     */
    public void setDefParamSet(DefParamSet defParamSet) {
        this.defParamSet = defParamSet;
    }

    /**
     * Retorna la estructura de tipo Datos
     * @return
     */
    public Datos getDatos() {
        return datos;
    }

    /**
     * Setea la estructura de Datos de la clase
     * @param datos
     */
    public void setDatos(Datos datos) {
        this.datos = datos;
    }
    
    public long getLongValue( String name ) {
        return datos.getLongValue( name );
    }
    
    public void setValue( String name, long valor ) {
        this.datos.setValue( name, valor );
    }
    
}