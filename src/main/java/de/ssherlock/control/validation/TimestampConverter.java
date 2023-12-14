package de.ssherlock.control.validation;

import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles conversion of Timestamps.
 *
 * @author Victor Vollmann
 */
@Named
@Dependent
@FacesConverter(value = "dateConverter", managed = true)
public class TimestampConverter implements Converter<Timestamp> {

    /**
     * Converts a String to the corresponding date.
     *
     * @param facesContext The context.
     * @param uiComponent The UI component calling the converter.
     * @param s The string to convert.
     * @return The Date object.
     */
    @Override
    public Timestamp getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        LocalDateTime localDate = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        return Timestamp.valueOf(localDate);
    }

    /**
     * Converts a Date to the corresponding string.
     *
     * @param facesContext The context.
     * @param uiComponent The UI component calling the converter.
     * @param timestamp The timestamp to convert.
     * @return The converted string object.
     */
    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Timestamp timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
        return formatter.format(timestamp);
    }
}
