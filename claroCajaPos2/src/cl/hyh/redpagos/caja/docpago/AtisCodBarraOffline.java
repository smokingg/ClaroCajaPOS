package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

public class AtisCodBarraOffline extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Documento: " + this.getDatos().getStringValue( "DocNumeroLATAM" ) 
        + "\nTipo Documento: " + this.getDatos().getStringValue( "DocTipo" ) 
        + "\nMonto: " + Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "DocNumeroLATAM" );
    }
    
}
