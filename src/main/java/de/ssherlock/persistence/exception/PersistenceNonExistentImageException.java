package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Is thrown when an image does not exist in the database.
 *
 * @author Victor Vollmann
 */
public class PersistenceNonExistentImageException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new PersistenceNonExistentImageException and sets the message. */
  public PersistenceNonExistentImageException() {
    super();
  }

  /**
   * Constructs a new PersistenceNonExistentImageException and sets the message.
   *
   * @param message stores the message.
   */
  public PersistenceNonExistentImageException(String message) {
    super(message);
  }

  /**
   * Constructs a new PersistenceNonExistentImageException and sets the message and the error.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public PersistenceNonExistentImageException(String message, Throwable err) {
    super(message, err);
  }
}
