package jw.spigot_fluent_api.simple_commands;

import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.utilites.ObjectUtility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SimpleCommandManger extends BukkitCommand {

    private HashMap<String, SimpleCommand> commands;
    private static SimpleCommandManger instance;

    private static SimpleCommandManger getInstance() {
        if (instance == null)
            instance = new SimpleCommandManger();

        return instance;
    }

    private SimpleCommandManger() {
        super("");
    }

    public static void register(SimpleCommand command) {
        var instance = getInstance();
        if (instance.commands.containsKey(command.getName())) {
            return;
        }
        instance.commands.put(command.getName(), command);
        instance.registerBukkitCommand(command);
    }

    public static void unregister(SimpleCommand command) {
        var instance = getInstance();
        instance.unregisterBukkitCommand(command);
        instance.commands.remove(command.getName());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        var command = getCommand(args);
        if (command == null)
            return List.of();
        command = isSubcommandInvoked(command, args);
        if (command == null)
            return List.of();

        return super.tabComplete(sender, alias, args);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        var command = getCommand(args);
        if (command == null)
            return false;
        command = isSubcommandInvoked(command, args);
        if (command == null)
            return false;
        return command.execute(sender, commandLabel, args);
    }

    private SimpleCommand isSubcommandInvoked(SimpleCommand command, String[] args) {
        SimpleCommand result = command;
        String[] part = null;
        for (SimpleCommand subcommand : result.getSubCommands()) {
            if (args.length > 1) {
                part = Arrays.copyOfRange(args, 1, args.length);
                result = isSubcommandInvoked(result, args);
                if (result != null) {
                    break;
                }
            }
            if (subcommand.getName().equalsIgnoreCase(args[0])) {
                result = subcommand;
                break;
            }
        }
        return result;
    }

    private SimpleCommand getCommand(String[] args) {
        if (args.length == 0)
            return null;
        var commandName = args[0];
        if (!commands.containsKey(commandName))
            return null;
        return commands.get(commandName);
    }


    private void registerBukkitCommand(SimpleCommand simpleCommand) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(simpleCommand.getName(), simpleCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unregisterBukkitCommand(SimpleCommand command) {
        try {

            Object result = ObjectUtility.getPrivateField(Bukkit.getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Object map = ObjectUtility.getPrivateField(commandMap, "knownCommands");
            @SuppressWarnings("unchecked")
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
            knownCommands.remove(command.getName());
            for (String alias : command.getAliases()) {
                if (!knownCommands.containsKey(alias))
                    continue;

                if (!knownCommands.get(alias).toString().contains(command.getName())) {
                    continue;
                }
                knownCommands.remove(alias);
            }
        } catch (Exception e) {
            FluentPlugin.logError(e.getMessage());
            e.printStackTrace();
        }
    }

}
