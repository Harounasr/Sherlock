package de.ssherlock.global.transport;

/**
 * Describes the possible roles a user can have in the context of the system.
 *
 * @author Leon Höfling
 */
public enum SystemRole {

  /** A user that is registered, but not verified. */
  NOT_REGISTERED,

  /** A user that is registered on the platform. */
  REGISTERED,

  /** A teacher on the platform. */
  TEACHER,

  /** An administrator on the platform. */
  ADMINISTRATOR,

  /** Role of a not registered user. */
  ANONYMOUS
}
