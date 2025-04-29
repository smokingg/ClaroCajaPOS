package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.Oper;
import cl.hyh.redpagos.caja.base.OperAdmin;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.ServicioJournalDetalle;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.trx.TrxCerrarCaja.MyFilter;
import cl.hyh.redpagos.caja.trx.TrxConsultaJournal.JournalComparator;

public class TrxConsultaTotalesAgencia implements ITrxBase{
    int estado;
    Datos data;
    ArrayList <Datos> totales;
    ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
    
    public void init(Datos data){
        
    }
    
    public int execute(ICajaView vista, int key, Datos htParam) {
        if( key == 0 ) {            
            estado = 0;
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } 
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                DefServicio def = Base.getDefServicio("ConsultaTotalesAgencia");
                data = new Datos(def.getInputRecordDef());
                Servicio consultaTotales = null; 
                int resp = 0;
                try {
                    consultaTotales = FactoryServicio.makeInstance("ConsultaTotalesAgencia");
                    consultaTotales.setRequest(this.data);
                    vista.showBusyWindow("Consultando", "Espere por favor...");
                    resp = consultaTotales.execute();
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
                totales = consultaTotales.getResponse().getArrayList("Total");
                
                if(totales.size() == 0){
                    Base.logger.info("No existen totales"); 
                    return 13;
                }
                
                String []titulos = new String[3];
                titulos[0] = "Concepto";
                titulos[1] = "Cantidad";
                titulos[2] = "Monto";

                String [][]matrix = new String[totales.size()][3];                
                
                for(int j = 0; j < matrix.length; j++){
                    matrix[j][0] = totales.get(j).getStringValue("Concepto");
                    matrix[j][1] = Integer.toString(totales.get(j).getIntValue("Cantidad"));
                    matrix[j][2] = Long.toString(totales.get(j).getLongValue("Monto"));
                }
                vista.setEntryTable(titulos, matrix, true);
                return ICajaView._WAITFORACTION;
            case 1:
                String []aux = Base.getTotales();
                String []categorias = new String[aux.length];
                String []totalizados = new String[aux.length ];
                int []indexRemesas = new int[this.numRemesas()];
                long montoRendido = 0;
                long montoRemesas = 0;
                long montoRecaudacion = 0;
                
                int posRemesa = 0;
                
                for(int i = 0 ; i < totalizados.length; i++){
                    totalizados[i] = "";
                }
                for(int i = 0 ; i < aux.length; i++){
                    String []opt = aux[i].split(",");
                    categorias[i] = opt[0];
                    for(int j = 1; j < opt.length ; j++){
                        totalizados[i] = totalizados[i] + "," + opt[j];
                    }
                }
                long []montoCategoria = new long[categorias.length];
                int []cantidadCategoria = new int[categorias.length];
                
                for(int i = 0 ; i < totales.size(); i++){
                    int pos = posCategoria(totales.get(i).getStringValue("Concepto"),totalizados);
                    if(pos != -1){
                        montoCategoria[pos] = montoCategoria[pos] + totales.get(i).getLongValue("Monto");
                        cantidadCategoria[pos] = cantidadCategoria[pos] + totales.get(i).getIntValue("Cantidad");
                    }
                    else if(totales.get(i).getStringValue("Concepto").contains("Remesa")){                        
                            indexRemesas[posRemesa] = i;
                            posRemesa++;                        
                    }
                }
                voucher.add(this.getLinea("COMPROBANTE CIERRE AGENCIA", true, false, false, true));
                voucher.add(this.getLinea("", false, false, false, false));
                String s = String.format("%-19s %-7s %-8s", "EMPRESAS","Cant","TotalRec");
                voucher.add(this.getLinea(s, true, true,false,false));
                voucher.add(this.getLinea("", false, false, false, false));
                
                for(int i = 0 ; i < categorias.length; i++){
                    if(!categorias[i].equals("-Devoluciones")){
                        if(cantidadCategoria[i] != 0){
                            s = String.format("%-21s %-3d %12s",categorias[i],cantidadCategoria[i],Format.formatMonto(montoCategoria[i]) );
                            voucher.add(this.getLinea(s, false, false,false,false));
                            Base.logger.info(categorias[i] + "  " + cantidadCategoria[i] + "    " + montoCategoria[i]);
                        }
                    }                       
                }
                voucher.add(this.getLinea(" , ", false, true,true,false));
                Base.logger.info("____________________________________________________________");
                s = String.format("%-21s %-3d %12s", "SUB TOTAL REC:",this.getCantidadTotalDocs(cantidadCategoria,categorias),Format.formatMonto(this.getMontoTotalDocs(montoCategoria,categorias)));
                voucher.add(this.getLinea( s, true, false,false,false));
                Base.logger.info("SUB TOTAL REC:" + this.getCantidadTotalDocs(cantidadCategoria,categorias) + " " + this.getMontoTotalDocs(montoCategoria,categorias));
                voucher.add(this.getLinea(" , ", false, true,true,false));
                Base.logger.info("____________________________________________________________");
                voucher.add(this.getLinea("", false, false, false,false));
                Base.logger.info(" ");
                
                int index = this.getPosConcepto("-Devoluciones", categorias);
                if(cantidadCategoria[index] != 0){
                    s = String.format("%-21s %-3d %12s", categorias[index],cantidadCategoria[index],Format.formatMonto(montoCategoria[index]));
                    voucher.add(this.getLinea(s, false, false, false,false));
                    Base.logger.info(categorias[index] + " " + montoCategoria[index]);
                    Base.logger.info(" ");
                }
                voucher.add(this.getLinea("", false, false, false,false));
                montoRecaudacion = this.getMontoTotal(montoCategoria, categorias);
                voucher.add(this.getLinea(" , ", false, true,true,false));
                Base.logger.info("____________________________________________________________");
                s = String.format("%-21s %-3d %12s","TOT. RECAUDACION:", this.getCantidadTotal(cantidadCategoria),Format.formatMonto(this.getMontoTotal(montoCategoria, categorias)));
                voucher.add(this.getLinea(s, true, false, false,false));
                Base.logger.info("TOTAL RECAUDACION:    " + this.getCantidadTotal(cantidadCategoria) + "    " + this.getMontoTotal(montoCategoria, categorias));
                voucher.add(this.getLinea(" , ", false, true,true,false));
                Base.logger.info("____________________________________________________________");

                //Procesamos las remesas
                montoCategoria = new long[indexRemesas.length];
                cantidadCategoria = new int[indexRemesas.length];
                
                voucher.add(this.getLinea("", false, false, false, false));
                Base.logger.info(" ");
                Base.logger.info(" ");
                
                for(int i = 0 ; i < indexRemesas.length; i++){                    
                    montoCategoria[i] = montoCategoria[i] + totales.get(indexRemesas[i]).getLongValue("Monto");
                    cantidadCategoria[i] = cantidadCategoria[i] + totales.get(indexRemesas[i]).getIntValue("Cantidad");
                    Datos auxData = totales.get(indexRemesas[i]);
                    if(cantidadCategoria[i] != 0){
                        String tipo = auxData.getStringValue("Concepto").substring(11,13);
                        String tipoR = "";
                        if(tipo.equals("EF")){
                            tipoR = "Efectivo";
                        }
                        else if(tipo.equals("CD")){
                            tipoR = "Cheque";
                        }
                        else{
                            tipoR = "Vale Vista";
                        }
                        s = String.format("%-21s %-3d %12s", auxData.getStringValue("Concepto").substring(5,11) + " " +tipoR, cantidadCategoria[i],Format.formatMonto( montoCategoria[i]));
                        voucher.add(this.getLinea(s, false, false, false, false));
                        Base.logger.info(auxData.getStringValue("Concepto").substring(5,11) + " " + auxData.getStringValue("Concepto").substring(11) + ","+ cantidadCategoria[i] + "," + montoCategoria[i]);
                    }
                }
                voucher.add(this.getLinea("", false, false, false, false));
                montoRemesas = this.getMontoTotal(montoCategoria, categorias);
                s = String.format("%-21s %-5s %12s", "TOT. REMESAS:",Integer.toString(this.getCantidadTotal(cantidadCategoria)) + " (1)",Format.formatMonto(this.getMontoTotal(montoCategoria, categorias)));
                voucher.add(this.getLinea(s, true, false, false, false));
                Base.logger.info("Total Remesas:    " + this.getCantidadTotal(cantidadCategoria) + "    " + this.getMontoTotal(montoCategoria, categorias));
                voucher.add(this.getLinea("", false, false, false, false));
                Base.logger.info(" ");
                //Procesamos los pagos
                
                aux = Base.getTotalesPagos("");
                categorias = new String[aux.length];
                totalizados = new String[aux.length ];
                
                s = String.format("%-19s %-7s %-8s", "No depositable","Cant","TotalRec");
                voucher.add(this.getLinea(s, true, true,false,false));
                voucher.add(this.getLinea("", false, false, false, false));
                
                for(int i = 0 ; i < totalizados.length; i++){
                    totalizados[i] = "";
                }
                for(int i = 0 ; i < aux.length; i++){
                    String []opt = aux[i].split(",");
                    categorias[i] = opt[0];
                    for(int j = 1; j < opt.length ; j++){
                        totalizados[i] = totalizados[i] + "," + opt[j];
                    }
                }
                montoCategoria = new long[categorias.length];
                cantidadCategoria = new int[categorias.length];
                
                for(int i = 0 ; i < totales.size(); i++){
                    int pos = posCategoria(totales.get(i).getStringValue("Concepto"),totalizados);
                    if(pos != -1){
                        montoCategoria[pos] = montoCategoria[pos] + totales.get(i).getLongValue("Monto");
                        cantidadCategoria[pos] = cantidadCategoria[pos] + totales.get(i).getIntValue("Cantidad");
                    }
                }

                ParamSet posDat = Base.getParamSet("posDat");
                for(int i = 0 ; i < categorias.length; i++){
                    if(!categorias[i].equals("-Devoluciones")){
                        if(cantidadCategoria[i] != 0){
                            if(categorias[i].contains("Mismo")){
                                String []aux1 = categorias[i].split("Mismo");
                                String []aux2 = posDat.getStringValue("banco").split("\\,");
                                s = String.format("%-21s %-3d %12s", aux1[0] + Base.getBancoCodigoCorto(Integer.parseInt(aux2[0])),cantidadCategoria[i],Format.formatMonto(montoCategoria[i]));
                            }
                            else{
                                s = String.format("%-21s %-3d %12s", categorias[i],cantidadCategoria[i],Format.formatMonto(montoCategoria[i]));
                            }
                            voucher.add(this.getLinea(s, false, false, false, false));
                            Base.logger.info(categorias[i] + "  " + cantidadCategoria[i] + "    " + montoCategoria[i]);
                        }
                    }                       
                }
                Base.logger.info(" ");
                voucher.add(this.getLinea("", false, false, false, false));
                s = String.format("%-21s %-5s %12s", "TOT. NO DEPOS:",Integer.toString(this.getCantidadTotal(cantidadCategoria)) + " (2)",Format.formatMonto(this.getMontoTotal(montoCategoria, categorias)));
                voucher.add(this.getLinea(s, true, false, false, false));
                Base.logger.info("Total No Depositable:    " + this.getCantidadTotal(cantidadCategoria) + "    " + this.getMontoTotal(montoCategoria, categorias));
                montoRendido = this.getMontoTotal(montoCategoria, categorias);
                Base.logger.info("");
                voucher.add(this.getLinea(" , ", false, true, true, false));
                Base.logger.info("____________________________________________________________");
                long montoTotal = montoRendido + montoRemesas;
                s = String.format("%-21s %-7s %6s", "TOT. VAL. REND:","(1 + 2)",Format.formatMonto(montoTotal));
                voucher.add(this.getLinea(s, true, false, false, false));
                Base.logger.info("Total Valores Rendidos:    " + montoTotal);
                voucher.add(this.getLinea(" , ", false, true, true, false));
                Base.logger.info("____________________________________________________________");
                long saldo = montoRecaudacion - montoRendido - montoRemesas;
                long sobrante = 0;
                long faltante = 0;
                if(saldo < 0 ){
                    sobrante = -saldo;                 
                }
                else{
                    faltante = saldo;
                }
                voucher.add(this.getLinea("", false, false, false, false));
                s = String.format("%-15s %-9s %12s", "Diferencia:", "Sobrante" , Format.formatMonto(sobrante));
                voucher.add(this.getLinea(s, false, false, false, false));
                Base.logger.info("Diferencia: " + "Sobrante: " + sobrante);
                s = String.format("%-15s %-9s %12s", " ", "Faltante" , Format.formatMonto(faltante));
                voucher.add(this.getLinea(s, false, false, false, false));
                Base.logger.info("            " + "Faltante: " + faltante);
                voucher.add(this.getLinea(" , ", false, true, true, false));
                voucher.add(this.getLinea(" , ", false, true, true, false));
                Base.logger.info("____________________________________________________________");
                Base.logger.info("____________________________________________________________");                
                
                
                //Procesamos los medios de pago depositables
                
                aux = Base.getTotalesPagos("Depositables");
                categorias = new String[aux.length];
                totalizados = new String[aux.length ];
                
                voucher.add(this.getLinea("", false, false, false,false));
                s = String.format("%-19s %-7s %-8s", "Depositable","Cant","TotalRec");
                voucher.add(this.getLinea(s, true, true,false,false));
                voucher.add(this.getLinea("", false, false, false, false));
                
                for(int i = 0 ; i < totalizados.length; i++){
                    totalizados[i] = "";
                }
                for(int i = 0 ; i < aux.length; i++){
                    String []opt = aux[i].split(",");
                    categorias[i] = opt[0];
                    for(int j = 1; j < opt.length ; j++){
                        totalizados[i] = totalizados[i] + "," + opt[j];
                    }
                }
                montoCategoria = new long[categorias.length];
                cantidadCategoria = new int[categorias.length];
                
                for(int i = 0 ; i < totales.size(); i++){
                    int pos = posCategoria(totales.get(i).getStringValue("Concepto"),totalizados);
                    if(pos != -1){
                        montoCategoria[pos] = montoCategoria[pos] + totales.get(i).getLongValue("Monto");
                        cantidadCategoria[pos] = cantidadCategoria[pos] + totales.get(i).getIntValue("Cantidad");
                    }
                }
                Base.logger.info(" ");
                for(int i = 0 ; i < categorias.length; i++){
                    if(!categorias[i].equals("-Devoluciones")){
                        if(cantidadCategoria[i] != 0){
                            if(categorias[i].contains("Mismo")){
                                String []aux1 = categorias[i].split("Mismo");  
                                String []aux2 = posDat.getStringValue("banco").split("\\,");
                                s = String.format("%-21s %-3d %12s", aux1[0] + Base.getBancoCodigoCorto(Integer.parseInt(aux2[0])),cantidadCategoria[i],Format.formatMonto(montoCategoria[i]));
                            }
                            else{
                                s = String.format("%-21s %-3d %12s", categorias[i],cantidadCategoria[i],Format.formatMonto(montoCategoria[i]));
                            }
                            voucher.add(this.getLinea(s, false, false, false, false));
                            Base.logger.info(categorias[i] + "  " + cantidadCategoria[i] + "    " + montoCategoria[i]);
                        } 
                    }
                }
                voucher.add(this.getLinea("", false, false, false, false));
                Base.logger.info(" ");
                s = String.format("%-21s %9d", "TOT. CANT:",this.getCantidadTotal(cantidadCategoria));
                voucher.add(this.getLinea(s, true, false, false, false));
                Base.logger.info("Total Cantidad:    " + this.getCantidadTotal(cantidadCategoria));
                voucher.add(this.getLinea(" , ", false, true, true, false));
                voucher.add(this.getLinea(" , ", false, true, true, false));
                Base.logger.info("____________________________________________________________");
                Base.logger.info("____________________________________________________________"); 
                s = String.format("%-21s %12s", "TOT. DEPOS:",Format.formatMonto(this.getMontoTotal(montoCategoria, categorias)));
                voucher.add(this.getLinea(s, true, false, false, false));
                Base.logger.info("Total Depósitos:    " + this.getMontoTotal(montoCategoria, categorias));
                voucher.add(this.getLinea(" , ", false, true, true, false));
                voucher.add(this.getLinea(" , ", false, true, true, false));
                Base.logger.info("____________________________________________________________");
                Base.logger.info("____________________________________________________________");
                voucher.add(this.getLinea("", false, false, false, false));
                //Agregamos los datos de la caja
                
                LineaVoucher oper = new LineaVoucher();
                ParamSet pList = Base.getParamSet( "posDat" ); 
                ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));
                oper.setLinea("N°Operacion:" + pList.getStringValue( "NumeroOperacion" ) + "   Operador:" + pList.getStringValue( "Cajero" ));
                
