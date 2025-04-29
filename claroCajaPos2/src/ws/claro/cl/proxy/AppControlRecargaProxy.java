package ws.claro.cl.proxy;

import org.example.www.ValidarRecargaWS.ValidarRecargaWSServerProxy;

import cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlRecargaProxy {

	private static ValidarRecargaWSServerProxy instance = null;
    
    public static ValidarRecargaWSServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new ValidarRecargaWSServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_Recargar"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }
}
