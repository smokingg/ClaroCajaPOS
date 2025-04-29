package cl.hyh.redpagos.caja.trx;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ws.claro.cl.AppControlCajaWSServerProxy;
import ws.claro.cl.ConsultarDevolucionInDTO;
import ws.claro.cl.ConsultarDevolucionOutDTO;
import ws.claro.cl.DesconectarCajaOutDTO;
import ws.claro.cl.DevolucionDTO;
import ws.claro.cl.HeaderDTO;
import ws.claro.cl.proxy.AppControlProxy;

import cl.hyh.cajas.ws.impl.ConsultaCuentasVtrIn;
import cl.hyh.cajas.ws.impl.ConsultaCuentasVtrOut;
import cl.hyh.cajas.ws.impl.ConsultaSaldoFavorVtrIn;
import cl.hyh.cajas.ws.impl.ConsultaSaldoFavorVtrOut;
import cl.hyh.cajas.ws.impl.CuentaVTR;
import cl.hyh.cajas.ws.impl.EnvioReversaIn;
import cl.hyh.cajas.ws.impl.HeaderIn;
import cl.hyh.cajas.ws.impl.LoginIn;
import cl.hyh.cajas.ws.impl.SaldoFavorVtr;
import cl.hyh.cajas.ws.impl.ServerProxy;
import cl.hyh.cajas.ws.proxy.Proxy;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.BaseException;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryDocumentoPago;
import cl.hyh.redpagos.caja.base.FactoryMedioPago;
import cl.hyh.redpagos.caja.base.FactoryServicio;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Servicio;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.redpagos.caja.base.parser.DefMedioPago;
import cl.hyh.redpagos.caja.base.parser.DefServicio;
import cl.hyh.redpagos.caja.docpago.DocumentoDevolucionClaro;
import cl.hyh.redpagos.caja.docpago.DocumentoDevolucionVtr;

