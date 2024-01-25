package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.exception.NoAccessException;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for course.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class CourseBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * The Course Service for course-related actions.
     */
    private final CourseService courseService;

    /**
     * The name of the current course.
     */
    private Course course;

    /**
     * The target page of the content.
     */
    private String targetPage;

    /**
     * Whether the user has teacher rights.
     */
    private boolean teacherRights;

    /**
     * Constructs a CourseBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param courseService The course service (Injected).
     */
    @Inject
    public CourseBean(SerializableLogger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
    }

    /**
     * Initializes the CourseBean after construction. Performs necessary setup actions for
     * course-related functionalities.
     */
    @PostConstruct
    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setDateHeader("Expires", 0);
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        String courseIdParam = requestParams.get("Id");
        Notification flashNotification = (Notification) facesContext.getExternalContext().getFlash().get("flashNotification");
        if (flashNotification != null) {
            flashNotification.generateUIMessage();
        }
        if (courseIdParam != null) {
            course = new Course();
            course.setId(Long.parseLong(courseIdParam));
        } else {
            Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
            course = (Course) sessionMap.get("currentCourse");
            if (course == null) {
                throw new NoAccessException("Course ID not found.");
            }
        }
        try {
            course = courseService.getCourseById(course);
        } catch (BusinessNonExistentCourseException e) {
            throw new NoAccessException("The course does not exist anymore.");
        }
        User user = appSession.getUser();
        CourseRole userCourseRole = user.getCourseRoles().getOrDefault(course.getId(), CourseRole.NONE);
        if (userCourseRole == CourseRole.NONE && user.getSystemRole() != SystemRole.ADMINISTRATOR) {
            throw new NoAccessException("Can not access, as user is not part of this course.");
        }
        teacherRights = userCourseRole == CourseRole.TEACHER || user.getSystemRole() == SystemRole.ADMINISTRATOR;
        setTargetPage("exercisePagination.xhtml");
        Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
        sessionMap.put("currentCourse", course);
    }

    /**
     * Deletes the current course from the database.
     *
     * @return the target page
     */
    public String deleteCourse() {
        try {
            courseService.removeCourse(course);
            logger.log(Level.INFO, "Course was deleted.");
            return "/view/registered/coursePagination.xhtml?faces-redirect=true";
        } catch (BusinessNonExistentCourseException e) {
            logger.log(Level.INFO, "Course could not be deleted.");
            Notification notification =
                    new Notification("Course could not be deleted.", NotificationType.ERROR);
            notification.generateUIMessage();
            return null;
        }
    }

    /**
     * Gets target page.
     *
     * @return the target page
     */
    public String getTargetPage() {
        return targetPage;
    }

    /**
     * Sets target page.
     *
     * @param targetPage the target page
     */
    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    /**
     * Getter for the course.
     *
     * @return Course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Is teacher rights boolean.
     *
     * @return the boolean
     */
    public boolean isTeacherRights() {
        return teacherRights;
    }

    /**
     * Sets teacher rights.
     *
     * @param teacherRights the teacher rights
     */
    public void setTeacherRights(boolean teacherRights) {
        this.teacherRights = teacherRights;
    }
}
