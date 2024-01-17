package de.ssherlock.load_tests;

import de.ssherlock.system_tests.ui.facelets.AdminUserPaginationUITest;
import de.ssherlock.system_tests.ui.facelets.ExerciseDescriptionUITest;
import de.ssherlock.system_tests.ui.facelets.LoginUITest;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.jsmart.zerocode.jupiter.extension.ParallelLoadExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@LoadWith("load_tests/load-config.properties")
@ExtendWith(ParallelLoadExtension.class)
public class MainLoadTest {

    @Test
    @DisplayName("Main Load Test")
    @TestMappings({
            @TestMapping(testClass = LoginUITest.class, testMethod = "testLoginSuccess"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditDates"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditHTMLSuccess"),
            @TestMapping(testClass = ExerciseDescriptionUITest.class, testMethod = "testEditHTMLFailure"),
            @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testCheckSearch"),
            @TestMapping(testClass = AdminUserPaginationUITest.class, testMethod = "testChangeRoles")
    })
    public void load() {

    }
}
