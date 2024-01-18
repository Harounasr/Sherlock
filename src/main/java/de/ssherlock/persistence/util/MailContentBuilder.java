package de.ssherlock.persistence.util;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;

/**
 * Utility class for building content for different types of emails.
 *
 * @author Leon Höfling
 */
public final class MailContentBuilder {

    /**
     * The base URL for the verification links.
     */
  private static final String URL = "http://localhost:8016/ssherlock_war_exploded/view/public/";

  /** Default constructor. */
  private MailContentBuilder() {}

  /**
   * Builds the content for a verification email.
   *
   * @param user The user for whom the verification email is generated.
   * @return The content of the verification email.
   */
  public static String buildVerificationMail(User user) {

      return String.format("Hi %s.%nThank you for registration.%nPlease follow the link below in order to verify your "
                           + "account:%n%sverification.xhtml?token=%s",
                                                 user.getUsername(), URL, user.getVerificationToken());

  }

  /**
   * Builds the content for a password reset email.
   *
   * @param user The user for whom the password reset email is generated.
   * @return The content of the password reset email.
   */
  public static String buildPasswordResetMail(User user) {

      return String.format("Hi %s.%nThis email was sent to you because you requested a password reset.%nPlease follow this link in order to change "
                           + "your password: %n%spasswordReset.xhtml?token=%s%nIf that was not you, please just ignore this email.",
                                       user.getUsername(), URL, user.getVerificationToken());

  }

  /**
   * Builds the content for a reminder email related to a specific exercise.
   *
   * @param exercise The exercise for which the reminder is generated.
   * @return The content of the reminder email.
   */
  public static String buildReminderMail(Exercise exercise) {

      return String.format("Hi.%nThis is a reminder that you have an upcoming "
                                             + "deadline for this exercise: %s.%nObligatory deadline: %s",
                                             exercise.getName(), exercise.getObligatoryDeadline());

  }
}
