package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.Exercise;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;
@Named
@RequestScoped
public class ExercisePaginationBean {

    private final Logger logger;
    private final AppSession appSession;
    private final ExerciseService exerciseService;

    private List<Exercise> exercises;

    @Inject
    public ExercisePaginationBean(Logger logger, AppSession appSession, ExerciseService exerciseService) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
    }

    @PostConstruct
    public void initialize() {
        exercises = BackingBeanInitializationUtils.loadExercises(null, exerciseService);
        toggleVisibility();
    }

    public String navigateToExercise(ActionEvent e) {
        return "";
    }

    private void toggleVisibility() {

    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
