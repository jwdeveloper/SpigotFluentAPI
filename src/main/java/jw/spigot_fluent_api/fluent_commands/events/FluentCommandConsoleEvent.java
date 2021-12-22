package jw.spigot_fluent_api.fluent_commands.events;

import org.bukkit.command.CommandSender;

public interface FluentCommandConsoleEvent
{
    void execute(CommandSender player, String[] args);
}
