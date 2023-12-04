package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when the Exercise name already exists in the database.
 *
 * @author Victor Vollmann
 */
public class BusinessExerciseNameAlreadyExistsException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new BusinessExerciseNameAlreadyExistsException. */
  public BusinessExerciseNameAlreadyExistsException() {
    super();
  }

  /**
   * Constructs a new BusinessExerciseNameAlreadyExistsException and sets the message.
   *
   * @param message stores the message.
   */
  public BusinessExerciseNameAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructs a new BusinessExerciseNameAlreadyExistsException and sets the message.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public BusinessExerciseNameAlreadyExistsException(String message, Throwable err) {
    super(message, err);
  }
}
