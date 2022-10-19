package jw.fluent_api.minecraft.commands.implementation.events;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommandEvent  extends CommandEvent
{
    @Getter
    private Player player;

    public PlayerCommandEvent(CommandSender sender, String[] commandArgs, String[] args, Object[] values, boolean result)
    {
        super(sender, commandArgs, args, values, result);
        player =  (Player) sender;
    }
}
