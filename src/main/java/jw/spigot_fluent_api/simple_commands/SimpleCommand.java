package jw.spigot_fluent_api.simple_commands;


import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.simple_commands.builders.SimpleCommandBuilder;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;
import jw.spigot_fluent_api.simple_commands.utilites.SimpleCommandUtility;
import jw.spigot_fluent_api.utilites.ObjectUtility;
import jw.spigot_fluent_api.utilites.PermissionsUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class SimpleCommand extends Command {

    @Setter
    private List<SimpleCommand> subCommands;
    @Setter
    private SimpleCommand parent;
    @Setter
    private Consumer<SimpleCommandEvent> onInvoke;

    private final CommandModel commandModel;

    @Setter
    private boolean logs;
    @Setter
    private boolean active;

    public SimpleCommand(CommandModel commandModel) {
        super(commandModel.getName());
        this.commandModel = commandModel;
        this.subCommands = new ArrayList<>();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.isActive())
            return false;

        if (!SimpleCommandUtility.hasSenderAccess(sender, commandModel.getCommandAccesses()))
            return false;

        if (!SimpleCommandUtility.hasSenderPermissions(sender, commandModel.getPermissions()))
            return false;

        if (!SimpleCommandUtility.validateArguments(args, commandModel.getArguments()))
            return false;

        try {
            var event = new SimpleCommandEvent(sender, args, args, true);
            onInvoke.accept(event);
            return event.getResult();
        } catch (Exception exception) {
            FluentPlugin.logError("Exception while invoke: " + this.getName());
            FluentPlugin.logError(exception.getMessage());
            FluentPlugin.logError(exception.toString());
            return false;
        }
    }

    public void addSubCommand(SimpleCommand command)
    {
        this.subCommands.add(command);
    }

    public String getName() {
        return commandModel.getName();
    }

    public static SimpleCommandBuilder build(String name) {
        return new SimpleCommandBuilder(name);
    }
}
