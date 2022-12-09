package jw.fluent.api.spigot.tasks;

import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class SimpleTasks
{
    public SimpleTaskTimer taskTimer(int ticks, TaskAction task) {
        return new SimpleTaskTimer(ticks, task);
    }

    public BukkitTask task(Consumer<Void> action) {
        return Bukkit.getScheduler().runTask(FluentApi.plugin(), () ->
        {
            action.accept(null);
        });
    }

    public BukkitTask taskAsync(Consumer<Void> action) {
        return Bukkit.getScheduler().runTaskAsynchronously(FluentApi.plugin(), () ->
        {
            action.accept(null);
        });
    }
}
