package de.ssherlock.system_tests.maintenance;

import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link de.ssherlock.business.maintenance.SendEmailNotificationEvent}
 * maintenance event.
 *
 * @author Victor Vollmann
 */
public class SendEmailNotificationEventST {

    /**
     * Tests whether the emails are sent.
     *
     * @throws InterruptedException When the thread is interrupted.
     */
    @Test
    void testSendEmailNotifications() throws InterruptedException {
        setDeadlinesToEndSoon();
        // wait for event to fire.
        Thread.sleep(23000);
        checkReminderEmailSent();
    }

    /**
     * Sets the deadlines in exercise 7 to end tomorrow.
     */
    private static void setDeadlinesToEndSoon() {
        String updateDeadlinesSql =
                """
                UPDATE exercise
                SET reminder_mail_sent = false,
                    recommended_deadline = ?,
                    obligatory_deadline = ?,
                    publish_date = ?
                WHERE id = 11;
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(updateDeadlinesSql)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            Timestamp tomorrow = new Timestamp(calendar.getTime().getTime());
            statement.setTimestamp(1, tomorrow);
            statement.setTimestamp(2, tomorrow);
            statement.setTimestamp(3, tomorrow);
            statement.executeUpdate();
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Checks whether the emails have been sent for exercise 11.
     */
    private static void checkReminderEmailSent() {
        String checkEmailSent =
                """
                SELECT reminder_mail_sent
                FROM exercise
                WHERE id = 11;
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(checkEmailSent)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                assertTrue(resultSet.getBoolean("reminder_mail_sent"));
            } else {
                fail("Exercise with id 1 could not be found.");
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

}
