package de.ssherlock.global.transport;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Exercise DTO.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
public class Exercise implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The id of the exercise
     */
    private long id;

    /**
     * The exercise name.
     */
    private String name;

    /**
     * The publishing date of the exercise.
     */
    private Date publishDate;

    /**
     * The recommended deadline for submissions.
     */
    private Date recommendedDeadline;

    /**
     * The obligatory deadline for submissions.
     */
    private Date obligatoryDeadline;

    /**
     * The exercise description in HTML format.
     */
    private String description;

    /**
     * The name of the course associated with this exercise.
     */
    private String courseName;

    /**
     * Instantiates a new Exercise.
     */
    public Exercise() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
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
     * Gets publish date.
     *
     * @return the publishing date
     */
    public java.sql.Date getPublishDate() {
        return publishDate;
    }

    /**
     * Sets publish date.
     *
     * @param publishDate the publishing date
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * Gets recommended deadline.
     *
     * @return the recommended deadline
     */
    public Date getRecommendedDeadline() {
        return recommendedDeadline;
    }

    /**
     * Sets recommended deadline.
     *
     * @param recommendedDeadline the recommended deadline
     */
    public void setRecommendedDeadline(Date recommendedDeadline) {
        this.recommendedDeadline = recommendedDeadline;
    }

    /**
     * Gets obligatory deadline.
     *
     * @return the obligatory deadline
     */
    public Date getObligatoryDeadline() {
        return obligatoryDeadline;
    }

    /**
     * Sets obligatory deadline.
     *
     * @param obligatoryDeadline the obligatory deadline
     */
    public void setObligatoryDeadline(Date obligatoryDeadline) {
        this.obligatoryDeadline = obligatoryDeadline;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets course name.
     *
     * @return the course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets course name.
     *
     * @param courseName the course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        return id == exercise.id &&
               Objects.equals(name, exercise.name) &&
               Objects.equals(publishDate, exercise.publishDate) &&
               Objects.equals(recommendedDeadline, exercise.recommendedDeadline) &&
               Objects.equals(obligatoryDeadline, exercise.obligatoryDeadline);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, publishDate, recommendedDeadline, obligatoryDeadline);
    }
}
