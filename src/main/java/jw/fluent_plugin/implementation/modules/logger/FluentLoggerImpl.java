package jw.fluent_plugin.implementation.modules.logger;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_api.logger.api.SimpleLogger;
import jw.fluent_api.logger.implementation.SimpleLoggerImpl;

public class FluentLoggerImpl implements FluentLogger {
    private SimpleLogger simpleLogger;

    FluentLoggerImpl(SimpleLogger simpleLogger)
    {
       this.simpleLogger = simpleLogger;
    }

    @Override
    public void error(String message)
    {
        error(message,null);
    }

    @Override
    public  void error(String message, Exception ex)
    {
        simpleLogger.error(message,ex);
    }

    @Override
    public  void warning(String message, String... params)
    {
        simpleLogger.warning(message,params);
    }

    @Override
    public void success(String message, String... params)
    {
        simpleLogger.success(message,params);
    }

    @Override
    public  void info(String message, String... params)
    {
        simpleLogger.info(message,params);
    }

    @Override
    public  void log(String message, Object... params)
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
        simpleLogger.log(message, SimpleLoggerImpl.LogType.INFO,res);
    }
}
