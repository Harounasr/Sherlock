package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.Faculty;
import de.ssherlock.global.transport.SystemRole;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileUITest extends AbstractSeleniumUITest {

    private final String USERNAME_ADMIN = "admin";
    private final String FIRSTNAME_ADMIN = "User";
    private final String LASTNAME_ADMIN = "One";
    private final String FACULTY_ADMIN = "Mathematik";

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
        String Faculty = dropDown.getFirstSelectedOption().getText();
        assertEquals(USERNAME_ADMIN, userName);
        assertEquals(FIRSTNAME_ADMIN, firstName);
        assertEquals(LASTNAME_ADMIN, lastName);
        assertEquals(FACULTY_ADMIN, Faculty);
    }

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
