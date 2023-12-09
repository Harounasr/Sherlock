package de.ssherlock.business.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.ssherlock.global.transport.Password;
import org.junit.jupiter.api.Test;

public class PasswordHashingIT {

    @Test
    void testGetHashedPasswordWithRandomSalt() {
        String password = "testPassword";
        Password hashedPassword = PasswordHashing.getHashedPassword(password);

        assertNotNull(hashedPassword);
        assertNotNull(hashedPassword.getHash());
        assertNotNull(hashedPassword.getSalt());
        assertNotEquals(password, hashedPassword.getHash());
    }

    @Test
    void testGetHashedPasswordWithGivenSalt() {
        String password = "testPassword";
        String salt = "randomSalt";

        String hashedPassword = PasswordHashing.getHashedPassword(password, salt);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }
}
