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
 * Managed bean representing the user session in a Jakarta Faces (JF) application.
 * This bean is annotated with {@code @Named} and {@code @SessionScoped}.
 *
 * @author Victor Vollmann
 */
@Named
@SessionScoped
public class AppSession implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to AppSession.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * An Instance of the UserService for user related operations.
     */
    @Inject
    private UserService userService;

    /**
     * Username representing the current user in the session. Is null if not logged in.
     */
    private String username;

    /**
     * Default constructor of AppSession.
     */
    public AppSession() {

    }

    /**
     * Checks if the user is Anonymous.
     *
     * @return true, if the user is Anonymous.
     */
    public synchronized boolean isAnonymous() {
        return username == null;
    }

    /**
     * Checks if the current logged-in user is an Admin.
     *
     * @return true, if the logged-in user is an Admin.
     */
    public synchronized boolean isAdmin() {
        if (username == null) {
            return false;
        } else {
            return getUser().getSystemRole() == SystemRole.ADMINISTRATOR;
        }
    }

    /**
     * Gets the current user in the session.
     *
     * @return The current user.
     */
    public synchronized User getUser() {
        if (username != null) {
            try {
                return userService.getUser(username);
            } catch (BusinessNonExistentUserException e) {
                username = null;
                throw new NoAccessException("User was deleted");
            }
        } else {
            User anonymousUser = new User();
            anonymousUser.setSystemRole(SystemRole.ANONYMOUS);
            return anonymousUser;
        }
    }

    /**
     * Sets the current user in the session and logs relevant information.
     *
     * @param user The user to set.
     */
    public synchronized void setUser(User user) {
        username = user.getUsername();
        logger.log(Level.INFO, "User " + user.getUsername() + " is now set. Course Roles: " + user.getCourseRoles());
    }

    /**
     * Performs necessary cleanup when the user logs out.
     *
     * @return Navigation target.
     */
    public String logout() {
        username = null;
        return "/view/public/login?faces-redirect=true";
    }
}
