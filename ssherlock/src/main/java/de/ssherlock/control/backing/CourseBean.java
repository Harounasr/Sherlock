package de.ssherlock.control.backing;

import de.ssherlock.business.service.CourseService;
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

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
@Named
@RequestScoped
public class CourseBean {

    private Logger logger;
    private AppSession appSession;
    private CourseService courseService;
    private ExerciseService exerciseService;
    private UserService userService;

    private Map<User, CourseRole> userRoles;

    @Inject
    public CourseBean(Logger logger, AppSession appSession, CourseService courseService, ExerciseService exerciseService, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.courseService = courseService;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
    }

    public Map<User, CourseRole> getUserRoles() {
        return userRoles;
    }

    public void setUsers(Map<User, CourseRole> users) {
        this.userRoles = users;
    }

}
