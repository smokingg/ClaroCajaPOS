package cl.hyh.redpagos.caja.docpago;

import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;

/**
 * Representacion de un documento de publiguias
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class DocumentoEmpresasExternas extends DocumentoPago {

    public String toString() {
        // retorna detalle de documento
        String s = "";
        String nombre = "";
        if(this.getNombre().contains("Documento")){
            nombre = this.getNombre().substring("Documento".length(), this.getNombre().length());
        }
        else{
            nombre = this.getNombre();
        }
        if( nombre.trim().equals("Mundo") ){
            nombre = "188 Telefonica LD";
        }
        else if( nombre.trim().equals("Prosegur") ){
            nombre = "Empresa 3";
        }
        else if( nombre.trim().equals("Globus")){
            nombre = "120 Globus LD";
        }
        else if( nombre.trim().equals("MovistarDist")){
            nombre = "181 Movistar LD";
        }
        else if( nombre.trim().equals("Retail")){
            nombre = "Empresa 4";
        }
        else if( nombre.trim().equals("Publiguias")){
        	nombre = "Empresa 1";
        }
        else if( nombre.trim().equals("Terra")){
        	nombre = "Empresa 2";
        }
        
        s = nombre + "\nNumero Documento: " + this.getDatos().getStringValue( "NumeroDocumento" ) + "\nMonto: " +
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
        boolean anulacion = false;
        if(this.getNombre().contains("Anulacion")){
        	anulacion = true;
            if(this.getNombre().contains("Documento")){
	            nombre = this.getNombre().substring("Documento".length() + "Anulacion".length(), this.getNombre().length());
	        }
	        else{
	            nombre = this.getNombre();
	        }
        }
        else{
	        if(this.getNombre().contains("Documento")){
	            nombre = this.getNombre().substring("Documento".length(), this.getNombre().length());
	        }
	        else{
	            nombre = this.getNombre();
	        }
        }
        if( nombre.trim().equals("Mundo") ){
            nombre = "188 Telefonica LD";
        }
        else if( nombre.trim().equals("Prosegur") ){
            nombre = "Empresa 3";
        }
        else if( nombre.trim().equals("Globus")){
            nombre = "120 Globus LD";
        }
        else if( nombre.trim().equals("MovistarDist")){
            nombre = "181 Movistar LD";
        } 
        else if( nombre.trim().equals("Retail")){
            nombre = "Empresa 4";
        }
        else if( nombre.trim().equals("Publiguias")){
        	nombre = "Empresa 1";
        }
        else if( nombre.trim().equals("Terra")){
        	nombre = "Empresa 2";
        }
        if(anulacion){
        	nombre = "Anulacion " + nombre;
        }
        return String.format( "%-20s %10s $%15s", nombre,this.getDatos().getStringValue("NumeroDocumento"), Format.formatMontoPantalla(this.getDatos().getLongValue( "Monto" )) );
    }

}
