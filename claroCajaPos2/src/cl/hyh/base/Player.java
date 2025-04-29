package cl.hyh.base;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cl.hyh.entity.Button;
import cl.hyh.entity.ReturnAction;
import cl.hyh.entity.ReturnCapture;
import cl.hyh.entity.Trx;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.interfaces.ITrxBase;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.base.Tools;
import cl.hyh.visual.MainWindow;

/**
 * @author abertens
 *
 */
public class Player extends Thread {

   private MainWindow mw = null;
   private boolean idle = true;
   private ArrayList<Trx> stack = new ArrayList<Trx>();
   private Datos htParam = new Datos();
   private boolean prePostProcess = false;
   
   public Player(MainWindow mw) {
      this.mw = mw;
   }
   
   public boolean isIdle() {
      return idle; 
   }
   
    public void run() {

        Trx trx        = null;
        ParamSet pList = Base.getParamSet( "posDat" );
        // NO TENGO USUARIO TODAVIA
        if(!pList.getStringValue("sinConexion").equals("si")){
        	
	     //   pList.setValue( "Usuario", "" );
	        pList.setValue( "Perfil", "" );
	     //   pList.setValue( "Cajero", "" );
	        pList.setValue( "FechaPago", Tools.getFecha() );
	        pList.save();
        }
        // mw.setAgencia( pList.getStringValue("Agencia") );
        ParamSet pListCfg = Base.getParamSet( "posCfg" );
        mw.setVersion( pListCfg.getStringValue("Version"), pList.getStringValue("Agencia"), 
        		pListCfg.getStringValue("Mensaje"), pListCfg.getStringValue("Proyecto") );
        Motor.logger.info( "Fecha de pago: " + pList.getStringValue("FechaPago") );
        
        while( true ) {


            if( stack.size() == 0 ) {
                // PRIMERA LLAMADA O DESPUES DE UNA CAIDA ESTREPITOSA
                // PRIMERA TRX SE OBTIENE DE LA CONFIGURACION
            	if( pList.getStringValue("configurado").equalsIgnoreCase("si") ) {
            		 if(!pList.getStringValue("sinConexion").equals("si")){
            		trx = Motor.trxList.getTrx( Motor.propCajas.getProperty("starterTrx") );
            		 }else{
            			 trx = Motor.trxList.getTrx( Motor.propCajas.getProperty("starterTrxOffLine") );
            		 }
            	} else {
            		trx = Motor.trxList.getTrx( Motor.propCajas.getProperty("configTrx") );            		
            	}
                stack.add( trx );
            }
            mw.dismissTableView();
            // CARGO LA ULTIMA DEL STACK
            trx = stack.get(stack.size()-1);
            Motor.logger.info( "Proceso trx " + trx.getName() );
            trx.setKey(0);
            htParam.setValue("trxParam", trx.getParameter() );
            // DEFAULT: NO ACEPTA ESCAPE
            mw.acceptEscape(false);
            // PINTO SUS BOTONES...
            String[] botones = new String[12];
            for( int i = 0; i < 12; ++i ) {
                Button b = trx.getButton("pf" + (i+1));
                if( b != null ) {
                    if( b.getProfile().length() > 0 ) {
                       if( pList.getStringValue("Perfil").indexOf(b.getProfile()) >= 0 ) {
                           botones[i] = b.getText();                           
                       } else {
                           // NO TIENE EL PERFIL
                           botones[i] = "";                           
                       }
                    } else {
                        botones[i] = b.getText();
                    }
                } else {
                    botones[i] = "";
                }
            }
            mw.paintButtons( botones );
            mw.showExtraButtons();
            // LIMPIO LOS CAMPOS PORQUE CAMBIO DE TRX
            mw.cleanEntryFields();
            // PONGO EL TITULO...
            mw.setTrxTitle( trx.getTitle() );
            // MUESTRO EL CARRO ADECUADO (SI CORRESPONDE)...
            mw.showShoppingCart(false);
            mw.showPaymentCart(false);                   
            if( trx.getCart().equalsIgnoreCase("shopping")) {
               mw.showShoppingCart(true);
            } else if( trx.getCart().equalsIgnoreCase("payment")) {
               mw.showPaymentCart(true);
            }
            
            // EL AREA DE DATOS DE TRASPASO ES COMUN A TODAS

            // PRE-PROCESO...
            if( ! trx.isPreProccessed() ) {
                prePostProcess = true;
                PreProcess( trx );
                // LA MARCO COMO PROCESADO SU PRE-PROCESO PARA NO LLAMARLA
                // NUEVAMENTE
                trx.setPreProccessed(true);
            }
            // PROCESO PRINCIPAL
            prePostProcess = false;
            ReturnCapture pr = MainProcess( trx );
            prePostProcess = true;
            
            if( pr.getRc() <= 0 ) {
                // SE TERMINA ESTA TRANSACCION, POST-PROCESO
                PostProcess( trx );
                // LIMPIO INSTANCIA (HINT GARBAGE COLLECTOR)
                trx.setTrxInstance(null);
                if( pr.getRc() == 0 ) {
                    // EXIT, VUELVE A LA ANTERIOR
                    if( stack.size() > 0 ) {
                        stack.remove(stack.size()-1);
                    }
                } else if( pr.getRc() == -1 ) {
                    // EXIT2, VUELVE A LA ANTERIOR DE LA ANTERIOR
                    for( int i = 0; i < 2; ++i ) {
                        if( stack.size() > 0 ) {
                            stack.remove(stack.size()-1);
                        }                    
                    }
                } else if( pr.getRc() == -2 ) {
                    // EXIT3, VUELVE A LA ANTERIOR DE LA ANTERIOR DE LA ANTERIOR
                    for( int i = 0; i < 3; ++i ) {
                        if( stack.size() > 0 ) {
                            stack.remove(stack.size()-1);
                        }                    
                    }
                } else if( pr.getRc() == -10 ) {
                	// VUELVE A UNA TRANSACCION PARTICULAR DEL STACK
            		Trx auxTrx  = null;
            		boolean fFirst = true;
            		boolean found = false;
            		String[] retTrxs = pr.getTrxName().split(",");
                	while( stack.size() > 0 && retTrxs != null ) {
                		auxTrx = stack.get(stack.size()-1);
                		for( int i = 0; !found && i < retTrxs.length; ++i ) {
	                		if( auxTrx.getName().equalsIgnoreCase(retTrxs[i]) ) {
	                			// LA ENCONTRE...
	                			found = true;
	                			break;
	                		}
                		}
                		if( found ) {
                			break;
                		}
                		if( !fFirst ) {
                			// SE TERMINA ESTA TRANSACCION, POST-PROCESO
                			PostProcess( auxTrx );
                		}
                		fFirst = false;
                		stack.remove(stack.size()-1);
                	}
                	if( stack.size() == 0 || (stack.size() > 0 &&
                			!stack.get(stack.size()-1).getName().equalsIgnoreCase(pr.getTrxName())) ) {
                        Motor.logger.warn("No encontro transaccion " + pr.getTrxName() + " en stack" );
                	}
                }
            } else {
                // rc = 1
                // REEMPLAZA O ANIDA SOBRE LA ANTERIOR
                if( pr.isReplace() ) {
                    // REEMPLAZA. SE TERMINA ESTA TRANSACCION, POST-PROCESO
                    PostProcess( trx );
                    // LIMPIO INSTANCIA (HINT GARBAGE COLLECTOR)
                    trx.setTrxInstance(null);
                    stack.set(stack.size()-1, pr.getTrx());
                 } else {
                     // ACUMULA EN STACK, VOLVERA...
                     // trxInstance SE MANTIENE
                     stack.add(pr.getTrx());
                 }
                 // PARA CADA NUEVA TRX, EJECUTO SU PREPROCESO
                 // SI VUELVE A APARECER POR STACK, NO LA VUELVO A EJECUTAR
                 pr.getTrx().setPreProccessed(false);
                 htParam.setValue( "btnParam", pr.getBtnParameter() );
                 htParam.setValue( "raParam", pr.getRaParameter() );
            }
            
            // PINTO ALGUNOS VALORES DE LA VENTANA EN CADA PASADA
            mw.setUsuario( pList.getStringValue("Usuario"), pList.getStringValue("Caja") );
            
            if( pList.getStringValue("Online").equalsIgnoreCase("no") || pList.getStringValue("sinConexion").equals("si")) {
                mw.setOnline( "Fuera de línea" );                                
            } else if( pList.getStringValue("Online").equalsIgnoreCase("si") ) {
                mw.setOnline( "En línea" , pList.getStringValue("Caja") );                                
            } else {
                mw.setOnline( pList.getStringValue("Online") );                                
            }
            
            // mw.setAgencia( pList.getStringValue("Agencia") );
            System.out.println("******  Fecha de Pago ******* "+pList.getStringValue("FechaPago"));
            if("".equalsIgnoreCase(pList.getStringValue("FechaPago")) 
               || pList.getStringValue("FechaPago") == null){
            	mw.setFechaPago(Tools.getFecha().substring(0, 10));
            }else{
            	 mw.setFechaPago(pList.getStringValue("FechaPago").substring(0, 10));
            }
            mw.setVersion( pListCfg.getStringValue("Version"), pList.getStringValue("Agencia"),
            		pListCfg.getStringValue("Mensaje"), pListCfg.getStringValue("Proyecto"));
        }            
    }            
    
    
    public void PreProcess( Trx trx ) {
        ITrxBase trxInstance = null;
        if( trx.getPreClassName().length() == 0 ) {
            return;
        }
        try {
            Motor.logger.info("Creo instancia de " + trx.getPreClassName() );
            trxInstance = (ITrxBase)Class.forName(trx.getPreClassName()).newInstance();
            Motor.logger.info("Llamo init() de " + trx.getPreClassName() );
            trxInstance.init(htParam);
        } catch (Exception e) {
            e.printStackTrace();
            Motor.logger.error("Falló creación de instancia de clase: " + e.getMessage() );
            return;
        }
        
        Capture( trx, trxInstance );
    }
    
