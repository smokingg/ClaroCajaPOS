package cl.hyh.redpagos.caja.base;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Datos para el Guardado de Boleta y voucherTBK 
 * @author Juan Carlos Gomez
 *
 */
public class DatosEnvioCorreo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8362067683443204948L;
	ArrayList<String> FilesName = new ArrayList<String>();
	private String Emails;
	private String NumOperacion;

	
	
	public ArrayList<String> getFilesName() {
		return FilesName;
	}
	public void setFilesName(ArrayList<String> filesName) {
		FilesName = filesName;
	}
	public String getEmails() {
		return Emails;
	}
	public void setEmails(String emails) {
		Emails = emails;
	}
	public String getNumOperacion() {
		return NumOperacion;
	}
	public void setNumOperacion(String numOperacion) {
		NumOperacion = numOperacion;
	}

    
}
