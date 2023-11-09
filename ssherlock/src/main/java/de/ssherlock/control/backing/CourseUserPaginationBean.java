package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.CourseRole;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Map;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CourseUserPaginationBean {

    @Inject
    private Logger logger;
    @Inject
    private UserService userService;

    private Map<User, CourseRole> userRoles;
    private String searchString;

    public CourseUserPaginationBean() {

    }

    /**
     * Initializes the bean.
     */
    @PostConstruct
    public void initialize() {
        userRoles = BackingBeanInitializationUtils.loadCourseRoles(null, userService);
    }

    /**
     * Changes the role of a user selected in the ui.
     *
     * @param e the ActionEvent
     */
    public void changeUserRole(ActionEvent e) {

    }

    public void handleSearch() {

    }

    public Map<User, CourseRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Map<User, CourseRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
