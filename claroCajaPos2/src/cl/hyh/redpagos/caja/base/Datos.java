package cl.hyh.redpagos.caja.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import cl.hyh.redpagos.caja.base.parser.DefElement;
import cl.hyh.redpagos.caja.base.parser.DefField;
import cl.hyh.redpagos.caja.base.parser.DefRecord;

/**
 * Representación interna de datos. Se usa un HashMap que contiene String, Double, Integer, Date, Datos
 * o ArrayList<Datos> para representar los datos de una estructura proveniente de un XML o definida a través de
 * records en las definiciones de servicios, documentos y similares
 * 
 * @author Rafael Hernandez
 *
 */
/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Datos implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private HashMap<String,Object> datos;
    
    /**
     * constructor normal que generar estructura vacia
     */
    public Datos() {
        datos = new HashMap<String,Object>();
    }
    
    /**
     * constructor que genera area de datos a partir de definicion de registro indicada almacenando
     * valores defaults para cada uno de los campos
     * @param defRecord
     */
    public Datos( DefRecord defRecord ) {
        /**
            Se instancia estructura de datos de acuerdo a la definicion del record que se indica
        */
        datos = new HashMap<String,Object>();
        
        ArrayList<DefElement> defElements = defRecord.getElements();
        for( int i = 0; i < defElements.size(); i++ ) {
            initDefElement( defElements.get( i ), this );
        }
    }
    
    private void initDefElement( DefElement dElement, Datos losDatos ) {
        
        if( dElement instanceof DefField ) {
            DefField dF = (DefField) dElement;
            if( dF.getType().equals( "char" ) )
                losDatos.setValue( dElement.getName(), dF.getDefaultValue() );
            else if( dF.getType().equals( "num" ) || dF.getType().equals( "int" ) )
                losDatos.setValue( dElement.getName(), new Integer( "0" + dF.getDefaultValue() ).intValue() );
            else if( dF.getType().equals( "long" ) )
                losDatos.setValue( dElement.getName(), new Long( "0" + dF.getDefaultValue() ).longValue() ); 
            else if( dF.getType().equals( "double" ) )
                losDatos.setValue( dElement.getName(), new Double( "0" + dF.getDefaultValue() ).doubleValue() );  
            else if( dF.getType().equals( "boolean" ) )
                losDatos.setValue( dElement.getName(), dF.getDefaultValue().equals( "true" ) ); 
            else if( dF.getType().equals( "date" ) )
                losDatos.setValue( dElement.getName(), new Date() );
            else if( dF.getType().equals( "amount" ) )
                losDatos.setValue( dElement.getName(), new Amount( 0 ) );             
        }
        else if( dElement instanceof DefRecord ) {
            DefRecord dR = (DefRecord) dElement;
            if( !dR.isMultiple() ) {
                Datos d = new Datos();
                losDatos.setValue( dElement.getName(), d );
                for( int i = 0; i < dR.getElements().size(); i++ ) {
                    initDefElement( dR.getElements().get( i ), d );
                }
            }
            else {
                losDatos.setValue( dElement.getName(), new ArrayList<Datos>() );
            }
        }
    }

    /**
     * asigna valor de tipo Amount al campo de nombre name
     * @param name
     * @param valor
     */
    public void setValue( String name, Amount valor ) {
        datos.put(name, valor);
    }
    
    public boolean isField( String name ) {
        Object o = datos.get( name );
        if( o == null )
            return false;
        else 
            return true;
    }
    
    /**
     * asigna valor de tipo String al campo de nombre name
     * @param name
     * @param unValue
     */
    public void setValue(String name, String unValue) {
        
        // como se asigna un string, vemos si este campo ya tiene una definicio y nos
        // ajustamos a su tipo
        
        try {
            Object o = datos.get( name );
            if( o == null || o instanceof String ) {
                datos.put( name, unValue );
            }
            
            if( o instanceof Integer ) {
                datos.put( name, new Integer( unValue ) );
                return;
            }
            if( o instanceof Long ) {
                datos.put( name, new Long( unValue ) );
                return;
            }        
            
            if( o instanceof Double ) {
                datos.put( name, new Double( unValue ) );
                return;
            }  
            
            if( o instanceof Boolean ) {
                datos.put( name, new Boolean( unValue ) );
                return;
            }
            
            if( o instanceof Date ) {
                return;
            }
        } catch (Exception e) {
            Base.logger.error( "error en asignacion a campo " + name );
            Tools.logStackTrace( Base.logger, e );
        }
        
    }

    /**
     * asigna valor de tipo Integer al campo de nombre name
     * @param name
     * @param value
     */
    public void setValue(String name, int value) {
        String s = Integer.toString(value);   
        Object o  = datos.get(name);
        if( o == null || o.equals("")){
            datos.put( name, value );
        }
        else{            
            asignarString(name,s);
        }        
    }
    

    /**
     * asigna valor de tipo Long al campo de nombre name
     * @param name
     * @param value
     */
    public void setValue(String name, long value) {
        String s = Long.toString(value);        
        Object o  = datos.get(name);
        if( o == null){
            datos.put( name, value );
        }
        else{            
            asignarString(name,s);
        } 
    }
    
    /**
     * asigna valor de tipo Double al campo de nombre name
     * @param name
     * @param value
     */
    public void setValue(String name, double value) {
        String s = Double.toString(value);        
        Object o  = datos.get(name);
        if( o == null){
            datos.put( name, value );
        }
        else{            
            asignarString(name,s);
        } 
    }

    /**
     * asigna valor de tipo Date al campo de nombre name
     * @param name
     * @param dateTime
     */
    public void setValue(String name, Date dateTime) {
        datos.put(name, dateTime); 
    }

    public void setValue( String name, boolean valor ) {
        String s = Boolean.toString(valor);        
        Object o  = datos.get(name);
        if( o == null){
            datos.put( name, valor );
        }
        else{            
            asignarString(name,s);
        } 
    }
    
    /**
     * asigna valor de tipo Datos al campo de nombre name
     * @param name
     * @param unosDatos
     */
    public void setValue(String name, Datos unosDatos) {
        datos.put( name, unosDatos );
    }

    /**
     * asigna valor de tipo ArrayList<Datos> al campo de nombre name
     * @param name
     * @param array
     */
    public void setValue(String name, ArrayList<Datos> array) {
        datos.put( name, array );
    }

    private void asignarString(String name , String value){
        Object o  = datos.get(name);  
        if(o.getClass() == Integer.class){
            try{
                datos.put(name, Integer.parseInt(value));
            }
            catch(Exception e){
                datos.put(name, 0);
            }
        }
        else if(o.getClass() == Long.class){
            try{
                datos.put(name, Long.parseLong(value));
            }
            catch(Exception e){
                datos.put(name, 0L);
            }
        }
        else if(o.getClass() == Double.class){
            try{
                datos.put(name, Double.parseDouble(value));
            }
            catch(Exception e){
                datos.put(name, 0);
            }
        }
        else if(o.getClass() == Boolean.class){
            try{
                datos.put(name, Boolean.parseBoolean(value));
            }
            catch(Exception e){
                datos.put(name, false);
            }
        }
        else if(o.getClass() == String.class){
            try{
                datos.put(name, value);
            }
            catch(Exception e){
                datos.put(name, "");
            }
        }
    }
    /**
     * retorna valor de tipo Amount del campo de nombre name
     * @param name
     * @return
     */
    public Amount getAmountValue( String name ) {
        Object o = datos.get( name );
        if( o == null ) {
            return new Amount( 0 );
        }
        return (Amount) datos.get( name );
    }
    
    /**
     * retorna valor de tipo String del campo de nombre name
     * @param name
     * @return
     */
    public String getStringValue(String name) {
        Object o = datos.get( name );
        if( o == null ) {
            return "";
        }
        return (String) "" + datos.get( name );
    }

    /**
     * retorna valor de tipo int del campo de nombre name
     * @param name
     * @return
     */
    public int getIntValue(String name) {
        Object o = datos.get( name );
        if( o == null || !(o instanceof Integer ) ) {
            return 0;
        }
        
        return ((Integer) (datos.get( name   ) )).intValue();
    }

    /**
     * retorna valor de tipo long del campo de nombre name
     * @param name
     * @return
     */
    public long getLongValue(String name) {
        Object o = datos.get( name );
        if( o == null || !(o instanceof Long ) ) {
            return 0;
        }
        
        return ((Long) (datos.get( name   ) )).longValue();
    }
    
    /**
     * retorna valor de tipo Date del campo de nombre name
     * @param name
     * @return
     */    
    public Date getDateValue( String name ) {
        Object o = datos.get( name );
        if( o == null || !(o instanceof Date ) ) {
            return null;
        }
        
        return ((Date) (datos.get( name   ) ));        
    }
    
    public boolean getBooleanValue( String name ) {
        Object o = datos.get( name );
        if( o == null || !(o instanceof Boolean ) ) {
            return false;
        }
        
        return ((Boolean) (datos.get( name   ) )).booleanValue();     
    }
    
    
    public String getFixedValue( DefRecord defRecord, String name ) {
        Object o = datos.get( name );
        DefField def = defRecord.getDefField( name );
        int len = -1;
        if( def != null )
            len = def.getLen();
        
        if( len > 0 ) { // se generara una salida del largo solicitado dependiendo del tipo de campo
            if( o instanceof String ) {
                String s = (String) datos.get( name );
                return Tools.toFix( s, len );
            }
            else if( o instanceof Long ) {
                long v = ( (Long) datos.get( name ) ).longValue();
                return Tools.editaNumero( v, len );
            }
            else if( o instanceof Integer ) {
                int v = ( (Long) datos.get( name ) ).intValue();  
                return Tools.editaNumero( v, len );                
            }
            else if( o instanceof Boolean ) {
                boolean v = ((Boolean) datos.get( name ) ).booleanValue();
                String s = Boolean.toString( v );
                return Tools.toFix( s, len );                
            }
            else if( o instanceof Double ) {
                double v = ((Double) datos.get( name ) ).doubleValue();
                String s = Double.toHexString( v );
                return Tools.toFix( s, len );
            }      
        }
        
        return (String) "" + datos.get( name );
        
    }
    
    /**
     * retorna valor de tipo ArrayList<Datos> del campo de nombre name
     * @param name
     * @return
     */
    public ArrayList<Datos> getArrayList(String name) {
        Object o = datos.get( name );
        if( o == null || !(o instanceof ArrayList ) ) {
            return null;
        }
        return (ArrayList) o;
    }

    /**
     * retorna valor de tipo Datos del campo de nombre name
     * @param name
     * @return
     */
    public Datos getDatos(String name) {
        Object o = datos.get( name );
        if( o == null || !(o instanceof Datos ) ) {
            return null;
        }
        return (Datos) o;
    }
    
    /**
     * Funcion que imprime los distintos campos y valores existentes en la estructura Datos
     * @param msg Titulo de la estructura a ser mostrada
     */
    public void show( String msg ) {
       Base.logger.info( msg );
       Set<String> llaves = datos.keySet();
       String[] ss = new String[datos.size()];
       llaves.toArray( ss );
       for( int i = 0; i < ss.length; i++ ) {
           Object o = datos.get( ss[i] );
           if( o instanceof ArrayList ) {
               ArrayList<Datos> array = (ArrayList<Datos>) o;
               for( int j = 0; j < array.size(); j++ )
                   array.get( j ).show( ss[i] );
           }
           else if( o instanceof Datos ) {
               Datos d = (Datos) o;
               d.show( ss[i] );
           }
           else
               Base.logger.info( "Campo " + ss[i] + " valor " + getStringValue( ss[i]) );
       }
    }
    
    public String showDatos( String msg ) {
        String s = "";
        Set<String> llaves = datos.keySet();
        String[] ss = new String[datos.size()];
        llaves.toArray( ss );
        for( int i = 0; i < ss.length; i++ ) {
            Object o = datos.get( ss[i] );
            if( o instanceof ArrayList ) {
                ArrayList<Datos> array = (ArrayList<Datos>) o;
                for( int j = 0; j < array.size(); j++ )
                    array.get( j ).show( ss[i] );
            }
            else if( o instanceof Datos ) {
                Datos d = (Datos) o;
                d.show( ss[i] );
            }
            else
                s =  s + ss[i] + ": " + getStringValue( ss[i]) +"\n";
        }
        return s;
     }
    
    public void asignaPorNombre( Datos origen ) {
        
        /**
         * se recorren los elementos del objeto y para cada campo se copia (si existe) el correspondiente campo de origen
         */
        
        Set<String> llaves = datos.keySet();
        String[] nombres = new String[datos.size()];
        llaves.toArray( nombres );
        
        for( int i = 0; i < llaves.size(); i++ ) {
            if( !origen.isField( nombres[i] ) )
                continue;
            Object o = datos.get( nombres[i] );
            if( o instanceof String ) {
                String s = origen.getStringValue( nombres[i] );
                asignarString( nombres[i], s );
            }
            else if( o instanceof Integer ) {
                String v = origen.getStringValue( nombres[i] );
                asignarString( nombres[i], v );
            }
            else if( o instanceof Long ) {
                String v = origen.getStringValue( nombres[i] );
                asignarString( nombres[i], v );
            } 
            else if( o instanceof Boolean ) {
                String v = origen.getStringValue( nombres[i] );
                asignarString( nombres[i], v );
            } 
            else if( o instanceof Amount ) {
                String v = origen.getStringValue( nombres[i] );
                asignarString( nombres[i], v );
            }   
        }
    }

    public HashMap<String, Object> getDatos() {
        return datos;
    }
}
