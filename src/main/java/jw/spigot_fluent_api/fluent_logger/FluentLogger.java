package jw.spigot_fluent_api.fluent_logger;
import jw.spigot_fluent_api.fluent_logger.implementation.SimpleLogger;

public class FluentLogger
{
    private static final FluentLogger instance;
    private SimpleLogger simpleLogger;

    static {
        instance = new FluentLogger();
    }

    FluentLogger() {
        simpleLogger = new SimpleLogger();
    }

    public void active(boolean active)
    {

    }

    public static void error(String message)
    {
        error(message,null);
    }

    public static void error(String message, Exception ex)
    {
       instance.simpleLogger.error(message,ex);
    }

    public static void warning(String message, String... params)
    {
        instance.simpleLogger.warning(message,params);
    }

    public static void success(String message, String... params)
    {
        instance.simpleLogger.success(message,params);
    }

    public static void info(String message, String... params)
    {
        instance.simpleLogger.info(message,params);
    }

    public static void log(String message, String... params)
    {
        instance.simpleLogger.log(message,params);
    }

}
