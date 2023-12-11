package de.ssherlock.control.session;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for the AppSession class.
 *
 * @author Leon Föckersperger
 */
@ExtendWith(MockitoExtension.class)
class AppSessionIT {

    /**
     * The logger for this class.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private SerializableLogger logger;

    /**
     * The user service.
     */
    @Produces
    @Default
    @Mock(serializable = true)
    private UserService userService;

    /**
     * The app session.
     */
    private AppSession appSession;

    /**
     * Sets up the test environment.
     */

    @BeforeEach
    void setUp() {
        appSession = new AppSession(logger, userService);
    }

    /**
     * Tests if the user is anonymous.
     */
    @Test
    void testIsAnonymous() {
        assertTrue(appSession.isAnonymous());
        User user = new User();
        user.setUsername("test");
        appSession.setUser(user);
        assertFalse(appSession.isAnonymous());
    }

    /**
     * Tests if the user is an admin.
     *
     * @throws BusinessNonExistentUserException if the user does not exist
     */
    @Test
    void testIsAdmin() throws BusinessNonExistentUserException {
        assertFalse(appSession.isAdmin());
        User user = new User();
        user.setUsername("test");
        user.setSystemRole(SystemRole.ADMINISTRATOR);
        when(userService.getUser(user)).thenReturn(user);
        appSession.setUser(user);
        assertTrue(appSession.isAdmin());
    }

    /**
     * Tests the getter for the user.
     *
     * @throws BusinessNonExistentUserException if the user does not exist
     */
    @Test
    void testGetUser() throws BusinessNonExistentUserException {
        User user = new User();
        user.setUsername("test");
        when(userService.getUser(user)).thenReturn(user);
        appSession.setUser(user);
        assertEquals(user, appSession.getUser());
    }

    /**
     * Tests the setter for the user.
     */
    @Test
    void testSetUser() {
        User user = new User();
        user.setUsername("test");
        appSession.setUser(user);
        verify(logger, times(1)).log(Level.INFO, "User " + user.getUsername() + " is now set.");
    }

    /**
     * Tests the logout method.
     */
    @Test
    void testLogout() {
        User user = new User();
        user.setUsername("test");
        appSession.setUser(user);
        assertEquals("/view/public/login?faces-redirect=true", appSession.logout());
        assertTrue(appSession.isAnonymous());
    }
}
