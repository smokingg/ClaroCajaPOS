package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

public class AtisAbonoFuturoCtaCte extends DocumentoPago {


    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nCliente: " + this.getDatos().getStringValue( "Cliente" ) +
            "\nCuenta: " + this.getDatos().getStringValue( "Cuenta" ) + "\nMonto: " +
            Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    
}
