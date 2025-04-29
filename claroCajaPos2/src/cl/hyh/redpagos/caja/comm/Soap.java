package cl.hyh.redpagos.caja.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;

public class Soap {
    
    public static String process(String soapAction, String msg, String urlStr ) throws SoapConnectException, SoapTimeoutException, BaseException {
        HttpURLConnection aC = null;
        URL aURL = null;
        OutputStreamWriter oS = null;
        InputStream iS = null;
        BufferedReader bR = null;
        
        StringBuffer line = new StringBuffer("");
        String aux;
        int timeout;
        String resp = "";

        timeout = Base.getParamSet( "posCfg" ).getIntValue( "soapTimeout" );
                    
        try {
            aURL = new URL( urlStr );
            aC = (HttpURLConnection) aURL.openConnection();
        } catch(Exception e) {
            Base.logger.error( "error URL " + e );
            throw new BaseException( e, "falla openConnection " + e );
        }
        
        aC.setConnectTimeout( 5000 );
        aC.setReadTimeout( timeout * 1000 );
                                    
        aC.setDoOutput( true );
        aC.setDoInput( true );
        try {
            aC.setRequestMethod( "POST" );
        } catch (ProtocolException e1) {;
        }
        aC.setUseCaches( false );
        aC.setAllowUserInteraction( false ); 
        aC.setRequestProperty( "Content-Length", Integer.toString( msg.length() ) );  
        aC.setRequestProperty( "Content-Type", "text/xml; charset=utf-8" );
        aC.setRequestProperty( "SOAPAction", soapAction );                

        try {                                   
            oS = new OutputStreamWriter( aC.getOutputStream(),"utf-8"  );
            oS.write( msg );
            oS.flush();
            oS.close();
            oS = null;

            bR = new BufferedReader( new InputStreamReader( aC.getInputStream(),"utf-8" ) );
            aux = bR.readLine();
            while( aux != null ) {
                line.append(aux);
                aux = bR.readLine();
            }
            bR.close(); bR = null;
            byte[] b = line.toString().getBytes("UTF8");
            resp = new String(b);
            resp = line.toString();
        } catch( java.net.SocketTimeoutException e ) {
            Base.logger.error( e );
            if( e.getMessage().startsWith( "connect" ) )
                throw new SoapConnectException();
            else
                throw new SoapTimeoutException();
        } catch (IOException e) {
            Base.logger.error( e.getMessage() );
            if(e.getMessage().contains("connect")){
                throw new SoapConnectException();
            }
            throw new BaseException( e , " falla ejecucion de soap" );
        }
        finally {
            if( bR != null )
                try {
                    bR.close();
                } catch (IOException e) {
                }
            if( oS != null )
                try {
                    oS.close();
                } catch (IOException e) {
                }
        }
        Base.logger.info("Respuesta: " + resp);
        return resp;
    }
}
