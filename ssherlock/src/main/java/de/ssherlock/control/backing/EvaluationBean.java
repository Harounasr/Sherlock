package de.ssherlock.control.backing;

import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.Testate;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class EvaluationBean {

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private TestateService testateService;

    private Testate testate;

    public EvaluationBean() {

    }

    @PostConstruct
    public void initialize() {
        loadEvaluation();
    }

    private void loadEvaluation() {

    }

    public Testate getEvaluation() {
        return testate;
    }

    public void setEvaluation(Testate testate) {
        this.testate = testate;
    }
}
