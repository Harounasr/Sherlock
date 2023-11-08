package de.ssherlock.control.notification;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jdk.jfr.Name;

import java.util.logging.Logger;

public class Notification {

    public static final String WRONG_PASSWORD_MSG = "Login Failed, Username and password do not match.";

    private String text;
    private NotificationType type;

    public Notification(String text, NotificationType type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void generateUIMessage() {
        FacesMessage.Severity severity = type == NotificationType.SUCCESS ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, text, null));
    }




}