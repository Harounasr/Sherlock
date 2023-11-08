package de.ssherlock.control.backing;

import de.ssherlock.global.transport.SystemRole;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class UserBean {
    @Inject
    private Logger logger;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String facultyName;
    private SystemRole systemRole;

    public UserBean() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public SystemRole getCourseRole() {
        return systemRole;
    }

    public void setCourseRole(SystemRole courseRole) {
        this.systemRole = courseRole;
    }
}
