package jw.spigot_fluent_api.simple_commands.events;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;



public class ConsoleCommandEvent extends CommandEvent
{
    @Getter
    private ConsoleCommandSender consoleSender;

    public ConsoleCommandEvent(CommandSender sender, String[] commandArgs, String[] args, Object[] values, boolean result)
    {
        super(sender, commandArgs, args, values, result);
        consoleSender =  (ConsoleCommandSender) sender;
    }
}
