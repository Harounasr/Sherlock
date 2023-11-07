package de.ssherlock.persistence.util;

import de.ssherlock.persistence.config.DatabaseConfiguration;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;

public class StartStopPersistence {

    public StartStopPersistence() {
        DatabaseConfiguration.getInstance();
    }

    public void init() {
        ConnectionPoolPsql.getInstance().init();
    }

    public void destroy() {

    }
}