    public void PostProcess( Trx trx ) {
        ITrxBase trxInstance = null;
        if( trx.getPostClassName().length() == 0 ) {
            return;
        }
        try {
            Motor.logger.info("Creo instancia de " + trx.getPostClassName() );
            trxInstance = (ITrxBase)Class.forName(trx.getPostClassName()).newInstance();
            Motor.logger.info("Llamo init() de " + trx.getPostClassName() );
            trxInstance.init(htParam);
        } catch (Exception e) {
            e.printStackTrace();
            Motor.logger.error("Falló creación de instancia de clase: " + e.getMessage() );
            return;
        }
        Capture( trx, trxInstance );
    }
    
    public ReturnCapture MainProcess( Trx trx ) {
        // PROCESO PRINCIPAL
        if( trx.getClassName().length() > 0 ) {
            if( trx.getTrxInstance() == null ) {
                // PRIMERA LLAMADA
                try {
                    Motor.logger.info("Creo instancia de " + trx.getClassName() );
                    trx.setTrxInstance( (ITrxBase)Class.forName(trx.getClassName()).newInstance() );
                    Motor.logger.info("Llamo init() de " + trx.getClassName() );
                    trx.getTrxInstance().init(htParam);
                } catch (Exception e) {
                    e.printStackTrace();
                    Motor.logger.error("Falló creación de instancia de clase: " + e.getMessage() );
                    // LA TIRO PARA AFUERA
                    return new ReturnCapture(0);
                }
            } else {
                // ESTA VOLVIENDO POR STACK
                trx.setKey(2); // RETORNO POR STACK 
            }
        } else {
            // NO TIENE CLASE, PROCESO POR DEFECTO CON TECLAS
        }
        return Capture( trx, trx.getTrxInstance() );
        
    }
    
