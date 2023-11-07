package de.ssherlock.control.util;

import de.ssherlock.business.util.StartStopBusiness;
import de.ssherlock.global.logging.LoggerCreator;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class StartStopControl implements ServletContextListener {

    private final Logger logger = LoggerCreator.get(StartStopControl.class);
    StartStopBusiness startStopBusiness;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        startStopBusiness.destroy();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        startStopBusiness = new StartStopBusiness();
        ServletContext servletContext = sce.getServletContext();
        LoggerCreator.readConfig(servletContext::getResourceAsStream);
        startStopBusiness.init(servletContext::getResourceAsStream);
        logger.log(Level.INFO, "Control Layer initialized");
    }

}