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
    private final SerializableLogger logger;

    /**
     * Exercise description image service for image related operations.
     */
    private final ExerciseDescriptionImageService exerciseDescriptionImageService;

    /**
     * Default constructor.
     */
    @Inject
    public ExerciseDescriptionImageServlet(
            SerializableLogger logger, ExerciseDescriptionImageService exerciseDescriptionImageService) {
        this.logger = logger;
        this.exerciseDescriptionImageService = exerciseDescriptionImageService;
    }

    /**
     * Empty constructor for CDI.
     */
    protected ExerciseDescriptionImageServlet() {
        this(null, null);
    }

    /**
     * Gets the requested image from the database and serves it through the response.
     *
     * @param request  The HTTPServletRequest.
     * @param response The HTTPServletResponse.
     * @throws IOException when the writer is not accessible.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            handleImageRequest(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    /**
     * Handles the client request for retrieving an image based on the provided ID.
     *
     * @param request  The HttpServletRequest object representing the client's request.
     * @param response The HttpServletResponse object for sending the response back to the client.
     * @throws IOException                       If an I/O error occurs while processing the request or response.
     * @throws BusinessNonExistentImageException If the requested image does not exist in the system.
     */
    private void handleImageRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, BusinessNonExistentImageException {
        String imageId = request.getParameter("id");
        if (imageId != null) {
            logger.log(Level.INFO, "Client request for image with id " + imageId);
            ExerciseDescriptionImage image = new ExerciseDescriptionImage();
            image.setUUID(imageId);
            image = exerciseDescriptionImageService.getImage(image);
            response.setContentType("image/png");
            response.getOutputStream().write(image.getImage());
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Image ID parameter is missing");
        }
    }

    /**
     * Handles exceptions that might occur during the image request handling process.
     *
     * @param response The HttpServletResponse object for sending the error response back to the client.
     * @param e        The exception that occurred during the image request handling.
     * @throws IOException If an I/O error occurs while sending the error response to the client.
     */
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        if (e instanceof BusinessNonExistentImageException) {
            logger.log(Level.WARNING, "Requested image does not exist: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("Image not found");
        } else {
            logger.log(Level.SEVERE, "Error while processing image request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error processing the request");
        }
    }
}
