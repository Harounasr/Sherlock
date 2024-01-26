package de.ssherlock.persistence.util;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link MailContentBuilder}.
 * The test fails locally if you do not replace all "\n" with "\r\n".
 *
 * @author Leon Höfling
 */
public class MailContentBuilderIT {

    /**
     * The Base url for the verification link.
     */
    private static final String URL = "http://localhost:8080/ssherlock_war_exploded/view/public/";

    /**
     * The user for testing.
     */
    private static User testUser;

    /**
     * The exercise for testing.
     */
    private static Exercise testExercise;

    /**
     * Initializes the test environment.
     */
    @BeforeAll
    public static void initTestEnvironment() {
        testUser = new User();
        testUser.setUsername("TestUser");
        testUser.setVerificationToken("TestToken123");
        testExercise = new Exercise();
        testExercise.setName("TestExercise");
        testExercise.setObligatoryDeadline(Timestamp.valueOf("2024-02-30 00:00:00"));
    }

    /**
     * Test for building the contents of a verification mail.
     */
    @Test
    public void testBuildVerificationMail() {
        String msg = MailContentBuilder.buildVerificationMail(testUser);
        String expectedMsg = "Hi " + testUser.getUsername() + ".\r\nThank you for registration.\r\nPlease follow the link below in order to verify "
                             + "your account:\r\n" + URL + "verification.xhtml?token=" + testUser.getVerificationToken();
        assertEquals(expectedMsg, msg);
    }

    /**
     * Test for building the contents of a password-reset mail.
     */
    @Test
    public void testBuildPasswordResetMail() {
        String msg = MailContentBuilder.buildPasswordResetMail(testUser);
        String expectedMsg = "Hi " + testUser.getUsername() + ".\r\nThis email was sent to you because you requested a password reset.\r\nPlease "
                             + "follow this link in order to change your password: \r\n" + URL + "passwordReset.xhtml?token="
                             + testUser.getVerificationToken() + "\r\nIf that was not you, please just ignore this email.";
        assertEquals(expectedMsg, msg);
    }

    /**
     * Test for building the contents of a reminder mail.
     */
    @Test
    public void testBuildReminderMail() {
        String msg = MailContentBuilder.buildReminderMail(testExercise);
        String expectedMsg = "Hi.\r\nThis is a reminder that you have an upcoming deadline for this exercise: " + testExercise.getName()
                             + ".\r\nObligatory deadline: " + testExercise.getObligatoryDeadline();
        assertEquals(expectedMsg, msg);
    }

}
