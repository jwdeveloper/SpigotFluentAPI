package jw.fluent.api.spigot.tasks;

import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class FluentTaskTimer {
    private final TaskAction task;
    private Consumer<FluentTaskTimer> onStop;

    private Consumer<FluentTaskTimer> onStart;
    private int speed = 20;
    private int time = 0;
    private int runAfter = 0;
    private int stopAfter = Integer.MAX_VALUE;
    private boolean isCancel = false;
    private BukkitTask bukkitTask;

    public FluentTaskTimer(int speed, TaskAction action) {
        this.speed = speed;
        this.task = action;
    }

    public void setIteration(int iteration)
    {
        this.time  =iteration;
    }
    public FluentTaskTimer runAsync() {

        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(FluentApi.plugin(), () ->
        {
            try
            {
                if (time >= stopAfter || isCancel) {
                    if (this.onStop != null)
                        this.onStop.accept(this);
                    stop();
                    return;
                }
                task.execute(time, this);
                time++;
            }
            catch (Exception e)
            {
                FluentApi.logger().error("FluentTask error",e);
                stop();
            }
        }, runAfter, speed);
        return this;
    }

    public void reset() {
        this.time = 0;
    }

    public FluentTaskTimer run() {

        if (onStart != null)
            onStart.accept(this);
        bukkitTask = Bukkit.getScheduler().runTaskTimer(FluentApi.plugin(), () ->
        {
            try
            {
                if (time >= stopAfter || isCancel) {
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
                FluentApi.logger().error("FluentTask error",e);
                stop();
            }
        }, runAfter, speed);
        return this;
    }

    public void stop() {
        if (bukkitTask == null)
            return;

        bukkitTask.cancel();
        bukkitTask = null;
    }

    public boolean isRunning()
    {
        return bukkitTask != null;
    }

    public void cancel() {
        isCancel = true;
    }

    public FluentTaskTimer startAfterIterations(int iterations) {
        this.runAfter = iterations;
        return this;
    }

    public FluentTaskTimer stopAfterIterations(int iterations) {
        this.stopAfter = iterations;
        return this;
    }

    public FluentTaskTimer onStop(Consumer<FluentTaskTimer> event) {
        this.onStop = event;
        return this;
    }

    public FluentTaskTimer onStart(Consumer<FluentTaskTimer> event) {
        this.onStart = event;
        return this;
    }

    public void runAgain() {
        this.time = 0;
        this.isCancel = false;
    }
}