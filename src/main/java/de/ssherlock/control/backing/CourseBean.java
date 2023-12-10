package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for course.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class CourseBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Logger for logging within this class. */
  private final SerializableLogger logger;

  /** Active session. */
  private final AppSession appSession;

  /** The Course Service for course-related actions. */
  private final CourseService courseService;

  /** The name of the current course. */
  private String courseName;

  /** The target page of the content. */
  private String targetPage;

  /**
   * Constructs a CourseBean.
   *
   * @param logger The logger used for logging within this class (Injected).
   * @param appSession The active session (Injected).
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
    Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
    courseName = requestParams.get("Id");
  }

  /** Deletes the current course from the database.
   *
   * @return the target page
   * */
  public String deleteCourse() {
    try {
      courseService.removeCourse(courseName);
      logger.log(Level.INFO, "Course was deleted.");
      return "/view/registered/coursePagination.xhtml";
    } catch (BusinessNonExistentCourseException e) {
      logger.log(Level.INFO, "Course could not be deleted.");
      Notification notification = new Notification("Course could not be deleted.", NotificationType.ERROR);
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
}
