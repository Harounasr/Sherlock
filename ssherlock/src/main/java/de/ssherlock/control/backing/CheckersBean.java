package de.ssherlock.control.backing;

import de.ssherlock.global.transport.Checker;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.logging.Logger;

@Named
@RequestScoped
public class CheckersBean {
    @Inject
    private Logger logger;
    private List<Checker> checkers;

    public CheckersBean() {

    }

    public List<Checker> getCheckers() {
        return checkers;
    }

    public void setCheckers(List<Checker> checkers) {
        this.checkers = checkers;
    }



}
