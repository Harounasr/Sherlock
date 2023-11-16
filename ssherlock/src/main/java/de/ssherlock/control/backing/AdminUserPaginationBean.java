package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

/**
 * Backing bean for the adminUserPagination.xhtml facelet.
 */
@Named
@RequestScoped
public class AdminUserPaginationBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * Active session.
     */
    private final AppSession appSession;

    /**
     * Service that provides user-based actions.
     */
    private final UserService userService;

    /**
     * List of all users.
     */
    private List<User> users;

    /**
     * Constructs an AdminUserPaginationBean.
     *
     * @param logger       The logger used for logging within this class (Injected).
     * @param appSession   The active session (Injected).
     * @param userService  The UserService used for user-related actions (Injected).
     */
    @Inject
    public AdminUserPaginationBean(Logger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    /**
     * Initializes the AdminUserPaginationBean after construction.
     * Sets up necessary components for managing users.
     */
    @PostConstruct
    public void initialize() {
        // Initialization logic goes here, e.g., loading users
    }

    /**
     * Action to redirect the admin to the selected user's profile-facelet.
     * Handles user selection navigation.
     */
    public void selectUser() {
    }
}
