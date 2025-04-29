package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefElement;
import cl.hyh.redpagos.caja.base.parser.DefField;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefRecord;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.comm.Soap;
import cl.hyh.redpagos.caja.comm.SoapConnectException;
import cl.hyh.redpagos.caja.comm.SoapTimeoutException;

public class ServicioTRV extends Servicio{
      
    public int execute(OperTRV operTrv) throws BaseException {
        
        ParamSet pSet = Base.getParamSet( "posDat" );
        String offline = pSet.getStringValue( "offline" );
        if( offline.equals( "si" ) )
            return RC_CONNECT_ERROR;
        
        /*
         * CASOS DE PRUEBA SAF->ONLINE
         * 
         */
        //return RC_CONNECT_ERROR;
        //return RC_TIMEOUT;
        //throw new BaseException("connect timeout" );
        //row new BaseException( "read timeout" );
        /*
         * 
         * 
         */
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        loadHeaderIn( operTrv);  
        String s = getMensajeSOAP(operTrv);
        
        this.setSoap(s);
        
        Base.logger.info("Soap ida: " + s);
        
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
        else { // se ejecuta el soap contra el remoto
            String url = defSrv.getHost();
            String resp;
            if( url.startsWith( "$" ) )
                url = posCfg.getStringValue( url.substring( 1 ) );
            try {
                resp = Soap.process( defSrv.getType(), s, url );
            } catch (SoapConnectException e) {
                throw new BaseException( e, "connect timeout" );
            } catch (SoapTimeoutException e) {
                throw new BaseException( e, "read timeout" );
            }
            procesaMensajeSoap( resp );            
        }
        
        return RC_OK;
        
    }    
    /**
     * construye el mensaje SOAP para el servicio
     * @return
     * @throws BaseException 
     */
    protected String getMensajeSOAP(OperTRV operTrv) throws BaseException {        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        StringBuffer mensaje = new StringBuffer();
        
        mensaje.append( "<soapenv:Envelope " );  
        mensaje.append( "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");   
        mensaje.append( "xmlns:ifop=\"http://www.example.org/IFOperacion/\">");  
        mensaje.append( "<soapenv:Header/>" );  
        mensaje.append( "<soapenv:Body>" );  
        
        mensaje.append(setTag( "ifop:" + defSrv.getType() + "Request" ) );
        
        appendHeaderIn( mensaje );
        appendDataIn( mensaje,operTrv );
        
        mensaje.append(setTag( "/ifop:" + defSrv.getType() + "Request" ) );
        mensaje.append( "</soapenv:Body>" );
        mensaje.append( "</soapenv:Envelope>" ); 
        
        return mensaje.toString();
    }
    protected void appendDataIn( StringBuffer sb, OperTRV operTrv ) throws BaseException {
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        for(int i = 0; i < operTrv.getCarroCompras().getDocumentos().size(); i++){
            DefDocumentoPago input = Base.getDefDocumentoPago((operTrv.getCarroCompras().getDocument(i).getNombre()));
            StringBuffer dataIn = createNode( input.getName(), input.getRecordDef(), operTrv.getCarroCompras().getDocument(i).getDatos());
            sb.append( dataIn );
        }
        for(int i = 0; i < operTrv.getCarroMediosPago().getMediosPago().size(); i++){
            DefMedioPago input = Base.getDefMedioPago((operTrv.getCarroMediosPago().getPago(i).getNombre()));
            StringBuffer dataIn = createNode( "MP" + input.getName(), input.getRecordDef(), operTrv.getCarroMediosPago().getPago(i).getDatos());
            sb.append( dataIn );
        }
              
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
                    DefField defF = (DefField)e;
                    if(defF.getTransmit()){
                        field = setTag(e.getName());
                        fieldText = data.getStringValue(e.getName());
                        buff.append(field);
                        buff.append(fieldText);
                        buff.append(setTag("/"+e.getName()));
                    }
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
    
    protected void loadHeaderIn(OperTRV operTrv) throws BaseException {
        // carga record de Datos headerIn para el servicio
        
        DefServicio defSrv  = Base.getDefServicio( nombreServicio );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        ParamSet posDat = Base.getParamSet("posDat");
        if(operTrv.isRegular()){
            headerIn.setValue( "TipoOperacion", this.nombreServicio + "R" );
        }
        else{
            headerIn.setValue( "TipoOperacion", this.nombreServicio );
        }
        if(operTrv.isRecarga()){
            headerIn.setValue( "TipoOperacion", this.nombreServicio + "VD" );
        }
        headerIn.setValue( "Entidad", posDat.getStringValue("Entidad") );
        headerIn.setValue( "Agencia", posDat.getStringValue("Agencia") );
        headerIn.setValue( "Cajero", operTrv.getCajero().trim() );
        headerIn.setValue( "Operador", operTrv.getUsuario().trim() );
        headerIn.setValue( "Caja", operTrv.getCaja().trim() );
        headerIn.setValue( "FechaPago", operTrv.getFechaPago().trim() );
        headerIn.setValue( "NumeroOperacion",  operTrv.getNumeroOperacion() );
        headerIn.setValue( "FechaOperacion", operTrv.getFecha().trim() );
        headerIn.setValue( "HoraOperacion", operTrv.getHora().trim() );
        
    }

}
