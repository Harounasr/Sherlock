package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import jakarta.faces.application.FacesMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * UI Test for {@code testate.xhtml}.
 *
 * @author Leon Höfling
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestateUITest extends AbstractSeleniumUITest {

    /**
     * Navigates to the testate facelet.
     */
    @BeforeEach
    public void navigateToTestate() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.TUTOR_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Submissions");
        WebElement testateButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='Create Testate']")));
        testateButton.click();
    }

    /**
     * Test for checking the validator messages when not selecting a grade.
     */
    @Test
    public void testGradeValidators() {
        SeleniumUITestUtils.clickOnElementWithId(getWait(), "testateForm:submitTestate");
        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Please select a grade.", null));
    }

    /**
     * Test for checking the validator message when exceeding the 500-character limit in the comment box.
     */
    @Test
    public void testCommentValidators() {
        getDriver().findElement(By.id("testateForm:commentInput")).sendKeys("ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong"
                                                                            + "ThisIsWayToLongThisIsWayToLongThisIsWayToLongThisIsWayToLong");
        SeleniumUITestUtils.clickOnElementWithId(getWait(), "testateForm:submitTestate");
        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Comment may not be longer than 500 characters.", null));
    }

    /**
     * Test for clicking the 'Submit Testate' button.
     */
    @Test
    public void testSubmitTestate() throws InterruptedException {
        getDriver().findElement(By.id("testateForm:functionalityGrade")).sendKeys("1");
        getDriver().findElement(By.id("testateForm:readabilityGrade")).sendKeys("2");
        getDriver().findElement(By.id("testateForm:layoutGrade")).sendKeys("3");
        getDriver().findElement(By.id("testateForm:structureGrade")).sendKeys("4");
        getDriver().findElement(By.id("testateForm:commentInput")).sendKeys("This is a comment.");
        SeleniumUITestUtils.clickOnElementWithId(getWait(), "testateForm:submitTestate");
    }
}
