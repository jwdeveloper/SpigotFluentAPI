package jw.fluent.plugin.implementation.modules.files.logger;

import jw.fluent.api.logger.implementation.SimpleLoggerImpl;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

public interface FluentLogger {
    void error(String message);

    void error(String message, Exception ex);

    void warning(String message, Object... params);

    void success(String message, Object... params);

    void info(String message, Object... params);

    void log(String message, Object... params);



    static FluentLogger LOGGER = createInstance();

    static FluentLogger createInstance()
    {
        if(Bukkit.getServer() != null)
        {
            return new FluentLoggerImpl(new SimpleLoggerImpl(Bukkit.getLogger()));
        }

        return new FluentLoggerImpl(new SimpleLoggerImpl(Logger.getGlobal()));
    }



}
