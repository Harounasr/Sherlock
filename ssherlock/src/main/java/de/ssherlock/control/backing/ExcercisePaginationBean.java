package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Exercise;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;
@Named
@RequestScoped
public class ExcercisePaginationBean {
    @Inject
    private Logger logger;
    private List<Exercise> exercises;

    public ExcercisePaginationBean() {

    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
