package jw.spigot_fluent_api.commands.events;

import org.bukkit.command.ConsoleCommandSender;

public interface FluentCommandConsoleEvent
{
    void execute(ConsoleCommandSender player, String[] args);
}
