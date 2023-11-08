package de.ssherlock.control.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.awt.*;
import java.util.Date;
import java.util.logging.Logger;
@Named
@RequestScoped
public class ExerciseBean {
    @Inject
    private Logger logger;
    private String name;
    private Date publishDate;
    private Date recommendedDeadline;
    private Date obligatoryDeadline;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
