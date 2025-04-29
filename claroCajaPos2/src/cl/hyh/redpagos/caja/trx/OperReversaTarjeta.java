package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;

public class OperReversaTarjeta extends OperReversa {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    int estado = 0;
    
    public OperReversaTarjeta(){
        this.setServicio("EnvioReversaTarjeta");
    }
    public void init(){
        
    }
    public int execute(Datos htParam){
        this.datos = new Datos(Base.getDefServicio("EnvioReversaTarjeta").getInputRecordDef());
        this.datos.setValue("NumeroOperacion", htParam.getStringValue("NumeroOperacion"));
        this.datos.setValue("Caja", htParam.getStringValue("Caja"));        
        this.datos.getDatos("ReversaTbk").asignaPorNombre(htParam);
        confirmar();
        return 0;
    }
    public void confirmar(){
        String fecha = Tools.getFecha();
        String hora = Tools.getTime();
        
        this.setFecha(fecha);
        this.setHora(Tools.getTime());
        this.setOperacion("EnvioReversa");
        
        this.grabarSaf();
        //Imprimimos un voucher para la remesa
    }
}
