package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

public class AtisAbonoCtaCte extends DocumentoPago {


    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = "Documento: Pago Adelantado Telefónica" + "\nCliente: " + this.getDatos().getStringValue( "Cliente" ) +
            "\nCuenta: " + this.getDatos().getStringValue( "Cuenta" ) + "\nMonto: " +
            Format.formatMonto(this.getDatos().getLongValue( "Monto" )) + "\nTeléfono: (" + 
            this.getDatos().getStringValue("Area") + ")" + this.getDatos().getStringValue("Telefono");
        
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    
    public boolean isIngresable(CarroCompra carro){
        return true;
    }
    
}
