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
@RequestScoped
public class ExercisePaginationBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

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
    private List<Exercise> exercises;

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
    public ExercisePaginationBean(Logger logger, AppSession appSession, CourseService courseService) {
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
        requestParams.get("Id");
        exercises = courseService.getCourse(requestParams.get("Id")).getExercises();
    }

    /**
     * Navigates to a selected exercise.
     *
     * @param e The action event.
     * @return Navigation outcome string to the exercise.
     */
    public String navigateToExercise(ActionEvent e) {
        return "";
    }

    /**
     * Retrieves the list of exercises.
     *
     * @return The list of exercises.
     */
    public List<Exercise> getExercises() {
        return exercises;
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
        setExerciseId(exercise.getName());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("exerciseName", exercise.getName());
        logger.log(Level.INFO, "Selected Course: " + exercise.getName());
        return "/view/registered/exercise.xhtml?faces-redirect=true&Id=" + getExerciseId();
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }
}
