package de.ssherlock.persistence.util;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;

/**
 * Utility class for building content for different types of emails.
 */
public class MailContentBuilder {

    /**
     * Builds the content for a verification email.
     *
     * @param user The user for whom the verification email is generated.
     * @return The content of the verification email.
     */
    public static String buildVerificationMail(User user) {
        return "Please verify!";
    }

    /**
     * Builds the content for a password reset email.
     *
     * @param user The user for whom the password reset email is generated.
     * @return The content of the password reset email.
     */
    public static String buildPasswordResetMail(User user) {
        return null;
    }

    /**
     * Builds the content for a reminder email related to a specific exercise.
     *
     * @param user     The user to whom the reminder email is sent.
     * @param exercise The exercise for which the reminder is generated.
     * @return The content of the reminder email.
     */
    public static String buildReminderMail(User user, Exercise exercise) {
        return null;
    }
}

