package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Backing bean for courseUserPagination.xhtml facelet.
 *
 * @author Victor Vollmann
 */
@Named
@ViewScoped
@SuppressWarnings("DesignForExtension")
public class CourseUserPaginationBean extends AbstractPaginationBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The page size for the pagination.
     */
    private static final int PAGE_SIZE = 5;

    /**
     * Logger for this class.
     */
    private final SerializableLogger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service handling user-related operations.
     */
    private final UserService userService;

    /**
     * The parent backing bean.
     */
    private final CourseBean courseBean;

    /**
     * The list of users.
     */
    private List<User> users;

    /**
     * The selected roles.
     */
    private Map<String, String> selectedRole;

    /**
     * Constructor for CourseUserPaginationBean.
     *
     * @param logger        The logger for logging purposes (Injected).
     * @param appSession    The active session (Injected).
     * @param userService   The service handling user-related operations (Injected).
     * @param courseBean    The parent backing bean (Injected).
     */
    @Inject
    public CourseUserPaginationBean(
            SerializableLogger logger, AppSession appSession, UserService userService, CourseBean courseBean) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
        this.courseBean = courseBean;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        getPagination().setSortBy("courserole");
        getPagination().setPageSize(PAGE_SIZE);
        users = userService.getUsers(getPagination());
        getPagination().setLastIndex(users.size() - 1);
        selectedRole = new HashMap<>();
        for (User user : users) {
            selectedRole.put(user.getUsername(), getCourseRoleForUser(user).toString());
        }
        logger.finest("Initialized CourseUserPaginationBean.");
    }

    /**
     * Changes the role of a selected user in the UI.
     *
     * @param user The user.
     */
    public void changeUserRole(User user) {
        CourseRole courseRole = CourseRole.valueOf(selectedRole.get(user.getUsername()));
        userService.updateCourseRole(user, courseBean.getCourse(), courseRole);
        Notification notification = new Notification("Changed Course role for user " + user.getUsername(), NotificationType.SUCCESS);
        notification.generateUIMessage();
        logger.finer("Updated system role for user " + user.getUsername() + " (" + courseRole + ").");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData() {
        users = userService.getUsers(getPagination());
        for (User user : users) {
            selectedRole.put(user.getUsername(), getCourseRoleForUser(user).toString());
        }
        if (Objects.equals(getPagination().getSortBy(), "courserole")) {
            Comparator<User> comparator = Comparator.comparing(user -> selectedRole.get(user.getUsername()).toLowerCase());
            Stream<User> userStream = users.stream();
            userStream = getPagination().isSortAscending() ? userStream.sorted(comparator) : userStream.sorted(comparator.reversed());
            users = userStream.collect(Collectors.toList());
        }
    }

    /**
     * Gets the course role for the current user in this course.
     *
     * @param user The user.
     * @return The course role.
     */
    public CourseRole getCourseRoleForUser(User user) {
        long courseId = courseBean.getCourse().getId();
        return user.getCourseRoles().getOrDefault(courseId, CourseRole.NONE);
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
     * Gets selected role.
     *
     * @return the selected role
     */
    public Map<String, String> getSelectedRole() {
        return selectedRole;
    }

    /**
     * Gets available roles.
     *
     * @return the available roles
     */
    public List<CourseRole> getAvailableRoles() {
        return Arrays.asList(CourseRole.values());
    }
}
