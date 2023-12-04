package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the profile.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@ViewScoped
public class ProfileBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for logging events.
     */
    private final SerializableLogger logger;

    /**
     * The active session of the user.
     */
    private final AppSession appSession;

    /**
     * The service for user-related operations.
     */
    private final UserService userService;

    /**
     * The user of the profile.
     */
    private User user;

    /**
     * The first new password for change.
     */
    private String newPasswordOne;

    /**
     * The second new password for change.
     */
    private String newPasswordTwo;

    /**
     * Constructor for ProfileBean.
     *
     * @param logger      The logger for logging events.
     * @param appSession  The active session of the user.
     * @param userService The service for user-related operations.
     */
    @Inject
    public ProfileBean(SerializableLogger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {

    }

    /**
     * Submits the password change request.
     */
    public void submitPasswordChange() {

    }

    /**
     * Submits the faculty change request.
     */
    public void submitFacultyChange() {

    }

    /**
     * Submits the system role change request.
     */
    public void submitSystemRoleChange() {

    }

    /**
     * Deletes the user account.
     */
    public void deleteAccount() {

    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets new password one.
     *
     * @return the new password one
     */
    public String getNewPasswordOne() {
        return newPasswordOne;
    }

    /**
     * Sets the first new password for change.
     *
     * @param newPasswordOne The first new password for change.
     */
    public void setNewPasswordOne(String newPasswordOne) {
        this.newPasswordOne = newPasswordOne;
    }

    /**
     * Gets new password two.
     *
     * @return the new password two
     */
    public String getNewPasswordTwo() {
        return newPasswordTwo;
    }

    /**
     * Sets the second new password for change.
     *
     * @param newPasswordTwo The second new password for change.
     */
    public void setNewPasswordTwo(String newPasswordTwo) {
        this.newPasswordTwo = newPasswordTwo;
    }

}
