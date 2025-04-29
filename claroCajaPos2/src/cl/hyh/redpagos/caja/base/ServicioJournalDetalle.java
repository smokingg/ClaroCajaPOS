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
import cl.hyh.redpagos.caja.base.parser.ParserJournal;
import cl.hyh.redpagos.caja.base.parser.ParserSoap;
import cl.hyh.redpagos.caja.comm.Soap;
import cl.hyh.redpagos.caja.comm.SoapConnectException;
import cl.hyh.redpagos.caja.comm.SoapTimeoutException;

public class ServicioJournalDetalle extends Servicio{
    Oper oper = null;  
    String soap = "";
    Datos headerIn = null;
    
    public int execute() throws BaseException {
        
        ParamSet pSet = Base.getParamSet( "posDat" );
        String offline = pSet.getStringValue( "offline" );
        if( offline.equals( "si" ) )
            return RC_CONNECT_ERROR;
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        loadHeaderIn();  
        String s = getMensajeSOAP();
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
                    line = line.trim();
                    resp += line;
                }
                input.close();
                Base.logger.info("Soap vuelta: " + resp);
                soap = resp;
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
            if( url.startsWith( "$" ) )
                url = posCfg.getStringValue( url.substring( 1 ) );
            String resp;
            try {
                resp = Soap.process( defSrv.getType(), s, url );
            } catch (SoapConnectException e) {
                throw new BaseException( e, "connect timeout" );
            } catch (SoapTimeoutException e) {
                throw new BaseException( e, "read timeout" );
            }
            soap = resp;
            procesaMensajeSoap( resp );
        }
        
        return RC_OK;
        
    }    
    
    
    /**
     * Construye un parser de mensajes soap el cual posee una instancia de el servicio y se encarga de procesar el 
     * mensaje soap y dejar la respuesta parseada en el response del servicio
     * @param soap Mensaje a ser procesado
     * @throws BaseException
     */
    protected void procesaMensajeSoap( String soap ) throws BaseException {
    
        DefRecord in = Base.getDefRecord("HeaderIn");
        headerIn = new Datos(in);
        
        ParserJournal parser = new ParserJournal( soap, this );
        oper = parser.parseXml();
        
        
    }
    
    public Oper getOper() {
        return oper;
    }

    public void setOper(Oper oper) {
        this.oper = oper;
    } 
    public String getSoap(){
        return soap;
    }


    public Datos getHeaderInDetalle() {
        return headerIn;
    }


    public void setHeaderInDetalle(Datos headerIn) {
        this.headerIn = headerIn;
    }
    
}
