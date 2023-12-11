package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when a Faculty is not in the database.
 *
 * @author Leon Höfling
 */
public class BusinessNonExistentFacultyException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new BusinessNonExistentFacultyException and sets the message. */
  public BusinessNonExistentFacultyException() {
    super();
  }

  /**
   * Constructs a new BusinessNonExistentFacultyException and sets the message.
   *
   * @param message stores the message.
   */
  public BusinessNonExistentFacultyException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessNonExistentFacultyException and sets the message and the error.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public BusinessNonExistentFacultyException(String message, Throwable err) {
    super(message, err);
  }
}
