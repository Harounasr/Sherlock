package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;

/**
 * Backing Bean for the footer.xhtml facelet.
 *
 * @author Lennart Hohls
 */
@Named
@ViewScoped
public class FooterBean implements Serializable {

    /**
     * Serial Version UID.
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
     * Constructs a FooterBean.
     *
     * @param logger     The logger used for logging within this class (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public FooterBean(SerializableLogger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Getter for the ImprintText.
     *
     * @return The Imprint text.
     */
    public String getImprintText() {
        return imprintText;
    }

    /**
     * Setter for the ImprintText.
     *
     * @param imprintText Text which will me set.
     */
    public void setImprintText(String imprintText) {
        this.imprintText = imprintText;
    }
}
