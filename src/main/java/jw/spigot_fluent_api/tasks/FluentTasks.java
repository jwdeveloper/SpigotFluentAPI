package jw.spigot_fluent_api.tasks;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class FluentTasks
{
    public static FluentTaskTimer taskTimer(int speed,TaskAction task)
    {
        return new FluentTaskTimer(speed,task);
    }

    public static BukkitTask task(Consumer<Void> action)
    {
       return Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(), () ->
        {
            action.accept(null);

        });
    }
}
