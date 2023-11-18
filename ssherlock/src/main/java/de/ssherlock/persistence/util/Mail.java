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

/**
 * Request-scoped class for sending emails using JavaMail API.
 * This class is responsible for configuring and sending emails to specified recipients.
 */
@Named
@RequestScoped
public class Mail {
    /**
     * Logger instance for logging messages related to the Mail class.
     */
    @Inject
    private Logger logger;

    /**
     * Configuration instance for obtaining email and mail server settings.
     */
    @Inject
    private Configuration config;
    /**
     * Default constructor for creating a Mail instance.
     */
    public Mail() {

    }

    /**
     * Sends an email to the specified user with the given content.
     *
     * @param user    The user to whom the email will be sent.
     * @param content The content of the email.
     */
    public void sendMail(User user, String content) {
        Session session = getSession();
        logger.log(Level.INFO, "Mail config loaded.");
        try {
            logger.log(Level.INFO, "Trying to send Mail to " + user.getEmail());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getMailFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setText(content);
            Transport.send(message);
            logger.log(Level.INFO, "Mail successfully sent to " + user.getEmail());
        } catch (MessagingException e) {
            logger.log(Level.INFO, "There was a problem with sending the Mail.");
        }
    }
    /**
     * Retrieves a configured JavaMail session based on the application's email settings.
     *
     * @return A configured JavaMail session.
     */
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
