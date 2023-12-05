package de.ssherlock.control.util;

import de.ssherlock.business.util.StartStopBusiness;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * Handles start and stop functionalities for the control layer.
 *
 * @author Leon Höfling
 */
@WebListener
@ApplicationScoped
public class StartStopControl implements ServletContextListener, Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger instance for logging messages related to StartStopControl. */
  @Inject private SerializableLogger logger;

  /** The StartStop instance of the business layer. */
  @Inject private StartStopBusiness startStopBusiness;

  /** Default Constructor. */
  public StartStopControl() {}

  /**
   * Destroys the system.
   *
   * @param sce The ServletContextEvent representing the destruction of the servlet context.
   */
  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    logger.log(Level.INFO, "Control layer destroyed.");
    startStopBusiness.destroy(sce);
  }

  /**
   * Initializes the system.
   *
   * @param sce The ServletContextEvent representing the initialization of the servlet context.
   */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    logger.log(Level.INFO, "Control Layer initialized.");
    startStopBusiness.init(sce);
  }
}
