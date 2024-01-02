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
     * @param request  The HTTPServletRequest.
     * @param response The HTTPServletResponse.
     * @throws IOException when the writer is not accessible.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String imageId = request.getParameter("id");
        if (imageId != null) {
            logger.info("Client request for image with id " + imageId);
            ExerciseDescriptionImage image = new ExerciseDescriptionImage();
            image.setUUID(imageId);
            try {
                image = exerciseDescriptionImageService.getImage(image);
                response.setContentType("image/png");
                response.getOutputStream().write(image.getImage());
                logger.info("Request managed successfully.");
            } catch (IOException | BusinessNonExistentImageException e) {
                handleException(response, e);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Image ID parameter is missing");
            logger.warning("Requested an image without use of id.");
        }
    }

    /**
     * Handles exceptions that might occur during the image request handling process.
     *
     * @param response The HttpServletResponse object for sending the error response back to the
     *                 client.
     * @param e        The exception that occurred during the image request handling.
     * @throws IOException If an I/O error occurs while sending the error response to the client.
     */
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        if (e instanceof BusinessNonExistentImageException) {
            logger.log(Level.WARNING, "Requested image does not exist.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("Image not found");
        } else {
            logger.log(Level.SEVERE, "Error while processing image request", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error processing the request");
        }
    }
}
