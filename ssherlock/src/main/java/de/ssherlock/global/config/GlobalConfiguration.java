package de.ssherlock.global.config;

public class GlobalConfiguration {

    GlobalConfiguration INSTANCE;
    String databaseUrl;
    String databaseUser;
    String databasePassword;

    private GlobalConfiguration() {

    }

    public GlobalConfiguration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalConfiguration();
            return INSTANCE;
        } else {
            return INSTANCE;
        }
    }

    public void reset() {

    }
}
