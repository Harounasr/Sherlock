package de.ssherlock.load_tests;

import de.ssherlock.system_tests.ui.facelets.AdminUserPaginationUITest;
import de.ssherlock.system_tests.ui.facelets.CheckerListUITest;
import de.ssherlock.system_tests.ui.facelets.CourseUserPaginationUITest;
import de.ssherlock.system_tests.ui.facelets.ExerciseDescriptionUITest;
import de.ssherlock.system_tests.ui.facelets.FooterUITest;
import de.ssherlock.system_tests.ui.facelets.LoginUITest;
import de.ssherlock.system_tests.ui.facelets.NavbarUITest;
import de.ssherlock.system_tests.ui.facelets.PasswordResetUITest;
import de.ssherlock.system_tests.ui.facelets.ProfileUITest;
import de.ssherlock.system_tests.ui.facelets.RegistrationUITest;
import de.ssherlock.system_tests.ui.facelets.TestateUITest;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.jsmart.zerocode.jupiter.extension.ParallelLoadExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Main Class for load testing.
 *
 * @author Victor Vollmann
 */
@LoadWith("load_tests/load-config.properties")
@ExtendWith(ParallelLoadExtension.class)
public class MainLoadTest {

    /**
     * The load test.
     */
    @Test
    @DisplayName("Main Load Test")
    @TestMappings({
            @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testCheckCorrectContent"),
            @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testCheckSearch"),
            @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testSelectUser"),
            @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testChangeFaculty"),
            @TestMapping(testClass = CheckerListUITest.class, testMethod = "testCheckContentPagination"),
            @TestMapping(testClass = CourseUserPaginationUITest.class, testMethod = "testCheckCorrectContent"),
            @TestMapping(testClass = CourseUserPaginationUITest.class, testMethod = "testCheckSearch"),
            @TestMapping(testClass = CourseUserPaginationUITest.class, testMethod = "testNextButton"),
            @TestMapping(testClass = CourseUserPaginationUITest.class, testMethod = "testLastButton"),
            @TestMapping(testClass = CourseUserPaginationUITest.class, testMethod = "testPrevButton"),
            @TestMapping(testClass = CourseUserPaginationUITest.class, testMethod = "testFirstButton"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testCheckCorrectDates"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditHTMLSuccess"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditHTMLFailure"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testUploadImageSuccess"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testUploadImageFailure"),
            @TestMapping(testClass = FooterUITest.class, testMethod = "testFooter"),
            @TestMapping(testClass = LoginUITest.class, testMethod = "testLoginSuccess"),
            @TestMapping(testClass = LoginUITest.class, testMethod = "testRegisterClicked"),
            @TestMapping(testClass = LoginUITest.class, testMethod = "testPasswordForgottenClicked"),
            @TestMapping(testClass = NavbarUITest.class, testMethod = "navigateToAllCourses"),
            @TestMapping(testClass = NavbarUITest.class, testMethod = "navigateToMyCourses"),
            @TestMapping(testClass = NavbarUITest.class, testMethod = "navigateToAdmin"),
            @TestMapping(testClass = NavbarUITest.class, testMethod = "navigateToProfile"),
            @TestMapping(testClass = NavbarUITest.class, testMethod = "navigateToHelp"),
            @TestMapping(testClass = NavbarUITest.class, testMethod = "logout"),
            @TestMapping(testClass = PasswordResetUITest.class, testMethod = "testResetPassword"),
            @TestMapping(testClass = ProfileUITest.class, testMethod = "testCorrectContent"),
            @TestMapping(testClass = RegistrationUITest.class, testMethod = "testRegistrationValidators"),
            @TestMapping(testClass = TestateUITest.class, testMethod = "testGradeValidators"),
            @TestMapping(testClass = TestateUITest.class, testMethod = "testCommentValidators"),

    })
    public void load() {

    }
}
