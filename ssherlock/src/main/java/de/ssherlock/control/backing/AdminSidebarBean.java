package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class AdminSidebarBean {

    private final Logger logger;
    private final AppSession appSession;

    @Inject
    public AdminSidebarBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    public void loadAdminSettings() {

    }

    public void loadUserPagination() {

    }

}
