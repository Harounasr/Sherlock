package de.ssherlock.global.logging;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* A serializable wrapper for Logger to enable serialization.
*/
public class SerializableLogger implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;
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
     * Logs a message to the log file.
     *
     * @param level The level.
     * @param msg The message.
     */
    public void log(Level level, String msg) {
        logger.log(level, msg);
    }


}
