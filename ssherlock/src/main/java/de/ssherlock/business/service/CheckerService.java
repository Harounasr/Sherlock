package de.ssherlock.business.service;

import de.ssherlock.global.transport.Checker;
import de.ssherlock.global.transport.Exercise;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@Dependent
public class CheckerService {
    @Inject
    private Logger logger;

    public CheckerService() {

    }
    public void addChecker(Checker checker) {

    }
    public void removeChecker(Checker checker) {

    }
    public List<Checker> getCheckersForExercise(Exercise exercise) {
        return null;
    }
    public void updateChecker(Checker checker) {

    }
    public Checker getCheckerByID(long id) {
        return null;
    }

}




