package de.ssherlock.control.backing;

import static java.util.logging.Level.INFO;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Backing bean for the coursePagination.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class CoursePaginationBean extends AbstractPaginationBean implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Page size for the pagination. */
  private static final int PAGE_SIZE = 2;

  /** Logger for logging within this class. */
  private final SerializableLogger logger;

  /** The active session. */
  private final AppSession appSession;

  /** Service to handle Course-related actions. */
  private final CourseService courseService;

  /** The new course that the user creates. */
  private Course newCourse;

  /** List of courses to be displayed. */
  private List<Course> courses;
/** List of the users courses*/
  private List<Course> myCourses;
  /** List of all courses.*/
  private List<Course> allCourses;

  /** current Index. */
  private int currentIndex;

  /** page Size of the pagination. */
  private int pageSize;

  /** Boolean for which lsit to display. */
  private boolean getAllCoursesBool;

  /**
   * Constructs a CoursesPaginationBean.
   *
   * @param logger The logger used for logging within this class (Injected).
   * @param appSession The active session (Injected).
   * @param courseService The CourseService (Injected).
   */
  @Inject
  public CoursePaginationBean(
      SerializableLogger logger, AppSession appSession, CourseService courseService) {
    this.logger = logger;
    this.appSession = appSession;
    this.courseService = courseService;
    this.newCourse = new Course();
  }

  /**
   * Initializes the CoursesPaginationBean after construction. Retrieves the list of courses for
   * pagination.
   */
  @PostConstruct
  public void initialize() {
    currentIndex = 0;
    pageSize = 5;
    System.out.println("bool: " + getAllCoursesBool);
    Map<String, String> params =
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    getAllCoursesBool = Boolean.parseBoolean(params.get("all"));
    initCourses();
    /*
    getPagination().setPageSize(PAGE_SIZE);
    getPagination().setCurrentIndex(0);
    getPagination().setLastIndex(courses.size() - 1);
    logger.log(INFO, String.valueOf(courses.get(0).getName()));

     */

  }

  private void initCourses() {
    myCourses = courseService.getCourses(appSession.getUser());
    allCourses = courseService.getCourses();
    /*
    getAllCoursesBool =
        Boolean.parseBoolean(
            FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("seeAllCourses"));

     */
    System.out.println(getAllCoursesBool);
    if (getAllCoursesBool) {
      System.out.println("init set all");
      courses = allCourses;
    } else {
      System.out.println("init set my");
      courses = myCourses;
    }
  }

  /**
   * Navigates to the selected course.
   *
   * @param course The selected course.
   * @return The navigation outcome.
   */
  public String select(Course course) {
    FacesContext.getCurrentInstance()
        .getExternalContext()
        .getFlash()
        .put("courseName", course.getName());
    logger.log(INFO, "Selected Course: " + course.getName());
    return "/view/registered/course.xhtml?faces-redirect=true&Id=" + course.getName();
  }

  /** Adds the newly created course to the database. */
  public void addCourse() {
    logger.log(INFO, "trying to add");
    courseService.addCourse(newCourse);
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

  /** {@inheritDoc} */
  @Override
  public String loadData() {
    initCourses();
    // courses = courseService.getCourses(appSession.getUser());
    return "";
  }

  /** {@inheritDoc} */
  @Override
  public String search() {
    return "";
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
   * Getter for the current index.
   *
   * @return the current index
   */
  public int getCurrentIndex() {
    return currentIndex;
  }

  /**
   * Setter for the current index.
   *
   * @param currentIndex the current index
   */
  public void setCurrentIndex(int currentIndex) {
    this.currentIndex = currentIndex;
  }

    /**
     * Getter for the Page Size.
     * @return page size
     */
  public int getPageSize() {
    return pageSize;
  }

    /**
     * Getter for the bool (not used at the moment).
     * @return boolean
     */
  public boolean isGetAllCoursesBool() {
    return getAllCoursesBool;
  }
    /**
     * Setter for the bool (not used at the moment).
     * @param bool boolean
     */
  public void setGetAllCoursesBool(boolean bool) {
    getAllCoursesBool = bool;
  }
}
