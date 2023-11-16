package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Backing bean for the courseSidebar.xhtml facelet.
 */
@Named
@RequestScoped
public class CourseSidebarBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service to handle User-related actions.
     */
    private final UserService userService;

    /**
     * Constructs a CourseSidebarBean.
     *
     * @param logger     The logger used for logging within this class (Injected).
     * @param appSession The active session (Injected).
     * @param userService The UserService (Injected).
     */
    @Inject
    public CourseSidebarBean(Logger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    /**
     * Initializes the CourseSidebarBean after construction.
     */
    @PostConstruct
    public void initialize() {

    }

    /**
     * Action to load exercises.xhtml into the content.
     */
    public void loadExercises() {

    }

    /**
     * Action to load tutorSelection.xhtml into the content.
     */
    public void loadTutorSelection() {

    }
}
