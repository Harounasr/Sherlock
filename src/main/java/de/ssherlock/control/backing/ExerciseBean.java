package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
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
 * Backing bean for the exercise.xhtml facelet.
 *
 * @author Leon Höfling
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
     * The ID of the current exercise.
     */
    private long exerciseId;

    /**
     * The target page of the content.
     */
    private String targetPage;

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
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        exerciseId = Long.parseLong(requestParams.get("Id"));
        logger.log(Level.INFO, "Param: " + exerciseId);
        this.setTargetPage("exerciseDescription.xhtml");
    }

    /**
     * Deletes the current exercise.
     */
    public void deleteExercise() {

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
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("courseId", exerciseId);
        this.targetPage = targetPage;
    }
}
