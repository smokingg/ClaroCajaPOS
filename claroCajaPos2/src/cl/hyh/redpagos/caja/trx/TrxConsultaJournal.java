package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.JournalEntryView;
import cl.hyh.redpagos.caja.base.MedioPagoException;
import cl.hyh.redpagos.caja.base.Oper;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.ServicioJournalDetalle;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.trx.TrxCerrarCaja.MyFilter;

public class TrxConsultaJournal implements ITrxBase{
    String origenDir;
    String destinoDir;
    String fecha;
    String hora;
    String agencia;
    String caja;
    String fName;
    int estado;
    Datos data;
    String []titulos;
    ArrayList <Datos> opers;
    ArrayList <JournalEntryView> journal;
    ArrayList <Object> operSaf;
    OperTRV operTRV = null;
    OperAdmin operAdmin = null;
    CodigoAutorizacion claveSup = null;
    
    public class JournalComparator implements Comparator{

        public int compare( Object o1, Object o2){
            JournalEntryView j1 = (JournalEntryView)o1;
            JournalEntryView j2 = (JournalEntryView)o2;
            String f1 = j1.getFecha() + j1.getHora();
            String f2 = j2.getFecha() + j2.getHora();
            if(Long.parseLong(f1) > Long.parseLong(f2)){
                return -1;
            }
            else if(Long.parseLong(f1) < Long.parseLong(f2)){
                return 1;
            }
            else 
                return 0;
        }
    }
    
