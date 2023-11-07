package de.ssherlock.control.util;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;

import java.util.Iterator;

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
        Iterator<ExceptionQueuedEvent> exceptionQueuedEventIterator = getHandledExceptionQueuedEvents().iterator();

        Throwable unhandledException = exceptionQueuedEventIterator.next().getContext().getException();

        // deal with exceptions
    }

    public static class Factory extends ExceptionHandlerFactory {

        public Factory(ExceptionHandlerFactory wrapped) {
            super(wrapped);
        }

        @Override
        public ExceptionHandler getExceptionHandler() {
            return new ApplicationExceptionHandler(getWrapped().getExceptionHandler());
        }
    }

}
