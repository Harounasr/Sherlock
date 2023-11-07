package de.ssherlock.control.backing;

import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class AdminUserlistBean {

    @Inject
    private Logger logger;
    private List<User> users;

    public AdminUserlistBean() {

    }



}
