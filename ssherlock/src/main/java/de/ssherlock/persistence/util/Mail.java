package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.config.Configuration;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Request-scoped class for sending emails using JavaMail API.
 * This class is responsible for configuring and sending emails to specified recipients.
 *
 * @author Leon Höfling
 */
@Named
@Dependent
public class Mail implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to the Mail class.
     */
    private final SerializableLogger logger;

    /**
     * Configuration instance for obtaining email and mail server settings.
     */
    private final Configuration config;

    /**
     * Default constructor for creating a Mail instance.
     *
     * @param logger The logger.
     * @param config The config.
     */
    @Inject
    public Mail(SerializableLogger logger, Configuration config) {
        this.logger = logger;
        this.config = config;
    }

    /**
     * Sends an email to the specified users with the given content.
     *
     * @param recipients The users to whom the email will be sent.
     * @param content    The content of the email.
     */
    public void sendMail(List<User> recipients, String content) {
        Session session = getSession();
        logger.log(Level.INFO, "Mail config loaded.");
        try {
            logger.log(Level.INFO, "Trying to send reminder Mails");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getMailFrom()));
            for (User user : recipients) {
                message.addRecipient(Message.RecipientType.CC, InternetAddress.parse(user.getEmail())[0]);
            }
            message.setText(content);
            message.setSubject("Upcoming exercise deadline reminder");
            Transport.send(message);
            logger.log(Level.INFO, "Reminder mails successfully sent.");
        } catch (MessagingException e) {
            logger.log(Level.INFO, "There was a problem with sending the reminder Mails.");
        }
    }

    /**
     * Sends an email to the specified user with the given content.
     *
     * @param recipient  The user to whom the email will be sent.
     * @param content    The content of the email.
     */
    public void sendMail(User recipient, String content, MailType subject) {
        Session session = getSession();
        logger.log(Level.INFO, "Mail config loaded.");
        try {
            logger.log(Level.INFO, "Trying to send Mail to " + recipient.getEmail());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getMailFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.getEmail()));
            message.setText(content);
            message.setSubject(subject.name()); // somehow doesn't work...
            Transport.send(message);
            logger.log(Level.INFO, "Mail successfully sent to " + recipient.getEmail());
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
