package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
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
 *
 * @author Haroun Alswedany
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExerciseUITest extends AbstractSeleniumUITest {

    /**
     * Test method to simulate cancelling the deletion of an exercise.
     */
    @Test
    @Order(1)
    void deleteExerciseCancel() {
        navigateToFirstExercise(SeleniumUITestUtils.ADMIN_USERNAME);
        WebElement cancelButton = getWait().until(elementToBeClickable(By.cssSelector("[id$=cancel-delete-button]")));
        cancelButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
    }

    /**
     * Test method to simulate the successful deletion of an exercise.
     */
    @Test
    @Order(2)
    void deleteExerciseSuccess() {
        navigateToFirstExercise(SeleniumUITestUtils.ADMIN_USERNAME);
        WebElement confirmButton = getWait().until(elementToBeClickable(By.cssSelector("[id$=confirm-delete-button]")));
        confirmButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/course.xhtml?Id=1", getDriver().getCurrentUrl());
    }

    /**
     * Navigate to the first exercise for a given user.
     *
     * @param username The username.
     */
    private void navigateToFirstExercise(String username) {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Delete");
    }
}
