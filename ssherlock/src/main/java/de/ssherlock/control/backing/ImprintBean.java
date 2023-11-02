package de.ssherlock.control.backing;

import java.util.logging.Logger;

public class ImprintBean {

    private String imprintHeading;
    private String imprintText;
    private Logger logger;

    public ImprintBean() {}

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
