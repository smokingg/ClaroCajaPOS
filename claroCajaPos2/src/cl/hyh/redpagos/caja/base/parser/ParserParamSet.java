package cl.hyh.redpagos.caja.base.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Esta clase analiza un xml que define ParamSets. Y se agregan a una tabla de definiciones
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserParamSet extends Parser {
    
    public void parseXml(String fname) throws ParserException{

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ClassLoader cl = this.getClass().getClassLoader();
        
        InputStream in = null;
        try {
            in = cl.getResource(fname).openStream();
        } catch (IOException e) {
            Tools.logStackTrace(Base.logger, e);
            throw new ParserException(e.toString());
        }
        
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(in);
            parseDocument( dom );
            in.close();
        }catch(ParserConfigurationException pce) {
            Tools.logStackTrace(Base.logger, pce);
            throw new ParserException(pce.toString());
        }catch(SAXException se) {
            Tools.logStackTrace(Base.logger, se);
            throw new ParserException(se.toString());
        }catch(IOException ioe) {
            Tools.logStackTrace(Base.logger, ioe);
            throw new ParserException(ioe.toString());
        }
    }
    
    public void parseDocument(Document dom) throws ParserException{
        Element docEle = dom.getDocumentElement();
        
        NodeList nl = docEle.getChildNodes();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("paramSet")){
                    Element el = (Element)nl.item(i);
                    DefParamSet e = getParam(el);
                    if(!existParam(e)){
                        Base.defParamSets.add(e);
                    }
                }
            }
        }
    }
    private boolean existParam(DefParamSet serv){
        boolean existe = false;
        for(int i = 0; i < Base.defParamSets.size(); i++){
            if(Base.defParamSets.get(i).getName().equals(serv.getName())){
                Base.logger.warn("Param ya existe: " + serv.getName() );
                existe = true;
            }
        }
        return existe;
    }
    private DefParamSet getParam(Element servEl) throws ParserException {
        
        DefParamSet def = new DefParamSet();
        Element el;
        boolean hasName = false;
        boolean hasType = false;
        boolean hasFName = false;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
            def.getDefRecord().setName(def.getName()+"Param");
            hasName = true;
        }
        if(!servEl.getAttribute("type").equals("")){
            def.setType(servEl.getAttribute("type"));
            hasType = true;
        }
        if(!servEl.getAttribute("fName").equals("")){
            def.setFName(servEl.getAttribute("fName"));
            hasFName = true;
        }
        if(!hasName || !hasType || !hasFName){
            throw new ParserException("Falta nombre o tipo");
        }

        NodeList nl = servEl.getChildNodes();

        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("field")){
                    el = (Element)nl.item(i);
                    DefField e = getField(el);
                    if(!existElement(e,def.getDefRecord())){
                        def.getDefRecord().add(e);
                    }
                }
            }
        }
        return def;
    }
    
    private boolean existElement(DefElement ele , DefRecord owner ){
        boolean existe = false;
        for(int i = 0; i < owner.getElements().size(); i++){
            if(owner.getElements().get(i).getName().equals(ele.getName())){
                Base.logger.warn("Elemento : '"+ ele.getName() + "' ya existe en : '"+owner.getName()+"'");
                existe = true;
                break;
            }
        }
        return existe;
    }

}
