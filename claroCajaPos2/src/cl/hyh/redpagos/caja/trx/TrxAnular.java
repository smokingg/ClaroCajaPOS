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
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefServicio;

/**
 * Implementacion de la transaccion de consulta de documentos Atis 
 * por medio de codigo de barra
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TrxAnular implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    DocumentoPago doc;
    OperTRV oper = null;
    String []docs = null;
    ArrayList<Datos> clientes;
    ArrayList<Datos> detalle;
    ArrayList<Datos> detalleCut;
    ArrayList<Datos> clientesCut;
    String []clientesCuentas;
    protected Datos dataPagina;
    protected int nextPage = 0;
    protected ArrayList <Integer> paginas = new ArrayList<Integer>();
    Servicio consultaTelefonica = null;
    Servicio consultaTelefonicaDetalle = null;
    String tiposDocs = "";
    boolean offline = false;
    boolean unico = false;
    int index = 0;
    boolean isCut = false;
    Object [][]matrix = null;
    
    public void init(Datos datosVista){
        tiposDocs = "Siscli";
    }
    
    public int execute(ICajaView vista, int key, Datos datos){ 
        if( key == 0 ) {
            estado = 0;
            vista.acceptEscape(true);
            //vista.setInputTimeout(15);
        } else if( key == 1 ) {
            // Timeout
            return 13;
        }
        else if( key == KeyEvent.VK_F8 ) {
            estado = 9;
            nextPage = dataPagina.getIntValue("InicioPaginacion");
            paginas.add(new Integer(nextPage).intValue());
        }
        else if( key == KeyEvent.VK_F7 ) {
            estado = 9;
            try{
                nextPage = paginas.get(paginas.size()-2);
                paginas.remove(paginas.size()-1);
            }
            catch(Exception e){
                Tools.logStackTrace(Base.logger, e);
            }
        } 
        else if( key == KeyEvent.VK_F1 ) {
            estado = 10;
            isCut = true;
        }
        else if( key == KeyEvent.VK_F2 ) {
            estado = 20;
        }
        else if( key == KeyEvent.VK_ENTER ) {
        } 
        else if( key == KeyEvent.VK_ESCAPE ) {
            return 20;
        }
        else if( key == KeyEvent.VK_F9 && estado == 22 ) {
        	if(isCut){
        		estado = 10;
        	}
        	else{
        		estado = 9;
        	}
        }
        else if( key == KeyEvent.VK_F9 && estado == 11 ) {
        	estado = 7;
        	isCut = false;
        }
        else {
            // otra tecla. Lo que sea que esté en el XML...
            return ICajaView._PASSTHROUGH;
        }
     
        switch( estado ) {   
        
            /** TODO Implementacion Dummy para Primera fase..
	        case 0:
	            int resp = 0;
	            estado = 2;
	            return ICajaView._NOWAITFORACTION;
	        case 2:
	            estado = 3;
	            vista.hideAllEntries();
	            vista.setEntryMessage( "Ingrese N°Operación", true );
	            vista.setEntryTextLabel("Ingrese N°Operación:", true);
	            vista.setEntryText("", true, false, false,null,null);
	            
	            return ICajaView._WAITFORACTION; // Esta es la que corresponde !!!
	            //return ICajaView._NOWAITFORACTION;
	            
	        case 3:
	        	 // Se habilita msj momentaneo !!!
	            //JOptionPane.showMessageDialog(null, "No se encuentra habilitado este Módulo....", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	            //return 13;
            */
        
            case 0:
            	estado = 1;
                String []titulos = new String[3];
                titulos[0] = "Sel";
                titulos[1] = "Tipo Documento";
                titulos[2] = "Monto";
                oper = Base.getOperTRV();
                int cantidad = oper.getCarroCompras().getDocumentos().size();
                matrix = new Object[cantidad][3];
                for(int i = 0; i < oper.getCarroCompras().getDocumentos().size(); i++){     
            		matrix[i][0] = new Boolean(false);
            		matrix[i][1] = "Empresa 1";
        			//matrix[i][1] = oper.getCarroCompras().getDocument(i).getNombre();
        			matrix[i][2] = Format.formatMontoPantalla(oper.getCarroCompras().getDocument(i).getMonto());
                }
                vista.setEntryTable(titulos, matrix, true);
                return ICajaView._WAITFORACTION;
                
            case 1:
            	doc = null;
            	boolean isSel = false;
            	for(int i = 0 ; i < matrix.length ; i++){
            		if((Boolean)(matrix[i][0]) == true){
            			isSel = true;
            			break;
            		}
            	}
            	if(!isSel){
            		estado = 2;
            		return ICajaView._NOWAITFORACTION;
            	}
                vista.paintButtons(Tools.getBotones(-1));
                estado = 2;
                for(int i = 0 ; i < matrix.length ; i++){
                	if((Boolean)matrix[i][0] == false)
                		continue;
	                try {
	                    doc = FactoryDocumentoPago.makeInstance("Anulacion" + oper.getCarroCompras().getDocument(i).getNombre());
	                } catch (BaseException e) {
	                    Base.logger.error("No se pudo instanciar el documento");
	                }
	                doc.getDatos().asignaPorNombre(oper.getCarroCompras().getDocument(i).getDatos());
	                doc.getDatos().setValue("FechaEmision", doc.getDatos().getStringValue("FechaEmision"));
	                doc.getDatos().setValue("FechaVencimiento", doc.getDatos().getStringValue("FechaVencimiento"));
	                doc.getDatos().setValue("Monto", doc.getDatos().getLongValue("MontoSaldo")*-1);
	                doc.getDatos().setValue("FolioB", doc.getDatos().getStringValue("NumeroDocumento"));
	                doc.getDatos().setValue("NumDocB", doc.getDatos().getStringValue("NumFolio"));                
	                doc.getDatos().setValue("MontoDocumento",  doc.getMonto());
	                doc.getDatos().setValue("Monto",  doc.getMonto());
	                
	                if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
	                    Base.logger.info("Documento ya existe en carro de compras");
	                    JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	                    estado = 0;
	                    return ICajaView._NOWAITFORACTION;
	                }
	                
	                // Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
	                if(Base.getEdicion()){
		                if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
		                	Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
		                }else{
		                	Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
		                    JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
		                    estado = 0;
		                    return ICajaView._NOWAITFORACTION;
		                }
	                }

	                
	                vista.hideAllEntries();
	                try {
	                    vista.getOperTRV().addDocumentoPago(doc);
	                } catch (BaseException e) {
	                    Tools.logStackTrace(Base.logger, e);
	                    Base.logger.error("Error en agregar documento de pago al carro");
	                }
	                doc.getDatos().show("DocumentoAtis");
	                vista.paintButtons(Tools.getBotones(-1));
	                }
                return ICajaView._NOWAITFORACTION;
                
            case 2:
                return 20;
        
        }
        return 0;
    }
}
