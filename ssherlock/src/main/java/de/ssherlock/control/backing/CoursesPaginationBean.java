package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CoursesPaginationBean {

    private Logger logger;
    private AppSession appSession;
    private CourseService courseService;

    private List<Course> courses;

    @Inject
    public CoursesPaginationBean(Logger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
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
