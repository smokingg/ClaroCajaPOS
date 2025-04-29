package cl.hyh.redpagos.caja.base;

/**
 * representa servicios que se han encolado a la cola SAF para ser ejecutados asincronicamente
 * por el thread ThreadSAF
 * @author Rafael Hernandez
 *
 */
public class ObjetoServicioSAF {

    private String mensajeSoap;
    
    public ObjetoServicioSAF( String soap ) {
        mensajeSoap = soap;
    }
}
