package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerType;
import de.ssherlock.global.transport.SystemRole;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckerListUITest extends AbstractSeleniumUITest {

    private final List<List<String>> FIRST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList( "true", "true", "", "", "IDENTITY","Delete"),
            Arrays.asList("true", "true","","", "COMPILATION","Delete"),
            Arrays.asList("true", "true","one","two", "USER_DEFINED","Delete")
    );

    private final List<List<String>> FIRST_PAGE_ELEMENTS_DELETE = Arrays.asList(
            Arrays.asList("true", "true","","", "COMPILATION","Delete"),
            Arrays.asList("true", "true","one","two", "USER_DEFINED","Delete")
    );

    private final List<List<String>> FIRST_PAGE_ELEMENTS_ADD = Arrays.asList(
            Arrays.asList("true", "true","","", "COMPILATION","Delete"),
            Arrays.asList("true", "true","one","two", "USER_DEFINED","Delete"),
            Arrays.asList("false", "false","one","three", "USER_DEFINED","Delete")
    );


    private static final List<List<String>> EMPTY_PAGINATION = Arrays.asList(
            Arrays.asList("", "")
    );

    @Test
    @Order(1)
    public void testCheckContentPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(),"Checkers");
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }

    @Test
    @Order(3)
    public void testDeleteChecker() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(),"Checkers");
        WebElement element = getDriver().findElement(By.cssSelector("[id$='deleteButton']"));
            getWait().until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        assertEquals(FIRST_PAGE_ELEMENTS_DELETE,SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }
    @Test
    @Order(2)
    void testChangeType() throws SQLException, InterruptedException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(),"Checkers");
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

    @Test
    @Order(4)
    public void testAddChecker() throws InterruptedException {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(),"Checkers");
        SeleniumUITestUtils.clickOnElementWithId(getWait(),"addCheckerButton");
        Thread.sleep(1000);
        WebElement Input = getDriver().findElement(By.cssSelector("[id$='inputParamOne']"));
        Input.sendKeys("one");
        WebElement Output = getDriver().findElement(By.cssSelector("[id$='inputParamTwo']"));
        Output.sendKeys("three");
      //  SeleniumUITestUtils.clickOnElementWithId(getWait(),"submitNewChecker");
        WebElement submit = getDriver().findElement(By.cssSelector("[id$='submitNewChecker']"));
        submit.click();
        Thread.sleep(2000);
        assertEquals(FIRST_PAGE_ELEMENTS_ADD, SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }
}
