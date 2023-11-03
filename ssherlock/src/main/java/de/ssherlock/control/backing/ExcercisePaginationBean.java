package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Exercise;

import java.util.List;
import java.util.logging.Logger;

public class ExcercisePaginationBean {

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
