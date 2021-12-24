package jw.spigot_fluent_api.simple_commands.builders;

import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandArgumentValidator;
import jw.spigot_fluent_api.simple_commands.validators.BoolValidator;
import jw.spigot_fluent_api.simple_commands.validators.ColorValidator;
import jw.spigot_fluent_api.simple_commands.validators.FloatValidator;
import jw.spigot_fluent_api.simple_commands.validators.IntValidator;
import org.bukkit.ChatColor;

import java.util.List;

public class CommandArgumentBuilder {

    private final SimpleCommandBuilder simpleCommandBuilder;
    private final List<CommandArgument> arguments;
    private final CommandArgument argument;

    public CommandArgumentBuilder(SimpleCommandBuilder simpleCommandBuilder, List<CommandArgument> arguments,String name) {
        this.simpleCommandBuilder = simpleCommandBuilder;
        this.arguments = arguments;
        this.argument = new CommandArgument();
        this.argument.setName(name);
    }

    public CommandArgumentBuilder setName(String name) {
        argument.setName(name.toLowerCase());
        return this;
    }

    public CommandArgumentBuilder setType(CommandArgumentType type)
    {
        argument.setType(type);

        switch (type)
        {
            case INT -> argument.addValidator(new IntValidator());
            case NUMBER, FLOAT -> argument.addValidator(new FloatValidator());
            case BOOL -> argument.addValidator(new BoolValidator());
            case COLOR -> argument.addValidator(new ColorValidator());
        }

        return this;
    }

    public CommandArgumentBuilder addValidator(CommandArgumentValidator validator)
    {
        argument.getValidators().add(validator);
        return this;
    }

    public CommandArgumentBuilder setDescription(String description)
    {
        argument.setDescription(description);
        return this;
    }

    public CommandArgumentBuilder setColor(ChatColor color)
    {
        argument.setColor(color);
        return this;
    }

    public SimpleCommandBuilder build() {
        arguments.add(argument);
        return simpleCommandBuilder;
    }
}
