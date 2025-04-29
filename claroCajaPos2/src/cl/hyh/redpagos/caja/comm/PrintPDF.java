package cl.hyh.redpagos.caja.comm;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.xerces.impl.dv.util.Base64;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import cl.hyh.base.Motor;
import cl.hyh.redpagos.caja.base.Base;
import cl.hyh.redpagos.caja.base.ParamSet;
import cl.hyh.redpagos.caja.pos.BasePos;
import cl.hyh.redpagos.caja.pos.PosDeviceException;
import cl.hyh.redpagos.caja.pos.PosDevicePrinter;
import cl.hyh.redpagos.caja.pos.Tools;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class PrintPDF {

	private List<String> boleta;

	public PrintPDF() {
		boleta = new ArrayList<String>();
	}

	public void printLine(int formato, String mensaje) throws PosDeviceException {
		String mensajeFormato = "<div style='";
		int num = 12;
		int[] n = new int[num];
		int count = 0;
		try {
			n[0] = formato & PosDevicePrinter._MODE_LEFT;
			n[1] = formato & PosDevicePrinter._MODE_CENTER;
			n[2] = formato & PosDevicePrinter._MODE_RIGHT;
			n[3] = formato & PosDevicePrinter._MODE_BOLD;
			n[4] = formato & PosDevicePrinter._MODE_UNDERLINE;
			n[5] = formato & PosDevicePrinter._MODE_SMALL;
			n[6] = formato & PosDevicePrinter._MODE_BIG_1;
			n[7] = formato & PosDevicePrinter._MODE_BIG_2;
			n[8] = formato & PosDevicePrinter._MODE_BIG_3;
			n[9] = formato & PosDevicePrinter._MODE_BIG_4;
			n[10] = formato & PosDevicePrinter._MODE_CUT;
			n[11] = formato & PosDevicePrinter._MODE_LOGO;

			if (n[0] != 0) {
				mensajeFormato = mensajeFormato + "text-align: left;";
				count++;
			}
			if (n[1] != 0) {
				mensajeFormato = mensajeFormato + "text-align: center;";
				count++;
			}
			if (n[2] != 0) {
				mensajeFormato = mensajeFormato + "text-align: right;";
				count++;
			}
			if (n[3] != 0) {
				mensajeFormato = mensajeFormato + "font-weight: bold;";
				count++;
			}
			if (n[4] != 0) {
				if (mensaje.contains("span")) {
					mensajeFormato = mensajeFormato + "border-bottom-style:solid;";
				} else {
					mensajeFormato = mensajeFormato + "text-decoration:underline;";
				}

				count++;
			}
			if (n[5] != 0) {
				// ptr.setRecLineChars(RecLineChars[4]);
				// ptr.setRecLineChars(RecLineChars[6]);;
				mensajeFormato = mensajeFormato + "font-size: 10px;";
				count++;
			}
			if (n[6] != 0) {
				mensajeFormato = mensajeFormato + "font-size: 15px;text-transform: uppercase;";
				count++;
			}
			if (n[7] != 0) {
				mensajeFormato = mensajeFormato + "font-size: 15px;text-transform: uppercase;";
				count++;
			}
			if (n[8] != 0) {
				mensajeFormato = mensajeFormato + "font-size: 20px;text-transform: uppercase;";
				count++;
			}
			if (n[9] != 0) {
				mensajeFormato = mensajeFormato + "font-size: 22px;text-transform: uppercase;";
				count++;
			}
			if (n[10] != 0) {
				mensajeFormato = mensajeFormato + "text-overflow: ellipsis;";
				count++;
			}

			if (count == 0 && "".equals(mensaje)) {
				mensajeFormato = "<div style='margin-top:20px;'/>";
			} else {
				if (count == 0) {
					mensajeFormato = "<div>" + mensaje + "</div>";
				} else {
					mensajeFormato = mensajeFormato + "'>" + mensaje + "</div>";
				}

			}
			// ******* cambios para cuadre de pdf cierre *******
			String cadena1 = mensajeFormato;
			int contador = 0;
			for (int j = 0; j < cadena1.length(); j++) {
				if (cadena1.charAt(j) == '$') {

					contador++;				}
			}

			if (contador == 4) {
				String cadena2 = mensajeFormato;
				cadena2 = cadena2.replace("-$", "X");
				cadena2 = cadena2.replace("<div>", "<div style=\"float:left; width:24%;\">");
				cadena2 = cadena2.replace("<div style='font-weight: bold;'>TOTAL&#160;REMESAS:", "<div style=\"font-weight: bold;float:left; width:24%;\">TOTAL&#160;REMESAS:");
				cadena2 = cadena2.replace("$", "</div><div style=\"float:left; width:19%;\">$");
				cadena2 = cadena2.replace("X", "</div><div style=\"float:left; width:19%;\">-$");
				cadena2 = "<div style=\"display:block;\">" + cadena2 + "</div>";
				mensajeFormato = cadena2;
			}


 
			mensajeFormato = mensajeFormato.replace("&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;:&#160;Cantidad&#160;&#160;&#160;Monto&#160;Total","&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Cantidad&#160;&#160;&#160;Monto&#160;Total");

			mensajeFormato = mensajeFormato.replace("<div style='font-weight: bold;'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;PAGOS&#160;&#160;&#160;&#160;&#160;REMESA&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div>","<div style=\"display:block;\"><div style='font-weight: bold; float:left; width:24%;'>&#160;</div><div style='font-weight: bold; float:left; width:19%;'>PAGOS</div><div style='font-weight: bold; float:left; width:19%;'>REMESA</div><div style='font-weight: bold; float:left; width:19%;'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div><div style='font-weight: bold; float:left; width:19%;'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;</div></div> ");
			mensajeFormato = mensajeFormato.replace("<div style='font-weight: bold;'>OPERACIONES&#160;&#160;:&#160;INGRESADO&#160;REALIZADA&#160;AJUSTE&#160;&#160;&#160;&#160;DIFERENCIA</div>","<div style=\"display:block;\"><div style='font-weight: bold; float:left; width:24%;'>OPERACIONES:</div><div style='font-weight: bold; float:left; width:19%;'>INGRESADO</div><div style='font-weight: bold; float:left; width:19%;'>REALIZADA</div><div style='font-weight: bold; float:left; width:19%;'>AJUSTE</div><div style='font-weight: bold; float:left; width:19%;'>DIFERENCIA</div></div>");

			// ******* fin cambios para cuadre de pdf cierre *******

			boleta.add(mensajeFormato);
			if (n[10] != 0) {
				String boletaF = "";
				// aca se setea el ancho de la linea del voucher !!

				for (int i = 0; i < boleta.size(); i++) {
					boletaF += boleta.get(i);
				}
				System.out.println(boletaF);
				boleta = new ArrayList<String>();
			}
		}
		 catch (Exception e) {
			Tools.logStackTrace(BasePos.logger, e);
			throw new PosDeviceException("Excepcion en la impresion: " + e.toString());
		}
	}

	public String makeRightPrintString(int tipo, String text1, String text2) {
		String mensaje = "";
		if (tipo == 0) {
			mensaje = mensaje + "<p style='font-size: 10px;text-align: left;'>" + text1;
		} else {
			mensaje = mensaje + "<p style='font-size: 15px;text-align: left;'>" + text1;
		}
		try {
			mensaje = mensaje + "<span style='float: right;'>" + text2 + "</span>";
		} catch (Exception ex) {
		}
		return mensaje + "</p>";
	}

	public void printBoleta(String fileName, boolean isLogo) {

		ParamSet posCfg = Base.getParamSet("posCfg");
		String basePathVoucher = posCfg.getStringValue("VoucherDir");

		Base64 base64 = new Base64();

		new ImageIcon().getImage();
		// URL ubi=  getClass().getClassLoader().getResource(Motor.propCajas.getProperty("reportLogo"));
		// Image img =  ImageIO.read(getClass().getClassLoader().getResource(Motor.propCajas.getProperty("reportLogo")));
		// File file = new File( basePathVoucher + "\\logo-doc.jpg" );

		InputStream inputStreamLogo = getClass().getClassLoader().getResourceAsStream(Motor.propCajas.getProperty("reportLogo"));

//		File file=null;
//		try {
//			file = new File(ubi.toURI());
//		} catch (URISyntaxException e1) {
//			// TODO Auto-generated catch block
//			Base.logger.error("Error al ubicar el logo para el PDF.. "+e1.getMessage());
//		}
		// File file = new File("..\\config\\logo-doc.gif");
		// byte[] fileArray = new byte[(int) file.length()];
		byte[] fileArray = null;
		try {
			fileArray = IOUtils.toByteArray(inputStreamLogo);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
//		 System.out.println("archivo: " + file.exists());
//		InputStream inputStream;

		String encodedFile = "";
		try {
//			inputStream = new FileInputStream(file);
			inputStreamLogo.read(fileArray);
			encodedFile = base64.encode(fileArray);
		} catch (Exception e) {
			Base.logger.error("Error al convertir base64 el Logo para el PDF.. " + e.getMessage());
		}

		String data = "<html>\r\n" + 
		"<body style='font-family: Courier;' >\r\n" +
				 "<div align=\"center\" width=\"100%\">\r\n" +
				 "<div style=\"width:485px;display:inline-block;text-align: left;font-size: 12px\">";

		if (isLogo) {
//			encodedFile = "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";
			data += "<div id=\"test2\" name=\"test2\" height=\"20px\" class=\"backgroundB\">&#160;&#160;&#160;&#160;\r\n" +
					 "<img height=\"81px\" width=\"87px\" src=\"data:image/jpg;base64, " + encodedFile + "\" /></div>";

		}

		for (String linea : boleta) {
			data += linea;
		}
		data += "</div>\r\n" +
		 "</div>\r\n" + 
		 "</body>\r\n" + 
		 "</html>";

		System.out.println(data);

		// guardo el HTML en el directorio

		FileOutputStream out = null;
		try {
//			out = new FileOutputStream(basePathVoucher + "\\temporal.html");
//			out.write(data.getBytes());		

			OutputStream os = new FileOutputStream(basePathVoucher + "\\temporal.html");
			OutputStreamWriter bufferedWriter = new OutputStreamWriter(os, "UTF8");
			bufferedWriter.write(data);

			bufferedWriter.close();

			PrintPDF printPDF = new PrintPDF();
			// creo el pdf a partir del html generado
			// PrintPDF.generatePDF(basePathVoucher + "\\temporal.html", basePathVoucher +
			// "\\" + fileName + ".pdf");
			printPDF.generatePDFWhitJasper(basePathVoucher + "\\temporal.html", basePathVoucher + "\\" + fileName + ".pdf");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (out != null) {
					out.close();
					// File html = new File(basePathVoucher + "\\temporal.html");
					// html.delete();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}

			if (Desktop.isDesktopSupported()) {
				try {
					File myFile = new File(basePathVoucher + "\\" + fileName + ".pdf");
					File copyFile = new File(basePathVoucher + "\\" + UUID.randomUUID().toString() + ".pdf");
					FileUtils.copyFile(myFile, copyFile);
					Desktop.getDesktop().open(copyFile);
				} catch (IOException ex) {
					// no application registered for PDFs
					ex.printStackTrace();
				}
			}

			boleta = new ArrayList<String>();
		}

	}

	
	public void generatePDFWhitJasper(String inputHtmlPath, String outputPdfPath) {
	    InputStream template = null;
	    SimpleOutputStreamExporterOutput exporterOutput = null;

	    try {
	        // Renderizar imágenes desde HTML
	        List pages = GenerateImageFromHtml.renderHtmlToImages(inputHtmlPath);

	        // Abrir InputStream del template .jasper
	        template = getClass().getClassLoader().getResourceAsStream("config/template_boleta_imagen.jasper");
	        if (template == null) {
	            throw new FileNotFoundException("template_boleta_imagen.jasper no encontrado");
	        }

	        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(template);
	        List jasperPrintList = new ArrayList();

	        for (int i = 0; i < pages.size(); i++) {
	            BufferedImage pageImage = (BufferedImage) pages.get(i);
	            Map parameters = new HashMap();
	            parameters.put("boletaImagen", pageImage);

	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
	            jasperPrintList.add(jasperPrint);
	        }

	        // Configurar exportador PDF
	        JRPdfExporter exporter = new JRPdfExporter();
	        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

	        exporterOutput = new SimpleOutputStreamExporterOutput(outputPdfPath);
	        exporter.setExporterOutput(exporterOutput);

	        exporter.exportReport();

	        Base.logger.info("PDF generado correctamente en: " + outputPdfPath);

	    } catch (Exception e) {
	        e.printStackTrace();
	        Base.logger.error("Error al generar PDF desde imagen: " + e.getMessage());
	    } finally {
	        // Cerrar InputStream del template
	        if (template != null) {
	            try {
	                template.close();
	            } catch (IOException e) {
	            	Base.logger.error("Error al cerrar el InputStream del template: " + e.getMessage());
	            }
	        }

	        // Cerrar OutputStream 
	        if (exporterOutput != null) {
	            exporterOutput.close();
	        }
	    }

	}

}
