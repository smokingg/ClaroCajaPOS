package cl.hyh.redpagos.caja.base;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.PingOutDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.hyh.redpagos.caja.trx.TrxClaroCierreContingencia;

/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ThreadIsAlive extends Thread {
    
    public void run() {
        
        ParamSet pSet = Base.getParamSet( "posCfg" );
        String respaldoCajero = null;
        String respaldoRecaudador = null;
        String respaldoCodigoRecaudador = null;
        String respaldoUsuario = null;
        int contador = 0;
        //Reintentos
        while(true){
        	
	            Tools.espera(10*Integer.parseInt(pSet.getStringValue("isAlive")));
	            //ServerProxy pr = Proxy.getProxyInstance();
	            AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
	            
	            //HeaderIn hIn = new HeaderIn();
	            HeaderDTO hIn = new HeaderDTO();
	            
	    		ParamSet pList = Base.getParamSet( "posDat" );
	    	try{	
	        	hIn.setAgencia((pList.getStringValue("Agencia")));
	    		hIn.setCajaFisica((pList.getStringValue("Caja")));
	    		hIn.setEntidad((pList.getStringValue("Entidad")));
	    		
	    		hIn.setCajero((pList.getStringValue("Cajero")));
	    		respaldoCajero = hIn.getCajero();
	    		
	    		hIn.setSession((pList.getStringValue("SessionId")));
	    		
	    		hIn.setUsuario(pList.getStringValue("Usuario"));
	    		respaldoUsuario = hIn.getUsuario();
	    		
	    		hIn.setRecaudador(pList.getStringValue("CodigoRecaudador"));
	    		respaldoCodigoRecaudador = hIn.getRecaudador();
	    		
	    		respaldoRecaudador = pList.getStringValue("Recaudador");

	    		
        	}
        	catch(Exception e){
        		continue;
        	}
        	
    		//PingOut resp = null;
    		PingOutDTO resp = null;
    		
    		try {
				//resp = pr.ping(new Request( hIn ));
				resp = pr.pingCaja( hIn );
			} catch (RemoteException e) {
				contador++;
				Base.logger.info("Deteccion de perdida de Conexion " +contador);
				Base.logger.info("Is NOT Alive ");
				//Tools.logStackTrace(Base.logger, e);
				if(!pList.getStringValue("sinConexion").equals("si")){
					if (contador > Integer.parseInt(pSet.getStringValue("Reintento"))){
						pList.setValue( "sinConexion", "si" );
						pList.setValue("UsuarioEnOffline", respaldoUsuario);
						pList.setValue("CodigoRecaudadorEnOffline", respaldoCodigoRecaudador);
						pList.setValue("RecaudadorEnOffline", respaldoRecaudador);
						pList.setValue("CajeroEnOffline", respaldoCajero);
						pList.setValue("CierreOffline","no");
						pList.setValue("AbiertaSinConexion", "no" );
						pList.save();
						 JOptionPane.showMessageDialog(null, "Se Cerrara la Caja para entrar a modo Contingencia", "Info", JOptionPane.INFORMATION_MESSAGE);
						 System.exit(0);
						 break;
					}
				}
				continue;
			}
    		
			// TODO cbriones: validar retorno ??? (flag ??)
			//Base.logger.info("Is Alive " + resp.getHeaderOut().getRc());
			Base.logger.info("Is Alive " + resp.getRetCode());
			Base.logger.info("Is Alive " + resp.getRetDesc());
			
			if(resp != null){
				//Valido si cierro la caja para modo Contingencia si la respuesta es distinta de 0.
				if(resp.getRetCode().equals("-10") || resp.getRetCode().equals("-11")) {
					contador++;
					Base.logger.info("Deteccion de perdida de Conexion " +contador);
					Base.logger.info("Is NOT Alive ");
					//Tools.logStackTrace(Base.logger, e);
					if(!pList.getStringValue("sinConexion").equals("si")){
						if (contador > Integer.parseInt(pSet.getStringValue("Reintento"))){
							pList.setValue( "sinConexion", "si" );
							pList.setValue("UsuarioEnOffline", respaldoUsuario);
							pList.setValue("CodigoRecaudadorEnOffline", respaldoCodigoRecaudador);
							pList.setValue("RecaudadorEnOffline", respaldoRecaudador);
							pList.setValue("CajeroEnOffline", respaldoCajero);
							pList.setValue("CierreOffline","no");
							pList.setValue("AbiertaSinConexion", "no" );	
							pList.save();
							 JOptionPane.showMessageDialog(null, "Se Cerrara la Caja para entrar a modo Contingencia", "Info", JOptionPane.INFORMATION_MESSAGE);
							 System.exit(0);
							 break;
						}
					}
					continue;
				}
			}
			
			Base.logger.info("Monto Cheque :" + resp.getCheques() );
			
			if(Long.parseLong(pSet.getStringValue("montoRemesa")) <= ( resp.getCheques() ) ){
                JOptionPane.showMessageDialog(null, "Debe realizar una remesa de Cheques", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
			
			if(Long.parseLong(pSet.getStringValue("montoRemesa")) <= ( resp.getChequesFecha() ) ){
                JOptionPane.showMessageDialog(null, "Debe realizar una remesa de Cheques", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
			
			if(Long.parseLong(pSet.getStringValue("montoRemesaEfect")) <= ( resp.getEfectivo() ) ){
                JOptionPane.showMessageDialog(null, "Debe realizar una remesa de Efectivo", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
			
			if(pList.getStringValue("sinConexion").equals("si")){
				JOptionPane.showMessageDialog(null, "Ha retornado la Conexión\nDebe hacer Cierre de Caja\nSe Cerrara la Caja para Volver a modo Online", "Info", JOptionPane.INFORMATION_MESSAGE);
				pList.setValue("sinConexion", "no" );
				pList.setValue("AbiertaSinConexion", "no" );
				//pSet.setValue("CierreOffline","si");
				pList.save();
			
			}
            continue;
        }
    }
}
