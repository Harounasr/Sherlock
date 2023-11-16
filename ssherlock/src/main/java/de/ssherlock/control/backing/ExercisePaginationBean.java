package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
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
    private final ExerciseService exerciseService;

    /**
     * List of exercises to display in pagination.
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
    public ExercisePaginationBean(Logger logger, AppSession appSession, ExerciseService exerciseService) {
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
        exercises = exerciseService.getExercises(null);
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

}
