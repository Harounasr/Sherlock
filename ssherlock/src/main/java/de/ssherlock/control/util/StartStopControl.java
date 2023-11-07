package de.ssherlock.control.util;

import de.ssherlock.business.util.StartStopBusiness;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StartStopControl implements ServletContextListener {

    StartStopBusiness startStopBusiness;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        startStopBusiness.destroy();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        startStopBusiness = new StartStopBusiness();
        ServletContext servletContext = sce.getServletContext();
        startStopBusiness.init(servletContext::getResourceAsStream);
    }

}