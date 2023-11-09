package de.ssherlock.business.util;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;

public class MailContentBuilder {
    public static String buildVerificationMail(User user) {
        return "Please verify!";
    }
    public static String buildPasswordResetMail(User user) {
        return null;
    }
    public static String buildReminderMail(User user, Exercise exercise) {
        return null;
    }

}
