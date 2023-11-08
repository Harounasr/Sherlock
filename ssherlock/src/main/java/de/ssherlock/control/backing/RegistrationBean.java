package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class RegistrationBean {

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    @Inject
    private Logger logger;

    public RegistrationBean() {}

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
}
