package de.ssherlock.control.exception;

import de.ssherlock.control.backing.ErrorBean;
import de.ssherlock.global.transport.Error;
import jakarta.el.ELException;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.FacesException;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Exception handler for handling exceptions.
 * Extends {@link ExceptionHandlerWrapper} to provide additional exception handling capabilities.
 *
 * @author Leon Föckserperger
 */
public class ApplicationExceptionHandler extends ExceptionHandlerWrapper {

    /**
     * Base path to all error pages.
     */
    private static final String BASE_PATH = "/WEB-INF/errorpages/";

    /**
     * Constructs a new ApplicationExceptionHandler with the specified wrapped ExceptionHandler.
     *
     * @param wrapped The wrapped ExceptionHandler.
     */
    public ApplicationExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    /**
     * Handles exceptions by delegating to the custom {@code doHandle} method
     * and then invoking the wrapped ExceptionHandler's handle method.
     */
    @Override
    public void handle() {
        doHandle(FacesContext.getCurrentInstance());
        getWrapped().handle();
    }

    /**
     * Custom exception handling logic. Subclasses can override this method
     * to provide specific handling for different types of exceptions.
     *
     * @param context The FacesContext associated with the current request.
     */
    protected void doHandle(FacesContext context) {
        Iterator<ExceptionQueuedEvent> unhandledExceptions = getUnhandledExceptionQueuedEvents().iterator();
        if (context == null || !unhandledExceptions.hasNext()) {
            return;
        }
        Throwable exception = unhandledExceptions.next().getContext().getException();
        while (exception.getCause() != null && (exception instanceof FacesException || exception instanceof ELException)) {
            exception = exception.getCause();
        }
        if (exception instanceof NoAccessException) {
            String message = "You don't have permission to see this site.";
            show404Page(context, exception);
        } else {
            return;
        }
        unhandledExceptions.remove();
        while (unhandledExceptions.hasNext()) {
            unhandledExceptions.next();
            unhandledExceptions.remove();
        }

    }

    /**
     * Sets the error bean.
     *
     * @param exception The exception.
     * @param message   The message.
     */
    private void setErrorBean(Throwable exception, String message) {
        ErrorBean errorBean = CDI.current().select(ErrorBean.class).get();
        Error error = new Error();
        error.setException((Exception) exception);
        error.setMessage(message);
        errorBean.setError(error);
    }

    /**
     * Shows a certain page.
     *
     * @param context The current context.
     * @param page    The page to show.
     */
    private void showPage(FacesContext context, String page) {
        context.setViewRoot(context.getApplication().getViewHandler().createView(context, BASE_PATH + page));
        context.getPartialViewContext().setRenderAll(true);
        context.getApplication().getViewHandler();
        context.renderResponse();
    }

    /**
     * Shows a certain error page.
     *
     * @param context   The current context.
     * @param exception The exception.
     * @param message   The message.
     */
    private void showErrorPage(FacesContext context, Throwable exception, String message) {
        setErrorBean(exception, message);
        showPage(context, "error.xhtml");
    }

    /**
     * Shows the "404 not found" error page.
     *
     * @param context   The current context.
     * @param exception The exception.
     */
    private void show404Page(FacesContext context, Throwable exception) {
        setErrorBean(exception, "");
        showPage(context, "error404.xhtml");
    }

    /**
     * Factory class for creating instances of {@code ApplicationExceptionHandler}.
     * Extends {@code ExceptionHandlerFactory} to provide the custom exception handler.
     */
    public static class Factory extends ExceptionHandlerFactory {

        /**
         * Constructs a new Factory with the specified wrapped ExceptionHandlerFactory.
         *
         * @param wrapped The wrapped ExceptionHandlerFactory.
         */
        public Factory(ExceptionHandlerFactory wrapped) {
            super(wrapped);
        }

        /**
         * Creates and returns a new instance of {@code ApplicationExceptionHandler}.
         *
         * @return A new instance of {@code ApplicationExceptionHandler}.
         */
        @Override
        public ApplicationExceptionHandler getExceptionHandler() {
            return new ApplicationExceptionHandler(getWrapped().getExceptionHandler());
        }
    }
}
