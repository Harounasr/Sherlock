package de.ssherlock.global.logging;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A serializable wrapper for the Logger to enable serialization.
 *
 * @author Victor Vollmann
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
    private final transient Logger logger;

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
        logWithCallerInfo(level, msg);
    }

    /**
     * Logs a message with a specific log level and a Throwable.
     *
     * @param level     The level.
     * @param msg       The message.
     * @param throwable Throwable associated with the log message.
     */
    public void log(Level level, String msg, Throwable throwable) {
        logWithCallerInfo(level, msg, throwable);
    }

    /**
     * Logs a message with the FINEST level.
     *
     * @param msg The message.
     */
    public void finest(String msg) {
        logWithCallerInfo(Level.FINEST, msg);
    }

    /**
     * Logs a message with the FINER level.
     *
     * @param msg The message.
     */
    public void finer(String msg) {
        logWithCallerInfo(Level.FINER, msg);
    }

    /**
     * Logs a message with the FINE level.
     *
     * @param msg The message.
     */
    public void fine(String msg) {
        logWithCallerInfo(Level.FINE, msg);
    }

    /**
     * Logs a message with the CONFIG level.
     *
     * @param msg The message.
     */
    public void config(String msg) {
        logWithCallerInfo(Level.CONFIG, msg);
    }

    /**
     * Logs a message with the INFO level.
     *
     * @param msg The message.
     */
    public void info(String msg) {
        logWithCallerInfo(Level.INFO, msg);
    }

    /**
     * Logs a message with the WARNING level.
     *
     * @param msg The message.
     */
    public void warning(String msg) {
        logWithCallerInfo(Level.WARNING, msg);
    }

    /**
     * Logs a message with the SEVERE level.
     *
     * @param msg The message.
     */
    public void severe(String msg) {
        logWithCallerInfo(Level.SEVERE, msg);
    }

    /**
     * Logs a message with the appropriate caller information.
     *
     * @param level The level.
     * @param msg The message.
     */
    private void logWithCallerInfo(Level level, String msg) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String className = stackTrace[2].getClassName();
        String methodName = stackTrace[2].getMethodName();
        logger.logp(level, className, methodName, msg);
    }

    /**
     * Logs a message with the appropriate caller information.
     *
     * @param level The level.
     * @param msg The message.
     * @param throwable The throwable.
     */
    private void logWithCallerInfo(Level level, String msg, Throwable throwable) {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String className = stackTrace[2].getClassName();
        String methodName = stackTrace[2].getMethodName();
        logger.logp(level, className, methodName, msg, throwable);
    }
}
