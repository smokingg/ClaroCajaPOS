package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

/**
 * Representacion de un documento atis
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DocumentoAtisDevolucion extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Documento: " + this.getDatos().getStringValue( "DocNumeroLATAM" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "DocSaldo" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "DocNumeroLATAM" );
    }
}
