package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Course;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    private int currentPage = 1;
    private int pageSize = 10;

    private long ExerciseId = 3;

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

    public void firstPage() {
        currentPage = 0;
        loadData();
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadData();
        }
    }

    public void nextPage() {
        int lastIndex = courses.size() - 1;
        if (currentPage < lastIndex) {
            currentPage++;
            loadData();
        }
    }

    public void lastPage() {
        int lastIndex = courses.size() - 1;
        currentPage = lastIndex;
        loadData();
    }
    public void loadData() {
        courses = courseService.getCourses();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String select(Course course) {
        setExerciseId(stringToNumber(course.name()));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("courseName", course.name());
        logger.log(Level.INFO, "Selected Course: " + course.name());
        return "/view/exercise.xhtml?faces-redirect=true&Id=" + getExerciseId();
    }

    public long getExerciseId() {
        return ExerciseId;
    }

    public void setExerciseId(long exerciseId) {
        ExerciseId = exerciseId;
    }
    private static long stringToNumber(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the hash bytes to a long value
            long result = 0;
            for (int i = 0; i < Math.min(hashBytes.length, 8); i++) {
                result <<= 8;
                result |= (hashBytes[i] & 0xFF);
            }

            return result;
        } catch (NoSuchAlgorithmException e) {
            // Handle the case where the algorithm is not available
            e.printStackTrace();
            return 0L; // or throw an exception, return a default value, etc.
        }
    }
}
