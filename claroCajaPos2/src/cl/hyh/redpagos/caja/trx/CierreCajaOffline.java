package cl.hyh.redpagos.caja.trx;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;

public class CierreCajaOffline implements ITrxBase {

	public void init(Datos htParm ) {}
	
	public int execute( ICajaView vista, int key, Datos htParam ) {
		// LIMPIO VARIABLES DE AMBIENTE        
        
//        //ServerProxy pr = Proxy.getProxyInstance();
//        AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
//        
//    	//HeaderIn hIn = new HeaderIn();
//    	HeaderDTO hIn = new HeaderDTO();
//    	
//    	ParamSet pSet = Base.getParamSet("posDat");
    	Base.logger.info( "Inicializando desconeccion de Caja Contingencia....");
    	
//		hIn.setAgencia((pSet.getStringValue("Agencia")));
//		Base.logger.info( "Agencia: " +  hIn.getAgencia());
//	 	hIn.setCajaFisica((pSet.getStringValue("Caja")));
//		Base.logger.info( "Caja Fisica: " +  hIn.getCajaFisica());
//		hIn.setEntidad((pSet.getStringValue("Entidad")));
//		Base.logger.info( "Entidad: " +  hIn.getEntidad());
//		hIn.setCajero((pSet.getStringValue("Cajero")));
//		Base.logger.info( "Cajero: " +  hIn.getCajero());
//		hIn.setSession((pSet.getStringValue("SessionId")));
//		Base.logger.info( "SessionId: " +  hIn.getSession());
//		//hIn.setUsuario(pSet.getStringValue("CodigoRecaudador"));
//		//hIn.setUsuario(pSet.getStringValue("usuario"));
//		hIn.setUsuario(pSet.getStringValue("Cajero"));
//		Base.logger.info( "Usuario: " +  hIn.getUsuario());
//		hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
//		Base.logger.info( "Recaudador: " +  hIn.getRecaudador());
//		
//		Base.logger.info( "Logout: " + pSet.getStringValue("Usuario") );
//        
//		pSet.setValue( "Usuario", "" );
//		pSet.setValue( "Perfil", "" );
//		pSet.setValue( "Cajero", 0 );
//		pSet.save();
//		
//		
//		//Response resp = null;
//		DesconectarCajaOutDTO resp = null;
//		
//		try {
//			//resp = pr.disconnect(new Request(hIn));
//			resp = pr.desconectarCaja(hIn);
//			Base.logger.info( "Codigo resp Desconectar: " + resp.getRetCode() );
//			Base.logger.info( "Desc resp Desconectar: " + resp.getRetDesc());
//		} catch (RemoteException e) {
//			Tools.logStackTrace(Base.logger, e);
//		}
//        
//		
//		// TODO, se debiese vaciar el carro de documentos y medios de pago..
//		// Para eliminar proceso de Edicion de Pago !!!
//		Base.logger.info( "OperTRV: " + vista.getOperTRV() );
//		if(vista.getOperTRV() != null){
//			
//			vista.getOperTRV().getCarroCompras().getDocumentos().clear();
//			Base.logger.info( "Carro de Documentos: " + vista.getOperTRV().getCarroCompras().getDocumentos() );
//			
//			vista.getOperTRV().getCarroMediosPago().borrarCarro();
//			Base.logger.info( "Carro de Medio Pago: " + vista.getOperTRV().getCarroMediosPago() );
//			
//			Base.setEdicion(false);
//			Base.logger.info( "Es Edicion: " + Base.getEdicion() );
//		}
		
		
    	//return 11;
		return 12;
    }
}
