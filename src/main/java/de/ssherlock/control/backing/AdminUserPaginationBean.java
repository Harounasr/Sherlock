package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Backing bean for the adminUserPagination.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
public class AdminUserPaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Page size of the pagination.
     */
    private static final int PAGE_SIZE = 20;

    /**
     * The {@code Logger} instance to be used in this class.
     */
    private final SerializableLogger logger;

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
     * @param logger      The logger used for logging within this class (Injected).
     * @param appSession  The active session (Injected).
     * @param userService The UserService used for user-related actions (Injected).
     */
    @Inject
    public AdminUserPaginationBean(SerializableLogger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    /**
     * Initializes the AdminUserPaginationBean after construction. Sets up necessary components for
     * managing users.
     */
    @PostConstruct
    public void initialize() {
        loadData();
        getPagination().setPageSize(PAGE_SIZE);
        getPagination().setLastIndex(users.size() - 1);
    }

    /**
     * Action to redirect the admin to the selected user's profile-facelet. Handles user selection
     * navigation.
     *
     * @param username The selected user's username.
     * @return The navigation outcome.
     */
    public String selectUser(String username) {
        return "";
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Sets users.
     *
     * @param users the users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        users = userService.getUsers(getPagination());
    }
}
