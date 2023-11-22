package de.ssherlock.global.logging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.io.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Application-scoped utility class for creating and configuring Logger instances.
 * This class provides methods to obtain a Logger instance, read logging configurations, and produce Logger instances
 * for injection using CDI.
 */
@ApplicationScoped
public class LoggerCreator implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default logger instance used for internal logging within the LoggerCreator class.
     */
    private static final Logger logger = get(LoggerCreator.class);

    /**
     * Obtains a Logger instance for the specified target class.
     *
     * @param target The class for which a Logger instance is requested.
     * @return The Logger instance for the specified class.
     */
    public static Logger get(Class<?> target) {
        Logger logger = Logger.getLogger(target.getName());
        logger.setLevel(Level.FINEST);
        return logger;
    }

    public static SerializableLogger getSerial(Class<?> target) {
        Logger logger = Logger.getLogger(target.getName());
        logger.setLevel(Level.FINEST);
        return new SerializableLogger(logger);
    }

    /**
     * Reads logging configurations from the specified resource using the provided resourceFetcher function.
     *
     * @param resourceFetcher The function to fetch an InputStream for the logging configuration resource.
     */
    public static void readConfig(Function<String, InputStream> resourceFetcher) {
        try {
            InputStream input = resourceFetcher.apply("/WEB-INF/config/config.properties");
            if (input == null) {
                throw new FileNotFoundException("Log config not found");
            }
            LogManager.getLogManager().readConfiguration(input);
            logger.log(Level.INFO, "Log config loaded");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Log Config not loaded");
        }
    }

    /**
     * Produces a Logger instance for injection using CDI.
     *
     * @param injectionPoint The injection point where the Logger instance is requested.
     * @return The Logger instance for injection.
     */
    @Produces
    public Logger produce(InjectionPoint injectionPoint) {
        return get(injectionPoint.getMember().getDeclaringClass());
    }

    @Produces
    public SerializableLogger produceSerial(InjectionPoint injectionPoint) {
        Logger logger = get(injectionPoint.getMember().getDeclaringClass());
        return new SerializableLogger(logger);
    }
}