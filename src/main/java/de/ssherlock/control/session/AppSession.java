package de.ssherlock.control.session;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.exception.NoAccessException;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * Managed bean representing the user session in a Jakarta Faces (JF) application. This bean is
 * annotated with {@code @Named} and {@code @SessionScoped}.
 *
 * @author Leon Föckersperger
 */
@Named
@SessionScoped
public class AppSession implements Serializable {

    /**
     * The serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The user service.
     */
    private final UserService userService;

    /**
     * The username of the user currently logged in.
     */
    private String username;

    /**
     * Creates a new instance of this class.
     *
     * @param logger      The logger for this class.
     * @param userService The user service.
     */
    @Inject
    public AppSession(SerializableLogger logger, UserService userService) {
        this.logger = logger;
        this.userService = userService;
    }

    /**
     * Checks whether the user is anonymous.
     *
     * @return {@code true} if the user is anonymous, {@code false} otherwise.
     */
    public boolean isAnonymous() {
        return username == null;
    }

    /**
     * Checks if the logged-in user is an administrator.
     *
     * @return {@code true} if the user is an administrator, {@code false} otherwise.
     */
    public synchronized boolean isAdmin() {
        if (username == null) {
            return false;
        } else {
            return getUser().getSystemRole() == SystemRole.ADMINISTRATOR;
        }
    }

    /**
     * Fetches the User object of the currently logged-in user from the Database and returns it.
     * If the user isn't yet set, an anonymous user is returned.
     *
     * @return The User object of the currently logged-in user.
     * @throws NoAccessException If the user was deleted.
     */
    public User getUser() throws NoAccessException {
        if (username != null) {
            try {
                return userService.getUser(username);
            } catch (BusinessNonExistentUserException e) {
                username = null;
                throw new NoAccessException("User was deleted");
            }
        } else {
            return createAnonymousUser();
        }
    }

    /**
     * Sets the user of this session.
     *
     * @param user The user to set.
     */
    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.username = user.getUsername();
        logger.log(Level.INFO, "User " + user.getUsername() + " is now set.");
    }

    /**
     * Logs out the user.
     *
     * @return The path to the login page.
     */

    public String logout() {
        username = null;
        return "/view/public/login?faces-redirect=true";
    }

    /**
     * Creates an anonymous user.
     *
     * @return The anonymous user.
     */
    private User createAnonymousUser() {
        User anonymousUser = new User();
        anonymousUser.setSystemRole(SystemRole.ANONYMOUS);
        return anonymousUser;
    }
}
