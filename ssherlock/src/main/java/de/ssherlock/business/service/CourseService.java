package de.ssherlock.business.service;


import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.connection.ConnectionPoolPsql;
import de.ssherlock.persistence.repository.CourseRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.*;
import java.sql.Connection;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Dependent
public class CourseService {

    private final Logger logger;
    private final ConnectionPoolPsql connectionPoolPsql;

    @Inject
    public CourseService(Logger logger, ConnectionPoolPsql connectionPoolPsql) {
        this.logger = logger;
        this.connectionPoolPsql = connectionPoolPsql;
    }
    public List<Course> getCourses() {
        Connection connection = connectionPoolPsql.getConnection();
        CourseRepository courseRepository = RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL, connection);
        List<Course> courses = courseRepository.fetchCourses((course -> true));
        connectionPoolPsql.releaseConnection(connection);
        return courses;
    }
    public List<Course> getCourses(User user) {
        return null;
    }
    public Course getCourse(String courseName) {
        return null;
    }
    public void addCourse() {

    }
    public void removeCourse() {

    }
    public void updateCourseRole(User user, CourseRole courseRole) {

    }
}