    public ReturnCapture Capture( Trx trx, ITrxBase trxInstance ) {
        ReturnCapture pr = null;
        while( pr == null ) {
            if( trxInstance != null ) {
               Motor.logger.info("Invoco " + trxInstance.getClass().getName() + ".execute(vista,"+trx.getKey()+",...)" );
               // CONFIGURA ESCAPE
               mw.acceptEscape(trx.isEscapeAccepted());
               idle = false;
               int rc = 0;
               try{
                   rc = trxInstance.execute((ICajaView)mw, trx.getKey(), htParam);
               }
               catch(Exception e){
                   Tools.logStackTrace(Base.logger, e);
                   JOptionPane.showMessageDialog(null, "Error en la aplicación, se cerrará la aplicación\nError: [ " + e.getMessage() + " ]", "Error", JOptionPane.ERROR_MESSAGE);
                   System.exit(1);
               }
               idle = true;
               // CONFIGURA ESCAPE
               trx.setEscapeAccepted(mw.returnEscape());
               Motor.logger.info("Después de "+trxInstance.getClass().getName()+".execute(...)=" + rc );
               // REPINTO LOS CARROS...
               if( trx.getCart().equalsIgnoreCase("shopping")) {
                   mw.showShoppingCart(true);
               } else if( trx.getCart().equalsIgnoreCase("payment")) {
                   mw.showPaymentCart(true);
               }
               if( rc == ICajaView._WAITFORACTION ) {
                   // espero acción por parte del usuario...
                   mw.setVisibleEntryMessage(mw.isEntryMessageVisible());
                   mw.setVisibleEntryTextLabel(mw.isEntryTextLabelVisible());
                   mw.setVisibleEntryText(mw.isEntryTextVisible() && !mw.isEntryTextPassword());
                   mw.setVisibleEntryPassword(mw.isEntryTextVisible() && mw.isEntryTextPassword());
                   mw.setVisibleEntryTextArea(mw.isEntryTextAreaVisible());
                   mw.setVisibleEntryTitle(mw.isEntryTitleVisible());
                   mw.setVisibleEntryList(mw.isEntryListVisible());
                   mw.setVisibleEntryTable(mw.isEntryTableVisible());
                   mw.setVisibleEntryCalendar(mw.isEntryCalendarVisible());
                   mw.setVisibleEnter(mw.displayEnter());
                   // ME ASEGURO QUE LOS CAMPOS ESTEN VISIBLES ANTES DE PONER EL FOCO
                   mw.repaint();
                   mw.invalidate();
                   try { Thread.sleep(10); } catch( Exception e ) {}
                   // PONGO EL FOCO EN EL CAMPO ACTIVO
                   if( mw.isEntryTextVisible() ) {
                       mw.setFocusEntryText();
                   } else if( mw.isEntryListVisible() ) {
                       mw.setFocusList();
                   } else if( mw.isEntryTableVisible() ) {
                       mw.setFocusTable();
                   } else if( mw.isEntryTextAreaVisible() ) {
                       mw.setFocusEntryTextArea();                       
                   } else if( mw.isEntryCalendarVisible() ) {
                       mw.setFocusEntryCalendar();                       
                   }
                   // TODO
                   mw.repaint();
                   // TODO
                   trx.setKey( mw.execute() );
                   mw.setVisibleEnter(false);
                   mw.showEnter();
               } else if( rc == ICajaView._NOWAITFORACTION ) {
                   // Presenta los campos con texto
                   mw.setVisibleEntryMessage(mw.isEntryMessageVisible());
                   mw.setVisibleEntryTextLabel(mw.isEntryTextLabelVisible());
                   mw.setVisibleEntryText(mw.isEntryTextVisible() && !mw.isEntryTextPassword());
                   mw.setVisibleEntryPassword(mw.isEntryTextVisible() && mw.isEntryTextPassword());
                   mw.setVisibleEntryTextArea(mw.isEntryTextAreaVisible());
                   mw.setVisibleEntryTitle(mw.isEntryTitleVisible());
                   mw.setVisibleEntryList(mw.isEntryListVisible());
                   mw.setVisibleEntryTable(mw.isEntryTableVisible());
                   mw.setVisibleEntryCalendar(mw.isEntryCalendarVisible());
                   // RETORNA INMEDIATAMENTE
                   trx.setKey( KeyEvent.VK_ENTER );
               } else if( rc == ICajaView._PASSTHROUGH ) {
                   // PROCESA TECLA POR OMISION
                   pr = ProcessKey( trx, trx.getKey() );
               } else if( rc >= ICajaView._PREPOSTRETURN ) {
                   if( prePostProcess ) { // rc == ICajaView._PREPOSTRETURN ) {
                       // VIENE DE PRE/POST PROCESO
                       // NO IMPORTA EL VALOR DE RETORNO
                       break;
                   }
                   ReturnAction ra = trx.getReturn( "" + rc );
                   if( ra != null ) {
                      if( ra.getAction().equalsIgnoreCase("trx") ) {
                          Motor.logger.info("Salgo de "+trx.getName() + " con trx=" + ra.getAnexAction() );
                          pr = new ReturnCapture(1);
                          pr.setTrx( Motor.trxList.getTrx(ra.getAnexAction()) );
                          pr.setRaParameter(ra.getParameter());
                          pr.setReplace( ra.isReplace() );
                      } else if( ra.getAction().equalsIgnoreCase("stack") ) {
                          Motor.logger.info("Salgo de "+trx.getName() + " con stack=" + ra.getAnexAction() );
                          pr = new ReturnCapture(-10);
                          pr.setTrxName(ra.getAnexAction());
                      } else if( ra.getAction().equalsIgnoreCase("exit") ) {
                          Motor.logger.info("Salgo de "+trx.getName() + " con exit" );
                          pr = new ReturnCapture(0);
                      } else if( ra.getAction().equalsIgnoreCase("exit2") ) {
                          Motor.logger.info("Salgo de "+trx.getName() + " con exit2" );
                          pr = new ReturnCapture(-1);
                      } else if( ra.getAction().equalsIgnoreCase("exit3") ) {
                          Motor.logger.info("Salgo de "+trx.getName() + " con exit3" );
                          pr = new ReturnCapture(-2);
                      }  else if( ra.getAction().equalsIgnoreCase("exitOffline") ) {
                          Motor.logger.info("Salgo de "+trx.getName() + " con exitOffline" );
                          System.exit(1);
                      } 
                      
                      else {
                          Motor.logger.error("Return de "+trx.getName() + " desconocido: " + ra.getAction() );                          
                      }
                  } else {
                      // ERROR
                      Motor.logger.error("No encontré <return .../> de " + trx.getName() + " para " + rc );
                  }
               }
            } else {
               // PROCESA TECLA POR OMISION
               pr = ProcessKey( trx, -1 );
            }
            // REPINTO LOS CARROS...
            if( trx.getCart().equalsIgnoreCase("shopping")) {
                mw.showShoppingCart(true);
            } else if( trx.getCart().equalsIgnoreCase("payment")) {
                mw.showPaymentCart(true);
            }
        }
        return pr;
    }
    
