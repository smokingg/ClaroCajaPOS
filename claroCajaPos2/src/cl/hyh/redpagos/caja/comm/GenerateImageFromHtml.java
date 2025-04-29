package cl.hyh.redpagos.caja.comm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.xerces.impl.dv.util.Base64;

public class GenerateImageFromHtml {

    public static List<BufferedImage> renderHtmlToImages(String htmlPath) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(htmlPath), "UTF-8"));
        StringBuilder html = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            html.append(line);
        reader.close();
        List<Map> lineas = new ArrayList<Map>();
        Matcher matcher = Pattern.compile("<(div|p)[^>]*?>(.*?)</\\1>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(html.toString());
        while (matcher.find()) {
            String raw = matcher.group(0).toLowerCase();
            String innerHtml = matcher.group(2);

            String textoPlano = innerHtml.replaceAll("(?i)<span[^>]*>(.*?)</span>", " $1 ");
            String contenido = decodeHtml(textoPlano.replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").trim());

            if (raw.contains("border-bottom")) {
                if (!contenido.isEmpty()) {
                    Map map = new HashMap();
                    map.put("texto", contenido);
                    map.put("alineacion", "left");
                    map.put("negrita", Boolean.valueOf(raw.contains("font-weight: bold")));
                    map.put("subrayado", Boolean.valueOf(raw.contains("text-decoration:underline")));
                    map.put("espaciado", Boolean.valueOf(raw.contains("margin-top")));
                    if (contenido.contains("Monto Doc.") || contenido.contains("Valor Pagado") || contenido.contains("TOTAL PAGADO") || contenido.contains("VUELTO") || contenido.contains("$"))
                        map.put("montos", Boolean.valueOf(true));
                    lineas.add(map);

                    { // <-- agregado para evitar el Duplicate local variable
                        Map lineMap = new HashMap();
                        lineMap.put("alineacion", "line");
                        lineMap.put("texto", "");
                        lineas.add(lineMap);
                    }
                } else {
                    Map lineMap = new HashMap();
                    lineMap.put("alineacion", "line");
                    lineMap.put("texto", "");
                    lineas.add(lineMap);
                }
            } else {
                Map map = new HashMap();
                map.put("texto", contenido);
                if (raw.contains("text-align: center")) {
                    map.put("alineacion", "center");
                } else if (raw.contains("text-align: right")) {
                    map.put("alineacion", "right");
                } else {
                    map.put("alineacion", "left");
                }
                map.put("negrita", Boolean.valueOf(raw.contains("font-weight: bold")));
                map.put("subrayado", Boolean.valueOf(raw.contains("text-decoration:underline")));
                map.put("espaciado", Boolean.valueOf(raw.contains("margin-top")));
                if (contenido.contains("Monto Doc.") || contenido.contains("Valor Pagado") || contenido.contains("TOTAL PAGADO") || contenido.contains("VUELTO") || contenido.contains("$"))
                    map.put("montos", Boolean.valueOf(true));
                lineas.add(map);
            }
        }

        if (!lineas.isEmpty()) {
            Map last = lineas.get(lineas.size() - 1);
            if ("line".equals(last.get("alineacion")))
                lineas.remove(lineas.size() - 1);
        }
        String base64Logo = null;
        Matcher imgMatch = Pattern.compile("src=\"data:image/[^;]+;base64,([^\"]+)\"").matcher(html.toString());
        if (imgMatch.find())
            base64Logo = imgMatch.group(1);
        int scale = 4;
        int contentWidth = 550;
        int fullWidth = 850;
        int offsetX = (fullWidth - contentWidth) / 2;
        int lineHeight = 16;
        int extraLogo = 100;
        int maxHeightPerPage = 1000;
        List<BufferedImage> pages = new ArrayList<BufferedImage>();
        BufferedImage pageImage = new BufferedImage(fullWidth * scale, maxHeightPerPage * scale, 1);
        Graphics2D g = pageImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, fullWidth * scale, maxHeightPerPage * scale);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.scale(scale, scale);
        int y = 30;
        if (base64Logo != null && base64Logo.length() > 50)
            try {
                byte[] decoded = Base64.decode(base64Logo.trim());
                InputStream in = new ByteArrayInputStream(decoded);
                BufferedImage logo = ImageIO.read(in);
                if (logo != null) {
                    g.drawImage(logo, offsetX + 20, y, 87, 81, null);
                    y += 100;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        for (int i = 0; i < lineas.size(); i++) {
            if (y + lineHeight > maxHeightPerPage) {
                g.dispose();
                pages.add(pageImage);
                pageImage = new BufferedImage(fullWidth * scale, maxHeightPerPage * scale, 1);
                g = pageImage.createGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, fullWidth * scale, maxHeightPerPage * scale);
                g.scale(scale, scale);
                y = 30;
            }
            Map map = lineas.get(i);
            String tipo = (String) map.get("alineacion");
            String texto = (String) map.get("texto");
            boolean bold = (map.containsKey("negrita") && ((Boolean) map.get("negrita")).booleanValue());
            boolean underline = (map.containsKey("subrayado") && ((Boolean) map.get("subrayado")).booleanValue());
            boolean montos = (map.containsKey("montos") && ((Boolean) map.get("montos")).booleanValue());
            boolean salto = (map.containsKey("espaciado") && ((Boolean) map.get("espaciado")).booleanValue());
            if (salto)
                y += 10;
            if ("line".equals(tipo)) {
                y += 5;
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(1.8F));
                g.drawLine(offsetX + 20, y, offsetX + contentWidth - 20, y);
                y += 17;
            } else {
                Font baseFont = new Font("Courier", bold ? 1 : 0, 12);
                g.setFont(baseFont);
                g.setColor(Color.BLACK);
                if (montos && texto.contains("$")) {
                    String[] parts = texto.split("\\$");
                    String left = parts[0].trim();
                    String right = "$" + parts[1].trim();
                    int leftX = offsetX + 20;
                    int rightX = offsetX + contentWidth - g.getFontMetrics().stringWidth(right) - 20;
                    g.drawString(left, leftX, y);
                    g.drawString(right, rightX, y);
                } else {
                    int strWidth = g.getFontMetrics().stringWidth(texto);
                    int x = offsetX + 20;
                    if ("center".equals(tipo)) {
                        x = offsetX + (contentWidth - strWidth) / 2;
                    } else if ("right".equals(tipo)) {
                        x = offsetX + contentWidth - strWidth - 20;
                    }
                    if (underline) {
                        AttributedString as = new AttributedString(texto);
                        as.addAttribute(TextAttribute.FONT, baseFont);
                        as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, texto.length());
                        g.drawString(as.getIterator(), x, y);
                    } else {
                        g.drawString(texto, x, y);
                    }
                }
                y += 17;
            }
        }
        g.dispose();
        pages.add(pageImage);
        return pages;
    }

    public static String decodeHtml(String input) {
        try {
            return new String(input.getBytes("UTF-8"), "UTF-8")
                    .replaceAll("&nbsp;", " ")
                    .replaceAll("&#160;", " ")
                    .replaceAll("&amp;", "&")
                    .replaceAll("&lt;", "<")
                    .replaceAll("&gt;", ">")
                    .replaceAll("&quot;", "\"")
                    .replaceAll("&#39;", "'")
                    .replace("Ã±", "ñ")
                    .replace("Ã‘", "Ñ")
                    .replace("Ã¡", "á")
                    .replace("Ã‰", "É")
                    .replace("Ã©", "é")
                    .replace("Ã�", "Í")
                    .replace("Ã­", "í")
                    .replace("Ã“", "Ó")
                    .replace("Ã³", "ó")
                    .replace("Ãš", "Ú")
                    .replace("Ãº", "ú")
                    .replace("ï¿½", "ñ");
        } catch (Exception e) {
            return input;
        }
    }
}
