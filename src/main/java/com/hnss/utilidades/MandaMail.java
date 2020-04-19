package com.hnss.utilidades;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.excepciones.MailExcepciones;
import com.hnss.lopdcaa.MyUI;
import com.hnss.ui.Notificaciones;

// TODO: Auto-generated Javadoc
/**
 * The Class MandaMail.
 */
public class MandaMail {

	private static final Logger logger = LogManager.getLogger(MandaMail.class);

	public static final String MAIL_ERRORENVIO_STRING = "Error enviando mail";

	private final Properties properties = new Properties();

	private Session session;

	/**
	 * Inits the.
	 */
	private void init() {

		/*
		 * properties.put("mail.smtp.host", Constantes.MIALHOST);
		 * properties.put("mail.smtp.starttls.enable", Constantes.MIALSTARTTLS);
		 * properties.put("mail.smtp.port", Constantes.MIALPORT);
		 * properties.put("mail.smtp.mail.sender", Constantes.MAILEMISOR);
		 * properties.put("mail.smtp.user", Constantes.MAILUSER);
		 * properties.put("mail.smtp.auth", Constantes.MAILAUTH);
		 */

	}

	/**
	 * Send email.
	 *
	 * @param destinatario the destinatario
	 * @param asunto       the asunto
	 * @param contenido    the contenido
	 * @throws AddressException   the address exception
	 * @throws MessagingException the messaging exception
	 * @throws Exception          the exception
	 */
	public void sendEmail(String destinatario, String asunto, String contenido) throws MailExcepciones {
		try {
//			init();

			// properties.put("mail.smtp.host",
			// MyUI.objParametros.get(Parametros.KEY_MIALHOST));
			// properties.put("mail.smtp.starttls.enable",
			// MyUI.objParametros.get(Parametros.KEY_MIALSTARTTLS));
			// properties.put("mail.smtp.port",
			// Integer.parseInt((String) MyUI.objParametros.get(Parametros.KEY_MIALPORT)));
			// properties.put("mail.smtp.mail.sender",
			// MyUI.objParametros.get(Parametros.KEY_MAILEMISOR));
			// properties.put("mail.smtp.user",
			// MyUI.objParametros.get(Parametros.KEY_MAILUSER));
			// properties.put("mail.smtp.auth",
			// MyUI.objParametros.get(Parametros.KEY_MAILAUTH));

			// properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			properties.put("mail.smtp.host", "smtp.saludcastillayleon.es");
			properties.put("mail.smtp.port", 25);
			properties.put("mail.smtp.mail.sender", "avisos.hnss@saludcastillayleon.es");
			properties.put("mail.smtp.user", "grs.root/avisos.hnss");
			properties.put("mail.smtp.auth", "true");
			session = Session.getDefaultInstance(properties);

			String[] destinatarios = destinatario.split(",");
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
			for (String destino : destinatarios) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			}
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress("informatica.hnss@saludcastillayleon.es"));
			message.setSubject(asunto);
			message.setText(contenido);

			Transport t = session.getTransport("smtp");
			System.out.println((String) properties.get("mail.smtp.user"));
			System.out.println((String) MyUI.objParametros.get(Parametros.KEY_MAILPASSW));
			// t.connect((String) properties.get("mail.smtp.host ") + (String)
			// properties.get("mail.smtp.user"),
			// (String) MyUI.objParametros.get(Parametros.KEY_MAILPASSW));
			t.connect("grs.root/avisos.hnss", "12345678");
			// t.connect((String) properties.get("mail.smtp.user"),
			// (String) MyUI.objParametros.get(Parametros.KEY_MAILPASSW));
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			logger.debug("mensaje enviado" + destinatarios.toString() + "\n Asunto: " + asunto + "\n Contenido:"
					+ contenido);
		} catch (AddressException e) {
			logger.error(e);
			throw new MailExcepciones("Error en dirección " + e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e);
			throw new MailExcepciones("Error en mensaje  ", e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
			throw new MailExcepciones(Notificaciones.EXCEPTION_ERROR + e.getMessage());
		}
	}

}
