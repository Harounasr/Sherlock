package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.datatype.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckerListUITest extends AbstractSeleniumUITest {

    private final List<List<String>> FIRST_PAGE_ELEMENTS = Arrays.asList(
            Arrays.asList( "true", "true", "", "", "IDENTITY","Delete"),
            Arrays.asList("true", "true","","", "COMPILATION","Delete"),
            Arrays.asList("true", "true","one","two", "USER_DEFINED","Delete")
    );

    @Test
    public void testCheckContentPagination() {
        SeleniumUITestUtils.tryLogin(
                getDriver(), getWait(), SeleniumUITestUtils.ADMIN_USERNAME, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "/view/registered/exercise.xhtml?Id=1");
        SeleniumUITestUtils.clickOnSidebarItem(getWait(),"Checkers");
        assertEquals(FIRST_PAGE_ELEMENTS, SeleniumUITestUtils.getCurrentTableRowsChecker(getDriver()));
    }
}
