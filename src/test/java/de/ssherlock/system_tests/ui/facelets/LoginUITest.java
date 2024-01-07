package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UI Test for {@code login.xhtml}.
 *
 * @author Victor Vollmann
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginUITest extends AbstractSeleniumUITest {

    /**
     * Test for logging in with correct credentials.
     */
    @Test
    void testLoginSuccess() {
        SeleniumUITestUtils.tryLogin(getDriver(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.ADMIN_PASSWORD);
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/coursePagination.xhtml?all=true", getDriver().getCurrentUrl());
    }

    /**
     * Test for logging in with incorrect credentials.
     */
    @Test
    void testLoginFailed() {
        SeleniumUITestUtils.tryLogin(getDriver(), SeleniumUITestUtils.ADMIN_USERNAME, "wrongPassword");
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/public/login.xhtml", getDriver().getCurrentUrl());
        Notification expectedNotification = new Notification("Login Failed, Username and password do not match.", NotificationType.ERROR);
        SeleniumUITestUtils.checkNotification(getDriver(), expectedNotification);
    }

}
