package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import ws.claro.cl.MedioPagoDTO;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;

public class OtroMedioPago extends MedioPago implements ITrxBase {
    int estado = 0;
    String opt[];
    String valorLista="";
    String depositante="";
    long montoTotal = 0;
    long montoDocumento= 0;
    String codigoDocumento="";
    int index=0;
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("OtroMedioPago");
        this.setNombre( "OtroMedioPago" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
            if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
                JOptionPane.showMessageDialog(null, "Medio Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            
            if(!isPermitido(vista)){
            	 JOptionPane.showMessageDialog(null, "Medio Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                 Base.logger.info("Medio de Pago no Autorizado");
            	return 13;
            }
            
            estado = 1;
            return this.setMontoMPago(vista);
           
            /*if(!isNotaVentaSap(vista)){
            	estado=0;
            }
            */
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            case 1:
            	estado=2;
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
            	montoDocumento = Long.parseLong(vista.getEntryText());
            	if(montoDocumento == 0){
                    estado = 1;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoDocumento), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
            	
            	vista.hideAllEntries();
                vista.setEntryTitle( "Seleccione Medio de Pago", true );                
                vista.setEntryMessage( "Otros Medio de Pago", true );
                vista.setEntryTextLabel("Seleccione:", true);
                opt = new String[8];      
                opt[0] = "AJIN";
                opt[1] = "AJPP";
                opt[2] = "COMU";
                opt[3] = "DEAL";
                opt[4] = "GPRO";
                opt[5] = "POST";
                opt[6] = "RRHH";
                opt[7] = "TAPA";
                vista.setEntryList(opt, true, false);
                return ICajaView._WAITFORACTION;
             
            case 2:
            	
                index = vista.getEntryListIndex();
                valorLista = opt[index];
            	estado=3;
            	vista.hideAllEntries();
                vista.setEntryTitle( "Rut Depositante", true );                
                vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
    			vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                 return ICajaView._WAITFORACTION;
            	
            	
            case 3:
            	
            	depositante = vista.getEntryText();
            	
            	
    			if("".trim().equalsIgnoreCase(depositante)){
    				estado = 3;
    				vista.setEntryMessage( "No ha ingresado ningun Rut - Ingrese nuevamente", true );
    				vista.setEntryTextLabel("Ingrese Rut:", true);
    				vista.setEntryText("", true, false, false,null,null);
    				return ICajaView._WAITFORACTION;
    			}
            	
    			if(!Tools.validarRut(depositante)){
    				estado = 3;
    				vista.setEntryMessage( "RUT  Inválido - Ingrese nuevamente", true );
    				vista.setEntryTextLabel("Ingrese Rut:", true);
    				vista.setEntryText("", true, false, false,null,null);
    				return ICajaView._WAITFORACTION;
    			}
            	
            	estado = 0;
                vista.hideAllEntries();
                vista.setEntryTitle( "Código de Promoción", true );                
                vista.setEntryMessage( "Ingrese código", true );
                vista.setEntryTextLabel("Ingrese código:", true);
            	vista.setEntryText("", true, false, false,"[0-9]+", "Código ingresado no válido");
            	return ICajaView._WAITFORACTION;
            	
            case 0:
            	
            	DefMedioPago def = Base.getDefMedioPago("OtroMedioPago");
            	if(def == null){
            		Base.logger.error("Medio de pago no encontrado");
            	}
            	this.datos = new Datos(def.getRecordDef());
            	this.datos.setValue("Monto", montoDocumento);
            	this.datos.setValue("pagadorNombre", valorLista);
            	this.datos.setValue("depositante", depositante);
            	this.datos.setValue("numeroDeposito", codigoDocumento);
            
            	this.datos.show("OtroMedioPago");
            	this.setNombre("OtroMedioPago");
            
            	//Metemos al carro de medios de pago el servicio
            
            	try {
            		vista.getOperTRV().addMedioPago(this);
            		//  vista.getOperTRV().setRegular(true);
            		ParamSet posDat = Base.getParamSet("posDat");
            		vista.getOperTRV().setCajero(posDat.getStringValue("Cajero"));
            	} catch (BaseException e) {
            		Tools.logStackTrace(Base.logger, e);
            		Base.logger.error("Error en agregar medio de pago al carro");
            	}
            return 13;
        }
        
        return 0;
    }
    
    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nMonto: " + Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    public boolean isIngresable(ICajaView vista){
        if(Base.getEdicion()){
            return false;
        }
        return true;
    }
    
    public boolean isPermitido(ICajaView vista){
    	
    	boolean permite = true;
    	int cantidad = vista.getOperTRV().getCarroCompras().getDocumentos().size();
    	
    	
    	for (int i = 0;  i<cantidad;i++){
    		DocumentoPago doc =  vista.getOperTRV().getCarroCompras().getDocumentos().get(i);
    		String name = (String)doc.getDatos().getStringValue("SistemaOrigenClaro");
    		if (doc.getTipoRegistro().equalsIgnoreCase("DEUDAMAYOR") || doc.getTipoRegistro().equalsIgnoreCase("DEUDAINTER")){
    			permite = false;
    			break;
    			
    		}
    	}
    	
    	
    	return permite;
    }
    
    
    public boolean isNotaVentaSap(ICajaView vista){
    	
    	boolean permite = false;
    	int cantidad = vista.getOperTRV().getCarroCompras().getDocumentos().size();
    	
    	
    	for (int i = 0;  i<cantidad;i++){
    		DocumentoPago doc =  vista.getOperTRV().getCarroCompras().getDocumentos().get(i);
    		if (doc.getDatos().getStringValue("SistemaOrigenClaro").equalsIgnoreCase("SAP")){
    			permite = true;
    			break;
    			
    		}
    	}
    	
    	
    	return permite;
    }
    
    
    
    // TODO validar valores de seteo en el DTO ???
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	if(this.getDatos().getLongValue("Monto") < 0){
    		mp.setMonto(-1*this.getDatos().getLongValue("Monto"));
    		
    	}
    	else{
    		mp.setMonto(this.getDatos().getLongValue("Monto"));
    		mp.setDepositante(this.getDatos().getStringValue("depositante"));
    		mp.setNumeroDeposito(this.getDatos().getStringValue("numeroDeposito"));
    		mp.setPagadorNombre(this.getDatos().getStringValue("pagadorNombre"));
    	}
    	mp.setTipoTransaccion("mpOtros");
    	// Validar cual debe ser el tipo total ??
    	// mp.setTipoTotal("1001");
    	mp.setTipoTotal("13");
    }
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("TipoTotal","13");
    }
}
