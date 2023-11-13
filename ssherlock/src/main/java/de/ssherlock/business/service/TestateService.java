package de.ssherlock.business.service;

import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.Testate;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@Dependent
public class TestateService {

    @Inject
    private Logger logger;
    public TestateService() {

    }
    public List<Testate> getTestates(Exercise exercise) {
        return null;
    }
    public List<Testate> getAssignedTestates(Exercise exercise, User user) {
        return null;
    }
    public Testate getTestate(Exercise exercise, User user) {
        return null;
    }
    public void updateTestate(Testate testate) {

    }

}
