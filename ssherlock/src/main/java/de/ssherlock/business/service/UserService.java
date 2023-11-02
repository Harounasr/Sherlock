package de.ssherlock.business.service;

import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.User;
import de.ssherlock.persistence.repository.CourseRepository;
import de.ssherlock.persistence.repository.RepositoryFactory;
import de.ssherlock.persistence.repository.RepositoryType;

import java.util.List;

public class UserService {

    public static void main(String[] args) {
        CourseRepository courseRepository = RepositoryFactory.getCourseRepository(RepositoryType.POSTGRESQL);
        List<Course> courses = courseRepository.fetchCourses(c -> true);
    }

    public static User getUser(String username) {
        return null;
    }
}
