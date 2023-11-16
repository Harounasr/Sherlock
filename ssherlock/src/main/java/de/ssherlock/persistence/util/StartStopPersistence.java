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
/**
 * Class for managing initialization and destruction of the persistence layer during application startup and shutdown.
 */
public class StartStopPersistence {
    /**
     * Logger instance for logging messages related to the StartStopPersistence class.
     */
    private final Logger logger = LoggerCreator.get(StartStopPersistence.class);

    /**
     * Configuration instance for obtaining persistence layer settings.
     */
    @Inject
    private Configuration configuration;

    /**
     * Connection pool for managing database connections.
     */
    @Inject
    private ConnectionPoolPsql connectionPoolPsql;
    /**
     * Default constructor for creating a StartStopPersistence instance.
     */
    public StartStopPersistence() {
    }
    /**
     * Initializes the persistence layer during application startup.
     *
     * @param resourceFetcher Function for fetching resources during initialization.
     */
    public void init(Function<String, InputStream> resourceFetcher) {
        //configuration.init();
        logger.log(Level.INFO, "Persistence Layer initialized");
    }
    /**
     * Destroys the persistence layer during application shutdown.
     */
    public void destroy() {
        connectionPoolPsql.destroy();
    }
}
