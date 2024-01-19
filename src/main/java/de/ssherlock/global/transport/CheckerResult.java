package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a CheckerResult DTO.
 *
 * @author Leon Höfling
 */
public class CheckerResult implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The Checker associated with the result. */
  private Checker checker;

  /** Whether the checker was passed. */
  private boolean passed;

  /** The stacktrace of the result. */
  private String stackTrace;

  /** Instantiates a new Checker result. */
  public CheckerResult() {}

  /**
   * Gets checker.
   *
   * @return the checker
   */
  public Checker getChecker() {
    return checker;
  }

  /**
   * Sets checker.
   *
   * @param checker the checker
   */
  public void setChecker(Checker checker) {
    this.checker = checker;
  }

  /**
   * Gets passed.
   *
   * @return the boolean
   */
  public boolean isPassed() {
    return passed;
  }

  /**
   * Sets passed.
   *
   * @param passed the passed
   */
  public void setPassed(boolean passed) {
    this.passed = passed;
  }

  /**
   * Gets stack trace.
   *
   * @return the stack trace
   */
  public String getStackTrace() {
    return stackTrace;
  }

  /**
   * Sets stack trace.
   *
   * @param stackTrace the stack trace
   */
  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }
}
