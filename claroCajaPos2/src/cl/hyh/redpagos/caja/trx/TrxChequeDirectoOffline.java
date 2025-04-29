package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.mpago.ChequeOffline;

public class TrxChequeDirectoOffline implements ITrxBase{
    int estado = 0;
    Datos data;
    long montoTotal = 0;
    DocumentoPago docP = null;
    MedioPago mPago = null;
    boolean armandoCheque = false;
    boolean ok = false;
    ChequeOffline cheque = null;
    
    public void init(Datos datosVista){        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 ) {
            estado = 0;
            
            /** Para bloquear la operacion !!
			JOptionPane.showMessageDialog(null, "No se encuentra habilitado este Módulo....", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            return 13;
			*/
            
            if(vista.getOperTRV().getCarroCompras().getDocumentos().size() == 0){
                JOptionPane.showMessageDialog(null, "Carro de Compras vacio... Operacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                JOptionPane.showMessageDialog(null, "Carro de Medios de Pago ya existe... Operacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9 ) {
            if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                vista.getOperTRV().getCarroMediosPago().borrarCarro();
            }
            return 13;
        }else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal();
                DefMedioPago defM = Base.getDefMedioPago("Cheque");
                data = new Datos(defM.getRecordDef()); 
                data.setValue("monto", montoTotal); 
                data.setValue("Monto", montoTotal);
                vista.setEntryMessage( "Ingrese monto a cancelar", true );
                vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+", "Monto ingresado no válido");
                return ICajaView._WAITFORACTION;
                
            case 1:
                if(armandoCheque == false && ok == false){
                    String montoM = vista.getEntryText();
                    if(Long.parseLong(montoM) != this.data.getLongValue("monto")){
                        estado = 1;
                        vista.setEntryMessage( "Montos deben coincidir:" + Format.formatMonto(Long.parseLong(montoM)), true );
                        vista.setEntryTextLabel("Montos deben coincidir:", true);
                        vista.setEntryText(Long.toString(this.data.getLongValue("monto")), true, false, false,"[0-9]+", "Monto ingresado no válido");
                        return ICajaView._WAITFORACTION;
                    }
                    
                    armandoCheque = true;
                    cheque = new ChequeOffline();
                    return cheque.execute(vista, 0, this.data);
                }
                else{
                    int respCheque = cheque.execute(vista, key, this.data);
                    if(respCheque == 13){
                        return 13;
                    }
                    if(respCheque != 15)
                        return respCheque;
                }
                
                estado = 2;
                return ICajaView._NOWAITFORACTION;
                
            case 2:
                estado = 3;
                vista.hideAllEntries();
                vista.setEntryTitle( "Pago Cheque", true );
                vista.setEntryTextLabel("Medio Pago:", true);
                vista.setEntryTextArea(vista.getOperTRV().imprimirBoletaPantalla(), true);
                return ICajaView._WAITFORACTION;
                
            case 3:            
            	// Se agrega confirmacion para generar Notificacion de Pago...
            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		 vista.showBusyWindow("Enviando", "Espere por favor...");
                     vista.getOperTRV().confirmarOffline(vista);
                     vista.hideBusyWindow();
                    
            	}
            	 vista.removeTRV(); 
                return 14;
        }
        return 0;
    }

}
