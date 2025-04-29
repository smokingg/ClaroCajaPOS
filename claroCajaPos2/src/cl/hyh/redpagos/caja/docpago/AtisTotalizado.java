package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

public class AtisTotalizado  extends DocumentoPago {
    

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Documento: " + this.getDatos().getStringValue( "DocNumeroLATAM" ) 
        + "\nTipo Documento: " + this.getDatos().getStringValue( "DocTipo" ) 
        + "\nMonto: " + Format.formatMonto(this.getDatos().getLongValue( "Monto" ))
        + "\nCliente: " + this.getDatos().getStringValue( "Cliente" )
        + "\nCuenta: " + this.getDatos().getStringValue( "Cuenta" );
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "DocNumeroLATAM" );
    }
    
    public boolean isEliminable(CarroCompra carro){
        return true;
    }
    public boolean isIngresable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("DocNumeroLATAM"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){
                return false;
            }
        }
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getNombre().equals("AtisTotalizado")){
                DocumentoPago doc = carro.getDocument(i);
                if(doc.getDatos().getStringValue("Cliente").equals(this.getDatos().getStringValue("Cliente"))
                   && doc.getDatos().getStringValue("Cuenta").equals(this.getDatos().getStringValue("Cuenta"))){
                    return false;
                }
            }
        }
        return true;
    }
    
}
