package jw.spigot_fluent_api.simple_commands.utilites;

import jw.spigot_fluent_api.simple_commands.enums.AccessType;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.StringJoiner;

public class SimpleCommandUtility {
    public static AccessType commandServerToCommandAccessType(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            return AccessType.PLAYER;
        }
        if (commandSender instanceof Entity) {
            return AccessType.ENTITY;
        }
        if (commandSender instanceof Block) {
            return AccessType.BLOCK_SENDER;
        }
        if (commandSender instanceof ConsoleCommandSender) {
            return AccessType.CONSOLE_SENDER;
        }
        return AccessType.COMMAND_SENDER;
    }

    public  static String connectArgs(String[] stringArray) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < stringArray.length; i++) {
            if (i < stringArray.length - 1)
                joiner.add(stringArray[i] + " ");
            else
                joiner.add(stringArray[i]);
        }
        return joiner.toString();
    }


}
