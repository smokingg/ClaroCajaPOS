package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.PlataformaPagoConsultaSAF.PlataformaPagoconsultaDevolucionSAFProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlDevolucionSAFProxy {
	private static PlataformaPagoconsultaDevolucionSAFProxy instance = null;
	
    public static PlataformaPagoconsultaDevolucionSAFProxy getProxyInstance() {
        if( instance == null ){
            instance = new PlataformaPagoconsultaDevolucionSAFProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_ConsultarSAF"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}



