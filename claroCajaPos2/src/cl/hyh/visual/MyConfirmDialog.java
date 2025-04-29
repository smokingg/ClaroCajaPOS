package cl.hyh.visual;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class MyConfirmDialog extends JDialog {

	private int option = JOptionPane.NO_OPTION;
    JButton no  = new JButton("No");
    JButton yes = new JButton("Sí");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyConfirmDialog( String title, String text ) {
		
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setTitle( title );
        
        add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel labels = new JPanel( new FlowLayout() );

        labels.add(Box.createRigidArea(new Dimension(5, 0)));
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("config/Help24.gif")); 
        JLabel label = new JLabel(icon);
        labels.add(label);

        String value = text;
        while( true ) {
        	int pos = value.indexOf('\n', 0);
        	if( pos < 0 )
        		break;
        	if( pos < value.length() + 1 ) { 
        		value = value.substring(0, pos) + "<br>" + value.substring(pos+1);
        	} else {
        		value = value.substring(0, pos);
        	}
        }
        JLabel name = new JLabel("<html>" + value, SwingConstants.CENTER);
        name.setFont(new Font("Serif", Font.BOLD, 13));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        labels.add(Box.createRigidArea(new Dimension(5, 0)));
        labels.add(name);
        labels.add(Box.createRigidArea(new Dimension(5, 0)));
        add(labels);
        
        add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout( buttons, BoxLayout.X_AXIS ));
        yes.setAlignmentX(0.5f);
        buttons.add(yes);
        buttons.add(Box.createRigidArea(new Dimension(5, 5)));
        no.setAlignmentX(0.5f);
        buttons.add(no);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(buttons);
        
        add(Box.createRigidArea(new Dimension(0, 20)));

        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        pack();
        center();

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	option = JOptionPane.YES_OPTION;
                dispose();
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	option = JOptionPane.NO_OPTION;
                dispose();
            }
        });

        yes.addActionListener(new EnterAction());
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "EnterPressed");
        getRootPane().getActionMap().put("EnterPressed", new EnterAction() );
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "EscPressed");
        getRootPane().getActionMap().put("EscPressed", new EscAction() );

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "LeftPressed");
        getRootPane().getActionMap().put("LeftPressed", new LeftAction() );
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "RightPressed");
        getRootPane().getActionMap().put("RightPressed", new RightAction() );
	
	}
	
	   public void center() {
	        Toolkit toolkit = getToolkit();
	        Dimension size = toolkit.getScreenSize();
	        setLocation(size.width/2 - getWidth()/2, 
	      size.height/2 - getHeight()/2);      
	   }

	   public int showDialog() {
		this.setVisible(true);
		return option;
	}

    class EnterAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        public void actionPerformed(ActionEvent e) {
        	if( no.isFocusOwner() ) {
            	option = JOptionPane.NO_OPTION;        		
        	} else {
            	option = JOptionPane.YES_OPTION;        		        		
        	}
            dispose();
        }
    }

    class EscAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        public void actionPerformed(ActionEvent e) {
            option = JOptionPane.NO_OPTION;        		
            dispose();
        }
    }

    class LeftAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        public void actionPerformed(ActionEvent e) {
        	if( no.isFocusOwner() ) {
            	yes.requestFocusInWindow();        		
        	}
        }
    }

    class RightAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        public void actionPerformed(ActionEvent e) {
        	if( yes.isFocusOwner() ) {
            	no.requestFocusInWindow();        		
        	}
        }
    }
}
