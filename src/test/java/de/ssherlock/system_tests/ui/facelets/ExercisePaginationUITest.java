package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Test class for the exercisePagination.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExercisePaginationUITest extends AbstractSeleniumUITest {

    /**
     * Creates a new exercise successfully.
     */
    @Test
    @Order(1)
    void testCreateExerciseSuccess() {
        tryAddExercise();
        Notification notification = new Notification("Exercise: Test Exercise created successfully.", NotificationType.SUCCESS);
        SeleniumUITestUtils.checkNotification(getWait(), notification);
    }

    /**
     * Creates a new exercise and fails.
     */
    @Test
    @Order(2)
    void testCreateExerciseFailure() {
        tryAddExercise();
        Notification notification = new Notification("Exercise with the same name already exists.", NotificationType.ERROR);
        SeleniumUITestUtils.checkNotification(getWait(), notification);
    }

    /**
     * Adds an exercise.
     */
    private void tryAddExercise() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement openModalButton = getWait().until(elementToBeClickable(By.cssSelector("[id$='open-exercise-modal']")));
        openModalButton.click();
        WebElement newCourseName = getWait().until(visibilityOfElementLocated(By.cssSelector("[id$='newExerciseName']")));
        newCourseName.sendKeys("Test Exercise");
        WebElement createCourseButton = getWait().until(elementToBeClickable(By.cssSelector("[id$='create-exercise-button']")));
        createCourseButton.click();
    }
}
