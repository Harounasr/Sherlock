package de.ssherlock.control.util;

import de.ssherlock.business.exception.BusinessNonExistentImageException;
import de.ssherlock.business.service.ExerciseDescriptionImageService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Web Servlet to serve all exercise description images.
 *
 * @author Victor Vollmann
 */
@WebServlet("/image")
public class ExerciseDescriptionImageServlet extends HttpServlet {

    /**
     * The logger instance for this class.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * Exercise description image service for image related operations.
     */
    @Inject
    private ExerciseDescriptionImageService exerciseDescriptionImageService;

    /**
     * Default constructor.
     */
    public ExerciseDescriptionImageServlet() {

    }

    /**
     * Gets the requested image from the database and serves it through the response.
     *
     * @param request The HTTPServletRequest.
     * @param response The HTTPServletResponse.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String imageId = request.getParameter("id");
        if (imageId != null) {
            logger.log(Level.INFO, "Client request for image with id " + imageId + ".");
            ExerciseDescriptionImage image = null;
            try {
                image = exerciseDescriptionImageService.getImage(imageId);
            } catch (BusinessNonExistentImageException e) {
                throw new RuntimeException(e);
            }

            response.setContentType("image/png");

            try {
                response.getOutputStream().write(image.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
