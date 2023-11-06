package de.ssherlock.persistence.repository;

import de.ssherlock.global.transport.Course;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class CourseRepositoryPsql extends RepositoryPsql implements CourseRepository {

    private Logger logger;
    public CourseRepositoryPsql(Connection connection) {
        super(connection);
    }

    @Override
    public void insertCourse(Course course) {

    }

    @Override
    public void updateCourse(Course course) {

    }

    @Override
    public void deleteCourse(String courseName) {

    }

    @Override
    public Course fetchCourse(String courseName) {
        return null;
    }

    @Override
    public List<Course> fetchCourses(Predicate<Course> predicate) {
        return null;
    }
}
