package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.LoginIn;
import cl.hyh.cajas.ws.impl.RecargaIn;
import cl.hyh.cajas.ws.impl.RecargaOut;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class TrxVtrRecarga implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    long celular= 0;
    String celularL = "";
    boolean ok = true;
    DocumentoPago doc = null;
    DocumentoPago docP = null;
    long montoMax = 0;
    long montoMin = 0;
    ParamSet recargaCfg = Base.getParamSet( "recargaCfg" );
    ParamSet recargaDat = Base.getParamSet( "recargaDat" );
    ParamSet posDat = Base.getParamSet( "posDat" );
    RecargaOut resp = null;
    long monto = 0;
    
    public void init(Datos datosVista){        
        montoMax = Long.parseLong(recargaCfg.getStringValue( "montoMax" ));
        montoMin = Long.parseLong(recargaCfg.getStringValue( "montoMin" ));
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 ) {
            estado = 0;
            if(vista.getOperTRV().getCarroCompras().getDocumentos().size() > 0){
                JOptionPane.showMessageDialog(null, "Carro de Compras no vacio\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 14;
            }
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F1 ) {
        	return 15;
        }
        else {
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga Vtr", true );
                if(ok)
                    vista.setEntryMessage( "Ingrese Número", true );
                else
                    vista.setEntryMessage( "Números no coinciden", true );
                vista.setEntryTextLabel("Ingrese Número:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Número ingresado no válido");
                return ICajaView._WAITFORACTION;
            case 1:
                estado = 2;
                celularL = vista.getEntryText();
                if( celularL.length() <= 8 && celularL.length() >= 12){
                    estado = 1;
                    vista.hideAllEntries();
                    vista.setEntryMessage( "Largos incorrectos", true );
                    vista.setEntryTextLabel("Ingrese Número:", true);
                    vista.setEntryText("", true, false, false,"[0-9]+", "Número ingresado no válido");
                    return ICajaView._WAITFORACTION;
                }                
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga Vtr", true );
                vista.setEntryMessage( "Reingrese Número", true );
                vista.setEntryTextLabel("Reingrese Número:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Número ingresado no válido");
                return ICajaView._WAITFORACTION;                
            case 2:
                estado = 3;
                
                if(!celularL.equals(vista.getEntryText())){
                    estado = 0;
                    ok = false;
                    return ICajaView._NOWAITFORACTION;
                }
                vista.hideAllEntries();
                vista.setEntryTitle( "Recarga Vtr", true );
                vista.setEntryMessage( "Ingrese monto recarga", true );
                vista.setEntryTextLabel("Ingrese monto recarga:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
            case 3:
                estado = 4;
                monto = Long.parseLong(vista.getEntryText());
                
                if(monto > montoMax || monto < montoMin){
                    estado = 3;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Recarga Vtr", true );
                    vista.setEntryMessage( "Monto no válido", true );
                    vista.setEntryTextLabel("Ingrese monto recarga:", true);
                    vista.setEntryText("", true, false, false,"[0-9]+", "Monto ingresado no válido");
                    return ICajaView._WAITFORACTION;
                }               

                //Crear documento de recarga
                DocumentoPago docR = null;
                try {
                    docR = FactoryDocumentoPago.makeInstance("Recarga");
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }
                docR.getDatos().setValue("Telefono", celularL );
                docR.getDatos().setValue("Monto", monto);
                vista.getOperTRV().getCarroCompras().addDocment(docR);
           
                vista.paintButtons(Tools.getBotones(5));
                vista.hideAllEntries();
                vista.hideEnter();
                vista.getOperTRV().setVentaDirecta("true");
                vista.getOperTRV().setRecargaMovistar("true");
                vista.getOperTRV().setRecarga( true );
                return ICajaView._WAITFORACTION;
        }
        return 0;
    }
}
