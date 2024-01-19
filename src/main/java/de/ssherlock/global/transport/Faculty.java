package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Faculty DTO.
 *
 * @author Leon Höfling
 */
public class Faculty implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The name of the course. */
  private String name;

  /** Instantiates a new Course. */
  public Faculty() {}

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
     * returns the string.
     * @return String of the name.
     */
  public String toString() {
    return name;
  }
}
