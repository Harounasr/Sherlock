package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

/**
 * Backing Bean for the adminSidebar.xhtml facelet.
 */
@Named
@RequestScoped
public class AdminSidebarBean {

    /**
     * Logger for logging within this class.
     */
    private final Logger logger;

    /**
     * The active session.
     */
    private final AppSession appSession;

    /**
     * Constructs an AdminSidebarBean.
     *
     * @param logger      The logger used for logging within this class (Injected).
     * @param appSession  The active session (Injected).
     */
    @Inject
    public AdminSidebarBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    /**
     * Action to load the adminSetting.xhtml facelet into the content.
     */
    public void loadAdminSettings() {
    }

    /**
     * Action to load the adminUserPagination.xhtml facelet into the content.
     */
    public void loadUserPagination() {
    }
}
