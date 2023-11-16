package de.ssherlock.business.maintenance;

import java.util.logging.Logger;

/**
 * Checks if there are Users, which have not been verified for a certain time and deletes those.
 */
public class UnverifiedUsersCleanEvent {

    /**
     * Defines the rate in which this Event should be executed.
     */
    public static final int EXECUTION_RATE = 60 * 60 * 2;

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private Logger logger;
    /**
     * Constructs a new UnverifiedUsersCleanEvent.
     */
    public UnverifiedUsersCleanEvent() {

    }
    /**
     * Deletes unverified users.
     */
    public void cleanUnverifiedUsers() {

    }

    /**
     * Checks if UnverifiedUsersCleanEvent is currently running.
     *
     * @return true/false according to the state of UnverifiedUsersCleanEvent.
     */
    public boolean isRunning() {
        return false;
    }

    /**
     * Shuts down the SendEmailNotificationEvent.
     */
    public void shutdown() {

    }
}
