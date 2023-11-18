package de.ssherlock.control.session;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Managed bean representing the user session in a JavaServer Faces (JSF) application.
 * This bean is annotated with {@code @Named} and {@code @SessionScoped}.
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
     * User object representing the current user in the session.
     */
    private User user;

    /**
     * Default constructor of AppSession.
     */
    public AppSession() {

    }

    /**
     * Gets the current user in the session.
     *
     * @return The current user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the current user in the session and logs relevant information.
     *
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
        logger.log(Level.INFO, "User " + user.getUsername() + " is now set. Course Roles: " + user.getCourseRoles());
    }

    /**
     * Performs necessary cleanup when the user logs out.
     */
    public void logout() {
        user = null;
    }
}
