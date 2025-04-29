package cl.hyh.redpagos.caja.pos;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import cl.hyh.redpagos.caja.comm.PrintPDF;

/**
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class BasePos {
    public static Logger logger;
    
    /**
     * Inicializa el logger para el manejo de informacion
     * 
     */
    public PosDeviceMicrInterface micr = ServiceLocator.getInstance().getMicrObj(); 
    
    public PosDevicePrinterInterface printer = ServiceLocator.getInstance().getPrinterObj();
    
    public PrintPDF pdfPrinter = new PrintPDF();
    
    public void init(){
        PropertyConfigurator.configure( getClass().getClassLoader().getResource("config/log4j.properties" ) );
        //logger = Logger.getLogger( "cajasCTC" );
        logger = Logger.getLogger( "cajasClaro" );
        logger.info( "Partimos..." );
    }
}
