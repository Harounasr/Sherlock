package de.ssherlock.control.util;

import de.ssherlock.control.exception.NoAccessException;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.annotation.FacesConfig;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

import java.io.Serial;
import java.util.logging.Logger;

/**
 * Oversees user access permissions by verifying whether the role saved in the current session aligns with the required
 * role for specific actions. If there's a mismatch, the user is promptly notified of their lack of permission to
 * perform the action.
 */
@FacesConfig
public class TrespassListener implements PhaseListener {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger instance for this class.
     */
    private final SerializableLogger logger = LoggerCreator.get(TrespassListener.class);

    /**
     * Default constructor.
     */
    public TrespassListener() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        AppSession appSession = CDI.current().select(AppSession.class).get();
        FacesContext context = event.getFacesContext();
        final UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot != null) {
            final String url = viewRoot.getViewId();
            if (url.startsWith("/view/admin") && !appSession.isAdmin()) {
                logger.info("An unauthorized attempt was made to access an admin site without the necessary rights.");
                throw new NoAccessException("User isn't an admin.");
            } else if (url.startsWith("/view/registered") && appSession.isAnonymous()) {
                logger.info("An attempt was made to access a user site without being logged in.");
                throw new NoAccessException("User isn't logged in.");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforePhase(PhaseEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
