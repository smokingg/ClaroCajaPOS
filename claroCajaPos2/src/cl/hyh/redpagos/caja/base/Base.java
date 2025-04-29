package cl.hyh.redpagos.caja.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.axis.configuration.XMLStringProvider;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarBancosOutDTO;
import ws.claro.cl.ConsultarTarjetasMTOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.InicializarCajaDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.PingOutDTO;
import ws.claro.cl.proxy.AppControlNotificarProxy;
import ws.claro.cl.proxy.AppControlProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Caja;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionEnvio;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.NotificacionRespuesta;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Operacion;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.PlataformaPagoNotificarServerProxy;
import cl.cyc.spdh40.Transbank;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefParamSet;
import cl.hyh.redpagos.caja.base.parser.DefRecord;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.base.parser.DefVoucher;
import cl.hyh.redpagos.caja.base.parser.ParserPosConfiguration;
import cl.hyh.redpagos.caja.pos.BasePos;

/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Base {

	public static final String Oper_SAF_DIR = "/RedDePagos/saf/oper";
	public static final String Servicio_SAF_DIR = "/RedDePagos/saf/servicios";

	public static Logger logger;
	private static boolean initialized = false;
	private static OperTRV operTRV;


	public static BasePos pos = null;
	public static ArrayList<DefServicio> tablaServicios = new ArrayList<DefServicio>();
	public static ArrayList<DefMedioPago> tablaMediosPago = new ArrayList<DefMedioPago>();
	public static ArrayList<DefRecord> tablaRecords = new ArrayList<DefRecord>();
	public static ArrayList<DefDocumentoPago> tablaDocumentos = new ArrayList<DefDocumentoPago>();
	public static ArrayList<DefVoucher> tablaVouchers = new ArrayList<DefVoucher>();
	public static ArrayList<DefParamSet> defParamSets = new ArrayList<DefParamSet>();
	public static ArrayList<ParamSet> paramSets = new ArrayList<ParamSet>();
	public static MedioPago mPago = null;
	
	public static Transbank tbk;// = new Transbank();
	
	public static XMLStringProvider theConfig = null;

	public static boolean isEdicion = false;
	public static int cuotaCero = 0;


	/**
	 * Funcion de inicializacion de la aplicacion, encargada de cargar los archivos de configuracion
	 * y los parametros de la aplicacion
	 * 
	 */
	public void preInit(){
		if( initialized )
			return;
		initialized = true;
		PropertyConfigurator.configure( getClass().getClassLoader().getResource("config/log4j.properties" ) );
		//logger = Logger.getLogger( "cajasCTC" );
		logger = Logger.getLogger( "cajasClaro" );
		logger.info( "Partimos..." );

		try {
			InetAddress addr = InetAddress.getLocalHost();

			// Get IP Address
			byte[] ipAddr = addr.getAddress();
			String ip = String.format("%d.%d.%d.%d", ipAddr[0]&0xFF,ipAddr[1]&0xFF,ipAddr[2]&0xFF,ipAddr[3]&0xFF);
			logger.info("IP: " + ip);            
		} catch (UnknownHostException e) {
			Tools.logStackTrace(Base.logger, e);
		}

		ParserPosConfiguration parser = new ParserPosConfiguration();
		parser.parsePosConfiguration("config/posConfiguration.xml"); 

		//Cargamos el XMLProvider

		Tools.loadXMLProviderConfig(this);

		// Se cargan archivos de parametros

		for( int i = 0; i < defParamSets.size(); i++ ) {
			DefParamSet dPS = defParamSets.get( i );
			ParamSet pSet = new ParamSet( dPS );
			paramSets.add( pSet );
		}

	}

	public void init(){ 


		ParamSet pSet = Base.getParamSet("posDat");
		ParamSet pCfg = Base.getParamSet("posCfg");

		logger.info("Versión Caja: " + pCfg.getStringValue("Version"));
		
		if (pSet.getStringValue("TransbankConfig").length() <= 0){
			pSet.setValue( "TransbankConfig", "C:\\RedDePagos\\data\\config\\" );
			//pSet.setValue( "TransbankConfig", "D:\\RedDePagos\\data\\config\\" );
			pSet.save();
			logger.info("Variable de ruta de configuracion transbank creada");
		}else{
			logger.info("Variable de ruta de configuracion transbank ya existe");
		}
		
		if (pSet.getStringValue("TransbankLogs").length() <= 0){
			pSet.setValue( "TransbankLogs", "C:\\RedDePagos\\logs\\tbkLogs\\" );
			//pSet.setValue( "TransbankLogs", "D:\\RedDePagos\\logs\\tbkLogs\\" );
			pSet.save();
			logger.info("Variable de ruta de Logs transbank creada");
			
		}else{
			logger.info("Variable de ruta de logs transbank ya existe");
		}
		
		if (pSet.getStringValue("TransbankTrx").length() <= 0){
			pSet.setValue( "TransbankTrx", "C:\\RedDePagos\\data\\tbkTrx\\" );
		//	pSet.setValue( "TransbankTrx", "D:\\RedDePagos\\data\\tbkTrx\\" );
			pSet.save();
			logger.info("Variable de ruta de Transacciones transbank creada");
			
		}else{
			logger.info("Variable de ruta de Transacciones transbank ya existe");
		}
		logger.info("Intentando Crear Directorio : " + pSet.getStringValue("TransbankLogs"));
		File directorioTbklogs = new File(pSet.getStringValue("TransbankLogs"));

		if (!directorioTbklogs.exists()){
			directorioTbklogs.mkdir();
			logger.info("Directorio de Logs para Transbank Creado");
		}else{
			logger.info("Directorio de Logs para Transbank Ya Existe..!!!!");

		}
		
		logger.info("Intentando Crear Directorio " + pSet.getStringValue("TransbankTrx"));
		File directorioTbkTrx = new File(pSet.getStringValue("TransbankTrx"));

		if (!directorioTbkTrx.exists()){
			directorioTbkTrx.mkdir();
			logger.info("Directorio de Trx para Transbank Creado");
		}else{
			logger.info("Directorio de Trx para Transbank Ya Existe..!!!!");

		}
		
		logger.info("P I N P A D - new");
		tbk = new Transbank();
		
		/* Verificar si la caja amanece OffLine*/
		logger.info("Verificando Disponibilidad de Línea de la Caja.......");
		String respaldoCajero = null;
	    String respaldoRecaudador = null;
	    String respaldoCodigoRecaudador = null;
	    String respaldoUsuario = null;
		HeaderDTO hInOffLine = new HeaderDTO();

		hInOffLine.setAgencia((pSet.getStringValue("Agencia")));
		hInOffLine.setCajaFisica((pSet.getStringValue("Caja")));
		hInOffLine.setEntidad((pSet.getStringValue("Entidad")));
		
		hInOffLine.setCajero((pSet.getStringValue("Cajero")));
		respaldoCajero = hInOffLine.getCajero();
		
		
		hInOffLine.setSession((pSet.getStringValue("SessionId")));
		
		hInOffLine.setUsuario(pSet.getStringValue("Usuario"));
		respaldoUsuario = hInOffLine.getUsuario();
		
		hInOffLine.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
		respaldoCodigoRecaudador = hInOffLine.getRecaudador();
		
		
		respaldoRecaudador = pSet.getStringValue("Recaudador");

		PingOutDTO respOffline = null;
		AppControlCajaWSServerProxy prOfline = AppControlProxy.getProxyInstance();

		try {
			//resp = pr.ping(new Request( hIn ));
			respOffline = prOfline.pingCaja( hInOffLine );

		} catch (RemoteException e) {

			Base.logger.info("No hay Disponibilidad de Línea.... ");
			Base.logger.info("Is NOT Alive ");
			//Tools.logStackTrace(Base.logger, e);
			pSet.setValue("UsuarioEnOffline", respaldoUsuario);
			Base.logger.info("Usuario Respaldo " + respaldoUsuario);
			pSet.setValue("CodigoRecaudadorEnOffline", respaldoCodigoRecaudador);
			pSet.setValue("RecaudadorEnOffline", respaldoRecaudador);
			pSet.setValue("CajeroEnOffline", respaldoCajero);
			pSet.setValue("CierreOffline","no");
			pSet.setValue( "sinConexion", "si" );
			pSet.save();
			JOptionPane.showMessageDialog(null, "Conexion No Disponible, la Caja Abrira en Modo Contingencia", "Info", JOptionPane.INFORMATION_MESSAGE);

		}

		if(respOffline != null){
		//Valido si cierro la caja para modo Contingencia si la respuesta es distinta de 0.
			if(respOffline.getRetCode().equals("-10") || respOffline.getRetCode().equals("-11")) {
				Base.logger.info("No hay Disponibilidad de Línea.... ");
				Base.logger.info("Is NOT Alive ");
				//Tools.logStackTrace(Base.logger, e);
				if(!pSet.getStringValue("sinConexion").equals("si")){
					pSet.setValue( "sinConexion", "si" );
					pSet.setValue("CierreOffline","no");
					pSet.setValue("AbiertaSinConexion", "no" );
					//Se toman los datos del usuario al momento de caerse la caja
					pSet.setValue("UsuarioEnOffline", respaldoUsuario);
					pSet.setValue("CodigoRecaudadorEnOffline", respaldoCodigoRecaudador);
					pSet.setValue("RecaudadorEnOffline", respaldoRecaudador);
					pSet.setValue("CajeroEnOffline", respaldoCajero);
					pSet.save();
					JOptionPane.showMessageDialog(null, "Se Cerrara la Caja para entrar a modo Contingencia", "Info", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			} else {
				Base.logger.info("Caja en Línea......!!! ");
				pSet.setValue( "sinConexion", "no" );
				pSet.save();
			}
		}

		/****************************************/	

		Tools.borrarTemporales();

		if(!pSet.getStringValue("sinConexion").equals("si")){


			pos = new BasePos();
			pos.init();        
			operTRV = new OperTRV();

			// TODO esto ahora lo hace la nueva api desde su propio propertie
			//Iniciamos multicard
//			int cajaM = (int)pSet.getLongValue("Agencia");
//			int agenciaM = (int)pSet.getLongValue("Caja");
//			int entidadM = (int)pSet.getLongValue("Entidad");
//			String direccion = pSet.getStringValue( "Direccion" );
//			String ciudad = pSet.getStringValue("Ciudad");
//			String comuna = pSet.getStringValue("Comuna");
//			String disco = pSet.getStringValue("discoInit");
//
//			if(pSet.getStringValue("transbank").equals("si")){
//				String destino = pSet.getStringValue("destinoMulticard");
//				String ip = pCfg.getStringValue("ipMulticard" + destino);
//				String portTbk = pCfg.getStringValue("portMulticardTbk" + destino);
//				String port = pCfg.getStringValue("portMulticard" + destino);
//				mtc = new Multicard( agenciaM ,cajaM , direccion , comuna , ciudad , ip , portTbk , port);
//				mtc.drivePGM = "C:";
//				mtc.driveINI = "C:";
//				mtc.setMtc();
//
//			}

			

			// TODO NO  se comenta proceso para consultar Ping, ya que se obtiene el Monto para Remesar...
			Thread tIsAlive = new Thread( new ThreadIsAlive() );
			tIsAlive.start();

			/***************************************************************/
			/************************Init Caja Nuevo***********************/
			/***************************************************************/

			//TODO cbriones: Inicializacion de Caja Claro !!
			//ServerProxy pr = Proxy.getProxyInstance();
			AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();

			//HeaderIn hIn = new HeaderIn();
			HeaderDTO hIn = new HeaderDTO();

			/**
		hIn.setAgencia((int)(pSet.getLongValue("Agencia")));
		hIn.setCajaFisica((int)(pSet.getLongValue("Caja")));
		hIn.setEntidad((int)(pSet.getLongValue("Entidad")));
			 */

			// Se enviaran solo 3 datos !!
			Base.logger.info("Inicializando Caja...");
			hIn.setAgencia(pSet.getStringValue("Agencia"));
			Base.logger.info("Agencia: "+hIn.getAgencia());
			hIn.setCajaFisica(pSet.getStringValue("Caja"));
			Base.logger.info("Caja: "+hIn.getCajaFisica());
			hIn.setEntidad(pSet.getStringValue("Entidad"));
			Base.logger.info("Entidad: "+hIn.getEntidad());

			//hIn.setCajero(pSet.getStringValue("Cajero"));
			//Base.logger.info("Cajero: "+hIn.getCajero());
			//hIn.setRecaudador(pSet.getStringValue("Recaudador"));
			//Base.logger.info("Recaudador: "+hIn.getRecaudador());
			//hIn.setSession(pSet.getStringValue("SessionId"));
			//Base.logger.info("SessionId: "+hIn.getSession());
			//hIn.setUsuario(pSet.getStringValue("Usuario"));
			//Base.logger.info("Usuario: "+hIn.getUsuario());

			//InicializaCajaOut resp = null;
			InicializarCajaDTO resp = null;

			try {
				resp = pr.inicializarCaja(hIn);
				Base.logger.info("Resp: "+resp.getRetCode());
				Base.logger.info("Resp msj: "+resp.getRetDesc());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null, "Error en servicio de inicialización, se cerrará la aplicación.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				Tools.logStackTrace(Base.logger, e);
				System.exit(0);
			}

			//if(resp.getParametros() == null){
			if(resp.getParametrosCaja() == null){
				//Base.logger.error("No existen parámetros");
				Base.logger.error("Codigo Retorno: "+resp.getRetCode());
				Base.logger.error("Mensaje Retorno: "+resp.getRetDesc());
				JOptionPane.showMessageDialog(null, "Error en servicio de inicialización, se cerrará la aplicación.", "Continuar", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}



			// Obtiene los parametros para insertar en archivo locales de configuraciones.
			ParamSet paramSet = null;
			for(int i = 0 ; i < resp.getParametrosCaja().length; i++){				
				boolean ok = true;
				Base.logger.error("Parametro dominio: "+resp.getParametrosCaja()[i].getDominio());
				//pSet = Base.getParamSet(resp.getParametrosCaja()[i].getDominio());
				//pCfg = Base.getParamSet(resp.getParametrosCaja()[i].getDominio());

				if("posDat".equalsIgnoreCase(resp.getParametrosCaja()[i].getDominio())){
					paramSet = pSet;
				}else if("posCfg".equalsIgnoreCase(resp.getParametrosCaja()[i].getDominio())){
					paramSet = pCfg;
				}else{
					paramSet = Base.getParamSet(resp.getParametrosCaja()[i].getDominio());
				}

				String field = resp.getParametrosCaja()[i].getNombre();
				Base.logger.error("Parametro field (nombre): "+field);

				String valor = resp.getParametrosCaja()[i].getValor();
				Base.logger.error("Parametro valor (valor): "+valor);

				//if(pSet == null){
				if(paramSet == null){
					Base.logger.error("el archivo de parametros no es valido...");
					ok = false;
					continue;
				}

				// Base.logger.error("cierreCaja: "+pCfg.getStringValue( "cierreCaja" ));

				// if( ok && !pSet.getStringValue(field).equals(valor) ){
				if( ok && !paramSet.getStringValue(field).equals(valor) ){
					// pSet.setValue( field , valor );
					paramSet.setValue( field , valor );
				}

				// Base.logger.error("montoRemesa: "+paramSet.getStringValue( "montoRemesa" ));

			}	
			
//			Thread tSAF = new Thread( new ThreadOperSaf(paramSet) );
//			tSAF.start();
//
//			revisarPayments();
//			revisarReversas();
//			revisarReversasPago();
//			Tools.revisarVoucherTBK();

			// TODO: Aca se envia a imprimir algo para inicializar la Impresora..
			inicializaImpresora();


			ParamSet posCfg = Base.getParamSet("posDat");
			
			// [REspinoza] Tbk pide que no se haga cierre inicial
//			if( posCfg.getStringValue( "transbank" ).equals("si") ){
//				try {
//
//				tbk.inicializa(pSet.getStringValue("TransbankConfig"));
//				
//				TbkMetodos metodos = new TbkMetodos();
//    			String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
//    			metodos.cierre(tbk,requerimiento);
//    			metodos.confirmarOperacion(tbk);
//    			
//				} catch(Exception e) {
//					logger.info("Error Cierre Transbank inicial, " + e.getMessage());
//					e.printStackTrace();
//					JOptionPane.showMessageDialog(null, "Error cierre inicial Transbank, se cerrara la aplicación", "Info", JOptionPane.INFORMATION_MESSAGE);
//					System.exit(0);
//				}
//
//			}

			ParamSet recargaDat = Base.getParamSet("recargaDat");
			if(!recargaDat.getStringValue("FechaPago").equals(posCfg.getStringValue("FechaPago"))){
				recargaDat.setValue( "NumeroSecuencia", 1 );
				recargaDat.setValue( "FechaPago", posCfg.getStringValue("FechaPago") );
				recargaDat.save();
			}
			pSet.setValue( "AbiertaSinConexion", "no" );	
			pSet.save();
			logger.info("Creando Directorio para transacciones Contingencia......");

			File directorioOffline = new File(pCfg.getStringValue("ValidaOfflineDir"));

			if (!directorioOffline.exists()){
				directorioOffline.mkdir();
				logger.info("Directorio SafOffline Creado");
			}else{
				logger.info("Directorio SafOffline Ya Existe..!!!!");

			}

			File directorioOfflineCvs = new File(pCfg.getStringValue("ValidaOfflineDirCvs"));

			if (!directorioOfflineCvs.exists()){
				directorioOfflineCvs.mkdir();
				logger.info("Directorio Cvs Creado");
			}else{
				logger.info("Directorio Cvs Ya Existe..!!!!");

			}

			File directorioOfflineBackUp = new File(pCfg.getStringValue("ValidaOfflineBackUp"));

			if (!directorioOfflineBackUp.exists()){
				directorioOfflineBackUp.mkdir();
				logger.info("Directorio BackUp Creado");
			}else{
				logger.info("Directorio BackUp Ya Existe..!!!!");

			}

			File directorioOfflineCierres = new File(pCfg.getStringValue("ValidaOfflineDirCierres"));

			if (!directorioOfflineCierres.exists()){
				directorioOfflineCierres.mkdir();
				logger.info("Directorio de Cierres Creado");
			}else{
				logger.info("Directorio de Cierres Ya Existe..!!!!");

			}

			File directorioOfflineBackUpErrores = new File(pCfg.getStringValue("ValidaOfflineBackUpErrores"));

			if (!directorioOfflineBackUpErrores.exists()){
				directorioOfflineBackUpErrores.mkdir();
				logger.info("Directorio de Cierres Creado");
			}else{
				logger.info("Directorio de Cierres Ya Existe..!!!!");

			}

		}else{
			logger.info("Iniciando Caja Claro Modo Contingencia......");
			pos = new BasePos();
			pos.init();  
			operTRV = new OperTRV();
			logger.info("Creando Directorio para transacciones Contingencia......");



			File directorioOffline = new File(pCfg.getStringValue("ValidaOfflineDir"));

			if (!directorioOffline.exists()){
				directorioOffline.mkdir();
				logger.info("Directorio SafOffline Creado");
			}else{
				logger.info("Directorio SafOffline Ya Existe..!!!!");

			}

			File directorioOfflineCvs = new File(pCfg.getStringValue("ValidaOfflineDirCvs"));

			if (!directorioOfflineCvs.exists()){
				directorioOfflineCvs.mkdir();
				logger.info("Directorio Cvs Creado");
			}else{
				logger.info("Directorio Cvs Ya Existe..!!!!");

			}

			File directorioOfflineBackUp = new File(pCfg.getStringValue("ValidaOfflineBackUp"));

			if (!directorioOfflineBackUp.exists()){
				directorioOfflineBackUp.mkdir();
				logger.info("Directorio BackUp Creado");
			}else{
				logger.info("Directorio BackUp Ya Existe..!!!!");

			}


			File directorioOfflineCierres = new File(pCfg.getStringValue("ValidaOfflineDirCierres"));

			if (!directorioOfflineCierres.exists()){
				directorioOfflineCierres.mkdir();
				logger.info("Directorio de Cierres Creado");
			}else{
				logger.info("Directorio de Cierres Ya Existe..!!!!");

			}


			File directorioOfflineBackUpErrores = new File(pCfg.getStringValue("ValidaOfflineBackUpErrores"));

			if (!directorioOfflineBackUpErrores.exists()){
				directorioOfflineBackUpErrores.mkdir();
				logger.info("Directorio de Cierres Creado");
			}else{
				logger.info("Directorio de Cierres Ya Existe..!!!!");

			}


			ArchivoCuadratura.crearArchivoCuadratura();
			inicializaImpresora();
			Thread tIsAlive = new Thread( new ThreadIsAlive() );
			tIsAlive.start();
			pSet.setValue( "AbiertaSinConexion", "no" );	
			pSet.save();

		}
	}

	public class MyFilter implements FilenameFilter {
		public boolean accept( File dir, String name ) {
			if( name.endsWith( ".ctl" ) )
				return( true );
			else
				return( false );
		}
	}

	private void revisarReversas() {
		ParamSet posCfg = Base.getParamSet( "posCfg" );
		String fileDat = posCfg.getStringValue("ReversaPendienteDir") + "ReversaPendiente.dat";
		ObjectInputStream in = null;
		Servicio servicio = null;             

		try {
			in = new ObjectInputStream(new FileInputStream(fileDat));
			servicio = (Servicio) in.readObject();
			in.close();
			in = null;
			servicio.execute();
		} catch (Exception e) {
			return;
		}

		Servicio.borraReversaPendiente();        
	}

	public static void revisarReversasPago() {
		File outFile = null;
		ObjectInputStream in = null;
		Object o = null;
		ParamSet posCfg = Base.getParamSet( "posCfg" );
		try{

			try{
				in = new ObjectInputStream(new FileInputStream(posCfg.getStringValue("VoucherDir") + "reversaPago.dat"));
				o = in.readObject();
				in.close();
				in = null;
			}catch(Exception e){
				Base.logger.error("Error en la carga del objeto serializado desde reversaPago.dat");
				return;
			}

			//if(o.getClass() == OperacionIn.class){
			if(o.getClass() == Operacion.class){	
				//Serializa.serializa((OperacionIn)o, "SAF");    
				Serializa.serializa((Operacion)o, "SAF");  
			}
		}
		catch(Exception e){
			Tools.logStackTrace(Base.logger, e);
		}
		finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					Tools.logStackTrace(Base.logger, e);
				}
			}             
		}

		//Borramos del disco la reversa y los duplicados del voucher
		outFile = new File( posCfg.getStringValue("VoucherDir") + "reversaPago.dat" );
		outFile.delete();

		outFile = new File( posCfg.getStringValue("VoucherDir") + "reversaPago.ctl" );
		outFile.delete();

		outFile = new File( posCfg.getStringValue("VoucherDir") + "boletaCliente.dat" );
		outFile.delete();

		outFile = new File( posCfg.getStringValue("VoucherDir") + "boletaLocal.dat" );
		outFile.delete();
		
		outFile = new File( posCfg.getStringValue("VoucherDir") + "boletaTbk.dat" );
		outFile.delete();
	}

	/**
	 * metodo para imprimir algo como forma de inicializar la Impresora !!
	 */
	public static void inicializaImpresora() {

		ArrayList<LineaVoucher> boleta = new ArrayList<LineaVoucher>();
		LineaVoucher oper = new LineaVoucher();
//		ParamSet pList = Base.getParamSet( "posDat" ); 
		oper.setLinea("Caja Inicializada....");
		oper.setBold(true);
		oper.setCenter(true);
		boleta.add(oper);
		for(int i = 0; i < 3 ; i++){
			oper = new LineaVoucher();
			oper.setLinea("");
			boleta.add(oper);
		}
		
		ParamSet posDat = Base.getParamSet( "posDat" );
    	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
    	//isPdf = true;
    	if(!isPdf ) {
    		Voucher.printVoucher(boleta,true,Voucher.COPIA_INICIALIZACION,null);
    		return;
    	}
		 
	}

	private void revisarPayments(){
		// se revisa el directorio en cuestion
		ParamSet posCfg = Base.getParamSet("posCfg");
		File fDir = new File( posCfg.getStringValue("PaymentDir") );
		String[] lista = fDir.list( new MyFilter() );

		if( lista.length <= 0 ) {
			return;
		}
		String sName = lista[0].substring( 0, lista[0].length() - 4 );
//		File outFile = null;
		ObjectInputStream in = null;
		Object o = null;
		String fileDat = posCfg.getStringValue("PaymentDir") + sName + ".dat";
		try{                
			in = new ObjectInputStream(new FileInputStream(fileDat));
			o = in.readObject();
			in.close();
			in = null;
		}catch(Exception e){
			Base.logger.error("Error en la carga del Carro de compras desde " + fileDat);            
		}
		if( o == null){
			new File( posCfg.getStringValue("PaymentDir") + sName + ".ctl" ).delete();
			new File( posCfg.getStringValue("PaymentDir") + sName + ".dat" ).delete();
			return;
		}
		CarroMPagos carro = (CarroMPagos)o;
		for( int i = 0; i < carro.getMediosPago().size(); i++){
			try {
				carro.getPago(i).reversar(true);
			} catch (MedioPagoException e) {
				Tools.logStackTrace(Base.logger, e);
			}
		}
		new File( posCfg.getStringValue("PaymentDir") + sName + ".ctl" ).delete();
		new File( posCfg.getStringValue("PaymentDir") + sName + ".dat" ).delete();
	}

	/**
	 * Funcion que retorna a partir de un nombre de parametro, su estructura correspondiente de ParamSet
	 * @param name
	 * @return
	 */
	public static ParamSet getParamSet( String name ) {
		for( int i = 0; i < paramSets.size(); i++ ) {
			if( name.equals( defParamSets.get( i ).getName() ) )
				return paramSets.get( i );
		}

		return null;
	}

	/**
	 * Funcion que retorna a partir de un nombre de parametro, su estructura correspondiente de DefServicio
	 * @param nombre
	 * @return
	 */
	public static DefServicio getDefServicio( String nombre ) {
		for( int i = 0; i < tablaServicios.size(); i++ ) {
			DefServicio dS = tablaServicios.get( i );
			if( dS.getName().equals( nombre ) ) 
				return dS;
		}

		return null;

	}

	/**
	 * Funcion que retorna a partir de un nombre de parametro, su estructura correspondiente de DefMedioPago
	 * @param nombre
	 * @return
	 */
	public static DefMedioPago getDefMedioPago( String nombre ) {
		for( int i = 0; i < tablaMediosPago.size(); i++ ) {
			DefMedioPago dMP = tablaMediosPago.get( i );
			if( dMP.getName().equals( nombre ) ) {
				return dMP;
			}
		}

		return null;

	}

	/**
	 * Funcion que retorna a partir de un nombre de parametro, su estructura correspondiente de DefRecord
	 * @param nombre
	 * @return
	 */
	public static DefRecord getDefRecord( String nombre ) {
		for( int i = 0; i < tablaRecords.size(); i++ ) {
			DefRecord dF = tablaRecords.get( i );
			if( dF.getName().equals( nombre ) ) {
				return dF;
			}
		} 

		return null;
	}

	/**
	 * Funcion que retorna a partir de un nombre de parametro, su estructura correspondiente de DefVoucher
	 * @param name
	 * @return
	 */
	public static DefVoucher getDefVoucher( String name ) {
		for( int i = 0; i < tablaVouchers.size(); i++ ) {
			DefVoucher dV = tablaVouchers.get( i );
			if( dV.getName().equals( name ) ) {
				return dV;             
			}
		}

		return null;
	}

	public static DefDocumentoPago getDefDocumentoPago( String name ) {
		for( int i = 0; i < tablaDocumentos.size(); i++ ) {
			DefDocumentoPago dDoc = tablaDocumentos.get( i );
			if( dDoc.getName().equals( name ) )
				return dDoc;
		}

		return null;
	}

	/**
	 * Funcion que entrega un arreglo de strings que contiene la lista de bancos y codigos de banco permitidos para
	 * la aplicacion
	 * @return
	 */
	public static String[] getBancos(){
		ParamSet banco = Base.getParamSet("bancos");
		int pos = 0;
		while(!banco.getDatos().getStringValue("banco_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("banco_" + (i+1));
		}
		return bancos;        
	}

	/**
	 * Funcion que entrega un arreglo de strings que contiene la lista de bancos y codigos 
	 * de banco desde web service impelentado
	 * Si existe fallo, obtiene la informacion desde bancos.cfg
	 * @return
	 */
	public static String[] traeBancos(){
		Base.logger.info("Obteniendo Bancos desde Servicio...");
		// TODO aca se debe implementar invocacion al servicio dependiendo del codigo de error retornado..
		ParamSet posDat = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy prAut = AppControlProxy.getProxyInstance();
		ConsultarBancosOutDTO bancos = new ConsultarBancosOutDTO();

		HeaderDTO head = new HeaderDTO();
		head.setAgencia(posDat.getStringValue("Agencia"));
		head.setCajaFisica(posDat.getStringValue("Caja"));
		head.setCajero(posDat.getStringValue("Cajero"));
		head.setEntidad(posDat.getStringValue("Entidad"));
		head.setRecaudador(posDat.getStringValue("CodigoRecaudador"));
		head.setSession(posDat.getStringValue("SessionId"));
		head.setUsuario(posDat.getStringValue("Usuario"));

		try {
			bancos = prAut.consultarBancos(head);

			Base.logger.info("Codigo respuesta bancos: " + bancos.getRetCode());
			Base.logger.info("Descripcion respuesta bancos. "+bancos.getRetDesc());

			if(!"0".equalsIgnoreCase(bancos.getRetCode())){
				Base.logger.error("El servicio de bancos fallo, se debe obtener desde properties...");
				return Base.getBancos();
			}

			// Decodificar el arreglo de dtos hacia el arreglo de String..
			String[] bancoDto = new String[bancos.getBancos().length];
			for(int i=0; i<bancos.getBancos().length; i++){
				bancoDto[i] = bancos.getBancos()[i].getCodBanco()+","+bancos.getBancos()[i].getNombre();
			}

			return bancoDto;

		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
			Base.logger.error("El execute del servicio para bancos retorno error o timeout");
			return Base.getBancos();
		}   

	}

	/**
	 * Funcion que entrega un arreglo de strings que contiene la lista de tarjetas Multitiendas 
	 * desde web service impelentado
	 * 
	 * @return
	 */
	public static String[] traeTarjetas(){
		Base.logger.info("Obteniendo tarjetas multitiendas desde Servicio...");

		ParamSet posDat = Base.getParamSet("posDat");
		AppControlCajaWSServerProxy prAut = AppControlProxy.getProxyInstance();
		ConsultarTarjetasMTOutDTO tarjeta = new ConsultarTarjetasMTOutDTO();

		HeaderDTO head = new HeaderDTO();
		head.setAgencia(posDat.getStringValue("Agencia"));
		head.setCajaFisica(posDat.getStringValue("Caja"));
		head.setCajero(posDat.getStringValue("Cajero"));
		head.setEntidad(posDat.getStringValue("Entidad"));
		head.setRecaudador(posDat.getStringValue("CodigoRecaudador"));
		head.setSession(posDat.getStringValue("SessionId"));
		head.setUsuario(posDat.getStringValue("Usuario"));

		try {
			tarjeta = prAut.listaTarjetasMultitienda(head);

			Base.logger.info("Codigo respuesta bancos: " + tarjeta.getRetCode());
			Base.logger.info("Descripcion respuesta bancos. "+tarjeta.getRetDesc());

			if(!"0".equalsIgnoreCase(tarjeta.getRetCode())){
				Base.logger.error("El servicio de tarjetas fallo, se debe obtener desde properties...");
				return Base.getEmisores();
			}

			// Decodificar el arreglo de dtos hacia el arreglo de String..
			String[] tarjetaDto = new String[tarjeta.getTajetasMultiTienda().length];
			for(int i=0; i<tarjeta.getTajetasMultiTienda().length; i++){
				tarjetaDto[i] = tarjeta.getTajetasMultiTienda()[i].getCodTarjeta()+","+tarjeta.getTajetasMultiTienda()[i].getNombre();
			}

			return tarjetaDto;

		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
			Base.logger.error("El execute del servicio para tarjetas retorno error o timeout");
			return Base.getEmisores();
		}   

	}

	public static String[] getBancosCorto(){
		ParamSet banco = Base.getParamSet("bancosCorto");
		int pos = 0;
		while(!banco.getDatos().getStringValue("banco_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("banco_" + (i+1));
		}
		return bancos;        
	}

	public static String getBancoCodigo(int codigo){
		String []bancos = Base.getBancos();
		for(int i = 0 ; i < bancos.length ; i++){
			String []aux = bancos[i].split("\\,");
			if(Integer.parseInt(aux[0]) == codigo){
				String []aux1 = aux[1].split("Banco");
				return aux1[1];
			}
		}
		return "";
	}

	public static String getBancoCodigoCorto(int codigo){
		String []bancos = Base.getBancosCorto();
		for(int i = 0 ; i < bancos.length ; i++){
			String []aux = bancos[i].split("\\,");
			if(Integer.parseInt(aux[0]) == codigo){                
				return aux[1];
			}
		}
		return "";
	}

	public static String[] getEmisores(){
		ParamSet banco = Base.getParamSet("emisores");
		int pos = 0;
		while(!banco.getDatos().getStringValue("emisor_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("emisor_" + (i+1));
		}
		return bancos;        
	}

	public static String[] getEmisoresMulti(){
		ParamSet banco = Base.getParamSet("emisoresMulti");
		int pos = 0;
		while(!banco.getDatos().getStringValue("emisor_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("emisor_" + (i+1));
		}
		return bancos;        
	}

	public static String[] getEmisoresCredito(){
		ParamSet tarjetaCred = Base.getParamSet("emisoresCredito");
		int pos = 0;
		while(!tarjetaCred.getDatos().getStringValue("emisor_" + (pos+1)).equals("")){
			pos++;
		}
		String []tarjetas = new String[pos];

		for(int i = 0; i < pos; i++){ 
			tarjetas[i] = tarjetaCred.getDatos().getStringValue("emisor_" + (i+1));
		}
		return tarjetas;        
	}
	
    public static String[] getEmisoresDebito(){
        ParamSet tarjetaCred = Base.getParamSet("emisoresDebito");
        int pos = 0;
        while(!tarjetaCred.getDatos().getStringValue("emisor_" + (pos+1)).equals("")){
            pos++;
        }
        String []tarjetas = new String[pos];
        
        for(int i = 0; i < pos; i++){ 
        	tarjetas[i] = tarjetaCred.getDatos().getStringValue("emisor_" + (i+1));
        }
        return tarjetas;        
    }

	public static String[] getEmisoresCasaComercial(){
		ParamSet tarjetaCred = Base.getParamSet("emisoresCasaComercial");
		int pos = 0;
		while(!tarjetaCred.getDatos().getStringValue("emisor_" + (pos+1)).equals("")){
			pos++;
		}
		String []tarjetas = new String[pos];

		for(int i = 0; i < pos; i++){ 
			tarjetas[i] = tarjetaCred.getDatos().getStringValue("emisor_" + (i+1));
		}
		return tarjetas;        
	}

	public static String[] getTotales(){
		ParamSet banco = Base.getParamSet("totales");
		int pos = 0;
		while(!banco.getDatos().getStringValue("total_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("total_" + (i+1));
		}
		return bancos;        
	}

	public static String[] getTotalesPagos(String tipo){
		ParamSet banco = Base.getParamSet("totalesPagos" + tipo);
		int pos = 0;
		while(!banco.getDatos().getStringValue("total_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("total_" + (i+1));
		}
		return bancos;        
	}

	public static String[] getDocs(String tipo){
		ParamSet banco = Base.getParamSet("tiposDocumentos" + tipo);
		int pos = 0;
		while(!banco.getDatos().getStringValue("doc_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("doc_" + (i+1));
		}
		return bancos;        
	}

	public static String[] getDias(){
		ParamSet banco = Base.getParamSet("diasCheque");
		int pos = 0;
		while(!banco.getDatos().getStringValue("dia_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = Integer.toString(banco.getDatos().getIntValue("dia_" + (i+1)));
		}
		return bancos;        
	}

	public static String[] getCuentas(){
		ParamSet banco = Base.getParamSet("cuentasContableTelefonica");
		int pos = 0;
		while(!banco.getDatos().getStringValue("cuenta_" + (pos+1)).equals("")){
			pos++;
		}
		String []bancos = new String[pos];

		for(int i = 0; i < pos; i++){ 
			bancos[i] = banco.getDatos().getStringValue("cuenta_" + (i+1));
		}
		return bancos;        
	}

	/**
	 * Funcion que retorna una columna que se genera apartir de un split de otro arreglo segun el indice recibido
	 * @param lista Arreglo a ser procesado
	 * @param separator Expresion por la cual se debe hacer el split de cada fila del arreglo
	 * @param col Columna a ser entregada en el return
	 * @return
	 */
	public static String[] getList(String []lista, String separator, int col){
		String []newLista = new String[lista.length];
		for(int i = 0; i < lista.length; i++){
			String []aux1 = lista[i].split(separator);
			newLista[i] = aux1[col].trim();
		}

		return newLista;
	}

	public static String getCampoList(String []lista, String separator, String campo){
		String []newLista = new String[lista.length];
		String []newListaNum = new String[lista.length];
		for(int i = 0; i < lista.length; i++){
			String []aux1 = lista[i].split(separator);
			newLista[i] = aux1[1].trim();
			newListaNum[i] = aux1[0].trim();
		}
		for(int i = 0 ; i < newListaNum.length ; i++){
			if(newListaNum[i].equals(campo)){
				return newLista[i];
			}
		}
		return "No Doc";
	}

	public static String getCampoList2(String []lista, String separator, String campo){
		String []newLista = new String[lista.length];
		String []newListaNum = new String[lista.length];
		for(int i = 0; i < lista.length; i++){
			String []aux1 = lista[i].split(separator);
			newLista[i] = aux1[1].trim();
			newListaNum[i] = aux1[0].trim();
		}
		for(int i = 0 ; i < newListaNum.length ; i++){
			if(Integer.parseInt(newListaNum[i]) == Integer.parseInt(campo)){
				return newLista[i];
			}
		}
		return "No Banco";
	}

	public static String[] getDiccionario(){
		ParamSet banco = Base.getParamSet("diccionario");
		int pos = 0;
		while(!banco.getDatos().getStringValue("total_" + (pos+1)).equals("")){
			pos++;
		}
		String []diccionario = new String[pos];

		for(int i = 0; i < pos; i++){ 
			diccionario[i] = banco.getDatos().getStringValue("total_" + (i+1));
		}
		return diccionario;        
	}

	public static String getTipoDoc(String []lista, String cod){
		int codN = 0;
		String codF = "";
		try{
			codN = Integer.parseInt(cod);
			codF = Integer.toString(codN);
		}
		catch(Exception e){
			codF = cod;
		}
		for(int i = 0; i < lista.length; i++){
			String []aux1 = lista[i].split(",");
			if(aux1[0].equals(codF)){
				return aux1[1];
			}
		}    
		return "";
	}

	public static int enviarTrxOffilne(Operacion operOffline){



		ParamSet pSet = Base.getParamSet("posDat");
		HeaderDTO hIn = new HeaderDTO();
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance(); 
		PlataformaPagoNotificarServerProxy prOffline = AppControlNotificarProxy.getProxyInstance();

		Caja caja = new Caja();
		NumeroOperacionOutDTO operOut = null;

		//HeaderIn hIn = new HeaderIn();

		Base.logger.info("Obteniendo data de la Caja...");

		hIn.setAgencia(pSet.getStringValue("Agencia"));
		Base.logger.info("Agencia: "+hIn.getAgencia());
		
		hIn.setCajaFisica(pSet.getStringValue("Caja"));
		Base.logger.info("Caja: "+hIn.getCajaFisica());
		
		hIn.setEntidad(pSet.getStringValue("Entidad"));
		Base.logger.info("Entidad: "+hIn.getEntidad());
		
		//	Toma los datos del cajero cuando entro Offline, no los del actual
		hIn.setCajero(pSet.getStringValue("CajeroEnOffline"));
		Base.logger.info("Cajero: "+hIn.getCajero());
		//	Toma los datos del cajero cuando entro Offline, no los del actual			
		hIn.setRecaudador(pSet.getStringValue("RecaudadorOffline"));
		Base.logger.info("Recaudador: "+hIn.getRecaudador());
		
//		hIn.setSession(pSet.getStringValue("SessionId"));
//		Base.logger.info("SessionId: "+hIn.getSession());
		//	Toma los datos del cajero cuando entro Offline, no los del actual
		// Se envía vacio para obtener una nueva session
		hIn.setSession("");
		Base.logger.info("SessionId: "+hIn.getSession());
		//	Toma los datos del cajero cuando entro Offline, no los del actual
		hIn.setUsuario(pSet.getStringValue("UsuarioEnOffline"));
		Base.logger.info("Usuario: "+hIn.getUsuario());

		//hIn.setAgencia(Integer.parseInt(this.getAgencia()));
		caja.setAgencia(pSet.getStringValue("Agencia"));
		Base.logger.info( "Agencia: " + caja.getAgencia());    
		//hIn.setCajaFisica(Integer.parseInt(this.getCaja()));
		caja.setIdCaja(Integer.parseInt(pSet.getStringValue("Caja")));
		Base.logger.info( "Caja: " + caja.getIdCaja());    
		//hIn.setEntidad(Integer.parseInt(this.getEntidad()));
		caja.setEntidad(pSet.getStringValue("Entidad"));
		Base.logger.info( "Entidad: " + caja.getEntidad());   

		//hIn.setCajero(Integer.parseInt(this.getCajero()));
		// No existe Cajero en DTO Caja !!

		//hIn.setSession(Integer.parseInt(this.getSesion()));
		// TODO se debe rescatar desde inicicalizacion de cja !!!
		//caja.setCodigoSesion(Long.parseLong(pSet.getStringValue("SessionId")));
		caja.setCodigoSesion(0l);
		Base.logger.info( "Session: " + caja.getCodigoSesion()); 

		//	Toma los datos del cajero cuando entro Offline, no los del actual		
		caja.setUsuario(pSet.getStringValue("CajeroEnOffline"));
		//caja.setUsuario(this.getRecaudador());
		Base.logger.info( "Usuario: " + caja.getUsuario()); 

		// TODO se debe rescatar desde inicicalizacion de cja !!!
		//	Toma los datos del cajero cuando entro Offline, no los del actual
		caja.setRecaudador(pSet.getStringValue("CodigoRecaudadorEnOffline"));
		Base.logger.info( "Recaudador: " + caja.getRecaudador()); 

		// TODO se debe rescatar desde inicicalizacion de cja !!!
		caja.setCanal(Integer.parseInt(pSet.getStringValue("Canal")));

		//caja.setCanal(1);
		Base.logger.info( "Canal: " + caja.getCanal()); 



		try {

			operOut= pr.numeroOperacion(hIn);

		} catch (RemoteException e2) {
			Tools.logStackTrace(Base.logger, e2);        
			Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
			JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
			return -1;
		}  

		//if(operOut.getHeaderOut().getRc() != 0 || operOut.getNumeroOperacion() < 0){   
		if(!("0").equalsIgnoreCase(operOut.getRetCode()) || operOut.getNumeroOperacion() < 0){
			Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
			JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
			return -1;
		}


		operOffline.setNumeroOperacion(operOut.getNumeroOperacion());
		operOffline.setCaja(caja);
		operOffline.setFechaPago(Tools.getFecha());

		NotificacionEnvio notif = new NotificacionEnvio();
		notif.setOperacion(operOffline);
		NotificacionRespuesta resp = null;
		try {
			// TODO aca debe invocar a Notificar !!!
			//resp = pr.envioOperacion(oIn);
			resp = prOffline.notificar(notif);
			Base.logger.info("Codigo Notificacion: "+resp.getRespuesta().getRetCode());
			Base.logger.info("Msje Notificacion: "+resp.getRespuesta().getRetDesc());

			if( resp.getRespuesta().getRetCode() != 0){
				return -2;
			}

		} catch (RemoteException e) {
			Tools.logStackTrace(Base.logger, e);
			//return -100;
			return -1;
		}

		return 0;
	}

	public static Operacion procesarArchivoOffline(String fileName){


		ParamSet pSet = Base.getParamSet( "posCfg" );
		String odir = pSet.getStringValue( "OperSafOfflineDir" );      
//		File outFile = null;
		ObjectInputStream in = null;
//		Object o = null;
		Operacion datos= new Operacion();
//		ArrayList <Operacion>trxs = new ArrayList<Operacion>();
		// TODO cbriones: se rescatan los codigos que se deben eliminar del SAF
//		String codSaf = pSet.getStringValue( "codigosSaf" );   
//		String codigos[] = codSaf.split(",");

//		String fileDat="OperOffline_" + Tools.getFechaYYYYMMDD() + ".dat";

		try{
			in = new ObjectInputStream(new FileInputStream(odir + fileName));
			//  while( (o = in.readObject()) != null){
			//	 trxs.add((Operacion)o);
			//}
			datos= (Operacion) in.readObject();
			in.close();
			in = null;
			Base.logger.info("Leido correctamente archivo de Notificacion Saf....");
		}catch(Exception e){
			e.printStackTrace();
			Base.logger.error("Error en la carga del OperTrv desde " + fileName);
			return datos;
		}


		return datos;
	}


	public static OperTRV getOperTRV() {
		return operTRV;
	}
	public static void setOperTRV(OperTRV oper){
		operTRV = oper;
	}
	public static void setEdicion(boolean opt){
		Base.isEdicion = opt;
	}
	public static boolean getEdicion(){
		return Base.isEdicion;
	}

	public void procesaArchivo(String fileCtl, String fileDat, String oDir, String codigos[]){
		File outFile = null;
		ObjectInputStream in = null;
		Object o = null;

		try{

			try{
				in = new ObjectInputStream(new FileInputStream(oDir + fileDat));
				o = in.readObject();
				in.close();
				in = null;
				Base.logger.info("Leido correctamente archivo de Notificacion Saf....");
			}catch(Exception e){
				Base.logger.error("Error en la carga del OperTrv desde " + fileDat);
				return;
			}

//			int resp = -1;
			//Response response = null;
			NotificacionRespuesta response = null;

			//if(o.getClass() == OperacionIn.class){
			if(o.getClass() == Operacion.class){	
				response = null;
				//ServerProxy pr = Proxy.getProxyInstance();
				PlataformaPagoNotificarServerProxy pr =  AppControlNotificarProxy.getProxyInstance();
				Base.logger.info("Enviando Notificacion Saf....");
				Operacion op = null;
				try {
					// TODO aca se debe enviar el DTO correspondiente a Claro !!
					//response = pr.envioOperacion((OperacionIn)o);
					NotificacionEnvio notif = new NotificacionEnvio();
					notif.setOperacion((Operacion)o);
					op = (Operacion)o;
					Base.logger.info("op NumeroOperacion: "+op.getNumeroOperacion());
					Base.logger.info("op TipoOperacion: "+op.getTipoOperacion());
					Base.logger.info("op Monto: "+op.getMonto());

					response = pr.notificar(notif);
				} catch (RemoteException e) {
					Tools.logStackTrace(Base.logger, e);
					return;
				}
			}

			//if(response.getHeaderOut().getRc() == 0 || response.getHeaderOut().getRc() == 1){
			// TODO validar que codigos de retorno se deben validar para una Operacion exitosa ??
			if(response.getRespuesta().getRetCode() == 0 || response.getRespuesta().getRetCode() == 1){
				Base.logger.info("Enviado Saf " + fileCtl + " " + new Date());
				Base.logger.info("Enviado Saf " + fileDat + " " + new Date());
				outFile = new File( oDir + fileCtl );
				outFile.delete();
				outFile = new File( oDir + fileDat );
				outFile.delete(); 
			}else{
				Base.logger.error("Codigo Retorno Saf: " + response.getRespuesta().getRetCode());
				Base.logger.error("Msj Retorno Saf: " + response.getRespuesta().getRetDesc());

				// TODO cbriones: Validar para el caso de Codigo -14 (No existe Nro. de Operacion), 
				// se debe eliminar el SAF ..
				// Validar si la cadena de codigos contiene el retornado desde App de Control..
				for(int i=0; i<codigos.length; i++){
					Base.logger.info("Codigo lista: "+codigos[i]);
					if(codigos[i].equalsIgnoreCase(String.valueOf(response.getRespuesta().getRetCode()))){
						// Corresponde a un Codigo de la lista..  Eliminar !!!!!
						Base.logger.info("Enviado Saf (lista) " + fileCtl + " " + new Date());
						Base.logger.info("Enviado Saf (lista) " + fileDat + " " + new Date());
						outFile = new File( oDir + fileCtl );
						outFile.delete();
						outFile = new File( oDir + fileDat );
						outFile.delete(); 
					}
				}
			}

		}
		catch(Exception e){
			Tools.logStackTrace(Base.logger, e);
		}
		finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					Tools.logStackTrace(Base.logger, e);
				}
			}             
		}
	}
	
	/**
	 * Funcion que entrega un arreglo de strings que contiene la lista de las sociedades Claro y sus codigos permitidos para
	 * la aplicacion
	 * @return
	 */
	public static String[] getSociedades(){
		ParamSet sociedad = Base.getParamSet("sociedades");
		int pos = 0;
		while(!sociedad.getDatos().getStringValue("sociedad_" + (pos+1)).equals("")){
			pos++;
		}
		String []sociedades = new String[pos];

		for(int i = 0; i < pos; i++){ 
			sociedades[i] = sociedad.getDatos().getStringValue("sociedad_" + (i+1));
		}
		return sociedades;        
	}
}
