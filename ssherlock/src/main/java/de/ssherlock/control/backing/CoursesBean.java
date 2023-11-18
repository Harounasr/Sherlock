package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backing bean for the courses.xhtml facelet.
 */
@Named
@RequestScoped
public class CoursesBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service to handle Course-related actions.
     */
    private final CourseService courseService;

    /**
     * The name of the new course.
     */
    private String newCourseName;

    /**
     * Constructs a CoursesBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param courseService The CourseService (Injected).
     */
    @Inject
    public CoursesBean(Logger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
    }

    /**
     * Initializes the CoursesBean after construction.
     * Loads courses.
     */
    @PostConstruct
    public void init() {
        List<Course> courses = courseService.getCourses();
        courses.forEach(course -> logger.log(Level.INFO, "loaded course " + course.name() + " with exercises " + course.exercises()));
    }

    /**
     * Action for adding a new course.
     *
     * @param e The fired ActionEvent.
     * @return A navigation outcome string to the created course.
     */
    public String addCourse(ActionEvent e) {
        return "";
    }

    /**
     * Gets the new course name.
     *
     * @return The new course name.
     */
    public String getNewCourseName() {
        return newCourseName;
    }

    /**
     * Setter for the new course name.
     *
     * @param newCourseName The new course name to be set.
     */
    public void setNewCourseName(String newCourseName) {
        this.newCourseName = newCourseName;
    }
}
