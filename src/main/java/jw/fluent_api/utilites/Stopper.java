package jw.fluent_api.utilites;

import jw.fluent_api.logger.OldLogger;

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
        OldLogger.success(message+" Executed in "+inMs+"ms");
        return inMs;
    }
}
