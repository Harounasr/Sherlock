package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.Exercise;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.sql.Date;
import java.util.Map;
import java.util.logging.Logger;

public class ExerciseDescriptionBean {

    @Inject
    private Logger logger;

    @Inject
    private AppSession appSession;

    @Inject
    private UserService userService;

    private Map<User, CourseRole> userRoles;
    private Exercise exercise;

    private Date reccomendedDate;
    private Date mandatoryDate;
    private Date publishDate;

    // TODO DTO for ExerciseDescriptionImage

    public ExerciseDescriptionBean() {

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
