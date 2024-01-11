package de.ssherlock.control.validation;

import de.ssherlock.global.logging.LoggerCreator;
import de.ssherlock.global.logging.SerializableLogger;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Handles conversion of Timestamps.
 *
 * @author Victor Vollmann
 */
@Dependent
@FacesConverter(value = "dateConverter", managed = true)
public class TimestampConverter implements Converter<Timestamp> {

    private final SerializableLogger logger = LoggerCreator.get(TimestampConverter.class);

    /**
     * Constructs a new timestamp converter.
     */
    public TimestampConverter() {

    }

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
        if (s == null || s.isEmpty()) {
            return null;
        }

        List<String> dateFormats = Arrays.asList(
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd HH:mm",
                "MM/dd/yyyy HH:mm",
                "dd/MM/yyyy HH:mm"
        );

        for (String format : dateFormats) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                return new Timestamp(formatter.parse(s).getTime());
            } catch (ParseException e) {
                logger.warning("Failed to parse date: " + s + " with format: " + format);
            }
        }

        logger.severe("Unable to parse date: " + s);
        return null;
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
