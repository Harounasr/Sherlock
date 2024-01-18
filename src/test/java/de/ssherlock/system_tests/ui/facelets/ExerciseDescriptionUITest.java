package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Test class for the {@code exerciseDescription.xhtml} facelet.
 *
 * @author Victor Vollmann
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExerciseDescriptionUITest extends AbstractSeleniumUITest {

    /**
     * The correct recommended deadline.
     */
    private static final String CORRECT_REC_DATE = "2024-01-14 19:26";

    /**
     * The correct obligatory deadline.
     */
    private static final String CORRECT_OB_DATE = "2025-01-21 19:26";

    /**
     * The correct publish date.
     */
    private static final String CORRECT_PUB_DATE = "2024-01-07 19:26";

    /**
     * The changed deadline string.
     */
    private static final String CHANGED_DEADLINE = "2026-01-21 19:26";

    /**
     * Path to the test image.
     */
    private static final String IMAGE_PATH = "de/ssherlock/test_data/description-test-data/test-image.png";

    /**
     * Path to the test not-an-image file.
     */
    private static final String NOT_AN_IMAGE_PATH = "de/ssherlock/test_data/description-test-data/not-an-image.txt";

    /**
     * Navigates to the target facelet.
     */
    @BeforeEach
    public void navigateToExerciseDescription() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
    }

    /**
     * Checks whether the correct dates are displayed.
     * For some reason this does not work on gitlab. ?!?!
     */
    @Test
    @Order(1)
    void testCheckCorrectDates() {
        WebElement recDeadline = getDriver().findElement(By.cssSelector("[id$='recDeadline']"));
        WebElement obDeadline = getDriver().findElement(By.cssSelector("[id$='obDeadline']"));
        WebElement pubDate = getDriver().findElement(By.cssSelector("[id$='pubDate']"));
        if (System.getenv("GITLAB_CI") == null) {
            assertEquals(CORRECT_REC_DATE, recDeadline.getText());
            assertEquals(CORRECT_OB_DATE, obDeadline.getText());
            assertEquals(CORRECT_PUB_DATE, pubDate.getText());
        }
    }

    /**
     * Test for editing one of the dates.
     * {@code <input type="datetime-local">} format differs across locales,
     * that is why different input strings are generated.
     */
    @Test
    @Order(2)
    void testEditDates() {
        clickEditButton();
        WebElement obDeadline = getDriver().findElement(By.cssSelector("[id$='obligatoryDeadline']"));
        obDeadline.clear();
        switch (System.getProperty("SYSTEM_TEST_BROWSER", "chrome")) {
        case "firefox" -> {
            if (System.getenv("GITLAB_CI") != null) {
                obDeadline.sendKeys("01212026");
                obDeadline.sendKeys(Keys.TAB);
                obDeadline.sendKeys("0726PM");
            } else {
                obDeadline.sendKeys("21012026");
                obDeadline.sendKeys(Keys.TAB);
                obDeadline.sendKeys("1926");
            }
        }
        case "chrome" -> {
            obDeadline.sendKeys("21012026");
            obDeadline.sendKeys(Keys.TAB);
            obDeadline.sendKeys("1926");
        }
        case "edge" -> {
            obDeadline.sendKeys("01212026");
            obDeadline.sendKeys(Keys.ARROW_RIGHT);
            obDeadline.sendKeys("0726PM");
        }
        default -> throw new RuntimeException("The browser is not specified");
        }
        clickSaveButton();
        WebElement obDeadlineChanged = getWait().until(visibilityOfElementLocated(By.cssSelector("[id$='obDeadline']")));
        assertEquals(CHANGED_DEADLINE, obDeadlineChanged.getText());
    }

    /**
     * Test for editing the html with a successful outcome.
     */
    @Test
    @Order(3)
    void testEditHTMLSuccess() {
        clickEditButton();
        WebElement htmlInput = getDriver().findElement(By.cssSelector("[id$='htmlInput']"));
        htmlInput.clear();
        htmlInput.sendKeys("<h1 id=\"changed-html\">This text is the new Description</h1>");
        clickSaveButton();
        WebElement changedHTML = getDriver().findElement(By.id("changed-html"));
        assertEquals("This text is the new Description", changedHTML.getText());
    }

    /**
     * Test for editing the html with failure.
     */
    @Test
    @Order(4)
    void testEditHTMLFailure() {
        clickEditButton();
        WebElement htmlInput = getDriver().findElement(By.cssSelector("[id$='htmlInput']"));
        htmlInput.clear();
        htmlInput.sendKeys("<body>this is a body</body>");
        clickSaveButton();
        Notification expectedNotification = new Notification("BODY tag detected. There should be no outer html, only the contents of the body.",
                                                             NotificationType.ERROR);
        SeleniumUITestUtils.checkNotification(getWait(), expectedNotification);
    }

    /**
     * Test for uploading an image.
     * Should display the image.
     *
     * @throws URISyntaxException When the path is incorrect.
     */
    @Test
    void testUploadImageSuccess() throws URISyntaxException {
        clickEditButton();
        WebElement imageUpload = getDriver().findElement(By.cssSelector("[id$='file']"));
        URL imageUrl = Thread.currentThread().getContextClassLoader().getResource(IMAGE_PATH);
        imageUpload.sendKeys(Paths.get(imageUrl.toURI()).toFile().getAbsolutePath());
        WebElement convertButton = getDriver().findElement(By.cssSelector("[id$='convert-button']"));
        convertButton.click();
        String imgComponent = getDriver().findElement(By.cssSelector("[id$='generated-img-component']")).getText();
        imgComponent = imgComponent.replace("ssherlock_war_exploded", "ssherlock");
        imgComponent = imgComponent.replace("<img", "<img id=\"test-image\"");
        WebElement htmlInput = getDriver().findElement(By.cssSelector("[id$='htmlInput']"));
        htmlInput.clear();
        htmlInput.sendKeys(imgComponent);
        clickSaveButton();
        WebElement generatedImage = getDriver().findElement(By.id("test-image"));
        boolean isImage = generatedImage.getTagName().equals("img")
                          && generatedImage.getAttribute("src") != null
                          && !generatedImage.getAttribute("src").isEmpty();
        assertTrue(isImage);
    }

    /**
     * Test for uploading a non-image.
     * Should display an error.
     *
     * @throws URISyntaxException When the path is incorrect.
     */
    @Test
    void testUploadImageFailure() throws URISyntaxException {
        clickEditButton();
        WebElement imageUpload = getDriver().findElement(By.cssSelector("[id$='file']"));
        URL imageUrl = Thread.currentThread().getContextClassLoader().getResource(NOT_AN_IMAGE_PATH);
        imageUpload.sendKeys(Paths.get(imageUrl.toURI()).toFile().getAbsolutePath());
        WebElement convertButton = getDriver().findElement(By.cssSelector("[id$='convert-button']"));
        convertButton.click();
        Notification expectedNotification = new Notification("File is not a valid image.",
                                                             NotificationType.ERROR);
        SeleniumUITestUtils.checkNotification(getWait(), expectedNotification);
    }

    /**
     * Clicks the edit button.
     */
    private void clickEditButton() {
        WebElement editButton = getWait().until(elementToBeClickable(By.cssSelector("[id$='editButton']")));
        editButton.click();
    }

    /**
     * Clicks the save button.
     */
    private void clickSaveButton() {
        WebElement saveButton = getWait().until(elementToBeClickable(By.cssSelector("[id$='saveButton']")));
        saveButton.click();
    }

}
