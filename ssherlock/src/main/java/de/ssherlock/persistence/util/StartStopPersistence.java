package de.ssherlock.persistence.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.config.Configuration;
import de.ssherlock.persistence.connection.ConnectionPool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * Class for managing initialization and destruction of the persistence layer during application startup and shutdown.
 *
 * @author Leon Höfling
 */
@ApplicationScoped
public class StartStopPersistence implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Configuration instance.
     */
    @Inject
    private Configuration configuration;

    /**
     * Connection pool instance
     */
    @Inject
    private ConnectionPool connectionPool;

    /**
     * Logger instance for logging messages related to the StartStopPersistence class.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * Default constructor.
     */
    public StartStopPersistence() {

    }

    /**
     * Initializes the persistence layer during application startup.
     *
     * @param sce   The Servlet Context Event.
     */
    public void init(ServletContextEvent sce) {
        logger.log(Level.INFO, "Persistence Layer initialized.");
        LoggerCreator.readConfig(sce);
        configuration.init(sce);
        connectionPool.init();
    }
    /**
     * Destroys the persistence layer during application shutdown.
     *
     * @param sce   The Servlet Context Event.
     */
    public void destroy(ServletContextEvent sce) {
        connectionPool.destroy();
        logger.log(Level.INFO, "Persistence Layer destroyed.");
    }

}
