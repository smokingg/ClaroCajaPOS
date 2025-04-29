package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

/**
 * Representacion de un documento de Telefonicac Internacional
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DocumentoMovistar extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nCelular: " + this.getDatos().getLongValue( "Celular" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "NumFolio" );
    }
    public boolean isIngresable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("NumFolio"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){
                if(carro.getDocument(i).getDatos().getStringValue("TipoDocumento").equals(this.getDatos().getStringValue("TipoDocumento")))
                    return false;
            }
        }
        return true;
    }
    public String glosaCarro() {
        String nombre = "";
        if(this.getNombre().contains("Documento")){
            nombre = this.getNombre().substring("Documento".length(), this.getNombre().length());
        }
        else{
            nombre = this.getNombre();
        }
            return String.format( "%-20s %10s $%15s", nombre,this.getDatos().getStringValue("NumFolio"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }

}
