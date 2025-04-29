package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

public class DocumentoTLP extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nSerie TLP: " + this.getDatos().getLongValue( "SerieTLP" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "CorrelativoTerminal" );
    }
    public boolean isEliminable(CarroCompra carro){
        return false;
    }
}