    public void init(Datos datosVista){        
        titulos = new String[9];
        titulos[0] = "Caja";
        titulos[1] = "Fecha";
        titulos[2] = "Hora";
        titulos[3] = "N°Operacion";
        titulos[4] = "Operacion";
        titulos[5] = "Estado";
        titulos[6] = "N° Item";
        titulos[7] = "Item";
        titulos[8] = "Monto";
    }
    public int execute(ICajaView vista, int key, Datos htParam) {

        boolean[] editable = null;

    	if( key == 0 ) {    
            
            estado = 0;
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }
        else if( key == 2 ) {
            estado = 20;
        }
        else if( key == KeyEvent.VK_ENTER ) {
        } else if( key == KeyEvent.VK_F8){
            estado = 5;
        }else if( key == KeyEvent.VK_F9 && estado > 1){
            String []botones = new String[12];
            botones[0] = "";
            botones[1] = "";
            botones[2] = "Buscar";
            botones[3] = "";
            botones[4] = "";
            botones[5] = "";
            botones[6] = "";
            botones[7] = "";
            botones[8] = "Volver";
            botones[9] = "";
            botones[10] = "";
            botones[11] = ""; 
            vista.paintButtons( botones );
            estado = 0;
        }
        else if( key == KeyEvent.VK_F9 && estado == 1 ){
            return 13;
        }
        else if( key == KeyEvent.VK_F3 ){
            estado = 6;
        }
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                DefServicio def = Base.getDefServicio("ConsultaJournal");
                data = new Datos(def.getInputRecordDef());
                Servicio consultaJournal = null; 
                int resp = 0;
                try {
                    consultaJournal = FactoryServicio.makeInstance("ConsultaJournal");
                    consultaJournal.setRequest(this.data);
                    vista.showBusyWindow("Consultando", "Espere por favor...");
                    resp = consultaJournal.execute();
                    vista.hideBusyWindow();
                } catch (BaseException e) {
                    vista.hideBusyWindow();
                    Tools.logStackTrace(Base.logger, e);
                    JOptionPane.showMessageDialog(null, e.getMsg(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
                    Base.logger.info("El execute del servicio retorno error o timeout");                    
                }
                opers = consultaJournal.getResponse().getArrayList("Operacion");
                
                //Cargamos todos los opers que existan en el saf
                
                String [] archivosCtl = procesaDir();
                if(archivosCtl != null){                
                    String [] archivosDat = new String[archivosCtl.length];
                    
                    operSaf = new ArrayList<Object>();
                    ParamSet posDat = Base.getParamSet("posDat");
                    for( int i = 0; i < archivosCtl.length; i++ ) {
                        String okFName = archivosCtl[i];
                        int l = okFName.length();
                        String datName = okFName.substring( 0, l - 4 ) + ".dat";
                        archivosDat[i] = datName;
                        Oper o = (Oper)loadFiles(datName);
                        if(o.getAgencia().equals(posDat.getStringValue("Agencia")) 
                           && o.getEntidad().equals(posDat.getStringValue("Entidad"))
                           && o.getUsuario().equals(posDat.getStringValue("Usuario")))
                            operSaf.add(o);
                    }
                }
                
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta Journal", true );                
                journal = new ArrayList<JournalEntryView>();
                
                int sizeSaf = 0;
                int sizeOpers = 0;
                if(operSaf == null){
                    sizeSaf = 0;
                }
                else{
                    sizeSaf = operSaf.size();
                }
                if(opers == null){
                    sizeOpers = 0;
                }
                else{
                    sizeOpers = opers.size();
                }
                if(sizeSaf == 0 && sizeOpers == 0){
                    return 13;
                }
                int i = 0;
                for(i = 0; i < sizeOpers; i++){
                    JournalEntryView fila = new JournalEntryView();
                    fila.setCaja( opers.get(i).getStringValue("Caja") );
                    fila.setFecha( opers.get(i).getStringValue("Fecha").trim() );
                    fila.setHora( opers.get(i).getStringValue("Hora").trim() );
                    fila.setNumeroOperacion( opers.get(i).getLongValue("NumeroOperacion") );
                    fila.setOperacion( opers.get(i).getStringValue("Operacion") );
                    fila.setNumeroItem( opers.get(i).getLongValue("NumeroItem") );
                    fila.setItem( opers.get(i).getStringValue("Item") );
                    fila.setMonto( opers.get(i).getLongValue("Monto") );
                    fila.setEstado( "IF" );
                    journal.add( fila );
                }
                for(int j = 0; j < sizeSaf; j++){
                    
                    if(operSaf.get(j) instanceof OperTRV){
                        OperTRV operTemp = (OperTRV)operSaf.get(j);
                        for(int k = 0; k < operTemp.getCarroCompras().getDocumentos().size(); k++){
                            JournalEntryView fila = new JournalEntryView();
                            fila.setCaja( ((Oper)operSaf.get(j)).getCaja() );
                            fila.setFecha( ((Oper)operSaf.get(j)).getFecha() );
                            fila.setHora( ((Oper)operSaf.get(j)).getHora() );
                            fila.setNumeroOperacion( ((Oper)operSaf.get(j)).getNumeroOperacion());
                            fila.setOperacion( ((Oper)operSaf.get(j)).getOperacion() );
                            fila.setNumeroItem(k + 1);
                            fila.setItem(operTemp.getCarroCompras().getDocument(k).getNombre());
                            fila.setMonto(operTemp.getCarroCompras().getDocument(k).getMonto());
                            fila.setEstado( "SAF" );
                            i++;
                            journal.add( fila );
                        }
                    }
                    else{
                        JournalEntryView fila = new JournalEntryView();
                        OperAdmin operTemp = (OperAdmin)operSaf.get(j);
                        fila.setNumeroItem(1);
                        fila.setItem(operTemp.getOperacion());
                        fila.setMonto(operTemp.getDatos().getLongValue("Monto"));
                        fila.setCaja( ((Oper)operSaf.get(j)).getCaja() );
                        fila.setFecha( ((Oper)operSaf.get(j)).getFecha() );
                        fila.setHora( ((Oper)operSaf.get(j)).getHora() );
                        fila.setNumeroOperacion( ((Oper)operSaf.get(j)).getNumeroOperacion());
                        fila.setOperacion( ((Oper)operSaf.get(j)).getOperacion() );
                        fila.setEstado( "SAF" );
                        i++;
                        journal.add( fila );
                    }                    
                }
                for(int j = 0; j < sizeSaf; j++){
                    if(operSaf.get(j) instanceof OperAdmin){
                        OperAdmin aux = (OperAdmin)operSaf.get(j);
                        if(aux.getOperacion().equals("REVERSA")){
                            for(int l = 0; l < operSaf.size(); l++){
                                if(Long.toString(((Oper)operSaf.get(l)).getNumeroOperacion()).equals(aux.getDatos().getStringValue("NumeroOperacion"))){
                                    ((Oper)operSaf.get(l)).setReversado(true);
                                }
                            }
                        }
                    }
                }
                String [][]matrix = new String[journal.size()][9];                
                Collections.sort( journal ,  new JournalComparator());
                
                for(int j = 0; j < matrix.length; j++){
                    matrix[j][0] = journal.get(j).getCaja();
                    matrix[j][1] = Format.formatFechaServicio(journal.get(j).getFecha());
                    matrix[j][2] = Format.formatHoraPresto(journal.get(j).getHora());
                    matrix[j][3] = Long.toString(journal.get(j).getNumeroOperacion());
                    matrix[j][4] = journal.get(j).getOperacion();
                    matrix[j][5] = journal.get(j).getEstado();
                    matrix[j][6] = Long.toString(journal.get(j).getNumeroItem());
                    //matrix[j][7] = journal.get(j).getItem();
                    matrix[j][7] = "Empresa " + (j+1);
                    matrix[j][8] = Format.formatMonto(journal.get(j).getMonto());
                }
                editable = new boolean[ titulos.length ];
                for( int k = 0; k < editable.length; ++k ) {
                	editable[k] = false;
                }
                vista.showTableView(titulos, matrix, editable);
                //vista.setEntryTable(titulos, matrix, true);
                return ICajaView._WAITFORACTION;
            case 1:
                //int index = vista.getEntryTableIndex();
                int index = vista.getTableViewIndex();
                vista.dismissTableView();
                if( index < 0 ) {
                    estado = 0;
                    return ICajaView._NOWAITFORACTION;
                }
                Oper actual = null;
                
                vista.hideAllEntries();
                
                JournalEntryView consulta = journal.get(index);
                
                if(consulta.getEstado().equals("IF")){
                    consultaJournal = null;
                    def = Base.getDefServicio("ConsultaJournalOperacion");
                    data = new Datos(def.getInputRecordDef());
                    resp = 0;
                    data.setValue("NumeroOperacion", consulta.getNumeroOperacion());
                    data.setValue( "Caja", consulta.getCaja() );
                    try {
                        consultaJournal = FactoryServicio.makeInstance("ConsultaJournalOperacion");
                        consultaJournal.setRequest(this.data);
                        vista.showBusyWindow("Consultando", "Espere por favor...");
                        resp = consultaJournal.execute();
                        vista.hideBusyWindow();
                    } catch (BaseException e) {
                        vista.hideBusyWindow();
                        Tools.logStackTrace(Base.logger, e);
                        return 13;
                    }
                    if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
                        Base.logger.info("El execute del servicio retorno error o timeout");
                        JOptionPane.showMessageDialog(null, "Error en la ejecucion del Servicio de Consulta"
                                ,"Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                    
                    try {
                        consultaJournal.checkRetcode();
                    } catch (BaseException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMsg(),"Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                    
                    if(consulta.getOperacion().startsWith("EnvioTrv")){
                        operTRV = (OperTRV)((ServicioJournalDetalle)consultaJournal).getOper();
                        operTRV.setFecha( consulta.getFecha() );
                        operTRV.setHora( consulta.getHora() );
                        operTRV.setCaja( consulta.getCaja());
                        operTRV.setNumeroOperacion( consulta.getNumeroOperacion() );
                        operTRV.setOperacion( consulta.getOperacion() );
                        estado = 2;
                    }
                    else{
                        operAdmin = (OperAdmin)((ServicioJournalDetalle)consultaJournal).getOper();
                        operAdmin.setFecha( consulta.getFecha() );
                        operAdmin.setHora( consulta.getHora() );
                        operAdmin.setCaja( consulta.getCaja());
                        operAdmin.setNumeroOperacion( consulta.getNumeroOperacion() );
                        operAdmin.setOperacion( consulta.getOperacion() );
                        estado = 3;
                    }
                }
                else if(consulta.getEstado().equals("SAF")){
                    for(int j = 0 ; j < operSaf.size(); j++){
                        if(((Oper)operSaf.get(j)).getNumeroOperacion() == consulta.getNumeroOperacion()){
                            actual = (Oper)operSaf.get(j);
                            break;
                        }
                    }
                    if(actual.getClass() == OperTRV.class){
                        operTRV = (OperTRV)actual;
                        operTRV.setReversado(true);
                        estado = 2;
                    }
                    else if(actual instanceof OperAdmin){
                        operAdmin = (OperAdmin)actual;
                        operAdmin.setReversado(true);
                        estado = 3;
                    }
                }
                return ICajaView._NOWAITFORACTION;
            case 2:
                vista.hideAllEntries();
                vista.setEntryTextLabel("Detalle:", true);
                vista.setEntryTextArea(operTRV.imprimirJournalPantalla(), true);
                if(operTRV.isReversable() && !operTRV.isReversado()){
                    String []botones = new String[12];
                    botones[0] = "";
                    botones[1] = "";
                    botones[2] = "";
                    botones[3] = "";
                    botones[4] = "";
                    botones[5] = "";
                    botones[6] = "";
                    botones[7] = "Reversar";
                    botones[8] = "Volver";
                    botones[9] = "";
                    botones[10] = "";
                    botones[11] = "";
                    vista.paintButtons( botones );
                }
                vista.hideEnter();
                return ICajaView._WAITFORACTION;
            case 3:
                vista.hideAllEntries();
                vista.setEntryTextLabel("Detalle:", true);
                vista.setEntryTextArea(operAdmin.imprimirJournalPantalla(), true);
                if(operAdmin.isReversable() && !operAdmin.isReversado()){
                    String []botones = new String[12];
                    botones[0] = "";
                    botones[1] = "";
                    botones[2] = "";
                    botones[3] = "";
                    botones[4] = "";
                    botones[5] = "";
                    botones[6] = "";
                    botones[7] = "Reversar";
                    botones[8] = "Volver";
                    botones[9] = "";
                    botones[10] = "";
                    botones[11] = "";                    
                    vista.paintButtons( botones );
                }
                vista.hideEnter();
                return ICajaView._WAITFORACTION;                
            case 5:
                if( vista.showMyConfirmDialog("Confirme Reversa", "Desea reversar?\nAL CONFIRMAR SE REVERSARA LA OPERACION SELECCIONADA\nY VOLVERA AL CARRO DE COMPRAS ORIGINAL") == JOptionPane.YES_OPTION ) {
                    estado = 100;
                    claveSup = new CodigoAutorizacion();
                    return claveSup.execute(vista, 0, this.data);
                }
                return 13;
            case 100:
                int respSuper = claveSup.execute(vista, key, this.data);
                if(respSuper == 15){
                    JOptionPane.showMessageDialog(null, "Validacion invalida de supervisor.","Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }
                if(respSuper != 11)
                    return respSuper;
                if(operTRV != null){
                    if(operTRV.isAnulable()){
                        int rc = operTRV.anular();
                        if(rc == -1){
                            JOptionPane.showMessageDialog(null, "No se pudieron anular los medios de pago.","Error", JOptionPane.INFORMATION_MESSAGE);
                            return 13;
                        }
                        else if(rc == 0){
                            estado = 20;
                            return ICajaView._NOWAITFORACTION;
                        }
                    }
                    else{
                        Base.mPago = operTRV.getCarroMediosPago().getPago(0);
                        return 100;
                    }                       
                }
                else if(operAdmin != null){
                    operAdmin.anular();
                    int rc = 0;
                    if(operAdmin.getOperacion().contains("EnvioRemesa")){
                        Servicio reversaRemesa;
                        try {
                            reversaRemesa = FactoryServicio.makeInstance("ReversaRemesa");
                            Datos temp = new Datos();
                            temp.setValue("NumeroOperacion", operAdmin.getNumeroOperacion());
                            temp.setValue("Caja", operAdmin.getCaja());
                            reversaRemesa.setRequest(temp);
                            vista.showBusyWindow("Consultando", "Espere por favor....");
                            rc = reversaRemesa.execute();
                            vista.hideBusyWindow();        
                        } catch (BaseException e) {
                            vista.hideBusyWindow();
                            JOptionPane.showMessageDialog(null, "No se pudo anular la remesa","Error", JOptionPane.INFORMATION_MESSAGE);
                            return 13;
                        }
                        if(rc == Servicio.RC_CONNECT_ERROR || rc == Servicio.RC_TIMEOUT){
                            JOptionPane.showMessageDialog(null, "No se pudo anular la remesa","Error", JOptionPane.INFORMATION_MESSAGE);
                            return 13;
                        }
                        
                        try {
                            reversaRemesa.checkRetcode();
                        } catch (BaseException e1) {
                            JOptionPane.showMessageDialog(null, "No se pudo anular la remesa","Error", JOptionPane.INFORMATION_MESSAGE);
                            return 13;
                        }
                        JOptionPane.showMessageDialog(null, "Reversa realizada","Info", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                    OperReversa reversa = new OperReversa();
                    Datos param = new Datos();
                    param.setValue("NumeroOperacion", operAdmin.getNumeroOperacion());
                    param.setValue("Caja", operAdmin.getCaja());
                    vista.showBusyWindow("Reversando", "Espere por favor...");
                    reversa.execute( param );
                    vista.hideBusyWindow();
                    return 13;
                }
                return 13;
            case 6:
                vista.dismissTableView();
                estado = 7;
                vista.setEntryMessage( "Ingrese busqueda", true );
                vista.setEntryTextLabel("Ingrese busqueda:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
            case 7:
                if(vista.getEntryText().equals("")){
                    estado = 6;
                    return ICajaView._NOWAITFORACTION;
                }
                String busqueda = vista.getEntryText();
                busqueda = busqueda.toUpperCase();
                int pos = 0;
                for(int j = 0 ; j < journal.size(); j++ ){
                    JournalEntryView aux = journal.get(j);
                    if(aux.getEstado().toUpperCase().contains(busqueda) || aux.getFecha().toUpperCase().contains(busqueda)
                       || aux.getHora().toUpperCase().contains(busqueda) || aux.getItem().toUpperCase().contains(busqueda)
                       || (Long.toString(aux.getMonto())).contains(busqueda)
                       || (Long.toString(aux.getNumeroOperacion())).contains(busqueda)){
                        pos = j;
                        break;
                    }
                }
                vista.hideAllEntries();
                estado = 1; 
                matrix = new String[journal.size()][9];                
                Collections.sort( journal ,  new JournalComparator());
                
                for(int j = 0; j < matrix.length; j++){
                    matrix[j][0] = journal.get(j).getCaja();
                    matrix[j][1] = journal.get(j).getFecha();
                    matrix[j][2] = journal.get(j).getHora();
                    matrix[j][3] = Long.toString(journal.get(j).getNumeroOperacion());
                    matrix[j][4] = journal.get(j).getOperacion();
                    matrix[j][5] = journal.get(j).getEstado();
                    matrix[j][6] = Long.toString(journal.get(j).getNumeroItem());
                    matrix[j][7] = journal.get(j).getItem();
                    matrix[j][8] = Format.formatMonto(journal.get(j).getMonto());
                }                
                editable = new boolean[ titulos.length ];
                for( int k = 0; k < editable.length; ++k ) {
                	editable[k] = false;
                }
                vista.showTableView(titulos, matrix, editable);
                vista.setTableViewIndex(pos);
                
                return ICajaView._WAITFORACTION;
            case 20:
                OperReversa reversa = null;
                Datos param = new Datos();
                if(Base.mPago != null){
                    if(Base.mPago.getDatos().getStringValue("rc").equals("1")){
                        Base.logger.info("No se pudo anular el medio de pago");
                        JOptionPane.showMessageDialog(null, "No se pudieron anular los medios de pago.","Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                    }
                    reversa = new OperReversaTarjeta();
                    param.setValue("CodigoAutorizacion", Base.mPago.getDatos().getStringValue("CodigoAutorizacion"));
                    param.setValue("FechaOriginal", Base.mPago.getDatos().getStringValue("FechaAutorizacionOriginal"));
                    param.setValue("HoraOriginal", Base.mPago.getDatos().getStringValue("HoraAutorizacionOriginal"));
                    param.setValue("NumeroUnico", Base.mPago.getDatos().getStringValue("NumeroUnico"));
                }
                else{
                    reversa = new OperReversa();
                }
                Base.mPago = null;
                if(htParam.getIntValue("rc") != 0){
                    JOptionPane.showMessageDialog(null, "No se pudieron anular los medios de pago.","Error", JOptionPane.INFORMATION_MESSAGE);
                    Base.logger.info("No se pudo anular el medio de pago");
                    return 13;
                }                
                param.setValue("NumeroOperacion", operTRV.getNumeroOperacion());
                param.setValue("Caja", operTRV.getCaja());
                reversa.execute( param );
                vista.createTRV();
                for(int j = 0; j < operTRV.getCarroCompras().getDocumentos().size(); j++){
                    try {
                        vista.getOperTRV().addDocumentoPago(operTRV.getCarroCompras().getDocument(j));
                    } catch (BaseException e) {
                        Tools.logStackTrace(Base.logger, e);
                    }
                }
                return 14;
        }
        return 0;
    }
    
    private String[] procesaDir() {
        
        ParamSet pSet = Base.getParamSet( "posCfg" );
        origenDir = pSet.getStringValue( "OperSafDir" );  
        
        File fDir = new File( origenDir );
        String[] lista = fDir.list( new MyFilter() );
        
        if( lista.length == 0 )
            return null;
        
        ArrayList<String> auxL = new ArrayList<String>();
        
        // ordenamos lista de archivos por nombre ascendentemente
        
        for( int i = 0; i < lista.length; i++ ) {
            auxL.add( lista[i] );
        }
        Collections.sort( auxL );
        for( int i = 0; i < lista.length; i++ ) {
            lista[i] = auxL.get( i );
        }       
        
        return lista;
    }
    private Object loadFiles(String fName){
        try{
            FileInputStream fileIn = new FileInputStream( origenDir + fName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object o = in.readObject();
            in.close();
            fileIn.close();
            return o;
        }catch(Exception e){
            Tools.logStackTrace(Base.logger, e);
            return null;
        }        
    }
}
