package cl.hyh.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cl.hyh.entity.Button;
import cl.hyh.entity.ReturnAction;
import cl.hyh.entity.Trx;
import cl.hyh.entity.TrxList;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.visual.MainWindow;
import cl.hyh.visual.SplashWindow;

/**
 * @author abertens
 *
 */
public class Motor {

   public static Logger     logger    = null;
   public static Properties propCajas = new Properties();
   private SplashWindow     sw        = null;
   private MainWindow       mw        = null;
   private Object           signal    = new Object();
   private FileOutputStream lockFile  = null;
   private FileLock         fl        = null;
   private Player           player    = null;
   
   public static TrxList trxList = new TrxList();
   
   public void Start() {

      // CARGO PROPIEDADES
      try {
         // propCajas.load( new FileInputStream("motor.properties") );
         propCajas.load( getClass().getClassLoader().getResourceAsStream("config/motor.properties") );
      } catch (Exception e2) {
         JOptionPane.showMessageDialog(null, 
               "Error al leer archivo de propiedades \'motor.properties\'", 
               "Error", 
               JOptionPane.ERROR_MESSAGE);
         System.exit(0);
      }
      
      PropertyConfigurator.configure( getClass().getClassLoader().getResource(Motor.propCajas.getProperty("log4jConfig") ));
      Motor.logger = Logger.getLogger( Motor.propCajas.getProperty("log4jLoggerClass") );
      Motor.logger.info("Parte aplicación de cajas");

      // VENTANA DE SPLASH ASINCRONICA
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                sw = new SplashWindow();
                sw.setVisible(true);
            }
        });
        try { Thread.sleep(2000);} catch (InterruptedException e1) { }

        // CONTROLO LA EXISTENCIA DE UNA UNICA INSTANCIA CON ARCHIVO DE LOCK
      String lockFileName = System.getProperty("java.io.tmpdir") + 
         "/" + 
         Motor.propCajas.getProperty("singleInstanceLockFile");
      Motor.logger.info("Archivo de lock: " + lockFileName );

      File fLock = new File(lockFileName);
      try {
         lockFile = new FileOutputStream( fLock );
         fl = lockFile.getChannel().tryLock();
      } catch (FileNotFoundException e1) {
         // ME INTERESA?
         Motor.logger.info( "lockFile: " + e1.toString() );
      } catch (IOException e2) {
          // ME INTERESA?
         Motor.logger.info( "lockFile: " + e2.toString() );
      }      
      if( fl == null ) {
         // EXISTE UNA INSTANCIA PREVIA, BYE!
           if( sw != null ) {
              sw.dispose();
              sw = null;
           }
           Motor.logger.warn("Existe instancia previa. Nos vamos..." );
         JOptionPane.showMessageDialog(null, 
               "La aplicación de Cajas yá está corriendo.\nUse Alt-Tab para acceder a ella.", 
               "Mensaje", 
               JOptionPane.INFORMATION_MESSAGE);
         System.exit(1);         
      }
      
        // SOY EL DUEGNO DEL ARCHIVO DE LOCK
        fLock.deleteOnExit();
        
        // INICIALIZO FRAMEWORK
        new Base().init();
        
        // LEO DEFINICIONES DE TRANSACCIONES
        loadTrx(Motor.propCajas.getProperty("trxFile"));
      
        // CREO LA VENTANA PRINCIPAL
        mw = new MainWindow(signal);
        mw.center();
        
        // ARRANCO EL INTERPRETE
        player = new Player( mw );
        player.start();

        // HAGO VISIBLE LA VENTANA PRICIPAL
        mw.setVisible(true);
        // CIERRO LA VENTANA DE SPLASH
        if( sw != null ) {
           sw.dispose();
           sw = null;
        }
      
        // Y QUEDO A LA ESPERA DEL CIERRE DE LA APLICACION
        synchronized( signal ) {
           while( true ) {
              try {
               signal.wait();
               if ( canStop() ) {
                  Stop();
               }
            } catch (InterruptedException e) {
               // NO ME INTERESA...
               e.printStackTrace();
            }      
           }
        }
   }
   
   public boolean canStop() {
      if( !player.isIdle() ) {
         return false;   
      }
      return true;
   }
      
   public void Stop() {
      // BORRO ARCHIVO DE LOCK
      if( lockFile != null ) {
         try { fl.release(); } catch (IOException e1) {}
         try { lockFile.close(); } catch (IOException e) {}
      }
      mw.dispose();
      Motor.logger.info("Termina aplicación de cajas");
      System.exit(0);
   }
   
   public boolean loadTrx( String xmlFile )
   {
      Document doc = null;
      ArrayList<Trx> trxs = new ArrayList<Trx>();
      
      try { 
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         doc = factory.newDocumentBuilder().parse(getClass().getClassLoader().getResourceAsStream(xmlFile));
      }
      catch( Exception e ) {
         e.printStackTrace();
         return false;
      }

      NodeList nodes;
      nodes = doc.getElementsByTagName( "trx" );
      for( int i = 0; i < nodes.getLength(); i++ ) {
         Node node = nodes.item( i );
         Trx trx = new Trx();
         trx.setName( ((Element)node).getAttribute("name") );
         trx.setClassName( ((Element)node).getAttribute("class") );
         trx.setPreClassName( ((Element)node).getAttribute("preClass") );
         trx.setPostClassName( ((Element)node).getAttribute("postClass") );
         trx.setTitle( ((Element)node).getAttribute("title") );
         trx.setParameter( ((Element)node).getAttribute("parameter") );
         NodeList childs = node.getChildNodes();
         for( int j = 0; j < childs.getLength(); j++ ) {
            Node item = childs.item(j);
            String nodeName = item.getNodeName();
            if( nodeName.equalsIgnoreCase("buttons") ) {
               loadButtonsDef( item, trx );
            } else if( nodeName.equalsIgnoreCase("return") ) {
                loadReturnDef( item, trx );
            } else if( nodeName.equalsIgnoreCase("cart") ) {
                Element el = (Element)item;
                if( el.getAttribute("name" ).equalsIgnoreCase("shopping") ) {
                    trx.setCart("shopping");
                } else if( el.getAttribute("name" ).equalsIgnoreCase("payment") ) {
                    trx.setCart("payment");
                }
            }
            if( nodeName.equalsIgnoreCase("pf1")  ||
                nodeName.equalsIgnoreCase("pf2")  ||
                nodeName.equalsIgnoreCase("pf3")  ||
                nodeName.equalsIgnoreCase("pf4")  ||
                nodeName.equalsIgnoreCase("pf5")  ||
                nodeName.equalsIgnoreCase("pf6")  ||
                nodeName.equalsIgnoreCase("pf7")  ||
                nodeName.equalsIgnoreCase("pf8")  ||
                nodeName.equalsIgnoreCase("pf9")  ||
                nodeName.equalsIgnoreCase("pf10") ||
                nodeName.equalsIgnoreCase("pf11") ||
                nodeName.equalsIgnoreCase("pf12") )  {
                loadPfDef( item, nodeName, trx );
             } 
         }
         // LA AGREGO A LA LISTA
         trxs.add(trx);
      }
      trxList.setTrxList(trxs);
      return true;
   }

   public void loadButtonsDef( Node node, Trx trx ) {
      // ALTERNATIVA 1: ATRIBUTOS DE <buttons pf1="" pf2="" ... />
      Element el = (Element)node;
      for( int i = 1; i <= 12; ++i ) {
          if( el.getAttribute("pf"+i).length() > 0 ) {
              Button pf = new Button("pf" + i);
              pf.setText( el.getAttribute("pf"+i) );
              trx.AddButton(pf);
          }
      }
      // ALTERNATIVA 2: TAGS BAJO <buttons><pf id="1"... /> </buttons>
      NodeList childs = el.getChildNodes();
      for( int j = 0; j < childs.getLength(); j++ ) {
         Node item = childs.item(j);
         String nodeName = item.getNodeName();
         if( nodeName.equalsIgnoreCase("pf") ) {
              el = (Element)item;
              try {
                  int id = new Integer(el.getAttribute("id" )).intValue();
                  if( id >= 1 && id <= 12 ) {
                      Button pf = new Button("pf" + id);
                      pf.setText( el.getAttribute( "text" ) );
                      pf.setProfile( el.getAttribute( "profile" ) );
                      trx.AddButton(pf);
                  }
               } catch (Exception e ) {
                   Motor.logger.error("Error en definición: " + e.toString() );                    
               }
         }
      }
   }

   public void loadReturnDef( Node node, Trx trx ) {
          Element el = (Element)node;
          ReturnAction ra = new ReturnAction();
          ra.setAction(el.getAttribute("action" ));
          ra.setAnexAction(el.getAttribute("name" ));
          ra.setReplace(el.getAttribute("replace" ).length()>0 && el.getAttribute("replace" ).equalsIgnoreCase("true"));
          ra.setValue(el.getAttribute("value" ));
          ra.setParameter(el.getAttribute("parameter" ));
          trx.addReturnAction(ra);
   }

   public void loadPfDef( Node node, String nodeName, Trx trx ) {
      Button b = trx.getButton(nodeName);
      if( b == null ) {
          return;
      }
      Element el = (Element)node;
      b.setAction( el.getAttribute("action") );
      b.setAnexAction(el.getAttribute("name"));
      b.setParameter(el.getAttribute("parameter"));
      b.setReplace(el.getAttribute("replace" ).length()>0 && el.getAttribute("replace" ).equalsIgnoreCase("true"));
   }
}
