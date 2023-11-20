package de.ssherlock.control.session;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.exception.NoAccessException;
import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.management.relation.Role;
import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Managed bean representing the user session in a Jakarta Faces (JF) application.
 * This bean is annotated with {@code @Named} and {@code @SessionScoped}.
 *
 * @author Leon Föckersperger
 */
@Named
@SessionScoped
public class AppSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging messages related to AppSession.
     */
    private final Logger logger = LoggerCreator.get(AppSession.class);

    /**
     * Username representing the current user in the session. Is null if not logged in.
     */
    private String username = null;

    /**
     * An Instance of the UserService.
     */
    @Inject
    private UserService userService;


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
                return userService.fetchUserByUsername(username);
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
        return "/view/login?faces-redirect=true";
    }
}
