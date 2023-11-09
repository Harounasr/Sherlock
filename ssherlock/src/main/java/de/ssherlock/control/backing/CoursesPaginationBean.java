package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Logger;

public class CoursesPaginationBean {

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private CourseService courseService;

    private List<Course> courses;

    public CoursesPaginationBean() {

    }

    @PostConstruct
    public void initialize() {
        courses = BackingBeanInitializationUtils.loadCourses(courseService);
    }

    public String navigateToCourse(ActionEvent e) {
        return "";
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
