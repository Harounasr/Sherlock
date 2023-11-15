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

@Named
@SessionScoped
public class AppSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerCreator.get(AppSession.class);

    private User user;

    public AppSession() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        logger.log(Level.INFO, "user " + user.username() + " is now set. Course Roles: " + user.courseRoles());
    }
}
