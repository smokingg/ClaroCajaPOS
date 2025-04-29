package cl.hyh.redpagos.caja.base;

import java.rmi.RemoteException;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.InicializarCajaDTO;
import ws.claro.cl.ParametroCajaDTO;
import ws.claro.cl.proxy.AppControlProxy;

import cl.hyh.cajas.ws.impl.InicializaCajaOut;
import cl.hyh.cajas.ws.impl.ParametroCaja;
import cl.hyh.cajas.ws.impl.Request;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;

public class ServicioInitNew extends Servicio {

    public int execute() throws BaseException {
        
        //ServerProxy proxy = Proxy.getProxyInstance();
        AppControlCajaWSServerProxy proxy = AppControlProxy.getProxyInstance();
        
        /**
        Request in = new Request();
        in.getHeaderIn().setAgencia( 1 );
        in.getHeaderIn().setCajaFisica( 1 );
        */
        
        HeaderDTO in = new HeaderDTO();
        in.setAgencia("1");
        in.setCajaFisica("1");
        
        try {
            InicializarCajaDTO out = proxy.inicializarCaja( in );
            ParametroCajaDTO[] pCajas = out.getParametrosCaja();
            for( int i = 0; i < pCajas.length; i++ ) {
                ParametroCajaDTO pCaja = pCajas[i];
                ParamSet pSet = Base.getParamSet( pCaja.getDominio() );
                pSet.setValue( pCaja.getNombre(), pCaja.getValor() );
            }
        } catch (RemoteException e) {
        }

        return 0;
    }
}
