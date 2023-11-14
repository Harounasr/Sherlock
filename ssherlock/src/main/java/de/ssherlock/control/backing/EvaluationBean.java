package de.ssherlock.control.backing;

import de.ssherlock.business.service.TestateService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.Testate;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class EvaluationBean {

    private final Logger logger;
    private final AppSession appSession;
    private final TestateService testateService;

    private Testate testate;

    @Inject
    public EvaluationBean(Logger logger, AppSession appSession, TestateService testateService) {
        this.logger = logger;
        this.appSession = appSession;
        this.testateService = testateService;
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
