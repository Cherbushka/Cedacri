import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSend {

	public static void main(String[] args) throws MessagingException {
		System.out.println("Sending begins...");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust","smtp.gmail.com");//это свойство позволяет мне запустить связь с почтой через ssl протокол.
        properties.put("mail.smtp.port","587");//порт gmail почты
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(EMailData.getLogin(),EMailData.getPass());
            }
        });
		
		
		MimeMessage mess = new MimeMessage(session);
		mess.setFrom(new InternetAddress(EMailData.getLogin()));
		mess.addRecipient(Message.RecipientType.TO, new InternetAddress(EMailData.getLogin()));
		mess.addRecipient(Message.RecipientType.TO, new InternetAddress("stefanboblic@gmail.com"));
		mess.setSubject("Uhhhh, fine");
		
		Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("<b><i>I guess you are my little pogchamp.<br>Come here</i></b><br>", "text/html");
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("<img src=\"https://media1.tenor.com/images/fa13d36d26a325aa0798921a02953baf/tenor.gif?itemid=19294003\">", "text/html");
			multipart.addBodyPart(messageBodyPart);
		mess.setContent(multipart);
	
		Transport.send(mess);
		System.out.println("Sending complete!");
		
	}

}
