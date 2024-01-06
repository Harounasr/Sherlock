package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * UI Test for {@code login.xhtml}.
 *
 * @author Victor Vollmann
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginUITest extends AbstractSeleniumUITest {

    @Test
    void testLoginSuccess() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/login.xhtml");
        SeleniumUITestUtils.tryLogin(getDriver(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.ADMIN_PASSWORD);
    }

}
