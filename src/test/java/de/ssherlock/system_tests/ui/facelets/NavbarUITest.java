package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the {@code navbar.xhtml} facelet.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NavbarUITest extends AbstractSeleniumUITest {

    /**
     * Navigation to All Courses.
     */
    @Test
    public void navigateToAllCourses() {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "allCoursesForm:allCoursesButton");
        assertEquals(
                "http://localhost:8080/ssherlock/view/registered/coursePagination.xhtml?all=true", getDriver().getCurrentUrl());
    }

    /**
     * Navigates to my Courses.
     */
    @Test
    public void navigateToMyCourses() {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "myCoursesForm:myCoursesButton");
        assertEquals(
                "http://localhost:8080/ssherlock/view/registered/coursePagination.xhtml?all=false", getDriver().getCurrentUrl());


    }

    /**
     * Navigates to the admin Page.
     */
    @Test
    public void navigateToAdmin() {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "adminForm:adminButton");
        assertEquals("http://localhost:8080/ssherlock/view/admin/admin.xhtml", getDriver().getCurrentUrl());
    }

    /**
     * Navigates to the profile Page.
     */
    @Test
    public void navigateToProfile() {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "profileForm:profileIcon");
        assertEquals("http://localhost:8080/ssherlock/view/registered/profile.xhtml", getDriver().getCurrentUrl());
    }

    /**
     * Navigates to the help Page.
     */
    @Test
    public void navigateToHelp() {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "helpForm:helpIcon");
        assertEquals("http://localhost:8080/ssherlock/view/public/help.xhtml", getDriver().getCurrentUrl());
    }

    /**
     * Logs the user out of the application.
     */
    @Test
    public void logout() {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "logoutForm:logoutIcon");
        assertEquals("http://localhost:8080/ssherlock/view/public/login.xhtml", getDriver().getCurrentUrl());
    }
}
