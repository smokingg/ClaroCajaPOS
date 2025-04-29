package cl.hyh.redpagos.caja.pos;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;

/**
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Tools {
  
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
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd" );
        String sDate = formatter.format( new Date() );
        return( sDate );                
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
    logger.info( e.toString() );
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
}
