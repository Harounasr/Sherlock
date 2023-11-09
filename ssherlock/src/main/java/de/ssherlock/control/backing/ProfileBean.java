package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.SystemRole;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class ProfileBean {

    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private UserService userService;

    private String newPasswordOne;
    private String newPasswordTwo;
    private String faculty;
    private SystemRole systemRole;

    public ProfileBean() {

    }

    @PostConstruct
    public void initialize() {

    }

    public void submitPasswordChange() {

    }

    public void submitFacultyChange() {

    }

    public void submitSystemRoleChange() {

    }

    public void deleteAccount() {

    }

    private void toggleElementVisibility() {

    }

    public String getNewPasswordOne() {
        return newPasswordOne;
    }

    public void setNewPasswordOne(String newPasswordOne) {
        this.newPasswordOne = newPasswordOne;
    }

    public String getNewPasswordTwo() {
        return newPasswordTwo;
    }

    public void setNewPasswordTwo(String newPasswordTwo) {
        this.newPasswordTwo = newPasswordTwo;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }
}