/**
 * Implementacion de la transaccion para devoluciones de Claro
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class TrxDevolucion implements ITrxBase{
    int estado = 0;
    String codigo = "";
    Datos data;
    DocumentoPago doc;
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
    String rut = "";
    ConsultaSaldoFavorVtrOut out = null;
    CuentaVTR cuenta = null;
    long monto;
    
    ConsultarDevolucionOutDTO outDev = null;
    DevolucionDTO cuentaDev = null;
    
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
                vista.setEntryMessage( "Ingrese Rut (Formato: 99999999-X)", true );
                vista.setEntryTextLabel("Ingrese Rut:", true);
                vista.setEntryText("", true, false, false,null,null);
                return ICajaView._WAITFORACTION;
                
            case 1:
                String codigo = vista.getEntryText();
                
                if("".trim().equalsIgnoreCase(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "No ha ingresado ningun Rut - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                if(!Tools.validarRut(codigo)){
                    estado = 1;
                    vista.setEntryMessage( "RUT  Inválido - Ingrese nuevamente", true );
                    vista.setEntryTextLabel("Ingrese Rut:", true);
                    vista.setEntryText("", true, false, false,null,null);
                    return ICajaView._WAITFORACTION;
                }
                
                //rut = codigo;
                // Se elimina el DV Ingresado por el Usuario !!
                rut = codigo.substring(0, codigo.length()-2);
                
                
                //////////////////////////////////////////////
                AppControlCajaWSServerProxy pr = AppControlProxy.getProxyInstance();
                
            	//HeaderIn hIn = new HeaderIn();
                ConsultarDevolucionInDTO hIn = new ConsultarDevolucionInDTO();
            	
            	ParamSet pSet = Base.getParamSet("posDat");
            	Base.logger.info( "Inicializando desconeccion de Caja....");
            	
        		hIn.setAgencia((pSet.getStringValue("Agencia")));
        		Base.logger.info( "Agencia: " +  hIn.getAgencia());
        	 	hIn.setCajaFisica((pSet.getStringValue("Caja")));
        		Base.logger.info( "Caja Fisica: " +  hIn.getCajaFisica());
        		hIn.setEntidad((pSet.getStringValue("Entidad")));
        		Base.logger.info( "Entidad: " +  hIn.getEntidad());
        		hIn.setCajero((pSet.getStringValue("Cajero")));
        		Base.logger.info( "Cajero: " +  hIn.getCajero());
        		hIn.setSession((pSet.getStringValue("SessionId")));
        		Base.logger.info( "SessionId: " +  hIn.getSession());
        		//hIn.setUsuario(pSet.getStringValue("CodigoRecaudador"));
        		hIn.setUsuario(pSet.getStringValue("Cajero"));
        		Base.logger.info( "Usuario: " +  hIn.getUsuario());
        		hIn.setRecaudador(pSet.getStringValue("CodigoRecaudador"));
        		Base.logger.info( "Recaudador: " +  hIn.getRecaudador());
        		Base.logger.info( "Usuario Login: " + pSet.getStringValue("Usuario") );
        		
        		/////////////////////////////////////
               // ServerProxy pr = Proxy.getProxyInstance();
        		//HeaderDTO hIn = new HeaderDTO();
        		
        		/**
                ParamSet pList = Base.getParamSet( "posDat" );
            	
            	hIn.setAgencia(Integer.parseInt(pList.getStringValue("Agencia")));
        		hIn.setCajaFisica(Integer.parseInt(pList.getStringValue("Caja")));
        		hIn.setEntidad(Integer.parseInt(pList.getStringValue("Entidad")));
        		hIn.setCajero(Integer.parseInt(pList.getStringValue("Cajero")));
        		hIn.setSession(Integer.parseInt(pList.getStringValue("SessionId")));
        		hIn.setUsuario(pList.getStringValue("Usuario"));
        		hIn.setRecaudador(pList.getStringValue("CodigoRecaudador"));
        		
                
                ConsultaSaldoFavorVtrIn in = new ConsultaSaldoFavorVtrIn();
            	*/
        		
                // in.setHeaderIn(hIn);
        		// in.setRut(rut); 
        		
        		hIn.setRut(rut);
        		Base.logger.info( "Rut Busqueda: " + hIn.getRut());
        		hIn.setDv( codigo.substring(codigo.length()-1,codigo.length()));
        		Base.logger.info( "Dv Busqueda: " + hIn.getDv());
        		
            	vista.showBusyWindow("Consultando", "Espere por favor...");
            	
            	////////////////////////////////////////////7
            	
            	//Response resp = null;
        		//DesconectarCajaOutDTO resp = null;
            	//ConsultarDevolucionOutDTO resp = null;
            	
        		try { 
        			//resp = pr.disconnect(new Request(hIn));
        			outDev = pr.consultarDevolucion(hIn);
        			Base.logger.info( "Codigo resp Devolucion: " + outDev.getRetCode() );
        			Base.logger.info( "Desc resp Devolucion: " + outDev.getRetDesc());
        		} catch (RemoteException e) {
        			JOptionPane.showMessageDialog(null, outDev.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					Tools.logStackTrace(Base.logger, e);
					return 13;
        		}
        		
        		/**
				try {
					out = pr.consultaSaldoFavorVtr(in);
				} catch (RemoteException e2) {
					vista.hideBusyWindow();
					Tools.logStackTrace(Base.logger, e2);
					return 13;
				}
				*/
        		
				vista.hideBusyWindow();
				/**
				if(out.getHeaderOut().getRc() != 0){
                    JOptionPane.showMessageDialog(null, out.getHeaderOut().getRcMessage(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 13;
				}
				*/
				
				if(!"0".equalsIgnoreCase(outDev.getRetCode())){
					JOptionPane.showMessageDialog(null, outDev.getRetDesc(), "Continuar", JOptionPane.INFORMATION_MESSAGE);
					return 13;
				}
				
				estado = 2;
				return ICajaView._NOWAITFORACTION;
				
            case 2:
                estado = 3;
               // clientesCuentas = new String[out.getSaldosFavor().length + 1];
                clientesCuentas = new String[outDev.getDevolucionesList().length + 1];
                vista.hideAllEntries();
                //vista.setEntryTitle( "Devolución Saldo a Favor", true );
                vista.setEntryTitle( "Devolución Productos Claro", true );
                //clientesCuentas[0] = String.format("%-13s %-13s %-13s %-13s", "N° Cuenta","Monto","Origen","Dir.Cobranza");
                clientesCuentas[0] = String.format("%-9s %-9s %-10s %-30s %-15s", "N° Cuenta","Cod. Dev.","Monto","Concepto","Observacion");
                for(int i = 0; i < outDev.getDevolucionesList().length; i++){
                	//CuentaVTR aux = out.getSaldosFavor()[i];
                	DevolucionDTO aux = outDev.getDevolucionesList(i);
                	if (aux.getNumCuenta() == null) aux.setNumCuenta("");
                    clientesCuentas[i + 1] = String.format("%9s %9s $%10s %30s %15s",aux.getNumCuenta(),aux.getCodDevolucion(),Format.formatMontoPantalla(aux.getMonto()),aux.getConcepto(),aux.getObservaciones());
                }
                vista.setEntryList(clientesCuentas, true, false,index + 1);
                return ICajaView._WAITFORACTION;
                
            case 3:                
                doc = null;
                index = vista.getEntryListIndex();
                if(index == 0){
                    estado = 2;
                    return ICajaView._NOWAITFORACTION;
                }
                else{
                    index = index -1;
                }
                estado = 5;
                //cuenta = out.getSaldosFavor()[index];      
                cuentaDev = outDev.getDevolucionesList(index);
                
                // TODO descomentar una vez implementado el servicio!!
               // rut = out.getRutCliente() + "-" + out.getDvCliente();
                rut = outDev.getDevolucionesList(0).getRut() + "-" + outDev.getDevolucionesList(0).getDv();
                
                return ICajaView._NOWAITFORACTION;
                
            case 5:
            	estado = 6;
            	//Sacar monto de la consulta
            	try {
                    doc = FactoryDocumentoPago.makeInstance("DocumentoDevolucionClaro");
                } catch (BaseException e) {
                	Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("No se pudo instanciar el documento");
                    return 13;
                }
                ((DocumentoDevolucionClaro)doc).llenarDatos(cuentaDev);
                doc.getDatos().setValue("Rut", rut);
                monto = Long.parseLong(doc.getDatos().getStringValue("Monto"));
                return ICajaView._NOWAITFORACTION;
                
            case 6:
            	vista.hideAllEntries();
                vista.setEntryTitle( "Devolución Saldo a Favor", true );
                vista.setEntryTextArea("Monto: " + doc.getDatos().getStringValue("Monto")
                		+"\nNúmero Cuenta: " + doc.getDatos().getStringValue("NumeroCuenta")
                		//+"\nSistema Origen: " + doc.getDatos().getStringValue("SistemaOrigen")
                		//+"\nDirección Cobranza: " + doc.getDatos().getStringValue("DireccionCobranza")
                		+"\nCodigo Devolución: " + doc.getDatos().getStringValue("CodigoDevolucion")
                		+"\nConcepto: " + doc.getDatos().getStringValue("Concepto")
                        , true);
                estado = 7;
                return ICajaView._WAITFORACTION;
                
            case 7:
            	if(!doc.isIngresable(vista.getOperTRV().getCarroCompras())){
                    Base.logger.info("Documento ya existe en carro de compras");
                    JOptionPane.showMessageDialog(null, "Documento ya existe en carro de compras", "Continuar", JOptionPane.INFORMATION_MESSAGE);
                    estado = 2;
                    return ICajaView._NOWAITFORACTION;
                }
            	
                // Validar si es edicion, que el monto no sobrepase el total de carro de medios de pago..
                if(Base.getEdicion()){
	                if(doc.isEditable(vista.getOperTRV().getCarroMediosPago(), vista.getOperTRV().getCarroCompras(), doc.getDatos().getLongValue("SaldoAdeudadoClaro"))){
	                	Base.logger.info("El Documento se puede agregar al carro, el monto es menor al de los Medios de pago");
	                }else{
	                	Base.logger.info("El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago");
	                    JOptionPane.showMessageDialog(null, "El Documento No se puede agregar al carro, el monto es mayor al de los Medios de Pago", "Continuar", JOptionPane.INFORMATION_MESSAGE);
	                    estado = 2;
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
                doc.getDatos().show("Devolución Claro");
                
                //Agregamos el medio de pago negativo que netea esta devolución.
                MedioPago mPago = null;
                
                try{
                    mPago = FactoryMedioPago.makeInstance("Efectivo");
                } 
                catch(BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    return 13;
                }          
                mPago.getDatos().setValue("Monto", monto);
                
                //Metemos al carro de medios de pago el servicio
                
                try {
                    vista.getOperTRV().addMedioPago(mPago);
                } catch (BaseException e) {
                    Tools.logStackTrace(Base.logger, e);
                    Base.logger.error("Error en agregar medio de pago al carro");
                    return 13;
                }
                
                // Se agrega confirmacion para generar Notificacion de Pago...
            	if( vista.showMyConfirmDialog("Confirme por favor","Está seguro de continuar?") == JOptionPane.OK_OPTION ) {
            		
            		  vista.getOperTRV().setDevolucion(true);
                      vista.showBusyWindow("Enviando", "Espere por favor..."); 
                      vista.getOperTRV().confirmar(vista);
                      vista.hideBusyWindow();
                     
            	}
            	vista.removeTRV();
                return 13;           
        }
        return 0;
    }
}
