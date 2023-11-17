package de.ssherlock.control.util;

import de.ssherlock.business.util.StartStopBusiness;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Servlet context listener for initializing and destroying resources during application startup and shutdown.
 */
@WebListener
@ApplicationScoped
public class StartStopControl implements ServletContextListener, Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to StartStopControl.
     */
    @Inject
    private SerializableLogger logger;

    @Inject
    private StartStopBusiness startStopBusiness;

    /**
     * Performs cleanup operations when the servlet context is destroyed.
     *
     * @param sce The ServletContextEvent representing the destruction of the servlet context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.log(Level.INFO, "Control layer destroyed.");
        startStopBusiness.destroy(sce);
    }

    /**
     * Initializes resources and business logic during servlet context initialization.
     *
     * @param sce The ServletContextEvent representing the initialization of the servlet context.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.log(Level.INFO, "Control Layer initialized.");
        startStopBusiness.init(sce);
    }
}
