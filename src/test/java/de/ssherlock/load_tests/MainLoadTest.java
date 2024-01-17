package de.ssherlock.load_tests;

import de.ssherlock.system_tests.ui.facelets.AdminUserPaginationUITest;
import de.ssherlock.system_tests.ui.facelets.ExerciseDescriptionUITest;
import de.ssherlock.system_tests.ui.facelets.LoginUITest;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeMultiLoadRunner;
import org.junit.runner.RunWith;

@LoadWith("/load_tests/load-config.properties")
@RunWith(ZeroCodeMultiLoadRunner.class)
@TestMappings({
        @TestMapping(testClass = LoginUITest.class, testMethod = "testLoginSuccess"),
        @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditDates"),
        @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditHTMLSuccess"),
        @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditHTMLFailure"),
        @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testCheckSearch"),
        @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testChangeRoles")
})
public class MainLoadTest {}
