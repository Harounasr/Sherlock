package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Class for executing Events.
 */
public class MaintenanceProcessExecutor extends ScheduledThreadPoolExecutor {

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private static final SerializableLogger logger = LoggerCreator.getSerial(MaintenanceProcessExecutor.class);

    /**
     * Defines the number of simultaneous threads.
     */
    private static final int CORE_POOL_SIZE = 4;

    /**
     * Defines the time to wait until the threads are interrupted.
     */
    private static final int DESTROY_TIMEOUT = 70;

    /**
     * Constructs a new MaintenanceProcessExecutor.
     */
    public MaintenanceProcessExecutor() {
        super(CORE_POOL_SIZE);
    }

    /**
     * Initializes a thread.
     */
    public void init() {

    }

    /**
     * Destroys a thread.
     */
    public void destroy() {

    }
}
