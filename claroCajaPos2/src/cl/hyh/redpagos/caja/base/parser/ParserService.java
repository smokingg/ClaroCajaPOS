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
 * Esta clase analiza un xml que define Servicios. Y se agregan a una tabla de definiciones
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserService extends Parser{
        
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
        
        NodeList nl = docEle.getElementsByTagName("service");
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                Element el = (Element)nl.item(i);
                DefServicio e = getService(el);
                if(!existServicio(e)){
                    Base.tablaServicios.add(e);
                }
            }
        }
    }
    private boolean existServicio(DefServicio serv){
        boolean existe = false;
        for(int i = 0; i < Base.tablaServicios.size(); i++){
            if(Base.tablaServicios.get(i).getName().equals(serv.getName())){
                Base.logger.warn("Servicio ya existe: " + serv.getName() );
                existe = true;
            }
        }
        return existe;
    }
    private DefServicio getService(Element servEl) throws ParserException {
        
        DefServicio def = new DefServicio();
        Element el;
        boolean hasName = false;
        boolean hasType = false;
        boolean hasClass = false;
        boolean hasHost = false;
        boolean hasAppl = false;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
            hasName = true;
        }
        if(!servEl.getAttribute("type").equals("")){
            def.setType((servEl.getAttribute("type")));
            hasType = true;
        }        
        if(!servEl.getAttribute("timeout").equals("")){
            def.setTimeout(Integer.parseInt(servEl.getAttribute("timeout")));
        }
        if(!servEl.getAttribute("class").equals("")){
            def.setClassName(servEl.getAttribute("class"));
            hasClass = true;
        }
        if(!servEl.getAttribute("host").equals("")){
            def.setHost(servEl.getAttribute("host"));
            hasHost = true;
        }
        if(!servEl.getAttribute("saf").equals("")){
            def.setSaf(servEl.getAttribute("saf"));
        }
        if(!servEl.getAttribute("cola").equals("")){
            def.setCola(servEl.getAttribute("cola"));
        }
        if(!servEl.getAttribute("appl").equals("")){
            def.setAppl(servEl.getAttribute("appl"));
            hasAppl = true;
        }
        if(!hasName || !hasType || !hasClass || !hasHost || !hasAppl){
            throw new ParserException("Falta nombre, tipo, clase, host o appl");
        }
        
        NodeList nl = servEl.getChildNodes();
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("input")){
                    el = (Element)nl.item(i);
                    def.setInputRecordDef(getRecord(el,def.getName(),""));
                }
                else if(nl.item(i).getNodeName().equals("output")){
                    el = (Element)nl.item(i);
                    def.setOutputRecordDef(getRecord(el,def.getName(),""));
                }
            }
        }
        return def;
    }

    private DefRecord getRecord(Element servEl,String name,String type) throws ParserException {
        
        DefRecord def = new DefRecord();
        Element el;

        if(name.equals("") && type.equals("")){
            if(!servEl.getAttribute("name").equals("")){
                def.setName(servEl.getAttribute("name"));
            }
            if(!servEl.getAttribute("multiple").equals("")){
                if(servEl.getAttribute("multiple").equals("yes")){
                    def.setMultiple(true);
                }
                else{
                    def.setMultiple(false);
                }
            }
        }
        else{
            def.setName(type+name);
            def.setMultiple(false);
        }
        
        NodeList nl = servEl.getChildNodes();
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("record")){
                    el = (Element)nl.item(i);
                    DefRecord e = getRecord(el,"","");
                    if(!existElement(e,def)){
                        def.add(e);
                    }
                }
                else if(nl.item(i).getNodeName().equals("copy")){
                    el = (Element)nl.item(i);
                    DefRecord e = copyRecord(el);
                    if(e != null){
                        if(!existElement(e,def)){
                            def.add(e);
                        }
                    }
                }
                else if(nl.item(i).getNodeName().equals("field")){
                    el = (Element)nl.item(i);
                    DefField e = getField(el);
                    if(!existElement(e,def)){
                        def.add(e);
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
    private DefRecord copyRecord(Element servEl) throws ParserException{
        String record = "";
        DefRecord e = null;
        
        if(!servEl.getAttribute("name").equals("")){
            record = servEl.getAttribute("name");
        }
        for(int i = 0; i < Base.tablaRecords.size(); i++){
            if(Base.tablaRecords.get(i).getName().equals(record)){
                e = Base.tablaRecords.get(i);
            }
        }
        if(e == null){
            Base.logger.error("No se encontró el record: '" + record + "' en la tabla de Records");
            throw new ParserException("No se encontró el record: '" + record + "' en la tabla de Records");
        }
        return e;
    }
}
