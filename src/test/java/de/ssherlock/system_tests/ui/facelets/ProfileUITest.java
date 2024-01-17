package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the profile.xhtml facelet.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SuppressWarnings("checkstyle:MagicNumber")
public class ProfileUITest extends AbstractSeleniumUITest {

    /**
     * the admins username.
     */
    private static final String USERNAME_ADMIN = "admin";
    /**
     * The first name of the admin user.
     */
    private static final String FIRSTNAME_ADMIN = "User";
    /**
     * The last name of the admin user.
     */
    private static final String LASTNAME_ADMIN = "One";
    /**
     * The faculty of the admin user.
     */
    private static final String FACULTY_ADMIN = "Mathematik";

    /**
     * Checks if the correct content is displayed in the table.
     *
     * @throws InterruptedException thrown by thread.sleep.
     */
    @Test
    public void testCorrectContent() throws InterruptedException {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/profile.xhtml");
        Thread.sleep(1000);
        WebElement userNameBox = getDriver().findElement(By.cssSelector("[id$='username']"));
        String userName = userNameBox.getText();
        WebElement firstNameBox = getDriver().findElement(By.cssSelector("[id$='firstName']"));
        String firstName = firstNameBox.getText();
        WebElement lastNameBox = getDriver().findElement(By.cssSelector("[id$='lastName']"));
        String lastName = lastNameBox.getText();
        WebElement selectBox = getDriver().findElement(By.cssSelector("[id$='facultyDropdown']"));
        Select dropDown = new Select(selectBox);
        String faculty = dropDown.getFirstSelectedOption().getText();
        assertEquals(USERNAME_ADMIN, userName);
        assertEquals(FIRSTNAME_ADMIN, firstName);
        assertEquals(LASTNAME_ADMIN, lastName);
        assertEquals(FACULTY_ADMIN, faculty);
    }

    /**
     * Tests the change of faculty.
     *
     * @throws InterruptedException thrown by thread.sleep.
     * @throws SQLException         if no faculty could be found in the database for the user.
     */
    @Test
    public void testChangeFaculty() throws InterruptedException, SQLException {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/profile.xhtml");
        Thread.sleep(1000);
        WebElement selectBox = getDriver().findElement(By.cssSelector("[id$='facultyDropdown']"));
        Select dropDown = new Select(selectBox);
        dropDown.selectByValue("Informatik");
        Thread.sleep(1000);
        String query = """
                       SELECT faculty
                       FROM "user"
                       WHERE id = 1;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String faculty = resultSet.getString("faculty");
                assertEquals("Informatik", faculty);
            } else {
                fail("User could not be found.");
            }
        }
    }

    /**
     * Tests the delete process of an account.
     *
     * @throws InterruptedException thrown by thread.sleep.
     * @throws SQLException         would be thrown by the DB, but the resultset is expected to be empty.
     */
    @Test
    public void testDeleteAccount() throws InterruptedException, SQLException {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/profile.xhtml");
        Thread.sleep(1000);
        WebElement deleteButton = getDriver().findElement(By.cssSelector("[id$='deleteAccountButton']"));
        deleteButton.click();
        Thread.sleep(2000);
        String query = """
                       SELECT *
                       FROM "user"
                       WHERE id = 1;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            assertFalse(resultSet.next());
        }
    }
}
