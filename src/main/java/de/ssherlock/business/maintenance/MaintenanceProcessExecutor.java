package de.ssherlock.business.maintenance;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * Class for executing Events.
 *
 * @author Victor Vollmann
 */
public class MaintenanceProcessExecutor extends ScheduledThreadPoolExecutor {

    /** Logger instance for logging messages related to CourseService. */
    private static final SerializableLogger LOGGER =
            LoggerCreator.get(MaintenanceProcessExecutor.class);

    /** Defines the number of simultaneous threads. */
    private static final int CORE_POOL_SIZE = 4;

    /** Defines the time to wait until the threads are interrupted. */
    private static final int DESTROY_TIMEOUT = 70;


    /** The rate at which the maintenance process is executed. */
    private static final int MAINTENANCE_RATE = 60 * 60; // Execute every hour


    /** The interval at which the clean task is executed. */
    private static final int CLEAN_INTERVAL = 60 * 60 * 24; // Execute every 24 hours

    /** Constructs a new MaintenanceProcessExecutor. */
    public MaintenanceProcessExecutor() {
        super(CORE_POOL_SIZE);
    }

    /** Initializes a thread. */
    public void init() {
        this.scheduleAtFixedRate(this::executeEmailNotifications,
                                  0, MAINTENANCE_RATE, TimeUnit.SECONDS);
        this.scheduleWithFixedDelay(this::executeCleanUnverifiedUsers,
                                    0, CLEAN_INTERVAL, TimeUnit.SECONDS);
        this.scheduleWithFixedDelay(this::executeCleanUnusedImages,
                                    0, CLEAN_INTERVAL, TimeUnit.SECONDS);
    }

    /** Destroys a thread. */
    public void destroy() {
      try {
          this.shutdown();
        this.awaitTermination(DESTROY_TIMEOUT, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
          LOGGER.log(Level.INFO, "Error while destroying MaintenanceProcessExecutor" + e);
          Thread.currentThread().interrupt();
      }
    }

    /**
     * Executes send email notification task.
     */
     private void executeEmailNotifications() {
         SendEmailNotificationEvent sendEmailNotificationEvent = new SendEmailNotificationEvent();
         sendEmailNotificationEvent.sendEmailNotifications();
     }

     /**
      * Executes clean unverified users task.
      */
     private void executeCleanUnverifiedUsers() {
        UnverifiedUsersCleanEvent unverifiedUsersCleanEvent = new UnverifiedUsersCleanEvent();
        unverifiedUsersCleanEvent.cleanUnverifiedUsers();
    }

    /**
     * Executes clean unused images task.
     */
    private void executeCleanUnusedImages() {
        UnusedImagesCleanEvent unusedImagesCleanEvent = new UnusedImagesCleanEvent();
        unusedImagesCleanEvent.cleanUnusedImages();
    }
}
