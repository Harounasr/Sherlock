package de.ssherlock.business.service;

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
public class MailService {
    @Inject
    private Logger logger;

    public MailService() {

    }

    public void sendMail(User user) {
        Configuration configuration = Configuration.getInstance();
        Session session = getSession(configuration);
        logger.log(Level.INFO, "Mail config loaded.");
        try {
            logger.log(Level.INFO, "Trying to send Mail to " + user.email());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(configuration.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.email()));
            //TODO
            message.setText("InsertTextHere");
            Transport.send(message);
            logger.log(Level.INFO, "Mail successfully sent to " + user.email());
        } catch (MessagingException e) {
            logger.log(Level.INFO, "There was a problem with sending the Mail.");
        }
    }

    private Session getSession(Configuration config) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", config.getMailhost());
        properties.put("mail.smtp.port", config.getPort());
        properties.put("mail.smtp.auth", config.getAuth());
        properties.put("mail.smtp.starttls.enable", config.getTls());
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getFrom(), config.getMailpassword());
            }
        });
    }
}
