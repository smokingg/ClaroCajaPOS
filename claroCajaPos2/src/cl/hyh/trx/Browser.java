package cl.hyh.trx;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cl.hyh.redpagos.caja.base.Base;


public class Browser{
	private static String url;
	
	public Browser(String userName, String urlin) {
		url = urlin + "?step=1&tipoUsuario=2&username=" + userName;
		Base.logger.error( "url Chat : " + url);
	}
	
    public void abrirChat() {
    	Base.logger.error( "Iniciando Chat ....");
    	
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (URISyntaxException e2) {
            	
            	 e2.printStackTrace();
				// TODO: handle exception
			}
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
	
//    public static void main(String[] args) {
//        if(Desktop.isDesktopSupported()){
//            Desktop desktop = Desktop.getDesktop();
//            try {
//                desktop.browse(new URI(url));
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            catch (URISyntaxException e2) {
//            	
//            	 e2.printStackTrace();
//				// TODO: handle exception
//			}
//        }else{
//            Runtime runtime = Runtime.getRuntime();
//            try {
//                runtime.exec("xdg-open " + url);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
}