package xyz.tehbrian.restrictionhelper;

import org.apache.commons.lang.NullArgumentException;

import java.util.logging.Logger;

/**
 * A small class which makes debug logging easier. If {@link DebugLogger#debug} is
 * set to true, messages logged using {@link DebugLogger#log(String)} will be logged
 * via {@link DebugLogger#logger}, else the message will just be ignored.
 */
public class DebugLogger {

    /**
     * The {@link Logger} to be used when debugging is enabled.
     */
    private final Logger logger;
    /**
     * Whether or not attempted logs should be logged via {@link Logger}.
     */
    private boolean debug = false;

    /**
     * Private no-args constructor to enforce providing a {@link Logger}.
     */
    private DebugLogger() {
        throw new IllegalArgumentException("No Logger provided.");
    }

    /**
     * Construct a {@link DebugLogger} using {@code logger} as the internal {@link Logger}.
     *
     * @param logger the {@link Logger} to be used when debugging is enabled.
     */
    public DebugLogger(final Logger logger) {
        if (logger == null) {
            throw new NullArgumentException("Logger provided is null.");
        }

        this.logger = logger;
    }

    /**
     * Check whether debug logs will be logged or not.
     *
     * @return whether debug is enabled or not
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Set whether debug logs will be logged or not.
     *
     * @param debug whether debug should be enabled or not
     */
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    /**
     * Logs a message using {@link DebugLogger#logger} if {@link DebugLogger#debug} is true.
     *
     * @param message the message to log
     */
    public void log(final String message) {
        if (!debug) {
            return;
        }

        logger.info("ResHelper:" + message);
    }

    /**
     * Convenience method which applies {@link String#format(String, Object...)}
     * onto a message before passing it to {@link DebugLogger#log(String)}.
     *
     * @param message the message to log
     * @param formats formats to apply to the message
     */
    public void logf(final String message, final Object... formats) {
        log(String.format(message, formats));
    }
}
