package jw.spigot_fluent_api.commands.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface FluentCommandEvent
{
    void execute(CommandSender player, String[] args);
}
