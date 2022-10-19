package jw.spigot_fluent_api.fluent_logger.api;

import jw.spigot_fluent_api.fluent_logger.implementation.SimpleLoggerImpl;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.messages.LogUtility;

public interface SimpleLogger extends System.Logger {
    public void warning(String message, String... params);

    public void success(String message, String... params);

    public void info(String message, String... params);

    public void log(String message, SimpleLoggerImpl.LogType logType, String... params);

    public void error(String message, Throwable exception);

    public void error(String message);
}
