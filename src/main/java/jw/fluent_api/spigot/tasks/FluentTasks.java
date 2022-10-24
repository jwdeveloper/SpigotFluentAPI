package jw.fluent_api.spigot.tasks;

import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class FluentTasks
{
    private static FluentTaskFasade instance;

    static {
        instance = new FluentTaskFasade();
    }

    public static FluentTaskFasade getInstance()
    {
        return instance;
    }

    public static FluentTaskTimer taskTimer(int speed,TaskAction task)
    {
        return instance.taskTimer(speed,task);
    }

    public static BukkitTask task(Consumer<Void> action)
    {
       return instance.task(action);
    }

    public static BukkitTask taskAsync(Consumer<Void> action)
    {
        return instance.task(action);
    }
}
