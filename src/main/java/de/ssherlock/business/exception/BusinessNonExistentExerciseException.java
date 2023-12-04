package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when an Exercise is not in the database.
 *
 * @author Victor Vollmann
 */
public class BusinessNonExistentExerciseException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new BusinessNonExistentExerciseException and sets the message. */
  public BusinessNonExistentExerciseException() {
    super();
  }

  /**
   * Constructs a new BusinessNonExistentExerciseException and sets the message.
   *
   * @param message stores the message.
   */
  public BusinessNonExistentExerciseException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessNonExistentExerciseException and sets the message and the error.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public BusinessNonExistentExerciseException(String message, Throwable err) {
    super(message, err);
  }
}
