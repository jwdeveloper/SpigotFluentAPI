package jw.fluent.plugin.implementation.modules.logger;

import jw.fluent.api.logger.implementation.SimpleLoggerImpl;
import org.bukkit.Bukkit;

public interface FluentLogger {
    void error(String message);

    void error(String message, Exception ex);

    void warning(String message, Object... params);

    void success(String message, Object... params);

    void info(String message, Object... params);

    void log(String message, Object... params);

    static FluentLogger LOGGER = new FluentLoggerImpl(new SimpleLoggerImpl(Bukkit.getLogger()));
}
