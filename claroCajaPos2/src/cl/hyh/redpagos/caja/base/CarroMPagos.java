package cl.hyh.redpagos.caja.base;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.redpagos.caja.mpago.ChequeBase;


/**
 * Clase que implementa el carro de Medios de Pago, que posee las distintas opciones de medios de pago, 
 * representadas en el arreglo de Medios de Pago con las distintas opciones
 * @author Rafael Hernandez
 *
 */
public class CarroMPagos implements Serializable {

    ArrayList<MedioPago> mediosPago = new ArrayList<MedioPago>();
    
    /**
     * Funcion que agrega un Medio de Pago al carro de medios de pago (arreglo de medios de pago)
     * @param mPago
     */
    public void addMedioPago( MedioPago mPago ) {
        mediosPago.add( mPago );
        for(int i = 0 ; i < this.getMediosPago().size() ; i++){
            if(this.getPago(i) instanceof ChequeBase ){
                ((ChequeBase)this.getPago(i)).claveSup = null;
            }
        }
        if(!mPago.getNombre().equals("Tarjeta") && !mPago.getNombre().contains("Cheque"))
            save();
    }
    
    /**
     *Funcion que salva el carro de medios de pago a disco en forma serializada
     * 
     */
    private void save(){
        ParamSet posCfg = Base.getParamSet( "posCfg" );
        
        try {            
            
            String s = "CarroMedioPago";
            
            FileOutputStream fos = new FileOutputStream( posCfg.getStringValue("PaymentDir") + s + ".dat" );
            ObjectOutputStream outStream = new ObjectOutputStream( fos );
            FileDescriptor fd = fos.getFD();
            
            outStream.writeObject( this );
            fos.flush();
            fd.sync();
            outStream.close();            
            
            FileOutputStream fctl = new FileOutputStream( posCfg.getStringValue("PaymentDir") + s + ".ctl" );
            outStream = new ObjectOutputStream( fctl );
            
            fd = fctl.getFD();
            fos.flush();
            fd.sync();
            
            outStream.close();            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar en disco,se cerrará la aplicación\n[" + e.getMessage()+"]\nClase:[CarroMPagos.java]", "Error", JOptionPane.ERROR_MESSAGE);
            Tools.logStackTrace(Base.logger, e);
            System.exit(1);
        }
    }
    
    /**
     * Remueve del carro el elemento i
     * @param index
     * @throws MedioPagoException
     */
    public void removeMedioPago( int index ) throws MedioPagoException {
        MedioPago mPago = mediosPago.get( index );        
        mPago.reversar( true );
        mediosPago.remove( index );
        for(int i = 0 ; i < this.getMediosPago().size() ; i++){
            if(this.getPago(i) instanceof ChequeBase ){
                ((ChequeBase)this.getPago(i)).claveSup = null;
            }
        }
        save();
    }
    
    public void removeMedioPagoAnular( int index ) throws MedioPagoException {
        MedioPago mPago = mediosPago.get( index );        
        try{
            mPago.anular();
        }
        catch(Exception e){
            throw new MedioPagoException("error en la anulacion");
        }
        mediosPago.remove( index );
        save();
    }

    /**
     * Retorna el Arraylist con los medios de pago
     * @return
     */
    public ArrayList<MedioPago> getMediosPago() {
        return mediosPago;
    }
    
    /**
     * Retorna el medio de pago en la posicion i del carro
     * @param index
     * @return
     */
    public MedioPago getPago( int index ) {
        return mediosPago.get( index );
    }
    
    /**
     * retorna la suma de los montos de los elementos que componen el carro
     * @return
     */
    public long getMontoTotal(){
        long monto = 0;
        for(int i = 0; i < this.mediosPago.size(); i++){
        	if(!this.mediosPago.get(i).getNombre().equals("AjusteSencillo")){
        		monto = monto + this.mediosPago.get(i).getMonto();
        	}
        }
        return monto;
    }
    
    /**
     * retorna la suma de los montos de los elementos que componen el carro
     * esto incluye los ajuste de sencillo para las anulaciones
     * @return
     */
    public long getMontoTotalAnulacion(){
        long monto = 0;
		for (int i = 0; i < this.mediosPago.size(); i++) {

			monto = monto + this.mediosPago.get(i).getMonto();

		}
        return monto;
    }
    /**
     * Retorna el monto total cancelado con el medio de pago efectivo que 
     * existe en el carro
     * @return
     */
    public long getMontoEfectivo(){
        long monto = 0;
        for(int i = 0; i < this.mediosPago.size(); i++){
            if(this.mediosPago.get(i).getNombre().equals("Efectivo")){
                monto = monto + this.mediosPago.get(i).getMonto();
            }
        }
        return monto;
    }
    
    public void borrarCarro(){
        for(int i = 0; i < this.getMediosPago().size(); i++){
            this.getMediosPago().remove(i);
        }
    }
    
    
    /**
     * Metodo que se encarga de recuperar el vuelto a entregar al clinete
     * este metodo considera los ajustes en el vuelto en caso de que exista paso en efectivo
     * 
     * @return monto del vuelto ajustado con sencillo en caso que aplique
     */
    public long getVuelto(long montoTotal){
        long ajuste = 0;
        for(int i = 0; i < this.mediosPago.size(); i++){
        	if(this.mediosPago.get(i).getNombre().equals("AjusteSencillo")){
        		ajuste = ajuste + this.mediosPago.get(i).getMonto();
        	}
        }
        ajuste = ajuste * -1;
        long vuelto =  ajuste + this.getMontoTotal() - montoTotal ;
        return vuelto;
    }
    
    
    /**
     * Metodo que se encarga de recuperar el vuelto a entregar al clinete
     * este metodo considera los ajustes en el vuelto en caso de que exista paso en efectivo
     * para anulaciones
     * 
     * @return monto del vuelto ajustado con sencillo en caso que aplique
     */
    public long getVueltoAnulacion(long montoTotal){
        long ajuste = 0;
        for(int i = 0; i < this.mediosPago.size(); i++){
        	if(this.mediosPago.get(i).getNombre().equals("AjusteSencillo")){
        		ajuste = ajuste + this.mediosPago.get(i).getMonto();
        	}
        }
        ajuste = ajuste * -1;
        long vuelto =  ajuste + this.getMontoTotalAnulacion() - montoTotal ;
        return vuelto;
    }
    
    
    /**
     * Metodo que indica si existe el medio de pago efectivo dentro del medio de carro
     * esto se utilizara para saber si se aplica o no el ajuste de sencillo
     * 
     * @return boolean true or false dependiendo si existe el medio de pago
     */
    public boolean existeMPEfectivo(){
        for(int i = 0; i < this.mediosPago.size(); i++){
        	if(this.mediosPago.get(i).getNombre().equals("Efectivo")){
        		return Boolean.TRUE;
        	}
        }
        return Boolean.FALSE;
    }
    

    
}
