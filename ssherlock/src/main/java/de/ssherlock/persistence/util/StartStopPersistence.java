package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.config.DatabaseConfiguration;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;

import java.io.InputStream;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartStopPersistence {

    private final Logger logger = LoggerCreator.get(StartStopPersistence.class);

    public StartStopPersistence() {
    }

    public void init(Function<String, InputStream> resourceFetcher) {
        DatabaseConfiguration.getInstance().init(resourceFetcher);
        ConnectionPoolPsql.getInstance().init();
        logger.log(Level.INFO, "Persistence Layer initialized");
    }

    public void destroy() {
        ConnectionPoolPsql.getInstance().destroy();
    }
}
