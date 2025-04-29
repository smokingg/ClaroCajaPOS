package cl.hyh.redpagos.caja.docpago;

import javax.swing.JOptionPane;

import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

/**
 * Representacion de un documento atis
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class AtisTelefonica extends DocumentoPago {
    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = "Documento: Telefónica" + "\nNumero Documento: " + this.getDatos().getStringValue( "DocNumeroLATAM" ) 
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
    
    public boolean isIngresable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("DocNumeroLATAM"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){
                return false;
            }
        }
        if(carro.getDocumentos().size() == 0){
            return true;
        }
        return true;
    }
    public boolean isIngresableParcial(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("DocNumeroLATAM"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){
                DocumentoPago doc = carro.getDocument(i);
                if(doc.getDatos().getStringValue("Cliente").equals(this.getDatos().getStringValue("Cliente"))
                        && doc.getDatos().getStringValue("Cuenta").equals(this.getDatos().getStringValue("Cuenta"))){
                    return false;
                }
            }
        }
        if(carro.getDocumentos().size() == 0){
            return true;
        }
        return true;
    }
    public int isIngresableCuotas(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getClaveDoc().equals(this.getDatos().getStringValue("DocNumeroLATAM"))
               && carro.getDocument(i).getNombre().equals(this.getNombre())){
                return 2;
            }
        }
        if(!this.getDatos().getStringValue("DocTipo").equals("05")  ) {
            return 0;
        }
        if(this.getDatos().getIntValue("NumeroCuota") == Base.cuotaCero ) {
            return 0;
        }
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getNombre().equals("AtisTelefonica")){
                DocumentoPago doc = carro.getDocument(i);
                if(doc.getDatos().getStringValue("Cliente").equals(this.getDatos().getStringValue("Cliente"))
                   && doc.getDatos().getStringValue("Cuenta").equals(this.getDatos().getStringValue("Cuenta"))){
                    if(this.getDatos().getIntValue("NumeroCuota") == (doc.getDatos().getIntValue("NumeroCuota") + 1)){
                        return 0;
                    }
                }
            }
        }
        if(this.getDatos().getIntValue("NumeroCuota") != Base.cuotaCero ) {
            return 1;
        }
        if(carro.getDocumentos().size() == 0){
            return 0;
        }
        return 3;
    }
    public boolean isEliminable(CarroCompra carro){
        for(int i = 0; i < carro.getDocumentos().size(); i++){
            if(carro.getDocument(i).getNombre().equals("AtisTelefonica")){
                DocumentoPago doc = carro.getDocument(i);
                if(doc == this){
                    continue;
                }
                if(doc.getDatos().getStringValue("Cliente").equals(this.getDatos().getStringValue("Cliente"))
                   && doc.getDatos().getStringValue("Cuenta").equals(this.getDatos().getStringValue("Cuenta"))
                   && doc.getDatos().getStringValue("DocTipo").equals(this.getDatos().getStringValue("DocTipo"))){
                    if(this.getDatos().getIntValue("NumeroCuota") < doc.getDatos().getIntValue("NumeroCuota")){
                        JOptionPane.showMessageDialog(null, "Se debe eliminar la cuota más reciente", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                    else{                        
                        continue;
                    }
                }
            }
        }
        return true;
    }
    
}
