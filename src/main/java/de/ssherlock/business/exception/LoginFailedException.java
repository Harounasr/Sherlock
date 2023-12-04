package de.ssherlock.business.exception;

import java.io.Serial;

/**
 * Is thrown when the login did not work.
 *
 * @author Leon Höfling
 */
public class LoginFailedException extends Exception {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** Constructs a new LoginFailedException and sets the message. */
  public LoginFailedException() {
    super();
  }

  /**
   * Constructs a new LoginFailedException and sets the message.
   *
   * @param message stores the message.
   */
  public LoginFailedException(String message) {
    super(message);
  }

  /**
   * Constructs a new LoginFailedException and sets the message.
   *
   * @param message stores the message.
   * @param err the exception which is going to be wrapped.
   */
  public LoginFailedException(String message, Throwable err) {
    super(message, err);
  }
}
