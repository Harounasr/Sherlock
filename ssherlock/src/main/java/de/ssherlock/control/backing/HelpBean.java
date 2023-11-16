package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the help.xhtml facelet.
 */
@Named
@RequestScoped
public class HelpBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The message to display as help.
     */
    private String helpMessage;

    /**
     * Constructor for HelpBean.
     *
     * @param logger     The logger instance (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public HelpBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Retrieves the help message.
     *
     * @return The help message.
     */
    public String getHelpMessage() {
        return helpMessage;
    }
}
