package de.ssherlock.global.logging;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link SerializableLogger}.
 */
public class SerializableLoggerIT {

    /**
     * The logger.
     */
    private static SerializableLogger logger;

    /**
     * Instance of a custom logger handler.
     */
    private static CustomLogHandler handler;

    /**
     * Creates the logger and reads the config.
     */
    @BeforeAll
    public static void setup() {
        ServletContext servletContext = mock(ServletContext.class);
        ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);
        when(servletContext.getResourceAsStream(anyString())).thenAnswer(invocation -> {
            String argument = invocation.getArgument(0);
            Class<LoggerCreator> clazz = LoggerCreator.class;
            return clazz.getResourceAsStream(argument);
        });
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        LoggerCreator.readConfig(servletContextEvent);
        logger = LoggerCreator.get(SerializableLoggerIT.class);
        handler = new CustomLogHandler();
        logger.addHandler(handler);
    }

    /**
     * Tests the info() method.
     */
    @Test
    void testLogInfo() {
        logger.info("This is an information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is an information", logRecord.getMessage());
        assertEquals(Level.INFO, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogInfo", logRecord.getSourceMethodName());
    }

    /**
     * Tests the finest() method.
     */
    @Test
    void testLogFinest() {
        logger.finest("This is a finest information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is a finest information", logRecord.getMessage());
        assertEquals(Level.FINEST, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogFinest", logRecord.getSourceMethodName());
    }

    /**
     * Tests the finer() method.
     */
    @Test
    void testLogFiner() {
        logger.finer("This is a finer information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is a finer information", logRecord.getMessage());
        assertEquals(Level.FINER, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogFiner", logRecord.getSourceMethodName());
    }

    /**
     * Tests the fine() method.
     */
    @Test
    void testLogFine() {
        logger.fine("This is a fine information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is a fine information", logRecord.getMessage());
        assertEquals(Level.FINE, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogFine", logRecord.getSourceMethodName());
    }

    /**
     * Tests the config() method.
     */
    @Test
    void testLogConfig() {
        logger.config("This is a config information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is a config information", logRecord.getMessage());
        assertEquals(Level.CONFIG, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogConfig", logRecord.getSourceMethodName());
    }

    /**
     * Tests the warning() method.
     */
    @Test
    void testLogWarning() {
        logger.warning("This is a warning information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is a warning information", logRecord.getMessage());
        assertEquals(Level.WARNING, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogWarning", logRecord.getSourceMethodName());
    }

    /**
     * Tests the severe() method.
     */
    @Test
    void testLogSevere() {
        logger.severe("This is a severe information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is a severe information", logRecord.getMessage());
        assertEquals(Level.SEVERE, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogSevere", logRecord.getSourceMethodName());
    }

    /**
     * Tests the log() method.
     */
    @Test
    void testLog() {
        logger.log(Level.INFO, "This is an information");
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is an information", logRecord.getMessage());
        assertEquals(Level.INFO, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLog", logRecord.getSourceMethodName());
    }

    /**
     * Tests the log() method with a throwable.
     */
    @Test
    void testLogWithThrowable() {
        Throwable throwable = new Exception("test");
        logger.log(Level.INFO, "This is an information", throwable);
        LogRecord logRecord = handler.getLastLogRecord();
        assertEquals("This is an information", logRecord.getMessage());
        assertEquals(Level.INFO, logRecord.getLevel());
        assertEquals("de.ssherlock.global.logging.SerializableLoggerIT", logRecord.getSourceClassName());
        assertEquals("testLogWithThrowable", logRecord.getSourceMethodName());
        assertEquals(throwable, logRecord.getThrown());
    }

    /**
     * Custom {@link Handler} to get the last {@link LogRecord}.
     */
    private static class CustomLogHandler extends Handler {

        /**
         * The last log record.
         */
        private LogRecord lastLogRecord;

        /**
         * {@inheritDoc}
         */
        @Override
        public void publish(LogRecord record) {
            lastLogRecord = record;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {}

        /**
         * Gets the last log record.
         *
         * @return The last log record.
         */
        public LogRecord getLastLogRecord() {
            return lastLogRecord;
        }
    }
}
