package de.ssherlock.business.service;


import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

@Named
@Dependent
public class CourseService {
    @Inject
    Logger logger;

    public CourseService() {

    }
    public List<Course> getCourses() {
        return null;
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
