package jw.spigot_fluent_api.commands.events;

import org.bukkit.entity.Player;

public interface FluentCommandEvent
{
     void execute(Player player,String[] args);
}
