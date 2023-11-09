package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class PasswordForgottenBean {

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    //@Inject
    //private MailService mailService;

    private String email;

    public PasswordForgottenBean() {

    }

    public void requestPasswordReset() {

    }

    public String navigateToLogin() {
        return "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
