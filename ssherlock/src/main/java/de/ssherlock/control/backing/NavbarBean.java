package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.logging.Logger;

@Named
@RequestScoped
public class NavbarBean {

    @Inject
    private Logger logger;

    public NavbarBean() {

    }

}
