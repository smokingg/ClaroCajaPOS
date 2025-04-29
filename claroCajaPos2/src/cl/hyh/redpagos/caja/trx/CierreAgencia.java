package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

public class CierreAgencia implements ITrxBase {
    int estado;
    Datos data;
    ArrayList <Datos> depositos;
    ArrayList <Datos> cheques;
    ArrayList <Datos> chequesA;
    ArrayList <LineaVoucher> voucher = new ArrayList<LineaVoucher>();
    ArrayList <LineaVoucher> voucherAgencia = new ArrayList<LineaVoucher>();
    String glosa = "";
    int cantTotal = 0;
    long montoTotal = 0;
    String glosaBanco = "";
    
	public void init(Datos htParm ) {}
	
	@SuppressWarnings("unchecked")
	public int execute( ICajaView vista, int key, Datos htParam ) {
	    if( key == 0 ) {            
            estado = 0;
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        } else if( key == KeyEvent.VK_F3 ){
            estado = 5;
        } 
        else if ( key == KeyEvent.VK_F9){
            return 11;
        }
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {
            case 0:
                if(vista.showMyConfirmDialog("Cierre de agencia", "Se realizarán depositos y se cerrará la agencia.\nDesea continuar?") == JOptionPane.NO_OPTION ){
                    return 13;
                }
                estado = 1;
                DefServicio def = Base.getDefServicio("ConsultaDepositosAgencia");
                data = new Datos(def.getInputRecordDef());
                Servicio consultaDepositos = null; 
                int resp = 0;
                try {
                    consultaDepositos = FactoryServicio.makeInstance("ConsultaDepositosAgencia");
                    consultaDepositos.setRequest(this.data);
                    vista.showBusyWindow("Consultando", "Espere por favor...");
                    resp = consultaDepositos.execute();
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
                depositos = consultaDepositos.getResponse().getArrayList("Deposito");
                
                if(depositos.size() == 0){
                    Base.logger.info("No existen depositos"); 
                    return 13;
                }
                enviaCierreAgencia();
                estado = 5;
                return ICajaView._NOWAITFORACTION;
            case 1:
                estado = 1;
                String []botones = new String[12];
                botones[0] = "";
                botones[1] = "";
                botones[2] = "Re Imprimir";
                botones[3] = "";
                botones[4] = "";
                botones[5] = "";
                botones[6] = "";
                botones[7] = "";
                botones[8] = "Salir";
                botones[9] = "";
                botones[10] = "";
                botones[11] = ""; 
                vista.paintButtons( botones );
                
                String []titulos = new String[7];
                titulos[0] = "Tipo Depósito";
                titulos[1] = "N°Depósito";
                titulos[2] = "Convenio";
                titulos[3] = "Cantidad Documentos";
                titulos[4] = "Monto";
                titulos[5] = "Fecha Depósito";
                titulos[6] = "Fecha Recaudación";

                String [][]matrix = new String[depositos.size()][7];                
                String zero = "0000000000000000000";
                for(int j = 0; j < matrix.length; j++){
                    String aux = "";
                    if(depositos.get(j).getStringValue("Concepto").contains("CHEQUE")){
                        ParamSet posDat = Base.getParamSet("posDat");
                        chequesA = depositos.get(j).getArrayList("Cheque");
                        String aux5 = posDat.getStringValue("banco");
                        String []aux1 = aux5.split("\\,");
                        if(chequesA.get(0).getIntValue("Banco") == Integer.parseInt(aux1[0]))
                            aux = "CHEQUE MISMO BANCO";
                        else
                            aux = "CHEQUE OTROS BANCOS";
                    }
                    else{
                        aux = depositos.get(j).getStringValue("Concepto").toUpperCase();
                    }
                    matrix[j][0] = aux;
                    aux = zero + depositos.get(j).getStringValue("NumeroDeposito");
                    matrix[j][1] = aux.substring(aux.length() - 12);
                    aux = zero + depositos.get(j).getStringValue("Convenio");
                    matrix[j][2] = aux.substring(aux.length() - 12);
                    aux = zero + depositos.get(j).getIntValue("Cantidad");
                    matrix[j][3] = aux.substring(aux.length() - 12);
                    matrix[j][4] = String.format("%-16s",Format.formatMonto(depositos.get(j).getLongValue("Monto")));
                    matrix[j][5] = depositos.get(j).getStringValue("FechaDeposito");
                    matrix[j][6] = Tools.getFecha();
                }
                boolean []editable = new boolean[ titulos.length ];
                for( int k = 0; k < editable.length; ++k ) {
                    editable[k] = false;
                }
                vista.showTableView(titulos, matrix, editable);
                return ICajaView._WAITFORACTION;
            case 5:
            	//Hay depositos
                String s = "";
                ParamSet posDat = Base.getParamSet("posDat");
                try{
                for(int i = 0 ; i < depositos.size(); i++){
                    for(int j = 0 ; j < 2 ; j++){
                        voucher = new ArrayList<LineaVoucher>(); 
                        String []aux3 = posDat.getStringValue("banco").split("\\,"); 
                        voucher.add(this.getLinea("Boleta para Deposito", true, false, false, true,false));
                        voucher.add(this.getLinea("Banco" + Base.getBancoCodigo(Integer.parseInt(aux3[0])), true, false, false, true,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));                        
                        voucher.add(this.getLinea("Recaudación Electrónica", true, false, false, true,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        s = "N°Depósito:," + depositos.get(i).getStringValue("NumeroDeposito");
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea(" , ", false, true,true,false,false));
    
                        s = "Convenio:," + depositos.get(i).getStringValue("Convenio") + " ";
                        LineaVoucher linea = this.getLinea(s, true, false,true,false,false);
                        linea.setCaps(true);
                        voucher.add(linea);
                        voucher.add(this.getLinea(" , ", false, true,true,false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        String fecha = depositos.get(i).getStringValue("FechaDeposito");
                        String fechaDeposito = fecha.substring(6) + fecha.substring(4,6) + fecha.substring(2,4);
                        s = "1.Fecha Depósito:," + fechaDeposito;
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        String numDeposito = depositos.get(i).getStringValue("NumeroDeposito");
                        fecha = Tools.getFecha();
                        String fechaRecau = fecha.substring(6) + fecha.substring(4,6) + fecha.substring(2,4);
                        s = "2.Fecha Recaudación:," + fechaRecau +numDeposito.substring(0,2)+ posDat.getStringValue("Entidad");
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        s = "3.Codigo Local:," + numDeposito.substring(2) + String.format("%03d",Integer.parseInt(posDat.getStringValue("Agencia"))) + "2";
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        
                        voucher.add(this.getLinea(" , ", false, true,true,false,false));
                        
                        if(depositos.get(i).getStringValue("Concepto").contains("CHEQUE")){
                            chequesA = depositos.get(i).getArrayList("Cheque");
                            String aux = posDat.getStringValue("banco");
                            String []aux1 = aux.split("\\,");
                            if(chequesA.get(0).getIntValue("Banco") == Integer.parseInt(aux1[0])){
                                s = "Tipo Depósito:," + "Cheque Mismo Banco";
                                glosaBanco = "Cheque Mismo Banco";
                            }
                            else{
                                s = "Tipo Depósito:," + "Cheque Otros Bancos";
                                glosaBanco = "Cheque Otros Bancos";
                            }
                        }
                        else{
                            s = "Tipo Depósito:," + depositos.get(i).getStringValue("Concepto");
                        }
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        
                        if(depositos.get(i).getIntValue("Cantidad") == 0){
                            s = "Cantidad Documentos:, N/A";
                        }
                        else{
                            s = "Cantidad Documentos:," + depositos.get(i).getIntValue("Cantidad");
                        }                    
                        voucher.add(this.getLinea(s, true, false,true,false,false));  
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        
                        s = "Total:," + Format.formatMonto(depositos.get(i).getLongValue("Monto"));
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        s = "Sucursal:," + posDat.getStringValue("Direccion");
                        voucher.add(this.getLinea(s, true, false,true,false,false));
                        
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(this.getLinea(" , ", false, true,true,false,false));
                        
                        if(j == 1){
                            LineaVoucher oper = new LineaVoucher();
//                            ParamSet pList = Base.getParamSet( "posDat" ); 
                            ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));
                            voucher.add(this.getLinea("", false, false, false, false,false));
                            voucher.add(oper);
                          //Agregamos el footer de datos de la caja
                            for(int k = 0; k < footerDatos.size(); k++){
                                voucher.add(footerDatos.get(k));
                            }
                            //Agregamos la hora y la fecha
                            DateFormat df = DateFormat.getDateInstance();
                            Time t = new Time(System.currentTimeMillis());
                            oper = new LineaVoucher();
                            oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
                            voucher.add( oper );
                            voucher.add(this.getLinea("", false, false, false, false,false));
                        }
                        s = "Original Banco/Copia Sucursal";
                        voucher.add(this.getLinea(s, false, false,false,true,false));
                        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
                        Voucher.printVoucherBarra(voucher, true,"");
                        vista.hideBusyWindow();
                    }
                    cheques = depositos.get(i).getArrayList("Cheque");
                    Collections.sort(cheques, new MyComparator());
                    zero = "0000000000000000000";
                    if(cheques.size() != 0){
                        voucher = new ArrayList<LineaVoucher>(); 
                        voucher.add(this.getLinea("Lista Cheque " + glosaBanco, true, true, false, true,false)); 
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        s = "N°Depósito:," + depositos.get(i).getStringValue("NumeroDeposito");
                        voucher.add(this.getLinea(s, false, false,true,false,false));
                        voucher.add(this.getLinea("", false, false, false, false,false));    
                        s = String.format("%-14s %-14s %-8s", "Cta.Corriente","N°Serie","Monto");
                        voucher.add(this.getLinea(s, true, true,false,false,false));
                        int bancoActual = -1;
                        int cantidad = 0;
                        for(int j = 0 ; j < cheques.size() ; j++){                           
                            
                            if(bancoActual != cheques.get(j).getIntValue("Banco")){
                                bancoActual = cheques.get(j).getIntValue("Banco");
                                String bancoA = Base.getBancoCodigo(bancoActual);
                                s = "Banco" + bancoA;
                                voucher.add(this.getLinea(s, true, false,false,false,false));                                
                            }
                            
                            zero = "000000000000000";
                            String auxC = zero + cheques.get(j).getStringValue("Cuenta");
                            String auxS = zero + cheques.get(j).getStringValue("Serial");
                            s = String.format("%-18s %-18s %15s",auxC.substring(auxC.length() - 12),auxS.substring(auxS.length() - 12),Format.formatMonto(cheques.get(j).getLongValue("Monto")) );
                           
                            cantidad++;
                            voucher.add(this.getLinea(s, false, false,false,false,true));      
                            try{
                                if(bancoActual != cheques.get(j+1).getIntValue("Banco")){
                                    s = String.format("%-43s %07d", "CANTIDAD DOCUMENTOS:",cantidad);
                                    voucher.add(this.getLinea( s, true, false,false,false,true));
                                    voucher.add(this.getLinea("", false, false, false, false,false));
                                    cantidad = 0;                               
                                }     
                            }
                            catch(Exception e){
                                s = String.format("%-43s %07d", "CANTIDAD DOCUMENTOS:",cantidad);
                                voucher.add(this.getLinea( s, true, false,false,false,true));
                                cantidad = 0;
                            }
                        }
                        LineaVoucher oper = new LineaVoucher();
//                        ParamSet pList = Base.getParamSet( "posDat" ); 
                        ArrayList<LineaVoucher> footerDatos = Voucher.armarVoucher(data, Base.getDefVoucher("VoucherBoletaDatos"));
                        voucher.add(this.getLinea("", false, false, false, false,false));
                        voucher.add(oper);
                      //Agregamos el footer de datos de la caja
                        for(int k = 0; k < footerDatos.size(); k++){
                            voucher.add(footerDatos.get(k));
                        }
                        //Agregamos la hora y la fecha
                        DateFormat df = DateFormat.getDateInstance();
                        Time t = new Time(System.currentTimeMillis());
                        oper = new LineaVoucher();
                        oper.setLinea("Hora: " + t.toString()+"    " + df.format(Calendar.getInstance().getTime()));
                        voucher.add( oper );
                        vista.showBusyWindow("Imprimiendo", "Espere por favor...");
                        
                        ParamSet pSet = Base.getParamSet("posDat");

                		DatosFileNet datosFileNet = new DatosFileNet();
                		datosFileNet.setCodigo_sesion(pSet.getStringValue("SessionId"));
                		datosFileNet.setCodusuario_envia(pSet.getStringValue("CodigoRecaudador"));
                		datosFileNet.setEmail_para(null);
                		datosFileNet.setNumOperacion(null);
                		datosFileNet.setPropietario(pSet.getStringValue("Usuario"));
                		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_CIERRE);
                		
                        Voucher.printVoucher(voucher, true, Voucher.COPIA_CIERRE_CAJA,datosFileNet);
                        
                        Base.logger.info("P I N P A D - inicializa");
                        Base.tbk.inicializa(posDat.getStringValue("TransbankConfig"));                        
                        TbkMetodos metodos = new TbkMetodos();
            			String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
            			metodos.cierre(Base.tbk,requerimiento);
            			metodos.confirmarOperacion(Base.tbk);
            			
            			
                        vista.hideBusyWindow();
                    }
                }        
                }
                catch(Exception e){
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }
        }
		// LIMPIO VARIABLES DE AMBIENTE
        ParamSet pList = Base.getParamSet( "posDat" );
        Base.logger.info( "Logout: " + pList.getStringValue("Usuario") );
        
        estado = 1;
    	return ICajaView._NOWAITFORACTION;
    }
	
    private void enviaCierreAgencia() {
        Servicio cierreAgencia;
        try {
            cierreAgencia = FactoryServicio.makeInstance( "CierreAgencia" );
            cierreAgencia.execute();
        } catch (BaseException e) {
            Tools.logStackTrace( Base.logger, e );
        }

    }

    @SuppressWarnings("rawtypes")
	public static class MyComparator implements Comparator {
        public int compare( Object a1, Object a2 ) {
            Datos d1 = (Datos)a1;
            Datos d2 = (Datos)a2;
            if( d1.getIntValue("Banco") > d2.getIntValue("Banco") )
                return 1;
            else if(d1.getIntValue("Banco") < d2.getIntValue("Banco"))
                return -1;
            else{
                if(d1.getLongValue("Monto") > d2.getLongValue("Monto"))
                    return 1;
                else if(d1.getLongValue("Monto") < d2.getLongValue("Monto"))
                    return -1;
                else 
                    return 0;
            }            
        }
    }
    public LineaVoucher getLinea(String mensaje, boolean bold,boolean underline,boolean right,boolean center,boolean small){
        LineaVoucher oper = new LineaVoucher();
        oper.setBold(bold);
        oper.setUnderline(underline);
        oper.setRight(right);
        oper.setCenter(center);
        oper.setSmall(small);
        oper.setLinea(mensaje);

        return oper;
    }
}
