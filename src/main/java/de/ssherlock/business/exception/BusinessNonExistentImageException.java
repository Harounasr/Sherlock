package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when image does not exist in the database.
 *
 * @author Leon Höfling
 */
public class BusinessNonExistentImageException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new BusinessNonExistentImageException and sets the message. */
  public BusinessNonExistentImageException() {
    super();
  }

  /**
   * Constructs a new BusinessNonExistentImageException and sets the message.
   *
   * @param message stores the message.
   */
  public BusinessNonExistentImageException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessNonExistentImageException and sets the message and the error.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public BusinessNonExistentImageException(String message, Throwable err) {
    super(message, err);
  }
}
