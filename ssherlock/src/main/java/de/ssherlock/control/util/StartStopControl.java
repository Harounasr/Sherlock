package de.ssherlock.control.util;

import de.ssherlock.business.util.StartStopBusiness;
import de.ssherlock.global.logging.LoggerCreator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Servlet context listener for initializing and destroying resources during application startup and shutdown.
 */
@WebListener
public class StartStopControl implements ServletContextListener {

    /**
     * Logger instance for logging messages related to StartStopControl.
     */
    private final Logger logger = LoggerCreator.get(StartStopControl.class);

    /**
     * Business logic handler for startup and shutdown operations.
     */
    StartStopBusiness startStopBusiness;

    /**
     * Performs cleanup operations when the servlet context is destroyed.
     *
     * @param sce The ServletContextEvent representing the destruction of the servlet context.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        startStopBusiness.destroy();
    }

    /**
     * Initializes resources and business logic during servlet context initialization.
     *
     * @param sce The ServletContextEvent representing the initialization of the servlet context.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        startStopBusiness = new StartStopBusiness();
        ServletContext servletContext = sce.getServletContext();
        LoggerCreator.readConfig(servletContext::getResourceAsStream);
        startStopBusiness.init(servletContext::getResourceAsStream);
        logger.log(Level.INFO, "Control Layer initialized");
    }
}
