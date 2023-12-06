package de.ssherlock.control.session;

import de.ssherlock.business.exception.BusinessNonExistentUserException;
import de.ssherlock.business.service.UserService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemRole;
import de.ssherlock.global.transport.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for the AppSession class.
 */
class AppSessionIT {

    /**
     * The logger for this class.
     */
    private SerializableLogger logger;

    /**
     * The user service.
     */
    private UserService userService;

    /**
     * The app session.
     */
    private AppSession appSession;

    @BeforeEach
    void setUp() {
        logger = mock(SerializableLogger.class);
        userService = mock(UserService.class);
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
        when(userService.getUser("test")).thenReturn(user);
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
        when(userService.getUser("test")).thenReturn(user);
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
