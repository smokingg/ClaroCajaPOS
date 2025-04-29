package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

/**
 * Representacion de un documento de Telefonica
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DocumentoTelefonicaEmpresas extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nNumero Documento: " + this.getDatos().getStringValue( "NumeroDocumento" ) + "\nMonto: " +
        Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean equals( DocumentoPago docPago ) {
        return false;
    }
    public String getClaveDoc(){
        return this.getDatos().getStringValue( "NumeroDocumento" );
    }    
    public boolean isIngresable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("NumeroDocumento"))
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
        String n = "";
        if(nombre.equals("TelefonicaEmpresas")){
        	n = "Documento Vtr";
        }
        else if(nombre.equals("TelefonicaEmpresasDetalle")){
        	n = "Movil Vtr";
        }
            return String.format( "%-20s %10s $%15s", n,this.getDatos().getStringValue("NumeroDocumento"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }

}
