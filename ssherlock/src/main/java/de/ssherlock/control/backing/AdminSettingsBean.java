package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.control.util.BackingBeanInitializationUtils;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;


@Named
@RequestScoped
public class AdminSettingsBean {
    @Inject
    private Logger logger;
    @Inject
    private AppSession appSession;
    @Inject
    private SystemService systemService;

    private SystemSettings systemSettings;

    private Image logo;
    private Color primaryColor;
    private Color secondaryColor;
    private String emailRegex;
    private String systemName;
    private List<String> faculties;

    public AdminSettingsBean() {

    }

    /**
     * Initializes the bean.
     */
    @PostConstruct
    public void initialize() {
        systemSettings = BackingBeanInitializationUtils.loadSystemSettings(systemService);
    }

    /**
     * Action for submitting all current changes.
     */
    public void submitAllChanges() {

    }

    public void addFaculty(ActionEvent e) {

    }

    public void removeFaculty(ActionEvent e) {

    }


    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public String getEmailRegex() {
        return emailRegex;
    }

    public void setEmailRegex(String emailRegex) {
        this.emailRegex = emailRegex;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public List<String> getFaculties() {
        return faculties;
    }

    public void setFaculties(List<String> faculties) {
        this.faculties = faculties;
    }
}
