package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentCourseException;
import de.ssherlock.business.service.CourseService;
import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Backing bean for the exercisePagination.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class ExercisePaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The page size for the pagination.
     */
    private static final int PAGE_SIZE = 10;

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
    private final ExerciseService exerciseService;

    /**
     * The current course.
     */
    private List<Exercise> exercises;

    /**
     * Constructs an ExercisePaginationBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     */
    @Inject
    public ExercisePaginationBean(SerializableLogger logger, AppSession appSession, ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
    }

    /**
     * Initializes the ExercisePaginationBean after construction.
     * Retrieves the exercises from the service.
     */
    @PostConstruct
    public void initialize() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        exercises = exerciseService.getExercises(requestParams.get("Id"));
    }

    /**
     * Navigates to the selected exercise.
     *
     * @param exercise The selected exercise.
     * @return The navigation outcome.
     */
    public String select(Exercise exercise) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("exerciseId", exercise.getId());
        logger.log(Level.INFO, "Selected Exercise: " + exercise.getName());
        return "/view/exercise.xhtml?faces-redirect=true&Id=" + exercise.getId();
    }

    /**
     * Gets exercises.
     *
     * @return the exercises
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Sets exercises.
     *
     * @param exercises the exercises
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void filterBy() {

    }
}
