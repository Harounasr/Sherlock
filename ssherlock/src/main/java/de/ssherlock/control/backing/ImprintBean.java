package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the imprint.xhtml facelet.
 */
@Named
@ViewScoped
public class ImprintBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The text content for the imprint.
     */
    private String imprintText;

    /**
     * Constructor for ImprintBean.
     *
     * @param logger       The logger instance (Injected).
     * @param appSession   The active session (Injected).
     */
    @Inject
    public ImprintBean(SerializableLogger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Retrieves the imprint text.
     *
     * @return The imprint text.
     */
    public String getImprintText() {
        return imprintText;
    }

    /**
     * Sets the imprint text.
     *
     * @param imprintText The text to set as the imprint.
     */
    public void setImprintText(String imprintText) {
        this.imprintText = imprintText;
    }
}
