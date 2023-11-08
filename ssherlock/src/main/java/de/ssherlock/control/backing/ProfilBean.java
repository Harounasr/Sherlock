package de.ssherlock.control.backing;

import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class ProfilBean {

    @Inject
    private Logger logger;
    private User user;

    public ProfilBean() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
