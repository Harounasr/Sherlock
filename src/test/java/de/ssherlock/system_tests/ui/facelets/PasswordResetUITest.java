package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UI Test for {@code passwordReset.xhtml}.
 *
 * @author Leon Höfling
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PasswordResetUITest extends AbstractSeleniumUITest {

    /**
     * Test for clicking on the back to login button.
     * User should be redirected to the login Facelet.
     */
    @Test
    void testNavigateToLogin() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/passwordReset.xhtml?token=someToken");
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "passwordResetForm:backToLogin");
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/public/login.xhtml", getDriver().getCurrentUrl());
    }

    /**
     * Test for resetting the password.
     */
    @Test
    void testResetPassword() throws InterruptedException {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/passwordReset.xhtml?token=12345");
        getDriver().findElement(By.id("passwordResetForm:passwordOne")).sendKeys("N3wPa22w-rd!");
        getDriver().findElement(By.id("passwordResetForm:passwordTwo")).sendKeys("N3wPa22w-rd!");
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "passwordResetForm:passwordForgotten");
        Thread.sleep(3000);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/login.xhtml");
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), "passwordReset", "N3wPa22w-rd!");
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/coursePagination.xhtml?all=true", getDriver().getCurrentUrl());
    }


}
