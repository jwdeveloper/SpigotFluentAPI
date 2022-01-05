package jw.spigot_fluent_api.legacy_commands.events;

import org.bukkit.command.CommandSender;

public interface FluentCommandEvent
{
    void execute(CommandSender player, String[] args);
}
