package jw.spigot_fluent_api.commands.events;

import org.bukkit.command.CommandSender;

public interface FluentCommandConsoleEvent
{
    void execute(CommandSender player, String[] args);
}
