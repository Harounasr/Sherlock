package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing Bean for the admin.xhtml facelet.
 */
@Named
@ViewScoped
public class AdminBean implements Serializable {

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
     * The active Session.
     */
    private final AppSession appSession;

    /**
     * Constructs an AdminBean.
     *
     * @param logger     The logger used for logging within this class (Injected).
     * @param appSession The active session (Injected).
     */
    @Inject
    public AdminBean(SerializableLogger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }
}
