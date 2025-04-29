package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.PlataformaPagoConsultarItemsOne.PlataformaPagoConsultarItemsOneServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlItemsOneProxy {

	private static PlataformaPagoConsultarItemsOneServerProxy instance = null;
    
    public static PlataformaPagoConsultarItemsOneServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new PlataformaPagoConsultarItemsOneServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_ConsultarItemsOne"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}
