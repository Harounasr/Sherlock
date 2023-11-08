package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Evaluation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class EvaluationBean {

    @Inject
    private Logger logger;
    private Evaluation evaluation;

    public EvaluationBean() {

    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
