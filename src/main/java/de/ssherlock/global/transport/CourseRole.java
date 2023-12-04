package de.ssherlock.global.transport;

/**
 * Describes the possible roles a user can have in the context of a course.
 *
 * @author Victor Vollmann
 */
public enum CourseRole {

  /** Not a member of the course. */
  NONE,

  /** Normal member of the course. */
  MEMBER,

  /** Tutor in the course. */
  TUTOR,

  /** Teacher of the course. */
  TEACHER
}
