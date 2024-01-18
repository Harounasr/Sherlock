package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
 * UI Test for the {@code testatePagination.xhtml} facelet.
 */
@Disabled
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestatePaginationUITest extends AbstractSeleniumUITest {

    /**
     * Method to set up test data by inserting a submission and associated testate into the database.
     */
    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        SeleniumUITestUtils.insertSubmissionIntoDatabase();
        SeleniumUITestUtils.insertTestateIntoDatabase();
    }

    /**
     * Test method to simulate the presence of testates for a user with the role of MEMBER.
     */
    @Test
    @Order(1)
    void testExistTestateForUserAsMember() throws InterruptedException {
        loginAndNavigateToExercisePage(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement submissionButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='to testate']")));
        submissionButton.click();
        getDriver().findElement(By.id("testate-container"));

    }

    /**
     * Test method to simulate the absence of testates for a user with the role of MEMBER.
     */
    @Test
    @Order(2)
    void testNoExistTestateForUserAsMember() throws InterruptedException {
        loginAndNavigateToExercisePage(SeleniumUITestUtils.MEMBER2_USERNAME);
        WebElement noTestatesMessage = getDriver().findElement(By.xpath("//h3[contains(text(), 'There are no testates')]"));
        assertEquals("There are no testates", noTestatesMessage.getText());
    }

    /**
     * Test method to simulate the presence of correct testates for a user with the role of TUTOR.
     */
    @Test
    @Order(2)
    void correctTestateForTutor() throws InterruptedException {
        loginAndNavigateToExercisePage(SeleniumUITestUtils.TUTOR_USERNAME);
        WebElement submissionButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='to testate']")));
        submissionButton.click();
        getDriver().findElement(By.id("testate-container"));
    }

    /**
     * Test method to simulate the absence of testates for a user with the role of TUTOR.
     */
    @Test
    @Order(4)
    void noTestateForTutor() throws InterruptedException {
        loginAndNavigateToExercisePage(SeleniumUITestUtils.TUTOR2_USERNAME);
        WebElement noTestatesMessage = getDriver().findElement(By.xpath("//h3[contains(text(), 'There are no testates')]"));
        assertEquals("There are no testates", noTestatesMessage.getText());

    }

    private  void loginAndNavigateToExercisePage(String username) throws InterruptedException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        Thread.sleep(1000);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        Thread.sleep(1000);
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Testates");
        Thread.sleep(1000);

    }
}

