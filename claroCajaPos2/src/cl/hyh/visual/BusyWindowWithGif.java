package cl.hyh.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BusyWindowWithGif extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String busyBgColor = "0xFFFFFF";
	private static final String busyFgColor = "0xFF0000";

	private String title = "";
	private String message = "";
	
	public BusyWindowWithGif ( String title, String message ) {
			this.title = title;
			this.message = message;
	}
	
	public void setup() {
	       setDefaultCloseOperation(EXIT_ON_CLOSE);
	       setResizable( false );
	       setTitle(title);
	       setUndecorated( true );
	   
	      JPanel panel = new JPanel( new GridLayout(2, 1, 10, 10) ); 
	      //panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
	      panel.setBorder(BorderFactory.createLineBorder(Color.black));
	      panel.setBackground(Color.decode(busyBgColor));
	      
	      // LABEL 1: TOP
	      JPanel panelT1 = new JPanel();
	      panelT1.setBackground(Color.decode(busyBgColor));
	        JLabel label1 = new JLabel(title);
	        label1.setFont(new Font("Arial", Font.PLAIN, 30));
	        label1.setForeground(Color.decode(busyFgColor));
	        panelT1.add(label1);
	        panel.add(panelT1);
	        MyImage image = new MyImage( getClass().getClassLoader().getResourceAsStream("config/Circulo.gif") );
	      // LABEL 2: BOTTOM
	      JPanel panelT2 = new JPanel();
	      panelT2.setBackground(Color.decode(busyBgColor));
	        JLabel label2 = new JLabel(message);
	        label2.setFont(new Font("Arial", Font.PLAIN, 20));
	        label2.setForeground(Color.decode(busyFgColor));
	        
	        panelT2.add(label2);
	        panelT2.add(image);
	        panel.add(panelT2);
	      
		add( panel );
		pack();

		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
				size.height/2 - getHeight()/2);      

	}

}
