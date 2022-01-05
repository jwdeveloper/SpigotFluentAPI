package jw.spigot_fluent_api.fluent_tasks;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class FluentTaskTimer
{
    private final TaskAction task;
    private Consumer<FluentTaskTimer> onStop;
    private int speed = 20;
    private int time = 0;
    private int runAfter = 0;
    private int stopAfter = Integer.MAX_VALUE;
    private boolean isCancel = false;
    private BukkitTask bukkitTaskId;

    public FluentTaskTimer(int speed, TaskAction action)
    {
        this.speed = speed;
        this.task = action;
    }
    public FluentTaskTimer runAsync()
    {
        bukkitTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(FluentPlugin.getPlugin(),()->
        {
            if(time>=stopAfter || isCancel)
            {
                if(this.onStop!=null)
                    this.onStop.accept(this);
                stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runAfter,speed);
        return this;
    }
    public void reset()
    {
        this.time = 0;
    }

    public FluentTaskTimer run()
    {
        bukkitTaskId = Bukkit.getScheduler().runTaskTimer(FluentPlugin.getPlugin(),()->
        {
            if(time>=stopAfter || isCancel)
            {
                if(onStop!=null)
                    onStop.accept(this);
                stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runAfter,speed);
        return this;
    }
    public void stop()
    {
        if(bukkitTaskId !=null)
            bukkitTaskId.cancel();
    }
    public void cancel()
    {
        isCancel =true;
    }

    public FluentTaskTimer startAfterIterations(int iterations)
    {
        this.runAfter = iterations;
        return this;
    }
    public FluentTaskTimer stopAfterIterations(int iterations)
    {
        this.stopAfter = iterations;
        return this;
    }
    public FluentTaskTimer onStop(Consumer<FluentTaskTimer> nextTask)
    {
        this.onStop = nextTask;
        return this;
    }
    public void runAgain()
    {
        this.time = 0;
        this.isCancel = false;
    }
}