package de.ssherlock.control.exception;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;

/**
 * Exception handler for handling exceptions.
 * Extends {@code ExceptionHandlerWrapper} to provide additional exception handling capabilities.
 */
public class ApplicationExceptionHandler extends ExceptionHandlerWrapper {

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
