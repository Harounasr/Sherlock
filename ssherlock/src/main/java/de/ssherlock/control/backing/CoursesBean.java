package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CoursesBean {

    private String newCourseName;
    @Inject
    Logger logger;

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
    public String select() {
        return "/view/exercise.xhtml";
    }
}
