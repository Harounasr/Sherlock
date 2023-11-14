package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class PasswordForgottenBean {

    private final Logger logger;
    private final AppSession appSession;
    private final UserService userService;

    private String emailAdress;

    @Inject
    public PasswordForgottenBean(Logger logger, AppSession appSession, UserService userService) {
        this.logger = logger;
        this.appSession = appSession;
        this.userService = userService;
    }

    public void requestPasswordReset() {
        User user = new User(null, emailAdress, null, null, null, null, null);
        userService.sendPasswordForgottenEmail(user);
    }

    public String navigateToLogin() {
        return "";
    }

    public String getEmaiLAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emaiLAdress) {
        this.emailAdress = emaiLAdress;
    }
}
