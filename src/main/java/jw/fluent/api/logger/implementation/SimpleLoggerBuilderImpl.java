package jw.fluent.api.logger.implementation;

import jw.fluent.api.logger.api.SimpleLogger;
import jw.fluent.api.logger.api.SimpleLoggerBuilder;

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
