package cl.hyh.redpagos.caja.base;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarOperacionOutDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.MedioPagoDTO;
import ws.claro.cl.NumeroOperacionOutDTO;
import ws.claro.cl.TransaccionDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.clarochile.osbservicios.PlataformaPagoNotificar.Transaccion;
import cl.hyh.cajas.ws.impl.ConsultaOperacionOut;
import cl.hyh.cajas.ws.impl.MedioPagoCaja;
import cl.hyh.cajas.ws.impl.TransaccionCaja;
import cl.hyh.redpagos.caja.docpago.DocumentoAbonoVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoCuentaVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoItemClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoServicioVtr;
import cl.hyh.redpagos.caja.docpago.DocumentoVtr;
import cl.hyh.redpagos.caja.docpago.RemesaChequeDia;
import cl.hyh.redpagos.caja.docpago.RemesaChequeFecha;
import cl.hyh.redpagos.caja.docpago.RemesaEfectivo;
import cl.hyh.redpagos.caja.mpago.AjusteSencillo;
import cl.hyh.redpagos.caja.mpago.Cheque;
import cl.hyh.redpagos.caja.mpago.ChequeFecha;
import cl.hyh.redpagos.caja.mpago.Deposito;
import cl.hyh.redpagos.caja.mpago.Efectivo;
import cl.hyh.redpagos.caja.mpago.OtroMedioPago;
import cl.hyh.redpagos.caja.mpago.Tarjeta;
import cl.hyh.redpagos.caja.mpago.TarjetaManual;
import cl.hyh.redpagos.caja.mpago.Transferencia;
import cl.hyh.redpagos.caja.mpago.ValeVista;

