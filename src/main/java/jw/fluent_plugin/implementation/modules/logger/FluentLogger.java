package jw.fluent_plugin.implementation.modules.logger;

import jw.fluent_api.logger.implementation.SimpleLoggerImpl;
import org.bukkit.Bukkit;

public interface FluentLogger {
    void error(String message);

    void error(String message, Exception ex);

    void warning(String message, String... params);

    void success(String message, String... params);

    void info(String message, String... params);

    void log(String message, Object... params);

    static FluentLogger LOGGER = new FluentLoggerImpl(new SimpleLoggerImpl(Bukkit.getLogger()));
}
