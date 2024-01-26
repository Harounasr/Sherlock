package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * UI Test for the {@code submissionPagination.xhtml} facelet.
 *
 * @author Victor Vollmann
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubmissionPaginationUITest extends AbstractSeleniumUITest {

    /**
     * Inserts a valid submission into the database.
     *
     * @throws IOException When the files aren't readable.
     * @throws URISyntaxException When the uri is invalid.
     */
    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        SeleniumUITestUtils.insertSubmissionIntoDatabase();
    }

    /**
     * Test for viewing the pagination as a course member.
     */
    @Test
    @Order(1)
    void testCheckCorrectDataAsMember() {
        loginAndNavigateAsUser(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement submissionButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Submission']")));
        submissionButton.click();
        getDriver().findElement(By.id("submission-container"));
    }

    /**
     * Test for viewing the pagination as a tutor when a testate has not been created.
     */
    @Test
    @Order(2)
    void testCheckCorrectDataAsTutorNoTestate() {
        loginAndNavigateAsUser(SeleniumUITestUtils.TUTOR_USERNAME);
        WebElement testateButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='Create Testate']")));
        testateButton.click();
        getDriver().findElement(By.cssSelector("[id$='testate-container']"));
    }

    /**
     * Test for viewing the pagination as a tutor when a testate has been created.
     */
    @Test
    @Order(3)
    void testCheckCorrectDataAsTutorWithTestate() {
        SeleniumUITestUtils.insertTestateIntoDatabase();
        loginAndNavigateAsUser(SeleniumUITestUtils.TUTOR_USERNAME);
        WebElement testateButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Testate']")));
        testateButton.click();
        getDriver().findElement(By.cssSelector("[id$='testate-container']"));
    }

    /**
     * Logs in as the given user and navigates to the submissionPagination.xhtml facelet.
     *
     * @param username The username.
     */
    private void loginAndNavigateAsUser(String username) {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Submissions");
    }

}
