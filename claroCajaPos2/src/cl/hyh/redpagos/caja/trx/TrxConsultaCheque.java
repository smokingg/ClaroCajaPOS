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

public class TrxConsultaCheque implements ITrxBase{
    int estado;
    Datos data;
    ArrayList <Datos> totales;
    ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
    Servicio consultaCheque = null;
    String glosa = "";
    int cantTotal = 0;
    long montoTotal = 0;
    String banco = "";
    
    public void init(Datos data){
        
    }
    
    public int execute(ICajaView vista, int key, Datos htParam) {
        if( key == 0 ) {            
            estado = 0;
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else if( key == KeyEvent.VK_F3 ){
            estado = 5;
        } 
        
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                estado = 1;
                String []filtros = new String[2];
                filtros[1] = "Cheque al Día";
                filtros[0] = "Cheque a Fecha";
            
                vista.setEntryList(filtros, true, true);
                return ICajaView._WAITFORACTION;
            case 1:
                DefServicio def = Base.getDefServicio("ConsultaDetalleMP");
                data = new Datos(def.getInputRecordDef());
                 
                
                estado = 2;
                int index = vista.getEntryListIndex();
                
                switch(index){ 
                    case 0:
                        estado = 3;
                        this.data.setValue("TipoMP", "MPChequeFecha");
                        glosa = "Cheque a Fecha";
                    break;
                    case 1:
                        estado = 2;
                        this.data.setValue("TipoMP", "MPCheque");
                        glosa = "Cheque al Día";
                        break;                    
                }               
                
                int resp = 0;
                try {
                    consultaCheque = FactoryServicio.makeInstance("ConsultaDetalleMP");                    
                    consultaCheque.setRequest(this.data);
                    vista.showBusyWindow("Consultando", "Espere por favor...");
                    resp = consultaCheque.execute();
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
                return ICajaView._NOWAITFORACTION;
            case 2:
                totales = consultaCheque.getResponse().getArrayList("MPCheque");
                estado = 4;
                return ICajaView._NOWAITFORACTION;
            case 3:
                totales = consultaCheque.getResponse().getArrayList("MPChequeFecha");
                estado = 4;
                return ICajaView._NOWAITFORACTION;
            case 4:
                estado = 4;
                if(totales.size() == 0){
                    JOptionPane.showMessageDialog(null, "No existen totales de " + glosa,"Error", JOptionPane.INFORMATION_MESSAGE);
                    Base.logger.info("No existen totales de cheques"); 
                    return 13;
                }
                
                String []botones = new String[12];
                botones[0] = "";
                botones[1] = "";
                botones[2] = "Imprimir";
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
                
                String []titulos = new String[4];
                titulos[0] = "Cta. Corriente";
                titulos[1] = "N°Serie";
                titulos[2] = "Banco";
                titulos[3] = "Monto";
                
                ParamSet posDat = Base.getParamSet("posDat");
                String [][]matrix = new String[totales.size()][4];                
                String zero = "0000000000000000000";
                for(int j = 0; j < matrix.length; j++){
                    String aux = zero + totales.get(j).getStringValue("Cuenta");
                    matrix[j][0] = aux.substring(aux.length() - 12);
                    aux = zero + totales.get(j).getStringValue("Serial");
                    matrix[j][1] = aux.substring(aux.length() - 12);
                    matrix[j][3] = String.format("%-16s",Format.formatMonto(totales.get(j).getLongValue("Monto")));
                    if(totales.get(j).getStringValue("TipoTotal").equals("MismoBanco")){
                        String []bancos = posDat.getStringValue("banco").split("\\,");
                        banco = Base.getBancoCodigo(Integer.parseInt(bancos[0]));
                        matrix[j][2] = banco;
                    }
                    else{
                        matrix[j][2] = " Otros Bancos";
                    }
                }
                boolean []editable = new boolean[ titulos.length ];
                for( int k = 0; k < editable.length; ++k ) {
                    editable[k] = false;
                }
                vista.showTableView(titulos, matrix, editable);
                return ICajaView._WAITFORACTION;
            case 5:
                
                estado = 100;
                voucher.add(this.getLinea("Consulta Totales " + glosa , true, false, false, true,false));
                voucher.add(this.getLinea("", false, false, false, false,false));
                voucher.add(this.getLinea("Banco" + banco, true, false, false, true,false));
                voucher.add(this.getLinea("", false, false, false, false,false));
                String s = String.format("%-14s %-14s %-8s", "Cta.Corriente","N°Serie","Monto");
                voucher.add(this.getLinea(s, true, true,false,false,false));
                voucher.add(this.getLinea("", false, false, false, false,false));
                long monto = 0;
                int cantidad = 0;
                for(int i = 0 ; i < totales.size(); i++){
                    if(totales.get(i).getStringValue("TipoTotal").equals("MismoBanco")){
                        zero = "000000000000000";
                        String auxC = zero + totales.get(i).getStringValue("Cuenta");
                        String auxS = zero + totales.get(i).getStringValue("Serial");
                        s = String.format("%-18s %-18s %15s",auxC.substring(auxC.length() - 12),auxS.substring(auxS.length() - 12),Format.formatMonto(totales.get(i).getLongValue("Monto")) );
                        monto = monto + totales.get(i).getLongValue("Monto");
                        cantidad++;
                        voucher.add(this.getLinea(s, false, false,false,false,true));
                        Base.logger.info(auxC.substring(auxC.length() - 12) + "     " + auxS.substring(auxS.length() - 12) + "     " + Format.formatMonto(totales.get(i).getLongValue("Monto")));                    
                    }
                }
                montoTotal += monto;
                cantTotal += cantidad;
                
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info("____________________________________________________________");
                s = String.format("%-35s %15s", "TOTAL:",Format.formatMonto(monto));
                voucher.add(this.getLinea( s, true, false,false,false,true));
                Base.logger.info("SUB TOTAL REC:" + monto );
                s = String.format("%-43s %07d", "CANTIDAD DOCUMENTOS:",cantidad);
                voucher.add(this.getLinea( s, true, false,false,false,true));
                Base.logger.info("Cantidad:" + cantidad );
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info("____________________________________________________________");
                voucher.add(this.getLinea("", false, false, false,false,false));
                Base.logger.info(" ");
                
                voucher.add(this.getLinea("Otros Bancos", true, false, false, true,false));
                voucher.add(this.getLinea("", false, false, false, false,false));
                s = String.format("%-14s %-14s %-8s", "Cta.Corriente","N°Serie","Monto");
                voucher.add(this.getLinea(s, true, true,false,false,false));
                voucher.add(this.getLinea("", false, false, false, false,false));
                
                monto = 0;
                cantidad = 0;
                for(int i = 0 ; i < totales.size(); i++){
                    if(!totales.get(i).getStringValue("TipoTotal").equals("MismoBanco")){
                        zero = "000000000000000";
                        String auxC = zero + totales.get(i).getStringValue("Cuenta");
                        String auxS = zero + totales.get(i).getStringValue("Serial");
                        s = String.format("%-18s %-18s %15s",auxC.substring(auxC.length() - 12),auxS.substring(auxS.length() - 12),Format.formatMonto(totales.get(i).getLongValue("Monto")) );
                        monto = monto + totales.get(i).getLongValue("Monto");
                        cantidad++;
                        voucher.add(this.getLinea(s, false, false,false,false,true));
                        Base.logger.info(auxC.substring(auxC.length() - 12) + "     " + auxS.substring(auxS.length() - 12) + "     " + Format.formatMonto(totales.get(i).getLongValue("Monto")));                    
                    }
                }
                montoTotal += monto;
                cantTotal += cantidad;
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info("____________________________________________________________");
                s = String.format("%-35s %15s", "TOTAL:",Format.formatMonto(monto));
                voucher.add(this.getLinea( s, true, false,false,false,true));
                Base.logger.info("SUB TOTAL REC:" + monto );
                s = String.format("%-43s %07d", "CANTIDAD DOCUMENTOS:",cantidad);
                voucher.add(this.getLinea( s, true, false,false,false,true));
                Base.logger.info("Cantidad:" + cantidad );
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info("____________________________________________________________");
                voucher.add(this.getLinea("", false, false, false,false,false));
                Base.logger.info(" ");
                
                //Agregamos los totales finales
                
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info("____________________________________________________________");
                s = String.format("%-35s %15s", "TOTAL DOCUMENTOS:",Format.formatMonto(montoTotal));
                voucher.add(this.getLinea( s, true, false,false,false,true));
                Base.logger.info("SUB TOTAL REC:" + monto );
                s = String.format("%-43s %07d", "CANTIDAD DOCUMENTOS:",cantTotal);
                voucher.add(this.getLinea( s, true, false,false,false,true));
                Base.logger.info("Cantidad:" + cantidad );
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info("____________________________________________________________");
                voucher.add(this.getLinea("", false, false, false,false,false));
                voucher.add(this.getLinea(" , ", false, true,true,false,false));
                Base.logger.info(" ");
                
            case 100:
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
                vista.dismissTableView();
                vista.showBusyWindow("Imprimiendo", "Espere por favor...");
                
                Voucher.printVoucher(voucher, true, Voucher.COPIA_LOCAL, null);
                vista.hideBusyWindow();
                return 13;           
        }
        return 0;
    }
    public LineaVoucher getLinea(String mensaje, boolean bold,boolean underline,boolean right,boolean tri,boolean small){
        LineaVoucher oper = new LineaVoucher();
        oper.setBold(bold);
        oper.setUnderline(underline);
        oper.setRight(right);
        oper.setCenter(tri);
        oper.setSmall(small);
        oper.setLinea(mensaje);
        
        return oper;
    }
    
}
