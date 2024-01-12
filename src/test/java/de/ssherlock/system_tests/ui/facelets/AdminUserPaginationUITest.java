package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
 * Test class for the {@code adminUserPagination.xhtml} facelet.
 *
 * @author Victor Vollmann
 */
@Disabled
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminUserPaginationUITest extends AbstractSeleniumUITest {

    /**
     * The elements visible on the first page of the pagination.
     */
    private static final List<List<String>> FIRST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList("admin", "User", "One", "admin", "Mathematik", "ADMINISTRATOR"),
            Arrays.asList("member", "User", "Three", "member", "Mathematik", "REGISTERED"),
            Arrays.asList("tutor", "User", "Four", "tutor", "Mathematik", "REGISTERED"),
            Arrays.asList("member1", "User", "Five", "member1", "Mathematik", "REGISTERED"),
            Arrays.asList("member2", "User", "Six", "member2", "Mathematik", "REGISTERED"),
            Arrays.asList("member3", "User", "Seven", "member3", "Mathematik", "REGISTERED"),
            Arrays.asList("member4", "User", "Eight", "member4", "Mathematik", "REGISTERED"),
            Arrays.asList("member5", "User", "Nine", "member5", "Mathematik", "REGISTERED"),
            Arrays.asList("member6", "User", "Ten", "member6", "Mathematik", "REGISTERED"),
            Arrays.asList("member7", "User", "Eleven", "member7", "Mathematik", "REGISTERED")
    );

    /**
     * Navigates to the target facelet.
     */
    @BeforeEach
    public void navigateToAdminUserPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/admin/admin.xhtml");
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
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchInput']"));
        searchBar.sendKeys("admin");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchButton']"));
        searchButton.click();
        List<List<String>> expectedTableData = Arrays.asList(Arrays.asList("admin", "User", "One", "admin", "Mathematik", "ADMINISTRATOR"));
        assertEquals(expectedTableData, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    @Test
    @Order(3)
    void testSelectUser() {
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchInput']"));
        searchBar.sendKeys("teacher");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchButton']"));
        searchButton.click();
        WebElement userLink = getDriver().findElement(By.linkText("teacher"));
        userLink.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/profile.xhtml?Id=2", getDriver().getCurrentUrl());
    }

    /**
     * Test for changing a users system role. Matches it in the database.
     *
     * @throws SQLException When the sql query fails.
     */
    @Test
    @Order(4)
    void testChangeRoles() throws SQLException, InterruptedException {
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchInput']"));
        searchBar.sendKeys("teacher");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchButton']"));
        searchButton.click();
        WebElement selectElement = getDriver().findElement(By.cssSelector("[id$='selectRole']"));
        Select dropdown = new Select(selectElement);
        dropdown.selectByValue("REGISTERED");
        // until the database is updated
        Thread.sleep(1000);
        String query = """
                       SELECT user_role
                       FROM "user"
                       WHERE id = 2;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SystemRole systemRole = SystemRole.valueOf(resultSet.getString("user_role"));
                assertEquals(SystemRole.REGISTERED, systemRole);
            } else {
                fail("User could not be found.");
            }
        }
    }

    @Test
    @Order(5)
    void testChangeFaculty() throws SQLException, InterruptedException {
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchInput']"));
        searchBar.sendKeys("teacher");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id^='paginationSearch:'][id$=':searchBar_searchButton']"));
        searchButton.click();
        WebElement selectElement = getDriver().findElement(By.cssSelector("[id$='selectFaculty']"));
        Select dropdown = new Select(selectElement);
        dropdown.selectByValue("Informatik");
        // until the database is updated
        Thread.sleep(1000);
        String query = """
                       SELECT faculty
                       FROM "user"
                       WHERE id = 2;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("Informatik", resultSet.getString("faculty"));
            } else {
                fail("User could not be found.");
            }
        }
    }

}
