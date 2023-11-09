package de.ssherlock.business.service;

import de.ssherlock.global.transport.SystemSettings;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class SystemService {
    @Inject
    private Logger logger;
    public SystemService() {

    }
    public SystemSettings getSystemSettings() {
        return null;
    }
    public void updateSystemSettings() {

    }
}
