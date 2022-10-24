package jw.fluent_api.spigot.commands.api.services;

import jw.fluent_api.spigot.commands.implementation.events.CommandEvent;
import jw.fluent_api.spigot.commands.implementation.events.ConsoleCommandEvent;
import jw.fluent_api.spigot.commands.implementation.events.PlayerCommandEvent;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

public interface EventsService
{
    public boolean invokeEvent(CommandSender commandSender, String[] allArgs, String[] commandArgs);


    public void onInvoke(Consumer<CommandEvent> event);

    public void onPlayerInvoke(Consumer<PlayerCommandEvent> event);

    public void onConsoleInvoke(Consumer<ConsoleCommandEvent> event);
}
