package de.ssherlock.business.util;

import de.ssherlock.global.transport.Password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

/**
 * Utility class for password hashing using a secure hash algorithm and salt.
 *
 * @author Leon Höfling
 */
public class PasswordHashing {

    /**
     * Default constructor.
     */
    private PasswordHashing() {

    }

    /**
     * The algorithm to use for hashing.
     */
    private static final String ALGORITHM = "SHA-512";

    /**
     * Generates a hashed password with a random salt.
     *
     * @param password The password to be hashed.
     * @return A Password object containing the hashed password and the salt used.
     */
    public static Password getHashedPassword(String password) {
        return hashPassword(password, Optional.empty());
    }

    /**
     * Generates a hashed password with a given salt.
     *
     * @param password The password to be hashed.
     * @param salt     The salt to hash with.
     * @return A String object containing the hashed password.
     */
    public static String getHashedPassword(String password, String salt) {
        return hashPassword(password, Optional.of(salt)).getHash();
    }

    /**
     * Hashes the provided password using the specified salt.
     *
     * @param password     The password to be hashed.
     * @param passwordSalt Optional salt value. If provided, the password will be hashed with this salt;
     *                     *                  otherwise, a random salt will be generated.
     * @return The hashed password.
     */
    private static Password hashPassword(String password, Optional<String> passwordSalt) {
        try {
            byte[] salt;
            // Generate a random salt
            if (passwordSalt.isPresent()) {
                salt = Base64.getDecoder().decode(passwordSalt.get());
            } else {
                salt = generateSalt();
            }
            // Combine the password and salt
            byte[] saltedPassword = combinePasswordAndSalt(password.getBytes(StandardCharsets.UTF_8), salt);

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
            // Handle the exception appropriately
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates random salt.
     *
     * @return the generated salt.
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes is a common size for a salt
        random.nextBytes(salt);
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
