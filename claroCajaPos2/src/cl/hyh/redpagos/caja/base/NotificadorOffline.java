package cl.hyh.redpagos.caja.base;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;


public class NotificadorOffline {

	private String smtpServerName;

	private int smtpServerPort;

	private String to;

	private String from;

	private String password;

	private String subject;

	private String body;

	private String cc;

	private String attachFile = null;
	
	private List files = new ArrayList();

	public static Logger logger;

	public NotificadorOffline(String archivoCvs) {
		super();
		ParamSet posCfg = Base.getParamSet( "posDat" );
		from = (posCfg.getStringValue("smtp_from") == null?"":posCfg.getStringValue("smtp_from"));
		password = (posCfg.getStringValue("smtp_pass") == null?"":posCfg.getStringValue("smtp_pass"));
		smtpServerName = (posCfg.getStringValue("smtp_server") == null?"10.38.11.219":posCfg.getStringValue("smtp_server"));
		
		smtpServerPort = Integer.parseInt((posCfg.getStringValue("smtp_port") == null?"25":posCfg.getStringValue("smtp_port")));
		
		to = (posCfg.getStringValue("smtp_to") == null?"":posCfg.getStringValue("smtp_to"));
		body = (posCfg.getStringValue("smtp_body") == null?"":posCfg.getStringValue("smtp_body"));
		subject = (posCfg.getStringValue("smtp_subject") == null?"":posCfg.getStringValue("smtp_subject")); 
		cc = (posCfg.getStringValue("smtp_cc") == null?"":posCfg.getStringValue("smtp_cc")); 

		attachFile = archivoCvs;
	
		
	}
	
	public NotificadorOffline(List files) {
		super();
		 ParamSet posCfg = Base.getParamSet( "posDat" );
		 
		from = (posCfg.getStringValue("smtp_from") == null?"":posCfg.getStringValue("smtp_from"));
		password = (posCfg.getStringValue("smtp_pass") == null?"":posCfg.getStringValue("smtp_pass"));
		smtpServerName = (posCfg.getStringValue("smtp_server") == null?"10.38.11.219":posCfg.getStringValue("smtp_server"));
		
		smtpServerPort = Integer.parseInt((posCfg.getStringValue("smtp_port") == null?"25":posCfg.getStringValue("smtp_port")));
		
		to = (posCfg.getStringValue("smtp_to") == null?"":posCfg.getStringValue("smtp_to"));
		body = (posCfg.getStringValue("smtp_body") == null?"":posCfg.getStringValue("smtp_body"));
		subject = (posCfg.getStringValue("smtp_subject") == null?"":posCfg.getStringValue("smtp_subject")); 
		cc = (posCfg.getStringValue("smtp_cc") == null?"":posCfg.getStringValue("smtp_cc"));
		this.files = files;
	
		
	}
	


	public boolean send() {
		boolean retorno = true;
		try {
			Properties props = System.getProperties();
			
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", smtpServerName);
			props.setProperty("mail.user", from);
			props.setProperty("mail.password", password);
			
			/* comentar para red Claro*/
//			props.put("mail.smtp.starttls.enable","true");
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.debug", "true");
			/* comentar para red Claro*/			
			
			props.put("mail.smtp.host", smtpServerName);
			props.put("mail.smtp.port", smtpServerPort);
			
			
			
			
			Session session = Session.getDefaultInstance(props,null);
			/* Cambiar para red Claro*/	
			//Session session = Session.getDefaultInstance(props, new GMailAuthenticator(from, password));
			session.setDebug(true);

			BodyPart texto = new MimeBodyPart();
			texto.setText(body);

			/** ************************************************************************ */
			
			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(texto);
			
			
			if (files != null && files.size() > 0) {
				for (Iterator iter = files.iterator(); iter.hasNext();) {
					String filename = (String) iter.next();
					FileDataSource fds = new FileDataSource(filename);
					MimeBodyPart adjunto = new MimeBodyPart();
					adjunto.setDataHandler(new DataHandler(fds));
					adjunto.setFileName(fds.getName());

					multiPart.addBodyPart(adjunto);

				}
			}
			

			// Para multiples envios CC

			String recipients = cc;
			ArrayList recipientsArray = new ArrayList();
			StringTokenizer st = new StringTokenizer(recipients, ",");
			while (st.hasMoreTokens()) {
				recipientsArray.add(st.nextToken());
			}
			int sizeCc = recipientsArray.size();
			InternetAddress[] addressCc = new InternetAddress[sizeCc];
			for (int i = 0; i < sizeCc; i++) {
				addressCc[i] = new InternetAddress(recipientsArray.get(i)
						.toString());
			}
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to
					.trim()));
			msg.setRecipients(Message.RecipientType.CC, addressCc);
			msg.setSubject(subject.trim());
			msg.setContent(multiPart);
			msg.setSentDate(new Date());

			Transport.send(msg);

		} catch (Exception ex) {
			ex.printStackTrace();
			retorno = false;
		}
		return retorno;

	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
public static boolean notificar(){
	
	 String fecha = Tools.getFechaYYYYMMDD();
     ParamSet pCfg = Base.getParamSet( "posCfg" );
     String dir = "";
     String s = "";
     FileWriter ficheroCvs = null;
     PrintWriter pw = null;
     
 	dir = "OperSafOfflineDir";
 	s = String.format( "EnvioCvs_%s", fecha );
 	
 	ArrayList archivos = new ArrayList<String>();
 	ArrayList archivosEnviados = new ArrayList<String>();
 	
 	 
 	File directorioOfflineCvs = new File(pCfg.getStringValue("ValidaOfflineDirCvs"));
 	
 	String[] ficheros = directorioOfflineCvs.list();
 	
 	if(ficheros != null && ficheros.length>0){
 		
 		for (int x=0; x < ficheros.length;x++){
 			String archivoEnviado = pCfg.getStringValue("ValidaOfflineDirCvs") +ficheros[x];
 			archivosEnviados.add(ficheros[x]);
 			archivos.add(archivoEnviado);
 		}
 	
 	NotificadorOffline email = new NotificadorOffline(archivos);
 	
 	if (email.send()){
 		
 		for (Iterator iter = archivosEnviados.iterator(); iter.hasNext();) {
 			String filename = (String) iter.next();
	 		String nombreArchivoCvs = pCfg.getStringValue("ValidaOfflineDirCvs") + filename;
	 		
	 		String nuevaFecha = Tools.getFechaHora();
	 		String nombre = String.format( "EnvioCvs_%s", nuevaFecha );
	 		String newFilename=nombre + ".cvs";
	 	 	String nombreArchivoCvsBackUp = pCfg.getStringValue("ValidaOfflineBackUp") + newFilename;
 		
 		Tools.fileMove(nombreArchivoCvs, nombreArchivoCvsBackUp);
 		}
 		
 	}else{
 		return false;
 	}
 	
 	}
 	else{
 		System.out.println( "No se pudo enviar Mail de Archivos CVS");
 		return false;
 	}
 	
	
 	return true;
}
	

}




