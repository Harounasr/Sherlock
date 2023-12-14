package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Represents an Exercise DTO.
 *
 * @author Victor Vollmann
 */
public class Exercise implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The id of the exercise. */
  private long id;

  /** The exercise name. */
  private String name;

  /** The publishing date of the exercise. */
  private Timestamp publishDate;

  /** The recommended deadline for submissions. */
  private Timestamp recommendedDeadline;

  /** The obligatory deadline for submissions. */
  private Timestamp obligatoryDeadline;

  /** The exercise description in HTML format. */
  private String description;

  /** The name of the course associated with this exercise. */
  private long courseId;

  /** Instantiates a new Exercise. */
  public Exercise() {}

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
  public Timestamp getPublishDate() {
    return publishDate;
  }

  /**
   * Sets publish date.
   *
   * @param publishDate the publishing date
   */
  public void setPublishDate(Timestamp publishDate) {
    this.publishDate = publishDate;
  }

  /**
   * Gets recommended deadline.
   *
   * @return the recommended deadline
   */
  public Timestamp getRecommendedDeadline() {
    return recommendedDeadline;
  }

  /**
   * Sets recommended deadline.
   *
   * @param recommendedDeadline the recommended deadline
   */
  public void setRecommendedDeadline(Timestamp recommendedDeadline) {
    this.recommendedDeadline = recommendedDeadline;
  }

  /**
   * Gets obligatory deadline.
   *
   * @return the obligatory deadline
   */
  public Timestamp getObligatoryDeadline() {
    return obligatoryDeadline;
  }

  /**
   * Sets obligatory deadline.
   *
   * @param obligatoryDeadline the obligatory deadline
   */
  public void setObligatoryDeadline(Timestamp obligatoryDeadline) {
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
   * Gets course id.
   *
   * @return the course id
   */
  public long getCourseId() {
    return courseId;
  }

  /**
   * Sets course id.
   *
   * @param courseId the course id
   */
  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Exercise exercise = (Exercise) o;
    return id == exercise.id
        && Objects.equals(name, exercise.name)
        && Objects.equals(publishDate, exercise.publishDate)
        && Objects.equals(recommendedDeadline, exercise.recommendedDeadline)
        && Objects.equals(obligatoryDeadline, exercise.obligatoryDeadline);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(id, name, publishDate, recommendedDeadline, obligatoryDeadline);
  }
}
