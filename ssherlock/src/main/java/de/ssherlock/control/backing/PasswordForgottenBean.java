package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class PasswordForgottenBean {

    @Inject
    private Logger logger;
    private String email;

    public PasswordForgottenBean() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
