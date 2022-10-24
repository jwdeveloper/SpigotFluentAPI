package jw.fluent_api.spigot.commands.implementation.builder;

import jw.fluent_api.spigot.commands.api.builder.ArgumentBuilder;
import jw.fluent_api.spigot.commands.api.enums.ArgumentDisplay;
import jw.fluent_api.spigot.commands.api.enums.ArgumentType;
import jw.fluent_api.spigot.commands.api.models.CommandArgument;
import jw.fluent_api.spigot.commands.implementation.validators.*;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public class ArgumentBuilderImpl implements ArgumentBuilder {

    private final CommandArgument argument;

    public ArgumentBuilderImpl(String argumentName) {
        argument = new CommandArgument();
        argument.setName(argumentName);
    }

    @Override
    public ArgumentBuilder setType(ArgumentType type) {
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
            case PLAYERS -> {
                argument.setArgumentDisplayMode(ArgumentDisplay.TAB_COMPLETE);
            }
        }
        argument.setType(type);
        return this;
    }

    @Override
    public ArgumentBuilder setTabComplete(List<String> tabComplete) {
        argument.setTabCompleter(tabComplete);
        return this;
    }

    @Override
    public ArgumentBuilder setTabComplete(String tabComplete) {
        var tabCompleter = argument.getTabCompleter();
        tabCompleter.add(tabComplete);
        return this;
    }

    @Override
    public ArgumentBuilder setTabComplete(String tabComplete, int index) {
        var tabCompleter = argument.getTabCompleter();
        tabCompleter.set(index, tabComplete);
        return this;
    }

    @Override
    public ArgumentBuilder setValidator(CommandArgumentValidator validator) {
        argument.getValidators().add(validator);
        return this;
    }

    @Override
    public ArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode) {
        argument.setArgumentDisplayMode(argumentDisplayMode);
        return this;
    }

    @Override
    public ArgumentBuilder setDescription(String description) {
        argument.setDescription(description);
        return this;
    }

    public CommandArgument build() {
        return argument;
    }
}
