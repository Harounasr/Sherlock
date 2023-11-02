package de.ssherlock.control.backing;

import java.util.logging.Logger;

public class VerificationBean {

    private String successText;
    private Logger logger;

    public VerificationBean() {}

    public String getSuccessText() {
        return successText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }
}
