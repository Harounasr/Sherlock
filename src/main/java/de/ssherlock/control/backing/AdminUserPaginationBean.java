package de.ssherlock.control.backing;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.FacultyService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Faculty;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
    private static final int PAGE_SIZE = 10;

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
     * Service that provides faculty-based actions.
     */
    private final FacultyService facultyService;

    /**
     * List of all users.
     */
    private List<User> users;

    /**
     * The selected roles.
     */
    private Map<String, String> selectedRole;

    /**
     * The selected faculties.
     */
    private Map<String, String> selectedFaculty;

    /**
     * Constructs an AdminUserPaginationBean.
     *
     * @param logger         The logger used for logging within this class (Injected).
     * @param appSession     The active session (Injected).
     * @param userService    The UserService used for user-related actions (Injected).
     * @param facultyService The FacultyService used for faculty-related actions (Injected).
     */
    @Inject
    public AdminUserPaginationBean(SerializableLogger logger, AppSession appSession, UserService userService, FacultyService facultyService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.facultyService = facultyService;
    }

    /**
     * Initializes the AdminUserPaginationBean after construction. Sets up necessary components for
     * managing users.
     */
    @PostConstruct
    public void initialize() {
        getPagination().setPageSize(PAGE_SIZE);
        getPagination().setSortBy("username");
        users = userService.getUsers(getPagination());
        getPagination().setLastIndex(users.size() - 1);
        selectedRole = new HashMap<>();
        selectedFaculty = new HashMap<>();
        for (User user : users) {
            selectedRole.put(user.getUsername(), user.getSystemRole().toString());
            selectedFaculty.put(user.getUsername(), user.getFacultyName());
        }
        logger.finest("Initialized AdminUserPaginationBean.");
    }

    /**
     * Changes the role of a selected user in the UI.
     *
     * @param user The user.
     */
    public void changeUserRole(User user) {
        user.setSystemRole(SystemRole.valueOf(selectedRole.get(user.getUsername())));
        try {
            userService.updateUser(user);
            Notification notification = new Notification("Updated system role for user " + user.getUsername(), NotificationType.SUCCESS);
            notification.generateUIMessage();
            logger.finer("Updated system role for user " + user.getUsername() + " (" + user.getSystemRole() + ").");
        } catch (BusinessNonExistentUserException e) {
            Notification notification = new Notification("Update failed", NotificationType.ERROR);
            notification.generateUIMessage();
            logger.log(Level.WARNING, "Updating user " + user.getUsername() + "failed.", e);
        }
    }

    /**
     * Changes the faculty of a selected user in the UI.
     *
     * @param user The user.
     */
    public void changeUserFaculty(User user) {
        user.setFacultyName(selectedFaculty.get(user.getUsername()));
        try {
            userService.updateUser(user);
            Notification notification = new Notification("Updated faculty for user " + user.getUsername(), NotificationType.SUCCESS);
            notification.generateUIMessage();
            logger.finer("Updated faculty for user " + user.getUsername() + " (" + user.getFacultyName() + ").");
        } catch (BusinessNonExistentUserException e) {
            Notification notification = new Notification("Update failed", NotificationType.ERROR);
            notification.generateUIMessage();
            logger.log(Level.WARNING, "Updating user " + user.getUsername() + "failed.", e);
        }
    }

    /**
     * Action to redirect the admin to the selected user's profile-facelet. Handles user selection
     * navigation.
     *
     * @param user The selected user's username.
     * @return The navigation outcome.
     */
    public String selectUser(User user) {
        logger.finest("Selected user " + user.getUsername() + ", redirecting to profile page.");
        return "/view/registered/profile.xhtml?faces-redirect=true&Id=" + user.getUsername();
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

    /**
     * Gets selected role.
     *
     * @return the selected role
     */
    public Map<String, String> getSelectedRole() {
        return selectedRole;
    }

    /**
     * Sets selected role.
     *
     * @param selectedRole the selected role
     */
    public void setSelectedRole(Map<String, String> selectedRole) {
        this.selectedRole = selectedRole;
    }

    /**
     * Gets selected faculty.
     *
     * @return the selected faculty
     */
    public Map<String, String> getSelectedFaculty() {
        return selectedFaculty;
    }

    /**
     * Sets selected faculty.
     *
     * @param selectedFaculty the selected faculty
     */
    public void setSelectedFaculty(Map<String, String> selectedFaculty) {
        this.selectedFaculty = selectedFaculty;
    }

    /**
     * Gets available roles.
     *
     * @return the available roles
     */
    public List<SystemRole> getAvailableRoles() {
        return Arrays.asList(SystemRole.values());
    }

    /**
     * Gets available faculties.
     *
     * @return The available faculties.
     */
    public List<Faculty> getAvailableFaculties() {
        return facultyService.getFaculties();
    }
}
