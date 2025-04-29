package cl.hyh.redpagos.caja.base;

import java.io.Serializable;

/**
 * Datos para el Guardado de Boleta y voucherTBK 
 * @author Juan Carlos Gomez
 *
 */
public class DatosFileNet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4783462069983336952L;
	private String FilesName;
	private String NumOperacion;
	private boolean Filenet;
	private String tipo_operacion;
	private String propietario;
	private String codigo_sesion;
	private String tipo_comprobante;
	private String tipo_copia;
	private String str_comprobante;
	private String email_para;
	private String codusuario_envia;

	
	public String getFilesName() {
		return FilesName;
	}
	public void setFilesName(String filesName) {
		FilesName = filesName;
	}
	public String getNumOperacion() {
		return NumOperacion;
	}
	public void setNumOperacion(String numOperacion) {
		NumOperacion = numOperacion;
	}
	public boolean isFilenet() {
		return Filenet;
	}
	public void setFilenet(boolean filenet) {
		Filenet = filenet;
	}
	public String getTipo_operacion() {
		return tipo_operacion;
	}
	public void setTipo_operacion(String tipo_operacion) {
		this.tipo_operacion = tipo_operacion;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	public String getCodigo_sesion() {
		return codigo_sesion;
	}
	public void setCodigo_sesion(String codigo_sesion) {
		this.codigo_sesion = codigo_sesion;
	}
	public String getTipo_comprobante() {
		return tipo_comprobante;
	}
	public void setTipo_comprobante(String tipo_comprobante) {
		this.tipo_comprobante = tipo_comprobante;
	}
	public String getTipo_copia() {
		return tipo_copia;
	}
	public void setTipo_copia(String tipo_copia) {
		this.tipo_copia = tipo_copia;
	}
	public String getStr_comprobante() {
		return str_comprobante;
	}
	public void setStr_comprobante(String str_comprobante) {
		this.str_comprobante = str_comprobante;
	}
	public String getEmail_para() {
		return email_para;
	}
	public void setEmail_para(String email_para) {
		this.email_para = email_para;
	}
	public String getCodusuario_envia() {
		return codusuario_envia;
	}
	public void setCodusuario_envia(String codusuario_envia) {
		this.codusuario_envia = codusuario_envia;
	}
    
}
