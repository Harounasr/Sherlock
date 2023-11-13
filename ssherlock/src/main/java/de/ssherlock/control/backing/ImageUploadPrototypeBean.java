package de.ssherlock.control.backing;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Logger;

/**
 *
 * NOT INCLUDED IN ACTUAL IMPLEMENTATION
 * FOR PROTOTYPING ONLY!!
 *
 */

@Named
@RequestScoped
public class ImageUploadPrototypeBean {
    @Inject
    private SystemService systemService;
    @Inject
    private AppSession appSession;
    @Inject
    private Logger logger;

    private Part uploadedFile;

    private SystemSettings systemSettings;

    private String logoImage;


    @PostConstruct
    public void initialize() {
        systemSettings = systemService.getSystemSettings();
        logoImage = Base64.getEncoder().encodeToString(systemSettings.logo());
    }

    public void handleUpload() {
        if (uploadedFile != null) {
            try {
                InputStream inputStream = uploadedFile.getInputStream();
                SystemSettings newSettings = new SystemSettings.Builder()
                        .copyFrom(systemSettings)
                        .logo(inputStream.readAllBytes())
                        .build();
                systemService.updateSystemSettings(newSettings);
                initialize();
            } catch (IOException e) {
            }
        }
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
}
