package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.MedioPagoDTO;
import ws.claro.cl.proxy.AppControlProxy;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DatosFileNet;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.MedioPagoException;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.URLString;
import cl.hyh.redpagos.caja.base.Voucher;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.pos.TipoTarjetaEnum;

public class TrxTarjetaTbkDirecto extends MedioPago implements ITrxBase{
	private static final long serialVersionUID = 1L;
	
	private static final String DB = "DEBITO";
	private static final String CR = "CREDITO";
	private static final String NB = "MULTITIENDA";
    int estado = 0;
    Datos data;
    long montoP= 0;
    MedioPago mPago = null;
    String origen = "";
    String ultdigTrj;
    String respLeer;
    String respVC;
    String tipoTarjeta;
    String codTbkReturn;
    String msgTbkReturn;
    boolean ult4digflag;
    LineaVoucher[] voucherCliente = null;
    LineaVoucher[] voucherComercio = null;
    LineaVoucher[] voucherClienteDuplicado = null;
    LineaVoucher[] voucherComercioDuplicado = null;
    LineaVoucher[] voucherPremioCliente = null;
    LineaVoucher[] voucherPremioComercio = null;
    
    LineaVoucher[] voucherClienteReimpresion = null;
    
    String[][] dataMulticard;
    ArrayList<LineaVoucher> comprobante;
    String email;
    
    /** Actual: comentado para pruebas de impresion...
    public LineaVoucher[] getVoucherCliente() {
        return voucherCliente;
    }
    public LineaVoucher[] getVoucherClienteDuplicado(){
        return voucherClienteDuplicado;
    }
    */
    
    // Inicio: Pruebas para impresion Transbank
    public LineaVoucher[] getVoucherCliente() {
        LineaVoucher lV = new LineaVoucher();
        LineaVoucher[] lVo = new LineaVoucher[2];
        lV.setLinea("Pago con Tarjeta");
        lVo[0] = lV;
        lVo[0].setRight(false);
        
        LineaVoucher lV2 = new LineaVoucher();
        String s = String.format("%-23s: %-9s", "Valor Pagado", Format.formatMonto(montoP));
        lV2.setLinea(s);
        lVo[1] = lV2;
        lVo[1].setRight(true);
        
        
        return lVo;
    }
    public LineaVoucher[] getVoucherClienteDuplicado(){
    	 LineaVoucher lV = new LineaVoucher();
         LineaVoucher[] lVo = new LineaVoucher[2];
         lV.setLinea("Pago con Tarjeta");
         lVo[0] = lV;
         lVo[0].setRight(false);
         
         LineaVoucher lV2 = new LineaVoucher();
         String s = String.format("%-23s: %-9s", "Valor Pagado", Format.formatMonto(montoP));
         lV2.setLinea(s);
         lVo[1] = lV2;
         lVo[1].setRight(true);
         
         return lVo;
    }
    
    public LineaVoucher[] getVoucherClte() {
        return voucherCliente;
    }
    public LineaVoucher[] getVoucherClteDuplicado(){
        return voucherClienteDuplicado;
    }
    // Fin: Pruebas para impresion Transbank
    
    public LineaVoucher[] getCommerceVoucher(){
        return voucherComercio;
    	// TODO: se reemplaza por Voucher de Reimpresion con Duplicado...
        //return voucherClienteReimpresion;
    }
    
    public LineaVoucher[] getCommerceVoucherDuplicado(){
        return voucherComercioDuplicado;
    }
    public LineaVoucher[] getPremioCliente(){
        return voucherPremioCliente;
    }
    public LineaVoucher[] getPremioLocal(){
        return voucherPremioComercio;
    }
    
    public LineaVoucher[] getCommerceVoucherReimpresion() {
        return voucherClienteReimpresion;
    }
    
