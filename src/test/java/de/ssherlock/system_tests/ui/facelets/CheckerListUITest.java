package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.CheckerType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the Checkerlist.xhtml facelet.
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckerListUITest extends AbstractSeleniumUITest {

    /**
     * the expected elements on the first page.
     */
    private final List<List<String>> firstPageElements = Arrays.asList(
            Arrays.asList("true", "true", "", "", "IDENTITY", "Delete"),
            Arrays.asList("true", "true", "", "", "COMPILATION", "Delete"),
            Arrays.asList("true", "true", "one", "two", "USER_DEFINED", "Delete")
    );

    /**
     * The expected elements on the first page, after a checker was deleted.
     */
    private final List<List<String>> firstPageElementsDelete = Arrays.asList(
            Arrays.asList("true", "true", "", "", "COMPILATION", "Delete"),
            Arrays.asList("true", "true", "one", "two", "USER_DEFINED", "Delete")
    );

    /**
     * The expected elements on the first page, after a new checker was added.
     */
    private final List<List<String>> firstPageElementsAdd = Arrays.asList(
            Arrays.asList("false", "false", "one", "three", "USER_DEFINED", "Delete"),
            Arrays.asList("true", "true", "", "", "COMPILATION", "Delete"),
            Arrays.asList("true", "true", "one", "two", "USER_DEFINED", "Delete")
    );

    /**
     * Tests for the correct content.
     */
    @Test
    @Order(1)
    public void testCheckContentPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Checkers");
        assertEquals(firstPageElements, SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }

    /**
     * Tests if a checker was deleted.
     */
    @Test
    @Order(3)
    public void testDeleteChecker() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Checkers");
        WebElement element = getDriver().findElement(By.cssSelector("[id$='deleteButton']"));
        getWait().until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        assertEquals(firstPageElementsDelete, SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }

    /**
     * Tests if the type of a checker can be changed.
     *
     * @throws SQLException         thrown if the checker cannot be found in the database.
     * @throws InterruptedException thrown by thread.sleep.
     */
    @Test
    @Order(2)
    void testChangeType() throws SQLException, InterruptedException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Checkers");
        WebElement selectElement = getDriver().findElement(By.cssSelector("[id$='typeDropDown']"));
        Select dropdown = new Select(selectElement);
        dropdown.selectByValue("USER_DEFINED");
        // until the database is updated
        Thread.sleep(1000);
        String query = """
                       SELECT type
                       FROM checker
                       WHERE id = 3;
                       """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CheckerType checkerType = CheckerType.valueOf(resultSet.getString("type"));
                assertEquals(CheckerType.IDENTITY, checkerType);
            } else {
                fail("Checker could not be found.");
            }
        }
    }

    /**
     * Tests if a new checker can be added.
     *
     * @throws InterruptedException thrown by thread.sleep.
     */
    @Test
    @Order(4)
    public void testAddChecker() throws InterruptedException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Checkers");
        SeleniumUITestUtils.clickOnElementWithId(getWait(), "addCheckerButton");
        WebElement input = getDriver().findElement(By.cssSelector("[id$='inputParamOne']"));
        input.sendKeys("one");
        WebElement output = getDriver().findElement(By.cssSelector("[id$='inputParamTwo']"));
        output.sendKeys("three");
        WebElement submit = getDriver().findElement(By.cssSelector("[id$='submitNewChecker']"));
        submit.click();
        assertEquals(firstPageElementsAdd, SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }
}
