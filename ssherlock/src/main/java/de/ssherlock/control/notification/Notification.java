package de.ssherlock.control.notification;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jdk.jfr.Name;

import java.util.logging.Logger;

@Named
@Dependent
public class Notification {



    private String text;
    private NotificationType type;

    public Notification() {

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

    public void generateUIMessage(String tag) {
        FacesMessage.Severity severity = type == NotificationType.SUCCESS ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_ERROR;
        FacesContext.getCurrentInstance().addMessage(tag, new FacesMessage(severity, text, null));
    }




}