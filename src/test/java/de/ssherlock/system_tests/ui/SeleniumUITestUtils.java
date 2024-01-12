package de.ssherlock.system_tests.ui;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Utility class for selenium ui tests.
 *
 * @author Victor Vollmann
 */
public final class SeleniumUITestUtils {

    /**
     * Username of an administrator in the system.
     */
    public static final String ADMIN_USERNAME = "admin";

    /**
     * Username of a course member in the system.
     */
    public static final String MEMBER_USERNAME = "member";

    /**
     * Username of a course tutor in the system.
     */
    public static final String TUTOR_USERNAME = "tutor";

    /**
     * Username of a course member in the system.
     */
    public static final String TEACHER_USERNAME = "teacher";

    /**
     * Password of the administrator of the system.
     */
    public static final String GLOBAL_PASSWORD = "lennyistdoof";

    /**
     * This systems' base location.
     * If running a single test and manually starting tomcat you may want
     * to change this to /ssherlock_war_exploded/.
     */
    public static final String BASE_URL = "http://localhost:8080/ssherlock/";

    /**
     * The database Url.
     */
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/ssherlock-test-db?user=postgres";

    /**
     * Private constructor for utility class.
     */
    private SeleniumUITestUtils() {

    }

    /**
     * Navigates to the provided location.
     *
     * @param webDriver The web driver.
     * @param location  The location.
     */
    public static void navigateTo(WebDriver webDriver, String location) {
        webDriver.get(BASE_URL + location);
    }

    /**
     * Attempts to log in with the given credentials.
     *
     * @param webDriver The web driver.
     * @param wait      The web driver wait.
     * @param username  The username.
     * @param password  The password.
     */
    public static void tryLogin(WebDriver webDriver, WebDriverWait wait, String username, String password) {
        navigateTo(webDriver, "view/public/login.xhtml");
        webDriver.findElement(By.id("loginForm:username")).sendKeys(username);
        webDriver.findElement(By.id("loginForm:password")).sendKeys(password);
        clickOnElementWithId(wait, "loginForm:login");
    }

    /**
     * Checks the current screen for a certain Notification.
     *
     * @param wait    The web driver.
     * @param notification The expected notification.
     */
    public static void checkNotification(WebDriverWait wait, Notification notification) {
        String typeClassName = notification.type() == NotificationType.ERROR ? ".notification-error" : ".notification-success";
        WebElement element = wait.until(visibilityOfElementLocated((By.cssSelector(".popup-notifications, " + typeClassName + " > td"))));
        assertTrue(element.isDisplayed());
        assertTrue(element.getText().contains(notification.text()));
    }

    /**
     * Clicks on the element with the specified id.
     *
     * @param wait The web driver wait.
     * @param id   The css id.
     */
    public static void clickOnElementWithId(WebDriverWait wait, String id) {
        WebElement element = wait.until(elementToBeClickable(By.id(id)));
        element.click();
    }

    /**
     * Clicks on a menu item in the sidebar based on its label.
     *
     * @param wait The web driver wait.
     * @param label The label.
     */
    public static void clickOnSidebarItem(WebDriverWait wait, String label) {
        WebElement element = wait.until(elementToBeClickable(
                By.cssSelector(String.format("li.nav-item-sidebar input[value=\"%s\"]", label))));
        element.click();
    }

    /**
     * Gets the current table rows of a pagination (without headers).
     *
     * @param driver The web driver.
     * @return The table rows.
     */
    public static List<List<String>> getCurrentTableRows(WebDriver driver) {
        List<List<String>> result = new ArrayList<>();
        WebElement table = driver.findElement(By.className("table-bordered"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            List<String> rowContent = new ArrayList<>();
            for (WebElement cell : cells) {
                List<WebElement> selectElements = cell.findElements(By.tagName("select"));
                if (!selectElements.isEmpty()) {
                    WebElement selectedOption = selectElements.get(0).findElement(By.cssSelector("option:checked"));
                    rowContent.add(selectedOption.getText());
                } else {
                    rowContent.add(cell.getText());
                }
            }
            result.add(rowContent);
        }
        // First element is always empty
        result.remove(0);
        return result;
    }
}
