package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import cl.cyc.tbk.TbkMetodos;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.LineaVoucher;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.URLString;
import cl.hyh.redpagos.caja.base.Voucher;


public class ReImpresionOperacionTbk implements ITrxBase, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    int estado = 0;
    String nroOperacion="";  
    String[] opt;
	String[] optOrigen;
	LineaVoucher[] voucherComercioDuplicado = null;
	String codTbkReturn;
	ArrayList<LineaVoucher> comprobante;
    public void init(Datos data){
        
    }
    public int execute(ICajaView vista, int key, Datos htParam){
        if( key == 0 ) {            
            estado = 0;
            //vista.setInputTimeout(15);
            
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }else if( key == KeyEvent.VK_F7){
            estado = 7;
        }else if( key == KeyEvent.VK_F8){
            estado = 6;
        } 
        else if( key == KeyEvent.VK_ENTER ) {
        }
        else if( key == KeyEvent.VK_F9){
            ParamSet pList = Base.getParamSet( "posDat" );
            pList.setValue( "FechaPago", Tools.getFecha() );
            return ICajaView._PASSTHROUGH;
        } 
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
        switch( estado ) {

            case 0:
                estado = 1;
                vista.hideAllEntries();
                vista.setEntryTitle("Reimpresion Voucher por Número de Operación", true);
                vista.setEntryMessage( "Ingrese Número de Operación", true );
                vista.setEntryTextLabel("Número de Operación:", true);
                vista.setEntryText("", true, false, false,null,null);
                
                return ICajaView._WAITFORACTION; // Esta es la que corresponde !!!
                //return ICajaView._NOWAITFORACTION;
                
            case 1:
            	
                // se comenta para solo mostrar ventana de advertencia !!!
                if(vista.getEntryText().equals("")){
                    estado = 0;
                    vista.hideAllEntries();
                   
                    vista.setEntryMessage( "Ingrese Número de Operación", true );
                    vista.setEntryTextLabel("Número de Operación:", true);
                    vista.setEntryText("", true, false, false,"[0-9]+", "Id Transbank no válido");
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
               
                estado = 2;
                return ICajaView._NOWAITFORACTION;
                
            case 2:
            	
            	estado = 3;
            	
            	nroOperacion = vista.getEntryText();
            	vista.hideAllEntries();
                vista.setEntryTitle( "Seleccione Tipo de Transacción", true );                
                vista.setEntryMessage( "Tipo de Transacción", true );
                vista.setEntryTextLabel("Seleccione:", true);
                opt = new String[3];      
                opt[0] = "Débito";
                opt[1] = "Crédito";
                opt[2] = "Anulación";
                optOrigen = new String[3];      
                optOrigen[0] = "DB";
                optOrigen[1] = "CR";
                optOrigen[2] = "DEV";
               
                vista.setEntryList(opt, true, false);
            	
                return ICajaView._WAITFORACTION;
            
            case 3:
            	vista.hideAllEntries();
            	
            	int index = vista.getEntryListIndex();
                String origen = optOrigen[index];
                String[]voucher = null;
                LineaVoucher vL = null;
            	// TODO Implementacion de busqueda para Claro !!
  
            	vista.showBusyWindow("Consultando", "Espere por favor...");
            	
//            	Llamada reimpresion
           	 ParamSet posDat = Base.getParamSet("posDat");
           	 
           	 try {
           		Base.logger.info("P I N P A D - inicializa");
           		 Base.tbk.inicializa(posDat.getStringValue("TransbankConfig"));
           	 } catch (Exception e) {
           		Base.logger.info("Error inicializacion pinpad");
           	 }
           	 
      		TbkMetodos metodos = new TbkMetodos();
           	
           	String requerimiento = "IDTRXORIGINAL=00700012201410300108390000&"
       				+ "TIPTRX=TBKREM&" + "TIPTRXORIG=DB&" + "FECHA=20141030&"
       				+ "NROOPE=010839&" + "LOCAL=0070&" + "CAJA=0012";
           	Base.logger.info("P I N P A D - reimpresion");
       		 String respuestaVoucher = metodos.reimpresion(Base.tbk, requerimiento);
       		 Base.logger.info(respuestaVoucher); // Esto retorna ---> ADSRNEMO no especificado
       		 
       		 URLString urlVC = new URLString(respuestaVoucher);
       		 String vcli;

                //Obtener código retornado por Transbank
       		codTbkReturn=urlVC.getValor("AUTRET")==null?codTbkReturn=urlVC.getValor("TXCRET"):urlVC.getValor("AUTRET");
        		Base.logger.info("Codigo retornado por Transbank : " + codTbkReturn);
       		 
        		vcli = urlVC.getValor("VOUCHER");
        		if (vcli.contains("%20%20"))
        		{
        		  vcli = URLDecoder.decode(vcli);
        		}
        		voucher = vcli.split("\\n");
        		
        		 voucherComercioDuplicado = new LineaVoucher[voucher.length];
                 for(int i = 0 ; i < voucher.length ; i++){
                     vL = new LineaVoucher();
                     vL.setLinea(voucher[i]);
                     vL.setSmall(true);
                     voucherComercioDuplicado[i] = vL;
                     Base.logger.info("Linea Voucher Copia Comercio: ["+vL.getLinea()+"]");
                 }                    
                 generaComprobante(voucherComercioDuplicado);
                 Voucher.printVoucher(comprobante, false, Voucher.COPIA_TBK_LOCAL, null);
                 
                 vista.hideBusyWindow();
                 
        		// Validar si el documento a armar es anulable !!!
 
                 return 11;
          
        }
        return 0;
    }
    
    

    

		public void generaComprobante (LineaVoucher []aux){
			
			comprobante = new ArrayList<LineaVoucher>();
			for(int j = 0; j < aux.length; j++){
				comprobante.add(aux[j]);
			}
			
		}
}

