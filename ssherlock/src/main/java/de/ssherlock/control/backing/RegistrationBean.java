package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Backing bean for the reistration.xhtml facelet.
 */
@Named
@ViewScoped
public class RegistrationBean implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * The service handling user-related operations.
     */
    private final UserService userService;

    /**
     * The user's chosen username.
     */
    private String userName;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's chosen password.
     */
    private String passWord;

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The user's chosen faculty.
     */
    private String faculty;

    /**
     * Constructs a new Registration bean.
     *
     * @param logger The logger for this class (Injected).
     * @param appSession The active session (Injected).
     * @param userService The service for user-based operations (Injected).
     */
    @Inject
    public RegistrationBean(SerializableLogger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    /**
     * Tries to register a new user using the provided information.
     */
    public void register() {
        userService.registerUser(new User(userName, email, firstName, lastName, SystemRole.REGISTERED, new Password("", ""), faculty, null));
    }

    /**
     * Navigates to the login page.
     *
     * @return String representing the navigation outcome.
     */
    public String navigateToLogin() {
        return "";
    }

    /**
     * Sets the user's chosen username.
     *
     * @param userName The chosen username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName The user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName The user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the user's chosen password.
     *
     * @param passWord The chosen password.
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The user's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's chosen faculty.
     *
     * @param faculty The chosen faculty.
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
