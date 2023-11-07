package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Submission;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class AllSubmissionsBean {

    @Inject
    private Logger logger;
    private List<Submission> submissions;

    public AllSubmissionsBean() {

    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}




