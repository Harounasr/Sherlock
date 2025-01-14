package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Base64;


/**
 * Backing Bean for the adminSettings.xhtml facelet.
 *
 * @author Haroun Alswedany
 */
@Named
@ViewScoped
public class AdminSettingsBean implements Serializable {

    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Logger for logging within this class.
     */
    private final SerializableLogger logger;

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
    private transient Part uploadedLogo;

    /**
     * Constructs an AdminSettingsBean.
     *
     * @param logger        The logger used for logging within this class (Injected).
     * @param appSession    The active session (Injected).
     * @param systemService The SystemService (Injected).
     */
    @Inject
    public AdminSettingsBean(
            SerializableLogger logger, AppSession appSession, SystemService systemService) {
        this.logger = logger;
        this.appSession = appSession;
        this.systemService = systemService;
    }

    /**
     * Initializes the bean after construction.
     */
    @PostConstruct
    public void initialize() {
        systemSettings = new SystemSettings();
        systemSettings = systemService.getSystemSettings();
        systemSettings.setPrimaryColorHex("#" + systemSettings.getPrimaryColorHex());
        systemSettings.setSecondaryColorHex("#" + systemSettings.getSecondaryColorHex());
    }

    /**
     * Action for submitting all current changes.
     *
     * @return Navigation outcome
     */
    public String submitAllChanges() {
        systemSettings.setPrimaryColorHex(convertToValidHex(systemSettings.getPrimaryColorHex()));
        systemSettings.setSecondaryColorHex(convertToValidHex(systemSettings.getSecondaryColorHex()));
        systemService.updateSystemSettings(systemSettings);
        systemSettings.setPrimaryColorHex("#" + systemSettings.getPrimaryColorHex());
        systemSettings.setSecondaryColorHex("#" + systemSettings.getSecondaryColorHex());
        Notification notification = new Notification("System settings successfully updated.", NotificationType.SUCCESS);
        notification.generateUIMessage();
        return "/view/admin/admin.xhtml";
    }

    /**
     * Method for converting the hex value to the correct format.
     * If the hex value starts with "#", remove it.
     *
     * @param hex The hex value to be converted.
     * @return The hex value in the correct format.
     */
    private String convertToValidHex(String hex) {
        if (hex.startsWith("#")) {
            return hex.substring(1);
        }
        return hex;
    }

    /**
     * Uploads the logo.
     */
    public void uploadLogo() {
        if (uploadedLogo == null) {
            new Notification("No logo selected.", NotificationType.ERROR).generateUIMessage();
            return;
        }
        try {
            this.systemSettings.setLogo(uploadedLogo.getInputStream().readAllBytes());
        } catch (IOException e) {
            logger.severe("Upload Logo failed");
        }
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

    /**
     * Gets logo base 64.
     *
     * @return the logo base 64
     */
    public String getLogoBase64() {
        return Base64.getEncoder().encodeToString(systemSettings.getLogo());
    }

    /**
     * Aborts the settings update.
     *
     * @return Navigation outcome.
     */
    public String abort() {
        return "";
    }
}
