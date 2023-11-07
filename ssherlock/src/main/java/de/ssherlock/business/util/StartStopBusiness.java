package de.ssherlock.business.util;

import de.ssherlock.persistence.util.StartStopPersistence;

import java.util.logging.Logger;

public class StartStopBusiness {

    private Logger logger;
    StartStopPersistence startStopPersistence;

    public StartStopBusiness() {

    }
    public void init() {
        startStopPersistence = new StartStopPersistence();
        startStopPersistence.init();
    }
    public void destroy() {
        startStopPersistence.destroy();
    }
}
