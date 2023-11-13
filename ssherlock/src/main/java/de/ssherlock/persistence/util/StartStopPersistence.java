package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.persistence.config.Configuration;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartStopPersistence {

    private final Logger logger = LoggerCreator.get(StartStopPersistence.class);
    @Inject
    private Configuration configuration;
    @Inject
    private ConnectionPoolPsql connectionPoolPsql;

    public StartStopPersistence() {
    }

    public void init(Function<String, InputStream> resourceFetcher) {
        //configuration.init();
        logger.log(Level.INFO, "Persistence Layer initialized");
    }

    public void destroy() {
        connectionPoolPsql.destroy();
    }
}
