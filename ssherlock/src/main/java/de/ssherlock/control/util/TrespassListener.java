package de.ssherlock.control.util;

import jakarta.faces.annotation.FacesConfig;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;

@FacesConfig
public class TrespassListener implements PhaseListener {


    public TrespassListener() {

    }

    @Override
    public void afterPhase(PhaseEvent event) {

    }

    @Override
    public void beforePhase(PhaseEvent event) {
        PhaseListener.super.beforePhase(event);
    }

    @Override
    public PhaseId getPhaseId() {
        return null;
    }
}
