package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * UI Test for the {@code submit.xhtml} facelet.
 */
@Disabled
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubmissionUITest extends AbstractSeleniumUITest {
    private static final String FILE_PATH = "de/ssherlock/test_data/zip-test-data/valid_submission.zip";

    /**
     * Test method to simulate the successful submission.
     */
    @Test
    @Order(1)
    void submitSuccess() throws URISyntaxException {

        loginAndNavigateToSubmitPage(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement inputFile = getDriver().findElement(By.cssSelector("[id$='file']"));
        URL fileUrl = Thread.currentThread().getContextClassLoader().getResource(FILE_PATH);
        inputFile.sendKeys(Paths.get(fileUrl.toURI()).toFile().getAbsolutePath());
        WebElement uploadElement = getDriver().findElement(By.cssSelector("[id$='upload-button']"));
        uploadElement.click();

        getDriver().findElement(By.cssSelector("[id$='submit-button']")).click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Submissions");
        WebElement submissionButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Submission']")));
        submissionButton.click();
        getDriver().findElement(By.id("submission-container"));
    }

    /**
     * Log in and navigate to the submit page for a given user.
     *
     * @param username The username.
     */
    private void loginAndNavigateToSubmitPage(String username) {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Submit");
    }


}
