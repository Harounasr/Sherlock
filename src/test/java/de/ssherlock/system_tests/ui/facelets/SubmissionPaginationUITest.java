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



    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        SeleniumUITestUtils.insertSubmissionIntoDatabase();
    }

    @Test
    @Order(1)
    void testCheckCorrectDataAsMember() {
        loginAndNavigateAsUser(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement submissionButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Submission']")));
        submissionButton.click();
        getDriver().findElement(By.id("submission-container"));
    }

    @Test
    @Order(2)
    void testCheckCorrectDataAsTutorNoTestate() {
        loginAndNavigateAsUser(SeleniumUITestUtils.TUTOR_USERNAME);
        WebElement testateButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='Create Testate']")));
        testateButton.click();
        getDriver().findElement(By.id("testate-container"));
    }

    @Test
    @Order(3)
    void testCheckCorrectDataAsTutorWithTestate() {
        SeleniumUITestUtils.insertTestateIntoDatabase();
        loginAndNavigateAsUser(SeleniumUITestUtils.TUTOR_USERNAME);
        WebElement testateButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Testate']")));
        testateButton.click();
        getDriver().findElement(By.id("testate-container"));
    }

    private static void loginAndNavigateAsUser(String username) {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Submissions");
    }

}
