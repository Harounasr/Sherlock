package de.ssherlock.business.util;

import de.ssherlock.persistence.util.StartStopPersistence;

import java.io.InputStream;
import java.util.function.Function;
import java.util.logging.Logger;

public class StartStopBusiness {

    private Logger logger;
    StartStopPersistence startStopPersistence;

    public StartStopBusiness() {

    }
    public void init(Function<String, InputStream> resourceFetcher) {
        startStopPersistence = new StartStopPersistence();
        startStopPersistence.init(resourceFetcher);
    }
    public void destroy() {

    }
}
