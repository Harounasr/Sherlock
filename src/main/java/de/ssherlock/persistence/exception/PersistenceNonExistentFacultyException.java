package de.ssherlock.persistence.exception;

import java.io.Serial;

/**
 * Exception indicating an attempt to interact with a non-existent Faculty in the database.
 *
 * @author Leon Höfling
 */
public class PersistenceNonExistentFacultyException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new PersistenceNonExistentFacultyException. */
  public PersistenceNonExistentFacultyException() {
    super();
  }

  /**
   * Constructs a new PersistenceNonExistentFacultyException with the specified detail message.
   *
   * @param msg The detail message.
   */
  public PersistenceNonExistentFacultyException(String msg) {
    super(msg);
  }

  /**
   * Constructs a new PersistenceNonExistentFacultyException with the specified detail message and
   * cause.
   *
   * @param msg The detail message.
   * @param cause The cause (which is saved for later retrieval by the getCause() method).
   */
  public PersistenceNonExistentFacultyException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
