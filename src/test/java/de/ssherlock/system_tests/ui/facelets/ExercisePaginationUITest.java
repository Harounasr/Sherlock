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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;


/**
 * UI test class for the exercise pagination facelet.
 */

@Disabled
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExercisePaginationUITest extends AbstractSeleniumUITest {

    /**
     * Test case for canceling the exercise creation dialog.
     */
    @Test
    @Order(1)
    void testCreateExerciseDialogFail() throws InterruptedException {
        loginAndNavigateToCourse(SeleniumUITestUtils.ADMIN_USERNAME);
        Thread.sleep(1000);
        WebElement createExerciseButton = getWait().until(elementToBeClickable(By.cssSelector(".btn.bg-success.text-light")));
        Thread.sleep(1000);
        createExerciseButton.click();
        Thread.sleep(1000);

        WebElement createExerciseModal = getWait().until(elementToBeClickable(By.id("createExerciseModal")));
        assertTrue(createExerciseModal.isDisplayed(), "Create Exercise modal is displayed");
        Thread.sleep(1000);

        WebElement cancelButton = getWait().until(elementToBeClickable(By.cssSelector(".btn.bg-danger.text-light")));
        cancelButton.click();
        Thread.sleep(1000);
    }

    /**
     * Test case for creating an exercise in the dialog.
     */
    @Test
    @Order(2)
    void testCreateExerciseDialogSuccess() throws InterruptedException {
        loginAndNavigateToCourse(SeleniumUITestUtils.ADMIN_USERNAME);
        Thread.sleep(1000);

        WebElement createExerciseButton = getWait().until(elementToBeClickable(By.cssSelector(".btn.bg-success.text-light")));
        createExerciseButton.click();
        Thread.sleep(1000);

        WebElement createExerciseModal = getWait().until(elementToBeClickable(By.id("createExerciseModal")));
        assertTrue(createExerciseModal.isDisplayed(), "Create Exercise modal is displayed");
        Thread.sleep(1000);

        getDriver().findElement(By.id("new exercise:newExerciseName")).sendKeys("new Exercise");

        Thread.sleep(1000);
        WebElement createButton = getWait().until(elementToBeClickable(By.cssSelector(".btn.bg-success.text-light")));
        createButton.click();
        Thread.sleep(1000);
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/course.xhtml?Id=1", getDriver().getCurrentUrl());
        Thread.sleep(1000);

    }

    /**
     * Log in and navigate to a course.
     *
     * @param username The username.
     */
    private void loginAndNavigateToCourse(String username) throws InterruptedException {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        Thread.sleep(1000);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        Thread.sleep(1000);
    }
}










