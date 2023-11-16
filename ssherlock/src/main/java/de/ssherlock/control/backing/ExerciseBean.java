package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the exercise.xhtml facelet.
 */
@Named
@ViewScoped
public class ExerciseBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;


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
     * The current Exercise being managed.
     */
    private Exercise exercise;

    /**
     * Constructs an ExerciseBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     */
    @Inject
    public ExerciseBean(Logger logger, AppSession appSession, ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
    }

    /**
     * Initializes the ExerciseBean after construction.
     * Performs any necessary setup.
     */
    @PostConstruct
    public void initialize() {

    }

    /**
     * Gets the exercise.
     *
     * @return The exercise.
     */
    public Exercise getExercise() {
        return exercise;
    }

}
