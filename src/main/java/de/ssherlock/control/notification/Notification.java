package de.ssherlock.control.notification;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 * A class representing a notification message, which can be used to display messages in the UI.
 *
 * @author Victor Vollmann
 */
public class Notification {

  /** Defines the messages in case of a wrong password. */
  public static final String WRONG_PASSWORD_MSG =
      "Login Failed, Username and password do not match.";

  /** Defines the Notification text. */
  private final String text;

  /** Defines the Notification Type. */
  private final NotificationType type;

  /**
   * Constructs a new Notification with the specified text and type.
   *
   * @param text The text of the notification.
   * @param type The type of the notification.
   */
  public Notification(String text, NotificationType type) {
    this.text = text;
    this.type = type;
  }

  /**
   * Gets the text of the notification.
   *
   * @return The text of the notification.
   */
  public String getText() {
    return text;
  }

  /**
   * Gets the type of the notification.
   *
   * @return The type of the notification.
   */
  public NotificationType getType() {
    return type;
  }

  /** Generates a UI message based on the notification type and adds it to the FacesContext. */
  public void generateUIMessage() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext != null) {
      FacesMessage.Severity severity =
          (type == NotificationType.SUCCESS)
              ? FacesMessage.SEVERITY_INFO
              : FacesMessage.SEVERITY_ERROR;
      facesContext.addMessage(null, new FacesMessage(severity, text, null));
    } else {
      // Handle the case where FacesContext is not available (e.g., in non-JSF contexts)
      System.err.println("FacesContext is not available. Unable to generate UI message.");
    }
  }
}
