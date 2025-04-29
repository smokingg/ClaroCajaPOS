package cl.hyh.redpagos.caja.pos;

import jpos.JposException;



/**
 * Interfaz que define la estructura de la representacion del dispositivo de impresion
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public interface PosDevicePrinterInterface {
    public static final int _MODE_LEFT = 1;
    public static final int _MODE_CENTER = 2;
    public static final int _MODE_RIGHT = 4;
    public static final int _MODE_BOLD = 8;
    public static final int _MODE_UNDERLINE = 16;
    public static final int _MODE_SMALL = 32;
    public static final int _MODE_BIG_1 = 64;
    public static final int _MODE_BIG_2 = 128;
    public static final int _MODE_BIG_3 = 256;
    public static final int _MODE_BIG_4 = 512;
    public static final int _MODE_CUT = 1024;  
    public static final int _MODE_LOGO = 2048;
    public static final int _SIZE_NORMAL = 0;
    public static final int _SIZE_2X = 2;
    public final int _MAX_LINE_WIDTHS = 11;
    
    public void openDevice() throws JposException;
    public void printLine(int formato, String mensaje)throws PosDeviceException;
    public void close() throws PosDeviceException;
    public void initPrinter();
    public void printCheck(long monto,String nombre, int dia,int mes, int agno)throws PosDeviceException;
    public void printFranqueo(String nombre, String telefono,String codAuto,long numOper,String monto,String operador,String fecha,String hora,long entidad,long agencia,long caja,String cuentaDeposito,String bancoDeposito, long codigoRechazo)throws PosDeviceException;
    public void printFranqueoNew(String nombre, String telefono,String codAuto,long numOper,String monto,String operador,String fecha,String hora,long entidad,long agencia,long caja,String cuentaDeposito,String bancoDeposito)throws PosDeviceException;
    public void printBarCode(String mensaje) throws PosDeviceException;
    public String makePrintString(int tipo,String text1,String text2);
    public boolean isOn();
    public void setOn(boolean isOn);
    
}
