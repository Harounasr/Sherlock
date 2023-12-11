package de.ssherlock.persistence.util;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;

/**
 * Utility class for building content for different types of emails.
 *
 * @author Leon Höfling
 */
public final class MailContentBuilder {

  private static final String URL = "http://localhost:8016/ssherlock_war_exploded/view/public/";

  /** Default constructor. */
  private MailContentBuilder() {}

  /**
   * Builds the content for a verification email.
   *
   * @param user The user for whom the verification email is generated.
   * @param token The verification token.
   * @return The content of the verification email.
   */
  public static String buildVerificationMail(User user, String token) {
    return "Hi "
        + user.getUsername()
        + ".\nThank you for registration.\n"
        + "Please follow the link below in order to verify your account.\n"
        + URL
        + "verification.xhtml?token="
        + token;
  }

  /**
   * Builds the content for a password reset email.
   *
   * @param user The user for whom the password reset email is generated.
   * @param token The verification token.
   * @return The content of the password reset email.
   */
  public static String buildPasswordResetMail(User user, String token) {
    return "Hi "
        + user.getUsername()
        + ".\nThis email was sent to you because you requested a password reset."
        + "\nPlease follow this link in order to change your password: \n"
        + URL
        + "passwordForgotten.xhtml?token="
        + token
        + "\n If that was not you, please just ignore this email.";
  }

  /**
   * Builds the content for a reminder email related to a specific exercise.
   *
   * @param exercise The exercise for which the reminder is generated.
   * @return The content of the reminder email.
   */
  public static String buildReminderMail(Exercise exercise) {
    return "Hi."
        + "\nThis is a reminder that you have a upcoming deadline for this exercise: "
        + exercise.getName()
        + ".\nRecommended deadline: "
        + exercise.getRecommendedDeadline()
        + "\nObligatory deadline: "
        + exercise.getObligatoryDeadline();
  }
}
