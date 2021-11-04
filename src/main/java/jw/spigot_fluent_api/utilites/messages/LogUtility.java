package jw.spigot_fluent_api.utilites.messages;

import org.bukkit.ChatColor;

public class LogUtility
{
    public static MessageBuilder error()
    {
        return getLog(ChatColor.DARK_RED,"Error");
    }

    public static MessageBuilder success()
    {
        return getLog(ChatColor.DARK_GREEN,"Success");
    }

    public static MessageBuilder info()
    {
      return getLog(ChatColor.YELLOW,"Info");
    }

    private static MessageBuilder getLog(ChatColor color,String type)
    {
        return new MessageBuilder().color(color).inBrackets(type).color(ChatColor.RESET).space();
    }

}
