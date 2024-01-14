package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Runtime exception indicating that the maintenance configuration file is not readable.
 *
 * @author Victor Vollmann
 */
public class MaintenanceConfigNotReadableException extends RuntimeException {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new MaintenanceConfigNotReadableException.
     */
    public MaintenanceConfigNotReadableException() {
        super();
    }

    /**
     * Constructs a new MaintenanceConfigNotReadableException with the specified detail message.
     *
     * @param message The detail message.
     */
    public MaintenanceConfigNotReadableException(String message) {
        super(message);
    }

    /**
     * Constructs a new MaintenanceConfigNotReadableException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public MaintenanceConfigNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }

}
