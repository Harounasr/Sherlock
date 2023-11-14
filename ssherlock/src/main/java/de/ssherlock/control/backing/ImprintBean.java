package de.ssherlock.control.backing;

import de.ssherlock.control.session.AppSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class ImprintBean {

    private final Logger logger;
    private final AppSession appSession;
    private String imprintHeading;
    private String imprintText;

    @Inject
    public ImprintBean(Logger logger, AppSession appSession) {
        this.logger = logger;
        this.appSession = appSession;
    }

    public String getImprintHeading() {
        return imprintHeading;
    }

    public void setImprintHeading(String imprintHeading) {
        this.imprintHeading = imprintHeading;
    }

    public String getImprintText() {
        return imprintText;
    }

    public void setImprintText(String imprintText) {
        this.imprintText = imprintText;
    }
}
