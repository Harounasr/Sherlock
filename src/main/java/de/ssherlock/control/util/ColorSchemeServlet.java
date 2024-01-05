package de.ssherlock.control.util;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Web Servlet to serve the applications color scheme.
 *
 * @author Victor Vollmann
 */
@WebServlet("/colorScheme.css")
public class ColorSchemeServlet extends HttpServlet {

    /**
     * The logger instance for this class.
     */
    @Inject
    private SerializableLogger logger;

    /**
     * System Settings Service to get the color scheme.
     */
    @Inject
    private SystemService systemService;

    /**
     * Default constructor.
     */
    public ColorSchemeServlet() {

    }

    /**
     * Returns a css file that sets global variables for the color scheme.
     *
     * @param request  The HTTPServletRequest.
     * @param response The HTTPServletResponse.
     * @throws IOException when the writer is not accessible.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.finer("Request for color scheme received.");
        SystemSettings systemSettings = systemService.getSystemSettings();
        response.setContentType("text/css");
        PrintWriter out = response.getWriter();
        out.println(":root {");
        out.println("  --primary-color: #" + systemSettings.getPrimaryColorHex() + ";");
        out.println("  --secondary-color: #" + systemSettings.getSecondaryColorHex() + ";");
        out.println("}");
        logger.finer("Request for color scheme handled.");
    }

}
