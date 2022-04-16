package jw.spigot_fluent_api.utilites;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;

public class Stopper
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
        FluentLogger.success(message+" Executed in "+inMs+"ms");
        return inMs;
    }
}
