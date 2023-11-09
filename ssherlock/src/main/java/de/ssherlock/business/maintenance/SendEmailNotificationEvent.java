package de.ssherlock.business.maintenance;

import java.util.logging.Logger;

final class SendEmailNotificationEvent {

    public static final int EXECUTION_RATE = 60 * 60 * 3;

    private Logger logger;

    public SendEmailNotificationEvent() {

    }

    public boolean isRunning() {
        return false;
    }

    public void shutdown() {

    }

    public void sendEmailNotifications() {

    }
}
