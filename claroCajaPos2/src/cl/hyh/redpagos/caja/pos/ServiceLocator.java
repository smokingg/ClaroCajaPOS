package cl.hyh.redpagos.caja.pos;

/**
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ServiceLocator {
    
    static private ServiceLocator serv = null;
    static private PosDevicePrinterInterface printer= null;
    static private PosDeviceMicrInterface micr = null;
    
    /**
     * 
     */
    private ServiceLocator(){
        try {
            printer = (PosDevicePrinterInterface)Class.forName("cl.hyh.redpagos.caja.pos.PosDevicePrinter").newInstance();
            printer.initPrinter();
            micr = (PosDeviceMicrInterface)Class.forName("cl.hyh.redpagos.caja.pos.PosDeviceMicr").newInstance();
            micr.initMicr();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    static public ServiceLocator getInstance(){
        if( serv == null ){
            serv = new ServiceLocator();
        }
        return serv;
    }
    public PosDevicePrinterInterface getPrinterObj(){
        return printer;
    }
    public PosDeviceMicrInterface getMicrObj(){
        return micr;
    }
}
