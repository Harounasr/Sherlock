package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FilenameFilter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * UI Test for the {@code submissionPagination.xhtml} facelet.
 *
 * @author Victor Vollmann
 */
@SuppressWarnings("checkstyle:MagicNumber")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubmissionPaginationUITest extends AbstractSeleniumUITest {

    /**
     * Path to the test data for a valid submission.
     */
    private static final String VALID_SUBMISSION_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/valid_submission";

    @BeforeAll
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

    @Test
    void testCheckCorrectDataAsMember() throws InterruptedException {
        loginAndNavigateAsUser(SeleniumUITestUtils.MEMBER_USERNAME);
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Submission']")));
        exerciseButton.click();
        Thread.sleep(60000);
    }

    @Test
    @Disabled
    void testCheckCorrectDataAsTutor() {
        loginAndNavigateAsUser(SeleniumUITestUtils.TUTOR_USERNAME);
    }

    private static void loginAndNavigateAsUser(String username) {
        SeleniumUITestUtils.tryLogin(getDriver(), getWait(), username, SeleniumUITestUtils.GLOBAL_PASSWORD);
        SeleniumUITestUtils.navigateTo(getDriver(), "view/registered/course.xhtml?Id=1");
        WebElement exerciseButton = getWait().until(elementToBeClickable(By.cssSelector("input[value='To Exercise']")));
        exerciseButton.click();
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/registered/exercise.xhtml?Id=1", getDriver().getCurrentUrl());
        SeleniumUITestUtils.clickOnSidebarItem(getWait(), "Submissions");
    }

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
