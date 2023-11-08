package de.ssherlock.control.backing;

import de.ssherlock.global.transport.SystemRole;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
@Named
@RequestScoped
public class UserPaginationBean {
    @Inject
    private Logger logger;
    private Map<String, SystemRole> users;

    public UserPaginationBean() {

    }

    public Map<String, SystemRole> getUsers() {
        return users;
    }

    public void setUsers(Map<String, SystemRole> users) {
        this.users = users;
    }
}
