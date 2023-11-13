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
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write((BufferedImage) systemSettings.logo(), "png", os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logoImage = Base64.getEncoder().encodeToString(os.toByteArray());
    }

    public void handleUpload() {
        if (uploadedFile != null) {
            try {
                InputStream inputStream = uploadedFile.getInputStream();
                Image image = ImageIO.read(inputStream);
                SystemSettings newSettings = new SystemSettings(
                     systemSettings.emailRegex(),
                        systemSettings.primaryColorHex(),
                        systemSettings.secondaryColorHex(),
                        systemSettings.systemName(),
                        image,
                        null
                );
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
    public String checkers() {return "/view/Checkers.xhtml";}
}
