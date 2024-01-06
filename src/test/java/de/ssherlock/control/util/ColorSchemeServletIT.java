package de.ssherlock.control.util;

import de.ssherlock.business.service.SystemService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.SystemSettings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Integration Test for the {@link ColorSchemeServlet} class.
 *
 * @author Victor Vollmann
 */
@ExtendWith(MockitoExtension.class)
public class ColorSchemeServletIT {

    /**
     * Mocked http request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Mocked http response.
     */
    @Mock
    private HttpServletResponse response;

    /**
     * Mocked system service
     */
    @Mock
    private SystemService systemService;

    /**
     * Mocked logger.
     */
    @Mock
    private SerializableLogger logger;

    /**
     * Color scheme servlet with injected mocks.
     */
    @InjectMocks
    private ColorSchemeServlet servlet;

    /**
     * Test for the do get method.
     * Servlet should provide the css for the color scheme.
     *
     * @throws Exception can be ignored.
     */
    @Test
    void testDoGet() throws Exception {
        SystemSettings settings = new SystemSettings();
        settings.setPrimaryColorHex("ff0000");
        settings.setSecondaryColorHex("00ff00");
        when(systemService.getSystemSettings()).thenReturn(settings);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        verify(systemService).getSystemSettings();
        verify(response).setContentType("text/css");

        writer.flush();
        String expectedCss = ":root{--primary-color:#ff0000;--secondary-color:#00ff00;}";
        String actualCss = stringWriter.toString().replaceAll("\\s+", "");
        assertEquals(expectedCss, actualCss);
    }

}
