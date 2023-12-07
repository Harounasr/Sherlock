package de.ssherlock.control.exception;

import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.PartialViewContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationExceptionHandlerIT {

    @Mock
    private ExceptionHandler wrappedExceptionHandler;

    @Mock
    private FacesContext facesContext;

    @Mock
    private PartialViewContext partialViewContext;

    @Mock
    private UIViewRoot viewRoot;

    private ApplicationExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new ApplicationExceptionHandler(wrappedExceptionHandler);
        lenient().when(facesContext.getPartialViewContext()).thenReturn(partialViewContext);
        lenient().when(facesContext.getViewRoot()).thenReturn(viewRoot);
    }

    @Test
    public void testHandleNoExceptions() {
        // Prepare an empty exception queue
        Queue<ExceptionQueuedEvent> events = new LinkedList<>();
        when(wrappedExceptionHandler.getUnhandledExceptionQueuedEvents()).thenReturn(events);

        // Call the handle method
        exceptionHandler.handle();

        // Verify that no further interactions occur
        verify(wrappedExceptionHandler, times(1)).handle();
        verifyNoMoreInteractions(facesContext);
    }

    // Additional tests for different exception types and scenarios,
    // For example, test handling a DBUnavailableException, ConfigNotReadableException, etc.
}


