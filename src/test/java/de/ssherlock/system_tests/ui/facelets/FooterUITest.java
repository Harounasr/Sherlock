package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the footer.xhtml facelet.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("checkstyle:MagicNumber")
public class FooterUITest extends AbstractSeleniumUITest {

    /**
     * Tests if the footer content is correclty displayed.
     *
     * @throws SQLException if the footer text cannot be retrieved from the database.
     */
    @Test
    public void testFooter() throws SQLException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        String footerText = getDriver().findElement(By.cssSelector("[id$='footerText']")).getText();
        String query = """
                       SELECT imprint
                       from system_settings;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals(footerText, resultSet.getString("imprint"));
            } else {
                fail("could not find imprint in db");
            }
        }
    }
}
