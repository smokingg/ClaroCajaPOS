package cl.hyh.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class SelectorTarjeta extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private String valor;
	JButton btnDebito = new JButton("D\u00E9bito");
	JButton btnCredito = new JButton("Cr\u00E9dito");
	JButton btnMultiTienda = new JButton("Multitiendas");
	
	public void setup() {
		
	}
	/**
	 * Create the frame.
	 */
	public SelectorTarjeta() {
		
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Seleccione Medio de Pago");
		setBounds(100, 100, 400, 250);
		getContentPane().setLayout(new BorderLayout());
		
		Panel panel = new Panel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
				size.height/2 - getHeight()/2);    
		
		
		btnDebito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valor = "DB";
				System.out.println(valor);
				 dispose();
			}
		});
		btnDebito.setHorizontalAlignment(SwingConstants.LEFT);
		btnDebito.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDebito.setPreferredSize(new Dimension(320,60));
		btnDebito.setBorderPainted(false);
		btnDebito.setIcon(new ImageIcon(getClass().getClassLoader().getResource("config/redcompra.png")));
		panel.add(btnDebito);
		
		btnCredito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valor = "CR";
				System.out.println(valor);
				 dispose();
			}
		});
		
		btnCredito.setHorizontalAlignment(SwingConstants.LEFT);
		btnCredito.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCredito.setPreferredSize(new Dimension(320,60));
		btnCredito.setBorderPainted(false);
		btnCredito.setIcon(new ImageIcon(getClass().getClassLoader().getResource("config/transbank.png")));
		panel.add(btnCredito);
		
		btnMultiTienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valor = "NB";
				System.out.println(valor);
				 dispose();
			}
		});
		
		btnMultiTienda.setHorizontalAlignment(SwingConstants.LEFT);
		btnMultiTienda.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMultiTienda.setPreferredSize(new Dimension(320,60));
		btnMultiTienda.setBorderPainted(false);
		btnMultiTienda.setIcon(new ImageIcon(getClass().getClassLoader().getResource("config/tarjetasMulti.jpg")));
		panel.add(btnMultiTienda);
		
		
		 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "EnterPressed");
	     getRootPane().getActionMap().put("EnterPressed", new EnterAction() );
	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "EscPressed");
	     getRootPane().getActionMap().put("EscPressed", new EscAction() );

	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DownPressed");
	     getRootPane().getActionMap().put("DownPressed", new DownAction() );
	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UpPressed");
	     getRootPane().getActionMap().put("UpPressed", new UptAction() );
	     btnDebito.addActionListener(new EnterAction());
	     btnCredito.addActionListener(new EnterAction());
	     btnMultiTienda.addActionListener(new EnterAction());
	}
	  public String showOption() {
			this.setVisible(true);
			return valor;
		}
	  
	    class EnterAction extends AbstractAction {
	        private static final long serialVersionUID = 1L;
	        public void actionPerformed(ActionEvent e) {
	        	if( btnDebito.isFocusOwner() ) {
	            	valor = "DB";
	            	System.out.println(valor);
	        	}
	        	if( btnCredito.isFocusOwner() ) {
	            	valor = "CR";
	            	System.out.println(valor);
	        	}
	        	if( btnMultiTienda.isFocusOwner() ) {
	            	valor = "NB";
	            	System.out.println(valor);
	        	}
	        	
	            dispose();
	        }
	    }

	    class EscAction extends AbstractAction {
	        private static final long serialVersionUID = 1L;
	        public void actionPerformed(ActionEvent e) {
//	        	if( btnDebito.isFocusOwner() ) {
//	            	valor = "DB";
//	            	System.out.println(valor);
//	        	}
//	        	if( btnCredito.isFocusOwner() ) {
//	            	valor = "CR";
//	            	System.out.println(valor);
//	        	}
//	        	if( btnMultiTienda.isFocusOwner() ) {
//	            	valor = "NB";
//	            	System.out.println(valor);
//	        	}
	        	
	            dispose();
	        }
	    }
	    
	    class DownAction extends AbstractAction {
	        private static final long serialVersionUID = 1L;
	        public void actionPerformed(ActionEvent e) {
	        	
	        	if( btnDebito.isFocusOwner() ) {
	        		btnCredito.requestFocusInWindow();    		
	        	}
	        	if( btnCredito.isFocusOwner() ) {
	        		btnMultiTienda.requestFocusInWindow();      		
	        	}
	        	if( btnMultiTienda.isFocusOwner() ) {
	        		btnDebito.requestFocusInWindow();      		
	        	}       	

	        }
	    }

	    class UptAction extends AbstractAction {
	        private static final long serialVersionUID = 1L;
	        public void actionPerformed(ActionEvent e) {
	        	if( btnDebito.isFocusOwner() ) {
	        		btnMultiTienda.requestFocusInWindow();    		
	        	}
	        	if( btnCredito.isFocusOwner() ) {
	        		btnDebito.requestFocusInWindow();      		
	        	}
	        	if( btnMultiTienda.isFocusOwner() ) {
	        		btnCredito.requestFocusInWindow();      		
	        	}      
	        }
	    }
		
		
