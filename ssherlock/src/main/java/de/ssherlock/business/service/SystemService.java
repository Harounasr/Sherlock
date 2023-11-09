package de.ssherlock.business.service;

import de.ssherlock.global.transport.SystemSettings;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class SystemService {
    public SystemService() {

    }
    public SystemSettings getSystemSettings() {
        return null;
    }

}
