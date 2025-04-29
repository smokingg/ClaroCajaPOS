package cl.hyh.redpagos.caja.base;

import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;

/**
 * Factory que instancia Documentos de Pago a partir de los nombres de documentos que se reciben. Implementa el 
 * patron fábrica
 * @author Rafael Hernandez
 *
 */
public class FactoryDocumentoPago {

    /**
     * Funcion que a partir de un nombre de documento instancia un objeto del tipo de documento correspondiente
     * @param documentName
     * @return Document
     */
    public static DocumentoPago makeInstance(String documentName) throws BaseException {
        
        DefDocumentoPago dDoc = Base.getDefDocumentoPago( documentName );
        
        if( dDoc != null ) {
            try {
                DocumentoPago doc = (DocumentoPago) Class.forName( dDoc.getClassName() ).newInstance();
                doc.setNombre( documentName );
                doc.setDatos( new Datos( dDoc.getRecordDef() ) );
                doc.setVisible( true );
                doc.setMontoVisible( 0 );
                doc.setMonto( 0 );
                return doc;
            } catch (InstantiationException e) {
                Tools.logStackTrace( Base.logger, e );
                throw new BaseException( e, "error Class.forName " + dDoc.getClassName() );
            } catch (IllegalAccessException e) {
                Tools.logStackTrace( Base.logger, e );
                throw new BaseException( e, "error Class.forName " + dDoc.getClassName() );
            } catch (ClassNotFoundException e) {
                Tools.logStackTrace( Base.logger, e );
                throw new BaseException( e, "error Class.forName " + dDoc.getClassName() );
            }
        }
        
        throw new BaseException( "documento " + documentName + " indefinido" );
    }
    
}
