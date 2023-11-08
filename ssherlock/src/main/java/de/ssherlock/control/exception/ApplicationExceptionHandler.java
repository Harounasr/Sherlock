package de.ssherlock.control.exception;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;

public class ApplicationExceptionHandler extends ExceptionHandlerWrapper {

    public ApplicationExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }

    @Override
    public void handle() {
        doHandle(FacesContext.getCurrentInstance());
        getWrapped().handle();
    }

    public void doHandle(FacesContext context) {
        //Iterator<ExceptionQueuedEvent> exceptionQueuedEventIterator = getHandledExceptionQueuedEvents().iterator();

        //Throwable unhandledException = exceptionQueuedEventIterator.next().getContext().getException();

        // deal with exceptions
    }

    public static class Factory extends ExceptionHandlerFactory {

        public Factory(ExceptionHandlerFactory wrapped) {
            super(wrapped);
        }

        @Override
        public ApplicationExceptionHandler getExceptionHandler() {
            return new ApplicationExceptionHandler(getWrapped().getExceptionHandler());
        }
    }

}
