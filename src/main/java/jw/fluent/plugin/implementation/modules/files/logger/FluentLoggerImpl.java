package jw.fluent.plugin.implementation.modules.files.logger;

import jw.fluent.api.logger.api.SimpleLogger;
import jw.fluent.api.logger.implementation.SimpleLoggerImpl;

public class FluentLoggerImpl implements FluentLogger {
    private SimpleLogger simpleLogger;
    private FluentLogger instance;

    public FluentLoggerImpl(SimpleLogger simpleLogger)
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
    public  void warning(String message, Object... params)
    {
        simpleLogger.warning(message,params);
    }

    @Override
    public void success(String message, Object... params)
    {
        simpleLogger.success(message,params);
    }

    @Override
    public  void info(String message, Object... params)
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
