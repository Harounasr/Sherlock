package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
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
 * UI Test for {@code verification.xhtml}.
 *
 * @author Leon Höfling
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationUITest extends AbstractSeleniumUITest {

    /**
     * Test for clicking on the back to login button.
     * User should be redirected to the login Facelet.
     */
    @Test
    void testNavigateToLogin() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/verification.xhtml?token=someToken");
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "verificationForm:backToLogin");
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/public/login.xhtml", getDriver().getCurrentUrl());
    }

    /**
     * Test for verifying user 'verificationUser', which has the token '12345' and id 12.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    void testVerification() throws InterruptedException, SQLException {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/verification.xhtml?token=12345");
        Thread.sleep(3000);
        String query = """
                       SELECT user_role
                       FROM "user"
                       WHERE id = 12;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SystemRole systemRole = SystemRole.valueOf(resultSet.getString("user_role"));
                assertEquals(SystemRole.REGISTERED, systemRole);
            } else {
                fail("User could not be found.");
            }
        }
    }
}
