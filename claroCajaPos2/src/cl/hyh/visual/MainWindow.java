package cl.hyh.visual; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import cl.hyh.base.Motor;
import cl.hyh.interfaces.ICajaView;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.CarroCompra;
import cl.hyh.redpagos.caja.base.CarroMPagos;
import cl.hyh.redpagos.caja.base.Datos;
import cl.hyh.redpagos.caja.base.DocumentoPago;
import cl.hyh.redpagos.caja.base.Format;
import cl.hyh.redpagos.caja.base.MedioPago;
import cl.hyh.redpagos.caja.base.OperTRV;
import cl.hyh.redpagos.caja.base.ParamSet;


/*
 * JOptionPane.INFORMATION_MESSAGE
 * JOptionPane.QUESTION_MESSAGE
 * JOptionPane.WARNING_MESSAGE
 * JOptionPane.ERROR_MESSAGE
 */

/**
 * @author abertens
 *
 */
public class MainWindow extends JFrame implements ICajaView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DateFormat _FMTDATE = new SimpleDateFormat(" MMM dd, yyyy  HH:mm:ss"); //SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static boolean[] editTV = null;
	private String realTextField = "";
	
	private JPanel mainPanel = null; 
	private JPanel top = null;
	private JPanel center = null;
	private JPanel bottom = null;
	private JPanel panelTopTV = null;
	private JPanel panelBottomTV = null;
	private JPanel panelCenterRight = new JPanel();

	@SuppressWarnings("unused")
	private JFrame mainFrame = null; 

	// TECLAS ACELERADORAS PARA CARROS
	private JButton altD = new JButton();
	private JButton altE = new JButton();
	// TITULOS DE LOS CARROS
	private JLabel cartTitle   = new JLabel();
	private JLabel cartMessage = new JLabel();
	private JLabel cartTotal   = new JLabel();

	// CONTROLES VISUALES
	private JButton enter    = new JButton();
	private JButton pf1      = new JButton();
	private JButton pf2      = new JButton();
	private JButton pf3      = new JButton();
	private JButton pf4      = new JButton();
	private JButton pf5      = new JButton();
	private JButton pf6      = new JButton();
	private JButton pf7      = new JButton();
	private JButton pf8      = new JButton();
	private JButton pf9      = new JButton();
	private JButton pf10     = new JButton();
	private JButton pf11     = new JButton();
	private JButton pf12     = new JButton();

	private JLabel lTime     = new JLabel();
	private JLabel lAgencia   = new JLabel();
	private JLabel lUsuario   = new JLabel();
	private JLabel lOnline    = new JLabel();
	private JLabel lFechaPago = new JLabel();
	private JLabel lVersion = new JLabel();

	private JLabel         entryMessage            = new JLabel();
	private boolean        isEntryMessageVisible   = false;
	private JLabel         entryTitle              = new JLabel();
	private boolean        isEntryTitleVisible     = false;
	private JLabel         entryTextLabel          = new JLabel();
	private boolean        isEntryTextLabelVisible = false;
	private JList          entryList               = new JList();
	private JScrollPane    entryListPane           = new JScrollPane();
	private boolean        isEntryListVisible      = false;
	private JTextField     entryText               = new JTextField();
	private boolean        isEntryTextVisible      = false;
	private JPasswordField entryPassword           = new JPasswordField();
	private boolean        isEntryTextPassword     = false;
	private JTextArea      entryTextArea           = new JTextArea();
	private JScrollPane    entryTextAreaPane       = new JScrollPane();
	private boolean        isEntryTextAreaVisible  = false;
	private Cal            entryCalendar           = new Cal();
	private boolean        isEntryCalendarVisible  = false;
	private JTable          entryTable              = new JTable();
	private JScrollPane    entryTablePane          = new JScrollPane();
	private boolean        isEntryTableVisible     = false;
	private boolean        showEnter               = true;
	private boolean        acceptEscape            = false;
	private JTable         entryTableTv            = new JTable();
	private JScrollPane    entryTablePaneTv        = new JScrollPane();

	private JList documentCart           = new JList();
	private JScrollPane documentCartPane = new JScrollPane();
	private JList paymentCart            = new JList();
	private JScrollPane paymentCartPane  = new JScrollPane();

	private BusyWindow     bw        = null;

	// CONTROL DE TIMEOUT EN LA ENTRADA DE DATOS
	private int inputTimeout = 0; // 0=INFINITO (SEGUNDOS)
	private boolean isInputTimeout = false;
	private ActionListener timerInputTimeout = null;

	// OBJETO PARA SEGNALIZACION DE WINDOW_CLOSE
	private Object signal    = null;

	// OBJETO DE SINCRONIZACION/INTERCAMBIO DE ENTRADA DE DATOS (USUARIO/CLASES)
	private Object userEvent = new Object();
	private int key;
	private String regExValidator = null;
	private String regExMessage = null;
	private Integer maxPasswordSize = null;

	// DATOS OPERATIVOS
	private OperTRV operTRV = null;
	private Datos datos = new Datos();
	private String[] list = null; // ENTRY LIST
	
	static String nomProy;
	static String versionMsg;
	static String vMsg;

	public MainWindow( Object signal ) {

		mainFrame = this;
		this.signal = signal;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //EXIT_ON_CLOSE);
		addWindowListener(new CloseAdapter());
		setResizable( false );
		setTitle(Motor.propCajas.getProperty("mainTopTitle"));
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource(Motor.propCajas.getProperty("mainIcon"))).getImage());
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBackground(Color.decode(Motor.propCajas.getProperty("mainTopBgColor")));
		setupTopPanel( mainPanel );
		setupCenterPanel( mainPanel );
		setupBottomPanel( mainPanel );
		
		add( mainPanel );

		// ASOCIACION DEL TECLADO A LOS BOTONES
		enter.addActionListener(new EnterAction());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "EnterPressed");
		mainPanel.getActionMap().put("EnterPressed", new EnterAction() );

		pf1.addActionListener(new Pf1Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F1"), "Pf1Pressed");
		mainPanel.getActionMap().put("Pf1Pressed", new Pf1Action() );

		pf2.addActionListener(new Pf2Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F2"), "Pf2Pressed");
		mainPanel.getActionMap().put("Pf2Pressed", new Pf2Action() );

		pf3.addActionListener(new Pf3Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F3"), "Pf3Pressed");
		mainPanel.getActionMap().put("Pf3Pressed", new Pf3Action() );

		pf4.addActionListener(new Pf4Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F4"), "Pf4Pressed");
		mainPanel.getActionMap().put("Pf4Pressed", new Pf4Action() );

		pf5.addActionListener(new Pf5Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F5"), "Pf5Pressed");
		mainPanel.getActionMap().put("Pf5Pressed", new Pf5Action() );

		pf6.addActionListener(new Pf6Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F6"), "Pf6Pressed");
		mainPanel.getActionMap().put("Pf6Pressed", new Pf6Action() );

		pf7.addActionListener(new Pf7Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F7"), "Pf7Pressed");
		mainPanel.getActionMap().put("Pf7Pressed", new Pf7Action() );

		pf8.addActionListener(new Pf8Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F8"), "Pf8Pressed");
		mainPanel.getActionMap().put("Pf8Pressed", new Pf8Action() );

		pf9.addActionListener(new Pf9Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F9"), "Pf9Pressed");
		mainPanel.getActionMap().put("Pf9Pressed", new Pf9Action() );

		pf10.addActionListener(new Pf10Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F10"), "Pf10Pressed");
		mainPanel.getActionMap().put("Pf10Pressed", new Pf10Action() );

		pf11.addActionListener(new Pf11Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F11"), "Pf11Pressed");
		mainPanel.getActionMap().put("Pf11Pressed", new Pf11Action() );

		pf12.addActionListener(new Pf12Action());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("F12"), "Pf12Pressed");
		mainPanel.getActionMap().put("Pf12Pressed", new Pf12Action() );

		altE.addActionListener(new AltEAction());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke( KeyEvent.VK_E, InputEvent.ALT_MASK ), "AltEPressed");
		mainPanel.getActionMap().put("AltEPressed", new AltEAction() );

		altD.addActionListener(new AltDAction());
		mainPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke( KeyEvent.VK_D, InputEvent.ALT_MASK ), "AltDPressed");
		mainPanel.getActionMap().put("AltDPressed", new AltDAction() );

		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "EscPressed");
		mainPanel.getActionMap().put("EscPressed", new EscAction() );
		
		//listener para click en version caja
	    lVersion.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e)  {
	        	JOptionPane.showMessageDialog(null, "Version: "+versionMsg
	        			+"\nÚltima modificación: "+nomProy+
	        			"\n"+vMsg);
	        }  
	    });

		// TIMER PARA MOSTRAR LA FECHA/HORA
		ActionListener timerShowTime = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				lTime.setText("Fecha: " + _FMTDATE.format(new java.util.Date()));
			}
		};
		new javax.swing.Timer(1000, timerShowTime).start();

		// TIME PARA CONTROL DE TIMEOUT EN INGRESO DE DATOS
		timerInputTimeout = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				isInputTimeout = true;
				((javax.swing.Timer)evt.getSource()).stop();
				synchronized (userEvent) {
					userEvent.notify();
				}
			}
		};

		pack();
		cleanEntryFields();
		showPaymentCart(false);
		showShoppingCart(false);
	}

	private void setupTopPanel( JPanel panel ) {
		top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setBackground(Color.decode(Motor.propCajas.getProperty("mainTopBgColor")));
		top.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// top.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));

		//MyImage image = new MyImage( getClass().getClassLoader().getResourceAsStream("config/vtr.JPG") );
		ParamSet pSet = Base.getParamSet("posDat");
		if(pSet.getStringValue("sinConexion").equals("si")){
			MyImage image = new MyImage( getClass().getClassLoader().getResourceAsStream("config/contingencia.png") );
			image.setPreferredSize(new Dimension(600,80));
			top.add(image, BorderLayout.LINE_START);
		}else{
			MyImage image = new MyImage( getClass().getClassLoader().getResourceAsStream("config/claro.PNG") );
			image.setPreferredSize(new Dimension(600,80));
			top.add(image, BorderLayout.LINE_START);
		}
		
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout( east, BoxLayout.PAGE_AXIS ));
		east.setBackground(Color.decode(Motor.propCajas.getProperty("mainTopBgColor")));
		// east.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		lTime.setForeground(Color.decode(Motor.propCajas.getProperty("mainTopFgColor")));
		lTime.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		east.add(lTime);

		lVersion.setForeground(Color.decode(Motor.propCajas.getProperty("mainTopFgColor")));
		lVersion.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		lVersion.setText( "Ver:      Agencia:" );
		east.add(lVersion);
		/*       
       lAgencia.setForeground(Color.decode(Motor.propCajas.getProperty("mainTopFgColor")));
       lAgencia.setFont(new Font("Sans Serif", Font.PLAIN, 12));
       lAgencia.setText( "Agencia: " );
       east.add(lAgencia);
		 */       
		lUsuario.setForeground(Color.decode(Motor.propCajas.getProperty("mainTopFgColor")));
		lUsuario.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		//lUsuario.setText( "Usuario:         Cajero:" );
		lUsuario.setText( "Usuario:         Caja:" );
		east.add(lUsuario);

		lOnline.setForeground(Color.decode(Motor.propCajas.getProperty("mainTopFgColor")));
		lOnline.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		//lOnline.setText( "Estado:          Entidad:" );
		lOnline.setText( "Estado: " );
		east.add(lOnline);

		lFechaPago.setForeground(Color.decode(Motor.propCajas.getProperty("mainTopFgColor")));
		lFechaPago.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		lFechaPago.setText( "Fecha Pago: " );
		east.add(lFechaPago);

		top.add(east, BorderLayout.EAST);

		panel.add( top );
	}

	private void setupCenterPanel( JPanel panel ) {
		center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		center.setBackground(Color.decode(Motor.propCajas.getProperty("mainCenterCartBgColor")));

		JPanel panelLeft = new JPanel();      
		panelLeft.setBackground(Color.decode(Motor.propCajas.getProperty("mainCenterCartBgColor")));
		panelLeft.setPreferredSize(new Dimension(600,200));
		panelLeft.setLayout(new BorderLayout());
		panelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		panelLeft.add(cartTitle, BorderLayout.NORTH);
		
		cartTitle.setForeground(Color.decode(Motor.propCajas.getProperty("mainCenterCartFgColor")));
	    cartMessage.setForeground(Color.decode(Motor.propCajas.getProperty("mainCenterCartFgColor")));
	    cartTotal.setForeground(Color.decode(Motor.propCajas.getProperty("mainCenterCartFgColor")));

		JPanel dummy = new JPanel( new FlowLayout() );
		dummy.setBackground(Color.decode(Motor.propCajas.getProperty("mainCenterCartBgColor")));
		documentCart.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		//documentCart.setForeground(Color.decode(Motor.propCajas.getProperty("mainCenterCartFgColor")));
		documentCart.setFixedCellHeight(16);
		documentCartPane.getViewport().add(documentCart);
		documentCartPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		documentCartPane.setPreferredSize(new Dimension(580,120));
		dummy.add(documentCartPane );

		paymentCart.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		//paymentCart.setForeground(Color.decode(Motor.propCajas.getProperty("mainCenterCartFgColor")));
		paymentCart.setFixedCellHeight(16);
		paymentCartPane.getViewport().add(paymentCart);
		paymentCartPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		paymentCartPane.setPreferredSize(new Dimension(580,120));
		dummy.add(paymentCartPane);      

		panelLeft.add( dummy, BorderLayout.CENTER );


		JPanel dummy2 = new JPanel( new BorderLayout() );
		dummy2.setBackground(Color.decode(Motor.propCajas.getProperty("mainCenterCartBgColor")));
		dummy2.add(cartMessage, BorderLayout.WEST);
		dummy2.add(cartTotal, BorderLayout.SOUTH);

		panelLeft.add(dummy2, BorderLayout.SOUTH);

		center.add( panelLeft );

		panelCenterRight = new JPanel();
		panelCenterRight.setBackground(Color.decode(Motor.propCajas.getProperty("mainCenterButtonsBgColor")));
		panelCenterRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelCenterRight.setLayout(new GridLayout(3, 3, 5, 5));
		panelCenterRight.setPreferredSize(new Dimension(400,200));
		// EL ORDEN DE LOS BOTONES ESTA DADO POR SU ORDEN AL AGREGARLOS
		// RECORRE POR FILA
		if( Motor.propCajas.getProperty("buttonOrder").equalsIgnoreCase("column") ) {
			panelCenterRight.add(pf1); panelCenterRight.add(pf4); panelCenterRight.add(pf7);
			panelCenterRight.add(pf2); panelCenterRight.add(pf5); panelCenterRight.add(pf8);
			panelCenterRight.add(pf3); panelCenterRight.add(pf6); panelCenterRight.add(pf9);
		} else {
			panelCenterRight.add(pf1); panelCenterRight.add(pf2); panelCenterRight.add(pf3);
			panelCenterRight.add(pf4); panelCenterRight.add(pf5); panelCenterRight.add(pf6);
			panelCenterRight.add(pf7); panelCenterRight.add(pf8); panelCenterRight.add(pf9);
		}

		center.add( panelCenterRight );

		panel.add( center );
	}

	private void setupBottomPanel( JPanel panel ) {      
		bottom = new JPanel();

		entryTitle.setText("12345 78901 34567");
		entryTextLabel.setText("123456 890123456");
		entryMessage.setText("1234567890 2345678901 34567890123");

		// bottom.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.setBackground(Color.decode(Motor.propCajas.getProperty("mainBottomBgColor")));
		bottom.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel panelLeft = new JPanel();
		// panelLeft.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		panelLeft.setBackground(Color.decode(Motor.propCajas.getProperty("mainBottomBgColor")));
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.PAGE_AXIS));
		panelLeft.setPreferredSize(new Dimension(720,448));

		// ARRIBA EL TITULO
		entryTitle.setPreferredSize(new Dimension(720,30));
		entryTitle.setFont(new Font("Sans Serif", Font.BOLD, 16));
		entryTitle.setForeground(Color.decode(Motor.propCajas.getProperty("mainBottomFgColor")));
		// entryTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		entryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelLeft.add( entryTitle );
		panelLeft.add(Box.createRigidArea(new Dimension(0,1)));

		// CAMPOS DE ENTRADA
		JPanel inputPane = new JPanel();
		// inputPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
		inputPane.setLayout(new FlowLayout());
		inputPane.setBackground(Color.decode(Motor.propCajas.getProperty("mainBottomBgColor")));
		entryTextLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
		entryTextLabel.setForeground(Color.decode(Motor.propCajas.getProperty("mainBottomFgColor")));
		entryTextLabel.setPreferredSize(new Dimension(200,280));
		// entryTextLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		inputPane.add( entryTextLabel );
		entryText.setPreferredSize(new Dimension(650,40));
		entryText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		// entryText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		entryText.addKeyListener(new KeyAdapter() { 	         
    		    public void keyTyped(KeyEvent e) {
    		        char c = e.getKeyChar();
    		        //e.setKeyChar(Character.toUpperCase(c));
    		        e.setKeyChar(c);
    		    }		           
		}); 		
		inputPane.add( entryText );
		entryTextArea.setRows(10);
		entryTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
		entryTextAreaPane.getViewport().add(entryTextArea);
		entryTextAreaPane.setPreferredSize(new Dimension(650,280));
		// entryTextAreaPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		inputPane.add( entryTextAreaPane );
		entryList.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		entryList.setFixedCellHeight(18);
		entryList.setLayoutOrientation(JList.VERTICAL);
		entryListPane.getViewport().add(entryList);
		entryListPane.setPreferredSize(new Dimension(650,280));
		// entryListPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		inputPane.add( entryListPane );
		// PASSWORD 
		entryPassword.setPreferredSize(new Dimension(650,40));
		entryPassword.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		inputPane.add( entryPassword );
		// CALENDAR
		inputPane.add(entryCalendar);
		// TABLE
		entryTablePane.getViewport().add(entryTable);
		entryTable.setFillsViewportHeight(true);
		entryTablePane.setPreferredSize(new Dimension(650,280));
		// entryTablePane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		inputPane.add(entryTablePane);

		panelLeft.add( inputPane );
		panelLeft.add(Box.createRigidArea(new Dimension(0,5)));

		JPanel botPanel = new JPanel();
		botPanel.setLayout( new BoxLayout( botPanel, BoxLayout.PAGE_AXIS ) );
		// botPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		botPanel.setBackground(Color.decode(Motor.propCajas.getProperty("mainBottomBgColor")));
		botPanel.setPreferredSize(new Dimension(720,50));

		// ABAJO EL MENSAJE
		entryMessage.setPreferredSize(new Dimension(720,30));
		entryMessage.setFont(new Font("Sans Serif", Font.BOLD, 16));
		entryMessage.setForeground(Color.decode(Motor.propCajas.getProperty("mainBottomFgMsgColor")));
		// entryMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		botPanel.add(entryMessage);
		
		

		JPanel buttons = new JPanel();
		buttons.setBackground(Color.decode(Motor.propCajas.getProperty("mainBottomBgColor")));
		buttons.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		buttons.setLayout(new GridLayout(2, 3, 20, 20));
		// buttons.setPreferredSize(new Dimension(580,60));
		buttons.add(Box.createRigidArea(new Dimension(0,60)));
		buttons.add(Box.createRigidArea(new Dimension(0,60)));
		buttons.add(Box.createRigidArea(new Dimension(0,60)));
		pf10.setText( "<html><div align=\"center\">" + "Texto PF10" + "<br>PF10" );
		buttons.add(pf10);
		pf11.setText( "<html><div align=\"center\">" + "Texto PF11" + "<br>PF11" );
		buttons.add(pf11);
		pf12.setText( "<html><div align=\"center\">" + "Texto PF12" + "<br>PF12" );
		buttons.add(pf12);        
		botPanel.add(buttons);

		panelLeft.add( botPanel );

		bottom.add( panelLeft );

		// Y EL BOTON DE ENTER
		JPanel panelRight = new JPanel();
		panelRight.setPreferredSize(new Dimension(80,448));
		// panelRight.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
		panelRight.setBackground(Color.decode(Motor.propCajas.getProperty("mainBottomBgColor")));
		enter.setAlignmentX(0.5f);
		enter.setText("Enter");
		enter.setPreferredSize(new Dimension(70,200));
		panelRight.add(Box.createRigidArea(new Dimension(0,100)));
		panelRight.add(enter);
		panelRight.add(Box.createVerticalGlue());
		panelRight.add(Box.createRigidArea(new Dimension(0,100)));
		bottom.add( panelRight );

		panel.add( bottom );
	}


	public void center() {
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
				size.height/2 - getHeight()/2);      
	}

	public void cleanEntryFields() {
		hideAllEntries();
		clearAllEntries();
		pf1.requestFocusInWindow();
	}

	public void setVisibleEntryMessage( boolean bool ) {
		isEntryMessageVisible = bool;
		entryMessage.setVisible(bool);
	}
	public void setVisibleEntryTitle( boolean bool ) {
		isEntryTitleVisible = bool;
		entryTitle.setVisible(bool);
	}
	public void setVisibleEntryTextLabel( boolean bool ) {
		isEntryTextLabelVisible = bool;
		entryTextLabel.setVisible(bool);
	}
	public void setVisibleEntryText( boolean bool ) {
		if( bool ) {
			isEntryTextVisible = true;
		} else if(!isEntryTextPassword) {
			isEntryTextVisible = false;
		}
		entryText.setVisible(bool);
	}

	public void setVisibleEntryPassword( boolean bool ) {
		if( bool ) {
			isEntryTextVisible = true;
		} else if(isEntryTextPassword) {
			isEntryTextVisible = false;
		}
		entryPassword.setVisible(bool);
	}

	public void setVisibleEntryTextArea( boolean bool ) {
		isEntryTextAreaVisible = bool;
		entryTextArea.setVisible(bool);
		entryTextAreaPane.setVisible(bool);
	}

	public void setVisibleEntryList( boolean bool ) {
		isEntryListVisible = bool;
		entryList.setVisible(bool);
		entryListPane.setVisible(bool);
	}

	public void setVisibleEntryTable( boolean bool ) {
		isEntryTableVisible = bool;
		entryTable.setVisible(bool);
		entryTablePane.setVisible(bool);
	}

	public void setVisibleEntryCalendar( boolean bool ) {
		isEntryCalendarVisible = bool;
		entryCalendar.setVisible(bool);
	}

	public boolean entryListIsEmpty() {
		return( entryList.getFirstVisibleIndex() == -1);
	}

	public void setVisibleEnter( boolean bool ) {
		enter.setVisible(bool);
	}
	public boolean isEnterVisible() {
		return enter.isVisible();
	}
	public boolean isEntryTextPassword() {
		return isEntryTextPassword;
	}
	public boolean isEntryTitleVisible() {
		return isEntryTitleVisible;
	}
	public boolean isEntryMessageVisible() {
		return isEntryMessageVisible;
	}
	public boolean isEntryTextLabelVisible() {
		return isEntryTextLabelVisible;
	}
	public boolean isEntryTextVisible() {
		return isEntryTextVisible;
	}
	public boolean isEntryTextAreaVisible() {
		return isEntryTextAreaVisible;
	}
	public boolean isEntryListVisible() {
		return isEntryListVisible;
	}
	public boolean isEntryTableVisible() {
		return isEntryTableVisible;
	}
	public boolean isEntryCalendarVisible() {
		return isEntryCalendarVisible;
	}

	public boolean isDocumentCartVisible() {
		return documentCart.isVisible();
	}

	public boolean isPaymentCartVisible() {
		return paymentCart.isVisible();
	}

	public void setFocusEntryText() {
		if(isEntryTextPassword) {
			entryPassword.requestFocusInWindow();
		} else {
			entryText.requestFocusInWindow();            
		}
	}
	public void setFocusEntryTextArea() {
		entryTextArea.requestFocusInWindow();            
	}
	public void setFocusList() {
		entryList.requestFocusInWindow();
	}
	public void setFocusTable() {
		entryTable.requestFocusInWindow();
	}
	public void setFocusEntryCalendar() {
		entryCalendar.requestFocusInWindow();
	}
	public boolean isInputTimeout() {
		return isInputTimeout;
	}

	public void showShoppingCart( boolean show ) {
		if( show ) {
			showPaymentCart(false);
			cartTitle.setText("Carro de Documentos a Pagar");
			//cartMessage.setText("Alt-D : Detalle      Alt-E : Eliminar");
			cartMessage.setText("Alt-E : Eliminar");
			// CALCULA MONTO Y CARGA cartTotal
			long sumaDoctos = 0;
			long sumaPago = 0;
			Vector<String> v = new Vector<String>();
			if( operTRV != null ) {
				CarroCompra cc = operTRV.getCarroCompras();
				ArrayList<DocumentoPago> ldp = cc.getDocumentos();
				for( DocumentoPago dp: ldp ) {
					v.add( dp.glosaCarro() );
					sumaDoctos += dp.getMonto();
				}
			}            
			documentCart.setListData( v );
			if( operTRV != null ) {
				CarroMPagos cp = operTRV.getCarroMediosPago();
				ArrayList<MedioPago> lmp = cp.getMediosPago();
				for( MedioPago mp: lmp ) {
					sumaPago += mp.getMonto();
				}
			}
			if( sumaDoctos-sumaPago >= 0 ) {
				cartTotal.setText( "TOTAL: " + Format.formatMonto(sumaDoctos) + "       PAGO: " + Format.formatMonto(sumaPago) + "       SALDO: " + Format.formatMonto((sumaDoctos-sumaPago)) );
			} else {
				cartTotal.setText( "TOTAL: " + Format.formatMonto(sumaDoctos) + "       PAGO: " + Format.formatMonto(sumaPago) + "       VUELTO: " + Format.formatMonto((sumaPago-sumaDoctos)) );                
			}
			cartTitle.setVisible(true);
			cartMessage.setVisible(true);
			cartTotal.setVisible(true);
			documentCart.setVisible(true);
			documentCartPane.setVisible(true);        
		} else {
			cartTitle.setVisible(false);
			cartMessage.setVisible(false);
			cartTotal.setVisible(false);
			documentCart.setVisible(false);            
			documentCartPane.setVisible(false);            
		}
	}

	public void showPaymentCart( boolean show ) {
		if( show ) {
			showShoppingCart(false);
			cartTitle.setText("Carro de Medios de Pago");
			//cartMessage.setText("Alt-D : Detalle      Alt-E : Eliminar");
			cartMessage.setText("Alt-E : Eliminar");
			// CALCULA MONTO Y SALDO
			long sumaDoctos = 0;
			long sumaPago = 0;
			// NO ES VISIBLE EL CARRO DE COMPRAS CUANDO SE VE EL DE PAGO
			if( operTRV != null ) {
				CarroCompra cc = operTRV.getCarroCompras();
				ArrayList<DocumentoPago> ldp = cc.getDocumentos();
				for( DocumentoPago dp: ldp ) {
					sumaDoctos += dp.getMonto();
				}
			}
			Vector<String> v = new Vector<String>();
			if( operTRV != null ) {
				CarroMPagos cp = operTRV.getCarroMediosPago();
				ArrayList<MedioPago> lmp = cp.getMediosPago();
				for( MedioPago mp: lmp ) {
					if(!mp.getNombre().equals("AjusteSencillo")){
						v.add( mp.glosaCarro() );
						sumaPago += mp.getMonto();
					}
					
				}
			}
			paymentCart.setListData( v );

			if( sumaDoctos-sumaPago >= 0 ) {
				cartTotal.setText( "TOTAL: " + Format.formatMonto(sumaDoctos) + "       PAGO: " + Format.formatMonto(sumaPago) + "       SALDO: " + Format.formatMonto((sumaDoctos-sumaPago)) );
			} else {
				cartTotal.setText( "TOTAL: " + Format.formatMonto(sumaDoctos) + "       PAGO: " + Format.formatMonto(sumaPago) + "       VUELTO: " + Format.formatMonto((sumaPago-sumaDoctos)) );                
			}
			cartTitle.setVisible(true);
			cartMessage.setVisible(true);
			cartTotal.setVisible(true);
			paymentCart.setVisible(true);
			paymentCartPane.setVisible(true);
		} else {
			cartTitle.setVisible(false);
			cartMessage.setVisible(false);
			cartTotal.setVisible(false);
			paymentCart.setVisible(false);            
			paymentCartPane.setVisible(false);            
		}
	}

	public void setUsuario( String usuario, String recaudador ) {
		lUsuario.setText("Usuario: " + usuario + "   Caja: " + recaudador);
	}

	public void setAgencia( String agencia ) {
		lAgencia.setText("Agencia: " + agencia );
	}

