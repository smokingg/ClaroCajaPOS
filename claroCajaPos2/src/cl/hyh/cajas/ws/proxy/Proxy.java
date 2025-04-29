package cl.hyh.cajas.ws.proxy;

import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class Proxy {

    private static ServerProxy instance = null;
    
    public static ServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new ServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_Proxy"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}
