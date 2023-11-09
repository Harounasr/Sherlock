package de.ssherlock.control.util;

import de.ssherlock.business.service.*;
import de.ssherlock.global.transport.*;

import java.util.List;
import java.util.Map;

public class BackingBeanInitializationUtils {

    public static Course getActiveCourse() {
        return null;
    }

    public static Exercise getActiveExercise() {
        return null;
    }

    public static List<User> loadUsers(UserService userService) {
        return null;
    }

    public static SystemSettings loadSystemSettings(SystemService systemService) {
        return null;
    }

    public static Map<User, CourseRole> loadCourseRoles(Course course, UserService userService) {
        return null;
    }

    public static List<Checker> loadCheckers(Exercise exercise, CheckerService checkerService) {
        return null;
    }

    public static List<Submission> loadSubmissions(Exercise exercise, SubmissionService submissionService) {
        return null;
    }

    public static List<Testate> loadTestates(Exercise exercise, TestateService testateService) {
        return null;
    }

    public static List<Exercise> loadExercises(Course course, ExerciseService exerciseService) {
        return null;
    }

    public static List<Course> loadCourses(CourseService courseService) {
        return null;
    }

}
