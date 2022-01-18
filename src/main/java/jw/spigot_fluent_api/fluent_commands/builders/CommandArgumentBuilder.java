package jw.spigot_fluent_api.fluent_commands.builders;

import jw.spigot_fluent_api.fluent_commands.builders.sub_builders.ArgumentBuilder;
import jw.spigot_fluent_api.fluent_commands.enums.ArgumentDisplay;
import jw.spigot_fluent_api.fluent_commands.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.validators.CommandArgumentValidator;
import jw.spigot_fluent_api.fluent_commands.validators.BoolValidator;
import jw.spigot_fluent_api.fluent_commands.validators.ColorValidator;
import jw.spigot_fluent_api.fluent_commands.validators.FloatValidator;
import jw.spigot_fluent_api.fluent_commands.validators.IntValidator;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class CommandArgumentBuilder {

    private final ArgumentBuilder argumentBuilder;
    private final List<CommandArgument> arguments;
    private final CommandArgument argument;

    public CommandArgumentBuilder(ArgumentBuilder simpleCommandBuilder,
                                  List<CommandArgument> arguments,
                                  String name) {
        this.argumentBuilder = simpleCommandBuilder;
        this.arguments = arguments;
        this.argument = new CommandArgument();
        this.argument.setName(name);
    }

    public CommandArgumentBuilder(ArgumentBuilder simpleCommandBuilder,
                                  List<CommandArgument> arguments,
                                  String name,
                                  ArgumentType argumentType) {
        this(simpleCommandBuilder, arguments, name);
        setType(argumentType);
    }

    public CommandArgumentBuilder setType(ArgumentType type) {
        argument.setType(type);

        switch (type) {
            case INT -> argument.addValidator(new IntValidator());
            case NUMBER, FLOAT -> argument.addValidator(new FloatValidator());
            case BOOL -> {
                argument.addValidator(new BoolValidator());
                argument.setTabCompleter(List.of("true", "false"));
            }
            case COLOR -> {
                argument.addValidator(new ColorValidator());
                argument.setTabCompleter(Arrays.stream(ChatColor.values()).toList().stream().map(c -> c.name()).toList());
            }
        }

        return this;
    }

    public CommandArgumentBuilder setTabComplete(List<String> tabComplete) {
        argument.setTabCompleter(tabComplete);
        return this;
    }

    public CommandArgumentBuilder addTabComplete(String tabComplete) {
        var tabCompleter = argument.getTabCompleter();
        tabCompleter.add(tabComplete);
        return this;
    }

    public CommandArgumentBuilder addTabComplete(String tabComplete, int index) {
        var tabCompleter = argument.getTabCompleter();
        tabCompleter.set(index, tabComplete);
        return this;
    }

    public CommandArgumentBuilder addValidator(CommandArgumentValidator validator) {
        argument.getValidators().add(validator);
        return this;
    }

    public CommandArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode)
    {
        argument.setArgumentDisplayMode(argumentDisplayMode);
        return this;
    }

    public CommandArgumentBuilder setDescription(String description) {
        argument.setDescription(description);
        return this;
    }

    //not working yet
    public CommandArgumentBuilder setColor(ChatColor color) {
        argument.setColor(color);
        return this;
    }

    public ArgumentBuilder build() {
        arguments.add(argument);
        return argumentBuilder;
    }
}
