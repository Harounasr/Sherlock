package de.ssherlock.business.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.util.StartStopPersistence;

import java.io.InputStream;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartStopBusiness {

    private final Logger logger = LoggerCreator.get(StartStopBusiness.class);
    StartStopPersistence startStopPersistence;

    public StartStopBusiness() {

    }
    public void init(Function<String, InputStream> resourceFetcher) {
        startStopPersistence = new StartStopPersistence();
        startStopPersistence.init(resourceFetcher);
        logger.log(Level.INFO, "Business Layer initialized");
    }
    public void destroy() {
        startStopPersistence.destroy();
    }
}
