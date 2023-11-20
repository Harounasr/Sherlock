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
    private Part uploadedLogo;

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
     * Gets uploaded logo.
     *
     * @return the uploaded logo
     */
    public Part getUploadedLogo() {
        return uploadedLogo;
    }

    /**
     * Setter for the uploaded logo.
     *
     * @param uploadedLogo The uploaded logo.
     */
    public void setUploadedLogo(Part uploadedLogo) {
        this.uploadedLogo = uploadedLogo;
    }

    /**
     * Gets system settings.
     *
     * @return the system settings
     */
    public SystemSettings getSystemSettings() {
        return systemSettings;
    }

    /**
     * Sets system settings.
     *
     * @param systemSettings the system settings
     */
    public void setSystemSettings(SystemSettings systemSettings) {
        this.systemSettings = systemSettings;
    }
}
