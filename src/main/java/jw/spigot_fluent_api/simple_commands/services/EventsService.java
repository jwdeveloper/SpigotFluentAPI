package jw.spigot_fluent_api.simple_commands.services;

import jw.spigot_fluent_api.simple_commands.events.CommandEvent;
import jw.spigot_fluent_api.simple_commands.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.simple_commands.events.PlayerCommandEvent;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

public interface EventsService
{
    public boolean invokeEvent(CommandSender commandSender, String[] allArgs, String[] commandArgs);


    public void onInvoke(Consumer<CommandEvent> event);

    public void onPlayerInvoke(Consumer<PlayerCommandEvent> event);

    public void onConsoleInvoke(Consumer<ConsoleCommandEvent> event);
}
