package de.ssherlock.control.backing;


import de.ssherlock.business.service.UserService;
import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class ExerciseSidebarBean {

    private final Logger logger;
    private final AppSession appSession;

    @Inject
    public ExerciseSidebarBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    public void loadDescription() {

    }
    public void loadUploadPage() {

    }
    public void loadAllSubmission() {

    }

    public void loadAllTestates() {

    }
}
