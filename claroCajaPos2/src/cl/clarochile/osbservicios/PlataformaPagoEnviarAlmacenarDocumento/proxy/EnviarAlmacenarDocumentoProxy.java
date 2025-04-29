package cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.proxy;
import cl.clarochile.osbservicios.PlataformaPagoEnviarAlmacenarDocumento.PlataformaPagoEnviarAlmacenarDocumentoProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class EnviarAlmacenarDocumentoProxy {
	 private static PlataformaPagoEnviarAlmacenarDocumentoProxy instance = null;
	    
	    public static PlataformaPagoEnviarAlmacenarDocumentoProxy getProxyInstance() {
	        if( instance == null ){
	            instance = new PlataformaPagoEnviarAlmacenarDocumentoProxy();
	            ParamSet posDat = Base.getParamSet( "posDat" );
	            ParamSet posCfg = Base.getParamSet( "posCfg" );          
	            instance.setEndpoint(posDat.getStringValue("URL_AlmacenarDocumento"));
	            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
	        }
	        return instance;
	    }
}
