package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;
@Named
@RequestScoped
public class ImprintBean {

    @Inject
    private Logger logger;
    private String imprintHeading;
    private String imprintText;

    public ImprintBean() {

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
