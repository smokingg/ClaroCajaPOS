package cl.hyh.redpagos.caja.pos;

import jpos.JposException;

/**
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Start {

    public static void main(String[] args) throws PosDeviceException {
        BasePos base = new BasePos();
        base.init();
        PosDevicePrinterInterface printer = ServiceLocator.getInstance().getPrinterObj();
        //PosDeviceMicrInterface micr = ServiceLocator.getInstance().getMicrObj();        
        
        try {
            printer.openDevice();
        } catch (JposException e) {
            e.printStackTrace();
        }
        printer.printLine(PosDevicePrinterInterface._MODE_LOGO, "");
        
        
        //printer.printCheck(58464900, "Advise Ltda.", 28, 10, 2008);
        printer.close();
        
        //micr.openDevice();
        //micr.insert();
        
        //micr.close();
    }
}
