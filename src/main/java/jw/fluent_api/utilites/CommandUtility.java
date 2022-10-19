package jw.fluent_api.utilites;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandUtility
{
    public static boolean invokeCommand(Player player, String command)
    {
        if(command.startsWith("[s]"))
        {
            command = command.replace("[s]","");
            command = command.replaceAll("\\[p]",player.getName());

            if(command.contains("say"))
            {
                command = command.replaceAll("&","§");
                command = command.replaceAll("say","");
                String finalCommand = command;

                Bukkit.getOnlinePlayers().forEach(p ->
                {
                    p.sendMessage(finalCommand);
                });
                return true;
            }
            else
            {
                return Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
        }
        command = command.replaceAll("\\[p]",player.getName());
        if(command.contains("say"))
        {
            command = command.replaceAll("&","§");
            command = command.replaceAll("say","");
            player.sendMessage(command);
            return true;
        }
        return Bukkit.getServer().dispatchCommand(player, command);
    }
}
