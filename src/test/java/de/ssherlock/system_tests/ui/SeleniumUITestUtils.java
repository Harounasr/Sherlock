package de.ssherlock.system_tests.ui;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.global.transport.SubmissionFile;
import jakarta.faces.application.FacesMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
     * Path to the test data for a valid submission.
     */
    private static final String VALID_SUBMISSION_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/valid_submission";

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
     * @param wait         The web driver.
     * @param notification The expected notification.
     */
    public static void checkNotification(WebDriverWait wait, Notification notification) {
        String typeClassName = notification.type() == NotificationType.ERROR ? ".notification-error" : ".notification-success";
        WebElement element = wait.until(visibilityOfElementLocated((By.cssSelector(".popup-notifications, " + typeClassName + " > td"))));
        assertTrue(element.isDisplayed());
        assertTrue(element.getText().contains(notification.text()));
    }

    /**
     * Checks the current screen for a certain facesMessage.
     *
     * @param wait         The web driver.
     * @param facesMessage The expected faces message.
     */
    public static void checkFacesMessage(WebDriverWait wait, FacesMessage facesMessage) {
        WebElement element = wait.until(visibilityOfElementLocated(By.id("j_idt33:notification")));
        assertTrue(element.isDisplayed());
        assertTrue(element.getText().contains(facesMessage.getDetail()));
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
     * Sends the enter key to an element with the specified id.
     *
     * @param wait The web driver wait.
     * @param id   The css id.
     */
    public static void enterOnElementWithId(WebDriverWait wait, String id) {
        WebElement element = wait.until(elementToBeClickable(By.id(id)));
        element.sendKeys(Keys.RETURN);
    }

    /**
     * Clicks on a menu item in the sidebar based on its label.
     *
     * @param wait  The web driver wait.
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

    public static List<List<String>> getCurrentTableRowsChecker(WebDriver driver) {
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
                    rowContent.add(selectedOption.getAccessibleName());
                } else {
                    rowContent.add(cell.getAccessibleName());
                }
            }
            result.add(rowContent);
        }
        // First element is always empty
        result.remove(0);
        return result;
    }

    /**
     * Inserts a submission with id 1 and exercise id 1 into the embedded database.
     *
     * @throws IOException        When the files cannot be parsed.
     * @throws URISyntaxException When the directory is not specified properly.
     */
    public static void insertSubmissionIntoDatabase() throws IOException, URISyntaxException {
        List<SubmissionFile> javaFiles = getJavaFiles();
        String insertSubmissionSql =
                """
                INSERT INTO submission (id, timestamp_submission, student_username, tutor_username, exercise_id)
                VALUES (1, (TO_TIMESTAMP(1705069256.596195)), 'member', 'tutor', 1);
                """;
        String insertFilesSql =
                """
                INSERT INTO submission_file (submission_id, file_name, file)
                VALUES (1, ?, ?);
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL)) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(insertSubmissionSql)) {
                statement.executeUpdate();
            }
            try (PreparedStatement fileStatement = connection.prepareStatement(insertFilesSql)) {
                for (SubmissionFile file : javaFiles) {
                    fileStatement.setString(1, file.getName());
                    fileStatement.setBytes(2, file.getBytes());
                    fileStatement.addBatch();
                }
                fileStatement.executeBatch();
            }
            connection.commit();
        } catch (BatchUpdateException bue) {
            throw new RuntimeException("Batch update failed: " + bue.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("SQL error: " + e.getMessage());
        }
    }

    /**
     * Inserts a testate with id 1 for submission 1 and tutor id 1.
     */
    public static void insertTestateIntoDatabase() {
        String insertTestateSql =
                """
                INSERT INTO testate (id, submission_id, tutor_id, layout_grade, structure_grade, readability_grade, functionality_grade)
                VALUES (1, 1, 4, ('1'::grade), ('1'::grade), ('1'::grade), ('1'::grade));
                """;
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertTestateSql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the java classes as SubmissionFiles at the given location.
     *
     * @return The java files as SubmissionFiles.
     * @throws IOException        When the files cannot be parsed.
     * @throws URISyntaxException When the directory is not specified properly.
     */
    private static List<SubmissionFile> getJavaFiles() throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL directoryUrl = classLoader.getResource(VALID_SUBMISSION_TEST_DATA);
        assert directoryUrl != null;
        List<SubmissionFile> result = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryUrl.toURI()))) {
            paths.filter(Files::isRegularFile).forEach(filePath -> {
                try {
                    SubmissionFile submissionFile = new SubmissionFile();
                    submissionFile.setName(String.valueOf(filePath.getFileName()));
                    submissionFile.setBytes(Files.readAllBytes(filePath));
                    result.add(submissionFile);
                } catch (IOException ignored) {
                }
            });
        }
        return result;
    }
}