    public ReturnCapture ProcessKey( Trx trx, int key ) {
        ReturnCapture pr = null;
        Button b = null;
        if( key == -1 ) {
            // TENGO QUE ESPERAR AL USUARIO POR UNA TECLA
            // PONGO EL FOCO EN EL CARRO ACTIVO SI SOLO TECLAS
            if( mw.isDocumentCartVisible() ) {
                mw.setFocusDocumentCart();
            } else if( mw.isPaymentCartVisible() ) {
                mw.setFocusPaymentCart();
            }
            // TENGO QUE CAPTURAR ALGUNA TECLA
            // TODO
            mw.repaint();
            // TODO
            key = mw.execute();
        } else {
            // VIENE DE UN PASSTHROUGH
        }
        switch( key ) {
            case KeyEvent.VK_F1:     b = trx.getButton("pf1"); break;
            case KeyEvent.VK_F2:     b = trx.getButton("pf2"); break;
            case KeyEvent.VK_F3:     b = trx.getButton("pf3"); break;
            case KeyEvent.VK_F4:     b = trx.getButton("pf4"); break;
            case KeyEvent.VK_F5:     b = trx.getButton("pf5"); break;
            case KeyEvent.VK_F6:     b = trx.getButton("pf6"); break;
            case KeyEvent.VK_F7:     b = trx.getButton("pf7"); break;
            case KeyEvent.VK_F8:     b = trx.getButton("pf8"); break;
            case KeyEvent.VK_F9:     b = trx.getButton("pf9"); break;
            case KeyEvent.VK_F10:    b = trx.getButton("pf10"); break;
            case KeyEvent.VK_F11:    b = trx.getButton("pf11"); break;
            case KeyEvent.VK_F12:    b = trx.getButton("pf12"); break;
            case KeyEvent.VK_ESCAPE: b = null; break;
            case KeyEvent.VK_ENTER:  b = null; break;
        }
        if( b == null ) {
            // ENTER
            pr = null;
        } else if( b.getAction().equalsIgnoreCase("trx") ) {
            Motor.logger.info("Salgo de "+trx.getName() + " con trx=" + b.getAnexAction() );
            pr = new ReturnCapture(1);
            pr.setTrx( Motor.trxList.getTrx(b.getAnexAction()) );
            pr.setBtnParameter(b.getParameter());
            pr.setReplace( b.isReplace() );
        } else if( b.getAction().equalsIgnoreCase("stack") ) {
            Motor.logger.info("Salgo de "+trx.getName() + " con stack="+b.getAnexAction() );
            pr = new ReturnCapture(-10);
            pr.setTrxName(b.getAnexAction());
        } else if( b.getAction().equalsIgnoreCase("exit") ) {
            Motor.logger.info("Salgo de "+trx.getName() + " con exit" );
            pr = new ReturnCapture(0);
        } else if( b.getAction().equalsIgnoreCase("exit2") ) {
            Motor.logger.info("Salgo de "+trx.getName() + " con exit2" );
            pr = new ReturnCapture(-1);
        } else if( b.getAction().equalsIgnoreCase("exit3") ) {
            Motor.logger.info("Salgo de "+trx.getName() + " con exit3" );
            pr = new ReturnCapture(-2);
        } else {
            Motor.logger.error("Botón con acción desconocida en "+trx.getName() + ": " + b.getAction() );
        }
        mw.setVisibleEnter(false);
        return pr;
    }    
}
