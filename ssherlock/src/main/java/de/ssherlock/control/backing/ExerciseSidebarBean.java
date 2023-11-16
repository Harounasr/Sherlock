package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

/**
 * Backing bean for the exerciseSidebar.xhtml facelet.
 */
@Named
@RequestScoped
public class ExerciseSidebarBean {

    /**
     * The logger for this class.
     */
    private final Logger logger;

    /**
     * The application session.
     */
    private final AppSession appSession;

    /**
     * Constructor for ExerciseSidebarBean.
     *
     * @param logger     The logger instance (Injected).
     * @param appSession The application session (Injected).
     */
    @Inject
    public ExerciseSidebarBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Loads exerciseDescription.xhtml into the content.
     */
    public void loadDescription() {

    }

    /**
     * Loads submissionUpload.xhtml into the content.
     */
    public void loadUploadPage() {

    }

    /**
     * Loads allSubmissionsPagination.xhtml into the content.
     */
    public void loadAllSubmission() {

    }

    /**
     * Loads allTestatesPagination.xhtml into the content.
     */
    public void loadAllTestates() {

    }
}
