package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class AdminUserPaginationBean {

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    UserService userService;

    private List<User> users;

    public AdminUserPaginationBean() {

    }

    /**
     * Sets up the bean.
     */
    @PostConstruct
    public void initialize() {
        users = BackingBeanInitializationUtils.loadUsers(userService);
    }

    /**
     * Redirects admin to user bean.
     */
    public void selectUser() {

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
