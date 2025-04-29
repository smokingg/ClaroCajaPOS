package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.naming.NoInitialContextException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cl.hyh.redpagos.caja.base.parser.DefElement;
import cl.hyh.redpagos.caja.base.parser.DefField;
import cl.hyh.redpagos.caja.base.parser.DefRecord;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.base.parser.ParserSoap;
import cl.hyh.redpagos.caja.comm.Soap;
import cl.hyh.redpagos.caja.comm.SoapConnectException;
import cl.hyh.redpagos.caja.comm.SoapTimeoutException;

/**
 * Representacion interna de los servicios que posee la aplicacion
 * @author Rafael Hernandez
 * 
 */
public class Servicio implements Serializable {

    public static final int RC_OK = 0;
    public static final int RC_TIMEOUT = 1;
    public static final int RC_CONNECT_ERROR = 2;
    
    protected Datos request;
    protected Datos response;
    protected Datos headerIn;
    protected Datos headerOut;
    protected String nombreServicio = "";
    protected String soap = "";
    
    /**
     * solicita ejecución del servicio
     * @throws BaseException 
     */
    public int execute() throws BaseException {
        
        ParamSet pDat = Base.getParamSet( "posDat" );
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String offline = pDat.getStringValue( "offline" );
        if( offline.equals( "si" ) )
            return RC_TIMEOUT;
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        loadHeaderIn();  
        String s = getMensajeSOAP();
        if( s.contains( "<Password>" ) ){
            String aux = s;
            int ini = aux.indexOf("<Password>");
            int fin = aux.indexOf("</Password>");
            aux = aux.replace(aux.substring(ini + "<Password>".length(), fin ), "**********");
            Base.logger.info("Soap ida: " + aux);
        }
        else if(s.contains( "<PasswordActual>" ) ){
            String aux = s;
            int ini = aux.indexOf("<PasswordActual>");
            int fin = aux.indexOf("</PasswordActual>");
            aux = aux.replace(aux.substring(ini + "<PasswordActual>".length(), fin ), "**********");
            
            ini = aux.indexOf("<PasswordNueva>");
            fin = aux.indexOf("</PasswordNueva>");
            aux = aux.replace(aux.substring(ini + "<PasswordNueva>".length(), fin ), "**********");
            
            Base.logger.info("Soap ida: " + aux);
        }
        else{
            Base.logger.info("Soap ida: " + s);
        }
        
        if( defSrv.getSaf().equals("si") ) {
            // es un mensaje SAF
            
            String sNumOper = headerIn.getStringValue( "NumeroOperacion" );
            String filename = String.format( "Saf_%s-%d", Tools.getFecha(), new Long( sNumOper ) );

            String fDat = pSet.getStringValue("ServiceSafDir") + "/" + defSrv.getCola() + "/"+ filename + ".dat";
            String fCtl = pSet.getStringValue("ServiceSafDir") + "/" + defSrv.getCola() + "/"+ filename + ".ctl";
            FileOutputStream os = null;
            try {
                os = new FileOutputStream( fDat );
                FileDescriptor fd = os.getFD();
                //BufferedWriter bw = new BufferedWriter( new FileWriter( fDat ) );
                os.write((this.nombreServicio + "\n").getBytes());
                os.write(( headerIn.getStringValue( "NumeroOperacion" ) + "\n" ).getBytes());
                os.write(( headerIn.getStringValue( "FechaOperacion" ) + "\n" ).getBytes());
                os.write(( headerIn.getStringValue( "HoraOperacion" ) + "\n" ).getBytes()); 
                os.write(( defSrv.getHost() + "\n" ).getBytes());
                os.write(( defSrv.getAppl() + "\n" ).getBytes());
                os.write(( s + "\n" ).getBytes());
                
                os.flush();
                fd.sync();
                os.close(); os = null;
                
                os = new FileOutputStream( fCtl );
                fd = os.getFD();
                os.flush();
                fd.sync();
                os.close(); os = null;
                
            } catch (Exception e) {
                Tools.logStackTrace(Base.logger, e);
                JOptionPane.showMessageDialog(null, "Error al grabar en disco,se cerrará la aplicación\n[" + e.getMessage()+"]\nClase: [Servicio.java]", "Error", JOptionPane.ERROR_MESSAGE);
                Tools.logStackTrace(Base.logger, e);
                System.exit(1);
            }
            Base.logger.info("Escribiendo archivo");
            return RC_OK;
        }
        else {
            // es una Consulta o Actualizacion online
            // por ahora leemos el xml de respuesta desde archivo en disco
            // de nombre NombreServicio_resp.xml
            
            ParamSet posCfg = Base.getParamSet( "posDat" );
            String testMode = posCfg.getStringValue( "testMode" );
            
            if( testMode.equals( "local" ) ) {
                ClassLoader cl = this.getClass().getClassLoader();
                String fname = "resp/" + defSrv.getName() + "_resp.xml";
                InputStream in = null;
                try {
                    in = cl.getResource(fname).openStream();
                    BufferedReader input = null;
                    input =  new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    String resp = "";
                    while (( line = input.readLine()) != null){
                        line = line.trim();
                        resp += line;
                    }
                    input.close();
                    Base.logger.info("Soap vuelta: " + resp);
                    procesaMensajeSoap( resp );
                    return RC_OK;            
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else { // se ejecuta el soap contra el servicio remoto
                String url = defSrv.getHost();
                if( url.startsWith( "$" ) ) {
                    if( url.length() < 2 )
                        throw new BaseException( "parametro url invalido" );
                    url = posCfg.getStringValue( url.substring( 1 ) );
                }
                String resp;

                try {
                    resp = Soap.process( defSrv.getType(), s, url );
                } catch (SoapConnectException e) {
                    return RC_CONNECT_ERROR;
                } catch (SoapTimeoutException e) {
                    return RC_TIMEOUT;
                } catch (Exception e) {
                    return RC_CONNECT_ERROR;
                }
                
                procesaMensajeSoap( resp );
                
                // vemos si hay codigo de error en el servicio
                
                Datos headerOut = getHeaderOut();
                String sRetcode = headerOut.getStringValue( "RetCode" ).trim();
                int retcode;
                if(!sRetcode.equals("")){
                    retcode = new Integer( sRetcode ).intValue();
                }
                else{
                    throw new BaseException( "No viene ret code en la respuesta" );
                }
                String retClass = headerOut.getStringValue( "RetClass" );
                if(!this.nombreServicio.equals("ValidaCheque") && !this.nombreServicio.equals("ValidaPresto")){
                    if( retClass != null && !retClass.equals( "NEGOCIO" ) && retcode != 0 ) {
                        String msg = headerOut.getStringValue( "RetClass" ) + " : " + headerOut.getStringValue( "RetDesc" );
                        Base.logger.info( msg );
                        throw new BaseException( msg );
                    }
                }
            }
        }
        
        return RC_OK;
        
    }    
    
    /**
     * construye el mensaje SOAP para el servicio
     * @return
     * @throws BaseException 
     */
    protected String getMensajeSOAP() throws BaseException {        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        StringBuffer mensaje = new StringBuffer();

        mensaje.append( "<soapenv:Envelope " );  
        mensaje.append( "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");   
        if( defSrv.getHost().contains( "OperCajaActualiza" ) ){
            mensaje.append( "xmlns:ifop=\"http://www.example.org/IFOperCajaActualiza/\">");  
        }
        else{
            mensaje.append( "xmlns:ifop=\"http://www.example.org/IFOperacion/\">"); 
        }
        mensaje.append( "<soapenv:Header/>" );  
        mensaje.append( "<soapenv:Body>" ); 

        mensaje.append(setTag( "ifop:" + defSrv.getType() + "Request" ) );
        
        appendHeaderIn( mensaje );
        appendDataIn( mensaje );
        
        mensaje.append(setTag( "/ifop:" + defSrv.getType() + "Request" ) );
        mensaje.append( "</soapenv:Body>" );
        mensaje.append( "</soapenv:Envelope>" ); 
        
        return mensaje.toString();
    }
    
    protected String getMensajeString(){
        StringBuffer mensaje = new StringBuffer();
        try {
            appendDataIn( mensaje );
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
        }
        return mensaje.toString();
    }
    
    
    /**
     * Construye un parser de mensajes soap el cual posee una instancia de el servicio y se encarga de procesar el 
     * mensaje soap y dejar la respuesta parseada en el response del servicio
     * @param soap Mensaje a ser procesado
     * @throws BaseException
     */
    protected void procesaMensajeSoap( String soap ) throws BaseException {
    
        ParserSoap parser = new ParserSoap( soap, this );
        parser.parseXml();
    }
    

    /**
     * construye y agrega el body soap para los datos propios del mensaje de request
     * Se cargan los datos definidos en la definición del servicio con los datos del Datos request
     * pasado por el usuario del objeto Servicio
     * Si la construcción es ad-hoc la subclase respectiva reimplementará este método a su pinta
     * @param sb
     * @throws BaseException 
     */
    protected void appendHeaderIn( StringBuffer sb ) throws BaseException {
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        DefRecord input = Base.getDefRecord( "HeaderIn" );
        sb.append( createNode( input.getName(), input, headerIn ) );

    }
    
    protected void loadHeaderIn() throws BaseException {
        // carga record de Datos headerIn para el servicio
        
        DefServicio defSrv  = Base.getDefServicio( nombreServicio );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        ParamSet posDat = Base.getParamSet("posDat");
        headerIn.setValue( "TipoOperacion", this.nombreServicio );        
        headerIn.setValue( "Agencia", posDat.getStringValue("Agencia") );
        headerIn.setValue( "Entidad", posDat.getStringValue("Entidad") );
        headerIn.setValue( "Cajero", posDat.getStringValue("Cajero") );
        headerIn.setValue( "Operador", posDat.getStringValue("Usuario") );
        headerIn.setValue( "Caja", posDat.getStringValue("Caja") );
        headerIn.setValue( "FechaPago", posDat.getStringValue("FechaPago") );
        ParamSet pList = Base.getParamSet( "posDat" );
        long numeroOperacion = pList.getLongValue( "NumeroOperacion" );
        pList.setValue( "NumeroOperacion", numeroOperacion + 1 );
        pList.save();
        headerIn.setValue( "NumeroOperacion", numeroOperacion );
        headerIn.setValue( "FechaOperacion", Tools.getFecha() );
        headerIn.setValue( "HoraOperacion", Tools.getTime() );
        
    }
    
    /**
     * construye y agrega el body soap para los datos propios del mensaje de request
     * 
     * Se cargan los datos definidos en la definición del servicio con los datos del Datos request
     * pasado por el usuario del objeto Servicio
     * 
     * Si la construcción es ad-hoc la subclase respectiva reimplementará este método a su pinta
     * @param sb
     */
    protected void appendDataIn( StringBuffer sb ) throws BaseException {
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        DefRecord input = defSrv.getInputRecordDef();
        StringBuffer dataIn = null;
        if(defSrv.getName().equals("EnvioReversaTarjeta")){
            dataIn = createNode( "EnvioReversa", input, request);
        }
        else{
            dataIn = createNode( input.getName(), input, request);
        }
        sb.append( dataIn );
              
    }
    
    protected StringBuffer createNode(String name,DefRecord rec,Datos data) throws BaseException{
        
        StringBuffer buff = new StringBuffer();
        buff.append(setTag(name));
        DefElement e = null;
        String field = null;
        String fieldText = null;
        
        for(int i = 0; i < rec.getElements().size(); i++){
            e = rec.getElements().get(i);
            try{
                if(e.getClass() == DefField.class){
                    field = setTag(e.getName());
                    fieldText = data.getStringValue(e.getName());
                    buff.append(field);
                    buff.append(fieldText);
                    buff.append(setTag("/"+e.getName()));
                }
                else if(e.getClass() == DefRecord.class && !((DefRecord)e).isMultiple()){
                    buff.append(createNode(e.getName(),(DefRecord)e,data.getDatos(e.getName())));
                }
                else if(e.getClass() == DefRecord.class && ((DefRecord)e).isMultiple()){
                    for(int j = 0; j < data.getArrayList(e.getName()).size(); j++){
                        buff.append(createNode(e.getName(),(DefRecord)e,data.getArrayList(e.getName()).get(j)));
                    }
                }
            }
            catch(Exception ex){
                Tools.logStackTrace( Base.logger, ex );
                throw new BaseException( ex, ex.toString() );
            }
        }
        buff.append(setTag("/"+name));
        return buff;
    } 
    
    protected String setTag( String tag ){
        return "<"+tag+">";
    }

    /**
     * Retorna el nombre del servicio
     * @return
     */
    public String getNombreServicio() {
        return nombreServicio;
    }

    /**
     * Setea el nombre del servicio
     * @param nombreServicio
     */
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
    
    /**
     * @return referecia a Datos del request
     */
    public Datos getRequest() {
        return request;
    }

    /**
     * @return referencia a Datos del response
     */
    public Datos getResponse() {
        return response;
    }

    /**
     * Setea el area de datos correspondiente al request
     * @param request
     */
    public void setRequest(Datos request) {
        this.request = request;
    }

    /**
     * Setea el area de datos correspondiente al response
     * @param response
     */
    public void setResponse(Datos response) {
        this.response = response;
    }

    /**
     * Retorna los datos del header in
     * @return
     */
    public Datos getHeaderIn() {
        return headerIn;
    }

    /**
     * Setea los datos del header in
     * @param headerIn
     */
    public void setHeaderIn(Datos headerIn) {
        this.headerIn = headerIn;
    }

    /**
     * Retorna los datos del header out
     * @return
     */
    public Datos getHeaderOut() {
        return headerOut;
    }

    /**
     * Setea los datos del header out
     * @param headerOut
     */
    public void setHeaderOut(Datos headerOut) {
        this.headerOut = headerOut;
    }
    

    public static String enviarSoap( String host, String appl, int timeout, String soap ) throws BaseException {
        String url = host;
        ParamSet posCfg = Base.getParamSet("posDat");
        if( url.startsWith( "$" ) )
            url = posCfg.getStringValue( url.substring( 1 ) );
        String resp;
        String tipo = "";
        if(url.equals("URL_OperCajaNotifica")){
            tipo = "Notificacion";
        }
        else{
            tipo = "Actualizacion";
        }
        try {
            resp = Soap.process( tipo, soap, url );
        } catch (SoapConnectException e) {
            throw new BaseException( e, "connect timeout" );
        } catch (SoapTimeoutException e) {
            throw new BaseException( e, "read timeout" );
        }
        return resp;
        
    }
    
    public  void checkRetcode() throws BaseException {
        String sRetcode = headerOut.getStringValue( "RetCode" ).trim();
        String sRetclass = headerOut.getStringValue( "RetClass" ).trim();
        int iRetcode;
        try {
            iRetcode = new Integer( sRetcode ).intValue();
        } catch (NumberFormatException e) {
            Base.logger.error( "Error en RetCode " + sRetcode );
            iRetcode = 99;
        }
        
        if( iRetcode != 0 && headerIn.getStringValue("TipoOperacion").contains("Consulta")){
            String retDesc = headerOut.getStringValue( "RetDesc" );
            throw new BaseException( retDesc );
        }
        if( iRetcode != 0 && this.getNombreServicio().equals("RecargaOnline")){
            String retDesc = headerOut.getStringValue( "RetDesc" );
            throw new BaseException( retDesc );
        }
        if( iRetcode != 0 && sRetclass.equals("SISTEMA")) {
            String retDesc = headerOut.getStringValue( "RetDesc" );
            throw new BaseException( "Error de sistema: " + retDesc );
        }
        if( iRetcode == 8205 && headerIn.getStringValue("TipoOperacion").contains("Trv") ) {
            String retDesc = headerOut.getStringValue( "RetDesc" );
            throw new BaseException( retDesc );
        }
        if( iRetcode == 8205 && headerIn.getStringValue("TipoOperacion").contains("Remesa") ) {
            String retDesc = headerOut.getStringValue( "RetDesc" );
            throw new BaseException( retDesc );
        }
        if(iRetcode == 161 && headerIn.getStringValue("TipoOperacion").contains("Remesa") ) {
            String retDesc = headerOut.getStringValue( "RetDesc" );
            throw new BaseException( retDesc );
        }
    }
    
    public  void checkRetcodeReversa() throws BaseException {
        String sRetcode = headerOut.getStringValue( "RetCode" ).trim();
        int iRetcode;
        try {
            iRetcode = new Integer( sRetcode ).intValue();
        } catch (NumberFormatException e) {
            Base.logger.error( "Error en RetCode " + sRetcode );
            iRetcode = 99;
        }
       
        if( iRetcode == 501 ) {
            throw new BaseException( "timeout error" );
        }  
        else if( iRetcode == 500 ){
            throw new BaseException( "connect error" );
        }
    }
    
    public static void grabaReversaPendiente( Servicio servicio ) {
        ParamSet posCfg = Base.getParamSet( "posCfg" );
        
        try {            
            FileOutputStream fos = new FileOutputStream( posCfg.getStringValue("ReversaPendienteDir") + "ReversaPendiente.dat" );
            ObjectOutputStream outStream = new ObjectOutputStream( fos );

            outStream.writeObject( servicio );
            outStream.close();         
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String getSoap() {
        return soap;
    }

    public void setSoap(String soap) {
        this.soap = soap;
    }

    public static void borraReversaPendiente() {
        ParamSet posCfg = Base.getParamSet( "posCfg" );
        File fDat = new File( posCfg.getStringValue("ReversaPendienteDir") + "ReversaPendiente.dat" );
        fDat.delete();   
    }
}
