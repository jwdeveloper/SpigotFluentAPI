package jw.fluent.plugin.implementation.modules.tasks;

import jw.fluent.api.spigot.tasks.SimpleTasks;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.api.spigot.tasks.TaskAction;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class FluentTasks
{
    private static SimpleTasks instance;

    static {
        instance = new SimpleTasks();
    }

    public static SimpleTasks getInstance()
    {
        return instance;
    }

    public static SimpleTaskTimer taskTimer(int speed, TaskAction task)
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
