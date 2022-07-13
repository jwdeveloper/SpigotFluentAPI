package jw.spigot_fluent_api.fluent_commands.implementation.events;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommandEvent  extends CommandEvent
{
    @Getter
    private Player playerSender;

    public PlayerCommandEvent(CommandSender sender, String[] commandArgs, String[] args, Object[] values, boolean result)
    {
        super(sender, commandArgs, args, values, result);
        playerSender =  (Player) sender;
    }
}
