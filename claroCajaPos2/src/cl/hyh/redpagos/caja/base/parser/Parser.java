package cl.hyh.redpagos.caja.base.parser;

import org.w3c.dom.Element;

import cl.hyh.redpagos.caja.base.Base;

/**
 * Clase de la que heredan los analizadores.
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Parser {

    /**
     * Funcion principal de los analizadores que se encarga de procesar y analizar los archivos xml
     * @param fName Nombre del archivo a ser analizado
     * @throws ParserException Arroja una excepcion de analisis hacia el objeto que utiliza esta funcion.
     */
    public void parseXml( String fName )throws ParserException{};
   
    /**
     * Funcion general para los distintos analizadores que retorna una definicion de un Field a partir de 
     * un tag Field en los archivos xml.
     * @param servEl Elemento del objeto DOM generado a partir del archivo xml.
     * @return
     * @throws ParserException Arroja una excepcion de analisis hacia el objeto que utiliza esta funcion.
     */
    protected DefField getField(Element servEl) throws ParserException{
        DefField def = new DefField();
        boolean hasName = false;
        boolean hasType = false;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
            hasName = true;
        }
        if(!servEl.getAttribute("len").equals("")){
            def.setLen( new Integer( servEl.getAttribute("name") ) .intValue() );
        }
        
        if(!servEl.getAttribute("type").equals("")){
            if(servEl.getAttribute("type").equals("char") || servEl.getAttribute("type").equals("long") ||
                    servEl.getAttribute("type").equals("int") || servEl.getAttribute("type").equals("date") || 
                    servEl.getAttribute("type").equals("double") || servEl.getAttribute("type").equals("boolean") ||
                    servEl.getAttribute("type").equals("num") || servEl.getAttribute("type").equals("monto")){
                def.setType((servEl.getAttribute("type")));
                hasType = true;
            }
            else{
                Base.logger.error("Tipo de Field no válido: '"+ servEl.getAttribute("type") +"'");
            }
        }
        if(!hasName || !hasType){
            throw new ParserException("Falta nombre o tipo");
        }
        if(servEl.getAttribute("transmit") != ""){
            if(servEl.getAttribute("transmit").equals("no")){
                def.setTransmit(false);
            }            
        }
        if(servEl.getAttribute("default") != ""){
            def.setDefaultValue(servEl.getAttribute("default"));           
        }
        return def;
    }
}
