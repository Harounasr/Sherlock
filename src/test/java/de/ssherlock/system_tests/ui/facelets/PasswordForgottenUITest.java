package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UI Test for {@code passwordForgotten.xhtml}.
 *
 * @author Leon Höfling
 */
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PasswordForgottenUITest extends AbstractSeleniumUITest {

    /**
     * Custom Timeout for waiting until email has been sent.
     */
    private static final int TIMEOUT = 60;

    /**
     * Test for clicking on the back to login button.
     * User should be redirected to the login Facelet.
     */
    @Test
    @Disabled
    void testNavigateToLogin() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/passwordForgotten.xhtml");
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "passwordForgottenForm:backToLogin");
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/public/login.xhtml", getDriver().getCurrentUrl());
    }

    /**
     * Test for clicking the send reset email button with username 'passwordResetUser'.
     */
    @Test
    void testSendResetEmail() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT));
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/passwordForgotten.xhtml");
        getDriver().findElement(By.id("passwordForgottenForm:username")).sendKeys("passwordResetUser");
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "passwordForgottenForm:passwordForgotten");
        SeleniumUITestUtils.checkNotification(wait, new Notification("Reset email sent for passwordResetUser", NotificationType.SUCCESS));
    }

}
