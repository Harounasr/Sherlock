package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.Date;
import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class ExerciseDescriptionBean {

    private final Logger logger;
    private final AppSession appSession;
    private final ExerciseService exerciseService;
    private final UserService userService;

    private Map<User, CourseRole> userRoles;
    private Exercise exercise;

    private Date reccomendedDate;
    private Date mandatoryDate;
    private Date publishDate;

    @Inject
    public ExerciseDescriptionBean(Logger logger, AppSession appSession, ExerciseService exerciseService, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        exercise = BackingBeanInitializationUtils.getActiveExercise();
        toggleVisibility();
    }

    public void startEditMode() {

    }

    public void submitChanges() {

    }

    public void uploadImage() {

    }


    private void toggleVisibility() {

    }

    public Date getReccomendedDate() {
        return reccomendedDate;
    }

    public void setReccomendedDate(Date reccomendedDate) {
        this.reccomendedDate = reccomendedDate;
    }

    public Date getMandatoryDate() {
        return mandatoryDate;
    }

    public void setMandatoryDate(Date mandatoryDate) {
        this.mandatoryDate = mandatoryDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
