package utils;

import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailSender {
	private String from;
	private String to;
	private String subject;
	private Session session;

	public EmailSender(String to, String subject) {
		this.to = to;
		this.subject = subject;
		this.from = System.getenv("EMAIL_ADDRESS");

		String username = System.getenv("EMAIL_USERNAME");
		String password = System.getenv("EMAIL_PASSWORD");
		String host = System.getenv("SMTP_HOST");
		String port = System.getenv("SMTP_PORT");

		if (from == null || username == null || password == null || host == null || port == null)
			throw new IllegalArgumentException("Missing email configuration in environment.");

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public Boolean send(String htmlContent, String text, String imagePath) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);

			Multipart multipart = new MimeMultipart();
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(text);

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");

			MimeBodyPart imagePart = new MimeBodyPart();
			FileDataSource imageSrc = new FileDataSource(imagePath);
			imagePart.setDataHandler(new DataHandler(imageSrc));
			imagePart.setHeader("Content-ID", "<logo>");

			multipart.addBodyPart(imagePart);
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(htmlPart);

			message.setContent(multipart);
			Transport.send(message);
			return true;
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}
