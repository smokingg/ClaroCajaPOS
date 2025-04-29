package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.JOptionPane;

import ws.claro.cl.MedioPagoDTO;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;

/**
 * Medio de pago: Efecivo
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class ValeVista extends MedioPago implements ITrxBase {
    int estado = 0;
    long montoP= 0;
    long montoTotal= 0;
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("ValeVista");
        this.setNombre( "ValeVista" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
        	//if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
            if(!this.isIngresable(vista)){
                JOptionPane.showMessageDialog(null, "Medio de Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            
            if(!vista.getOperTRV().isValid(this)){
            	Base.logger.info("No se pudo agregar un Segundo Medio de Pago ...");
                return 13;
            }
            
            estado = 0;
            return this.setMontoMPago(vista);  
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            case 0:
            	//TODO validar que si existen varios docs en el carro..
                // Se debe cancelar el total de carro con solo este medio de pago  !!
                if(vista.getOperTRV().getCarroCompras().getDocumentos().size()>1){
                	// Si el monto ingresado no coincide
                	if(vista.getOperTRV().getCarroCompras().getMontoTotal() > Long.valueOf(vista.getEntryText())){
                		estado = 0;
                		Base.logger.error("El monto no coincide con el total del carro para N documentos...");
                		JOptionPane.showMessageDialog(null, "Debe cancelar el Total del Carro","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                		 return this.setMontoMPago(vista);  
                	}
                }
                montoP = Long.parseLong(vista.getEntryText());
                montoTotal = vista.getOperTRV().getCarroCompras().getMontoTotal() - vista.getOperTRV().getCarroMediosPago().getMontoTotal();
            	if((montoP)  == 0){
                    estado = 0;
                    vista.setEntryMessage("Monto no válido: "+ Format.formatMonto(montoP), true );
                    vista.setEntryTextLabel("Ingrese monto a cancelar:", true);
                    vista.setEntryText(Long.toString(montoTotal), true, false, false,"[0-9]+","Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
                DefMedioPago def = Base.getDefMedioPago("ValeVista");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", montoP);
                estado = 1;
                vista.setEntryTextLabel("Bancos: ", true);
                //String[] bancos = Base.getBancos();
                String[] bancos = Base.traeBancos();
                
                vista.setEntryMessage("Bancos", true);
                vista.setEntryTitle( "Bancos", true );  
                vista.setEntryList(Base.getList(bancos,",",1), true, false);
                return ICajaView._WAITFORACTION; 
                
            case 1:
                estado = 2;
                int index = vista.getEntryListIndex();
                ParamSet posDat = Base.getParamSet("posDat");
                vista.hideAllEntries();
                //this.datos.setValue("Banco",Integer.parseInt(Base.getList( Base.getBancos(), ",", 0)[index]));
                this.datos.setValue("Banco",Integer.parseInt(Base.getList( Base.traeBancos(), ",", 0)[index]));
                String []bancosAsoc = posDat.getStringValue("banco").split("\\,");
                for(int i = 0 ; i < bancosAsoc.length ; i++){
                    if(this.datos.getStringValue("Banco").equals(bancosAsoc[i])){
                        this.datos.setValue("TipoTotal", "MismoBanco");
                        break;
                    }
                }
                vista.setEntryTitle( "VALE VISTA", true );
                vista.setEntryMessage( "Ingrese N°Documento", true );
                vista.setEntryTextLabel("Ingrese N°Documento:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,30}","N°Documento no válido");
                return ICajaView._WAITFORACTION;
            
            case 2:
                estado = 3;
                if( vista.getEntryText().equals("") || vista.getEntryText().length() > 30 ){
                    estado = 2;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "VALE VISTA", true );
                    vista.setEntryMessage( "N°Documento inválido", true );
                    vista.setEntryTextLabel("Ingrese N°Documento:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                this.datos.setValue("NumeroDocumento", vista.getEntryText());
                
                vista.hideAllEntries();
                vista.setEntryTitle( "VALE VISTA", true );
                vista.setEntryMessage( "Ingrese Beneficiario", true );
                vista.setEntryTextLabel("Ingrese Beneficiario:", true);
                vista.setEntryText("Claro Chile S.A.", true, false, false,null,null);
                return ICajaView._WAITFORACTION; 
                
            case 3:
            	
            	//estado = 3;
                if( vista.getEntryText().equals("") || vista.getEntryText().trim().equals("")){
                    estado = 3;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "VALE VISTA", true );
                    vista.setEntryMessage( "Beneficiario inválido", true );
                    vista.setEntryTextLabel("Ingrese Beneficiario:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
              //  this.datos.setValue("NumeroDocumento", vista.getEntryText());
            	this.datos.setValue("Nombre", vista.getEntryText()); 
                this.datos.setValue("Fecha", Tools.getFecha().substring(0, 10));  
                
                this.datos.show("ValeVista");
                this.setNombre("ValeVista");
            
                //Metemos al carro de medios de pago el servicio
                try {
                    vista.getOperTRV().addMedioPago(this);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                }
                return 13;
            }
        
        return 0;
    }
    
    // TODO validar valores de seteo en el DTO ???
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	//mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodigoAutorizacion"));
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	//mp.setDepositante(this.getDatos().getStringValue("Titular"));
    	
    	String year = "";
    	String month = "";
    	String day = "";
    	
    	System.out.println("Fecha Vale Vista: "+this.getDatos().getStringValue("Fecha"));
    	if(this.getDatos().getStringValue("Fecha").indexOf("-") > 0){
    		year = this.getDatos().getStringValue("Fecha").trim().substring(0,4);
        	System.out.println("Anho Vale Vista largo: "+year);
        	month = this.getDatos().getStringValue("Fecha").trim().substring(5,7);
        	System.out.println("Mes Vale Vista largo: "+month);
        	day = this.getDatos().getStringValue("Fecha").trim().substring(8,10);
        	System.out.println("Dia Vale Vista largo: "+day);
    	}else{
    		year = this.getDatos().getStringValue("Fecha").trim().substring(0,4);
        	System.out.println("Anho Vale Vista corto: "+year);
        	month = this.getDatos().getStringValue("Fecha").trim().substring(4,6);
        	System.out.println("Mes Vale Vista corto: "+month);
        	day = this.getDatos().getStringValue("Fecha").trim().substring(6,8);
        	System.out.println("Dia Vale Vista corto: "+day);
    	}
    	
    	//mp.setFechaVencimiento((new GregorianCalendar(year,month - 1,day)).toString());
    	//mp.setFechaVencimiento(this.getDatos().getStringValue("Fecha"));mp.setFechaVencimiento(year+"-"+month+"-"+day);
    	mp.setFechaVencimiento(year+"-"+month+"-"+day);
    	
    	mp.setSerieValeVista(this.getDatos().getStringValue("NumeroDocumento"));
    	mp.setNumeroDeposito(this.getDatos().getStringValue("NumeroDocumento"));
    	
    	mp.setPagadorNombre(this.getDatos().getStringValue("Nombre"));
    	
    	//mp.setNumeroCheque(this.getDatos().getStringValue("Serial"));
    	//mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	//mp.setPagadorRut(this.getDatos().getStringValue("Rut"));
    	//mp.setPagadorDigitoVerificador(this.getDatos().getStringValue("Dv"));
    	mp.setTipoTransaccion("mpValeVista");
    	// Validar cual debe ser el tipo total ??
    	// mp.setTipoTotal("1002");
    	mp.setTipoTotal("8");
    }
    
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("Banco",mp.getCodigoBanco());
    	this.getDatos().setValue("Sucursal","");
    	this.getDatos().setValue("Cuenta",mp.getNumeroCtaCte());
    	this.getDatos().setValue("Serial",mp.getNumeroCheque());
    	this.getDatos().setValue("Rut",mp.getRutPagador());
    	this.getDatos().setValue("CodigoAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("NumeroDocumento",mp.getSerieValeVista());
    	this.getDatos().setValue("Titular",mp.getNombrePagador());
    	this.getDatos().setValue("Fecha",mp.getFechaVencimiento());
    	//this.getDatos().setValue("TipoTotal","1002");
    	this.getDatos().setValue("TipoTotal","8");
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
}
