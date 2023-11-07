package de.ssherlock.persistence.util;

import de.ssherlock.persistence.config.DatabaseConfiguration;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;

import java.io.InputStream;
import java.util.function.Function;

public class StartStopPersistence {

    public StartStopPersistence() {
    }

    public void init(Function<String, InputStream> resourceFetcher) {
        ConnectionPoolPsql.getInstance();
        DatabaseConfiguration.getInstance().init(resourceFetcher);
    }

    public void destroy() {

    }
}
