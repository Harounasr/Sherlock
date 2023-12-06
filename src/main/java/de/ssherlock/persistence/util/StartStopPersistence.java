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

/**
 * Handles start and stop functionalities for the persistence layer.
 * @author Victor Vollmann
 */
@ApplicationScoped
public class StartStopPersistence implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Configuration instance. */
  @Inject private Configuration configuration;

  /** Connection pool instance. */
  @Inject private ConnectionPool connectionPool;

  /** Logger instance for logging messages related to the StartStopPersistence class. */
  @Inject private SerializableLogger logger;

  /** Default constructor. */
  public StartStopPersistence() {}

    /**
     * Initializes the persistence layer during application startup.
     *
     * @param sce   The Servlet Context Event.
     */
    public void init(ServletContextEvent sce) {
        LoggerCreator.readConfig(sce);
        configuration.init(sce);
        connectionPool.init();
        // TODO remove comment when Database Scheme is ready
        /*
        Connection connection = connectionPoolPsql.getConnection();
        DatabaseInitializer.initialize(sce, connection);
        connectionPool.releaseConnection(connection);
         */
        logger.info("Persistence Layer initialized.");
    }
    /**
     * Destroys the persistence layer during application shutdown.
     */
    public void destroy() {
        connectionPool.destroy();
        logger.info("Persistence Layer destroyed");
    }

}
