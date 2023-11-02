package de.ssherlock.business.cyclic;

import java.util.logging.Logger;

public class UnverifiedEmailCleanEvent implements CyclicEvent {

    private Logger logger;

    @Override
    public void run() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void shutdown() {

    }
}
