package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static java.util.logging.Level.INFO;

/**
 * Backing bean for the coursePagination.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@SuppressWarnings("checkstyle:MagicNumber")
@Named
@ViewScoped
public class CoursePaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Page size for the pagination.
     */
    private static final int PAGE_SIZE = 5;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service to handle Course-related actions.
     */
    private final CourseService courseService;

    /**
     * Service to handle Course-related actions.
     */
    private final UserService userService;

    /**
     * The new course that the user creates.
     */
    private Course newCourse;

    /**
     * List of courses to be displayed.
     */
    private List<Course> courses;

    /**
     * Boolean for which list to display.
     */
    private boolean getAllCoursesBool;

    /**
     * Whether the pagination has loaded already.
     */
    private boolean paginationLoaded;

    /**
     * Constructs a CoursesPaginationBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param courseService The CourseService (Injected).
     * @param userService   The User service (Injected).
     */
    @Inject
    public CoursePaginationBean(SerializableLogger logger, AppSession appSession, CourseService courseService, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
        this.userService = userService;
    }

    /**
     * Initializes the CoursesPaginationBean after construction. Retrieves the list of courses for
     * pagination.
     */
    @PostConstruct
    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        Notification flashNotification = (Notification) facesContext.getExternalContext().getFlash().get("flashNotification");
        if (flashNotification != null) {
            flashNotification.generateUIMessage();
        }
        newCourse = new Course();
        getPagination().setPageSize(PAGE_SIZE);
        getPagination().setSortBy("name");
        if (!paginationLoaded) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            getAllCoursesBool = Boolean.parseBoolean(params.get("all"));
        }
        courses = getAllCoursesBool ? courseService.getCourses(getPagination()) : courseService.getCourses(getPagination(), appSession.getUser());
        getPagination().setLastIndex(courses.size() - 1);
        paginationLoaded = true;
    }

    /**
     * Navigates to the selected course.
     *
     * @param course The selected course.
     * @return The navigation outcome.
     */
    public String select(Course course) {
        CourseRole courseRole = appSession.getUser().getCourseRoles().get(course.getId());
        if (courseRole == null || courseRole == CourseRole.NONE) {
            userService.updateCourseRole(appSession.getUser(), course, CourseRole.MEMBER);
        }
        logger.info("selected course: " + course.getName() + " with id " + course.getId());
        return "/view/registered/course.xhtml?faces-redirect=true&Id=" + course.getId();
    }

    /**
     * Adds the newly created course to the database.
     */
    public void addCourse() {
        logger.log(INFO, "trying to add");
        courseService.addCourse(newCourse);
        Notification notification = new Notification("Success! The course " + newCourse.getName() + " was created.", NotificationType.SUCCESS);
        notification.generateUIMessage();
        String message = "Added course" + newCourse.getName();
        logger.info(message);
    }

    /**
     * Gets courses.
     *
     * @return the courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Sets courses.
     *
     * @param courses the courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * Gets new course.
     *
     * @return the new course
     */
    public Course getNewCourse() {
        return newCourse;
    }

    /**
     * Sets new course.
     *
     * @param newCourse the new course
     */
    public void setNewCourse(Course newCourse) {
        this.newCourse = newCourse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        courses = getAllCoursesBool ? courseService.getCourses(getPagination()) : courseService.getCourses(getPagination(), appSession.getUser());
    }

    /**
     * Getter for the appSession.
     *
     * @return appsSession
     */
    public AppSession getAppSession() {
        return appSession;
    }

    /**
     * Getter for the bool (not used at the moment).
     *
     * @return boolean
     */
    public boolean isGetAllCoursesBool() {
        return getAllCoursesBool;
    }

    /**
     * Setter for the bool (not used at the moment).
     *
     * @param bool boolean
     */
    public void setGetAllCoursesBool(boolean bool) {
        getAllCoursesBool = bool;
    }

    /**
     * Checks whether user is member of course.
     *
     * @param course The course.
     * @return Whether user is course member.
     */
    public boolean isInCourse(Course course) {
        User user = appSession.getUser();
        Map<Long, CourseRole> courseRoles = user.getCourseRoles();
        return courseRoles.get(course.getId()) != null && courseRoles.get(course.getId()) != CourseRole.NONE;
    }
}
