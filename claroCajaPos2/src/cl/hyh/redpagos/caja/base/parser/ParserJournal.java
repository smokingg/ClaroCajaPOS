package cl.hyh.redpagos.caja.base.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.hyh.redpagos.caja.base.Amount;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.Oper;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.ServicioJournalDetalle;
import cl.hyh.redpagos.caja.base.Tools;

/**
 * Esta clase se encarga de transformar un mensaje Soap a su correspondiente estructura de Datos.
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ParserJournal {    
    DefServicio serv;
    Servicio servicio;
    String xml;
    String name;
    Datos datos;
    ArrayList<Datos> arrayDatos;
    Oper oper;
    
    /**
     * Constructor del analizador Soap que procesara el mensaje Soap.
     * @param response Mensaje Soap a ser procesado
     * @param s Definicion del servicio al que corresponde el mensaje a ser procesado
     * @throws BaseException 
     */
    public ParserJournal( String response, Servicio s ) throws BaseException {
        serv = Base.getDefServicio( s.getNombreServicio() );
        servicio = s;
        if( serv == null ) {
            throw new BaseException( "servicio " + s.getNombreServicio() + " indefinido" );
        }        
        xml = response;
    }
    
    /**
     * Funcion inicial encargada de procesar el mensaje Soap y transformarlo a estructuras Datos
     * @return Retorna la estructura Datos con los datos provenientes del mensaje Soap.
     * @throws BaseException Arroja una excepcion de analisis hacia el objeto que utiliza esta funcion.
     */
    public Oper parseXml() throws BaseException{

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(new ByteArrayInputStream(xml.getBytes("UTF8")));
            return parseDocument( dom );
        }catch(ParserConfigurationException pce) {
            Tools.logStackTrace(Base.logger, pce);
            throw new BaseException(pce.toString());
        }catch(SAXException se) {
            Tools.logStackTrace(Base.logger, se);
            throw new BaseException(se.toString());
        }catch(IOException ioe) {
            Tools.logStackTrace(Base.logger, ioe);
            throw new BaseException(ioe.toString());
        }      

    }

    private Oper parseDocument(Document dom) throws BaseException{
        Element docEle = dom.getDocumentElement();
        
        String tag = "ConsultaResponse";
        
        NodeList nl = docEle.getElementsByTagNameNS( "http://www.example.org/IFOperacion/" , tag );
        
        if( nl == null || nl.getLength() <= 0 )
            throw new BaseException( "falta tag " + tag + " en servicio " + servicio.getNombreServicio() );
        Element el = (Element) nl.item( 0 );
        
        // buscamos el HeaderOut en el
        
        NodeList nl1 = el.getElementsByTagName( "HeaderOut" );
        if( nl1 == null || nl1.getLength() <= 0 )
            throw new BaseException( "falta tag HeaderOut en servicio " + servicio.getNombreServicio() );
        servicio.setHeaderOut( armarDatos( (Element) nl1.item( 0 ), Base.getDefRecord( "HeaderOut" ) ) );
        
        nl = el.getElementsByTagName( "DetalleJournal" );
        nl = nl.item(0).getChildNodes();
        
        nl1 = el.getElementsByTagName( "HeaderIn" );
        if( nl1 == null || nl1.getLength() <= 0 )
            throw new BaseException( "falta tag HeaderIn en servicio " + servicio.getNombreServicio() );
        Datos dat = armarDatos( (Element) nl1.item( 0 ), Base.getDefRecord( "HeaderIn"  ) );
        ServicioJournalDetalle serv = (ServicioJournalDetalle)this.servicio;
        serv.setHeaderInDetalle(dat);
        this.servicio = serv;
        if(dat.getStringValue("TipoOperacion").startsWith("EnvioTrv")){
            oper = new OperTRV();
            oper.setCajero(dat.getStringValue("Cajero"));
            oper.setFechaPago(dat.getStringValue("FechaPago"));
            if(dat.getStringValue("TipoOperacion").equals("EnvioTrvExt")){
                oper.setAgencia(dat.getStringValue("Agencia"));
                oper.setEntidad(dat.getStringValue("Entidad"));
            }
        }
        else{
            oper = new OperAdmin();
            oper.setCajero(dat.getStringValue("Cajero"));
            oper.setFechaPago(dat.getStringValue("FechaPago"));
        }
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i) == null){
                    continue;
                }
                Node n = nl.item(i);
                el = (Element)nl.item(i);
                
                armarDatos(el);
            }
        }
        return oper;
    }
    private void armarDatos(Element servEl) throws BaseException {
        
        NodeList nl = servEl.getChildNodes();
        Element el;
        DefRecord rec = null;
        Datos data = new Datos();
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                Base.logger.info(nl.item(i).getNodeName());
                if(Base.getDefDocumentoPago(nl.item(i).getNodeName()) != null){
                    String name = nl.item(i).getNodeName();
                    Base.logger.info( name );
                    DefRecord def = Base.getDefDocumentoPago(name).getRecordDef();
                    DocumentoPago doc = FactoryDocumentoPago.makeInstance(name);
                    el = (Element)nl.item(i);
                    doc.setDatos(armarDatos(el , def));
                    ((OperTRV)oper).addDocumentoPago(doc);
                }
                else if(Base.getDefMedioPago(nl.item(i).getNodeName().substring(2)) != null){
                    String name = nl.item(i).getNodeName().substring(2);
                    Base.logger.info( name );
                    DefRecord def = Base.getDefMedioPago(name).getRecordDef();
                    MedioPago med = FactoryMedioPago.makeInstance(name);
                    el = (Element)nl.item(i);
                    med.setDatos(armarDatos(el , def));
                    ((OperTRV)oper).addMedioPago(med);
                }
                else if(Base.getDefServicio(nl.item(i).getNodeName()) != null){
                    String name = nl.item(i).getNodeName();
                    Base.logger.info( name );
                    DefRecord def = Base.getDefServicio(name).getInputRecordDef();
                    el = (Element)nl.item(i);
                    try{
                        ((OperAdmin)oper).setDatos(armarDatos(el,def));
                    }
                    catch(Exception e){
                        ((OperTRV)oper).setDatos(armarDatos(el,def));
                    }
                }
            }
        }
    }
    private Datos armarDatos(Element servEl, DefRecord rec) throws BaseException {
        
        NodeList nl = servEl.getChildNodes();
        Element el;
        Datos data = new Datos(rec);
        
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0; i < nl.getLength(); i++) {
                if(existTag(nl.item(i).getNodeName(),rec) && !isRecord(nl.item(i).getNodeName(),rec)){
                    el = (Element)nl.item(i);
                    try{
                        String value = "";
                        if( el.getFirstChild() != null )
                            value = el.getFirstChild().getNodeValue();
                        if(getType(el.getNodeName(),rec).equals("char")){
                            data.setValue(el.getNodeName(), value);
                        }
                        else if(getType(el.getNodeName(),rec).equals("int")){
                            data.setValue(el.getNodeName(), Integer.parseInt("0" + value));
                        }
                        else if(getType(el.getNodeName(),rec).equals("long")){
                            data.setValue(el.getNodeName(), Long.parseLong("0" + value));
                        }
                        else if(getType(el.getNodeName(),rec).equals("double")){
                            data.setValue(el.getNodeName(), Double.parseDouble("0" + value));
                        }
                        else if(getType(el.getNodeName(),rec).equals("boolean")){
                            data.setValue(el.getNodeName(), Boolean.parseBoolean(value));
                        }
                        else if(getType(el.getNodeName(),rec).equals("date")){
                            data.setValue(el.getNodeName(), value);
                        }
                        else if(getType(el.getNodeName(),rec).equals("amount")){
                            data.setValue(el.getNodeName(), new Amount(Long.parseLong("0" + value)));
                        }
                    }
                    catch(Exception e){
                        throw new BaseException(e,"Excepcion de tipo de datos");
                    }
                }
                else if(existTag(nl.item(i).getNodeName(),rec) && isRecord(nl.item(i).getNodeName(),rec)){
                    DefRecord def = getRecord(nl.item(i).getNodeName(),rec);
                    if(def == null){                        
                        throw new BaseException("No se encontro el Record a cargar");
                    }
                    el = (Element)nl.item(i);
                    if(!def.isMultiple()){
                        data.setValue(nl.item(i).getNodeName(), armarDatos(el,def));
                    }
                    else{
                        data.getArrayList(nl.item(i).getNodeName()).add(armarDatos(el,def));                       
                    }
                }
            }
        }
        return data;
    }
    private boolean existTag(String ename, DefRecord rec){
        boolean existe = false;
        
        for(int i = 0; i < rec.getElements().size(); i++){
            if(rec.getElements().get(i).getName().equals(ename)){
                existe =  true;
            }
        }
        return existe;
    }
    private String getType(String ename, DefRecord rec){
        String str = "";
        
        for(int i = 0; i < rec.getElements().size(); i++){
            if(rec.getElements().get(i).getName().equals(ename)){
                str = ((DefField)rec.getElements().get(i)).getType();
            }
        }
        return str;
    }
    private boolean isRecord(String ename, DefRecord rec){
        boolean is = false;
        
        for(int i = 0; i < rec.getElements().size(); i++){
            if(rec.getElements().get(i).getName().equals(ename)){
                if(rec.getElements().get(i).getClass() == DefRecord.class){
                    is = true;
                }
            }
        }
        return is;
    }
    private DefRecord getRecord(String ename, DefRecord rec){
        DefRecord def = null;
        
        for(int i = 0; i < rec.getElements().size(); i++){
            if(rec.getElements().get(i).getName().equals(ename)){
                if(rec.getElements().get(i).getClass() == DefRecord.class){
                    def = (DefRecord)rec.getElements().get(i);
                }
            }
        }
        return def;
    }
}
