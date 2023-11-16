package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Backing Bean for the adminSettings.xhtml facelet.
 */
@Named
@RequestScoped
public class AdminSettingsBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Service to handle SystemSetting-specific actions.
     */
    private final SystemService systemService;

    /**
     * Currently active SystemSettings.
     */
    private SystemSettings systemSettings;

    /**
     * The uploaded logo.
     */
    private Part logo;

    /**
     * The selected primary color.
     */
    private Color primaryColor;

    /**
     * The selected secondary color.
     */
    private Color secondaryColor;

    /**
     * The entered email regex.
     */
    private String emailRegex;

    /**
     * The entered system name.
     */
    private String systemName;

    /**
     * The entered list of faculty options.
     */
    private List<String> faculties;

    /**
     * Constructs an AdminSettingsBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param systemService The SystemService (Injected).
     */
    @Inject
    public AdminSettingsBean(Logger logger, AppSession appSession, SystemService systemService) {
        this.logger = logger;
        this.appSession = appSession;
        this.systemService = systemService;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        systemSettings = systemService.getSystemSettings();
    }

    /**
     * Action for submitting all current changes.
     */
    public void submitAllChanges() {
        // Implementation for submitting changes goes here
    }

    /**
     * Action to add a faculty to the option list.
     *
     * @param e The fired ActionEvent.
     */
    public void addFaculty(ActionEvent e) {
        // Logic for adding faculty goes here
    }

    /**
     * Action to remove a faculty from the option list.
     *
     * @param e The fired ActionEvent.
     */
    public void removeFaculty(ActionEvent e) {
        // Logic for removing faculty goes here
    }

    /**
     * Setter for the uploaded logo.
     *
     * @param logo The uploaded logo.
     */
    public void setLogo(Part logo) {
        this.logo = logo;
    }

    /**
     * Setter for the entered email regex.
     *
     * @param emailRegex The entered email regex.
     */
    public void setEmailRegex(String emailRegex) {
        this.emailRegex = emailRegex;
    }

    /**
     * Setter for the selected primary color.
     *
     * @param primaryColor The selected primary color.
     */
    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    /**
     * Setter for the selected secondary color.
     *
     * @param secondaryColor The selected secondary color.
     */
    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    /**
     * Setter for the entered system name.
     *
     * @param systemName The entered system name.
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
