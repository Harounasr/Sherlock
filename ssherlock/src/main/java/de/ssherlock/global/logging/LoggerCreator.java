package de.ssherlock.global.logging;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerCreator {

    private static final Logger logger = get(LoggerCreator.class);
    public static Logger get(Class<?> target) {
        Logger logger = Logger.getLogger(target.getName());
        logger.setLevel(Level.FINEST);
        return logger;
    }

    public static void readConfig(Function<String, InputStream> resourceFetcher) {
            try {
                InputStream input = resourceFetcher.apply("/WEB-INF/config/logger-config.properties");
                if (input == null) {
                    throw new FileNotFoundException("log config not found");
                }
                LogManager.getLogManager().readConfiguration(input);
                logger.log(Level.INFO, "Log config loaded");
            } catch (IOException e) {
                logger.log(Level.WARNING, "Log Config not loaded");
            }
    }
}