//	public void setVersion( String version, String agencia, String entidad ) {
//		//lVersion.setText("Ver: " + version + "  Local: " + agencia );
//		lVersion.setText("Ver: " + version + "  Agencia: " + agencia );
//	}
	public void setVersion( String version, String agencia, String msg, String nombreProyecto) {
        lVersion.setText("Ver: " + version + " (m5k) Agencia: " + agencia );
        versionMsg = version;
        nomProy = nombreProyecto;
        vMsg = msg;
    }
	public void setFechaPago( String fecha ) {
		lFechaPago.setText("Fecha Pago: " + fecha );
	}

	public void setOnline( String online ) {
		lOnline.setText("Estado: " + online);
	}
	
	public void setOnline( String online , String caja ) {
        lOnline.setText("Estado: " + online);
    }

	public void setFocusDocumentCart() {
		if( documentCart.getComponentCount() > 0 ) {
			documentCart.setSelectedIndex(0);            
		}
		documentCart.requestFocusInWindow();
	}

	public void setFocusPaymentCart() {
		if( paymentCart.getComponentCount() > 0 ) {
			paymentCart.setSelectedIndex(0);            
		}
		paymentCart.requestFocusInWindow();
	}

	public boolean displayEnter() {
		return showEnter;
	}

	public void showExtraButtons() {
		if( pf10.getText().length() > 0 || 
				pf11.getText().length() > 0 || 
				pf12.getText().length() > 0 ) {
			pf10.setVisible(true);
			pf11.setVisible(true);
			pf12.setVisible(true);
		} else {
			pf10.setVisible(false);
			pf11.setVisible(false);
			pf12.setVisible(false);    		
		}
	}

	public boolean returnEscape() {
		return acceptEscape;
	}

	///////////////////////////////////////////////////////////////////////
	//                     METODOS INTERFAZ ICajaView                    //
	///////////////////////////////////////////////////////////////////////
	public int execute() {
		javax.swing.Timer t  = null;
		synchronized ( userEvent ) {
			isInputTimeout = false;
			if( inputTimeout != 0 ) {
				t = new javax.swing.Timer(inputTimeout*1000, timerInputTimeout);
				t.start();
			}
			while( true ) {
				try {
					// ESPERO UN EVENTO POR PARTE DEL USUARIO
					userEvent.wait();
					// OCURRIO EVENTO O TIMEOUT...
					// SI NO ES TIMEOUT Y TECLA ENTER Y ENTRYTEXT VISIBLE Y REGEX
					// VALIDO ENTONCES...
					if( (t == null || !isInputTimeout) &&
							key == KeyEvent.VK_ENTER && 
							isEntryTextVisible() && 
							regExValidator != null ) {
						// STOP TIMER
						if( t != null ) {
							t.stop();
							t = null;
						}
						Pattern pattern = Pattern.compile("^"+regExValidator+"$");
						Matcher matcher = pattern.matcher(getEntryText());
						if( !matcher.find() ) {
							// FALLO VALIDACION...
							setEntryMessage( (regExMessage != null ? regExMessage : "Valor ingresado inválido"), true);
							entryMessage.setVisible(true);
							// RESET TIMER
							if( inputTimeout != 0 ) {
								t = new javax.swing.Timer(inputTimeout*1000, timerInputTimeout);
								isInputTimeout = false;                               
								t.start();
							}
							Motor.logger.info( "No valida RegExp. Reingresa." );
							continue;
						}
					}
					break;
				} catch (InterruptedException e) {}
			}
			if( t != null ) {
				t.stop();
				if( isInputTimeout ) {
					key = 1;
				}
				t = null;
			}
		}
		isInputTimeout = false;
		return key;
	}

	public void setTrxTitle(String trxTitle) {
		setTitle(Motor.propCajas.getProperty("mainTopTitle") + " - " + trxTitle);
	}

	public void acceptEscape( boolean accept ) {
		acceptEscape = accept;
	}

	public void createTRV() {
		Motor.logger.info( "Create de TRV" );
		if( operTRV == null ) {
			Motor.logger.info( "Creo TRV" );
			operTRV = new OperTRV();
		} else {
			Motor.logger.info( "Uso TRV existente" );            
		}
	}

	public void removeTRV() {
		Motor.logger.info( "Remove de TRV" );
		operTRV = null;
	}

	public void paintButtons( String[] buttons ) {      
		JButton button = null;
		String extra = null;
		for (int i = 0; i < buttons.length && i < 9; i++) {
			switch( i ) {
			case 0: button = pf1; extra = "<br>F1"; break;
			case 1: button = pf2; extra = "<br>F2"; break;
			case 2: button = pf3; extra = "<br>F3"; break;
			case 3: button = pf4; extra = "<br>F4"; break;
			case 4: button = pf5; extra = "<br>F5"; break;
			case 5: button = pf6; extra = "<br>F6"; break;
			case 6: button = pf7; extra = "<br>F7"; break;
			case 7: button = pf8; extra = "<br>F8"; break;
			case 8: button = pf9; extra = "<br>F9"; break;
			}
			if( buttons[i].length() > 0 ) {
				button.setText( "<html><div align=\"center\">" + buttons[i] + extra );
			} else {
				button.setText( "" );
			}
		}
		if( buttons.length > 0 && 
				(buttons[9].length() > 0 || 
						buttons[10].length() > 0 ||
						buttons[11].length() > 0) ) {

			for (int i = 9; i < buttons.length && i < 12; i++) {
				switch( i ) {
				case 9: button = pf10; extra = "<br>F10"; break;
				case 10: button = pf11; extra = "<br>F11"; break;
				case 11: button = pf12; extra = "<br>F12"; break;
				}
				if( buttons[i].length() > 0 ) {
					button.setText( "<html><div align=\"center\">" + buttons[i] + extra );
				} else {
					button.setText( "" );
				}
			}
		} else {
			pf10.setText("");
			pf11.setText("");
			pf12.setText("");
		}
	}

	public String getEntryMessage() {
		return entryMessage.getText();
	}

	public void setEntryMessage(String text, boolean visible) {
		isEntryMessageVisible = visible;
		entryMessage.setText( text );
	}

	public void setEntryCalendar( java.util.Date date, boolean visible) {
		isEntryCalendarVisible = visible;
		entryCalendar.setDate( date );

		setVisibleEntryText(false);
		setVisibleEntryPassword(false);
		setVisibleEntryList(false);
		setVisibleEntryTable(false);
		setVisibleEntryTextArea(false);
	}

	public java.util.Date getEntryCalendarDate() {
		return entryCalendar.getSelectedDate();
	}

	public String getEntryTitle() {
		return entryTitle.getText();
	}

	public void setEntryTitle(String text, boolean visible) {
		isEntryTitleVisible = visible;
		entryTitle.setText(text);
	}

	public String getEntryTextLabel() {
		return entryTextLabel.getText();
	}

	public void setEntryTextLabel(String text, boolean visible) {
		isEntryTextLabelVisible = visible;
		entryTextLabel.setText(text);
	}

	public String getEntryText() {
		return realTextField;
	}

	public void setEntryText(String text, boolean visible, boolean readOnly, 
			boolean password, String regExp, String message ) {
		isEntryTextVisible = visible;
		isEntryTextPassword = password;
		entryPassword.setText(text);
		entryText.setText(text);
		realTextField = text;
		entryPassword.setEnabled(!readOnly);
		entryText.setEnabled(!readOnly);             
		regExValidator = regExp;         
		regExMessage = message;
		// SELECCIONO EL TEXTO
		if( isEntryTextPassword ) {
			if(maxPasswordSize != null) {
				entryPassword.setColumns(maxPasswordSize);
			}
			entryPassword.setSelectionStart(0);
			entryPassword.setSelectionEnd(text.length());             
		} else {
			entryText.setSelectionStart(0);
			entryText.setSelectionEnd(text.length());
		}
		setVisibleEntryList(false);
		setVisibleEntryTable(false);
		setVisibleEntryTextArea(false);
		setVisibleEntryCalendar(false);
	}


	public String getEntryTextArea() {
		return entryTextArea.getText();
	}

	public void setEntryTextArea( String text, boolean visible ) {
		isEntryTextAreaVisible = visible;
		entryTextArea.setText(text);
		entryTextArea.setEditable(false);
		setVisibleEntryList(false);
		setVisibleEntryTable(false);
		setVisibleEntryText(false);
		setVisibleEntryPassword(false);
		setVisibleEntryCalendar(false);
	}

	public String[] getEntryList() {
		return this.list;
	}

	public void setEntryList(String[] list, boolean visible, boolean multiple) {
		this.list = list;
		isEntryListVisible = visible;
		entryList.clearSelection();
		entryList.setSelectionMode( multiple ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION );
		entryList.setListData(list);
		if( list.length > 0 ) {
			entryList.setSelectedIndex(0);
		}
		setVisibleEntryText(false);
		setVisibleEntryPassword(false);
		setVisibleEntryTable(false);
		setVisibleEntryTextArea(false);
		setVisibleEntryCalendar(false);
	}
	
	public void setEntryList(String[] list, boolean visible, boolean multiple, int index) {
        this.list = list;
        isEntryListVisible = visible;
        entryList.clearSelection();
        entryList.setSelectionMode( multiple ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION );
        entryList.setListData(list);
        if( list.length > 0 ) {
            entryList.setSelectedIndex(index);
        }
        setVisibleEntryText(false);
        setVisibleEntryPassword(false);
        setVisibleEntryTable(false);
        setVisibleEntryTextArea(false);
        setVisibleEntryCalendar(false);
    }

	@SuppressWarnings("serial")
	public void setEntryTable(String[] titles, Object[][] rows, boolean visible ) {
		isEntryTableVisible = visible;
		if( visible ) {
			entryTable = new JTable( rows, titles ) {
                // FILAS NO EDITABLES!
                public boolean isCellEditable(int rowIndex, int vColIndex) {
                if ( getColumnClass(vColIndex) == Boolean.class  ) {
                    return true;
                } else {
                    return false;
                }                               }
            /*
             * JTable uses this method to determine the default renderer/
             * editor for each cell.  If we didn't implement this method,
             * then the last column would contain text ("true"/"false"),
             * rather than a check box.
             */
            public Class getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }
        };
			entryTable.setFont(new Font("Courier", Font.PLAIN, 12));
			entryTablePane.getViewport().add(entryTable);
			entryTable.setFillsViewportHeight(true);
			if( entryTable.getComponentCount() > 0 ) {
				entryTable.setRowSelectionInterval(0, 0);
			}
			entryTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter-key");
			entryTable.getActionMap().put("enter-key", new EnterAction());
		}

		setVisibleEntryText(false);
		setVisibleEntryPassword(false);
		setVisibleEntryList(false);
		setVisibleEntryTextArea(false);
		setVisibleEntryCalendar(false);
	}

	public void setEntryTableIndex( int index ) {
		if( entryTable.getComponentCount() > 0 ) {
			entryTable.setRowSelectionInterval(index, index);
			entryTable.scrollRectToVisible(entryTable.getCellRect(index, 0, false));
		}    	 
	}

	public int getEntryTableIndex() {
		return entryTable.getSelectedRow();
	}

	public int getEntryListIndex() {
		return entryList.getSelectedIndex();
	}
	public int[] getEntryListIndeces() {
		return entryList.getSelectedIndices();
	}

	public void cleanEntryList() {
		entryList.setListData(new Vector<String>());
	}

	public void setInputTimeout( int timeout ) {
		inputTimeout = timeout;
	}

	public void hideAllEntries() {
		enter.setVisible(false);

		entryTextLabel.setVisible(false);
		isEntryTextLabelVisible = false;

		entryTitle.setVisible(false);
		isEntryTitleVisible = false;

		entryMessage.setVisible(false);
		isEntryMessageVisible = false;

		entryCalendar.setVisible(false);
		isEntryCalendarVisible = false;

		entryText.setVisible(false);
		entryText.setEnabled(true);
		isEntryTextVisible = false;
		isEntryTextPassword = false;
		entryPassword.setVisible(false);
		entryPassword.setEnabled(true);

		entryTextArea.setVisible(false);
		entryTextAreaPane.setVisible(false);
		isEntryTextAreaVisible = false;

		entryList.setVisible(false);
		entryListPane.setVisible(false);
		isEntryListVisible = false;

		entryTable.setVisible(false);
		entryTablePane.setVisible(false);
		isEntryTableVisible = false;
	}

	public void clearAllEntries() {
		entryTextLabel.setText("");
		entryTitle.setText("");
		entryMessage.setText("");
		entryText.setText("");
		entryTextArea.setText("");
		entryPassword.setText("");
		entryList.setListData(new Vector<String>());
		inputTimeout = 0;
		regExValidator = null;
		regExMessage   = null;
	}

	public OperTRV getOperTRV() {
		return operTRV;
	}

	public Datos getDatos() {
		return datos;
	}

	public void hideEnter() {
		showEnter = false;
	}
	public void showEnter() {
		showEnter = true;
	}

	public int showMyConfirmDialog( String title, String message ) {
		return new MyConfirmDialog( title, message).showDialog();
	}
	
	
	public String openSolicitaTrajetas(  ) {
		return new SelectorTarjeta().showOption();
	}
	

	public void showBusyWindow( String title, String message ) {
		// VENTANA DE BUSY ASINCRONICA
		bw = new BusyWindow( title, message );
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bw.setup();
				bw.setVisible(true);
			}
		});

	}

	public void hideBusyWindow() {
		if( bw != null ) {
			bw.dispose();
			bw = null;    		 
		}
	}

	public void showTableView( String[] titles, Object[][] rows, boolean[] editable ) {
		editTV = new boolean[ editable.length ];
		for( int i = 0; i < editable.length; ++i ) {
			editTV[i] = editable[i];
		}
		
		if( panelTopTV == null ) {
			// HAGO INVISIBLE LOS PANELES PRINCIPALES
			top.setVisible(false);
			center.setVisible(false);
			bottom.setVisible(false);
			// CREO LOS NUEVOS PANELES
			panelTopTV = new JPanel();
			panelTopTV.setBackground(Color.decode(Motor.propCajas.getProperty("mainTopBgColor")));
			panelTopTV.setPreferredSize(new Dimension(600,400));
			panelTopTV.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			panelTopTV.setLayout(new GridLayout(1, 1, 5, 5));
			// panelTop.setPreferredSize(new Dimension(600,200));
			// TABLE
			entryTablePaneTv.getViewport().add(entryTableTv);
			entryTableTv.setFillsViewportHeight(true);
			entryTablePaneTv.setPreferredSize(new Dimension(600,420));
			// entryTablePane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			panelTopTV.add(entryTablePaneTv);
			mainPanel.add(panelTopTV);
	
			panelBottomTV = new JPanel();
			panelBottomTV.setBackground(Color.decode(Motor.propCajas.getProperty("mainCenterButtonsBgColor")));
			panelBottomTV.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			panelBottomTV.setLayout(new GridLayout(3, 3, 5, 5));
			panelBottomTV.setPreferredSize(new Dimension(600,180));
			// EL ORDEN DE LOS BOTONES ESTA DADO POR SU ORDEN AL AGREGARLOS
			// RECORRE POR FILA
			if( Motor.propCajas.getProperty("buttonOrder").equalsIgnoreCase("column") ) {
				panelBottomTV.add(pf1); panelBottomTV.add(pf4); panelBottomTV.add(pf7);
				panelBottomTV.add(pf2); panelBottomTV.add(pf5); panelBottomTV.add(pf8);
				panelBottomTV.add(pf3); panelBottomTV.add(pf6); panelBottomTV.add(pf9);
			} else {
				panelBottomTV.add(pf1); panelBottomTV.add(pf2); panelBottomTV.add(pf3);
				panelBottomTV.add(pf4); panelBottomTV.add(pf5); panelBottomTV.add(pf6);
				panelBottomTV.add(pf7); panelBottomTV.add(pf8); panelBottomTV.add(pf9);
			}
			mainPanel.add(panelBottomTV);
		}

		entryTableTv = new JTable( rows, titles ) {
			// FILAS EDITABLES!			
			public boolean isCellEditable(int rowIndex, int colIndex) {
				if( colIndex < editTV.length && editTV[colIndex] ) {
					return true;
				} else {
					return false;
				}
			}
		};
		entryTablePaneTv.getViewport().add(entryTableTv);
		entryTableTv.setFillsViewportHeight(true);
		if( entryTableTv.getComponentCount() > 0 ) {
			entryTableTv.setRowSelectionInterval(0, 0);
		}
		entryTableTv.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter-key");
		entryTableTv.getActionMap().put("enter-key", new EnterAction());

		entryTableTv.requestFocusInWindow();
	}

	public void dismissTableView() {
		if( panelTopTV == null ) {
			return;
		}

		panelTopTV.setVisible(false);
		panelBottomTV.setVisible(false);
		panelTopTV.removeAll();
		panelBottomTV.removeAll();
		panelTopTV = null;
		panelBottomTV = null;

		top.setVisible(true);
		center.setVisible(true);
		bottom.setVisible(true);

		if( Motor.propCajas.getProperty("buttonOrder").equalsIgnoreCase("column") ) {
			panelCenterRight.add(pf1); panelCenterRight.add(pf4); panelCenterRight.add(pf7);
			panelCenterRight.add(pf2); panelCenterRight.add(pf5); panelCenterRight.add(pf8);
			panelCenterRight.add(pf3); panelCenterRight.add(pf6); panelCenterRight.add(pf9);
		} else {
			panelCenterRight.add(pf1); panelCenterRight.add(pf2); panelCenterRight.add(pf3);
			panelCenterRight.add(pf4); panelCenterRight.add(pf5); panelCenterRight.add(pf6);
			panelCenterRight.add(pf7); panelCenterRight.add(pf8); panelCenterRight.add(pf9);
		}
	}

	public void setTableViewIndex( int index ) {
		if( entryTableTv.getComponentCount() > 0 ) {
			entryTableTv.setRowSelectionInterval(index, index);
			entryTableTv.scrollRectToVisible(entryTableTv.getCellRect(index, 0, false));
		}    	 
	}

	public int getTableViewIndex() {
		return entryTableTv.getSelectedRow();
	}
	

	public void setMaxPasswordSize(Integer maxPasswordSize) {
		this.maxPasswordSize = maxPasswordSize;
	}




	///////////////////////////////////////////////////////////////////////
	//                     INNER CLASSES PARA MANEJO DE EVENTOS          //
	///////////////////////////////////////////////////////////////////////
	/**
	 * @author abertens
	 *
	 */
	class CloseAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			/*
           if( JOptionPane.showConfirmDialog(null,
                    "Seguro desea salir?", "Confirme por favor",  
                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION ) {
              return;
           }
			 */
			 ParamSet pList = Base.getParamSet( "posDat" );
			
			if( new MyConfirmDialog( "Confirme por favor","Seguro desea salir?").showDialog()  
					== JOptionPane.NO_OPTION ) {
				return;
			}
			
			if(pList.getStringValue("CierreOffline").equals("no") && pList.getStringValue("AbiertaSinConexion").equals("si")){
				JOptionPane.showMessageDialog(null, "Debe Realizar el Cierre de la Caja Contingencia", "Info", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			synchronized( signal ) {
				signal.notify();
			}
		}
	}

	/**
	 * @author abertens
	 *
	 */
	class EnterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_ENTER;
			// JOptionPane.showMessageDialog(mainPanel, "ENTER", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( enter.isVisible() ) {
			       if( isEntryTextPassword() ) {
			               realTextField = new String(entryPassword.getPassword());
			       }
			       else {
			               realTextField = entryText.getText();
			       }
			       synchronized (userEvent ) { userEvent.notify(); }
			}
		}
	}

	/**
	 * @author abertens
	 *
	 */
	class EscAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_ESCAPE;
			if( acceptEscape ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf1Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F1;
			// JOptionPane.showMessageDialog(mainPanel, "PF1", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			// dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
			if( pf1.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}

	/**
	 * @author abertens
	 *
	 */
	class Pf2Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F2;
			// JOptionPane.showMessageDialog(mainPanel, "PF2", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf2.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf3Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F3;
			// JOptionPane.showMessageDialog(mainPanel, "PF3", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf3.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf4Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F4;
			// JOptionPane.showMessageDialog(mainPanel, "PF4", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf4.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf5Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F5;
			// JOptionPane.showMessageDialog(mainPanel, "PF5", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf5.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf6Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F6;
			// JOptionPane.showMessageDialog(mainPanel, "PF6", pf6.getText(), JOptionPane.INFORMATION_MESSAGE);
			if( pf6.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf7Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F7;
			// JOptionPane.showMessageDialog(mainPanel, "PF7", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf7.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf8Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F8;
			// JOptionPane.showMessageDialog(mainPanel, "PF8", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf8.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf9Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F9;
			// JOptionPane.showMessageDialog(mainPanel, "PF9", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf9.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf10Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F10;
			// JOptionPane.showMessageDialog(mainPanel, "PF10", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf10.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf11Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F11;
			// JOptionPane.showMessageDialog(mainPanel, "PF11", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf11.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class Pf12Action extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			key = KeyEvent.VK_F12;
			// JOptionPane.showMessageDialog(mainPanel, "PF12", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			if( pf12.getText().length() > 0 ) {
				synchronized (userEvent) { userEvent.notify(); }
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class AltEAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if( documentCart.isVisible() ) {
				int i = documentCart.getSelectedIndex();
				if( i == -1 ) {
					JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un ítem de la lista", "Mensaje", JOptionPane.WARNING_MESSAGE );
					return;
				}
				if( JOptionPane.showConfirmDialog(null,
						"Seguro desea eliminar?", "Confirme por favor",  
						JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION ) {
					return;
				}
				// RECORRO EL CARRO DE COMPRAS BUSCANDO EL INDICE i
				CarroCompra cc = operTRV.getCarroCompras();
				//System.out.println("Tamanho Carro Compras: "+cc.getDocumentos().size());
				DocumentoPago doc = cc.getDocument(i);
				//System.out.println("Documento en el Carro Compras: "+doc.getNombre());
				//System.out.println("Documento es eliminable ??: "+doc.isEliminable(cc));
				if(!doc.isEliminable(cc)){
					showShoppingCart( true );
					// cbriones: se agrega para validar el focus !!
					documentCart.setSelectedIndex(i);
					return;
				}
				if (cc.getDocument(i).getTipoRegistro().equalsIgnoreCase("SC")){
					cc.removeAllDocument();
					
				}else{
					
					if (cc.getDocument(i).getTipoRegistro().equalsIgnoreCase("PagoDeudaNV")){
						JOptionPane.showMessageDialog(mainPanel, "Se eliminaran todos ítem de la lista", "Mensaje", JOptionPane.WARNING_MESSAGE );
						cc.removeAllDocument();
						
					}else{
						cc.removeDocument(i);
						}
				}
				showShoppingCart( true );
				documentCart.setSelectedIndex(0);
				return;
			}
			
			if( paymentCart.isVisible() ) {
				int i = paymentCart.getSelectedIndex();
				if( i == -1 ) {
					JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un ítem de la lista", "Mensaje", JOptionPane.WARNING_MESSAGE );
					return;
				}
				if( JOptionPane.showConfirmDialog(null,
						"Seguro desea eliminar?", "Confirme por favor",  
						JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION ) {
					return;
				}
				// RECORRO EL CARRO DE MEDIOS DE PAGO BUSCANDO EL INDICE i
				CarroMPagos cmp = operTRV.getCarroMediosPago();
				// Se debe validar que para edicion no se puede eliminar el carro de medios de pago..
				if(Base.getEdicion()){
					JOptionPane.showMessageDialog(mainPanel, "Existe una Regularizacion de Pago, no se permite eliminar el Medio de pago", "Mensaje", JOptionPane.WARNING_MESSAGE );
					showPaymentCart( true );
					return;
				}
				
				try {
					cmp.removeMedioPago(i);
				} catch( Exception e1 ) {
					JOptionPane.showMessageDialog(mainPanel, e1.toString(), "Error al eliminar medio de pago", JOptionPane.ERROR_MESSAGE );
				}
				showPaymentCart( true );
				return;
			}
		}
	}
	/**
	 * @author abertens
	 *
	 */
	class AltDAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if( documentCart.isVisible() ) {
				int i = documentCart.getSelectedIndex();
				if( i == -1 ) {
					JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un ítem de la lista", "Mensaje", JOptionPane.WARNING_MESSAGE );
					return;
				}
				// RECORRO EL CARRO DE COMPRAS BUSCANDO EL INDICE i
				CarroCompra cc = operTRV.getCarroCompras();
				ArrayList<DocumentoPago> ldp = cc.getDocumentos();
				int j = 0;
				for( DocumentoPago dp: ldp ) {
					if( i == j ) {
						JOptionPane.showMessageDialog(mainPanel, dp.toString(), "Detalle de documento", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					++j;
				}
			}
			if( paymentCart.isVisible() ) {
				int i = paymentCart.getSelectedIndex();
				if( i == -1 ) {
					JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un ítem de la lista", "Mensaje", JOptionPane.WARNING_MESSAGE );
					return;
				}
				// RECORRO EL CARRO DE MEDIOS DE PAGO BUSCANDO EL INDICE i
				CarroMPagos cmp = operTRV.getCarroMediosPago();
				ArrayList<MedioPago> lmp = cmp.getMediosPago();
				int j = 0;
				for( MedioPago mp: lmp ) {
					if( i == j ) {
						JOptionPane.showMessageDialog(mainPanel, mp.toString(), "Detalle de documento", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					++j;
				}
			}
		}
	}
}
