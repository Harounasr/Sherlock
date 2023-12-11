package de.ssherlock.business.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.ssherlock.global.transport.Password;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link PasswordHashing}.
 *
 * @author Leon Höfling
 */
public class PasswordHashingIT {

  /** Tests the hashing of a password without a salt. */
  @Test
  void testGetHashedPasswordWithRandomSalt() {
    String password = "testPassword";
    Password hashedPassword = PasswordHashing.hashPassword(password);

    assertNotNull(hashedPassword);
    assertNotNull(hashedPassword.getHash());
    assertNotNull(hashedPassword.getSalt());
    assertNotEquals(password, hashedPassword.getHash());
  }

  /** Tests the hashing of a password with a given salt. */
  @Test
  void testGetHashedPasswordWithGivenSalt() {
    String password = "testPassword";
    String salt = "randomSalt";

    String hashedPassword = PasswordHashing.hashPassword(password, salt).getHash();

    assertNotNull(hashedPassword);
    assertNotEquals(password, hashedPassword);
  }
}
