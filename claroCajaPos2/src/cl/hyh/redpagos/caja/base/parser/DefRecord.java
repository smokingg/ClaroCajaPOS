package cl.hyh.redpagos.caja.base.parser;

import java.util.ArrayList;

/**
 * Clase que define la estructura de los Records
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DefRecord extends DefElement {

    private ArrayList<DefElement> elements;
    private boolean multiple;
    
    /**
     * Constructor de las definiciones de los Records
     * 
     */
    public DefRecord(){
        elements = new ArrayList<DefElement>();
        multiple = false;
    }
    /**
     * Agrega un elemento al arreglo de elementos
     * @param e
     */
    public void add(DefElement e ){
        elements.add(e);
    }
    /**
     * Retorna la variable multiple
     * @return
     */
    public boolean isMultiple() {
        return multiple;
    }
    /**
     * Setea la variable multiple
     * @param emultiple
     */
    public void setMultiple(boolean emultiple) {
        this.multiple = emultiple;
    }
    /**
     * Retorna el arreglo de elementos
     * @return
     */
    public ArrayList<DefElement> getElements() {
        return elements;
    }
    /**
     * Setea el arreglo de elementos
     * @param eelements
     */
    public void setElements(ArrayList<DefElement> eelements) {
        this.elements = eelements;
    }
    
    public DefField getDefField( String campo ) {
        
        for( int i = 0; i < elements.size(); i++ ) {
            DefElement def = elements.get( i );
            if( def.name.equals( campo ) && def instanceof DefField )
                return (DefField) def;
        }
        
        return null;
        
    }    
    
}
