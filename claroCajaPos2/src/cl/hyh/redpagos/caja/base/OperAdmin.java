package cl.hyh.redpagos.caja.base;

import java.io.Serializable;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;


/**
 * representa una operación de tipo administrativa ejecutada en la caja que debe enviarse al IF
 * para su proceso
 * 
 * Pasos:
 * 
 *  - se instancia objeto indicando nombre del servicio y si es online o saf
 *  - se cargan los datos en el area de datos del objeto
 *  - se llama a execute para que se ejecute el servicio online o se grabe
 *    el requerimiento en el saf de operaciones
 *  - se recuperan resultados del area de datos del objeto
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class OperAdmin extends Oper implements Serializable, ITrxBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean online;

    public OperAdmin( String nombreServicio, boolean online ) {
        this.setServicio(nombreServicio);
        this.online = online;
        this.datos = new Datos( Base.getDefServicio( nombreServicio ).getInputRecordDef() );
    }
    public OperAdmin(){
        
    }
    public void init(Datos datosVista){
        
    }
    public void init(String nombreServicio, boolean online){
        this.setServicio(nombreServicio);
        this.online = online;
        this.datos = new Datos( Base.getDefServicio( nombreServicio ).getInputRecordDef() );
    }
    public int execute(ICajaView vista, int key, Datos htParam) {
        // TODO Auto-generated method stub
        return 0;
    }
    public boolean isReversable(){
        if( this.getOperacion().contains("EnvioRemesa") ){
            return true;
        }
        return false;
    }
    public String imprimirJournalPantalla(){
        String s = "";
        s = s + "Fecha: " + this.getFecha() + "\n";
        s = s + "Hora: " + this.getHora() + "\n";
        s = s + "N°Operacion: " + this.getNumeroOperacion() + "\n";
        s = s + "Operacion: " + this.getOperacion() + "\n";
        s = s + "---------------------------------\n" ;
        s = s + this.getDatos().showDatos(this.getOperacion());
        return s;
    }
  
}
