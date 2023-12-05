package de.ssherlock.business.maintenance;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;

/**
 * Automatically sends an E-Mail to a user.
 *
 * @author Leon Höfling
 */
final class SendEmailNotificationEvent {

  /** Logger instance for logging messages related to CourseService. */
  private static final SerializableLogger LOGGER =
      LoggerCreator.get(SendEmailNotificationEvent.class);

  /** Defines the rate in which this Event should be executed. */
  public static final int EXECUTION_RATE = 60 * 60 * 3;

  /** Constructs a new SendEmailNotificationEvent. */
  SendEmailNotificationEvent() {}

  /**
   * Checks if SendEmailNotificationEvent is currently running.
   *
   * @return true/false according to the state of SendEmailNotificationEvent.
   */
  public boolean isRunning() {
    return false;
  }

  /** Shuts down the SendEmailNotificationEvent. */
  public void shutdown() {}

  /** Sends email notifications. */
  public void sendEmailNotifications() {}
}
