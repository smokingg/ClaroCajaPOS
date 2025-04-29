package cl.hyh.redpagos.caja.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

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
public class ServicioOper  extends Servicio{

    public static final int RC_OK = 0;
    public static final int RC_TIMEOUT = 1;
    public static final int RC_CONNECT_ERROR = 2;
    
    protected Datos request;
    protected Datos response;
    protected String nombreServicio = "";
    
    /**
     * solicita ejecución del servicio
     * @throws BaseException 
     */
    public int execute(OperAdmin operAdmin) throws BaseException {
        
        ParamSet pSet = Base.getParamSet( "posCfg" );
        ParamSet pDat = Base.getParamSet( "posDat" );
        String offline = pDat.getStringValue( "offline" );
        if( offline.equals( "si" ) )
            return RC_CONNECT_ERROR;
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        
        loadHeaderIn(operAdmin);
        String s = getMensajeSOAP();
        Base.logger.info("Soap ida: " + s);
        
        if( defSrv.getSaf().equals("si") ) {
            // es un mensaje SAF
            
            String sNumOper = headerIn.getStringValue( "NumeroOperacion" );
            String filename = String.format( "Saf_%s-%d", Tools.getFecha(), new Long( sNumOper ) );

            String fDat = pSet.getStringValue("ServiceSafDir") + "/" + defSrv.getCola() + "/" + filename + ".dat";
            String fCtl = pSet.getStringValue("ServiceSafDir") + "/" + defSrv.getCola() + "/"+ filename + ".ctl";
            FileOutputStream os = null;
            try {
                os = new FileOutputStream( fDat );
                FileDescriptor fd = os.getFD();
                //BufferedWriter bw = new BufferedWriter( new FileWriter( fDat ) );
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
                JOptionPane.showMessageDialog(null, "Error al grabar en disco,se cerrará la aplicación\n[" + e.getMessage()+"]\nClase:[ServicioOper.java]", "Error", JOptionPane.ERROR_MESSAGE);
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
                procesaMensajeSoap( resp );
                
                // vemos si hay codigo de error en el servicio
                
                Datos headerOut = getHeaderOut();
                String sRetcode = headerOut.getStringValue( "RetCode" ).trim();
                String tipoCod = headerOut.getStringValue("RetClass").trim();
                int retcode = new Integer( sRetcode ).intValue();
                if( retcode != 0 && tipoCod.equals("SISTEMA")) {
                    String msg = headerOut.getStringValue( "RetClass" ) + " : " + headerOut.getStringValue( "RetDesc" );
                    throw new BaseException( msg );
                }
            }
        }
        return RC_OK;
        
    }    
    
    protected void loadHeaderIn(OperAdmin operAdmin) throws BaseException {
        // carga record de Datos headerIn para el servicio
        
        DefServicio defSrv  = Base.getDefServicio( this.getNombreServicio() );
        if( defSrv == null ) {
            throw new BaseException( "Servicio " + nombreServicio + " indefinido" );
        }
        ParamSet posDat = Base.getParamSet("posDat");
        if( this.getNombreServicio().equals("EnvioReversaTbk")){
            headerIn.setValue( "TipoOperacion", "EnvioReversa" );
        }
        else{
            if(operAdmin.isRegular()){
                headerIn.setValue( "TipoOperacion", this.getNombreServicio() + "R" );
            }
            else{
                headerIn.setValue( "TipoOperacion", this.getNombreServicio() );
            }
        }
        headerIn.setValue( "Entidad", operAdmin.getEntidad() );
        headerIn.setValue( "Agencia", operAdmin.getAgencia() );
        headerIn.setValue( "Cajero", operAdmin.getCajero() );
        headerIn.setValue( "Operador", operAdmin.getUsuario() );
        headerIn.setValue( "Caja", operAdmin.getCaja());
        if(operAdmin.isRegular()){
            headerIn.setValue( "FechaPago", operAdmin.getFechaPago() );
        }
        else{
            headerIn.setValue( "FechaPago", operAdmin.getFecha() );
        }
        headerIn.setValue( "NumeroOperacion", operAdmin.getNumeroOperacion() );
        headerIn.setValue( "FechaOperacion", operAdmin.getFecha() );
        headerIn.setValue( "HoraOperacion", operAdmin.getHora() );        
    }
    
    public StringBuffer getMensajeSoapString(){
        StringBuffer s = new StringBuffer();
        String msg = getMensajeString();
        s.append(msg);
        return s;
    }
}
