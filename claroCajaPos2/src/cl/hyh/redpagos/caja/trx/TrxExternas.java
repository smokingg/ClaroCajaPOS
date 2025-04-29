package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;


import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class TrxExternas implements ITrxBase{
    int estado = 0;
    int estadoA = 0;
    Datos data;
    String []docs = null;
    DocumentoPago doc = null;
    ArrayList <Datos> clientes;
    String []documentos = null;
    boolean unico = false;
    int indice = 0;
    protected Datos dataPagina;
    protected int nextPage = 0;
    protected ArrayList <Integer> paginas = new ArrayList<Integer>();
    private String msg = "";
    private String servicio = "";
    private String documento = "";
    private String glosa ="";
    private boolean isRut = true;
    private boolean isDoc = true;
    String tipoDoco = "";
    private String []filtros = null;
    private String tipoDoc = "";
    Servicio consultaExternas = null;
    String numDoc="";
    String tiposDocs = "";
    int index;
    boolean offline = false;
    
    public void init(Datos datosVista){
        msg = datosVista.getStringValue("btnParam");
        String []aux = msg.split(",");
        servicio = aux[0];
        documento = aux[1];
        indice = 0;
        isRut = Boolean.parseBoolean(aux[2]);
        isDoc = Boolean.parseBoolean(aux[3]);
        tipoDoc = aux[4];
        if(documento.equals("DocumentoTerra")){
            tiposDocs = "Terra";
            glosa = "Empresa 2";
            estadoA = 2;
        }
        else if(documento.equals("DocumentoGlobus")){
            tiposDocs = "Globus";
            glosa = "120 Globus LD";
        }
        else if(documento.equals("DocumentoMundo")){
            tiposDocs = "Mundo";
            glosa = "188 Telefonica LD";
        }
        else if(documento.equals("DocumentoProsegur")){
            tiposDocs = "Prosegur";
            glosa = "Empresa 3";
        }
        else if(documento.equals("DocumentoMovistarDist")){
            tiposDocs = "MovistarDist";
            glosa = "181 Movistar LD";
        }
        else if(documento.equals("DocumentoGuias")){
            tiposDocs = "Guia";
            glosa = "Guia";
        }
        else if(documento.equals("DocumentoRetail")){
            tiposDocs = "Retail";
            glosa = "Empresa 4";
        }
        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 ) {
            estado = estadoA;
            vista.acceptEscape(true);
        } else if( key == 1 ) {
            return 13;
        }
        else if( key == KeyEvent.VK_F8 ) {
            estado = 6;
            nextPage = dataPagina.getIntValue("InicioPaginacion");
            paginas.add(new Integer(nextPage).intValue());
        }
        else if( key == KeyEvent.VK_F7 ) {
            estado = 6;
            try{
                nextPage = paginas.get(paginas.size()-2);
                paginas.remove(paginas.size()-1);
            }
            catch(Exception e){
                Tools.logStackTrace(Base.logger, e);
            }
        } 
        else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_ESCAPE ) {
            return 20;
        } 
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            case 0:
                estado = 1;
                DefServicio def = Base.getDefServicio(this.servicio);
                data = new Datos(def.getInputRecordDef());   
                docs = Base.getDocs(tiposDocs);
                vista.hideAllEntries();
                vista.setTrxTitle("Consulta " + glosa);
                vista.setEntryTitle( "Consulta ", true );
                vista.setEntryMessage( "Ingrese Filtro", true );
                vista.setEntryTextLabel("Ingrese Filtro:", true);
                if(isRut && isDoc){
                    filtros = new String[2];
                    filtros[1] = "Rut";
                    filtros[0] = "Numero Documento";
                }
                else if(isRut && !isDoc){
                    filtros = new String[1];
                    filtros[0] = "Rut";
                }
                else if(!isRut && isDoc){
                    filtros = new String [1];
                    filtros[0] = "Numero Documento";
                }
                vista.setEntryList(filtros, true, true);
                return ICajaView._WAITFORACTION;
            case 1:
                index = vista.getEntryListIndex();
                if(filtros[index].equals("Rut")){
                    estado = 2;
                    indice = 0;
                    return ICajaView._NOWAITFORACTION;
                }
                else if(filtros[index].equals("Numero Documento")){
                    estado = 4;
                    indice = 1;
                    return ICajaView._NOWAITFORACTION;
                }
            case 2:
                estado = 3;
                vista.setEntryTitle( "Consulta", true );
                vista.setEntryMessage( "Ingrese Rut", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
            case 3:
                estado = 6;
                if(Tools.validarRut(vista.getEntryText())){
                    this.data.setValue("Rut", vista.getEntryText()); 
                }   
                else{                 
                    estado = 3;
                    vista.setEntryTitle( "Consulta", true );
                    vista.setEntryMessage( "RUT  Invalido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                return ICajaView._NOWAITFORACTION;                                       
                
            case 4:
                estado = 104;
                vista.setEntryTitle( "Consulta", true );
                vista.setEntryTextLabel("Tipos de Documentos: ", true);
                if(Base.getList(docs, ",", 1).length > 1){
                    vista.setEntryList(Base.getList(docs,",",1), true, false);
                    return ICajaView._WAITFORACTION;
                }
                else{
                    estado = 103;
                }
            case 201:
                estado = 5; 
                tipoDoco = "0";
                vista.setEntryTitle( "Consulta", true );
                vista.setEntryMessage( "Ingrese N°Cuenta/Contrato" , true);
                vista.setEntryTextLabel( "Ingrese N°Cuenta/Contrato" , true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Valor ingresado no válido" );
            case 103:
                estado = 5; 
                this.data.setValue("TipoDocumento", Base.getList(docs, ",", 0)[0]);
                tipoDoco = this.data.getStringValue("TipoDocumento");
                vista.setEntryTitle( "Consulta", true );
                vista.setEntryMessage( "Ingrese N°" + Base.getList(docs,",",1)[0] , true);
                vista.setEntryTextLabel( "Ingrese N°" + Base.getList(docs,",",1)[0] , true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Valor ingresado no válido" );
                return ICajaView._WAITFORACTION;
            case 104:
                estado = 5;
                int ind = vista.getEntryListIndex();                
                this.data.setValue("TipoDocumento", Base.getList(docs, ",", 0)[ind]);
                tipoDoco = this.data.getStringValue("TipoDocumento");
                vista.setEntryTitle( "Consulta", true );
                vista.setEntryMessage( "Ingrese N°" + Base.getList(docs,",",1)[ind] , true);
                vista.setEntryTextLabel( "Ingrese N°" + Base.getList(docs,",",1)[ind] , true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Valor ingresado no válido" );
                return ICajaView._WAITFORACTION;
            case 5:
                estado = 6;
                if(vista.getEntryText().length() > 30){
                    String num = vista.getEntryText().substring(0,30);
                    this.data.setValue("NumeroDocumento", num); 
                    numDoc = num;
                }
                else{
                    this.data.setValue("NumeroDocumento", vista.getEntryText()); 
                    numDoc = vista.getEntryText();
                }
                return ICajaView._NOWAITFORACTION; 
            case 6:
                estado = 7;
                consultaExternas = null; 
                int resp = 0;
                
                if(indice == 0){
                    this.data.setValue("InicioPaginacion", nextPage);
                }
                else{
                    this.data.setValue("InicioPaginacion", 0);
                    
                }
                
                try {
                    consultaExternas = FactoryServicio.makeInstance(servicio);
                    consultaExternas.setRequest(this.data);
                    vista.showBusyWindow("Consultando", "Espere por favor....");
                    resp = consultaExternas.execute();
                    vista.hideBusyWindow();        
                } catch (BaseException e) {
                    vista.hideBusyWindow();
                    Tools.logStackTrace(Base.logger, e);
                    JOptionPane.showMessageDialog(null, e.getMsg(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    return 20;
                }
                if(resp == Servicio.RC_CONNECT_ERROR || resp == Servicio.RC_TIMEOUT){
                    Base.logger.info("El execute del servicio retorno error o timeout");
                    estado = 11;                    
                    data = null;
                    DefDocumentoPago defDoc = Base.getDefDocumentoPago(documento);
                    data = new Datos(defDoc.getRecordDef());
                    data.setValue("TipoDocumento", tipoDoco);
                    if(indice == 0){
                        JOptionPane.showMessageDialog(null, "Error de conexion/timeout"
                                ,"Error", JOptionPane.INFORMATION_MESSAGE);
                        return 20;
                    }
                    else{
                        if(tiposDocs.equals("Retail")){
                            JOptionPane.showMessageDialog(null, "N° ingresado no tiene saldo pendiente"
                                    ,"Error", JOptionPane.INFORMATION_MESSAGE);
                            return 20;
                        }
                        JOptionPane.showMessageDialog(null, "Error de conexion/timeout\nDocumento no existe\nIngrese datos manualmente"
                                ,"Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    return ICajaView._NOWAITFORACTION;
                }
                
                try {
                    consultaExternas.checkRetcode();
                } catch (BaseException e1) {
                    estado = 11;
                    data = null;
                    DefDocumentoPago defDoc = Base.getDefDocumentoPago(documento);
                    data = new Datos(defDoc.getRecordDef());
                    if(indice == 0){
                        JOptionPane.showMessageDialog(null, "Rut ingresado no tiene saldo pendiente"
                                ,"Error", JOptionPane.INFORMATION_MESSAGE);
                        return 20;
                    }
                    else{
                        if(tiposDocs.equals("Retail")){
                            JOptionPane.showMessageDialog(null, "N° ingresado no tiene saldo pendiente"
                                    ,"Error", JOptionPane.INFORMATION_MESSAGE);
                            return 20;
                        }
                        JOptionPane.showMessageDialog(null, e1.getMsg() + "\nIngrese datos manualmente"
                                ,"Error", JOptionPane.INFORMATION_MESSAGE);
                    }                    
                    return ICajaView._NOWAITFORACTION;
                }
                
                if(indice == 0){
                    dataPagina = consultaExternas.getResponse().getDatos("Paginacion");
                    
                    if(dataPagina.getStringValue("IndicaPaginacion").equals("S")){
                        if(nextPage == 0 || paginas.size() == 1){
                            vista.paintButtons(Tools.getBotones(0));
                            paginas.add(new Integer(0).intValue());
                        }
                        else{
                            vista.paintButtons(Tools.getBotones(1));
                        }
                        
                    }
                }
                estado = 15;
                return ICajaView._NOWAITFORACTION;
            case 15:
                estado = 7;
                ArrayList <Datos> aux  = consultaExternas.getResponse().getArrayList(documento);
                int cant = Tools.getRows(aux, "NumeroDocumento", "");
                clientes = new ArrayList<Datos>();
                for(int i = 0 ; i < cant ; i++ ){
                    clientes.add(aux.get(i));
                }
                documentos = new String[this.clientes.size() + 1];
                if(clientes.size() > 1 ){                    
                    vista.hideAllEntries();
                    vista.setEntryTitle( "Consulta ", true );
                    vista.setEntryTextLabel("Documentos: ", true);
                    documentos[0] = String.format("%-20s %3s %10s %15s", "Nombre Doc", "Tipo","N°Doc","Monto");
                    for(int i = 0; i < clientes.size(); i++){
                        documentos[i + 1] = String.format("%-20s %3s %10s  $%15s", Base.getCampoList(docs, ",", clientes.get(i).getStringValue("TipoDocumento")), clientes.get(i).getStringValue("TipoDocumento"),clientes.get(i).getStringValue("NumeroDocumento"),Format.formatMontoPantalla(clientes.get(i).getLongValue("MontoSaldo")));
                    }
                    
                    vista.setEntryList(documentos, true, false, indice + 1 );
                    return ICajaView._WAITFORACTION;
                }
                else{
                    unico = true;
                    return ICajaView._NOWAITFORACTION;
                }
            case 7:                
                if(!unico){
                    indice = vista.getEntryListIndex();
                    if(indice == 0){
                        estado = 15;
                        return ICajaView._NOWAITFORACTION;
                    }
                    else{
                        indice = indice -1;
                    }
                }
                else
                    indice = 0;
                vista.paintButtons(Tools.getBotones(-1));
                try {
                    doc = FactoryDocumentoPago.makeInstance(documento);
                } catch (BaseException e) {
                    Base.logger.error("No se pudo instanciar el documento");
                }                    
                doc.getDatos().asignaPorNombre(clientes.get(indice));
                doc.getDatos().setValue("FechaEmision", doc.getDatos().getStringValue("FechaEmision"));
                doc.getDatos().setValue("FechaVencimiento", doc.getDatos().getStringValue("FechaVencimiento"));

                doc.getDatos().setValue("Monto", doc.getDatos().getLongValue("MontoSaldo"));
                estado = 8;
                return ICajaView._NOWAITFORACTION;                 
            case 8:
                estado = 9;
                if(!tiposDocs.equals("Terra") && !tiposDocs.equals("Prosegur") && !tiposDocs.equals("Guia") && !tiposDocs.equals("Retail")){
                    vista.setEntryMessage( "Ingrese Monto", true );
                    vista.setEntryTextLabel("Ingrese Monto:", true);
                    vista.setEntryText(String.valueOf(doc.getMonto()), true, false, false,"[0-9]+", "Monto no válido");
                    return ICajaView._WAITFORACTION;
                }
                else{
                    vista.setEntryText(Long.toString(doc.getDatos().getLongValue("MontoSaldo")), false, true, false, null, null);
                    return ICajaView._NOWAITFORACTION;
                }
            case 9:
                if(Base.getEdicion()){
                    if(vista.getOperTRV().getCarroCompras().getMontoTotal() + Long.parseLong(vista.getEntryText()) > vista.getOperTRV().getCarroMediosPago().getMontoTotal()){
                        estado = 8;
                        JOptionPane.showMessageDialog(null, "Monto no debe exeder el saldo restante", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                        return ICajaView._NOWAITFORACTION;
                    }
                }
                if(Long.parseLong(vista.getEntryText()) == 0){
                    estado = 8;
                    return ICajaView._NOWAITFORACTION;
                }
                doc.getDatos().setValue("Monto",  Long.parseLong(vista.getEntryText()));
                vista.hideAllEntries();
                vista.setEntryTitle( "Consulta", true );
                vista.setEntryTextArea("Documento: " + glosa 
                        +"\nN°Documento: " + doc.getDatos().getStringValue("NumeroDocumento")
                        +"\nRut: " + doc.getDatos().getStringValue("Rut")
                        +"\nFecha Emision: " + doc.getDatos().getStringValue("FechaEmision")
                        +"\nFecha Vencimiento: " + doc.getDatos().getStringValue("FechaVencimiento")
                        +"\nMonto Facturado: " + Format.formatMonto(doc.getDatos().getLongValue("MontoFacturado"))
                        +"\nMonto Saldo: " + Format.formatMonto(doc.getDatos().getLongValue("MontoSaldo"))
                        , true);
                estado = 10;
                return ICajaView._WAITFORACTION;
            case 10:
                if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
                    Base.logger.info("Documento ya existe en carro de compras");
                    JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    estado = 6;
                    return ICajaView._NOWAITFORACTION;
                }
                try {
                    vista.getOperTRV().addDocumentoPago(doc);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar documento de pago al carro");
                }
                doc.getDatos().show(documento);
                if(unico){
                    return 20;
                }
                if(filtros[index].equals( "Rut" ) && offline == false){
                    estado = 15;
                    return ICajaView._NOWAITFORACTION;
                }                
                else{
                    return 20;
                }
                
            case 11:
                estado = 12;
                vista.setEntryTitle( "Ingreso Manual", true );
                vista.setEntryMessage( "Ingrese Rut", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
            case 12:
                if(Tools.validarRut(vista.getEntryText())){
                    this.data.setValue("Rut", vista.getEntryText()); 
                }   
                else{                 
                    estado = 12;   
                    vista.setEntryTitle( "Ingreso Manual", true );
                    vista.setEntryMessage( "RUT  Invalido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                estado = 14;
                ind = vista.getEntryListIndex();                
                this.data.setValue("TipoDocumento", tipoDoco);
                this.data.setValue("NumeroDocumento", numDoc); 
                vista.hideAllEntries();                
                vista.setEntryTitle( "Ingreso Manual", true );
                vista.setEntryMessage( "Ingrese Monto", true );
                vista.setEntryTextLabel("Ingrese Monto:", true);
                vista.setEntryText("", true, false, false,"[0-9]+", "Monto no válido");
                return ICajaView._WAITFORACTION;
            case 14:
                
                this.data.setValue("Monto", Long.parseLong(vista.getEntryText()));              
                doc = null;
          
                try {
                    doc = FactoryDocumentoPago.makeInstance(documento);
                } catch (BaseException e) {
                    Base.logger.error("No se pudo instanciar el documento");
                    return 20;
                }                    
                doc.getDatos().asignaPorNombre(this.data);
                vista.hideAllEntries();                
                vista.setEntryTitle( "Ingreso Manual", true );
                vista.setEntryTextArea("Documento: " + doc.getNombre() +"\nRut: " + doc.getDatos().getStringValue("Rut") 
                        +"\nN°Documento: " + doc.getDatos().getStringValue("NumeroDocumento")
                        +"\nMonto Documento: " + Format.formatMonto(doc.getDatos().getLongValue("Monto"))
                        , true);
                estado = 10;
                offline = true;
                return ICajaView._WAITFORACTION;
        }
        return 0;
    }
}
