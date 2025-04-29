package cl.hyh.redpagos.caja.base;


import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Clase que funciona con metodos estaticos que formatea distintos inputs para la vista
 * @author Felipe Hernandez - Hernandez e Hidalgo Ltda.
 *
 */
public class Format {
    
    /**
     * Funcion que recibe un monto en formato long y lo transforma a un 
     * string con la forma $xx.xxx.xxx.xxx
     * @param monto valor a ser formateado
     * @return
     */
    public static String formatMonto( long monto ) {
    	boolean neg = false;
    	if(monto < 0){
    		neg = true;
    		monto = monto * -1;
    	}
        char []aux = Long.toString(monto).toCharArray();
        String []montoFormat = null;
        if(monto/1000000000 > 0){
            montoFormat = new String [aux.length + 3 ];
        }
        else if(monto/100000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/10000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/1000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/100000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else if(monto/10000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else if(monto/1000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else{
            montoFormat = new String [aux.length];
        }
        int j = montoFormat.length - 1;
        int pos = 0;
        for(int i = aux.length - 1; i >= 0; i--){            
            if((pos)%3 == 0 && i != aux.length - 1 ){
                montoFormat[j] = ".";
                j--;
            }
            montoFormat[j] = aux[i] + "";
            j--;
            pos++;
        }
        String temp = "$";
        for(int i = 0; i < montoFormat.length; i++ ){
            temp = temp + montoFormat[i];
        }
        return temp;
    }
    
    /**
     * Funcion que recibe un monto en formato long y lo transforma a un 
     * string con la forma $xx.xxx.xxx.xxx y permite montos negativos
     * @param monto valor a ser formateado
     * @return
     */
    public static String formatMontoAjustado( long monto ) {
    	boolean neg = false;
    	if(monto < 0){
    		neg = true;
    		monto = monto * -1;
    	}
        char []aux = Long.toString(monto).toCharArray();
        String []montoFormat = null;
        if(monto/1000000000 > 0){
            montoFormat = new String [aux.length + 3 ];
        }
        else if(monto/100000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/10000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/1000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/100000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else if(monto/10000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else if(monto/1000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else{
            montoFormat = new String [aux.length];
        }
        int j = montoFormat.length - 1;
        int pos = 0;
        for(int i = aux.length - 1; i >= 0; i--){            
            if((pos)%3 == 0 && i != aux.length - 1 ){
                montoFormat[j] = ".";
                j--;
            }
            montoFormat[j] = aux[i] + "";
            j--;
            pos++;
        }
        String temp = "$";
        if(neg){
        	temp = "-$";
    	}
        for(int i = 0; i < montoFormat.length; i++ ){
            temp = temp + montoFormat[i];
        }
        return temp;
    }
    
    public static String formatMontoPantalla( long monto ) {
    	boolean neg = false;
    	if(monto < 0){
    		neg = true;
    		monto = monto * -1;
    	}
        char []aux = Long.toString(monto).toCharArray();
        String []montoFormat = null;
        if(monto/1000000000 > 0){
            montoFormat = new String [aux.length + 3 ];
        }
        else if(monto/100000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/10000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/1000000 > 0){
            montoFormat = new String [aux.length + 2 ];
        }
        else if(monto/100000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else if(monto/10000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else if(monto/1000 > 0){
            montoFormat = new String [aux.length + 1 ];
        }
        else{
            montoFormat = new String [aux.length];
        }
        int j = montoFormat.length - 1;
        int pos = 0;
        for(int i = aux.length - 1; i >= 0; i--){            
            if((pos)%3 == 0 && i != aux.length - 1 ){
                montoFormat[j] = ".";
                j--;
            }
            montoFormat[j] = aux[i] + "";
            j--;
            pos++;
        }
        String temp = "";
        for(int i = 0; i < montoFormat.length; i++ ){
            temp = temp + montoFormat[i];
        }
        if(neg)
        	temp = "-" + temp;
        return temp;
    }
    public static String formatFecha( String fecha ){
        String agno = fecha.substring( 0 , 4 );
        String mes = fecha.substring( 5 , 7 );
        String dia = fecha.substring( 8 , 10);
        
        return dia + "/" + mes + "/" + agno;
    }
    public static String formatFechaBoleta( String fecha ){
        String agno = fecha.substring( 0 , 4 );
        String mes = fecha.substring( 4 , 6 );
        String dia = fecha.substring( 6 , 8);
        
        return dia + "-" + mes + "-" + agno;
    }
    public static String formatPercent(String porcentaje){
        try{
            int ent = Integer.parseInt( porcentaje.substring(0,3) );
            int dec = Integer.parseInt( porcentaje.substring( 3 , porcentaje.length()) );
            String s = Integer.toString(ent);
            String t = Integer.toString( dec );
            
            return s + "." + t + "%";
        }
        catch(Exception e){
            return "";
        }
    }
    public static String formatFechaPresto( String fecha ){
        String agno = fecha.substring( 0 , 2 );
        String mes = fecha.substring( 2 , 4 );
        String dia = fecha.substring( 4 , 6);
        
        return dia + "/" + mes + "/" + agno;
    }
    public static String formatFechaRipley( String fecha ){
        String agno = fecha.substring( 0 , 4 );
        String mes = fecha.substring( 4 , 6 );
        String dia = fecha.substring( 6 , 8);
        
        return dia + "/" + mes + "/" + agno;
    }
    public static String formatTarjeta( String tarjeta ){
        String priv = tarjeta.substring(0,tarjeta.length()-4);
        String ast = "";
        for(int i = 0 ; i < priv.length(); i++){
            ast += "*";
        }
        return ast + tarjeta.substring(tarjeta.length()-4);
    }
    public static String formatHoraPresto( String hora ){
        if(hora.length() != 6){
            return hora;
        }
        String hour = hora.substring( 0 , 2 );
        String minuto = hora.substring( 2 , 4 );
        String segundo = hora.substring( 4 , 6);
        
        return hour + ":" + minuto + ":" + segundo;
    }
    public static String formatHoraFyH( String fya ){
        String hour = fya.substring( 8 , 10 );
        String minuto = fya.substring( 10 , 12 );
        String segundo = fya.substring( 12 , 14);
        
        return hour + ":" + minuto + ":" + segundo;
    }
    public static String formatFechaServicio( String fecha ){
        if(fecha.length() != 8){
            return fecha;
        }
        String agno = fecha.substring( 0 , 4 );
        String mes = fecha.substring( 4 , 6 );
        String dia = fecha.substring( 6 , 8);
        
        return agno + "-" + mes + "-" + dia;
    }
    public static String formatFechaIda( String fecha ){
        fecha = fecha.trim();
        String []aux = fecha.split("-");
        if(aux.length != 3){
            return "00010101";
        }
        return aux[0] + aux[1] + aux[2];
    }
    
    /*
     * [REspinoza] Quitar caracteres raros en respuesta Multicard/Tbk
     */
    public static String formatMsjTbk( String mensaje ){
        mensaje = mensaje.replaceAll("%20", " ");
        mensaje = mensaje.replaceAll("%2F", "/");
        mensaje = mensaje.replaceAll("%26", " ");
        
    	if (mensaje == null || mensaje.equalsIgnoreCase("")) {
    		mensaje = "Transacción Rechazada";
    	}
        
        return mensaje;
    }
    
    /*
     * [REspinoza] Mensaje muy largo en joptionpane Transbank
     */
    public static JScrollPane formatText(String mensaje) {
    	JScrollPane scrollPane;
    	JTextArea text = new JTextArea();
    	
        	try {
        		text.setText(mensaje);
        		text.setEditable(false);
        		text.setOpaque(false);
        		text.setLineWrap(true);
        		text.setWrapStyleWord(true);
        		text.setSize(500, 100);
        		text.setBorder(null);
        		
        		scrollPane = new JScrollPane(text);
        		scrollPane.setPreferredSize( new Dimension( 500, 50 ) );
        	}catch(Exception e) {
        		scrollPane = new JScrollPane(text);
        	}
   
        return scrollPane;
    }
    
    /*
     * [REspinoza] Obtener mensaje retornado por transbank o xcash segun corresponda
     */
	public static String getMesageTbk(URLString urlVC) {
		String msgTbkReturn = "";
		
//	   	 if(!urlVC.getValor("TXCRET").equalsIgnoreCase("0")) {
//			 msgTbkReturn = urlVC.getValor("TXCGLO");
//		 } else {
//			 if (Integer.parseInt(urlVC.getValor("AUTRET")) <= 9) {
//				 msgTbkReturn = urlVC.getValor("AUTGLO");
//			 } else {
//				 msgTbkReturn = "";
//			 }
//		 }
		
		 if (!urlVC.getValor("TXCRET").equalsIgnoreCase("00")) { //Error XCash, trx no fue a TBK
			 msgTbkReturn = urlVC.getValor("TXCGLO");
		 } else { //Transaccion fue a TBK, mostrar glosa de TBK
			 msgTbkReturn = urlVC.getValor("AUTGLO");
		 }

	   	 return msgTbkReturn;
	}
}
