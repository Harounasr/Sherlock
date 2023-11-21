package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Backing bean for the exercisePagination.xhtml facelet.
 */
@Named
@ViewScoped
public class ExercisePaginationBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service for handling Exercise-related actions.
     */
    private final CourseService courseService;

    /**
     * List of exercises to display in pagination.
     */
    private Course course;

    private int currentPage = 1;
    private int pageSize = 10;

    private String exerciseId;

    /**
     * Constructs an ExercisePaginationBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param courseService The ExerciseService (Injected).
     */
    @Inject
    public ExercisePaginationBean(SerializableLogger logger, AppSession appSession, CourseService courseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
    }

    /**
     * Initializes the ExercisePaginationBean after construction.
     * Retrieves the exercises from the service.
     */
    @PostConstruct
    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        try {
            course = courseService.getCourse(requestParams.get("Id"));
        } catch (BusinessNonExistentCourseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the list of exercises.
     *
     * @return The list of exercises.
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    public String select(Exercise exercise) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("exerciseId", exercise.getId());
        logger.log(Level.INFO, "Selected Exercise: " + exercise.getName());
        return "/view/exercise.xhtml?faces-redirect=true&Id=" + exercise.getId();
    }

    public void firstPage() {
        currentPage = 0;
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }

    public void nextPage() {
        int lastIndex = course.getExercises().size() - 1;
        if (currentPage < lastIndex) {
            currentPage++;
        }
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
