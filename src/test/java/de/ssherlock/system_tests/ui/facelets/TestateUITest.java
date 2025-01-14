package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.faces.application.FacesMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * UI Test for {@code testate.xhtml}.
 *
 * @author Leon Höfling
 */
@Disabled
@SuppressWarnings({"PMD.UseUnderscoresInNumericLiterals", "checkstyle:MagicNumber"})
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

    /**
     * Test for clicking the 'Download code' button and check if file was downloaded.
     */
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    @SuppressWarnings("checkstyle:MagicNumber, PMD.UseUnderscoresInNumericLiterals")
    @Test
    public void testDownloadCode() throws InterruptedException {
        SeleniumUITestUtils.clickOnElementWithId(getWait(), "downloadForm:downloadCode");
        Thread.sleep(15000);
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();
        boolean found = false;
        File f = null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println("File " + listOfFile.getName());
                if (fileName.matches("CodeFiles.zip")) {
                    f = new File(fileName);
                    found = true;
                }
            }
        }
        Assertions.assertTrue(found);
        f.deleteOnExit();
    }
}
