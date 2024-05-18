package hotel.entity;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Email {
    String to = "recipient@example.com";
    String from = "sender@example.com";
    String subject = "Test Email";
    String body = "This is a test email sent using JavaMail API.";

    Properties properties = new Properties();
//        properties.put("mail.smtp.host","smtp.example.com");
//        properties.put("mail.smtp.port","587");
//        properties.put("mail.smtp.auth","true");
//        properties.put("mail.smtp.starttls.enable","true");

    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("sender@example.com", "senderPassword");
        }

        public void method() {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    });
}