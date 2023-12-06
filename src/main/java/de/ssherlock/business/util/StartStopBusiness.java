package de.ssherlock.business.util;

import de.ssherlock.business.maintenance.MaintenanceProcessExecutor;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.persistence.util.StartStopPersistence;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;

import java.io.Serial;
import java.io.Serializable;

/**
 * Handles start and stop functionalities for the business layer.
 *
 * @author Victor Vollmann
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
    @Inject private SerializableLogger logger;

    /**
     * The StartStop instance of the persistence layer.
     */
    @Inject private StartStopPersistence startStopPersistence;

    private MaintenanceProcessExecutor executor;

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
        executor = new MaintenanceProcessExecutor();
        executor.init();
        logger.info("Business Layer initialized.");
        startStopPersistence.init(sce);
    }

    /**
     * Destroys the business layer.
     */
    public void destroy() {
        executor.destroy();
        logger.info("Business Layer destroyed.");
        startStopPersistence.destroy();
    }
}
