package de.ssherlock.control.backing;

import de.ssherlock.global.transport.SystemRole;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class UserPaginationBean {

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
