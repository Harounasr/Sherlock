package de.ssherlock.business.maintenance;

import de.ssherlock.business.exception.MaintenanceConfigNotReadableException;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.servlet.ServletContextEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Class for executing Events.
 *
 * @author Haroun Alswedany
 */
public class MaintenanceProcessExecutor extends ScheduledThreadPoolExecutor {

    /**
     * Logger instance for logging messages related to CourseService.
     */
    private static final SerializableLogger LOGGER =
            LoggerCreator.get(MaintenanceProcessExecutor.class);

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
     *
     * @param sce The Servlet context event of the StartStop instance.
     */
    public void init(ServletContextEvent sce) {
        Properties properties = new Properties();
        try (InputStream is = sce.getServletContext().getResourceAsStream("/WEB-INF/config/maintenance-config.properties")) {
            if (is != null) {
                properties.load(is);
            } else {
                throw new MaintenanceConfigNotReadableException("The configuration for the maintenance properties is not readable.");
            }
        } catch (IOException e) {
            throw new MaintenanceConfigNotReadableException("The configuration for the maintenance properties is not readable.", e);
        }
        LOGGER.info("Loaded maintenance configuration.");
        long startDelay = Long.parseLong(properties.getProperty("start.delay"));


        scheduleWithFixedDelay(new UnverifiedUsersCleanEvent(),
                                    startDelay,  Long.parseLong(properties.getProperty("unverifiedUsersClean.delay")), TimeUnit.SECONDS);
        LOGGER.info("Scheduled UnverifiedUsersCleanEvent at rate " + properties.getProperty("unverifiedUsersClean.delay") + " (seconds).");

        scheduleWithFixedDelay(new SendEmailNotificationEvent(),
                                 startDelay, Long.parseLong(properties.getProperty("sendEmailNotification.delay")), TimeUnit.SECONDS);
        LOGGER.info("Scheduled SendEmailNotificationEvent at rate " + properties.getProperty("sendEmailNotification.delay") + " (seconds).");

        scheduleWithFixedDelay(new UnusedImagesCleanEvent(),
                                    startDelay, Long.parseLong(properties.getProperty("unusedImagesClean.delay")), TimeUnit.SECONDS);
        LOGGER.info("Scheduled UnusedImagesCleanEvent at rate " + properties.getProperty("unusedImagesClean.delay") + " (seconds).");

        scheduleWithFixedDelay(new ResetPasswordAttemptsEvent(),
                                    startDelay, Long.parseLong(properties.getProperty("resetPasswordAttempts.delay")), TimeUnit.SECONDS);
        LOGGER.info("Scheduled ResetPasswordAttempts at rate " + properties.getProperty("resetPasswordAttempts.delay") + " (seconds).");

    }

    /**
     * Destroys a thread.
     */
    public void destroy() {
        try {
            this.shutdown();
            this.awaitTermination(DESTROY_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, "Error while destroying MaintenanceProcessExecutor" + e);
            Thread.currentThread().interrupt();
        }
    }
}
