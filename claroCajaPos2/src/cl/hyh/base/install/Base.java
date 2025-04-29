package cl.hyh.base.install;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Base {
    public static Logger logger;    

    public void init(){
        PropertyConfigurator.configure( getClass().getClassLoader().getResource("config/log4j.properties" ) );
        logger = Logger.getLogger( "visor" );
        logger.info( "Partimos..." );
    }
}
