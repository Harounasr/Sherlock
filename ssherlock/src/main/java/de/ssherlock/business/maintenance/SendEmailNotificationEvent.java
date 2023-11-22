package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;

import java.util.logging.Logger;

/**
 * Automatically sends an E-Mail to a user.
 */
final class SendEmailNotificationEvent {

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private static final SerializableLogger logger = LoggerCreator.get(SendEmailNotificationEvent.class);

    /**
     * Defines the rate in which this Event should be executed.
     */
    public static final int EXECUTION_RATE = 60 * 60 * 3;

    /**
     * Constructs a new SendEmailNotificationEvent.
     */
    public SendEmailNotificationEvent() {

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
    public void shutdown() {

    }

    /**
     * Sends email notifications.
     */
    public void sendEmailNotifications() {

    }
}
