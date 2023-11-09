package de.ssherlock.business.maintenance;

import java.util.logging.Logger;

public class UnverifiedUsersCleanEvent {

    public static final int EXECUTION_RATE = 60 * 60 * 2;

    private Logger logger;

    public void cleanUnverifiedUsers() {

    }

    public boolean isRunning() {
        return false;
    }

    public void shutdown() {

    }
}
