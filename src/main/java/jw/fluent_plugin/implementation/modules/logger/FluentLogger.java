package jw.fluent_plugin.implementation.modules.logger;

public interface FluentLogger {
    void error(String message);

    void error(String message, Exception ex);

    void warning(String message, String... params);

    void success(String message, String... params);

    void info(String message, String... params);

    void log(String message, Object... params);
}
