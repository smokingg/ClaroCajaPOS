package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import ws.claro.cl.MedioPagoDTO;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class Transferencia extends MedioPago implements ITrxBase{

	 int estado = 0;
	 
	@Override
	public void init(Datos htParam) {
		// TODO Auto-generated method stub
		DefMedioPago dMP = Base.getDefMedioPago("Transferencia");
        this.setNombre( "Efectivo" );
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
                
            	/**
            	 * Se necesita servicio para consulta de Bancos para Depositos
            	 
                DefServicio defS = Base.getDefServicio("ConsultaBancosDepositos");
                dataS = new Datos(defS.getInputRecordDef());
                Servicio consultaBancos = null;
                */
                DefMedioPago def = Base.getDefMedioPago("Transferencia");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", Long.parseLong(vista.getEntryText()));
                estado = 1;
                return ICajaView._NOWAITFORACTION;
                
            case 1:
            	estado = 2;
                return ICajaView._NOWAITFORACTION;
                    
            case 2:
                estado = 3;
                int resp = 0;
                /**
            	 * Se necesita servicio para consulta de Bancos para Depositos
                try {
                    consultaBancos = FactoryServicio.makeInstance("ConsultaBancosDepositos");
                    consultaBancos.setRequest(this.dataS);
                    vista.showBusyWindow("Consultando", "Espere por favor...");
                    resp = consultaBancos.execute();
                    vista.hideBusyWindow();
                } catch (BaseException e) {
                    vista.hideBusyWindow();
                    Tools.logStackTrace(Base.logger, e);
                    JOptionPane.showMessageDialog(null, e.getMsg(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
                    Base.logger.info("El execute del servicio retorno error o timeout"); 
                    return 13;
                }
                bancosS = consultaBancos.getResponse().getArrayList("Banco");
                
                if(bancosS.size() == 0){
                    Base.logger.info("No existen bancos"); 
                    return 13;
                }
                String []aux = new String[bancosS.size()];
                for(int i = 0 ; i < bancosS.size() ; i++){
                    aux[i] = "Banco" + Base.getBancoCodigo(bancosS.get(i).getIntValue("Codigo"));
                }
                */
                vista.setEntryTextLabel("Bancos: ", true);
                //vista.setEntryList(aux, true, false);
                return ICajaView._WAITFORACTION; 
            case 3:
                estado = 4;
                int index = vista.getEntryListIndex();
                ParamSet posDat = Base.getParamSet("posDat");
                vista.hideAllEntries();
                /**
                 * Se necesitan bancos y Cientas para seleccionar donde se realizao la transferencia ??
                 
                cuentasS = bancosS.get(index).getArrayList("Cuenta");
                if(cuentasS.size() == 0){
                    Base.logger.info("No existen cuentas asociadas"); 
                    return 13;
                }
                cuentas = new String[cuentasS.size()];
                for(int i = 0 ; i < cuentasS.size() ; i++){
                    cuentas[i] = cuentasS.get(i).getStringValue("Numero");
                }
                vista.setEntryTextLabel("número Cuenta: ", true);
                vista.setEntryList(cuentas, true, false);
                
                Base.logger.info(bancosS.get(index).getIntValue("Codigo"));
                this.datos.setValue("Banco",bancosS.get(index).getIntValue("Codigo"));
                */
                return ICajaView._WAITFORACTION;
                    
            case 4:
                estado = 5;
                index = vista.getEntryListIndex();
                // cbriones: descomentar cuando se tenga la lista de Bancos/Cuentas
                //this.datos.setValue("Cuenta", cuentas[index]);
                vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                vista.setEntryTextLabel("Ingrese Fecha Depósito:", true);
                vista.setEntryText("", true, false, false,"[0-9]+","Fecha inválida");
                return ICajaView._WAITFORACTION;
            case 5:
                estado = 6;
                String fecha = vista.getEntryText();
                if(vista.getEntryText().equals("")){
                    this.datos.setValue("FechaDeposito",Tools.getFecha().substring(0, 10));
                }
                else{
                    SimpleDateFormat inFormat = new SimpleDateFormat( "yyyyMMdd" );
                    try {
                        Date date = inFormat.parse( fecha );
                        if(!inFormat.format(date).equals(fecha)){
                            estado = 5;
                            vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                            vista.setEntryTextLabel("Ingrese Fecha Transferencia:", true);
                            vista.setEntryText("", true, false, false,"[0-9]+","Fecha inválida");
                            return ICajaView._WAITFORACTION;
                        }
                    } catch (ParseException e) {
                        estado = 5;
                        vista.setEntryMessage( "Formato Fecha: AAAAMMDD", true );
                        vista.setEntryTextLabel("Ingrese Fecha Transferencia:", true);
                        vista.setEntryText("", true, false, false,"[0-9]+","Fecha inválida");
                        return ICajaView._WAITFORACTION;
                    }
                }
                this.datos.setValue("FechaDeposito",fecha);
                vista.setEntryMessage( "Ingrese N° Transferencia", true );
                vista.setEntryTextLabel("Ingrese N° Transferencia:", true);
                vista.setEntryText("", true, false, false,"[0-9]{1,30}","N° Transferencia no válido");
                return ICajaView._WAITFORACTION;
            case 6:
                estado = 7;
                this.datos.setValue("NumeroDocumento", vista.getEntryText());
                vista.setEntryMessage( "Ingrese Detalle Depositante", true );
                vista.setEntryTextLabel("Ingrese Detalle Depositante:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
            case 7:
                if(vista.getEntryText().length() > 64){
                    this.datos.setValue("Depositante", vista.getEntryText().substring(0, 64));
                }
                else{
                    this.datos.setValue("Depositante", vista.getEntryText());
                }
                this.datos.show("Deposito");
            
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
    
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	mp.setTipoTransaccion("mpTransferencia");
    	mp.setCodigoBanco(this.getDatos().getIntValue("Banco"));
    	mp.setNumeroCtaCte(this.getDatos().getStringValue("Cuenta"));
    	mp.setFechaVencimiento(this.getDatos().getStringValue("Fecha"));
    	mp.setNumeroDeposito(this.getDatos().getStringValue("NumeroDocumento"));
    	mp.setDepositante(this.getDatos().getStringValue("Depositante"));
    	
    	//mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodAutorizacion"));
    	//mp.setNumeroTarjeta(this.getDatos().getStringValue("NumeroUnico"));
    	//mp.setCantidadCuotas(Integer.parseInt(this.getDatos().getStringValue("NumeroCuotas")));
    	mp.setTipoTotal("5");
    }
    
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("Banco",mp.getCodigoBanco());
    	this.getDatos().setValue("Sucursal","");
    	this.getDatos().setValue("Cuenta",mp.getNumeroCtaCte());
    	this.getDatos().setValue("Serial",mp.getNumeroCheque());
    	this.getDatos().setValue("Rut",mp.getRutPagador());
    	this.getDatos().setValue("CodigoAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("NumeroDocumento",mp.getNumeroDeposito());
    	this.getDatos().setValue("Titular",mp.getNombrePagador());
    	this.getDatos().setValue("Fecha",mp.getFechaVencimiento());
    	//this.getDatos().setValue("TipoTotal","1002");
    	this.getDatos().setValue("TipoTotal","5");
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
