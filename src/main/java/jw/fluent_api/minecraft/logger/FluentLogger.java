package jw.fluent_api.minecraft.logger;
import jw.fluent_api.minecraft.logger.api.SimpleLogger;
import jw.fluent_api.minecraft.logger.implementation.SimpleLoggerImpl;

public class FluentLogger
{

    private static final FluentLogger instance;
    private SimpleLoggerImpl simpleLogger;

    static {
        instance = new FluentLogger();
    }

    FluentLogger() {
        simpleLogger = new SimpleLoggerImpl();
    }

    public static SimpleLogger CreateLogger()
    {
        return new SimpleLoggerImpl();
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

    public static void log(String message, Object... params)
    {
        var res = new String[params.length];
        for(var i =0;i<params.length;i++)
        {
            if(params[i] == null)
            {
                res[i] = "null";
            }
            else
            {
                res[i] = params[i].toString();
            }
        }
        instance.simpleLogger.log(message, SimpleLoggerImpl.LogType.INFO,res);
    }

}
