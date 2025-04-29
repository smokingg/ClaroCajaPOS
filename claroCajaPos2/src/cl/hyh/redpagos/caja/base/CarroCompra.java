package cl.hyh.redpagos.caja.base;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import cl.hyh.interfaces.ICajaView;

/**
 * Clase que implementa el carro de compras y que posee un arreglo de documentos a ser pagados
 * @author Rafael Hernandez
 *
 */
public class CarroCompra implements Serializable {

    private ArrayList<DocumentoPago> documentos = new ArrayList<DocumentoPago>();
    
    /**
     * Funcion que agrega un Documento de Pago al carro de compras(arreglo de documentos de pago)
     * @param doc
     */
    public void addDocment( DocumentoPago doc ) {
        documentos.add( doc );
        
    }
    
    /**
     * Retorna el arraylist con los documentos de pago
     * @return
     */
    public ArrayList<DocumentoPago> getDocumentos() {
        return documentos;
    }
    
    /**
     * Remueve del carro el elemento i
     * @param index
     */
    public void removeDocument( int index ) {
        documentos.remove( index );
    }
    
    /**
     * Remueve si es que existe el documento doc del carro de documentos
     * @param doc
     */
    public void removeDocumento( DocumentoPago doc ) {
        documentos.remove( doc );
    }
    
    /**
     * Retorna el documento en la posicion i del arraylist
     * @param index
     * @return
     */
    public DocumentoPago getDocument( int index ) {
        return documentos.get( index );
    }
    
    /**
     * Retorna la suma de los montos de los documentos que componen el carro
     * @return
     */
    public long getMontoTotal(){
        long monto = 0;
        for(int i = 0; i < this.documentos.size(); i++){
            monto = monto + this.documentos.get(i).getMonto();
        }
        return monto;
    }
    
    public boolean isDocument(String nombre, String numDoc){
        for(int i = 0; i < this.getDocumentos().size(); i++){
            if(this.getDocument(i).getClaveDoc().equals(numDoc)
               && this.getDocument(i).getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    public void removeAllDocument(  ) {
    	documentos.removeAll(getDocumentos());
    }
}
