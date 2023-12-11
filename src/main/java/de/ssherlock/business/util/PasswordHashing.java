package de.ssherlock.business.util;

import de.ssherlock.global.transport.Password;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing using a secure hash algorithm and salt.
 *
 * @author Leon Höfling
 */
public final class PasswordHashing {

  /** The algorithm to use for hashing. */
  private static final String ALGORITHM = "SHA-512";

  /** The size of the salt. */
  private static final int SALT_SIZE = 16;

  /** For random byte generation. */
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  /** Default constructor. */
  private PasswordHashing() {}

  /**
   * Hashes the provided password.
   *
   * @param password The password to be hashed.
   * @return The hashed password.
   */
  public static Password hashPassword(String password) {
    try {
      byte[] salt = generateSalt();
      // Combine the password and salt
      byte[] saltedPassword =
          combinePasswordAndSalt(password.getBytes(StandardCharsets.UTF_8), salt);

      // Create a MessageDigest object for SHA-512
      MessageDigest md = MessageDigest.getInstance(ALGORITHM);

      // Update the digest with the salted password
      md.update(saltedPassword);

      // Get the hash bytes
      byte[] hashedBytes = md.digest();

      // Convert the salt and hash to a base64-encoded string
      String saltBase64 = Base64.getEncoder().encodeToString(salt);
      String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedBytes);

      // Combine the salt and hash for storage
      Password p = new Password();
      p.setHash(hashedPasswordBase64);
      p.setSalt(saltBase64);
      return p;
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

  /**
   * Hashes the provided password using the specified salt.
   *
   * @param password The password to be hashed.
   * @param passwordSalt The password will be hashed with this salt.
   * @return The hashed password.
   */
  public static Password hashPassword(String password, String passwordSalt) {
    try {
      byte[] salt = Base64.getDecoder().decode(passwordSalt);

      // Combine the password and salt
      byte[] saltedPassword =
          combinePasswordAndSalt(password.getBytes(StandardCharsets.UTF_8), salt);

      // Create a MessageDigest object for SHA-512
      MessageDigest md = MessageDigest.getInstance(ALGORITHM);

      // Update the digest with the salted password
      md.update(saltedPassword);

      // Get the hash bytes
      byte[] hashedBytes = md.digest();

      // Convert the salt and hash to a base64-encoded string
      String saltBase64 = Base64.getEncoder().encodeToString(salt);
      String hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedBytes);

      // Combine the salt and hash for storage
      Password p = new Password();
      p.setHash(hashedPasswordBase64);
      p.setSalt(saltBase64);
      return p;
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
  }

  /**
   * Generates random salt.
   *
   * @return the generated salt.
   */
  private static byte[] generateSalt() {
    byte[] salt = new byte[SALT_SIZE];
    SECURE_RANDOM.nextBytes(salt);
    return salt;
  }

  /**
   * Combines password and salt.
   *
   * @param password The password.
   * @param salt The salt.
   * @return The combined password and salt.
   */
  private static byte[] combinePasswordAndSalt(byte[] password, byte[] salt) {
    byte[] combined = new byte[password.length + salt.length];
    System.arraycopy(password, 0, combined, 0, password.length);
    System.arraycopy(salt, 0, combined, password.length, salt.length);
    return combined;
  }
}
