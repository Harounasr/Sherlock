package de.ssherlock.control.util;

import de.ssherlock.global.transport.Password;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordHashing {

    private static final int LENGTH_SALT = 12;

    private static final String HASH_ALGORITHM = "SHA-512";

    private static final String SALT_ALGORITHM = "SHA1PRNG";

    private PasswordHashing() {
    }

    private static String hashPassword(String password, String salt) {
        return "";
    }

    public static Password getHashedPassword(String password) {
        String salt = generateSalt();
        return new Password(hashPassword(password, salt), salt);
    }

    private static String generateSalt() {
        Random saltGenerator;
        try {
            saltGenerator = SecureRandom.getInstance(SALT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            saltGenerator = new SecureRandom();
        }
        byte[] salt = new byte[LENGTH_SALT];
        saltGenerator.nextBytes(salt);
        return convertBytesToHex(salt);
    }

    private static String convertBytesToHex(final byte[] salt) {
        return "";
    }
}
