package de.ssherlock.control.backing;

import de.ssherlock.global.transport.CourseRole;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CourseBean {

    private Logger logger;
    private String name;
    private Map<String, CourseRole> users;
    private List<String> exercises;

    public CourseBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, CourseRole> getUsers() {
        return users;
    }

    public void setUsers(Map<String, CourseRole> users) {
        this.users = users;
    }

    public List<String> getExercises() {
        return exercises;
    }

    public void setExercises(List<String> exercises) {
        this.exercises = exercises;
    }
}
