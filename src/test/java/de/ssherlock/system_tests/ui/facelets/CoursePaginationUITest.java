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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoursePaginationUITest extends AbstractSeleniumUITest {

    private static final List<List<String>> first_page_Elements= Arrays.asList(
            Arrays.asList("Algorithm", ""),
            Arrays.asList("English", ""),
            Arrays.asList("German", ""),
            Arrays.asList("Informatik",""),
            Arrays.asList("Mathematik","")
    );

    private static final List<List<String>> member_first_page_Elements= Arrays.asList(
            Arrays.asList("Algorithm", ""),
            Arrays.asList("English", ""),
            Arrays.asList("German", ""),
            Arrays.asList("Informatik",""),
            Arrays.asList("Mathematik","")
    );

    private static final List<List<String>> emptyPagination = Arrays.asList(
            Arrays.asList("","")
    );

    private static final List<List<String>> adminCourses = Arrays.asList(
            Arrays.asList("Informatik", "")
    );



    /**
     * Test for the correct content being displayed on load.
     */
    @Test
    void testAllCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        assertEquals(first_page_Elements, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    @Test
    void testMemberMyCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.MEMBER_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(),"/view/registered/coursePagination.xhtml?faces-redirect=true&all=false");
        assertEquals(member_first_page_Elements, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

    @Test
    void testAdminMyCoursesContent() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(),"/view/registered/coursePagination.xhtml?faces-redirect=true&all=false");
        assertEquals(adminCourses, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }

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

    @Test
    void testEmptyPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        WebElement searchBar = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchInput']"));
        searchBar.sendKeys("Stricken");
        WebElement searchButton = getDriver().findElement(By.cssSelector("[id$=':searchBar_searchButton']"));
        searchButton.click();
        assertEquals(emptyPagination, SeleniumUITestUtils.getCurrentTableRows(getDriver()));
    }
}
