package de.ssherlock.control.notification;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 * A class representing a notification message, which can be used to display messages in the UI.
 *
 * @param text Defines the Notification text.
 * @param type Defines the Notification Type.
 * @author Victor Vollmann
 */
public record Notification(String text, NotificationType type) {

    /**
     * Defines the messages in case of a wrong password.
     */
    public static final String WRONG_PASSWORD_MSG =
            "Login Failed, Username and password do not match.";

    /**
     * Constructs a new Notification with the specified text and type.
     *
     * @param text The text of the notification.
     * @param type The type of the notification.
     */
    public Notification {
    }

    /**
     * Gets the text of the notification.
     *
     * @return The text of the notification.
     */
    @Override
    public String text() {
        return text;
    }

    /**
     * Gets the type of the notification.
     *
     * @return The type of the notification.
     */
    @Override
    public NotificationType type() {
        return type;
    }

    /**
     * Generates a UI message based on the notification type and adds it to the FacesContext.
     */
    public void generateUIMessage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            FacesMessage.Severity severity =
                    (type == NotificationType.SUCCESS)
                            ? FacesMessage.SEVERITY_INFO
                            : FacesMessage.SEVERITY_ERROR;
            facesContext.addMessage(null, new FacesMessage(severity, text, null));
        }
    }
}
