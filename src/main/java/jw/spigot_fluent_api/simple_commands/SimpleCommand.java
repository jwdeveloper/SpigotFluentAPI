package jw.spigot_fluent_api.simple_commands;


import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.simple_commands.configuration.SimpleCommandConfiguration;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SimpleCommand extends BukkitCommand {
    private final SimpleCommandConfiguration configuration;
    private List<CommandModel> commands;

    public SimpleCommand() {
        super("name");
        commands = new ArrayList<>();
        configuration = new SimpleCommandConfiguration(this);
        initialize();
    }

    public abstract void configure(SimpleCommandConfiguration configurator);


    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        String[] arguments = args;
        CommandModel subCommand = findCommand(args);
        if (subCommand == null)
        {
           return false;
        }
        for (int j = 0; j < args.length; j++)
        {
            if (!args[j].equalsIgnoreCase(subCommand.getName())) {
                continue;
            }
            arguments = Arrays.copyOfRange(args, j + 1, args.length);
        }
        try
        {
            if(!subCommand.validate(sender,arguments))
            {
                return false;
            }
            subCommand.runCommand(sender,arguments);
        }
        catch (Exception e)
        {
            FluentPlugin.logError("Error!");
            return false;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return super.tabComplete(sender, alias, args);
    }

    private void initialize() {
        try {
            commands = SimpleCommandResolver.resolveCommands(this);
        } catch (Exception e) {
            FluentPlugin.logError(e.getMessage());
        }
        configure(configuration);
        //configuration.apply();
        registerCommand();
    }

    private CommandModel findCommand(String[] args)
    {
        for (var command: commands)
        {
           if(args.length ==0 && command.isNameEmpty())
           {
               return command;
           }
           if(!args[0].equalsIgnoreCase(command.getName()))
               continue;
           return command;
        }
        return null;
    }

    private void registerCommand() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(this.getName(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
