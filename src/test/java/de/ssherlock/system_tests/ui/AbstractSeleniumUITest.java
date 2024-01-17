package de.ssherlock.system_tests.ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.stream.Collectors;

/**
 * Abstract class defining setup and teardown methods for Selenium UI Tests.
 *
 * @author Victor Vollmann
 */
public abstract class AbstractSeleniumUITest {

    /**
     * Location of the clear database script.
     */
    private static final String CLEAR_TABLES_LOCATION = "de/ssherlock/clear_tables.sql";

    /**
     * Location of the test data script.
     */
    private static final String TEST_DATA_LOCATION = "de/ssherlock/testData.sql";

    /**
     * The current WebDriver.
     */
    private static WebDriver driver;

    /**
     * The current WebDriverWait.
     */
    private static WebDriverWait wait;

    /**
     * The timeout for elements to be found.
     */
    private static final int TIMEOUT = 10;

    /**
     * Screen height.
     */
    private static final int SCREEN_WIDTH = 1920;

    /**
     * Screen width.
     */
    private static final int SCREEN_HEIGHT = 1080;

    /**
     * Sets up the web driver and wait and the embedded database.
     */
    @BeforeAll
    public static void setUp() {
        String browser = System.getProperty("SYSTEM_TEST_BROWSER", "chrome");
        switch (browser) {
        case "chrome" -> {
            ChromeOptions options = new ChromeOptions();
            if (System.getenv("GITLAB_CI") != null || System.getenv("JENKINS_NODE_COOKIE") != null) {
                options.addArguments("--headless");
            }
            driver = new ChromeDriver(options);
        }
        case "edge" -> {
            EdgeOptions options = new EdgeOptions();
            if (System.getenv("GITLAB_CI") != null || System.getenv("JENKINS_NODE_COOKIE") != null) {
                options.addArguments("--headless");
            }
            driver = new EdgeDriver(options);
        }
        case "firefox" -> {
            FirefoxOptions options = new FirefoxOptions();
            if (System.getenv("GITLAB_CI") != null || System.getenv("JENKINS_NODE_COOKIE") != null) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        }
        default -> throw new RuntimeException("The driver is not specified");
        }
        driver.manage().window().setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    /**
     * Tears down the web driver.
     *
     * @throws SQLException When the database cannot be started.
     * @throws IOException When the scripts cannot be found.
     */
    @AfterAll
    public static void tearDown() throws SQLException, IOException {
        if (driver != null) {
            driver.quit();
        }
        resetDatabase();
    }

    /**
     * Gets driver.
     *
     * @return the driver
     */
    @SuppressFBWarnings("MS_EXPOSE_REP")
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Gets wait.
     *
     * @return the wait
     */
    public static WebDriverWait getWait() {
        return wait;
    }

    /**
     * Resets the database.
     *
     * @throws SQLException When the database cannot be started.
     * @throws IOException When the scripts cannot be found.
     */
    private static void resetDatabase() throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(SeleniumUITestUtils.DATABASE_URL);
             Statement clearDB = connection.createStatement();
             Statement fillDB = connection.createStatement()) {
            InputStream clearInput = Thread.currentThread().getContextClassLoader().getResourceAsStream(CLEAR_TABLES_LOCATION);
            InputStream fillInput = Thread.currentThread().getContextClassLoader().getResourceAsStream(TEST_DATA_LOCATION);
            if (clearInput == null || fillInput == null) {
                throw new IllegalArgumentException("SQL script file not found.");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clearInput, StandardCharsets.UTF_8))) {
                String sql = reader.lines().collect(Collectors.joining("\n"));
                clearDB.execute(sql);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(fillInput, StandardCharsets.UTF_8))) {
                String sql = reader.lines().collect(Collectors.joining("\n"));
                fillDB.execute(sql);
            }
        }
    }

}
