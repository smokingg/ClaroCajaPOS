package cl.hyh.redpagos.caja.base.parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
 * Esta clase analiza un xml que define que archivos iniciales de definiciones
 * deben ser cargados en la aplicacion. Ademas se encarga de cargar estos archivos segun el orden 
 * establecido en el archivo xml inicial.
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserPosConfiguration {
    
    /**
     * Inicia el proceso de parseo del archivo de configuracion inicial.
     * @param file 
     */
    public void parsePosConfiguration(String file){
        parseXmlFile(file);       
        
        ArrayList<DefDocumentoPago> d = Base.tablaDocumentos;
        ArrayList<DefMedioPago> f = Base.tablaMediosPago;
        ArrayList<DefRecord> g = Base.tablaRecords;
        ArrayList<DefServicio> h = Base.tablaServicios;
        ArrayList<DefParamSet> i = Base.defParamSets;
        ArrayList<DefVoucher> j = Base.tablaVouchers;
        Base.logger.info("Cargados: " + d.size() + " DocumentosPagos");
        Base.logger.info("Cargados: " + f.size() + " MedioPagos");
        Base.logger.info("Cargados: " + g.size() + " Records");
        Base.logger.info("Cargados: " + h.size() + " Servicios");
        Base.logger.info("Cargados: " + i.size() + " ParamSets");
        Base.logger.info("Cargados: " + j.size() + " Vouchers");
        
    }

    private void parseXmlFile(String file){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ClassLoader cl = this.getClass().getClassLoader();

        InputStream in = null;
        try {
            in = cl.getResource(file).openStream();
        } catch (IOException e) {
            Tools.logStackTrace(Base.logger, e);
        }

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(in);
            parseDocument(dom);
            in.close();
        }catch(ParserConfigurationException pce) {
            Tools.logStackTrace(Base.logger, pce);
        }catch(SAXException se) {
            Tools.logStackTrace(Base.logger, se);
        }catch(IOException ioe) {
            Tools.logStackTrace(Base.logger, ioe);
        }
    }

    private void parseDocument(Document dom){
        Element docEle = dom.getDocumentElement();

        NodeList nl = docEle.getElementsByTagName("include");
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++){
                Element el = (Element)nl.item(i);
                loadXML(el);
            }
        }
    }
    private void loadXML(Element servEl) {
        String file="";
        String parser="";

        if(!servEl.getAttribute("file").equals("")){
            file = servEl.getAttribute("file");
        }
        if(!servEl.getAttribute("parser").equals("")){
            parser = servEl.getAttribute("parser");
        }
        Parser p;
        try {
            p = (Parser) Class.forName(parser).newInstance();
            p.parseXml(file);
        } catch (InstantiationException e) {
            Base.logger.error(e);
        } catch (IllegalAccessException e) {
            Base.logger.error(e);
        } catch (ClassNotFoundException e) {
            Base.logger.error(e);
        } catch (ParserException e) {
            Tools.logStackTrace(Base.logger, e);
        }
        return;
    }
}
