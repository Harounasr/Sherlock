package de.ssherlock.business.service;

import de.ssherlock.global.transport.Course;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.logging.Logger;

@Named
@Dependent
public class ExerciseService {
    @Inject
    private Logger logger;
    
    public ExerciseService() {
        
    }   
    
    public List<Exercise> getExercises(Course course) {
        return null;
    }
    public void updateExercise(Exercise exercise) {

    }
    public void addExercise(Exercise exercise) {

    }
    public void removeExercise(Exercise exercise) {

    }
    public Exercise getExercise(long id) {
        return null;
    }
    public String addExerciseDescriptionImage(ExerciseDescriptionImage exerciseDescriptionImage) {
        return null;
    }

}





