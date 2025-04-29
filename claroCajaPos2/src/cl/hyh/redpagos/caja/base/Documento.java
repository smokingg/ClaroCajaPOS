package cl.hyh.redpagos.caja.base;

import java.io.Serializable;
import java.util.ArrayList;

import cl.hyh.redpagos.caja.base.parser.DefDocumentoPago;
import cl.hyh.redpagos.caja.base.parser.DefVoucher;

/**
 * Representación interna de los documentos de pago existentes en la aplicacion
 * @author Rafael Hernandez
 *
 */
public class Documento implements Serializable {

    private String nombre;
    private String tipo;
    private long monto;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public long getMonto() {
		return monto;
	}
	public void setMonto(long monto) {
		this.monto = monto;
	}
    
    
}
