package jw.spigot_fluent_api.utilites.messages;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public final class MessageUtility
{
    public static MessageBuilder to(Player player)
    {
        return new MessageBuilder(player);
    }
    public static MessageBuilder to(List<Player> playerList)
    {
      return new MessageBuilder(playerList.toArray(new Player[playerList.size()]));
    }
    public static MessageBuilder toConsole()
    {
        return new MessageBuilder(null);
    }
}
