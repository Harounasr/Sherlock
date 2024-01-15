package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the coursePagination.xhtml facelet.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoursePaginationUITest extends AbstractSeleniumUITest {

    /**
     * List of all Courses on the first page for the admin user.
     */
    private static final List<List<String>> FIRST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList("Algorithm", ""),
            Arrays.asList("English", ""),
            Arrays.asList("German", ""),
            Arrays.asList("Informatik", ""),
            Arrays.asList("Mathematik", "")
    );
    /**
     * List of all Courses on the first page for the member user.
     */
    private static final List<List<String>> MEMBER_FIRST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList("Algorithm", ""),
            Arrays.asList("English", ""),
            Arrays.asList("German", ""),
            Arrays.asList("Informatik", ""),
            Arrays.asList("Mathematik", "")
    );

    /**
     * Empty pagination.
     */
    private static final List<List<String>> EMPTY_PAGINATION = Arrays.asList(
            Arrays.asList("", "")
    );

    /**
     * List of all Courses of the admin user.
     */
    private static final List<List<String>> ADMIN_COURSES = Arrays.asList(
            Arrays.asList("Informatik", "")
    );


    /**
     * Test for the correct content being displayed on load.
     */
    @Test
    void testAllCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the member's courses.
     */
    @Test
    void testMemberMyCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.MEMBER_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/coursePagination.xhtml?faces-redirect=true&all=false");
        assertEquals(MEMBER_FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the admin's courses.
     */
    @Test
    void testAdminMyCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/coursePagination.xhtml?faces-redirect=true&all=false");
        assertEquals(ADMIN_COURSES, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test to join Courses.
     */
    @Test
    void testJoinCourse() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchInput']"));
        searchBar.sendKeys("Informatik");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchButton']"));
        searchButton.click();
        WebElement joinButton = getDriver().findElement(By.cssSelector("[id$='joinCourseButton']"));
        joinButton.click();
        assertEquals("http://localhost:8080/ssherlock/view/registered/course.xhtml?Id=1", getDriver().getCurrentUrl());
    }

    /**
     * Test to search for a nonexistent course.
     */
    @Test
    void testEmptyPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchInput']"));
        searchBar.sendKeys("Stricken");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchButton']"));
        searchButton.click();
        assertEquals(EMPTY_PAGINATION, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }
}
