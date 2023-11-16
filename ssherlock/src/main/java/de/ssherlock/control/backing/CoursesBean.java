package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CoursesBean {

    private final Logger logger;
    private final AppSession appSession;
    private final CourseService courseService;

    private String newCourseName;

    @Inject
    public CoursesBean(Logger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
    }

    @PostConstruct
    public void init() {
        List<Course> courses = courseService.getCourses();
        courses.forEach(course -> logger.log(Level.INFO, "loaded course " + course.name() + " with exercises " + course.exercises()));
    }

    public String addCourse(ActionEvent e) {
        return "";
    }

    public String getNewCourseName() {
        return newCourseName;
    }

    public void setNewCourseName(String newCourseName) {
        this.newCourseName = newCourseName;
    }
    public String select() {
        return "/view/exercise.xhtml";
    }
}
