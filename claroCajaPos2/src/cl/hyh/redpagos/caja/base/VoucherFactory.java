package cl.hyh.redpagos.caja.base;

public class VoucherFactory {
	
	
	/**
	 * Metodo constructor privado, la clase no puede ser instanciada
	 */
	private VoucherFactory() {
		super();
	}
	
	/**
	 * Metodo que recupera la instancia del voucher a utilizar dependiendo del parametro 
	 * recibido para la caja en uno
	 * @return instancia de Voucher
	 */
	public static Voucher getInstance() {
		// TODO agregar logica para consultar por el parametro de caja para saber si utiliza
		// la impresora termica o con PDF
		boolean imprimePdf = Boolean.TRUE;
		if(imprimePdf) {
			return new VoucherPDF();
		}else {
			return new Voucher();
		}
	}

}
