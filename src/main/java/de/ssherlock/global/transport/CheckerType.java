package de.ssherlock.global.transport;

/**
 * Describes the possible types of Checkers.
 *
 * @author Victor Vollmann
 */
public enum CheckerType {

  /** Compilation checker. */
  COMPILATION,

  /** Identity checker. */
  IDENTITY,

  /** Line width checker. */
  LINE_WIDTH,

  /** User defined checker. */
  USER_DEFINED
}
