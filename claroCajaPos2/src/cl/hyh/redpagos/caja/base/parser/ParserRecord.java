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
 * Esta clase analiza un xml que define Records . Y se agregan a una tabla de definiciones
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserRecord extends Parser{
    
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
    
    private void parseDocument(Document dom) throws ParserException{
        Element docEle = dom.getDocumentElement();
        
        NodeList nl = docEle.getChildNodes();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("record")){
                    Element el = (Element)nl.item(i);
                    DefRecord e = getRecord(el);
                    if(!existRecord(e)){
                        Base.tablaRecords.add(e);
                    }
                }
            }
        }
    }
    private boolean existRecord(DefRecord serv){
        boolean existe = false;
        for(int i = 0; i < Base.tablaRecords.size(); i++){
            if(Base.tablaRecords.get(i).getName().equals(serv.getName())){
                Base.logger.warn("Record ya existe: " + serv.getName() );
                existe = true;
            }
        }
        return existe;
    }
    private DefRecord getRecord(Element servEl) throws ParserException {
        
        DefRecord def = new DefRecord();
        Element el;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
        }
        if(servEl.getAttribute("multiple").equals("")){
            if(servEl.getAttribute("multiple").equals("")){
                def.setMultiple(true);
            }
        }

        NodeList nl = servEl.getChildNodes();

        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("field")){
                    el = (Element)nl.item(i);
                    DefField e = getField(el);
                    if(!existElement(e,def)){
                        def.add(e);
                    }
                }
                else if(nl.item(i).getNodeName().equals("record")){
                    el = (Element)nl.item(i);
                    DefRecord d = null;
                    if(!el.getAttribute("include").equals("")){
                        d = searchRecord(el);
                    }
                    else{
                        d = getRecord(el);
                    }
                    if(d != null ){
                        if(!existElement(d,def)){
                            def.add(d);
                        }
                    }
                    else{
                        Base.logger.warn("Record no encontrado : '" + el.getAttribute("include") + "'");
                    }
                }
            }
        }
        return def;
    }
    private DefRecord searchRecord(Element el){
        DefRecord e = null;
        for(int i = 0; i < Base.tablaRecords.size(); i++){
            if(Base.tablaRecords.get(i).getName().equals(el.getAttribute("include"))){
                e = Base.tablaRecords.get(i);
                break;
            }
        }
        return e;
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
