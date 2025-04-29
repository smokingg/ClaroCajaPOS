package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlNotificarProxy {
	
	private static PlataformaPagoNotificarServerProxy instance = null;
    
    public static PlataformaPagoNotificarServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new PlataformaPagoNotificarServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_Notificar"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }

}
