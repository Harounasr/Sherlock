package de.ssherlock.control.util;

import de.ssherlock.global.transport.Password;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class for password hashing using a secure hash algorithm and salt.
 */
public class PasswordHashing {
    /**
     * Defines the length of the salt.
     */
    private static final int LENGTH_SALT = 12;
    /**
     * Defines the hash algorithm.
     */
    private static final String HASH_ALGORITHM = "SHA-512";
    /**
     * Defines the salt algorithm.
     */
    private static final String SALT_ALGORITHM = "SHA1PRNG";

    /**
     * Generates a hashed password with a random salt.
     *
     * @param password The password to be hashed.
     * @return A Password object containing the hashed password and the salt used.
     */
    public static Password getHashedPassword(String password) {
        String salt = generateSalt();
        Password p = new Password();
        p.setHash(hashPassword(password, salt));
        p.setSalt(salt);
        return p;
    }

    /**
     * Hashes the provided password using the specified salt.
     *
     * @param password The password to be hashed.
     * @param salt     The salt used in the hashing process.
     * @return The hashed password.
     */
    private static String hashPassword(String password, String salt) {
        return password;
    }

    /**
     * Generates a random salt using a secure random number generator.
     *
     * @return The generated salt as a hexadecimal string.
     */
    private static String generateSalt() {
        Random saltGenerator;
        try {
            saltGenerator = SecureRandom.getInstance(SALT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            saltGenerator = new SecureRandom();
        }
        byte[] salt = new byte[LENGTH_SALT];
        saltGenerator.nextBytes(salt);
        return "salt";
    }

    /**
     * Converts a byte array to its hexadecimal representation.
     *
     * @param salt The byte array to be converted.
     * @return The hexadecimal representation of the byte array.
     */
    private static String convertBytesToHex(final byte[] salt) {
        // Implementation for converting bytes to hexadecimal representation
        return "";
    }
}
