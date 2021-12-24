package jw.spigot_fluent_api.simple_commands.utilites;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.utilites.PermissionsUtility;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class SimpleCommandUtility {
    public static CommandAccessType commandServerToCommandAccessType(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            return CommandAccessType.PLAYER;
        }
        if (commandSender instanceof ConsoleCommandSender) {
            return CommandAccessType.CONSOLE_SENDER;
        }
        if (commandSender instanceof Entity) {
            return CommandAccessType.ENTITY;
        }
        if (commandSender instanceof Block) {
            return CommandAccessType.BLOCK_SENDER;
        }
        return CommandAccessType.COMMAND_SENDER;
    }

}
