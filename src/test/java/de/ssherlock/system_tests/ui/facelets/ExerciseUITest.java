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
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * UI Test for the {@code exercisePagination.xhtml} facelet.
 */
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExerciseUITest extends AbstractSeleniumUITest {

    /**
     * Test method to simulate cancelling the deletion of an exercise.
     */
    @Test
    @Order(1)
    void deleteExerciseCancel() throws InterruptedException {
        navigateToFirstExercise(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement cancelButton = getWait().until(elementToBeClickable(By.cssSelector(".btn.btn-secondary")));
        cancelButton.click();
        Thread.sleep(1000);
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        Thread.sleep(1000);
        Thread.sleep(1000);
    }

    /**
     * Test method to simulate the successful deletion of an exercise.
     */
    @Test
    @Order(2)
    void deleteExerciseSuccess() throws InterruptedException {
        navigateToFirstExercise(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement deleteButton = getWait().until(elementToBeClickable(By.cssSelector(".btn.btn-danger")));
        Thread.sleep(1000);
        deleteButton.click();
        Thread.sleep(1000);
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/course.xhtml?Id=1", getDriver().getCurrentUrl());
        Thread.sleep(1000);
    }

    /**
     * Navigate to the first exercise for a given user.
     */
    private void navigateToFirstExercise(String username) throws InterruptedException {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        Thread.sleep(1000);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        Thread.sleep(1000);
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        Thread.sleep(1000);
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        Thread.sleep(1000);
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Delete");
        Thread.sleep(1000);
    }
}
