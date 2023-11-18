package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Course DTO.
 */
public class Course implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The name of the course.
     */
    private String name;

    /**
     * List of all exercises.
     */
    private List<Exercise> exercises;

    /**
     * Instantiates a new Course.
     */
    public Course() {

    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets exercises.
     *
     * @return the exercises
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Sets exercises.
     *
     * @param exercises the exercises
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(exercises, course.exercises);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, exercises);
    }

}
