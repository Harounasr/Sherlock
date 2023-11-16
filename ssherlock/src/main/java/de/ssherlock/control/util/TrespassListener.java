package de.ssherlock.control.util;

import jakarta.faces.annotation.FacesConfig;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

/**
 * PhaseListener implementation for handling trespass-related events.
 */
@FacesConfig
public class TrespassListener implements PhaseListener {

    /**
     * Invoked after the phase has been processed.
     *
     * @param event The PhaseEvent representing the phase after event.
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        // Implementation for handling events after the phase
    }

    /**
     * Invoked before the phase is processed.
     *
     * @param event The PhaseEvent representing the phase before event.
     */
    @Override
    public void beforePhase(PhaseEvent event) {
        // Implementation for handling events before the phase
    }

    /**
     * Returns the identifier of the phase during which this listener is interested in processing events.
     *
     * @return The PhaseId representing the phase during which this listener is interested.
     */
    @Override
    public PhaseId getPhaseId() {
        return null;
    }
}
