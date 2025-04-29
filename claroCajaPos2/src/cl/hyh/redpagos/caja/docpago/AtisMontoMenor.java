package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

/**
 * Representacion de un documento atis
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class AtisMontoMenor extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Documento: " + this.getDatos().getStringValue( "DocNumeroLATAM" ) 
        + "\nTipo Documento: " + this.getDatos().getStringValue( "DocTipo" )
        + "\nMonto: " +Format.formatMonto(this.getDatos().getLongValue( "DocSaldo" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "DocNumeroLATAM" );
    }
    public boolean isIngresable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("DocNumeroLATAM"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){
                return false;
            }
        }
        return true;
    }
}