    public void init(Datos datosVista){        
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 


    		ParamSet posCfg = Base.getParamSet("posCfg"); 
    		ParamSet posDat = Base.getParamSet( "posDat" );
        	boolean isPdf = Boolean.parseBoolean(posDat.getStringValue("isPDF") != null ? posDat.getStringValue("isPDF")  : "false");
        if( key == 0 ) {
            ParamSet pDat = Base.getParamSet("posDat");
            ParamSet pSet = Base.getParamSet( "posCfg" );
            if( pDat.getStringValue("transbank").equals("no") ){
                JOptionPane.showMessageDialog(null, "Medio de Pago no disponible.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return 13;
            }
            
            if(!this.isIngresable(vista)){
                JOptionPane.showMessageDialog(null, "Medio de Pago no autorizado", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                Base.logger.info("Medio de Pago no Autorizado");
                return 13;
            }
            
            if(!vista.getOperTRV().isValid(this)){
            	Base.logger.info("No se pudo agregar un Segundo Medio de Pago ...");
                return 13;
            }
            
            if(datos.getStringValue("trxParam").equals("anular")){
                estado = 100;
            }
            else{
                estado = 0;
                origen = datos.getStringValue("isRapido");
            }
            
            if(vista.getOperTRV() != null){
                if(vista.getOperTRV().getCarroCompras().getDocumentos().size() == 0){
                    JOptionPane.showMessageDialog(null, "Carro de Compras vacio\nOperacion no valida", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13;
                }else{
                	// Validar si el monto total del Carro no es menor a $50 (Parametro en cfg)..!!
                	if(vista.getOperTRV().getCarroCompras().getMontoTotal() < Integer.parseInt(pSet.getStringValue("montoTransbank"))){
                		JOptionPane.showMessageDialog(null, "El monto minimo a cancelar con Tarjeta debe ser de $"+pSet.getStringValue("montoTransbank"), "Error", JOptionPane.INFORMATION_MESSAGE);
                        return 13;
                	}
                }
               
            }
        } else if( key == 1 ) {
            // Timeout
            return 13;
        } else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9 ) {
          /*  if(vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0){
                vista.getOperTRV().getCarroMediosPago().borrarCarro();
            }*/
            return 13;
        }else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        
        switch( estado ) {
            
                
                        
            case 0 :
                estado = 1;
                DefMedioPago defM = Base.getDefMedioPago("Tarjeta");
                data = new Datos(defM.getRecordDef());  
                montoP = vista.getOperTRV().getCarroCompras().getMontoTotal();
                data.setValue("Monto", montoP); 
                return ICajaView._NOWAITFORACTION;
                
            case 1:
                estado = 2;
//                mPago = null;
                ParamSet p = Base.getParamSet( "posCfg" );
                //montoP = Long.parseLong(vista.getEntryText());
                if (montoP < Long.parseLong(p.getStringValue("montoTransbank"))){
            		estado = 8;
            		JOptionPane.showMessageDialog(null, "El monto minimo a cancelar con Tarjeta debe ser de $"+p.getStringValue("montoTransbank"), "Error", JOptionPane.INFORMATION_MESSAGE);
                    return 13; 
            	}
               
                //Llamamos las apis de Multicard para autorizar el medio de pago y obtenemos los valores a guardar
                
                int numBoleta = Integer.parseInt( posDat.getStringValue("NumeroOperacion") );
                int montoPago = (int)this.data.getLongValue("Monto");
                
                try {
                
                tipoTarjeta = vista.openSolicitaTrajetas();
                
                vista.hideAllEntries();
                
                Base.tbk.inicializa(posDat.getStringValue("TransbankConfig"));
                
                vista.showBusyWindow("Cliente Operando Pinpad", "Espere por favor...");
                respLeer = leerTarjeta(montoPago+"", tipoTarjeta, "CL", numBoleta+"");
                
                if (respLeer != null){
		                Base.logger.error(respLeer);
		                URLString url = new URLString(respLeer);
		                
		              //Obtener código retornado por Transbank
		              codTbkReturn=url.getValor("AUTRET")==null?codTbkReturn=url.getValor("TXCRET"):url.getValor("AUTRET");
		              msgTbkReturn= url.getValor("TXCGLO")==null?"":url.getValor("TXCGLO");
		              msgTbkReturn=Format.formatMsjTbk(msgTbkReturn);
		                
		              //FIX El flujo de exito es con TXCRET = 00
		        		if (url.getValor("TXCRET") == null || "00".equalsIgnoreCase(url.getValor("TXCRET"))) {
		        			//La transacción fluyó ida y vuelta hasta el Host de Transbank sin problemas
		        			
		        			if (url.getValor("SOLICITAULTDIGITOS").equalsIgnoreCase("Y")) {
		        				Base.logger.info("Ultimos 4 digitos necesarios");
		        				vista.hideBusyWindow();
		        				vista.setEntryMessage( "Ingrese últimos 4 dígitos", true );
		                        vista.setEntryTextLabel("Ingrese últimos 4 dígitos:", true);
		                        vista.setEntryText("", true, false, false,"[0-9]{4}", "4 últimos dígitos necesarios");
		                        ult4digflag=true;
		                        estado=4;
		                        return ICajaView._WAITFORACTION;
		        			} else {
		        				Base.logger.info("4 digitos no son necesarios");
		        				ultdigTrj=url.getValor("CUATROULTDIGITOS");
		        				ult4digflag=false;
		        				estado=5;
		                        return ICajaView._NOWAITFORACTION;
		        			}
		        		} else{
		        			vista.hideBusyWindow();
		        			String mensaje = "";		        					
		        			if (msgTbkReturn.contains("tecla Cancel/Timeout")){
//		        				mensaje = "RetTbk [" + codTbkReturn + "]. Operación Cancelada";
		        				mensaje = "RetTbk [" + codTbkReturn + "]. " + msgTbkReturn;
		        			}else{
			        			if (msgTbkReturn.contains("comunicacion")){
//			        				mensaje = "RetTbk [" + codTbkReturn + "]. Error de Comunicación Con el Pinpad";
			        				mensaje = "RetTbk [" + codTbkReturn + "]. " + msgTbkReturn;
			        			} else{
//			        				mensaje = "RetTbk [" + codTbkReturn + "]. Error al realizar pago Transbank, reintentar...";
			        				mensaje = "RetTbk [" + codTbkReturn + "]. " + msgTbkReturn;
			        			}
		        			}
		                    Base.logger.error("RetTbk [" + codTbkReturn + "]. " + mensaje);
		                    JOptionPane.showMessageDialog(null, mensaje.length()>100?Format.formatText(mensaje):mensaje,
		                    		"Error", JOptionPane.INFORMATION_MESSAGE);
		                    return 13;
		                }
		        		
		        } else{
		        	 vista.hideBusyWindow();
		            Base.logger.error("No se pudo realizar la validacion del pinpad");
		            JOptionPane.showMessageDialog(null, "Error al realizar pago Transbank, reintentar...", "Error", JOptionPane.INFORMATION_MESSAGE);
		            return 13;
		        }
                
                } catch(Exception e) {
                	vista.hideBusyWindow();
                	JOptionPane.showMessageDialog(null, "Error al realizar pago Transbank, reintentar...", "Error", JOptionPane.INFORMATION_MESSAGE);
                	Base.logger.error("No se pudo realizar la transaccion del pinpad, motivo : " 
                			+ e.getMessage());
                	return 13;
                }
		                
            case 2:
                estado = 1010;
				
                if(origen.equals("si")){
                	 vista.hideBusyWindow();
                 	// vista.getDatos().setValue("voucherComercio",respVC);
                 	 vista.getOperTRV().getDatos().setValue("voucherComercio",respVC);
                    return 15;
                }
                vista.hideAllEntries();
                vista.setEntryTitle( "Pago Tarjeta", true );
                vista.setEntryTextLabel("Medio Pago:", true);
                vista.setEntryTextArea(vista.getOperTRV().imprimirBoletaPantalla(), true);
//                return ICajaView._WAITFORACTION;
                return ICajaView._NOWAITFORACTION;
                
            case 3:     
            	 // [REspinoza] Se quita confirmación por solicitud TBK
//            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		vista.showBusyWindow("Enviando", "Espere por favor...");

                    int ok = vista.getOperTRV().confirmarTBK(vista, respVC);
                    
                    vista.hideBusyWindow();
                    
                    vista.removeTRV(); 
                     return 14;
                    
//                    [Se quita según Multicard]
//                    if (ok != 0){//                    	
//                    	TbkMetodos metodos = new TbkMetodos();
//            			String requerimiento = "MONTO=0&TIPTRX=TBKCIE&MONEDA=CL";
//            			metodos.cierre(Base.tbk,requerimiento);
//            			metodos.confirmarOperacion(Base.tbk);
//                    }
                    
//            	}
            	
            case 4:
            	vista.showBusyWindow("Operando Pinpad", "Espere por favor...");
            	ultdigTrj = vista.getEntryText();
            	vista.hideAllEntries();
            	estado=5;
            	return ICajaView._NOWAITFORACTION;
            case 5:
            	
            	try {
            	
            	 respVC = ventaTarjeta(ult4digflag, ultdigTrj,(int)this.data.getLongValue("Monto")+"", tipoTarjeta, "CL", respLeer);
            	 estado = 2;
            	 
//  				try {
// 					mPago = FactoryMedioPago.makeInstance("Tarjeta");
// 				} catch (BaseException e1) {
// 					Tools.logStackTrace(Base.logger, e1);
// 				}
//  				data.setValue("Monto", this.data.getLongValue("Monto"));
//  				mPago.getDatos().asignaPorNombre(this.data);
//  				vista.getOperTRV().getCarroMediosPago().addMedioPago(mPago);
            	 
            	 if(respVC!= null){
            	 
		            	 URLString urlVC = new URLString(respVC);
		            	 Base.logger.info(respVC);
		            	 
			             //Obtener código/mensage retornado por Transbank
		            	 codTbkReturn=urlVC.getValor("AUTRET")==null?urlVC.getValor("TXCRET"):urlVC.getValor("AUTRET"); 
//		            	 msgTbkReturn=urlVC.getValor("TXCGLO")==null?"":urlVC.getValor("TXCGLO");
		            	 msgTbkReturn=Format.getMesageTbk(urlVC);
		            	 msgTbkReturn="RetTbk [" + codTbkReturn + "]." + Format.formatMsjTbk(msgTbkReturn);
		            	 
		            	 if(respVC.contains("AUTRET") && respVC.contains("TXCRET")){
		            		 
			            	 if(urlVC.getValor("TXCRET").equals("00") 
			            			 && Integer.parseInt(urlVC.getValor("AUTRET")) <= 9){
			            		 vista.hideBusyWindow();
			            		 vista.showBusyWindow("Transacción Aprobada", "Espere por favor...");
			            		 try {
			            			 Thread.sleep(5000);
								 } catch (InterruptedException e) {
									e.printStackTrace();
								 }
			            		 if (!urlVC.getValor("VOUCHERPREMIO").equalsIgnoreCase("")) {
				            		 vista.hideBusyWindow();
				         			 vista.showBusyWindow("Transaccion Premiada", "Espere por favor...");
				         			 try {
											Thread.sleep(5000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}				            		 
				            	 }
			            		 
			                 } else{
			                	 vista.hideBusyWindow();
			                     Base.logger.error("RetTbk " + codTbkReturn + " No se pudo realizar el pago");
			                     
			                     if(msgTbkReturn.trim().equalsIgnoreCase("")){
			                    	 msgTbkReturn = " Error al realizar pago Transbank, reintentar...";
			                     }
			                     JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
				                    		"Error", JOptionPane.INFORMATION_MESSAGE);
			                     
			                     return 13;
			                 }
		            	 }else{
		            		 vista.hideBusyWindow();
		                     Base.logger.error("No se pudo realizar el pago");
		                     
		                     if(msgTbkReturn.trim().equalsIgnoreCase("")){
		                    	 msgTbkReturn = "Error al realizar pago Transbank, reintentar...";
		                     }
		                     
		                     JOptionPane.showMessageDialog(null, msgTbkReturn.length()>100?Format.formatText(msgTbkReturn):msgTbkReturn,
			                    		"Error", JOptionPane.INFORMATION_MESSAGE);		                     
		                     return 13;
		            	 }
            	 
            	 }else{
            		 vista.hideBusyWindow();
                     Base.logger.error("No se pudo realizar el pago");                     
                     String mensaje = "Error al realizar pago Transbank, reintentar...";

                     JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.INFORMATION_MESSAGE);
                     return 13;
            	 }
            	 //DESPUES DE QUE EL PAGO FUE EXITOSO
            	 /*MTORO*/
            	 
//            	 estado = 1010;
            			 
            	 vista.hideBusyWindow();
                 this.data.setValue("Monto", (long)this.data.getLongValue("Monto"));
                                  
                 URLString pagoAux = new URLString(respVC);
                 URLString leerAux = new URLString(respLeer);
                 
                 String tarjeta = pagoAux.getValor("FAMTRX");
                 
                 System.out.println("tarjeta = " + tarjeta);
                 if(tarjeta.equalsIgnoreCase("DEBITO")){
                	 this.data.setValue("TipoTarjeta", DB);
                 }else if(tarjeta.equalsIgnoreCase("CREDIT")){
                	 if (tipoTarjeta.equalsIgnoreCase("NB")) {
                	 		this.data.setValue("TipoTarjeta", NB);
                	 	} else{
                            this.data.setValue("TipoTarjeta",CR);
                	 	}
                 }	 	
                 URLString urlVC = new URLString(respVC);
                // this.data.setValue("TipoTarjeta", leerAux.getValor("ABREVIACIONTARJETA"));
                 Base.logger.info("TipoTarjeta: "+this.data.getStringValue("TipoTarjeta"));
                 
                 //TODO no tenemos el numero completo
                 this.data.setValue("NumeroTarjeta", ultdigTrj);
                 Base.logger.info("NumeroTarjeta: "+this.data.getStringValue("NumeroTarjeta"));
                 
                 this.data.setValue("TitularTarjeta", urlVC.getValor("TARJETAHABIENTE"));
                 Base.logger.info("TitularTarjeta: "+this.data.getStringValue("TitularTarjeta"));
                 
                 this.data.setValue("NombreTarjeta",  pagoAux.getValor("MARCA"));
//                 this.data.setValue("NombreTarjeta",  leerAux.getValor("ABREVIACIONTARJETA"));
                 
                 String producto = urlVC.getValor("PRODUCTO");
                 String descProducto=null;
                 
                 if(producto.equalsIgnoreCase("0")){
                	 descProducto = "Sin cuotas";
                 }else if(producto.equalsIgnoreCase("1")){
                	 descProducto = "Cuotas normales";
                 }else if(producto.equalsIgnoreCase("3")){
                	 descProducto = "C3C o C2C";
                 }else if(producto.equalsIgnoreCase("4")){
                	 descProducto = "CIC o N-cuotas";
                 }else{
                	 descProducto = "";
                 }
                 
                 this.data.setValue("IdPlanCuotas", urlVC.getValor("PRODUCTO"));
                 Base.logger.info("IdPlanCuotas: "+this.data.getStringValue("IdPlanCuotas"));
                 
                 this.data.setValue("CantidadCuotas", urlVC.getValor("NUMCUO"));
                 Base.logger.info("CantidadCuotas: "+this.data.getStringValue("CantidadCuotas"));
                 
                 this.data.setValue("FechaAutorizacion", urlVC.getValor("FECTBK"));
                 Base.logger.info("FechaAutorizacion: "+this.data.getStringValue("FechaAutorizacion"));
                 
                 this.data.setValue("HoraAutorizacion", urlVC.getValor("HORTBK"));
                 Base.logger.info("FechaAutorizacion: "+this.data.getStringValue("FechaAutorizacion"));
                 
                 this.data.setValue("CodigoAutorizacion", urlVC.getValor("AUTCOD").replaceAll("%20",""));
                 Base.logger.info("CodigoAutorizacion: "+this.data.getStringValue("CodigoAutorizacion"));
                 
                 this.data.setValue("NumeroUnico", urlVC.getValor("NUMUNI"));
                 Base.logger.info("NumeroUnico: "+this.data.getStringValue("NumeroUnico"));
                 
                 //FECHA CONTABLE
                 this.data.setValue("FechaRendicion", urlVC.getValor("FECHA"));
                 Base.logger.info("FechaRendicion: "+this.data.getStringValue("FechaRendicion"));
                 
                 if( this.data.getStringValue("TipoTarjeta").equals("CREDITO") ){
                     this.data.setValue("TipoTotal", "TC" );
                 } else if( this.data.getStringValue("TipoTarjeta").equals("DEBITO") ){
                     this.data.setValue("TipoTotal", "TD" );
                 } else { //Multitiendas
                     this.data.setValue("TipoTotal", "TR" );
                 }
                    
                 Base.logger.info("TIPO TOTAL: "+this.data.getStringValue("TipoTotal"));
                 
                 String []emisores = Base.getList( Base.getEmisoresMulti(), ",", 1);
                 int index = 0 ;
                 for(int i = 0 ; i < emisores.length ; i++){
                     if(this.data.getStringValue("TipoTarjeta").contains(emisores[i])){
                         index = i;
                         break;
                     }
                 }
                 
                 this.data.setValue("Emisor", Base.getList( Base.getEmisores(), ",", 0)[index] );
                 Base.logger.info("Emisor Tarjeta: "+this.data.getStringValue("Emisor"));
                 
                 this.data.setValue("idTrxTbk", urlVC.getValor("NUMUNI"));

                 Date efchaTbk = formatFecha(urlVC.getValor("FECTBK"));

                 this.data.setValue("fechaContable", formatDateAsString(efchaTbk));
                 this.data.setValue("productoTarjeta",descProducto);
                 this.data.setValue("tbkContingencia", "o");

                 this.setNombre("TarjetaTbkDirecto");
                 this.datos = this.data;
                 this.datos.show("Tarjeta");
                 
                 //id para reimpresion
                 vista.getOperTRV().getDatos().setValue("idTrxTbk", urlVC.getValor("NUMUNI"));
                 
                 vista.getOperTRV().getCarroMediosPago().addMedioPago(this);
                 
                } catch(Exception e) {
                	vista.hideBusyWindow();
                	JOptionPane.showMessageDialog(null, "Error al realizar pago Transbank, reintentar...", "Error", JOptionPane.INFORMATION_MESSAGE);
                	Base.logger.error("No se pudo realizar la transaccion del pinpad, motivo : " 
                			+ e.getMessage());
                	return 13;
                }
                 
                 return ICajaView._NOWAITFORACTION;
                 
            case 1010:  ////// NUEVO PARA IMPRIMIR PDF
               estado = 3;
                if(isPdf) {
                	LineaVoucher []aux = null;
            		Set<String> ruts = new HashSet<String>();
            		ArrayList rut = new ArrayList();
            		for(int i = 0; i < vista.getOperTRV().getCarroCompras().getDocumentos().size(); i++){
            			aux = vista.getOperTRV().getCarroCompras().getDocument(i).getCustomerVoucher();
            			
            			for(int j = 0; j < aux.length; j++){
            				if (aux[j].getLinea().contains("RUT:")) {
            					ruts.add(aux[j].getLinea().replace("RUT:", "").trim());
            				}
            			}
            		}
            		
            		String[] listaRuts = new String[0];
            		Base.logger.info("DOCUMETO1: "+ruts.toString());
            	/*	
            		for(int k = 0; k < ruts.size(); k++){
            			listaRuts[k]=ruts[0].toString();
            		}
            		*/
            		//String[] listaRuts = ruts.toArray(new String[ruts.size()]);
            		
            		
            		
            		
            		
                	//Base.logger.info("DOCUMETO: "+listaRuts.);
                	
            		AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
            		try {
            			email = pr.obtenerEmailsPorRut(ruts.toArray(new String[ruts.size()]));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
            		

                	Base.logger.info("EMAILS: "+email);
                	

                vista.setEntryMessage( "Email(s) para el envio de Boleta", true );
    			vista.setEntryTextLabel("Email(s):", true);
                vista.setEntryText(email, true, false, false, null, null);
                
                return ICajaView._WAITFORACTION;
                
                }
                
                
                
               
//           case 1011:            
//           	 // Se agrega confirmacion para generar Notificacion de Pago...
//           	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
//           		vista.showBusyWindow("Enviando", "Espere por favor...");
//                   vista.getOperTRV().confirmar(vista);
//                   vista.hideBusyWindow();
//                   
//           	}
//           	vista.removeTRV(); 
//               return 14;

            	
//            case 100:
//                estado = 101;
//                DefMedioPago def = Base.getDefMedioPago("Tarjeta");
//                if(def == null){
//                    Base.logger.error("Medio de pago no encontrado");
//                    data.setValue("rc", 1);
//                    return 13;
//                }
//                this.data = Base.mPago.getDatos();
//                Base.logger.info(Base.mPago.getDatos().getStringValue("NumeroUnico").trim());
//                
//                
//                if( Base.mtc.devolucion(Base.mPago.getDatos().getStringValue("NumeroUnico").trim()) ){
//                    
//                    voucher = Base.mtc.getVoucherComercio();
//                    voucherComercio = new LineaVoucher[voucher.length];
//                    for(int i = 0 ; i < voucher.length ; i++){
//                        vL = new LineaVoucher();
//                        vL.setLinea(voucher[i]);
//                        vL.setSmall(true);
//                        voucherComercio[i] = vL;
//                    }
//                    
//                    voucher = Base.mtc.getVoucherCliente();
//                    voucherCliente = new LineaVoucher[voucher.length];
//                    for(int i = 0 ; i < voucher.length ; i++){
//                        vL = new LineaVoucher();
//                        vL.setLinea(voucher[i]);
//                        vL.setSmall(true);
//                        voucherCliente[i] = vL;
//                    }
//                    
//                    //Duplicado Comercio
//                    voucher = Base.mtc.getVoucherComercioDuplicado();
//                    voucherComercioDuplicado = new LineaVoucher[voucher.length];
//                    for(int i = 0 ; i < voucher.length ; i++){
//                        vL = new LineaVoucher();
//                        vL.setLinea(voucher[i]);
//                        vL.setSmall(true);
//                        voucherComercioDuplicado[i] = vL;
//                    }
//                    
//                    //Duplicado Cliente
//                    voucher = Base.mtc.getVoucherClienteDuplicado();
//                    voucherClienteDuplicado = new LineaVoucher[voucher.length];
//                    for(int i = 0 ; i < voucher.length ; i++){
//                        vL = new LineaVoucher();
//                        vL.setLinea(voucher[i]);
//                        vL.setSmall(true);
//                        voucherClienteDuplicado[i] = vL;
//                    }
//                    
//                    dataMulticard = Base.mtc.getDatosTrx();
//                    
//                    // TODO: se debe confirmar la Trx una vez que se imprima el primer Voucher TBK
//                    //Base.mtc.confirmarTrx();
//                    
//                    HashMap<String, String> hash = new HashMap<String,String>();
//                    for( int i = 0; i < dataMulticard.length; i++ ){
//                        hash.put(dataMulticard[i][0], dataMulticard[i][1]);
//                    }
//                    this.data.setValue("FechaAutorizacionOriginal", this.data.getStringValue("FechaAutorizacion"));
//                    this.data.setValue("HoraAutorizacionOriginal", this.data.getStringValue("HoraAutorizacion"));
//                    this.data.setValue("TipoTarjeta", hash.get("TIPO TARJETA"));
//                    Base.logger.info("TipoTarjeta: "+this.data.getStringValue("TipoTarjeta"));
//                    
//                    this.data.setValue("NumeroTarjeta", hash.get("NUMERO TARJETA"));
//                    Base.logger.info("NumeroTarjeta: "+this.data.getStringValue("NumeroTarjeta"));
//                    
//                    this.data.setValue("TitularTarjeta", hash.get("TITULAR TARJETA"));
//                    Base.logger.info("TitularTarjeta: "+this.data.getStringValue("TitularTarjeta"));
//                    
//                    this.data.setValue("IdPlanCuotas", hash.get("PLAN DE CUOTAS"));
//                    Base.logger.info("IdPlanCuotas: "+this.data.getStringValue("IdPlanCuotas"));
//                    
//                    this.data.setValue("CantidadCuotas", hash.get("CANTIDAD DE CUOTAS"));
//                    Base.logger.info("CantidadCuotas: "+this.data.getStringValue("CantidadCuotas"));
//                    
//                    this.data.setValue("FechaAutorizacion", hash.get("FECHA DE AUTORIZACION"));
//                    Base.logger.info("FechaAutorizacion: "+this.data.getStringValue("FechaAutorizacion"));
//                    
//                    this.data.setValue("HoraAutorizacion", hash.get("HORA DE AUTORIZACION"));
//                    Base.logger.info("HoraAutorizacion: "+this.data.getStringValue("HoraAutorizacion"));
//                    
//                    this.data.setValue("CodigoAutorizacion", hash.get("CODIGO DE AUTORIZACION"));
//                    Base.logger.info("CodigoAutorizacion: "+this.data.getStringValue("CodigoAutorizacion"));
//                    
//                    this.data.setValue("NumeroUnico", hash.get("NUMERO UNICO"));
//                    Base.logger.info("NumeroUnico: "+this.data.getStringValue("NumeroUnico"));
//                    
//                    this.data.setValue("FechaRendicion", hash.get("FECHA CONTABLE"));
//                    Base.logger.info("FechaRendicion: "+this.data.getStringValue("FechaRendicion"));
//                    
//                    data.setValue("rc", 0);
//                    Base.mPago.setDatos(this.data);
//                }
//                else{
//                    Base.logger.error("No se pudo realizar la devolución");
//                    JOptionPane.showMessageDialog(null, "Error en la devolución Multi-Concentrador", "Error", JOptionPane.INFORMATION_MESSAGE);
//                    data.setValue("rc", 1);
//                    Base.mPago.setDatos(this.data);
//                    return 13;
//                }
//                this.setNombre("TarjetaTbkDirecto");
                
	//            case 101:
	//                vista.hideAllEntries();
	//                vista.showBusyWindow("Enviando", "Espere por favor...");
	//                ArrayList<LineaVoucher> voucherDevolucion = new ArrayList<LineaVoucher>();
	//                
	//                //Preparamos los vouchers de duplicado por si falla la impresión
	//                //Cliente
	//                voucherDevolucion = new ArrayList<LineaVoucher>();
	//                for(int i = 0 ; i < voucherClienteDuplicado.length ; i++){
	//                	Base.logger.info("Voucher Devolucion Cliente reImpresion: ["+voucherClienteDuplicado[i]+"]");
	//                    voucherDevolucion.add(voucherClienteDuplicado[i]);
	//                }
	//                Base.logger.info("Generando boletaCliente desde Tarjeta....");
	//                Voucher.escribeBoleta(voucherDevolucion,"boletaCliente.dat");
	//                
	//                //Comercio
	//                /**
	//                voucherDevolucion = new ArrayList<LineaVoucher>();
	//                for(int i = 0 ; i < voucherComercioDuplicado.length ; i++){
	//                    voucherDevolucion.add(voucherComercioDuplicado[i]);
	//                }
	//                Voucher.escribeBoleta(voucherDevolucion,"boletaLocal.dat");
	//                */
	//                
	//                voucherDevolucion = new ArrayList<LineaVoucher>();
	//                for(int i = 0 ; i < voucherClienteReimpresion.length ; i++){
	//                	Base.logger.info("Voucher Devolucion Local reImpresion: ["+voucherClienteReimpresion[i]+"]");
	//                    voucherDevolucion.add(voucherClienteReimpresion[i]);
	//                }
	//                
	//                Base.logger.info("Generando boletaLocal desde Tarjeta....");
	//                Voucher.escribeBoleta(voucherDevolucion,"boletaLocal.dat");
	//                
	//                
	//                //Armamos los vouchers para la impresión
	//                voucherDevolucion = new ArrayList<LineaVoucher>();
	//                for(int i = 0 ; i < voucherCliente.length ; i++){
	//                    voucherDevolucion.add(voucherCliente[i]);
	//                }
	//                
	//                ParamSet pSet2 = Base.getParamSet("posDat");
	//
	//        		DatosFileNet datosFileNet = new DatosFileNet();
	//        		datosFileNet.setCodigo_sesion(pSet2.getStringValue("SessionId"));
	//        		datosFileNet.setCodusuario_envia(pSet2.getStringValue("CodigoRecaudador"));
	//        		datosFileNet.setEmail_para(vista.getEntryText());
	//        		datosFileNet.setNumOperacion(String.valueOf(posDat.getStringValue("NumeroOperacion")));
	//        		datosFileNet.setPropietario(pSet2.getStringValue("Usuario"));
	//        		datosFileNet.setTipo_operacion(Voucher.TIPO_OPERACION_PAGO);
	//        		
	//                Voucher.printVoucher(voucherDevolucion, true, Voucher.COPIA_CLIENTE,datosFileNet);                
	//                Base.logger.info("--------------  ---------------- datos del emmail: "+vista.getDatos().getStringValue("emails"));
	//                voucherDevolucion = new ArrayList<LineaVoucher>();
	//                for(int i = 0 ; i < voucherComercio.length ; i++){
	//                    voucherDevolucion.add(voucherComercio[i]);
	//                }
	//                Voucher.printVoucher(voucherDevolucion, false, Voucher.COPIA_LOCAL,datosFileNet);
	//                Base.logger.info("--------------  ---------------- datos del emmail: "+vista.getDatos().getStringValue("emails"));
	//                vista.hideBusyWindow();
	//                return 13; 
        }
        return 0;
    }
    
	public static String ventaTarjeta(boolean ult4digflag, String ing4UD,String ingMonto,
			  String ingTipoTarjeta,String ingTipoMoneda, String resp){
			try{
			
			TbkMetodos metodos = new TbkMetodos();
			String respuestaVenta = null;
			
			// Requerimiento entrada para lectura de tarjeta
			if(ult4digflag){
				resp += "TIPTAR=" + ingTipoTarjeta +
						 "&CUATROULTDIGITOS=" + ing4UD +
						 "&MONEDA=" + ingTipoMoneda;
			}else{
				resp += "TIPTAR=" + ingTipoTarjeta +
						 "&MONEDA=" + ingTipoMoneda;
			}
			
			Base.logger.info("url : " + resp);
			
			if (ingTipoTarjeta.equalsIgnoreCase("DB")){
				Base.logger.info("P I N P A D - venta debito");
				respuestaVenta = metodos.ventaDebito(Base.tbk, resp);
				Base.logger.info("Entro en DB");
			}else{
				if (ingTipoTarjeta.equalsIgnoreCase("CR") || ingTipoTarjeta.equalsIgnoreCase("NB")){
					Base.logger.info("Entro en CR o NB");
					Base.logger.info("P I N P A D - venta credito");
					respuestaVenta = metodos.ventaCreditoNC(Base.tbk, resp);
				}
			}
			URLString url = new URLString(respuestaVenta);
			
			Base.logger.info("url : " + url.getValor("TXCGLO"));
			
			return  respuestaVenta;
			
			}catch(Exception ex){
			System.out.println("error : " + ex.getMessage());
			return "Error=" + ex.getMessage();
			}
	}
    
	public static String leerTarjeta(String ingMonto,
	 		String ingTipoTarjeta,String ingTipoMoneda, String ingNroBoleta){

		try{
			
		//Base.tbk.setNroBoleta(ingNroBoleta);
		TbkMetodos metodos = new TbkMetodos();
		
		// Requerimiento entrada para lectura de tarjeta
		StringBuilder sbRequerimiento = new StringBuilder();
		sbRequerimiento.append("MONTO=" + ingMonto);
		sbRequerimiento.append("&TIPTAR=" + ingTipoTarjeta);
		sbRequerimiento.append("&BOLETA=" + ingNroBoleta); //esta linea reemplaza a la comentada arriba
		sbRequerimiento.append("&MONEDA=" + ingTipoMoneda);
		
		Base.logger.info("P I N P A D - leer tarjeta");
		String respuestaVenta = metodos.leerTarjeta(Base.tbk, sbRequerimiento.toString());

		System.out.println("url : " + respuestaVenta);
		
		return  respuestaVenta;
		
		}catch(Exception ex){
			//System.out.println("error leerTarjeta : " + ex.getMessage());
			
			Base.logger.error("error leerTarjeta : ", ex);
			System.out.println("error leerTarjeta : " + ex.getMessage());
			return null;
		}
	}
    
    public String[] obtenerTipoTotalTarjeta (String nombreTarjeta) {
    	
    	Boolean obtuvoTipoTotal = false;
    	String[] tipoTotal = new String[2];
//    	List tarjetas = new ArrayList();
//    	tarjetas = TipoTarjetaEnum.getEnumList();
    	String valor = TipoTarjetaEnum.getEnum(nombreTarjeta).getId();
    	Base.logger.info("obtenerTipoTotalTarjeta : " + nombreTarjeta.toUpperCase());

    	//Tipo total es Credito
    	String tarjetasCreditoCod[] = Base.getList( Base.getEmisoresCredito(), ",", 0);
    	String tarjetasCreditoDes[] = Base.getList( Base.getEmisoresCredito(), ",", 1);
    	
    	for(int i=0; (i<tarjetasCreditoDes.length) && (!obtuvoTipoTotal);i++) {
//    		Base.logger.info("TARJETA !! : " + this.getDatos().
//    				getStringValue("TipoTarjeta").toUpperCase());
//    		Base.logger.info("comparando con : " + tarjetasCreditoDes[i].toUpperCase());
    		if(tarjetasCreditoDes[i].toUpperCase().equalsIgnoreCase(valor.toUpperCase())) {
    			tipoTotal[0] = tarjetasCreditoCod[i];
    			tipoTotal[1] = "mpCredito";
    			obtuvoTipoTotal = true;
    		}
    	}
    	//Tipo total es Casa comercial
    	String tarjetasCCCod[] = Base.getList( Base.getEmisoresCasaComercial(), ",", 0);
    	String tarjetasCCDes[] = Base.getList( Base.getEmisoresCasaComercial(), ",", 1);
    	
    	for(int i = 0; (i<tarjetasCCDes.length) && (!obtuvoTipoTotal);i++) {
//    		Base.logger.info("TARJETA !! : " + this.getDatos().
//    				getStringValue("TipoTarjeta").toUpperCase());
//    		Base.logger.info("comparando con : " + tarjetasCCDes[i].toUpperCase());
    		if(tarjetasCCDes[i].toUpperCase().equalsIgnoreCase(valor.toUpperCase())) {
    			tipoTotal[0] = tarjetasCCCod[i];
    			tipoTotal[1] = "mpMultitienda";
    			obtuvoTipoTotal = true;
    		}
    	}
    	
    	//Tipo total es Debito
    	String tarjetasDebitoCod[] = Base.getList( Base.getEmisoresDebito(), ",", 0);
    	String tarjetasDebitoDes[] = Base.getList( Base.getEmisoresDebito(), ",", 1);
    	
    	for(int i=0; (i<tarjetasDebitoDes.length) && (!obtuvoTipoTotal);i++) {
    		if(tarjetasDebitoDes[i].toUpperCase().equalsIgnoreCase(valor.toUpperCase())) {
    			tipoTotal[0] = tarjetasDebitoCod[i];
    			tipoTotal[1] = "mpDebito";
    			obtuvoTipoTotal = true;
    		}
    	}
    	
    	Base.logger.info("Tipo Total Encontrado : " + tipoTotal[0] + " - " + tipoTotal[1]);
    	
    	return tipoTotal;
    	
    }
    
    public void llenarMPClaro(cl.clarochile.osbservicios.PlataformaPagoNotificar.MedioPago mp){
    	Base.logger.info("Monto Tarjeta: "+this.getDatos().getLongValue("Monto"));
    	mp.setMonto(this.getDatos().getLongValue("Monto"));
    	//mp.setTipoTransaccion(this.getDatos().getStringValue("TipoTotal"));
    	Base.logger.info("Tipo Tarjeta: "+this.getDatos().getStringValue("TipoTarjeta"));
    	if(this.getDatos().getStringValue("TipoTarjeta").equals("DEBITO")){
    		mp.setTipoTransaccion("mpDebito");
    		mp.setTipoTotal("3");
    	// TODO por cambio de data en Tipo de Tarjeta, se debe validar solo Debito !!!
        // Cambio realizado por CyC y solicitado por Claro.. Pendiente !!!!
//    	}else if(this.getDatos().getStringValue("TipoTarjeta").equals("CREDITO")){
//    		mp.setTipoTransaccion("mpCredito");
//    		mp.setTipoTotal("2");
    	}else{
    		String[] tipoTotalTarjeta = obtenerTipoTotalTarjeta(this.getDatos().getStringValue("NombreTarjeta"));
			if(tipoTotalTarjeta != null && tipoTotalTarjeta[0] != null) {
			mp.setTipoTransaccion(tipoTotalTarjeta[1]);
			mp.setTipoTotal(tipoTotalTarjeta[0]);
			} else {
			mp.setTipoTransaccion("mpMultitienda");
			mp.setTipoTotal("4");
			}
    	}
    	
    	Base.logger.info("Tipo Trx Tarjeta: "+mp.getTipoTransaccion());
    	Base.logger.info("Tipo Total Tarjeta: "+mp.getTipoTotal());
    	mp.setFechaVencimiento(this.getDatos().getStringValue("fechaContable"));
    	mp.setCodigoAutorizacion(this.getDatos().getStringValue("CodigoAutorizacion").replaceAll("%20",""));
    	Base.logger.info("Cod. Autorizacion Tarjeta: "+this.getDatos().getStringValue("CodigoAutorizacion"));
    	mp.setNumeroTarjeta(this.getDatos().getStringValue("NumeroTarjeta"));
    	Base.logger.info("Nro Tarjeta: "+this.getDatos().getStringValue("NumeroTarjeta"));
    	
    	Base.logger.info("Nro Cuotas Tarjeta: "+this.getDatos().getStringValue("CantidadCuotas"));
    	if(this.getDatos().getStringValue("CantidadCuotas") != null && 
    	   !"".equalsIgnoreCase(this.getDatos().getStringValue("CantidadCuotas"))){
    		mp.setCantidadCuotas(Integer.parseInt(this.getDatos().getStringValue("CantidadCuotas")));
    	}
    	
    	mp.setIdTrxTbk(this.getDatos().getStringValue("idTrxTbk"));
    	mp.setFechaContable(this.getDatos().getStringValue("fechaContable"));
    	mp.setProductoTarjeta(this.getDatos().getStringValue("productoTarjeta"));
    	mp.setTbkContingencia(this.getDatos().getStringValue("tbkContingencia"));
    	 	
    	Base.logger.info("Nro Cuotas Tarjeta mp: "+mp.getCantidadCuotas());
    	
    	Base.logger.info("Nro Unico mp: "+mp.getIdTrxTbk());
    	Base.logger.info("Fecha Contable mp: "+mp.getFechaContable());
    	Base.logger.info("Producto Tarjeta mp: "+mp.getProductoTarjeta());
    	Base.logger.info("Contingencia mp: "+mp.getTbkContingencia());
    	
    }
    

    // TODO validar informacion que se debe cargar para Tarjetas TBk.
    public void vaciarMPClaro(MedioPagoDTO mp){
    	this.getDatos().setValue("Monto",mp.getMontoPagado());
    	this.getDatos().setValue("CodAutorizacion",mp.getCodigoAutorizacion());
    	this.getDatos().setValue("NumeroCuotas", mp.getCantidadCuotas());
    	this.getDatos().setValue("NumeroUnico", mp.getNumeroTarjeta());
    	this.getDatos().setValue("Emisor","1000");
    	
    	if(mp.getTipoTransaccion().equals("mpCredito")){
    		this.datos.setValue("isCredito", "1" );
    		this.getDatos().setValue("TipoTarjeta", "CREDITO");
    	}
    	else if(mp.getTipoTransaccion().equals("mpDebito")){
    		this.datos.setValue("isCredito", "0" );
    		this.getDatos().setValue("TipoTarjeta", "DEBITO");
    	}else{
    		this.getDatos().setValue("TipoTarjeta", "CREDITO");
    	}
    	
    	this.getDatos().setValue("TipoTotal",mp.getTipoTransaccion());
    	
    	this.getDatos().setValue("idTrxTbk",mp.getIdTrxTbk());
    	this.getDatos().setValue("fechaContable",mp.getFechaContable());
    	this.getDatos().setValue("productoTarjeta",mp.getProductoTarjeta());
    	this.getDatos().setValue("tbkContingencia",mp.getTbkContingencia());
    	
    	
    }
    
    public void reversar(boolean llamadaExt) throws MedioPagoException {
        throw new MedioPagoException("No se pueden eliminar Tarjetas");
    }
    
    public boolean isIngresable(ICajaView vista){
      //  boolean total = isTotal(vista);
        if(Base.getEdicion()){
            return false;
        }
        /*if(total){
            if( vista.getOperTRV().getCarroMediosPago().getMediosPago().size() > 0 )
                return false;
        }*/
        return true;
    }
    
    
    public void generaComprobante (LineaVoucher []aux){
    	
    	comprobante = new ArrayList<LineaVoucher>();
		for(int j = 0; j < aux.length; j++){
			comprobante.add(aux[j]);
		}
    	
    	
    }

    public static Date formatFecha(String strFecha) {
               Date fecha = null;
               try {
            	   Base.logger.info("Entrada " + strFecha);
                       SimpleDateFormat formato = new SimpleDateFormat("yyMMdd");
                       
                       fecha = formato.parse(strFecha);

               } catch (Exception ex) {
                      
                    ex.printStackTrace(); 

               }
               Base.logger.info("Salida " + fecha);
               return fecha;
       }

	public static String formatDateAsString(Date date) {
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    Base.logger.info("Salida 2 " + formato.format(date));
	    return formato.format(date);
	}
    
}
