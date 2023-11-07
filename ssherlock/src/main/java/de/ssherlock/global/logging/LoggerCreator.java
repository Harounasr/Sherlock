package de.ssherlock.global.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerCreator {
    public static Logger get(Class<?> target) {
        Logger logger = Logger.getLogger(target.getName());
        logger.setLevel(Level.FINEST);
        return logger;
    }
}
