package jw.spigot_fluent_api.utilites.messages;

import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import org.bukkit.ChatColor;

public class LogUtility
{
    public static MessageBuilder warning()
    {
        return getLog(ChatColor.YELLOW,"Warning");
    }

    public static MessageBuilder error()
    {
        return getLog(ChatColor.DARK_RED,"Error");
    }

    public static MessageBuilder exception()
    {
        return getLog(ChatColor.DARK_RED,"Critical error");
    }

    public static MessageBuilder success()
    {
        return getLog(ChatColor.GREEN,"Success");
    }

    public static MessageBuilder info()
    {
      return getLog(ChatColor.AQUA,"Info");
    }

    private static MessageBuilder getLog(ChatColor color,String type)
    {
        return new MessageBuilder().color(color).inBrackets(type).color(ChatColor.RESET).space();
    }

}
