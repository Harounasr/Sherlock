package de.ssherlock.control.backing;

import java.awt.*;
import java.util.Date;
import java.util.logging.Logger;

public class ExerciseBean {

    private Logger logger;
    private String name;
    private Date publishDate;
    private Date recommendedDeadline;
    private Date obligatoryDeadline;
    private List checkers;
    public ExerciseBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getRecommendedDeadline() {
        return recommendedDeadline;
    }

    public void setRecommendedDeadline(Date recommendedDeadline) {
        this.recommendedDeadline = recommendedDeadline;
    }

    public Date getObligatoryDeadline() {
        return obligatoryDeadline;
    }

    public void setObligatoryDeadline(Date obligatoryDeadline) {
        this.obligatoryDeadline = obligatoryDeadline;
    }

    public List getCheckers() {
        return checkers;
    }

    public void setCheckers(List checkers) {
        this.checkers = checkers;
    }
}
