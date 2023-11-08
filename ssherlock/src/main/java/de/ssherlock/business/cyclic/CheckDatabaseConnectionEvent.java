package de.ssherlock.business.cyclic;

import java.util.logging.Logger;

public class CheckDatabaseConnectionEvent implements CyclicEvent {

    private Logger logger;

    public CheckDatabaseConnectionEvent() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void shutdown() { }

    @Override
    public void run() { }
}
