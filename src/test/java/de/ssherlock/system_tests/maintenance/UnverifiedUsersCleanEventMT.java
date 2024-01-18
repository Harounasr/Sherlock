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
 * Test class for {@link de.ssherlock.business.maintenance.UnverifiedUsersCleanEvent}.
 *
 * @author Victor Vollmann
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnverifiedUsersCleanEventMT {

    /**
     * The id of the user that is not registered and not expired.
     */
    private static final int NOT_REGISTERED_NOT_EXPIRED_ID = 12;

    /**
     * There is one unverified user in the testdata that has expired
     * and one that has not.
     */
    @Test
    void testUnverifiedUsersClean() {
        String getUnverifiedUsersSql =
                """
                SELECT id
                FROM "user"
                WHERE user_role = ('NOT_REGISTERED'::system_role)
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(getUnverifiedUsersSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals(NOT_REGISTERED_NOT_EXPIRED_ID, resultSet.getInt("id"));
                if (resultSet.next()) {
                    fail("No user has been deleted");
                }
            } else {
                fail("Both users have been deleted.");
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
