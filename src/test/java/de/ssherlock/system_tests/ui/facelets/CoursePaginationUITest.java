package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Test class for the coursePagination.xhtml facelet.
 */
@SuppressWarnings("checkstyle:MagicNumber")
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
    @Order(2)
    void testAllCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the admin's courses.
     */
    @Test
    @Order(4)
    void testAdminMyCoursesContent() throws InterruptedException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/coursePagination.xhtml?faces-redirect=true&all=false");
        Thread.sleep(1000);
        assertEquals(ADMIN_COURSES, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test to join Courses.
     */
    @Test
    @Order(3)
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
    @Order(5)
    void testEmptyPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchInput']"));
        searchBar.sendKeys("Stricken");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchButton']"));
        searchButton.click();
        assertEquals(EMPTY_PAGINATION, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Tests adding a course.
     */
    @Test
    @Order(6)
    void testAddCourseSuccess() {
        createCourse();
        Notification notification = new Notification("Success! The course New Course Name was created.", NotificationType.SUCCESS);
        SeleniumUITestUtils.checkNotification(getWait(), notification);
    }

    /**
     * Tests adding a course with an existing name.
     */
    @Test
    @Order(7)
    void testAddCourseFailure() {
        createCourse();
        Notification notification = new Notification("Course name already exists.", NotificationType.ERROR);
        SeleniumUITestUtils.checkNotification(getWait(), notification);
    }

    /**
     * Creates a course with the name "New Course Name".
     *
     * @author Victor Vollmann
     */
    private void createCourse() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        WebElement openModalButton = getWait().until(elementToBeClickable(By.cssSelector("[id$='open-course-modal']")));
        openModalButton.click();
        WebElement newCourseName = getWait().until(visibilityOfElementLocated(By.cssSelector("[id$='newCourseName']")));
        newCourseName.sendKeys("New Course Name");
        WebElement createCourseButton = getWait().until(elementToBeClickable(By.cssSelector("[id$='create-course-button']")));
        createCourseButton.click();
    }
}
