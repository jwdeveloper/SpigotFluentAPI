package jw.fluent_api.logger.api;

import jw.fluent_api.logger.implementation.SimpleLoggerImpl;

public interface SimpleLogger extends System.Logger {
    public void warning(String message, String... params);

    public void success(String message, String... params);

    public void info(String message, String... params);

    public void log(String message, SimpleLoggerImpl.LogType logType, String... params);

    public void error(String message, Throwable exception);

    public void error(String message);
}