//		setTitle("Seleccione Medio de Pago");
//		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
//		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(50, 100, 400, 250);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		
//		Panel panel = new Panel();
//		contentPane.add(panel, BorderLayout.CENTER);
//		
//		btnDebito.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				valor = "DB";
//				System.out.println(valor);
//				 dispose();
//								
//			}
//		});
//		btnDebito.setHorizontalAlignment(SwingConstants.LEFT);
//		btnDebito.setFont(new Font("Tahoma", Font.BOLD, 14));
//		btnDebito.setPreferredSize(new Dimension(320,60));
//		btnDebito.setBorderPainted(false);
//		btnDebito.setIcon(new ImageIcon(getClass().getClassLoader().getResource("config/redcompra.png")));
//		panel.add(btnDebito);
//		
//		
//		btnCredito.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				 valor = "CR";
//				System.out.println(valor);
//				 dispose();
//			}
//		});
//		btnCredito.setHorizontalAlignment(SwingConstants.LEFT);
//		btnCredito.setFont(new Font("Tahoma", Font.BOLD, 14));
//		btnCredito.setPreferredSize(new Dimension(320,60));
//		btnCredito.setBorderPainted(false);
//		btnCredito.setIcon(new ImageIcon(getClass().getClassLoader().getResource("config/transbank.png")));
//		panel.add(btnCredito);
//		
//		
//		
//		
//		btnMultiTienda.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				valor = "NB";
//				System.out.println(valor);
//				 dispose();
//			}
//		});
//		btnMultiTienda.setHorizontalAlignment(SwingConstants.LEFT);
//		btnMultiTienda.setFont(new Font("Tahoma", Font.BOLD, 14));
//		btnMultiTienda.setPreferredSize(new Dimension(320,60));
//		btnMultiTienda.setBorderPainted(false);
//		btnMultiTienda.setIcon(new ImageIcon(getClass().getClassLoader().getResource("config/tarjetasMulti.jpg")));
//		panel.add(btnMultiTienda);
//		
//
//	     
//		 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "EnterPressed");
//	     getRootPane().getActionMap().put("EnterPressed", new EnterAction() );
//	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "EscPressed");
//	     getRootPane().getActionMap().put("EscPressed", new EscAction() );
//
//	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "LeftPressed");
//	     getRootPane().getActionMap().put("LeftPressed", new DownAction() );
//	     getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "RightPressed");
//	     getRootPane().getActionMap().put("RightPressed", new UptAction() );
//	     btnDebito.addActionListener(new EnterAction());
//	     btnCredito.addActionListener(new EnterAction());
//	     btnMultiTienda.addActionListener(new EnterAction());
//	}
//	  public String showOption() {
//			this.setVisible(true);
//			return valor;
//		}
//	  
//	    class EnterAction extends AbstractAction {
//	        private static final long serialVersionUID = 1L;
//	        public void actionPerformed(ActionEvent e) {
//	        	if( btnDebito.isFocusOwner() ) {
//	            	valor = "DB";        		
//	        	}
//	        	if( btnCredito.isFocusOwner() ) {
//	            	valor = "CR";        		
//	        	}
//	        	if( btnMultiTienda.isFocusOwner() ) {
//	            	valor = "NB";        		
//	        	}
//	        	
//	            dispose();
//	        }
//	    }
//
//	    class EscAction extends AbstractAction {
//	        private static final long serialVersionUID = 1L;
//	        public void actionPerformed(ActionEvent e) {
//	        	if( btnDebito.isFocusOwner() ) {
//	            	valor = "DB";        		
//	        	}
//	        	if( btnCredito.isFocusOwner() ) {
//	            	valor = "CR";        		
//	        	}
//	        	if( btnMultiTienda.isFocusOwner() ) {
//	            	valor = "NB";        		
//	        	}
//	        	
//	            dispose();       		
//	            dispose();
//	        }
//	    }
//	    
//	    class DownAction extends AbstractAction {
//	        private static final long serialVersionUID = 1L;
//	        public void actionPerformed(ActionEvent e) {
//	        	
//	        	if( btnDebito.isFocusOwner() ) {
//	        		btnCredito.requestFocusInWindow();    		
//	        	}
//	        	if( btnCredito.isFocusOwner() ) {
//	        		btnMultiTienda.requestFocusInWindow();      		
//	        	}
//	        	if( btnMultiTienda.isFocusOwner() ) {
//	        		btnDebito.requestFocusInWindow();      		
//	        	}       	
//
//	        }
//	    }
//
//	    class UptAction extends AbstractAction {
//	        private static final long serialVersionUID = 1L;
//	        public void actionPerformed(ActionEvent e) {
//	        	if( btnDebito.isFocusOwner() ) {
//	        		btnMultiTienda.requestFocusInWindow();    		
//	        	}
//	        	if( btnCredito.isFocusOwner() ) {
//	        		btnDebito.requestFocusInWindow();      		
//	        	}
//	        	if( btnMultiTienda.isFocusOwner() ) {
//	        		btnCredito.requestFocusInWindow();      		
//	        	}      
//	        }
//	    }
}
