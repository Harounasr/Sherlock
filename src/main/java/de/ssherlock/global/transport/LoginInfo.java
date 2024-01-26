package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a LoginInfo DTO.
 *
 * @author Leon Höfling
 */
public class LoginInfo implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The username of the user. */
  private String username;

  /** The entered password. */
  private String unhashedPassword;

  /** Instantiates a new Login info. */
  public LoginInfo() {}

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getUnhashedPassword() {
    return unhashedPassword;
  }

  /**
   * Sets password.
   *
   * @param unhashedPassword the password
   */
  public void setUnhashedPassword(String unhashedPassword) {
    this.unhashedPassword = unhashedPassword;
  }
}
