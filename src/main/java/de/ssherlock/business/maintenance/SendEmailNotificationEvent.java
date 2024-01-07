package de.ssherlock.business.maintenance;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.connection.ConnectionPool;
import de.ssherlock.persistence.util.Mail;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Automatically sends an E-Mail to a user.
 *
 * @author Leon Höfling
 */
public class SendEmailNotificationEvent {

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private static final SerializableLogger LOGGER =
            LoggerCreator.get(SendEmailNotificationEvent.class);

    /**
     * The User-service for this class.
     */
    @Inject
    private UserService userService;

    /**
     * Constructs a new SendEmailNotificationEvent.
     */
    public SendEmailNotificationEvent() {}

    /**
     * Sends email notifications.
     */
    public void sendEmailNotifications() {
        userService.sendReminderEmail();
    }

    /**
     * Checks if SendEmailNotificationEvent is currently running.
     *
     * @return true/false according to the state of SendEmailNotificationEvent.
     */
    public boolean isRunning() {
        return false;
    }

    /**
     * Shuts down the SendEmailNotificationEvent.
     */
    public void shutdown() {}
}
