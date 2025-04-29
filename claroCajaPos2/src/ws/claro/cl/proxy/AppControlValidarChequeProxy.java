package ws.claro.cl.proxy;

import cl.clarochile.osbservicios.ValidarChequeWS.ValidarChequeWSServerProxy;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

public class AppControlValidarChequeProxy {
	
	private static ValidarChequeWSServerProxy instance = null;
    
    public static ValidarChequeWSServerProxy getProxyInstance() {
        if( instance == null ){
            instance = new ValidarChequeWSServerProxy();
            ParamSet posDat = Base.getParamSet( "posDat" );
            ParamSet posCfg = Base.getParamSet( "posCfg" );
            instance.setEndpoint(posDat.getStringValue("URL_Validar_Cheque"));
            ((org.apache.axis.client.Stub)instance.getServer()).setTimeout(1000 * Integer.parseInt(posCfg.getStringValue("wsTimeOut")));
        }
        return instance;
    }

}
