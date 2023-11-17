package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * Backing bean for the exercise.xhtml facelet.
 */
@Named
@ViewScoped
//@ManagedBean
public class ExerciseBean implements Serializable {

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
    private final ExerciseService exerciseService;

    /**
     * The current Exercise being managed.
     */
    private Exercise exercise;

    //@ManagedProperty(value="#{param.yourParamName}")
    //private String yourParam;

    /**
     * Constructs an ExerciseBean.
     *
     * @param logger          The logger used for logging within this class (Injected).
     * @param appSession      The active session (Injected).
     * @param exerciseService The ExerciseService (Injected).
     */
    @Inject
    public ExerciseBean(SerializableLogger logger, AppSession appSession, ExerciseService exerciseService) {
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
        //logger.log(Level.INFO, "Param: " + yourParam);
    }

    /**
     * Gets the exercise.
     *
     * @return The exercise.
     */
    public Exercise getExercise() {
        return exercise;
    }

    //public String getYourParam() {
    //    return yourParam;
    //}

   // public void setYourParam(String yourParam) {
   //     this.yourParam = yourParam;
    //}
}
