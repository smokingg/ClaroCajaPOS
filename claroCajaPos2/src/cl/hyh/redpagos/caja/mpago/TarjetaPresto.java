package cl.hyh.redpagos.caja.mpago;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.MedioPagoException;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.PlanCuotas;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;

/**
 * Medio de pago: Efecivo
 * 
 * @author Rafael Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TarjetaPresto extends MedioPago implements ITrxBase {
    int estado = 0;
    
    public void init(Datos datosVista){
        DefMedioPago dMP = Base.getDefMedioPago("TarjetaPresto");
        this.setNombre( "TarjetaPresto" );
        this.setDatos( new Datos( dMP.getRecordDef() ) );
    }
    
    public int execute(ICajaView vista, int key, Datos data){ 
        if( key == 0 ) {
            if(!this.isIngresable(vista) || !vista.getOperTRV().isValid(this)){
                JOptionPane.showMessageDialog(null, "Medio Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            estado = 0;
            if( data.getLongValue("monto")==0 )
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
                estado = 1;
                DefMedioPago def = Base.getDefMedioPago("TarjetaPresto");
                if(def == null){
                    Base.logger.error("Medio de pago no encontrado");
                    return 13;
                }
                this.datos = new Datos(def.getRecordDef());
                this.datos.setValue("Monto", Long.parseLong(vista.getEntryText()));
                if(vista.getOperTRV().getCarroCompras().getMontoTotal() != this.datos.getLongValue("Monto")){
                    estado = 0;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Tarjeta Presto", true );                
                    vista.setEntryMessage( "Ingrese monto por el total", true );
                    vista.setEntryTextLabel("Ingrese monto por el total:", true);
                    vista.setEntryText(Long.toString(vista.getOperTRV().getCarroCompras().getMontoTotal()), true, false, false, "[0-9]+", "Monto no valido");
                    return ICajaView._WAITFORACTION;
                }
                vista.hideAllEntries();
                vista.setEntryTitle( "Tarjeta Presto", true );                
                vista.setEntryMessage( "Ingrese tarjeta en lector", true );
                vista.setEntryTextLabel("Ingrese tarjeta en lector:", true);
                //vista.setEntryText("", true, false, false,null,null);
                //vista.setEntryText("%B2034479310010^RAFAEL HERNANDEZ CONTRERA^1007501000001000068600716000000?;7444444444444=10075010000017160686?", true, false, false,null,null);
                //vista.setEntryText("%B9200618800360008^PRUEBA5 PRUEBA TBK 1 P^1603501000001000068600937000000;?9200618800360006=16035010000019370686?", true, false, false,null,null);
                vista.setEntryText("%B2012494580023^PRUEB12 PRUEBA TBK 2 P^?;2012494580023=1603?",true, false, false,null,null);
                
                return ICajaView._WAITFORACTION;
            case 1:
                estado = 2;

                try {
                    parsearTarjetaPresto( vista.getEntryText() );
                } catch (BaseException e1) {
                    estado = 1;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Tarjeta Presto", true );                
                    vista.setEntryMessage( e1.getMsg(), true );
                    vista.setEntryTextLabel("Ingrese tarjeta en lector:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                vista.hideAllEntries();
                vista.setEntryTitle( "Tarjeta Presto", true );                
                vista.setEntryMessage( "Ingrese ultimos 4 digitos", true );
                vista.setEntryTextLabel("Ingrese ultimos 4 digitos:", true);
                vista.setEntryText("", true, false, false,"[0-9]{4}","Error en digitos");
                return ICajaView._WAITFORACTION;
            case 2:
                estado = 3;
                String s = this.datos.getStringValue("NumTarjeta");
                if(!s.substring(s.length()-4,s.length()).equals(vista.getEntryText())){
                    estado = 2;
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Tarjeta Presto", true );                
                    vista.setEntryMessage( "Ingrese ultimos 4 digitos", true );
                    vista.setEntryTextLabel("Ingrese ultimos 4 digitos:", true);
                    vista.setEntryText("", true, false, false,"[0-9]{4}","Error en digitos");
                    return ICajaView._WAITFORACTION;
                }
            case 3:
                estado = 4;
                vista.hideAllEntries();
                vista.setEntryTitle( "Tarjeta Presto", true );                
                vista.setEntryMessage( "Ingrese plan de pago", true );
                vista.setEntryTextLabel("Ingrese plan de pago:", true);
                def = Base.getDefMedioPago("TarjetaPresto");
                String []planes = new String[def.getPlanesCuotas().size()];
                for(int i = 0 ; i < def.getPlanesCuotas().size(); i++){
                    planes[i] = def.getPlanesCuotas().get(i).getName();
                }
                vista.setEntryList(planes, true, false);
                return ICajaView._WAITFORACTION;
            case 4:
                int index = vista.getEntryListIndex();
                def = Base.getDefMedioPago("TarjetaPresto");
                PlanCuotas plan = def.getPlanesCuotas().get(index);
                this.datos.setValue( "TipoCredito", plan.getTipoCredito() );
                if( plan.isTieneCuotas() ) {
                    this.datos.setValue("Interes", "02");
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Tarjeta Presto", true );                
                    vista.setEntryMessage( "Ingrese N°Cuotas", true );
                    vista.setEntryTextLabel("Ingrese N°Cuotas:", true);
                    def = Base.getDefMedioPago("TarjetaPresto");
                    int []cuotas = plan.getCuotas();
                    String []cuota = new String[cuotas.length];
                    for(int i = 0; i < cuotas.length; i++){
                        cuota[i] = Integer.toString(cuotas[i]);
                    }
                    vista.setEntryList(cuota, true, false);
                    estado = 5;
                    return ICajaView._WAITFORACTION;
                }
                else {
                    this.datos.setValue("Interes", "01");
                    this.datos.setValue( "NumeroCuotas", plan.getCuotas()[0] );
                    long []cuotas = this.getCuotasSI(this.datos.getLongValue("Monto"));
                    this.datos.setValue( "cuota1", cuotas[0] );
                    this.datos.setValue( "cuota2", cuotas[1] );
                    this.datos.setValue( "cuota3", cuotas[2] );
                    estado = 6;
                    return ICajaView._NOWAITFORACTION;
                }                
            case 5:
                estado = 6;
                def = Base.getDefMedioPago("TarjetaPresto");
                String str = vista.getEntryList()[vista.getEntryListIndex()];
                this.datos.setValue("NumeroCuotas", new Integer( str ).intValue() );
                return ICajaView._NOWAITFORACTION;
            case 6:
                estado = 8;
                //Listos para preguntar por el servicio
                ParamSet prestoCfg = Base.getParamSet("prestoCfg");
                ParamSet prestoDat = Base.getParamSet("prestoDat");
                
                this.datos.setValue("MerchantType", prestoCfg.getStringValue("MerchantType"));
                this.datos.setValue("AcqInstCode", prestoCfg.getStringValue("AcqInstCode"));
                this.datos.setValue("AcqInstId", prestoCfg.getStringValue("AcqInstId"));
                this.datos.setValue("FwdInstCode", prestoCfg.getStringValue("FwdInstCode"));
                this.datos.setValue("MerchantCode", prestoCfg.getStringValue("MerchantCode"));
                this.datos.setValue("CountryCode", prestoCfg.getStringValue("CountryCode"));
                this.datos.setValue("CurrencyCode", prestoCfg.getStringValue("CurrencyCode"));
                
                this.datos.setValue("NumJournal", prestoDat.getStringValue("NumJournal"));
                this.datos.setValue("Local", prestoDat.getStringValue("Local"));
                this.datos.setValue("Address", prestoDat.getStringValue("Address"));
                this.datos.setValue("City", prestoDat.getStringValue("City"));
                this.datos.setValue("TerminalId", prestoDat.getStringValue("TerminalId"));
                
                String date1 = Tools.getFechaHora();
                String time = Tools.getTime();
                String date2 = Tools.getFecha();
                
                this.datos.setValue("TrxDateTime", date1.substring(4));
                long numJournal = prestoDat.getLongValue( "NumJournal" );
                prestoDat.setValue( "NumJournal", numJournal + 1 );
                prestoDat.save();
                
                this.datos.setValue("NumJournal", numJournal);
                this.datos.setValue("TrxLocTime", time);
                this.datos.setValue("TrxLocDate", date2.substring(4));
                this.datos.setValue("DateSett", date2);
                this.datos.setValue("RetRefNum", "00");
                
                this.datos.show("PRESTO");
                
                Servicio presto = null; 
                int resp = 0;
                try {
                    presto = FactoryServicio.makeInstance("ValidaPresto");
                    presto.setRequest(this.datos);
                    vista.showBusyWindow("Consultando", "Espere por favor...");
                    resp = presto.execute();
                } catch (BaseException e) {
                    vista.hideBusyWindow();
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }
                vista.hideBusyWindow();
                if(resp == Servicio.RC_CONNECT_ERROR){
                    Base.logger.error("Error en el execute del servicio");
                    JOptionPane.showMessageDialog(null, "No se pudo realizar Medio Pago Presto\n" , "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                else if(resp == Servicio.RC_TIMEOUT){
                    Base.logger.error("Timeout en el execute del servicio");
                    JOptionPane.showMessageDialog(null, "Timeout se debe reversar", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    //Reversamos el medio de pago
                    estado = 9;
                    return ICajaView._NOWAITFORACTION;
                }
                try {
                    presto.checkRetcodeReversa();
                } catch (BaseException e1) {
                    if(e1.getMsg().equals("timeout error")){
                        Base.logger.error("Timeout en el execute del servicio");
                        JOptionPane.showMessageDialog(null, presto.getHeaderOut().getStringValue("RetDesc"), "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        //Reversamos el medio de pago
                        estado = 9;
                        return ICajaView._NOWAITFORACTION; 
                    }
                    else if(e1.getMsg().equals("connect error")){
                        Base.logger.error("Error en el execute del servicio");
                        JOptionPane.showMessageDialog(null, presto.getHeaderOut().getStringValue("RetDesc") , "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                }
                
                if(!presto.getResponse().getDatos("DocumentoValidaPresto").getStringValue("CodigoAutorizacion").equals("0")){
                    JOptionPane.showMessageDialog(null, "Medio Pago Presto autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    this.datos.setValue("AuthCode", presto.getResponse().getDatos("DocumentoValidaPresto").getStringValue("AuthCode"));
                    this.datos.setValue("RespCode", presto.getResponse().getDatos("DocumentoValidaPresto").getStringValue("RespCode"));
                    this.datos.setValue("MontoCuota", Long.parseLong( presto.getResponse().getDatos("DocumentoValidaPresto").getStringValue("MontoCuota")) );
                    this.datos.setValue("InteresMensual", presto.getResponse().getDatos("DocumentoValidaPresto").getStringValue("InteresMensual"));
                    this.datos.setValue("InteresAnual", presto.getResponse().getDatos("DocumentoValidaPresto").getStringValue("InteresAnual"));
                    this.datos.setValue("currentDate", Format.formatFechaPresto(this.datos.getStringValue("DateSett")));
                    this.datos.setValue("currentTime", Format.formatHoraPresto(this.datos.getStringValue("TrxLocTime")));
                    String tarj = this.datos.getStringValue("NumTarjeta");
                    this.datos.setValue("cuatroDigitos",tarj.substring(tarj.length()-4,tarj.length()));
                    ParamSet pDat = Base.getParamSet("posDat");
                    this.datos.setValue("boleta", pDat.getLongValue("NumeroOperacion"));
                }
                else{                    
//                    JOptionPane.showMessageDialog(null, "Operacion rechazada\n", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Operacion rechazada\n", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                return ICajaView._NOWAITFORACTION;
            case 8:
                try {
                    vista.getOperTRV().addMedioPago(this);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                }
                return 15;
            case 9:
                try {
                    this.reversar(false);
                } catch (MedioPagoException e) {
                    Tools.logStackTrace(Base.logger, e);
                }
                return 13;
        }
        
        return 0;
    }
    
    public void reversar(boolean llamadaExt) throws MedioPagoException {
        String date1 = Tools.getFechaHora();
        String time = Tools.getTime();
        String date2 = Tools.getFecha();
        
        this.datos.setValue("OriTrxDateTime", this.datos.getStringValue("TrxDateTime"));
        this.datos.setValue("TrxDateTime", this.datos.getStringValue("TrxDateTime"));        
        this.datos.setValue("TrxLocTime", this.datos.getStringValue("TrxLocTime"));
        this.datos.setValue("TrxLocDate", this.datos.getStringValue("TrxLocDate"));
        this.datos.setValue("DateSett", this.datos.getStringValue("DateSett"));
        
        if(llamadaExt){
            this.datos.setValue("MotivoAnulacion", "11");
        }
        else{
            this.datos.setValue("MotivoAnulacion", "01");
        }
        Servicio presto = null;
        
        try {
            presto = FactoryServicio.makeInstance("ReversaPresto");
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
        }
        presto.setRequest(this.datos);
        try {
            int resp = presto.execute();
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
        }
    }
    
    public void anular() throws MedioPagoException {
        String date1 = Tools.getFechaHora();
        String time = Tools.getTime();
        String date2 = Tools.getFecha();
        int resp = 0;
        
        this.datos.setValue("OriTrxDateTime", this.datos.getStringValue("TrxDateTime"));
        this.datos.setValue("TrxDateTime", date1.substring(4));        
        this.datos.setValue("TrxLocTime", time);
        this.datos.setValue("TrxLocDate", date2.substring(4));
        this.datos.setValue("DateSett", date2);
        this.datos.setValue("ProcCode", 203000);
        String tarj = this.datos.getStringValue("NumTarjeta");
        this.datos.setValue("cuatroDigitos",tarj.substring(tarj.length()-4,tarj.length()));
        this.datos.setValue("MotivoAnulacion", "11");
        Servicio presto = null;
        
        try {
            presto = FactoryServicio.makeInstance("AnularPresto");
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
        }
        presto.setRequest(this.datos);
        try {
            resp = presto.execute();
        } catch (BaseException e) {
            Tools.logStackTrace(Base.logger, e);
            throw new MedioPagoException( "Connect error" );
        }
        if(resp == Servicio.RC_CONNECT_ERROR){
            Base.logger.error("Error en el execute del servicio");     
            throw new MedioPagoException( "Connect error" );
        }
        else if(resp == Servicio.RC_TIMEOUT){
            Base.logger.error("Timeout en el execute del servicio");
            
            //Creamos la reversa
            try {
                this.reversar(true);
            } catch (MedioPagoException e) {
                Tools.logStackTrace(Base.logger, e);
            }            
            throw new MedioPagoException( "Connect timeout" );
        }
        try {
            presto.checkRetcodeReversa();
        } catch (BaseException e1) {
            if(e1.getMsg().equals("timeout error")){
                try {
                    this.reversar(false);
                } catch (MedioPagoException e) {
                    Tools.logStackTrace(Base.logger, e);
                } 
            }
            throw new MedioPagoException( "Connect error" );
        }
        ArrayList<LineaVoucher> voucher = Voucher.armarVoucher(this.datos, Base.getDefVoucher("comprobantePrestoAnulacion"));

        Voucher.printVoucher(voucher,false, Voucher.COPIA_CLIENTE,null);   
    }
    
    public String toString() {
        // retorna detalle de documento
        String s = "";
        
        s = this.getNombre() + "\nMonto: " + Format.formatMonto(this.getDatos().getLongValue( "Monto" ));
        
        return s;
    }
    
    private void parsearTarjetaPresto( String valor ) throws BaseException {
        String numeroTarjeta = "";
        String fechaVencimiento = "";
        String track2 = "";
        String nombre ="";
        
        if( valor.length() == 0 )
            throw new BaseException( "largo tarjeta invalido" );
        
        String[] tracks = valor.split( "\\?" );
        if( tracks.length < 2 )
            throw new BaseException( "tarjeta invalida - no vienen tracks" );
        
        track2 = tracks[1];
        
        // validamos el track 1
        
        String[] vT1 = tracks[0].split( "\\^" );
        if( vT1.length < 2 )
            throw new BaseException( "tarjeta invalida - numero de campos en track 1" );
        
        if( !vT1[0].startsWith( "%" ) )
            throw new BaseException( "tarjeta invalida - track1 no trae caracter de partida" );
        
        if( vT1[0].length() < 15 )
            throw new BaseException( "tarjeta invalida - largo numero tarjeta < 13" );

        numeroTarjeta = vT1[0].substring( 2 );
        nombre = vT1[1];
        // validamos bines presto
        
        String sBines = Base.getParamSet( "prestoCfg" ).getStringValue( "Bines" );
        String[] bines = sBines.split( "," );
        int tipoBin = 0;
        for( int i = 0; i< bines.length; i++ ) {
            // cada entrada es de la forma nnnn:tipo
            String[] ss = bines[i].split( ":" );
            if( numeroTarjeta.startsWith( ss[0] ) ) {
                tipoBin = new Integer( ss[1] ).intValue();
                break;
            }
        }
        if( tipoBin == 0 )
            throw new BaseException( "tarjeta invalida - no presto" );
        
        if( tipoBin == 1 ) { // bines 200 y 201
            if( numeroTarjeta.length() != 13 )
                throw new BaseException( "tarjeta invalida - largo para bin 200,201" );
            try {
                long l = new Long(numeroTarjeta).longValue();
            } catch (Exception e) {
                throw new BaseException( "tarjeta invalida - no numerica" );
            }
            
            // vemos el track 2
            
            String[] vT2 = tracks[1].split( "=" );
            if( vT2.length < 2 )
                throw new BaseException( "tarjeta invalida - track 2" );
            if( vT2[1].length() < 4 )
                throw new BaseException( "tarjeta invalida - fecha vencimiento" );
            fechaVencimiento = vT2[1].substring( 0, 4 );
            try {
                long l = new Long(fechaVencimiento).longValue();
            } catch (Exception e) {
                throw new BaseException( "tarjeta invalida - fecha vencimiento" );
            }
            
        }
        else if( tipoBin == 2 ) { // bines 202 a 204
            if( numeroTarjeta.length() != 13 )
                throw new BaseException( "tarjeta invalida - largo para bin 200,201" );
            try {
                long l = new Long(numeroTarjeta).longValue();
            } catch (Exception e) {
                throw new BaseException( "tarjeta invalida - no numerica" );
            }
            
            // vemos el track 2
            
            String[] vT2 = tracks[1].split( "=" );
            if( vT2.length < 2 )
                throw new BaseException( "tarjeta invalida - track 2" );
            if( vT2[1].length() < 4 )
                throw new BaseException( "tarjeta invalida - fecha vencimiento" );
            fechaVencimiento = vT2[1].substring( 0, 4 );
        }
        else if( tipoBin == 3 ) { // bines 920061 a 920067                 
            if( numeroTarjeta.length() != 16 )
                throw new BaseException( "tarjeta invalida - largo para bin 200,201" );
            try {
                long l = new Long(numeroTarjeta).longValue();
            } catch (Exception e) {
                throw new BaseException( "tarjeta invalida - no numerica" );
            }
            
            // vemos el track 2
            
            String[] vT2 = tracks[1].split( "=" );
            if( vT2.length < 2 )
                throw new BaseException( "tarjeta invalida - track 2" );
            if( vT2[1].length() < 4 )
                throw new BaseException( "tarjeta invalida - fecha vencimiento" );
            fechaVencimiento = vT2[1].substring( 0, 4 );
        }
        
        // se cargan datos track2, numeroTarjeta y fechaVencimiento al area de datos
        
        datos.setValue( "NumTarjeta", numeroTarjeta );
        datos.setValue( "Track2", track2 );
        datos.setValue( "ExpDate", fechaVencimiento );
        datos.setValue("Titular", nombre);
        
    }
    public boolean isIngresable(ICajaView vista){
        if( vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0 )
            return false;
        if(Base.getEdicion()){
            return false;
        }
        return true;
    }
    private long[]getCuotasSI(long monto){
        double aux = monto/3;
        long c1 = (long)Math.floor(aux);
        long c2 = c1;
        long c3 = monto-c1-c2;
        
        long []cuotas = new long[3];
        cuotas[0] = c1;
        cuotas[1] = c2;
        cuotas[2] = c3;
        return cuotas;
    }
}
