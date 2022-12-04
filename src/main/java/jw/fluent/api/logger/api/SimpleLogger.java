package jw.fluent.api.logger.api;

import jw.fluent.api.logger.implementation.SimpleLoggerImpl;

public interface SimpleLogger extends System.Logger {
    public void warning(String message, Object... params);

    public void success(String message, Object... params);

    public void info(String message, Object... params);

    public void log(String message, SimpleLoggerImpl.LogType logType, Object... params);

    public void error(String message, Throwable exception);

    public void error(String message);
}
