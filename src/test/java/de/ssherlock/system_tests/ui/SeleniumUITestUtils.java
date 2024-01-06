package de.ssherlock.system_tests.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Utility class for selenium ui tests.
 *
 * @author Victor Vollmann
 */
public class SeleniumUITestUtils {

    /**
     * Username of an administrator in the system.
     */
    public static final String ADMIN_USERNAME = "user";

    /**
     * Password of the administrator of the system.
     */
    public static final String ADMIN_PASSWORD = "lennyistdoof";

    /**
     * This' systems base location.
     */
    public static final String BASE_URL = "http://localhost:8080/software_sherlock_exploded/";

    /**
     * Navigates to the provided location.
     *
     * @param webDriver The web driver.
     * @param location The location.
     */
    public static void navigateTo(WebDriver webDriver, String location) {
        webDriver.get(BASE_URL + location);
    }

    /**
     * Attempts to log in with the given credentials.
     *
     * @param webDriver The web driver.
     * @param username The username.
     * @param password The password.
     */
    public static void tryLogin(WebDriver webDriver, String username, String password) {
        webDriver.findElement(By.id("username")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.id("login")).click();
    }
}
