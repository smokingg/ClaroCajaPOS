package cl.hyh.redpagos.caja.base;

import cl.hyh.redpagos.caja.base.parser.DefServicio;

/**
 * Factory de objetos Servicios
 * @author Rafael Hernandez
 * 
 */
public class FactoryServicio {
    /**
     * Funcion que instancia un Servicio a partir de su nombre realizando una busqueda en la tabla de Servicios
     * @param serviceName
     * @return
     */
    public static Servicio makeInstance(String serviceName) throws BaseException {
        
        DefServicio dS = Base.getDefServicio( serviceName );
        if( dS != null ) {
            try {
                Servicio srv = (Servicio) Class.forName( dS.getClassName() ).newInstance();
                srv.setRequest( new Datos( dS.getInputRecordDef() ) );
                srv.setResponse( new Datos( dS.getOutputRecordDef() ) );
                srv.setNombreServicio( serviceName );
                srv.setHeaderIn( new Datos( Base.getDefRecord( "HeaderIn" ) ) );
                srv.setHeaderOut( new Datos( Base.getDefRecord( "HeaderOut" ) ) );
                return srv;
            } catch (InstantiationException e) {
                Tools.logStackTrace( Base.logger, e );
                throw new BaseException( e, "error Class.forName " + dS.getClassName() );
            } catch (IllegalAccessException e) {
                Tools.logStackTrace( Base.logger, e );
                throw new BaseException( e, "error Class.forName " + dS.getClassName() );
            } catch (ClassNotFoundException e) {
                Tools.logStackTrace( Base.logger, e );
                throw new BaseException( e, "error Class.forName " + dS.getClassName() );
            }
        }
        
        throw new BaseException( "Servicio " + serviceName + " indefinido" );
        
    }
}
