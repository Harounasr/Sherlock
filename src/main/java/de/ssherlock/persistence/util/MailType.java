package de.ssherlock.persistence.util;

/**
 * An Enum for the Mailtypes.
 *
 * @author Leon Höfling
 */
public enum MailType {

    /**
     * Enum for a login verification email.
     */
    VERIFICATION,

    /**
     * Enum for a reminder email.
     */
    REMINDER,

    /**
     * Enum for a password-reset email.
     */
    PASSWORD
}
