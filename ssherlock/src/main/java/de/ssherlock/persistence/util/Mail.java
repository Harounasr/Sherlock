package de.ssherlock.persistence.util;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.config.Configuration;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Named
@RequestScoped
public class Mail {
    @Inject
    private Logger logger;
    @Inject
    Configuration config;

    public Mail() {

    }

    public void sendMail(User user, String content) {
        Session session = getSession();
        logger.log(Level.INFO, "Mail config loaded.");
        try {
            logger.log(Level.INFO, "Trying to send Mail to " + user.email());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getMailFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.email()));
            message.setText(content);
            Transport.send(message);
            logger.log(Level.INFO, "Mail successfully sent to " + user.email());
        } catch (MessagingException e) {
            logger.log(Level.INFO, "There was a problem with sending the Mail.");
        }
    }

    private Session getSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", config.getMailHost());
        properties.put("mail.smtp.port", config.getMailPort());
        properties.put("mail.smtp.auth", config.isMailAuthentication());
        properties.put("mail.smtp.starttls.enable", config.isTlsEnabled());
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getMailFrom(), config.getMailPassword());
            }
        });
    }
}
