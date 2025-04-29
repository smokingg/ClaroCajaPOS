package cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.proxy;
import cl.clarochile.osbservicios.PlataformaPagoEnviarCorreoSilverpopWS.PlataformaPagoEnviarCorreoSilverpopWSProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;
public class EnviarCorreoSilverpopProxy {
	private static PlataformaPagoEnviarCorreoSilverpopWSProxy instance = null;
	
	public static PlataformaPagoEnviarCorreoSilverpopWSProxy getProxyInstance() {
		 if( instance == null ){
	            instance = new PlataformaPagoEnviarCorreoSilverpopWSProxy();
	            ParamSet posDat = Base.getParamSet( "posDat" );
	            ParamSet posCfg = Base.getParamSet( "posCfg" );
	            instance.setEndpoint(posDat.getStringValue("URL_EnviarCorreo"));
	            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
	        }
	        return instance;
	}

}

