package de.ssherlock.control.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.CheckerResult;
import de.ssherlock.global.transport.CheckerType;
import de.ssherlock.global.transport.SubmissionFile;
import de.ssherlock.global.transport.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for {@link ZipUtils}.
 *
 * @author Victor Vollmann
 */
@ExtendWith(MockitoExtension.class)
public class CheckerUtilsIT {

    /**
     * Path to the test data for a valid submission.
     */
    private static final String VALID_SUBMISSION_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/valid_submission";

    /**
     * Path to the test data with compilation errors.
     */
    private static final String COMPILATION_ERRORS_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/compilation_errors";

    /**
     * Path to the test data with identity errors.
     */
    private static final String IDENTITY_ERRORS_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/identity_errors";

    /**
     * Path to the test data with line width errors.
     */
    private static final String LINE_WIDTH_ERRORS_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/line_width_errors";

    /**
     * Path to the test data for user defined checkers.
     */
    private static final String USER_DEFINED_TEST_DATA =
            "de/ssherlock/test_data/checker_test_data/user_defined";

    /**
     * The list of checkers being run.
     */
    private static List<Checker> checkersToRun;

    /**
     * The list of submission files the checkers should check.
     */
    private static List<SubmissionFile> filesToCheck;

    /**
     * The user for whom the checkers are run.
     */
    private static User user;

    /**
     * Initializes the test environment.
     */
    @BeforeAll
    public static void initTestEnvironment() {
        checkersToRun = new ArrayList<>();
        filesToCheck = new ArrayList<>();
        user = getUserMock();
    }

    /**
     * Clears the lists.
     */
    @AfterEach
    public void clearLists() {
        checkersToRun.clear();
        filesToCheck.clear();
    }

    /**
     * Runs a compilation checker for a valid submission.
     * The checker should pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunCompilationCheckerValidSubmission() throws Exception {
        checkersToRun.add(getCheckerMock(CheckerType.COMPILATION));
        filesToCheck = getSubmissionFilesForTest(VALID_SUBMISSION_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult compilationResult = results.get(0);
        assertEquals("All files compiled successfully.", compilationResult.getStackTrace());
        assertTrue(compilationResult.isPassed());
    }

    /**
     * Runs a compilation checker for a submission with compilation errors.
     * The checker should not pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunCompilationCheckerWithCompilationErrors() throws Exception {
        String expectedErrors =
                """
                Car: ';' expected
                Main: unclosed string literal
                Main: ';' expected
                """;
        checkersToRun.add(getCheckerMock(CheckerType.COMPILATION));
        filesToCheck = getSubmissionFilesForTest(COMPILATION_ERRORS_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult compilationResult = results.get(0);
        assertEquals(expectedErrors, compilationResult.getStackTrace());
        assertFalse(compilationResult.isPassed());
    }

    /**
     * Runs an identity checker for a valid submission.
     * The checker should pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunIdentityCheckerValidSubmission() throws Exception {
        String expectedStacktrace =
                """
                No identity content found in files.
                The checker ran successfully.
                """;
        checkersToRun.add(getCheckerMock(CheckerType.IDENTITY));
        filesToCheck = getSubmissionFilesForTest(VALID_SUBMISSION_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult identityResult = results.get(0);
        assertEquals(expectedStacktrace, identityResult.getStackTrace());
        assertTrue(identityResult.isPassed());
    }

    /**
     * Runs an identity checker for a submission with identity errors.
     * The checker should not pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunIdentityCheckerWithIdentityErrors() throws Exception {
        String expectedErrors =
                """
                Found 'Victor' in line 6 of file Car.java
                Found 'Vollmann' in line 6 of file Car.java
                Found 'Victor' in line 3 of file Main.java
                Found 'vollma08@ads.uni-passau.de' in line 5 of file Person.java
                """;
        checkersToRun.add(getCheckerMock(CheckerType.IDENTITY));
        filesToCheck = getSubmissionFilesForTest(IDENTITY_ERRORS_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult identityResult = results.get(0);
        assertEquals(expectedErrors, identityResult.getStackTrace());
        assertFalse(identityResult.isPassed());
    }

    /**
     * Runs a line-width checker for a valid submission.
     * The checker should pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunLineWidthCheckerValidSubmission() throws Exception {
        String expectedStacktrace =
                """
                All lines adhere to the maximum line width of 90 characters.
                The Checker LINE_WIDTH ran successfully.
                """;
        Checker checker = getCheckerMock(CheckerType.LINE_WIDTH);
        Mockito.when(checker.getParameterOne()).thenReturn("90");
        checkersToRun.add(checker);
        filesToCheck = getSubmissionFilesForTest(VALID_SUBMISSION_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult lineWidthResult = results.get(0);
        assertEquals(expectedStacktrace, lineWidthResult.getStackTrace());
        assertTrue(lineWidthResult.isPassed());
    }

    /**
     * Runs a line-width checker for a submission with line-width errors.
     * The checker should not pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunLineWidthCheckerWithSpacingErrors() throws Exception {
        String expectedStacktrace =
                """
                Line 13 in file Car.java exceeds the maximum line width (90 characters).
                Line 5 in file Main.java exceeds the maximum line width (90 characters).
                """;
        Checker checker = getCheckerMock(CheckerType.LINE_WIDTH);
        Mockito.when(checker.getParameterOne()).thenReturn("90");
        checkersToRun.add(checker);
        filesToCheck = getSubmissionFilesForTest(LINE_WIDTH_ERRORS_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult lineWidthResult = results.get(0);
        assertEquals(expectedStacktrace, lineWidthResult.getStackTrace());
        assertFalse(lineWidthResult.isPassed());
    }

    /**
     * Runs a user-defined checker for a valid submission
     * and valid input output configuration.
     * The checker should pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunUserDefinedCheckerSuccess() throws Exception {
        String input = "java Main.java hello none";
        String expectedOutput = """
                                12
                                hello
                                """;
        Checker checker = getCheckerMock(CheckerType.USER_DEFINED);
        checker.setParameterOne(input);
        checker.setParameterTwo(expectedOutput);
        checkersToRun.add(checker);
        filesToCheck = getSubmissionFilesForTest(USER_DEFINED_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult userDefinedResult = results.get(0);
        assertEquals("Checker USER_DEFINED ran successfully.", userDefinedResult.getStackTrace());
        assertTrue(userDefinedResult.isPassed());
    }

    /**
     * Runs a user-defined checker for a valid submission and
     * valid input output configuration and an expected error output.
     * The checker should pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunUserDefinedCheckerSuccessThrows() throws Exception {
        String input = "java Main.java hello throws";
        String expectedOutput = """
                                12
                                hello
                                Exception in thread "main" java.lang.IllegalArgumentException: The argument was illegal.
                                	at Main.main(Main.java:7)
                                """;
        Checker checker = getCheckerMock(CheckerType.USER_DEFINED);
        checker.setParameterOne(input);
        checker.setParameterTwo(expectedOutput);
        checkersToRun.add(checker);
        filesToCheck = getSubmissionFilesForTest(USER_DEFINED_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult userDefinedResult = results.get(0);
        assertEquals("Checker USER_DEFINED ran successfully.", userDefinedResult.getStackTrace());
        assertTrue(userDefinedResult.isPassed());
    }

    /**
     * Runs a user-defined checker for a valid submission, but with
     * invalid input output pairs.
     * The checker should not pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunUserDefinedCheckerFailure() throws Exception {
        String expectedStackTrace = """
                                    Checker USER_DEFINED did not run successfully.
                                    Expected:
                                    this will not be the output
                                    Actual:
                                    12
                                    hello
                                    """;
        String input = "java Main.java hello none";
        String expectedOutput = "this will not be the output";
        Checker checker = getCheckerMock(CheckerType.USER_DEFINED);
        checker.setParameterOne(input);
        checker.setParameterTwo(expectedOutput);
        checkersToRun.add(checker);
        filesToCheck = getSubmissionFilesForTest(USER_DEFINED_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult userDefinedResult = results.get(0);
        assertEquals(expectedStackTrace, userDefinedResult.getStackTrace());
        assertFalse(userDefinedResult.isPassed());
    }

    /**
     * Runs a user-defined checker for a submission with compilation errors
     * and with invalid input output pairs.
     * The checker should not pass and provide the correct stacktrace.
     *
     * @throws Exception When there is a problem loading the test data.
     */
    @Test
    void testRunUserDefinedCheckerCompilationError() throws Exception {
        String input = "java Main.java hello none";
        String expectedOutput = "this will not be the output";
        Checker checker = getCheckerMock(CheckerType.USER_DEFINED);
        checker.setParameterOne(input);
        checker.setParameterTwo(expectedOutput);
        checkersToRun.add(checker);
        filesToCheck = getSubmissionFilesForTest(COMPILATION_ERRORS_TEST_DATA);
        List<CheckerResult> results = CheckerUtils.runCheckers(checkersToRun, filesToCheck, user);
        assertEquals(1, results.size());
        CheckerResult userDefinedResult = results.get(0);
        assertEquals("The Checker: USER_DEFINED was not executed due to a compilation error.", userDefinedResult.getStackTrace());
        assertFalse(userDefinedResult.isPassed());
    }

