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
 * Esta clase analiza un xml que define Documentos de Pago. Y se agregan a una tabla de definiciones
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserDocument extends Parser {

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
    
    private void parseDocument( Document dom ) throws ParserException{
        Element docEle = dom.getDocumentElement();
        
        NodeList nl = docEle.getChildNodes();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("document")){
                    Element el = (Element)nl.item(i);
                    DefDocumentoPago e = getDocumento(el);
                    if(!existDocument(e)){
                        Base.tablaDocumentos.add(e);
                    }
                }
            }
        }
    }
    private boolean existDocument(DefDocumentoPago serv){
        boolean existe = false;
        for(int i = 0; i < Base.tablaDocumentos.size(); i++){
            if(Base.tablaDocumentos.get(i).getName().equals(serv.getName())){
                Base.logger.warn("Documento ya existe: " + serv.getName() );
                existe = true;
                break;
            }
        }
        return existe;
    }
    private DefDocumentoPago getDocumento(Element servEl) throws ParserException {
        
        DefDocumentoPago def = new DefDocumentoPago();
        Element el;
        boolean hasName = false;
        boolean hasClass = false;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
            def.getRecordDef().setName(def.getName()+"Document");
            hasName = true;
        }
        if(!servEl.getAttribute("class").equals("")){
            def.setClassName((servEl.getAttribute("class")));
            hasClass = true;
        }
        if(!hasName || !hasClass){
            throw new ParserException("Falta nombre o clase");
        }
        if(!servEl.getAttribute("customerVoucher").equals("")){
            def.setCustomerVoucher(servEl.getAttribute("customerVoucher"));
        }
        if(!servEl.getAttribute("empresa").equals("")){
            def.setEmpresa(servEl.getAttribute("empresa"));
        }
        if(!servEl.getAttribute("reversable").equals("")){
            def.setReversable(Boolean.parseBoolean(servEl.getAttribute("reversable")));
        }
        if(!servEl.getAttribute("commerceVoucher").equals("")){
            def.setComerceVoucher(servEl.getAttribute("commerceVoucher"));
        }
        if(!servEl.getAttribute("mediosPago").equals("")){
            def.setMediosPago(servEl.getAttribute("mediosPago"));
        }

        NodeList nl = servEl.getChildNodes();

        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("field")){
                    el = (Element)nl.item(i);
                    DefField e = getField(el);
                    if(!existElement(e,def.getRecordDef())){
                        def.getRecordDef().add(e);
                    }
                }
                else if(nl.item(i).getNodeName().equals("record")){
                    el = (Element)nl.item(i);
                    DefRecord d = getRecord(el);
                    if(d != null ){
                        if(!existElement(d,def.getRecordDef())){
                            def.getRecordDef().add(d);
                        }
                    }
                    else{
                        Base.logger.error("Error al cargar el Record");
                        throw new ParserException("Error al cargar el Record");
                    }
                }
                else if(nl.item(i).getNodeName().equals("copy")){
                    el = (Element)nl.item(i);
                    DefRecord d = copyRecord(el);
                    if(d != null ){
                        if(!existElement(d,def.getRecordDef())){
                            def.getRecordDef().add(d);
                        }
                    }
                    else{
                        Base.logger.error("Error al cargar el Record");
                        throw new ParserException("Error al cargar el Record");
                    }
                }
            }
        }
        return def;
    }
    private DefRecord getRecord(Element servEl) throws ParserException {
        
        DefRecord def = new DefRecord();
        Element el;

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
        
        NodeList nl = servEl.getChildNodes();
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("record")){
                    el = (Element)nl.item(i);
                    DefRecord e = getRecord(el);
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
    private DefRecord copyRecord(Element servEl) throws ParserException{
        String record = "";
        DefRecord e = null;
        
        if(!servEl.getAttribute("name").equals("")){
            record = servEl.getAttribute("name");
        }
        for(int i = 0; i < Base.tablaRecords.size(); i++){
            if(Base.tablaRecords.get(i).getName().equals(record)){
                e = Base.tablaRecords.get(i);
                break;
            }
        }
        if(e == null){
            Base.logger.error("No se encontró el record: '" + record + "' en la tabla de Records");
            throw new ParserException("No se encontró el record: '" + record + "' en la tabla de Records");
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
