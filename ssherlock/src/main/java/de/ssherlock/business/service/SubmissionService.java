package de.ssherlock.business.service;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Submission;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class SubmissionService {
    @Inject
    private Logger logger;
    public SubmissionService() {

    }
    public void addSubmission() {

    }
    public void removeSubmission() {

    }
    public List<Submission> getSubmissions(Exercise exercise) {
        return null;
    }
    public List<Submission> getSubmissions(User user, Exercise exercise) {
        return null;
    }
    public void updateSubmission(Submission submission) {

    }
    public List<Submission> getNewestSubmission(Exercise exercise) {
        return null;
    }

}
