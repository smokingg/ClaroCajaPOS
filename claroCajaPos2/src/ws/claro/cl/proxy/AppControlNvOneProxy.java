package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.PlataformaPagoConsultaNVOne.PlataformaPagoConsultaNVOneServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlNvOneProxy {
	private static PlataformaPagoConsultaNVOneServerProxy instance = null;
    
    public static PlataformaPagoConsultaNVOneServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new PlataformaPagoConsultaNVOneServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_ConsultarNvOne"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}
