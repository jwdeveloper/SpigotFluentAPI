package jw.fluent.api.utilites;

import jw.fluent.plugin.implementation.modules.logger.FluentLogger;

public class Stopwatch
{

    long startTime;
    long stopTime;
    boolean isStarted;
    public void start()
    {
        startTime = System.nanoTime();
    }

    public double stop(String message)
    {
        stopTime = System.nanoTime();
        var time =  stopTime - startTime;
        var inMs = time/Math.pow(10,6);
        FluentLogger.LOGGER.success(message+" Executed in "+inMs+"ms");
        return inMs;
    }
}
