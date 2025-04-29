package cl.hyh.redpagos.caja.comm;

public class MainTest {
	public static void main(String[] args) {
		System.out.println();
        PrintPDF pdf = new PrintPDF();
        pdf.generatePDFWhitJasper("C:/Users/sebastian.sepulveda/Downloads/temporal.html", "C:/Users/sebastian.sepulveda/Downloads/temporal.pdf");
        pdf.generatePDFWhitJasper("C:/Users/sebastian.sepulveda/Downloads/temporal 1.html", "C:/Users/sebastian.sepulveda/Downloads/temporal 1.pdf");
        pdf.generatePDFWhitJasper("C:/Users/sebastian.sepulveda/Downloads/temporalagu.html", "C:/Users/sebastian.sepulveda/Downloads/temporalagu.pdf");
        pdf.generatePDFWhitJasper("C:/Users/sebastian.sepulveda/Downloads/temporalcliente.html", "C:/Users/sebastian.sepulveda/Downloads/temporalcliente.pdf");
        pdf.generatePDFWhitJasper("C:/Users/sebastian.sepulveda/Downloads/temporalcaja.html", "C:/Users/sebastian.sepulveda/Downloads/temporalcaja.pdf");
	}
}
