package de.ssherlock.system_tests.maintenance;

import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for {@link de.ssherlock.business.maintenance.ResetPasswordAttemptsEvent}.
 *
 * @author Victor Vollmann
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResetPasswordAttemptsEventMT {

    /**
     * Tests the event. The testdata contains users with failed password attempts.
     * The event should be fired shortly after system start.
     */
    @Test
    void testResetPasswordAttempts() throws InterruptedException {
        // Wait for event to be fired (remove when email event is enabled)
        Thread.sleep(10000);
        String checkPasswordAttemptsSql =
                """
                SELECT failed_login_attempts
                FROM "user";
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(checkPasswordAttemptsSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assertEquals(0, resultSet.getInt("failed_login_attempts"));
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

}
