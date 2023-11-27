package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serial;
import java.io.Serializable;


@Named
@ViewScoped
public class FooterBean implements Serializable {

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
     * The text content for the imprint.
     */

    @Inject
    public FooterBean(SerializableLogger logger, AppSession appSession){
        this.logger = logger;
        this.appSession = appSession;
    }

    public String getImprintText() {
        return imprintText;
    }

    public void setImprintText(String imprintText) {
        this.imprintText = imprintText;
    }
}