/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Tools {
	
	private static Pattern numericoPattern = Pattern.compile("\\d+");

	/**
	 * Método para eliminar los blancos de la derecha de un objeto String.
	 * @param s String a procesar.
	 * @return Retorna un objeto String sin los blancos por la derecha.
	 */
	public static String rtrim( String s ) {
		int len = s.length();
		if( len == 0 )
			return s;
		while( s.charAt( len-1 ) == ' ' ) {
			s = s.substring( 0, len-1 );
			len--;
			if( len <= 0 )
				break;
		}
		return( s );
	}

	public static String  rigthAlign( int toLen, String from, char fByte ) {

		int l = toLen - from.length();

		if( l < 0 )
			return from.substring( from.length() - toLen );

		byte[] bb = new byte[l];

		for( int i = 0; i < l; i++ )
			bb[i] = (byte) fByte;

		return new String( bb ) + from;
	}

	/**
	 * Método que calcula el número del siguiente día al actual. 
	 * @return Retorna el día en entero (1-31).
	 */
	public static int Tomorrow()  {
		// Se obtiene la fecha actual
		Calendar c = Calendar.getInstance();
		// Se le agrega un día
		c.add( Calendar.DAY_OF_MONTH, 1 );
		// Se obtiene el dia en formato "dd"
		SimpleDateFormat sdf = new SimpleDateFormat( "dd" );
		String str = sdf.format( c.getTime() );
		return new Integer( str ).intValue();  
	}

	public static String getNewFecha(int dias)  {
		// Se obtiene la fecha actual
		Calendar c = Calendar.getInstance();
		// Se le agrega un día
		c.add( Calendar.DAY_OF_MONTH, dias );
		// Se obtiene el dia en formato "dd"
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
		String str = sdf.format( c.getTime() );
		return str;  
	}

	/**
	 * Método que convierte un número de tipo long a formato editable.
	 * @param iMonto Valor númerico a editar.
	 * @param len Largo máximo para valor editado.
	 * @return Retorna un objeto String con el número editado.
	 */
	public static String editaNumero( long iMonto, int len ) {
		// Genero formato decimal
		long montoN = iMonto;
		DecimalFormat df = new DecimalFormat();
		String mascara = "";
		for( int i = 0; i < len; i++ )
			mascara = mascara + "0";
		df.applyPattern( mascara );
		// Aplico formato decimal
		String res = df.format( montoN );
		int l = res.length();
		// Retorno String generado
		if( l > len )
			return res.substring( l - len, l );
		else
			return res;
	}

	/**
	 * Método que convierte un número de tipo int a formato editable.
	 * @param iMonto Valor númerico a editar.
	 * @param len Largo máximo para valor editado.
	 * @return Retorna un objeto String con el número editado.
	 */
	public static String editaNumero( int iMonto, int len ) {
		// Genero formato decimal
		int montoN = iMonto;
		DecimalFormat df = new DecimalFormat();
		String mascara = "";
		for( int i = 0; i < len; i++ )
			mascara = mascara + "0";
		df.applyPattern( mascara );
		// Aplico formato decimal
		String res = df.format( montoN );
		int l = res.length();
		// Retorno String generado
		if( l > len )
			return res.substring( l - len, l );
		else
			return res;
	}

	/**
	 * Método que obtiene un substring dado un largo específicado.
	 * Si el largo excede el largo del String entregado como parámetro
	 * se rellena el faltente con blancos por la derecha.
	 * @param s String a procesar.
	 * @param len Largo del String requerido.
	 * @return Retorna un objeto String con el largo especificado.
	 */
	public static String toFix( String s, int len ) {
		// Controlo si es nulo
		if ( s == null )
			s = "";
		// Calculo largo
		int lenS = s.length();
		String fixS;
		// Se valida si se excede el largo del String original
		if( len > lenS ) {
			fixS = s;
			// Se rellena con " " por la derecha
			for( int i = 0; i < len - lenS; i++ )
				fixS += " ";
		}
		else
			fixS = s.substring( 0, len );
		return fixS;
	}

	/**
	 * Método que obtiene fecha actual en formato yyyyMMddHHmmss.
	 * @return Retorna fecha y hora en formato yyyyMMddHHmmss.
	 */
	public static String getFechaHora() {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss" );
		String sDate = formatter.format( new Date() );
		return( sDate );                
	}

	/**
	 * Método que convierte fecha y hora entregado como parámetro
	 * a formato yyyyMMddHHmmss.
	 * @param date Fecha y hora a procesar.
	 * @return Retorna fecha y hora en formato yyyyMMddHHmmss.
	 */
	public static String getFechaHora( Date date ) {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss" );
		String sDate = formatter.format( date );
		return( sDate );                
	}

	/**
	 * Método que obtiene fecha actual en formato yyyyMMdd.
	 * @return Retorna fecha en formato yyyyMMdd.
	 */
	public static String getFecha() {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
		String sDate = formatter.format( new Date() );
		return( sDate );                
	}
	
	/**
	 * Método que obtiene fecha actual en formato dd-MM-yyyy.
	 * 
	 * @return Retorna fecha en formato dd-MM-yyyy.
	 */
	public static String getFechaDDMMYYYY() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String sDate = formatter.format(new Date());
		return (sDate);
	}


	/**
	 * Método que convierte fecha y hora entregado como parámetro
	 * a formato yyyyMMdd.
	 * @param date Fecha y hora a procesar.
	 * @return Retorna fecha en formato yyyyMMdd.
	 */
	public static String getFecha( Date date ) {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd" );
		String sDate = formatter.format( date );
		return( sDate );                
	}

	/**
	 * Método que obtiene fecha actual en formato yyMMdd.
	 * @return Retorna fecha en formato yyMMdd.
	 */
	public static String getFechaYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyMMdd" );
		String sDate = formatter.format( new Date() );
		return( sDate );                
	}
	public static String getFechaYYYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd" );
		String sDate = formatter.format( new Date() );
		return( sDate );                
	}

	/**
	 * Método que convierte fecha y hora entregado como parámetro
	 * a formato yyMMdd.
	 * @param date Fecha y hora a procesar.
	 * @return Retorna fecha en formato yyMMdd.
	 */
	public static String getFechaYYMMDD( Date date ) {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyMMdd" );
		String sDate = formatter.format( date );
		return( sDate );                
	}

	/**
	 * Método que obtiene time stamp actual en formato HH:mm:ss.SSS.
	 * @return Retorna hora y milisegundos en formato HH:mm:ss.SSS.
	 */
	public static String getFechaHoraTrace() {
		SimpleDateFormat formatter = new  SimpleDateFormat( "HH:mm:ss.SSS" );
		String sDate = formatter.format( new Date() );
		return( sDate );
	}

	/**
	 * Método que obtiene time stamp actual en formato yyyyMMddHHmmssSSS.
	 * @return Retorna time stamp en formato yyyyMMddHHmmssSSS.
	 */
	public static String getTimestamp() {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
		String sDate = formatter.format( new Date() );
		return( sDate );      
	}

	/**
	 * Método que convierte time stamp entregado como parámetro a 
	 * formato yyyyMMddHHmmssSSS. 
	 * @param date Time stamp a procesar.
	 * @return Retorna time stamp en formato yyyyMMddHHmmssSSS.
	 */
	public static String getTimestamp( Date date ) {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
		String sDate = formatter.format( date );
		return( sDate );      
	}

	/**
	 * Método que obtiene la hora actual en formato HHmmss.
	 * @return Retorna la hora en formato HHmmss.
	 */
	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat( "HHmmss" );
		String sDate = formatter.format( new Date() );
		return( sDate );      
	}         

	/**
	 * Método que convierte un time stamp entregado como parámetro en formato
	 * yyyyMMddHHmmssSSS a formato ISO8601.
	 * @param fecha Time stamp en formato yyyyMMddHHmmssSSS.
	 * @return Retorna un time stamp en formato ISO8601 20060302T00:00:00+0000.
	 * @throws ParseException 
	 */
	public static String fechaISO8601( String fecha ) throws ParseException {
		DateFormat inFormat = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
		Date date = (Date) inFormat.parse( fecha );
		int offset =  TimeZone.getTimeZone( "Chile/Continental" ).getRawOffset() / 1000 / 3600;
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd'T'hh:mm:ss" + Tools.editaNumero( offset, 2 ) + "00" );
		return formatter.format( date );
	}


	/**
	 * Método que convierte un time stamp entregado como parámetro en formato
	 * ISO8601 a formato yyyyMMddHHmmssSSS.
	 * @param fecha Time stamp en formato ISO8601 20060302T00:00:00+0000.
	 * @return Retorna un time stamp en formato yyyyMMddHHmmssSSS.
	 * @throws ParseException 
	 */
	public static String fechaFromISO8601( String fecha ) throws ParseException {
		SimpleDateFormat inFormat = new SimpleDateFormat( "yyyyMMddThh:mm:ssTZ" );
		Date date = inFormat.parse( fecha );
		DateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
		return formatter.format( date );
	}

	public static String hexValue( byte[] b ) {

		StringBuffer sb = new StringBuffer();

		for( int i = 0; i < b.length; i++ ) {
			sb.append(toHexChar((b[i]>>>4)&0x0F));
			sb.append(toHexChar(b[i]&0x0F));
		}
		return sb.toString();
	}

	/**
	 * Metodo que revisa si un rut es valido considerando la revision del digito verificador
	 * retorna true en caso positivo o false en caso contrario
	 * @param rut
	 * @return
	 */
	public static boolean validarRut(String rut){
		try{
			if(rut.equals("")){
				return false;
			}
			rut = rut.toUpperCase();
			Pattern p = Pattern.compile("^0*(\\d{1,3}(\\.?\\d{3})*)\\-([\\dkK])$");
			Matcher mat = p.matcher(rut);
			if ( !mat.find() ){
				Base.logger.info("Rut con formato invalido");
				return false;
			}
			//validar si rut tiene el digito verificador correcto
			int largo=rut.length();        
			char digito=rut.charAt(largo-1);
			char val;

			if(rut.contains("-")){
				rut=rut.substring(0, largo-2);
			}
			else{
				rut=rut.substring(0, largo-1);
			}
			if(rut.contains(".")){
				rut = rut.replaceAll("\\.", "");
			}

			int m=0;
			int s=1;
			int t=Integer.parseInt(rut);

			for(;t!=0;t/=10){
				s=(s+t%10*(9-m++%6))%11;
			}
			val=(char)(s!=0?s+47:75);

			if(digito==val){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}

	public static char toHexChar(int i)
	{
		if ((0 <= i) && (i <= 9 ))
			return (char)('0' + i);
		else
			return (char)('a' + (i-10));
	} 

	public static void logStackTrace( Logger logger, Exception e ) {

		if(e == null){
			return;
		}

		try {
			logger.info( e.toString() );
			e.printStackTrace();
		}
		catch( Exception ee ) {        
		}

		// Se obtiene stack de trace
		StackTraceElement stack[] = e.getStackTrace();
		// Se detalla las condiciones del error
		for ( int i=0; i < stack.length; i++ ) {
			String filename = stack[i].getFileName();
			if ( filename == null ) {
				continue;
			}
			String className = stack[i].getClassName();
			String methodName = stack[i].getMethodName();
			int line = stack[i].getLineNumber();
			logger.error( filename + " " + 
					className + " " + 
					methodName + " " + 
					line );
		}     

	}
	public static byte[] createChecksum(InputStream in) throws Exception{

		InputStream fis =  in;

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		return complete.digest();
	}

	public static String getMD5Checksum(InputStream in) throws Exception {
		byte[] b = createChecksum(in);
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
					Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

	public static void espera( int segundos ){
		try {
			Thread.sleep(1000*segundos);
		} catch (InterruptedException e) {
		}
	}

	public static String[] getBotones(int pos){
		String []botones = new String[12];
		if( pos == -1){
			botones[0] = "";
			botones[1] = "";
			botones[2] = "";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 0){
			botones[0] = "";
			botones[1] = "";
			botones[2] = "";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "Avanzar";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 1){
			botones[0] = "";
			//botones[0] = "Detalle Boleta/Factura";
			botones[1] = "Detalle Productos";
			botones[2] = "";
			//botones[2] = "Boleta Flexible";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 2){
			botones[0] = "";
			botones[1] = "";
			botones[2] = "";
			//botones[2] = "Boleta Flexible";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 3){
			botones[0] = "";
			botones[1] = "Ver Detalle";
			botones[2] = "";
			//botones[2] = "Boleta Flexible";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 4){
			botones[0] = "";
			botones[1] = "";
			botones[2] = "Subir Todos";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 5){
			botones[0] = "Cerrar Recarga";
			botones[1] = "";
			botones[2] = "";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else if(pos == 6){
			botones[0] = "";
			botones[1] = "Detalle Productos";
			botones[2] = "";
			//botones[2] = "Boleta Flexible";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "";
			botones[7] = "";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		else{
			botones[0] = "";
			botones[1] = "";
			botones[2] = "";
			botones[3] = "";
			botones[4] = "";
			botones[5] = "";
			botones[6] = "Retroceder";
			botones[7] = "Avanzar";
			botones[8] = "Volver";
			botones[9] = "";
			botones[10] = "";
			botones[11] = "";
		}
		return botones;
	}

	public static String[] getValoresOtro(String s){
		String []vals = s.split(",");
		String []valores = new String[vals.length + 1];
		for(int i = 0; i < vals.length; i++){
			valores[i] = vals[i]; 
		}
		valores[vals.length] = "Otro";

		return valores; 

	}

	public static boolean isArea(String telefono){
		if(telefono.startsWith("800") || telefono.startsWith("600") || telefono.startsWith("099")){
			return false;
		}
		return true;
	}

	public static int getRows(ArrayList<Datos> filas,String campo,String valor){
		int cant = 0;
		for(int i = 0 ; i < filas.size(); i++){
			if(!filas.get(i).getStringValue(campo).trim().equals(valor)){
				cant++;
			}
		}
		return cant;
	}

	public static int getRowsDuo(ArrayList<Datos> filas,String campo,String valor,String valor2){
		int cant = 0;
		String campoF = filas.get(filas.size()-1).getStringValue(campo).trim();
		if(campoF.equals("")){
			for(int i = 0 ; i < filas.size(); i++){
				if(!filas.get(i).getStringValue(campo).trim().equals(valor)){
					cant++;
				}
			}
		}
		else{
			for(int i = 0 ; i < filas.size(); i++){
				if(!filas.get(i).getStringValue(campo).trim().equals(valor2)){
					cant++;
				}
			}
		}
		return cant;
	}

	public static int getDias(String fechaFin){

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		int a1 = Integer.parseInt( fechaFin.substring(0,4) );
		int a2 = Integer.parseInt( fechaFin.substring(4,6) );
		int a3 = Integer.parseInt( fechaFin.substring(6,8) );

		// Set the date for both of the calendar instance
		cal2.set(a1, a2-1, a3);

		String fechaActual = Tools.getFecha();
		/**
        a1 = Integer.parseInt( fechaActual.substring(0,4) );
        a2 = Integer.parseInt( fechaActual.substring(4,6) );
        a3 = Integer.parseInt( fechaActual.substring(6,8) );
		 */

		a1 = Integer.parseInt(fechaActual.substring(0,4));
		System.out.println("Anho actual: "+a1);
		a2 = Integer.parseInt(fechaActual.substring(5,7));
		System.out.println("Mes actual: "+a2);
		a3 = Integer.parseInt(fechaActual.substring(8,10));
		System.out.println("Dia actual: "+a3);

		cal1.set(a1, a2-1, a3);
		// Get the represented date in milliseconds
		long milis1 = cal1.getTimeInMillis();
		long milis2 = cal2.getTimeInMillis();

		// Calculate difference in milliseconds
		long diff = milis2 - milis1;

		// Calculate difference in days
		long diffDays = diff / (24 * 60 * 60 * 1000);

		if(diffDays < 0)
			diffDays = 0;
		return (int) diffDays;
	}

	public static void initTrx(TransaccionCaja trx){
		trx.setCodigoPortador(0);
		trx.setCuentaCliente(0l);
		trx.setDv("");
		trx.setEmpresa(0);
		trx.setFechaVencimiento(new GregorianCalendar());
		trx.setMonto(0l);
		trx.setNroOperacionAReversar(0l);
		trx.setNumeroDocumento("");
		trx.setNumeroTransaccion(0l);
		trx.setOrigen("");
		trx.setRut("");
		trx.setServicio("");
		trx.setTipoDocumento("");
		trx.setTipoRegistro("");
		trx.setTipoTransaccion("");
		trx.setNumeroCorrelativo("");
		trx.setTipoCorrelativo("");
	}

	public static void initTrxClaro(Transaccion trx){
		trx.setCodigoPortador(0);
		trx.setCuentaCliente("01");
		trx.setDv("");
		trx.setEmpresa(0);
		trx.setFechaVencimiento(new GregorianCalendar().toString());
		trx.setMonto(0l);
		trx.setNroOperacionAReversar(0l);
		trx.setNumeroDocumento(new Long(0));
		//trx.setNumeroTransaccion(0l);
		trx.setOrigen("");
		trx.setRut(new Long(0));
		trx.setServicio("");
		trx.setTipoDocumento("");
		trx.setTipoRegistro("");
		trx.setTipoTransaccion("");
		//trx.setNumeroCorrelativo("");
		//trx.setTipoCorrelativo("");
	}

	public static void initMP(MedioPagoCaja mp){
		mp.setCantidadCuotas(0);
		mp.setCodigoAutorizacion("");
		mp.setCodigoBanco(0);
		mp.setCodigoPlaza(0);
		mp.setDepositante("");
		mp.setFechaVencimiento(new GregorianCalendar());
		mp.setMonto(0l);
		mp.setNombrePagador("");
		mp.setNumeroCheque("");
		mp.setNumeroCtaCte("");
		mp.setNumeroDeposito("");
		mp.setNumeroTarjeta("");
		mp.setNumeroTransaccion(0l);
		mp.setRutPagador("");
		mp.setSerieValeVista("");
		mp.setTipoTotal("");
		mp.setTipoTransaccion("");
	}

	public static void initMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
		mp.setCantidadCuotas(0);
		mp.setCodigoAutorizacion("");
		mp.setCodigoBanco(0);
		mp.setCodigoPlaza(0);
		mp.setDepositante("");
		//mp.setFechaVencimiento(new GregorianCalendar().toString());
		mp.setFechaVencimiento(Tools.getFecha());
		mp.setMonto(0l);
		//mp.setNombrePagador("");
		mp.setNumeroCheque("");
		mp.setNumeroCtaCte("");
		mp.setNumeroDeposito("");
		mp.setNumeroTarjeta("");
		//mp.setNumeroTransaccion(0l);
		//mp.setRutPagador("");
		mp.setSerieValeVista("");
		mp.setTipoTotal("");
		mp.setTipoTransaccion("");
	}

	public static String limpiarRut(String rut){
		String aux = rut;
		String newRut = "";
		for(int i = 0 ; i < aux.length(); i++){
			if(aux.charAt(i) == '0'){
				newRut = rut.substring(i + 1);
			}
			else{
				break;
			}
		}
		return newRut;
	}

	// TODO cbriones: validar cuales seran la operaciones que se podran anular y/o Editar ??
	public static OperTRV armarVTR(ConsultaOperacionOut opOut){
		OperTRV oper = null;
		if(opOut.getOperacion().getTipoOperacion().equals("1")){
			oper = new OperTRV();
		}
		else{
			if(opOut.getOperacion().getTipoOperacion().equals("6") || opOut.getOperacion().getTipoOperacion().equals("9") || opOut.getOperacion().getTipoOperacion().equals("10")){
				DocumentoPago doc = null;
				oper = new OperTRV();
				oper.setRemesa(true);

				if(opOut.getOperacion().getTipoOperacion().equals("6")){
					try {
						doc = FactoryDocumentoPago.makeInstance("RemesaEfectivo");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((RemesaEfectivo)doc).getDatos().setValue("Monto", opOut.getOperacion().getMonto());
				}
				else if(opOut.getOperacion().getTipoOperacion().equals("9")){
					try {
						doc = FactoryDocumentoPago.makeInstance("RemesaChequeDia");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((RemesaChequeDia)doc).getDatos().setValue("Monto", opOut.getOperacion().getMonto());
				}
				else if(opOut.getOperacion().getTipoOperacion().equals("10")){
					try {
						doc = FactoryDocumentoPago.makeInstance("RemesaChequeFecha");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((RemesaChequeFecha)doc).getDatos().setValue("Monto", opOut.getOperacion().getMonto());
				}
				try {
					oper.addDocumentoPago(doc);
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
					Base.logger.error("Error en agregar documento de pago al carro");
				}
				return oper;
			}
		}

		for(int i = 0 ; i < opOut.getTransacciones().length ; i++){
			TransaccionCaja trx = opOut.getTransacciones()[i];
			DocumentoPago doc = null;
			boolean isCut = false;
			if(trx.getTipoTransaccion().equals("PagoDeudaCut")){
				isCut = true;
			}
			if(trx.getTipoTransaccion().equals("PagoDeuda") || trx.getTipoTransaccion().equals("PagoDeudaCut")){
				oper.setRemesa(false);
				if(trx.getServicio() != null){
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((DocumentoServicioVtr)doc).vaciarDatos(trx);
					if(isCut){
						doc.getDatos().setValue("Cut", "si");
					}
				}
				else if(trx.getNumeroDocumento() != null && !trx.getNumeroDocumento().equals("0")){
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((DocumentoVtr)doc).vaciarDatos(trx);
				}
				else{
					try {
						doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaVtr");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((DocumentoCuentaVtr)doc).vaciarDatos(trx);
				}
				try {
					oper.addDocumentoPago(doc);
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
					Base.logger.error("Error en agregar documento de pago al carro");
				}
			} 
			else if( trx.getTipoTransaccion().equals("AbonoDeuda") ){
				try {
					doc = FactoryDocumentoPago.makeInstance("DocumentoAbonoVtr");
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((DocumentoAbonoVtr)doc).vaciarDatos(trx);
				try {
					oper.addDocumentoPago(doc);
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
					Base.logger.error("Error en agregar documento de pago al carro");
				}
			}
		}

		for(int i = 0 ; i < opOut.getMediosPago().length ; i++){
			MedioPagoCaja mp = opOut.getMediosPago()[i];
			MedioPago mPago = null;
			if(mp.getTipoTransaccion().equals("mpEfectivo")){                
				try{
					mPago = FactoryMedioPago.makeInstance("Efectivo");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((Efectivo)mPago).vaciarMP(mp);
			}
			else if(mp.getTipoTransaccion().equals("mpCheque")){
				try{
					mPago = FactoryMedioPago.makeInstance("Cheque");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((Cheque)mPago).vaciarMP(mp);
			}
			else if(mp.getTipoTransaccion().equals("mpDebitoManual") || mp.getTipoTransaccion().equals("mpCreditoManual") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("TarjetaManual");
					mPago.setNombre("TarjetaTbkManual");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((TarjetaManual)mPago).vaciarMP(mp);
			}

			try {
				oper.addMedioPago(mPago);
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("Error en agregar documento de pago al carro");
			}
		}
		return oper;
	}


	public static OperTRV armarClaro(ConsultarOperacionOutDTO opOut){
		OperTRV oper = null;

		Base.logger.info("Codigo Operacion a Rearmar: "+opOut.getOperacion().getCodigoOperacion());
		Base.logger.info("Numero Operacion a Rearmar: "+opOut.getOperacion().getNumeroOperacion());
		Base.logger.info("Id Operacion Ext. a Rearmar: "+opOut.getOperacion().getIdOperacionExterno());

		// TODO validar si puede ser el campo Tipo Operacion ..
		if(opOut.getOperacion().getCodigoOperacion().equals("1")){
			Base.logger.info("La Operacion corresponde a un Pago Deuda (Cod. Oper = 1)");
			oper = new OperTRV();
		}
		else{
			if(opOut.getOperacion().getCodigoOperacion().equals("6") || opOut.getOperacion().getCodigoOperacion().equals("9") || opOut.getOperacion().getCodigoOperacion().equals("10")){
				DocumentoPago doc = null;
				oper = new OperTRV();
				oper.setRemesa(true);

				if(opOut.getOperacion().getCodigoOperacion().equals("6")){
					try {
						doc = FactoryDocumentoPago.makeInstance("RemesaEfectivo");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((RemesaEfectivo)doc).getDatos().setValue("Monto", opOut.getOperacion().getMonto());
				}
				else if(opOut.getOperacion().getCodigoOperacion().equals("9")){
					try {
						doc = FactoryDocumentoPago.makeInstance("RemesaChequeDia");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((RemesaChequeDia)doc).getDatos().setValue("Monto", opOut.getOperacion().getMonto());
				}
				else if(opOut.getOperacion().getCodigoOperacion().equals("10")){
					try {
						doc = FactoryDocumentoPago.makeInstance("RemesaChequeFecha");
					} catch (BaseException e) {
						Tools.logStackTrace(Base.logger, e);
					}
					((RemesaChequeFecha)doc).getDatos().setValue("Monto", opOut.getOperacion().getMonto());
				}
				try {
					oper.addDocumentoPago(doc);
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
					Base.logger.error("Error en agregar documento de pago al carro");
				}
				return oper;
			}
		}

		for(int i = 0 ; i < opOut.getOperacion().getTransacciones().length ; i++){
			TransaccionDTO trx = opOut.getOperacion().getTransacciones()[i];

			Base.logger.info("Numero Transaccion: "+trx.getNumeroTransaccion());
			Base.logger.info("Tipo Transaccion: "+trx.getTipoTransaccion());
			Base.logger.info("Tipo Registro: "+trx.getTipoRegistro());
			Base.logger.info("Tipo Documento: "+trx.getTipoDocumento());


			DocumentoPago doc = null;

			if(trx.getTipoTransaccion().equals("PagoDeuda")){
				oper.setRemesa(false);
				/**
	    		if(trx.getServicio() != null){
	    			try {
                        doc = FactoryDocumentoPago.makeInstance("DocumentoServicioVtr");
                    } catch (BaseException e) {
                    	Tools.logStackTrace(Base.logger, e);
                    }
                    ((DocumentoServicioVtr)doc).vaciarDatos(trx);
                    if(isCut){
                    	doc.getDatos().setValue("Cut", "si");
                    }
	    		}
	    		else if(trx.getNumeroDocumento() != null && !trx.getNumeroDocumento().equals("0")){
	    			try {
                        doc = FactoryDocumentoPago.makeInstance("DocumentoVtr");
                    } catch (BaseException e) {
                    	Tools.logStackTrace(Base.logger, e);
                    }
                    ((DocumentoVtr)doc).vaciarDatos(trx);
	    		}
	    		else{
	    			try {
                        doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaVtr");
                    } catch (BaseException e) {
                    	Tools.logStackTrace(Base.logger, e);
                    }
                    ((DocumentoCuentaVtr)doc).vaciarDatos(trx);
	    		}
				 */

				try {
					doc = FactoryDocumentoPago.makeInstance("DocumentoCuentaClaro");
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}

				((DocumentoCuentaClaro)doc).vaciarDatosClaro(trx);

				Base.logger.info("Monto Doc: "+doc.getMonto());
				Base.logger.info("Cta Clte.: "+doc.getDatos().getStringValue("NumeroCuenta"));
				Base.logger.info("Tipo Total : "+ "??");
				Base.logger.info("Tipo Transaccion: "+doc.getDatos().getStringValue("TipoTransaccionClaro"));
				Base.logger.info("Fecha Vencimiento: "+doc.getDatos().getStringValue("FechaVencimientoClaro"));
				Base.logger.info("Nro. Correlativo: "+"??");
				Base.logger.info("Nro. Documento: "+doc.getDatos().getStringValue("FolioDocumentoClaro"));
				Base.logger.info("Nro. Transaccion: "+"??");

				try {
					oper.addDocumentoPago(doc);
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
					Base.logger.error("Error en agregar documento de pago al carro");
				}
			} 

			/** Se comenta el Abono...
    		else if( trx.getTipoTransaccion().equals("AbonoDeuda") ){
    			try {
                    doc = FactoryDocumentoPago.makeInstance("DocumentoAbonoVtr");
                } catch (BaseException e) {
                	Tools.logStackTrace(Base.logger, e);
                }
                ((DocumentoAbonoVtr)doc).vaciarDatos(trx);
	    		try {
	    			oper.addDocumentoPago(doc);
	            } catch (BaseException e) {
	                Tools.logStackTrace(Base.logger, e);
	                Base.logger.error("Error en agregar documento de pago al carro");
	            }
    		}
			 */
			else if( trx.getTipoTransaccion().equals("PagoItem") ){
				try {
					doc = FactoryDocumentoPago.makeInstance("DocumentoItemClaro");
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}

				((DocumentoItemClaro)doc).vaciarDatosClaro(trx);

				Base.logger.info("Monto Doc: "+trx.getMontoDocumento());
				Base.logger.info("Cta Clte.: "+trx.getCuentaCliente());
				Base.logger.info("Tipo Total : "+trx.getTipoTotal());
				Base.logger.info("Tipo Transaccion: "+trx.getTipoTransaccion());
				Base.logger.info("Fecha Vencimiento: "+trx.getFechaVencimiento());
				Base.logger.info("Nro. Correlativo: "+trx.getNroCorrelativo());
				Base.logger.info("Nro. Documento: "+trx.getNumeroDocumento());
				Base.logger.info("Nro. Transaccion: "+trx.getNumeroTransaccion());

				try {
					oper.addDocumentoPago(doc);
				} catch (BaseException e) {
					Tools.logStackTrace(Base.logger, e);
					Base.logger.error("Error en agregar documento de pago al carro");
				}
			}
		}

		for(int i = 0 ; i < opOut.getOperacion().getMediosdepago().length ; i++){
			MedioPagoDTO mp = opOut.getOperacion().getMediosdepago()[i];

			if(mp.getFechaVencimiento() != null){
				mp.setFechaVencimiento(mp.getFechaVencimiento().replace("-", ""));
			}

			Base.logger.info("Monto Pagado: "+mp.getMontoPagado());
			Base.logger.info("Fecha Vcto.: "+mp.getFechaVencimiento());
			Base.logger.info("Tipo Total.: "+mp.getTipoTotal());
			Base.logger.info("Tipo Trx.: "+mp.getTipoTransaccion());
			Base.logger.info("Cod Autoriz.: "+mp.getCodigoAutorizacion());
			Base.logger.info("Banco : "+mp.getCodigoBanco());

			MedioPago mPago = null;
			if(mp.getTipoTransaccion().equals("mpEfectivo")){                
				try{
					mPago = FactoryMedioPago.makeInstance("Efectivo");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((Efectivo)mPago).vaciarMPClaro(mp);
			}
			if(mp.getTipoTransaccion().equals("mpAjusteSencillo")){                
				try{
					mPago = FactoryMedioPago.makeInstance("AjusteSencillo");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((AjusteSencillo)mPago).vaciarMPClaro(mp);
			}
			else if(mp.getTipoTransaccion().equals("mpCheque")){                
				try{
					mPago = FactoryMedioPago.makeInstance("Cheque");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((Cheque)mPago).vaciarMPClaro(mp);
			}
			else if(mp.getTipoTransaccion().equals("mpChequeFecha")){                
				try{
					mPago = FactoryMedioPago.makeInstance("ChequeFecha");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((ChequeFecha)mPago).vaciarMPClaro(mp);
			}
			else if(mp.getTipoTransaccion().equals("mpDebitoManual") || mp.getTipoTransaccion().equals("mpCreditoManual") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("TarjetaManual");
					mPago.setNombre("TarjetaTbkManual");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((TarjetaManual)mPago).vaciarMPClaro(mp);
			}
			// TODO se debe validar cuando se arme una Trx cancelada con Tarjeta de Credito  
			else if(mp.getTipoTransaccion().equals("mpDebito") || mp.getTipoTransaccion().equals("mpCredito") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("Tarjeta");
					mPago.setNombre("Tarjeta");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((Tarjeta)mPago).vaciarMPClaro(mp);
			}
			// Medio de Pago Deposito
			else if(mp.getTipoTransaccion().equals("mpDepositoFisico") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("DepositoEfectivo");
					mPago.setNombre("DepositoEfectivo");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				Base.logger.info("Ya instancio Medio de Pago Deposito Fisico..");
				((Deposito)mPago).vaciarMPClaro(mp);
			}

			// Medio de Pago Transferencia
			else if(mp.getTipoTransaccion().equals("mpTransferencia") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("Transferencia");
					mPago.setNombre("Transferencia");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((Transferencia)mPago).vaciarMPClaro(mp);
			}

			// Medio de Pago Vale Vista
			else if(mp.getTipoTransaccion().equals("mpValeVista") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("ValeVista");
					mPago.setNombre("ValeVista");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((ValeVista)mPago).vaciarMPClaro(mp);
			}

			// Medio de Pago otro Medio
			else if(mp.getTipoTransaccion().equals("mpOtros") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("OtroMedioPago");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((OtroMedioPago)mPago).vaciarMPClaro(mp);
			}

			// Medio de Pago otro Medio
			else if(mp.getTipoTransaccion().equals("mpMultitienda") ){                
				try{
					mPago = FactoryMedioPago.makeInstance("TarjetaManual");
					mPago.setNombre("TarjetaManual");
				} 
				catch(BaseException e) {
					Tools.logStackTrace(Base.logger, e);
				}
				((TarjetaManual)mPago).vaciarMPClaro(mp);
			}

			try {
				oper.addMedioPago(mPago);
			} catch (BaseException e) {
				Tools.logStackTrace(Base.logger, e);
				Base.logger.error("Error en agregar documento de pago al carro");
			}
		}
		Base.logger.info("Retornando la operacion rearmada para su Edicion...");
		return oper;
	}



	public static void loadXMLProviderConfig(Base base){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		ClassLoader cl = base.getClass().getClassLoader();

		InputStream in = null;
		try {
			in = cl.getResource("config/handlerConfig.xml").openStream();
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
		}

		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result;
		try {
			result = bis.read();		
			while(result != -1) {
				byte b = (byte)result;
				buf.write(b);
				result = bis.read();
			} 
		} catch (IOException e) {
			Tools.logStackTrace(Base.logger, e);
		}
		Base.theConfig = new org.apache.axis.configuration.XMLStringProvider(buf.toString());		
	}

	public static String getClaveDiccionario(String concepto){
		String []diccionario = Base.getDiccionario();
		String clave = "";
		for(int i = 0 ; i < diccionario.length ; i++){
			String []aux = diccionario[i].split(",");
			Base.logger.info("Medio de pago Diccionario: "+aux[0]+" - "+aux[1]);
			if(aux[1].equals(concepto)){
				clave = aux[0];
			}
			else
				continue;
		}
		Base.logger.info("Clave para Medio de pago: "+clave);
		return clave;
	}

	// metodo para extraer equivalencia en tipos de registro
	// cbriones
	public static String getTipoRegistroVisual(String tipoRegistro){
		if("SC".equalsIgnoreCase(tipoRegistro)) return "Saldo Cast.";
		if("ST".equalsIgnoreCase(tipoRegistro)) return "Lim. Crdto.";
		if("CH".equalsIgnoreCase(tipoRegistro)) return "Cheque Prot.";
		if("DEUDA".equalsIgnoreCase(tipoRegistro)) return "Deuda";
		return tipoRegistro;
	}

	public static NumeroOperacionOutDTO getNumOper() {
		ParamSet pSet = Base.getParamSet("posDat");

		//Pedimos N°Operacion al servidor

		//ServerProxy pr = Proxy.getProxyInstance();
		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance(); 

		//NumeroOperacionOut operOut = null;
		NumeroOperacionOutDTO operOut = null;

		//HeaderIn hIn = new HeaderIn();
		HeaderDTO hIn = new HeaderDTO();

		hIn.setAgencia(pSet.getStringValue("Agencia"));
		hIn.setCajaFisica(pSet.getStringValue("Caja"));
		hIn.setEntidad(pSet.getStringValue("Entidad"));
		hIn.setCajero(pSet.getStringValue("Cajero"));
		hIn.setSession(pSet.getStringValue("SessionId"));
		hIn.setUsuario(pSet.getStringValue("Usuario"));
		//hIn.setUsuario(this.getRecaudador());
		hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));

		try {

			operOut= pr.numeroOperacion(hIn);


		} catch (RemoteException e2) {
			Tools.logStackTrace(Base.logger, e2);        
			Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
			operOut = null;
			/*this.reversar();
			JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
			return -1;*/
		}  

		//if(operOut.getHeaderOut().getRc() != 0 || operOut.getNumeroOperacion() < 0){   
		if(!("0").equalsIgnoreCase(operOut.getRetCode()) || operOut.getNumeroOperacion() < 0){
			Base.logger.info("No se pudo obtener en Numero de Operacion a generar !!!");
			operOut = null;
			/*this.reversar();
			JOptionPane.showMessageDialog(null, "No se pudo realizar el pago\nSe reversará toda la operación", "Info", JOptionPane.INFORMATION_MESSAGE);
			return -1;*/
		}

		return operOut;
	}
	
	public static String generaLineaCvs(String [] datos ){
		
		String linea=null;
		StringBuffer sb = new StringBuffer();
		for (String elemento: datos){
			sb.append(elemento);
			sb.append(";");
		}
		
		linea=sb.toString();
		return linea;
	
	}
	
	
	public static String generaCuadratura(String mP, String monto){
		
		String linea=null;
		StringBuffer sb = new StringBuffer();
		sb.append(mP);
		sb.append("=");
		sb.append(monto);
		linea=sb.toString();
		return linea;
	
	}
	
	
	public static void fileMove(String sourceFile, String destinationFile) {
		   System.out.println("Desde: " + sourceFile);
		   System.out.println("Hacia: " + destinationFile);

		 
		    File inFile = new File(sourceFile);
		    File outFile = new File(destinationFile);
		    
		    inFile.renameTo(outFile);
		    File file = new File(sourceFile);
		    if (file.exists()) {
		     file.delete();
		    }
		   
		    
		  }
	  public static void borrarTemporales() {

		  ParamSet pCfg = Base.getParamSet( "posCfg" );
	        ParamSet pSet = Base.getParamSet("posDat");
	        Base.logger.info("Se eliminaran los Archivos más Antiguos Respaldados");
	        //Cargamos el parametro que determina la antiguedad de los ficheros
	    	int antFicheros = 0;
	    	
	    	if (pSet.getStringValue("DuracionBackUp").equalsIgnoreCase("") || pSet.getStringValue("DuracionBackUp") == null){
	    		antFicheros = 7;
	    		pSet.setValue("DuracionBackUp",String.valueOf(7));
			    pSet.save();
	    	}else{
	    		antFicheros = Integer.parseInt(pSet.getStringValue("DuracionBackUp"));
	    	}
	    	
	    	//long cutoff =  (antFicheros * 24 * 60 * 60 * 1000);
	    	long cutoff2 = System.currentTimeMillis() -  (antFicheros * 24 * 60 * 60 * 1000);
	    	
	    	String dirBck="ValidaOfflineBackUp";
	       
	        File directorioOfflineBackUp = new File(pCfg.getStringValue(dirBck));
	        
	        
	        if (!directorioOfflineBackUp.exists()){
	        	 directorioOfflineBackUp.mkdir();
	        	 Base.logger.info("Directorio BackUp Creado");
	         }else{
	     	
	     	//Cargamos los ficheros
	        File[] ficheros = directorioOfflineBackUp.listFiles();

	        //Obtenemos la fecha actual
	       // Date fechaActual = new Date();

	        //Recorremos todos los ficheros
	        for (int x = 0; x < ficheros.length; x++){
	            //Obtenemos la fecha de última modificación del fichero
	            Date fechaFichero = new Date(ficheros[x].lastModified());
	            System.out.println(ficheros[x].getName());
	            //Comparamos con la actual
	            long dif = fechaFichero.getTime();
	            long difSegundos = dif;

	            //Si el fichero tiene más de los segundos de antFicheros lo borramos
	            if (difSegundos < cutoff2){
	                ficheros[x].delete();
	            }
	        }
	    }
	        
	        // msepulveda: Se eliminan los archivos *.pdf de vouchers generados
	        ParamSet posCfg = Base.getParamSet("posCfg");
			String basePathVoucher = posCfg.getStringValue("VoucherDir");
			File directorioVoucher = new File(basePathVoucher);
	        
	        
	        if (!directorioVoucher.exists()){
	        	directorioVoucher.mkdir();
	        	 Base.logger.info("Directorio Voucher Creado");
	         }else{
	        	 
	        	//Cargamos los ficheros
	 	        File[] ficheros = directorioVoucher.listFiles();

	 	        //Recorremos todos los ficheros
	 	        for (int x = 0; x < ficheros.length; x++){

	 	            //Si es pdf lo borro
	 	            if (ficheros[x].getName().contains(".pdf")){
	 	                try {
							ficheros[x].delete();
						} catch (Exception e) {
							Base.logger.error("Error al eliminar PDF " + ficheros[x].getName() , e);
						}
	 	            }
	 	        }
	        	 
	         }
	        
	        
	  }    
	  
		public static boolean fileCierreMove() {
				
			ParamSet pCfg = Base.getParamSet( "posCfg" );
			boolean movido =true;
			String dirBck="ValidaOfflineBackUp";
			String dirVal = "ValidaOfflineDirCierres";
			 
//			dir = "OfflineDirCierres";
			
			String fecha = Tools.getFechaYYYYMMDD();
			String fechaNew = Tools.getFechaHora();
			
			String oldFile = String.format( "Txrs_%s", fecha );
			String newFile = String.format( "Txrs_%s", fechaNew );
			
			String destinationFile=pCfg.getStringValue(dirBck) + newFile + ".dat";
			
			String sourceFile= pCfg.getStringValue(dirVal) + oldFile +".dat"; 
			
			Base.logger.info("Desde: " + sourceFile);
			Base.logger.info("Hacia: " + destinationFile);
			
		    System.out.println("Desde: " + sourceFile);
		    System.out.println("Hacia: " + destinationFile);
		    
		    try {
		    	
			    File inFile = new File(sourceFile);
			    File outFile = new File(destinationFile);
			    
			    inFile.renameTo(outFile);
			    File file = new File(sourceFile);
			    if (file.exists()) {
			     file.delete();
			    }
				
			} catch (Exception e) {
				e.printStackTrace();
				movido = false;
				// TODO: handle exception
			}
	
			 return movido;  
			    
			  }
		
//		public static boolean fileCierreMoveOnline() {
//			
//			ParamSet pCfg = Base.getParamSet( "posCfg" );
//			boolean movido =true;
//			String dirBck="ValidaOfflineBackUp";
//			String dirVal = "ValidaOfflineDirCierres";
//
//			ArrayList<String> archivos = new ArrayList<String>();
//			
//        	File directorioOfflineCierres = new File(pCfg.getStringValue("ValidaOfflineDirCierres"));
//         	String[] ficheros = directorioOfflineCierres.list();
//         	
//         	if(ficheros != null && ficheros.length>0){
//         		
//         		for (int x=0; x < ficheros.length;x++){
//         			if(ficheros[x].endsWith(".dat")){
//         				archivos.add(ficheros[x]);
//         			}
//         		}
//         		
//         		if(archivos.isEmpty()){
//         			return false;
//         		}
//			
//					
//			
//			String fecha = Tools.getFechaYYYYMMDD();
//			String fechaNew = Tools.getFechaHora();
//			
//			String oldFile = String.format( "Txrs_%s", fecha );
//			String newFile = String.format( "Txrs_%s", fechaNew );
//			
//			String destinationFile=pCfg.getStringValue(dirBck) + newFile + ".dat";
//			
//			String sourceFile= pCfg.getStringValue(dirVal) + oldFile +".dat"; 
//					
//		    System.out.println("Desde: " + sourceFile);
//		    System.out.println("Hacia: " + destinationFile);
//		    
//		    try {
//		    	
//			    File inFile = new File(sourceFile);
//			    File outFile = new File(destinationFile);
//			    
//			    inFile.renameTo(outFile);
//			    File file = new File(sourceFile);
//			    if (file.exists()) {
//			     file.delete();
//			    }
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				movido = false;
//				// TODO: handle exception
//			}
//	
//			 return movido;  
//			    
//			  }
//	
	public static boolean validaNumerico(String numero) {
		Matcher matcher = numericoPattern.matcher(numero);
		return matcher.matches();
	}
	
	public static String getCodAprobacion( String numCtaCte, String codBanco, String serie) {
		String codGenerado 	= "";
		String cuenta 		= numCtaCte;
		String banco 		= codBanco;
		String serial		= serie;
		cuenta = rellenaPorLaIzquierda(cuenta, 11, "0");
		banco =  rellenaPorLaIzquierda(banco, 3, "0");
		serial = rellenaPorLaIzquierda(serial, 7, "0");
		codGenerado = serial + banco + cuenta;
		return codGenerado;
	}
	
	public static String rellenaPorLaIzquierda(String original, int cantidad, String cadenaRelleno){
		  String resultado = "";
		  if(original.length() < cantidad){
			  int cantidadOriginal = original.length();
			  for (int i = 0; i < (cantidad - cantidadOriginal); i++) {
				  resultado = cadenaRelleno +  resultado;
			  }
			  resultado += original; 
		  }else{
			  resultado = original;
		  }
		  return resultado;
	  }
	
	/*
	 * REspinoza
	 * En caso de existir respaldo voucher para reimpresión se reescribe
	 * (Caso corte de luz)
	 */
	public static void revisarVoucherTBK() {
		ParamSet posCfg = Base.getParamSet( "posCfg" );
		File temporalFile = new File(posCfg.getStringValue("VoucherDir") + "boletaTbkTemp.dat");
		File originalFile = new File(posCfg.getStringValue("VoucherDir") + "boletaTbk.dat");
//		ObjectInputStream in = null;
		
		try { 
			if (!originalFile.exists() && temporalFile.exists()) {
				//borrar original				
//				originalFile.delete();
				//renombrar temp
				boolean success = temporalFile.renameTo(new File(posCfg.getStringValue("VoucherDir") + "boletaTbk.dat"));
	            if (!success) {
	                System.out.println("Error intentando cambiar el nombre de fichero voucher temporal tbk");
	            }
			}

		} catch (Exception e) {
			Base.logger.error("Error revisar voucher tbk");
			return;
		}
		
	}
}
