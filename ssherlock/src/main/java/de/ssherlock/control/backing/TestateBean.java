package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Submission;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class TestateBean {
    @Inject
    private Logger logger;
    private Submission submission;

    public TestateBean() {

    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
