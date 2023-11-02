package de.ssherlock.control.session;

import de.ssherlock.global.transport.User;

public class Session {

    private User user;

    public Session() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
