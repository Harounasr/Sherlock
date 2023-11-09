package de.ssherlock.control.backing;

import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class RegistrationBean {

    @Inject
    private Logger logger;

    @Inject
    private AppSession appSession;

    @Inject
    private UserService userService;

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String faculty;

    public RegistrationBean() {}

    public void register() {

    }

    public String navigateToLogin() {
        return "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurName() {
        return firstName;
    }

    public void setSurName(String surName) {
        this.firstName = surName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