                voucher.add(oper);
              //Agregamos el footer de datos de la caja
                for(int i = 0; i < footerDatos.size(); i++){
                    voucher.add(footerDatos.get(i));
                }
                //Agregamos la hora y la fecha
                DateFormat df = DateFormat.getDateInstance();
                Time t = new Time(System.currentTimeMillis());
                oper = new LineaVoucher();
                oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
                voucher.add( oper );
                
                vista.showBusyWindow("Imprimiendo", "Espere por favor...");
                Voucher.printVoucher(voucher, true, Voucher.COPIA_LOCAL,null);
                vista.hideBusyWindow();
                return 13;           
        }
        return 0;
    }
    private int getPosConcepto(String concepto,String []categorias){
        int pos = -1;
        for(int i = 0; i < categorias.length; i++){
            if(categorias[i].contains(concepto)){
                pos = i;
                break;
            }
        }
        return pos;
    }
    private int numRemesas(){
        int cant = 0;
        for(int i = 0 ; i < totales.size(); i++){
            if(totales.get(i).getStringValue("Concepto").contains("Remesa")){
                cant++;
            }
        }
        return cant;
    }
    private int posCategoria(String cat, String []totalizados ){
        for(int i = 0 ; i < totalizados.length; i++){
            String []concepts = totalizados[i].split(",");
            for(int j = 0; j < concepts.length; j++){
                if(concepts[j].equals(cat)){
                    return i;
                }
            }
        }
        return -1;
    }
    private int getCantidadTotal(int []cantidadCategorias){
        int total = 0;
        for(int i = 0; i < cantidadCategorias.length; i++){
            total = total + cantidadCategorias[i];
        }
        return total;
    }
    private int getCantidadTotalDocs(int []cantidadCategorias, String []categorias){
        int total = 0;
        for(int i = 0; i < cantidadCategorias.length; i++){
            if(!categorias[i].equals("-Devoluciones")){
                total = total + cantidadCategorias[i];
            }
        }
        return total;
    }
    private long getMontoTotal(long []montoCategorias , String []categorias){
        long total = 0;
        for(int i = 0; i < montoCategorias.length; i++){
            if(categorias[i].equals("-Devoluciones")){
                total = total - montoCategorias[i];
            }
            else{
                total = total + montoCategorias[i];
            }
        }
        return total;
    }
    private long getMontoTotalDocs(long []montoCategorias , String []categorias){
        long total = 0;
        for(int i = 0; i < montoCategorias.length; i++){
            if(!categorias[i].equals("-Devoluciones")){
                total = total + montoCategorias[i];
            }
        }
        return total;
    }
    public LineaVoucher getLinea(String mensaje, boolean bold,boolean underline,boolean right,boolean tri){
        LineaVoucher oper = new LineaVoucher();
        oper.setBold(bold);
        oper.setUnderline(underline);
        oper.setRight(right);
        oper.setCenter(tri);
        oper.setLinea(mensaje);
        
        return oper;
    }
    
}