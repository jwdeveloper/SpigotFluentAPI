package jw.fluent_api.minecraft.tasks;

import jw.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class FluentTaskFasade
{
    public FluentTaskTimer taskTimer(int speed, TaskAction task) {
        return new FluentTaskTimer(speed, task);
    }

    public BukkitTask task(Consumer<Void> action) {
        return Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(), () ->
        {
            action.accept(null);
        });
    }

    public BukkitTask taskAsync(Consumer<Void> action) {
        return Bukkit.getScheduler().runTaskAsynchronously(FluentPlugin.getPlugin(), () ->
        {
            action.accept(null);
        });
    }
}
