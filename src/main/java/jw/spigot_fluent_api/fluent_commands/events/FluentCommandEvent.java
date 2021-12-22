package jw.spigot_fluent_api.fluent_commands.events;

import org.bukkit.command.CommandSender;

public interface FluentCommandEvent
{
    void execute(CommandSender player, String[] args);
}
