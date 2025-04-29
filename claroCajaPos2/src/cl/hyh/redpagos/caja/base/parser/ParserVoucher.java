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
 * Esta clase analiza un xml que define Vouchers. Y se agregan a una tabla de definiciones
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserVoucher extends Parser {
    
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
        
        NodeList nl = docEle.getElementsByTagName("voucher");
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                Element el = (Element)nl.item(i);
                DefVoucher e = getVoucher(el);
                if(!existVoucher(e)){
                    Base.tablaVouchers.add(e);
                }
            }
        }
    }
    private boolean existVoucher(DefVoucher serv){
        boolean existe = false;
        for(int i = 0; i < Base.tablaVouchers.size(); i++){
            if(Base.tablaVouchers.get(i).getName().equals(serv.getName())){
                Base.logger.warn("Voucher ya existe: " + serv.getName() );
                existe = true;
            }
        }
        return existe;
    }
    private DefVoucher getVoucher(Element servEl) throws ParserException {
        
        DefVoucher def = new DefVoucher();
        Element el;
        boolean hasName = false;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
            hasName = true;
        }
        if(!hasName){
            throw new ParserException("Falta nombre");
        }
        
        NodeList nl = servEl.getChildNodes();
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("line")){
                    el = (Element)nl.item(i);
                    def.getDefLines().add(getVoucherLine(el));
                }
            }
        }
        return def;
    }

    private DefVoucherLine getVoucherLine(Element servEl) {
        
        DefVoucherLine def = new DefVoucherLine();
        Element el;

        if(!servEl.getAttribute("center").equals("")){
            if(servEl.getAttribute("center").equals("yes")){
                def.setCenter(true);
            }
        }
        if(!servEl.getAttribute("small").equals("")){
            if(servEl.getAttribute("small").equals("yes")){
                def.setSmall(true);
            }
        }
        if(!servEl.getAttribute("bold").equals("")){
            if(servEl.getAttribute("bold").equals("yes")){
                def.setBold(true);
            }
        }
        if(!servEl.getAttribute("underline").equals("")){
            if(servEl.getAttribute("underline").equals("yes")){
                def.setUnderline(true);
            }
        }
        if(!servEl.getAttribute("right").equals("")){
            if(servEl.getAttribute("right").equals("yes")){
                def.setRight(true);
            }
        }
        if(!servEl.getAttribute("caps").equals("")){
            if(servEl.getAttribute("caps").equals("yes")){
                def.setCaps(true);
            }
        }
        if(!servEl.getAttribute("condField").equals("")){
            def.setCondField(servEl.getAttribute("condField"));
        }
        if(!servEl.getAttribute("value").equals("")){
            def.setValue(servEl.getAttribute("value"));
        }
        if(!servEl.getAttribute("type").equals("")){
            def.setType(servEl.getAttribute("type"));
        }
        if(!servEl.getAttribute("cond").equals("")){
            if(servEl.getAttribute("cond").equals("true")){
                def.setCond(true);
            }
            else{
                def.setCond(false);
            }
        }
        if(!servEl.getAttribute("include").equals("")){
            def.setInclude(servEl.getAttribute("include"));
        }
        
        NodeList nl = servEl.getChildNodes();
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getNodeName().equals("print")){
                    el = (Element)nl.item(i);
                    DefVoucherPrint e = getPrint(el);
                    def.getDefPrints().add(e);
                }
            }
        }
        return def;
    }
    private DefVoucherPrint getPrint(Element servEl){
        DefVoucherPrint def = new DefVoucherPrint();
        
        if(!servEl.getAttribute("value").equals("")){
            def.setValue(servEl.getAttribute("value"));
        }
        if(!servEl.getAttribute("field").equals("")){
            def.setField(servEl.getAttribute("field"));
        }
        if(!servEl.getAttribute("format").equals("")){
            def.setFormat(servEl.getAttribute("format"));
        }
        if(!servEl.getAttribute("length").equals("")){
            def.setLength(Integer.parseInt(servEl.getAttribute("length")));
        }
        if(!servEl.getAttribute("rFill").equals("")){
            def.setRFill(servEl.getAttribute("rFill"));
        }
        if(!servEl.getAttribute("lFill").equals("")){
            def.setLFill(servEl.getAttribute("lFill"));
        }
        return def;
    }
}
