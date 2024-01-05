package de.ssherlock.control.backing;

import de.ssherlock.business.service.FacultyService;
import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.business.util.PasswordHashing;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.Faculty;
import de.ssherlock.global.transport.Password;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Base64;
import java.util.List;
import java.util.logging.Level;

/**
 * Backing bean for the registration.xhtml facelet.
 *
 * @author Leon Höfling
 */
@Named
@RequestScoped
public class RegistrationBean {

    /**
     * The logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The service handling user-related operations.
     */
    private final UserService userService;

    /**
     * The service handling faculty-related operations.
     */
    private final FacultyService facultyService;

    /**
     * The system service.
     */
    private final SystemService systemService;

    /**
     * The user that is to be registered.
     */
    private User user;

    /**
     * The unhashed password entered by the user.
     */
    private String unhashedPassword;

    /**
     * The list of faculties.
     */
    private List<Faculty> faculties;

    /**
     * Constructs a new Registration bean.
     *
     * @param logger         The logger for this class (Injected).
     * @param userService    The service for user-based operations (Injected).
     * @param systemService  The System Service.
     * @param facultyService The service for faculty-based operations (Injected).
     */
    @Inject
    public RegistrationBean(
            SerializableLogger logger,
            UserService userService,
            SystemService systemService,
            FacultyService facultyService) {
        this.logger = logger;
        this.userService = userService;
        this.facultyService = facultyService;
        this.systemService = systemService;
        this.user = new User();
        this.faculties = this.facultyService.getFaculties();
    }

    /**
     * Tries to register a new user using the provided information.
     */
    public void register() {
        Password password = PasswordHashing.hashPassword(unhashedPassword);
        user.setPassword(password);
        userService.registerUser(user);
    }

    /**
     * Navigates to the login page.
     *
     * @return String representing the navigation outcome.
     */
    public String navigateToLogin() {
        logger.log(Level.INFO, "Login");
        return "/view/public/login.xhtml";
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
     * Gets unhashed password.
     *
     * @return the unhashed password
     */
    public String getUnhashedPassword() {
        return unhashedPassword;
    }

    /**
     * Sets unhashed password.
     *
     * @param unhashedPassword the unhashed password
     */
    public void setUnhashedPassword(String unhashedPassword) {
        this.unhashedPassword = unhashedPassword;
    }

    /**
     * Gets the faculties.
     *
     * @return The faculties.
     */
    public List<Faculty> getFaculties() {
        return faculties;
    }

    /**
     * Sets the faculties.
     *
     * @param faculties The faculties.
     */
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    /**
     * Gets the logo in base 64.
     *
     * @return The logo.
     */
    public String getLogoBase64() {
        return Base64.getEncoder().encodeToString(systemService.getSystemSettings().getLogo());
    }
}
