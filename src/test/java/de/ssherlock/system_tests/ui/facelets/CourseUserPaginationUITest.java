package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@code courseUserPagination.xhtml} facelet.
 * Also tests the elements of the {@code pagination.xhtml} and {@code paginationSearch.xhtml}
 * composite components.
 *
 * @author Victor Vollmann
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseUserPaginationUITest extends AbstractSeleniumUITest {

    /**
     * The elements visible on the first page of the pagination.
     */
    private static final List<List<String>> FIRST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList("admin", "User", "One", "admin", "NONE"),
            Arrays.asList("member", "User", "Three", "member", "MEMBER"),
            Arrays.asList("member1", "User", "Five", "member1", "MEMBER"),
            Arrays.asList("member2", "User", "Six", "member2", "MEMBER"),
            Arrays.asList("member3", "User", "Seven", "member3", "MEMBER")
    );

    /**
     * The elements visible on the second page of the pagination.
     */
    private static final List<List<String>> SECOND_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList("member4", "User", "Eight", "member4", "MEMBER"),
            Arrays.asList("member5", "User", "Nine", "member5", "MEMBER"),
            Arrays.asList("member6", "User", "Ten", "member6", "MEMBER"),
            Arrays.asList("member7", "User", "Eleven", "member7", "MEMBER"),
            Arrays.asList("teacher", "User", "Two", "teacher", "TEACHER")
    );

    /**
     * The elements visible on the last page of the pagination.
     */
    private static final List<List<String>> LAST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList("victor", "UserFor", "SendNotificationTest", "sep23g05@outlook.com", "NONE")
    );

    /**
     * Navigates to the course user pagination facelet.
     */
    @BeforeEach
    public void navigateToCourseUserPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Users");
    }

    /**
     * Test for the correct content being displayed on load.
     */
    @Test
    @Order(1)
    void testCheckCorrectContent() {
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the search mechanism, checks whether the correct elements are being displayed upon search.
     */
    @Test
    @Order(2)
    void testCheckSearch() {
        SeleniumUITestUtils.searchFor(getDriver(), "admin");
        List<List<String>> expectedTableData = Arrays.asList(Arrays.asList("admin", "User", "One", "admin", "NONE"));
        assertEquals(expectedTableData, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for changing a users role in the course. Matches it in the database.
     *
     * @throws SQLException When the sql query fails.
     */
    @Test
    @Order(7)
    void testChangeRoles() throws SQLException {
        WebElement selectElement = getDriver().findElement(By.cssSelector("td select"));
        Select dropdown = new Select(selectElement);
        dropdown.selectByValue("TEACHER");
        String query = """
                       SELECT user_role
                       FROM participates
                       WHERE user_id = 1 AND course_id = 1;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CourseRole courseRole = CourseRole.valueOf(resultSet.getString("user_role"));
                assertEquals(CourseRole.TEACHER, courseRole);
            } else {
                fail("User does not participate.");
            }
        }
    }

    /**
     * Test for the pagination's next button.
     */
    @Test
    @Order(3)
    void testNextButton() {
        WebElement nextPage = getDriver().findElement(By.cssSelector("[id$=':pagination_nextButton']"));
        nextPage.click();
        assertEquals(SECOND_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the pagination's last button.
     */
    @Test
    @Order(4)
    void testLastButton() {
        WebElement lastPage = getDriver().findElement(By.cssSelector("[id$=':pagination_lastButton']"));
        lastPage.click();
        assertEquals(LAST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the pagination's prev button.
     */
    @Test
    @Order(5)
    void testPrevButton() {
        WebElement nextPage = getDriver().findElement(By.cssSelector("[id$=':pagination_nextButton']"));
        nextPage.click();
        WebElement prevPage = getDriver().findElement(By.cssSelector("[id$=':pagination_prevButton']"));
        prevPage.click();
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    /**
     * Test for the pagination's first button.
     */
    @Test
    @Order(6)
    void testFirstButton() {
        WebElement nextPage = getDriver().findElement(By.cssSelector("[id$=':pagination_nextButton']"));
        nextPage.click();
        WebElement firstPage = getDriver().findElement(By.cssSelector("[id$=':pagination_firstButton']"));
        firstPage.click();
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }
}
