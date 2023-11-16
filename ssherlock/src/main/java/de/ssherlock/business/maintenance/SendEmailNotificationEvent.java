package de.ssherlock.business.maintenance;

import java.util.logging.Logger;

/**
 * Automatically sends an E-Mail to a user.
 */
final class SendEmailNotificationEvent {

    /**
     * Defines the rate in which this Event should be executed.
     */
    public static final int EXECUTION_RATE = 60 * 60 * 3;

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private Logger logger;

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
     * Sends email.
     */
    public void sendEmailNotifications() {

    }
}
