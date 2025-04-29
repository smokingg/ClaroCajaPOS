package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.PlataformaPagoConsultar.PlataformaPagoConsultarServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlConsultarProxy {

	private static PlataformaPagoConsultarServerProxy instance = null;
    
    public static PlataformaPagoConsultarServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new PlataformaPagoConsultarServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_Consultar"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}
