package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
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
public class CourseSidebarBean {
    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private UserService userService;

    private Map<User, CourseRole> userRoles;

    public CourseSidebarBean() {

    }

    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
        toggleVisibility();
    }

    public void loadExercises() {

    }

    public void loadTutorSelection() {

    }

    private void toggleVisibility() {

    }

}
