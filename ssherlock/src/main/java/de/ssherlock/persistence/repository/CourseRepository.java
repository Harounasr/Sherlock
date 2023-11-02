package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;

import java.util.List;
import java.util.function.Predicate;

public interface CourseRepository {
    void insertCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(String courseName);
    Course fetchCourse(String courseName);
    List<Course> fetchCourses(Predicate<Course> predicate);

}
