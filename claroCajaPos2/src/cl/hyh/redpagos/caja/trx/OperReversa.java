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
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;

public class OperReversa extends OperAdmin implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    int estado = 0;
    
    public OperReversa(){
        this.setServicio("EnvioReversa");
    }
    
    public void init(){
        
    }
    
    public int execute(Datos htParam){
    	
        this.datos.setValue("NumeroOperacion", htParam.getStringValue("NumeroOperacion"));
        this.datos.setValue("Caja", htParam.getStringValue("Caja"));
        this.datos.setValue("Entidad", htParam.getStringValue("Entidad"));
        this.datos.setValue("Agencia", htParam.getStringValue("Agencia"));
        if(!htParam.getStringValue("Agencia").equals(""))
            this.datos.setValue("Cajero", htParam.getStringValue("Cajero"));
        this.setCajero(htParam.getStringValue("Cajero"));
        this.setFechaPago(Tools.getFecha());
        this.setRegular(Boolean.parseBoolean(htParam.getStringValue("Regular")));
        confirmar();
        return 0;
    }
    
    public void confirmar(){
        String fecha = Tools.getFecha();
        String hora = Tools.getTime();
        
        this.setFecha(fecha);
        this.setHora(Tools.getTime());
        this.setOperacion("EnvioReversa");
        
        ParamSet pList = Base.getParamSet( "posDat" );
        this.numeroOperacion = pList.getLongValue( "NumeroOperacion" );        
        pList.setValue( "NumeroOperacion", numeroOperacion + 1 );
        pList.setValue("FechaPago", Tools.getFecha());
        pList.save();
        this.grabarSaf();
        //Imprimimos un voucher para la remesa
    }
}
