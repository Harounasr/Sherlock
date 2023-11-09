package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CoursesBean {

    private String newCourseName;

    public CoursesBean() {

    }

    public String addCourse(ActionEvent e) {
        return "";
    }

    public String getNewCourseName() {
        return newCourseName;
    }

    public void setNewCourseName(String newCourseName) {
        this.newCourseName = newCourseName;
    }
}
