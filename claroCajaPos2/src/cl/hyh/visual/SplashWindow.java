package cl.hyh.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cl.hyh.base.Motor;

/**
 * @author abertens
 *
 */
public class SplashWindow extends JFrame {

   private static final long serialVersionUID = 1L;

   public SplashWindow() {
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setResizable( false );
       setTitle(Motor.propCajas.getProperty("splashLabel1"));
       setUndecorated( true );
   
      JPanel panel = new JPanel( new GridLayout(2, 1, 10, 10) ); 
      //panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
      panel.setBorder(BorderFactory.createLineBorder(Color.black));
      panel.setBackground(Color.decode(Motor.propCajas.getProperty("splashBgColor")));
      
      // LABEL 1: TOP
      JPanel panelT1 = new JPanel();
      panelT1.setBackground(Color.decode(Motor.propCajas.getProperty("splashBgColor")));
        JLabel label1 = new JLabel(Motor.propCajas.getProperty("splashLabel1"));
        label1.setFont(new Font("Arial", Font.PLAIN, 30));
        label1.setForeground(Color.decode(Motor.propCajas.getProperty("splashFgColor")));
        panelT1.add(label1);
        panel.add(panelT1);

      // LABEL 2: BOTTOM
      JPanel panelT2 = new JPanel();
      panelT2.setBackground(Color.decode(Motor.propCajas.getProperty("splashBgColor")));
        JLabel label2 = new JLabel(Motor.propCajas.getProperty("splashLabel2"));
        label2.setFont(new Font("Arial", Font.PLAIN, 20));
        label2.setForeground(Color.decode(Motor.propCajas.getProperty("splashFgColor")));
        panelT2.add(label2);
        panel.add(panelT2);
      
      add( panel );
      pack();
      center();
   }

   public void center() {
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, 
      size.height/2 - getHeight()/2);      
   }

}
