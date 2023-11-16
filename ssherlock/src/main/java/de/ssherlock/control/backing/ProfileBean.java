package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemRole;
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
     * The first new password for change.
     */
    private String newPasswordOne;

    /**
     * The second new password for change.
     */
    private String newPasswordTwo;

    /**
     * The faculty information.
     */
    private String faculty;

    /**
     * The system role for the user.
     */
    private SystemRole systemRole;

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
     * Sets the first new password for change.
     *
     * @param newPasswordOne The first new password for change.
     */
    public void setNewPasswordOne(String newPasswordOne) {
        this.newPasswordOne = newPasswordOne;
    }

    /**
     * Sets the second new password for change.
     *
     * @param newPasswordTwo The second new password for change.
     */
    public void setNewPasswordTwo(String newPasswordTwo) {
        this.newPasswordTwo = newPasswordTwo;
    }

    /**
     * Sets the faculty.
     *
     * @param faculty The faculty.
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * Sets the system role for the user.
     *
     * @param systemRole The system role for the user.
     */
    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }
}
