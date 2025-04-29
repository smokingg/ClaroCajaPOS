package ws.claro.cl.proxy;

import ws.claro.cl.AppControlCajaWSServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlProxy {

    private static AppControlCajaWSServerProxy instance = null;
    
    public static AppControlCajaWSServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new AppControlCajaWSServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_Proxy"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}
