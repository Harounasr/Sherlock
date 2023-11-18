package de.ssherlock.global.transport;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a LoginInfo DTO.
 */
public class LoginInfo implements Serializable {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The entered password.
     */
    private Password password;

    /**
     * Instantiates a new Login info.
     */
    public LoginInfo() {

    }

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
    public Password getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(Password password) {
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInfo loginInfo = (LoginInfo) o;
        return Objects.equals(username, loginInfo.username) && Objects.equals(password, loginInfo.password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
