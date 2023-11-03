package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Course;

import java.util.List;
import java.util.logging.Logger;

public class CoursePaginationBean {

    private Logger logger;
    private List<Course> courses;
    public CoursePaginationBean() {

    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
