package de.ssherlock.business.service;

import de.ssherlock.global.transport.Mail;

import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void sendMail(Mail mail) {
        Configuration configuration = Configuration.getInstance();
        Session session = getSession(configuration);
        checkSMTPConfiguration();
        logger.log(Level.INFO, "Mail config loaded.");
        try {
            logger.log(Level.INFO, "Sending message to " + mail.email());
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(configuration.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.email()));
            message.setText("Juuuuuunge Krank!");
            Transport.send(message);
            logger.log(Level.INFO, "Mail sent to " + mail.username());
        } catch (MessagingException e) {
            logger.log(Level.INFO, e.getMessage());
            logger.log(Level.INFO, Arrays.toString(e.getStackTrace()));
        }
    }

    private Session getSession(Configuration config) {
        //configure SMTP server details
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", config.getAuth());
        properties.put("mail.smtp.starttls.enable", config.getTls());
        properties.put("mail.smtp.host", config.getMailhost());
        properties.put("mail.smtp.port", config.getPort());

        return Session.getInstance(properties,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getFrom(), config.getMailpassword());
                    }
                });
    }

    public void checkSMTPConfiguration() {
        logger.log(Level.INFO, "Checking the connection to the smtp-server");
        try {
            Transport transport = getSession(Configuration.getInstance()).getTransport("smtp");
            transport.connect();
            transport.close();
            logger.info("Connection was successfully established.");
        } catch (MessagingException e) {
            logger.log(Level.INFO, "Error while trying to connect with the smtp-server");
        }
    }



}
