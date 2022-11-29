package jw.fluent_api.logger.implementation;

import jw.fluent_api.logger.api.SimpleLogger;
import jw.fluent_api.logger.api.SimpleLoggerBuilder;
import jw.fluent_plugin.implementation.modules.logger.FluentLoggerImpl;

import java.util.logging.Logger;

public class SimpleLoggerBuilderImpl implements SimpleLoggerBuilder
{
    private Logger logger;

    public SimpleLoggerBuilderImpl(Logger logger)
    {
        this.logger = logger;
    }


    public SimpleLogger build()
    {
        var simpleLogger = new SimpleLoggerImpl(logger);
       return simpleLogger;
    }
}
