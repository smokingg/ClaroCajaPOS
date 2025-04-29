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
import cl.hyh.redpagos.caja.base.PlanCuotas;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Esta clase analiza un xml que define Medios de Pago. Y se agregan a una tabla de definiciones
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserPayment extends Parser {
    
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
                if(nl.item(i).getNodeName().equals("medioPago")){
                    Element el = (Element)nl.item(i);
                    DefMedioPago e = getPago(el);
                    if(!existPayment(e)){
                        Base.tablaMediosPago.add(e);
                    }
                }
            }
        }
    }
    
    private boolean existPayment(DefMedioPago serv){
        boolean existe = false;
        for(int i = 0; i < Base.tablaMediosPago.size(); i++){
            if(Base.tablaMediosPago.get(i).getName().equals(serv.getName())){
                Base.logger.warn("Medio de Pago ya existe: " + serv.getName() );
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    private DefMedioPago getPago(Element servEl) throws ParserException {
        
        DefMedioPago def = new DefMedioPago();
        Element el;
        boolean hasName = false;
        boolean hasClass = false;
        
        if(!servEl.getAttribute("name").equals("")){
            def.setName(servEl.getAttribute("name"));
            def.getRecordDef().setName(def.getName()+"Payment");
            hasName = true;
        }
        if(!servEl.getAttribute("class").equals("")){
            def.setClassName((servEl.getAttribute("class")));
            hasClass = true;
        }
        if(!hasName || !hasClass){
            throw new ParserException("Falta nombre o class");
        }
        if(!servEl.getAttribute("customerVoucher").equals("")){
            def.setCustomerVoucher(servEl.getAttribute("customerVoucher"));
        }
        if(!servEl.getAttribute("commerceVoucher").equals("")){
            def.setComerceVoucher(servEl.getAttribute("commerceVoucher"));
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
                else if(nl.item(i).getNodeName().equals("planesCuotas")){
                    el = (Element)nl.item(i);
                    NodeList nle = el.getChildNodes();
                    PlanCuotas d = null;
                    Element ele = null;

                    if(nl != null && nle.getLength() > 0) {
                        for(int j = 0; j < nle.getLength(); j++) {
                            if(nle.item(j).getNodeName().equals("planCuotas")){
                                ele = (Element)nle.item(j);
                                d = getPlan(ele);
                                if(d != null ){
                                    def.getPlanesCuotas().add(d);
                                }                               
                            }
                        }
                    }                                
                   
                    else{
                        Base.logger.error("Error al cargar el plan de cuota");
                        throw new ParserException("Error al cargar el plan de cuota");
                    }
                }
            }
        }
        return def;
    }
    private PlanCuotas getPlan(Element servEl) throws ParserException {
        
        PlanCuotas def = new PlanCuotas();
        boolean hasName = false;
        boolean hasTieneCuotas = false;
        boolean hasMontoMinimo = false;
        boolean hasMontoMaximo = false;
        boolean hasMarcas = false;
        
        try{
            if(!servEl.getAttribute("name").equals("")){
                def.setName(servEl.getAttribute("name"));
                hasName = true;
            }
            if(!servEl.getAttribute("tieneCuotas").equals("")){
                def.setTieneCuotas(Boolean.parseBoolean(servEl.getAttribute("tieneCuotas")));
                hasTieneCuotas = true;
            }
            if(!servEl.getAttribute("montoMinimo").equals("")){
                def.setMontoMinimo(Long.parseLong(servEl.getAttribute("montoMinimo")));
                hasMontoMinimo = true;
            }
            if(!servEl.getAttribute("montoMaximo").equals("")){
                def.setMontoMinimo(Long.parseLong(servEl.getAttribute("montoMaximo")));
                hasMontoMaximo = true;
            }
            if(!servEl.getAttribute("cuotas").equals("")){
                String []s = servEl.getAttribute("cuotas").split(",");
                int []cuotas = null;
                boolean []hasCuotas = new boolean [100];
                String []sub = null;  
                
                for(int i = 0; i < hasCuotas.length; i++){
                    hasCuotas[i] = false;
                }
                for(int i = 0; i < s.length; i++){
                    if(s[i].contains("-")){
                        sub = s[i].split("-");
                        int tam = Integer.parseInt(sub[1])-Integer.parseInt(sub[0]) + 1;
                        int ini = Integer.parseInt(sub[0]);
                        for(int j = 0; j < tam; j++){
                            hasCuotas[ini] = true;
                            ini++;
                        }
                    }
                    else{
                        hasCuotas[Integer.parseInt(s[i])] = true;
                    }
                }
                int size = 0;
                for(int i = 0; i < hasCuotas.length; i++){
                    if(hasCuotas[i] == true){
                        size++;
                    }
                }
                cuotas = new int[size];
                int pos = 0;
                for(int i = 0; i < hasCuotas.length; i++){
                    if(hasCuotas[i] == true){
                        cuotas[pos] = i;
                        pos++;
                    }
                }
                def.setCuotas(cuotas);
            }
            if(!servEl.getAttribute("marcas").equals("")){
                String str = servEl.getAttribute("marcas");
                def.setMarcas(str.split(","));
                hasMarcas = true;
            }
            if(!servEl.getAttribute("tipoCredito").equals("")){
                String str = servEl.getAttribute("tipoCredito");
                def.setTipoCredito( new Integer( str ).intValue() );
            }
            
            if(!hasName || !hasTieneCuotas || !hasMontoMinimo || !hasMontoMaximo ){
                throw new ParserException("Faltan parametros en el plan de cuotas");
            }
        }
        catch(Exception e){
            throw new ParserException("Tipo de dato incorrecto");
        }
        return def;
    }
    private boolean existElement(DefElement ele , DefRecord owner ){
        boolean existe = false;
        for(int i = 0; i < owner.getElements().size(); i++){
            if(owner.getElements().get(i).getName().equals(ele.getName())){
                Base.logger.warn("Elemento : '"+ ele.getName() + "' ya existe en : '"+owner.getName()+"'");
                existe = true;
            }
        }
        return existe;
    }

}
