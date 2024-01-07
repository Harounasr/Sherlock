package de.ssherlock.control.util;

import de.ssherlock.business.exception.BusinessNonExistentImageException;
import de.ssherlock.business.service.ExerciseDescriptionImageService;
import de.ssherlock.global.logging.SerializableLogger;
import de.ssherlock.global.transport.ExerciseDescriptionImage;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Integration Test for the {@link ExerciseDescriptionImageServlet} class.
 *
 * @author Victor Vollmann
 */
@ExtendWith(MockitoExtension.class)
public class ExerciseDescriptionImageServletIT {

    /**
     * Mocked servlet request.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * Mocked servlet response.
     */
    @Mock
    private HttpServletResponse response;

    /**
     * Mocked Exercise Description Image Service.
     */
    @Mock
    private ExerciseDescriptionImageService imageService;

    /**
     * Mocked logger.
     */
    @Mock
    private SerializableLogger logger;

    /**
     * Mocked mock servlet output stream.
     */
    @Mock
    private ServletOutputStream mockedOutputStream;

    /**
     * Servlet with injected mocks.
     */
    @InjectMocks
    private ExerciseDescriptionImageServlet servlet;

    /**
     * Test for the doGet method with successful outcome.
     *
     * @throws Exception can be ignored.
     */
    @Test
    @SuppressWarnings("checkstyle:MagicNumber")
    void testDoGetSuccess() throws Exception {
        when(request.getParameter("id")).thenReturn("validImageId");
        ExerciseDescriptionImage mockImage = new ExerciseDescriptionImage();
        mockImage.setImage(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00});
        when(imageService.getImage(any())).thenReturn(mockImage);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        doAnswer(invocation -> {
            byte[] b = invocation.getArgument(0);
            byteArrayOutputStream.write(b);
            return null;
        }).when(mockedOutputStream).write(any(byte[].class));
        when(response.getOutputStream()).thenReturn(mockedOutputStream);

        servlet.doGet(request, response);

        assertArrayEquals(mockImage.getImage(), byteArrayOutputStream.toByteArray());
        verify(response).setContentType("image/png");
    }

    /**
     * Test for the doGet method with bad outcome.
     * The image does not exist, response should explain this.
     *
     * @throws Exception can be ignored.
     */
    @Test
    void testDoGetFailedNonExistentImage() throws Exception {
        when(request.getParameter("id")).thenReturn("invalidImageId");
        when(imageService.getImage(any())).thenThrow(new BusinessNonExistentImageException());
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertTrue(stringWriter.toString().contains("Image not found"));
    }

    /**
     * Test for the doGet method with bad outcome.
     * The output stream throws an IOException, response should explain this.
     *
     * @throws Exception can be ignored.
     */
    @Test
    @SuppressWarnings("checkstyle:MagicNumber")
    void testDoGetFailedIOException() throws Exception {
        when(request.getParameter("id")).thenReturn("validImageId");
        doThrow(new IOException()).when(mockedOutputStream).write(any(byte[].class));
        when(response.getOutputStream()).thenReturn(mockedOutputStream);
        ExerciseDescriptionImage mockImage = new ExerciseDescriptionImage();
        mockImage.setImage(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00, 0x00});
        when(imageService.getImage(any())).thenReturn(mockImage);
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        assertTrue(stringWriter.toString().contains("Error processing the request"));
    }

    /**
     * Test for the doGet method with bad outcome.
     * The image id is not provided, response should explain this.
     *
     * @throws Exception can be ignored.
     */
    @Test
    void testDoGetFailedNoImageId() throws Exception {
        when(request.getParameter("id")).thenReturn(null);
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        assertTrue(stringWriter.toString().contains("Image ID parameter is missing"));
    }
}
