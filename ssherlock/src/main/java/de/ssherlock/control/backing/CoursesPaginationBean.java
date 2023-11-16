package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Logger;

/**
 * Backing bean for the coursesPagination.xhtml facelet.
 */
@Named
@RequestScoped
public class CoursesPaginationBean {

    /**
     * Logger for logging within this class.
     */
    private Logger logger;

    /**
     * The active session.
     */
    private AppSession appSession;

    /**
     * Service to handle Course-related actions.
     */
    private CourseService courseService;

    /**
     * List of courses to be displayed.
     */
    private List<Course> courses;

    /**
     * Constructs a CoursesPaginationBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param courseService The CourseService (Injected).
     */
    @Inject
    public CoursesPaginationBean(Logger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
    }

    /**
     * Initializes the CoursesPaginationBean after construction.
     * Retrieves the list of courses for pagination.
     */
    @PostConstruct
    public void initialize() {
        courses = courseService.getCourses();
    }

    /**
     * Action for navigating to a specific course.
     *
     * @param e The fired ActionEvent.
     * @return A navigation outcome string of the selected course.
     */
    public String navigateToCourse(ActionEvent e) {
        return "";
    }

    /**
     * Getter for the list of courses.
     *
     * @return The list of courses.
     */
    public List<Course> getCourses() {
        return courses;
    }

}
