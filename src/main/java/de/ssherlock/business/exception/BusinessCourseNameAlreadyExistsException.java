package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when the CourseName already exists in the Database.
 *
 * @author Victor Vollmann
 */
public class BusinessCourseNameAlreadyExistsException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new BusinessCourseNameAlreadyExistsException. */
  public BusinessCourseNameAlreadyExistsException() {
    super();
  }

  /**
   * Constructs a new BusinessCourseNameAlreadyExistsException and sets the Message.
   *
   * @param message stores the message.
   */
  public BusinessCourseNameAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessCourseNameAlreadyExistsException and sets the Message.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public BusinessCourseNameAlreadyExistsException(String message, Throwable err) {
    super(message, err);
  }
}
