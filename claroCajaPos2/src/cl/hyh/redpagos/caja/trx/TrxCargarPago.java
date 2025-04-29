package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.Documento;
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
public class TrxCargarPago implements ITrxBase{
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
            case 0:
            	estado = 0;
                String []titulos = new String[2];
                titulos[0] = "Medio de Pago";
                titulos[1] = "Monto";
                oper = Base.getOperTRV();
                int cantidad = oper.getCarroMediosPago().getMediosPago().size();
                matrix = new Object[cantidad][2];
                for(int i = 0; i < oper.getCarroMediosPago().getMediosPago().size(); i++){     
            		matrix[i][0] = oper.getCarroMediosPago().getPago(i).getNombre();
        			matrix[i][1] = Format.formatMontoPantalla(oper.getCarroMediosPago().getPago(i).getMonto());
                }
                vista.setEntryTable(titulos, matrix, true);
                vista.hideEnter();
				BeanInfo info;
				try {
					info = Introspector.getBeanInfo( Documento.class, Object.class );			
	                for ( PropertyDescriptor pd : info.getPropertyDescriptors() )
	                    Base.logger.info( pd.getName() );
				} catch (IntrospectionException e) {
					Tools.logStackTrace(Base.logger, e);
				}
            	return ICajaView._WAITFORACTION;
        }
        return 0;
    }
}
