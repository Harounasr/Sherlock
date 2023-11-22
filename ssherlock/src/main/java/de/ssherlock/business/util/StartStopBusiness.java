package de.ssherlock.business.util;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.util.StartStopPersistence;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;

import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles start and stop functionalities for the business layer.
 */
@ApplicationScoped
public class StartStopBusiness implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for this class.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * The StartStop instance of the persistence layer.
     */
    @Inject
    private StartStopPersistence startStopPersistence;

    /**
     * Default constructor.
     */
    public StartStopBusiness() {

    }

    /**
     * Initializes the business layer.
     *
     * @param sce The Servlet Context Event.
     */
    public void init(ServletContextEvent sce) {
        logger.log(Level.INFO, "Business Layer initialized.");
        startStopPersistence.init(sce);
    }

    /**
     * Destroys the business layer.
     *
     * @param sce The Servlet Context Event.
     */
    public void destroy(ServletContextEvent sce) {
        logger.log(Level.INFO, "Business Layer destroyed.");
        startStopPersistence.destroy(sce);
    }
}
