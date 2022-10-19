package jw.fluent_api.minecraft.commands.api.services;

import jw.fluent_api.minecraft.messages.message.MessageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public interface MessagesService
{

    default String inactiveCommand(String commandName)
    {
        return "Sorry but "+commandName+" is inactive";
    }

    default String noPermission(CommandSender sender, String permission)
    {
        return new MessageBuilder()
                .inBrackets(sender.getName())
                .space()
                .color(ChatColor.RED)
                .color(ChatColor.BOLD)
                .text("has no permission ->")
                .space()
                .color(ChatColor.RESET)
                .text(permission)
                .toString();
    }

    default String noAccess(CommandSender sender)
    {
        return "Sorry but "+sender.getName()+" has no access";
    }

    default String invalidArgument(String message)
    {
        return "Sorry but invalid argument "+message;
    }
}
