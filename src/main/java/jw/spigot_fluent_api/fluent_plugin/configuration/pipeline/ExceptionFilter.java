package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;


import org.bukkit.Bukkit;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ExceptionFilter extends Handler {

    public void attachToBukkit() {
        Bukkit.getLogger().addHandler(this);
    }


    @Override
    public void close() throws SecurityException {

    }

    @Override
    public void flush() {

    }

    @Override
    public void publish(LogRecord logRecord) {
        if (logRecord.getMessage().contains("Exception")) {
            //Do your thing
        }
    }
}