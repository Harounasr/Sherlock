package de.ssherlock.business.cyclic;

import java.util.logging.Logger;

public class SendEmailNotificationEvent implements CyclicEvent {

    private Logger logger;

    public SendEmailNotificationEvent() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void run() {

    }
}