    /**
     * Creates a user mock.
     *
     * @return The user mock.
     */
    private static User getUserMock() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getEmail()).thenReturn("vollma08@ads.uni-passau.de");
        Mockito.when(user.getUsername()).thenReturn("vicer12");
        Mockito.when(user.getFirstName()).thenReturn("Victor");
        Mockito.when(user.getLastName()).thenReturn("Vollmann");
        return user;
    }

    /**
     * Creates a checker mock of a given {@link CheckerType}.
     *
     * @param checkerType The type of checker.
     * @return The checker mock.
     */
    private static Checker getCheckerMock(CheckerType checkerType) {
        Checker checker = Mockito.mock(Checker.class);
        Mockito.lenient().when(checker.getCheckerType()).thenReturn(checkerType);
        Mockito.lenient().when(checker.getName()).thenReturn(checkerType.toString());
        return checker;
    }

    /**
     * Gets a list of test submission files from the provided location.
     *
     * @param location The location with the test data.
     * @return The list of submission files.
     * @throws IOException When there is a problem loading the test data.
     * @throws URISyntaxException When the path is invalid.
     */
    private static List<SubmissionFile> getSubmissionFilesForTest(String location) throws IOException, URISyntaxException {
        URL resourceURL = Thread.currentThread().getContextClassLoader().getResource(location);
        if (resourceURL == null) {
            throw new FileNotFoundException("Resource not found: " + location);
        }
        List<SubmissionFile> result = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(resourceURL.toURI()))) {
            paths.filter(Files::isRegularFile).forEach(filePath -> {
                try {
                    byte[] fileBytes = Files.readAllBytes(filePath);
                    String fileName = filePath.getFileName().toString();
                    SubmissionFile submissionFile = new SubmissionFile();
                    submissionFile.setBytes(fileBytes);
                    submissionFile.setName(fileName);
                    result.add(submissionFile);
                } catch (IOException ignored) { }
            });
        }
        return result;
    }
}
