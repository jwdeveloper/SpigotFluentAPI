package jw.fluent.api.spigot.tasks;

import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class SimpleTaskTimer {
    private final TaskAction task;
    private Consumer<SimpleTaskTimer> onStop;

    private Consumer<SimpleTaskTimer> onStart;
    private int speed = 20;
    private int time = 0;
    private int runAfter = 0;
    private int stopAfter = Integer.MAX_VALUE;
    private boolean isCancel = false;
    private BukkitTask bukkitTask;

    public SimpleTaskTimer(int speed, TaskAction action) {
        this.speed = speed;
        this.task = action;
    }

    public void setIteration(int iteration)
    {
        this.time  =iteration;
    }
    public SimpleTaskTimer runAsync() {

        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(FluentApi.plugin(), this::taskBody, runAfter, speed);
        return this;
    }

    public void reset() {
        this.time = 0;
    }

    public SimpleTaskTimer run() {
        isCancel =false;
        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimer(FluentApi.plugin(), this::taskBody, runAfter, speed);
        return this;
    }

    private void taskBody()
    {
        try
        {
            if (time >= stopAfter || isCancel || bukkitTask.isCancelled())
            {
                if (onStop != null)
                    onStop.accept(this);
                stop();
                return;
            }
            task.execute(time, this);
            time++;
        }
        catch (Exception e)
        {
            FluentLogger.LOGGER.error("FluentTask error",e);
            stop();
        }
    }

    public void stop() {
        if (bukkitTask == null)
            return;

        Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
        bukkitTask.cancel();
        isCancel =true;
        bukkitTask = null;
    }

    public boolean isRunning()
    {
        return bukkitTask != null;
    }

    public void cancel() {
        isCancel = true;
    }

    public SimpleTaskTimer startAfterTicks(int iterations) {
        this.runAfter = iterations;
        return this;
    }

    public SimpleTaskTimer stopAfterIterations(int iterations) {
        this.stopAfter = iterations;
        return this;
    }

    public SimpleTaskTimer onStop(Consumer<SimpleTaskTimer> event) {
        this.onStop = event;
        return this;
    }

    public SimpleTaskTimer onStart(Consumer<SimpleTaskTimer> event) {
        this.onStart = event;
        return this;
    }

    public void runAgain() {
        this.time = 0;
        this.isCancel = false;
    }
}