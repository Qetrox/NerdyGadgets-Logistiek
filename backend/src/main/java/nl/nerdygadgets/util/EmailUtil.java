package nl.nerdygadgets.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    public static void sendEmail(String email, String subject, String message) {

        String host = "mail.zxcs.nl";
        int port = 465;
        String username = "noreply@nerdy-gadgets.nl";
        String password = "NerdyGadgetsZijnHeelErgCOOL2024!";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
