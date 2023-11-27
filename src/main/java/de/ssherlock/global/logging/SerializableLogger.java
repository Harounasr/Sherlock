package de.ssherlock.global.logging;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* A serializable wrapper for the Logger to enable serialization.
 *
 * @author Leon Höfling
*/
public class SerializableLogger implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The wrapped logger.
     */
    private transient Logger logger;

    /**
    * Constructor for SerializableLogger.
    *
    * @param logger The Logger instance to wrap.
    */
    public SerializableLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Logs a message to the log file with specified log level.
     *
     * @param level The level.
     * @param msg   The message.
     */
    public void log(Level level, String msg) {
        logger.log(level, msg);
    }

    /**
     * Logs a message with a specific log level and a Throwable.
     *
     * @param level     The level.
     * @param msg       The message.
     * @param throwable Throwable associated with the log message.
     */
    public void log(Level level, String msg, Throwable throwable) {
        logger.log(level, msg, throwable);
    }

    /**
     * Logs a message with the FINEST level.
     *
     * @param msg The message.
     */
    public void finest(String msg) {
        logger.finest(msg);
    }

    /**
     * Logs a message with the FINER level.
     *
     * @param msg The message.
     */
    public void finer(String msg) {
        logger.finer(msg);
    }

    /**
     * Logs a message with the FINE level.
     *
     * @param msg The message.
     */
    public void fine(String msg) {
        logger.fine(msg);
    }

    /**
     * Logs a message with the CONFIG level.
     *
     * @param msg The message.
     */
    public void config(String msg) {
        logger.config(msg);
    }

    /**
     * Logs a message with the INFO level.
     *
     * @param msg The message.
     */
    public void info(String msg) {
        logger.info(msg);
    }

    /**
     * Logs a message with the WARNING level.
     *
     * @param msg The message.
     */
    public void warning(String msg) {
        logger.warning(msg);
    }

    /**
     * Logs a message with the SEVERE level.
     *
     * @param msg The message.
     */
    public void severe(String msg) {
        logger.severe(msg);
    }

}
