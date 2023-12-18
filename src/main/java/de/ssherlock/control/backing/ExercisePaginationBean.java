package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

import static java.util.logging.Level.INFO;

/**
 * Backing bean for the exercisePagination.xhtml facelet.
 *
 * @author Haroun Alswedany
 */
@Named
@ViewScoped
public class ExercisePaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
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
     * The parent bean.
     */
    private final CourseBean courseBean;

    /**
     * The current course.
     */
    private List<Exercise> exercises;

    /**
     * The new Exercise.
     */
    private Exercise exercise;

    /**
     * Constructs an ExercisePaginationBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     * @param courseBean The parent bean.
     */
    @Inject
    public ExercisePaginationBean(
            SerializableLogger logger, AppSession appSession, ExerciseService exerciseService, CourseBean courseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
        this.courseBean = courseBean;
    }

    /**
     * Initializes the ExercisePaginationBean after construction. Retrieves the exercises from the service.
     */
    @PostConstruct
    public void initialize() {
        getPagination().setPageSize(PAGE_SIZE);
        getPagination().setSortBy("name");
        exercises = exerciseService.getExercises(getPagination(), courseBean.getCourse());
        getPagination().setLastIndex(exercises.size() - 1);
    }

    /**
     * Navigates to the selected exercise.
     *
     * @param exercise The selected exercise.
     * @return The navigation outcome.
     */
    public String select(Exercise exercise) {
        logger.log(Level.INFO, "Selected Exercise: " + exercise.getId());
        return "/view/registered/exercise.xhtml?faces-redirect=true&Id=" + exercise.getId();
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
     * Adds an exercise to the database.
     */
    public void addExercise() {
        logger.log(INFO, "add new exercise");
        exerciseService.addExercise(exercise);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        exercises = exerciseService.getExercises(getPagination(), courseBean.getCourse());
    }

    /**
     * Gets exercise.
     *
     * @return the exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Sets exercise.
     *
     * @param exercise the exercise
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
