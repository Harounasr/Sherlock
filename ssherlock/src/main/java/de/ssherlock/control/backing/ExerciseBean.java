package de.ssherlock.control.backing;

import de.ssherlock.business.service.ExerciseService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Map;
import java.util.logging.Logger;
@Named
@RequestScoped
public class ExerciseBean {

    private final Logger logger;
    private final ExerciseService exerciseService;
    private final UserService userService;

    private Map<User, CourseRole> userRoles;

    @Inject
    public ExerciseBean(Logger logger, ExerciseService exerciseService, UserService userService) {
        this.logger = logger;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        toggleVisibility();
    }

    private void toggleVisibility() {

    }

    public Map<User, CourseRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Map<User, CourseRole> userRoles) {
        this.userRoles = userRoles;
    }
}
