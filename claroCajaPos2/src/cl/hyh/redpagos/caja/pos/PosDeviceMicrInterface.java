package cl.hyh.redpagos.caja.pos;

/**
 * Interfaz que define la estructura de la representacion del dispositivo de lectura de cheques
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public interface PosDeviceMicrInterface {
    public void openDevice() throws PosDeviceException;
    public void close() throws PosDeviceException;
    public void remove() throws PosDeviceException;
    public void insert() throws PosDeviceException;
    public void initMicr();
    public String getBanco();
    public String getCuenta();
    public String getSerial(); 
    public boolean isLeido();
}
