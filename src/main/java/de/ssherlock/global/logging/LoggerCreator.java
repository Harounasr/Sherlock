package de.ssherlock.global.logging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.servlet.ServletContextEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Application-scoped utility class for creating and configuring Logger instances. This class
 * provides methods to obtain a Logger instance, read logging configurations, and produce Logger
 * instances for injection using CDI.
 *
 * @author Victor Vollmann
 */
@ApplicationScoped
public final class LoggerCreator implements Serializable {

  /** Serial Version UID */
  @Serial private static final long serialVersionUID = 1L;

  /** Default logger instance used for internal logging within the LoggerCreator class. */
  private static final SerializableLogger LOGGER = get(LoggerCreator.class);

  /** Default constructor. */
  private LoggerCreator() {}

  /**
   * Obtains a SerializableLogger instance for the specified target class.
   *
   * @param target The class for which a Logger instance is requested.
   * @return The Logger instance for the specified class.
   */
  public static SerializableLogger get(Class<?> target) {
    Logger logger = Logger.getLogger(target.getName());
    logger.setLevel(Level.FINEST);
    return new SerializableLogger(logger);
  }

  /**
   * Reads logging configurations from the specified resource using the provided resourceFetcher
   * function.
   *
   * @param sce The servlet context event of the initialization.
   */
  public static void readConfig(ServletContextEvent sce) {
    try {
      InputStream input =
          sce.getServletContext()
              .getResourceAsStream("/WEB-INF/config/logger-database-config.properties");
      if (input == null) {
        throw new FileNotFoundException("Log config not found");
      }
      LogManager.getLogManager().readConfiguration(input);
      LOGGER.log(Level.INFO, "Log config loaded");
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Log Config not loaded");
    }
  }

  /**
   * Produces a Logger instance for injection using CDI.
   *
   * @param injectionPoint The injection point where the Logger instance is requested.
   * @return The Logger instance for injection.
   */
  @Produces
  public SerializableLogger produce(InjectionPoint injectionPoint) {
    return get(injectionPoint.getMember().getDeclaringClass());
  }
}
