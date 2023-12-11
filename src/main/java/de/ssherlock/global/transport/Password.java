package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Password DTO.
 *
 * @author Leon Höfling
 */
public class Password implements Serializable {

  /** Serial Version UID. */
  @Serial private static final long serialVersionUID = 1L;

  /** The hashed password. */
  private String hash;

  /** The salt that was used. */
  private String salt;

  /** Instantiates a new Password. */
  public Password() {}

  /**
   * Gets hash.
   *
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * Sets hash.
   *
   * @param hash the hash
   */
  public void setHash(String hash) {
    this.hash = hash;
  }

  /**
   * Gets salt.
   *
   * @return the salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * Sets salt.
   *
   * @param salt the salt
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Password password = (Password) o;
    return Objects.equals(hash, password.hash) && Objects.equals(salt, password.salt);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(hash, salt);
  }
}
