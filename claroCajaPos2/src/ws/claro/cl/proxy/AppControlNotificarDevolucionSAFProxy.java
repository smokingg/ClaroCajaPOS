package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.PlataformaPagoNotificarSAF.PlataformaPagonotificarDevolucionSAFProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlNotificarDevolucionSAFProxy {
	private static PlataformaPagonotificarDevolucionSAFProxy instance = null;
	
    public static PlataformaPagonotificarDevolucionSAFProxy getProxyInstance() {
        if( instance == null ){
            instance = new PlataformaPagonotificarDevolucionSAFProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_NotificarSAF"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}



