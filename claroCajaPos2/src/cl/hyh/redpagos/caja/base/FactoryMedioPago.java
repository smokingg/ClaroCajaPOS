package cl.hyh.redpagos.caja.base;

import cl.hyh.redpagos.caja.base.parser.DefMedioPago;

/**
 * Factory de objetos MedioPago
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class FactoryMedioPago {

    /**
     * Funcion que instancia a partir de un nombre de medio de pago el objeto correspondiente
     * @param medioPago - nombre del mendio de pago a instanciar
     * @return
     * @throws BaseException 
     */
    public static MedioPago makeInstance( String medioPago ) throws BaseException {
        MedioPago mPago = null;
        DefMedioPago dMP = null;
        
        for( int i = 0; i < Base.tablaMediosPago.size(); i++ ) {
            dMP =Base.tablaMediosPago.get( i );
            if( dMP.getName().equals( medioPago) ) {
                try {
                    mPago = (MedioPago) Class.forName( dMP.getClassName() ).newInstance();
                    break;
                } catch (InstantiationException e) {
                    throw new BaseException( "falla carga de clase " + dMP.getClassName() + " instancia");
                } catch (IllegalAccessException e) {
                    throw new BaseException( "falla carga de clase " + dMP.getClassName() + " acceso");
                } catch (ClassNotFoundException e) {
                    throw new BaseException( "falla carga de clase " + dMP.getClassName() + " clase");
                }
            }
                
        }
        
        if( mPago != null ) {
            mPago.setNombre( medioPago );
            mPago.setDatos( new Datos( dMP.getRecordDef() ) );
            return mPago;
        }
        else
            throw new BaseException( "no existe Medio de Pago " + medioPago );
        
    }
}
