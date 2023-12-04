package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when a testate does not exist in the database.
 *
 * @author Leon Höfling
 */
public class BusinessNonExistentTestateException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new BusinessNonExistentTestateException and sets the message. */
  public BusinessNonExistentTestateException() {
    super();
  }

  /**
   * Constructs a new BusinessNonExistentTestateException and sets the message.
   *
   * @param message stores the message.
   */
  public BusinessNonExistentTestateException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessNonExistentTestateException and sets the message and the error.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public BusinessNonExistentTestateException(String message, Throwable err) {
    super(message, err);
  }
}
