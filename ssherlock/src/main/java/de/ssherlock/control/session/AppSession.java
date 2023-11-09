package de.ssherlock.control.session;

import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;

@Named
@SessionScoped
public class AppSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private User user;

    public AppSession() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
